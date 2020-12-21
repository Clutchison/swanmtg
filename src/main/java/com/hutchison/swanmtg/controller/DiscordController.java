package com.hutchison.swanmtg.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// This is purely for testing at this point. Use bot!
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/disc")
public class DiscordController {

    @GetMapping
    public String ping() {
        return "pong!";
    }
}
