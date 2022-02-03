package com.example.backswim.pool.controller;

import com.example.backswim.common.api.APIResult;
import com.example.backswim.common.api.enums.StatusEnum;
import com.example.backswim.common.controller.CommonController;
import com.example.backswim.pool.apiresult.PoolDetailAPI;
import com.example.backswim.pool.dto.PoolDetailDto;
import com.example.backswim.pool.dto.PoolDto;
import com.example.backswim.pool.excetption.poolexception.PoolNotFoundException;
import com.example.backswim.pool.params.GetPoolDetailParam;
import com.example.backswim.pool.service.PoolDetailService;
import com.example.backswim.pool.service.PoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RestControllerAdvice
@RequiredArgsConstructor
@RequestMapping(value="api/pooldetail")
public class PoolDetailController extends CommonController {

    private final PoolDetailService poolDetailService;

    private final PoolService poolService;


    /**
     * id를 통한 상세 조회
     * @param getPoolDetailParam
     * @param request
     * @return
     */
    @GetMapping("/getpooldetail")
    public APIResult<PoolDetailAPI> getPoolDetail(GetPoolDetailParam getPoolDetailParam, HttpServletRequest request){

        HttpHeaders header = new HttpHeaders();
        PoolDetailDto poolDetail = null;
        PoolDetailAPI poolDetailAPI = new PoolDetailAPI();
        PoolDto poolDto = null;

        if(!getPoolDetailParam.checkStatus()){
            return new APIResult<>(400,null, StatusEnum.BAD_REQUEST);
        }

        try{
            poolDto = poolService.findPoolById(getPoolDetailParam.getId());
            poolDetail = poolDetailService.GetPoolDetail(getPoolDetailParam);
            poolDetailAPI.setPoolDetail(poolDetail);
            poolDetailAPI.setTotalCount(1);

        }catch(PoolNotFoundException e){
            return new APIResult<>(400,null,StatusEnum.NOT_FOUND);
        }catch(Exception e){
            PrintErrorLog(request);
            return new APIResult<>(500,null,StatusEnum.INTERNAL_SERVER_ERROR);
        }

        PrintLog(request);

        return new APIResult<>(200,poolDetailAPI,StatusEnum.OK);
    }
}
