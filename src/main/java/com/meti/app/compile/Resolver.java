package com.meti.app.compile;

import com.meti.core.Ok;
import com.meti.core.Option;
import com.meti.core.Result;
import com.meti.java.JavaMap;
import com.meti.java.JavaString;
import com.meti.java.Map;
import com.meti.java.String_;

public record Resolver(String_ value) implements Lexer {
    private static Map<String, String> createMap() {
        return JavaMap.<String, String>empty()
                .insert("int", "I16")
                .insert("void", "Void")
                .insert("boolean", "Bool");
    }

    @Override
    public Option<Result<Node, CompileException>> lex() {
        return createMap()
                .applyOptionally(value.unwrap())
                .map(JavaString::fromSlice)
                .map(Content::ofContent)
                .map(Ok::apply);
    }
}