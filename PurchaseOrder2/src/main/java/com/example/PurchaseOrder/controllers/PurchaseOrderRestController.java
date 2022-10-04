package com.example.PurchaseOrder.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.PurchaseOrder.models.Quotation;
import com.example.PurchaseOrder.services.OrderService;

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

@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class PurchaseOrderRestController {

    @Autowired
    private OrderService orderService;

    @PostMapping(path = "/po")
    public ResponseEntity<String> postOrder (@RequestBody String payload) {

        try (InputStream is = new ByteArrayInputStream(payload.getBytes())) {
            JsonReader r = Json.createReader(is);
            JsonObject o = r.readObject();

            JsonArray itemsArray = o.getJsonArray("lineItems");

            List<String> listOfItems = new ArrayList<>();
            for (int i = 0; i < itemsArray.size(); i++) {
                listOfItems.add(itemsArray.getJsonObject(i).getString("item"));

                // System.out.println(">>>>> List of items: " + listOfItems);
            }

            Optional<Quotation> opt = orderService.getQuotations(listOfItems);
            Quotation quote = opt.get();
            Float totalPrice = 0.0f;
            for (int i = 0; i < itemsArray.size(); i++) {
                String item = itemsArray.getJsonObject(i).getString("item");
                Integer quantity = itemsArray.getJsonObject(i).getInt("quantity");
                Float itemPrice = quote.getQuotation(item);
                totalPrice = totalPrice + (itemPrice * quantity);
            }

            String nameOfBuyer = o.getString("name");
            JsonObject jObject = Json.createObjectBuilder()
                .add("invoiceId", quote.getQuoteId())
                .add("name", nameOfBuyer)
                .add("total", totalPrice)
                .build();

            return ResponseEntity.ok().body(jObject.toString());

        } catch (IOException e) {
            return ResponseEntity.status(404).body("Error!");
        }

    }  
}
