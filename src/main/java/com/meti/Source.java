package com.meti;

import java.io.IOException;

public interface Source {
    Stream<Script> stream() throws IOException;
}
