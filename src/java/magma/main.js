"use strict";
/* private static */ class None {
    /* @Override
        public  */ map(mapper) {
        return new None();
    }
    /* @Override
        public */ isPresent() {
        return /* false */;
    }
    /* @Override
        public */ orElse(other) {
        return other;
    }
    /* @Override
        public */ filter(predicate) {
        return new None();
    }
    /* @Override
        public */ orElseGet(supplier) {
        return supplier();
    }
    /* @Override
        public */ or(other) {
        return other.get();
    }
    /* @Override
        public  */ flatMap(mapper) {
        return new None();
    }
    /* @Override
        public */ isEmpty() {
        return /* true */;
    }
    /* @Override
        public  */ and(other) {
        return new None();
    }
}
/* private */ class Some {
    constructor(value) {
    }
    /* @Override
        public  */ map(mapper) {
        return new Some(mapper(value));
    }
    /* @Override
        public */ isPresent() {
        return /* true */;
    }
    /* @Override
        public */ orElse(other) {
        return /* this */ .value;
    }
    /* @Override
        public */ filter(predicate) {
        /* if (predicate.test(this.value))  */ {
            return /* this */;
        }
        return new None();
    }
    /* @Override
        public */ orElseGet(supplier) {
        return /* this */ .value;
    }
    /* @Override
        public */ or(other) {
        return /* this */;
    }
    /* @Override
        public  */ flatMap(mapper) {
        return mapper(value);
    }
    /* @Override
        public */ isEmpty() {
        return /* false */;
    }
    /* @Override
        public  */ and(other) {
        return other.get().map((otherValue) => new /* Tuple */ ( /* this */.value, otherValue));
    }
}
/* private static */ class SingleHead {
    constructor() {
        this.let /* this.value  */ = value;
        this.let /* this.retrieved  */ = ;
    }
}
/* @Override
    public */ next();
Option < T > {
/* if (this.retrieved)  */ };
/* if (this.retrieved)  */ {
    return new None();
}
let /* this.retrieved  */ =  /* true */;
return new Some(value);
/* private static */ class EmptyHead {
    /* @Override
        public */ next() {
        return new None();
    }
}
/* private */ class HeadedIterator {
    constructor(head) {
    }
    /* @Override
        public  */ fold(initial, folder) {
        let current = initial;
        /* while (true)  */ {
            let finalCurrent =  /* current */;
            let optional = /* this */ .head.next().map((inner) => folder(inner));
            /* if (optional.isPresent())  */ {
                let /* current  */ = /* optional */ .orElse( /* null */);
            }
            /* else  */ {
                return /* current */;
            }
        }
    }
    /* @Override
        public  */ map(mapper) {
        return new HeadedIterator(() => /* this */ .head.next().map(mapper));
    }
    /* @Override
        public  */ collect(collector) {
        return /* this */ .fold(collector.createInitial(), collector.fold);
    }
    /* @Override
        public */ filter(predicate) {
        return /* this */ .flatMap((element) => {
            /* if (predicate.test(element))  */ {
                return new HeadedIterator(new SingleHead(element));
            }
            return new HeadedIterator(new EmptyHead());
        });
    }
    /* @Override
        public */ next() {
        return /* this */ .head.next();
    }
    /* @Override
        public  */ flatMap(f) {
        return new HeadedIterator(new /* FlatMapHead */ ( /* this */.head, f));
    }
    /* @Override
        public  */ zip(other) {
        return new HeadedIterator(() => /* HeadedIterator */ .this.head.next().and(other.next));
    }
}
/* private static */ class RangeHead /*  */ {
    constructor() {
        this.let /* this.length  */ = length;
    }
}
/* @Override
    public */ next();
Option < number > {
/* if (this.counter < this.length)  */ };
/* if (this.counter < this.length)  */ {
    let value = /* this */ .counter;
    /* this.counter++ */ ;
    return new Some(value);
}
return new None();
/* private static final */ class JVMList {
    constructor() {
        this.let /* this.elements  */ = elements;
    }
}
JVMList();
{
    /* this(new ArrayList<>()) */ ;
}
/* @Override
        public */ addLast(element, T);
List < T > {
    return:  /* this */
};
/* @Override
        public */ iterate();
Iterator < T > {
    return /* this */: /* this */ .iterateWithIndices().map() /* Tuple */.right
};
/* @Override
        public */ removeLast();
Option < [(List), T] > {
/* if (this.elements.isEmpty())  */ };
/* if (this.elements.isEmpty())  */ {
    return new None();
}
let slice = /* this */ .elements.subList(0, elements.size() - 1);
let last = /* this */ .elements.getLast();
return new Some(new [(List), T](new JVMList( /* slice */)));
/* @Override
        public */ size();
number;
{
    return /* this */ .elements.size();
}
/* @Override
        public */ isEmpty();
boolean;
{
    return /* this */ .elements.isEmpty();
}
/* @Override
        public */ addFirst(element, T);
List < T > {
    return:  /* this */
};
/* @Override
        public */ iterateWithIndices();
Iterator < [number, T] > {
    return: new HeadedIterator(new RangeHead() /* this */.elements.size()), : .map((index) => new /* Tuple */ (index, /* this */ .elements.get(index)))
};
/* @Override
        public */ removeFirst();
Option < [T, (List)] > {
/* if (this.elements.isEmpty())  */ };
/* if (this.elements.isEmpty())  */ {
    return new None();
}
let first = /* this */ .elements.getFirst();
let slice = /* this */ .elements.subList(1, elements.size());
return new Some(new [T, (List)](new JVMList( /* slice */)));
/* @Override
        public */ addAllLast(others, (List));
List < T > {
    let, initial: (List) =  /* this */,
    return: others.iterate().fold(initial) /* List */.addLast
};
/* @Override
        public */ get(index, number);
Option < T > {
/* if (index < this.elements.size())  */ };
/* if (index < this.elements.size())  */ {
    return new Some(elements.get(index));
}
/* else  */ {
    return new None();
}
/* private static */ class Lists /*  */ {
    /* public static  */ empty() {
        return new JVMList();
    }
    /* public static  */ of(elements) {
        return new JVMList(new /* ArrayList */ ( /* Arrays */.asList(elements)));
    }
}
/* private */ class ObjectType /*  */ {
    constructor(name, typeParams, definitions) {
    }
    /* @Override
        public */ generate() {
        return /* this */ .name;
    }
}
return new ObjectType(name, typeParams, definitions.iterate().map((definition) => definition.mapType((type) => type.replace(mapping))).collect(new /* ListCollector */ ()));
/* @Override
    public */ find(name, string);
