package com.example.backswim.common.controller;

import com.example.backswim.pool.apiresult.APIResult;
import com.example.backswim.pool.apiresult.enums.StatusEnum;
import com.example.backswim.pool.error.PoolError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
public class CommonController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 타입 에러에 대한 Handler
     * 컨트롤러 자체에 들어가기 전 상황에 대해 Bind Exception 발생시 작동됩니다.
     * 주로 Parameter에 타입 에러가 들어갔을 경우
     * ex ) int 형인데 char형 or String이 들어갔을 경우
     * @return
     */
    @ExceptionHandler(BindException.class)
    public APIResult sampleError(HttpServletRequest request){
        PoolError error = new PoolError();
        error.setErrorCode("400");
        error.setErrorMessage("WRONG TYPE");

        PrintErrorLog(request);

        return new APIResult(400,null, StatusEnum.PARAMETER_TYPE_ERROR);
    }

    public void PrintLog(HttpServletRequest request){
        logger.info("요청 URL : {}",request.getRequestURL());
        logger.info("요청시간 : {}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
        logger.info("클라이언트 IP : {}",request.getRemoteAddr());
        logger.info("요청 브라우저 : {}",request.getHeader("User-Agent"));
    }

    public void PrintErrorLog(HttpServletRequest request){
        logger.warn("요청 URL : {}",request.getRequestURL());
        logger.warn("요청시간 : {}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
        logger.warn("클라이언트 IP : {}",request.getRemoteAddr());
        logger.warn("요청 브라우저 : {}",request.getHeader("User-Agent"));
    }
}
