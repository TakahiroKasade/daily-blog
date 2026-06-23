package com.dailycoding.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception{

        http
            .authorizeHttpRequests((requests) -> requests
                    .requestMatchers("/","/register","/login","/css/**","/js/**","/images/**", "/posts/{id}/**").permitAll()
                    .requestMatchers("/admin/api/stats/user").authenticated() // 允許所有登入者查自己的數據
                    .requestMatchers("/admin/**").hasRole("ADMIN") // 其他 admin 路徑僅限管理員
                    .requestMatchers("/posts/new", "/posts", "/posts/edit/**", "/posts/delete/**").hasAuthority("POST_WRITE")
                    .requestMatchers("/projects/new", "/projects", "/projects/edit/**", "/projects/delete/**").hasRole("ADMIN")
                    .requestMatchers("/experiences/new", "/experiences/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                    .loginPage("/login")
                    .defaultSuccessUrl("/", true)
                    .permitAll()
            )
            .logout((logout) -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .permitAll()
            );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
