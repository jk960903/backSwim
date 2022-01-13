package com.example.backswim.pool.controller;

import com.example.backswim.pool.entity.PoolEntity;
import com.example.backswim.pool.error.PoolError;
import com.example.backswim.pool.params.GetPoolMapParam;
import com.example.backswim.pool.service.PoolService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping(value="api/pool")
@RequiredArgsConstructor
@RestControllerAdvice
public class PoolController {

    Logger logger = LoggerFactory.getLogger(this.getClass());
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

    /**
     * 타입 에러에 대한 Handler
     * 컨트롤러 자체에 들어가기 전 상황에 대해 Bind Exception 발생시 작동됩니다.
     * 주로 Parameter에 타입 에러가 들어갔을 경우
     * ex ) int 형인데 char형 or String이 들어갔을 경우
     * @return
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity sampleError(HttpServletRequest request){
        PoolError error = new PoolError();
        error.setErrorCode("400");
        error.setErrorMessage("WRONG TYPE");

        logger.info("요청 URL : {}",request.getRequestURL());
        logger.info("요청시간 : {}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
        logger.info("클라이언트 IP : {}",request.getRemoteAddr());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


    @GetMapping("/getpoolmapforlocate")
    public ResponseEntity<List<PoolEntity>> GetPoolMapForLocate(HttpServletRequest request, GetPoolMapParam getPoolMapParam){
        HttpHeaders header = new HttpHeaders();
        if(!getPoolMapParam.checkStatus()){
            return new ResponseEntity<>(null,header,HttpStatus.BAD_REQUEST);
        }
        List<PoolEntity> lists = poolService.findPoolListForMapLocate(getPoolMapParam);

        logger.info("요청 URL : {}",request.getRequestURL());
        logger.info("요청시간 : {}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
        logger.info("클라이언트 IP : {}",request.getRemoteAddr());

        return new ResponseEntity<>(lists,header, HttpStatus.OK);
    }
}
