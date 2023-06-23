package com.meti;

import java.util.function.Predicate;

record NativeString(String internalValue) {
    static NativeString from(String unwrap) {
        return new NativeString(unwrap);
    }

    Option<Integer> firstIndexOfCharByPredicate(Predicate<Character> predicate) {
        return Iterators.fromRange(0, length()).zip(iter())
                .map(tuple -> predicate.test(tuple.b()) ? new Some<>(tuple.a()) : new None<Integer>())
                .flatMap(Iterators::fromOption)
                .head();
    }

    public String internalValue() {
        return internalValue;
    }

    public Option<Integer> firstIndexOfChar(char c) {
        var index = internalValue.indexOf(c);
        return index == -1 ? new None<>() : new Some<>(index);
    }

    public Option<NativeString> slice(int start, int end) {
        if (start >= 0 && end >= start) {
            return new Some<>(from(internalValue.substring(start, end)));
        } else {
            return new None<>();
        }
    }

    public NativeString strip() {
        return from(internalValue.strip());
    }

    public int length() {
        return internalValue.length();
    }

    public boolean startsWith(NativeString other) {
        return internalValue.startsWith(other.internalValue);
    }

    public Iterator<Character> iter() {
        return new IndexedIterator<>() {
            @Override
            protected int size() {
                return internalValue.length();
            }

            @Override
            protected Option<Character> apply(int counter) {
                return new Some<>(internalValue.charAt(counter));
            }
        };
    }

    public boolean equalsTo(NativeString other) {
        return length() == other.length() &&
               iter().zip(other.iter()).allMatch(tuple -> tuple.a() == tuple.b());
    }
}