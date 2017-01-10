package com.codecool.micro_services.video_service.vimeo_service;


import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

// TODO: proper logging

public class VimeoAPIService {
    private static final Logger logger = LoggerFactory.getLogger(VimeoAPIService.class);
    private static final String API_URL = "https://api.vimeo.com/videos";
    private static final String AUTH_TOKEN = "Bearer 79c255eb2e3d0145bb91a26c824662d8";

    private static VimeoAPIService INSTANCE;

    public static VimeoAPIService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new VimeoAPIService();
        }
        return INSTANCE;
    }

    public Map<String, String> getVideosFromVimeo(String productName) throws IOException, URISyntaxException {
        String reviewLink = getVideoLinkFor(productName, "review");
        String unboxingLink = getVideoLinkFor(productName, "unboxing");

        Map<String, String> vimeoEmbedCodes = new HashMap<String, String>();
        vimeoEmbedCodes.put("review", reviewLink);
        vimeoEmbedCodes.put("unboxing", unboxingLink);

        return vimeoEmbedCodes;
    }

    private String getVideoLinkFor(String productName, String category) throws URISyntaxException, IOException {
        URIBuilder uriBuilder = new URIBuilder(API_URL);
        uriBuilder.addParameter("page", "1");  //optional
        uriBuilder.addParameter("per_page", "1"); //optional
        uriBuilder.addParameter("query", productName + " " + category);
        uriBuilder.addParameter("sort", "relevant");
        uriBuilder.addParameter("direction", "asc"); //optional

        String response = execute(uriBuilder.build());

        String link = getLinkFromJSON(response);

        return link;
    }

    private String execute(URI uri) throws IOException {
        return Request.Get(uri)
                .addHeader("Autorization", AUTH_TOKEN)
                .addHeader("Accept", "text/plain")
                .execute()
                .returnContent()
                .asString();
    }

    private String getLinkFromJSON(String jsonString) {
// FIXME: implement method
        return "https://vimeo.com/fubiztv/fubiztalks";
    }
}
