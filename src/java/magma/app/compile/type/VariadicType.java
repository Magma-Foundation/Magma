package magma.app.compile.type;

import magma.api.Type;

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
