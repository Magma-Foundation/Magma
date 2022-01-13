package com.meti.compile;

import com.meti.collect.CollectionException;
import com.meti.collect.Stream;
import com.meti.collect.StreamException;
import com.meti.compile.clang.CFormat;
import com.meti.compile.node.Node;

public interface Division {
    Stream<Node> apply(CFormat format) throws CollectionException;

    Division divide(Node node) throws StreamException;

    Stream<CFormat> stream();
}
