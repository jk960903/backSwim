package com.example.backswim.pool.apiresult;

import com.example.backswim.common.api.CommonAPI;
import com.example.backswim.pool.dto.PoolDetailDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PoolDetailAPI extends CommonAPI {

    private PoolDetailDto poolDetail;

}
