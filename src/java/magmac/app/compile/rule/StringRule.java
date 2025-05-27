package magmac.app.compile.rule;

import magmac.api.result.Ok;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.InlineCompileResult;
import magmac.app.compile.error.error.CompileErrors;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;

public record StringRule(String key) implements Rule {
    @Override
    public CompileResult<Node> lex(String input) {
        return InlineCompileResult.fromResult(new Ok<>(new MapNode().withString(this.key, input)));
    }

    @Override
    public CompileResult<String> generate(Node node) {
        return node.findString(this.key)
                .map(value -> InlineCompileResult.fromResult(new Ok<>(value)))
                .orElseGet(() -> CompileErrors.createNodeError("String '" + this.key + "' not present", node));
    }
}