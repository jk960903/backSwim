package com.example.backswim.member.params;

import com.example.backswim.common.params.CheckInterface;
import lombok.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
public class CheckDuplicateID extends UserEmailParam{

    public CheckDuplicateID(String s){
        super(s);
    }
}
