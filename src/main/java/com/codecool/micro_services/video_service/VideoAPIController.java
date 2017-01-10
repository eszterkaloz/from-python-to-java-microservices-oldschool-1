package com.codecool.micro_services.video_service;


import com.codecool.micro_services.video_service.vimeo_service.VimeoAPIService;
import com.codecool.micro_services.video_service.youtube_service.YouTubeAPIService;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class VideoAPIController {
    public static final String SEARCH_PARAM_KEY = "search";
    public static final List<String> VIDEO_CATEGORIES = Arrays.asList("unboxing", "review");
    private Map<String, Map> videoLinksByService;

    private final YouTubeAPIService youTubeAPIService;
    private final VimeoAPIService vimeoAPIService;

    public VideoAPIController(YouTubeAPIService youTubeAPIService, VimeoAPIService vimeoAPIService) {
        this.youTubeAPIService = youTubeAPIService;
        this.vimeoAPIService = vimeoAPIService;
    }

    public JSONObject getVideoLinks(Request request, Response response) throws IOException, URISyntaxException {
        String searchKey = request.queryParams(SEARCH_PARAM_KEY);
        //todo: exceptions for wrong parameter
        videoLinksByService = new HashMap<>();
        Map<String, String> videosByCategory = new HashMap<>();
        videoLinksByService.put("youtube", videosByCategory);
        for (int i = 0; i < VIDEO_CATEGORIES.size(); i++) {

            videoLinksByService.put(VIDEO_CATEGORIES.get(i), youTubeAPIService.getVideoFromYoutube(searchKey+"+"+ VIDEO_CATEGORIES.get(i)));
            //videoLinksByService.put("vimeo", vimeoAPIService.getVideosFromVimeo(searchKey));

        }

        return resultToJSON();
    }

    private JSONObject resultToJSON() {
        return new JSONObject().put("result", videoLinksByService);
    }

    public String status(Request request, Response response) {
        return "ok";
    }

}
