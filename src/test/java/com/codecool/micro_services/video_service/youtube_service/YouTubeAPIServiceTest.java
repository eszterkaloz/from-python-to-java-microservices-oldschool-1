package com.codecool.micro_services.video_service.youtube_service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class YouTubeAPIServiceTest {

    YouTubeAPIService youTubeAPIService;
    @Before
    public void setUp() throws Exception {
    youTubeAPIService = YouTubeAPIService.getInstance();
    }

    @After
    public void tearDown() throws Exception {
    youTubeAPIService = null;
    }

    @Test
    public void getInstance() throws Exception {
    YouTubeAPIService youTubeAPIService2 = YouTubeAPIService.getInstance();
        Assert.assertEquals(youTubeAPIService, youTubeAPIService2);
    }

    @Test
    public void getVideoFromYoutube() throws Exception {
        String videoId = "<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/Yi9RElWqixA\" frameborder=\"0\" allowfullscreen></iframe>";
        Assert.assertEquals(youTubeAPIService.getVideoFromYoutube("iphone"),videoId);
    }

}