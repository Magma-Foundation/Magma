package com.meti.app.compile;

import com.meti.api.collect.CollectionException;
import com.meti.api.collect.Stream;
import com.meti.app.compile.clang.CFormat;
import com.meti.app.compile.node.Node;

public interface Divider {
    Stream<Node> apply(CFormat format) throws CollectionException;

    Divider divide(Node node) throws CompileException;

    Stream<CFormat> stream();
}