Option <  /* Type */ > {
    return /* this */: /* this */ .definitions.iterate().filter((definition) => definition.name.equals(name)).map() /* Definition */.type, : .next()
};
/* private */ class TypeParam /*  */ {
    constructor(value) {
    }
    /* @Override
        public */ generate() {
        return /* this */ .value;
    }
    /* @Override
        public */ replace(mapping) {
        return mapping.get(value);
    }
}
/* private static */ class DivideState /*  */ {
    constructor() {
        this.let /* this.segments  */ = segments;
        this.let /* this.buffer  */ = buffer;
        this.let /* this.depth  */ = depth;
        this.let /* this.input  */ = input;
        this.let /* this.index  */ = index;
    }
}
DivideState(input, string);
{
    /* this(input, 0, Lists.empty(), new StringBuilder(), 0) */ ;
}
/* private */ advance();
DivideState;
{
    let /* this.segments  */ = /* this */ .segments.addLast(buffer.toString());
    let /* this.buffer  */ = new /* StringBuilder */ ();
    return /* this */;
}
/* private */ append(c);
DivideState;
{
    /* this.buffer.append(c) */ ;
    return /* this */;
}
/* public */ enter();
DivideState;
{
    /* this.depth++ */ ;
    return /* this */;
}
/* public */ isLevel();
boolean;
{
    return /* this.depth == 0 */;
}
/* public */ exit();
DivideState;
{
    /* this.depth-- */ ;
    return /* this */;
}
/* public */ isShallow();
boolean;
{
    return /* this.depth == 1 */;
}
/* public */ pop();
Option < [/* Character */ , DivideState] > {
/* if (this.index < this.input.length())  */ };
/* if (this.index < this.input.length())  */ {
    let c = /* this */ .input.charAt(index);
    return new Some(new /* Tuple */ (c, new DivideState(input, index + 1, segments, buffer, depth)));
}
return new None();
/* public */ popAndAppendToTuple();
Option < [/* Character */ , DivideState] > {
    return /* this */: /* this */ .pop().map((tuple) => new /* Tuple */ (tuple.left, tuple.right.append(tuple.left)))
};
/* public */ popAndAppendToOption();
Option < DivideState > {
    return /* this */: /* this */ .popAndAppendToTuple().map() /* Tuple */.right
};
/* public */ peek();
{
    return /* this */ .input.charAt(index);
}
/* private */ class Joiner /*  */ {
    constructor(delimiter) {
    }
    /* @Override
        public */ createInitial() {
        return new None();
    }
    /* @Override
        public */ fold(current, element) {
        return new Some(current.map((inner) => inner + /* this */ .delimiter + element).orElse(element));
    }
}
/* private */ class Definition /*  */ {
    constructor(maybeBefore, name, type /* Type */, typeParams) {
    }
    /* private */ generate() {
        return /* this */ .generateWithParams("");
    }
    /* public */ generateWithParams(params) {
        let joined = /* this */ .joinTypeParams();
        let before = /* this */ .joinBefore();
        let typeString = /* this */ .generateType();
        return /* before */ +.name + /* joined */ +params +  /* typeString */;
    }
    /* private */ generateType() {
        /* if (this.type.equals(Primitive.Unknown))  */ {
            return "";
        }
        return " : " + /* this */ .type.generate();
    }
    /* private */ joinBefore() {
        return /* this */ .maybeBefore.filter((value) => !value.isEmpty()).map(generatePlaceholder).map((inner) => inner + " ").orElse("");
    }
    /* private */ joinTypeParams() {
        return /* this */ .typeParams.iterate().collect(new Joiner()).map((inner) => "<" + inner + ">").orElse("");
    }
    /* public */ mapType(mapper /* Type */) {
        return new Definition(maybeBefore, name, mapper(type), typeParams);
    }
}
/* private static */ class ListCollector {
    /* @Override
        public */ createInitial() {
        return /* Lists */ .empty();
    }
    /* @Override
        public */ fold(current, element) {
        return current.addLast(element);
    }
}
/* private */ class Tuple {
}
/* private static */ class FlatMapHead {
    constructor() {
        this.let /* this.mapper  */ = mapper;
        this.let /* this.current  */ = new None();
        this.let /* this.head  */ = head;
    }
}
/* @Override
    public */ next();
Option < R > {
/* while (true)  */ };
/* while (true)  */ {
    /* if (this.current.isPresent())  */ {
        let inner = /* this */ .current.orElse( /* null */);
        let maybe = inner.next();
        /* if (maybe.isPresent())  */ {
            return /* maybe */;
        }
        /* else  */ {
            let /* this.current  */ = new None();
        }
    }
    let outer = /* this */ .head.next();
    /* if (outer.isPresent())  */ {
        let /* this.current  */ = /* outer */ .map(mapper);
    }
    /* else  */ {
        return new None();
    }
}
/* private */ class ArrayType /*  */ {
    constructor(right /* Type */) {
    }
    /* @Override
        public */ generate() {
        return /* this */ .right.generate() + "[]";
    }
}
/* private static */ class Whitespace /*  */ {
}
/* private static */ class Iterators /*  */ {
    /* public static  */ fromOption(option) {
        let single = option.map(SingleHead.new);
        return new HeadedIterator(orElseGet(new ));
    }
}
/* private */ class FunctionType /*  */ {
    constructor(arguments, returns /* Type */) {
    }
    /* @Override
        public */ generate() {
        let joined = /* this */ .arguments().iterateWithIndices().map((pair) => "arg" + pair.left + " : " + pair.right.generate()).collect(new Joiner(", ")).orElse("");
        return "(" + /* joined */ +") => " + /* this */ .returns.generate();
    }
}
return new FunctionType(arguments.iterate().map((type) => type.replace(mapping)).collect(new ListCollector()), returns);
/* private */ class TupleType /*  */ {
    constructor(arguments) {
    }
    /* @Override
        public */ generate() {
        let joinedArguments = /* this */ .arguments.iterate().map(generate).collect(new Joiner(", ")).orElse("");
        return "[" + joinedArguments + "]";
    }
}
/* private */ class Template /*  */ {
    constructor(base /* FindableType */, arguments) {
    }
    /* @Override
        public */ generate() {
        let joinedArguments = /* this */ .arguments.iterate().map(generate).collect(new Joiner(", ")).map((inner) => "<" + inner + ">").orElse("");
        return /* this */ .base.generate() +  /* joinedArguments */;
    }
    /* @Override
        public */ typeParams() {
        return /* this */ .base.typeParams();
    }
    /* @Override
        public */ find(name) {
        return /* this */ .base.find(name).map((found) => {
            let mapping = /* this */ .base.typeParams().iterate().zip(arguments.iterate()).collect(new /* MapCollector */ ());
            return found.replace(mapping);
        });
    }
}
/* private */ class Placeholder /*  */ {
    constructor(input) {
    }
    /* @Override
        public */ generate() {
        return /* generatePlaceholder */ ( /* this */.input);
    }
}
return /* Primitive */ .Unknown;
/* @Override
    public */ typeParams();
List < string > {
    return /* Lists */: /* Lists */ .empty()
};
/* @Override
    public */ find(name, string);
