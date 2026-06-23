package com.dailycoding.blog.service;

import com.dailycoding.blog.entity.User;
import com.dailycoding.blog.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

   private UserRepository userRepository;

   public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
   }

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       User user = userRepository.findByUsername(username)
               .orElseThrow(() -> new UsernameNotFoundException("找不到使用者: "+ username));
       
       List<GrantedAuthority> authorities = new ArrayList<>();
       user.getRoles().forEach(role -> {
           authorities.add(new SimpleGrantedAuthority(role.getName()));
           role.getPermissions().forEach(permission -> {
               authorities.add(new SimpleGrantedAuthority(permission.getName()));
           });
       });

       return new org.springframework.security.core.userdetails.User(
               user.getUsername(),
               user.getPassword(),
               authorities
       );

   }

}
