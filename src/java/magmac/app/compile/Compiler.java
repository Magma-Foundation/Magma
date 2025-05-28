package magmac.app.compile;

import magmac.app.compile.error.CompileResult;

import magmac.app.stage.unit.UnitSet;

public interface Compiler {
    CompileResult<UnitSet<String>> compile(UnitSet<String> units);
}
