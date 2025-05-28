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
        return new SingleDestroyer<>(this.node.removeNodeList(key).flatMapValue((Tuple2<Node, NodeList> tuple) -> tuple.right()
                .iter()
                .map(deserializer)
                .collect(new CompileResultCollector<>(new ListCollector<>()))
                .mapValue((List<T> collect) -> new Tuple2<>(tuple.left(), collect))));
    }

    public SingleDestroyer<String> string(String key) {
        return new SingleDestroyer<>(this.node.removeString(key));
    }

    public <T> SingleDestroyer<T> node(String key, Function<Node, CompileResult<T>> deserializer) {
        return new SingleDestroyer<>(this.node.removeNode(key).flatMapValue((Tuple2<Node, Node> tuple) -> deserializer.apply(tuple.right()).mapValue((T deserialized) -> new Tuple2<>(tuple.left(), deserialized))));
    }
}
