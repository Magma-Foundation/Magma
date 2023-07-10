package com.meti.app;

import com.meti.core.Result;
import com.meti.java.JavaString;
import com.meti.java.List;

import java.io.IOException;

public interface Targets {
    Result<NIOTarget, IOException> resolve(List<JavaString> package_, JavaString name);
}
