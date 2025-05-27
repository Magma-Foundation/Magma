package magmac.app.compile.rule;

import magmac.api.result.Result;
import magmac.app.compile.error.CompileError;
import magmac.app.compile.error.CompileErrors;
import magmac.app.compile.node.Node;

public record PrefixRule(String prefix, Rule childRule) implements Rule {
    @Override
    public Result<Node, CompileError> lex(String input) {
        if (!input.startsWith(this.prefix())) {
            return CompileErrors.createStringError("Prefix '" + this.prefix + "' not present", input);
        }

        String sliced = input.substring(this.prefix.length());
        return this.childRule.lex(sliced);
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return this.childRule.generate(node).mapValue(value -> this.prefix + value);
    }
}