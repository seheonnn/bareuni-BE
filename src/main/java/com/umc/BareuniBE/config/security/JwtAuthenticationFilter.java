package com.umc.BareuniBE.config.security;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter  extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("filter 시작");
        // Request Header에서 JWT 를 받아옴
        String token = jwtTokenProvider.resolveToken(request);

        System.out.println("Header의 token: "+token);

        //String username =

        if(token != null && jwtTokenProvider.validateToken(token)) {
            // JWT 토큰

            // token이 유효하면 인증 객체 생성
            Authentication authentication = jwtTokenProvider.getAuthentication(token);


            // SecurityContextHolder.getContext() : 시큐리티의 session 공간
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        }
        else{
            System.out.println("header가 null인 상태!");
            filterChain.doFilter(request, response);
        }
    }
}
