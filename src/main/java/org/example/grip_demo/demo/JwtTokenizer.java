package org.example.grip_demo.demo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenizer {
    private final byte[] accessSecret;
    private final byte[] refreshSecret;
    public static Long ACCESS_TOKEN_EXPIRE_COUNT = 30 * 60 * 1000L; //30분
    public static Long REFRESH_TOKEN_EXPIRE_COUNT=7*24*60*60*1000L; //7일

    public JwtTokenizer(@Value("${jwt.secretKey}") String accessSecret, @Value("${jwt.refreshKey}") String refreshSecret){
        this.accessSecret = accessSecret.getBytes(StandardCharsets.UTF_8);
        this.refreshSecret = refreshSecret.getBytes(StandardCharsets.UTF_8);
    }


    //jwt 토큰 생성
    private String createToken(Long id, String email, String name, String username,
                               List<String> roles, Long expire, byte[] secretKey){

        Claims claims = Jwts.claims().setSubject(email);

        claims.put("userId",id);
        claims.put("username",username);
        claims.put("name",name);
        claims.put("roles", roles);


        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+expire))
                .signWith(Keys.hmacShaKeyFor(secretKey))
                .compact();

    }

    //ACCESS Token 생성
    public String createAccessToken(Long id, String email, String name, String username, List<String> roles){
        return createToken(id,email,name,username,roles,ACCESS_TOKEN_EXPIRE_COUNT,accessSecret);
    }
    //Refresh Token생성
    public String createRefreshToken(Long id, String email, String name, String username, List<String> roles){
        return createToken(id,email,name,username,roles,REFRESH_TOKEN_EXPIRE_COUNT,refreshSecret);
    }

    public Claims parseToken(String token, byte[] secretKey){
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public Claims parseAccessToken(String accessToken) {
        return parseToken(accessToken, accessSecret);
    }

    public Claims parseRefreshToken(String refreshToken) {
        return parseToken(refreshToken, refreshSecret);
    }

}
