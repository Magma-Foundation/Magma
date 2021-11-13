package com.meti.app;

import java.io.IOException;

public interface Path {
    String computeFileName();

    boolean exists();

    Path extendWith(String extension);

    String readAsString() throws IOException;

    void writeAsString(String output) throws IOException;
}
