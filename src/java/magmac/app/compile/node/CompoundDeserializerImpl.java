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
        return new CompoundDeserializerImpl<>(this.result.flatMapValue((Tuple2<Node, T> inner) -> this.getTuple2CompileResult(key, deserializer, inner)));
    }

    private <R> CompileResult<Tuple2<Node, Tuple2<T, List<R>>>> getTuple2CompileResult(String key, Function<Node, CompileResult<R>> deserializer, Tuple2<Node, T> inner) {
        return inner.left().removeNodeList(key).flatMapValue((Tuple2<Node, NodeList> tuple) -> tuple.right()
                .iter()
                .map(deserializer)
                .collect(new CompileResultCollector<>(new ListCollector<>()))
                .mapValue((List<R> collect) -> new Tuple2<>(tuple.left(), new Tuple2<>(inner.right(), collect))));
    }
}
