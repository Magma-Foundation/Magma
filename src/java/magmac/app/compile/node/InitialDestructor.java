package magmac.app.compile.node;

import magmac.api.collect.list.List;
import magmac.app.compile.error.CompileResult;
import magmac.app.lang.node.Deserializer;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Starting point for destructuring a {@link Node}.
 */
public interface InitialDestructor {
    /**
     * Reads a list under {@code key} using the provided deserializer.
     */
    <T> CompoundDestructor<List<T>> withNodeList(String key, Deserializer<T> deserializer);

    /**
     * Reads a plain string value under {@code key}.
     */
    CompoundDestructor<String> withString(String key);

    /**
     * Reads a child node under {@code key} using the supplied deserializer.
     */
    <T> CompoundDestructor<T> withNode(String key, Function<Node, CompileResult<T>> deserializer);

    /**
     * Finishes building the value using the gathered pieces.
     */
    <T> CompileResult<T> complete(Supplier<T> supplier);
}
