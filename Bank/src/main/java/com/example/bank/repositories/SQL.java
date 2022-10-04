package com.example.bank.repositories;

public interface SQL {

    public static final String SQL_SELECT_ACCOUNT_BY_ACCT_ID = "select * from account where acct_id = ?";
    
    public static final String SQL_UPDATE_ACCOUNT_BY_ACCT_ID = "update account set balance = ? where acct_id = ?";
}
