package magmac.app.compile.rule;

import magmac.api.result.Ok;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.InlineCompileResult;
import magmac.app.compile.error.error.CompileErrors;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;

public record ExactRule(String value) implements Rule {
    @Override
    public CompileResult<Node> lex(String input) {
        if (input.equals(this.value)) {
            return InlineCompileResult.fromResult(new Ok<>(new MapNode()));
        }

        return CompileErrors.createStringError("Slice '" + this.value + "' not present", input);
    }

    @Override
    public CompileResult<String> generate(Node node) {
        return InlineCompileResult.fromResult(new Ok<>(this.value));
    }
}
