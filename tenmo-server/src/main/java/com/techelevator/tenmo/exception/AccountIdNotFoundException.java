package com.techelevator.tenmo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Account ID Not Found")
public class AccountIdNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public AccountIdNotFoundException() {
        super("Account ID Not Found");
    }

}
