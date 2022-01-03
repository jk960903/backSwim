package com.example.backswim.admin.repository;

import com.example.backswim.admin.entity.PoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoolRepository extends JpaRepository<PoolEntity,String> {
}
