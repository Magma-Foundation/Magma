package magmac.app.compile.node;

import magmac.api.Tuple2;
import magmac.api.collect.list.List;
import magmac.app.compile.error.CompileResult;

import java.util.function.Function;

public interface CompoundDeserializer<T> {
    <R> CompileResult<R> complete(Function<T, R> mapper);

    <R> CompoundDeserializer<Tuple2<T, List<R>>> withNodeList(String key, Function<Node, CompileResult<R>> deserializer);

    <R> CompoundDeserializer<Tuple2<T, R>> withNode(String key, Function<Node, CompileResult<R>> deserializer);
}
