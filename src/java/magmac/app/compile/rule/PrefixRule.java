package magmac.app.compile.rule;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.error.CompileErrors;
import magmac.app.compile.node.Node;

public record PrefixRule(String prefix, Rule childRule) implements Rule {
    @Override
    public CompileResult<Node> lex(String input) {
        if (!input.startsWith(this.prefix())) {
            return CompileErrors.createStringError("Prefix '" + this.prefix + "' not present", input);
        }

        String sliced = input.substring(this.prefix.length());
        return this.childRule.lex(sliced);
    }

    @Override
    public CompileResult<String> generate(Node node) {
        return this.childRule.generate(node).mapValue((String value) -> this.prefix + value);
    }
}