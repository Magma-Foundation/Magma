package magmac.app.compile.rule;

import magmac.api.result.Ok;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.InlineCompileResult;
import magmac.app.compile.error.error.CompileErrors;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;

public record NodeRule(String key, Rule childRule) implements Rule {

    private CompileResult<Node> findNode(Node node) {
        return node.findNode(this.key)
                .map(node1 -> InlineCompileResult.fromResult(new Ok<>(node1)))
                .orElseGet(() -> CompileErrors.createNodeError("Node '" + this.key + "' not present", node));
    }

    @Override
    public CompileResult<Node> lex(String input) {
        return this.childRule.lex(input)
                .mapValue(lexed -> new MapNode().withNode(this.key, lexed));
    }

    @Override
    public CompileResult<String> generate(Node node) {
        return this.findNode(node).flatMapValue(child -> this.childRule.generate(child));
    }
}
