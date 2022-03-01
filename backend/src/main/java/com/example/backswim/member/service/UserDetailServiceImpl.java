package com.example.backswim.member.service;

import com.example.backswim.configuration.UserSecurityAdapter;
import com.example.backswim.member.entity.UserEntity;
import com.example.backswim.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> optional = userRepository.findByUserEmail(username);

        if(optional.isEmpty()){
            throw new UsernameNotFoundException("이메일 을 찾을수 없습니다. ");
        }
        return new UserSecurityAdapter(optional.get());
    }
}
