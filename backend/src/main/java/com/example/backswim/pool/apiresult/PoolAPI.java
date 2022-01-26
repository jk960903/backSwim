package com.example.backswim.pool.apiresult;

import com.example.backswim.pool.dto.PoolDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PoolAPI extends CommonAPI{

    List<PoolDto> pool;

    public PoolAPI(List<PoolDto> list , int totalCount){
        this.setTotalCount(totalCount);
        this.pool = list;
    }
}
