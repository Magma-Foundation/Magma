package com.meti;

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