package magmac.app.lang.java;

import magmac.app.lang.node.ReturnNode;

public final class JavaReturnNode extends ReturnNode<JavaLang.JavaValue> implements JavaFunctionSegmentValue, JavaFunctionSegment {
    public JavaReturnNode(JavaLang.JavaValue child) {
        super(child);
    }
}
