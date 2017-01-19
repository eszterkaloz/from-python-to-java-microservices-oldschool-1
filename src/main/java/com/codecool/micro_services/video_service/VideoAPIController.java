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

/**
 * @author      Oldschool
 * @version     1.0
 * @since       13/01/17
 */

public class VideoAPIController {
    /**
     * Request parameter key used for searching on the video sharing sites
     */
    public static final String SEARCH_PARAM_KEY = "search";
    /**
     * Video categories used as filters when searching
     */
    public static final List<String> VIDEO_CATEGORIES = Arrays.asList("unboxing", "review");
    private final YouTubeAPIService youTubeAPIService;
    private final VimeoAPIService vimeoAPIService;
    private List<String> videos;

    /**
     * @param youTubeAPIService youtubeAPIService instance, not null
     * @param vimeoAPIService vimeoAPIService instance, not null
     */
    public VideoAPIController(YouTubeAPIService youTubeAPIService, VimeoAPIService vimeoAPIService) {
        this.youTubeAPIService = youTubeAPIService;
        this.vimeoAPIService = vimeoAPIService;
    }

    /**
     * Validates the incoming request parameter
     * (if param is shorter than or equal to 2,
     * it exits with a JSONObject
     * containing an error message),
     * calls service methods responsible for
     * the actual requests (for both categories)
     * and parses the response
     *
     * @param request Spark request
     * @param response Spark response
     * @return Stringified JSON response
     * @throws IOException input or output operation is failed or interpreted
     * @throws URISyntaxException URI building error
     */
    public String getVideoLinks(Request request, Response response) throws IOException, URISyntaxException {
        String searchKey = request.queryParams(SEARCH_PARAM_KEY);
        Map<String, List<String>> result = new HashMap<>();
        response.type("application/json");
        if (searchKey.length() <= 2) {
            response.status(400);
            return new JSONObject()
                    .put("error_type", "Bad request. Request parameter is missing or too short?")
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

    /**
     * Generates a JSON string from the
     * given parameters
     *
     * @param title not null
     * @param embedCode not null
     * @param provider not null
     * @param category not null
     * @return String version of a JSON Object
     */
    private String responseBuilder(String title, String embedCode, String provider, String category) {
        return new JSONObject()
                .put("key", title)
                .put("embed code", embedCode)
                .put("provider", provider)
                .put("category", category).toString();
    }


}
