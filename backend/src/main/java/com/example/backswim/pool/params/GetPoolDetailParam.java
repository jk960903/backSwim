package com.example.backswim.pool.params;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetPoolDetailParam implements checkInterface{

    private Long id;

    @Override
    public boolean checkStatus() {
        if(this .id == null || this.id < 0){
            return false;
        }

        return true;
    }
}
