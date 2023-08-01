package com.umc.BareuniBE.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class TokenDTO {

    //1. 로그인 시 토큰을 응답
    private String types;
    private String token;
    private Date tokenExpriresTime;
}
