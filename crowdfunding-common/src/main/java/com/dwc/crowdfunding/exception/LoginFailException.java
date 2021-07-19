package com.dwc.crowdfunding.exception;

public class LoginFailException extends RuntimeException {
    public LoginFailException(String message){
        super(message);
    }
}
