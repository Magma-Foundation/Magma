package com.meti.compile;

import com.meti.api.iterator.Iterator;
import com.meti.api.option.Option;

public interface Node {
    boolean is(String type);

    Option<String> getString(String name);

    Iterator<Tuple<String, Node>> getNodes();

    Option<Node> withNode(String name, Node value);

    Option<String> getValue();
}
