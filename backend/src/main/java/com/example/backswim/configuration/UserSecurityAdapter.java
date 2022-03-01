package com.example.backswim.configuration;

import com.example.backswim.member.entity.UserEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class UserSecurityAdapter extends User {

    private UserEntity userEntity;

    public UserSecurityAdapter(UserEntity user){
        super(user.getUserEmail(),user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.userEntity = user;
    }
}
