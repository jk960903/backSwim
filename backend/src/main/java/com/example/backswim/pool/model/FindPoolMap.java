package com.example.backswim.pool.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FindPoolMap {

    private Double startLongitude;

    private Double endLongitude;

    private Double startLatitude;

    private Double endLatitude;
}
