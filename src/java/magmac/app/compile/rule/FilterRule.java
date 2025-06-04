package magmac.app.compile.rule;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.type.CompileErrors;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.filter.Filter;
import magmac.app.compile.rule.filter.NumberFilter;
import magmac.app.compile.rule.filter.SymbolFilter;

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
    public CompileResult<Node> lex(String input) {
        if (this.filter.test(input)) {
            return this.childRule.lex(input);
        }
        else {
            return CompileErrors.createStringError(this.filter.createMessage(), input);
        }
    }

    @Override
    public CompileResult<String> generate(Node node) {
        return this.childRule.generate(node);
    }
}
