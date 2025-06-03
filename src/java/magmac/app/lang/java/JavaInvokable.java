package magmac.app.lang.java;

import magmac.api.collect.list.List;
import magmac.app.lang.node.JavaValue;

public final class JavaInvokable extends Invokable<Lang.JavaCaller, Lang.JavaArgument> implements JavaValue, JavaFunctionSegmentValue {
    public JavaInvokable(Lang.JavaCaller caller, List<Lang.JavaArgument> arguments) {
        super(caller, arguments);
    }
}
