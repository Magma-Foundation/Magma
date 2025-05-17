package magma.app.compile.type;

import magma.api.Type;

public record ArrayType(Type child) implements Type {
    @Override
    public String generate() {
        return this.child.generate() + "[]";
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
        return "";
    }
}
