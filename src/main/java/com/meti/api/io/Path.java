package com.meti.api.io;

import com.meti.api.option.Option;

public interface Path {
    String computeFileNameWithoutExtension();

    File create() throws IOException;

    void deleteIfExists() throws IOException;

    Option<File> existing();

    boolean exists();

    Path resolveChild(String child);

    Path resolveSibling(String sibling);
}
