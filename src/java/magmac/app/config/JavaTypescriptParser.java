package magmac.app.config;

import magmac.api.Tuple2;
import magmac.api.collect.TupleCollector;
import magmac.api.collect.list.List;
import magmac.api.collect.list.Lists;
import magmac.api.iter.Iter;
import magmac.api.iter.collect.Joiner;
import magmac.api.iter.collect.ListCollector;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResultCollector;
import magmac.app.compile.error.CompileResults;
import magmac.app.io.Location;
import magmac.app.io.sources.UnitSetCollector;
import magmac.app.lang.java.JavaBreak;
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
import magmac.app.lang.web.Caller;
import magmac.app.lang.web.FunctionStatement;
import magmac.app.lang.web.Symbol;
import magmac.app.lang.web.TypescriptLang;
import magmac.app.stage.parse.Parser;
import magmac.app.stage.unit.SimpleUnit;
import magmac.app.stage.unit.Unit;
import magmac.app.stage.unit.UnitSet;

class JavaTypescriptParser implements Parser<JavaLang.Root, TypescriptLang.TypescriptRoot> {
    private record TypeMap(List<Tuple2<List<String>, String>> types) {
    }

    private static CompileResult<Unit<TypescriptLang.TypescriptRoot>> parseUnit(Unit<JavaLang.Root> unit, TypeMap typeMap) {
        return unit.destruct((location, root) -> JavaTypescriptParser.parseRoot(location, root, typeMap));
    }

    private static CompileResult<Unit<TypescriptLang.TypescriptRoot>> parseRoot(Location location, JavaLang.Root root, TypeMap typeMap) {
        var rootSegments = root.children()
                .iter()
                .map(rootSegment -> JavaTypescriptParser.parseRootSegment(location, rootSegment, typeMap))
                .flatMap(List::iter)
                .collect(new ListCollector<>());

        return CompileResults.Ok(new SimpleUnit<>(location, new TypescriptLang.TypescriptRoot(rootSegments)));
    }

    private static List<TypescriptLang.TypeScriptRootSegment> parseRootSegment(Location location, JavaRootSegment rootSegment, TypeMap typeMap) {
        return switch (rootSegment) {
            case JavaLang.Whitespace whitespace -> Lists.of(new TypescriptLang.Whitespace());
            case JavaNamespacedNode namespaced -> Lists.of(JavaTypescriptParser.parseNamespaced(location, namespaced));
            case JavaLang.Structure structure -> JavaTypescriptParser.getCollect(structure, typeMap);
        };
    }

    private static List<TypescriptLang.TypeScriptRootSegment> getCollect(JavaLang.Structure structure, TypeMap typeMap) {
        return JavaTypescriptParser.parseStructure(structure, typeMap)
                .iter()
                .map(JavaTypescriptParser::wrap)
                .collect(new ListCollector<>());
    }

    private static TypescriptLang.TypeScriptRootSegment wrap(TypescriptLang.StructureNode value) {
        return value;
    }

    private static List<TypescriptLang.StructureNode> parseStructure(JavaLang.Structure structure, TypeMap typeMap) {
        return switch (structure.type()) {
            case Class, Record ->
                    JavaTypescriptParser.parseStructureWithType(TypescriptLang.StructureType.Class, structure, typeMap);
            case Interface ->
                    JavaTypescriptParser.parseStructureWithType(TypescriptLang.StructureType.Interface, structure, typeMap);
            case Enum -> Lists.empty();
        };
    }

    private static List<TypescriptLang.StructureNode> parseStructureWithType(
            TypescriptLang.StructureType type,
            JavaLang.Structure structure,
            TypeMap typeMap
    ) {
        var value = structure.value;
        var membersTuple = value.members()
                .iter()
                .map(structureNode -> JavaTypescriptParser.parseStructureMember(structureNode, typeMap))
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
                value.maybeExtended().map(list -> JavaTypescriptParser.parseTypeList(list, typeMap)),
                value.maybeImplemented().map(list1 -> JavaTypescriptParser.parseTypeList(list1, typeMap))
        );

