package com.meti.app.io;

import com.meti.core.Result;
import com.meti.java.Set;

import java.io.IOException;

public interface Sources {
    Result<Set<NIOSource>, IOException> collect();
}
