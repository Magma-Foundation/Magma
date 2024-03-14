package com.meti.compile;

import com.meti.collect.result.Result;
import com.meti.collect.result.ThrowableOption;
import com.meti.collect.stream.Collectors;
import com.meti.collect.stream.Streams;
import com.meti.compile.attempt.CatchLexer;
import com.meti.compile.attempt.TryLexer;
import com.meti.compile.external.ImportLexer;
import com.meti.compile.external.PackageLexer;
import com.meti.compile.node.Node;
import com.meti.compile.procedure.ConstructionLexer;
import com.meti.compile.procedure.InvocationLexer;
import com.meti.compile.procedure.MethodLexer;
import com.meti.compile.scope.*;
import com.meti.compile.string.StringLexer;
import com.meti.java.JavaString;

import java.util.function.Function;

import static com.meti.collect.result.Ok.Ok;
import static com.meti.collect.result.Results.$Result;

public record Compiler(String input) {
    static Result<Node, CompileException> lexExpression(String line, int indent) {
        return Streams.<Function<String, Lexer>>from(
                exp -> new CatchLexer(new JavaString(exp)),
                exp -> new ConstructionLexer(new JavaString(exp)),
                exp -> new ReturnLexer(new JavaString(exp)),
                exp -> new TryLexer(new JavaString(exp)),
                PackageLexer::new,
                StringLexer::new,
                exp -> new DefinitionLexer(exp, indent),
                exp -> new BlockLexer(exp, indent),
                ObjectLexer::new,
                ImportLexer::new,
                exp -> new MethodLexer(new JavaString(exp), indent), stripped1 -> new InvocationLexer(new JavaString(stripped1)), input -> new FieldLexer(new JavaString(input)), VariableLexer::new).map(constructor -> constructor.apply(line.strip())).map(Lexer::lex).flatMap(Streams::fromOption).next().into(ThrowableOption::new).orElseThrow(() -> new CompileException("Failed to compile: '%s'.".formatted(line))).flatMapValue(Compiler::lexTree);
    }

    private static Result<Node, CompileException> lexTree(Node node) {
        return $Result(() -> {
            var withValue = node.findValueAsNode().map(value -> lexContent(value).mapValue(content -> node.withValue(content).orElse(node))).orElse(Ok(node)).$();

            return withValue.findChildren().map(oldChildren -> $Result(() -> {
                var newChildren = Streams.fromList(oldChildren).map(Compiler::lexContent).collect(Collectors.exceptionally(Collectors.toList())).$();

                return withValue.withChildren(newChildren).orElse(withValue);
            })).orElse(Ok(withValue)).$();
        }).mapErr(err -> new CompileException("Failed to lex AST for: '" + node + "'", err));
    }

    private static Result<Node, CompileException> lexContent(Node content) {
        return content.findValueAsString().and(content.findIndent()).map(tuple -> lexExpression(tuple.a(), tuple.b())).into(ThrowableOption::new).orElseThrow(() -> new CompileException("Both the value and the indent must be present. This is not Content.")).flatMapValue(Function.identity());
    }

    String compile() throws CompileException {
        return $Result(() -> {
            var tree = new Splitter(this.input()).split().map(line -> lexExpression(line, 0)).collect(Collectors.exceptionally(Collectors.toList())).$();

            var outputTree = Streams.fromList(tree).filter(element -> !element.is("package")).collect(Collectors.toList());

            return Streams.fromList(outputTree).map(node -> node.render().orElse("")).collect(Collectors.joining()).orElse("");
        }).$();
    }
}