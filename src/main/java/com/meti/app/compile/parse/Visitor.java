package com.meti.app.compile.parse;

import com.meti.api.option.Option;
import com.meti.app.compile.stage.CompileException;

public interface Visitor {
    Option<State> onEnter() throws CompileException;

    Option<State> onExit() throws CompileException;
}
