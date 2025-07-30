package com.employee.exception;

public class DepartmentAlreadyExistException extends Exception {

    public DepartmentAlreadyExistException(String message) {
        super(message);
    }

    public DepartmentAlreadyExistException() {
    }

}
