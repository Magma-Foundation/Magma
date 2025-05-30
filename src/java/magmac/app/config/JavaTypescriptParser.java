package magmac.app.config;

import magmac.api.collect.list.List;
import magmac.api.collect.list.Lists;
import magmac.api.iter.collect.ListCollector;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResultCollector;
import magmac.app.compile.error.CompileResults;
import magmac.app.io.Location;
import magmac.app.io.sources.UnitSetCollector;
import magmac.app.lang.node.JavaRootSegment;
import magmac.app.lang.node.Namespaced;
import magmac.app.lang.node.Root;
import magmac.app.lang.node.StructureNode;
import magmac.app.lang.node.TypeScriptRootSegment;
import magmac.app.lang.node.TypescriptRoot;
import magmac.app.lang.node.Whitespace;
import magmac.app.stage.parse.Parser;
import magmac.app.stage.unit.SimpleUnit;
import magmac.app.stage.unit.Unit;
import magmac.app.stage.unit.UnitSet;

class JavaTypescriptParser implements Parser<Root<JavaRootSegment>, TypescriptRoot> {
    private static CompileResult<Unit<TypescriptRoot>> parseUnit(Unit<Root<JavaRootSegment>> unit) {
        return unit.destruct(JavaTypescriptParser::parseRoot);
    }

    private static CompileResult<Unit<TypescriptRoot>> parseRoot(Location location, Root<JavaRootSegment> root) {
        List<TypeScriptRootSegment> rootSegments = root.children()
                .iter()
                .map(JavaTypescriptParser::parseRootSegment)
                .collect(new ListCollector<>());

        return CompileResults.Ok(new SimpleUnit<>(location, new TypescriptRoot(rootSegments)));
    }

    private static TypeScriptRootSegment parseRootSegment(JavaRootSegment rootSegment) {
        return switch (rootSegment) {
            case Namespaced namespaced -> new Whitespace();
            case StructureNode structureNode -> new Whitespace();
            case Whitespace whitespace -> whitespace;
        };
    }

    @Override
    public CompileResult<UnitSet<TypescriptRoot>> apply(UnitSet<Root<JavaRootSegment>> initial) {
        return initial.iter()
                .map(JavaTypescriptParser::parseUnit)
                .collect(new CompileResultCollector<>(new UnitSetCollector<>()));
    }
}
