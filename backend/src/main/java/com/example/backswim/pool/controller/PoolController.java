package com.example.backswim.pool.controller;

import com.example.backswim.common.api.APIResult;
import com.example.backswim.common.api.enums.StatusEnum;
import com.example.backswim.common.controller.CommonController;
import com.example.backswim.pool.apiresult.PoolAPI;
import com.example.backswim.pool.dto.PoolDto;
import com.example.backswim.pool.params.GetPoolMapParam;
import com.example.backswim.pool.service.PoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value="api/pool")
@RequiredArgsConstructor
public class PoolController extends CommonController {



    private final PoolService poolService;

    /**
     * 현재 위치를 토대로 가져오기
     * longitude = 경도
     * latitude = 위도
     * mapLevel로 이루어져있음
     * 카카오 api를 확인해보니
     * 1Lv 1cm = 20m , 2Lv 30m ,3Lv 50m , 4Lv 100, 5Lv 250m
     * 6Lv 500m 7LV 1km 8Lv 2km 9LV 4km 10LV 8km 11Lv 16
     * 12Lv 32km 13Lv 64km 14Lv 128km
     */

    @GetMapping("/getpoolmapforlocate")
    public APIResult<PoolAPI> GetPoolMapForLocate(HttpServletRequest request, GetPoolMapParam getPoolMapParam){
        PoolAPI poolAPI = null;

        if(!getPoolMapParam.checkStatus()){
            return new APIResult(400,null, StatusEnum.BAD_REQUEST);
        }

        try{
            List<PoolDto> lists = poolService.findPoolListForMapLocate(getPoolMapParam);
            poolAPI = new PoolAPI(lists,lists.size());
            PrintLog(request);
        }catch(Exception e) {
            PrintErrorLog(request);

            return new APIResult<>(500,null,StatusEnum.INTERNAL_SERVER_ERROR);
        }
        return new APIResult(200,poolAPI,StatusEnum.OK);
    }
}
