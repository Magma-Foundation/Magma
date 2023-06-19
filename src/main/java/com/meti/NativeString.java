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

    public NativeString slice(int start, int end) {
        return new NativeString(unwrap.substring(start, end));
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
