package com.meti;

import java.util.Map;
import java.util.function.Function;

final class PresentState extends State {
    final NativeString value;

    PresentState(NativeString value, Map<NativeString, NativeString> declarations) {
        super(declarations);
        this.value = value;
    }

    @Override
    public PresentState withValue(NativeString value) {
        return new PresentState(value, declarations);
    }

    @Override
    PresentState mapValue(Function<NativeString, NativeString> mapper) {
        return new PresentState(mapper.apply(this.value), this.declarations);
    }

    Result<State, InterpretationError> mapValueToResult(Function<NativeString, Result<NativeString, InterpretationError>> mapper) {
        return mapper.apply(this.value).mapValue(apply1 -> new PresentState(apply1, this.declarations));
    }

    @Override
    public Option<NativeString> findValue() {
        return new Some<>(value);
    }
}
