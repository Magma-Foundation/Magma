package magmac.app.compile.node;

import magmac.api.Tuple2;
import magmac.api.collect.list.List;
import magmac.api.iter.collect.ListCollector;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResultCollector;
import magmac.app.compile.error.CompileResults;

import java.util.function.Function;

public class CompoundDeserializerImpl<T> implements CompoundDeserializer<T> {
    private final CompileResult<Tuple2<Node, T>> result;

    public CompoundDeserializerImpl(CompileResult<Tuple2<Node, T>> result) {
        this.result = result;
    }

    @Override
    public <R> CompileResult<R> complete(Function<T, R> mapper) {
        return this.result.flatMapValue((Tuple2<Node, T> tuple) -> {
            if (!tuple.left().isEmpty()) {
                return CompileResults.NodeErr("Fields still present", tuple.left());
            }

            return CompileResults.Ok(mapper.apply(tuple.right()));
        });
    }

    @Override
    public <R> CompoundDeserializer<Tuple2<T, List<R>>> nodeList(String key, Function<Node, CompileResult<R>> deserializer) {
        return new CompoundDeserializerImpl<>(this.result.flatMapValue((Tuple2<Node, T> inner) -> inner.left().removeNodeList(key).flatMapValue((Tuple2<Node, NodeList> tuple) -> tuple.right()
                .iter()
                .map(deserializer)
                .collect(new CompileResultCollector<>(new ListCollector<>()))
                .mapValue((List<R> collect) -> new Tuple2<>(tuple.left(), new Tuple2<>(inner.right(), collect))))));
    }

    @Override
    public <R> CompoundDeserializer<Tuple2<T, R>> withNode(String key, Function<Node, CompileResult<R>> deserializer) {
        return new CompoundDeserializerImpl<Tuple2<T, R>>(this.result.flatMapValue((Tuple2<Node, T> inner) -> inner.left().removeNode(key).flatMapValue((Tuple2<Node, Node> removed) -> {
            Node right = removed.right();
            return deserializer.apply(right).flatMapValue((R newRight) -> CompileResults.Ok(new Tuple2<>(removed.left(), new Tuple2<>(inner.right(), newRight))));
        })));
    }
}
