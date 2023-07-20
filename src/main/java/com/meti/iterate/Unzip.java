package com.meti.iterate;

import java.util.function.Function;
import java.util.function.Predicate;

public interface Unzip<T, P, R> {
    Unzip<T, P, R> on(Predicate<P> predicate, Function<Iterator<T>, Iterator<R>> handler);


    Unzipped<R> onDefault(Function<Iterator<T>, Iterator<R>> handler);
}
