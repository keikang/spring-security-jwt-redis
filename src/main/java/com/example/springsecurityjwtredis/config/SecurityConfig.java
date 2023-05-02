package com.example.springsecurityjwtredis.config;

import com.example.springsecurityjwtredis.jwt.JwtAuthenticationFilter;
import com.example.springsecurityjwtredis.service.MemberUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtEntryPoint jwtEntryPoint;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final MemberUserDetailService memberUserDetailService;

    private final String[] permitUrl = {
                                        "/api/v1/auths/"
                                        , "/api/v1/member/login"
                                        , "/api/v1/auth"
                                        , "/api/v1/member/add"
                                        , "/api/v1/member/reissue"
                                        //, "/api/v1/role/**"
                                        //, "/api/v1/member/**"
                                        };

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web -> web.ignoring().requestMatchers("/favicon.ico"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        System.out.println("SecurityConfig authenticationProvider");
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(memberUserDetailService);
        //authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().disable();
        http
                    .authorizeHttpRequests()
                    .requestMatchers(permitUrl)
                        .permitAll()
                    .anyRequest()
                        .authenticated()
                .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(jwtEntryPoint)
                .and()
                    .logout()
                    .disable()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        //.requestMatchers("/", "/api/v1/**").permitAll()
        return http.build();
    }

/*    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.cors()
                .and()
                .csrf().disable()
                .authorizeHttpRequests((authz) -> {
                    try {
                        authz.requestMatchers("/", "/login","/api/v1/member/user").permitAll()
                                .anyRequest().hasRole("USER")
                                .and()
                                .exceptionHandling().authenticationEntryPoint(jwtEntryPoint)
                                .and()
                                .logout().disable()
                                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                                .and()
                                .authenticationProvider(authenticationProvider())
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
        return http.build();
    }*/

}
