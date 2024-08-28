package com.accounting.accounting.controller;

import com.accounting.accounting.model.Role;
import com.accounting.accounting.model.User;
import com.accounting.accounting.repository.RoleRepository;
import com.accounting.accounting.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository; // 新增角色倉庫
    private final PasswordEncoder passwordEncoder;

    // 使用構造函數注入 UserRepository, RoleRepository 和 PasswordEncoder
    public UserController(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 顯示註冊表單
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User()); // 添加一個新的 User 對象到模型中
        return "register"; // 返回註冊頁面的視圖名稱
    }

    // 處理註冊表單提交
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // 將用戶密碼加密
        user.setEnabled(true); // 啟用用戶帳戶
        
        // 設置默認角色為 ROLE_USER
        Role userRole = roleRepository.findByName("ROLE_USER");
        if (userRole == null) {
            userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole); // 確保角色已保存到數據庫
        }
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);
        
        userRepository.save(user); // 保存用戶到資料庫
        
        return "redirect:/login"; // 註冊成功後重定向到登錄頁面
    }

    // 顯示登錄表單
    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // 返回登錄頁面的視圖名稱
    }

    // 顯示用戶登入後的首頁
    @GetMapping("/user/home")
    public String userHome(Model model) {
        // 獲取當前認證信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // 獲取當前登入用戶的用戶名

        // 將用戶名添加到模型中，以便在視圖中顯示
        model.addAttribute("username", username);

        return "UserHome"; // 返回用戶首頁的視圖名稱
    }

    @GetMapping("/viewdata")
    public String showdata(){
        return "uploadAndSearch";
    }
}