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
        //todo: exceptions for wrong parameter
        videos = new ArrayList<String>();

        for (int i = 0; i < VIDEO_CATEGORIES.size(); i++) {

            videos.add(youTubeAPIService.getVideoFromYoutube(searchKey + "+" + VIDEO_CATEGORIES.get(i)));
            videos.add(vimeoAPIService.getVideoFromVimeo(searchKey + "+" + VIDEO_CATEGORIES.get(i)));

        }

        return resultToJSON();
    }

    private JSONObject resultToJSON() {
        return new JSONObject().put("result", videos);
    }

    public String status(Request request, Response response) {
        return "ok";
    }

}
