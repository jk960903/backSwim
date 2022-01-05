package com.example.backswim.pool.service;

import com.example.backswim.pool.entity.PoolEntity;
import com.example.backswim.pool.params.GetPoolMapParam;

import java.util.List;

public interface PoolService {

    List<PoolEntity> findPoolListForMapLocate(GetPoolMapParam getPoolMapParam);
}
