package com.meti.state;

import com.meti.safe.NativeString;
import com.meti.safe.option.Option;
import com.meti.safe.option.Some;

import java.util.function.Function;

public final class PresentState extends State {
    public final NativeString value;

    PresentState(NativeString value, Stack stack) {
        super(stack);
        this.value = value;
    }

    @Override
    public PresentState withValue(NativeString value) {
        return new PresentState(value, stack);
    }

    @Override
    protected State copy(Stack stack) {
        return new PresentState(value, stack);
    }

    @Override
    public State mapValue(Function<NativeString, NativeString> mapper) {
        return new PresentState(mapper.apply(this.value), stack);
    }

    @Override
    public Option<NativeString> findValue() {
        return new Some<>(value);
    }
}
