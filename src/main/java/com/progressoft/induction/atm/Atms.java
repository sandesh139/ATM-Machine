package com.progressoft.induction.atm;

import com.progressoft.induction.atm.exceptions.InsufficientFundsException;
import com.progressoft.induction.atm.exceptions.NotEnoughMoneyInATMException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Atms implements ATM{


    Map<Banknote, Integer> atmMoney = new HashMap<Banknote, Integer>();
    Bank bank = new Bank();
    public Atms(){
        setUp();
    }

    private void setUp(){
        bank.setUpAccount();
        atmMoney.put(Banknote.FIFTY_JOD, 10);
        atmMoney.put(Banknote.TWENTY_JOD, 20);
        atmMoney.put(Banknote.TEN_JOD, 100);
        atmMoney.put(Banknote.FIVE_JOD, 100);
    }

    private boolean hasEnoughMoney(BigDecimal amount){
        BigDecimal atmSum = new BigDecimal("0");
        for (Banknote note : atmMoney.keySet()){
            atmSum = atmSum.add(note.getValue().multiply(new BigDecimal(atmMoney.get(note)+"")));
        }
        if (atmSum.subtract(amount).compareTo(BigDecimal.ZERO) < 0 ){
            throw new NotEnoughMoneyInATMException("Not enough Money at Atm");
        }
        return true;
    }

    private boolean canGetNote(Banknote banknote, BigDecimal amount){
        if (amount.compareTo(banknote.getValue())>=0 && atmMoney.get(banknote) > 0){
            atmMoney.put(banknote, atmMoney.get(banknote)- 1);
            return true;
        }
        return false;
    }


    private List<Banknote> getMoneyFromAtm (BigDecimal amount, List<Banknote> notes){
        if (canGetNote(Banknote.FIFTY_JOD,amount)){
            notes.add(Banknote.FIFTY_JOD);
            getMoneyFromAtm(amount.subtract(Banknote.FIFTY_JOD.getValue()), notes);
        } else if (canGetNote(Banknote.TWENTY_JOD,amount)){
            notes.add(Banknote.TWENTY_JOD);
            getMoneyFromAtm(amount.subtract(Banknote.TWENTY_JOD.getValue()), notes);
        } else if (canGetNote(Banknote.TEN_JOD,amount)){
            getMoneyFromAtm(amount.subtract(Banknote.TEN_JOD.getValue()), notes);
        } else if (canGetNote(Banknote.FIVE_JOD,amount)){
            getMoneyFromAtm(amount.subtract(Banknote.FIVE_JOD.getValue()), notes);
        }
        return notes;
    }


    @Override
    public List<Banknote> withdraw(String accountNumber, BigDecimal amount) {
        if (bank.getAccountBalance(accountNumber).subtract(amount).compareTo(BigDecimal.ZERO)<0){
            throw new InsufficientFundsException("Not enough balance");
        }
        List<Banknote> result = new ArrayList<>();

        if (hasEnoughMoney(amount)){
            bank.debitAccount(accountNumber,amount);
            return getMoneyFromAtm(amount, result);
        }
        return result;
    }
}
