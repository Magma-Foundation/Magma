package com.meti.compile.state;

import com.meti.api.option.Option;
import com.meti.compile.node.Node;

public interface State {

    Option<Node> value();
}
