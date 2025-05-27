package magmac.app.stage.lexer;

import magmac.api.result.Result;
import magmac.app.compile.error.CompileError;
import magmac.app.io.Location;
import magmac.app.stage.Roots;

import java.util.Map;

public interface Lexer {
    Result<Roots, CompileError> lexAll(Map<Location, String> values);
}
