package com.meti.compile;

import com.meti.collect.CollectionException;
import com.meti.collect.Stream;
import com.meti.compile.clang.CFormat;
import com.meti.compile.node.Node;

public interface Divider<T extends Divider<T>> {
    Stream<Node> apply(CFormat format) throws CollectionException;

    T divide(Node node) throws CompileException;

    Stream<CFormat> stream();
}
