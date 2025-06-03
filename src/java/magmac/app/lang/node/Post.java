package magmac.app.lang.node;

import magmac.app.lang.java.JavaLang;

public class Post {
    protected final PostVariant variant;
    protected final JavaLang.JavaValue value;

    public Post(PostVariant variant, JavaLang.JavaValue value) {
        this.variant = variant;
        this.value = value;
    }
}
