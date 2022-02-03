package com.example.backswim.pool.repository;

import com.example.backswim.pool.entity.PoolEntity;
import com.example.backswim.pool.model.FindPoolMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PoolRepository extends JpaRepository<PoolEntity,Long> {

    List<PoolEntity> findByLongitudeBetweenAndLatitudeBetween(Double startLongitude, Double endLongitude , Double startLatitude,Double endLatitude);

    List<PoolEntity> findByAddressNameContaining(String addressName);

}