        return structures.addLast(new TypescriptLang.StructureNode(type, structureNode1));
    }

    private static List<TypescriptLang.Type> parseTypeList(List<JavaLang.JavaType> list, TypeMap typeMap) {
        return list.iter().map(variadicType -> JavaTypescriptParser.parseType(variadicType, typeMap)).collect(new ListCollector<>());
    }

    private static Tuple2<List<TypescriptLang.TypescriptStructureMember>, List<TypescriptLang.StructureNode>> parseStructureMember(JavaStructureMember structureNode, TypeMap typeMap) {
        return switch (structureNode) {
            case JavaLang.Whitespace whitespace -> JavaTypescriptParser.getList();
            case JavaEnumValues enumValues -> JavaTypescriptParser.getList();
            case JavaStructureStatement structureStatement -> JavaTypescriptParser.getList();
            case JavaMethod methodNode ->
                    JavaTypescriptParser.getListListTuple2(JavaTypescriptParser.parseMethod(methodNode, typeMap));
            case JavaLang.Structure javaStructure ->
                    new Tuple2<>(Lists.empty(), JavaTypescriptParser.parseStructure(javaStructure, typeMap));
        };
    }

    private static Tuple2<List<TypescriptLang.TypescriptStructureMember>, List<TypescriptLang.StructureNode>> getListListTuple2(TypescriptLang.TypescriptStructureMember typescriptStructureMember) {
        return new Tuple2<>(Lists.of(typescriptStructureMember), Lists.empty());
    }

    private static Tuple2<List<TypescriptLang.TypescriptStructureMember>, List<TypescriptLang.StructureNode>> getList() {
        return JavaTypescriptParser.getListListTuple2(new TypescriptLang.Whitespace());
    }

    private static TypescriptLang.TypescriptStructureMember parseMethod(JavaMethod methodNode, TypeMap typeMap) {
        var parameters = methodNode.parameters()
                .iter()
                .map(parameter -> JavaTypescriptParser.parseParameter(parameter, typeMap))
                .collect(new ListCollector<>());

        var header = JavaTypescriptParser.parseMethodHeader(methodNode.header(), typeMap);
        var parameterizedHeader = new ParameterizedMethodHeader<TypescriptLang.TypeScriptParameter>(header, parameters);
        return new TypescriptLang.TypescriptMethod(parameterizedHeader, methodNode.maybeChildren().map(segments -> JavaTypescriptParser.parseFunctionSegments(segments, typeMap)));
    }

    private static List<TypescriptLang.FunctionSegment> parseFunctionSegments(List<JavaFunctionSegment> segments, TypeMap typeMap) {
        return segments.iter()
                .map(segment -> JavaTypescriptParser.parseFunctionSegment(segment, typeMap))
                .collect(new ListCollector<>());
    }

    private static TypescriptLang.FunctionSegment parseFunctionSegment(JavaFunctionSegment segment, TypeMap typeMap) {
        return switch (segment) {
            case JavaLang.Whitespace whitespace -> new TypescriptLang.Whitespace();
            case JavaLang.Block block -> JavaTypescriptParser.parseBlock(block, typeMap);
            case JavaLang.Case caseNode -> new TypescriptLang.Whitespace();
            case JavaLang.Return aReturn ->
                    new TypescriptLang.Return(JavaTypescriptParser.parseValue(aReturn.child(), typeMap));
            case JavaLang.FunctionStatement functionStatement ->
                    JavaTypescriptParser.parseFunctionStatement(functionStatement, typeMap);
        };
    }

    private static TypescriptLang.FunctionSegment parseFunctionStatement(JavaLang.FunctionStatement functionStatement, TypeMap typeMap) {
        var oldValue = JavaTypescriptParser.parseFunctionStatementValue(functionStatement.child(), typeMap);
        var newValue = JavaTypescriptParser.getOldValue(oldValue);
        return new FunctionStatement(newValue);
    }

    private static TypescriptLang.FunctionSegment.Value getOldValue(TypescriptLang.FunctionSegment.Value oldValue) {
        if (oldValue instanceof TypescriptLang.Assignment(
                var assignable, var value
        )) {
            if (assignable instanceof TypescriptLang.Definition oldDefinition) {
                var newDefinition = oldDefinition.withModifier(new Modifier("let"));
                return new TypescriptLang.Assignment(newDefinition, value);
            }
        }

        return oldValue;
    }

    private static TypescriptLang.FunctionSegment.Value parseFunctionStatementValue(JavaFunctionSegmentValue child, TypeMap typeMap) {
        return switch (child) {
            case JavaBreak javaBreak -> new TypescriptLang.Break();
            case JavaContinue javaContinue -> new TypescriptLang.Continue();
            case JavaYieldNode javaYieldNode -> new TypescriptLang.Break();
            case JavaPost javaPost ->
                    new TypescriptLang.Post(javaPost.variant(), JavaTypescriptParser.parseValue(javaPost.value(), typeMap));
            case JavaLang.Return aReturn ->
                    new TypescriptLang.Return(JavaTypescriptParser.parseValue(aReturn.child(), typeMap));
            case JavaLang.Invokable invokable -> JavaTypescriptParser.parseInvokable(invokable, typeMap);
            case JavaLang.Assignment assignment ->
                    new TypescriptLang.Assignment(JavaTypescriptParser.parseAssignable(assignment.assignable(), typeMap), JavaTypescriptParser.parseValue(assignment.value(), typeMap));
        };
    }

    private static TypescriptLang.Assignable parseAssignable(JavaLang.Assignable assignable, TypeMap typeMap) {
        return switch (assignable) {
            case JavaLang.Definition definition -> JavaTypescriptParser.parseDefinition(definition, typeMap);
            case JavaLang.Value value -> JavaTypescriptParser.parseValue(value, typeMap);
        };
    }

    private static TypescriptLang.Invokable parseInvokable(JavaLang.Invokable invokable, TypeMap typeMap) {
        return new TypescriptLang.Invokable(JavaTypescriptParser.parseCaller(invokable.caller(), typeMap), JavaTypescriptParser.parseArguments(invokable.arguments(), typeMap));
    }

    private static Caller parseCaller(JavaLang.JavaCaller caller, TypeMap typeMap) {
        return switch (caller) {
            case JavaLang.Value javaValue -> JavaTypescriptParser.parseValue(javaValue, typeMap);
            case JavaLang.Construction javaConstruction ->
                    new TypescriptLang.Construction(JavaTypescriptParser.parseType(javaConstruction.type(), typeMap));
        };
    }

    private static List<TypescriptLang.Argument> parseArguments(List<JavaLang.JavaArgument> arguments, TypeMap typeMap) {
        return arguments.iter()
                .map(argument -> JavaTypescriptParser.parseArgument(argument, typeMap))
                .collect(new ListCollector<>());
    }

    private static TypescriptLang.Argument parseArgument(JavaLang.JavaArgument argument, TypeMap typeMap) {
        return switch (argument) {
            case JavaLang.Whitespace whitespace -> new TypescriptLang.Whitespace();
            case JavaLang.Value value -> JavaTypescriptParser.parseValue(value, typeMap);
        };
    }

    private static TypescriptLang.Value parseValue(JavaLang.Value child, TypeMap typeMap) {
        return switch (child) {
            case JavaLang.Access access -> JavaTypescriptParser.parseAccess(access, typeMap);
            case JavaLang.Char aChar -> new TypescriptLang.Char(aChar.value());
            case JavaLang.Index index -> JavaTypescriptParser.parseIndex(index, typeMap);
            case JavaLang.Invokable invokable -> JavaTypescriptParser.parseInvokable(invokable, typeMap);
            case JavaLang.Not not -> new TypescriptLang.Not(JavaTypescriptParser.parseValue(not.value(), typeMap));
            case JavaLang.Number number -> new TypescriptLang.Number(number.value());
            case JavaLang.operation operation -> JavaTypescriptParser.parseOperation(operation, typeMap);
            case JavaLang.StringValue javaStringNode -> new TypescriptLang.StringValue(javaStringNode.value());
            case JavaLang.Symbol symbol -> new TypescriptLang.Symbol(symbol.value());
            case JavaLang.Lambda javaLambda -> new TypescriptLang.Number("0");
            case JavaLang.SwitchNode javaSwitchNode -> new TypescriptLang.Number("0");
            case JavaLang.InstanceOf instanceOf -> new TypescriptLang.Number("0");
        };
    }

    private static TypescriptLang.Access parseAccess(JavaLang.Access access, TypeMap typeMap) {
        return new TypescriptLang.Access(JavaTypescriptParser.parseValue(access.receiver(), typeMap), access.property());
    }

    private static TypescriptLang.Index parseIndex(JavaLang.Index index, TypeMap typeMap) {
        return new TypescriptLang.Index(JavaTypescriptParser.parseValue(index.parent(), typeMap), JavaTypescriptParser.parseValue(index.argument(), typeMap));
    }

    private static TypescriptLang.Operation parseOperation(JavaLang.operation operation, TypeMap typeMap) {
        return new TypescriptLang.Operation(JavaTypescriptParser.parseValue(operation.left(), typeMap), operation.operator(), JavaTypescriptParser.parseValue(operation.right(), typeMap));
    }

    private static TypescriptLang.Block parseBlock(JavaLang.Block block, TypeMap typeMap) {
        return new TypescriptLang.Block(JavaTypescriptParser.parseHeader(block.header()), JavaTypescriptParser.parseFunctionSegments(block.segments(), typeMap));
    }

    private static TypescriptLang.TypescriptBlockHeader parseHeader(JavaLang.BlockHeader header) {
        return new TypescriptLang.TypescriptConditional(ConditionalType.If, new Symbol("true"));
    }

    private static TypescriptLang.TypeScriptParameter parseParameter(JavaParameter parameter, TypeMap typeMap) {
        return switch (parameter) {
            case JavaLang.Whitespace whitespace -> new TypescriptLang.Whitespace();
            case JavaLang.Definition javaDefinition -> JavaTypescriptParser.parseDefinition(javaDefinition, typeMap);
        };
    }

    private static TypescriptLang.TypeScriptMethodHeader parseMethodHeader(JavaMethodHeader header, TypeMap typeMap) {
        return switch (header) {
            case JavaConstructor constructor -> new TypescriptLang.TypescriptConstructor();
            case JavaLang.Definition javaDefinition -> JavaTypescriptParser.parseDefinition(javaDefinition, typeMap);
        };
    }

    private static TypescriptLang.Definition parseDefinition(JavaLang.Definition definition, TypeMap typeMap) {
        var maybeAnnotations = definition.maybeAnnotations();
        var maybeModifiers = definition.modifiers();
        var maybeTypeParameters = definition.maybeTypeParams();
        var type = definition.type();
        var oldName = definition.name();

        if (type instanceof JavaLang.JavaVariadicType(var child)) {
            var newType = new TypescriptLang.ArrayType(JavaTypescriptParser.parseType(child, typeMap));
            var name = "..." + oldName;
            return new TypescriptLang.Definition(maybeAnnotations, maybeModifiers, name, maybeTypeParameters, newType);
        }
        else {
            return new TypescriptLang.Definition(maybeAnnotations, maybeModifiers, oldName, maybeTypeParameters, JavaTypescriptParser.parseType(definition.type(), typeMap));
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

    private static TypescriptLang.Type parseArrayType(JavaLang.JavaArrayType type, TypeMap typeMap) {
        return new TypescriptLang.ArrayType(JavaTypescriptParser.parseType(type.inner, typeMap));
    }

    private static TypescriptLang.Type parseType(JavaLang.JavaType variadicType, TypeMap typeMap) {
        return switch (variadicType) {
            case JavaLang.Symbol symbol -> JavaTypescriptParser.parseSymbol(symbol);
            case JavaLang.JavaArrayType type -> JavaTypescriptParser.parseArrayType(type, typeMap);
            case JavaLang.JavaTemplateType templateType ->
                    JavaTypescriptParser.parseTemplateType(templateType, typeMap);
            case JavaLang.JavaVariadicType type -> new Symbol("?");
            case JavaLang.Qualified qualified -> JavaTypescriptParser.parseQualifiedType(qualified);
        };
    }

    private static TypescriptLang.TemplateType parseTemplateType(JavaLang.JavaTemplateType type, TypeMap typeMap) {
        var base = JavaTypescriptParser.parseBaseType(type.base());
        var listOption = type.typeArguments().map(list -> JavaTypescriptParser.parseTypeList(list, typeMap));
        return new TypescriptLang.TemplateType(base, listOption);
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
        return new TypescriptLang.Whitespace();
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

    private static Tuple2<List<String>, String> attachNamespace(Location location, Tuple2<List<String>, String> tuple) {
        var namespace = location.namespace().addAllLast(tuple.left());
        return new Tuple2<>(namespace, tuple.right());
    }

    private static List<Tuple2<List<String>, String>> findStructuresInRootSegment(JavaRootSegment child) {
        if (child instanceof JavaLang.Structure structure) {
            return JavaTypescriptParser.findStructuresInNode(Lists.empty(), structure);
        }

        return Lists.empty();
    }

    private static Iter<Tuple2<List<String>, String>> findStructuresWithLocation(Location location, JavaLang.Root root) {
        return root.children()
                .iter()
                .map(JavaTypescriptParser::findStructuresInRootSegment)
                .flatMap(List::iter)
                .map(tuple -> JavaTypescriptParser.attachNamespace(location, tuple));
    }

    private static List<Tuple2<List<String>, String>> findStructuresInMember(List<String> namespace, JavaStructureMember member) {
        if (member instanceof JavaLang.Structure structure) {
            return JavaTypescriptParser.findStructuresInNode(namespace, structure);
        }

        return Lists.empty();
    }

    private static List<Tuple2<List<String>, String>> findStructuresInNode(List<String> namespace, JavaLang.Structure structure) {
        var name = structure.name();
        var collect = structure.value.members()
                .iter()
                .map(child -> JavaTypescriptParser.findStructuresInMember(namespace.addLast(name), child))
                .flatMap(List::iter)
                .collect(new ListCollector<>());

        var entry = new Tuple2<List<String>, String>(namespace, name);
        return collect.addLast(entry);
    }

    @Override
    public CompileResult<UnitSet<TypescriptLang.TypescriptRoot>> apply(UnitSet<JavaLang.Root> set) {
        var types = set.iter()
                .flatMap(roots -> roots.destruct(JavaTypescriptParser::findStructuresWithLocation))
                .collect(new ListCollector<>());

        return set.iter()
                .map(unit -> JavaTypescriptParser.parseUnit(unit, new TypeMap(types)))
                .collect(new CompileResultCollector<>(new UnitSetCollector<>()));
    }
}
