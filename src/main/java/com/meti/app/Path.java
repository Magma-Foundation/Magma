package com.meti.app;

import java.io.IOException;

public interface Path {
    String computeFileName();

    boolean exists();

    java.nio.file.Path extendWith(String extension);

    java.nio.file.Path getSource();

    String readAsString() throws IOException;

    void writeAsString(String output) throws IOException;
}
