package magmac.app.lang;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.api.collect.list.Lists;
import magmac.api.collect.map.Map;
import magmac.api.collect.map.Maps;
import magmac.api.iter.Iter;
import magmac.api.iter.Iters;
import magmac.api.iter.collect.Joiner;
import magmac.api.iter.collect.ListCollector;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResults;
import magmac.app.io.Location;
import magmac.app.lang.java.JavaLang;
import magmac.app.lang.java.JavaNamespacedNode;
import magmac.app.lang.java.JavaRootSegment;
import magmac.app.lang.node.PlantUMLDependency;
import magmac.app.lang.node.PlantUMLFooter;
import magmac.app.lang.node.PlantUMLHeader;
import magmac.app.lang.node.PlantUMLInherits;
import magmac.app.lang.node.PlantUMLRoot;
import magmac.app.lang.node.PlantUMLRootSegment;
import magmac.app.lang.node.PlantUMLStructure;
import magmac.app.lang.node.PlantUMLStructureType;
import magmac.app.lang.node.Segment;
import magmac.app.stage.parse.Parser;
import magmac.app.stage.unit.MapUnitSet;
import magmac.app.stage.unit.SimpleUnit;
import magmac.app.stage.unit.Unit;
import magmac.app.stage.unit.UnitSet;

public class JavaPlantUMLParser implements Parser<JavaLang.Root, PlantUMLRoot> {
    private static Iter<PlantUMLRootSegment> parseNamespaced(String child, JavaNamespacedNode namespaced) {
        return switch (namespaced.type()) {
            case Package -> Iters.empty();
            case Import -> {
                var parent = namespaced.segments().findLast().orElse(null)
                        .value();

                if ("*".equals(parent)) {
                    yield Iters.empty();
                }
                else {
                    yield Iters.fromValues(new PlantUMLDependency(child, parent));
                }
            }
        };
    }

    private static String createSimpleName(JavaLang.Base base) {
        return switch (base) {
            case JavaLang.Qualified qualifiedType ->
                    JavaPlantUMLParser.createSimpleNameFromQualifiedType(qualifiedType);
            case JavaLang.Symbol symbol -> symbol.value();
        };
    }

    private static PlantUMLRootSegment createStructureSegment(JavaLang.Structure structure) {
        var name = structure.name();
        var type = structure.type();

        return switch (type) {
            case Class, Record -> new PlantUMLStructure(PlantUMLStructureType.Class, name);
            case Enum -> new PlantUMLStructure(PlantUMLStructureType.Enum, name);
            case Interface -> new PlantUMLStructure(PlantUMLStructureType.Interface, name);
        };
    }

    private static String createSimpleNameFromType(JavaLang.JavaType type) {
        return switch (type) {
            case JavaLang.JavaArrayType _, JavaLang.JavaVariadicType _ -> "?";
            case JavaLang.Symbol symbol -> symbol.value();
            case JavaLang.JavaTemplateType templateType -> JavaPlantUMLParser.createSimpleName(templateType.base());
            case JavaLang.Qualified qualified -> JavaPlantUMLParser.createSimpleNameFromQualifiedType(qualified);
        };
    }

    private static String createSimpleNameFromQualifiedType(JavaLang.Qualified qualified) {
        return qualified.segments()
                .iter()
                .map(Segment::value)
                .collect(new Joiner("."))
                .orElse("");
    }

    private static Iter<PlantUMLRootSegment> parseRoot(Unit<JavaLang.Root> unit) {
        return unit.destruct((location, root) -> root.children()
                .iter()
                .flatMap(segment -> JavaPlantUMLParser.parseRootSegment(location.name(), segment)));
    }

    private static Iter<PlantUMLRootSegment> parseRootSegment(String fileName, JavaRootSegment rootSegment) {
        return switch (rootSegment) {
            case JavaLang.Whitespace _, JavaLang.Comment _ -> Iters.empty();
            case JavaNamespacedNode namespaced -> JavaPlantUMLParser.parseNamespaced(fileName, namespaced);
            case JavaLang.Structure structure -> JavaPlantUMLParser.parseStructure(structure);
        };
    }

    private static Iter<PlantUMLRootSegment> parseStructure(JavaLang.Structure structure) {
        var segment = JavaPlantUMLParser.createStructureSegment(structure);
        var child = structure.name();

        return Lists.of(segment)
                .addAllLast(JavaPlantUMLParser.toInherits(child, structure.extended()))
                .addAllLast(JavaPlantUMLParser.toInherits(child, structure.implemented()))
                .iter();
    }

    private static List<PlantUMLRootSegment> toInherits(String child, Option<List<JavaLang.JavaType>> maybeOption) {
        return maybeOption.orElse(Lists.empty())
                .iter()
                .map(JavaPlantUMLParser::createSimpleNameFromType)
                .map(parent -> JavaPlantUMLParser.getPlantUMLInherits(child, parent))
                .collect(new ListCollector<>());
    }

    private static PlantUMLRootSegment getPlantUMLInherits(String child, String parent) {
        return new PlantUMLInherits(child, parent);
    }

    private static List<PlantUMLRootSegment> removeTransitiveDependencies(List<PlantUMLRootSegment> segments) {
        Map<String, List<String>> adjacency = segments.iter()
                .filter(segment -> segment instanceof PlantUMLDependency)
                .map(segment -> (PlantUMLDependency) segment)
                .fold(Maps.empty(), (Map<String, List<String>> current, PlantUMLDependency dep) ->
                        current.mapOrPut(dep.child(), (List<String> list) -> list.addLast(dep.parent()), () -> Lists.of(dep.parent())));

        return segments.iter()
                .filter(segment -> JavaPlantUMLParser.extracted(segment, adjacency))
                .collect(new ListCollector<>());
    }

    private static boolean extracted(PlantUMLRootSegment segment, Map<String, List<String>> adjacency) {
        if (!(segment instanceof PlantUMLDependency(String child, String parent))) {
            return true;
        }

        List<String> directParents = adjacency.getOrDefault(child, Lists.empty());

        boolean transitive = directParents.iter()
                .filter(p -> !p.equals(parent))
                .map(p -> adjacency.getOrDefault(p, Lists.empty()).contains(parent))
                .fold(false, (Boolean acc, Boolean has) -> acc || has);

        return !transitive;
    }

    @Override
    public CompileResult<UnitSet<PlantUMLRoot>> apply(UnitSet<JavaLang.Root> initial) {
        var segments = initial.iter()
                .flatMap(JavaPlantUMLParser::parseRoot)
                .collect(new ListCollector<>());

        var roots = JavaPlantUMLParser.removeTransitiveDependencies(segments)
                .addFirst(new PlantUMLHeader())
                .addLast(new PlantUMLFooter());

        var defaultLocation = new Location(Lists.empty(), "diagram");
        var mergedRoot = new PlantUMLRoot(roots);

        return CompileResults.Ok(new MapUnitSet<PlantUMLRoot>()
                .add(new SimpleUnit<>(defaultLocation, mergedRoot)));
    }
}
