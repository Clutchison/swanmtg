package com.hutchison.swanmtg.controller.route;

import lombok.Value;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Value
public class RouteMappings {

    Set<RouteMapping> routeMappings;

    public RouteMappings(@NotNull String basePackage) {
        this.routeMappings = getRouters(basePackage).stream()
                .flatMap(router -> Arrays.stream(router.getMethods()))
                .filter(method -> method.getAnnotation(Route.class) != null)
                .map(RouteMapping::new)
                .collect(Collectors.toSet());
    }

    private Set<Class> getRouters(String basePackage) {
        BeanDefinitionRegistry bdr = new SimpleBeanDefinitionRegistry();
        ClassPathBeanDefinitionScanner s = new ClassPathBeanDefinitionScanner(bdr);
        TypeFilter tf = new AnnotationTypeFilter(Router.class);
        s.resetFilters(false);
        s.setIncludeAnnotationConfig(false);
        s.addIncludeFilter(tf);
        s.scan(basePackage);
        return Arrays.stream(bdr.getBeanDefinitionNames())
                .map(cn -> {
                    try {
                        return Class.forName(bdr.getBeanDefinition(cn).getBeanClassName());
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException("Error finding class from name: " + cn);
                    }
                })
                .collect(Collectors.toSet());
    }
}
