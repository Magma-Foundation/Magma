package magmac.app.compile;

import magmac.app.io.Location;

import java.util.Map;

public interface Compiler {
    CompileResult<Map<Location, String>> compile(Map<Location, String> units);
}
