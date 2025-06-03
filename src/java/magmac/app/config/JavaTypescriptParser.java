package magmac.app.config;

import magmac.api.Option;
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
import magmac.app.lang.common.Annotation;
import magmac.app.lang.java.JavaBreak;
import magmac.app.lang.java.JavaConstruction;
import magmac.app.lang.java.JavaConstructor;
import magmac.app.lang.java.JavaContinue;
import magmac.app.lang.java.JavaEnumValues;
import magmac.app.lang.java.JavaFunctionSegment;
import magmac.app.lang.java.JavaFunctionSegmentValue;
import magmac.app.lang.java.JavaLang;
import magmac.app.lang.java.JavaMethod;
import magmac.app.lang.java.JavaMethodHeader;
import magmac.app.lang.java.JavaNamespacedNode;
import magmac.app.lang.java.JavaParameter;
import magmac.app.lang.java.JavaPost;
import magmac.app.lang.java.JavaRootSegment;
import magmac.app.lang.java.JavaStructureMember;
import magmac.app.lang.java.JavaStructureStatement;
import magmac.app.lang.java.JavaYieldNode;
import magmac.app.lang.node.ConditionalType;
import magmac.app.lang.node.Modifier;
import magmac.app.lang.node.ParameterizedMethodHeader;
import magmac.app.lang.node.Segment;
import magmac.app.lang.node.StructureValue;
import magmac.app.lang.node.TypeArguments;
import magmac.app.lang.web.Caller;
import magmac.app.lang.web.FunctionStatement;
import magmac.app.lang.web.Symbol;
import magmac.app.lang.web.TypescriptLang;
import magmac.app.stage.parse.Parser;
import magmac.app.stage.unit.SimpleUnit;
import magmac.app.stage.unit.Unit;
import magmac.app.stage.unit.UnitSet;

class JavaTypescriptParser implements Parser<JavaLang.JavaRoot, TypescriptLang.TypescriptRoot> {
    private static CompileResult<Unit<TypescriptLang.TypescriptRoot>> parseUnit(Unit<JavaLang.JavaRoot> unit) {
        return unit.destruct(JavaTypescriptParser::parseRoot);
    }

    private static CompileResult<Unit<TypescriptLang.TypescriptRoot>> parseRoot(Location location, JavaLang.JavaRoot root) {
        var rootSegments = root.children()
                .iter()
                .map(rootSegment -> JavaTypescriptParser.parseRootSegment(location, rootSegment))
                .flatMap(List::iter)
                .collect(new ListCollector<>());

        return CompileResults.Ok(new SimpleUnit<>(location, new TypescriptLang.TypescriptRoot(rootSegments)));
    }

    private static List<TypescriptLang.TypeScriptRootSegment> parseRootSegment(Location location, JavaRootSegment rootSegment) {
        return switch (rootSegment) {
            case JavaLang.Whitespace whitespace -> Lists.of(new TypescriptLang.Whitespace());
            case JavaNamespacedNode namespaced -> Lists.of(JavaTypescriptParser.parseNamespaced(location, namespaced));
            case JavaLang.StructureNode structureNode -> JavaTypescriptParser.getCollect(structureNode);
        };
    }

    private static List<TypescriptLang.TypeScriptRootSegment> getCollect(JavaLang.StructureNode structureNode) {
        return JavaTypescriptParser.parseStructure(structureNode)
                .iter()
                .map(JavaTypescriptParser::wrap)
                .collect(new ListCollector<>());
    }

    private static TypescriptLang.TypeScriptRootSegment wrap(TypescriptLang.StructureNode value) {
        return value;
    }

    private static List<TypescriptLang.StructureNode> parseStructure(JavaLang.StructureNode structureNode) {
        return switch (structureNode.type()) {
            case Class, Record ->
                    JavaTypescriptParser.parseStructureWithType(TypescriptLang.StructureType.Class, structureNode);
            case Interface ->
                    JavaTypescriptParser.parseStructureWithType(TypescriptLang.StructureType.Interface, structureNode);
            case Enum -> Lists.empty();
        };
    }

