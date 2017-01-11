package com.codecool.micro_services.video_service;


import com.codecool.micro_services.video_service.vimeo_service.VimeoAPIService;
import com.codecool.micro_services.video_service.youtube_service.YouTubeAPIService;

import org.json.JSONObject;

import spark.Request;
import spark.Response;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VideoAPIController {
    public static final String SEARCH_PARAM_KEY = "search";
    public static final List<String> VIDEO_CATEGORIES = Arrays.asList("unboxing", "review");
    private List<String> videos;

    private final YouTubeAPIService youTubeAPIService;
    private final VimeoAPIService vimeoAPIService;

    public VideoAPIController(YouTubeAPIService youTubeAPIService, VimeoAPIService vimeoAPIService) {
        this.youTubeAPIService = youTubeAPIService;
        this.vimeoAPIService = vimeoAPIService;
    }

    public JSONObject getVideoLinks(Request request, Response response) throws IOException, URISyntaxException {
        String searchKey = request.queryParams(SEARCH_PARAM_KEY);

        if (searchKey.length() <= 2) {
            response.status(400);
            JSONObject errorContent = new JSONObject()
                    .put("error_type", "Bad request. Request parameter is missing or too low?")
                    .put("error_code", 400);
            return new JSONObject().put("error", errorContent);
        }

        videos = new ArrayList<>();
        for (String category : VIDEO_CATEGORIES) {
            String embedCode = youTubeAPIService.getVideoFromYoutube(searchKey + "+" + category);
            videos.add(responseBuilder(searchKey, embedCode, "youtube", category));

//            embedCode = vimeoAPIService.getVideosFromVimeo(searchKey + "+" + category);
//            videos.add(responseBuilder(searchKey, embedCode, "vimeo", category));

        }

        return new JSONObject().put("result", videos);
    }

    private String responseBuilder(String title, String embedCode, String provider, String category){
        return "{\"title\":\""+title+"\",\"embed code\":\""+embedCode+"\",\"provider\":\""+provider+"\",\"category\":\""+ category+"\"}";
    }

    public String status(Request request, Response response) {
        return "ok";
    }

}
