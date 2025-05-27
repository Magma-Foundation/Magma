package magmac.app.compile.rule;

import magmac.api.result.Result;
import magmac.app.compile.error.CompileError;
import magmac.app.compile.error.CompileErrors;
import magmac.app.compile.node.Node;

public final class FilterRule implements Rule {
    private final Rule childRule;
    private final Filter filter;

    private FilterRule(Filter filter, Rule childRule) {
        this.childRule = childRule;
        this.filter = filter;
    }

    public static Rule Symbol(Rule childRule) {
        return new FilterRule(new SymbolFilter(), childRule);
    }

    public static Rule Number(Rule childRule) {
        return new FilterRule(new NumberFilter(), childRule);
    }

    @Override
    public Result<Node, CompileError> lex(String input) {
        if (this.filter.test(input)) {
            return this.childRule.lex(input);
        }
        else {
            return CompileErrors.createStringError(filter.createMessage(), input);
        }
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return this.childRule.generate(node);
    }
}
