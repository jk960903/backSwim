package com.example.backswim.pool.dto;

import com.example.backswim.pool.entity.PoolDetailEntity;
import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PoolDetailDto {

    private Long id;

    private LocalTime monOpen;

    private LocalTime monClose;

    private LocalTime tueOpen;

    private LocalTime tueClose;

    private LocalTime wedOpen;

    private LocalTime wedClose;

    private LocalTime thurOpen;

    private LocalTime thurClose;

    private LocalTime friOpen;

    private LocalTime friClose;

    private LocalTime satOpen;

    private LocalTime satClose;

    private LocalTime sunOpen;

    private LocalTime sunClose;

    private Integer price;

    public static PoolDetailDto of(PoolDetailEntity entity){

        PoolDetailDto poolDetailDto = PoolDetailDto.builder()
                .id(entity.getId())
                .monOpen(entity.getMonOpen())
                .monClose(entity.getMonClose())
                .tueOpen(entity.getTueOpen())
                .tueClose(entity.getTueClose())
                .wedOpen(entity.getWedOpen())
                .wedClose(entity.getWedClose())
                .thurOpen(entity.getThurOpen())
                .thurClose(entity.getThurClose())
                .friOpen(entity.getFriOpen())
                .friClose(entity.getFriClose())
                .satClose(entity.getSatClose())
                .satOpen(entity.getSatOpen())
                .sunClose(entity.getSunClose())
                .sunOpen(entity.getSunOpen())
                .price(entity.getPrice())
                .build();


        return poolDetailDto;
    }

}
