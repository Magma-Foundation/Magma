package magma.compile.rule;

import magma.compile.CompileError;
import magma.compile.MapNode;
import magma.compile.Node;
import magma.compile.context.NodeContext;
import magma.result.Err;
import magma.result.Ok;
import magma.result.Result;

public record StringRule(String propertyKey) implements Rule {
    @Override
    public Result<Node, CompileError> parse(String value) {
        return new Ok<>(new MapNode().withString(propertyKey(), value));
    }

    @Override
    public Result<String, CompileError> generate(Node input) {
        return input.findString(propertyKey)
                .<Result<String, CompileError>>map(Ok::new)
                .orElseGet(() -> new Err<>(new CompileError("String '" + propertyKey + "' not present", new NodeContext(input))));
    }
}