Option <  /* Type */ > {
    return: new None()
};
/* private */ class StringValue /*  */ {
    constructor(stripped) {
    }
    /* @Override
        public */ generate() {
        return /* this */ .stripped;
    }
}
return /* Primitive */ .Unknown;
/* private */ class DataAccess /*  */ {
    constructor(parent /* Value */, property, type /* Type */) {
    }
    /* @Override
        public */ generate() {
        return /* this */ .parent.generate() + "." + /* this */ .property + /* createDebugString */ ( /* this */.type);
    }
}
return /* this */ .type;
/* private */ class ConstructionCaller /*  */ {
    constructor(type /* Type */) {
    }
    /* @Override
        public */ generate() {
        return "new " + /* this */ .type.generate();
    }
    /* public */ toFunction() {
        return new FunctionType(empty(), type);
    }
}
/* private */ class Operation /*  */ {
    constructor(left /* Value */, infix, right /* Value */) {
    }
    /* @Override
        public */ generate() {
        return /* this */ .left.generate() + " " + /* this */ .infix + " " + /* this */ .right.generate();
    }
}
return /* Primitive */ .Unknown;
/* private */ class Not /*  */ {
    constructor(value /* Value */) {
    }
    /* @Override
        public */ generate() {
        return "!" + /* this */ .value.generate();
    }
}
return /* Primitive */ .Unknown;
/* private */ class BlockLambdaValue /*  */ {
    constructor(right, depth) {
    }
    /* @Override
        public */ generate() {
        return "{" + this.right + createIndent(this.depth) + "}";
    }
}
/* private */ class Lambda /*  */ {
    constructor(parameters, body) {
    }
    /* @Override
        public */ generate() {
        let joined = /* this */ .parameters.iterate().map(Definition.generate).collect(new Joiner(", ")).orElse("");
        return "(" + /* joined */ +") => " + /* this */ .body.generate();
    }
}
return /* Primitive */ .Unknown;
/* private */ class Invokable /*  */ {
    constructor(caller, arguments, type /* Type */) {
    }
    /* @Override
        public */ generate() {
        let joined = /* this */ .arguments.iterate().map(generate).collect(new Joiner(", ")).orElse("");
        return /* this */ .caller.generate() + "(" + /* joined */ +")" + /* createDebugString */ ( /* this */.type);
    }
}
/* private */ class IndexValue /*  */ {
    constructor(parent /* Value */, child /* Value */) {
    }
    /* @Override
        public */ generate() {
        return /* this */ .parent.generate() + "[" + this.child.generate() + "]";
    }
}
return /* Primitive */ .Unknown;
/* private */ class SymbolValue /*  */ {
    constructor(stripped, type /* Type */) {
    }
    /* @Override
        public */ generate() {
        return /* this */ .stripped + /* createDebugString */ ( /* this */.type);
    }
}
/* private */ class MapCollector {
    constructor() {
    }
}
return new /* HashMap */ ();
/* @Override
    public */ fold(current, /* Map */ , V > , element, [K, V]);
