package com.meti.app.compile;

import com.meti.api.option.Option;
import com.meti.app.compile.node.Node;

public interface Transformer {
    Option<Node> transform() throws CompileException;
}
