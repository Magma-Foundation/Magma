package com.meti.state;

import com.meti.feature.Node;
import com.meti.safe.option.Option;
import com.meti.safe.option.Some;

public final class PresentState extends State {
    private final Node value;

    PresentState(Node value, Stack stack) {
        super(stack);
        this.value = value;
    }

    @Override
    protected State copy(Stack stack) {
        return new PresentState(value, stack);
    }

    @Override
    public Option<Node> findValue1() {
        return Some.apply(value);
    }

    @Override
    public PresentState withValue(Node value) {
        return new PresentState(value, stack);
    }
}
