package magmac.app.compile.node;

import magmac.api.collect.list.List;
import magmac.app.compile.error.CompileResult;
import magmac.app.lang.node.Deserializer;

import java.util.function.Function;
import java.util.function.Supplier;

public interface InitialDestructor {
    <T> CompoundDeserializer<List<T>> withNodeList(String key, Deserializer<T> deserializer);

    CompoundDeserializer<String> withString(String key);

    <T> CompoundDeserializer<T> withNode(String key, Function<Node, CompileResult<T>> deserializer);

    <T> CompileResult<T> complete(Supplier<T> supplier);
}
