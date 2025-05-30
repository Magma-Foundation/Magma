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
import magmac.app.lang.node.ArrayType;
import magmac.app.lang.node.Base;
import magmac.app.lang.node.Root;
import magmac.app.lang.node.JavaRootSegment;
import magmac.app.lang.node.JavaNamespacedNode;
import magmac.app.lang.node.PlantUMLDependency;
import magmac.app.lang.node.PlantUMLFooter;
import magmac.app.lang.node.PlantUMLHeader;
import magmac.app.lang.node.PlantUMLInherits;
import magmac.app.lang.node.PlantUMLRoot;
import magmac.app.lang.node.PlantUMLRootSegment;
import magmac.app.lang.node.PlantUMLStructure;
import magmac.app.lang.node.PlantUMLStructureType;
import magmac.app.lang.node.QualifiedType;
import magmac.app.lang.node.Segment;
import magmac.app.lang.node.JavaStructureNode;
import magmac.app.lang.node.StructureType;
import magmac.app.lang.node.Symbol;
import magmac.app.lang.node.TemplateType;
import magmac.app.lang.node.Type;
import magmac.app.lang.node.VariadicType;
import magmac.app.lang.node.Whitespace;
import magmac.app.stage.parse.Parser;
import magmac.app.stage.unit.MapUnitSet;
import magmac.app.stage.unit.SimpleUnit;
import magmac.app.stage.unit.Unit;
import magmac.app.stage.unit.UnitSet;

public class JavaPlantUMLParser implements Parser<Root<JavaRootSegment>, PlantUMLRoot> {
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

    private static String createSimpleName(Base base) {
        return switch (base) {
            case QualifiedType qualifiedType -> JavaPlantUMLParser.createSimpleNameFromQualifiedType(qualifiedType);
            case Symbol symbol -> symbol.value();
        };
    }

    private static String createSimpleNameFromQualifiedType(QualifiedType qualifiedType) {
        return qualifiedType.segments()
                .iter()
                .map(Segment::value)
                .collect(new Joiner("."))
                .orElse("");
    }

    private static PlantUMLRootSegment createStructureSegment(JavaStructureNode structureNode) {
        String name = structureNode.name();
        StructureType type = structureNode.type();

        return switch (type) {
            case Class, Record -> new PlantUMLStructure(PlantUMLStructureType.Class, name);
            case Enum -> new PlantUMLStructure(PlantUMLStructureType.Enum, name);
            case Interface -> new PlantUMLStructure(PlantUMLStructureType.Interface, name);
        };
    }

    private static String createSimpleNameFromType(Type type) {
        return switch (type) {
            case ArrayType _, VariadicType _ -> "?";
            case Symbol symbol -> symbol.value();
            case TemplateType templateType -> JavaPlantUMLParser.createSimpleName(templateType.base());
        };
    }

    @Override
    public CompileResult<UnitSet<PlantUMLRoot>> apply(UnitSet<Root<JavaRootSegment>> initial) {
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

    private static Iter<PlantUMLRootSegment> parseRoot(Unit<Root<JavaRootSegment>> unit) {
        return unit.destruct((location, root) -> root.children()
                .iter()
                .flatMap(segment -> JavaPlantUMLParser.parseRootSegment(location.name(), segment)));
    }

    private static Iter<PlantUMLRootSegment> parseRootSegment(String fileName, JavaRootSegment rootSegment) {
        return switch (rootSegment) {
            case Whitespace _ -> Iters.empty();
            case JavaNamespacedNode namespaced -> JavaPlantUMLParser.parseNamespaced(fileName, namespaced);
            case JavaStructureNode structureNode -> JavaPlantUMLParser.parseStructure(structureNode);
        };
    }

    private static Iter<PlantUMLRootSegment> parseStructure(JavaStructureNode structureNode) {
        PlantUMLRootSegment segment = JavaPlantUMLParser.createStructureSegment(structureNode);
        String child = structureNode.name();

        return Lists.of(segment)
                .addAllLast(JavaPlantUMLParser.toInherits(child, structureNode.extended()))
                .addAllLast(JavaPlantUMLParser.toInherits(child, structureNode.implemented()))
                .iter();
    }

    private static List<PlantUMLRootSegment> toInherits(String child, Option<List<Type>> maybeOption) {
        return maybeOption.orElse(Lists.empty())
                .iter()
                .map(JavaPlantUMLParser::createSimpleNameFromType)
                .<PlantUMLRootSegment>map(parent -> new PlantUMLInherits(child, parent))
                .collect(new ListCollector<>());
    }
}
