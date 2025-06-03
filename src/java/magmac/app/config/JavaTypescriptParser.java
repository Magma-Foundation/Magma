package magmac.app.config;

import magmac.api.Tuple2;
import magmac.api.collect.TupleCollector;
import magmac.api.collect.list.List;
import magmac.api.collect.list.Lists;
import magmac.api.iter.collect.Joiner;
import magmac.api.iter.collect.ListCollector;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResultCollector;
import magmac.app.compile.error.CompileResults;
import magmac.app.io.Location;
import magmac.app.io.sources.UnitSetCollector;
import magmac.app.lang.java.JavaAssignmentNode;
import magmac.app.lang.java.JavaBlock;
import magmac.app.lang.java.JavaBreak;
import magmac.app.lang.java.JavaCaseNode;
import magmac.app.lang.java.JavaConstructor;
import magmac.app.lang.java.JavaContinue;
import magmac.app.lang.java.JavaDefinition;
import magmac.app.lang.java.JavaEnumValues;
import magmac.app.lang.java.JavaFunctionSegment;
import magmac.app.lang.java.JavaFunctionSegmentValue;
import magmac.app.lang.java.JavaFunctionStatement;
import magmac.app.lang.java.JavaInvokable;
import magmac.app.lang.java.JavaMethod;
import magmac.app.lang.java.JavaMethodHeader;
import magmac.app.lang.java.JavaNamespacedNode;
import magmac.app.lang.java.JavaParameter;
import magmac.app.lang.java.JavaPost;
import magmac.app.lang.java.JavaReturnNode;
import magmac.app.lang.java.JavaRootSegment;
import magmac.app.lang.java.JavaStructureMember;
import magmac.app.lang.java.JavaStructureNode;
import magmac.app.lang.java.JavaStructureStatement;
import magmac.app.lang.java.JavaWhitespace;
import magmac.app.lang.java.JavaYieldNode;
import magmac.app.lang.node.ConditionalType;
import magmac.app.lang.node.Definition;
import magmac.app.lang.node.JavaArrayType;
import magmac.app.lang.node.JavaBase;
import magmac.app.lang.node.JavaBlockHeader;
import magmac.app.lang.node.JavaRoot;
import magmac.app.lang.node.JavaTemplateType;
import magmac.app.lang.node.JavaType;
import magmac.app.lang.node.ParameterizedMethodHeader;
import magmac.app.lang.node.Qualified;
import magmac.app.lang.node.Segment;
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
import magmac.app.lang.node.TypescriptBlock;
import magmac.app.lang.node.TypescriptBlockHeader;
import magmac.app.lang.node.TypescriptConditional;
import magmac.app.lang.node.TypescriptConstructor;
import magmac.app.lang.node.TypescriptFunctionSegment;
import magmac.app.lang.node.TypescriptMethod;
import magmac.app.lang.node.TypescriptRoot;
import magmac.app.lang.node.TypescriptStructureMember;
import magmac.app.lang.node.TypescriptStructureNode;
import magmac.app.lang.node.TypescriptStructureType;
import magmac.app.lang.node.VariadicType;
import magmac.app.lang.web.TypescriptBreak;
import magmac.app.lang.web.TypescriptContinue;
import magmac.app.lang.web.TypescriptFunctionSegmentValue;
import magmac.app.lang.web.TypescriptFunctionStatement;
import magmac.app.lang.web.TypescriptWhitespace;
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
                .flatMap(List::iter)
                .collect(new ListCollector<>());

        return CompileResults.Ok(new SimpleUnit<>(location, new TypescriptRoot(rootSegments)));
    }

    private static List<TypeScriptRootSegment> parseRootSegment(Location location, JavaRootSegment rootSegment) {
        return switch (rootSegment) {
            case JavaWhitespace whitespace -> Lists.of(new TypescriptWhitespace());
            case JavaNamespacedNode namespaced -> Lists.of(JavaTypescriptParser.parseNamespaced(location, namespaced));
            case JavaStructureNode structureNode -> JavaTypescriptParser.getCollect(structureNode);
        };
    }

    private static List<TypeScriptRootSegment> getCollect(JavaStructureNode structureNode) {
        return JavaTypescriptParser.parseStructure(structureNode)
                .iter()
                .map(JavaTypescriptParser::wrap)
                .collect(new ListCollector<>());
    }

    private static TypeScriptRootSegment wrap(TypescriptStructureNode value) {
        return value;
    }

    private static List<TypescriptStructureNode> parseStructure(JavaStructureNode structureNode) {
        return switch (structureNode.type()) {
            case Class, Record ->
                    JavaTypescriptParser.parseStructureWithType(TypescriptStructureType.Class, structureNode);
            case Interface ->
                    JavaTypescriptParser.parseStructureWithType(TypescriptStructureType.Interface, structureNode);
            case Enum -> Lists.empty();
        };
    }

    private static List<TypescriptStructureNode> parseStructureWithType(TypescriptStructureType type, JavaStructureNode structureNode) {
        StructureValue<JavaType, JavaStructureMember> value = structureNode.value;
        Tuple2<List<List<TypescriptStructureMember>>, List<List<TypescriptStructureNode>>> membersTuple = value.members()
                .iter()
                .map(JavaTypescriptParser::parseStructureMember)
                .collect(new TupleCollector<>(new ListCollector<>(), new ListCollector<>()));

        List<TypescriptStructureMember> members = membersTuple.left()
                .iter()
                .flatMap(List::iter)
                .collect(new ListCollector<>());

        List<TypescriptStructureNode> structures = membersTuple.right()
                .iter()
                .flatMap(List::iter)
                .collect(new ListCollector<>());

        StructureValue<TypeScriptType, TypescriptStructureMember> structureNode1 = new StructureValue<>(
                value.name(),
                value.modifiers(),
                members,
                value.maybeTypeParams(),
                value.maybeExtended().map(JavaTypescriptParser::parseTypeList),
                value.maybeImplemented().map(JavaTypescriptParser::parseTypeList)
        );

        return structures.addLast(new TypescriptStructureNode(type, structureNode1));
    }

    private static List<TypeScriptType> parseTypeList(List<JavaType> list) {
        return list.iter().map(JavaTypescriptParser::parseType).collect(new ListCollector<>());
    }

    private static Tuple2<List<TypescriptStructureMember>, List<TypescriptStructureNode>> parseStructureMember(JavaStructureMember structureNode) {
        return switch (structureNode) {
            case JavaWhitespace whitespace -> JavaTypescriptParser.getList();
            case JavaEnumValues enumValues -> JavaTypescriptParser.getList();
            case JavaStructureStatement structureStatement -> JavaTypescriptParser.getList();
            case JavaMethod methodNode ->
                    JavaTypescriptParser.getListListTuple2(JavaTypescriptParser.parseMethod(methodNode));
            case JavaStructureNode javaStructureNode ->
                    new Tuple2<>(Lists.empty(), JavaTypescriptParser.parseStructure(javaStructureNode));
        };
    }

    private static Tuple2<List<TypescriptStructureMember>, List<TypescriptStructureNode>> getListListTuple2(TypescriptStructureMember typescriptStructureMember) {
        return new Tuple2<>(Lists.of(typescriptStructureMember), Lists.empty());
    }

    private static Tuple2<List<TypescriptStructureMember>, List<TypescriptStructureNode>> getList() {
        return JavaTypescriptParser.getListListTuple2(new TypescriptWhitespace());
    }

    private static TypescriptStructureMember parseMethod(JavaMethod methodNode) {
        List<TypeScriptParameter> parameters = methodNode.parameters()
                .iter()
                .map(JavaTypescriptParser::parseParameter)
                .collect(new ListCollector<>());

        TypeScriptMethodHeader header = JavaTypescriptParser.parseMethodHeader(methodNode.header());
        ParameterizedMethodHeader<TypeScriptParameter> parameterizedHeader = new ParameterizedMethodHeader<>(header, parameters);
        return new TypescriptMethod(parameterizedHeader, methodNode.maybeChildren().map(JavaTypescriptParser::parseFunctionSegments));
    }

    private static List<TypescriptFunctionSegment> parseFunctionSegments(List<JavaFunctionSegment> segments) {
        return segments.iter()
                .map(JavaTypescriptParser::parseFunctionSegment)
                .collect(new ListCollector<>());
    }

    private static TypescriptFunctionSegment parseFunctionSegment(JavaFunctionSegment segment) {
        return switch (segment) {
            case JavaBlock block -> JavaTypescriptParser.parseBlock(block);
            case JavaCaseNode caseNode -> new TypescriptWhitespace();
            case JavaReturnNode javaReturnNode -> new TypescriptWhitespace();
            case JavaFunctionStatement functionStatement ->
                    JavaTypescriptParser.parseFunctionStatement(functionStatement);
            case JavaWhitespace whitespace -> new TypescriptWhitespace();
        };
    }

    private static TypescriptFunctionSegment parseFunctionStatement(JavaFunctionStatement functionStatement) {
        return new TypescriptFunctionStatement(JavaTypescriptParser.parseFunctionStatementValue(functionStatement.child()));
    }

    private static TypescriptFunctionSegmentValue parseFunctionStatementValue(JavaFunctionSegmentValue child) {
        return switch (child) {
            case JavaBreak javaBreak -> new TypescriptBreak();
            case JavaContinue javaContinue -> new TypescriptContinue();
            case JavaReturnNode javaReturnNode -> new TypescriptBreak();
            case JavaYieldNode javaYieldNode -> new TypescriptBreak();
            case JavaInvokable javaInvokable -> new TypescriptBreak();
            case JavaPost javaPost -> new TypescriptBreak();
            case JavaAssignmentNode javaAssignmentNode -> new TypescriptBreak();
        };
    }

    private static TypescriptBlock parseBlock(JavaBlock block) {
        return new TypescriptBlock(JavaTypescriptParser.parseHeader(block.header()), JavaTypescriptParser.parseFunctionSegments(block.segments()));
    }

    private static TypescriptBlockHeader parseHeader(JavaBlockHeader header) {
        return new TypescriptConditional(ConditionalType.If, new Symbol("true"));
    }

    private static TypeScriptParameter parseParameter(JavaParameter parameter) {
        return switch (parameter) {
            case JavaWhitespace whitespace -> new TypescriptWhitespace();
            case JavaDefinition javaDefinition -> JavaTypescriptParser.parseDefinition(javaDefinition);
        };
    }

    private static TypeScriptMethodHeader parseMethodHeader(JavaMethodHeader header) {
        return switch (header) {
            case JavaConstructor constructor -> new TypescriptConstructor();
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
            case Qualified qualified ->
                    new TypeScriptDefinition(definition.withType(JavaTypescriptParser.parseQualifiedType(qualified)));
        };
    }

    private static Symbol parseQualifiedType(Qualified qualified) {
        String joined = qualified.segments()
                .iter()
                .map(Segment::value)
                .collect(new Joiner("."))
                .orElse("");

        return new Symbol(joined);
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
            case Qualified qualified -> JavaTypescriptParser.parseQualifiedType(qualified);
        };
    }

    private static TypeScriptTemplateType parseTemplateType(JavaTemplateType type) {
        Symbol base = JavaTypescriptParser.parseBaseType(type.base);
        List<TypeScriptType> collect = JavaTypescriptParser.parseTypeList(type.typeArguments.arguments());
        return new TypeScriptTemplateType(base, new TypeArguments<>(collect));
    }

    private static Symbol parseBaseType(JavaBase base) {
        return switch (base) {
            case Qualified qualifiedType -> {
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
            case Package -> new TypescriptWhitespace();
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
