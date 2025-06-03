package magmac.app.lang.node;

public class Post {
    protected final PostVariant variant;
    protected final JavaValue value;

    public Post(PostVariant variant, JavaValue value) {
        this.variant = variant;
        this.value = value;
    }
}
