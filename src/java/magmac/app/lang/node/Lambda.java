package magmac.app.lang.node;

import magmac.app.lang.java.JavaLang;

public class Lambda {
    private final JavaLang.JavaLambdaHeader header;
    private final JavaLang.JavaLambdaContent content;

    public Lambda(JavaLang.JavaLambdaHeader header, JavaLang.JavaLambdaContent content) {
        this.header = header;
        this.content = content;
    }

    public JavaLang.JavaLambdaHeader header() {
        return header;
    }

    public JavaLang.JavaLambdaContent content() {
        return content;
    }
}
