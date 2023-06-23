package com.meti.feature;

import com.meti.InterpretationError;
import com.meti.safe.NativeString;
import com.meti.safe.option.None;
import com.meti.safe.option.Option;
import com.meti.safe.option.Some;
import com.meti.safe.result.Ok;
import com.meti.safe.result.Result;
import com.meti.state.State;

public record NumberActor(State state, NativeString input) implements Actor {
    @Override
    public Option<Result<State, InterpretationError>> act() {
        try {
            var withoutSuffix = this.input
                    .firstIndexOfCharByPredicate(Character::isLetter)
                    .map(this.input::sliceTo)
                    .unwrapOrElse(input);

            var from = NativeString.from(String.valueOf(Integer.parseInt(withoutSuffix.strip().internalValue())));
            return new Some<>(Ok.apply(state.withValue(from)));
        } catch (NumberFormatException e) {
            return new None<>();
        }
    }
}