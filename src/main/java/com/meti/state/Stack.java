package com.meti.state;

import com.meti.InterpretationError;
import com.meti.feature.attribute.Attribute;
import com.meti.feature.definition.ExplicitDefinition;
import com.meti.safe.NativeString;
import com.meti.safe.SafeList;
import com.meti.safe.SafeMap;
import com.meti.safe.Tuple2;
import com.meti.safe.iter.Collectors;
import com.meti.safe.iter.Iterators;
import com.meti.safe.option.Option;
import com.meti.safe.result.Err;
import com.meti.safe.result.Ok;
import com.meti.safe.result.Result;

import java.util.function.Function;

public record Stack(SafeList<SafeMap<NativeString, ExplicitDefinition>> frames) {
    public Stack() {
        this(SafeList.<SafeMap<NativeString, ExplicitDefinition>>empty().add(SafeMap.empty()));
    }

    public Result<Stack, InterpretationError> mapDefinition(
            NativeString name,
            Function<ExplicitDefinition, Result<ExplicitDefinition, InterpretationError>> mapper) {

        return frames.indices()
                .zip(frames.iter())
                .map(tuple -> tuple.mapRightOptionally(value -> value.mapValueWithResult(name, mapper)))
                .flatMap(Iterators::fromOption)
                .mapToResult(Tuple2::unwrapRight)
                .collectToResult(Collectors.toMap())
                .flatMapValue(s ->
                {
                    if (s.isEmpty()) {
                        var format = "'%s' is undefined.";
                        var message = format.formatted(name.internalValue());
                        return Err.apply(new InterpretationError(message));
                    } else if (s.size() > 1) {
                        return Err.apply(new InterpretationError(name.internalValue() + " was defined in multiple frames?"));
                    } else {
                        var first = s.iter().head().unwrapOrPanic();
                        var index = first.left();
                        var frame = first.right();
                        return Ok.apply(frames.set(index, frame));
                    }
                }).mapValue(Stack::new);
    }

    public Stack define(ExplicitDefinition explicitDefinition) {
        return frames.last()
                .map(index -> frames.map(index, frame -> frame.with(explicitDefinition.apply(NativeString.from("name")).flatMap(Attribute::asString).unwrapOrPanic(), explicitDefinition)))
                .map(Stack::new)
                .unwrapOrElse(this);
    }

    public Option<ExplicitDefinition> apply(NativeString name) {
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