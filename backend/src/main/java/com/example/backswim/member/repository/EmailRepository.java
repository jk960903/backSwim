package com.example.backswim.member.repository;

import com.example.backswim.member.entity.EmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmailRepository extends JpaRepository<EmailEntity,Integer> {

    Optional<EmailEntity> findByEmailTitleAndUserEmail(String title , String userEmail);

    void deleteByUserEmail(String userEmail);


    Optional<List<EmailEntity>> findAllByUserEmail(String userEmail);
}
