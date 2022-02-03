package com.example.backswim.pool.controller;

import com.example.backswim.common.controller.CommonController;
import com.example.backswim.common.api.APIResult;
import com.example.backswim.pool.apiresult.PoolAPI;
import com.example.backswim.common.api.enums.StatusEnum;
import com.example.backswim.pool.dto.PoolDto;
import com.example.backswim.pool.params.SearchQueryParameter;
import com.example.backswim.pool.params.SearchAddressParam;
import com.example.backswim.pool.service.PoolDetailService;
import com.example.backswim.pool.service.PoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
@RestControllerAdvice
public class PoolSearchController extends CommonController {

    private final PoolService poolService;

    private final PoolDetailService poolDetailService;

    /**
     * 검색어를 통한 수영장 목록 API
     * @param request
     * @param parameter
     * @return
     * @throws Exception
     */

    @GetMapping("/searchquery")
    public APIResult<?> SearchQuery(HttpServletRequest request , SearchQueryParameter parameter) throws Exception{
        PoolAPI poolAPI = new PoolAPI();
        if(!parameter.checkStatus()){
            return new APIResult<>(400,null, StatusEnum.BAD_REQUEST);
        }
        try{
            List<PoolDto> poolDtoList = poolService.findPoolPlaceListForQuery(parameter);
            poolAPI.setPool(poolDtoList);
            poolAPI.setTotalCount(poolDtoList.size());

            PrintLog(request);
        }catch(Exception e){
            PrintErrorLog(request);
            return new APIResult<>(500,null,StatusEnum.INTERNAL_SERVER_ERROR);
        }

        return new APIResult<>(200,poolAPI,StatusEnum.OK);
    }

    /**
     * 지역검색 API
     * @param request
     * @param parameter
     * firstAddress : 광역시 , 도 secondAddress : 구 , 군 ,시 thirdAddress (secondAddress가 시일경우 )구 , 읍 , fourthAddress : 동 면 리
     * @return
     */
    @GetMapping("/searchaddress")
    public APIResult<?> SearchAddress(HttpServletRequest request, SearchAddressParam parameter){
        PoolAPI poolAPI = new PoolAPI();

        if(!parameter.checkStatus()){
            return new APIResult<>(400,null,StatusEnum.BAD_REQUEST);
        }
        try{

            List<PoolDto> poolDtoList = poolService.findPoolAddressList(parameter);
            poolAPI.setPool(poolDtoList);
            poolAPI.setTotalCount(poolDtoList.size());
            PrintLog(request);

        }catch(Exception e){
            PrintErrorLog(request);
            return new APIResult<>(500,null,StatusEnum.INTERNAL_SERVER_ERROR);
        }

        return new APIResult<>(200,poolAPI,StatusEnum.OK);
    }
}
