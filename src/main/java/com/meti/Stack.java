package com.meti;

import java.util.HashMap;
import java.util.function.Function;

public record Stack(SafeMap definitions) {
    public Stack() {
        this(new SafeMap(new HashMap<>()));
    }

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

    public boolean isDefined(NativeString name) {
        return definitions.containsKey(name);
    }

    public Option<Definition> apply(NativeString name) {
        return definitions.apply(name);
    }
}