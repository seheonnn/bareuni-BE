package com.umc.BareuniBE.service;

import com.umc.BareuniBE.config.security.JwtTokenProvider;
import com.umc.BareuniBE.dto.*;
import com.umc.BareuniBE.entities.User;
import com.umc.BareuniBE.global.BaseException;
import com.umc.BareuniBE.global.enums.RoleType;
import com.umc.BareuniBE.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.umc.BareuniBE.global.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private static final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d|[^A-Za-z\\d]).{8,20}$";


    //private final BCryptPasswordEncoder bCryptPasswordEncoder;
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    public UserRes.UserJoinRes join(UserReq.UserJoinReq request) throws BaseException {
        //System.out.println("Service의 join함수 실행중");
        if(!request.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$"))
            throw new BaseException(POST_USERS_INVALID_EMAIL);
        System.out.println("UserService의 Join메소드 실행");
        // 이메일을 찾아봐. 근데 이미 있었어? 그럼 중복된 이메일입니다 오류!
        // 이메일 없으면
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        System.out.println("DB에 이메일 있는지 확인 :" + userOptional);

        if(userOptional.isEmpty()){ // 이메일 없었으면 회원가입 시작~
            //String encryptedPw = bCryptPasswordEncoder.encode(request.getPassword());
            System.out.println("가입안된 이메일이므로 회원가입을 시작함.");
            String encryptedPw = encoder.encode(request.getPassword());
            /*System.out.println("encryptedPw: "+encryptedPw);
            System.out.println(request.getEmail());
            System.out.println(request.getNickname());
            System.out.println(request.getName());
            System.out.println(request.getPhone());
            System.out.println(request.getGender());
            System.out.println(request.getAge());
            System.out.println(request.isOrtho());
            System.out.println(request.getRole());
            System.out.println(request.getProvider());*/

            User newUser = User.builder()
                    .email(request.getEmail())
                    .password(encryptedPw)
                    .nickname(request.getNickname())
                    //.name(request.getName()) // 이름과 전화번호는 상담신청 이후에 생기도록,,,,,
                    //.phone(request.getPhone())
                    .gender(request.getGender())
                    .age(request.getAge())
                    .ortho(request.isOrtho())
                    .role(RoleType.USER)
                    .provider(request.getProvider())
                    .build();
            //System.out.println("새로 가입하는 유저: "+newUser);
            User user = userRepository.saveAndFlush(newUser);
            return new UserRes.UserJoinRes(user);
        }else{
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }
    }

    //임시비밀번호 발급 및 재설정
    @Transactional
    public boolean updatePassword(Long userIdx, String newPassword) throws BaseException{
        Optional<User> user = userRepository.findById(userIdx);
        user.ifPresent(u -> {
            u.setPassword(newPassword);
            userRepository.saveAndFlush(u);
        });
        return true;
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


    public Long findUserByEmail(String email) throws BaseException{
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return user.getUserIdx();
        } else {
            throw new BaseException(POST_USERS_NOT_FOUND_EMAIL);
        }
    }


    //코드확인 후 비밀번호 재설정
    private boolean isValidPassword(String password) {
        return password.matches(PASSWORD_PATTERN);
    }

    public String changePassword(String email, PasswordUpdateReq.NewPasswordUpdateReq passwordUpdateReq) throws BaseException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(USERS_EMPTY_USER_ID));

        // 새로운 비밀번호 형식이 맞는지 확인
        String newPassword = passwordUpdateReq.getNewPassword();
        if (!isValidPassword(newPassword)) {
            throw new BaseException(INVALID_PASSWORD_FORMAT);
        }

        // 새로운 비밀번호와 비밀번호 확인이 일치하는지 확인
        String confirmPassword = passwordUpdateReq.getConfirmPassword();
        if (newPassword != null && !newPassword.equals(confirmPassword)) {
            throw new BaseException(NEW_PASSWORD_INCORRECT);
        }

        // 새로운 비밀번호가 null이 아닌 경우, 사용자의 비밀번호를 새로운 값으로 업데이트
        if (newPassword != null) {
            user.setPassword(newPassword);
        }

        userRepository.save(user);

        return "비밀번호 변경 성공";
    }





}
