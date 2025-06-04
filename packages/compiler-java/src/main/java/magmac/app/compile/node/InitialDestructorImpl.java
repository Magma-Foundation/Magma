package magmac.app.compile.node;

import magmac.api.Tuple2;
import magmac.api.collect.list.List;
import magmac.api.iter.collect.ListCollector;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResultCollector;
import magmac.app.compile.error.CompileResults;
import magmac.app.lang.node.Deserializer;

import java.util.function.Function;
import java.util.function.Supplier;

public class InitialDestructorImpl implements InitialDestructor {
    private final Node node;

    public InitialDestructorImpl(Node node) {
        this.node = node;
    }

    @Override
    public <T> CompoundDestructor<List<T>> withNodeList(String key, Deserializer<T> deserializer) {
        return new CompoundDestructorImpl<>(this.node.removeNodeListOrError(key).flatMapValue((Tuple2<Node, NodeList> tuple) -> tuple.right()
                .iter()
                .map(deserializer::deserialize)
                .collect(new CompileResultCollector<>(new ListCollector<>()))
                .mapValue((List<T> collect) -> new Tuple2<>(tuple.left(), collect))));
    }

    @Override
    public CompoundDestructor<String> withString(String key) {
        return new CompoundDestructorImpl<>(this.node.removeString(key));
    }

    @Override
    public <T> CompoundDestructor<T> withNode(String key, Function<Node, CompileResult<T>> deserializer) {
        return new CompoundDestructorImpl<>(this.node.removeNodeOrError(key).flatMapValue((Tuple2<Node, Node> tuple) -> deserializer.apply(tuple.right()).mapValue((T deserialized) -> new Tuple2<>(tuple.left(), deserialized))));
    }

    @Override
    public <T> CompileResult<T> complete(Supplier<T> supplier) {
        if (this.node.hasContent()) {
            return CompileResults.NodeErr("Fields still present", this.node);
        }

        return CompileResults.Ok(supplier.get());
    }
}
