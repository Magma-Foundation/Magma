package magmac.app.lang.java;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.api.collect.list.Lists;
import magmac.api.iter.Iters;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResults;
import magmac.app.compile.error.context.NodeContext;
import magmac.app.compile.error.error.CompileError;
import magmac.app.compile.node.Node;
import magmac.app.error.ImmutableCompileError;

public class Deserializers {
    public static <T> CompileResult<T> orError(String type, Node node, List<Deserializer<T>> deserializers) {
        return Deserializers.or(node, deserializers)
                .map(result -> result.mapErr(err -> Deserializers.wrap(type, node, err)))
                .orElseGet(() -> CompileResults.NodeErr("Cannot deserialize of type '" + type + "'", node));
    }

    private static CompileError wrap(String type, Node node, CompileError err) {
        return new ImmutableCompileError("Invalid type '" + type + "'", new NodeContext(node), Lists.of(err));
    }

    public static <T> Option<CompileResult<T>> or(Node node, List<Deserializer<T>> deserializers) {
        return deserializers.iter()
                .map(deserializer -> deserializer.deserialize(node))
                .flatMap(Iters::fromOption)
                .next();
    }

    public static <T extends R, R> Deserializer<R> wrap(Deserializer<T> deserializer) {
        return node -> deserializer.deserialize(node).map(result -> result.mapValue(value -> value));
    }
}