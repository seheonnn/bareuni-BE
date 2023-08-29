package com.umc.BareuniBE.controller;

import com.umc.BareuniBE.dto.PasswordUpdateReq;
import com.umc.BareuniBE.dto.TokenDTO;
import com.umc.BareuniBE.dto.UserReq;
import com.umc.BareuniBE.global.BaseException;
import com.umc.BareuniBE.global.BaseResponse;
import com.umc.BareuniBE.global.BaseResponseStatus;
import com.umc.BareuniBE.service.EmailService;
import com.umc.BareuniBE.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import java.io.IOException;

import static com.umc.BareuniBE.global.BaseResponseStatus.SUCCESS;


@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final EmailService emailService;

    // 회원가입
    @ApiOperation(value = "회원가입", notes = "ex)\n\n" +
            "request:\n\n " +
            "{\n\n" +
            "    \"email\": \"abc123@naver.com\",\n\n" +
            "    \"age\":20,\n\n" +
            "    \"password\": \"abc123\",\n\n" +
            "    \"nickname\": \"바른이바른이\",\n\n" +
            "    \"gender\":\"MALE\"\n\n" +
            "}")
    @PostMapping(value = "/join", consumes = {"multipart/form-data"})
    public BaseResponse<Void> join(
            @ModelAttribute UserReq.UserJoinRequestWrapper requestWrapper

    ) throws BaseException, IOException {
        System.out.println("controller에서 Service로 join함수 실행직전");
        userService.join(requestWrapper.getFile(), requestWrapper.getUserJoinReq());
        return new BaseResponse<>(SUCCESS);
    }


    //이메일 인증
    @ApiOperation(value = "이메일 인증", notes = "ex)http://localhost:8080/users/email?email=exemail@exnet.com \n\n ")
    @PostMapping("/email")
    public BaseResponse<String> emailConfirm(@RequestParam String email) throws Exception {
        if(userService.emailValidation(email)) {
            try {
                String ePw = emailService.sendSimpleMessage(email);
//                이메일 임시비밀번호 발급 및 저장 코드
//                userService.updatePassword(userService.findUserByEmail(email), ePw);
                return new BaseResponse<>(ePw);
            } catch (BaseException exception) {
                return new BaseResponse<>(exception.getStatus());
            }
        } else return new BaseResponse<>(BaseResponseStatus.POST_USERS_INVALID_EMAIL);
    }

    //비밀번호 찾기
    @ApiOperation(value = "이메일 인증", notes = "ex)http://localhost:8080/users/email?email=exemail@exnet.com \n\n " +
            "{\n\n" +
            "    \"newPassword\": \"asdasd123\",\n\n" +
            "    \"confirmPassword\"asdasd123,\n\n" +
            "}")
    @PostMapping("/password")
    public BaseResponse<String> changePassword(
            @RequestParam String email,
            @RequestBody PasswordUpdateReq.NewPasswordUpdateReq passwordUpdateReq
    ) throws BaseException {
        return new BaseResponse<>(userService.changePassword(email, passwordUpdateReq));
    }

    // 로그인
    @ApiOperation(value = "로그인", notes = "ex)\n\n " +
            "{\n\n" +
            "    \"email\": \"abc123@naver.com\",\n\n" +
            "    \"password\": \"abc123\",\n\n" +
            "}")
    @PostMapping("/login")
    public BaseResponse<List<TokenDTO>> login(@RequestBody UserReq.UserLoginReq request) throws BaseException {
        System.out.println("controller: login함수 실행");
        return new BaseResponse<>(userService.login(request));
    }

    // 로그아웃
    @PostMapping("/logout")
    public BaseResponse<String> logout(HttpServletRequest request) throws BaseException {
        return new BaseResponse<>(userService.logout(request));
    }

    // 토큰 재발급
    @PostMapping("/reissue")
    public BaseResponse<List<TokenDTO>> reissue(HttpServletRequest request) throws BaseException {
        System.out.println("controller: reissue 함수 실행");
        return new BaseResponse<>(userService.reissue(request));
    }

    // 회원 탈퇴
    @PostMapping("/delete")
    public BaseResponse<String> deactivateUser(HttpServletRequest request) throws BaseException {
        return new BaseResponse<>(userService.deactivateUser(request));
    }

//    // 회원 탈퇴2 test
//    private final UserRepository userRepository;
//    @DeleteMapping("/delete2/{useridx}")
//    public BaseResponse<Void> deleteUserTest(@PathVariable Long useridx) throws BaseException {
//        userRepository.deleteById(useridx);
//        return new BaseResponse<>(SUCCESS);
//    }

    // 테스트
    @PostMapping("/test")
    public BaseResponse<String> UserTest(HttpServletRequest request) throws BaseException {
        System.out.println("test함수 실행");
        return new BaseResponse<>(userService.test(request));
    }
}
