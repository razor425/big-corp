package com.rrodriguez.glide.bigcorp.controller.validation;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum Type {
    EMPLOYEE("employee"),
    MANAGER("manager", "employee"),
    DEPARTMENT("department"),
    SUPERDEPARTMENT("superdepartment", "department"),
    OFFICE("office");

    private String type;
    private String primitive;
    static Map<String, Type> mapper = initMapper();

    private static Map<String, Type> initMapper() {
        return Arrays.stream(Type.values())
                .collect(Collectors.toMap(Type::getTypeValue, type -> type));
    }

    Type(String type) {
        this.type = type;
        this.primitive = type;
    }

    Type(String type, String primitive) {
        this.type = type;
        this.primitive = primitive;
    }

    public static Type getAsPrimitiveType(String stringType) {
        return getAsType(getAsType(stringType).getPrimitiveValue());
    }

    public String getTypeValue() {
        return this.type;
    }

    public String getPrimitiveValue() {
        return this.primitive;
    }

    public static Type getAsType(String stringValue) {
        return mapper.get(stringValue);
    }

    public static boolean exists(String stringValue) {
        return mapper.containsKey(stringValue);
    }
}
