package com.meti.app;

import com.meti.java.JavaSet;
import com.meti.nio.NIOFile;

public interface Gateway {
    JavaSet<NIOFile> collectSources();
}
