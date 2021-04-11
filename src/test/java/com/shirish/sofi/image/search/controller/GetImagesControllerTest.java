package com.shirish.sofi.image.search.controller;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.shirish.sofi.image.search.domain.Data;
import com.shirish.sofi.image.search.domain.Image;
import com.shirish.sofi.image.search.domain.ImageSearchResponseDEF;
import com.shirish.sofi.image.search.service.ImageSearch;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.util.StreamUtils;


//@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(ImagesRestServiceController.class)
public class GetImagesControllerTest {
    @MockBean
    private ImageSearch imageSearchService;


    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getImages() throws Exception{
        String content = readJson("giphymockresponse.json");

        //System.out.println("mock response " + content);
        JsonObject convertedObject = new Gson().fromJson(content, JsonObject.class);
        ImageSearchResponseDEF imageSearchResp = new ImageSearchResponseDEF();
        Data data = new Data();

        JsonElement dataObject = convertedObject.get("data");
        JsonArray giphyImageObjects = dataObject.getAsJsonArray();
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
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("limit", "5");

        given(imageSearchService.imageSearchService("cheeseburgers", 5)).willReturn(imageSearchResp);

        this.mockMvc.perform(get("/search/{searchterm}", "cheeseburgers"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("uQvxobRExS9nG")));


    }
    public String readJson(String fileName) throws Exception {
        Resource resource = new ClassPathResource(fileName);
        String json = StreamUtils.copyToString(resource.getInputStream(), Charset.defaultCharset());
        return json;
    }

}
