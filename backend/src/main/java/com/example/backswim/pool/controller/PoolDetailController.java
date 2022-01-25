package com.example.backswim.pool.controller;

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
    public ResponseEntity<PoolDetailEntity> getPoolDetail(GetPoolDetailParam getPoolDetailParam,HttpServletRequest request){

        HttpHeaders header = new HttpHeaders();
        PoolDetailEntity poolDetailEntity = null;

        if(!getPoolDetailParam.checkStatus()){
            return new ResponseEntity<>(null,header,HttpStatus.BAD_REQUEST);
        }
        try{
            poolDetailEntity = poolDetailService.GetPoolDetail(getPoolDetailParam);


        }catch(PoolNotFoundException e){
            return new ResponseEntity<>(null,header,HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            return new ResponseEntity<>(null,header,HttpStatus.SERVICE_UNAVAILABLE);
        }

        logger.info("요청 URL : {}",request.getRequestURL());
        logger.info("요청시간 : {}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
        logger.info("클라이언트 IP : {}",request.getRemoteAddr());

        return new ResponseEntity<>(poolDetailEntity,header,HttpStatus.OK);
    }
}
