package com.meti.api.io;

public interface File extends Path {
    String readAsString() throws IOException;

    File writeAsString(String output) throws IOException;
}
