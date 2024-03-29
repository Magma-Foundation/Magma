package com.meti;

import java.util.Arrays;
import java.util.List;

public record Unit(List<String> namespace) {
    String compile(String input) throws CompileException {
        var segments = input.split(";");
        var hasPackage = false;
        for (String segment : segments) {
            var aPackage = isPackage(segment);
            if (aPackage) {
                if (hasPackage) {
                    throw new CompileException("Input has too many packages!");
                } else {
                    hasPackage = true;
                }
            }
        }

        return "";
    }

    boolean isPackage(String input) throws CompileException {
        if (input.isEmpty()) {
            return false;
        } else if (input.startsWith("package ")) {
            return compilePackage(input);
        } else {
            var format = "Unknown content: '%s'.";
            var message = format.formatted(input);
            throw new CompileException(message);
        }
    }

    boolean compilePackage(String input) throws CompileException {
        var segments = input.substring("package ".length())
                .strip()
                .split("\\.");

        var expectedNamespace = Arrays.asList(segments);
        if (namespace().equals(expectedNamespace)) {
            return true;
        } else {
            var format = "Expected a namespace of '%s' but was actually '%s'.";
            var message = format.formatted(expectedNamespace, namespace());
            throw new CompileException(message);
        }
    }
}