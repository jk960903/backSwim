package com.example.backswim.member.controller;

import com.example.backswim.common.api.APIResult;
import com.example.backswim.common.api.enums.StatusEnum;
import com.example.backswim.common.controller.CommonController;
import com.example.backswim.component.JwtComponent;
import com.example.backswim.member.dto.UserDto;
import com.example.backswim.member.exception.UserNotFoundException;
import com.example.backswim.member.exception.WrongPasswordException;
import com.example.backswim.member.params.ChangePasswordParam;
import com.example.backswim.member.params.mypage.CheckPasswordParam;
import com.example.backswim.member.params.mypage.UpdateUserParam;
import com.example.backswim.member.params.mypage.UpdateUserPassword;
import com.example.backswim.member.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MyPageController extends CommonController {

    private final UserService userService;

    private final JwtComponent jwtComponent;

    /**
     * mypage 들어가면서 사용하는 비밀번호 확인 (자신인지 확인하는 페이지 )
     * @param request
     * @param param
     * @return
     */
    @PostMapping("/checkpassword")
    public APIResult<Boolean> checkPassword(HttpServletRequest request , @RequestBody CheckPasswordParam param){
        boolean result = false;
        if(!param.checkStatus()){
            return new APIResult<>(400,null, StatusEnum.BAD_REQUEST);
        }

        try{
            String token = jwtComponent.resolveToken(request);
            if(jwtComponent.validateToken(token)){
                int id = jwtComponent.getUserId(token);

                result = userService.checkPassword(param,id);

                PrintLog(request);
            }
        }catch(WrongPasswordException e){
            PrintErrorLog(request);
            return new APIResult<>(200,result,StatusEnum.OK);
        }catch(UserNotFoundException e){
            PrintErrorLog(request);
            return new APIResult<>(401,null,StatusEnum.EXPIRED_TOKEN);
        }catch(Exception e){
            PrintErrorLog(request);
            return new APIResult<>(500,result,StatusEnum.INTERNAL_SERVER_ERROR);
        }
        return new APIResult<>(200,result,StatusEnum.OK);
    }

    /**
     * 의문점 ...1 유저 패스워드를 바꿧을 때 Spring Security에 문제가 생기진 않는가? <- 해결방안
     *
     * @param request
     * @param file
     * @return
     */
    @PostMapping("/updateuserimage")
    public APIResult<Boolean> updateUser(HttpServletRequest request, @RequestParam MultipartFile file){
        String token = jwtComponent.resolveToken(request);
        boolean result = false;
        if(token == null){
            return new APIResult<>(401,null,StatusEnum.LOGIN_FIRST);
        }
        if(file == null){
            return new APIResult<>(400,null,StatusEnum.BAD_REQUEST);
        }
        try{
            int id = jwtComponent.getUserId(token);

            result = userService.uploadProfileImage(file,id);
        }catch(Exception e){
            return new APIResult<>(500,null,StatusEnum.INTERNAL_SERVER_ERROR);
        }

        return new APIResult<>(200,result,StatusEnum.OK);
    }

    @PostMapping("/updateuserpassword")
    public APIResult<Boolean> updateUserPassword(HttpServletRequest request, @RequestBody UpdateUserPassword param){
        boolean result = false;
        if(!param.checkStatus()){
            return new APIResult<>(400,null, StatusEnum.BAD_REQUEST);
        }
        try{
            String token = jwtComponent.resolveToken(request);
            if(!jwtComponent.validateToken(token)){
                return new APIResult<>(401,null,StatusEnum.EXPIRED_TOKEN);
            }
            int id = jwtComponent.getUserId(token);

            result = userService.updatePassword(param,id);
        }catch(UserNotFoundException e){
            return new APIResult<>(401,null,StatusEnum.EXPIRED_TOKEN);
        }catch(WrongPasswordException e){
            return new APIResult<>(401,null, StatusEnum.WRONG_PASSWORD);
        }catch(Exception e){
            return new APIResult<>(500,null,StatusEnum.INTERNAL_SERVER_ERROR);
        }

        return new APIResult<>(200,result,StatusEnum.OK);
    }
    @GetMapping("/getmypage")
    public APIResult<UserDto> getmypage(HttpServletRequest request){
        String token = jwtComponent.resolveToken(request);
        UserDto userDto = null;
        if(token == null){
            return new APIResult<>(401,null,StatusEnum.LOGIN_FIRST);
        }
        try{
            int id = jwtComponent.getUserId(token);

            userDto = userService.getmyPage(id);
        }catch(Exception e){
            return new APIResult<>(500,null,StatusEnum.INTERNAL_SERVER_ERROR);
        }
        return new APIResult<>(200,userDto,StatusEnum.OK);
    }
}
