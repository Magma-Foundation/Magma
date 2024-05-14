package com.meti.compile;

import com.meti.api.result.Err;
import com.meti.api.result.Ok;
import com.meti.api.result.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record ParamsCompiler(String paramString, List<String> stack) {

    Result<String, CompileException> compile() {
        var paramStrings = splitParams();
        var outputParams = Optional.<StringBuilder>empty();
        for (String string : paramStrings) {
            if (string.isBlank()) continue;

            var strippedParam = string.strip();
            var separator = strippedParam.lastIndexOf(' ');
            if (separator == -1) {
                return new Err<>(new CompileException("Malformed parameter: " + strippedParam));
            }

            var type = strippedParam.substring(0, separator);
            var name = strippedParam.substring(separator + 1);
            stack.add(name);

            String compiledType;
            try {
                final TypeCompiler typeCompiler = new TypeCompiler(type);
                compiledType = TypeCompiler.compile(typeCompiler.inputType).$();
            } catch (CompileException e) {
                return new Err<>(e);
            }

            var next = name + " : " + compiledType;
            outputParams = Optional.of(outputParams.map(value -> value.append(", ").append(next))
                    .orElse(new StringBuilder(next)));
        }

        return new Ok<>(outputParams.orElse(new StringBuilder()).toString());
    }

    private List<String> splitParams() {
        var list = new ArrayList<String>();
        var builder = new StringBuilder();
        var depth = 0;
        for (int i = 0; i < paramString.length(); i++) {
            var c = paramString.charAt(i);
            if (c == ',' && depth == 0) {
                list.add(builder.toString());
                builder = new StringBuilder();
            } else {
                if (c == '<') depth++;
                if (c == '>') depth--;
                builder.append(c);
            }
        }
        list.add(builder.toString());
        return list;
    }
}