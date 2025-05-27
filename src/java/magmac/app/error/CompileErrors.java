package magmac.app.error;

import magmac.api.result.Err;
import magmac.api.result.Result;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.NodeContext;

public final class CompileErrors {
    public static <T> Result<T, CompileError> createNodeError(Node node, String message) {
        return new Err<>(new ImmutableCompileError(message, new NodeContext(node)));
    }
}
