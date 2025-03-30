package magma.compile.context;

import jvm.collect.string.JavaString;

public record StringContext(String value) implements Context {
    private String display0() {
        return value;
    }

    @Override
    public JavaString display() {
        return new JavaString(display0());
    }
}