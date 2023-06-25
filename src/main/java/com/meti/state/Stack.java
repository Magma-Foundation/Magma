package com.meti.state;

import com.meti.InterpretationError;
import com.meti.safe.NativeString;
import com.meti.safe.SafeList;
import com.meti.safe.SafeMap;
import com.meti.safe.iter.Collectors;
import com.meti.safe.iter.Iterators;
import com.meti.safe.option.Option;
import com.meti.safe.result.Result;

import java.util.function.Function;

public record Stack(SafeList<SafeMap> frames) {
    public Stack() {
        this(SafeList.<SafeMap>empty().add(SafeMap.empty()));
    }

    public Result<Stack, InterpretationError> mapDefinition(
            NativeString name,
            Function<Definition, Result<Definition, InterpretationError>> mapper) {

        return frames.iter()
                .flatMapToResult(frame -> Iterators.fromOption(frame.updateDefinition(name, mapper)))
                .collectToResult(Collectors.toList())
                .mapValue(Stack::new);
    }

    public Stack define(Definition definition) {
        return frames.last()
                .map(index -> frames.map(index, frame -> frame.with(definition.name(), definition)))
                .map(Stack::new)
                .unwrapOrElse(this);
    }

    public boolean isDefined(NativeString name) {
        return apply(name).isPresent();
    }

    public Option<Definition> apply(NativeString name) {
        return frames.iter()
                .flatMap(frame -> Iterators.fromOption(frame.apply(name)))
                .head();
    }

    public Stack enter() {
        return new Stack(frames.add(SafeMap.empty()));
    }

    public Stack exit() {
        return new Stack(frames.removeLast());
    }
}