package com.rrodriguez.glide.bigcorp.controller.validation;

import com.rrodriguez.glide.bigcorp.converter.Transformation;
import com.rrodriguez.glide.bigcorp.exception.InvalidExpansionDependencyException;
import com.rrodriguez.glide.bigcorp.exception.InvalidExpansionException;
import com.rrodriguez.glide.bigcorp.exception.InvalidExpansionTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InputValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(InputValidator.class);

    public static List<Transformation> validateAndParseExpansions(String root, List<String> rawExpansions) throws InvalidExpansionException {

        List<Transformation> transformations = new ArrayList<>();
        if (rawExpansions != null) {
            List<String> rawExpansionsAppendedRoot = rawExpansions.stream().map(p -> root.concat("." + p)).collect(Collectors.toList());

            for (String rawExpansion : rawExpansionsAppendedRoot) {
                List<String> expansions = parseExpansion(rawExpansion);

                for (int i = 0; i < expansions.size() - 1; i++) {
                    String from = expansions.get(i);
                    String to = expansions.get(i + 1);
                    if (!Expansions.validateTransformation(from, to)){
                        LOGGER.warn("Failed to validate expand arguments for dependency: {}->{}",from,to);
                        throw new InvalidExpansionDependencyException(String.format("Root %s cant expand to %s", from, to));
                    }

                    Expansions expansion = Expansions.getExpansion(from, to);
                    if (i > transformations.size() - 1) {
                        transformations.add(new Transformation(expansion));
                    } else {
                        transformations.get(i).addExpansion(expansion);
                    }
                }
            }
        }

        return transformations;

    }

    private static List<String> parseExpansion(String expansion) throws InvalidExpansionTypeException {
        List<String> parsedExpansions = Arrays.asList(expansion.split("\\."));
        for (String expansionType : parsedExpansions) {
            if (!Type.exists(expansionType)) {
                LOGGER.warn("Unknown type '{}'",expansionType);
                throw new InvalidExpansionTypeException(String.format("Type '%s' does not exist as an expansion type", expansionType));
            }
        }
        return parsedExpansions;
    }


}
