package com.meti;

record Some(String value) implements Option {
    @Override
    public boolean isPresent() {
        return true;
    }

    @Override
    public String unwrapOrPanic() {
        return value;
    }
}
