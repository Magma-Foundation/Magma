package magmac.app.compile.error.type;

import magmac.api.result.Err;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResults;
import magmac.app.compile.error.context.NodeContext;
import magmac.app.compile.error.context.StringContext;
import magmac.app.compile.node.Node;
import magmac.app.error.ImmutableCompileError;

public final class CompileErrors {
    public static <T> CompileResult<T> createNodeError(String message, Node node) {
        return CompileResults.fromResult(new Err<>(new ImmutableCompileError(message, new NodeContext(node))));
    }

    public static <T> CompileResult<T> createStringError(String message, String context) {
        return CompileResults.fromResult(new Err<>(new ImmutableCompileError(message, new StringContext(context))));
    }
}
