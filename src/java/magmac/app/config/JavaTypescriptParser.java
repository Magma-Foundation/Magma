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
import magmac.app.lang.CommonLang;
import magmac.app.lang.java.JavaAssignmentNode;
import magmac.app.lang.java.JavaBlock;
import magmac.app.lang.java.JavaBreak;
import magmac.app.lang.java.JavaCaseNode;
import magmac.app.lang.java.JavaConstruction;
import magmac.app.lang.java.JavaConstructor;
import magmac.app.lang.java.JavaContinue;
import magmac.app.lang.java.JavaEnumValues;
import magmac.app.lang.java.JavaFunctionSegment;
import magmac.app.lang.java.JavaFunctionSegmentValue;
import magmac.app.lang.java.JavaFunctionStatement;
import magmac.app.lang.java.JavaLang;
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
import magmac.app.lang.java.JavaYieldNode;
import magmac.app.lang.node.ConditionalType;
import magmac.app.lang.node.ParameterizedMethodHeader;
import magmac.app.lang.node.Segment;
import magmac.app.lang.node.StructureValue;
import magmac.app.lang.node.TypeArguments;
import magmac.app.lang.web.TypescriptCaller;
import magmac.app.lang.web.TypescriptFunctionSegmentValue;
import magmac.app.lang.web.TypescriptFunctionStatement;
import magmac.app.lang.web.TypescriptLang;
import magmac.app.lang.web.TypescriptSymbol;
import magmac.app.stage.parse.Parser;
import magmac.app.stage.unit.SimpleUnit;
import magmac.app.stage.unit.Unit;
import magmac.app.stage.unit.UnitSet;

class JavaTypescriptParser implements Parser<JavaLang.JavaRoot, TypescriptLang.TypescriptRoot> {
    private static CompileResult<Unit<TypescriptLang.TypescriptRoot>> parseUnit(Unit<JavaLang.JavaRoot> unit) {
        return unit.destruct(JavaTypescriptParser::parseRoot);
    }

    private static CompileResult<Unit<TypescriptLang.TypescriptRoot>> parseRoot(Location location, JavaLang.JavaRoot root) {
        List<TypescriptLang.TypeScriptRootSegment> rootSegments = root.children()
                .iter()
                .map(rootSegment -> JavaTypescriptParser.parseRootSegment(location, rootSegment))
                .flatMap(List::iter)
                .collect(new ListCollector<>());

        return CompileResults.Ok(new SimpleUnit<>(location, new TypescriptLang.TypescriptRoot(rootSegments)));
    }

    private static List<TypescriptLang.TypeScriptRootSegment> parseRootSegment(Location location, JavaRootSegment rootSegment) {
        return switch (rootSegment) {
            case JavaLang.JavaWhitespace whitespace ->
                    Lists.of(new TypescriptLang.TypescriptArgument.TypescriptWhitespace());
            case JavaNamespacedNode namespaced -> Lists.of(JavaTypescriptParser.parseNamespaced(location, namespaced));
            case JavaStructureNode structureNode -> JavaTypescriptParser.getCollect(structureNode);
        };
    }

    private static List<TypescriptLang.TypeScriptRootSegment> getCollect(JavaStructureNode structureNode) {
        return JavaTypescriptParser.parseStructure(structureNode)
                .iter()
                .map(JavaTypescriptParser::wrap)
                .collect(new ListCollector<>());
    }

    private static TypescriptLang.TypeScriptRootSegment wrap(TypescriptLang.TypescriptStructureNode value) {
        return value;
    }

    private static List<TypescriptLang.TypescriptStructureNode> parseStructure(JavaStructureNode structureNode) {
        return switch (structureNode.type()) {
            case Class, Record ->
                    JavaTypescriptParser.parseStructureWithType(TypescriptLang.TypescriptStructureType.Class, structureNode);
            case Interface ->
                    JavaTypescriptParser.parseStructureWithType(TypescriptLang.TypescriptStructureType.Interface, structureNode);
            case Enum -> Lists.empty();
        };
    }

