package magmac.app.lang.node;

import magmac.app.lang.java.JavaFunctionSegmentValue;

public final class JavaReturnNode extends ReturnNode<JavaValue> implements JavaFunctionSegmentValue, JavaFunctionSegment {
    public JavaReturnNode(JavaValue child) {
        super(child);
    }
}
