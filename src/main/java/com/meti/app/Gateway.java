package com.meti.app;

import com.meti.core.Result;
import com.meti.java.JavaSet;
import com.meti.nio.NIOFile;

import java.io.IOException;

public interface Gateway {
    Result<JavaSet<NIOFile>, IOException> collectSources();
}
