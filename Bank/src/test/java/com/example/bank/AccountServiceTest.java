package com.example.bank;

import static org.junit.jupiter.api.Assertions.*;

import com.example.bank.repositories.AccountRepository;
import com.example.bank.services.AccountService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class AccountServiceTest {

    @MockBean
    private AccountRepository acctRepo;

    @Autowired
    private AccountService acctSvc;
    
    @Test
    public void transferShouldFail() {
        Float amount = 1000f;

        Mockito.when(acctRepo.withdraw("fred", amount)).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, 
            () -> acctSvc.transfer("fred", "barney", amount));
    }
}