    private static List<TypescriptLang.TypescriptStructureNode> parseStructureWithType(TypescriptLang.TypescriptStructureType type, JavaStructureNode structureNode) {
        StructureValue<JavaLang.JavaType, JavaStructureMember> value = structureNode.value;
        Tuple2<List<List<TypescriptLang.TypescriptStructureMember>>, List<List<TypescriptLang.TypescriptStructureNode>>> membersTuple = value.members()
                .iter()
                .map(JavaTypescriptParser::parseStructureMember)
                .collect(new TupleCollector<>(new ListCollector<>(), new ListCollector<>()));

        List<TypescriptLang.TypescriptStructureMember> members = membersTuple.left()
                .iter()
                .flatMap(List::iter)
                .collect(new ListCollector<>());

        List<TypescriptLang.TypescriptStructureNode> structures = membersTuple.right()
                .iter()
                .flatMap(List::iter)
                .collect(new ListCollector<>());

        StructureValue<TypescriptLang.TypeScriptType, TypescriptLang.TypescriptStructureMember> structureNode1 = new StructureValue<>(
                value.name(),
                value.modifiers(),
                members,
                value.maybeTypeParams(),
                value.maybeExtended().map(JavaTypescriptParser::parseTypeList),
                value.maybeImplemented().map(JavaTypescriptParser::parseTypeList)
        );

        return structures.addLast(new TypescriptLang.TypescriptStructureNode(type, structureNode1));
    }

    private static List<TypescriptLang.TypeScriptType> parseTypeList(List<JavaLang.JavaType> list) {
        return list.iter().map(JavaTypescriptParser::parseType).collect(new ListCollector<>());
    }

    private static Tuple2<List<TypescriptLang.TypescriptStructureMember>, List<TypescriptLang.TypescriptStructureNode>> parseStructureMember(JavaStructureMember structureNode) {
        return switch (structureNode) {
            case JavaLang.JavaWhitespace whitespace -> JavaTypescriptParser.getList();
            case JavaEnumValues enumValues -> JavaTypescriptParser.getList();
            case JavaStructureStatement structureStatement -> JavaTypescriptParser.getList();
            case JavaMethod methodNode ->
                    JavaTypescriptParser.getListListTuple2(JavaTypescriptParser.parseMethod(methodNode));
            case JavaStructureNode javaStructureNode ->
                    new Tuple2<>(Lists.empty(), JavaTypescriptParser.parseStructure(javaStructureNode));
        };
    }

    private static Tuple2<List<TypescriptLang.TypescriptStructureMember>, List<TypescriptLang.TypescriptStructureNode>> getListListTuple2(TypescriptLang.TypescriptStructureMember typescriptStructureMember) {
        return new Tuple2<>(Lists.of(typescriptStructureMember), Lists.empty());
    }

    private static Tuple2<List<TypescriptLang.TypescriptStructureMember>, List<TypescriptLang.TypescriptStructureNode>> getList() {
        return JavaTypescriptParser.getListListTuple2(new TypescriptLang.TypescriptArgument.TypescriptWhitespace());
    }

    private static TypescriptLang.TypescriptStructureMember parseMethod(JavaMethod methodNode) {
        List<TypescriptLang.TypeScriptParameter> parameters = methodNode.parameters()
                .iter()
                .map(JavaTypescriptParser::parseParameter)
                .collect(new ListCollector<>());

        TypescriptLang.TypeScriptMethodHeader header = JavaTypescriptParser.parseMethodHeader(methodNode.header());
        ParameterizedMethodHeader<TypescriptLang.TypeScriptParameter> parameterizedHeader = new ParameterizedMethodHeader<>(header, parameters);
        return new TypescriptLang.TypescriptMethod(parameterizedHeader, methodNode.maybeChildren().map(JavaTypescriptParser::parseFunctionSegments));
    }

    private static List<TypescriptLang.TypescriptFunctionSegment> parseFunctionSegments(List<JavaFunctionSegment> segments) {
        return segments.iter()
                .map(JavaTypescriptParser::parseFunctionSegment)
                .collect(new ListCollector<>());
    }

    private static TypescriptLang.TypescriptFunctionSegment parseFunctionSegment(JavaFunctionSegment segment) {
        return switch (segment) {
            case JavaLang.JavaWhitespace whitespace -> new TypescriptLang.TypescriptArgument.TypescriptWhitespace();
            case JavaBlock block -> JavaTypescriptParser.parseBlock(block);
            case JavaCaseNode caseNode -> new TypescriptLang.TypescriptArgument.TypescriptWhitespace();
            case JavaReturnNode javaReturnNode -> new TypescriptLang.TypescriptArgument.TypescriptWhitespace();
            case JavaFunctionStatement functionStatement ->
                    JavaTypescriptParser.parseFunctionStatement(functionStatement);
        };
    }

    private static TypescriptLang.TypescriptFunctionSegment parseFunctionStatement(JavaFunctionStatement functionStatement) {
        return new TypescriptFunctionStatement(JavaTypescriptParser.parseFunctionStatementValue(functionStatement.child()));
    }

