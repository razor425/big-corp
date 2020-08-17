package com.rrodriguez.glide.bigcorp.converter;

import com.rrodriguez.glide.bigcorp.controller.validation.Expansions;

import java.util.HashSet;
import java.util.Set;

public class Transformation {

    private Set<Expansions> expansions;

    public Transformation(Expansions e) {
        expansions = new HashSet<>();
        expansions.add(e);
    }

    public void addExpansion(Expansions e) {
        expansions.add(e);
    }

    public Set<Expansions> getExpansions() {
        return this.expansions;
    }

}
