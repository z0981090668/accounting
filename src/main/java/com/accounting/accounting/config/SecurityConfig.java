package com.accounting.accounting.config;

import com.accounting.accounting.service.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final MyUserDetailsService myUserDetailsService;

    public SecurityConfig(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return myUserDetailsService; 
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .userDetailsService(userDetailsService()) 
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/register", "/login").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .loginPage("/login")
                .defaultSuccessUrl("/user/home", true) // 指定登入成功後跳轉的頁面
                .permitAll()
            )
            .logout((logout) -> logout.permitAll());
    
        return http.build();
    }
    
}
