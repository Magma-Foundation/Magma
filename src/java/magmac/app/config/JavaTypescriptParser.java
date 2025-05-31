package magmac.app.config;

import magmac.api.collect.list.List;
import magmac.api.collect.list.Lists;
import magmac.api.iter.collect.Joiner;
import magmac.api.iter.collect.ListCollector;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResultCollector;
import magmac.app.compile.error.CompileResults;
import magmac.app.io.Location;
import magmac.app.io.sources.UnitSetCollector;
import magmac.app.lang.node.Constructor;
import magmac.app.lang.node.Definition;
import magmac.app.lang.node.EnumValues;
import magmac.app.lang.node.JavaArrayType;
import magmac.app.lang.node.JavaBase;
import magmac.app.lang.node.JavaDefinition;
import magmac.app.lang.node.JavaMethod;
import magmac.app.lang.node.JavaMethodHeader;
import magmac.app.lang.node.JavaNamespacedNode;
import magmac.app.lang.node.JavaParameter;
import magmac.app.lang.node.JavaRootSegment;
import magmac.app.lang.node.JavaStructureMember;
import magmac.app.lang.node.JavaStructureNode;
import magmac.app.lang.node.JavaTemplateType;
import magmac.app.lang.node.JavaType;
import magmac.app.lang.node.ParameterizedMethodHeader;
import magmac.app.lang.node.QualifiedType;
import magmac.app.lang.node.JavaRoot;
import magmac.app.lang.node.Segment;
import magmac.app.lang.node.StructureStatement;
import magmac.app.lang.node.StructureValue;
import magmac.app.lang.node.Symbol;
import magmac.app.lang.node.TypeArguments;
import magmac.app.lang.node.TypeScriptDefinition;
import magmac.app.lang.node.TypeScriptImport;
import magmac.app.lang.node.TypeScriptMethodHeader;
import magmac.app.lang.node.TypeScriptParameter;
import magmac.app.lang.node.TypeScriptRootSegment;
import magmac.app.lang.node.TypeScriptTemplateType;
import magmac.app.lang.node.TypeScriptType;
import magmac.app.lang.node.TypescriptArrayType;
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

class JavaTypescriptParser implements Parser<JavaRoot, TypescriptRoot> {
    private static CompileResult<Unit<TypescriptRoot>> parseUnit(Unit<JavaRoot> unit) {
        return unit.destruct(JavaTypescriptParser::parseRoot);
    }

    private static CompileResult<Unit<TypescriptRoot>> parseRoot(Location location, JavaRoot root) {
        List<TypeScriptRootSegment> rootSegments = root.children()
                .iter()
                .map(rootSegment -> JavaTypescriptParser.parseRootSegment(location, rootSegment))
                .collect(new ListCollector<>());

        return CompileResults.Ok(new SimpleUnit<>(location, new TypescriptRoot(rootSegments)));
    }

    private static TypeScriptRootSegment parseRootSegment(Location location, JavaRootSegment rootSegment) {
        return switch (rootSegment) {
            case JavaNamespacedNode namespaced -> JavaTypescriptParser.parseNamespaced(location, namespaced);
            case JavaStructureNode structureNode -> JavaTypescriptParser.parseStructure(structureNode);
            case Whitespace whitespace -> whitespace;
        };
    }

    private static TypeScriptRootSegment parseStructure(JavaStructureNode structureNode) {
        return switch (structureNode.type()) {
            case Class, Record -> JavaTypescriptParser.parseStructureWithType(TypescriptStructureType.Class, structureNode);
            case Interface ->
                    JavaTypescriptParser.parseStructureWithType(TypescriptStructureType.Interface, structureNode);
            case Enum -> new Whitespace();
        };
    }

