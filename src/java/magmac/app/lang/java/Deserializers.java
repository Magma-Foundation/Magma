package magmac.app.lang.java;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.api.iter.Iters;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResults;
import magmac.app.compile.node.Node;

import java.util.function.Function;

public class Deserializers {
    static <T> CompileResult<T> or(Node node, List<Function<Node, Option<CompileResult<T>>>> deserializers) {
        return deserializers.iter()
                .map(deserializer -> deserializer.apply(node))
                .flatMap(Iters::fromOption)
                .next()
                .orElseGet(() -> CompileResults.NodeErr("Cannot deserialize", node));
    }
}