package magmac.app.compile.rule;

import magmac.api.result.Err;
import magmac.api.result.Result;
import magmac.app.error.CompileError;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.result.StringContext;

public record PrefixRule(String prefix, Rule childRule) implements Rule {
    @Override
    public Result<Node, CompileError> lex(String input) {
        if (!input.startsWith(this.prefix())) {
            return new Err<>(new CompileError("Prefix '" + this.prefix + "' not present", new StringContext(input)));
        }

        String sliced = input.substring(this.prefix.length());
        return this.childRule.lex(sliced);
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return this.childRule.generate(node).mapValue(value -> this.prefix + value);
    }
}