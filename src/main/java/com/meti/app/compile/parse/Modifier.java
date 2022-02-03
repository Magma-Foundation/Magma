package com.meti.app.compile.parse;

import com.meti.api.option.Option;
import com.meti.app.compile.stage.CompileException;

public interface Modifier extends Visitor {
    Option<State> modifyBeforeAST() throws CompileException;

    Option<State> modifyAfterAST() throws CompileException;
}
