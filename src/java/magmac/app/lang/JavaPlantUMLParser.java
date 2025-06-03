package magmac.app.lang;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.api.collect.list.Lists;
import magmac.api.iter.Iter;
import magmac.api.iter.Iters;
import magmac.api.iter.collect.Joiner;
import magmac.api.iter.collect.ListCollector;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResults;
import magmac.app.io.Location;
import magmac.app.lang.java.JavaLang;
import magmac.app.lang.java.JavaRootSegment;
import magmac.app.lang.java.JavaNamespacedNode;
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

public class JavaPlantUMLParser implements Parser<JavaLang.JavaRoot, PlantUMLRoot> {
    private static Iter<PlantUMLRootSegment> parseNamespaced(String child, JavaNamespacedNode namespaced) {
        return switch (namespaced.type()) {
            case Package -> Iters.empty();
            case Import -> {
                String parent = namespaced.segments().findLast().orElse(null)
                        .value();

                yield Iters.fromValues(new PlantUMLDependency(child, parent));
            }
        };
    }

    private static String createSimpleName(JavaLang.Base base) {
        return switch (base) {
            case JavaLang.Qualified qualifiedType -> JavaPlantUMLParser.createSimpleNameFromQualifiedType(qualifiedType);
            case JavaLang.Symbol symbol -> symbol.value();
        };
    }

    private static String createSimpleNameFromQualifiedType(JavaLang.Qualified qualifiedType) {
        return qualifiedType.segments()
                .iter()
                .map(Segment::value)
                .collect(new Joiner("."))
                .orElse("");
    }

    private static PlantUMLRootSegment createStructureSegment(JavaLang.StructureNode structureNode) {
        String name = structureNode.name();
        JavaLang.JavaStructureType type = structureNode.type();

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
            case JavaLang.Qualified qualified -> "?";
        };
    }

    @Override
    public CompileResult<UnitSet<PlantUMLRoot>> apply(UnitSet<JavaLang.JavaRoot> initial) {
        List<PlantUMLRootSegment> roots = initial.iter()
                .flatMap(JavaPlantUMLParser::parseRoot)
                .collect(new ListCollector<>())
                .addFirst(new PlantUMLHeader())
                .addLast(new PlantUMLFooter());

        Location defaultLocation = new Location(Lists.empty(), "diagram");
        PlantUMLRoot mergedRoot = new PlantUMLRoot(roots);

        return CompileResults.Ok(new MapUnitSet<PlantUMLRoot>()
                .add(new SimpleUnit<>(defaultLocation, mergedRoot)));
    }

    private static Iter<PlantUMLRootSegment> parseRoot(Unit<JavaLang.JavaRoot> unit) {
        return unit.destruct((location, root) -> root.children()
                .iter()
                .flatMap(segment -> JavaPlantUMLParser.parseRootSegment(location.name(), segment)));
    }

    private static Iter<PlantUMLRootSegment> parseRootSegment(String fileName, JavaRootSegment rootSegment) {
        return switch (rootSegment) {
            case JavaLang.Whitespace _ -> Iters.empty();
            case JavaNamespacedNode namespaced -> JavaPlantUMLParser.parseNamespaced(fileName, namespaced);
            case JavaLang.StructureNode structureNode -> JavaPlantUMLParser.parseStructure(structureNode);
        };
    }

    private static Iter<PlantUMLRootSegment> parseStructure(JavaLang.StructureNode structureNode) {
        PlantUMLRootSegment segment = JavaPlantUMLParser.createStructureSegment(structureNode);
        String child = structureNode.name();

        return Lists.of(segment)
                .addAllLast(JavaPlantUMLParser.toInherits(child, structureNode.extended()))
                .addAllLast(JavaPlantUMLParser.toInherits(child, structureNode.implemented()))
                .iter();
    }

    private static List<PlantUMLRootSegment> toInherits(String child, Option<List<JavaLang.JavaType>> maybeOption) {
        return maybeOption.orElse(Lists.empty())
                .iter()
                .map(JavaPlantUMLParser::createSimpleNameFromType)
                .<PlantUMLRootSegment>map(parent -> new PlantUMLInherits(child, parent))
                .collect(new ListCollector<>());
    }
}
