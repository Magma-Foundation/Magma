package com.meti.clang;

import com.meti.option.Option;
import com.meti.output.Output;

public interface Renderer {
    Option<Output> render();
}
