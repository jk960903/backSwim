package com.example.backswim.pool.repository;

import com.example.backswim.pool.entity.PoolDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PoolDetailRepostiory extends JpaRepository<PoolDetailEntity,String> {

    Optional<PoolDetailEntity> findById(Long id);
}
