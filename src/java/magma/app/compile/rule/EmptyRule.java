package magma.app.compile.rule;

import magma.api.result.Err;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.app.compile.CompileError;
import magma.app.compile.MapNode;
import magma.app.compile.Node;
import magma.app.compile.ParseState;
import magma.app.compile.StringContext;

public class EmptyRule implements Rule {
    @Override
    public Result<Node, CompileError> parse(ParseState state, String input) {
        if (input.isEmpty()) return new Ok<>(new MapNode());
        return new Err<>(new CompileError("Must be empty", new StringContext(input)));
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return new Ok<>("");
    }
}