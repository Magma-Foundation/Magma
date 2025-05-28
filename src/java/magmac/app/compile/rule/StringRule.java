package magmac.app.compile.rule;

import magmac.api.result.Ok;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.InlineCompileResult;
import magmac.app.compile.error.error.CompileErrors;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;

public record StringRule(String key) implements Rule {
    public static CompileResult<String> findString(Node node, String key) {
        return node.findString(key)
                .map((String value) -> InlineCompileResult.fromResult(new Ok<>(value)))
                .orElseGet(() -> CompileErrors.createNodeError("String '" + key + "' not present", node));
    }

    @Override
    public CompileResult<Node> lex(String input) {
        return InlineCompileResult.fromResult(new Ok<>(new MapNode().withString(this.key, input)));
    }

    @Override
    public CompileResult<String> generate(Node node) {
        return StringRule.findString(node, this.key);
    }
}