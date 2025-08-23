package com.employee.exception.department;

public class DepartmentAlreadyExistException extends Exception {

    public DepartmentAlreadyExistException(String message) {
        super(message);
    }

    public DepartmentAlreadyExistException() {
    }

}
