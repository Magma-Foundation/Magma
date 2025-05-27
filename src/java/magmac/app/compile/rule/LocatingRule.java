package magmac.app.compile.rule;

import magmac.api.result.Result;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileError;
import magmac.app.compile.error.CompileErrors;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.locate.FirstLocator;
import magmac.app.compile.rule.locate.LastLocator;
import magmac.app.compile.rule.locate.Locator;

import java.util.Optional;

public final class LocatingRule implements Rule {
    private final Rule leftRule;
    private final String infix;
    private final Rule rightRule;
    private final Locator locator;

    private LocatingRule(Rule leftRule, String infix, Rule rightRule, Locator locator) {
        this.leftRule = leftRule;
        this.infix = infix;
        this.rightRule = rightRule;
        this.locator = locator;
    }

    public static Rule First(Rule leftRule, String infix, Rule rightRule) {
        return new LocatingRule(leftRule, infix, rightRule, new FirstLocator());
    }

    public static Rule Last(Rule leftRule, String infix, Rule rightRule) {
        return new LocatingRule(leftRule, infix, rightRule, new LastLocator());
    }

    private Result<Node, CompileError> lex0(String input) {
        Optional<Integer> maybeSeparator = this.locator.locate(input, this.infix);
        if (maybeSeparator.isEmpty()) {
            return CompileErrors.createStringError("Infix '" + this.infix + "' not present", input);
        }

        int separator = maybeSeparator.get();
        String left = input.substring(0, separator);
        String right = input.substring(separator + this.infix.length());
        return this.leftRule.lex(left).result()
                .and(() -> this.rightRule.lex(right).result())
                .mapValue(tuple -> tuple.left().merge(tuple.right()));
    }

    private Result<String, CompileError> generate0(Node node) {
        return this.leftRule.generate(node).result()
                .and(() -> this.rightRule.generate(node).result())
                .mapValue(tuple -> tuple.left() + this.infix + tuple.right());
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