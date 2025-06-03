package magmac.app.lang.node;

import magmac.app.lang.java.JavaLang;

public class LambdaValueContent {
    private final JavaLang.Value value;

    public LambdaValueContent(JavaLang.Value value) {
        this.value = value;
    }

    public JavaLang.Value value() {
        return value;
    }
}
