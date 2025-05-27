package magmac.app.compile.error.error;

import magmac.api.result.Err;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.context.NodeContext;
import magmac.app.compile.error.context.StringContext;
import magmac.app.compile.node.Node;
import magmac.app.error.ImmutableCompileError;

public final class CompileErrors {
    public static <T> CompileResult<T> createNodeError(String message, Node node) {
        return new CompileResult<>(new Err<>(new ImmutableCompileError(message, new NodeContext(node))));
    }

    public static <T> CompileResult<T> createStringError(String message, String context) {
        return new CompileResult<>(new Err<>(new ImmutableCompileError(message, new StringContext(context))));
    }
}
