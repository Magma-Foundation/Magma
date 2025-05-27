package magmac.app.compile.rule;

import magmac.api.result.Ok;
import magmac.api.result.Result;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileError;
import magmac.app.compile.error.CompileErrors;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;

public record StringRule(String key) implements Rule {
    private Result<Node, CompileError> lex0(String input) {
        return new Ok<>(new MapNode().withString(this.key, input));
    }

    private Result<String, CompileError> generate0(Node node) {
        return node.findString(this.key)
                .<Result<String, CompileError>>map(Ok::new)
                .orElseGet(() -> CompileErrors.createNodeError("String '" + this.key + "' not present", node));
    }

    @Override
    public CompileResult<Node> lex(String input) {
        return new CompileResult<>(this.lex0(input));
    }

    @Override
    public CompileResult<String> generate(Node node) {
        return new CompileResult<>(this.generate0(node));
    }
}