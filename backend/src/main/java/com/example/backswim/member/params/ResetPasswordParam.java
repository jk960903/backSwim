package com.example.backswim.member.params;

import com.example.backswim.common.params.CheckInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResetPasswordParam extends UserEmailParam {
    public ResetPasswordParam(String email){
        super(email);
    }
}
