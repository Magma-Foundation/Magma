package com.meti.compile;

import com.meti.compile.node.Node;
import com.meti.option.Option;

public interface Transformer {
    Option<Node> transform() throws CompileException;
}
