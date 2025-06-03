package magmac.app.lang.node;

public class LambdaValueContent {
    protected final JavaValue value;

    public LambdaValueContent(JavaValue value) {
        this.value = value;
    }

    public JavaValue value() {
        return value;
    }
}
