package com.meti;

import java.util.Map;

public record SafeMap(Map<NativeString, Definition> unwrap) {
    @Deprecated
    public Map<NativeString, Definition> unwrap() {
        return unwrap;
    }

    SafeMap with(NativeString key, Definition value) {
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
}