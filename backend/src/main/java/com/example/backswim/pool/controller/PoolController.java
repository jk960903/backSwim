package com.example.backswim.pool.controller;

import com.example.backswim.pool.entity.PoolEntity;
import com.example.backswim.pool.params.GetPoolMapParam;
import com.example.backswim.pool.service.PoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="api/pool")
@RequiredArgsConstructor
public class PoolController {

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

    private final PoolService poolService;


    @GetMapping("/getpoolmapforlocate")
    public ResponseEntity<List<PoolEntity>> GetPoolMapForLocate(GetPoolMapParam getPoolMapParam){
        HttpHeaders header = new HttpHeaders();
        if(!getPoolMapParam.checkStatus()){
            return new ResponseEntity<>(null,header,HttpStatus.BAD_REQUEST);
        }
        List<PoolEntity> lists = poolService.findPoolListForMapLocate(getPoolMapParam);
        return new ResponseEntity<>(lists,header, HttpStatus.OK);
    }
}
