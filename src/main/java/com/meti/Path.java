package com.meti;

public interface Path {
    String computeFileNameWithoutExtension();

    void create() throws IOException;

    void deleteIfExists() throws IOException;

    boolean exists();

    Path resolveChild(String child);

    Path resolveSibling(String sibling);
}
