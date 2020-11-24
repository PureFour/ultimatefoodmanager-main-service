package com.purefour.mainservice.config;

import com.purefour.mainservice.service.UserService;
import com.purefour.mainservice.service.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    private static final String BEARER = "Bearer";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

//        final String authorizationHeader = request.getHeader("Authorization");
//
//        String username = null;
//        String jwtToken = null;
//
//        if (!StringUtil.isNullOrEmpty(authorizationHeader) && authorizationHeader.startsWith(BEARER)) {
//
//            jwtToken = authorizationHeader.substring(BEARER.length());
//            username = jwtUtil.extractLogin(jwtToken);
//        }
//
//        if (!StringUtil.isNullOrEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
//
//            UserDetails userDetails = this.userService.loadUserByUsername(username);
//
//            if (jwtUtil.validateToken(jwtToken, userDetails)) {
//
//                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
//                        new UsernamePasswordAuthenticationToken(userDetails,  null, userDetails.getAuthorities());
//                usernamePasswordAuthenticationToken.setDetails(
//                        new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//            }
//        }
        filterChain.doFilter(request, response);
    }
}
