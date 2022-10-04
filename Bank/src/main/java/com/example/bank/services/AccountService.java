package com.example.bank.services;

import com.example.bank.repositories.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository acctRepo;

    @Transactional
    public void transfer(String fromAcct, String toAcct, Float amount) {

        try {
            acctRepo.withdraw(fromAcct, amount);

            if (amount > 300)
                throw new IllegalArgumentException("You cannot transfer more than $300");

            acctRepo.deposit(toAcct, amount);
        } catch (Exception e) {
            e.printStackTrace();
            // if it throws e, then the transaction will rollback
            throw e;
        }

        // if it exits here, then the transaction will commit
    }
    
}
