package com.example.backswim.member.exception;

public class WrongPasswordException extends RuntimeException{

    public WrongPasswordException(String s){
        super(s);
    }
}
