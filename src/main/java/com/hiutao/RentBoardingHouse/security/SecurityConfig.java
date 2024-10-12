package com.hiutao.RentBoardingHouse.security;

import com.hiutao.RentBoardingHouse.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class SecurityConfig {
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(csrf -> csrf
                            .ignoringRequestMatchers("/create") // Vô hiệu hóa CSRF cho endpoint /create
                    )//vừa thêm
                    // Cấu hình phân quyền
                    .authorizeHttpRequests(auth -> auth
                            // Cho phép các trang không yêu cầu xác thực
                            .requestMatchers("/getauth", "/create", "/login", "/signup",
                                    "/css/**", "/images/**", "/js/**","/libs/**", "/scss/**", "/icons.tabler-icons/**").permitAll()
                            .anyRequest().authenticated() // Tất cả các yêu cầu khác cần xác thực
                    )
                    // Cấu hình đăng nhập
                    .formLogin(form -> form
                            .loginPage("/login")               // URL của trang login
                            .loginProcessingUrl("/login")      // URL mà form login sẽ submit đến
                            .defaultSuccessUrl("/", true) // URL khi đăng nhập thành công, `true` để luôn chuyển đến đây
                            .permitAll()                       // Cho phép mọi người truy cập trang đăng nhập
                            .failureUrl("/login?error=true") // Chuyển đến trang đăng nhập với thông báo lỗi
                    )
                    .logout(logout -> logout
                            .logoutUrl("/logout")          // URL để đăng xuất
                            .logoutSuccessUrl("/login")    // URL chuyển tới sau khi đăng xuất
                            .permitAll()
                    )
            ;

            return http.build();
        }

    @Autowired
    private CustomUserDetailsService userDetailsService;

    // Cấu hình AuthenticationManager
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Sử dụng BCrypt để mã hóa mật khẩu
    }
}
