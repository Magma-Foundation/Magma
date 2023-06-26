package com.meti.state;

import com.meti.feature.Content;
import com.meti.feature.Node;
import com.meti.safe.NativeString;
import com.meti.safe.option.Option;
import com.meti.safe.option.Some;

public final class PresentState extends State {
    public final NativeString value;

    PresentState(NativeString value, Stack stack) {
        super(stack);
        this.value = value;
    }

    private PresentState withValue2(NativeString value) {
        return new PresentState(value, stack);
    }

    @Override
    protected State copy(Stack stack) {
        return new PresentState(value, stack);
    }

    private Option<NativeString> findValue() {
        return Some.apply(value);
    }

    @Override
    public Option<Node> findValue1() {
        return findValue().map(Content::new);
    }

    @Override
    public PresentState withValue(Node value) {
        return withValue2(value.valueAsString().unwrapOrPanic());
    }
}
