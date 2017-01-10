package com.codecool.micro_services.video_service.youtube_service;


import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class YouTubeAPIService {
    private static final Logger logger = LoggerFactory.getLogger(YouTubeAPIService.class);
    private static final String API_URL = "";
    private static final String API_KEY = "";
    private static YouTubeAPIService INSTANCE;

    public static YouTubeAPIService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new YouTubeAPIService();
        }
        return INSTANCE;
    }

    public String getVideosFromYoutube(String productName) throws IOException, URISyntaxException {
        URIBuilder builder = new URIBuilder(API_URL);
        logger.debug("Getting videos from Youtube for the following product: {}", productName);
        logger.debug("The built uri for the youtube api is {}", builder);

        return execute(builder.build());
    }

    private String execute(URI uri) throws IOException {
        return Request.Get(uri)
                .addHeader("", API_KEY)
                .addHeader("Accept", "text/plain")
                .execute()
                .returnContent()
                .asString();
    }
}
