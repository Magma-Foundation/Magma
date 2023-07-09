package com.meti.app;

import com.meti.core.Result;
import com.meti.java.JavaSet;

import java.io.IOException;

public interface Sources {
    Result<JavaSet<NIOSource>, IOException> collect();
}
