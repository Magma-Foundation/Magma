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

    public Option<NativeString> slice(int from, int to) {
        return Iterables.range(from, to)
                .map(iterable -> iterable
                        .map(index -> values[index])
                        .collect(Iterables.toArray())
                        .toArray(Character[]::new))
                .map(NativeArrays::unbox)
                .map(NativeString::new);
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
        var start = this.length() - other.length();
        var end = this.length();
        return slice(start, end)
                .map(slice -> slice.equalsTo(other))
                .unwrapOrElse(false);
    }

    private boolean equalsTo(NativeString other) {
        return iter().zip(other.iter()).allMatch(values -> values.value0() == values.value1());
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
