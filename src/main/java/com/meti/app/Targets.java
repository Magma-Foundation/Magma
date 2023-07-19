package com.meti.app;

import com.meti.core.Result;
import com.meti.java.List;
import com.meti.java.String_;

import java.io.IOException;

public interface Targets {
    Result<NIOTarget, IOException> resolve(List<String_> package_, String_ name);
}
