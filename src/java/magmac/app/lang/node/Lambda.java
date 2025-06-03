package magmac.app.lang.node;

public class Lambda {
    protected final LambdaHeader header;
    protected final JavaLambdaContent content;

    public Lambda(LambdaHeader header, JavaLambdaContent content) {
        this.header = header;
        this.content = content;
    }

    public LambdaHeader header() {
        return header;
    }

    public JavaLambdaContent content() {
        return content;
    }
}
