package com.insurance.exception;

public class PolicyNotFoundException extends Exception {

    public PolicyNotFoundException(String message) {
        super(message);
    }
}
