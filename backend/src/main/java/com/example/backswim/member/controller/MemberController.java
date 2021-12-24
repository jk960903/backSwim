package com.example.backswim.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api/member")
public class MemberController {
    @GetMapping(value="/main")
    public String Main(){
        return "MAIN";
    }
}
