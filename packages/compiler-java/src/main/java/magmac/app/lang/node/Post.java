package magmac.app.lang.node;

/**
 * Base class for postfix operations like {@code value++}. The variant stores
 * the operator while {@code value} holds the expression being operated on.
 */

import magmac.app.lang.java.JavaLang;

public class Post {
    protected final PostVariant variant;
    protected final JavaLang.Value value;

    public Post(PostVariant variant, JavaLang.Value value) {
        this.variant = variant;
        this.value = value;
    }
}
