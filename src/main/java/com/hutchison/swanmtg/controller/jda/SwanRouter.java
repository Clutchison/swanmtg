package com.hutchison.swanmtg.controller.jda;

import com.hutchison.swanmtg.controller.route.Route;
import com.hutchison.swanmtg.controller.route.Router;
import com.hutchison.swanmtg.service.SwanService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

@Router
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SwanRouter {

    final SwanService service;

    @Autowired
    public SwanRouter(SwanService service) {
        this.service = service;
    }

    @Route(startsWith = "!leaderboard")
    public String test(@NotNull String input) {
        List<String> args = getArgs(input);
        if (args.size() < 1) {

        }
        throw new UnsupportedOperationException();
    }

    @Route(startsWith = "!signup")
    public String signup(@NotNull String input, @NotNull MessageReceivedEvent event) {
        return "notimplemented";
    }

    private static List<String> getArgs(@NotNull String s) {
        List<String> strings = Arrays.asList(s.split("\\s"));
        return strings.subList(1, strings.size() - 1);
    }

}
