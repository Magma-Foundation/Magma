package magma.app;

import magma.api.Type;

record VarArgs(Type type) implements Type {
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
