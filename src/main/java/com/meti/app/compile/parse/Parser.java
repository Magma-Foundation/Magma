package com.meti.app.compile.parse;

import com.meti.api.option.Option;

public interface Parser {
    Option<State> onEnter(State state);

    Option<State> onExit(State state);
}
