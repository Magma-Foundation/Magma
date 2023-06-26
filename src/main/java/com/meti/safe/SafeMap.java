package com.meti.safe;

import com.meti.InterpretationError;
import com.meti.feature.definition.ImplicitDefinition;
import com.meti.safe.option.None;
import com.meti.safe.option.Option;
import com.meti.safe.option.Some;
import com.meti.safe.result.Result;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public record SafeMap(Map<NativeString, ImplicitDefinition> unwrap) {
    public static SafeMap empty() {
        return new SafeMap(new HashMap<>());
    }

    @Deprecated
    public Map<NativeString, ImplicitDefinition> unwrap() {
        return unwrap;
    }

    public SafeMap with(NativeString key, ImplicitDefinition value) {
        unwrap.put(key, value);
        return this;
    }

    public Option<ImplicitDefinition> apply(NativeString key) {
        if (unwrap.containsKey(key)) {
            return Some.apply(unwrap.get(key));
        } else {
            return None.apply();
        }
    }

    public Option<Result<SafeMap, InterpretationError>> updateDefinition(NativeString key, Function<ImplicitDefinition, Result<ImplicitDefinition, InterpretationError>> mapper) {
        return apply(key)
                .map(mapper)
                .map(result -> result.mapValue(value -> with(key, value)));
    }
}