package com.example.backswim.member.controller;

import com.example.backswim.common.api.APIResult;
import com.example.backswim.common.api.enums.StatusEnum;
import com.example.backswim.common.controller.CommonController;
import com.example.backswim.member.exception.UserNotEmailAuthException;
import com.example.backswim.member.exception.WrongPasswordException;
import com.example.backswim.member.params.login.LoginRequestParam;
import com.example.backswim.member.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/login")
@RequiredArgsConstructor
public class LoginController extends CommonController {

    private final UserService userService;

    @PostMapping("/login")
    public APIResult<String> login(HttpServletRequest request,@RequestBody LoginRequestParam param){
        String result = "";

        if(!param.checkStatus()){
            return new APIResult<>(400,null, StatusEnum.BAD_REQUEST);
        }
        try{
            result = userService.userLogin(param);
        }catch(UserNotEmailAuthException e){
            PrintLog(request);
            return new APIResult<>(401,null,StatusEnum.AUTH_FIRST);
        }catch(WrongPasswordException e){
            PrintLog(request);
            return new APIResult<>(401,null,StatusEnum.WRONG_PASSWORD);
        }catch (Exception e){
            return new APIResult<>(500,null,StatusEnum.INTERNAL_SERVER_ERROR);
        }
        return new APIResult<>(200,result,StatusEnum.OK);
    }
}
