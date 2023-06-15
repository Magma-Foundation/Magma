package com.meti;

record Some(String value) implements Option {
    @Override
    public String unwrapOrPanic() {
        return value;
    }
}
