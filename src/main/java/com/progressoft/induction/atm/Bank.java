package com.progressoft.induction.atm;

import com.progressoft.induction.atm.exceptions.AccountNotFoundException;

import java.math.BigDecimal;
import java.util.HashMap;

public class Bank implements BankingSystem {
    HashMap<String, BigDecimal> accounts = new HashMap<String, BigDecimal>();

    public void setUpAccount(){
        accounts.put("123456789", new BigDecimal(1000));
        accounts.put("111111111", new BigDecimal(1000));
        accounts.put("222222222", new BigDecimal(1000));
        accounts.put("333333333", new BigDecimal(1000));
        accounts.put("444444444", new BigDecimal(1000));
    }
    @Override
    public BigDecimal getAccountBalance(String accountNumber) {
        boolean isAccountPresent = accounts.containsKey(accountNumber);

        if(!isAccountPresent){
            throw new AccountNotFoundException("not found");
        }
        return accounts.get(accountNumber);
    }

    @Override
    public void debitAccount(String accountNumber, BigDecimal amount) {
        accounts.put(accountNumber,accounts.get(accountNumber).subtract(amount));
    }
}
