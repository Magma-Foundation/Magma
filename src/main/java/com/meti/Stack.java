package com.meti;

import java.util.function.Function;

public record Stack(SafeMap definitions) {
    public Result<Stack, InterpretationError> mapDefinition(NativeString name, Function<Definition, Result<Definition, InterpretationError>> mapper) {
        return definitions.apply(name)
                .map(mapper)
                .unwrapOrThrow(() -> new InterpretationError("No value present"))
                .mapValueToResult(value -> value)
                .mapValue(found -> definitions.with(name, found))
                .mapValue(Stack::new);
    }

    public Stack define(Definition definition) {
        return new Stack(definitions.with(definition.name(), definition));
    }
}