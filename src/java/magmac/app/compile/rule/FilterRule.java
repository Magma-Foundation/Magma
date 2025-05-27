package magmac.app.compile.rule;

import magmac.api.result.Result;
import magmac.app.compile.error.CompileResult;
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

    private Result<Node, CompileError> lex0(String input) {
        if (this.filter.test(input)) {
            return this.childRule.lex(input).result();
        }
        else {
            return CompileErrors.createStringError(filter.createMessage(), input);
        }
    }

    private Result<String, CompileError> generate0(Node node) {
        return this.childRule.generate(node).result();
    }

    @Override
    public CompileResult<Node> lex(String input) {
        return new CompileResult<>(this.lex0(input));
    }

    @Override
    public CompileResult<String> generate(Node node) {
        return new CompileResult<>(this.generate0(node));
    }
}
