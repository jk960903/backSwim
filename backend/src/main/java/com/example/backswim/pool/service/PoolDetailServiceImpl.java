package com.example.backswim.pool.service;

import com.example.backswim.pool.dto.PoolDetailDto;
import com.example.backswim.pool.entity.PoolDetailEntity;
import com.example.backswim.pool.excetption.poolexception.PoolNotFoundException;
import com.example.backswim.pool.params.GetPoolDetailParam;
import com.example.backswim.pool.params.repository.PoolDetailRepostiory;
import com.example.backswim.pool.params.repository.PoolRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PoolDetailServiceImpl implements PoolDetailService{

    private final PoolRepository poolRepository;

    private final PoolDetailRepostiory poolDetailRepostiory;

    private Logger logger = LoggerFactory.logger(this.getClass());
    @Override
    public PoolDetailDto GetPoolDetail(GetPoolDetailParam getPoolDetailParam) {

        Optional<PoolDetailEntity> optionalPoolDetailEntity = poolDetailRepostiory.findById(getPoolDetailParam.getId());

        if(optionalPoolDetailEntity.isEmpty()){
            throw new PoolNotFoundException("NOT FOUND ID");
        }



        return PoolDetailDto.of(optionalPoolDetailEntity.get());

    }
}
