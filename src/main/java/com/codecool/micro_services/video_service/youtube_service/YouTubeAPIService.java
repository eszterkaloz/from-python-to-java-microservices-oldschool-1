package com.codecool.micro_services.video_service.youtube_service;


import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


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

    public String getVideoFromYoutube(String productName) throws IOException, URISyntaxException {
        logger.info("Getting a video from Youtube api");
        URIBuilder builder = new URIBuilder(API_URL);

        builder.addParameter(PART_PARAM_KEY, "snippet");
        builder.addParameter(TYPE_PARAM_KEY, "video");

        if (!StringUtils.isEmpty(productName)) {
            builder.addParameter(QUERY_PARAM_KEY, productName);
        }

        builder.addParameter(MAX_RESULTS_PARAM_KEY, "1");
        builder.addParameter(API_PARAM_KEY, "AIzaSyBXTrCzn2_gkCKRjlGEt_x4K5ZwlZJ2kAQ");

        logger.debug("Getting videos from Youtube for the following product: {}", productName);
        logger.debug("The built uri for the youtube api is {}", builder);

        return execute(builder.build());
    }

    private String execute(URI uri) throws IOException {
        return Request.Get(uri)
                .addHeader("Accept", "text/plain")
                .execute()
                .returnContent()
                .asString();
    }
}
