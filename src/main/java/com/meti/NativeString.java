package com.meti;

record NativeString(String unwrap) {
    public String unwrap() {
        return unwrap;
    }

    public Option<Integer> firstIndexOfChar(char c) {
        var index = unwrap.indexOf(c);
        if (index == -1) return new Some<>(index);
        else return new None<>();
    }

    public Option<NativeString> slice(int start, int end) {
        if (start >= 0 && end >= start) {
            return new Some<>(new NativeString(unwrap.substring(start, end)));
        } else {
            return new None<>();
        }
    }

    public NativeString strip() {
        return new NativeString(unwrap.strip());
    }

    public int length() {
        return unwrap.length();
    }

    public boolean startsWith(NativeString other) {
        return unwrap.startsWith(other.unwrap);
    }
}
