package vttp2022.day12.giphy.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp2022.day12.giphy.services.GiphyService;

@Controller
public class GiphyController {

    @Autowired
    private GiphyService giphySvc;

    @GetMapping(path="/search")
    public String getSearch(
        @RequestParam(name="search", required = true) String searchPhrase, 
        @RequestParam(name="limit", defaultValue="10") Integer searchLimit, 
        @RequestParam(name="rating", defaultValue="pg") String searchRating, 
        Model model) 
        {
            List<String> images = giphySvc.getGifs(searchPhrase, searchLimit, searchRating);
            
            model.addAttribute("searchPhrase", searchPhrase);
            model.addAttribute("images", images);
            
            return "result";
            
        }
    
}
