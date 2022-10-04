package vttp2022.purchaseorder.controllers;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp2022.purchaseorder.models.Quotation;
import vttp2022.purchaseorder.services.QuotationService;

@RestController
@RequestMapping(path="/api/po")
public class PurchaseOrderRestController {

    @Autowired
    private QuotationService qService;

    @PostMapping(path="", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postPO(@RequestBody String payload) {

        InputStream is = new ByteArrayInputStream(payload.getBytes());
        JsonReader reader = Json.createReader(is);
        JsonObject obj = reader.readObject();

        JsonArray listOfItems = obj.getJsonArray("lineItems");

        HashMap<String, Integer> hMap = new HashMap<>();
        for(int i=0; i < listOfItems.size(); i++) {
        JsonObject itemObj = listOfItems.getJsonObject(i);
        hMap.put(itemObj.getString("item"), itemObj.getInt("quantity"));
    }

        Optional<Quotation> opt = qService.getQuotations(new ArrayList<>());

        if (opt.isEmpty()) {
            return ResponseEntity.status(404).body("Error!");
        }

        Map.Entry<String,Integer> entry = hMap.entrySet().iterator().next();
        String key = entry.getKey();
        Integer value = entry.getValue();

        Quotation quote = opt.get();
        System.out.println(">>>>> keys: " + hMap);
        Float unitPrice = quote.getQuotation(key);
        Integer total = (int) (value * unitPrice);
        Float totalPrice = total.floatValue();
        totalPrice = 0.00f;

        String name = obj.getString("name");
        JsonObject jsonObject = Json.createObjectBuilder()
            .add("invoiceId", quote.getQuoteId())
            .add("name", name)
            .add("total", totalPrice)
            .build();

        return ResponseEntity.ok().body(jsonObject.toString());
    }
}
