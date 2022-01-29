package com.example.backswim.pool.service;

import com.example.backswim.pool.dto.PoolDetailDto;
import com.example.backswim.pool.params.GetPoolDetailParam;


public interface PoolDetailService {

    PoolDetailDto GetPoolDetail(GetPoolDetailParam getPoolDetailParam);


}
