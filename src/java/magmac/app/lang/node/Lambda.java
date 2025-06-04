package magmac.app.lang.node;

/**
 * A lambda expression consisting of a header (parameters) and content. For
 * example {@code (x) -> x + 1} would be represented by this node.
 */

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
