package magmac.app.lang;

import magmac.app.lang.node.JavaType;

public class JavaLang {
    public interface Caller {
    }

    public record Construction(JavaType type) implements Caller {
    }
}
