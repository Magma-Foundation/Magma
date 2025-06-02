package magmac.app.lang;

import magmac.app.lang.node.JavaType;

public class JavaLang {
    public interface Caller {
    }

    public interface Argument extends Serializable {
    }

    public record Construction(JavaType type) implements Caller {
    }
}
