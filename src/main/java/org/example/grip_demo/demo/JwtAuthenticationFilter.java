package org.example.grip_demo.demo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.grip_demo.user.domain.RefreshToken;
import org.example.grip_demo.user.infrastructure.RefreshTokenRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenizer jwtTokenizer;
    private final RefreshTokenRepository refreshTokenRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //accessToken 있을 경우
        //accessToken 검증
        String accessToken = getAccessToken(request);

        //accessToken 없을 경우
        //refreshToken 조회 후 검증 완료 되면 다시 accessToken 재발급
        if (!StringUtils.hasText(accessToken)) {
            String refreshToken = getRefreshToken(request);
            if (StringUtils.hasText(refreshToken)) {
                RefreshToken refreshTokenEntity = refreshTokenRepository.findByValue(refreshToken).orElse(null);
                //쿠키의 refresh token과 db의 토큰 일치 여부 확인
                if (refreshTokenEntity != null &&  Objects.equals(refreshTokenEntity.getValue(), refreshToken)) {
                    accessToken = jwtTokenizer.createAccessTokenFromRefreshToken(refreshToken);

                    Cookie accessTokenCookie = new Cookie("accessToken",accessToken);
                    accessTokenCookie.setHttpOnly(true);
                    accessTokenCookie.setPath("/");
                    //30분 쿠키의 유지시간 단위 조정
                    accessTokenCookie.setMaxAge(Math.toIntExact(JwtTokenizer.ACCESS_TOKEN_EXPIRE_COUNT/1000));

                    response.addCookie(accessTokenCookie);

                }
            }

        }

        if(StringUtils.hasText(accessToken)){
            try{
                getAuthentication(accessToken);
            }catch (ExpiredJwtException e){
                request.setAttribute("exception", JwtExceptionCode.EXPIRED_TOKEN.getCode());
                throw new BadCredentialsException("Expired token exception", e);
            }catch (UnsupportedJwtException e){
                request.setAttribute("exception", JwtExceptionCode.UNSUPPORTED_TOKEN.getCode());
                throw new BadCredentialsException("Unsupported token exception", e);
            } catch (MalformedJwtException e) {
                request.setAttribute("exception", JwtExceptionCode.INVALID_TOKEN.getCode());
                throw new BadCredentialsException("Invalid token exception", e);
            } catch (IllegalArgumentException e) {
                request.setAttribute("exception", JwtExceptionCode.NOT_FOUND_TOKEN.getCode());
                throw new BadCredentialsException("Token not found exception", e);
            } catch (Exception e) {
                throw new BadCredentialsException("JWT filter internal exception", e);
            }
        }


        filterChain.doFilter(request, response);
    }

    private void getAuthentication(String token){
        Claims claims = jwtTokenizer.parseAccessToken(token);
        String email = claims.getSubject();
        Long userId = claims.get("userId", Long.class);
        String name = claims.get("name", String.class);
        String username = claims.get("username", String.class);

        //claim에서 Role 가져와서 authorities에 넣기
        List<GrantedAuthority> authorities = getGrantedAuthorities(claims);

        CustomUserDetails userDetails = new CustomUserDetails(userId, username,"",name,authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

        Authentication authentication = new JwtAuthenticationToken(authorities,userDetails,null);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private List<GrantedAuthority> getGrantedAuthorities(Claims claims){
        List<String> roles = (List<String>)claims.get("roles");
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(String role : roles){
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
    private String getAccessToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("accessToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    private String getRefreshToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }
}
