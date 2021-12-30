package com.meti;

public interface File {
    String readAsString() throws IOException;

    void writeAsString(String output) throws IOException;
}
