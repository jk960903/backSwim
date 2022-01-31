package com.example.backswim.pool.mapper;

import com.example.backswim.pool.entity.PoolEntity;
import com.example.backswim.pool.params.SearchQueryParameter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PoolSearchMapper {

    List<PoolEntity> selectQueryPool(SearchQueryParameter parameter);
}
