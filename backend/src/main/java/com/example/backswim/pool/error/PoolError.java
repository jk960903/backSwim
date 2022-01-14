package com.example.backswim.pool.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PoolError {

    private String ErrorCode;

    private String ErrorMessage;
}
