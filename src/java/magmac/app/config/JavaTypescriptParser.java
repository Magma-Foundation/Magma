package magmac.app.config;

import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResultCollector;
import magmac.app.compile.error.CompileResults;
import magmac.app.io.sources.UnitSetCollector;
import magmac.app.lang.node.JavaRoot;
import magmac.app.stage.parse.Parser;
import magmac.app.stage.unit.Unit;
import magmac.app.stage.unit.UnitSet;

class JavaTypescriptParser implements Parser<JavaRoot, TypescriptRoot> {
    private static CompileResult<Unit<TypescriptRoot>> parseUnit(Unit<JavaRoot> unit) {
        return unit.destruct((location, javaRoot) -> CompileResults.fromErrWithString("", ""));
    }

    @Override
    public CompileResult<UnitSet<TypescriptRoot>> apply(UnitSet<JavaRoot> initial) {
        return initial.iter()
                .map(JavaTypescriptParser::parseUnit)
                .collect(new CompileResultCollector<>(new UnitSetCollector<>()));
    }
}
