package com.umc.BareuniBE.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Cors error 설정
@Configuration
@Component
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") //리액트에서 들어오는 요청을 허용하는 거
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS" , "PATCH") // Http Method 들 하용
                .allowedHeaders("*");
//                .exposedHeaders("Authorization", "atk"); // 응답헤더에 포함할 내용 //atk가 jwt토큰이름,,!
    }
}
