package com.example.backswim.member.controller;

import com.example.backswim.common.api.APIResult;
import com.example.backswim.common.api.enums.StatusEnum;
import com.example.backswim.common.controller.CommonController;
import com.example.backswim.component.JwtComponent;
import com.example.backswim.member.exception.UserNotEmailAuthException;
import com.example.backswim.member.exception.WrongPasswordException;
import com.example.backswim.member.params.login.LoginRequestParam;
import com.example.backswim.member.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api/login")
@RequiredArgsConstructor
public class LoginController extends CommonController {

    private final UserService userService;

    private final JwtComponent jwtComponent;

    @PostMapping("/login")
    public APIResult<String> login(HttpServletRequest request, @RequestBody LoginRequestParam param, HttpServletResponse response){
        String result = "";

        String token = jwtComponent.resolveToken(request);

        if(jwtComponent.validateToken(token)){

        }

        if(token != null){
            return new APIResult(200,token,StatusEnum.OK);
        }

        if(!param.checkStatus()){
            return new APIResult<>(400,null, StatusEnum.BAD_REQUEST);
        }
        try{

            result = userService.userLogin(param);
            if(result != null){
                Cookie cookie = new Cookie("JWTTOKEN",result);

                cookie.setMaxAge(3600 * 24 * 30); // 쿠키 설정 30일

                response.addCookie(cookie);
            }
            PrintLog(request);

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
