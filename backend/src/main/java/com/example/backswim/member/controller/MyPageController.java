package com.example.backswim.member.controller;

import com.example.backswim.common.api.APIResult;
import com.example.backswim.common.api.enums.StatusEnum;
import com.example.backswim.common.controller.CommonController;
import com.example.backswim.component.JwtComponent;
import com.example.backswim.member.dto.UserDto;
import com.example.backswim.member.params.ChangePasswordParam;
import com.example.backswim.member.params.mypage.CheckPasswordParam;
import com.example.backswim.member.params.mypage.UpdateUserParam;
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
        if(param.checkStatus()){
            return new APIResult<>(400,null, StatusEnum.BAD_REQUEST);
        }

        try{
            String token = jwtComponent.resolveToken(request);
            if(jwtComponent.validateToken(token)){
                //TODO Token Claim으로부터 userSeq 가져와서 해당 아이디로 유저 검색 및 패스워드 비교 service 매핑
                result = true;
            }
        }catch(Exception e){

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
    public String updateUser(HttpServletRequest request, @RequestParam MultipartFile file){
        String token = jwtComponent.resolveToken(request);
        boolean result = false;
        if(token == null){
            //return new APIResult<>(401,null,StatusEnum.LOGIN_FIRST);
            return "tokenerror";
        }
        try{
            int id = jwtComponent.getUserId(token);
            //String id = jwtComponent.getUserId(token);
            result = userService.uploadProfileImage(file,id);
        }catch(Exception e){
            e.printStackTrace();
            return e.getMessage();
        }

        return "SUCCESS";
        //return new APIResult<>(200,result,StatusEnum.OK);
    }

    @PostMapping("/updateuserpassword")
    public APIResult<Boolean> updateUserPassword(HttpServletRequest request, @RequestBody ChangePasswordParam param){
        return null;
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
