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
        return new BaseResponse<>(userService.join(request));
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

    // 테스트
    @PostMapping("/test")
    public BaseResponse<String> UserTest(HttpServletRequest request) throws BaseException {
        System.out.println("test함수 실행");
        return new BaseResponse<>(userService.test(request));
    }
}
