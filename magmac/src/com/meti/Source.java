package com.meti;

import java.io.IOException;
import java.util.Set;

public interface Source {
    Set<Location> list() throws IOException;
}
