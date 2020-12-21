package com.purefour.mainservice.security.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.purefour.mainservice.model.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class JwtUtil {

    public static final String BEARER = "Bearer ";
    private static final String HEADER = "Authorization";
    private static final String SECRET = "***** ***";

    private static final long EXPIRATION_TIME = Long.MAX_VALUE;
    private static final String USER_UUID = "userUuid";
    private static final String AUTHORITIES = "authorities";

    public static String generateToken(User user) {
        return createToken(user.getEmail(), Map.of(USER_UUID, user.getUuid()));
    }

    private static String createToken(String subject, Map<String, Object> variables) {
        return BEARER + JWT.create()
                .withHeader(variables)
                .withClaim(AUTHORITIES, List.of("USER"))
                .withSubject(subject)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET));
    }

    public static Claims validateToken(HttpServletRequest request) {
        String jwtToken = request.getHeader(HEADER).replace(BEARER, "");
        return Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();
    }

    public static boolean checkJWTToken(HttpServletRequest request, HttpServletResponse res) {
        String authenticationHeader = request.getHeader(HEADER);
        return authenticationHeader != null && authenticationHeader.startsWith(BEARER);
    }

    public static String extractUserUuid(String authorizationToken) {
        String jwtToken = authorizationToken.replace(BEARER, "");
        final Map<String, Object> tokenHeaders = Jwts.parser().setSigningKey(SECRET.getBytes()).parse(jwtToken).getHeader();
        return String.valueOf(tokenHeaders.get(USER_UUID));
    }
}
