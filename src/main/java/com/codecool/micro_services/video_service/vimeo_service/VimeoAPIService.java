package com.codecool.micro_services.video_service.vimeo_service;


import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author      Oldschool
 * @version     1.0
 * @since       13/01/17
 */

public class VimeoAPIService {
    private static final Logger logger = LoggerFactory.getLogger(VimeoAPIService.class);
    private static final String API_URL = "https://api.vimeo.com/videos";
    private static final String AUTH_TOKEN = "Bearer 79c255eb2e3d0145bb91a26c824662d8";

    private static VimeoAPIService INSTANCE;

    /**
     * Implements the singleton design pattern
     *
     * @return instance of the class
     */
    public static VimeoAPIService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new VimeoAPIService();
        }
        return INSTANCE;
    }

    /**
     * Builds an URI with the given search key
     * and executes the request using the
     * Vimeo API service
     *
     * @param searchExpression used as a search string, not null
     * @return getLinkFromJSON(String jsonString)
     * @throws IOException input or output operation is failed or interpreted
     * @throws URISyntaxException URI building error
     */
    public String getVideoFromVimeo(String searchExpression) throws IOException, URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(API_URL);
        uriBuilder.addParameter("page", "1");  //optional
        uriBuilder.addParameter("per_page", "1"); //optional
        uriBuilder.addParameter("query", searchExpression);
        uriBuilder.addParameter("sort", "relevant");
        uriBuilder.addParameter("direction", "asc"); //optional
        logger.debug("URI prepared for request: {}", uriBuilder.toString());

        String response = execute(uriBuilder.build());
        logger.debug("HTTP response received: {}", response);

        logger.debug("Link from Vimeo response JSON: {}", getLinkFromJSON(response));

        return getLinkFromJSON(response);
    }

    /**
     * Executes the actual GET request against the given URI
     *
     * @param uri obj containing path and params.
     * @return response JSON as string
     * @throws IOException input or output operation is failed or interpreted
     */
    private String execute(URI uri) throws IOException {
        return Request.Get(uri)
                .addHeader("Authorization", AUTH_TOKEN)
                .execute()
                .returnContent()
                .asString();
    }

    /**
     * Parse JSON received from the API
     *
     * @param jsonString - JSON from the API
     * @return - embed code
     */
    private String getLinkFromJSON(String jsonString) {
        JSONObject json = new JSONObject(jsonString);
        JSONArray dataArray = (JSONArray) json.get("data");
        JSONObject video = (JSONObject) dataArray.get(0);
        JSONObject embed = (JSONObject) video.get("embed");
        return embed.get("html").toString();
    }


}

