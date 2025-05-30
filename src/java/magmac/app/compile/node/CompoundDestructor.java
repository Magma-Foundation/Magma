package magmac.app.compile.node;

import magmac.api.Option;
import magmac.api.Tuple2;
import magmac.api.collect.list.List;
import magmac.app.compile.error.CompileResult;

import java.util.function.Function;

public interface CompoundDestructor<T> {
    <R> CompileResult<R> complete(Function<T, R> mapper);

    <R> CompoundDestructor<Tuple2<T, List<R>>> withNodeList(String key, Function<Node, CompileResult<R>> deserializer);

    <R> CompoundDestructor<Tuple2<T, R>> withNode(String key, Function<Node, CompileResult<R>> deserializer);

    <R> CompoundDestructor<Tuple2<T, Option<List<R>>>> withNodeListOptionally(String key, Function<Node, CompileResult<R>> deserializer);
}
