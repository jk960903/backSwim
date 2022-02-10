package com.example.backswim.pool.params;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetPoolMapParam implements checkInterface{

    private Double longitude;

    private Double latitude;

    private Integer mapLevel;

    public boolean checkStatus(){
        if( this.longitude == null || this.latitude == null || this.mapLevel == null || mapLevel < 1 || mapLevel > 14) return false;
        return true;
    }
}
