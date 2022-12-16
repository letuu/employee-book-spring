package com.skypro.employeebookspring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid data entered") //или так, тогда createEmployee без try
public class InvalidEmployeeRequestExp extends RuntimeException {
}
