package magma.compile.rule;

import jvm.collect.list.Lists;
import magma.compile.CompileError;
import magma.compile.Node;
import magma.compile.rule.tree.OrRule;
import magma.result.Result;

public class OptionalNodeListRule implements Rule {
    private final String propertyKey;
    private final Rule ifPresent;
    private final Rule ifMissing;
    private final OrRule parseRule;

    public OptionalNodeListRule(String propertyKey, Rule ifPresent, Rule ifMissing) {
        this.propertyKey = propertyKey;
        this.ifPresent = ifPresent;
        this.ifMissing = ifMissing;
        parseRule = new OrRule(Lists.of(ifPresent, ifMissing));
    }

    @Override
    public Result<Node, CompileError> parse(String input) {
        return parseRule.parse(input);
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return node.hasNodeList(propertyKey)
                ? ifPresent.generate(node)
                : ifMissing.generate(node);
    }
}
