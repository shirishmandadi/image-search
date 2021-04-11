package com.shirish.sofi.image.search.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shirish.sofi.image.search.domain.ImageSearchResponseDEF;
import com.shirish.sofi.image.search.service.ImageSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController
public class ImagesRestServiceController {
        @Autowired
        ImageSearch imageSearch;
        @GetMapping
        @RequestMapping(value={"/search/{searchterm}"})
        public String searchImages(@PathVariable("searchterm") String searchterm, @RequestHeader Map<String, String> headers) {
            ///Default limit value
            int limit = 5;
            if (headers.get("limit") != null ) {
                limit = Integer.parseInt(headers.get("limit"));
            }
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            ImageSearchResponseDEF imageSearchResponseDEF = this.imageSearch.imageSearchService(searchterm, limit);
            return gson.toJson(imageSearchResponseDEF);
        }

    }
