package magmac.app.compile.rule;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;

/**
 * Rule that trims input before lexing and appends optional strings when
 * generating output.  The strings are read from {@code before} and
 * {@code after} keys on the node and default to empty if absent.
 */
public record StripRule(Rule rule) implements Rule {
    @Override
    public CompileResult<Node> lex(String input) {
        return this.rule.lex(input.strip());
    }

    @Override
    public CompileResult<String> generate(Node node) {
        var before = node.findString("before").orElse("");
        var after = node.findString("after").orElse("");
        return this.rule.generate(node).mapValue(result -> before + result + after);
    }
}
