package com.codecool.micro_services.video_service;


import spark.Request;
import spark.Response;

public class VideoAPIController {

    private static VideoAPIController INSTANCE;

    public static VideoAPIController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new VideoAPIController();
        }
        return INSTANCE;
    }

    public String status(Request request, Response response) {
        return "ok";
    }

}
