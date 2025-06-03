package magmac.app.lang.java;

import magmac.api.collect.list.List;
import magmac.app.lang.node.JavaValue;
import magmac.app.lang.node.TypeScriptValue;

public final class JavaInvokable extends Invokable<JavaCaller, JavaArgument> implements JavaValue, JavaFunctionSegmentValue, TypeScriptValue {
    public JavaInvokable(JavaCaller caller, List<JavaArgument> arguments) {
        super(caller, arguments);
    }
}
