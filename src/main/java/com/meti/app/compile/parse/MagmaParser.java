package com.meti.app.compile.parse;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.stage.CompileException;

public class MagmaParser {
    private final List<Node> input;

    public MagmaParser(List<Node> input) {
        this.input = input;
    }

    public List<Node> parse() throws StreamException, CompileException {
        return input;
    }
}
