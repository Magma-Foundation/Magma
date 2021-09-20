package com.meti.source;

import com.meti.Script;
import com.meti.Stream;

import java.io.IOException;

public interface Source {
    Stream<Script> stream() throws IOException;
}
