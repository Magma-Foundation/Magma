package com.meti.compile.render;

import com.meti.compile.CompileException;
import com.meti.compile.node.Text;
import com.meti.option.Option;

public interface Renderer {
    Option<Text> render() throws CompileException;
}
