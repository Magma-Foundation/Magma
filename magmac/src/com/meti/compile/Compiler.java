package com.meti.compile;

import com.meti.collect.stream.Collectors;
import com.meti.collect.stream.Stream;
import com.meti.collect.stream.Streams;
import com.meti.compile.attempt.CatchLexer;
import com.meti.compile.attempt.TryLexer;
import com.meti.compile.external.ImportLexer;
import com.meti.compile.external.PackageLexer;
import com.meti.compile.node.Node;
import com.meti.compile.node.Statement;
import com.meti.compile.node.TerminatingStatement;
import com.meti.compile.procedure.InvocationLexer;
import com.meti.compile.procedure.MethodLexer;
import com.meti.compile.scope.*;
import com.meti.compile.string.StringLexer;
import com.meti.java.JavaString;

import java.util.Collections;
import java.util.function.Function;

import static com.meti.collect.result.Results.$Result;

public record Compiler(String input) {
    public static Stream<Node> lexExpression(String line, int indent) {
        return lexHead(line, indent).flatMap(Compiler::lexTree);
    }

    public static Stream<Node> lexHead(String line, int indent) {
        return Streams.<Function<String, Lexer>>from(
                        exp -> new CatchLexer(new JavaString(exp), indent),
                        exp -> new ReturnLexer(new JavaString(exp), indent),
                        exp -> new TryLexer(new JavaString(exp), indent),
                        PackageLexer::new,
                        StringLexer::new,
                        exp -> new DefinitionLexer(new JavaString(exp), indent),
                        exp -> new BlockLexer(exp, indent),
                        ObjectLexer::new,
                        exp -> new ImportLexer(new JavaString(exp)),
                        exp -> new MethodLexer(new JavaString(exp), indent),
                        exp -> new InvocationLexer(new JavaString(exp)),
                        exp -> new LambdaLexer(new JavaString(exp)),
                        exp -> new FieldLexer(new JavaString(exp)),
                        VariableLexer::new
                ).map(constructor -> constructor.apply(line.strip()))
                .map(Lexer::lex)
                .flatMap(Streams::fromOption);
    }

    private static Stream<Node> lexTree(Node node) {
        var withValues = node.findValueAsNode()
                .map(Compiler::lexContent)
                .orElse(Streams.empty())
                .map(content -> node.withValue(content).orElse(node));

        return withValues.map(withValue -> {
            return withValue.findChildren().map(oldChildren -> {
                var newChildren = Streams.fromList(oldChildren)
                        .flatMap(Compiler::lexContent)
                        .collect(Collectors.toList());

                return withValue.withChildren(newChildren).orElse(withValue);
            });
        }).flatMap(Streams::fromOption);
    }

    private static Stream<Node> lexContent(Node content) {
        return content.findValueAsString()
                .and(content.findIndent())
                .map(tuple -> lexExpression(tuple.a(), tuple.b()))
                .orElse(Streams.empty());
    }

    private static Node transformAST(Node tree) {
        var withValue = tree.findValueAsNode()
                .map(Compiler::transformAST)
                .flatMap(tree::withValue)
                .orElse(tree);

        var node = withValue.findChildren()
                .map(children -> Streams.fromList(children)
                        .map(Compiler::transformAST)
                        .collect(Collectors.toList()))
                .flatMap(withValue::withChildren)
                .orElse(withValue);

        return transformNode(node);
    }

    private static Node transformNode(Node node) {
        if (node.is("block")) {
            var children = node.findChildren().orElse(Collections.emptyList());
            var newChildren = Streams.fromList(children)
                    .map(child -> {
                        var realIndent = node.findIndent().orElse(0) + 1;
                        if (child.is("method") || child.is("try") || child.is("catch")) {
                            return new Statement(child, realIndent);
                        } else {
                            return new TerminatingStatement(child, realIndent);
                        }
                    })
                    .collect(Collectors.toList());
            return node.withChildren(newChildren).orElse(node);
        } else {
            return node;
        }
    }

    String compile() throws CompileException {
        return $Result(() -> {
            var tree = new Splitter(this.input())
                    .split()
                    .flatMap(line -> lexExpression(line, 0))
                    .collect(Collectors.toList());

            var outputTree = Streams.fromList(tree)
                    .filter(element -> !element.is("package"))
                    .map(Compiler::transformAST)
                    .collect(Collectors.toList());

            return Streams.fromList(outputTree).map(node -> node.render().orElse("")).collect(Collectors.joining()).orElse("");
        }).$();
    }
}