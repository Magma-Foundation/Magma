package magmac.app.compile.rule;

import magmac.api.result.Err;
import magmac.api.result.Ok;
import magmac.api.result.Result;
import magmac.app.error.CompileError;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.result.StringContext;

public record ExactRule(String value) implements Rule {
    @Override
    public Result<Node, CompileError> lex(String input) {
        if (input.equals(this.value)) {
            return new Ok<>(new MapNode());
        }
        return new Err<>(new CompileError("Slice '" + value + "' not present", new StringContext(value)));
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return new Ok<>(this.value);
    }
}
