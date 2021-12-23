package com.meti;

import com.meti.option.None;
import com.meti.option.Option;

public class ReturnRenderer extends AbstractRenderer {
    public ReturnRenderer(Node node) {
        super(node);
    }

    @Override
    public Option<String> process() {
        if (node.is(Node.Type.Return)) {
            return node.getValueAsNode()
                    .flatMap(Node::getValueAsInput)
                    .map(Input::compute)
                    .map(value -> "return " + value + ";");
        } else {
            return new None<>();
        }
    }
}
