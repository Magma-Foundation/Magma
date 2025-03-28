package magma.app.compile.rule;

import magma.api.result.Err;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.api.result.Tuple;
import magma.app.compile.CompileError;
import magma.app.compile.Compiler;
import magma.app.compile.MapNode;
import magma.app.compile.Node;
import magma.app.compile.ParseState;
import magma.app.compile.StringContext;

public class EmptyRule implements Rule {
    private Result<Tuple<String, String>, CompileError> apply(ParseState state, String stripped) {
        if (stripped.isEmpty()) return Compiler.generateEmpty();
        return new Err<>(new CompileError("Input not empty", new StringContext(stripped)));
    }

    @Override
    public Result<Node, CompileError> parse(ParseState state, String input) {
        return apply(state, input).mapValue(tuple -> {
            return new MapNode().withString("header", tuple.left()).withString("target", tuple.right());
        });
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return new Ok<>(new MapOutput().with("header", node.findString("header").orElse("")));
    }
}