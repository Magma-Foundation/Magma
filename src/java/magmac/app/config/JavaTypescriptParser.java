package magmac.app.config;

import magmac.api.collect.list.List;
import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResultCollector;
import magmac.app.compile.error.CompileResults;
import magmac.app.io.Location;
import magmac.app.io.sources.UnitSetCollector;
import magmac.app.lang.node.JavaRoot;
import magmac.app.lang.node.TypeScriptRootSegment;
import magmac.app.lang.node.TypescriptRoot;
import magmac.app.stage.parse.Parser;
import magmac.app.stage.unit.SimpleUnit;
import magmac.app.stage.unit.Unit;
import magmac.app.stage.unit.UnitSet;

class JavaTypescriptParser implements Parser<JavaRoot, TypescriptRoot> {
    private static CompileResult<Unit<TypescriptRoot>> parseUnit(Unit<JavaRoot> unit) {
        return unit.destruct((location, javaRoot) -> JavaTypescriptParser.parseRoot(location, javaRoot));
    }

    private static CompileResult<Unit<TypescriptRoot>> parseRoot(Location location, JavaRoot root) {
        List<TypeScriptRootSegment> rootSegments = Lists.empty();
        return CompileResults.Ok(new SimpleUnit<>(location, new TypescriptRoot(rootSegments)));
    }

    @Override
    public CompileResult<UnitSet<TypescriptRoot>> apply(UnitSet<JavaRoot> initial) {
        return initial.iter()
                .map(JavaTypescriptParser::parseUnit)
                .collect(new CompileResultCollector<>(new UnitSetCollector<>()));
    }
}
