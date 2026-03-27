package com.cineflow.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter){
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf->csrf.disable())
                .cors(cors-> cors.disable())
                .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth-> auth
                        .requestMatchers("/users/login", "/users/register").permitAll()
                        .requestMatchers("/swagger-ui/**",
                                         "/v3/api-docs/**",
                                         "/swagger-ui.html").permitAll()
                        .requestMatchers(HttpMethod.GET,"/movies/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/movies/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/movies/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/movies/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .exceptionHandling(ex ->
                                ex.authenticationEntryPoint((req, res, e)->{
                                            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                            res.getWriter().write("Unauthorized");
                                })
                                .accessDeniedHandler((req, res, e)->{
                                    res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                                    res.getWriter().write("Forbidden");
                                })
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
