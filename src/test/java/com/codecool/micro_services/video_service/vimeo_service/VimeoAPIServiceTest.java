package com.codecool.micro_services.video_service.vimeo_service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class VimeoAPIServiceTest {

    VimeoAPIService vimeoAPIService;
    @Before
    public void setUp() throws Exception {
        vimeoAPIService = VimeoAPIService.getInstance();

    }

    @After
    public void tearDown() throws Exception {
        vimeoAPIService = null;
    }

    @Test
    public void getInstance() throws Exception {
        VimeoAPIService vimeoAPIService2 = VimeoAPIService.getInstance();
        Assert.assertEquals(vimeoAPIService, vimeoAPIService2);
    }

    @Test
    public void getVideoFromVimeo() throws Exception {
        String embedCode = "<iframe src=\"https://player.vimeo.com/video/40056491?title=0&byline=0&portrait=0&badge=0&autopause=0&player_id=0\" width=\"1280\" height=\"720\" frameborder=\"0\" title=\"iphone- diorama\" webkitallowfullscreen mozallowfullscreen allowfullscreen></iframe>";
        Assert.assertEquals(vimeoAPIService.getVideoFromVimeo("iphone"), embedCode);
    }

}