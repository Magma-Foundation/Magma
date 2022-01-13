package com.meti.compile;

import com.meti.core.F1;
import com.meti.core.F2;
import com.meti.option.Supplier;

import java.util.HashMap;
import java.util.Map;

public record JavaMap<A, B>(Map<A, B> map) {
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

    public B get(A key) {
        return map.get(key);
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
}
