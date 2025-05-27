package magmac.app.compile.rule;

import magmac.api.result.Err;
import magmac.api.result.Ok;
import magmac.api.result.Result;
import magmac.app.compile.error.CompileError;
import magmac.app.compile.error.CompileErrors;
import magmac.app.compile.error.context.NodeContext;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.error.ImmutableCompileError;

public record StringRule(String key) implements Rule {
    @Override
    public Result<Node, CompileError> lex(String input) {
        return new Ok<>(new MapNode().withString(this.key, input));
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return node.findString(this.key)
                .<Result<String, CompileError>>map(Ok::new)
                .orElseGet(() -> CompileErrors.createNodeError("String '" + this.key + "' not present", node));
    }
}