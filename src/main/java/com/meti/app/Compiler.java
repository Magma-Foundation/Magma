package com.meti.app;

import com.meti.core.Ok;
import com.meti.core.Result;
import com.meti.java.String_;

public record Compiler(String_ input) {
    Result<String_, CompileException> compile() {
        return Ok.apply(input);
    }
}