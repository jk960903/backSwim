package com.example.backswim.pool.apiresult;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class APIResult<T> {

    private Integer statusCode;

    private T Data;

    private String message;

}
