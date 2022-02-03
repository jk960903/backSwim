package com.example.backswim.pool.params;

import com.example.backswim.common.params.CheckInterface;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetPoolMapParam implements CheckInterface {

    private Double longitude;

    private Double latitude;

    private Integer mapLevel;

    public boolean checkStatus(){
        if( this.longitude == null || this.latitude == null || this.mapLevel == null) return false;
        return true;
    }
}
