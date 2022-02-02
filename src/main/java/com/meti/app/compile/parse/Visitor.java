package com.meti.app.compile.parse;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.StreamException;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.stage.CompileException;

public interface Visitor {
    Stream<Parser> streamParsers(State state);

    List<Node> visit() throws StreamException, CompileException;
}
