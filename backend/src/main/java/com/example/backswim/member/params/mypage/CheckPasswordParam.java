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
public class CheckPasswordParam implements CheckInterface {

    private String password;

    @Override
    public boolean checkStatus() {
        if(password == null || password.equals("")){
            return false;

        }

        return true;
    }
}
