package com.meti.app.compile.render;

import com.meti.api.option.Option;
import com.meti.app.compile.CompileException;
import com.meti.app.compile.text.Output;

public interface Renderer {
    Option<Output> render() throws CompileException;
}
