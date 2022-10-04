package vttp2022.purchaseorder;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import vttp2022.purchaseorder.models.Quotation;
import vttp2022.purchaseorder.services.QuotationService;

@SpringBootTest
class PurchaseorderApplicationTests {

	@Autowired
	private QuotationService qService;

	@Test
	void contextLoads() {

		List<String> listOfItems = new LinkedList<>(){{
			add("durian");
			add("plum");
			add("pear"); 
		}};

		Optional<Quotation> opt = qService.getQuotations(listOfItems);

		Assertions.assertTrue(opt != null);
	}

}