    private static List<TypescriptLang.StructureNode> parseStructureWithType(
            TypescriptLang.StructureType type,
            JavaLang.StructureNode structureNode
    ) {
        var value = structureNode.value;
        var membersTuple = value.members()
                .iter()
                .map(JavaTypescriptParser::parseStructureMember)
                .collect(new TupleCollector<>(new ListCollector<>(), new ListCollector<>()));

        var members = membersTuple.left()
                .iter()
                .flatMap(List::iter)
                .collect(new ListCollector<>());

        var structures = membersTuple.right()
                .iter()
                .flatMap(List::iter)
                .collect(new ListCollector<>());

        var structureNode1 = new StructureValue<TypescriptLang.Type, TypescriptLang.TypescriptStructureMember>(
                value.name(),
                value.modifiers(),
                members,
                value.maybeTypeParams(),
                value.maybeExtended().map(JavaTypescriptParser::parseTypeList),
                value.maybeImplemented().map(JavaTypescriptParser::parseTypeList)
        );

        return structures.addLast(new TypescriptLang.StructureNode(type, structureNode1));
    }

    private static List<TypescriptLang.Type> parseTypeList(List<JavaLang.JavaType> list) {
        return list.iter().map(JavaTypescriptParser::parseType).collect(new ListCollector<>());
    }

    private static Tuple2<List<TypescriptLang.TypescriptStructureMember>, List<TypescriptLang.StructureNode>> parseStructureMember(JavaStructureMember structureNode) {
        return switch (structureNode) {
            case JavaLang.Whitespace whitespace -> JavaTypescriptParser.getList();
            case JavaEnumValues enumValues -> JavaTypescriptParser.getList();
            case JavaStructureStatement structureStatement -> JavaTypescriptParser.getList();
            case JavaMethod methodNode ->
                    JavaTypescriptParser.getListListTuple2(JavaTypescriptParser.parseMethod(methodNode));
            case JavaLang.StructureNode javaStructureNode ->
                    new Tuple2<>(Lists.empty(), JavaTypescriptParser.parseStructure(javaStructureNode));
        };
    }

    private static Tuple2<List<TypescriptLang.TypescriptStructureMember>, List<TypescriptLang.StructureNode>> getListListTuple2(TypescriptLang.TypescriptStructureMember typescriptStructureMember) {
        return new Tuple2<>(Lists.of(typescriptStructureMember), Lists.empty());
    }

    private static Tuple2<List<TypescriptLang.TypescriptStructureMember>, List<TypescriptLang.StructureNode>> getList() {
        return JavaTypescriptParser.getListListTuple2(new TypescriptLang.Whitespace());
    }

    private static TypescriptLang.TypescriptStructureMember parseMethod(JavaMethod methodNode) {
        var parameters = methodNode.parameters()
                .iter()
                .map(JavaTypescriptParser::parseParameter)
                .collect(new ListCollector<>());

        var header = JavaTypescriptParser.parseMethodHeader(methodNode.header());
        var parameterizedHeader = new ParameterizedMethodHeader<TypescriptLang.TypeScriptParameter>(header, parameters);
        return new TypescriptLang.TypescriptMethod(parameterizedHeader, methodNode.maybeChildren().map(JavaTypescriptParser::parseFunctionSegments));
    }

    private static List<TypescriptLang.FunctionSegment> parseFunctionSegments(List<JavaFunctionSegment> segments) {
        return segments.iter()
                .map(JavaTypescriptParser::parseFunctionSegment)
                .collect(new ListCollector<>());
    }

    private static TypescriptLang.FunctionSegment parseFunctionSegment(JavaFunctionSegment segment) {
        return switch (segment) {
            case JavaLang.Whitespace whitespace -> new TypescriptLang.Whitespace();
            case JavaLang.Block block -> JavaTypescriptParser.parseBlock(block);
            case JavaLang.Case caseNode -> new TypescriptLang.Whitespace();
            case JavaLang.Return aReturn -> new TypescriptLang.Return(JavaTypescriptParser.parseValue(aReturn.child()));
            case JavaLang.FunctionStatement functionStatement ->
                    JavaTypescriptParser.parseFunctionStatement(functionStatement);
        };
    }

    private static TypescriptLang.FunctionSegment parseFunctionStatement(JavaLang.FunctionStatement functionStatement) {
        return new FunctionStatement(JavaTypescriptParser.parseFunctionStatementValue(functionStatement.child()));
    }

    private static TypescriptLang.FunctionSegment.Value parseFunctionStatementValue(JavaFunctionSegmentValue child) {
        return switch (child) {
            case JavaBreak javaBreak -> new TypescriptLang.Break();
            case JavaContinue javaContinue -> new TypescriptLang.Continue();
            case JavaYieldNode javaYieldNode -> new TypescriptLang.Break();
            case JavaPost javaPost ->
                    new TypescriptLang.Post(javaPost.variant(), JavaTypescriptParser.parseValue(javaPost.value()));
            case JavaLang.Return aReturn -> new TypescriptLang.Return(JavaTypescriptParser.parseValue(aReturn.child()));
            case JavaLang.Invokable invokable -> JavaTypescriptParser.parseInvokable(invokable);
            case JavaLang.Assignment assignment ->
                    new TypescriptLang.Assignment(JavaTypescriptParser.parseAssignable(assignment.assignable()), JavaTypescriptParser.parseValue(assignment.value()));
        };
    }

