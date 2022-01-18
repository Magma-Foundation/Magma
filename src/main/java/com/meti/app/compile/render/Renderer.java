package com.meti.app.compile.render;

import com.meti.api.option.Option;
import com.meti.app.compile.CompileException;
import com.meti.app.compile.node.Text;

public interface Renderer {
    Option<Text> render() throws CompileException;
}
