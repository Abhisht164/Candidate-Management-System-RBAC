package com.learning.candidatemanagementsystem.exception;

public class UserAlreadyExistException extends RuntimeException{
    public UserAlreadyExistException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s already exists with the given input id %s : '%s'", resourceName, fieldName, fieldValue));
    }
}
