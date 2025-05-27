package magmac.app.compile.rule;

import magmac.api.result.Err;
import magmac.api.result.Result;
import magmac.app.error.CompileError;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.result.StringContext;

public record SuffixRule(Rule childRule, String suffix) implements Rule {
    @Override
    public Result<Node, CompileError> lex(String input) {
        if (!input.endsWith(this.suffix())) {
            return new Err<>(new CompileError("?", new StringContext("?")));
        }

        String slice = input.substring(0, input.length() - this.suffix().length());
        return this.childRule.lex(slice);
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return this.childRule.generate(node).mapValue(result -> result + this.suffix);
    }
}