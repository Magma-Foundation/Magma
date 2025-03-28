package magma.app.compile.rule;

import magma.api.result.Err;
import magma.api.result.Result;
import magma.api.result.Tuple;
import magma.app.compile.CompileError;
import magma.app.compile.Compiler;
import magma.app.compile.ParseState;
import magma.app.compile.Rule;

public class EmptyRule implements Rule {
    @Override
    public Result<Tuple<String, String>, CompileError> apply(ParseState state, String stripped) {
        if (stripped.isEmpty()) return Compiler.generateEmpty();
        return new Err<>(new CompileError("Input not empty", stripped));
    }
}