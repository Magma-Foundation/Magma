package magmac.app.compile.rule;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;

public record NodeRule(String key, Rule childRule) implements Rule {
    @Override
    public CompileResult<Node> lex(String input) {
        return this.childRule.lex(input)
                .mapValue((Node lexed) -> new MapNode().withNode(this.key, lexed));
    }

    @Override
    public CompileResult<String> generate(Node node) {
        return node.findNodeOrError(this.key).flatMapValue((Node child) -> this.childRule.generate(child));
    }
}
