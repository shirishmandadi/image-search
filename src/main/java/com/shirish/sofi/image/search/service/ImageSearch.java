package com.shirish.sofi.image.search.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.shirish.sofi.image.search.domain.Data;
import com.shirish.sofi.image.search.domain.ErrorResponse;
import com.shirish.sofi.image.search.domain.Image;
import com.shirish.sofi.image.search.domain.ImageSearchResponseDEF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
@Service
public class ImageSearch {
    @Autowired
    RestTemplate restTemplate;
    @Value("${giffy.api.key}")
    private String apiKey;
    public ImageSearchResponseDEF imageSearchService(String searchterm, int limit) {
        String uri = "https://api.giphy.com/v1/gifs/search?api_key="+apiKey.trim()+ "&q=" + searchterm + "&limit="+limit+"&offset=0";
        String result = restTemplate.getForObject(uri, String.class);
        ImageSearchResponseDEF imageSearchResponseDEF = new ImageSearchResponseDEF();
        try {
            JsonObject convertedObject = new Gson().fromJson(result, JsonObject.class);
            if (convertedObject.isJsonObject()) {
                JsonElement metaData = convertedObject.get("meta");
                if (metaData.getAsJsonObject().get("status").getAsInt() == 200) {
                    JsonElement dataObject = convertedObject.get("data");
                    JsonArray giphyImageObjects = dataObject.getAsJsonArray();
                    if (giphyImageObjects.isJsonArray()) {
                        if (giphyImageObjects.size() < 5) {
                            return populateNoImagesFound();
                        } else {
                            imageSearchResponseDEF = populateImageSearchResponse(giphyImageObjects);
                        }

                    } else {
                        imageSearchResponseDEF = populateErrorResponse("InvalidJsonArrayObjet", "Invalid Json Object returned from Giphy");
                    }
                } else {
                    populateErrorResponse(metaData.getAsJsonObject().get("status").getAsString(), metaData.getAsJsonObject().get("msg").getAsString());

                }
            }
        } catch ( Exception exp) {
            populateErrorResponse("Unknown Error status", "Error while parsing Giphy Response");
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

    private ImageSearchResponseDEF populateErrorResponse(String errorStatus, String errorDesc) {
        ErrorResponse errorResp = new ErrorResponse();
        errorResp.setDescription(errorDesc);
        errorResp.setError_code(errorStatus);
        List<ErrorResponse> errors = new ArrayList<>();
        errors.add(errorResp);
        ImageSearchResponseDEF imageSearchErrorResponseDEF = new ImageSearchResponseDEF();
        imageSearchErrorResponseDEF.setErrors(errors);
        return imageSearchErrorResponseDEF;
    }



}
