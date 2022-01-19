package com.meti.app.compile.stage;

import com.meti.api.option.Option;
import com.meti.app.compile.node.Node;

public interface Transformer {
    Option<Node> transform() throws CompileException;
}
