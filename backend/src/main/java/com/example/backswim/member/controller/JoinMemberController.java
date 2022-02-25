package com.example.backswim.member.controller;


import com.example.backswim.common.api.APIResult;
import com.example.backswim.common.api.enums.StatusEnum;
import com.example.backswim.common.controller.CommonController;
import com.example.backswim.member.params.CheckDuplicateID;
import com.example.backswim.member.params.JoinMemberParam;
import com.example.backswim.member.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value="api/joinmember")
@RequiredArgsConstructor
public class JoinMemberController extends CommonController {

    private final UserService userService;

    @PostMapping("/addmember")
    public APIResult<Boolean> joinMember(HttpServletRequest request, JoinMemberParam param){
        if(!param.checkStatus()){
            PrintLog(request);
            return new APIResult<>(400,null, StatusEnum.BAD_REQUEST);
        }
        try{
            if(!userService.joinMember(param)){
                return new APIResult<>(200,false,StatusEnum.OK);
            }
            PrintLog(request);
        }catch(Exception e){
            PrintErrorLog(request);
            e.printStackTrace();
            return new APIResult<>(500, null, StatusEnum.INTERNAL_SERVER_ERROR);
        }
        return new APIResult<>(200,true,StatusEnum.OK);
    }

    @PostMapping("/duplicateid")
    public APIResult<Boolean> checkduplicate(HttpServletRequest request,CheckDuplicateID param){
        if(!param.checkStatus()){
            return new APIResult<>(400,null, StatusEnum.BAD_REQUEST);
        }
        try{
            if(!userService.duplicateEmail(param)){ // 중복된 이메일 입니다.
                return new APIResult<>(200,false,StatusEnum.OK);
            }
        }catch(Exception e){
            PrintErrorLog(request);
            return new APIResult<>(500,null,StatusEnum.INTERNAL_SERVER_ERROR);
        }
        return new APIResult<>(200,true,StatusEnum.OK);
    }

    @GetMapping("/email-auth")
    public APIResult<Boolean> emailAuth(HttpServletRequest request,String uuid){
        if(uuid == null){
            return new APIResult<>(400,null,StatusEnum.BAD_REQUEST);
        }
        try{
            if(!userService.emailAuth(uuid)){
                return new APIResult<>(200,false,StatusEnum.OK);
            }
            PrintLog(request);
        }catch(Exception e){
            PrintErrorLog(request);
            return new APIResult<>(500,null,StatusEnum.INTERNAL_SERVER_ERROR);
        }
        return new APIResult<>(200,true,StatusEnum.OK);
    }







}
