package com.meti;

import static com.meti.Options.Some;

record NativeString(char[] values) {
    static String toNative(NativeString s) {
        return new String(s.values);
    }

    static NativeString fromNative(String s) {
        return new NativeString(s.toCharArray());
    }

    public Option<Integer> indexOf(char c) {
        for (int i = 0; i < values.length; i++) {
            if (values[i] == c) {
                return Some(i);
            }
        }

        return Options.None();
    }

    public NativeString slice(int from, int to) {
        var copy = new char[to - from];
        if (to - from >= 0) System.arraycopy(this.values, from, copy, from, to - from);
        return new NativeString(copy);
    }

    public int length() {
        return values.length;
    }

    public NativeString concat(NativeString other) {
        var copy = new char[length() + other.length()];
        System.arraycopy(values, 0, copy, 0, length());

        for (int i = 0; i < other.length(); i++) {
            copy[i + length()] = other.apply(i).unwrapOrElse('\0');
        }

        return new NativeString(copy);
    }

    private Option<Character> apply(int index) {
        if (index < 0) return Options.None();
        if (index >= length()) return Options.None();
        return Some(values[index]);
    }

    public boolean endsWith(NativeString other) {
        return Iterables.range(0, other.length()).match(
                integerIterable -> this.iter().take(other.length() - this.length()).match(
                        sliced -> sliced.zip(other.iter()).allMatch(
                                tuple -> tuple.value0().equals(tuple.value1())),
                        () -> false),
                () -> false);
    }

    private Iterable<Character> iter() {
        return new IndexedIterable<>() {
            @Override
            protected int size() {
                return values.length;
            }

            @Override
            protected Character getElement(int index) {
                return values[index];
            }
        };
    }
}
