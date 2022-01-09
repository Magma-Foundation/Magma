package com.meti.io;

import com.meti.option.Option;

import java.io.IOException;
import java.util.stream.Stream;

public interface Path {
    String asString();

    String computeFileNameWithoutExtension();

    File ensureAsFile() throws IOException;

    Option<File> existingAsFile();

    boolean exists();

    boolean hasExtensionOf(String expected);

    NIOPath resolveChild(String name);

    Stream<String> streamNames();

}
