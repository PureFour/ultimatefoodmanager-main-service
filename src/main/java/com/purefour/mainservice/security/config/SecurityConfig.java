package com.purefour.mainservice.security.config;

import com.purefour.mainservice.config.JwtRequestFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] PUBLIC_PATHS = {
            "/**/swagger-ui/**",
            "/**/swagger-resources/**",
            "/v3/api-docs",
            "/actuator/**",
            "/**/users/signUp",
            "/**/users/signIn"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .addFilterAfter(new JwtRequestFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .mvcMatchers(SecurityConfig.PUBLIC_PATHS).permitAll()
                .anyRequest().authenticated();
    }
}
