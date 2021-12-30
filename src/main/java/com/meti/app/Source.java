package com.meti.app;

import com.meti.api.io.IOException;

public interface Source {
    Reference computePackage();

    String read() throws IOException;
}
