package magma.app.compile;

import magma.api.result.Result;
import magma.api.result.Tuple;

public interface Rule {
    Result<Tuple<String, String>, CompileError> apply(ParseState state, String input);
}
