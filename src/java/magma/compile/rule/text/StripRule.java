package magma.compile.rule.text;

import magma.compile.CompileError;
import magma.compile.Node;
import magma.compile.rule.Rule;
import magma.result.Result;

public record StripRule(String leftKey, Rule childRule, String rightKey) implements Rule {
    public StripRule(Rule childRule) {
        this("", childRule, "");
    }

    @Override
    public Result<Node, CompileError> parse(String input) {
        return childRule().parse(input.strip());
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return childRule.generate(node).mapValue(value -> attachPadding(node, value));
    }

    private String attachPadding(Node node, String value) {
        String leftPad = node.findString(leftKey).orElse("");
        String rightPad = node.findString(rightKey).orElse("");
        return leftPad + value + rightPad;
    }
}