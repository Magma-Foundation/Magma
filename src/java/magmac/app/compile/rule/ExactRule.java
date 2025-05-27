package magmac.app.compile.rule;

import magmac.api.result.Ok;
import magmac.api.result.Result;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileError;
import magmac.app.compile.error.CompileErrors;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;

public record ExactRule(String value) implements Rule {
    private Result<Node, CompileError> lex0(String input) {
        if (input.equals(this.value)) {
            return new Ok<>(new MapNode());
        }

        return CompileErrors.createStringError("Slice '" + this.value + "' not present", input);
    }

    private Result<String, CompileError> generate0(Node node) {
        return new Ok<>(this.value);
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
