package com.umc.BareuniBE.controller;

import com.umc.BareuniBE.dto.*;
import com.umc.BareuniBE.global.BaseException;
import com.umc.BareuniBE.global.BaseResponse;
import com.umc.BareuniBE.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

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

    // 로그인
    @PostMapping("/login")
    public BaseResponse<List<TokenDTO>> login(@RequestBody UserReq.UserLoginReq request) throws BaseException {
        System.out.println("controller: login함수 실행");
        return new BaseResponse<>(userService.login(request));
    }

    // 로그아웃
    @PostMapping("/logout")
    public BaseResponse<String> logout(HttpServletRequest httpServletRequest) throws BaseException {
        return new BaseResponse<>(userService.logout(httpServletRequest));
    }

    // 회원 탈퇴
    @PostMapping("/delete")
    public BaseResponse<String> deactivateUser(HttpServletRequest httpServletRequest) throws BaseException {
        return new BaseResponse<>(userService.deactivateUser(httpServletRequest));
    }

}
