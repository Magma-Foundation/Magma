package magmac.app.compile.node;

import magmac.api.None;
import magmac.api.Option;
import magmac.api.Some;
import magmac.api.Tuple2;
import magmac.api.collect.list.List;
import magmac.api.iter.collect.ListCollector;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResultCollector;
import magmac.app.compile.error.CompileResults;

import java.util.function.Function;

public class CompoundDestructorImpl<T> implements CompoundDestructor<T> {
    private final CompileResult<Tuple2<Node, T>> result;

    public CompoundDestructorImpl(CompileResult<Tuple2<Node, T>> result) {
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
    public <R> CompoundDestructor<Tuple2<T, List<R>>> withNodeList(String key, Function<Node, CompileResult<R>> deserializer) {
        return new CompoundDestructorImpl<>(this.result.flatMapValue((Tuple2<Node, T> inner) -> inner.left().removeNodeListOrError(key).flatMapValue((Tuple2<Node, NodeList> tuple) -> tuple.right()
                .iter()
                .map(deserializer)
                .collect(new CompileResultCollector<>(new ListCollector<>()))
                .mapValue((List<R> collect) -> new Tuple2<>(tuple.left(), new Tuple2<>(inner.right(), collect))))));
    }

    @Override
    public <R> CompoundDestructor<Tuple2<T, R>> withNode(String key, Function<Node, CompileResult<R>> deserializer) {
        return new CompoundDestructorImpl<Tuple2<T, R>>(this.result.flatMapValue((Tuple2<Node, T> inner) -> inner.left().removeNode(key).flatMapValue((Tuple2<Node, Node> removed) -> {
            Node right = removed.right();
            return deserializer.apply(right).flatMapValue((R newRight) -> CompileResults.Ok(new Tuple2<>(removed.left(), new Tuple2<>(inner.right(), newRight))));
        })));
    }

    @Override
    public <R> CompoundDestructor<Tuple2<T, Option<List<R>>>> withNodeListOptionally(String key, Function<Node, CompileResult<R>> deserializer) {
        return new CompoundDestructorImpl<>(this.result.flatMapValue((inner -> inner.left()
                .removeNodeList(key)
                .map(tuple -> this.mapElements(tuple.left(), tuple.right(), deserializer, inner.right()))
                .orElseGet(() -> CompileResults.Ok(new Tuple2<>(inner.left(), new Tuple2<>(inner.right(), new None<List<R>>())))))));
    }

    private <R> CompileResult<Tuple2<Node, Tuple2<T, Option<List<R>>>>> mapElements(
            Node current,
            NodeList elements,
            Function<Node, CompileResult<R>> deserializer,
            T more
    ) {
        return elements.iter()
                .map(deserializer)
                .collect(new CompileResultCollector<>(new ListCollector<>()))
                .mapValue((List<R> deserialized) -> {
                    Option<List<R>> deserializedOption = new Some<>(deserialized);
                    return new Tuple2<>(current, new Tuple2<T, Option<List<R>>>(more, deserializedOption));
                });
    }
}
