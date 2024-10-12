package com.hiutao.RentBoardingHouse.controllers;

import com.hiutao.RentBoardingHouse.models.CustomUserDetails;
import com.hiutao.RentBoardingHouse.models.User;
import com.hiutao.RentBoardingHouse.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
public class UserController {
    @Autowired
    public UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getauth")
    public User getAuth(@RequestParam String Username) throws InterruptedException, ExecutionException {
        return userService.getAuth(Username);
    }
    @PostMapping("/create")
    public String CreateUser(@RequestBody User user) throws ExecutionException, InterruptedException {
        return userService.createUser(user);
    }
    @GetMapping("/current-user")
    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) principal;
            // Tạo đối tượng User để trả về thông tin cần thiết
            User user = new User();
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());
            user.setPhoneNum(userDetails.getPhoneNum());
            user.setNickname(userDetails.getNickname());
            user.setAvt(userDetails.getAvt());
            return user;
        } else {
            return null; // Hoặc xử lý theo cách của bạn
        }
    }


}
