package com.meti.compile.render;

import com.meti.compile.attribute.AttributeException;
import com.meti.compile.node.Text;
import com.meti.option.Option;

public interface Renderer {
    Option<Text> render() throws AttributeException;
}
