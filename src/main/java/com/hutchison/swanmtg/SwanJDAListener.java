package com.hutchison.swanmtg;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.security.auth.login.LoginException;
import java.util.Set;

import static net.dv8tion.jda.api.requests.GatewayIntent.*;

@Component
public class SwanJDAListener extends ListenerAdapter implements EventListener {

    @Value("${DISCORD_BOT_TOKEN}")
    private String botToken;
    private JDA jda;
    private final Set<GatewayIntent> INTENTS = Set.of(
            GUILD_MEMBERS,
            GUILD_WEBHOOKS,
            GUILD_INVITES
    );


    @PostConstruct
    private void startJDA() {
        try {
            jda = JDABuilder.createDefault(botToken)
                    .setEnabledIntents(INTENTS)
                    .build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
//        if (event.getAuthor().isBot()) return;
        String contentRaw = event.getMessage().getContentRaw();
        System.out.println("MESSAGE RECEIVED: \n" + contentRaw);
//        log.debug("Received " + contentRaw);
//        routeMappings.stream()
//                .filter(mapping -> mapping.check(contentRaw))
//                .findFirst()
//                .ifPresent(value -> send(event, contentRaw, value));
    }
}
