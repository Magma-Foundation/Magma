package magmac.app.lang.node;

import magmac.app.lang.common.Access;

public final class JavaAccess extends Access<JavaAccessType, JavaValue> implements JavaValue {
    public JavaAccess(JavaAccessType type, JavaValue receiver, String property) {
        super(type, receiver, property);
    }
}
