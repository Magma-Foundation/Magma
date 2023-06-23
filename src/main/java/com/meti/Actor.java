package com.meti;

public interface Actor {
    Option<Result<State, InterpretationError>> act();
}
