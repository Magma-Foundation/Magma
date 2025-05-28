package magmac.app.lang.java;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.api.iter.Iters;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResults;
import magmac.app.compile.node.Node;

public class Deserializers {
    public static <T> CompileResult<T> orError(Node node, List<Deserializer<T>> deserializers) {
        return Deserializers.or(node, deserializers)
                .orElseGet(() -> CompileResults.NodeErr("Cannot deserialize", node));
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