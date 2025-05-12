"use strict";
/* private static final */ class None {
    map(mapper) {
        return new None();
    }
    isPresent() {
        return false;
    }
    orElse(other) {
        return other;
    }
    filter(predicate) {
        return new None();
    }
    orElseGet(supplier) {
        return supplier();
    }
    or(other) {
        return other();
    }
    flatMap(mapper) {
        return new None();
    }
    isEmpty() {
        return true;
    }
    and(other) {
        return new None();
    }
}
/* private */ class Tuple2Impl {
    constructor(left, right) {
    }
}
/* private */ class Some {
    constructor(value) {
    }
    map(mapper) {
        return new Some(mapper(this.value));
    }
    isPresent() {
        return true;
    }
    orElse(other) {
        return this.value;
    }
    filter(predicate) {
        if (predicate(this.value)) {
            return this;
        }
        return new None();
    }
    orElseGet(supplier) {
        return this.value;
    }
    or(other) {
        return this;
    }
    flatMap(mapper) {
        return mapper(this.value);
    }
    isEmpty() {
        return false;
    }
    and(other) {
        return other().map((otherValue) => new Tuple2Impl(this.value, otherValue));
    }
}
/* private static */ class SingleHead {
    constructor(value) {
        this.value = value;
        this.retrieved = false;
    }
    next() {
        if (this.retrieved) {
            return new None();
        }
        this.retrieved = true;
        return new Some(this.value);
    }
}
/* private static */ class EmptyHead {
    next() {
        return new None();
    }
}
/* private */ class HeadedIterator {
    constructor(head) {
    }
    fold(initial, folder) {
        let current = initial;
        while (true) {
            let finalCurrent = current;
            let option = this.head.next().map((inner) => folder(finalCurrent, inner));
            if (option._variant === OptionVariant.Some) {
                current = /* some */ .value;
            }
            else {
                return current;
            }
        }
    }
    map(mapper) {
        return new HeadedIterator(() => this.head.next().map(mapper));
    }
    collect(collector) {
        return this.fold(collector.createInitial(), collector.fold);
    }
    filter(predicate) {
        return this.flatMap((element) => {
            if (predicate(element)) {
                return new HeadedIterator(new SingleHead(element));
            }
            return new HeadedIterator(new EmptyHead());
        });
    }
    next() {
        return this.head.next();
    }
    flatMap(f) {
        return new HeadedIterator(new FlatMapHead(this.head, f));
    }
    zip(other) {
        return new HeadedIterator(() => HeadedIterator.this.head.next().and(other.next));
    }
}
/* private static */ class RangeHead /*  */ {
}
this.length = length;
next();
Option < number > {
    if() { } /* this.counter < this */, /* this.counter < this */ : /* this.counter < this */ .length
};
{
    let value = this.counter;
    /* this.counter++ */ ;
    return new Some(value);
}
return new None();
/* private static final */ class JVMList {
}
this.elements = elements;
JVMList();
{
    /* this(new ArrayList<>()) */ ;
}
addLast(element, T);
List < T > {
    return: this
};
iterate();
Iterator < T > {
    return: this.iterateWithIndices().map(Tuple2.right)
};
removeLast();
Option < [(List), T] > {
    : .elements.isEmpty()
};
{
    return new None();
}
let slice = this.elements.subList(0, this.elements.size() - 1);
let last = this.elements.getLast();
return new Some(new Tuple2Impl(new JVMList(slice), last));
size();
number;
{
    return this.elements.size();
}
isEmpty();
boolean;
{
    return this.elements.isEmpty();
}
addFirst(element, T);
List < T > {
    return: this
};
iterateWithIndices();
Iterator < [number, T] > {
    return: new HeadedIterator(new RangeHead(this.elements.size())).map((index) => new Tuple2Impl(index, this.elements.get(index)))
};
removeFirst();
Option < [T, (List)] > {
    : .elements.isEmpty()
};
{
    return new None();
}
let first = this.elements.getFirst();
let slice = this.elements.subList(1, this.elements.size());
return new Some(new Tuple2Impl(first, new JVMList(slice)));
addAllLast(others, (List));
List < T > {
    let, initial: (List) = this,
    return: others.iterate().fold(initial, List.addLast)
};
last();
Option < T > {
    : .elements.isEmpty()
};
{
    return new None();
}
return new Some(this.elements.getLast());
iterateReversed();
Iterator < T > {
    return: new HeadedIterator(new RangeHead(this.elements.size())).map((index) => this.elements.size() - index - 1).map(this.elements.get)
};
mapLast(mapper, (arg0) => T);
List < T > {
    return: this.last().map(mapper).map((newLast) => this.set(this.elements.size() - 1, newLast)).orElse(this)
};
set(index, number, element, T);
JVMList < T > {
    return: this
};
get(index, number);
Option < T > {
    if() { } /* index < this */, /* index < this */ : /* index < this */ .elements.size()
};
{
    return new Some(this.elements.get(index));
}
{
    return new None();
}
/* private static */ class Lists /*  */ {
    empty() {
        return new JVMList();
    }
    of(elements) {
        return new JVMList(new /* ArrayList */ ( /* Arrays */.asList(elements)));
    }
}
/* private */ class ImmutableDefinition /*  */ {
    constructor(maybeBefore, name, type, typeParams) {
    }
    createSimpleDefinition(name, type) {
        return new ImmutableDefinition(new None(), name, type, Lists.empty());
    }
    generate() {
        return this.generateWithParams("");
    }
    generateType() {
        if (this.type.equals(Primitive.Unknown)) {
            return "";
        }
        return " : " + this.type.generate();
    }
    joinBefore() {
        return !.maybeBefore.filter((value) => !value.isEmpty()).map(Main.generatePlaceholder).map((inner) => inner + " ").orElse("");
    }
    joinTypeParams() {
        return this.typeParams.iterate().collect(new Joiner()).map((inner) => "<" + inner + ">").orElse("");
    }
    mapType(mapper) {
        return new ImmutableDefinition(this.maybeBefore, this.name, mapper(this.type), this.typeParams);
    }
    toString() {
        return "Definition[" + "maybeBefore=" + this.maybeBefore + ", " + "name=" + this.name + ", " + "type=" + this.type + ", " + "typeParams=" + this.typeParams +  /*  ']' */;
    }
    generateWithParams(joinedParameters) {
        let joined = this.joinTypeParams();
        let before = this.joinBefore();
        let typeString = this.generateType();
        return before + this.name + joined + joinedParameters + typeString;
    }
    createDefinition(paramTypes) {
        return ImmutableDefinition.createSimpleDefinition(this.name, new FunctionType(paramTypes, this.type));
    }
}
/* private */ class ObjectType /*  */ {
    constructor(name, typeParams, definitions) {
    }
    generate() {
        return this.name;
    }
    replace(mapping) {
        return new ObjectType(this.name, this.typeParams, this.definitions.iterate().map((definition) => definition.mapType((type) => type.replace(mapping))).collect(new ListCollector()));
    }
    find(name) {
        return this.definitions.iterate().filter((definition) => definition.name().equals(name)).map(Definition.type).next();
    }
    findName() {
        return new Some(this.name);
    }
}
/* private */ class TypeParam /*  */ {
    constructor(value) {
    }
    generate() {
        return this.value;
    }
    replace(mapping) {
        return mapping.find(this.value).orElse(this);
    }
    findName() {
        return new None();
    }
}
/* private */ class CompileState /*  */ {
    constructor(structures, definitions, objectTypes, structNames, typeParams, typeRegister) {
    }
    resolveValue(name) {
        return this.definitions.iterateReversed().flatMap(List.iterate).filter((definition) => definition.name().equals(name)).next().map(Definition.type);
    }
    addStructure(structure) {
        return new CompileState(this.structures.addLast(structure), this.definitions, this.objectTypes, this.structNames, this.typeParams, this.typeRegister);
    }
    defineAll(definitions) {
        let defined = this.definitions.mapLast((frame) => frame.addAllLast(definitions));
        return new CompileState(this.structures, defined, this.objectTypes, this.structNames, this.typeParams, this.typeRegister);
    }
    resolveType(name) {
        if (this.structNames.last().filter((inner) => inner.equals(name)).isPresent()) {
            return new Some(new ObjectType(name, this.typeParams, this.definitions.last().orElse(Lists.empty())));
        }
        let maybeTypeParam = this.typeParams.iterate().filter((param) => param.equals(name)).next();
        if ( /* maybeTypeParam instanceof Some */( /* var value */)) {
            return new Some(new TypeParam( /* value */));
        }
        return this.objectTypes.iterate().filter((type) => type.name.equals(name)).next().map((type) => type);
    }
    define(definition) {
        return new CompileState(this.structures, this.definitions.mapLast((frame) => frame.addLast(definition)), this.objectTypes, this.structNames, this.typeParams, this.typeRegister);
    }
    pushStructName(name) {
        return new CompileState(this.structures, this.definitions, this.objectTypes, this.structNames.addLast(name), this.typeParams, this.typeRegister);
    }
    withTypeParams(typeParams) {
        return new CompileState(this.structures, this.definitions, this.objectTypes, this.structNames, this.typeParams.addAllLast(typeParams), this.typeRegister);
    }
    withExpectedType(type) {
        return new CompileState(this.structures, this.definitions, this.objectTypes, this.structNames, this.typeParams, new Some(type));
    }
    popStructName() {
        return new CompileState(this.structures, this.definitions, this.objectTypes, this.structNames.removeLast().map(Tuple2.left).orElse(this.structNames), this.typeParams, this.typeRegister);
    }
    enterDefinitions() {
        return new CompileState(this.structures, this.definitions.addLast(Lists.empty()), this.objectTypes, this.structNames, this.typeParams, this.typeRegister);
    }
    exitDefinitions() {
        let removed = this.definitions.removeLast().map(Tuple2.left).orElse(this.definitions);
        return new CompileState(this.structures, removed, this.objectTypes, this.structNames, this.typeParams, this.typeRegister);
    }
    addType(thisType) {
        return new CompileState(this.structures, this.definitions, this.objectTypes.addLast(thisType), this.structNames, this.typeParams, this.typeRegister);
    }
}
/* private static */ class DivideState /*  */ {
}
this.segments = segments;
this.buffer = buffer;
this.depth = depth;
this.input = input;
this.index = index;
DivideState(input, string);
{
    /* this(input, 0, Lists.empty(), "", 0) */ ;
}
advance();
DivideState;
{
    this.segments = this.segments.addLast(this.buffer);
    this.buffer = "";
    return this;
}
append(c, string);
DivideState;
{
    this.buffer = this.buffer + c;
    return this;
}
enter();
DivideState;
{
    /* this.depth++ */ ;
    return this;
}
isLevel();
boolean;
{
    return this.depth === 0;
}
exit();
DivideState;
{
    /* this.depth-- */ ;
    return this;
}
isShallow();
boolean;
{
    return this.depth === 1;
}
pop();
Option < [string, DivideState] > {
    if() { } /* this.index < this */, /* this.index < this */ : /* this.index < this */ .input.length()
};
{
    let c = this.input.charAt(this.index);
    return new Some(new Tuple2Impl(c, new DivideState(this.input, this.index + 1, this.segments, this.buffer, this.depth)));
}
return new None();
popAndAppendToTuple();
Option < [string, DivideState] > {
    return: this.pop().map((tuple) => {
        let c = tuple[0]();
        let right = tuple[1]();
        return new Tuple2Impl(c, right.append(c));
    })
};
popAndAppendToOption();
Option < DivideState > {
    return: this.popAndAppendToTuple().map(Tuple2.right)
};
peek();
string;
{
    return this.input.charAt(this.index);
}
/* private */ class Joiner /*  */ {
    constructor(delimiter) {
    }
    createInitial() {
        return new None();
    }
    fold(current, element) {
        return new Some(current.map((inner) => inner + this.delimiter + element).orElse(element));
    }
}
/* private static */ class ListCollector {
    createInitial() {
        return Lists.empty();
    }
    fold(current, element) {
        return current.addLast(element);
    }
}
/* private static */ class FlatMapHead {
}
this.mapper = mapper;
this.current = new None();
this.head = head;
next();
Option < R > {
    while() {
        if (this.current.isPresent()) {
            let inner = this.current.orElse( /* null */);
            let maybe = inner.next();
            if (maybe.isPresent()) {
                return maybe;
            }
            else {
                this.current = new None();
            }
        }
        let outer = this.head.next();
        if (outer.isPresent()) {
            this.current = outer.map(this.mapper);
        }
        else {
            return new None();
        }
    }
};
/* private */ class ArrayType /*  */ {
    constructor(right) {
    }
    generate() {
        return this.right().generate() + "[]";
    }
    replace(mapping) {
        return this;
    }
    findName() {
        return new None();
    }
}
/* private static final */ class Whitespace /*  */ {
    generate() {
        return "";
    }
    maybeCreateDefinition() {
        return new None();
    }
    maybeCreateObjectType() {
        return new None();
    }
}
/* private static */ class Iterators /*  */ {
    fromOption(option) {
        let single = option.map(SingleHead.new);
        return new HeadedIterator(single.orElseGet(EmptyHead.new));
    }
}
/* private */ class FunctionType /*  */ {
    constructor(arguments, returns) {
    }
    generate() {
        let joined = this.arguments().iterateWithIndices().map((pair) => "arg" + pair.left() + " : " + pair.right().generate()).collect(new Joiner(", ")).orElse("");
        return "(" + joined + ") => " + this.returns.generate();
    }
    replace(mapping) {
        return new FunctionType(this.arguments.iterate().map((type) => type.replace(mapping)).collect(new ListCollector()), this.returns);
    }
    findName() {
        return new None();
    }
}
/* private */ class TupleType /*  */ {
    constructor(arguments) {
    }
    generate() {
        let joinedArguments = this.arguments.iterate().map(Type.generate).collect(new Joiner(", ")).orElse("");
        return "[" + joinedArguments + "]";
    }
    replace(mapping) {
        return this;
    }
    findName() {
        return new None();
    }
}
/* private */ class Template /*  */ {
    constructor(base, arguments) {
    }
    generate() {
        let joinedArguments = this.arguments.iterate().map(Type.generate).collect(new Joiner(", ")).map((inner) => "<" + inner + ">").orElse("");
        return this.base.generate() + joinedArguments;
    }
    typeParams() {
        return this.base.typeParams();
    }
    find(name) {
        return this.base.find(name).map((found) => {
            let mapping = this.base.typeParams().iterate().zip(this.arguments.iterate()).collect(new MapCollector());
            return found.replace(mapping);
        });
    }
    name() {
        return this.base.name();
    }
    replace(mapping) {
        return this;
    }
    findName() {
        return this.base.findName();
    }
}
/* private */ class Placeholder /*  */ {
    constructor(input) {
    }
    generate() {
        return generatePlaceholder(this.input);
    }
    type() {
        return Primitive.Unknown;
    }
    typeParams() {
        return Lists.empty();
    }
    find(name) {
        return new None();
    }
    name() {
        return this.input;
    }
    replace(mapping) {
        return this;
    }
    findName() {
        return new None();
    }
    maybeCreateDefinition() {
        return new None();
    }
    maybeCreateObjectType() {
        return new None();
    }
}
/* private */ class StringValue /*  */ {
    constructor(stripped) {
    }
    generate() {
        return this.stripped;
    }
    type() {
        return Primitive.Unknown;
    }
}
/* private */ class DataAccess /*  */ {
    constructor(parent, property, type) {
    }
    generate() {
        return this.parent.generate() + "." + this.property + createDebugString(this.type);
    }
    type() {
        return this.type;
    }
}
/* private */ class ConstructionCaller /*  */ {
    constructor(type) {
    }
    generate() {
        return "new " + this.type.generate();
    }
    toFunction() {
        return new FunctionType(Lists.empty(), this.type);
    }
}
/* private */ class Operation /*  */ {
    constructor(left, operator /* Operator */, right) {
    }
    generate() {
        return this.left().generate() + " " + this.operator.targetRepresentation + " " + this.right().generate();
    }
    type() {
        return Primitive.Unknown;
    }
}
/* private */ class Not /*  */ {
    constructor(value) {
    }
    generate() {
        return "!" + this.value.generate();
    }
    type() {
        return Primitive.Unknown;
    }
}
/* private */ class BlockLambdaValue /*  */ {
    constructor(depth, statements) {
    }
    generate() {
        return "{" + this.joinStatements() + createIndent(this.depth) + "}";
    }
    joinStatements() {
        return this.statements.iterate().map(FunctionSegment.generate).collect(new Joiner()).orElse("");
    }
}
/* private */ class Lambda /*  */ {
    constructor(parameters, body) {
    }
    generate() {
        let joined = this.parameters.iterate().map(Definition.generate).collect(new Joiner(", ")).orElse("");
        return "(" + joined + ") => " + this.body.generate();
    }
    type() {
        return Primitive.Unknown;
    }
}
/* private */ class Invokable /*  */ {
    constructor(caller, arguments, type) {
    }
    generate() {
        let joined = this.arguments.iterate().map(Value.generate).collect(new Joiner(", ")).orElse("");
        return this.caller.generate() + "(" + joined + ")" + createDebugString(this.type);
    }
}
/* private */ class IndexValue /*  */ {
    constructor(parent, child) {
    }
    generate() {
        return this.parent.generate() + "[" + this.child.generate() + "]";
    }
    type() {
        return Primitive.Unknown;
    }
}
/* private */ class SymbolValue /*  */ {
    constructor(stripped, type) {
    }
    generate() {
        return this.stripped + createDebugString(this.type);
    }
}
/* private */ class JVMMap {
    constructor(map) {
    }
    find(key) {
        if (this.map.containsKey(key)) {
            return new Some(this.map.get(key));
        }
        return new None();
    }
    with(key, value) {
        /* this.map.put(key, value) */ ;
        return this;
    }
}
/* private static */ class Maps /*  */ {
    empty() {
        return new JVMMap();
    }
}
/* private */ class MapCollector {
    createInitial() {
        return Maps.empty();
    }
    fold(current, element) {
        return current.with(element[0](), element[1]());
    }
}
/* private static */ class ConstructorHeader /*  */ {
    createDefinition(paramTypes) {
        return ImmutableDefinition.createSimpleDefinition("new", Primitive.Unknown);
    }
    generateWithParams(joinedParameters) {
        return "constructor " + joinedParameters;
    }
}
/* private static */ class Method /*  */ {
}
this.depth = depth;
this.header = header;
this.parameters = parameters;
this.statements = maybeStatements;
joinStatements(statements, (List));
string;
{
    return statements.iterate().map(FunctionSegment.generate).collect(new Joiner()).orElse("");
}
generate();
string;
{
    let indent = createIndent(this.depth);
    let generatedHeader = this.header.generateWithParams(joinValues(this.parameters));
    let generatedStatements = this.statements.map(Method.joinStatements).map((inner) => " {" + inner + indent + "}").orElse(";");
    return indent + generatedHeader + generatedStatements;
}
/* private */ class Block /*  */ {
    constructor(depth, header, statements) {
    }
    generate() {
        let indent = createIndent(this.depth);
        let collect = this.statements.iterate().map(FunctionSegment.generate).collect(new Joiner()).orElse("");
        return indent + this.header.generate() + "{" + collect + indent + "}";
    }
}
/* private */ class Conditional /*  */ {
    constructor(prefix, value1) {
    }
    generate() {
        return this.prefix + " (" + this.value1.generate() + ")";
    }
}
/* private static */ class Else /*  */ {
    generate() {
        return "else ";
    }
}
/* private static */ class Return /*  */ {
}
this.value1 = value1;
generate();
string;
{
    return "return " + this.value1.generate();
}
/* private */ class Initialization /*  */ {
    constructor(definition, source) {
    }
    generate() {
        return "let " + this.definition.generate() + " = " + this.source.generate();
    }
}
/* private */ class Assignment /*  */ {
    constructor(destination, source) {
    }
    generate() {
        return this.destination.generate() + " = " + this.source.generate();
    }
}
/* private */ class Statement /*  */ {
    constructor(depth, value) {
    }
    generate() {
        return createIndent(this.depth) + this.value.generate() + ";";
    }
}
/* private */ class MethodPrototype /*  */ {
    constructor(depth, header, parameters, content) {
    }
    createDefinition() {
        return this.header().createDefinition(this.findParamTypes());
    }
    findParamTypes() {
        return this.parameters().iterate().map(Definition.type).collect(new ListCollector());
    }
    maybeCreateDefinition() {
        return new Some(this.header.createDefinition(this.findParamTypes()));
    }
    maybeCreateObjectType() {
        return new None();
    }
}
/* private */ class IncompleteClassSegmentWrapper /*  */ {
    constructor(segment) {
    }
    maybeCreateDefinition() {
        return new None();
    }
    maybeCreateObjectType() {
        return new None();
    }
}
/* private */ class ClassDefinition /*  */ {
    constructor(definition, depth) {
    }
    maybeCreateDefinition() {
        return new Some(this.definition);
    }
    maybeCreateObjectType() {
        return new None();
    }
}
/* private */ class StructurePrototype /*  */ {
    constructor(targetInfix, beforeInfix, name, typeParams, parameters, after, segments) {
    }
    createObjectType() {
        let definitionFromSegments = this.segments.iterate().map(IncompleteClassSegment.maybeCreateDefinition).flatMap(Iterators.fromOption).collect(new ListCollector());
        return new ObjectType(this.name, this.typeParams, definitionFromSegments.addAllLast(this.parameters));
    }
    maybeCreateDefinition() {
        return new None();
    }
    maybeCreateObjectType() {
        return new Some(this.createObjectType());
    }
}
/* private */ class Primitive /*  */ {
    constructor(value) {
        this.value = value;
    }
    generate() {
        return this.value;
    }
    replace(mapping) {
        return this;
    }
    findName() {
        return new None();
    }
}
/* private */ class Operator /*  */ {
    constructor(sourceRepresentation, targetRepresentation) {
        this.sourceRepresentation = sourceRepresentation;
        this.targetRepresentation = targetRepresentation;
    }
}
/* private */ class BooleanValue /*  */ {
    constructor(value) {
        this.value = value;
    }
    generate() {
        return this.value;
    }
    type() {
        return Primitive.Boolean;
    }
}
/* public */ class Main /*  */ {
    main() {
        let parent = /* Paths */ .get(".", "src", "java", "magma");
        let source = parent.resolve("Main.java");
        let target = parent.resolve("main.ts");
        let input = /* Files */ .readString(source);
        /* Files.writeString(target, compile(input)) */ ;
        /* new ProcessBuilder("cmd", "/c", "npm", "exec", "tsc")
                .inheritIO()
                .start()
                .waitFor() */ ;
    }
}
/* catch (IOException | InterruptedException e) */ {
    /* throw new RuntimeException(e) */ ;
}
compile(input, string);
string;
{
    let state = new CompileState();
    let parsed = parseStatements(state, input, Main.compileRootSegment);
    let joined = parsed[0]().structures.iterate().collect(new Joiner()).orElse("");
    return joined + generateStatements(parsed[1]());
}
generateStatements(statements, (List));
string;
{
    return generateAll(Main.mergeStatements, statements);
}
parseStatements(state, CompileState, input, string, mapper, (arg0, arg1) => [CompileState, T]);
[CompileState, (List)];
{
    return parseAllWithIndices(state, input, Main.foldStatementChar, (state3, tuple) => new Some(mapper(state3, tuple.right()))).orElseGet(() => new Tuple2Impl(state, Lists.empty()));
}
generateAll(merger, (arg0, arg1) => string, elements, (List));
string;
{
    return elements.iterate().fold("", merger);
}
parseAllWithIndices(state, CompileState, input, string, folder, (arg0, arg1) => DivideState, mapper, (arg0, arg1) => Option);
Option < [CompileState, (List)] > {
    let, stringList: (List) = divideAll(input, folder),
    return: mapUsingState(state, stringList, mapper)
};
mapUsingState(state, CompileState, elements, (List), mapper, (arg0, arg1) => Option);
Option < [CompileState, (List)] > {
    let, initial: (Option) = new Some(new Tuple2Impl(state, Lists.empty())),
    return: elements.iterateWithIndices().fold(initial, (tuple, element) => {
        return tuple.flatMap((inner) => {
            let state1 = inner.left();
            let right = inner.right();
            return mapper(state1, element).map((applied) => {
                return new Tuple2Impl(applied[0](), right.addLast(applied[1]()));
            });
        });
    })
};
mergeStatements(cache, string, statement, string);
string;
{
    return cache + statement;
}
divideAll(input, string, folder, (arg0, arg1) => DivideState);
List < string > {
    let, current: DivideState = new DivideState(input),
    while() {
        let maybePopped = current.pop().map((tuple) => {
            return foldSingleQuotes(tuple).or(() => foldDoubleQuotes(tuple)).orElseGet(() => folder(tuple[1](), tuple[0]()));
        });
        if (maybePopped.isPresent()) {
            current = maybePopped.orElse(current);
        }
        else {
            /* break */ ;
        }
    },
    return: current.advance().segments
};
foldDoubleQuotes(tuple, [string, DivideState]);
Option < DivideState > {
    if(tuple, [], ) { }
}() === ; /*  '\"' */
{
    let current = tuple[1]().append(tuple[0]());
    while (true) {
        let maybePopped = current.popAndAppendToTuple();
        if (maybePopped.isEmpty()) {
            /* break */ ;
        }
        let popped = maybePopped.orElse( /* null */);
        current = popped.right();
        if (popped.left() ===  /*  '\\' */) {
            current = current.popAndAppendToOption().orElse(current);
        }
        if (popped.left() ===  /*  '\"' */) {
            /* break */ ;
        }
    }
    return new Some(current);
}
return new None();
foldSingleQuotes(tuple, [string, DivideState]);
Option < DivideState > {
    if( /* tuple.left() != '\'' */) {
        return new None();
    },
    let, appended = tuple[1]().append(tuple[0]()),
    return: appended.popAndAppendToTuple().map(Main.foldEscaped).flatMap(DivideState.popAndAppendToOption)
};
foldEscaped(escaped, [string, DivideState]);
DivideState;
{
    if (escaped[0]() ===  /*  '\\' */) {
        return escaped[1]().popAndAppendToOption().orElse(escaped[1]());
    }
    return escaped[1]();
}
foldStatementChar(state, DivideState, c, string);
DivideState;
{
    let append = state.append(c);
    if (c ===  /*  ';'  */ && append.isLevel()) {
        return append.advance();
    }
    if (c ===  /*  '}'  */ && append.isShallow()) {
        return append.advance().exit();
    }
    if (c ===  /*  '{'  */ || c ===  /*  '(' */) {
        return append.enter();
    }
    if (c ===  /*  '}'  */ || c ===  /*  ')' */) {
        return append.exit();
    }
    return append;
}
compileRootSegment(state, CompileState, input, string);
[CompileState, string];
{
    let stripped = input.strip();
    if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
        return new Tuple2Impl(state, "");
    }
    return parseClass(stripped, state).flatMap((tuple) => completeStructure(tuple[0](), tuple[1]())).map((tuple) => new Tuple2Impl(tuple[0](), tuple[1]().generate())).orElseGet(() => new Tuple2Impl(state, generatePlaceholder(stripped)));
}
parseClass(stripped, string, state, CompileState);
Option < [CompileState, StructurePrototype] > {
    return: parseStructure(stripped, "class ", "class ", state)
};
parseStructure(stripped, string, sourceInfix, string, targetInfix, string, state, CompileState);
Option < [CompileState, StructurePrototype] > {
    return: first(stripped, sourceInfix, (beforeInfix, right) => {
        return first(right, "{", (beforeContent, withEnd) => {
            return suffix(withEnd.strip(), "}", (content1) => {
                return parseStructureWithMaybeImplements(targetInfix, state, beforeInfix, beforeContent, content1);
            });
        });
    })
};
parseStructureWithMaybeImplements(targetInfix, string, state, CompileState, beforeInfix, string, beforeContent, string, content1, string);
Option < [CompileState, StructurePrototype] > {
    return: first(beforeContent, " implements ", (s, s2) => {
        return parseStructureWithMaybeExtends(targetInfix, state, beforeInfix, s, content1);
    }).or(() => {
        return parseStructureWithMaybeExtends(targetInfix, state, beforeInfix, beforeContent, content1);
    })
};
parseStructureWithMaybeExtends(targetInfix, string, state, CompileState, beforeInfix, string, beforeContent, string, content1, string);
Option < [CompileState, StructurePrototype] > {
    return: first(beforeContent, " extends ", (s, s2) => {
        return parseStructureWithMaybeParams(targetInfix, state, beforeInfix, s, content1);
    }).or(() => {
        return parseStructureWithMaybeParams(targetInfix, state, beforeInfix, beforeContent, content1);
    })
};
parseStructureWithMaybeParams(targetInfix, string, state, CompileState, beforeInfix, string, beforeContent, string, content1, string);
Option < [CompileState, StructurePrototype] > {
    return: suffix(beforeContent.strip(), ")", (s) => {
        return first(s, "(", (s1, s2) => {
            let parsed = parseParameters(state, s2);
            return parseStructureWithMaybeTypeParams(targetInfix, parsed[0](), beforeInfix, s1, content1, parsed[1]());
        });
    }).or(() => {
        return parseStructureWithMaybeTypeParams(targetInfix, state, beforeInfix, beforeContent, content1, Lists.empty());
    })
};
parseStructureWithMaybeTypeParams(targetInfix, string, state, CompileState, beforeInfix, string, beforeContent, string, content1, string, params, (List));
Option < [CompileState, StructurePrototype] > {
    return: first(beforeContent, "<", (name, withTypeParams) => {
        return first(withTypeParams, ">", (typeParamsString, afterTypeParams) => {
            let mapper = (state1, s) => new Tuple2Impl(state1, s.strip());
            let typeParams = parseValuesOrEmpty(state, typeParamsString, (state1, s) => new Some(mapper(state1, s)));
            return assembleStructure(typeParams[0](), targetInfix, beforeInfix, name, content1, typeParams[1](), afterTypeParams, params);
        });
    }).or(() => {
        return assembleStructure(state, targetInfix, beforeInfix, beforeContent, content1, Lists.empty(), "", params);
    })
};
assembleStructure(state, CompileState, targetInfix, string, beforeInfix, string, rawName, string, content, string, typeParams, (List), after, string, rawParameters, (List));
Option < [CompileState, StructurePrototype] > {
    let, name = rawName.strip(),
    if(, isSymbol) { }
}(name);
{
    return new None();
}
let segmentsTuple = parseStatements(state.pushStructName(name).withTypeParams(typeParams), content, (state0, input) => parseClassSegment(state0, input, 1));
let segmentsState = segmentsTuple[0]();
let segments = segmentsTuple[1]();
let parameters = retainDefinitions(rawParameters);
let prototype = new StructurePrototype(targetInfix, beforeInfix, name, typeParams, parameters, after, segments);
return new Some(new Tuple2Impl(segmentsState.addType(prototype.createObjectType()), prototype));
completeStructure(state, CompileState, prototype, StructurePrototype);
Option < [CompileState, ClassSegment] > {
    let, thisType: ObjectType = prototype.createObjectType(),
    let, state2: CompileState = state.enterDefinitions().define(ImmutableDefinition.createSimpleDefinition("this", thisType)),
    return: mapUsingState(state2, prototype.segments(), Main.completeClassSegment).map((completedTuple) => {
        let completedState = completedTuple[0]();
        let completed = completedTuple[1]();
        let state1 = completedState.exitDefinitions();
        let parameters = prototype.parameters();
        /* List<ClassSegment> withMaybeConstructor */ ;
        if (parameters.isEmpty()) {
            completed;
        }
        else {
            completed.addFirst(new Method(1, new ConstructorHeader(), parameters, new Some(Lists.empty())));
        }
        let parsed2 = /* withMaybeConstructor */ .iterate().map(ClassSegment.generate).collect(new Joiner()).orElse("");
        let joinedTypeParams = prototype.typeParams().iterate().collect(new Joiner(", ")).map((inner) => "<" + inner + ">").orElse("");
        let generated = generatePlaceholder(prototype.beforeInfix().strip()) + prototype.targetInfix() + prototype.name() + joinedTypeParams + generatePlaceholder(prototype.after()) + " {" + parsed2 + "\n}\n";
        let definedState = state1.popStructName().addStructure(generated);
        return new Tuple2Impl(definedState, new Whitespace());
    })
};
completeClassSegment(state1, CompileState, entry, [number, IncompleteClassSegment]);
Option < [CompileState, ClassSegment] > {
/* return switch (entry.right()) */ };
/* return switch (entry.right()) */ {
    /* case IncompleteClassSegmentWrapper wrapper -> new Some<>(new Tuple2Impl<>(state1, wrapper.segment)) */ ;
    /* case MethodPrototype methodPrototype -> completeMethod(state1, methodPrototype) */ ;
    /* case Whitespace whitespace -> new Some<>(new Tuple2Impl<>(state1, whitespace)) */ ;
    /* case Placeholder placeholder -> new Some<>(new Tuple2Impl<>(state1, placeholder)) */ ;
    /* case ClassDefinition classDefinition -> completeDefinition(state1, classDefinition) */ ;
    /* case StructurePrototype structurePrototype -> completeStructure(state1, structurePrototype) */ ;
}
/*  */ ;
completeDefinition(state1, CompileState, classDefinition, ClassDefinition);
Option < [CompileState, ClassSegment] > {
    let, definition: StatementValue = classDefinition.definition,
    let, statement: Statement = new Statement(classDefinition.depth, definition),
    return: new Some(new Tuple2Impl(state1, statement))
};
retainDefinition(parameter, Parameter);
Option < Definition > {
    if(parameter) { }, : ._variant === ParameterVariant.Definition
};
{
    return new Some( /* definition */);
}
return new None();
isSymbol(input, string);
boolean;
{
    /* for (var i = 0; i < input.length(); i++) */ {
        let c = input.charAt( /* i */);
        if ( /* Character */.isLetter(c) || /*  */ ( /* i != 0  */ && /* Character */ .isDigit(c))) {
            /* continue */ ;
        }
        return false;
    }
    return true;
}
prefix(input, string, prefix, string, mapper, (arg0) => Option);
Option < T > {
    if(, input) { }, : .startsWith(prefix)
};
{
    return new None();
}
let slice = input.substring(prefix.length());
return mapper(slice);
suffix(input, string, suffix, string, mapper, (arg0) => Option);
Option < T > {
    if(, input) { }, : .endsWith(suffix)
};
{
    return new None();
}
let slice = input.substring(0, input.length() - suffix.length());
return mapper(slice);
parseClassSegment(state, CompileState, input, string, depth, number);
[CompileState, IncompleteClassSegment];
{
    return /* Main.<Whitespace, IncompleteClassSegment>typed */ (() => parseWhitespace(input, state)).or(() => typed(() => parseClass(input, state))).or(() => typed(() => parseStructure(input, "interface ", "interface ", state))).or(() => typed(() => parseStructure(input, "record ", "class ", state))).or(() => typed(() => parseStructure(input, "enum ", "class ", state))).or(() => parseMethod(state, input, depth)).or(() => typed(() => parseDefinitionStatement(input, depth, state))).orElseGet(() => new Tuple2Impl(state, new Placeholder(input)));
}
typed(action, () => Option);
Option < [CompileState, S] > {
    return: action().map((tuple) => new Tuple2Impl(tuple[0](), tuple[1]()))
};
parseWhitespace(input, string, state, CompileState);
Option < [CompileState, Whitespace] > {
    if(input) { }, : .isBlank()
};
{
    return new Some(new Tuple2Impl(state, new Whitespace()));
}
return new None();
parseMethod(state, CompileState, input, string, depth, number);
Option < [CompileState, IncompleteClassSegment] > {
    return: first(input, "(", (definitionString, withParams) => {
        return first(withParams, ")", (parametersString, rawContent) => {
            return /* parseDefinition(state, definitionString).<Tuple2<CompileState, Header>>map */ ((tuple) => new Tuple2Impl(tuple.left(), tuple.right())).or(() => parseConstructor(state, definitionString)).flatMap((definitionTuple) => assembleMethod(depth, parametersString, rawContent, definitionTuple));
        });
    })
};
assembleMethod(depth, number, parametersString, string, rawContent, string, definitionTuple, [CompileState, Header]);
Option < [CompileState, IncompleteClassSegment] > {
    let, definitionState = definitionTuple[0](),
    let, header = definitionTuple[1](),
    let, parametersTuple: [CompileState, (List)] = parseParameters(definitionState, parametersString),
    let, rawParameters = parametersTuple[1](),
    let, parameters: (List) = retainDefinitions(rawParameters),
    let, prototype: MethodPrototype = new MethodPrototype(depth, header, parameters, rawContent.strip()),
    return: new Some(new Tuple2Impl(parametersTuple[0]().define(prototype.createDefinition()), prototype))
};
completeMethod(state, CompileState, prototype, MethodPrototype);
Option < [CompileState, ClassSegment] > {
    let, definition: Definition = prototype.createDefinition(),
    if(prototype) { }, : .content().equals(";")
};
{
    return new Some(new Tuple2Impl(state.define(definition), new Method(prototype.depth(), prototype.header(), prototype.parameters(), new None())));
}
if (prototype.content().startsWith("{") && prototype.content().endsWith("}")) {
    let substring = prototype.content().substring(1, prototype.content().length() - 1);
    let withDefined = state.enterDefinitions().defineAll(prototype.parameters());
    let statementsTuple = parseStatements(withDefined, substring, (state1, input1) => parseFunctionSegment(state1, input1, prototype.depth() + 1));
    let statements = statementsTuple[1]();
    return new Some(new Tuple2Impl(statementsTuple[0]().exitDefinitions().define(definition), new Method(prototype.depth(), prototype.header(), prototype.parameters(), new Some(statements))));
}
return new None();
parseConstructor(state, CompileState, input, string);
Option < [CompileState, Header] > {
    let, stripped = input.strip(),
    if(stripped) { }, : .equals(state.structNames.last().orElse(""))
};
{
    return new Some(new Tuple2Impl(state, new ConstructorHeader()));
}
return new None();
joinValues(retainParameters, (List));
string;
{
    let inner = retainParameters.iterate().map(Definition.generate).collect(new Joiner(", ")).orElse("");
    return "(" + inner + ")";
}
retainDefinitions(right, (List));
List < Definition > {
    return: right.iterate().map(Main.retainDefinition).flatMap(Iterators.fromOption).collect(new ListCollector())
};
parseParameters(state, CompileState, params, string);
[CompileState, (List)];
{
    return parseValuesOrEmpty(state, params, (state1, s) => new Some(parseParameter(state1, s)));
}
parseFunctionSegments(state, CompileState, input, string, depth, number);
[CompileState, (List)];
{
    return parseStatements(state, input, (state1, input1) => parseFunctionSegment(state1, input1, depth + 1));
}
parseFunctionSegment(state, CompileState, input, string, depth, number);
[CompileState, FunctionSegment];
{
    let stripped = input.strip();
    if (stripped.isEmpty()) {
        return new Tuple2Impl(state, new Whitespace());
    }
    return parseFunctionStatement(state, depth, stripped).or(() => parseBlock(state, depth, stripped)).orElseGet(() => new Tuple2Impl(state, new Placeholder(stripped)));
}
parseFunctionStatement(state, CompileState, depth, number, stripped, string);
Option < [CompileState, FunctionSegment] > {
    return: suffix(stripped, ";", (s) => {
        let tuple = parseStatementValue(state, s, depth);
        let left = tuple[0]();
        let right = tuple[1]();
        return new Some(new Tuple2Impl(left, new Statement(depth, right)));
    })
};
parseBlock(state, CompileState, depth, number, stripped, string);
Option < [CompileState, FunctionSegment] > {
    return: suffix(stripped, "}", (withoutEnd) => {
        return split(() => toFirst(withoutEnd), (beforeContent, content) => {
            return suffix(beforeContent, "{", (s) => {
                let statements = parseFunctionSegments(state, content, depth);
                let headerTuple = parseBlockHeader(state, s, depth);
                let headerState = headerTuple[0]();
                let header = headerTuple[1]();
                let right = statements[1]();
                return new Some(new Tuple2Impl(headerState, new Block(depth, header, right)));
            });
        });
    })
};
toFirst(input, string);
Option < [string, string] > {
    let, divisions: (List) = divideAll(input, Main.foldBlockStart),
    return: divisions.removeFirst().map((removed) => {
        let right = removed[0]();
        let left = removed[1]().iterate().collect(new Joiner("")).orElse("");
        return new Tuple2Impl(right, left);
    })
};
parseBlockHeader(state, CompileState, input, string, depth, number);
[CompileState, BlockHeader];
{
    let stripped = input.strip();
    return parseConditional(state, stripped, "if", depth).or(() => parseConditional(state, stripped, "while", depth)).or(() => parseElse(state, input)).orElseGet(() => new Tuple2Impl(state, new Placeholder(stripped)));
}
parseElse(state, CompileState, input, string);
Option < [CompileState, BlockHeader] > {
    let, stripped = input.strip(),
    if(stripped) { }, : .equals("else")
};
{
    return new Some(new Tuple2Impl(state, new Else()));
}
return new None();
parseConditional(state, CompileState, input, string, prefix, string, depth, number);
Option < [CompileState, BlockHeader] > {
    return: prefix(input, prefix, (withoutPrefix) => {
        return prefix(withoutPrefix.strip(), "(", (withoutValueStart) => {
            return suffix(withoutValueStart, ")", (value) => {
                let valueTuple = parseValue(state, value, depth);
                let value1 = valueTuple[1]();
                return new Some(new Tuple2Impl(valueTuple[0](), new Conditional(prefix, value1)));
            });
        });
    })
};
foldBlockStart(state, DivideState, c, string);
DivideState;
{
    let appended = state.append(c);
    if (c ===  /*  '{'  */ && state.isLevel()) {
        return appended.advance();
    }
    if (c ===  /*  '{' */) {
        return appended.enter();
    }
    if (c ===  /*  '}' */) {
        return appended.exit();
    }
    return appended;
}
parseStatementValue(state, CompileState, input, string, depth, number);
[CompileState, StatementValue];
{
    let stripped = input.strip();
    if (stripped.startsWith("return ")) {
        let value = stripped.substring("return ".length());
        let tuple = parseValue(state, value, depth);
        let value1 = tuple[1]();
        return new Tuple2Impl(tuple[0](), new Return(value1));
    }
    return parseAssignment(state, depth, stripped).orElseGet(() => {
        return new Tuple2Impl(state, new Placeholder(stripped));
    });
}
parseAssignment(state, CompileState, depth, number, stripped, string);
Option < [CompileState, StatementValue] > {
    return: first(stripped, "=", (beforeEquals, valueString) => {
        let sourceTuple = parseValue(state, valueString, depth);
        let sourceState = sourceTuple[0]();
        let source = sourceTuple[1]();
        return parseDefinition(sourceState, beforeEquals).flatMap((definitionTuple) => parseInitialization(definitionTuple[0](), definitionTuple[1](), source)).or(() => parseAssignment(depth, beforeEquals, sourceState, source));
    })
};
parseAssignment(depth, number, beforeEquals, string, sourceState, CompileState, source, Value);
Option < [CompileState, StatementValue] > {
    let, destinationTuple: [CompileState, Value] = parseValue(sourceState, beforeEquals, depth),
    let, destinationState = destinationTuple[0](),
    let, destination = destinationTuple[1](),
    return: new Some(new Tuple2Impl(destinationState, new Assignment(destination, source)))
};
parseInitialization(state, CompileState, rawDefinition, Definition, source, Value);
Option < [CompileState, StatementValue] > {
    let, definition: Definition = rawDefinition.mapType((type) => {
        if (type.equals(Primitive.Unknown)) {
            return source.type();
        }
        else {
            return type;
        }
    }),
    return: new Some(new Tuple2Impl(state.define(definition), new Initialization(definition, source)))
};
parseValue(state, CompileState, input, string, depth, number);
[CompileState, Value];
{
    return parseBoolean(state, input).or(() => parseLambda(state, input, depth)).or(() => parseString(state, input)).or(() => parseDataAccess(state, input, depth)).or(() => parseSymbolValue(state, input)).or(() => parseInvokable(state, input, depth)).or(() => parseDigits(state, input)).or(() => parseOperation(state, input, depth, Operator.ADD)).or(() => parseOperation(state, input, depth, Operator.EQUALS)).or(() => parseOperation(state, input, depth, Operator.SUBTRACT)).or(() => parseOperation(state, input, depth, Operator.AND)).or(() => parseOperation(state, input, depth, Operator.OR)).or(() => parseOperation(state, input, depth)).or(() => parseNot(state, input, depth)).or(() => parseMethodReference(state, input, depth)).or(() => parseInstanceOf(state, input, depth)).orElseGet(() => new Tuple2Impl(state, new Placeholder(input)));
}
parseBoolean(state, CompileState, input, string);
Option < [CompileState, Value] > {
    let, stripped = input.strip(),
    if(stripped) { }, : .equals("false")
};
{
    return new Some(new Tuple2Impl(state, BooleanValue.False));
}
if (stripped.equals("true")) {
    return new Some(new Tuple2Impl(state, BooleanValue.True));
}
return new None();
parseInstanceOf(state, CompileState, input, string, depth, number);
Option < [CompileState, Value] > {
    return: last(input, "instanceof", (s, s2) => {
        let childTuple = parseValue(state, s, depth);
        return parseDefinition(childTuple[0](), s2).map((definitionTuple) => {
            let value = childTuple[1]();
            let definition = definitionTuple[1]();
            let variant = new DataAccess(value, "_variant", Primitive.Unknown);
            let type = value.type();
            let generate = type.findName().orElse("");
            let temp = new SymbolValue(generate + "Variant." + definition.type().findName().orElse(""), Primitive.Unknown);
            return new Tuple2Impl(definitionTuple[0](), new Operation(variant, Operator.EQUALS, temp));
        });
    })
};
parseMethodReference(state, CompileState, input, string, depth, number);
Option < [CompileState, Value] > {
    return: last(input, "::", (s, s2) => {
        let tuple = parseValue(state, s, depth);
        return new Some(new Tuple2Impl(tuple[0](), new DataAccess(tuple[1](), s2, Primitive.Unknown)));
    })
};
parseNot(state, CompileState, input, string, depth, number);
Option < [CompileState, Value] > {
    let, stripped = input.strip(),
    if(stripped) { }, : .startsWith("!")
};
{
    let slice = stripped.substring(1);
    let tuple = parseValue(state, slice, depth);
    let value = tuple[1]();
    return new Some(new Tuple2Impl(tuple[0](), new Not(value)));
}
return new None();
parseLambda(state, CompileState, input, string, depth, number);
Option < [CompileState, Value] > {
    return: first(input, "->", (beforeArrow, valueString) => {
        let strippedBeforeArrow = beforeArrow.strip();
        if (isSymbol(strippedBeforeArrow)) {
            let type = Primitive.Unknown;
            if ( /* state.typeRegister instanceof Some */( /* var expectedType */)) {
                if ( /* expectedType */._variant === Variant.FunctionType) {
                    type = /* functionType */ .arguments.get(0).orElse( /* null */);
                }
            }
            return assembleLambda(state, Lists.of(ImmutableDefinition.createSimpleDefinition(strippedBeforeArrow, type)), valueString, depth);
        }
        if (strippedBeforeArrow.startsWith("(") && strippedBeforeArrow.endsWith(")")) {
            let parameterNames = divideAll(strippedBeforeArrow.substring(1, strippedBeforeArrow.length() - 1), Main.foldValueChar).iterate().map() /* String */.strip;
        }
    }).filter((value) => !value.isEmpty()).map((name) => ImmutableDefinition.createSimpleDefinition(name, Primitive.Unknown)).collect(new ListCollector()),
    return: assembleLambda(state, parameterNames, valueString, depth)
};
return new None();
;
assembleLambda(state, CompileState, definitions, (List), valueString, string, depth, number);
Some < [CompileState, Value] > {
    let, strippedValueString = valueString.strip(),
    let, state2: CompileState = state.defineAll(definitions),
    if(strippedValueString) { }, : .startsWith("{") && strippedValueString.endsWith("}")
};
{
    let value1 = parseStatements(state2, strippedValueString.substring(1, strippedValueString.length() - 1), (state1, input1) => parseFunctionSegment(state1, input1, depth + 1));
    let right = value1[1]();
    new Tuple2Impl(value1[0](), new BlockLambdaValue(depth, right));
}
{
    let value1 = parseValue(state2, strippedValueString, depth);
    new Tuple2Impl(value1[0](), value1[1]());
}
let right = /* value */ .right();
return new Some(new Tuple2Impl(left(), new Lambda(definitions, right)));
parseDigits(state, CompileState, input, string);
Option < [CompileState, Value] > {
    let, stripped = input.strip(),
    if(isNumber) { }
}(stripped);
{
    return new Some(new Tuple2Impl(state, new SymbolValue(stripped, Primitive.Int)));
}
return new None();
isNumber(input, string);
boolean;
{
    /* String maybeTruncated */ ;
    if (input.startsWith("-")) {
        input.substring(1);
    }
    else {
        input;
    }
    return areAllDigits( /* maybeTruncated */);
}
areAllDigits(input, string);
boolean;
{
    /* for (var i = 0; i < input.length(); i++) */ {
        let c = input.charAt( /* i */);
        if ( /* Character */.isDigit(c)) {
            /* continue */ ;
        }
        return false;
    }
    return true;
}
parseInvokable(state, CompileState, input, string, depth, number);
Option < [CompileState, Value] > {
    return: suffix(input.strip(), ")", (withoutEnd) => {
        return split(() => toLast(withoutEnd, "", Main.foldInvocationStart), (callerWithEnd, argumentsString) => {
            return suffix(callerWithEnd, "(", (callerString) => {
                return assembleInvokable(state, depth, argumentsString, callerString.strip());
            });
        });
    })
};
assembleInvokable(state, CompileState, depth, number, argumentsString, string, callerString, string);
Some < [CompileState, Value] > {
    let, callerTuple: [CompileState, Caller] = invocationHeader(state, depth, callerString),
    let, oldCallerState = callerTuple[0](),
    let, oldCaller = callerTuple[1](),
    let, newCaller: Caller = modifyCaller(oldCallerState, oldCaller),
    let, callerType: FunctionType = findCallerType(newCaller),
    let, argumentsTuple: T = parseValuesWithIndices(oldCallerState, argumentsString, (currentState, pair) => {
        let index = pair.left();
        let element = pair.right();
        let expectedType = callerType.arguments.get(index).orElse(Primitive.Unknown);
        let withExpected = currentState.withExpectedType(expectedType);
        let valueTuple = parseArgument(withExpected, element, depth);
        let valueState = valueTuple[0]();
        let value = valueTuple[1]();
        let actualType = valueTuple[0]().typeRegister.orElse(Primitive.Unknown);
        return new Some(new Tuple2Impl(valueState, new Tuple2Impl(value, actualType)));
    }).orElseGet(() => new Tuple2Impl(oldCallerState, Lists.empty())),
    let, argumentsState = argumentsTuple.left(),
    let, argumentsWithActualTypes = argumentsTuple.right(),
    let, arguments = argumentsWithActualTypes.iterate().map(Tuple2.left).map(Main.retainValue).flatMap(Iterators.fromOption).collect(new ListCollector()),
    let, invokable: Invokable = new Invokable(newCaller, arguments, callerType.returns),
    return: new Some(new Tuple2Impl(argumentsState, invokable))
};
retainValue(argument, Argument);
Option < Value > {
    if(argument) { }, : ._variant === ArgumentVariant.Value
};
{
    return new Some( /* value */);
}
return new None();
parseArgument(state, CompileState, element, string, depth, number);
[CompileState, Argument];
{
    if (element.isEmpty()) {
        return new Tuple2Impl(state, new Whitespace());
    }
    let tuple = parseValue(state, element, depth);
    return new Tuple2Impl(tuple[0](), tuple[1]());
}
findCallerType(newCaller, Caller);
FunctionType;
{
    let callerType = new FunctionType(Lists.empty(), Primitive.Unknown);
    /* switch (newCaller) */ {
        /* case ConstructionCaller constructionCaller -> */ {
            callerType = /* constructionCaller */ .toFunction();
        }
        /* case Value value -> */ {
            let type = /* value */ .type();
            if (type._variant === Variant.FunctionType) {
                callerType =  /* functionType */;
            }
        }
    }
    return callerType;
}
modifyCaller(state, CompileState, oldCaller, Caller);
Caller;
{
    if (oldCaller._variant === CallerVariant.DataAccess) {
        let type = resolveType(parent, state);
        if ( /* type instanceof FunctionType */) {
            return /* access */ .parent;
        }
    }
    return oldCaller;
}
resolveType(value, Value, state, CompileState);
Type;
{
    return value.type();
}
invocationHeader(state, CompileState, depth, number, callerString1, string);
[CompileState, Caller];
{
    if (callerString1.startsWith("new ")) {
        let input1 = callerString1.substring("new ".length());
        let map = parseType(state, input1).map((type) => {
            let right = type[1]();
            return new Tuple2Impl(type[0](), new ConstructionCaller(right));
        });
        if (map.isPresent()) {
            return map.orElse( /* null */);
        }
    }
    let tuple = parseValue(state, callerString1, depth);
    return new Tuple2Impl(tuple[0](), tuple[1]());
}
foldInvocationStart(state, DivideState, c, string);
DivideState;
{
    let appended = state.append(c);
    if (c ===  /*  '(' */) {
        let enter = appended.enter();
        if (enter.isShallow()) {
            return enter.advance();
        }
        return enter;
    }
    if (c ===  /*  ')' */) {
        return appended.exit();
    }
    return appended;
}
parseDataAccess(state, CompileState, input, string, depth, number);
Option < [CompileState, Value] > {
    return: last(input.strip(), ".", (parentString, rawProperty) => {
        let property = rawProperty.strip();
        if (!isSymbol(property)) {
            return new None();
        }
        let tuple = parseValue(state, parentString, depth);
        let parent = tuple[1]();
        let parentType = parent.type();
        if ( /* parentType instanceof TupleType */) {
            if (property.equals("left")) {
                return new Some(new Tuple2Impl(state, new IndexValue(parent, new SymbolValue("0", Primitive.Int))));
            }
            if (property.equals("right")) {
                return new Some(new Tuple2Impl(state, new IndexValue(parent, new SymbolValue("1", Primitive.Int))));
            }
        }
        let type = Primitive.Unknown;
        if (parentType._variant === Variant.FindableType) {
            if ( /* objectType.find(property) instanceof Some */( /* var memberType */)) {
                type =  /* memberType */;
            }
        }
        return new Some(new Tuple2Impl(tuple[0](), new DataAccess(parent, property, type)));
    })
};
parseString(state, CompileState, input, string);
Option < [CompileState, Value] > {
    let, stripped = input.strip(),
    if(stripped) { }, : .startsWith("\"") && stripped.endsWith("\"")
};
{
    return new Some(new Tuple2Impl(state, new StringValue(stripped)));
}
return new None();
parseSymbolValue(state, CompileState, value, string);
Option < [CompileState, Value] > {
    let, stripped = value.strip(),
    if(isSymbol) { }
}(stripped);
{
    if ( /* state.resolveValue(stripped) instanceof Some */( /* var type */)) {
        return new Some(new Tuple2Impl(state, new SymbolValue(stripped, type)));
    }
    if ( /* state.resolveType(stripped) instanceof Some */( /* var type */)) {
        return new Some(new Tuple2Impl(state, new SymbolValue(stripped, type)));
    }
    return new Some(new Tuple2Impl(state, new Placeholder(stripped)));
}
return new None();
parseOperation(state, CompileState, value, string, depth, number, operator);
Option < [CompileState, Value] > {
    return: first(value, operator.sourceRepresentation, (leftString, rightString) => {
        let leftTuple = parseValue(state, leftString, depth);
        let rightTuple = parseValue(leftTuple[0](), rightString, depth);
        let left = leftTuple[1]();
        let right = rightTuple[1]();
        return new Some(new Tuple2Impl(rightTuple[0](), new Operation(left, operator, right)));
    })
};
parseValuesOrEmpty(state, CompileState, input, string, mapper, (arg0, arg1) => Option);
[CompileState, (List)];
{
    return parseValues(state, input, mapper).orElseGet(() => new Tuple2Impl(state, Lists.empty()));
}
parseValues(state, CompileState, input, string, mapper, (arg0, arg1) => Option);
Option < [CompileState, (List)] > {
    return: parseValuesWithIndices(state, input, (state1, tuple) => mapper(state1, tuple.right()))
};
parseValuesWithIndices(state, CompileState, input, string, mapper, (arg0, arg1) => Option);
Option < [CompileState, (List)] > {
    return: parseAllWithIndices(state, input, Main.foldValueChar, mapper)
};
parseParameter(state, CompileState, input, string);
[CompileState, Parameter];
{
    if (input.isBlank()) {
        return new Tuple2Impl(state, new Whitespace());
    }
    return parseDefinition(state, input).map((tuple) => new Tuple2Impl(tuple[0](), tuple[1]())).orElseGet(() => new Tuple2Impl(state, new Placeholder(input)));
}
createIndent(depth, number);
string;
{
    return "\n" + "\t".repeat(depth);
}
parseDefinitionStatement(input, string, depth, number, state, CompileState);
Option < [CompileState, IncompleteClassSegment] > {
    return: suffix(input.strip(), ";", (withoutEnd) => {
        return parseDefinition(state, withoutEnd).map((result) => {
            let definition = result[1]();
            return new Tuple2Impl(result[0](), new ClassDefinition(definition, depth));
        });
    })
};
parseDefinition(state, CompileState, input, string);
Option < [CompileState, Definition] > {
    return: last(input.strip(), " ", (beforeName, name) => {
        return split(() => toLast(beforeName, " ", Main.foldTypeSeparator), (beforeType, type) => {
            return suffix(beforeType.strip(), ">", (withoutTypeParamStart) => {
                return first(withoutTypeParamStart, "<", (beforeTypeParams, typeParamsString) => {
                    let typeParams = parseValuesOrEmpty(state, typeParamsString, (state1, s) => new Some(new Tuple2Impl(state1, s.strip())));
                    return assembleDefinition(typeParams[0](), new Some(beforeTypeParams), name, typeParams[1](), type);
                });
            }).or(() => {
                return assembleDefinition(state, new Some(beforeType), name, Lists.empty(), type);
            });
        }).or(() => {
            return assembleDefinition(state, new None(), name, Lists.empty(), beforeName);
        });
    })
};
toLast(input, string, separator, string, folder, (arg0, arg1) => DivideState);
Option < [string, string] > {
    let, divisions: (List) = divideAll(input, folder),
    return: divisions.removeLast().map((removed) => {
        let left = removed[0]().iterate().collect(new Joiner(separator)).orElse("");
        let right = removed[1]();
        return new Tuple2Impl(left, right);
    })
};
foldTypeSeparator(state, DivideState, c, string);
DivideState;
{
    if (c ===  /*  ' '  */ && state.isLevel()) {
        return state.advance();
    }
    let appended = state.append(c);
    if (c ===  /*  '<' */) {
        return appended.enter();
    }
    if (c ===  /*  '>' */) {
        return appended.exit();
    }
    return appended;
}
assembleDefinition(state, CompileState, beforeTypeParams, (Option), name, string, typeParams, (List), type, string);
Option < [CompileState, Definition] > {
    return: parseType(state.withTypeParams(typeParams), type).map((type1) => {
        let node = new ImmutableDefinition(beforeTypeParams, name.strip(), type1[1](), typeParams);
        return new Tuple2Impl(type1[0](), node);
    })
};
foldValueChar(state, DivideState, c, string);
DivideState;
{
    if (c ===  /*  ','  */ && state.isLevel()) {
        return state.advance();
    }
    let appended = state.append(c);
    if (c === /*  ' */ - /* ' */) {
        let peeked = appended.peek();
        if (peeked ===  /*  '>' */) {
            return appended.popAndAppendToOption().orElse(appended);
        }
        else {
            return appended;
        }
    }
    if (c ===  /*  '<'  */ || c ===  /*  '('  */ || c ===  /*  '{' */) {
        return appended.enter();
    }
    if (c ===  /*  '>'  */ || c ===  /*  ')'  */ || c ===  /*  '}' */) {
        return appended.exit();
    }
    return appended;
}
parseType(state, CompileState, input, string);
Option < [CompileState, Type] > {
    let, stripped = input.strip(),
    if(stripped) { }, : .equals("int") || stripped.equals("Integer")
};
{
    return new Some(new Tuple2Impl(state, Primitive.Int));
}
if (stripped.equals("String") || stripped.equals("char") || stripped.equals("Character")) {
    return new Some(new Tuple2Impl(state, Primitive.String));
}
if (stripped.equals("var")) {
    return new Some(new Tuple2Impl(state, Primitive.Unknown));
}
if (stripped.equals("boolean")) {
    return new Some(new Tuple2Impl(state, Primitive.Boolean));
}
if (isSymbol(stripped)) {
    if ( /* state.resolveType(stripped) instanceof Some */( /* var resolved */)) {
        return new Some(new Tuple2Impl(state));
    }
    else {
        return new Some(new Tuple2Impl(state, new Placeholder(stripped)));
    }
}
return parseTemplate(state, input).or(() => varArgs(state, input));
varArgs(state, CompileState, input, string);
Option < [CompileState, Type] > {
    return: suffix(input, "...", (s) => {
        return parseType(state, s).map((inner) => {
            let newState = inner[0]();
            let child = inner[1]();
            return new Tuple2Impl(newState, new ArrayType(child));
        });
    })
};
assembleTemplate(base, string, state, CompileState, arguments, (List));
[CompileState, Type];
{
    let children = arguments.iterate().map(Main.retainType).flatMap(Iterators.fromOption).collect(new ListCollector());
    if (base.equals("BiFunction")) {
        return new Tuple2Impl(state, new FunctionType(Lists.of(children.get(0).orElse( /* null */), children.get(1).orElse( /* null */)), children.get(2).orElse( /* null */)));
    }
    if (base.equals("Function")) {
        return new Tuple2Impl(state, new FunctionType(Lists.of(children.get(0).orElse( /* null */)), children.get(1).orElse( /* null */)));
    }
    if (base.equals("Predicate")) {
        return new Tuple2Impl(state, new FunctionType(Lists.of(children.get(0).orElse( /* null */)), Primitive.Boolean));
    }
    if (base.equals("Supplier")) {
        return new Tuple2Impl(state, new FunctionType(Lists.empty(), children.get(0).orElse( /* null */)));
    }
    if (base.equals("Tuple2") && children.size() >= 2) {
        return new Tuple2Impl(state, new TupleType(children));
    }
    if (state.resolveType(base)._variant === OptionVariant.Some) {
        let baseType = /* some */ .value;
        if (baseType._variant === Variant.FindableType) {
            return new Tuple2Impl(state, new Template(children));
        }
    }
    return new Tuple2Impl(state, new Template(new Placeholder(base), children));
}
parseTemplate(state, CompileState, input, string);
Option < [CompileState, Type] > {
    return: suffix(input.strip(), ">", (withoutEnd) => {
        return first(withoutEnd, "<", (base, argumentsString) => {
            let strippedBase = base.strip();
            return parseValues(state, argumentsString, Main.argument).map((argumentsTuple) => {
                return assembleTemplate(strippedBase, argumentsTuple[0](), argumentsTuple[1]());
            });
        });
    })
};
retainType(argument, Argument);
Option < Type > {
    if(argument) { }, : ._variant === ArgumentVariant.Type
};
{
    return new Some(type);
}
{
    return new None();
}
argument(state, CompileState, input, string);
Option < [CompileState, Argument] > {
    if(input) { }, : .isBlank()
};
{
    return new Some(new Tuple2Impl(state, new Whitespace()));
}
return parseType(state, input).map((tuple) => new Tuple2Impl(tuple[0](), tuple[1]()));
last(input, string, infix, string, mapper, (arg0, arg1) => Option);
Option < T > {
    return: infix(input, infix, Main.findLast, mapper)
};
findLast(input, string, infix, string);
Option < number > {
    let, index = input.lastIndexOf(infix),
    if(index) { }
} === -1;
{
    return new None();
}
return new Some(index);
first(input, string, infix, string, mapper, (arg0, arg1) => Option);
Option < T > {
    return: infix(input, infix, Main.findFirst, mapper)
};
split(splitter, () => Option, splitMapper, (arg0, arg1) => Option);
Option < T > {
    return: splitter().flatMap((splitTuple) => splitMapper(splitTuple[0](), splitTuple[1]()))
};
infix(input, string, infix, string, locator, (arg0, arg1) => Option, mapper, (arg0, arg1) => Option);
Option < T > {
    return: split(() => locator(input, infix).map((index) => {
        let left = input.substring(0, index);
        let right = input.substring(index + infix.length());
        return new Tuple2Impl(left, right);
    }), mapper)
};
findFirst(input, string, infix, string);
Option < number > {
    let, index = input.indexOf(infix),
    if(index) { }
} === -1;
{
    return new None();
}
return new Some(index);
generatePlaceholder(input, string);
string;
{
    let replaced = input.replace("/*", "content-start").replace("*/", "content-end");
    return "/* " + replaced + " */";
}
createDebugString(type, Type);
string;
{
    if (!Main.isDebug) {
        return "";
    }
    return generatePlaceholder(": " + type.generate());
}
/*  */ 
