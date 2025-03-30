package magma.compile.rule;

import magma.compile.CompileError;
import magma.compile.MapNode;
import magma.compile.Node;
import magma.compile.context.StringContext;
import magma.result.Err;
import magma.result.Ok;
import magma.result.Result;

public class EmptyRule implements Rule {
    @Override
    public Result<Node, CompileError> parse(String input) {
        return input.isEmpty() ? new Ok<>(new MapNode()) : new Err<>(new CompileError("Input not empty", new StringContext(input)));
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return new Ok<>("");
    }
}
