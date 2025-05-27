package magmac.app.compile;

import magmac.api.result.Result;
import magmac.app.error.CompileError;
import magmac.app.io.Location;

import java.util.Map;

public interface Compiler {
    Result<Map<Location, String>, CompileError> compile(Map<Location, String> units);
}
