package com.meti.io;

import java.io.IOException;
import java.util.stream.Stream;

public interface Directory extends Path {
    void deleteAsDirectory() throws IOException;

    Stream<Path> walk() throws IOException;
}
