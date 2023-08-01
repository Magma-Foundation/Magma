package com.meti.app.compile;

import com.meti.core.Option;
import com.meti.core.Result;
import com.meti.java.String_;

public interface Renderer {
    Option<Result<String_, CompileException>> render();
}
