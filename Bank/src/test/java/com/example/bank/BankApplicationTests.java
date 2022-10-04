package com.example.bank;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import com.example.bank.models.Account;
import com.example.bank.repositories.AccountRepository;
import com.example.bank.services.AccountService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BankApplicationTests {

	@Autowired
	private AccountRepository acctRepo;

	@Autowired
	private AccountService acctSvc;

	@Test
	void shouldFindFred() {
		Optional<Account> opt = acctRepo.findAccountByAccountId("fred");
		assertTrue(opt.isPresent());
	}

	@Test
	public void shouldNotDeposit() {
		assertThrows(IllegalArgumentException.class, 
			() -> acctRepo.deposit("wilma", 100f));
	}

	@Test
	public void shouldWithdraw() {
		Float amount = 100f;

		assertTrue(acctRepo.deposit("fred", amount));
	}

	@Test
	public void notTransaction() {
		Float amount = 50f;

		acctSvc.transfer("fred", "barney", amount);
		assertTrue(true);
	}

}
