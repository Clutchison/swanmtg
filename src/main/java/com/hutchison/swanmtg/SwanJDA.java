package com.hutchison.swanmtg;

import org.springframework.stereotype.Service;

@Service
public class SwanJDA {

    public SwanJDA() {
        start();
    }

    private void start() {
        System.out.println("Hello World!");
    }
}