    private static TypescriptFunctionSegmentValue parseFunctionStatementValue(JavaFunctionSegmentValue child) {
        return switch (child) {
            case JavaBreak javaBreak -> new TypescriptLang.TypescriptBreak();
            case JavaContinue javaContinue -> new TypescriptLang.TypescriptContinue();
            case JavaYieldNode javaYieldNode -> new TypescriptLang.TypescriptBreak();
            case JavaPost javaPost ->
                    new TypescriptLang.Post(javaPost.variant(), JavaTypescriptParser.parseValue(javaPost.value()));
            case JavaReturnNode javaReturnNode ->
                    new TypescriptLang.TypescriptReturnNode(JavaTypescriptParser.parseValue(javaReturnNode.child()));
            case JavaLang.Invokable invokable -> JavaTypescriptParser.parseInvokable(invokable);
            case JavaAssignmentNode javaAssignmentNode -> new TypescriptLang.TypescriptBreak();
        };
    }

    private static TypescriptLang.Invokable parseInvokable(JavaLang.Invokable invokable) {
        return new TypescriptLang.Invokable(JavaTypescriptParser.parseCaller(invokable.caller()), JavaTypescriptParser.parseArguments(invokable.arguments()));
    }

    private static TypescriptCaller parseCaller(JavaLang.JavaCaller caller) {
        return switch (caller) {
            case JavaLang.JavaValue javaValue -> JavaTypescriptParser.parseValue(javaValue);
            case JavaConstruction javaConstruction ->
                    new TypescriptLang.Construction(JavaTypescriptParser.parseType(javaConstruction.type()));
        };
    }

    private static List<TypescriptLang.TypescriptArgument> parseArguments(List<JavaLang.JavaArgument> arguments) {
        return arguments.iter()
                .map(JavaTypescriptParser::parseArgument)
                .collect(new ListCollector<>());
    }

    private static TypescriptLang.TypescriptArgument parseArgument(JavaLang.JavaArgument argument) {
        return switch (argument) {
            case JavaLang.JavaWhitespace whitespace -> new TypescriptLang.TypescriptArgument.TypescriptWhitespace();
            case JavaLang.JavaValue value -> JavaTypescriptParser.parseValue(value);
        };
    }

    private static TypescriptLang.TypescriptValue parseValue(JavaLang.JavaValue child) {
        return switch (child) {
            case JavaLang.Access access ->
                    new TypescriptLang.Access(JavaTypescriptParser.parseValue(access.receiver()), access.property());
            case JavaLang.Char aChar -> new TypescriptLang.Char(aChar.value());
            case JavaLang.Index index ->
                    new TypescriptLang.Index(JavaTypescriptParser.parseValue(index.parent()), JavaTypescriptParser.parseValue(index.argument()));
            case JavaLang.Invokable invokable -> JavaTypescriptParser.parseInvokable(invokable);
            case JavaLang.JavaLambda javaLambda -> new TypescriptLang.NumberNode("0");
            case JavaLang.JavaNot javaNot -> new TypescriptLang.NumberNode("0");
            case JavaLang.JavaNumberNode javaNumberNode -> new TypescriptLang.NumberNode("0");
            case JavaLang.JavaOperation javaOperation -> new TypescriptLang.NumberNode("0");
            case JavaLang.JavaStringNode javaStringNode -> new TypescriptLang.NumberNode("0");
            case JavaLang.JavaSwitchNode javaSwitchNode -> new TypescriptLang.NumberNode("0");
            case JavaLang.JavaSymbol javaSymbol -> new TypescriptLang.NumberNode("0");
        };
    }

    private static TypescriptLang.TypescriptBlock parseBlock(JavaBlock block) {
        return new TypescriptLang.TypescriptBlock(JavaTypescriptParser.parseHeader(block.header()), JavaTypescriptParser.parseFunctionSegments(block.segments()));
    }

    private static TypescriptLang.TypescriptBlockHeader parseHeader(JavaLang.JavaBlockHeader header) {
        return new TypescriptLang.TypescriptConditional(ConditionalType.If, new TypescriptSymbol("true"));
    }

    private static TypescriptLang.TypeScriptParameter parseParameter(JavaParameter parameter) {
        return switch (parameter) {
            case JavaLang.JavaWhitespace whitespace -> new TypescriptLang.TypescriptArgument.TypescriptWhitespace();
            case JavaLang.JavaDefinition javaDefinition -> JavaTypescriptParser.parseDefinition(javaDefinition);
        };
    }

    private static TypescriptLang.TypeScriptMethodHeader parseMethodHeader(JavaMethodHeader header) {
        return switch (header) {
            case JavaConstructor constructor -> new TypescriptLang.TypescriptConstructor();
            case JavaLang.JavaDefinition javaDefinition -> JavaTypescriptParser.parseDefinition(javaDefinition);
        };
    }

