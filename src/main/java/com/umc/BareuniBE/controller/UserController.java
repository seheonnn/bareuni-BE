package com.umc.BareuniBE.controller;

import com.umc.BareuniBE.dto.PasswordUpdateReq;
import com.umc.BareuniBE.dto.UserReq;
import com.umc.BareuniBE.dto.UserRes;
import com.umc.BareuniBE.global.BaseException;
import com.umc.BareuniBE.global.BaseResponse;
import com.umc.BareuniBE.global.BaseResponseStatus;
import com.umc.BareuniBE.service.EmailService;
import com.umc.BareuniBE.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final EmailService emailService;

    // 회원가입
    @ApiOperation(value = "회원가입", notes = "ex)\n\n " +
            "{\n\n" +
            "    \"email\": \"abc123@naver.com\",\n\n" +
            "    \"age\":20,\n\n" +
            "    \"password\": \"abc123\",\n\n" +
            "    \"nickname\": \"바른이바른이\",\n\n" +
            "    \"gender\":\"MALE\"\n\n" +
            "}")
    @PostMapping("/join")
    public BaseResponse<UserRes.UserJoinRes> join(@RequestBody UserReq.UserJoinReq request) throws BaseException {
        System.out.println("controller에서 Service로 join함수 실행직전");
        return new BaseResponse<>(userService.join(request));
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

}
