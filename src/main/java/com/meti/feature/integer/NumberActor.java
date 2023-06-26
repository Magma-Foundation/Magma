package com.meti.feature.integer;

import com.meti.InterpretationError;
import com.meti.feature.Actor;
import com.meti.feature.ParsingStage;
import com.meti.feature.integer.IntNode;
import com.meti.safe.NativeString;
import com.meti.safe.option.None;
import com.meti.safe.option.Option;
import com.meti.safe.option.Some;
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

            var i = Integer.parseInt(withoutSuffix.strip().internalValue());
            return Some.apply(new ParsingStage(state, new IntNode(i)).parse());
        } catch (NumberFormatException e) {
            return None.apply();
        }
    }
}