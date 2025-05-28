package magmac.app.lang;

import magmac.api.None;
import magmac.api.Option;
import magmac.api.Some;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.error.CompileErrors;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.Rule;

public class MutableLazyRule implements LazyRule {
    private Option<Rule> maybeRule = new None<>();

    @Override
    public CompileResult<Node> lex(String input) {
        return this.maybeRule
                .map((Rule rule) -> rule.lex(input))
                .orElseGet(() -> CompileErrors.createStringError("Rule not set", input));
    }

    @Override
    public CompileResult<String> generate(Node node) {
        return this.maybeRule
                .map((Rule rule) -> rule.generate(node))
                .orElseGet(() -> CompileErrors.createNodeError("Rule not set", node));
    }

    @Override
    public LazyRule set(Rule rule) {
        this.maybeRule = new Some<>(rule);
        return this;
    }
}
