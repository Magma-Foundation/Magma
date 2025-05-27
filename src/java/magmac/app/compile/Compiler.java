package magmac.app.compile;

import magmac.app.compile.error.CompileResult;
import magmac.app.io.Location;

import magmac.api.collect.map.Map;

public interface Compiler {
    CompileResult<Map<Location, String>> compile(Map<Location, String> units);
}
