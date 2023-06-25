package com.meti.safe;

import com.meti.InterpretationError;
import com.meti.safe.option.None;
import com.meti.safe.option.Option;
import com.meti.safe.option.Some;
import com.meti.safe.result.Result;
import com.meti.state.Definition;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public record SafeMap(Map<NativeString, Definition> unwrap) {
    public static SafeMap empty() {
        return new SafeMap(new HashMap<>());
    }

    @Deprecated
    public Map<NativeString, Definition> unwrap() {
        return unwrap;
    }

    public SafeMap with(NativeString key, Definition value) {
        unwrap.put(key, value);
        return this;
    }

    public Option<Definition> apply(NativeString key) {
        if (unwrap.containsKey(key)) {
            return new Some<>(unwrap.get(key));
        } else {
            return new None<>();
        }
    }

    public boolean containsKey(NativeString name) {
        return unwrap.containsKey(name);
    }

    public Option<Result<SafeMap, InterpretationError>> updateDefinition(NativeString key, Function<Definition, Result<Definition, InterpretationError>> mapper) {
        return apply(key)
                .map(mapper)
                .map(result -> result.mapValue(value -> with(key, value)));
    }
}