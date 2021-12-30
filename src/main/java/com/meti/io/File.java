package com.meti.io;

import com.meti.io.IOException;

public interface File {
    String readAsString() throws IOException;

    void writeAsString(String output) throws IOException;
}
