package com.meti.compile;

import com.meti.core.F2;

import java.util.HashMap;
import java.util.Map;

public record JavaMap<A, B>(Map<A, B> self) {
    JavaMap<A, Output<B>> mapValues(F2<A, B, Output<B>, CompileException> mapper) throws CompileException {
        var outputMap = new HashMap<A, Output<B>>();
        for (A aA : getSelf().keySet()) {
            var input = mapper.apply(aA, getSelf().get(aA));
            outputMap.put(aA, input);
        }
        return new JavaMap<>(outputMap);
    }

    public Map<A, B> getSelf() {
        return self;
    }
}
