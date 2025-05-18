package magma.app.compile.type;

public record VariadicType(Type type) implements Type {
    @Override
    public String generate() {
        return this.type.generate() + "[]";
    }

    @Override
    public boolean isFunctional() {
        return false;
    }

    @Override
    public boolean isVar() {
        return false;
    }

    @Override
    public String generateBeforeName() {
        return "...";
    }
}
