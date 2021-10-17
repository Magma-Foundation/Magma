package com.meti.api;

public interface Path {
    Path absolute();

    void ensureAsFile(String defaultContent) throws IOException;

    String readContentAsString() throws IOException;
}
