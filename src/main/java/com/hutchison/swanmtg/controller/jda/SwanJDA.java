package com.hutchison.swanmtg.controller.jda;

import com.hutchison.swanmtg.controller.route.RouteMappings;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;

import static net.dv8tion.jda.api.requests.GatewayIntent.*;


@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SwanJDA {
    static final Set<GatewayIntent> INTENTS = Set.of(
            GUILD_MEMBERS,
            GUILD_WEBHOOKS,
            GUILD_INVITES,
            GUILD_MESSAGES, // TODO: Remove
            DIRECT_MESSAGES
    );
    static final Set<CacheFlag> DISABLED_CACHES = Set.of(
            CacheFlag.VOICE_STATE,
            CacheFlag.EMOTE
    );

    final SwanListener listener;

    @Value("${DISCORD_BOT_TOKEN}")
    String botToken;
    JDA jda;

    public SwanJDA() {
        listener = new SwanListener();
    }

    @PostConstruct
    private void startJDA() {
//        try {
//            jda = JDABuilder.createDefault(botToken)
//                    .setEnabledIntents(INTENTS)
//                    .disableCache(DISABLED_CACHES)
//                    .addEventListeners(listener)
//                    .build();
//        } catch (LoginException e) {
//            e.printStackTrace();
//        }
    }
}
