package com.example.backswim.common.api;

import com.example.backswim.common.api.enums.StatusEnum;
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
