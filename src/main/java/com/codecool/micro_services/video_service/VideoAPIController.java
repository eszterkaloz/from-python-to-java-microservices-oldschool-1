package com.codecool.micro_services.video_service;


public class VideoAPIController {

    private static VideoAPIController INSTANCE;

    public static VideoAPIController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new VideoAPIController();
        }
        return INSTANCE;
    }
}
