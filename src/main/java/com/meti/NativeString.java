package com.meti;

record NativeString(String unwrap) {
    static NativeString from(String unwrap) {
        return new NativeString(unwrap);
    }

    public String unwrap() {
        return unwrap;
    }

    public Option<Integer> firstIndexOfChar(char c) {
        var index = unwrap.indexOf(c);
        return index == -1 ? new None<>() : new Some<>(index);
    }

    public Option<NativeString> slice(int start, int end) {
        if (start >= 0 && end >= start) {
            return new Some<>(from(unwrap.substring(start, end)));
        } else {
            return new None<>();
        }
    }

    public NativeString strip() {
        return from(unwrap.strip());
    }

    public int length() {
        return unwrap.length();
    }

    public boolean startsWith(NativeString other) {
        return unwrap.startsWith(other.unwrap);
    }
}
