package magmac.app.compile.rule;

import magmac.api.result.Result;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileError;
import magmac.app.compile.error.CompileErrors;
import magmac.app.compile.node.Node;

public record SuffixRule(Rule childRule, String suffix) implements Rule {
    private Result<Node, CompileError> lex0(String input) {
        if (!input.endsWith(this.suffix())) {
            return CompileErrors.createStringError("Suffix '" + this.suffix + "' not present", input);
        }

        String slice = input.substring(0, input.length() - this.suffix.length());
        return this.childRule.lex(slice).result();
    }

    private Result<String, CompileError> generate0(Node node) {
        return this.childRule.generate(node).result().mapValue(result -> result + this.suffix);
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