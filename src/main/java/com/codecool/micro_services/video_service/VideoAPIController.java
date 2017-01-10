package com.codecool.micro_services.video_service;


import com.codecool.micro_services.video_service.vimeo_service.VimeoAPIService;
import com.codecool.micro_services.video_service.youtube_service.YouTubeAPIService;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class VideoAPIController {
    public static final String SEARCH_PARAM_KEY = "search";
    private Map<String, Map> videoLinksByService ;

    private final YouTubeAPIService youTubeAPIService;
    private final VimeoAPIService vimeoAPIService;

    public VideoAPIController(YouTubeAPIService youTubeAPIService, VimeoAPIService vimeoAPIService) {
        this.youTubeAPIService = youTubeAPIService;
        this.vimeoAPIService = vimeoAPIService;
    }

    public Map<String,Map> getVideoLinks(Request request, Response response) throws IOException, URISyntaxException {
        String searchKey = request.queryParams(SEARCH_PARAM_KEY);
        //todo: exceptions for wrong parameter
        videoLinksByService = new HashMap<>();
        videoLinksByService.put("youtube", youTubeAPIService.getVideoFromYoutube(searchKey));
        //videoLinksByService.put("vimeo", vimeoAPIService.getVideosFromVimeo(searchKey));
        JSONObject json = new JSONObject();
        json.put("result", videoLinksByService);
        return videoLinksByService;
    }




//todo: method returns JSON - gets 2 hashmaps (youtube: *review (link) *unboxing (String link); vimeo (same))

    public String status(Request request, Response response) {
        return "ok";
    }

}
