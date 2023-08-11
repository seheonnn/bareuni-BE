package com.umc.BareuniBE.config.security;

import com.umc.BareuniBE.dto.TokenDTO;
import com.umc.BareuniBE.entities.User;
import com.umc.BareuniBE.global.BaseException;
import com.umc.BareuniBE.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

import static com.umc.BareuniBE.global.BaseResponseStatus.INVALID_JWT;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    private final UserDetailsService userDetailsService;

    private final UserRepository userRepository;

    private final RedisTemplate redisTemplate;


    // 객체 초기화, secretKey를 Base64로 인코딩
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT Access 토큰 생성
    public TokenDTO createAccessToken(String email) {
        // 토큰 유효시간 30분
        long tokenValidTime = 30 * 60 * 1000L;
        
        Optional<User> user = userRepository.findByEmail(email);

        Claims claims = Jwts.claims().setSubject(email); // JWT payload에 저장되는 정보단위
        claims.put("userIdx", user.get().getUserIdx());

        /*if(user.isPresent()) {
            System.out.println("user.get().getRole(): "+user.get().getRole());
            claims.put("roles", user.get().getRole()); // role넣어주기
        }*/

        claims.put("types", "atk"); // accessToken
        System.out.println("claims: "+claims);
        Date now = new Date();
        Date expiresTime = new Date(now.getTime() + tokenValidTime);
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        return new TokenDTO(String.valueOf(TokenType.atk), token, expiresTime);
    }

    // JWT Refresh 토큰 생성
    public TokenDTO createRefreshToken(String email) {
        // Refresh 토큰 유효시간 2주
        long tokenValidTime = 60 * 60 * 24 * 14 * 1000L;

        Claims claims = Jwts.claims().setSubject(email);

        Date now = new Date();
        Date expiresTime = new Date(now.getTime() + tokenValidTime); // 토큰 만료 시간
        String token = Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(expiresTime) // Expire Time 설정
                .signWith(SignatureAlgorithm.HS256, secretKey) // 사용할 암호화 알고리즘과 signature 에 들어갈 secretkey 값 설정
                .claim("types", "rtk")
                .compact();
        return new TokenDTO(String.valueOf(TokenType.rtk),token, expiresTime);
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원정보 추출 -email (payload의 subject)
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Request 의 Header 에서 token 값을 가져옵니다. "atk" : "token--"
    public String resolveAccessToken(HttpServletRequest request) {
        return request.getHeader("atk");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken); // 토큰의  payload(claim)
            // Access 토큰의 경우 redis 까지 검사
            if (claims.getBody().get("types").equals("atk")) {
                Object isLogOut = redisTemplate.opsForValue().get(jwtToken); // token 을 key 로 value 가져옴 (null 이면 유효 토큰, logout 이면 유효하지 않은 토큰)
                // 로그인 시 redis 에 email : refreshtoken 형태로 저장
                // 로그아웃 시 redis 에 accesstoken : logout 형태로 저장
                if (isLogOut != null) {
                    return false;
                }
                return !claims.getBody().getExpiration().before(new Date()); // 만료안됐으면 true, 만료됐으면 false
            } else {
                // refresh token : 14일 기간 안지났으면 유효
                System.out.println("refresh token 아직 유효");
                return !claims.getBody().getExpiration().before(new Date()); // 만료안됐으면 true, 만료됐으면 false
            }
        }catch (Exception e) {
            return false;
        }
    }

    // 토큰 만료 시간
    public Date getExpireTime(String jwtToken) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
        return claims.getBody().getExpiration();
    }

    // 토큰에서 회원정보 추출 - userIdx 추출
    public Long getCurrentUser(HttpServletRequest request) throws BaseException { // userIdx 가져오기
        String jwtToken = resolveAccessToken(request); // 요청의 header에서 토큰 추출
        if(!validateToken(jwtToken)) {
            throw new BaseException(INVALID_JWT);
        }
        Long userIdx = Long.valueOf(String.valueOf(Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwtToken)
                .getBody()
                .get("userIdx")));
        return userIdx;
    }
}
