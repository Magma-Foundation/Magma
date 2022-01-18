package com.meti.app.source;

import java.io.IOException;

public interface Source {
    Packaging computePackage();

    String read() throws IOException;
}
