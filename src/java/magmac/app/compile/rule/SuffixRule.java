package magmac.app.compile.rule;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.error.CompileErrors;
import magmac.app.compile.node.Node;

public record SuffixRule(Rule childRule, String suffix) implements Rule {
    @Override
    public CompileResult<Node> lex(String input) {
        if (!input.endsWith(this.suffix())) {
            return CompileErrors.createStringError("Suffix '" + this.suffix + "' not present", input);
        }

        String slice = input.substring(0, input.length() - this.suffix.length());
        return this.childRule.lex(slice);
    }

    @Override
    public CompileResult<String> generate(Node node) {
        return this.childRule.generate(node).mapValue((String result) -> result + this.suffix);
    }
}