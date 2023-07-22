package com.meti.app.compile;

import com.meti.app.compile.block.BlockLexer;
import com.meti.app.compile.clazz.ClassLexer;
import com.meti.app.compile.clazz.Class_;
import com.meti.app.compile.declare.DeclarationLexer;
import com.meti.app.compile.function.FunctionLexer;
import com.meti.app.compile.imports.ImportLexer;
import com.meti.core.Ok;
import com.meti.core.Result;
import com.meti.java.JavaMap;
import com.meti.java.JavaString;
import com.meti.java.Objects;
import com.meti.java.String_;

import static com.meti.java.JavaString.Empty;
import static com.meti.java.JavaString.joining;

public record Compiler(String_ input) {

    public static String_ resolveType(String_ type) {
        return JavaMap.<String, String>empty()
                .insert("int", "I16")
                .insert("void", "Void")
                .applyOptionally(type.unwrap())
                .map(JavaString::fromSlice)
                .unwrapOrElse(type);
    }

    public Result<String_, CompileException> compile() {
        var output = new Splitter(input).split()
                .filter(line -> !line.strip().startsWith("package "))
                .map(line1 -> compileNode(line1).render())
                .collect(joining(Empty))
                .unwrapOrElse(Empty);

        return Ok.apply(output);
    }

    private Node compileNode(String_ line) {
        System.out.println(line.unwrap());
        return new ImportLexer(line).lex()
                .or(new ClassLexer(line).lex()
                        .flatMap(value -> Objects.cast(Class_.class, value).map(s -> new Class_(s.name().unwrap(), compileNode(s.body().unwrap().value().unwrap()))))
                        .flatMap(Class_::transform))
                .or(new BlockLexer(line).lex())
                .or(new FunctionLexer(line).lex())
                .or(new DeclarationLexer(line).lex())
                .unwrapOrElse(Content.ofContent(line));
    }

}