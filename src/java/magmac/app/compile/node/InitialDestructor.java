package magmac.app.compile.node;

import magmac.api.collect.list.List;
import magmac.app.compile.error.CompileResult;
import magmac.app.lang.node.Deserializer;

import java.util.function.Function;
import java.util.function.Supplier;

public interface InitialDestructor {
    <T> CompoundDestructor<List<T>> withNodeList(String key, Deserializer<T> deserializer);

    CompoundDestructor<String> withString(String key);

    <T> CompoundDestructor<T> withNode(String key, Function<Node, CompileResult<T>> deserializer);

    <T> CompileResult<T> complete(Supplier<T> supplier);
}
