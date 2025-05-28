package magmac.app.compile.node;

import magmac.api.Tuple2;
import magmac.api.collect.list.List;
import magmac.api.iter.collect.ListCollector;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResultCollector;

import java.util.function.Function;

public class EmptyDestroyer {
    private final Node node;

    public EmptyDestroyer(Node node) {
        this.node = node;
    }

    public <T> SingleDestroyer<List<T>> nodeList(String key, Function<Node, CompileResult<T>> deserializer) {
        return new SingleDestroyer<>(this.node.removeNodeList(key).flatMapValue(tuple -> {
            return tuple.right()
                    .iter()
                    .map(deserializer::apply)
                    .collect(new CompileResultCollector<>(new ListCollector<>()))
                    .mapValue(collect -> new Tuple2<>(tuple.left(), collect));
        }));
    }
}
