package magmac.app.compile.node;

import magmac.api.collect.list.List;
import magmac.app.compile.error.CompileResult;

import java.util.function.Function;

public interface InitialDeserializer {
    <T> CompoundDeserializer<List<T>> withNodeList(String key, Function<Node, CompileResult<T>> deserializer);

    CompoundDeserializer<String> withString(String key);

    <T> CompoundDeserializer<T> withNode(String key, Function<Node, CompileResult<T>> deserializer);
}
