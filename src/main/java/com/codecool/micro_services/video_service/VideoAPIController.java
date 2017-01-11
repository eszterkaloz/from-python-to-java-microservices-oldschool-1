package com.codecool.micro_services.video_service;


import com.codecool.micro_services.video_service.vimeo_service.VimeoAPIService;
import com.codecool.micro_services.video_service.youtube_service.YouTubeAPIService;
import org.json.JSONException;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class VideoAPIController {
    public static final String SEARCH_PARAM_KEY = "search";
    public static final List<String> VIDEO_CATEGORIES = Arrays.asList("unboxing", "review");
    private final YouTubeAPIService youTubeAPIService;
    private final VimeoAPIService vimeoAPIService;
    private List<String> videos;

    public VideoAPIController(YouTubeAPIService youTubeAPIService, VimeoAPIService vimeoAPIService) {
        this.youTubeAPIService = youTubeAPIService;
        this.vimeoAPIService = vimeoAPIService;
    }

    public String getVideoLinks(Request request, Response response) throws IOException, URISyntaxException {
        String searchKey = request.queryParams(SEARCH_PARAM_KEY);
        Map<String, List<String>> result = new HashMap<>();
        response.type("application/json");
        if (searchKey.length() <= 2) {
            response.status(400);
            return new JSONObject()
                    .put("error_type", "Bad request. Request parameter is missing or too low?")
                    .put("error_code", 400).toString();
        }

        videos = new ArrayList<>();
        for (String category : VIDEO_CATEGORIES) {
            String embedCodeForYoutube = youTubeAPIService.getVideoFromYoutube(searchKey + "+" + category);
            videos.add(responseBuilder(searchKey, embedCodeForYoutube, "youtube", category));

            try {
                String embedCodeForVimeo = vimeoAPIService.getVideoFromVimeo(searchKey + "+" + category);
                videos.add(responseBuilder(searchKey, embedCodeForVimeo, "vimeo", category));
            } catch (JSONException e) {
                System.err.println("No video found on Vimeo");
            }
        }
        result.put("result", videos);
        return result.toString();
    }


    private String responseBuilder(String title, String embedCode, String provider, String category) {
        return new JSONObject()
                .put("title", title)
                .put("embed code", embedCode)
                .put("provider", provider)
                .put("category", category).toString();
    }


}
