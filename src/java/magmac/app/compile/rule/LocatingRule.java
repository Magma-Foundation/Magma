package magmac.app.compile.rule;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.error.CompileErrors;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.locate.FirstLocator;
import magmac.app.compile.rule.locate.LastLocator;
import magmac.app.compile.rule.locate.Locator;

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

    @Override
    public CompileResult<Node> lex(String input) {
        return this.locator.locate(input, this.infix).map(separator -> {
            String left = input.substring(0, separator);
            String right = input.substring(separator + this.infix.length());
            return this.leftRule.lex(left).merge(() -> this.rightRule.lex(right), Node::merge);
        }).orElseGet(() -> {
            return CompileErrors.createStringError("Infix '" + this.infix + "' not present", input);
        });
    }

    @Override
    public CompileResult<String> generate(Node node) {
        return this.leftRule.generate(node).merge(
                () -> this.rightRule.generate(node),
                (leftString, rightString) -> leftString + this.infix + rightString);
    }
}