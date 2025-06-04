package magmac.app.compile.node;

import magmac.api.Option;
import magmac.api.Tuple2;
import magmac.api.collect.list.List;
import magmac.app.compile.error.CompileResult;

import java.util.function.Function;

/**
 * Helper used to destructure nested {@link Node} structures step by step.
 */
public interface CompoundDestructor<T> {
    /**
     * Completes the destruction producing a value.
     */
    <R> CompileResult<R> complete(Function<T, R> mapper);

    /**
     * Extracts a list under {@code key} using the given deserializer.
     */
    <R> CompoundDestructor<Tuple2<T, List<R>>> withNodeList(String key, Function<Node, CompileResult<R>> deserializer);

    /**
     * Extracts a child node under {@code key} using the given deserializer.
     */
    <R> CompoundDestructor<Tuple2<T, R>> withNode(String key, Function<Node, CompileResult<R>> deserializer);

    /**
     * Optionally extracts a child node when present.
     */
    <R> CompoundDestructor<Tuple2<T, Option<R>>> withNodeOptionally(String key, Function<Node, CompileResult<R>> deserializer);

    /**
     * Optionally extracts a list when present.
     */
    <R> CompoundDestructor<Tuple2<T, Option<List<R>>>> withNodeListOptionally(String key, Function<Node, CompileResult<R>> deserializer);
}
