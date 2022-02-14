package com.example.backswim.pool.service;

import com.example.backswim.pool.dto.PoolDto;
import com.example.backswim.pool.entity.PoolEntity;
import com.example.backswim.pool.params.GetPoolDetailParam;
import com.example.backswim.pool.params.GetPoolMapParam;
import com.example.backswim.pool.params.SearchAddressParam;
import com.example.backswim.pool.params.SearchQueryParameter;

import java.util.List;

public interface PoolService {

    PoolDto findPoolById(long id);

    List<PoolDto> findPoolListForMapLocate(GetPoolMapParam getPoolMapParam);

    List<PoolDto> findPoolAddressList(SearchAddressParam param);

    List<PoolDto> findPoolPlaceListForQuery(SearchQueryParameter param);
}
