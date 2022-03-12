package com.example.backswim.member.service;


import com.example.backswim.member.dto.UserDto;
import com.example.backswim.member.params.*;
import com.example.backswim.member.params.login.LoginRequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.TimeoutException;

public interface UserService{

    boolean joinMember(JoinMemberParam param);

    boolean duplicateEmail(CheckDuplicateID param);

    boolean emailAuth(String uuid);

    String userLogin(LoginRequestParam param);

    boolean resendEmaiAuthEmail(UserEmailParam uuid);


    boolean deleteUser(UserEmailParam param);

    boolean sendResetPassword(ResetPasswordParam param);

    boolean changePassword(ChangePasswordParam param) throws TimeoutException;

    boolean resendResetPasswordEmail(ResetPasswordParam param);

    boolean uploadProfileImage(MultipartFile file , int userId) throws Exception;

    UserDto getmyPage(int id);
}
