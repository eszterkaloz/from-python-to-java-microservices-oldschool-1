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
//    todo: method that gets a product name as a query param --> 2 methods (one that calls youtubeapi, the other calls vimeo)


//todo: method returns JSON - gets 2 hashmaps (youtube: *review (link) *unboxing (String link); vimeo (same))

    public String status(Request request, Response response) {
        return "ok";
    }

}
