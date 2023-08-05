package com.meti.app.compile;

import com.meti.core.Ok;
import com.meti.core.Option;
import com.meti.core.Result;
import com.meti.java.JavaMap;
import com.meti.java.JavaString;
import com.meti.java.String_;

import static com.meti.java.JavaString.fromSlice;

public record Resolver(String_ type) implements Lexer {
    @Override
    public Option<Result<Node, CompileException>> lex() {
        return JavaMap.<String, String>empty()
                .insert("int", "I16")
                .insert("void", "Void")
                .applyOptionally(type().unwrap())
                .map(JavaString::fromSlice)
                .map(value -> new Content(fromSlice(""), value))
                .map(Ok::apply);
    }
}