package vttp2022.purchaseorder.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp2022.purchaseorder.models.Quotation;

@Service
public class QuotationService {

    private static final String URL = "https://quotation.chuklee.com/quotation";

    public Optional<Quotation> getQuotations(List<String> items) {

        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

        for (String item : items) {
            jsonArrayBuilder.add(item);
        }

        RequestEntity<String> req = RequestEntity
            .post(URL)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(jsonArrayBuilder.build().toString());

        RestTemplate template = new RestTemplate();

        ResponseEntity<String> resp = template.exchange(req, String.class);

        InputStream is = new ByteArrayInputStream(resp.getBody().getBytes());
            JsonReader reader = Json.createReader(is);
            JsonObject data = reader.readObject();
        
        Quotation quote = new Quotation();
        quote.setQuoteId(data.getString("quoteId"));

        JsonArray jsonArray = data.getJsonArray("quotations");
        for(int i=0; i < jsonArray.size(); i++) {
            JsonObject itemObj = jsonArray.getJsonObject(i);
            String itemName = itemObj.getString("itemObj");
            Integer unitPrice = itemObj.getJsonNumber("unitPrice").intValue();
            quote.addQuotation(itemName, unitPrice.floatValue());

        }

        return Optional.of(quote);
    }

}
