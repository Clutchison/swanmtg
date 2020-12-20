package com.hutchison.swanmtg.controller;

import com.hutchison.swanmtg.model.DiscordRequest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/disc")
public class DiscordController {

    @PostMapping()
    public String post(@RequestBody DiscordRequest request) {
        switch (Type.fromInt(request.getType())) {
            case PING:
                return ping();
            default:
                return "Type: " + request + " is not implemented.";
        }
    }

    public String ping() {
       return "pong!";
    }
}
