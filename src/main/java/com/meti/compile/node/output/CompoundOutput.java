package com.meti.compile.node.output;

import com.meti.F1;
import com.meti.compile.node.Node;

import java.util.ArrayList;
import java.util.List;

public record CompoundOutput(List<Output> children) implements Output {
    @Override
    public <E extends Exception> Output mapNodes(F1<Node, Output, E> mapper) throws E {
        var list = new ArrayList<Output>();
        for (var child : children) {
            list.add(child.asNode()
                    .map(mapper)
                    .orElse(child));
        }
        return new CompoundOutput(list);
    }
}