, V > {
    return: current
};
/* public */ class Main /*  */ {
    /* private */ CompileState(structures, definitions, objectTypes, maybeStructName, typeParams, typeRegister) {
        /* this(Lists.empty(), Lists.empty(), Lists.empty(), new None<>(), Lists.empty(), new None<>()) */ ;
    }
}
/* private Option<Type> resolveValue(String name)  */ {
    return /* this */ .definitions.iterate().filter((definition) => definition.name.equals(name)).next().map(type);
}
/* public CompileState addStructure(String structure)  */ {
    return new /* CompileState */ ( /* this */.structures.addLast( /* structure */), /* this */ .definitions, /* this */ .objectTypes, /* this */ .maybeStructName, /* this */ .typeParams, /* this */ .typeRegister);
}
/* public CompileState withDefinitions(List<Definition> definitions)  */ {
    return new /* CompileState */ ( /* this */.structures, /* this */ .definitions.addAllLast(definitions), /* this */ .objectTypes, /* this */ .maybeStructName, /* this */ .typeParams, /* this */ .typeRegister);
}
/* public Option<Type> resolveType(String name)  */ {
    /* if (this.maybeStructName.filter(inner -> inner.equals(name)).isPresent())  */ {
        return new Some(new ObjectType(name, typeParams, definitions));
    }
    let maybeTypeParam = /* this */ .typeParams.iterate().filter((param) => param.equals(name)).next();
    /* if (maybeTypeParam instanceof Some(var value))  */ {
        return new Some(new TypeParam(value));
    }
    return /* this */ .objectTypes.iterate().filter((type) => type.name.equals(name)).next().map((type) => type);
}
/* public CompileState addType(ObjectType type)  */ {
    return new /* CompileState */ ( /* this */.structures, /* this */ .definitions, /* this */ .objectTypes.addLast(type), /* this */ .maybeStructName, /* this */ .typeParams, /* this */ .typeRegister);
}
/* public CompileState withDefinition(Definition definition)  */ {
    return new /* CompileState */ ( /* this */.structures, /* this */ .definitions.addLast(definition), /* this */ .objectTypes, /* this */ .maybeStructName, /* this */ .typeParams, /* this */ .typeRegister);
}
/* public CompileState withStructName(String name)  */ {
    return new /* CompileState */ ( /* this */.structures, /* this */ .definitions, /* this */ .objectTypes, new Some(name), /* this */ .typeParams, /* this */ .typeRegister);
}
/* public CompileState withTypeParams(List<String> typeParams)  */ {
    return new /* CompileState */ ( /* this */.structures, /* this */ .definitions, /* this */ .objectTypes, /* this */ .maybeStructName, /* this */ .typeParams.addAllLast(typeParams), /* this */ .typeRegister);
}
/* public CompileState withExpectedType(Type type)  */ {
    return new /* CompileState */ ( /* this */.structures, /* this */ .definitions, /* this */ .objectTypes, /* this */ .maybeStructName, /* this */ .typeParams, new Some(type));
}
/* public static */ main();
{
    /* try  */ {
        let parent = /* Paths */ .get(".", "src", "java", "magma");
        let source = /* parent */ .resolve("Main.java");
        let target = /* parent */ .resolve("main.ts");
        let input = /* Files */ .readString( /* source */);
        /* Files.writeString(target, compile(input)) */ ;
        /* new ProcessBuilder("cmd", "/c", "npm", "exec", "tsc")
                .inheritIO()
                .start()
                .waitFor() */ ;
    }
    /* catch (IOException | InterruptedException e)  */ {
        /* throw new RuntimeException(e) */ ;
    }
}
/* private static */ compile(input, string);
string;
{
    let tuple = /* compileStatements */ (new /* CompileState */ (), input, /* Main */ .compileRootSegment);
    let joined = tuple.left.structures.iterate().collect(new Joiner()).orElse("");
    return /* joined */ +tuple.right;
}
/* private static */ compileStatements(state, input, string, mapper, (arg0 /* CompileState */, arg1) => [/* CompileState */ , string]);
[/* CompileState */ , string];
{
    let parsed = /* parseStatements */ (state, input, mapper);
    return new Tuple(left, /* generateStatements */ ( /* parsed */.right));
}
/* private static */ generateStatements(statements, (List));
string;
{
    return /* generateAll */ ( /* Main */.mergeStatements, statements);
}
/* private static */ parseStatements(state, input, string, mapper, (arg0 /* CompileState */, arg1) => [/* CompileState */ , string]);
[/* CompileState */ , (List)];
{
    return /* parseAll0 */ (state, input, /* Main */ .foldStatementChar, mapper);
}
/* private static */ generateAll(merger, (arg0 /* StringBuilder */, arg1) =>  /* StringBuilder */, elements, (List));
string;
{
    return elements.iterate().fold(new /* StringBuilder */ (), merger).toString();
}
/* private static  */ parseAll0(state, input, string, folder, (arg0, arg1 /* Character */) => DivideState, mapper, (arg0 /* CompileState */, arg1) => [/* CompileState */ , T]);
[/* CompileState */ , (List)];
{
    return /* getCompileStateListTuple */ (state, input, folder, (state1, s) => new Some(mapper(state1, s))).orElseGet(() => new Tuple(state, empty()));
}
/* private static  */ getCompileStateListTuple(state, input, string, folder, (arg0, arg1 /* Character */) => DivideState, mapper, (arg0 /* CompileState */, arg1) => Option);
Option < [/* CompileState */ , (List)] > {
    return /* parseAll */(state, input, folder) { }
}(state1, tuple);
mapper(state1, tuple.right);
;
/* private static  */ parseAll(state, input, string, folder, (arg0, arg1 /* Character */) => DivideState, mapper, (arg0 /* CompileState */, arg1) => Option);
Option < [/* CompileState */ , (List)] > {
    let, initial: (Option) = new Some(new Tuple(state) /* Lists */.empty()),
    return /* divideAll */(input, folder) { }, : .iterateWithIndices().fold(initial, (tuple, element) => {
        return tuple.flatMap((inner) => {
            let state1 = inner.left;
            let right = inner.right;
            return mapper(state1, element).map((applied) => {
                return new Tuple(applied.left) /* right */.addLast(applied.right);
            });
        });
    })
};
;
/* private static */ mergeStatements(stringBuilder, str, string);
{
    return stringBuilder.append(str);
}
/* private static */ divideAll(input, string, folder, (arg0, arg1 /* Character */) => DivideState);
List < string > {
    let, current = new DivideState(input)
};
/* while (true)  */ {
    let maybePopped = current.pop().map((tuple) => {
        return /* foldSingleQuotes */ (tuple).or(() => /* foldDoubleQuotes */ (tuple)).orElseGet(() => folder(tuple.right, tuple.left));
    });
    /* if (maybePopped.isPresent())  */ {
        let /* current  */ = /* maybePopped */ .orElse(current);
    }
    /* else  */ {
        /* break */ ;
    }
}
return current.advance().segments;
/* private static */ foldDoubleQuotes(tuple, [/* Character */ , DivideState]);
Option < DivideState > {
/* if (tuple.left == '\"')  */ };
/* if (tuple.left == '\"')  */ {
    let current = tuple.right.append(tuple.left);
    /* while (true)  */ {
        let maybePopped = current.popAndAppendToTuple();
        /* if (maybePopped.isEmpty())  */ {
            /* break */ ;
        }
        let popped = /* maybePopped */ .orElse( /* null */);
        let /* current  */ = /* popped */ .right;
        /* if (popped.left == '\\')  */ {
            let /* current  */ = current.popAndAppendToOption().orElse(current);
        }
        /* if (popped.left == '\"')  */ {
            /* break */ ;
        }
    }
    return new Some(current);
}
return new None();
/* private static */ foldSingleQuotes(tuple, [/* Character */ , DivideState]);
Option < DivideState > {
/* if (tuple.left != '\'')  */ };
/* if (tuple.left != '\'')  */ {
    return new None();
}
let appended = tuple.right.append(tuple.left);
return /* appended */ .popAndAppendToTuple().map(foldEscaped).flatMap(DivideState.popAndAppendToOption);
/* private static */ foldEscaped(escaped, [/* Character */ , DivideState]);
DivideState;
{
    /* if (escaped.left == '\\')  */ {
        return escaped[1].popAndAppendToOption().orElse(escaped[1]);
    }
    return escaped[1];
}
/* private static */ foldStatementChar(state, DivideState, c);
DivideState;
{
    let append = state.append(c);
    /* if (c == ';' && append.isLevel())  */ {
        return append();
    }
    /* if (c == '}' && append.isShallow())  */ {
        return append().exit();
    }
    /* if (c == '{' || c == '(')  */ {
        return append();
    }
    /* if (c == '}' || c == ')')  */ {
        return append();
    }
    return append;
}
/* private static */ compileRootSegment(state, input, string);
[/* CompileState */ , string];
{
    let stripped = input.strip();
    /* if (stripped.startsWith("package ") || stripped.startsWith("import "))  */ {
        return new Tuple(state, "");
    }
    return /* compileClass */ ( /* stripped */, 0, state).orElseGet(() => new Tuple(state, /* generatePlaceholder */ ( /* stripped */)));
}
/* private static */ compileClass(stripped, string, depth, number, state);
Option < [/* CompileState */ , string] > {};
/* private static */ compileStructure(stripped, string, sourceInfix, string, targetInfix, string, state);
Option < [/* CompileState */ , string] > {
    return /* first */(stripped, sourceInfix) { }
}(beforeInfix, right);
{
    return /* first */ (right, "{", (beforeContent, withEnd) => {
        let strippedWithEnd = withEnd.strip();
        return /* suffix */ ( /* strippedWithEnd */, "}", (content1) => {
            return /* first */ (beforeContent, " implements ", (s, s2) => {
                return /* structureWithMaybeParams */ (targetInfix, state, beforeInfix, s, content1);
            }).or(() => {
                return /* structureWithMaybeParams */ (targetInfix, state, beforeInfix, beforeContent, content1);
            });
        });
    });
}
;
/* private static */ structureWithMaybeParams(targetInfix, string, state, beforeInfix, string, beforeContent, string, content1, string);
Option < [/* CompileState */ , string] > {
    return /* suffix */(beforeContent, ) { }
}(s);
{
    return /* first */ (s, "(", (s1, s2) => {
        let parsed = /* parseParameters */ (state, s2);
        return /* getOred */ (targetInfix, /* parsed */ .left, beforeInfix, s1, content1, /* parsed */ .right);
    });
}
or(() => {
    return /* getOred */ (targetInfix, state, beforeInfix, beforeContent, content1, /* Lists */ .empty());
});
/* private static */ getOred(targetInfix, string, state, beforeInfix, string, beforeContent, string, content1, string, params, (List));
Option < [/* CompileState */ , string] > {
    return /* first */(beforeContent, ) { }
}(name, withTypeParams);
{
    return /* first */ (withTypeParams, ">", (typeParamsString, afterTypeParams) => {
        let /* final */ compileStateStringTupleBiFunction = (state1, s) => new Tuple(state1, s.strip());
        let typeParams = /* parseValuesOrEmpty */ (state, typeParamsString, (state1, s) => new Some(apply(state1, s)));
        return /* assembleStructure */ (typeParams.left, targetInfix, beforeInfix, name, content1, typeParams.right, afterTypeParams, params);
    });
}
or(() => {
    return /* assembleStructure */ (state, targetInfix, beforeInfix, beforeContent, content1, /* Lists */ .empty(), "", params);
});
/* private static */ assembleStructure(state, targetInfix, string, beforeInfix, string, rawName, string, content, string, typeParams, (List), afterTypeParams, string, params, (List));
Option < [/* CompileState */ , string] > {
    let, name = rawName.strip()
};
/* if (!isSymbol(name))  */ {
    return new None();
}
let joinedTypeParams = typeParams.iterate().collect(new Joiner(", ")).map((inner) => "<" + inner + ">").orElse("");
let parsed = parseStatements(state.withStructName(name).withTypeParams(typeParams), content, (state0, input) => /* compileClassSegment */ (state0, input, 1));
/* List<String> parsed1 */ ;
/* if (params.isEmpty())  */ {
    let /* parsed1  */ = /* parsed */ .right;
}
/* else  */ {
    let joined = /* joinValues */ ( /* retainDefinitions */(params));
    let constructorIndent = /* createIndent */ (1);
    let /* parsed1  */ = /* parsed */ .right.addFirst(/* constructorIndent */ +"constructor (" + joined + ") {" + constructorIndent + "}\n");
}
let parsed2 = /* parsed1 */ .iterate().collect(new Joiner()).orElse("");
let generated = /* generatePlaceholder */ (beforeInfix.strip()) + targetInfix + name + /* joinedTypeParams */ +(afterTypeParams) + " {" + parsed2 + "\n}\n";
return new Some(new Tuple(left.addStructure( /* generated */).addType(new ObjectType(name, typeParams, left.definitions)), ""));
/* private static */ retainDefinition(parameter, Parameter);
Option < Definition > {
/* if (parameter instanceof Definition definition)  */ };
/* if (parameter instanceof Definition definition)  */ {
    return new Some(definition);
}
return new None();
/* private static */ isSymbol(input, string);
boolean;
{
    /* for (var i = 0; i < input.length(); i++)  */ {
        let c = input.charAt( /* i */);
        /* if (Character.isLetter(c) || (i != 0 && Character.isDigit(c)))  */ {
            /* continue */ ;
        }
        return /* false */;
    }
    return /* true */;
}
/* private static  */ suffix(input, string, suffix, string, mapper, (arg0) => Option);
Option < T > {
/* if (!input.endsWith(suffix))  */ };
/* if (!input.endsWith(suffix))  */ {
    return new None();
}
let slice = input.substring(0, input.length() - suffix.length());
return mapper( /* slice */);
/* private static */ compileClassSegment(state, input, string, depth, number);
[/* CompileState */ , string];
{
    return /* compileWhitespace */ (input, state).or(() => compileClass(input, depth, state)).or(() => compileStructure(input, "interface ", "interface ", state)).or(() => compileStructure(input, "record ", "class ", state)).or(() => /* compileMethod */ (state, input, depth)).or(() => /* compileDefinitionStatement */ (input, depth, state)).orElseGet(() => new Tuple(state, /* generatePlaceholder */ (input)));
}
/* private static */ compileWhitespace(input, string, state);
Option < [/* CompileState */ , string] > {
/* if (input.isBlank())  */ };
/* if (input.isBlank())  */ {
    return new Some(new Tuple(state, ""));
}
return new None();
/* private static */ compileMethod(state, input, string, depth, number);
Option < [/* CompileState */ , string] > {
    return /* first */(input, ) { }
}(definitionString, withParams);
{
    return /* first */ (withParams, ")", (parametersString, rawContent) => {
        return /* parseDefinition */ (state, definitionString).flatMap((definitionTuple) => {
            let definitionState = definitionTuple.left;
            let definition = definitionTuple.right;
            let parametersTuple = /* parseParameters */ ( /* definitionState */, parametersString);
            let rawParameters = /* parametersTuple */ .right;
            let parameters = /* retainDefinitions */ ( /* rawParameters */);
            let joinedParameters = /* joinValues */ ( /* parameters */);
            let content = rawContent.strip();
            let indent = /* createIndent */ (depth);
            let paramTypes = /* parameters */ .iterate().map(Definition.type).collect(new ListCollector());
            let toDefine = new Definition(definition.name, new FunctionType(definition.type));
            let generatedHeader = definition.generateWithParams("(" + joinedParameters + ")");
            /* if (content.equals(";"))  */ {
                return new Some(new Tuple(left.withDefinition( /* toDefine */), /* indent */ + +";"));
            }
            /* if (content.startsWith("{") && content.endsWith("}"))  */ {
                let substring = content.substring(1, content.length() - 1);
                let statementsTuple = /* compileFunctionSegments */ ( /* parametersTuple */.left.withDefinitions( /* parameters */),  /* substring */, depth);
                let generated = /* indent */ + +" {" + statementsTuple.right + indent + "}";
                return new Some(new Tuple(left.withDefinition( /* toDefine */)));
            }
            return new None();
        });
    });
}
;
/* private static */ joinValues(retainParameters, (List));
string;
{
    return retainParameters.iterate().map(Definition.generate).collect(new Joiner(", ")).orElse("");
}
/* private static */ retainDefinitions(right, (List));
List < Definition > {
    return: right.iterate().map() /* Main */.retainDefinition, : .flatMap() /* Iterators */.fromOption, : .collect(new ListCollector())
};
/* private static */ parseParameters(state, params, string);
[/* CompileState */ , (List)];
{
    return /* parseValuesOrEmpty */ (state, params, (state1, s) => new Some(/* compileParameter */ (state1, s)));
}
/* private static */ compileFunctionSegments(state, input, string, depth, number);
[/* CompileState */ , string];
{
    return compileStatements(state, input, (state1, input1) => /* compileFunctionSegment */ (state1, input1, depth + 1));
}
/* private static */ compileFunctionSegment(state, input, string, depth, number);
[/* CompileState */ , string];
{
    let stripped = input.strip();
    /* if (stripped.isEmpty())  */ {
        return new Tuple(state, "");
    }
    return suffix(stripped, ";", (s) => {
        let tuple = /* statementValue */ (state, s, depth);
        return new Some(new Tuple(tuple.left, /* createIndent */ (depth) + tuple.right + ";"));
    }).or(() => {
        return /* block */ (state, depth, stripped);
    }).orElseGet(() => {
        return new Tuple(state, /* generatePlaceholder */ (stripped));
    });
}
/* private static */ block(state, depth, number, stripped, string);
Option < [/* CompileState */ , string] > {
    return: suffix(stripped, "}", (withoutEnd) => {
        return /* split */ (() => {
            let divisions = divideAll(withoutEnd) /* Main */.foldBlockStart;
        });
        return /* divisions */ .removeFirst().map((removed) => {
            let right = removed.left;
            let left = removed.right.iterate().collect(new Joiner("")).orElse("");
            return new Tuple(right);
        });
    }, (beforeContent, content) => {
        return suffix(beforeContent, "{", (s) => {
            let compiled = compileFunctionSegments(state, content, depth);
            let indent = /* createIndent */ (depth);
            return new Some(new Tuple() /* compiled */.left, /* indent */ +(s) + "{" + compiled.right + indent + "}");
        });
    })
};
;
;
/* private static */ foldBlockStart(state, DivideState, c);
DivideState;
{
    let appended = state.append(c);
    /* if (c == '{' && state.isLevel())  */ {
        return /* appended */ .advance();
    }
    /* if (c == '{')  */ {
        return /* appended */ .enter();
    }
    /* if (c == '}')  */ {
        return /* appended */ .exit();
    }
    return /* appended */;
}
/* private static */ statementValue(state, input, string, depth, number);
[/* CompileState */ , string];
{
    let stripped = input.strip();
    /* if (stripped.startsWith("return "))  */ {
        let value = stripped.substring("return ".length());
        let tuple = /* compileValue */ (state, value, depth);
        return new Tuple(tuple.left, "return " + tuple.right);
    }
    return /* first */ (stripped, "=", (s, s2) => {
        let definitionTuple = /* compileDefinition */ (state, s);
        let valueTuple = /* compileValue */ (definitionTuple.left, s2, depth);
        return new Some(new Tuple(left, "let " + definitionTuple.right + " = " + /* valueTuple */ .right));
    }).orElseGet(() => {
        return new Tuple(state, /* generatePlaceholder */ (stripped));
    });
}
/* private static */ compileValue(state, input, string, depth, number);
[/* CompileState */ , string];
{
    let tuple = /* parseValue */ (state, input, depth);
    return new Tuple(tuple.left, tuple.right.generate());
}
/* private static */ parseValue(state, input, string, depth, number);
[/* CompileState */ , /* Value */];
{
    return /* parseLambda */ (state, input, depth).or(() => /* parseString */ (state, input)).or(() => /* parseDataAccess */ (state, input, depth)).or(() => /* parseSymbolValue */ (state, input)).or(() => /* parseInvokable */ (state, input, depth)).or(() => /* parseOperation */ (state, input, depth, "+")).or(() => /* parseOperation */ (state, input, depth, "-")).or(() => /* parseDigits */ (state, input)).or(() => /* parseNot */ (state, input, depth)).or(() => /* parseMethodReference */ (state, input, depth)).orElseGet(() => new [/* CompileState */ , /* Value */](state, new Placeholder(input)));
}
/* private static */ parseMethodReference(state, input, string, depth, number);
Option < [/* CompileState */ , /* Value */] > {
    return /* last */(input, ) { }
}(s, s2);
{
    let tuple = parseValue(state, s, depth);
    return new Some(new Tuple(tuple.left, new DataAccess(tuple.right, s2, Unknown)));
}
;
/* private static */ parseNot(state, input, string, depth, number);
Option < [/* CompileState */ , /* Value */] > {
    let, stripped = input.strip()
};
/* if (stripped.startsWith("!"))  */ {
    let slice = stripped.substring(1);
    let tuple = parseValue(state, depth);
    let value = tuple.right;
    return new Some(new Tuple(tuple.left, new Not(value)));
}
return new None();
/* private static */ parseLambda(state, input, string, depth, number);
Option < [/* CompileState */ , /* Value */] > {
    return /* first */(input, ) { }
}(beforeArrow, valueString);
{
    let strippedBeforeArrow = beforeArrow.strip();
    /* if (isSymbol(strippedBeforeArrow))  */ {
        let type = /* Primitive */ .Unknown;
        /* if (state.typeRegister instanceof Some(var expectedType))  */ {
            /* if (expectedType instanceof FunctionType functionType)  */ {
                let /* type  */ = /* functionType */ .arguments.get(0).orElse( /* null */);
            }
        }
        return /* assembleLambda */ (state, /* Lists */ .of(new Definition(type)), valueString, depth);
    }
    /* if (strippedBeforeArrow.startsWith("(") && strippedBeforeArrow.endsWith(")"))  */ {
        let parameterNames = divideAll(substring(1, length() - 1), foldValueChar).iterate().map(strip).filter((value) => !value.isEmpty()).map((name) => new Definition(name, Unknown)).collect(new ListCollector());
        return /* assembleLambda */ (state,  /* parameterNames */, valueString, depth);
    }
    return new None();
}
;
/* private static */ assembleLambda(state, definitions, (List), valueString, string, depth, number);
Some < [/* CompileState */ , /* Value */] > {
    let, strippedValueString = valueString.strip(),
    let, state2 = state.withDefinitions(definitions)
};
/* if (strippedValueString.startsWith("{") && strippedValueString.endsWith("}"))  */ {
    let value1 = compileStatements(substring(1, length() - 1), (state1, input1) => compileFunctionSegment(state1, input1, depth + 1));
    let right = /* value1 */ .right;
    let /* value  */ = new Tuple(left, new BlockLambdaValue(right, depth));
}
/* else  */ {
    let value1 = parseValue(depth);
    let /* value  */ = new Tuple(left, right);
}
let right = value.right;
return new Some(new Tuple(value.left, new Lambda(definitions, right)));
/* private static */ parseDigits(state, input, string);
Option < [/* CompileState */ , /* Value */] > {
    let, stripped = input.strip()
};
/* if (isNumber(stripped))  */ {
    return new Some(new [/* CompileState */ , /* Value */](state, new SymbolValue(stripped, Int)));
}
return new None();
/* private static */ isNumber(input, string);
boolean;
{
    /* for (var i = 0; i < input.length(); i++)  */ {
        let c = input.charAt( /* i */);
        /* if (Character.isDigit(c))  */ {
            /* continue */ ;
        }
        return /* false */;
    }
    return /* true */;
}
/* private static */ parseInvokable(state, input, string, depth, number);
Option < [/* CompileState */ , /* Value */] > {
    return: suffix(input.strip(), ")", (withoutEnd) => {
        return /* split */ (() => /* toLast */ (withoutEnd, "", /* Main */ .foldInvocationStart), (callerWithEnd, argumentsString) => {
            return suffix(callerWithEnd, "(", (callerString) => {
                return /* assembleInvokable */ (state, depth, argumentsString, callerString.strip());
            });
        });
    })
};
/* private static */ assembleInvokable(state, depth, number, argumentsString, string, callerString, string);
Some < [/* CompileState */ , /* Value */] > {
    let, callerTuple = (state, depth, callerString),
    let, oldCallerState = .left,
    let, oldCaller = .right,
    let, newCaller = ( /* oldCallerState */,  /* oldCaller */),
    let, callerType = ( /* newCaller */),
    let, argumentsTuple = ( /* oldCallerState */, argumentsString, (currentState, pair) => {
        let index = pair.left;
        let element = pair.right;
        let expectedType = /* callerType */ .arguments.get(index).orElse() /* Primitive */.Unknown;
        let withExpected = currentState.withExpectedType( /* expectedType */);
        let valueTuple = /* parseArgument */ ( /* withExpected */, element, depth);
        let valueState = /* valueTuple */ .left;
        let value = /* valueTuple */ .right;
        let actualType = /* valueTuple */ .left.typeRegister.orElse() /* Primitive */.Unknown;
        return new Some(new Tuple(new Tuple(value)));
    }).orElseGet(() => new Tuple( /* oldCallerState */) /* Lists */.empty()),
    let, argumentsState = .left,
    let, argumentsWithActualTypes = .right,
    let, arguments = .iterate().map() /* Tuple */.left, : .map() /* Main */.retainValue, : .flatMap() /* Iterators */.fromOption, : .collect(new ListCollector()),
    let, invokable = new Invokable( /* newCaller */) /* callerType */.returns,
    return: new Some(new Tuple( /* argumentsState */))
};
/* private static */ retainValue(argument, Argument);
Option <  /* Value */ > {
/* if (argument instanceof Value value)  */ };
/* if (argument instanceof Value value)  */ {
    return new Some(value);
}
return new None();
/* private static */ parseArgument(state, element, string, depth, number);
[/* CompileState */ , Argument];
{
    /* if (element.isEmpty())  */ {
        return new Tuple(state, new Whitespace());
    }
    let tuple = parseValue(state, element, depth);
    return new Tuple(tuple.left, tuple.right);
}
/* private static */ findCallerType(newCaller, Caller);
FunctionType;
{
    let callerType = new FunctionType(empty(), Unknown);
    /* switch (newCaller)  */ {
        /* case ConstructionCaller constructionCaller ->  */ {
            let /* callerType  */ = /* constructionCaller */ .toFunction();
        }
        /* case Value value ->  */ {
            let type = value.type();
            /* if (type instanceof FunctionType functionType)  */ {
                let /* callerType  */ =  /* functionType */;
            }
        }
    }
    return /* callerType */;
}
/* private static */ modifyCaller(state, oldCaller, Caller);
Caller;
{
    /* if (oldCaller instanceof DataAccess access)  */ {
        let type = /* resolveType */ ( /* access */.parent, state);
        /* if (type instanceof FunctionType)  */ {
            return /* access */ .parent;
        }
    }
    return oldCaller;
}
/* private static */ resolveType(value, state);
{
    /* return switch (value)  */ {
        /* case DataAccess dataAccess -> Primitive.Unknown */ ;
        /* case Invokable invokable -> Primitive.Unknown */ ;
        /* case Lambda lambda -> Primitive.Unknown */ ;
        /* case Not not -> Primitive.Unknown */ ;
        /* case Operation operation -> Primitive.Unknown */ ;
        /* case Placeholder placeholder -> Primitive.Unknown */ ;
        /* case StringValue stringValue -> Primitive.Unknown */ ;
        /* case SymbolValue symbolValue -> symbolValue.type */ ;
        /* case IndexValue indexValue -> Primitive.Unknown */ ;
    }
    /*  */ ;
}
/* private static */ invocationHeader(state, depth, number, callerString1, string);
[/* CompileState */ , Caller];
{
    /* if (callerString1.startsWith("new "))  */ {
        let input1 = callerString1.substring("new ".length());
        let map = /* parseType */ (state, input1).map((type) => {
            let right = type.right;
            return new [/* CompileState */ , Caller](type.left, new ConstructionCaller(right));
        });
        /* if (map.isPresent())  */ {
            return map( /* null */);
        }
    }
    let tuple = parseValue(state, callerString1, depth);
    return new Tuple(tuple.left, tuple.right);
}
/* private static */ foldInvocationStart(state, DivideState, c);
DivideState;
{
    let appended = state.append(c);
    /* if (c == '(')  */ {
        let enter = /* appended */ .enter();
        /* if (enter.isShallow())  */ {
            return enter();
        }
        return enter;
    }
    /* if (c == ')')  */ {
        return /* appended */ .exit();
    }
    return /* appended */;
}
/* private static */ parseDataAccess(state, input, string, depth, number);
Option < [/* CompileState */ , /* Value */] > {
    return /* last */(input) { }, : .strip(), ".": ,
}(parentString, rawProperty);
{
    let property = rawProperty.strip();
    /* if (!isSymbol(property))  */ {
        return new None();
    }
    let tuple = parseValue(state, parentString, depth);
    let parent = tuple.right;
    let parentType = /* parent */ .type();
    /* if (parentType instanceof TupleType)  */ {
        /* if (property.equals("left"))  */ {
            return new Some(new Tuple(state, new IndexValue(new SymbolValue("0", Int))));
        }
        /* if (property.equals("right"))  */ {
            return new Some(new Tuple(state, new IndexValue(new SymbolValue("1", Int))));
        }
    }
    let type = /* Primitive */ .Unknown;
    /* if (parentType instanceof FindableType objectType)  */ {
        /* if (objectType.find(property) instanceof Some(var memberType))  */ {
            let /* type  */ =  /* memberType */;
        }
    }
    return new Some(new Tuple(tuple.left, new DataAccess(type)));
}
;
/* private static */ parseString(state, input, string);
Option < [/* CompileState */ , /* Value */] > {
    let, stripped = input.strip()
};
/* if (stripped.startsWith("\"") && stripped.endsWith("\""))  */ {
    return new Some(new Tuple(state, new StringValue(stripped)));
}
return new None();
/* private static */ parseSymbolValue(state, value, string);
Option < [/* CompileState */ , /* Value */] > {
    let, stripped = value.strip()
};
/* if (isSymbol(stripped))  */ {
    /* if (state.resolveValue(stripped) instanceof Some(var type))  */ {
        return new Some(new Tuple(state, new SymbolValue(stripped, type)));
    }
    return new Some(new Tuple(state, new Placeholder(stripped)));
}
return new None();
/* private static */ parseOperation(state, value, string, depth, number, infix, string);
Option < [/* CompileState */ , /* Value */] > {
    return /* first */(value, infix) { }
}(s, s2);
{
    let tuple = parseValue(state, s, depth);
    let tuple1 = parseValue(tuple.left, s2, depth);
    let left = tuple.right;
    let right = /* tuple1 */ .right;
    return new Some(new Tuple(left, new Operation(infix, right)));
}
;
/* private static */ compileValues(state, params, string, mapper, (arg0 /* CompileState */, arg1) => [/* CompileState */ , string]);
[/* CompileState */ , string];
{
    let parsed = /* parseValuesOrEmpty */ (state, params, (state1, s) => new Some(mapper(state1, s)));
    let generated = /* generateValues */ ( /* parsed */.right);
    return new Tuple(left);
}
/* private static */ generateValues(elements, (List));
string;
{
    return generateAll(mergeValues, elements);
}
/* private static  */ parseValuesOrEmpty(state, input, string, mapper, (arg0 /* CompileState */, arg1) => Option);
[/* CompileState */ , (List)];
{
    return /* parseValues */ (state, input, mapper).orElseGet(() => new Tuple(state, empty()));
}
/* private static  */ parseValues(state, input, string, mapper, (arg0 /* CompileState */, arg1) => Option);
Option < [/* CompileState */ , (List)] > {
    return /* parseValuesWithIndices */(state, input) { }
}(state1, tuple);
mapper(state1, tuple.right);
;
/* private static  */ parseValuesWithIndices(state, input, string, mapper, (arg0 /* CompileState */, arg1) => Option);
Option < [/* CompileState */ , (List)] > {
    return: parseAll(state, input) /* Main */.foldValueChar, mapper
};
/* private static */ compileParameter(state, input, string);
[/* CompileState */ , Parameter];
{
    /* if (input.isBlank())  */ {
        return new Tuple(state, new Whitespace());
    }
    return /* parseDefinition */ (state, input).map((tuple) => new [/* CompileState */ , Parameter](tuple.left, tuple.right)).orElseGet(() => new Tuple(state, new Placeholder(input)));
}
/* private static */ compileDefinition(state, input, string);
[/* CompileState */ , string];
{
    return /* parseDefinition */ (state, input).map((tuple) => new Tuple(tuple.left, tuple.right.generate())).orElseGet(() => new Tuple(state, /* generatePlaceholder */ (input)));
}
/* private static */ mergeValues(cache, element, string);
{
    /* if (cache.isEmpty())  */ {
        return cache.append(element);
    }
    return cache.append(", ").append(element);
}
/* private static */ createIndent(depth, number);
string;
{
    return "\n" + "\t".repeat(depth);
}
/* private static */ compileDefinitionStatement(input, string, depth, number, state);
Option < [/* CompileState */ , string] > {
    return: suffix(input.strip(), ";", (withoutEnd) => {
        return /* parseDefinition */ (state, withoutEnd).map((result) => {
            let generated = createIndent(depth) + result.right.generate() + ";";
            return new Tuple(result.left);
        });
    })
};
/* private static */ parseDefinition(state, input, string);
Option < [/* CompileState */ , Definition] > {
    return /* last */(input) { }, : .strip(), " ": ,
}(beforeName, name);
{
    return /* split */ (() => /* toLast */ (beforeName, " ", /* Main */ .foldTypeSeparator), (beforeType, type) => {
        return suffix(beforeType.strip(), ">", (withoutTypeParamStart) => {
            return /* first */ (withoutTypeParamStart, "<", (beforeTypeParams, typeParamsString) => {
                let /* final */ compileStateStringTupleBiFunction = (state1, s) => new Tuple(state1, s.strip());
                let typeParams = parseValuesOrEmpty(state, typeParamsString, (state1, s) => new Some(apply(state1, s)));
                return /* assembleDefinition */ (typeParams.left, new Some(beforeTypeParams), name, typeParams.right, type);
            });
        }).or(() => {
            return /* assembleDefinition */ (state, new Some(beforeType), name, /* Lists */ .empty(), type);
        });
    }).or(() => {
        return /* assembleDefinition */ (state, new None(), name, /* Lists */ .empty(), beforeName);
    });
}
;
/* private static */ toLast(input, string, separator, string, folder, (arg0, arg1 /* Character */) => DivideState);
Option < [string, string] > {
    let, divisions = divideAll(input, folder),
    return /* divisions */: /* divisions */ .removeLast().map((removed) => {
        let left = removed.left.iterate().collect(new Joiner(separator)).orElse("");
        let right = removed.right;
        return new Tuple(right);
    })
};
/* private static */ foldTypeSeparator(state, DivideState, c);
DivideState;
{
    /* if (c == ' ' && state.isLevel())  */ {
        return state.advance();
    }
    let appended = state.append(c);
    /* if (c == '<')  */ {
        return /* appended */ .enter();
    }
    /* if (c == '>')  */ {
        return /* appended */ .exit();
    }
    return /* appended */;
}
/* private static */ assembleDefinition(state, beforeTypeParams, (Option), name, string, typeParams, (List), type, string);
Option < [/* CompileState */ , Definition] > {
    return /* parseType */(state) { }, : .withTypeParams(typeParams), type, : .map((type1) => {
        let node = new Definition(beforeTypeParams, name.strip(), type1.right, typeParams);
        return new Tuple(type1.left);
    })
};
/* private static */ foldValueChar(state, DivideState, c);
DivideState;
{
    /* if (c == ',' && state.isLevel())  */ {
        return state.advance();
    }
    let appended = state.append(c);
    /* if (c == '-')  */ {
        let peeked = /* appended */ .peek();
        /* if (peeked == '>')  */ {
            return /* appended */ .popAndAppendToOption().orElse( /* appended */);
        }
        /* else  */ {
            return /* appended */;
        }
    }
    /* if (c == '<' || c == '(' || c == '{')  */ {
        return /* appended */ .enter();
    }
    /* if (c == '>' || c == ')' || c == '}')  */ {
        return /* appended */ .exit();
    }
    return /* appended */;
}
/* private static */ compileType(state, input, string);
Option < [/* CompileState */ , string] > {
    return /* parseType */(state, input) { }, : .map((tuple) => new Tuple(tuple.left, tuple.right.generate()))
};
/* private static */ parseType(state, input, string);
Option < [/* CompileState */ , /* Type */] > {
    let, stripped = input.strip()
};
/* if (stripped.equals("int") || stripped.equals("Integer"))  */ {
    return new Some(new Tuple(state, Int));
}
/* if (stripped.equals("String"))  */ {
    return new Some(new Tuple(state, String));
}
/* if (stripped.equals("var"))  */ {
    return new Some(new Tuple(state, Unknown));
}
/* if (stripped.equals("boolean"))  */ {
    return new Some(new Tuple(state, Boolean));
}
/* if (isSymbol(stripped))  */ {
    /* if (state.resolveType(stripped) instanceof Some(var resolved))  */ {
        return new Some(new Tuple(state));
    }
    /* else  */ {
        return new Some(new Tuple(state, new Placeholder(stripped)));
    }
}
return /* parseTemplate */ (state, input).or(() => /* varArgs */ (state, input));
/* private static */ varArgs(state, input, string);
Option < [/* CompileState */ , /* Type */] > {
    return: suffix(input, "...", (s) => {
        return parseType(state, s).map((inner) => {
            let newState = inner.left;
            let child = inner.right;
            return new Tuple(new ArrayType( /* child */));
        });
    })
};
/* private static */ parseTemplate(state, input, string);
Option < [/* CompileState */ , /* Type */] > {
    return: suffix(input.strip(), ">", (withoutEnd) => {
        return /* first */ (withoutEnd, "<", (base, argumentsString) => {
            let strippedBase = base.strip();
            return parseValues(state, argumentsString) /* Main */.argument;
        }).map((argumentsTuple) => {
            return /* assembleTemplate */ ( /* strippedBase */, argumentsTuple[0], argumentsTuple[1]);
        });
    })
};
;
/* private static */ assembleTemplate(base, string, state, arguments, (List));
[/* CompileState */ , /* Type */];
{
    let children = arguments.iterate().map(retainType).flatMap(fromOption).collect(new ListCollector());
    /* if (base.equals("BiFunction"))  */ {
        return new Tuple(state, new FunctionType(of(get(0).orElse( /* null */), get(1).orElse( /* null */)), get(2).orElse( /* null */)));
    }
    /* if (base.equals("Function"))  */ {
        return new Tuple(state, new FunctionType(of(get(0).orElse( /* null */)), get(1).orElse( /* null */)));
    }
    /* if (base.equals("Predicate"))  */ {
        return new Tuple(state, new FunctionType(of(get(0).orElse( /* null */)), Boolean));
    }
    /* if (base.equals("Supplier"))  */ {
        return new Tuple(state, new FunctionType(empty(), get(0).orElse( /* null */)));
    }
    /* if (base.equals("Tuple") && children.size() >= 2)  */ {
        return new Tuple(state, new TupleType( /* children */));
    }
    /* if (state.resolveType(base) instanceof Some(var baseType) && baseType instanceof FindableType findableType)  */ {
        return new Tuple(state, new Template( /* findableType */));
    }
    return new Tuple(state, new Template(new Placeholder(base)));
}
/* private static */ retainType(argument, Argument);
Option <  /* Type */ > {
/* if (argument instanceof Type type)  */ };
/* if (argument instanceof Type type)  */ {
    return new Some(type);
}
/* else  */ {
    return new None();
}
/* private static */ argument(state, input, string);
Option < [/* CompileState */ , Argument] > {
/* if (input.isBlank())  */ };
/* if (input.isBlank())  */ {
    return new Some(new Tuple(state, new Whitespace()));
}
return parseType(state, input).map((tuple) => new Tuple(tuple.left, tuple.right));
/* private static  */ last(input, string, infix, string, mapper, (arg0, arg1) => Option);
Option < T > {
    return: infix(input, infix) /* Main */.findLast, mapper
};
/* private static */ findLast(input, string, infix, string);
Option < number > {
    let, index = input.lastIndexOf(infix)
};
/* if (index == -1)  */ {
    return new None();
}
return new Some(index);
/* private static  */ first(input, string, infix, string, mapper, (arg0, arg1) => Option);
Option < T > {
    return: infix(input, infix) /* Main */.findFirst, mapper
};
/* private static  */ infix(input, string, infix, string, locator, (arg0, arg1) => Option, mapper, (arg0, arg1) => Option);
Option < T > {
    return /* split */() { }
}();
locator(input, infix).map((index) => {
    let left = input.substring(0, index);
    let right = input.substring(index + infix.length());
    return new Tuple(right);
}), mapper;
;
/* private static  */ split(splitter, () => Option, splitMapper, (arg0, arg1) => Option);
Option < T > {
    return: splitter().flatMap((splitTuple) => splitMapper(splitTuple[0], splitTuple[1]))
};
/* private static */ findFirst(input, string, infix, string);
Option < number > {
    let, index = input.indexOf(infix)
};
/* if (index == -1)  */ {
    return new None();
}
return new Some(index);
/* private static */ generatePlaceholder(input, string);
string;
{
    let replaced = input.replace("/*", "content-start").replace("*/", "content-end");
    return "/* " + replaced + " */";
}
/* private static */ createDebugString(type);
string;
{
    /* if (!Main.isDebug)  */ {
        return "";
    }
    return generatePlaceholder(": " + type.generate());
} /*

private enum Primitive implements Type {
    Int("number"),
    String("string"),
    Boolean("boolean"),
    Unknown("unknown");

    private final String value;

    Primitive(String value) {
        this.value = value;
    }

    @Override
    public String generate() {
        return this.value;
    }

    @Override
    public Type replace(Map<String, Type> mapping) {
        return this;
    }
} */
/*  */ 