    private static TypescriptLang.Assignable parseAssignable(JavaLang.Assignable assignable) {
        return switch (assignable) {
            case JavaLang.Definition definition -> JavaTypescriptParser.parseDefinition(definition);
            case JavaLang.Value value -> JavaTypescriptParser.parseValue(value);
        };
    }

    private static TypescriptLang.Invokable parseInvokable(JavaLang.Invokable invokable) {
        return new TypescriptLang.Invokable(JavaTypescriptParser.parseCaller(invokable.caller()), JavaTypescriptParser.parseArguments(invokable.arguments()));
    }

    private static Caller parseCaller(JavaLang.JavaCaller caller) {
        return switch (caller) {
            case JavaLang.Value javaValue -> JavaTypescriptParser.parseValue(javaValue);
            case JavaConstruction javaConstruction ->
                    new TypescriptLang.Construction(JavaTypescriptParser.parseType(javaConstruction.type()));
        };
    }

    private static List<TypescriptLang.Argument> parseArguments(List<JavaLang.JavaArgument> arguments) {
        return arguments.iter()
                .map(JavaTypescriptParser::parseArgument)
                .collect(new ListCollector<>());
    }

    private static TypescriptLang.Argument parseArgument(JavaLang.JavaArgument argument) {
        return switch (argument) {
            case JavaLang.Whitespace whitespace -> new TypescriptLang.Whitespace();
            case JavaLang.Value value -> JavaTypescriptParser.parseValue(value);
        };
    }

    private static TypescriptLang.Value parseValue(JavaLang.Value child) {
        return switch (child) {
            case JavaLang.Access access -> JavaTypescriptParser.parseAccess(access);
            case JavaLang.Char aChar -> new TypescriptLang.Char(aChar.value());
            case JavaLang.Index index -> JavaTypescriptParser.parseIndex(index);
            case JavaLang.Invokable invokable -> JavaTypescriptParser.parseInvokable(invokable);
            case JavaLang.Not not -> new TypescriptLang.Not(JavaTypescriptParser.parseValue(not.value()));
            case JavaLang.Number number -> new TypescriptLang.Number(number.value());
            case JavaLang.operation operation -> JavaTypescriptParser.parseOperation(operation);
            case JavaLang.StringValue javaStringNode -> new TypescriptLang.StringValue(javaStringNode.value());
            case JavaLang.Symbol symbol -> new TypescriptLang.Symbol(symbol.value());
            case JavaLang.Lambda javaLambda -> new TypescriptLang.Number("0");
            case JavaLang.SwitchNode javaSwitchNode -> new TypescriptLang.Number("0");
            case JavaLang.InstanceOf instanceOf -> new TypescriptLang.Number("0");
        };
    }

    private static TypescriptLang.Access parseAccess(JavaLang.Access access) {
        return new TypescriptLang.Access(JavaTypescriptParser.parseValue(access.receiver()), access.property());
    }

    private static TypescriptLang.Index parseIndex(JavaLang.Index index) {
        return new TypescriptLang.Index(JavaTypescriptParser.parseValue(index.parent()), JavaTypescriptParser.parseValue(index.argument()));
    }

    private static TypescriptLang.Operation parseOperation(JavaLang.operation operation) {
        return new TypescriptLang.Operation(JavaTypescriptParser.parseValue(operation.left()), operation.operator(), JavaTypescriptParser.parseValue(operation.right()));
    }

    private static TypescriptLang.Block parseBlock(JavaLang.Block block) {
        return new TypescriptLang.Block(JavaTypescriptParser.parseHeader(block.header()), JavaTypescriptParser.parseFunctionSegments(block.segments()));
    }

    private static TypescriptLang.TypescriptBlockHeader parseHeader(JavaLang.BlockHeader header) {
        return new TypescriptLang.TypescriptConditional(ConditionalType.If, new Symbol("true"));
    }

