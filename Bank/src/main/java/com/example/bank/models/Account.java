package com.example.bank.models;

import java.sql.Timestamp;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Account {
    private String acctId;
    private Float balance;
    private Timestamp ts;

    public String getAcctId() {
        return acctId;
    }
    public void setAcctId(String acctId) {
        this.acctId = acctId;
    }
    public Float getBalance() {
        return balance;
    }
    public void setBalance(Float balance) {
        this.balance = balance;
    }
    public Timestamp getTs() {
        return ts;
    }
    public void setTs(Timestamp ts) {
        this.ts = ts;
    }

    public static Account populate(SqlRowSet rs) {
        Account acct = new Account();
        acct.setAcctId(rs.getString("acct_id"));
        acct.setBalance(rs.getFloat("balance"));
        acct.setTs(rs.getTimestamp("ts"));
        return acct;
    }

}
