package com.example.backswim.member.repository;

import com.example.backswim.member.entity.EmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<EmailEntity,Integer> {
}
