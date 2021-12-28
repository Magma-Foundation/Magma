package com.meti;

public interface Path {
    void create() throws IOException;

    void deleteIfExists() throws IOException;

    boolean exists();

    Path resolve(String child);
}
