package magmac.app.config;

import magmac.api.collect.list.List;
import magmac.api.collect.list.Lists;
import magmac.api.iter.collect.ListCollector;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResultCollector;
import magmac.app.compile.error.CompileResults;
import magmac.app.io.Location;
import magmac.app.io.sources.UnitSetCollector;
import magmac.app.lang.node.ArrayType;
import magmac.app.lang.node.Constructor;
import magmac.app.lang.node.Definition;
import magmac.app.lang.node.EnumValues;
import magmac.app.lang.node.JavaDefinition;
import magmac.app.lang.node.JavaMethod;
import magmac.app.lang.node.JavaMethodHeader;
import magmac.app.lang.node.JavaNamespacedNode;
import magmac.app.lang.node.JavaParameter;
import magmac.app.lang.node.JavaRootSegment;
import magmac.app.lang.node.JavaStructureMember;
import magmac.app.lang.node.JavaStructureNode;
import magmac.app.lang.node.JavaType;
import magmac.app.lang.node.ParameterizedMethodHeader;
import magmac.app.lang.node.Root;
import magmac.app.lang.node.Segment;
import magmac.app.lang.node.StructureStatement;
import magmac.app.lang.node.StructureValue;
import magmac.app.lang.node.TypeScriptDefinition;
import magmac.app.lang.node.TypeScriptImport;
import magmac.app.lang.node.TypeScriptMethodHeader;
import magmac.app.lang.node.TypeScriptParameter;
import magmac.app.lang.node.TypeScriptRootSegment;
import magmac.app.lang.node.TypeScriptType;
import magmac.app.lang.node.TypescriptMethod;
import magmac.app.lang.node.TypescriptRoot;
import magmac.app.lang.node.TypescriptStructureMember;
import magmac.app.lang.node.TypescriptStructureNode;
import magmac.app.lang.node.TypescriptStructureType;
import magmac.app.lang.node.VariadicType;
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
                .map(rootSegment -> JavaTypescriptParser.parseRootSegment(location, rootSegment))
                .collect(new ListCollector<>());

        return CompileResults.Ok(new SimpleUnit<>(location, new TypescriptRoot(rootSegments)));
    }

    private static TypeScriptRootSegment parseRootSegment(Location location, JavaRootSegment rootSegment) {
        return switch (rootSegment) {
            case JavaNamespacedNode namespaced -> JavaTypescriptParser.parseNamespaced(location, namespaced);
            case JavaStructureNode structureNode -> JavaTypescriptParser.parseJavaStructure(structureNode);
            case Whitespace whitespace -> whitespace;
        };
    }

    private static TypeScriptRootSegment parseJavaStructure(JavaStructureNode structureNode) {
        return switch (structureNode.type()) {
            case Class -> JavaTypescriptParser.parseClass(structureNode);
            case Record -> new Whitespace();
            case Enum -> new Whitespace();
            case Interface -> new Whitespace();
        };
    }

    private static TypescriptStructureNode parseClass(JavaStructureNode structureNode) {
        StructureValue<JavaStructureMember> value = structureNode.value;
        List<TypescriptStructureMember> collect = value.members()
                .iter()
                .map(JavaTypescriptParser::parseStructureMember)
                .collect(new ListCollector<>());

        StructureValue<TypescriptStructureMember> structureNode1 = new StructureValue<>(
                value.name(),
                value.modifiers(),
                collect,
                value.maybeTypeParams(),
                value.maybeExtended(),
                value.maybeImplemented()
        );

        return new TypescriptStructureNode(TypescriptStructureType.Class, structureNode1);
    }

    private static TypescriptStructureMember parseStructureMember(JavaStructureMember structureNode) {
        return switch (structureNode) {
            case Whitespace whitespace -> new Whitespace();
            case EnumValues enumValues -> new Whitespace();
            case StructureStatement structureStatement -> new Whitespace();
            case JavaMethod methodNode -> JavaTypescriptParser.parseMethod(methodNode);
        };
    }

    private static TypescriptStructureMember parseMethod(JavaMethod methodNode) {
        List<TypeScriptParameter> parameters = methodNode.parameters()
                .iter()
                .map(JavaTypescriptParser::parseParameter)
                .collect(new ListCollector<>());

        JavaMethodHeader header1 = methodNode.header();
        TypeScriptMethodHeader header = JavaTypescriptParser.parseMethodHeader(header1);
        return new TypescriptMethod(new ParameterizedMethodHeader<TypeScriptParameter>(header, parameters));
    }

    private static TypeScriptParameter parseParameter(JavaParameter parameter) {
        return switch (parameter) {
            case Whitespace whitespace -> whitespace;
            case JavaDefinition javaDefinition -> JavaTypescriptParser.parseWrappedDefinition(javaDefinition);
        };
    }

    private static TypeScriptDefinition parseWrappedDefinition(JavaDefinition javaDefinition) {
        return new TypeScriptDefinition(JavaTypescriptParser.parseDefinition(javaDefinition.definition()));
    }

    private static TypeScriptMethodHeader parseMethodHeader(JavaMethodHeader header) {
        return switch (header) {
            case Constructor constructor -> constructor;
            case JavaDefinition javaDefinition ->
                    new TypeScriptDefinition(JavaTypescriptParser.parseDefinition(javaDefinition.definition()));
        };
    }

    private static Definition<TypeScriptType> parseDefinition(Definition<JavaType> definition) {
        return switch (definition.type()) {
            case TypeScriptType type -> definition.withType(type);
            case VariadicType variadicType -> definition
                    .withName("..." + definition.name())
                    .withType(new ArrayType(variadicType.child()));
        };
    }

    private static TypeScriptRootSegment parseNamespaced(Location location, JavaNamespacedNode namespaced) {
        return switch (namespaced.type()) {
            case Package -> new Whitespace();
            case Import -> JavaTypescriptParser.parseImport(location, namespaced.segments());
        };
    }

    private static TypeScriptImport parseImport(Location location, List<Segment> segments) {
        List<String> segmentValues = segments.iter()
                .map(Segment::value)
                .collect(new ListCollector<>());

        List<Segment> before = location.namespace()
                .iter()
                .map(_ -> "..")
                .map(Segment::new)
                .collect(new ListCollector<>());

        Segment last = new Segment(segmentValues.findLast().orElse(""));
        return new TypeScriptImport(Lists.of(last), before.addAllLast(segments));
    }

    @Override
    public CompileResult<UnitSet<TypescriptRoot>> apply(UnitSet<Root<JavaRootSegment>> initial) {
        return initial.iter()
                .map(JavaTypescriptParser::parseUnit)
                .collect(new CompileResultCollector<>(new UnitSetCollector<>()));
    }
}
