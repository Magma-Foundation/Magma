package com.meti.compile;

import com.meti.api.collect.Collectors;
import com.meti.api.collect.ImmutableLists;
import com.meti.api.collect.JavaString;
import com.meti.api.collect.List;
import com.meti.api.option.Option;
import com.meti.api.option.ThrowableOption;
import com.meti.api.result.Ok;
import com.meti.api.result.Result;
import com.meti.api.result.Results;
import com.meti.compile.block.BlockNode;

public record Compiler(JavaString input) {

    static Result<JavaString, CompileException> compileLine(JavaString input) {
        return new JavaLexer(input)
                .lex()
                .map(Compiler::transform)
                .map((Result<Node, CompileException> node) -> node.mapValue(Compiler::renderNode))
                .into(ThrowableOption::new)
                .unwrapOrThrow(new CompileException("Invalid input: '" + input + "'."))
                .flatMapValue(value -> value);
    }

    private static JavaString renderNode(Node node) {
        return new MagmaRenderer(node).render().unwrapOrElseGet(() -> JavaString.Empty);
    }

    private static Result<Node, CompileException> transform(Node node) {
        return Results.$Result(() -> {
            var withBody = node.getBody()
                    .map(body -> compileLine(body).mapValue(node::withBody))
                    .unwrapOrElse(Ok.apply(node)).$();

            return withBody.getLines().map(Compiler::compileLines)
                    .unwrapOrElse(Ok.apply(withBody))
                    .$();
        });
    }

    private static Result<Node, CompileException> compileLines(List<JavaString> lines) {
        return lines.iter()
                .map(Compiler::compileLine)
                .collect(com.meti.api.collect.Collectors.exceptionally(ImmutableLists.into()))
                .mapValue(BlockNode::new);
    }

    public Result<JavaString, CompileException> compile() {
        var lines = new Splitter(input()).split();
        return lines.iter()
                .map(Compiler::compileLine)
                .collect(Collectors.exceptionally(Collectors.joining()))
                .mapValue(value -> value.unwrapOrElse(JavaString.Empty));
    }
}