package com.example.backswim.member.params;

import com.example.backswim.common.params.CheckInterface;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangePasswordParam implements CheckInterface {
    private String uuid;
    private String password;


    @Override
    public boolean checkStatus() {
        if(uuid == null || password == null) {
            return false;
        }

        return true;
    }
}
