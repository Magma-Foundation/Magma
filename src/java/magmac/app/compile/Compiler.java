package magmac.app.compile;

import magmac.app.compile.error.CompileResult;
import magmac.app.io.Location;

import magmac.api.collect.map.Map;
import magmac.app.stage.UnitSet;

public interface Compiler {
    CompileResult<UnitSet<String>> compile(UnitSet<String> units);
}
