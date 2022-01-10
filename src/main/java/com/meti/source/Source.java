package com.meti.source;

import java.io.IOException;

public interface Source {
    Package computePackage();

    String read() throws IOException;
}
