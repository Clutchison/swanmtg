package com.hutchison.swanmtg.controller;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum Type {
    PING(1),
    UNKNOWN(-1);

    private final int intValue;
    private static final Map<Integer, Type> map;

    static {
        map = Arrays.stream(Type.values()).collect(Collectors.toMap(
                t -> t.intValue,
                t -> t
        ));
    }

    Type(Integer intValue) {
        this.intValue = intValue;
    }

    public static Type fromInt(int intValue) {
        Type foundType = map.get(intValue);
        return foundType == null ? UNKNOWN : foundType;
    }

}
