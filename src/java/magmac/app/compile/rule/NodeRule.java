package magmac.app.compile.rule;

import magmac.api.result.Err;
import magmac.api.result.Ok;
import magmac.api.result.Result;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.error.CompileError;
import magmac.app.error.ImmutableCompileError;

public record NodeRule(String key, Rule childRule) implements Rule {
    @Override
    public Result<Node, CompileError> lex(String input) {
        return this.childRule.lex(input).mapValue(lexed -> new MapNode().withNode(this.key, lexed));
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return this.findNode(node).flatMapValue(child -> {
            return this.childRule.generate(child);
        });
    }

    private Result<Node, CompileError> findNode(Node node) {
        return node.findNode(this.key)
                .<Result<Node, CompileError>>map(Ok::new)
                .orElseGet(() -> new Err<>(new ImmutableCompileError("Node '" + this.key + "' not present", new NodeContext(node))));
    }
}
