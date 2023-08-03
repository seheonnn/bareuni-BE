package com.umc.BareuniBE.config.security;

import com.umc.BareuniBE.entities.User;
import com.umc.BareuniBE.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    private final UserDetailsService userDetailsService;

    private final UserRepository userRepository;

    //private String secretKey = "bareuni"; // 일단 넣어보기
    // 토큰 유효시간 30분
    long tokenValidTime = 30 * 60 * 1000L;
//    public JwtTokenProvider(JwtConfig jwtConfig, UserRepository userRepository) {
//        this.jwtConfig = jwtConfig;
//        this.userRepository = userRepository;
//    }
    
    // 객체 초기화, secretKey를 Base64로 인코딩
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT Access 토큰 생성
    //public TokenDTO createAccessToken(String email, String roles) {
    public String createAccessToken(String email, String roles) {
        Optional<User> user = userRepository.findByEmail(email);

        //Claims claims = Jwts.claims().setSubject(email); // JWT payload에 저장되는 정보단위
        Claims claims = Jwts.claims().setSubject(String.valueOf(user));
        System.out.println("claims: "+claims);
        // claims안에 String형태만 들어감,,,
        // email 받은 걸로 User찾아ㅓㅅ(reposotryfindByid)
        // user정보를 claims에 넣어주기,..<- Access토큰(User모든정보들어가야함..)
        // Refresh토큰은
//        claims.put("roles", roles);
        claims.put("types", "atk"); // accessToken
        Date now = new Date();
        Date expiresTime = new Date(now.getTime() + tokenValidTime);
        String token = Jwts.builder()
                .setClaims(claims)
                //.claim("user", User.of(user.getIdx(), user.getName(), user.getEmail()))
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        return token;
       // return new TokenDTO(String.valueOf(TokenType.atk), token, expiresTime);
    }

    // JWT Refresh 토큰 생성(email정보)
    //public TokenDTO createRefreshToken(String email) {
    public String createRefreshToken(String email) {
        // Refresh 토큰 유효시간 2주
        long tokenValidTime = 60 * 60 * 14 * 1000L;

        Claims claims = Jwts.claims().setSubject(String.valueOf(email));

        Date now = new Date();
        Date expiresTime = new Date(now.getTime() + tokenValidTime); // 토큰 만료 시간
        String token = Jwts.builder()
                .setClaims(claims) // 정보 저장
                //.claim()
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(expiresTime) // Expire Time 설정
                //.signWith(secretKey, SignatureAlgorithm.HS256)
                .signWith(SignatureAlgorithm.HS256, secretKey) // 사용할 암호화 알고리즘과 signature 에 들어갈 secretkey 값 설정
                .claim("types", "rtk")
                .compact();
        return token;
        //return new TokenDTO(String.valueOf(TokenType.rtk),token, expiresTime);
    }
/*여기까지 기본 jwt accessToken, refreshToken 생성*/
/*이 아래부터는 jwt토큰에서 인증정보를 가져오고, 토큰의 유효성을 검증하는거 */

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Request 의 Header 에서 token 값을 가져옵니다. "atk" : "token--"
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("atk");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception exception) {
//            log.info("검증 secret key: " + secretKey);
            return false;
        }
    }
}
