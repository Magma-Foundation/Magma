package com.meti.compile.java;

import com.meti.collect.stream.Stream;
import com.meti.collect.stream.Streams;
import com.meti.compile.Lexer;
import com.meti.compile.attempt.CatchLexer;
import com.meti.compile.attempt.TryLexer;
import com.meti.compile.external.ImportLexer;
import com.meti.compile.external.PackageLexer;
import com.meti.compile.node.Node;
import com.meti.compile.procedure.InvocationLexer;
import com.meti.compile.procedure.MethodLexer;
import com.meti.compile.scope.*;
import com.meti.compile.string.StringLexer;
import com.meti.compile.string.TextBlockLexer;
import com.meti.java.JavaString;

import java.util.function.Function;

public record JavaLexer(JavaString line, int indent) implements Lexer {
    @Override
    public Stream<Node> lex() {
        return Streams.<Function<String, Lexer>>from(
                exp -> new IntegerLexer(JavaString.from(exp)),
                exp -> new TextBlockLexer(JavaString.from(exp)),
                exp -> new StringLexer(JavaString.from(exp)),
                exp -> new CatchLexer(JavaString.from(exp), indent()),
                exp -> new ReturnLexer(JavaString.from(exp), indent()),
                exp -> new TryLexer(JavaString.from(exp), indent()),
                PackageLexer::new,
                exp -> new ClassLexer(JavaString.from(exp)),
                exp -> new BlockLexer(exp, indent()),
                exp -> new MethodLexer(JavaString.from(exp), indent()),
                exp -> new DefinitionLexer(JavaString.from(exp), indent()),
                exp -> new ImportLexer(JavaString.from(exp)),
                exp -> new InvocationLexer(JavaString.from(exp)),
                exp -> new LambdaLexer(JavaString.from(exp)),
                exp -> new FieldLexer(JavaString.from(exp)),
                VariableLexer::new,
                exp -> new InterfaceLexer(JavaString.from(exp)))
                .map(constructor -> constructor.apply(line().strip().inner()))
                .map(lexer -> lexer.lex().next())
                .flatMap(Streams::fromOption);
    }
}