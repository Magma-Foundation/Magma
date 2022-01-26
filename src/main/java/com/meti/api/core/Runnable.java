package com.meti.api.core;

public interface Runnable<E extends Exception> {
    void run() throws E;
}
