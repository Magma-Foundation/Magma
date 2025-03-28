package magma.app.compile.rule;

import magma.api.result.Err;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.app.compile.CompileError;
import magma.app.compile.MapNode;
import magma.app.compile.Node;
import magma.app.compile.NodeContext;
import magma.app.compile.ParseState;

public record StringRule(String propertyKey) implements Rule {
    @Override
    public Result<Node, CompileError> parse(ParseState state, String input) {
        return new Ok<>(new MapNode().withString(propertyKey, input));
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return node.findString(propertyKey)
                .<Result<String, CompileError>>map(Ok::new)
                .orElseGet(() -> createErr(node));
    }

    private Result<String, CompileError> createErr(Node node) {
        String format = "String '%s' not present";
        String message = format.formatted(propertyKey);
        NodeContext context = new NodeContext(node);
        CompileError error = new CompileError(message, context);
        return new Err<>(error);
    }
}
