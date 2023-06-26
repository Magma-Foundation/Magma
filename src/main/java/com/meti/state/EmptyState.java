package com.meti.state;

import com.meti.feature.Content;
import com.meti.feature.Node;
import com.meti.safe.NativeString;
import com.meti.safe.option.None;
import com.meti.safe.option.Option;

public class EmptyState extends State {

    public EmptyState(Stack stack) {
        super(stack);
    }

    private EmptyState() {
        this(new Stack());
    }

    public static State create() {
        return new EmptyState();
    }

    @Override
    protected State copy(Stack stack) {
        return new EmptyState(stack);
    }

    private Option<NativeString> findValue() {
        return None.apply();
    }

    @Override
    public Option<Node> findValue1() {
        return findValue().map(Content::new);
    }

    @Override
    public PresentState withValue(Node value) {
        return new PresentState(value, stack);
    }
}
