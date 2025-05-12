"use strict";
{
    return this;
}
/* private static */ class None {
    map(mapper) {
        return new None();
    }
}
{
    return false;
}
orElse(other, T);
T;
{
    return other;
}
filter(predicate, (arg0) => boolean);
Option < T > {
    return: new None()
};
orElseGet(supplier, () => T);
T;
{
    return supplier();
}
or(other, () => Option);
Option < T > {
    return: other.get()
};
flatMap(mapper, (arg0) => Option);
Option < R > {
    return: new None()
};
isEmpty: boolean;
{
    return true;
}
and(other, () => Option);
Option < [T, R] > {
    return: new None()
};
/* private */ class Tuple2Impl {
}
(left, right) => ;
{
}
/* private */ class Some {
}
(value) => ;
{
}
map(mapper, (arg0) => R);
Option < R > {
    return: new Some(mapper(this.value))
};
isPresent: boolean;
{
    return true;
}
orElse(other, T);
T;
{
    return this.value;
}
filter(predicate, (arg0) => boolean);
Option < T > {
    if(predicate) { }
}(this.value);
{
    return this;
}
return new None();
orElseGet(supplier, () => T);
T;
{
    return this.value;
}
or(other, () => Option);
Option < T > {
    return: this
};
flatMap(mapper, (arg0) => Option);
Option < R > {
    return: mapper(this.value)
};
isEmpty: boolean;
{
    return false;
}
and(other, () => Option);
Option < [T, R] > {
    return: other.get().map((otherValue) => new Tuple2Impl(this.value, otherValue))
};
/* private static */ class SingleHead {
    constructor(value) {
        this.value = value;
        this.retrieved = false;
    }
}
{
    if (this.retrieved) {
        return new None();
    }
    this.retrieved = true;
    return new Some(this.value);
}
/* private static */ class EmptyHead {
}
{
    return new None();
}
/* private */ class HeadedIterator {
}
(head) => ;
{
}
fold(initial, R, folder, (arg0, arg1) => R);
R;
{
    let current = initial;
    while (true) {
        let finalCurrent = current;
        let optional = this.head.next().map((inner) => folder(finalCurrent, inner));
        if (optional.isPresent()) {
            current = optional.orElse( /* null */);
        }
        /* else */ {
            return current;
        }
    }
}
map(mapper, (arg0) => R);
Iterator < R > {
    return: new HeadedIterator(() => this.head.next().map(mapper))
};
collect(collector, (Collector));
R;
{
    return this.fold(collector.createInitial(), collector.fold);
}
filter(predicate, (arg0) => boolean);
Iterator < T > {
    return: this.flatMap((element) => {
        if (predicate(element)) {
            return new HeadedIterator(new SingleHead(element));
        }
        return new HeadedIterator(new EmptyHead());
    })
};
next: Option < T > {
    return: this.head.next()
};
flatMap(f, (arg0) => Iterator);
Iterator < R > {
    return: new HeadedIterator(new /* FlatMapHead */ (this.head, f))
};
zip(other, (Iterator));
Iterator < [T, R] > {
    return: new HeadedIterator(() => HeadedIterator.this.head.next().and(other.next))
};
/* private static */ class RangeHead /*  */ {
}
this.length = length;
next: Option < number > {
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
JVMList: /* public */ {
    /* this(new ArrayList<>()) */ ;
}
addLast(element, T);
List < T > {
    return: this
};
iterate: Iterator < T > {
    return: this.iterateWithIndices().map(Tuple2.right)
};
removeLast: Option < [(List), T] > {
    : .elements.isEmpty()
};
{
    return new None();
}
let slice = this.elements.subList(0, this.elements.size() - 1);
let last = this.elements.getLast();
return new Some(new Tuple2Impl(new JVMList(slice), last));
size: number;
{
    return this.elements.size();
}
isEmpty: boolean;
{
    return this.elements.isEmpty();
}
addFirst(element, T);
List < T > {
    return: this
};
iterateWithIndices: Iterator < [number, T] > {
    return: new HeadedIterator(new RangeHead(this.elements.size())).map((index) => new Tuple2Impl(index, this.elements.get(index)))
};
removeFirst: Option < [T, (List)] > {
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
last: Option < T > {
    : .elements.isEmpty()
};
{
    return new None();
}
return new Some(this.elements.getLast());
get(index, number);
Option < T > {
    if() { } /* index < this */, /* index < this */ : /* index < this */ .elements.size()
};
{
    return new Some(this.elements.get(index));
}
/* else */ {
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
/* private */ class Definition /*  */ {
    constructor() { }
}
(maybeBefore, name, type, typeParams) => ;
{
}
createSimpleDefinition(name, string, type, Type);
Definition;
{
    return new Definition(new None(), name, type, Lists.empty());
}
generate: string;
{
    return this.generateWithParams("");
}
generateType: string;
{
    if (this.type(Unknown)) {
        return "";
    }
    return " : " + this.type.generate();
}
joinBefore: string;
{
    return !.maybeBefore.filter((value) => !value.isEmpty()).map(generatePlaceholder).map((inner) => inner + " ").orElse("");
}
joinTypeParams: string;
{
    return this.typeParams().collect(new /* Joiner */ ()).map((inner) => "<" + inner + ">").orElse("");
}
mapType(mapper, (arg0) => Type);
Definition;
{
    return new Definition(this.maybeBefore, this.name, mapper(this.type), this.typeParams);
}
toString: string;
{
    return "Definition[" + "maybeBefore=" + this.maybeBefore + ", " + "name=" + this.name + ", " + "type=" + this.type + ", " + "typeParams=" + this.typeParams +  /*  ']' */;
}
generateWithParams(joinedParameters, string);
string;
{
    let joined = this.joinTypeParams();
    let before = this.joinBefore();
    let typeString = this.generateType();
    return before + this.name + joined + joinedParameters + typeString;
}
createDefinition(paramTypes, (List));
Definition;
{
    return Definition.createSimpleDefinition(this.name, new /* FunctionType */ (paramTypes, this.type));
}
/* private */ class ObjectType /*  */ {
    constructor() { }
}
(name, typeParams, definitions) => ;
{
}
generate: string;
{
    return this.name;
}
replace(mapping, (Map));
Type;
{
    return new ObjectType(this.name, this.typeParams, this.definitions.iterate().map((definition) => definition.mapType((type) => type(mapping))).collect(new /* ListCollector */ ()));
}
find(name, string);
Option < Type > {
    return: this.definitions.iterate().filter((definition) => definition.name.equals(name)).map(Definition.type).next()
};
/* private */ class TypeParam /*  */ {
    constructor() { }
}
(value) => ;
{
}
generate: string;
{
    return this.value;
}
replace(mapping, (Map));
Type;
{
    return mapping.find(this.value).orElse(this);
}
/* private */ class CompileState /*  */ {
    constructor() { }
}
(structures, definitions, objectTypes, structNames, typeParams, typeRegister) => ;
{
}
CompileState: /* public */ {
    /* this(Lists.empty(), Lists.empty(), Lists.empty(), Lists.empty(), Lists.empty(), new None<>()) */ ;
}
resolveValue(name, string);
Option < Type > {
    if(name) { }, : .equals("this")
};
{
    return new Some(new ObjectType(name, this.typeParams, this.definitions));
}
return this.definitions.iterate().filter((definition) => definition.name.equals(name)).next().map(Definition.type);
addStructure(structure, string);
CompileState;
{
    return new CompileState(this.structures.addLast(structure), this.definitions, this.objectTypes, this.structNames, this.typeParams, this.typeRegister);
}
withDefinitions(definitions, (List));
CompileState;
{
    return new CompileState(this.structures, this.definitions.addAllLast(definitions), this.objectTypes, this.structNames, this.typeParams, this.typeRegister);
}
resolveType(name, string);
Option < Type > {
    : .structNames.last().filter((inner) => inner.equals(name)).isPresent()
};
{
    return new Some(new ObjectType(name, this.typeParams, this.definitions));
}
let maybeTypeParam = this.typeParams().filter((param) => param.equals(name)).next();
if ( /* maybeTypeParam instanceof Some */( /* var value */)) {
    return new Some(new TypeParam(value));
}
return this.objectTypes.iterate().filter((type) => type.name.equals(name)).next().map((type) => type);
addType(type, ObjectType);
CompileState;
{
    return new CompileState(this.structures, this.definitions, this.objectTypes.addLast(type), this.structNames, this.typeParams, this.typeRegister);
}
withDefinition(definition, Definition);
CompileState;
{
    return new CompileState(this.structures, this.definitions.addLast(definition), this.objectTypes, this.structNames, this.typeParams, this.typeRegister);
}
pushStructName(name, string);
CompileState;
{
    return new CompileState(this.structures, this.definitions, this.objectTypes, this.structNames.addLast(name), this.typeParams, this.typeRegister);
}
withTypeParams(typeParams, (List));
CompileState;
{
    return new CompileState(this.structures, this.definitions, this.objectTypes, this.structNames, this.typeParams(typeParams), this.typeRegister);
}
withExpectedType(type, Type);
CompileState;
{
    return new CompileState(this.structures, this.definitions, this.objectTypes, this.structNames, this.typeParams, new Some(type));
}
popStructName: CompileState;
{
    return new CompileState(this.structures, this.definitions, this.objectTypes, this.structNames.removeLast().map(Tuple2.left).orElse(this.structNames), this.typeParams, this.typeRegister);
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
advance: DivideState;
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
enter: DivideState;
{
    /* this.depth++ */ ;
    return this;
}
isLevel: boolean;
{
    return this.depth === 0;
}
exit: DivideState;
{
    /* this.depth-- */ ;
    return this;
}
isShallow: boolean;
{
    return this.depth === 1;
}
pop: Option < [string, DivideState] > {
    if() { } /* this.index < this */, /* this.index < this */ : /* this.index < this */ .input.length()
};
{
    let c = this.input.charAt(this.index);
    return new Some(new Tuple2Impl(c, new DivideState(this.input, this.index + 1, this.segments, this.buffer, this.depth)));
}
return new None();
popAndAppendToTuple: Option < [string, DivideState] > {
    return: this.pop().map((tuple) => {
        let c = tuple[0]();
        let right = tuple[1]();
        return new Tuple2Impl(c, right(c));
    })
};
popAndAppendToOption: Option < DivideState > {
    return: this.popAndAppendToTuple().map(Tuple2.right)
};
peek: string;
{
    return this.input.charAt(this.index);
}
/* private */ class Joiner /*  */ {
    constructor() { }
}
(delimiter) => ;
{
}
Joiner: /* private */ {
    /* this("") */ ;
}
createInitial: Option < string > {
    return: new None()
};
fold(current, (Option), element, string);
Option < string > {
    return: new Some(current.map((inner) => inner + this.delimiter + element).orElse(element))
};
/* private static */ class ListCollector {
}
{
    return Lists.empty();
}
fold(current, (List), element, T);
List < T > {
    return: current.addLast(element)
};
/* private static */ class FlatMapHead {
}
this.mapper = mapper;
this.current = new None();
this.head = head;
next: Option < R > {
    while() {
        if (this.current.isPresent()) {
            let inner = this.current.orElse( /* null */);
            let maybe = inner.next();
            if (maybe.isPresent()) {
                return maybe;
            }
            /* else */ {
                this.current = new None();
            }
        }
        let outer = this.head.next();
        if (outer.isPresent()) {
            this.current = outer.map(this.mapper);
        }
        /* else */ {
            return new None();
        }
    }
};
/* private */ class ArrayType /*  */ {
    constructor() { }
}
(right) => ;
{
}
generate: string;
{
    return this.right().generate() + "[]";
}
replace(mapping, (Map));
Type;
{
    return this;
}
/* private static */ class Whitespace /*  */ {
}
/* private static */ class Iterators /*  */ {
    fromOption(option) {
        let single = option.map(SingleHead.new);
        return new HeadedIterator(single.orElseGet(EmptyHead.new));
    }
}
/* private */ class FunctionType /*  */ {
    constructor() { }
}
(arguments, returns) => ;
{
}
generate: string;
{
    let joined = this.arguments().iterateWithIndices().map((pair) => "arg" + pair.left() + " : " + pair.right().generate()).collect(new Joiner(", ")).orElse("");
    return "(" + joined + ") => " + this.returns.generate();
}
replace(mapping, (Map));
Type;
{
    return new FunctionType(this.arguments.iterate().map((type) => type(mapping)).collect(new ListCollector()), this.returns);
}
/* private */ class TupleType /*  */ {
    constructor() { }
}
(arguments) => ;
{
}
generate: string;
{
    let joinedArguments = this.arguments.iterate().map(Type.generate).collect(new Joiner(", ")).orElse("");
    return "[" + joinedArguments + "]";
}
replace(mapping, (Map));
Type;
{
    return this;
}
/* private */ class Template /*  */ {
    constructor() { }
}
(base, arguments) => ;
{
}
generate: string;
{
    let joinedArguments = this.arguments.iterate().map(Type.generate).collect(new Joiner(", ")).map((inner) => "<" + inner + ">").orElse("");
    return this.base.generate() + joinedArguments;
}
typeParams: List < string > {
    return: this.base.typeParams()
};
find(name, string);
Option < Type > {
    return: this.base.find(name).map((found) => {
        let mapping = this.base.typeParams().iterate().zip(this.arguments.iterate()).collect(new /* MapCollector */ ());
        return found.replace(mapping);
    })
};
/* private */ class Placeholder /*  */ {
    constructor() { }
}
(input) => ;
{
}
generate: string;
{
    return /* generatePlaceholder */ (this.input);
}
type: Type;
{
    return /* Primitive */ .Unknown;
}
typeParams: List < string > {
    return: Lists.empty()
};
find(name, string);
Option < Type > {
    return: new None()
};
/* private */ class StringValue /*  */ {
    constructor() { }
}
(stripped) => ;
{
}
generate: string;
{
    return this.stripped;
}
type: Type;
{
    return /* Primitive */ .Unknown;
}
/* private */ class DataAccess /*  */ {
    constructor() { }
}
(parent, property, type) => ;
{
}
generate: string;
{
    return this.parent.generate() + "." + this.property + /* createDebugString */ (this.type);
}
type: Type;
{
    return this.type;
}
/* private */ class ConstructionCaller /*  */ {
    constructor() { }
}
(type) => ;
{
}
generate: string;
{
    return "new " + this.type.generate();
}
toFunction: FunctionType;
{
    return new FunctionType(Lists.empty(), this.type);
}
/* private */ class Operation /*  */ {
    constructor() { }
}
(left, operator /* Operator */, right) => ;
{
}
generate: string;
{
    return this.left().generate() + " " + this.operator.targetRepresentation + " " + this.right().generate();
}
type: Type;
{
    return /* Primitive */ .Unknown;
}
/* private */ class Not /*  */ {
    constructor() { }
}
(value) => ;
{
}
generate: string;
{
    return "!" + this.value.generate();
}
type: Type;
{
    return /* Primitive */ .Unknown;
}
/* private */ class BlockLambdaValue /*  */ {
    constructor() { }
}
(right, depth) => ;
{
}
generate: string;
{
    return "{" + this.right() + createIndent(this.depth) + "}";
}
/* private */ class Lambda /*  */ {
    constructor() { }
}
(parameters, body) => ;
{
}
generate: string;
{
    let joined = this.parameters.iterate().map(Definition.generate).collect(new Joiner(", ")).orElse("");
    return "(" + joined + ") => " + this.body.generate();
}
type: Type;
{
    return /* Primitive */ .Unknown;
}
/* private */ class Invokable /*  */ {
    constructor() { }
}
(caller, arguments, type) => ;
{
}
generate: string;
{
    let joined = this.arguments.iterate().map(Value.generate).collect(new Joiner(", ")).orElse("");
    return this.caller.generate() + "(" + joined + ")" + /* createDebugString */ (this.type);
}
/* private */ class IndexValue /*  */ {
    constructor() { }
}
(parent, child) => ;
{
}
generate: string;
{
    return this.parent.generate() + "[" + this.child.generate() + "]";
}
type: Type;
{
    return /* Primitive */ .Unknown;
}
/* private */ class SymbolValue /*  */ {
    constructor() { }
}
(stripped, type) => ;
{
}
generate: string;
{
    return this.stripped + /* createDebugString */ (this.type);
}
/* private */ class JVMMap {
}
(map) => ;
{
}
JVMMap: /* public */ {
    /* this(new HashMap<>()) */ ;
}
find(key, K);
Option < V > {
    : .map(key)
};
{
    return new Some(this.map(key));
}
return new None();
with (key)
    : K, value;
V;
Map < K, V > {
    return: this
};
/* private static */ class Maps /*  */ {
    empty() {
        return new JVMMap();
    }
}
/* private */ class MapCollector {
    constructor() {
    }
}
{
    return Maps.empty();
}
fold(current, (Map), element, [K, V]);
Map < K, V > {
    return: current.with(element.left(), element.right())
};
/* private static */ class ConstructorHeader /*  */ {
    createDefinition(paramTypes) {
        return Definition.createSimpleDefinition("new", Unknown);
    }
    generateWithParams(joinedParameters) {
        return "constructor " + joinedParameters;
    }
}
/* private */ class Primitive /*  */ {
    constructor(value) {
        this.value = value;
    }
}
{
    return this.value;
}
replace(mapping, (Map));
Type;
{
    return this;
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
}
{
    return this.value;
}
type: Type;
{
    return Primitive.Boolean;
}
/* public */ class Main /*  */ {
}
/* try */ {
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
/* catch (IOException | InterruptedException e) */ {
    /* throw new RuntimeException(e) */ ;
}
compile(input, string);
string;
{
    let tuple = /* compileStatements */ (new CompileState(), input, Main.compileRootSegment);
    let joined = tuple[0]().structures.iterate().collect(new Joiner()).orElse("");
    return joined + tuple.right();
}
compileStatements(state, CompileState, input, string, mapper, (arg0, arg1) => [CompileState, string]);
[CompileState, string];
{
    let parsed = /* parseStatements */ (state, input, mapper);
    return new Tuple2Impl(parsed.left(), /* generateStatements */ (parsed.right()));
}
generateStatements(statements, (List));
string;
{
    return /* generateAll */ (Main.mergeStatements, statements);
}
parseStatements(state, CompileState, input, string, mapper, (arg0, arg1) => [CompileState, string]);
[CompileState, (List)];
{
    return /* parseAll0 */ (state, input, Main.foldStatementChar, mapper);
}
generateAll(merger, (arg0, arg1) => string, elements, (List));
string;
{
    return elements.iterate().fold("", merger);
}
parseAll0(state, CompileState, input, string, folder, (arg0, arg1) => DivideState, mapper, (arg0, arg1) => [CompileState, T]);
[CompileState, (List)];
{
    return /* getCompileStateListTuple */ (state, input, folder, (state1, s) => new Some(mapper(state1, s))).orElseGet(() => new Tuple2Impl(state, Lists.empty()));
}
getCompileStateListTuple(state, CompileState, input, string, folder, (arg0, arg1) => DivideState, mapper, (arg0, arg1) => Option);
Option < [CompileState, (List)] > {
    return /* parseAll */(state, input, folder) { }
}(state1, tuple);
mapper(state1, tuple[1]());
;
parseAll(state, CompileState, input, string, folder, (arg0, arg1) => DivideState, mapper, (arg0, arg1) => Option);
Option < [CompileState, (List)] > {
    let, initial: (Option) = new Some(new Tuple2Impl(state, Lists.empty())),
    return /* divideAll */(input, folder) { }, : .iterateWithIndices().fold(initial, (tuple, element) => {
        return tuple.flatMap((inner) => {
            let state1 = inner.left();
            let right = inner.right();
            return mapper(state1, element).map((applied) => {
                return new Tuple2Impl(applied.left(), right(applied.right()));
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
    let, current = new DivideState(input),
    while() {
        let maybePopped = current.pop().map((tuple) => {
            return /* foldSingleQuotes */ (tuple).or(() => /* foldDoubleQuotes */ (tuple)).orElseGet(() => folder(tuple[1](), tuple[0]()));
        });
        if (maybePopped.isPresent()) {
            current = maybePopped.orElse(current);
        }
        /* else */ {
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
        return append();
    }
    if (c ===  /*  '}'  */ && append.isShallow()) {
        return append().exit();
    }
    if (c ===  /*  '{'  */ || c ===  /*  '(' */) {
        return append();
    }
    if (c ===  /*  '}'  */ || c ===  /*  ')' */) {
        return append();
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
    return /* compileClass */ (stripped, 0, state).orElseGet(() => new Tuple2Impl(state, /* generatePlaceholder */ (stripped)));
}
compileClass(stripped, string, depth, number, state, CompileState);
Option < [CompileState, string] > {};
compileStructure(stripped, string, sourceInfix, string, targetInfix, string, state, CompileState);
Option < [CompileState, string] > {
    return: first(stripped, sourceInfix, (beforeInfix, right) => {
        return first(right, "{", (beforeContent, withEnd) => {
            return /* suffix */ (withEnd.strip(), "}", (content1) => {
                return /* getOr */ (targetInfix, state, beforeInfix, beforeContent, content1);
            });
        });
    })
};
getOr(targetInfix, string, state, CompileState, beforeInfix, string, beforeContent, string, content1, string);
Option < [CompileState, string] > {
    return: first(beforeContent, " implements ", (s, s2) => {
        return /* structureWithMaybeExtends */ (targetInfix, state, beforeInfix, s, content1);
    }).or(() => {
        return /* structureWithMaybeExtends */ (targetInfix, state, beforeInfix, beforeContent, content1);
    })
};
structureWithMaybeExtends(targetInfix, string, state, CompileState, beforeInfix, string, beforeContent, string, content1, string);
Option < [CompileState, string] > {
    return: first(beforeContent, " extends ", (s, s2) => {
        return /* structureWithMaybeParams */ (targetInfix, state, beforeInfix, s, content1);
    }).or(() => {
        return /* structureWithMaybeParams */ (targetInfix, state, beforeInfix, beforeContent, content1);
    })
};
structureWithMaybeParams(targetInfix, string, state, CompileState, beforeInfix, string, beforeContent, string, content1, string);
Option < [CompileState, string] > {
    return /* suffix */(beforeContent) { }, : .strip(), ")": ,
}(s);
{
    return first(s, "(", (s1, s2) => {
        let parsed = /* parseParameters */ (state, s2);
        return /* getOred */ (targetInfix, parsed.left(), beforeInfix, s1, content1, parsed.right());
    });
}
or(() => {
    return /* getOred */ (targetInfix, state, beforeInfix, beforeContent, content1, Lists.empty());
});
getOred(targetInfix, string, state, CompileState, beforeInfix, string, beforeContent, string, content1, string, params, (List));
Option < [CompileState, string] > {
    return: first(beforeContent, "<", (name, withTypeParams) => {
        return first(withTypeParams, ">", (typeParamsString, afterTypeParams) => {
            let mapper = (state1, s) => new Tuple2Impl(state1, s.strip());
            let typeParams = /* parseValuesOrEmpty */ (state, typeParamsString, (state1, s) => new Some(mapper(state1, s)));
            return /* assembleStructure */ (typeParams(), targetInfix, beforeInfix, name, content1, typeParams(), afterTypeParams, params);
        });
    }).or(() => {
        return /* assembleStructure */ (state, targetInfix, beforeInfix, beforeContent, content1, Lists.empty(), "", params);
    })
};
assembleStructure(state, CompileState, targetInfix, string, beforeInfix, string, rawName, string, content, string, typeParams, (List), afterTypeParams, string, params, (List));
Option < [CompileState, string] > {
    let, name = rawName.strip(),
    if() { }
} /* isSymbol */(name);
{
    return new None();
}
let joinedTypeParams = typeParams().collect(new Joiner(", ")).map((inner) => "<" + inner + ">").orElse("");
let statementsTuple = parseStatements(state.pushStructName(name).withTypeParams(typeParams), content, (state0, input) => /* compileClassSegment */ (state0, input, 1));
/* List<String> parsed1 */ ;
if (params.isEmpty()) {
    statementsTuple.right();
}
/* else */ {
    let joined = /* joinValues */ ( /* retainDefinitions */(params));
    let constructorIndent = /* createIndent */ (1);
    statementsTuple.right().addFirst(constructorIndent + "constructor (" + joined + ") {" + constructorIndent + "}\n");
}
let parsed2 = /* parsed1 */ .iterate().collect(new Joiner()).orElse("");
let generated = /* generatePlaceholder */ (beforeInfix.strip()) + targetInfix + name + joinedTypeParams + /* generatePlaceholder */ (afterTypeParams) + " {" + parsed2 + "\n}\n";
return new Some(new Tuple2Impl(statementsTuple.left().popStructName().addStructure(generated).addType(new ObjectType(name, typeParams, statementsTuple.left().definitions)), ""));
retainDefinition(parameter, Parameter);
Option < Definition > {
    if(parameter) { }, : ._variant === ParameterVariant.Definition
};
{
    return new Some(definition);
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
compileClassSegment(state, CompileState, input, string, depth, number);
[CompileState, string];
{
    return /* compileWhitespace */ (input, state).or(() => compileClass(input, depth, state)).or(() => compileStructure(input, "interface ", "interface ", state)).or(() => compileStructure(input, "record ", "class ", state)).or(() => compileStructure(input, "enum ", "class ", state)).or(() => /* compileMethod */ (state, input, depth)).or(() => /* compileDefinitionStatement */ (input, depth, state)).orElseGet(() => new Tuple2Impl(state, /* generatePlaceholder */ (input)));
}
compileWhitespace(input, string, state, CompileState);
Option < [CompileState, string] > {
    if(input) { }, : .isBlank()
};
{
    return new Some(new Tuple2Impl(state, ""));
}
return new None();
compileMethod(state, CompileState, input, string, depth, number);
Option < [CompileState, string] > {
    return: first(input, "(", (definitionString, withParams) => {
        return first(withParams, ")", (parametersString, rawContent) => {
            return /* parseDefinition(state, definitionString).<Tuple2<CompileState, Header>>map */ ((tuple) => new Tuple2Impl(tuple[0](), tuple[1]())).or(() => /* parseConstructor */ (state, definitionString)).flatMap((definitionTuple) => {
                let definitionState = definitionTuple.left();
                let header = definitionTuple.right();
                let parametersTuple = /* parseParameters */ (definitionState, parametersString);
                let rawParameters = parametersTuple.right();
                let parameters = /* retainDefinitions */ (rawParameters);
                let joinedParameters = /* joinValues */ (parameters);
                let content = rawContent.strip();
                let indent = /* createIndent */ (depth);
                let paramTypes = parameters.iterate().map(Definition.type).collect(new ListCollector());
                let toDefine = header.createDefinition(paramTypes);
                let generatedHeader = header.generateWithParams(joinedParameters);
                if (content.equals(";")) {
                    return new Some(new Tuple2Impl(parametersTuple.left().withDefinition(toDefine), indent + generatedHeader + ";"));
                }
                if (content.startsWith("{") && content.endsWith("}")) {
                    let substring = content.substring(1, content.length() - 1);
                    let statementsTuple = /* compileFunctionSegments */ (parametersTuple.left().withDefinitions(parameters), substring, depth);
                    let generated = indent + generatedHeader + " {" + statementsTuple.right() + indent + "}";
                    return new Some(new Tuple2Impl(statementsTuple.left().withDefinition(toDefine), generated));
                }
                return new None();
            });
        });
    })
};
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
    return retainParameters.iterate().map(Definition.generate).collect(new Joiner(", ")).map((inner) => "(" + inner + ")").orElse("");
}
retainDefinitions(right, (List));
List < Definition > {
    return: right().map(Main.retainDefinition).flatMap(Iterators.fromOption).collect(new ListCollector())
};
parseParameters(state, CompileState, params, string);
[CompileState, (List)];
{
    return /* parseValuesOrEmpty */ (state, params, (state1, s) => new Some(/* compileParameter */ (state1, s)));
}
compileFunctionSegments(state, CompileState, input, string, depth, number);
[CompileState, string];
{
    return compileStatements(state, input, (state1, input1) => /* compileFunctionSegment */ (state1, input1, depth + 1));
}
compileFunctionSegment(state, CompileState, input, string, depth, number);
[CompileState, string];
{
    let stripped = input.strip();
    if (stripped.isEmpty()) {
        return new Tuple2Impl(state, "");
    }
    return /* compileFunctionStatement */ (state, depth, stripped).or(() => /* compileBlock */ (state, depth, stripped)).orElseGet(() => new Tuple2Impl(state, /* generatePlaceholder */ (stripped)));
}
compileFunctionStatement(state, CompileState, depth, number, stripped, string);
Option < [CompileState, string] > {
    return: suffix(stripped, ";", (s) => {
        let tuple = /* compileStatementValue */ (state, s, depth);
        return new Some(new Tuple2Impl(tuple[0](), /* createIndent */ (depth) + tuple[1]() + ";"));
    })
};
compileBlock(state, CompileState, depth, number, stripped, string);
Option < [CompileState, string] > {
    return: suffix(stripped, "}", (withoutEnd) => {
        return /* split */ (() => /* toFirst */ (withoutEnd), (beforeContent, content) => {
            return suffix(beforeContent, "{", (s) => {
                let compiled = compileFunctionSegments(state, content, depth);
                let indent = /* createIndent */ (depth);
                let headerTuple = /* compileBlockHeader */ (state, s, depth);
                let headerState = headerTuple.left();
                let header = headerTuple.right();
                return new Some(new Tuple2Impl(headerState, indent + header + "{" + compiled.right() + indent + "}"));
            });
        });
    })
};
toFirst(input, string);
Option < [string, string] > {
    let, divisions = divideAll(input, Main.foldBlockStart),
    return: divisions.removeFirst().map((removed) => {
        let right = removed.left();
        let left = removed.right().iterate().collect(new Joiner("")).orElse("");
        return new Tuple2Impl(right, left);
    })
};
compileBlockHeader(state, CompileState, input, string, depth, number);
[CompileState, string];
{
    let stripped = input.strip();
    return /* compileConditional */ (state, stripped, "if", depth).or(() => /* compileConditional */ (state, stripped, "while", depth)).orElseGet(() => new Tuple2Impl(state, /* generatePlaceholder */ (stripped)));
}
compileConditional(state, CompileState, input, string, prefix, string, depth, number);
Option < Tuple2Impl < CompileState, string >> {
    return: prefix(input, prefix, (withoutPrefix) => {
        return prefix(withoutPrefix.strip(), "(", (withoutValueStart) => {
            return suffix(withoutValueStart, ")", (value) => {
                let compiled = /* compileValue */ (state, value, depth);
                return new Some(new Tuple2Impl(compiled.left(), prefix + " (" + compiled.right() + ")"));
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
compileStatementValue(state, CompileState, input, string, depth, number);
[CompileState, string];
{
    let stripped = input.strip();
    if (stripped.startsWith("return ")) {
        let value = stripped.substring("return ".length());
        let tuple = /* compileValue */ (state, value, depth);
        return new Tuple2Impl(tuple[0](), "return " + tuple.right());
    }
    return first(stripped, "=", (beforeEquals, valueString) => {
        let sourceTuple = /* compileValue */ (state, valueString, depth);
        let sourceState = sourceTuple.left();
        let sourceString = sourceTuple.right();
        return /* parseDefinition */ (sourceState, beforeEquals).flatMap((definitionTuple) => {
            let definitionState = definitionTuple.left();
            let definition = definitionTuple.right();
            return new Some(new Tuple2Impl(definitionState.withDefinition(definition), "let " + definition.generate() + " = " + sourceString));
        }).or(() => {
            let destinationTuple = /* compileValue */ (sourceState, beforeEquals, depth);
            let destinationState = destinationTuple.left();
            let destinationString = destinationTuple.right();
            return new Some(new Tuple2Impl(destinationState, destinationString + " = " + sourceString));
        });
    }).orElseGet(() => {
        return new Tuple2Impl(state, /* generatePlaceholder */ (stripped));
    });
}
compileValue(state, CompileState, input, string, depth, number);
[CompileState, string];
{
    let tuple = /* parseValue */ (state, input, depth);
    return new Tuple2Impl(tuple[0](), tuple[1]().generate());
}
parseValue(state, CompileState, input, string, depth, number);
[CompileState, Value];
{
    return /* parseBoolean */ (state, input).or(() => /* parseLambda */ (state, input, depth)).or(() => /* parseString */ (state, input)).or(() => /* parseDataAccess */ (state, input, depth)).or(() => /* parseSymbolValue */ (state, input)).or(() => /* parseInvokable */ (state, input, depth)).or(() => /* parseDigits */ (state, input)).or(() => /* parseOperation */ (state, input, depth, /* Operator */ .ADD)).or(() => /* parseOperation */ (state, input, depth, /* Operator */ .EQUALS)).or(() => /* parseOperation */ (state, input, depth, /* Operator */ .SUBTRACT)).or(() => /* parseOperation */ (state, input, depth, /* Operator */ .AND)).or(() => /* parseOperation */ (state, input, depth, /* Operator */ .OR)).or(() => /* parseOperation */ (state, input, depth,  /*  Operator.GREATER_THAN_OR_EQUALS */)).or(() => /* parseNot */ (state, input, depth)).or(() => /* parseMethodReference */ (state, input, depth)).or(() => /* parseInstanceOf */ (state, input, depth)).orElseGet(() => new Tuple2Impl(state, new Placeholder(input)));
}
parseBoolean(state, CompileState, input, string);
Option < [CompileState, Value] > {
    let, stripped = input.strip(),
    if(stripped) { }, : .equals("false")
};
{
    return new Some(new Tuple2Impl(state, False));
}
if (stripped.equals("true")) {
    return new Some(new Tuple2Impl(state, True));
}
return new None();
parseInstanceOf(state, CompileState, input, string, depth, number);
Option < [CompileState, Value] > {
    return: last(input, "instanceof", (s, s2) => {
        let childTuple = parseValue(state, s, depth);
        return /* parseDefinition */ (childTuple.left(), s2).map((definitionTuple) => {
            let value = childTuple.right();
            let definition = definitionTuple.right();
            let variant = new DataAccess(value, "_variant") /* Primitive */.Unknown;
        });
        let temp = new SymbolValue(value.type().generate() + "Variant." + definition.type.generate()) /* Primitive */.Unknown;
    }),
    return: new Tuple2Impl(definitionTuple.left(), new Operation(variant) /* Operator */.EQUALS, temp)
};
;
;
parseMethodReference(state, CompileState, input, string, depth, number);
Option < [CompileState, Value] > {
    return: last(input, "::", (s, s2) => {
        let tuple = parseValue(state, s, depth);
        return new Some(new Tuple2Impl(tuple[0](), new DataAccess(tuple[1](), s2) /* Primitive */.Unknown));
    })
};
;
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
            let type = /* Primitive */ .Unknown;
            if ( /* state.typeRegister instanceof Some */( /* var expectedType */)) {
                if ( /* expectedType */._variant === unknownVariant.FunctionType) {
                    type = /* functionType */ .arguments.get(0).orElse( /* null */);
                }
            }
            return /* assembleLambda */ (state, Lists.of(Definition.createSimpleDefinition(strippedBeforeArrow, type)), valueString, depth);
        }
        if (strippedBeforeArrow.startsWith("(") && strippedBeforeArrow.endsWith(")")) {
            let parameterNames = divideAll(strippedBeforeArrow.substring(1, strippedBeforeArrow.length() - 1), Main.foldValueChar).iterate().map() /* String */.strip;
        }
    }).filter((value) => !value.isEmpty()).map((name) => Definition.createSimpleDefinition(name) /* Primitive */.Unknown), : .collect(new ListCollector())
};
return new None();
;
assembleLambda(state, CompileState, definitions, (List), valueString, string, depth, number);
Some < [CompileState, Value] > {
    let, strippedValueString = valueString.strip(),
    let, state2 = state.withDefinitions(definitions),
    if(strippedValueString) { }, : .startsWith("{") && strippedValueString.endsWith("}")
};
{
    let value1 = compileStatements(state2, strippedValueString.substring(1, strippedValueString.length() - 1), (state1, input1) => compileFunctionSegment(state1, input1, depth + 1));
    let right = value1.right();
    value = new Tuple2Impl(value1.left(), new BlockLambdaValue(right, depth));
}
/* else */ {
    let value1 = parseValue(state2, strippedValueString, depth);
    value = new Tuple2Impl(value1.left(), value1.right());
}
let right = value.right();
return new Some(new Tuple2Impl(value.left(), new Lambda(definitions, right)));
parseDigits(state, CompileState, input, string);
Option < [CompileState, Value] > {
    let, stripped = input.strip(),
    if() { }
} /* isNumber */(stripped);
{
    return new Some(new Tuple2Impl(state, new SymbolValue(stripped, Int)));
}
return new None();
isNumber(input, string);
boolean;
{
    /* String maybeTruncated */ ;
    if (input.startsWith("-")) {
        input.substring(1);
    }
    /* else */ {
        input;
    }
    return /* areAllDigits */ ( /* maybeTruncated */);
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
        return /* split */ (() => /* toLast */ (withoutEnd, "", Main.foldInvocationStart), (callerWithEnd, argumentsString) => {
            return suffix(callerWithEnd, "(", (callerString) => {
                return /* assembleInvokable */ (state, depth, argumentsString, callerString.strip());
            });
        });
    })
};
assembleInvokable(state, CompileState, depth, number, argumentsString, string, callerString, string);
Some < [CompileState, Value] > {
    let, callerTuple = (state, depth, callerString),
    let, oldCallerState = callerTuple.left(),
    let, oldCaller = callerTuple.right(),
    let, newCaller = (oldCallerState, oldCaller),
    let, callerType = (newCaller),
    let, argumentsTuple = (oldCallerState, argumentsString, (currentState, pair) => {
        let index = pair.left();
        let element = pair.right();
        let expectedType = callerType.arguments.get(index).orElse() /* Primitive */.Unknown;
        let withExpected = currentState.withExpectedType(expectedType);
        let valueTuple = /* parseArgument */ (withExpected, element, depth);
        let valueState = valueTuple.left();
        let value = valueTuple.right();
        let actualType = valueTuple.left().typeRegister.orElse() /* Primitive */.Unknown;
        return new Some(new Tuple2Impl(valueState, new Tuple2Impl(value, actualType)));
    }).orElseGet(() => new Tuple2Impl(oldCallerState, Lists.empty())),
    let, argumentsState = argumentsTuple.left(),
    let, argumentsWithActualTypes = argumentsTuple.right(),
    let, arguments = argumentsWithActualTypes.iterate().map(Tuple2.left).map(Main.retainValue).flatMap(Iterators.fromOption).collect(new ListCollector()),
    let, invokable = new Invokable(newCaller, arguments, callerType.returns),
    return: new Some(new Tuple2Impl(argumentsState, invokable))
};
retainValue(argument, Argument);
Option < Value > {
    if(argument) { }, : ._variant === ArgumentVariant.Value
};
{
    return new Some(value);
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
    let callerType = new FunctionType(Lists.empty(), Unknown);
    /* switch (newCaller) */ {
        /* case ConstructionCaller constructionCaller -> */ {
            callerType = /* constructionCaller */ .toFunction();
        }
        /* case Value value -> */ {
            let type = value.type();
            if (type._variant === ())
                () => TypeVariant.FunctionType;
            {
                callerType =  /* functionType */;
            }
        }
    }
    return callerType;
}
modifyCaller(state, CompileState, oldCaller, Caller);
Caller;
{
    if (oldCaller._variant === unknownVariant.DataAccess) {
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
        let map = /* parseType */ (state, input1).map((type) => {
            let right = type();
            return new Tuple2Impl(type(), new ConstructionCaller(right));
        });
        if (map()) {
            return map( /* null */);
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
        if (enter()) {
            return enter();
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
                return new Some(new Tuple2Impl(state, new IndexValue(parent, new SymbolValue("0") /* Primitive */.Int)));
            }
        }
    })
};
if (property.equals("right")) {
    return new Some(new Tuple2Impl(state, new IndexValue(parent, new SymbolValue("1", Int))));
}
let type = /* Primitive */ .Unknown;
if (parentType._variant === unknownVariant.FindableType) {
    if ( /* objectType.find(property) instanceof Some */( /* var memberType */)) {
        type =  /* memberType */;
    }
}
return new Some(new Tuple2Impl(tuple[0](), new DataAccess(parent, property, type)));
;
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
        let rightTuple = parseValue(leftTuple.left(), rightString, depth);
        let left = leftTuple.right();
        let right = rightTuple.right();
        return new Some(new Tuple2Impl(rightTuple.left(), new Operation(left, operator, right)));
    })
};
parseValuesOrEmpty(state, CompileState, input, string, mapper, (arg0, arg1) => Option);
[CompileState, (List)];
{
    return /* parseValues */ (state, input, mapper).orElseGet(() => new Tuple2Impl(state, Lists.empty()));
}
parseValues(state, CompileState, input, string, mapper, (arg0, arg1) => Option);
Option < [CompileState, (List)] > {
    return /* parseValuesWithIndices */(state, input) { }
}(state1, tuple);
mapper(state1, tuple[1]());
;
parseValuesWithIndices(state, CompileState, input, string, mapper, (arg0, arg1) => Option);
Option < [CompileState, (List)] > {
    return: parseAll(state, input, Main.foldValueChar, mapper)
};
compileParameter(state, CompileState, input, string);
[CompileState, Parameter];
{
    if (input.isBlank()) {
        return new Tuple2Impl(state, new Whitespace());
    }
    return /* parseDefinition */ (state, input).map((tuple) => new Tuple2Impl(tuple[0](), tuple[1]())).orElseGet(() => new Tuple2Impl(state, new Placeholder(input)));
}
compileDefinition(state, CompileState, input, string);
[CompileState, string];
{
    return /* parseDefinition */ (state, input).map((tuple) => new Tuple2Impl(tuple[0](), tuple[1]().generate())).orElseGet(() => new Tuple2Impl(state, /* generatePlaceholder */ (input)));
}
createIndent(depth, number);
string;
{
    return "\n" + "\t".repeat(depth);
}
compileDefinitionStatement(input, string, depth, number, state, CompileState);
Option < [CompileState, string] > {
    return: suffix(input.strip(), ";", (withoutEnd) => {
        return /* parseDefinition */ (state, withoutEnd).map((result) => {
            let generated = createIndent(depth) + result.right().generate() + ";";
            return new Tuple2Impl(result.left(), generated);
        });
    })
};
parseDefinition(state, CompileState, input, string);
Option < [CompileState, Definition] > {
    return: last(input.strip(), " ", (beforeName, name) => {
        return /* split */ (() => /* toLast */ (beforeName, " ", Main.foldTypeSeparator), (beforeType, type) => {
            return suffix(beforeType.strip(), ">", (withoutTypeParamStart) => {
                return first(withoutTypeParamStart, "<", (beforeTypeParams, typeParamsString) => {
                    let compileStateStringTupleBiFunction = (state1, s) => new Tuple2Impl(state1, s.strip());
                    let typeParams = parseValuesOrEmpty(state, typeParamsString, (state1, s) => new Some(compileStateStringTupleBiFunction(state1, s)));
                    return /* assembleDefinition */ (typeParams(), new Some(beforeTypeParams), name, typeParams(), type);
                });
            }).or(() => {
                return /* assembleDefinition */ (state, new Some(beforeType), name, Lists.empty(), type);
            });
        }).or(() => {
            return /* assembleDefinition */ (state, new None(), name, Lists.empty(), beforeName);
        });
    })
};
toLast(input, string, separator, string, folder, (arg0, arg1) => DivideState);
Option < [string, string] > {
    let, divisions = divideAll(input, folder),
    return: divisions.removeLast().map((removed) => {
        let left = removed.left().iterate().collect(new Joiner(separator)).orElse("");
        let right = removed.right();
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
    return /* parseType */(state) { }, : .withTypeParams(typeParams), type, : .map((type1) => {
        let node = new Definition(beforeTypeParams, name.strip(), type1.right(), typeParams);
        return new Tuple2Impl(type1.left(), node);
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
        /* else */ {
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
compileType(state, CompileState, input, string);
Option < [CompileState, string] > {
    return /* parseType */(state, input) { }, : .map((tuple) => new Tuple2Impl(tuple[0](), tuple[1]().generate()))
};
parseType(state, CompileState, input, string);
Option < [CompileState, Type] > {
    let, stripped = input.strip(),
    if(stripped) { }, : .equals("int") || stripped.equals("Integer")
};
{
    return new Some(new Tuple2Impl(state, Int));
}
if (stripped.equals("String") || stripped.equals("char") || stripped.equals("Character")) {
    return new Some(new Tuple2Impl(state, String));
}
if (stripped.equals("var")) {
    return new Some(new Tuple2Impl(state, Unknown));
}
if (stripped.equals("boolean")) {
    return new Some(new Tuple2Impl(state, Boolean));
}
if (isSymbol(stripped)) {
    if ( /* state.resolveType(stripped) instanceof Some */( /* var resolved */)) {
        return new Some(new Tuple2Impl(state));
    }
    /* else */ {
        return new Some(new Tuple2Impl(state, new Placeholder(stripped)));
    }
}
return /* parseTemplate */ (state, input).or(() => /* varArgs */ (state, input));
varArgs(state, CompileState, input, string);
Option < [CompileState, Type] > {
    return: suffix(input, "...", (s) => {
        return parseType(state, s).map((inner) => {
            let newState = inner.left();
            let child = inner.right();
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
        return new Tuple2Impl(state, new FunctionType(Lists.of(children.get(0).orElse( /* null */)), Boolean));
    }
    if (base.equals("Supplier")) {
        return new Tuple2Impl(state, new FunctionType(Lists.empty(), children.get(0).orElse( /* null */)));
    }
    if (base.equals("Tuple2") && children.size() >= 2) {
        return new Tuple2Impl(state, new TupleType(children));
    }
    if (state.resolveType(base)._variant === Option < Type > (Variant.Some)) {
        let baseType = /* some */ .value;
        if (baseType._variant === unknownVariant.FindableType) {
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
                return assembleTemplate(strippedBase, argumentsTuple.left(), argumentsTuple.right());
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
/* else */ {
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
