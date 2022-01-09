package com.meti.io;

import java.io.IOException;

public interface File extends Path {
    void delete() throws IOException;

    String readAsString() throws IOException;

    Path writeAsString(String value) throws IOException;
}
