package com.codecool.micro_services.video_service;


import com.codecool.micro_services.video_service.vimeo_service.VimeoAPIService;
import com.codecool.micro_services.video_service.youtube_service.YouTubeAPIService;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;

import static spark.Spark.*;

/**
 * @author      Oldschool
 * @version     1.0
 * @since       13/01/17
 */

public class Servlet {
    private static final Logger logger = LoggerFactory.getLogger(Servlet.class);
    private VideoAPIController controller;
    private static final int DEFAULT_PORT = 60000;

    /**
     *
     * @param args will be used for setting up the port,
     *             if omitted, the system defaults to 60000
     */
    public static void main(String[] args) {
        logger.debug("Starting " + Servlet.class.getName() + "...");

        Servlet application = new Servlet();

        application.controller = new VideoAPIController(YouTubeAPIService.getInstance(), VimeoAPIService.getInstance());

        // --- EXCEPTION HANDLING ---
        exception(URISyntaxException.class, (exception, request, response) -> {
            response.status(500);
            JSONObject errorContent = new JSONObject()
                    .put("error_type", "URI building error, maybe wrong format?")
                    .put("error_code", 500)
                    .put("error_message", exception.getMessage());
            response.body(new JSONObject().put("error", errorContent).toString());
            logger.error("Error while processing request", exception);
        });

        exception(Exception.class, (exception, request, response) -> {
            response.status(500);
            JSONObject errorContent = new JSONObject()
                    .put("error_type", "Unexpected error occurred")
                    .put("error_code", 500)
                    .put("error_message", exception.getMessage());
            response.body(new JSONObject().put("error", errorContent).toString());
            logger.error("Error while processing request", exception);
        });

        // --- SERVER SETUP ---
        staticFileLocation("/public");
        setup(args);

        // --- ROUTES ---
        get("/apivideos", application.controller::getVideoLinks);

    }

    /**
     * Setting up port
     * @param args app args
     */
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
