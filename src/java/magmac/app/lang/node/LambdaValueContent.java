package magmac.app.lang.node;

import magmac.app.lang.java.JavaLang;

public class LambdaValueContent {
    protected final JavaLang.JavaValue value;

    public LambdaValueContent(JavaLang.JavaValue value) {
        this.value = value;
    }

    public JavaLang.JavaValue value() {
        return value;
    }
}
