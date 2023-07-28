package com.meti.app.compile;

import com.meti.core.Err;
import com.meti.core.Ok;
import com.meti.core.Result;
import com.meti.java.JavaMap;
import com.meti.java.JavaString;
import com.meti.java.String_;

public record Resolver(String_ type) {
    public Result<String_, CompileException> resolve() {
        return JavaMap.<String, String>empty()
                .insert("int", "I16")
                .insert("void", "Void")
                .applyOptionally(type().unwrap())
                .map(JavaString::fromSlice)
                .map(Ok::<String_, CompileException>apply)
                .unwrapOrElse(Err.apply(new CompileException("Unknown type: " + type().unwrap())));
    }
}