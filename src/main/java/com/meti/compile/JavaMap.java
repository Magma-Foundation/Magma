package com.meti.compile;

import com.meti.core.F1;
import com.meti.core.F2;
import com.meti.option.Supplier;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class JavaMap<A, B> {
    private final Map<A, B> map;

    public JavaMap() {
        this(new HashMap<>());
    }

    public JavaMap(Map<A, B> map) {
        this.map = map;
    }

    <E extends Exception> JavaMap<A, B> ensure(A key, Supplier<B, E> generator) throws E {
        if (!map.containsKey(key)) {
            var value = generator.get();
            var copy = new HashMap<>(map);
            copy.put(key, value);
            return new JavaMap<>(copy);
        } else {
            return this;
        }
    }

    public B apply(A key) {
        return map.get(key);
    }

    public boolean hasKey(A key) {
        return map.containsKey(key);
    }

    public Set<A> keys() {
        return map.keySet();
    }

    <C extends Exception> JavaMap<A, B> mapValue(A key, F1<B, B, C> mapper) throws C {
        var outputMap = new HashMap<>(map);
        for (A aA : getMap().keySet()) {
            var input = mapper.apply(getMap().get(aA));
            outputMap.put(aA, input);
        }
        return new JavaMap<>(outputMap);
    }

    public Map<A, B> getMap() {
        return map;
    }

    <C, D extends Exception> JavaMap<A, C> mapValues(F2<A, B, C, D> mapper) throws D {
        var outputMap = new HashMap<A, C>();
        for (A aA : getMap().keySet()) {
            var input = mapper.apply(aA, getMap().get(aA));
            outputMap.put(aA, input);
        }
        return new JavaMap<>(outputMap);
    }

    public JavaMap<A, B> put(A key, B value) {
        var copy = new HashMap<>(map);
        copy.put(key, value);
        return new JavaMap<>(copy);
    }
}
