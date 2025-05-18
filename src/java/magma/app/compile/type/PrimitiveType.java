package magma.app.compile.type;

import magma.app.Main;

public enum PrimitiveType implements Type {
    String("string"),
    Number("number"),
    Boolean("boolean"),
    Var("var"),
    Void("void"),
    Unknown("unknown");

    final String value;

    PrimitiveType(String value) {
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
