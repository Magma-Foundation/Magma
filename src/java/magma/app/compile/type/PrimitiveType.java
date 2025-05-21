package magma.app.compile.type;

public enum PrimitiveType implements Type {
    String("string"),
    Number("number"),
    Boolean("boolean"),
    Var("var"),
    Void("void"),
    Unknown("unknown");

    private final String value;

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
