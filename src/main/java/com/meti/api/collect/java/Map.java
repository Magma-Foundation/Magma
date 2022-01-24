package com.meti.api.collect.java;

import com.meti.api.core.F1;
import com.meti.api.core.F2;
import com.meti.api.option.Option;
import com.meti.api.option.Supplier;

import java.util.Set;

public interface Map<A, B> {
    Option<B> applyOptionally(A key);

    <E extends Exception> Map<A, B> ensure(A key, Supplier<B, E> generator) throws E;

    Set<A> keys();

    <C extends Exception> Map<A, B> mapValue(A key, F1<B, B, C> mapper) throws C;

    <C, D extends Exception> JavaMap<A, C> mapValues(F2<A, B, C, D> mapper) throws D;

    B orElse(A key, B value);

    JavaMap<A, B> put(A key, B value);
}
