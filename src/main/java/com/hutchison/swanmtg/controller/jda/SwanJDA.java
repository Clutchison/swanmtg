package com.hutchison.swanmtg.controller.jda;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.security.auth.login.LoginException;
import java.util.Set;

import static net.dv8tion.jda.api.requests.GatewayIntent.*;
import static net.dv8tion.jda.api.utils.cache.CacheFlag.EMOTE;
import static net.dv8tion.jda.api.utils.cache.CacheFlag.VOICE_STATE;


@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SwanJDA {
    static final Set<GatewayIntent> INTENTS = Set.of(
            GUILD_MEMBERS,
            GUILD_WEBHOOKS,
            GUILD_INVITES,
            GUILD_MESSAGES,
            DIRECT_MESSAGES
    );
    static final Set<CacheFlag> DISABLED_CACHES = Set.of(
            VOICE_STATE,
            EMOTE
    );
    static final boolean jdaEnabled = false;

    final SwanListener listener;
    JDA jda;

    @Value("${DISCORD_BOT_TOKEN}")
    String botToken;


    @Autowired
    public SwanJDA(SwanListener swanListener) {
        this.listener = swanListener;
    }

    @PostConstruct
    private void startJDA() {
        try {
            if (jdaEnabled) {
                jda = JDABuilder.createDefault(botToken)
                                .setEnabledIntents(INTENTS)
                                .disableCache(DISABLED_CACHES)
                                .addEventListeners(listener)
                                .build();
            }
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}
