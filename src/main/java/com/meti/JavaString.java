package com.meti;

class JavaString {
    private final String value;

    public JavaString(String value) {
        this.value = value;
    }

    public Option<Integer> indexOf(String slice) {
        var index = value.indexOf(slice);
        if (index == -1) return new None<>();
        else return new Some<>(index);
    }

    public Result<JavaString, BoundsError> slice(int start, int end) {
        if (start < 0) return new ErrorResult<>(new StartNegativeError(start));
        if (end < 0) return new ErrorResult<>(new EndNegativeError(end));

        if (start > value.length()) return new ErrorResult<>(new StartExceedsError(start));
        if (end > value.length()) return new ErrorResult<>(new EndExceedsError(end));

        return new ValueOk<>(new JavaString(value.substring(start, end)));
    }

    public JavaString add(String other) {
        return new JavaString(value + other);
    }

    public String asString() {
        return value;
    }
}
