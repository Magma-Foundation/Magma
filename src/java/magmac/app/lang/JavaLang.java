package magmac.app.lang;

import magmac.app.lang.node.JavaRootSegment;
import magmac.app.lang.node.JavaRootSegments;
import magmac.app.lang.node.RootDeserializer;

public class JavaLang {
    public static RootDeserializer<JavaRootSegment> createDeserializer() {
        return new RootDeserializer<JavaRootSegment>(JavaRootSegments::deserialize);
    }
}
