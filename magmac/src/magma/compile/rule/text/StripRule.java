package magma.compile.rule.text;

import magma.compile.attribute.Attribute;
import magma.compile.rule.Node;
import magma.compile.rule.Rule;
import magma.compile.rule.result.RuleResult;

import java.util.Optional;

public record StripRule(Rule child) implements Rule {
    @Override
    public RuleResult toNode(String input) {
        return child.toNode(input.strip());
    }

    @Override
    public Optional<String> fromNode(Node node) {
        var leftIndent = node.attributes()
                .apply("left-indent")
                .flatMap(Attribute::asString)
                .orElse("");

        var rightIndent = node.attributes()
                .apply("right-indent")
                .flatMap(Attribute::asString)
                .orElse("");

        return child.fromNode(node).map(inner -> leftIndent + inner + rightIndent);
    }
}
