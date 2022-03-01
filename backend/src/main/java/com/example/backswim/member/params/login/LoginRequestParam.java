package com.example.backswim.member.params.login;

import com.example.backswim.common.params.CheckInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestParam implements CheckInterface {

    private String userEmail;
    private String password;

    @Override
    public boolean checkStatus() {
        if(userEmail == null || password == null) return false;
        return true;
    }
}
