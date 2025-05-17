package magma.app.compile.type;

import magma.api.Type;

public enum PrimitiveType implements Type {
    String("string"),
    Number("number"),
    Var("var"),
    Void("void"),
    Unknown("unknown"),
    I8("I8"),
    I32("I32");

    private final String value;

    PrimitiveType(final String value) {
        this.value = value;
    }

    @Override
    public String generate() {
        return this.value;
    }

    @Override
    public boolean isFunctional() {
        return false;
    }

    @Override
    public boolean isVar() {
        return PrimitiveType.Var == this;
    }

    @Override
    public String generateBeforeName() {
        return "";
    }
}
