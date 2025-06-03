package magmac.app.lang.node;

import magmac.app.lang.java.JavaFunctionSegmentValue;

public final class JavaYieldNode implements JavaFunctionSegmentValue {
    private final JavaValue value;

    public JavaYieldNode(JavaValue value) {
        this.value = value;
    }

    public JavaValue value() {
        return this.value;
    }
}
