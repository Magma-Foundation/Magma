package com.meti;

import java.io.IOException;

public interface Script {
    boolean exists();

    String stringifyPackage();

    String asString();

    boolean hasExtensionOf(String extension);

    String read() throws IOException;

    void write(Output output) throws ApplicationException;
}
