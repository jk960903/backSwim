package com.example.backswim.member.params;

import com.example.backswim.common.params.CheckInterface;
import lombok.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckDuplicateID implements CheckInterface {

    private String userEmail;

    @Override
    public boolean checkStatus() {

        if(userEmail == null || !checkEmail()) return false;
        return true;
    }

    public boolean checkEmail(){
        String regex = "[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(userEmail);
        if(matcher.matches()){
            return true;
        }
        return false;
    }
}
