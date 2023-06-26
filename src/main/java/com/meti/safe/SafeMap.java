package com.meti.safe;

import com.meti.safe.iter.Iterator;
import com.meti.safe.iter.NativeIterators;
import com.meti.safe.option.None;
import com.meti.safe.option.Option;
import com.meti.safe.option.Some;
import com.meti.safe.result.Result;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public record SafeMap<A, B>(Map<A, B> internalMap) {
    public static <A, B> SafeMap<A, B> empty() {
        return new SafeMap<>(new HashMap<>());
    }

    public SafeMap<A, B> with(A key, B value) {
        internalMap.put(key, value);
        return this;
    }

    public Option<B> apply(A key) {
        if (internalMap.containsKey(key)) {
            return Some.apply(internalMap.get(key));
        } else {
            return None.apply();
        }
    }

    public <E extends Throwable> Option<Result<SafeMap<A, B>, E>> mapValueWithResult(A key, Function<B, Result<B, E>> mapper) {
        return apply(key)
                .map(mapper)
                .map(result -> result.mapValue(value -> with(key, value)));
    }

    public boolean isEmpty() {
        return internalMap.isEmpty();
    }

    public Iterator<Tuple2<A, B>> iter() {
        return NativeIterators.fromList(internalMap.entrySet()
                .stream()
                .map(entry -> new Tuple2<>(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList()));
    }

    public int size() {
        return internalMap.size();
    }
}