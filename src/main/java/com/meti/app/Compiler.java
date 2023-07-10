package com.meti.app;

import com.meti.core.Ok;
import com.meti.core.Result;
import com.meti.java.JavaString;

public record Compiler(JavaString input) {
    Result<JavaString, CompileException> compile() {
        if (input.startsWith("import ")) {
            return Ok.apply(input);
        }
        return Ok.apply(JavaString.empty());
    }
}