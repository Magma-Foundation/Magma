package com.meti;

public class EmptyClassPath implements ClassPath {
    public EmptyClassPath() {
    }

    @Override
    public boolean isDefined(String name) {
        return false;
    }
}