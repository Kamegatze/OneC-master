package com.clone.OneC.generate_code.gnerate_method_controller;

public class FactoryCreateMethod {
    public CreateMethod createTo(TypeMethod type) {
        return switch (type) {
            case GET -> new CreateGet();
            case POST -> new CreatePost();
            case PUT -> new CreatePut();
            case PATCH -> new CreatePatch();
            default -> new CreateDelete();
        };
    }
}
