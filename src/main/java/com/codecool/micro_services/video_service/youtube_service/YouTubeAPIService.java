package com.codecool.micro_services.video_service.youtube_service;


import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class YouTubeAPIService {
    private static final Logger logger = LoggerFactory.getLogger(YouTubeAPIService.class);
    private static final String API_URL = "https://www.googleapis.com/youtube/v3/search";
    private static final String PART_PARAM_KEY = "part";
    private static final String TYPE_PARAM_KEY = "type";
    private static final String QUERY_PARAM_KEY = "q";
    private static final String MAX_RESULTS_PARAM_KEY = "maxResults";
    private static final String API_PARAM_KEY = "key";

    private static YouTubeAPIService INSTANCE;

    public static YouTubeAPIService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new YouTubeAPIService();
        }
        return INSTANCE;
    }

    public Map<String, String> getVideoFromYoutube(String productName) throws IOException, URISyntaxException {
        logger.info("Getting a video from Youtube api");
        URIBuilder builder = new URIBuilder(API_URL);

        builder.addParameter(PART_PARAM_KEY, "snippet");
        builder.addParameter(TYPE_PARAM_KEY, "video");

        if (!StringUtils.isEmpty(productName)) {
            builder.addParameter(QUERY_PARAM_KEY, productName );
        }

        builder.addParameter(MAX_RESULTS_PARAM_KEY, "1");
        builder.addParameter(API_PARAM_KEY, "AIzaSyBXTrCzn2_gkCKRjlGEt_x4K5ZwlZJ2kAQ");

        logger.debug("Getting videos from Youtube for the following product: {}", productName);
        logger.debug("The built uri for the youtube api is {}", builder);

        return getVideoFromYoutubeJSONParser(builder.build());
    }

    private Map<String, String> getVideoFromYoutubeJSONParser(URI uri) throws IOException, URISyntaxException{
        String result = null;
        JSONArray items = new JSONObject(execute(uri)).getJSONArray("items");

        try {
            for(int i = 0 ; i < items.length() ; i++){
                JSONObject id = ((JSONObject)items.get(i)).getJSONObject("id");
                result = id.getString("videoId");
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
            logger.error("JSONException found, there might not be a video linked to this search word");
        }

        Map<String, String> videosByCategory = new HashMap<>();
        videosByCategory.put("youtube", result);
        return videosByCategory;
    }

    private String execute(URI uri) throws IOException {
        return Request.Get(uri)
                .addHeader("Accept", "text/plain")
                .execute()
                .returnContent()
                .asString();
    }

}
