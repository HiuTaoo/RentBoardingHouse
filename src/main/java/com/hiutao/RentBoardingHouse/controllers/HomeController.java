package com.hiutao.RentBoardingHouse.controllers;

import com.hiutao.RentBoardingHouse.models.User;
import com.hiutao.RentBoardingHouse.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.ExecutionException;

@Controller
public class HomeController {
    @Autowired
    UserService userService;
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng."); // Thông báo lỗi
        }
        return "login"; // Trả về trang đăng nhập
    }
    @GetMapping("/")
    public String getIndexPage(Model model, Authentication authentication) throws ExecutionException, InterruptedException {
        if (authentication != null) {
            String username = authentication.getName(); // Lấy tên người dùng từ authentication
            User user = userService.getAuth(username); // Lấy thông tin người dùng từ UserService
            model.addAttribute("user", user); // Thêm thông tin người dùng vào model
        }
        return "index"; // Trả về trang index
    }

    @GetMapping("/signup")
    public String test(){
        return "signup";
    }

}
