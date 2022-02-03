package com.example.backswim.pool.params;

import com.example.backswim.common.params.CheckInterface;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetPoolDetailParam implements CheckInterface {

    private Long id;

    @Override
    public boolean checkStatus() {
        if(this .id == null || this.id < 0){
            return false;
        }

        return true;
    }
}
