package com.meti;

record None() implements Option {
    @Override
    public boolean isPresent() {
        return false;
    }

    @Override
    public String unwrapOrPanic() {
        throw new UnsupportedOperationException();
    }
}
