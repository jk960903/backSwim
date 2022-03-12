package com.example.backswim.member.params.mypage;

import com.example.backswim.common.params.CheckInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 이 파라미터에 생각한 로직으로는 password 변경을 안할수도 있어 param이 널이라도 400 에러를 뱉지않습니다.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserParam implements CheckInterface{

    private String beforePassword;

    private String afterPassword;


    @Override
    public boolean checkStatus() {
        if(beforePassword == null || afterPassword == null){
            return false;
        }

        return true;
    }
}
