package magmac.app.compile.rule;

import magmac.api.result.Result;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileError;
import magmac.app.compile.error.CompileErrors;
import magmac.app.compile.node.Node;

public record PrefixRule(String prefix, Rule childRule) implements Rule {
    private Result<Node, CompileError> lex0(String input) {
        if (!input.startsWith(this.prefix())) {
            return CompileErrors.createStringError("Prefix '" + this.prefix + "' not present", input);
        }

        String sliced = input.substring(this.prefix.length());
        return this.childRule.lex(sliced).result();
    }

    private Result<String, CompileError> generate0(Node node) {
        return this.childRule.generate(node).result().mapValue(value -> this.prefix + value);
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