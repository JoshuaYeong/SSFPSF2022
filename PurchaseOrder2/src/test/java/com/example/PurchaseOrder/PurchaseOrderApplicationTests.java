package com.example.PurchaseOrder;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.example.PurchaseOrder.models.Quotation;
import com.example.PurchaseOrder.services.OrderService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PurchaseOrderApplicationTests {

	@Autowired
	private OrderService oService;

	@Test
	void contextLoads() {

		List<String> listOfItems = new LinkedList<>(){{
			add("durian");
			add("plum");
			add("pear"); 
		}};

		Optional<Quotation> opt = oService.getQuotations(listOfItems);

		Assertions.assertTrue(opt != null);
	}

}
