package com.progressoft.induction.atm.exceptions;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String message){
        super (message);
    }
}
