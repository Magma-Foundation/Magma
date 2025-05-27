package magmac.app.stage;

import magmac.api.result.Result;
import magmac.app.compile.CompileError;
import magmac.app.io.Location;

import java.util.Map;

public interface Lexer {
    Result<Roots, CompileError> lexAll(Map<Location, String> values);
}
