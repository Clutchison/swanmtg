package com.hutchison.swanmtg.controller.jda;

import com.hutchison.swanmtg.controller.route.Route;
import com.hutchison.swanmtg.controller.route.Router;
import org.springframework.stereotype.Component;

@Router
@Component
public class SwanRouter {

    @Route(startsWith = "!test")
    public String test(String string) {
        return "Test!";
    }

    @Route(startsWith = "!another")
    public String anotherTest(String string) {
        return "Another Test!";
    }
}
