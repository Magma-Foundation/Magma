package com.meti.compile;

import com.meti.api.collect.JavaString;
import com.meti.api.option.Option;

public interface Renderer {
    Option<JavaString> render();
}
