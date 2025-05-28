package magmac.app.compile.node;

import magmac.api.Tuple2;
import magmac.api.collect.list.List;
import magmac.api.iter.collect.ListCollector;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResultCollector;

import java.util.function.Function;

public class InitialDeserializerImpl implements InitialDeserializer {
    private final Node node;

    public InitialDeserializerImpl(Node node) {
        this.node = node;
    }

    @Override
    public <T> CompoundDeserializer<List<T>> withNodeList(String key, Function<Node, CompileResult<T>> deserializer) {
        return new CompoundDeserializerImpl<>(this.node.removeNodeList(key).flatMapValue((Tuple2<Node, NodeList> tuple) -> tuple.right()
                .iter()
                .map(deserializer)
                .collect(new CompileResultCollector<>(new ListCollector<>()))
                .mapValue((List<T> collect) -> new Tuple2<>(tuple.left(), collect))));
    }

    @Override
    public CompoundDeserializer<String> withString(String key) {
        return new CompoundDeserializerImpl<>(this.node.removeString(key));
    }

    @Override
    public <T> CompoundDeserializer<T> withNode(String key, Function<Node, CompileResult<T>> deserializer) {
        return new CompoundDeserializerImpl<>(this.node.removeNode(key).flatMapValue((Tuple2<Node, Node> tuple) -> deserializer.apply(tuple.right()).mapValue((T deserialized) -> new Tuple2<>(tuple.left(), deserialized))));
    }
}
