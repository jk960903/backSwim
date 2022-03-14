package com.example.backswim.member.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String s){

        super(s);
    }
}
