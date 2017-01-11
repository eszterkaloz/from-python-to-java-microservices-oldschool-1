package com.codecool.micro_services.video_service;


import com.codecool.micro_services.video_service.vimeo_service.VimeoAPIService;
import com.codecool.micro_services.video_service.youtube_service.YouTubeAPIService;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;

import static spark.Spark.*;


public class Servlet {
    private static final Logger logger = LoggerFactory.getLogger(Servlet.class);
    private VideoAPIController controller;
    private static final int DEFAULT_PORT = 60000;

    public static void main(String[] args) {
        logger.debug("Starting " + Servlet.class.getName() + "...");

        Servlet application = new Servlet();

        application.controller = new VideoAPIController(YouTubeAPIService.getInstance(), VimeoAPIService.getInstance());

        // --- EXCEPTION HANDLING ---
        exception(URISyntaxException.class, (exception, request, response) -> {
            response.status(500);
            JSONObject errorContent = new JSONObject();
            errorContent.put("error_type", "URI building error, maybe wrong format?");
            errorContent.put("error_code", 500);
            errorContent.put("error_message", exception.getMessage());
            response.body(new JSONObject().put("error", errorContent).toString());
            logger.error("Error while processing request", exception);
        });

        exception(Exception.class, (exception, request, response) -> {
            response.status(500);
            JSONObject errorContent = new JSONObject();
            errorContent.put("error_type", "Unexpected error occurred");
            errorContent.put("error_code", 500);
            errorContent.put("error_message", exception.getMessage());
            response.body(new JSONObject().put("error", errorContent).toString());
            logger.error("Error while processing request", exception);
        });

        // --- SERVER SETUP ---
        staticFileLocation("/public");
        setup(args);

        // --- ROUTES ---
        get("/apivideos", application.controller::getVideoLinks);

    }

    private static void setup(String[] args) {
        if (args == null || args.length == 0) {
            port(DEFAULT_PORT);
            logger.info("VideoAPIServer started on port " + DEFAULT_PORT);
        } else {
            try {
                port(Integer.parseInt(args[0]));
                logger.info("VideoAPIServer started on port " + args[0]);
            } catch (NumberFormatException e) {
                logger.error("Invalid port given '{}', it should be number.", args[0]);
                System.exit(-1);
            }
        }
    }
}
