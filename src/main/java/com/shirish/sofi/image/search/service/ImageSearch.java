package com.shirish.sofi.image.search.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import com.shirish.sofi.image.search.domain.ErrorResponse;
import com.shirish.sofi.image.search.domain.Data;
import com.shirish.sofi.image.search.domain.Image;
import com.shirish.sofi.image.search.domain.ImageSearchResponseDEF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;
@Service
public class ImageSearch {
    @Autowired
    RestTemplate restTemplate;
    @Value("${giffy.api.key}")
    private String apiKey;
    public ImageSearchResponseDEF imageSearchService(String searchterm, int limit) {
        System.out.println("Api Key " + apiKey);
        System.out.println("resultLimit " + limit);
        String uri = "https://api.giphy.com/v1/gifs/search?api_key="+apiKey.trim()+ "&q=" + searchterm + "&limit="+limit+"&offset=0";
        System.out.println("URI " + uri);
        String result = restTemplate.getForObject(uri, String.class);
        ImageSearchResponseDEF imageSearchResponseDEF = new ImageSearchResponseDEF();

        JsonObject convertedObject = new Gson().fromJson(result, JsonObject.class);
        if (convertedObject.isJsonObject()) {
            JsonElement dataObject = convertedObject.get("data");
            JsonArray giphyImageObjects = dataObject.getAsJsonArray();
            if (giphyImageObjects.isJsonArray()) {
                if (giphyImageObjects.size() < 5) {
                    return populateNoImagesFound();
                } else {
                    imageSearchResponseDEF = populateImageSearchResponse(giphyImageObjects);
                }

            } else {
                imageSearchResponseDEF = populateErrorResponse();
            }

            return imageSearchResponseDEF;
        }
        return imageSearchResponseDEF;
    }

    private ImageSearchResponseDEF populateNoImagesFound() {
        ImageSearchResponseDEF noDataFound = new ImageSearchResponseDEF();
        noDataFound.setItemCount(0);
        return noDataFound;
    }

    private ImageSearchResponseDEF populateImageSearchResponse(JsonArray giphyImageObjects) {
        ImageSearchResponseDEF imageSearchResp = new ImageSearchResponseDEF();
        Data data = new Data();
        List<Image> images = new ArrayList<>();
        for (JsonElement jsonElement: giphyImageObjects) {
            JsonObject imageObject =  jsonElement.getAsJsonObject();
            Image image = new Image();
            image.setGifId(imageObject.get("id").getAsString());
            image.setUrl(imageObject.get("url").getAsString());
            images.add(image);
        }
        data.setImages(images);
        imageSearchResp.setData(data);
        return imageSearchResp;
    }

    private ImageSearchResponseDEF populateErrorResponse() {
        ErrorResponse errorResp = new ErrorResponse();
        errorResp.setDescription("InvalidJsonObject");
        errorResp.setError_code("Invalid Json Object received from Giphy");
        List<ErrorResponse> errors = new ArrayList<>();
        errors.add(errorResp);
        ImageSearchResponseDEF imageSearchErrorResponseDEF = new ImageSearchResponseDEF();
        imageSearchErrorResponseDEF.setErrors(errors);
        return imageSearchErrorResponseDEF;
    }



}
