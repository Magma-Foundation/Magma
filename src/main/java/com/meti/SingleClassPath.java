package com.meti;

public record SingleClassPath(String name) implements ClassPath {
    @Override
    public boolean isDefined(String name) {
        return this.name.equals(name);
    }
}
