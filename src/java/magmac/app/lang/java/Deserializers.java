package magmac.app.lang.java;

import magmac.api.collect.list.List;
import magmac.api.iter.Iters;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResults;
import magmac.app.compile.node.Node;

public class Deserializers {
    static <T> CompileResult<T> or(Node node, List<Deserializer<T>> deserializers) {
        return deserializers.iter()
                .map(deserializer -> deserializer.deserialize(node))
                .flatMap(Iters::fromOption)
                .next()
                .orElseGet(() -> CompileResults.NodeErr("Cannot deserialize", node));
    }
}