package com.hiutao.RentBoardingHouse.services;

import com.hiutao.RentBoardingHouse.models.CustomUserDetails;
import com.hiutao.RentBoardingHouse.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService; // Dịch vụ để lấy thông tin người dùng từ Firestore

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;
        try {
            user = userService.getAuth(username);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Trả về đối tượng CustomUserDetails
        return new CustomUserDetails(user);
    }

}