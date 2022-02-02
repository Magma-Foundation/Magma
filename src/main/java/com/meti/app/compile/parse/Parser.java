package com.meti.app.compile.parse;

import com.meti.api.option.Option;
import com.meti.app.compile.stage.CompileException;

public interface Parser extends Visitor {
    Option<State> onParse() throws CompileException;
}
