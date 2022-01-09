package com.meti.io;

import com.meti.option.Option;

import java.io.IOException;
import java.util.stream.Stream;

public interface Path {
    String asString();

    String computeFileNameWithoutExtension();

    void createAsDirectory() throws IOException;

    File createAsFile() throws IOException;

    void ensureAsDirectory() throws IOException;

    File ensureAsFile() throws IOException;

    Option<File> existingAsFile();

    boolean exists();

    boolean hasExtensionOf(String expected);

    Path relativize(Path path);

    NIOPath resolveChild(String name);

    Stream<String> streamNames();

    Path toAbsolutePath();
}
