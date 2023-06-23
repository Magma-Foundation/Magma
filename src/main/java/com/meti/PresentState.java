package com.meti;

import java.util.function.Function;

final class PresentState extends State {
    final NativeString value;

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
