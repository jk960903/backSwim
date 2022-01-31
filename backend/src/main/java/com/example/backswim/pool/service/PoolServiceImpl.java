package com.example.backswim.pool.service;

import com.example.backswim.pool.dto.PoolDto;
import com.example.backswim.pool.entity.PoolEntity;
import com.example.backswim.pool.mapper.PoolSearchMapper;
import com.example.backswim.pool.model.FindPoolMap;
import com.example.backswim.pool.params.SearchAddressParam;
import com.example.backswim.pool.params.SearchQueryParameter;
import com.example.backswim.pool.repository.PoolRepository;
import com.example.backswim.pool.params.GetPoolMapParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PoolServiceImpl implements PoolService{

    private final PoolRepository poolRepository;

    private final PoolSearchMapper poolSearchMapper;

    private final int[] levelPerMulti = {30, 45 , 75 , 150 , 375 , 750 , 1500 , 3000 , 6000 , 12000 , 24000 , 48000 , 96000 , 192000} ;

    /**
     * 위도별 경도의 범위가 다르지만 우리나라 위도 33~38.xx 를 참조하여 약 35도 적용
     * 35도의 경도 1당길이는 96.490 km
     * 0.1당 9.6km
     * 0.01당 960m
     * 0.001당 96m
     * 0.0001당 9.6m 0.0002 * 30 을 하면될듯
     * 1Lv 1cm = 20m , 2Lv 30m ,3Lv 50m , 4Lv 100, 5Lv 250m
     * 6Lv 500m 7LV 1km 8Lv 2km 9LV 4km 10LV 8km 11Lv 16
     * 12Lv 32km 13Lv 64km 14Lv 128km 의척도
     * 맵크기를 결정한다면 변경될수 있음
     * @param getPoolMapParam
     * @return
     */


    @Override
    public List<PoolDto> findPoolListForMapLocate(GetPoolMapParam getPoolMapParam) {

        Double status = 0.0001 * levelPerMulti[getPoolMapParam.getMapLevel() -1 ] / 2;

        FindPoolMap findPoolMap = FindPoolMap.builder().startLongitude(getPoolMapParam.getLongitude() -status)
                .endLongitude(getPoolMapParam.getLongitude() + status)
                .startLatitude(getPoolMapParam.getLatitude() - status)
                .endLatitude(getPoolMapParam.getLatitude() + status) . build() ;
        List<PoolEntity> poolEntities = poolRepository.findByLongitudeBetweenAndLatitudeBetween(findPoolMap.getStartLongitude() , findPoolMap.getEndLongitude(),
                findPoolMap.getStartLatitude(), findPoolMap.getEndLatitude());
        return PoolDto.of(poolEntities);
    }


    @Override
    public List<PoolDto> findPoolAddressList(SearchAddressParam param) {
        String query = "";
        if(!param.getFirstAddress().equals("")){
            query += param.getFirstAddress();
            if(!param.getSecondAddress().equals("")){
                query +=" " + param.getSecondAddress();

                if(!param.getThirdAddress().equals("")){
                    query +=" " + param.getThirdAddress();

                    if(!param.getFourthAddress().equals("")){
                        query +=" " + param.getFourthAddress();

                    }

                }
            }
        }
        List<PoolEntity> poolEntities = poolRepository.findByAddressNameContaining(query);


        return PoolDto.of(poolEntities);
    }

    @Override
    public List<PoolDto> findPoolPlaceListForQuery(SearchQueryParameter param) {
        param.makeQuery();

        List<PoolEntity> poolEntities = poolSearchMapper.selectQueryPool(param);

        return PoolDto.of(poolEntities);
    }
}
