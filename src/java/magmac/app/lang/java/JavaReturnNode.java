package magmac.app.lang.java;

import magmac.app.lang.node.JavaValue;
import magmac.app.lang.node.ReturnNode;

public final class JavaReturnNode extends ReturnNode<JavaValue> implements JavaFunctionSegmentValue, JavaFunctionSegment {
    public JavaReturnNode(JavaValue child) {
        super(child);
    }
}
