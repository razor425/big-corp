package com.rrodriguez.glide.bigcorp.controller.validation;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.*;
import java.util.stream.Collectors;

public enum Expansions {

    EMPLOYEE_DEPARTMENT(Type.EMPLOYEE, Type.DEPARTMENT),
    EMPLOYEE_OFFICE(Type.EMPLOYEE, Type.OFFICE),
    EMPLOYEE_MANAGER(Type.EMPLOYEE, Type.MANAGER),
    DEPARTMENT_SUPERDEPARTMENT(Type.DEPARTMENT, Type.SUPERDEPARTMENT);

    private Type from;
    private Type to;

    static Multimap<Type, Type> dependencies = initDependenciesMap();
    static Map<Type, Multimap<Type, Type>> reach = new HashMap<>();

    private static Multimap<Type, Type> initDependenciesMap() {
        Multimap<Type, Type> multimap = ArrayListMultimap.create();
        Arrays.stream(Expansions.values()).forEach(p -> multimap.put(p.from, p.to));
        return multimap;
    }

    Expansions(Type from, Type to) {
        this.from = from;
        this.to = to;
    }

    public Type getTypeFrom() {
        return this.from;
    }

    public Type getTypeTo() {
        return this.to;
    }

    public boolean isExpansion(Type from, Type to) {
        return this.from == from && this.to == to;
    }

    public static boolean validateTransformation(String from, String to) {
        Type fromType = Type.getAsPrimitiveType(from);
        Type toType = Type.getAsType(to);

        return getReach(fromType).containsEntry(fromType, toType);
    }

    public static Expansions getExpansion(String from, String to) {
        Type fromType = Type.getAsPrimitiveType(from);
        Type toType = Type.getAsType(to);

        return Arrays.stream(values()).filter(e -> e.isExpansion(fromType, toType)).collect(Collectors.toList()).get(0);
    }

    public static Multimap<Type, Type> getReach(Type baseType) {
        if (!reach.containsKey(baseType)) {
            List<Type> types = new ArrayList<>();
            types.add(baseType);
            Multimap<Type, Type> result = ArrayListMultimap.create();

            while (!types.isEmpty()) {
                List<Type> listReach = new ArrayList<>();
                for (Type t : types) {
                    for (Type v : dependencies.get(t)) {
                        listReach.add(Type.getAsType(v.getPrimitiveValue()));
                        if (!result.containsEntry(t, v))
                            result.put(t, v);
                    }
                    types = listReach;

                }

                reach.put(baseType, result);
            }
        }
        return reach.get(baseType);
    }
}
