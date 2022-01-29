package com.example.backswim.pool.service;

import com.example.backswim.pool.dto.PoolDto;
import com.example.backswim.pool.entity.PoolEntity;
import com.example.backswim.pool.params.GetPoolMapParam;

import java.util.List;

public interface PoolService {

    List<PoolDto> findPoolListForMapLocate(GetPoolMapParam getPoolMapParam);
}
