package com.codecool.micro_services.video_service.vimeo_service;


import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class VimeoAPIService {
    private static final Logger logger = LoggerFactory.getLogger(VimeoAPIService.class);
    private static final String API_URL = "";
    private static final String API_KEY = "";
    private static VimeoAPIService INSTANCE;

    public static VimeoAPIService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new VimeoAPIService();
        }
        return INSTANCE;
    }

    public String getVideos(String productName) throws IOException, URISyntaxException {
        URIBuilder builder = new URIBuilder(API_URL);
        logger.debug("Getting videos for the following product: {}", productName);
        logger.debug("The built uri is {}", builder);

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
