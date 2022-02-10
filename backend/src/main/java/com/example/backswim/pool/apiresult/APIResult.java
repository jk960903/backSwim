package com.example.backswim.pool.apiresult;

import com.example.backswim.pool.apiresult.enums.StatusEnum;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class APIResult<T> {

    private Integer statusCode;

    private T Data;

    private StatusEnum message;

}
