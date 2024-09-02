package com.accounting.accounting.service;

import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.accounting.accounting.model.User;
import com.accounting.accounting.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查找用戶
        User user = userRepository.findByUsername(username);
        if (user == null) {
            logger.error("User not found with username: " + username);
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // 將用戶的角色轉換為 GrantedAuthority
        Collection<GrantedAuthority> authorities = user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role)) // 確保角色名稱格式正確
            .collect(Collectors.toList());

        // 返回 UserDetails 對象
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), 
                user.getPassword(), 
                user.isEnabled(), // 是否啟用
                true, // 帳號是否未過期
                true, // 憑據是否未過期
                true, // 帳號是否未鎖定
                authorities
        );
    }
}
