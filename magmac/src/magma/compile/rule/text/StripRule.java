package magma.compile.rule.text;

import magma.api.Err;
import magma.api.Ok;
import magma.api.Result;
import magma.compile.CompileException;
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

    private Optional<String> fromNode0(Node node) {
        var leftIndent = node.attributes()
                .apply("left-indent")
                .flatMap(Attribute::asString)
                .orElse("");

        var rightIndent = node.attributes()
                .apply("right-indent")
                .flatMap(Attribute::asString)
                .orElse("");

        return child.fromNode(node).findValue().map(inner -> leftIndent + inner + rightIndent);
    }

    @Override
    public Result<String, CompileException> fromNode(Node node) {
        return fromNode0(node)
                .<Result<String, CompileException>>map(Ok::new)
                .orElseGet(() -> new Err<>(new CompileException("Cannot render: " + node)));
    }
}
