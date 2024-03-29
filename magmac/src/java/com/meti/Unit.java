package com.meti;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public record Unit(List<String> namespace) {
    public Unit() {
        this(Collections.emptyList());
    }

    String compile(String input) throws CompileException {
        var segments = input.split(";");
        var hasPackage = false;
        var builder = new StringBuilder();
        for (String segment : segments) {
            var result = compileSegment(segment);
            if (result.isPackage) {
                if (hasPackage) {
                    throw new CompileException("Input has too many packages!");
                } else {
                    hasPackage = true;
                }
            }

            result.output.ifPresent(builder::append);
        }

        return builder.toString();
    }

    Result compileSegment(String input) throws CompileException {
        if (input.isEmpty()) {
            return new Result(false, Optional.empty());
        } else if(input.startsWith("import ")) {
            var name = input.substring("import ".length()).strip();
            return new Result(false, Optional.of("import " + name + ";"));
        } else if (input.startsWith("package ")) {
            return new Result(compilePackage(input), Optional.empty());
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

    record Result(boolean isPackage, Optional<String> output) {
    }
}