    private static TypescriptLang.TypeScriptDefinition parseDefinition(JavaLang.JavaDefinition javaDefinition) {
        CommonLang.Definition<JavaLang.JavaType> definition = javaDefinition.definition();
        return switch (definition.type()) {
            case JavaLang.JavaArrayType javaArrayType ->
                    new TypescriptLang.TypeScriptDefinition(definition.withType(JavaTypescriptParser.parseArrayType(javaArrayType)));
            case JavaLang.JavaTemplateType javaTemplateType ->
                    new TypescriptLang.TypeScriptDefinition(definition.withType(JavaTypescriptParser.parseTemplateType(javaTemplateType)));
            case JavaLang.JavaVariadicType variadicType -> new TypescriptLang.TypeScriptDefinition(definition
                    .withName("..." + definition.name())
                    .withType(new TypescriptLang.TypescriptArrayType(JavaTypescriptParser.parseType(variadicType.child()))));
            case JavaLang.JavaQualified qualified ->
                    new TypescriptLang.TypeScriptDefinition(definition.withType(JavaTypescriptParser.parseQualifiedType(qualified)));
            case JavaLang.JavaSymbol javaSymbol ->
                    new TypescriptLang.TypeScriptDefinition(definition.withType(JavaTypescriptParser.parseSymbol(javaSymbol)));
        };
    }

    private static TypescriptSymbol parseSymbol(JavaLang.JavaSymbol javaSymbol) {
        return new TypescriptSymbol(javaSymbol.value());
    }

    private static TypescriptSymbol parseQualifiedType(JavaLang.JavaQualified qualified) {
        String joined = qualified.segments()
                .iter()
                .map(Segment::value)
                .collect(new Joiner("."))
                .orElse("");

        return new TypescriptSymbol(joined);
    }

    private static TypescriptLang.TypeScriptType parseArrayType(JavaLang.JavaArrayType type) {
        return new TypescriptLang.TypescriptArrayType(JavaTypescriptParser.parseType(type.inner));
    }

    private static TypescriptLang.TypeScriptType parseType(JavaLang.JavaType variadicType) {
        return switch (variadicType) {
            case JavaLang.JavaSymbol symbol -> JavaTypescriptParser.parseSymbol(symbol);
            case JavaLang.JavaArrayType type -> JavaTypescriptParser.parseArrayType(type);
            case JavaLang.JavaTemplateType templateType -> JavaTypescriptParser.parseTemplateType(templateType);
            case JavaLang.JavaVariadicType type -> new TypescriptSymbol("?");
            case JavaLang.JavaQualified qualified -> JavaTypescriptParser.parseQualifiedType(qualified);
        };
    }

    private static TypescriptLang.TypeScriptTemplateType parseTemplateType(JavaLang.JavaTemplateType type) {
        JavaLang.JavaSymbol base = JavaTypescriptParser.parseBaseType(type.base);
        List<TypescriptLang.TypeScriptType> collect = JavaTypescriptParser.parseTypeList(type.typeArguments.arguments());
        return new TypescriptLang.TypeScriptTemplateType(base, new TypeArguments<>(collect));
    }

    private static JavaLang.JavaSymbol parseBaseType(JavaLang.JavaBase base) {
        return switch (base) {
            case JavaLang.JavaQualified qualifiedType -> {
                String joined = qualifiedType.segments()
                        .iter()
                        .map(Segment::value)
                        .collect(new Joiner("."))
                        .orElse("");

                yield new JavaLang.JavaSymbol(joined);
            }
            case JavaLang.JavaSymbol symbol -> symbol;
        };
    }

    private static TypescriptLang.TypeScriptRootSegment parseNamespaced(Location location, JavaNamespacedNode namespaced) {
        return switch (namespaced.type()) {
            case Package -> new TypescriptLang.TypescriptArgument.TypescriptWhitespace();
            case Import -> JavaTypescriptParser.parseImport(location, namespaced.segments());
        };
    }

    private static TypescriptLang.TypeScriptImport parseImport(Location location, List<Segment> segments) {
        List<String> segmentValues = segments.iter()
                .map(Segment::value)
                .collect(new ListCollector<>());

        List<Segment> before = location.namespace()
                .iter()
                .map(_ -> "..")
                .map(Segment::new)
                .collect(new ListCollector<>());

        Segment last = new Segment(segmentValues.findLast().orElse(""));
        return new TypescriptLang.TypeScriptImport(Lists.of(last), before.addAllLast(segments));
    }

    @Override
    public CompileResult<UnitSet<TypescriptLang.TypescriptRoot>> apply(UnitSet<JavaLang.JavaRoot> initial) {
        return initial.iter()
                .map(JavaTypescriptParser::parseUnit)
                .collect(new CompileResultCollector<>(new UnitSetCollector<>()));
    }
}
