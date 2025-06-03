package magmac.app.lang.node;

public final class JavaLambda extends Lambda implements JavaValue {
    public JavaLambda(LambdaHeader header, JavaLambdaContent content) {
        super(header, content);
    }
}
