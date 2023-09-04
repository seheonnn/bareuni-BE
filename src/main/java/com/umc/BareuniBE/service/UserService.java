package com.umc.BareuniBE.service;

import com.umc.BareuniBE.config.security.JwtTokenProvider;
import com.umc.BareuniBE.dto.PasswordUpdateReq;
import com.umc.BareuniBE.dto.TokenDTO;
import com.umc.BareuniBE.dto.UserReq;
import com.umc.BareuniBE.dto.UserRes;
import com.umc.BareuniBE.entities.User;
import com.umc.BareuniBE.global.BaseException;
import com.umc.BareuniBE.global.BaseResponseStatus;
import com.umc.BareuniBE.global.enums.RoleType;
import com.umc.BareuniBE.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.umc.BareuniBE.global.BaseResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UploadService uploadService;

    private final UserRepository userRepository;
    private final CommunityRepository communityRepository;
    private final LikeRepository likeRepository;

    private final CommentRepository commentRepository;

    private final BookingRepository bookingRepository;

    private final ScrapRepository scrapRepository;

    private final ReviewRepository reviewRepository;
    private final EmailService emailService;

    private final JwtTokenProvider jwtTokenProvider;

    private final RedisTemplate redisTemplate;

    private static final String PASSWORD_PATTERN = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+{}\\[\\]:;<>,.?~\\\\-]).{8,20}$";

    //private final BCryptPasswordEncoder bCryptPasswordEncoder;
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserRes.UserJoinRes join(MultipartFile file, UserReq.UserJoinReq request) throws BaseException, IOException {
        //System.out.println("Service의 join함수 실행중");

        if(!request.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$"))
            throw new BaseException(POST_USERS_INVALID_EMAIL);
        if(!request.getPassword().matches(PASSWORD_PATTERN))
            throw new BaseException(INVALID_PASSWORD_FORMAT);
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        if(userOptional.isEmpty()){

            String encryptedPw = encoder.encode(request.getPassword());

            String profileUrl = file == null ? null : uploadService.uploadImage(file);

            User newUser = User.builder()
                    .email(request.getEmail())
                    .password(encryptedPw)
                    .nickname(request.getNickname())
                    .gender(request.getGender())
                    .age(request.getAge())
                    .ortho(request.isOrtho())
                    .role(RoleType.USER)
                    .provider(request.getProvider())
                    .profile(profileUrl)
                    .reception(request.isReception())
                    .build();
            User user = userRepository.saveAndFlush(newUser);
            return new UserRes.UserJoinRes(user);
        }else{
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }
    }

    //임시비밀번호 발급 및 재설정
    @Transactional
    public String updatePassword(Long userIdx, String newPassword) throws BaseException{
        Optional<User> user = userRepository.findById(userIdx);
        user.ifPresent(u -> {
            u.setPassword(encoder.encode(newPassword));
            userRepository.saveAndFlush(u);
        });
        throw new BaseException(SUCCESS);
    }

    public boolean emailValidation(String email) throws BaseException {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

//    public Long findUserByEmail(String email) throws BaseException{
//        Optional<User> optionalUser = userRepository.findByEmail(email);
//
//        if (optionalUser.isPresent()) {
//            User user = optionalUser.get();
//            return user.getUserIdx();
//        } else {
//            throw new BaseException(POST_USERS_NOT_FOUND_EMAIL);
//        }
//    }

    //코드확인 후 비밀번호 재설정
    private boolean isValidPassword(String password) {
        return password.matches(PASSWORD_PATTERN);
    }

//    public String changePassword(String email, PasswordUpdateReq.NewPasswordUpdateReq passwordUpdateReq) throws BaseException {
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new BaseException(POST_USERS_NOT_FOUND_EMAIL));
//
//        // 새로운 비밀번호 형식이 맞는지 확인
//        String newPassword = passwordUpdateReq.getNewPassword();
//        if (!isValidPassword(newPassword)) {
//            throw new BaseException(INVALID_PASSWORD_FORMAT);
//        }
//
//        // 새로운 비밀번호와 비밀번호 확인이 일치하는지 확인
//        String confirmPassword = passwordUpdateReq.getConfirmPassword();
//        if (newPassword != null && !newPassword.equals(confirmPassword)) {
//            throw new BaseException(NEW_PASSWORD_INCORRECT);
//        }
//
//        // 새로운 비밀번호가 null이 아닌 경우, 사용자의 비밀번호를 새로운 값으로 업데이트
//        if (newPassword != null) {
//            user.setPassword(encoder.encode(newPassword));
//        }
//
//        userRepository.save(user);
//
//        return "비밀번호 변경 성공";
//    }

    // 로그인
    public List<TokenDTO> login(UserReq.UserLoginReq request) throws BaseException {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BaseException(FAILED_TO_LOGIN)); // 가입안된 이메일
        if(!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new BaseException(FAILED_TO_LOGIN); // 비밀번호 일치 X
        }

        // 토큰 발급해서
        TokenDTO refreshToken = jwtTokenProvider.createRefreshToken(user.getEmail());
        TokenDTO accessToken = jwtTokenProvider.createAccessToken(user.getEmail());

        // login 시 Redis에 RT:bareuni@email.com(key): --refresh token실제값--(value) 형태로 refresh 토큰 저장하기
        // opsForValue() : set을 통해 key,value값 저장하고 get(key)통해 value가져올 수 있음.
        // refreshToken.getTokenExpriresTime().getTime() : 리프레시 토큰의 만료시간이 지나면 해당 값 자동 삭제
        redisTemplate.opsForValue().set("RT:"+user.getEmail(),refreshToken.getToken(),refreshToken.getTokenExpriresTime().getTime(),TimeUnit.MILLISECONDS);

        List<TokenDTO> tokenDTOList = new ArrayList<>();
        tokenDTOList.add(refreshToken);
        tokenDTOList.add(accessToken);
        System.out.println(tokenDTOList);

        return tokenDTOList;
    }

    // 로그아웃 - userIdx
    public String logout(HttpServletRequest request) throws BaseException {

        Long userIdx = jwtTokenProvider.getCurrentUser(request);

        System.out.println("getCurrentUser()로 가져온 userIdx : "+userIdx);
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new BaseException(USERS_EMPTY_USER_ID));

        // Redis 에서 해당 User email 로 저장된 Refresh Token 이 있는지 여부를 확인 후 있을 경우 삭제
        if (redisTemplate.opsForValue().get("RT:" + user.getEmail()) != null) {
            // Refresh Token 삭제
            redisTemplate.delete("RT:" + user.getEmail());
        }
        // 해당 AccessToken 유효시간 가지고 와서 BlackList 로 저장하기
        String accessToken = jwtTokenProvider.resolveAccessToken(request);
        Long expiration = jwtTokenProvider.getExpireTime(accessToken).getTime();
        // Redis 에 --accesstoken--(key) : logout(value) 로 저장, token 만료시간 지나면 자동 삭제
        redisTemplate.opsForValue().set(accessToken, "logout", expiration, TimeUnit.MILLISECONDS);

        return "로그아웃 성공";
    }

    // 토큰 재발급 - 요청 온 refresh token으로 access, refresh token을 모두 새로 발급한다
    public List<TokenDTO> reissue(HttpServletRequest request) throws BaseException {
        String rtk = request.getHeader("rtk");
        System.out.println("reissue함수실행 rtk: "+rtk);

        // refresh token 유효성 검증
        if (!jwtTokenProvider.validateToken(rtk))
            throw new BaseException(INVALID_JWT);

        String email = jwtTokenProvider.getTokenSub(rtk);
        System.out.println("rtk subject인 email: "+email);

        // Redis에서 email 기반으로 저장된 refresh token 값 가져오기
        String refreshToken = (String) redisTemplate.opsForValue().get("RT:" + email);
        if(!refreshToken.equals(rtk)) {
            throw new BaseException(RTK_INCORRECT);
        }

        // refresh token 유효할 경우 새로운 토큰 생성
        List<TokenDTO> tokenDTOList = new ArrayList<>();
        TokenDTO newRefreshToken = jwtTokenProvider.createRefreshToken(email);
        TokenDTO newAccessToken = jwtTokenProvider.createAccessToken(email);
        tokenDTOList.add(newRefreshToken);
        tokenDTOList.add(newAccessToken);
        System.out.println("Access Token, Refresh Token 재발행: " +tokenDTOList);

        // Redis에 refresh token 업데이트
        redisTemplate.opsForValue().set("RT:"+email,newRefreshToken.getToken(),newRefreshToken.getTokenExpriresTime().getTime(),TimeUnit.MILLISECONDS);

        return tokenDTOList;
    }

    // 회원 탈퇴 - userIdx
    @Transactional
    public String deactivateUser(HttpServletRequest request) throws BaseException {
        Long userIdx = jwtTokenProvider.getCurrentUser(request);

        System.out.println("getCurrentUser()로 가져온 userIdx : "+userIdx);
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new BaseException(USERS_EMPTY_USER_ID));

        // Redis에 로그인되어있는 토큰 삭제
        // Redis 에서 해당 User email 로 저장된 Refresh Token 이 있는지 여부를 확인 후 있을 경우 삭제
        if (redisTemplate.opsForValue().get("RT:" + user.getEmail()) != null) {
            // Refresh Token 삭제
            redisTemplate.delete("RT:" + user.getEmail());
        }
        String accessToken = jwtTokenProvider.resolveAccessToken(request);
        // 탈퇴한 토큰을 차단 (deactivateUser 토큰 블랙리스트)
        Long expiration = jwtTokenProvider.getExpireTime(accessToken).getTime();
        // Redis 에 --accesstoken--(key) : logout(value) 로 저장, token 만료시간 지나면 자동 삭제
        redisTemplate.opsForValue().set(accessToken, "logout", expiration, TimeUnit.MILLISECONDS);

        // 해당 회원 삭제
        communityRepository.deleteAllByUser(user);
        likeRepository.deleteAllByUser(user);
        commentRepository.deleteAllByUser(user);
        bookingRepository.deleteAllByUser(user);
        scrapRepository.deleteAllByUser(user);
        reviewRepository.deleteAllByUser(user);
        userRepository.deleteById(user.getUserIdx());

        //시큐리티
        //SecurityContextHolder.clearContext();
        return "회원 탈퇴 성공";
    }

    // 회원 테스트
    public String test(HttpServletRequest request) throws BaseException {
        Long userIdx = jwtTokenProvider.getCurrentUser(request);
        System.out.println("getCurrentUser()로 가져온 userIdx: "+userIdx);
        return "test성공";
    }

    // 회원가입 시 중복된 이메일인지 확인
    public Boolean checkEmail(String email) throws BaseException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(USERS_EMPTY_USER_ID));

        if(user != null) {
            return true; // 중복된 이메일
        }else {
            return false; // 사용가능한 이메일
        }
    }

    // 소셜로그인 - 프로필 설정
    public UserRes.SocialLoginRes createProfile(MultipartFile file, UserReq.UserProfileReq request) throws BaseException, IOException {
        String profileUrl = uploadService.uploadImage(file);

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BaseException(FAILED_TO_LOGIN));
        user.setNickname(request.getNickname());
        user.setGender(request.getGender());
        user.setAge(request.getAge());
        user.setOrtho(request.isOrtho());
        user.setRole(RoleType.USER);
        user.setProvider("카카오");
        user.setProfile(profileUrl);

        return new UserRes.SocialLoginRes(user);
        /*if(userOptional.isEmpty()){

            String profileUrl = uploadService.uploadImage(file);

            User user = userOptional;

            User newUser = User.builder()
                    .nickname(request.getNickname())
                    .gender(request.getGender())
                    .age(request.getAge())
                    .ortho(request.isOrtho())
                    .role(RoleType.USER)
                    .provider("카카오")
                    .profile(profileUrl)
                    .build();
            User user = userRepository.saveAndFlush(newUser);
            return new UserRes.SocialLoginRes(user);
        }else{
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }*/
    }

    // 회원가입 시 이메일 중복 확인
    public Boolean checkEmail(UserReq.EmailCheckReq request) throws BaseException{
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return true; // 중복됨
        } else {
            throw new BaseException(POST_USERS_NOT_FOUND_EMAIL); // 중복 안됨 사용가능
        }
    }

    // 회원정보 조회
    public UserRes.UserInfo getUserInfo(HttpServletRequest request) throws BaseException {
        Long userIdx = jwtTokenProvider.getCurrentUser(request);
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new BaseException(FAILED_TO_LOGIN));

        UserRes.UserInfo userInfo = new UserRes.UserInfo();
        userInfo.setEmail(user.getEmail());
        userInfo.setNickname(user.getNickname());
        userInfo.setNickname(user.getNickname());
        userInfo.setAge(user.getAge());
        userInfo.setOrtho(user.isOrtho());
        userInfo.setProfile(user.getProfile());
        return userInfo;
    }

    public BaseResponseStatus getEmPw(UserReq.EmailCheckReq emailCheckReq) throws Exception {
        User user = userRepository.findByEmail(emailCheckReq.getEmail())
                .orElseThrow(() -> new BaseException(FAILED_TO_LOGIN));
        String emPw = emailService.sendSimpleMessage(user.getEmail(), true);
        user.setPassword(encoder.encode(emPw));
        userRepository.saveAndFlush(user);
        return SUCCESS;
    }
}
