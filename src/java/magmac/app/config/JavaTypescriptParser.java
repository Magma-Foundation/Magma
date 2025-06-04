package magmac.app.config;

import magmac.api.None;
import magmac.api.Option;
import magmac.api.Some;
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

    private static CompileResult<Unit<TypescriptLang.TypescriptRoot>> parseUnit(Unit<JavaLang.Root> unit, List<Tuple2<List<String>, String>> types) {
        return unit.destruct((location, root) -> {
            var map = new CompileState(types, location);
            return JavaTypescriptParser.parseRoot(location, root, map);
        });
    }

    private static CompileResult<Unit<TypescriptLang.TypescriptRoot>> parseRoot(Location location, JavaLang.Root root, CompileState typeMap) {
        var segmentsResult = root.children()
                .iter()
                .fold(
                        CompileResults.Ok(Lists.<TypescriptLang.TypeScriptRootSegment>empty()),
                        (current, child) -> current.flatMapValue(list ->
                                JavaTypescriptParser.parseRootSegment(location, child, typeMap)
                                        .mapValue(list::addAllLast))
                );

        return segmentsResult.mapValue(segments -> {
            var imports = buildImports(location, typeMap);
            var finalSegments = imports.addAllLast(segments);
            return new SimpleUnit<>(location, new TypescriptLang.TypescriptRoot(finalSegments));
        });
    }

    private static List<TypescriptLang.TypeScriptRootSegment> buildImports(Location location, CompileState typeMap) {
        return typeMap.importList()
                .iter()
                .map(loc -> loc.namespace().addLast(loc.name())
                        .iter()
                        .map(Segment::new)
                        .collect(new ListCollector<>()))
                .map(segs -> (TypescriptLang.TypeScriptRootSegment) JavaTypescriptParser.parseImport(location, segs))
                .collect(new ListCollector<>());
    }

    private static CompileResult<List<TypescriptLang.TypeScriptRootSegment>> parseRootSegment(Location location, JavaRootSegment rootSegment, CompileState typeMap) {
        return switch (rootSegment) {
            case JavaLang.Whitespace whitespace -> CompileResults.Ok(Lists.of(new TypescriptLang.Whitespace()));
            case JavaLang.Comment comment -> CompileResults.Ok(Lists.of(new TypescriptLang.Comment(comment.value())));
            case JavaNamespacedNode namespaced ->
                    JavaTypescriptParser.parseNamespaced(location, namespaced, typeMap).mapValue(Lists::of);
            case JavaLang.Structure structure -> JavaTypescriptParser.getCollect(structure, typeMap);
        };
    }

    private static CompileResult<List<TypescriptLang.TypeScriptRootSegment>> getCollect(JavaLang.Structure structure, CompileState typeMap) {
        return JavaTypescriptParser.parseStructure(structure, typeMap)
                .mapValue(list -> list.iter()
                        .map(JavaTypescriptParser::wrap)
                        .collect(new ListCollector<>()));
    }

    private static TypescriptLang.TypeScriptRootSegment wrap(TypescriptLang.StructureNode value) {
        return value;
    }

    private static CompileResult<List<TypescriptLang.StructureNode>> parseStructure(JavaLang.Structure structure, CompileState typeMap) {
        return switch (structure.type()) {
            case Class, Record ->
                    JavaTypescriptParser.parseStructureWithType(TypescriptLang.StructureType.Class, structure, typeMap);
            case Interface ->
                    JavaTypescriptParser.parseStructureWithType(TypescriptLang.StructureType.Interface, structure, typeMap);
            case Enum -> CompileResults.Ok(Lists.empty());
        };
    }

    private static CompileResult<List<TypescriptLang.StructureNode>> parseStructureWithType(
            TypescriptLang.StructureType type,
            JavaLang.Structure structure,
            CompileState typeMap
    ) {
        var value = structure.value;
        var membersTuple = value.members()
                .iter()
                .map(structureNode -> JavaTypescriptParser.parseStructureMember(structureNode, typeMap))
                .collect(new CompileResultCollector<>(new TupleCollector<>(new ListCollector<>(), new ListCollector<>())));
        return membersTuple.flatMapValue(tuple -> {
            var members = tuple.left()
                    .iter()
                    .flatMap(List::iter)
                    .collect(new ListCollector<>());

            var structures = tuple.right()
                    .iter()
                    .flatMap(List::iter)
                    .collect(new ListCollector<>());

            var extendedResult = value.maybeExtended()
                    .map(list -> JavaTypescriptParser.parseTypeList(list, typeMap)
                            .mapValue(types -> (Option<List<TypescriptLang.Type>>) new Some<>(types)))
                    .orElseGet(() -> CompileResults.Ok(new None<>()));

            var implementedResult = value.maybeImplemented()
                    .map(list1 -> JavaTypescriptParser.parseTypeList(list1, typeMap)
                            .mapValue(types -> (Option<List<TypescriptLang.Type>>) new Some<>(types)))
                    .orElseGet(() -> CompileResults.Ok(new None<>()));

            return extendedResult.and(() -> implementedResult).mapValue(extImpl -> {
                var structureNode1 = new StructureValue<TypescriptLang.Type, TypescriptLang.TypescriptStructureMember>(
                        value.name(),
                        value.modifiers(),
                        members,
                        value.maybeTypeParams(),
                        extImpl.left(),
                        extImpl.right()
                );
                return structures.addLast(new TypescriptLang.StructureNode(type, structureNode1));
            });
        });
    }

    private static CompileResult<List<TypescriptLang.Type>> parseTypeList(List<JavaLang.JavaType> list, CompileState typeMap) {
        return list.iter()
                .map(variadicType -> JavaTypescriptParser.parseType(variadicType, typeMap))
                .collect(new CompileResultCollector<>(new ListCollector<>()));
    }

    private static CompileResult<Tuple2<List<TypescriptLang.TypescriptStructureMember>, List<TypescriptLang.StructureNode>>> parseStructureMember(JavaStructureMember structureNode, CompileState typeMap) {
        return switch (structureNode) {
            case JavaLang.Whitespace whitespace -> CompileResults.Ok(JavaTypescriptParser.getList());
            case JavaLang.Comment comment ->
                    CompileResults.Ok(JavaTypescriptParser.getListListTuple2(new TypescriptLang.Comment(comment.value())));
            case JavaEnumValues enumValues -> CompileResults.Ok(JavaTypescriptParser.getList());
            case JavaStructureStatement structureStatement -> CompileResults.Ok(JavaTypescriptParser.getList());
            case JavaMethod methodNode ->
                    JavaTypescriptParser.parseMethod(methodNode, typeMap).mapValue(JavaTypescriptParser::getListListTuple2);
            case JavaLang.Structure javaStructure ->
                    JavaTypescriptParser.parseStructure(javaStructure, typeMap).mapValue(structs -> new Tuple2<>(Lists.empty(), structs));
        };
    }

    private static Tuple2<List<TypescriptLang.TypescriptStructureMember>, List<TypescriptLang.StructureNode>> getListListTuple2(TypescriptLang.TypescriptStructureMember typescriptStructureMember) {
        return new Tuple2<>(Lists.of(typescriptStructureMember), Lists.empty());
    }

    private static Tuple2<List<TypescriptLang.TypescriptStructureMember>, List<TypescriptLang.StructureNode>> getList() {
        return JavaTypescriptParser.getListListTuple2(new TypescriptLang.Whitespace());
    }

    static CompileResult<TypescriptLang.TypescriptStructureMember> parseMethod(JavaMethod methodNode, CompileState typeMap) {
        var parameters = methodNode.parameters()
                .iter()
                .map(parameter -> JavaTypescriptParser.parseParameter(parameter, typeMap))
                .collect(new CompileResultCollector<>(new ListCollector<>()));

        var header = JavaTypescriptParser.parseMethodHeader(methodNode.header(), typeMap);

        var headerAndParams = header.and(() -> parameters)
                .mapValue(tuple -> new ParameterizedMethodHeader<TypescriptLang.TypeScriptParameter>(tuple.left(), tuple.right()));

        var maybeOldChildren = methodNode.maybeChildren();
        if (methodNode.header() instanceof JavaLang.Definition def) {
            boolean hasActual = def.hasActual();
            var isStatic = def.isStatic();
            if (hasActual && isStatic) {
                maybeOldChildren = new None<>();
            }
        }

        var maybeNewChildren = maybeOldChildren.map(segments -> JavaTypescriptParser.parseFunctionSegments(segments, typeMap));
        return headerAndParams.mapValue(parameterizedHeader -> new TypescriptLang.TypescriptMethod(
                parameterizedHeader, maybeNewChildren
        ));
    }

    private static List<TypescriptLang.FunctionSegment> parseFunctionSegments(List<JavaFunctionSegment> segments, CompileState typeMap) {
        return segments.iter()
                .map(segment -> JavaTypescriptParser.parseFunctionSegment(segment, typeMap))
                .collect(new ListCollector<>());
    }

    private static TypescriptLang.FunctionSegment parseFunctionSegment(JavaFunctionSegment segment, CompileState typeMap) {
        return switch (segment) {
            case JavaLang.Whitespace whitespace -> new TypescriptLang.Whitespace();
            case JavaLang.Comment comment -> new TypescriptLang.Comment(comment.value());
            case JavaLang.Block block -> JavaTypescriptParser.parseBlock(block, typeMap);
            case JavaLang.Case caseNode -> new TypescriptLang.Whitespace();
            case JavaLang.Return aReturn ->
                    new TypescriptLang.Return(JavaTypescriptParser.parseValue(aReturn.child(), typeMap));
            case JavaLang.FunctionStatement functionStatement ->
                    JavaTypescriptParser.parseFunctionStatement(functionStatement, typeMap);
        };
    }

    private static TypescriptLang.FunctionSegment parseFunctionStatement(JavaLang.FunctionStatement functionStatement, CompileState typeMap) {
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

    private static TypescriptLang.FunctionSegment.Value parseFunctionStatementValue(JavaFunctionSegmentValue child, CompileState typeMap) {
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

    private static TypescriptLang.Assignable parseAssignable(JavaLang.Assignable assignable, CompileState typeMap) {
        return switch (assignable) {
            case JavaLang.Definition definition -> JavaTypescriptParser.parseDefinition(definition, typeMap).match(
                    d -> d,
                    err -> new TypescriptLang.Definition(definition.maybeAnnotations(), definition.modifiers(), definition.name(), definition.maybeTypeParams(), new Symbol("?"))
            );
            case JavaLang.Value value -> JavaTypescriptParser.parseValue(value, typeMap);
        };
    }

    private static TypescriptLang.Invokable parseInvokable(JavaLang.Invokable invokable, CompileState typeMap) {
        return new TypescriptLang.Invokable(JavaTypescriptParser.parseCaller(invokable.caller(), typeMap), JavaTypescriptParser.parseArguments(invokable.arguments(), typeMap));
    }

    private static Caller parseCaller(JavaLang.JavaCaller caller, CompileState typeMap) {
        return switch (caller) {
            case JavaLang.Value javaValue -> JavaTypescriptParser.parseValue(javaValue, typeMap);
            case JavaLang.Construction javaConstruction -> new TypescriptLang.Construction(
                    JavaTypescriptParser.parseType(javaConstruction.type(), typeMap)
                            .match(t -> t, err -> new Symbol("?"))
            );
        };
    }

    private static List<TypescriptLang.Argument> parseArguments(List<JavaLang.JavaArgument> arguments, CompileState typeMap) {
        return arguments.iter()
                .map(argument -> JavaTypescriptParser.parseArgument(argument, typeMap))
                .collect(new ListCollector<>());
    }

    private static TypescriptLang.Argument parseArgument(JavaLang.JavaArgument argument, CompileState typeMap) {
        return switch (argument) {
            case JavaLang.Whitespace whitespace -> new TypescriptLang.Whitespace();
            case JavaLang.Comment comment -> new TypescriptLang.Comment(comment.value());
            case JavaLang.Value value -> JavaTypescriptParser.parseValue(value, typeMap);
        };
    }

    private static TypescriptLang.Value parseValue(JavaLang.Value child, CompileState typeMap) {
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
            case JavaLang.InstanceOf instanceOf -> JavaTypescriptParser.parseInstanceOf(instanceOf, typeMap);
        };
    }

    private static TypescriptLang.Access parseAccess(JavaLang.Access access, CompileState typeMap) {
        return new TypescriptLang.Access(JavaTypescriptParser.parseValue(access.receiver(), typeMap), access.property());
    }

    private static TypescriptLang.Index parseIndex(JavaLang.Index index, CompileState typeMap) {
        return new TypescriptLang.Index(JavaTypescriptParser.parseValue(index.parent(), typeMap), JavaTypescriptParser.parseValue(index.argument(), typeMap));
    }

    private static TypescriptLang.Operation parseOperation(JavaLang.operation operation, CompileState typeMap) {
        return new TypescriptLang.Operation(JavaTypescriptParser.parseValue(operation.left(), typeMap), operation.operator(), JavaTypescriptParser.parseValue(operation.right(), typeMap));
    }

    private static TypescriptLang.InstanceOf parseInstanceOf(JavaLang.InstanceOf instanceOf, CompileState typeMap) {
        var value = JavaTypescriptParser.parseValue(instanceOf.value(), typeMap);

        JavaLang.Base base = switch (instanceOf.definition()) {
            case JavaLang.InstanceOfDefinitionWithParameters(var b, var params) -> b;
            case JavaLang.InstanceOfDefinitionWithName(var b, var name) -> b;
            default -> new JavaLang.Symbol("?");
        };

        var type = JavaTypescriptParser.parseSymbol(JavaTypescriptParser.parseBaseType(base), typeMap)
                .match(t -> (TypescriptLang.Type) t, err -> new Symbol("?"));

        return new TypescriptLang.InstanceOf(value, type);
    }

    private static TypescriptLang.Block parseBlock(JavaLang.Block block, CompileState typeMap) {
        return new TypescriptLang.Block(JavaTypescriptParser.parseHeader(block.header()), JavaTypescriptParser.parseFunctionSegments(block.segments(), typeMap));
    }

    private static TypescriptLang.TypescriptBlockHeader parseHeader(JavaLang.BlockHeader header) {
        return new TypescriptLang.TypescriptConditional(ConditionalType.If, new Symbol("true"));
    }

    private static CompileResult<TypescriptLang.TypeScriptParameter> parseParameter(JavaParameter parameter, CompileState typeMap) {
        return switch (parameter) {
            case JavaLang.Whitespace whitespace -> CompileResults.Ok(new TypescriptLang.Whitespace());
            case JavaLang.Comment comment -> CompileResults.Ok(new TypescriptLang.Comment(comment.value()));
            case JavaLang.Definition javaDefinition ->
                    JavaTypescriptParser.parseDefinition(javaDefinition, typeMap).mapValue(p -> p);
        };
    }

    private static CompileResult<TypescriptLang.TypeScriptMethodHeader> parseMethodHeader(JavaMethodHeader header, CompileState typeMap) {
        return switch (header) {
            case JavaConstructor constructor -> CompileResults.Ok(new TypescriptLang.TypescriptConstructor());
            case JavaLang.Definition javaDefinition ->
                    JavaTypescriptParser.parseDefinition(javaDefinition, typeMap).mapValue(d -> d);
        };
    }

    private static CompileResult<TypescriptLang.Definition> parseDefinition(JavaLang.Definition definition, CompileState typeMap) {
        var maybeAnnotations = definition.maybeAnnotations();
        var maybeModifiers = definition.modifiers();
        var maybeTypeParameters = definition.maybeTypeParams();
        var type = definition.type();
        var oldName = definition.name();

        if (type instanceof JavaLang.JavaVariadicType(var child)) {
            return JavaTypescriptParser.parseType(child, typeMap)
                    .mapValue(inner -> {
                        var newType = new TypescriptLang.ArrayType(inner);
                        var name = "..." + oldName;
                        return new TypescriptLang.Definition(maybeAnnotations, maybeModifiers, name, maybeTypeParameters, newType);
                    });
        }
        else {
            return JavaTypescriptParser.parseType(definition.type(), typeMap)
                    .mapValue(parsed -> new TypescriptLang.Definition(maybeAnnotations, maybeModifiers, oldName, maybeTypeParameters, parsed));
        }
    }

    private static CompileResult<Symbol> parseSymbol(JavaLang.Symbol symbol, CompileState state) {
        return switch (symbol.value()) {
            case "byte", "short", "int", "long", "float", "double" -> CompileResults.Ok(new Symbol("number"));
            case "boolean" -> CompileResults.Ok(new Symbol("boolean"));
            case "char", "String" -> CompileResults.Ok(new Symbol("string"));
            case "void" -> CompileResults.Ok(new Symbol("void"));
            default -> state.find(symbol.value())
                    .map(loc -> {
                        state.registerImport(loc);
                        return CompileResults.Ok(new Symbol(symbol.value()));
                    })
                    .orElseGet(() -> CompileResults.fromErrWithString("Unknown type", symbol.value()));
        };
    }

    private static CompileResult<Symbol> parseQualifiedType(JavaLang.Qualified qualified) {
        var joined = qualified.segments()
                .iter()
                .map(Segment::value)
                .collect(new Joiner("."))
                .orElse("");

        return CompileResults.Ok(new Symbol(joined));
    }

    private static CompileResult<TypescriptLang.Type> parseArrayType(JavaLang.JavaArrayType type, CompileState typeMap) {
        return JavaTypescriptParser.parseType(type.inner, typeMap)
                .mapValue(TypescriptLang.ArrayType::new);
    }

    private static CompileResult<TypescriptLang.Type> parseType(JavaLang.JavaType variadicType, CompileState typeMap) {
        return switch (variadicType) {
            case JavaLang.Symbol symbol ->
                    JavaTypescriptParser.parseSymbol(symbol, typeMap).mapValue(s -> (TypescriptLang.Type) s);
            case JavaLang.JavaArrayType type -> JavaTypescriptParser.parseArrayType(type, typeMap);
            case JavaLang.JavaTemplateType templateType ->
                    JavaTypescriptParser.parseTemplateType(templateType, typeMap).mapValue(t -> (TypescriptLang.Type) t);
            case JavaLang.JavaVariadicType type -> CompileResults.Ok(new Symbol("?"));
            case JavaLang.Qualified qualified ->
                    JavaTypescriptParser.parseQualifiedType(qualified).mapValue(s -> (TypescriptLang.Type) s);
        };
    }

    private static CompileResult<TypescriptLang.TemplateType> parseTemplateType(JavaLang.JavaTemplateType type, CompileState typeMap) {
        var base = JavaTypescriptParser.parseBaseType(type.base());
        var listResult = type.typeArguments()
                .map(list -> JavaTypescriptParser.parseTypeList(list, typeMap)
                        .mapValue(types -> (Option<List<TypescriptLang.Type>>) new Some<>(types)))
                .orElseGet(() -> CompileResults.Ok(new None<>()));
        return listResult.mapValue(option -> new TypescriptLang.TemplateType(base, option));
    }

    private static JavaLang.Symbol parseBaseType(JavaLang.Base base) {
        return switch (base) {
            case JavaLang.Qualified qualifiedType -> JavaTypescriptParser.parseQualifiedTypeAsBase(qualifiedType);
            case JavaLang.Symbol symbol -> symbol;
        };
    }

    private static JavaLang.Symbol parseQualifiedTypeAsBase(JavaLang.Qualified qualifiedType) {
        var joined = qualifiedType.segments()
                .iter()
                .map(Segment::value)
                .collect(new Joiner("."))
                .orElse("");

        return new JavaLang.Symbol(joined);
    }

    private static CompileResult<TypescriptLang.TypeScriptRootSegment> parseNamespaced(Location location, JavaNamespacedNode namespaced, CompileState typeMap) {
        return switch (namespaced.type()) {
            case Package -> CompileResults.Ok(new TypescriptLang.Whitespace());
            case Import -> JavaTypescriptParser.parseImport(location, namespaced, typeMap);
        };
    }

    private static CompileResult<TypescriptLang.TypeScriptRootSegment> parseImport(Location location, JavaNamespacedNode namespaced, CompileState typeMap) {
        var segments = namespaced.segments();
        var lastValue = segments.findLast().map(Segment::value).orElse("");
        if ("*".equals(lastValue)) {
            return CompileResults.Ok(new TypescriptLang.Whitespace());
        }

        var joined = segments.iter()
                .map(Segment::value)
                .collect(new Joiner("."))
                .orElse("");

        var exists = typeMap.types().iter()
                .filter(tuple -> {
                    var path = tuple.left().addLast(tuple.right())
                            .iter()
                            .collect(new Joiner("."))
                            .orElse("");
                    return path.equals(joined);
                })
                .next()
                .isPresent();

        if (exists) {
            return CompileResults.Ok(JavaTypescriptParser.parseImport(location, segments));
        }
        else {
            return CompileResults.fromErrWithString("Unknown import", joined);
        }
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
                .map(unit -> JavaTypescriptParser.parseUnit(unit, types))
                .collect(new CompileResultCollector<>(new UnitSetCollector<>()));
    }
}