    private static TypescriptStructureNode parseStructureWithType(TypescriptStructureType type, JavaStructureNode structureNode) {
        StructureValue<JavaType, JavaStructureMember> value = structureNode.value;
        List<TypescriptStructureMember> collect = value.members()
                .iter()
                .map(JavaTypescriptParser::parseStructureMember)
                .collect(new ListCollector<>());

        StructureValue<TypeScriptType, TypescriptStructureMember> structureNode1 = new StructureValue<>(
                value.name(),
                value.modifiers(),
                collect,
                value.maybeTypeParams(),
                value.maybeExtended().map(JavaTypescriptParser::parseTypeList),
                value.maybeImplemented().map(JavaTypescriptParser::parseTypeList)
        );

        return new TypescriptStructureNode(type, structureNode1);
    }

    private static List<TypeScriptType> parseTypeList(List<JavaType> list) {
        return list.iter().map(JavaTypescriptParser::parseType).collect(new ListCollector<>());
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

        TypeScriptMethodHeader header = JavaTypescriptParser.parseMethodHeader(methodNode.header());
        ParameterizedMethodHeader<TypeScriptParameter> parameterizedHeader = new ParameterizedMethodHeader<>(header, parameters);
        return new TypescriptMethod(parameterizedHeader, methodNode.maybeChildren());
    }

    private static TypeScriptParameter parseParameter(JavaParameter parameter) {
        return switch (parameter) {
            case Whitespace whitespace -> whitespace;
            case JavaDefinition javaDefinition -> JavaTypescriptParser.parseDefinition(javaDefinition);
        };
    }

    private static TypeScriptMethodHeader parseMethodHeader(JavaMethodHeader header) {
        return switch (header) {
            case Constructor constructor -> constructor;
            case JavaDefinition javaDefinition -> JavaTypescriptParser.parseDefinition(javaDefinition);
        };
    }

    private static TypeScriptDefinition parseDefinition(JavaDefinition javaDefinition) {
        Definition<JavaType> definition = javaDefinition.definition();
        return switch (definition.type()) {
            case TypeScriptType type -> new TypeScriptDefinition(definition.withType(type));
            case JavaArrayType javaArrayType ->
                    new TypeScriptDefinition(definition.withType(JavaTypescriptParser.parseArrayType(javaArrayType)));
            case JavaTemplateType javaTemplateType ->
                    new TypeScriptDefinition(definition.withType(JavaTypescriptParser.parseTemplateType(javaTemplateType)));
            case VariadicType variadicType -> new TypeScriptDefinition(definition
                    .withName("..." + definition.name())
                    .withType(new TypescriptArrayType(JavaTypescriptParser.parseType(variadicType.child()))));
        };
    }

    private static TypeScriptType parseArrayType(JavaArrayType type) {
        return new TypescriptArrayType(JavaTypescriptParser.parseType(type.inner));
    }

    private static TypeScriptType parseType(JavaType variadicType) {
        return switch (variadicType) {
            case Symbol symbol -> symbol;
            case JavaArrayType type -> JavaTypescriptParser.parseArrayType(type);
            case JavaTemplateType templateType -> JavaTypescriptParser.parseTemplateType(templateType);
            case VariadicType type -> new Symbol("?");
        };
    }

    private static TypeScriptTemplateType parseTemplateType(JavaTemplateType type) {
        Symbol base = JavaTypescriptParser.parseBaseType(type.base);
        List<TypeScriptType> collect = JavaTypescriptParser.parseTypeList(type.typeArguments.arguments());
        return new TypeScriptTemplateType(base, new TypeArguments<>(collect));
    }

    private static Symbol parseBaseType(JavaBase base) {
        return switch (base) {
            case QualifiedType qualifiedType -> {
                String joined = qualifiedType.segments()
                        .iter()
                        .map(Segment::value)
                        .collect(new Joiner("."))
                        .orElse("");

                yield new Symbol(joined);
            }
            case Symbol symbol -> symbol;
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
    public CompileResult<UnitSet<TypescriptRoot>> apply(UnitSet<JavaRoot> initial) {
        return initial.iter()
                .map(JavaTypescriptParser::parseUnit)
                .collect(new CompileResultCollector<>(new UnitSetCollector<>()));
    }
}
