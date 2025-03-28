package magma.app.compile.rule;

import magma.api.result.Result;
import magma.api.result.Tuple;
import magma.app.compile.CompileError;
import magma.app.compile.ParseState;

public interface Rule {
    Result<Tuple<String, String>, CompileError> apply(ParseState state, String input);
}
