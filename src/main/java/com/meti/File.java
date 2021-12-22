package com.meti;

import java.io.IOException;

public interface File {
    PathWrapper asPath();

    String readString() throws IOException;

    File writeString(String value) throws IOException;
}
