package com.example.backswim.pool.service;

import com.example.backswim.pool.entity.PoolDetailEntity;
import com.example.backswim.pool.params.GetPoolDetailParam;
import com.example.backswim.pool.repository.PoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


public interface PoolDetailService {

    PoolDetailEntity GetPoolDetail(GetPoolDetailParam getPoolDetailParam);


}
