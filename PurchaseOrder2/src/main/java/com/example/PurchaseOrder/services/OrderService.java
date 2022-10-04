package com.example.PurchaseOrder.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.PurchaseOrder.models.Quotation;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class OrderService {

    private static final String URL = "https://quotation.chuklee.com/%s";
    private static final String QUOTE = "/quotation";

    public Optional<Quotation> getQuotations(List<String> items) {

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        List<String> list = Arrays.asList("apple", "durian", "orange", "grapes", "pear");
        for (String i : items.stream().distinct().toList()) {
            if (list.contains(i))
                arrBuilder.add(i);
        }

        String getQuoteFromQuotation = UriComponentsBuilder
        .fromUriString(URL.formatted(QUOTE))
        .toUriString();

        RequestEntity<String> req = RequestEntity.post(getQuoteFromQuotation)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .body(arrBuilder.build().toString());

        // System.out.println(">>>>> This is the req: " + req);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> resp = restTemplate.exchange(req, String.class);

        // System.out.println(">>>>> This is the resp: " + resp);

        Quotation quotation = new Quotation();

        try (InputStream is = new ByteArrayInputStream(
                resp.getBody().getBytes())) {
            JsonReader reader = Json.createReader(is);
            JsonObject obj = reader.readObject();

            quotation.setQuoteId(obj.getString("quoteId"));
            JsonArray jArray = obj.getJsonArray("quotations");

            for (int i = 0; i < jArray.size(); i++) {
                JsonObject arrayObj = jArray.getJsonObject(i);
                String itemName = arrayObj.getString("item");
                Float itemPrice = Float.parseFloat(arrayObj.getJsonNumber("unitPrice").toString());

                quotation.addQuotation(itemName, itemPrice);
            }

            return Optional.of(quotation);

        } catch (Exception ex) {

            System.err.printf("+++ error: %s\n", ex.getMessage());
        }
        return Optional.empty();
    }
}