package magmac.app.lang.node;

public final class JavaConditional extends Conditional<JavaValue> implements JavaBlockHeader {
    public JavaConditional(ConditionalType type, JavaValue condition) {
        super(type, condition);
    }
}
