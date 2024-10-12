package com.hiutao.RentBoardingHouse.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final String email;
    private final String phoneNum;
    private final String nickname;
    private final String avt;

    public CustomUserDetails(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.authorities = List.of(() -> user.getRole()); // Chuyển đổi Role của bạn thành GrantedAuthority
        this.email = user.getEmail();
        this.phoneNum = user.getPhoneNum();
        this.nickname = user.getNickname();
        this.avt  = user.getAvt();
    }

    // Các phương thức từ UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Tùy chỉnh theo logic của bạn
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Tùy chỉnh theo logic của bạn
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Tùy chỉnh theo logic của bạn
    }

    @Override
    public boolean isEnabled() {
        return true; // Tùy chỉnh theo logic của bạn
    }

    // Getter cho các thuộc tính bổ sung
    public String getEmail() {
        return email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getNickname() {
        return nickname;
    }
    public String getAvt() {
        return avt;
    }
}
