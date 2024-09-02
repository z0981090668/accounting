package com.accounting.accounting.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.accounting.accounting.service.MyUserDetailsService;

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
            .csrf().disable() // 修正語法錯誤
            .userDetailsService(userDetailsService())
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/register", "/login", "/css/**", "/js/**", "/images/**", "/error").permitAll() // 公共路徑，允許所有用戶訪問
                .requestMatchers("/viewinfo","/search").hasRole("USER") // 修正角色名稱
                .requestMatchers("/admin/**").hasRole("ADMIN") // 只有擁有 ADMIN 角色的用戶可以訪問
                .anyRequest().authenticated() // 其他所有請求都需要認證
            )
            .formLogin((form) -> form
                .loginPage("/login")
                .defaultSuccessUrl("/user/home", true)
                .permitAll()
            )
            .logout((logout) -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }
}
