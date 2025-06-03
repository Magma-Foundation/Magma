package magmac.app.lang.java;

import magmac.app.lang.common.FunctionStatement;

public final class JavaFunctionStatement extends FunctionStatement<JavaFunctionSegmentValue> implements JavaFunctionSegment {
    public JavaFunctionStatement(JavaFunctionSegmentValue child) {
        super(child);
    }
}
