package magmac.app.lang;

import magmac.api.None;
import magmac.api.Option;
import magmac.api.Some;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.error.CompileErrors;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.Rule;

public class LazyRule implements Rule {
    private Option<Rule> maybeRule = new None<>();

    @Override
    public CompileResult<Node> lex(String input) {
        return this.maybeRule
                .map(rule -> rule.lex(input))
                .orElseGet(() -> CompileErrors.createStringError("Rule not set", input));
    }

    @Override
    public CompileResult<String> generate(Node node) {
        return this.maybeRule
                .map(rule -> rule.generate(node))
                .orElseGet(() -> CompileErrors.createNodeError("Rule not set", node));
    }

    public void set(Rule rule) {
        this.maybeRule = new Some<>(rule);
    }
}
