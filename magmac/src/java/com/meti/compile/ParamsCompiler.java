package com.meti.compile;

import java.util.List;
import java.util.Optional;

public record ParamsCompiler(String paramString) {
    String compile() {
        var paramStrings = List.of(paramString().split(","));
        var outputParams = Optional.<StringBuilder>empty();
        for (String string : paramStrings) {
            if (string.isBlank()) continue;

            var strippedParam = string.strip();
            var separator = strippedParam.lastIndexOf(' ');
            var type = strippedParam.substring(0, separator);
            var name = strippedParam.substring(separator + 1);

            var next = name + " : " + type;
            outputParams = Optional.of(outputParams.map(value -> value.append(", ").append(next))
                    .orElse(new StringBuilder(next)));
        }

        return outputParams.orElse(new StringBuilder()).toString();
    }
}