package com.meti.compile;

import com.meti.api.collect.JavaString;
import com.meti.api.option.Option;
import com.meti.api.result.Result;

public interface Renderer {
    Option<Result<JavaString, CompileException>> render();
}
