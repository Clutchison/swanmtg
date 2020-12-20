package com.hutchison.swanmtg.controller.jda;

import com.hutchison.swanmtg.controller.route.Route;
import com.hutchison.swanmtg.controller.route.Router;
import org.springframework.stereotype.Component;

@Router
@Component
public class SwanRouter {

    @Route(startsWith = "test")
    public void test() {

    }

    @Route(startsWith = "test")
    public void anotherTest() {

    }
}
