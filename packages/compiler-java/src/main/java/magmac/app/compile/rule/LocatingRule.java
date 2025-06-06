package magmac.app.compile.rule;

import magmac.api.Tuple2;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.type.CompileErrors;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.locate.FirstLocator;
import magmac.app.compile.rule.locate.LastLocator;
import magmac.app.compile.rule.locate.Locator;

public final class LocatingRule implements Rule {
    private final Rule leftRule;
    private final Splitter splitter;
    private final Rule rightRule;

    public LocatingRule(Rule leftRule, Splitter splitter, Rule rightRule) {
        this.leftRule = leftRule;
        this.rightRule = rightRule;
        this.splitter = splitter;
    }

    public static Rule First(Rule leftRule, String infix, Rule rightRule) {
        return LocatingRule.createLocatingRule(leftRule, infix, rightRule, new FirstLocator());
    }

    public static Rule Last(Rule leftRule, String infix, Rule rightRule) {
        return LocatingRule.createLocatingRule(leftRule, infix, rightRule, new LastLocator());
    }

    private static Rule createLocatingRule(Rule leftRule, String infix, Rule rightRule, Locator locator) {
        return new LocatingRule(leftRule, new LocatingSplitter(infix, locator), rightRule);
    }

    @Override
    public CompileResult<Node> lex(String input) {
        return this.splitter.split(input)
                .map(this::lexPair)
                .orElseGet(() -> CompileErrors.createStringError(this.splitter.createMessage(), input));
    }

    private CompileResult<Node> lexPair(Tuple2<String, String> tuple) {
        var left = tuple.left();
        var right = tuple.right();
        return this.leftRule.lex(left).merge(() -> this.rightRule.lex(right), Node::merge);
    }

    @Override
    public CompileResult<String> generate(Node node) {
        return this.leftRule.generate(node).merge(
                () -> this.rightRule.generate(node),
                this.splitter::merge);
    }
}