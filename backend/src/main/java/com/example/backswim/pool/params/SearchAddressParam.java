package com.example.backswim.pool.params;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchAddressParam implements checkInterface{

    private String firstAddress;

    private String secondAddress;

    private String thirdAddress;

    private String fourthAddress;

    @Override
    public boolean checkStatus() {
        if(firstAddress == null || secondAddress == null || thirdAddress == null || fourthAddress == null){
            return false;
        }
        return true;
    }


}
