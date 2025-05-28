package magmac.app.compile.rule;

import magmac.api.result.Ok;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResults;
import magmac.app.compile.error.error.CompileErrors;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;

public record NodeRule(String key, Rule childRule) implements Rule {
    private static CompileResult<Node> findNode(Node node, String key) {
        return node.findNode(key)
                .map((Node node1) -> CompileResults.fromResult(new Ok<>(node1)))
                .orElseGet(() -> CompileErrors.createNodeError("Node '" + key + "' not present", node));
    }

    @Override
    public CompileResult<Node> lex(String input) {
        return this.childRule.lex(input)
                .mapValue((Node lexed) -> new MapNode().withNode(this.key, lexed));
    }

    @Override
    public CompileResult<String> generate(Node node) {
        return NodeRule.findNode(node, this.key).flatMapValue((Node child) -> this.childRule.generate(child));
    }
}
