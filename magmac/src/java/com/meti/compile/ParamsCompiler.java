package com.meti.compile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record ParamsCompiler(String paramString) {
    String compile() throws CompileException {
        var paramStrings = splitParams();
        var outputParams = Optional.<StringBuilder>empty();
        for (String string : paramStrings) {
            if (string.isBlank()) continue;

            var strippedParam = string.strip();
            var separator = strippedParam.lastIndexOf(' ');
            if (separator == -1) {
                throw new CompileException("Malformed parameter: " + strippedParam);
            }

            var type = strippedParam.substring(0, separator);
            var name = strippedParam.substring(separator + 1);

            var next = name + " : " + type;
            outputParams = Optional.of(outputParams.map(value -> value.append(", ").append(next))
                    .orElse(new StringBuilder(next)));
        }

        return outputParams.orElse(new StringBuilder()).toString();
    }

    private List<String> splitParams() {
        var list = new ArrayList<String>();
        var builder = new StringBuilder();
        var depth =0 ;
        for (int i = 0; i < paramString.length(); i++) {
            var c = paramString.charAt(i);
            if(c == ',' && depth == 0) {
                list.add(builder.toString());
                builder = new StringBuilder();
            } else {
                if(c == '<') depth++;
                if(c == '>') depth--;
                builder.append(c);
            }
        }
        list.add(builder.toString());
        return list;
    }
}