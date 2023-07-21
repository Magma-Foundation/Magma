package com.meti.app;

import com.meti.core.Ok;
import com.meti.core.Result;
import com.meti.java.JavaString;
import com.meti.java.String_;

public record Compiler(String_ input) {
    Result<String_, CompileException> compile() {
        var output = input.split(";")
                .filter(line -> {
                    return !line.strip().startsWith("package ");
                })
                .filter(line -> !line.strip().isEmpty())
                .collect(JavaString.joining(JavaString.from(";")))
                .unwrapOrElse(JavaString.Empty);

        return Ok.apply(output);
    }
}