    private static TypescriptLang.TypeScriptParameter parseParameter(JavaParameter parameter) {
        return switch (parameter) {
            case JavaLang.Whitespace whitespace -> new TypescriptLang.Whitespace();
            case JavaLang.Definition javaDefinition -> JavaTypescriptParser.parseDefinition(javaDefinition);
        };
    }

    private static TypescriptLang.TypeScriptMethodHeader parseMethodHeader(JavaMethodHeader header) {
        return switch (header) {
            case JavaConstructor constructor -> new TypescriptLang.TypescriptConstructor();
            case JavaLang.Definition javaDefinition -> JavaTypescriptParser.parseDefinition(javaDefinition);
        };
    }

    private static TypescriptLang.Definition parseDefinition(JavaLang.Definition definition) {
        var maybeAnnotations = definition.maybeAnnotations();
        var maybeModifiers = definition.modifiers();
        var maybeTypeParameters = definition.maybeTypeParams();
        var type = definition.type();
        var oldName = definition.name();

        if (type instanceof JavaLang.JavaVariadicType(var child)) {
            var newType = new TypescriptLang.ArrayType(JavaTypescriptParser.parseType(child));
            var name = "..." + oldName;
            return new TypescriptLang.Definition(maybeAnnotations, maybeModifiers, name, maybeTypeParameters, newType);
        }
        else {
            return new TypescriptLang.Definition(maybeAnnotations, maybeModifiers, oldName, maybeTypeParameters, JavaTypescriptParser.parseType(definition.type()));
        }
    }

    private static Symbol parseSymbol(JavaLang.Symbol symbol) {
        return new Symbol(symbol.value());
    }

    private static Symbol parseQualifiedType(JavaLang.Qualified qualified) {
        var joined = qualified.segments()
                .iter()
                .map(Segment::value)
                .collect(new Joiner("."))
                .orElse("");

        return new Symbol(joined);
    }

    private static TypescriptLang.Type parseArrayType(JavaLang.JavaArrayType type) {
        return new TypescriptLang.ArrayType(JavaTypescriptParser.parseType(type.inner));
    }

    private static TypescriptLang.Type parseType(JavaLang.JavaType variadicType) {
        return switch (variadicType) {
            case JavaLang.Symbol symbol -> JavaTypescriptParser.parseSymbol(symbol);
            case JavaLang.JavaArrayType type -> JavaTypescriptParser.parseArrayType(type);
            case JavaLang.JavaTemplateType templateType -> JavaTypescriptParser.parseTemplateType(templateType);
            case JavaLang.JavaVariadicType type -> new Symbol("?");
            case JavaLang.Qualified qualified -> JavaTypescriptParser.parseQualifiedType(qualified);
        };
    }

    private static TypescriptLang.TemplateType parseTemplateType(JavaLang.JavaTemplateType type) {
        var base = JavaTypescriptParser.parseBaseType(type.base());
        var collect = JavaTypescriptParser.parseTypeList(type.typeArguments().arguments());
        return new TypescriptLang.TemplateType(base, new TypeArguments<>(collect));
    }

    private static JavaLang.Symbol parseBaseType(JavaLang.Base base) {
        return switch (base) {
            case JavaLang.Qualified qualifiedType -> {
                var joined = qualifiedType.segments()
                        .iter()
                        .map(Segment::value)
                        .collect(new Joiner("."))
                        .orElse("");

                yield new JavaLang.Symbol(joined);
            }
            case JavaLang.Symbol symbol -> symbol;
        };
    }

    private static TypescriptLang.TypeScriptRootSegment parseNamespaced(Location location, JavaNamespacedNode namespaced) {
        return switch (namespaced.type()) {
            case Package -> new TypescriptLang.Whitespace();
            case Import -> JavaTypescriptParser.parseImport(location, namespaced.segments());
        };
    }

    private static TypescriptLang.TypeScriptImport parseImport(Location location, List<Segment> segments) {
        var segmentValues = segments.iter()
                .map(Segment::value)
                .collect(new ListCollector<>());

        var before = location.namespace()
                .iter()
                .map(_ -> "..")
                .map(Segment::new)
                .collect(new ListCollector<>());

        var last = new Segment(segmentValues.findLast().orElse(""));
        return new TypescriptLang.TypeScriptImport(Lists.of(last), before.addAllLast(segments));
    }

    @Override
    public CompileResult<UnitSet<TypescriptLang.TypescriptRoot>> apply(UnitSet<JavaLang.JavaRoot> initial) {
        return initial.iter()
                .map(JavaTypescriptParser::parseUnit)
                .collect(new CompileResultCollector<>(new UnitSetCollector<>()));
    }
}
