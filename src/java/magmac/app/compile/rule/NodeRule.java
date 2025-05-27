package magmac.app.compile.rule;

import magmac.api.result.Ok;
import magmac.api.result.Result;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileError;
import magmac.app.compile.error.CompileErrors;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;

public record NodeRule(String key, Rule childRule) implements Rule {
    private Result<Node, CompileError> lex0(String input) {
        return this.childRule.lex(input).result().mapValue(lexed -> new MapNode().withNode(this.key, lexed));
    }

    private Result<String, CompileError> generate0(Node node) {
        return this.findNode(node).flatMapValue(child -> this.childRule.generate(child).result());
    }

    private Result<Node, CompileError> findNode(Node node) {
        return node.findNode(this.key)
                .<Result<Node, CompileError>>map(Ok::new)
                .orElseGet(() -> CompileErrors.createNodeError("Node '" + this.key + "' not present", node));
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
