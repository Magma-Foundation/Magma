package com.meti;

record JavaString(String value) {

    public Option<Index> firstIndexOfChar(char c) {
        var index = this.value.indexOf(c);
        return index == -1
                ? new None<>()
                : new Some<>(new Index(index));
    }

    public JavaString sliceToEnd(Index index) {
        return new JavaString(value.substring(0, index.unwrap()));
    }

    public JavaString concat(String value) {
        return new JavaString(this.value + value);
    }

    public String unwrap() {
        return value;
    }
}
