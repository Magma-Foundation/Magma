package com.meti;

interface Option {
    boolean isPresent();

    String unwrapOrPanic();
}
