package magma;

import magma.result.Err;
import magma.result.Ok;
import magma.result.Result;

public record StringRule(String propertyKey) implements Rule {
    @Override
    public Result<String, CompileError> generate(MapNode node) {
        return node.findString(propertyKey())
                .<Result<String, CompileError>>map(Ok::new)
                .orElseGet(() -> new Err<>(new CompileError("String '" + propertyKey() + "' not present", new NodeContext(node))));
    }

    @Override
    public Result<MapNode, CompileError> parse(String input) {
        return new Ok<>(new MapNode().withString(propertyKey(), input));
    }
}