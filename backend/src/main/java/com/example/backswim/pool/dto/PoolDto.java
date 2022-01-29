package com.example.backswim.pool.dto;

import com.example.backswim.pool.entity.PoolEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PoolDto {

    private long id;

    private String placeName;

    private String roadAddressName;

    private double longitude;

    private double latitude;

    private String addressName;

    private String phone;

    private Integer seq;

    public static PoolDto of(PoolEntity entity,int seq){
        PoolDto poolDto = PoolDto.builder()
                .id(entity.getId())
                .addressName(entity.getAddressName())
                .roadAddressName(entity.getRoadAddressName())
                .placeName(entity.getPlaceName())
                .longitude(entity.getLongitude())
                .latitude(entity.getLatitude())
                .phone(entity.getPhone())
                .seq(seq)
                .build();
        return poolDto;
    }

    public static List<PoolDto> of(List<PoolEntity> poolList){

        List<PoolDto> poolDtos = new ArrayList<>();

        int seq = 1;
        for(PoolEntity x : poolList){
            poolDtos.add(PoolDto.of(x,seq));
            seq++;
        }

        return poolDtos;
    }
}
