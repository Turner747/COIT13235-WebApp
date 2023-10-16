package com.optimed.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Bean
    public SecurityFilterChain formSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/js/**","/css/**","/img/**", "/users/register")
                        .permitAll()
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/patients", "/visitnotes", "/conditions")
                        .hasAuthority("PATIENT_READ")
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/patients/register", "/patients/save",
                                "/patients/update/**", "/visitnotes/add", "/visitnotes/save",
                                "/visitnotes/update/**", "/visitnotes/delete/**", "/conditions/save",
                                "/conditions/add", "/conditions/update/**", "/conditions/delete/**"
                                )
                        .hasAuthority("PATIENT_WRITE")
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/appointments")
                        .hasAuthority("APPOINTMENT_READ")
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/appointments/save", "/appointments/add/**",
                                "/appointments/update/**", "/appointments/select-doctor")
                        .hasAuthority("APPOINTMENT_WRITE")
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/staffs", "/shifts")
                        .hasAuthority("STAFF_READ")
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/staffs/register", "/staffs/role/**", "/staffs/update/id/**",
                                "/staffs/update/**", "/staffs/save", "/staffs/save-new",
                                "/staffs/update/username/**", "/staffs/delete/**", "/shifts/add",
                                "/shifts/update/**", "/shifts/delete/**", "/shifts/save")
                        .hasAuthority("STAFF_WRITE")
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/").authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .permitAll())
                .logout(logout -> logout
                        .permitAll()
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
