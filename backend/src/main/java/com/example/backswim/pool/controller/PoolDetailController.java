package com.example.backswim.pool.controller;

import com.example.backswim.pool.apiresult.APIResult;
import com.example.backswim.pool.apiresult.PoolDetailAPI;
import com.example.backswim.pool.dto.PoolDetailDto;
import com.example.backswim.pool.entity.PoolDetailEntity;
import com.example.backswim.pool.error.PoolError;
import com.example.backswim.pool.excetption.poolexception.PoolNotFoundException;
import com.example.backswim.pool.model.dto.PoolDetailInfoDto;
import com.example.backswim.pool.params.GetPoolDetailParam;
import com.example.backswim.pool.service.PoolDetailService;
import com.example.backswim.pool.service.PoolService;
import lombok.RequiredArgsConstructor;
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

@RestController
@RestControllerAdvice
@RequiredArgsConstructor
@RequestMapping(value="api/pooldetail")
public class PoolDetailController {

    private final PoolDetailService poolDetailService;

    private final PoolService poolService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 파라미터 오류시 Exception Handler
     * @param request
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

    /**
     * 요청 Parameter를 통해 들어온 데이터
     * @param request
     * @return
     */
    @ExceptionHandler(PoolNotFoundException.class)
    public ResponseEntity PoolNotFoundError(HttpServletRequest request){
        PoolError error = new PoolError();
        error.setErrorCode("400");
        error.setErrorMessage("BAD PARAMETER");

        logger.info("요청 URL : {}",request.getRequestURL());
        logger.info("요청시간 : {}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
        logger.info("클라이언트 IP : {}",request.getRemoteAddr());

        logger.error("BAD REQUEST");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


    @GetMapping("/getpooldetail")
    public APIResult<PoolDetailAPI> getPoolDetail(GetPoolDetailParam getPoolDetailParam, HttpServletRequest request){

        HttpHeaders header = new HttpHeaders();
        PoolDetailDto poolDetail = null;
        PoolDetailAPI poolDetailAPI = new PoolDetailAPI();
        if(!getPoolDetailParam.checkStatus()){
            return new APIResult<>(400,null,"BAD_REQUEST PARAMETER");
        }
        try{
            poolDetail = poolDetailService.GetPoolDetail(getPoolDetailParam);
            poolDetailAPI.setPoolDetail(poolDetail);
            poolDetailAPI.setTotalCount(1);

        }catch(PoolNotFoundException e){
            return new APIResult<>(400,null,"BAD REQUEST DATA NOT FOUND");
        }catch(Exception e){
            logger.warn("요청 URL : {}",request.getRequestURL());
            logger.warn("요청시간 : {}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
            logger.warn("클라이언트 IP : {}",request.getRemoteAddr());
            logger.warn(e.getMessage());
            return new APIResult<>(500,null,"SERVER ERROR");
        }

        logger.info("요청 URL : {}",request.getRequestURL());
        logger.info("요청시간 : {}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
        logger.info("클라이언트 IP : {}",request.getRemoteAddr());

        return new APIResult<>(200,poolDetailAPI,"OK");
    }
}
