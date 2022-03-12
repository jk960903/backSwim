package com.example.backswim.member.dto;

import com.example.backswim.member.entity.UserEntity;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String userEmail;

    private String imageURL;

    public static UserDto of(UserEntity userEntity){
        return UserDto.builder().userEmail(userEntity.getUserEmail()).imageURL(userEntity.getImgUrl()).build();
    }
}
