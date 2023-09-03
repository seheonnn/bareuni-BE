package com.umc.BareuniBE.service;

import com.umc.BareuniBE.config.security.JwtTokenProvider;
import com.umc.BareuniBE.dto.*;

import com.umc.BareuniBE.dto.UserUpdateReq;
import com.umc.BareuniBE.entities.Hospital;
import com.umc.BareuniBE.entities.Review;
import com.umc.BareuniBE.entities.Scrap;
import com.umc.BareuniBE.entities.User;
import com.umc.BareuniBE.global.BaseException;
import com.umc.BareuniBE.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.umc.BareuniBE.global.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MypageService {

    @Value("${num.api.key}")
    private String num_api_key;

    @Value("${num.api.secret}")
    private String num_api_secret;

    @Value("${num.api.phone_num}")
    private String phone_num;

    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;
    private final ScrapRepository scrapRepository;
    private final ReviewRepository reviewRepository;
    private final BookingRepository bookingRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UploadService uploadService;

    private static final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d|[^A-Za-z\\d]).{8,20}$";

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    // 작성한 글 목록 조회 (최신순)
    public List<CommunityRes.CommunityListRes> getMyCommunityList(Pageable page, HttpServletRequest request) throws BaseException {
        User user = userRepository.findById(jwtTokenProvider.getCurrentUser(request))
                .orElseThrow(() -> new BaseException(USERS_EMPTY_USER_ID));
        List<Object[]> communities = communityRepository.MyCommunityList(user, page);

        return communities.stream()
                .map(communityData -> {
                    CommunityRes.CommunityListRes communityRes = new CommunityRes.CommunityListRes();
                    communityRes.setCommunityIdx(communityData[0]);
                    communityRes.setCreatedAt( communityData[1]);
                    communityRes.setUpdatedAt( communityData[2]);
                    communityRes.setContent( communityData[3]);
                    communityRes.setUser(userRepository.findById(((BigInteger)communityData[4]).longValue()).orElse(null));
                    communityRes.setLike(communityData[5]);

                    return communityRes;
                })
                .collect(Collectors.toList());
    }

    // 치과 저장 목록 조회
    public List<HospitalRes.HospitalListRes> getMyHospitalList(HttpServletRequest request) throws BaseException {

        List<Object[]> scraps = scrapRepository.findAllMyScrapHosList(jwtTokenProvider.getCurrentUser(request));

        List<HospitalRes.HospitalListRes> result = scraps.stream()
                .map(scrapData -> {
                    return new HospitalRes.HospitalListRes(scrapData);
                }).collect(Collectors.toList());


        return result;
    }

    // 작성한 리뷰 목록 조회 (최신순)

    public List<ReviewRes.ReviewListRes> getMyReviewList(Pageable page, HttpServletRequest request) throws BaseException {
        User user = userRepository.findById(jwtTokenProvider.getCurrentUser(request))
                .orElseThrow(() -> new BaseException(USERS_EMPTY_USER_ID));

        List<Review> reviews = reviewRepository.findReviewByUser(page, user);

        return reviews.stream()
                .map(reviewData -> {
                    ReviewRes.ReviewListRes reviewListRes = new ReviewRes.ReviewListRes(reviewData, user);
                    return reviewListRes;
                })
                .collect(Collectors.toList());
    }

    // 예약 내역 조회 (다가오는 예약 날짜 순?)
    public List<BookingRes.BookingListRes> getMyBookingList(Long userId, Pageable page) throws BaseException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(USERS_EMPTY_USER_ID));

        List<Object[]> bookings = bookingRepository.MyBookingList(user, page);

        return bookings.stream()
                .map(bookingData -> {
                    BookingRes.BookingListRes bookingListRes = new BookingRes.BookingListRes();
                    bookingListRes.setBookingIdx(bookingData[0]);
                    bookingListRes.setCreatedAt(bookingData[1]);
                    bookingListRes.setUpdatedAt( bookingData[2]);
                    bookingListRes.setUser(userRepository.findById((Long) bookingData[3]).orElse(null));
                    bookingListRes.setHospital(bookingData[4]);
                    bookingListRes.setMethod(bookingData[5]);
                    bookingListRes.setBookingDate(bookingData[6]);

                    return bookingListRes;
                })
                .collect(Collectors.toList());
    }

    // 회원 정보 수정 (닉네임, 이름, 성별, 연령대, 교정 여부)
    public String userUpdate(MultipartFile file, UserUpdateReq.MyUpdateReq myUpdateReq, HttpServletRequest request) throws BaseException, IOException {


        User user = userRepository.findById(jwtTokenProvider.getCurrentUser(request))
                .orElseThrow(() -> new BaseException(USERS_EMPTY_USER_ID));

        if (myUpdateReq.getNickname() != null) {
            user.setNickname(myUpdateReq.getNickname());
        }

        if (myUpdateReq.getName() != null) {
            user.setName(myUpdateReq.getName());
        }

        if (myUpdateReq.getGender() != null) {
            user.setGender(myUpdateReq.getGender());
        }

        if (myUpdateReq.getAge() != null) {
            user.setAge(myUpdateReq.getAge());
        }

        if (myUpdateReq.isOrtho() != user.isOrtho()) {
            user.setOrtho(myUpdateReq.isOrtho());
        }

        if (file != null){
            uploadService.deleteImage(user.getProfile());
            String url = uploadService.uploadImage(file);
            user.setProfile(url);
        }


        userRepository.save(user);

        return "회원 정보 수정 성공";
    }

    // 비밀번호 변경

    private boolean isValidPassword(String password) {
        return password.matches(PASSWORD_PATTERN);
    }

    public String changePassword(PasswordUpdateReq.MyPasswordUpdateReq passwordUpdateReq, HttpServletRequest request) throws BaseException {
        User user = userRepository.findById(jwtTokenProvider.getCurrentUser(request))
                .orElseThrow(() -> new BaseException(USERS_EMPTY_USER_ID));

        // 입력된 현재 비밀번호가 데이터베이스에 저장된 현재 비밀번호와 일치하는지 확인
        if (!encoder.matches(passwordUpdateReq.getCurrentPassword(), user.getPassword())) {
            throw new BaseException(PASSWORD_INCORRECT);
        }

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
            user.setPassword(encoder.encode(newPassword));
        }
        userRepository.save(user);
        return "비밀번호 변경 성공";
    }

    // 전화번호 인증
    public String PhoneNumberCheck(String to) throws CoolsmsException {

        Message coolsms = new Message(num_api_key, num_api_secret);

        Random rand  = new Random();
        String numStr = "";
        for(int i=0; i<4; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr+=ran;
        }

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", to);    // 수신전화번호 (ajax로 view 화면에서 받아온 값으로 넘김)
        params.put("from", phone_num);    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
        params.put("type", "sms");
        params.put("text", "인증번호는 [" + numStr + "] 입니다.");

        coolsms.send(params); // 메시지 전송

        return numStr;

    }

}


