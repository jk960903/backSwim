package com.example.backswim.member.params.mypage;


import com.example.backswim.common.params.CheckInterface;

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
