package com.meti.app.compile.feature.scope;

import com.meti.app.compile.stage.CompileException;
import com.meti.app.compile.text.Input;

public class UndefinedException extends CompileException {
    public UndefinedException(Input message) {
        super("Variable '%s' is undefined.".formatted(message));
    }
}
