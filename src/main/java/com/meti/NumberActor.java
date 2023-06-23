package com.meti;

public record NumberActor(State state, NativeString input) implements Actor {
    @Override
    public Option<Result<State, InterpretationError>> act() {
        try {
            var withoutSuffix = this.input().firstIndexOfCharByPredicate(Character::isLetter).match(index -> input().slice(0, index).unwrapOrElse(input()), () -> input());
            var from = NativeString.from(String.valueOf(Integer.parseInt(withoutSuffix.strip().internalValue())));
            return new Some<>(Ok.of(state.withValue(from)));
        } catch (NumberFormatException e) {
            return new None<>();
        }
    }
}