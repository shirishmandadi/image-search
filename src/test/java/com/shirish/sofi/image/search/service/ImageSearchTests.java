package com.shirish.sofi.image.search.service;

import com.shirish.sofi.image.search.controller.GetImagesControllerTest;
import com.shirish.sofi.image.search.domain.ImageSearchResponseDEF;
import static org.mockito.Mockito.when;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;


//@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(properties = {"giffy.api.key=YKeWL64F8i9lxRJN269CqthXsB8n6TkF"})
public class ImageSearchTests {
    @MockBean
    RestTemplate restTemplate;
    @Autowired
    ImageSearch imageSearch;
    @Test
    public void searchImages() throws Exception{
        GetImagesControllerTest getImagesControllerTest = new GetImagesControllerTest();
        String content = getImagesControllerTest.readJson("giphymockresponse.json");
        when(restTemplate.getForObject("https://api.giphy.com/v1/gifs/search?api_key=YKeWL64F8i9lxRJN269CqthXsB8n6TkF&q=cheeseburgers&limit=5&offset=0", String.class)).thenReturn(content);
        ImageSearchResponseDEF imageSearchResponseDEF = imageSearch.imageSearchService("cheeseburgers", 5);
        Assert.assertNotNull(imageSearchResponseDEF);
        Assert.assertEquals(5, imageSearchResponseDEF.getData().getImages().size());

    }
    public void searchNoImagesTest() throws Exception{
        GetImagesControllerTest getImagesControllerTest = new GetImagesControllerTest();
        String content = getImagesControllerTest.readJson("giphymockresponse.json");
        when(restTemplate.getForObject("https://api.giphy.com/v1/gifs/search?api_key=YKeWL64F8i9lxRJN269CqthXsB8n6TkF&q=cheeseburgers&limit=5&offset=0", String.class)).thenReturn(content);
        ImageSearchResponseDEF imageSearchResponseDEF = imageSearch.imageSearchService("cheeseburgers", 5);
        Assert.assertNotNull(imageSearchResponseDEF);
        Assert.assertEquals(0,imageSearchResponseDEF.getItemCount().intValue());

    }
}
