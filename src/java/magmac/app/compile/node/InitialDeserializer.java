package magmac.app.compile.node;

import magmac.api.collect.list.List;
import magmac.app.compile.error.CompileResult;

import java.util.function.Function;

public interface InitialDeserializer {
    <T> CompoundDeserializer<List<T>> nodeList(String key, Function<Node, CompileResult<T>> deserializer);

    CompoundDeserializer<String> string(String key);

    <T> CompoundDeserializer<T> node(String key, Function<Node, CompileResult<T>> deserializer);
}
