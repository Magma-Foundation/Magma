package magma.compile.context;

import jvm.collect.string.JavaString;
import magma.collect.string.String_;

public record StringContext(String value) implements Context {
    private String display0() {
        return value;
    }

    @Override
    public String_ display() {
        return new JavaString(display0());
    }
}