package vttp2022.day12.giphy.services;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class GiphyService {

    private static final String URL = "https://api.giphy.com/v1/gifs/search";
    //  ?api_key=Ai03xNUmAYKXXqyaXgZig2k5AYG4qEjH&q=pokemon&limit=25&offset=0&rating=g&lang=en

    // GIPHY_API_KEY=Ai03xNUmAYKXXqyaXgZig2k5AYG4qEjH
    @Value("${giphy.api.key}")
    private String giphyKey;

    public List<String> getGifs(String searchPhrase)
    {
        return getGifs(searchPhrase, 10, "pg");
    }

    public List<String> getGifs(String searchPhrase, Integer searchLimit)
    {
        return getGifs(searchPhrase, searchLimit, "pg");
    }

    public List<String> getGifs(String searchPhrase, String searchRating)
    {
        return getGifs(searchPhrase, 10, searchRating);
    }

    public List<String> getGifs(String searchPhrase, Integer searchLimit, String searchRating)
    {
        List<String> results = new LinkedList<>();
        
        String searchUrl = UriComponentsBuilder.fromUriString(URL)
        .queryParam("api_key", giphyKey)
        .queryParam("q", searchPhrase)
        .queryParam("limit", searchLimit)
        .queryParam("rating", searchRating)
        .toUriString();

        RequestEntity<Void> req = RequestEntity
        .get(searchUrl)
        .build();

        RestTemplate template = new RestTemplate();

        ResponseEntity<String> resp = null;
        
        try {resp = template.exchange(req, String.class);
        }catch (Exception ex) {
            ex.printStackTrace();
            return results;
        }

            JsonReader r = Json.createReader(new StringReader(resp.getBody()));
            JsonObject o = r.readObject();
            JsonArray data = o.getJsonArray("data");

            for (int i=0; i<data.size(); i++) {
                JsonObject obj = data.getJsonObject(i);
                // obj = obj.getJsonObject("images").getJsonObject("fixed_width");
                // obj = obj.getJsonObject("fixed_width");
                String url = obj.getJsonObject("images").getJsonObject("fixed_width").getString("url");
                results.add(url);
            }    
            
            return results;
    }
    
}
