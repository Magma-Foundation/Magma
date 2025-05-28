package magmac.app.lang.java;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.root.JavaRootSegment;

import java.util.function.Supplier;

public class JavaRootSegments {
    public static CompileResult<JavaRootSegment> deserialize(Node node) {
        return node.destroy().complete(new Supplier<JavaRootSegment>() {
            @Override
            public JavaRootSegment get() {
                return new JavaRootSegment() {
                };
            }
        });
    }

    public static Node serialize(JavaRootSegment segment) {
        return null;
    }
}
