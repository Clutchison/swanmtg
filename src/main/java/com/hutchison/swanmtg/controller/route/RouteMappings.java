package com.hutchison.swanmtg.controller.route;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.dv8tion.jda.api.events.Event;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class RouteMappings implements ApplicationContextAware {

    Set<RouteMapping> routeMappings;
    ApplicationContext applicationContext;
    static final String basePackage = "com.hutchison.swanmtg.controller";


    @PostConstruct
    public void init() {
        this.routeMappings = getRouters().stream()
                .flatMap(router ->
                        Arrays.stream(router.getClass().getMethods())
                                .filter(method -> method.getAnnotation(Route.class) != null)
                                .map(method -> new RouteMapping(router, method)))
                .collect(Collectors.toSet());
    }

    private Set<Object> getRouters() {
        BeanDefinitionRegistry bdr = new SimpleBeanDefinitionRegistry();
        ClassPathBeanDefinitionScanner s = new ClassPathBeanDefinitionScanner(bdr);
        TypeFilter tf = new AnnotationTypeFilter(Router.class);
        s.resetFilters(false);
        s.setIncludeAnnotationConfig(false);
        s.addIncludeFilter(tf);
        s.scan(basePackage);
        return Arrays.stream(bdr.getBeanDefinitionNames())
                .map(cn -> applicationContext.getBean(cn))
                .collect(Collectors.toSet());
    }

    public String route(String input, Event event) {
        Optional<RouteMapping> mapping = routeMappings.stream()
                .filter(rm -> rm.check(input))
                .findFirst();
        return mapping.isPresent() ? mapping.get().route(input, event) : "";
    }

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
