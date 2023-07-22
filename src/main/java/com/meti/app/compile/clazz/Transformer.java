package com.meti.app.compile.clazz;

import com.meti.app.compile.Node;
import com.meti.core.Option;

public interface Transformer {
    Option<Node> transform();
}
