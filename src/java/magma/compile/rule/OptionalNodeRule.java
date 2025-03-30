package magma.compile.rule;

import jvm.collect.list.Lists;
import magma.compile.CompileError;
import magma.compile.Node;
import magma.compile.rule.tree.OrRule;
import magma.result.Result;

public class OptionalNodeRule implements Rule {
    private final OrRule rule;
    private final String propertyKey;
    private final Rule ifPresent;
    private final Rule ifEmpty;

    public OptionalNodeRule(String propertyKey, Rule ifPresent, Rule ifEmpty) {
        this.propertyKey = propertyKey;
        this.ifPresent = ifPresent;
        this.ifEmpty = ifEmpty;
        this.rule = new OrRule(Lists.of(ifPresent, ifEmpty));
    }

    @Override
    public Result<Node, CompileError> parse(String input) {
        return rule.parse(input);
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return node.hasNode(propertyKey)
                ? ifPresent.generate(node)
                : ifEmpty.generate(node);
    }
}
