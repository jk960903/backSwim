package com.example.backswim.pool.controller;

import com.example.backswim.pool.apiresult.APIResult;
import com.example.backswim.pool.apiresult.PoolAPI;
import com.example.backswim.pool.dto.PoolDto;
import com.example.backswim.pool.error.PoolError;
import com.example.backswim.pool.params.SearchQueryParameter;
import com.example.backswim.pool.params.SearchAddressParam;
import com.example.backswim.pool.service.PoolDetailService;
import com.example.backswim.pool.service.PoolService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
@RestControllerAdvice
public class PoolSearchController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    private final PoolService poolService;

    private final PoolDetailService poolDetailService;

    @ExceptionHandler(BindException.class)
    public APIResult sampleError(HttpServletRequest request){
        PoolError error = new PoolError();
        error.setErrorCode("400");
        error.setErrorMessage("WRONG TYPE");

        logger.info("요청 URL : {}",request.getRequestURL());
        logger.info("요청시간 : {}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
        logger.info("클라이언트 IP : {}",request.getRemoteAddr());

        return new APIResult(400,null,"WRONG TYPE");
    }


    @GetMapping("/searchquery")
    public APIResult<?> SearchQuery(HttpServletRequest request , SearchQueryParameter parameter) throws Exception{
        PoolAPI poolAPI = new PoolAPI();
        if(!parameter.checkStatus()){
            return new APIResult<>(400,null,"PARAMETER ERROR");
        }

        List<PoolDto> poolDtoList = poolService.findPoolPlaceListForQuery(parameter);
        poolAPI.setPool(poolDtoList);
        poolAPI.setTotalCount(poolDtoList.size());

        logger.info("요청 URL : {}",request.getRequestURL());
        logger.info("요청시간 : {}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
        logger.info("클라이언트 IP : {}",request.getRemoteAddr());


        return new APIResult<>(200,poolAPI,"OK");
    }

    @GetMapping("/searchaddress")
    public APIResult<?> SearchAddress(HttpServletRequest request, SearchAddressParam parameter){
        PoolAPI poolAPI = new PoolAPI();
        if(!parameter.checkStatus()){
            return new APIResult<>(400,null,"PARAMETER ERROR");
        }

        List<PoolDto> poolDtoList = poolService.findPoolAddressList(parameter);
        poolAPI.setPool(poolDtoList);
        poolAPI.setTotalCount(poolDtoList.size());

        logger.info("요청 URL : {}",request.getRequestURL());
        logger.info("요청시간 : {}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
        logger.info("클라이언트 IP : {}",request.getRemoteAddr());

        return new APIResult<>(200,poolAPI,"OK");
    }
}
