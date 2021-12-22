package com.meti;

record Field(String name, String type) {
    public boolean isNamed(String name) {
        return this.name.equals(name);
    }

    boolean isTyped(String type) {
        return this.type.equals(type);
    }
}
