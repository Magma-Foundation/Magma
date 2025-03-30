package magma.compile.lang;

import magma.compile.CompileError;
import magma.compile.Node;
import magma.compile.context.StringContext;
import magma.compile.rule.Rule;
import magma.result.Err;
import magma.result.Result;

public final class FilterRule implements Rule {
    private final Rule rule;
    private final Filter filter;

    public FilterRule(Filter filter, Rule rule) {
        this.rule = rule;
        this.filter = filter;
    }

    @Override
    public Result<Node, CompileError> parse(String input) {
        if (filter.isValid(input)) {
            return rule.parse(input);
        } else {
            return new Err<>(new CompileError("Not a symbol", new StringContext(input)));
        }
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return rule.generate(node);
    }
}
