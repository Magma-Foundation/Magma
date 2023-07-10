package com.meti.app;

import com.meti.core.Result;
import com.meti.java.JavaList;
import com.meti.java.JavaString;

import java.io.IOException;

public interface Targets {
    Result<NIOTarget, IOException> resolve(JavaList<JavaString> package_, JavaString name);
}
