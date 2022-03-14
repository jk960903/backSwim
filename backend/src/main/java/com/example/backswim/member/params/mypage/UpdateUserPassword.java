package com.example.backswim.member.params.mypage;

import com.example.backswim.common.params.CheckInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserPassword implements CheckInterface {

    private String beforePassword;
    private String changePassword;

    @Override
    public boolean checkStatus() {
        if(beforePassword.isBlank() && changePassword.isBlank()){
            return false;
        }
        return true;
    }

}
