package com.meti.io;

import java.io.IOException;

public interface File extends Path {
    String readAsString() throws IOException;

    Path writeAsString(String value) throws IOException;
}
