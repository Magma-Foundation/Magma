package magmac.app.compile.rule;

import magmac.api.result.Err;
import magmac.api.result.Ok;
import magmac.api.result.Result;
import magmac.app.compile.CompileError;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.result.InlineRuleResult;
import magmac.app.compile.rule.result.RuleResult;

public record StringRule(String key) implements Rule {
    @Override
    public RuleResult<Node> lex(String input) {
        return InlineRuleResult.from(new MapNode().withString(this.key, input));
    }

    @Override
    public RuleResult<String> generate(Node node) {
        return new InlineRuleResult<>(node.findString(this.key)
                .<Result<String, CompileError>>map(Ok::new)
                .orElseGet(() -> new Err<>(new CompileError("String '" + this.key + "' not present", new NodeContext(node)))));
    }
}