package com.example.myevent;

import com.mysql.cj.protocol.Message;

public class ExistUserException extends Exception {

    public ExistUserException(String message) {
        super(message);
    }
}
