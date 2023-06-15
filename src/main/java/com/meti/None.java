package com.meti;

class None implements Option {
    @Override
    public String unwrapOrPanic() {
        throw new RuntimeException("Empty option.");
    }
}
