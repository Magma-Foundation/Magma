package magmac.app.lang.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Destructors;
import magmac.app.lang.java.JavaFunctionSegmentValue;

class JavaContinue implements JavaFunctionSegmentValue {
    public static Option<CompileResult<JavaFunctionSegmentValue>> deserialize(Node node) {
        return Destructors.destructWithType("continue", node).map(deserializer -> deserializer.complete(JavaContinue::new));
    }
}
