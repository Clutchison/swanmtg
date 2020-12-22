package com.hutchison.swanmtg.controller.route;

import lombok.Value;
import net.dv8tion.jda.api.events.Event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.BiPredicate;
import java.util.regex.Pattern;

@Value
public class RouteMapping {

    Object router;
    Method method;
    BiPredicate<String, String> biPredicate;
    String routeValue;

    public RouteMapping(Object router, Method method) {
        this.router = router;
        Route route = method.getAnnotation(Route.class);
        this.method = method;
        if (!route.startsWith().equals("")) {
            this.routeValue = route.startsWith();
            this.biPredicate = String::startsWith;
        } else if (!route.contains().equals("")) {
            this.routeValue = route.contains();
            this.biPredicate = String::contains;
        } else if (!route.matches().equals("")) {
            this.routeValue = route.matches();
            this.biPredicate = (s1, s2) -> Pattern.matches(s2, s1);
        } else {
            throw new RuntimeException("Failed to create routeMapping");
        }
    }

    public boolean check(String s) {
        return biPredicate.test(s, routeValue);
    }

    public String invoke(String input, Event event) {
        try {
            return (String) method.invoke(router, input, event);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to invoke route." + e.getMessage());
        }
    }
}
