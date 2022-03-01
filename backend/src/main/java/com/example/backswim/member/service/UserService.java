package com.example.backswim.member.service;

import com.example.backswim.member.entity.UserEntity;
import com.example.backswim.member.params.ChangePasswordParam;
import com.example.backswim.member.params.CheckDuplicateID;
import com.example.backswim.member.params.JoinMemberParam;
import com.example.backswim.member.params.ResetPasswordParam;
import com.example.backswim.member.params.login.LoginRequestParam;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.concurrent.TimeoutException;

public interface UserService{

    boolean joinMember(JoinMemberParam param);

    boolean duplicateEmail(CheckDuplicateID param);

    boolean emailAuth(String uuid);

    boolean sendResetPassword(ResetPasswordParam param);

    boolean changePassword(ChangePasswordParam param) throws TimeoutException;

    String userLogin(LoginRequestParam param);
}
