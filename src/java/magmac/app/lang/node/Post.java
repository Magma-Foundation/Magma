package magmac.app.lang.node;

import magmac.app.lang.java.JavaLang;

public class Post {
    protected final PostVariant variant;
    protected final JavaLang.Value value;

    public Post(PostVariant variant, JavaLang.Value value) {
        this.variant = variant;
        this.value = value;
    }
}
