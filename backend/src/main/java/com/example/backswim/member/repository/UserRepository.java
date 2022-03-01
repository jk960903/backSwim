package com.example.backswim.member.repository;

import com.example.backswim.member.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {

    Optional<UserEntity> findByUserEmail(String userEmail);

    Optional<UserEntity> findByEmailAuthKey(String emailAuthKey);


    Optional<UserEntity> findByResetPasswordKey(String resetPasswordKey);

}
