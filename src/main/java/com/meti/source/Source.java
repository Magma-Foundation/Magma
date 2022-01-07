package com.meti.source;

import java.io.IOException;
import java.util.stream.Stream;

public interface Source {
    String computeName();

    Stream<String> computePackage();

    String read() throws IOException;
}
