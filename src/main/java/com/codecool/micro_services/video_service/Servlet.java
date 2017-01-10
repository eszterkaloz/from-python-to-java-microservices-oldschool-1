package com.codecool.micro_services.video_service;


import com.codecool.micro_services.video_service.vimeo_service.VimeoAPIService;
import com.codecool.micro_services.video_service.youtube_service.YouTubeAPIService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.resourceresolver.ClassLoaderResourceResolver;
import org.thymeleaf.templateresolver.TemplateResolver;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import java.net.URISyntaxException;

import static spark.Spark.*;


public class Servlet {
    private static final Logger logger = LoggerFactory.getLogger(Servlet.class);
    private VideoAPIController controller;
    private static final int PORT = 9000;

    public static void main(String[] args) {
        logger.debug("Starting " + Servlet.class.getName() + "...");

        Servlet application = new Servlet();

        application.controller = new VideoAPIController(YouTubeAPIService.getInstance(), VimeoAPIService.getInstance());

        // --- EXCEPTION HANDLING ---
        exception(URISyntaxException.class, (exception, request, response) -> {
            response.status(500);
            response.body(String.format("URI building error, maybe wrong format? : %s", exception.getMessage()));
            logger.error("Error while processing request", exception);
        });

        exception(Exception.class, (exception, request, response) -> {
            response.status(500);
            response.body(String.format("Unexpected error occurred: %s", exception.getMessage()));
            logger.error("Error while processing request", exception);
        });

        // --- SERVER SETUP ---
        staticFileLocation("/public");
        port(PORT);

        // --- TEMPLATE ENGINE ---
        TemplateResolver templateResolver = new TemplateResolver();
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setPrefix("templates/payment/");
        templateResolver.setSuffix(".html");
        templateResolver.setCacheTTLMs(3600000L);
        templateResolver.setResourceResolver(new ClassLoaderResourceResolver());

        // --- ROUTES ---
        //get("/status", application.controller::status);

        //todo: define routes for videoapicontroller's methods


        logger.info("VideoAPIServer started on port " + PORT);
    }
}
