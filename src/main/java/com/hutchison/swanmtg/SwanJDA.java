package com.hutchison.swanmtg;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.security.auth.login.LoginException;

@Component
public class SwanJDA {

    @Value("${DISCORD_BOT_TOKEN}")
    private String botToken;
    JDA jda;
//
//    public SwanJDA() {
//        start();
//    }

    @PostConstruct
    private void start() {
        try {
            jda = JDABuilder.createDefault(botToken)
                    .build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}
