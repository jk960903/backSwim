package com.example.backswim.member.service;

import com.example.backswim.member.entity.UserEntity;
import com.example.backswim.member.params.CheckDuplicateID;
import com.example.backswim.member.params.JoinMemberParam;

public interface UserService{

    boolean joinMember(JoinMemberParam param);

    boolean duplicateEmail(CheckDuplicateID param);

    boolean emailAuth(String uuid);
}
