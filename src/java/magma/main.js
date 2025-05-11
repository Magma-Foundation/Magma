"use strict";
 > ;
get(index, number);
T;
size();
number;
isEmpty();
boolean;
addFirst(element, T);
;
iterateWithIndices();
;
removeFirst();
;
/* private */ class Some {
    constructor(value) {
    }
}
return new /* Some */ (mapper /*  : (arg0 : T) => R */(value) /* : R */) /* : content-start Some content-end */;
/* @Override
    public */ isPresent();
boolean;
{
    return /* true */;
}
/* @Override
    public */ orElse(other, T);
T;
{
    return /* this */ .value;
}
/* @Override
    public */ filter(predicate, (arg0) => boolean);
({
/* if (predicate.test(this.value))  */ });
/* if (predicate.test(this.value))  */ {
    return /* this */;
}
return new /* None */ ( /*  */) /* : content-start None content-end */;
/* @Override
    public */ orElseGet(supplier, () => T);
T;
{
    return /* this */ .value;
}
/* @Override
    public */ or(other, () => /* Option */ );
({
    return:  /* this */
});
/* @Override
    public  */ flatMap(mapper, (arg0) => /* Option */ );
({
    return: mapper /*  : (arg0 : T) => content-start Option content-end<R> */() /* this */.value
});
/* @Override
    public */ isEmpty();
boolean;
{
    return /* false */;
}
/* private static */ class None {
}
return new /* None */ ( /*  */) /* : content-start None content-end */;
/* @Override
    public */ isPresent();
boolean;
{
    return /* false */;
}
/* @Override
    public */ orElse(other, T);
T;
{
    return other /*  : T */;
}
/* @Override
    public */ filter(predicate, (arg0) => boolean);
({
    return: new /* None */ ( /*  */) /* : content-start None content-end */
});
/* @Override
    public */ orElseGet(supplier, () => T);
T;
{
    return supplier /*  : () => T */( /*  */) /* : T */;
}
/* @Override
    public */ or(other, () => /* Option */ );
({
    return: other /*  : () => content-start Option content-end<T> */( /*  */) /* : content-start Option content-end<T> */
});
/* @Override
    public  */ flatMap(mapper, (arg0) => /* Option */ );
({
    return: new /* None */ ( /*  */) /* : content-start None content-end */
});
/* @Override
    public */ isEmpty();
boolean;
{
    return /* true */;
}
/* private static */ class SingleHead {
    constructor() {
        this.let /* this.retrieved  */ = ;
    }
    SingleHead(value) {
        let /* this.value  */ = value /*  : T */;
        let /* this.retrieved  */ =  /* false */;
    }
    /* @Override
        public */ next() {
        return new /* None */ ( /*  */) /* : content-start None content-end */;
    }
}
return new /* Some */ ( /* this */.value) /* : content-start Some content-end */;
/* private static */ class EmptyHead {
}
return new /* None */ ( /*  */) /* : content-start None content-end */;
/* private */ class HeadedIterator {
    constructor(head) {
    }
    /* @Override
        public  */ fold(initial, folder) {
        let current = initial /*  : R */;
        /* while (true)  */ {
            let finalCurrent =  /* current */;
            let optional = /* this */ .head.next( /*  */) /* : unknown */.map((inner) => folder /*  : (arg0 : R, arg1 : T) => R */( /* finalCurrent */) /* : R */) /* : unknown */;
            /* if (optional.isPresent())  */ {
                let /* current  */ = /* optional */ .orElse( /* null */) /* : unknown */;
            }
            /* else  */ {
                return /* current */;
            }
        }
    }
}
return new /* HeadedIterator */ (() => /* this */ .head.next( /*  */) /* : unknown */.map(mapper /*  : (arg0 : T) => R */) /* : unknown */) /* : content-start HeadedIterator content-end */;
/* @Override
    public  */ collect(collector, /* Collector */ , R > );
R;
{
    return /* this */ .fold(collector /*  : content-start Collector content-end<T, R> */.createInitial( /*  */) /* : unknown */, collector /*  : content-start Collector content-end<T, R> */.fold) /* : unknown */;
}
/* @Override
    public */ filter(predicate, (arg0) => boolean);
({
    return /* this */: /* this */ .flatMap((element) => {
        /* if (predicate.test(element))  */ {
            return new /* HeadedIterator */ (new /* SingleHead */ ( /* element */) /* : content-start SingleHead content-end */) /* : content-start HeadedIterator content-end */;
        }
        return new /* HeadedIterator */ (new /* EmptyHead */ ( /*  */) /* : content-start EmptyHead content-end */) /* : content-start HeadedIterator content-end */;
    }) /* : unknown */
});
/* @Override
    public */ next();
({
    return /* this */: /* this */ .head.next( /*  */) /* : unknown */
});
/* @Override
    public  */ flatMap(f, (arg0) => /* Iterator */ );
({
    return: new /* HeadedIterator */ (new /* FlatMapHead */ ( /* this */.head, f /*  : (arg0 : T) => content-start Iterator content-end<R> */) /* : content-start FlatMapHead content-end */) /* : content-start HeadedIterator content-end */
});
/* private static */ class RangeHead /*  */ {
    RangeHead(length) {
        let /* this.length  */ = length /*  : number */;
    }
    /* @Override
        public */ next() {
        let value = /* this */ .counter;
        /* this.counter++ */ ;
        return new /* Some */ ( /* value */) /* : content-start Some content-end */;
    }
}
return new /* None */ ( /*  */) /* : content-start None content-end */;
/* private static final */ class JVMList {
    JVMList(elements) {
        let /* this.elements  */ = elements /*  : content-start java.util.List content-end<T> */;
    }
    JVMList() {
        /* this(new ArrayList<>()) */ ;
    }
}
return /* this */ .iterateWithIndices( /*  */) /* : unknown */.map(right) /* : unknown */;
/* @Override
        public */ removeLast();
({
/* if (this.elements.isEmpty())  */ });
/* if (this.elements.isEmpty())  */ {
    return new /* None */ ( /*  */) /* : content-start None content-end */;
}
let slice = /* this */ .elements.subList(0 /*  : number */, elements.size( /*  */) /* : unknown */ - 1 /*  : number */) /* : unknown */;
let last = /* this */ .elements.getLast( /*  */) /* : unknown */;
return new /* Some */ (new [/* List */ , T](new /* JVMList */ ( /* slice */) /* : content-start JVMList content-end */) /* : [content-start List content-end<T>, T] */) /* : content-start Some content-end */;
/* @Override
        public */ get(index, number);
T;
{
    return /* this */ .elements.get(index /*  : number */) /* : unknown */;
}
/* @Override
        public */ size();
number;
{
    return /* this */ .elements.size( /*  */) /* : unknown */;
}
/* @Override
        public */ isEmpty();
boolean;
{
    return /* this */ .elements.isEmpty( /*  */) /* : unknown */;
}
/* @Override
        public */ addFirst(element, T);
({
    return:  /* this */
});
/* @Override
        public */ iterateWithIndices();
({
    return: new /* HeadedIterator */ (new RangeHead() /* this */.elements.size( /*  */) /* : unknown */) /* : RangeHead */, /* : content-start HeadedIterator content-end */ : /* : content-start HeadedIterator content-end */ .map((index) => new /* Tuple */ ( /* index */, /* this */ .elements.get( /* index */) /* : unknown */) /* : content-start Tuple content-end */) /* : unknown */
});
/* @Override
        public */ removeFirst();
({
/* if (this.elements.isEmpty())  */ });
/* if (this.elements.isEmpty())  */ {
    return new /* None */ ( /*  */) /* : content-start None content-end */;
}
let first = /* this */ .elements.getFirst( /*  */) /* : unknown */;
let slice = /* this */ .elements.subList(1 /*  : number */, elements.size( /*  */) /* : unknown */) /* : unknown */;
return new /* Some */ (new [T, /* List */ ](new /* JVMList */ ( /* slice */) /* : content-start JVMList content-end */) /* : [T, content-start List content-end<T>] */) /* : content-start Some content-end */;
/* private static */ class Lists /*  */ {
}
return new /* JVMList */ ( /*  */) /* : content-start JVMList content-end */;
/* public static  */ of(elements, T[]);
({
    return: new /* JVMList */ (new /* ArrayList */ ( /* Arrays */.asList(elements /*  : T[] */) /* : unknown */) /* : content-start ArrayList content-end */) /* : content-start JVMList content-end */
});
/* private */ class ObjectType /*  */ {
    constructor(name) {
    }
    /* @Override
        public */ generate() {
        return /* this */ .name;
    }
}
/* private static */ class DivideState /*  */ {
    DivideState(input, index, segments, buffer, depth) {
        let /* this.segments  */ = segments /*  : content-start List content-end<string> */;
        let /* this.buffer  */ = buffer /*  : StringBuilder */;
        let /* this.depth  */ = depth /*  : number */;
        let /* this.input  */ = input /*  : string */;
        let /* this.index  */ = index /*  : number */;
    }
    DivideState(input) {
        /* this(input, 0, Lists.empty(), new StringBuilder(), 0) */ ;
    }
    /* private */ advance() {
        let /* this.segments  */ = /* this */ .segments.addLast(buffer.toString( /*  */) /* : unknown */) /* : unknown */;
        let /* this.buffer  */ = new StringBuilder( /*  */) /* : StringBuilder */;
        return /* this */;
    }
    /* private */ append(c) {
        /* this.buffer.append(c) */ ;
        return /* this */;
    }
    /* public */ enter() {
        /* this.depth++ */ ;
        return /* this */;
    }
    /* public */ isLevel() {
        return /* this.depth == 0 */;
    }
    /* public */ exit() {
        /* this.depth-- */ ;
        return /* this */;
    }
    /* public */ isShallow() {
        return /* this.depth == 1 */;
    }
}
 > {
/* if (this.index < this.input.length())  */ };
/* if (this.index < this.input.length())  */ {
    let c = /* this */ .input.charAt(index) /* : unknown */;
    return new /* Some */ (new /* Tuple */ ( /* c */, new DivideState(input, index + 1 /*  : number */, segments, buffer, depth) /* : DivideState */) /* : content-start Tuple content-end */) /* : content-start Some content-end */;
}
return new /* None */ ( /*  */) /* : content-start None content-end */;
/* public */ popAndAppendToTuple();
({
    return /* this */: /* this */ .pop( /*  */) /* : unknown */.map((tuple) => new /* Tuple */ ( /* tuple */.left, /* tuple */ .right.append() /* tuple */.left) /* : unknown */) /* : content-start Tuple content-end */
});
/* public */ popAndAppendToOption();
({
    return /* this */: /* this */ .popAndAppendToTuple( /*  */) /* : unknown */.map() /* Tuple */.right
});
/* public */ peek();
char;
{
    return /* this */ .input.charAt(index) /* : unknown */;
}
/* private */ class Joiner /*  */ {
    constructor(delimiter) {
    }
    Joiner() {
        /* this("") */ ;
    }
}
return new /* None */ ( /*  */) /* : content-start None content-end */;
/* @Override
    public */ fold(current, /* Option */ , element, string);
({
    return: new /* Some */ (current /*  : content-start Option content-end<string> */.map((inner) => /* inner */ +.delimiter + element /*  : string */) /* : unknown */.orElse(element /*  : string */) /* : unknown */) /* : content-start Some content-end */
});
/* private */ class Definition /*  */ {
    constructor(maybeBefore, type, name, typeParams) {
    }
    /* private */ generate() {
        return /* this */ .generateWithParams("") /* : unknown */;
    }
    /* public */ generateWithParams(params) {
        let joined = /* this */ .joinTypeParams( /*  */) /* : unknown */;
        let before = /* this */ .joinBefore( /*  */) /* : unknown */;
        let typeString = /* this */ .generateType( /*  */) /* : unknown */;
        return /* before */ +.name + /* joined */ +params /*  : string */ +  /* typeString */;
    }
    /* private */ generateType() {
        /* if (this.type.equals(Primitive.Unknown))  */ {
            return "";
        }
        return " : " + /* this */ .type.generate( /*  */) /* : unknown */;
    }
    /* private */ joinBefore() {
        return /* this */ .maybeBefore.filter((value) => !.isEmpty( /*  */) /* : unknown */) /* : unknown */.map(generatePlaceholder) /* : unknown */.map((inner) => /* inner */ +" ") /* : unknown */.orElse("") /* : unknown */;
    }
    /* private */ joinTypeParams() {
        return /* this */ .typeParams.iterate( /*  */) /* : unknown */.collect(new Joiner( /*  */) /* : Joiner */) /* : unknown */.map((inner) => "<" + inner + ">") /* : unknown */.orElse("") /* : unknown */;
    }
}
/* private static */ class ListCollector {
}
return /* Lists */ .empty( /*  */) /* : unknown */;
/* @Override
    public */ fold(current, /* List */ , element, T);
({
    return: current /*  : content-start List content-end<T> */.addLast(element /*  : T */) /* : unknown */
});
/* private */ class Tuple {
}
/* private static */ class FlatMapHead {
}
 > ;
FlatMapHead(head, /* Head */ , mapper, (arg0) => /* Iterator */ );
public;
{
    let /* this.mapper  */ = mapper /*  : (arg0 : T) => content-start Iterator content-end<R> */;
    let /* this.current  */ = new /* None */ ( /*  */) /* : content-start None content-end */;
    let /* this.head  */ = head /*  : content-start Head content-end<T> */;
}
/* @Override
    public */ next();
({
/* while (true)  */ });
/* while (true)  */ {
    /* if (this.current.isPresent())  */ {
        let inner = /* this */ .current.orElse( /* null */) /* : unknown */;
        let maybe = /* inner */ .next( /*  */) /* : unknown */;
        /* if (maybe.isPresent())  */ {
            return /* maybe */;
        }
        /* else  */ {
            let /* this.current  */ = new /* None */ ( /*  */) /* : content-start None content-end */;
        }
    }
    let outer = /* this */ .head.next( /*  */) /* : unknown */;
    /* if (outer.isPresent())  */ {
        let /* this.current  */ = /* outer */ .map(mapper) /* : unknown */;
    }
    /* else  */ {
        return new /* None */ ( /*  */) /* : content-start None content-end */;
    }
}
/* private */ class SymbolType /*  */ {
    constructor(input) {
    }
    /* @Override
        public */ generate() {
        return /* this */ .input;
    }
}
/* private static */ class ArrayType /*  */ {
    ArrayType(right) {
        let /* this.right  */ = right /*  : Type */;
    }
    /* @Override
        public */ generate() {
        return /* this */ .right.generate( /*  */) /* : unknown */ + "[]";
    }
}
/* private static */ class Whitespace /*  */ {
}
/* private static */ class Iterators /*  */ {
}
let single;
 > ;
option /*  : content-start Option content-end<T> */.map(new ) /* : unknown */;
return new /* HeadedIterator */ ( /* single */.orElseGet(new ) /* : unknown */) /* : content-start HeadedIterator content-end */;
/* private */ class FunctionType /*  */ {
    constructor(arguments, returns) {
    }
    /* @Override
        public */ generate() {
        let joined = /* this */ .arguments( /*  */) /* : unknown */.iterateWithIndices( /*  */) /* : unknown */.map((pair) => "arg" + /* pair */ .left + " : " + /* pair */ .right.generate( /*  */) /* : unknown */) /* : unknown */.collect(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse("") /* : unknown */;
        return "(" + /* joined */ +") => " + /* this */ .returns.generate( /*  */) /* : unknown */;
    }
}
/* private */ class TupleType /*  */ {
    constructor(arguments) {
    }
    /* @Override
        public */ generate() {
        let joinedArguments = /* this */ .arguments.iterate( /*  */) /* : unknown */.map(generate) /* : unknown */.collect(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse("") /* : unknown */;
        return "[" + joinedArguments + "]";
    }
}
/* private */ class Template /*  */ {
    constructor(base, arguments) {
    }
    /* @Override
        public */ generate() {
        let joinedArguments = /* this */ .arguments.iterate( /*  */) /* : unknown */.map(generate) /* : unknown */.collect(new Joiner(", ") /* : Joiner */) /* : unknown */.map((inner) => "<" + inner + ">") /* : unknown */.orElse("") /* : unknown */;
        return /* this */ .base.generate( /*  */) /* : unknown */ +  /* joinedArguments */;
    }
}
/* private */ class Placeholder /*  */ {
    constructor(input) {
    }
    /* @Override
        public */ generate() {
        return /* generatePlaceholder */ ( /* this */.input) /* : unknown */;
    }
    /* @Override
        public */ type() {
        return /* Primitive */ .Unknown;
    }
}
/* private */ class StringValue /*  */ {
    constructor(stripped) {
    }
    /* @Override
        public */ generate() {
        return /* this */ .stripped;
    }
    /* @Override
        public */ type() {
        return /* Primitive */ .Unknown;
    }
}
/* private */ class DataAccess /*  */ {
    constructor(parent, property) {
    }
    /* @Override
        public */ generate() {
        return /* this */ .parent.generate( /*  */) /* : unknown */ + "." + /* this */ .property;
    }
    /* @Override
        public */ type() {
        return /* Primitive */ .Unknown;
    }
}
/* private */ class ConstructionCaller /*  */ {
    constructor(type) {
    }
    /* @Override
        public */ generate() {
        return "new " + /* this */ .type.generate( /*  */) /* : unknown */;
    }
}
/* private */ class Operation /*  */ {
    constructor(left, infix, right) {
    }
    /* @Override
        public */ generate() {
        return /* this */ .left.generate( /*  */) /* : unknown */ + " " + /* this */ .infix + " " + /* this */ .right.generate( /*  */) /* : unknown */;
    }
    /* @Override
        public */ type() {
        return /* Primitive */ .Unknown;
    }
}
/* private */ class Not /*  */ {
    constructor(value) {
    }
    /* @Override
        public */ generate() {
        return "!" + /* this */ .value.generate( /*  */) /* : unknown */;
    }
    /* @Override
        public */ type() {
        return /* Primitive */ .Unknown;
    }
}
/* private */ class BlockLambdaValue /*  */ {
    constructor(right, depth) {
    }
    /* @Override
        public */ generate() {
        return "{" + this.right + createIndent(this.depth) + "}";
    }
}
/* private */ class Lambda /*  */ {
    constructor(parameterNames, body) {
    }
    /* @Override
        public */ generate() {
        let joined = /* this */ .parameterNames.iterate( /*  */) /* : unknown */.map((inner) => /* inner */ +" : unknown") /* : unknown */.collect(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse("") /* : unknown */;
        return "(" + /* joined */ +") => " + /* this */ .body.generate( /*  */) /* : unknown */;
    }
    /* @Override
        public */ type() {
        return /* Primitive */ .Unknown;
    }
}
/* private */ class Invokable /*  */ {
    constructor(caller, arguments, returnsType) {
    }
    /* @Override
        public */ generate() {
        let joined = /* this */ .arguments.iterate( /*  */) /* : unknown */.map(generate) /* : unknown */.collect(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse("") /* : unknown */;
        return /* this */ .caller.generate( /*  */) /* : unknown */ + "(" + /* joined */ +")" + /* generatePlaceholder */ (": " + /* this */ .returnsType.generate( /*  */) /* : unknown */) /* : unknown */;
    }
    /* @Override
        public */ type() {
        return /* Primitive */ .Unknown;
    }
}
/* private */ class IndexValue /*  */ {
    constructor(parent, child) {
    }
    /* @Override
        public */ generate() {
        return /* this */ .parent.generate( /*  */) /* : unknown */ + "[" + this.child.generate() + "]";
    }
    /* @Override
        public */ type() {
        return /* Primitive */ .Unknown;
    }
}
/* private */ class SymbolValue /*  */ {
    constructor(stripped, type) {
    }
    /* @Override
        public */ generate() {
        return /* this */ .stripped + /* generatePlaceholder */ (" : " + /* this */ .type.generate( /*  */) /* : unknown */) /* : unknown */;
    }
}
/* public */ class Main /*  */ {
    /* private */ CompileState(structures, definitions, types) {
        /* public CompileState()  */ {
            /* this(Lists.empty(), Lists.empty(), Lists.empty()) */ ;
        }
        /* private Option<Type> resolveValue(String name)  */ {
            return /* this */ .definitions.iterate( /*  */) /* : unknown */.filter((definition) => /* definition */ .name.equals( /* name */) /* : unknown */) /* : unknown */.next( /*  */) /* : unknown */.map(type) /* : unknown */;
        }
        /* public CompileState addStructure(String structure)  */ {
            return new CompileState(structures.addLast( /* structure */) /* : unknown */, definitions, types) /* : CompileState */;
        }
        /* public CompileState withDefinitions(List<Definition> definitions)  */ {
            return new CompileState(structures, definitions /*  : content-start List content-end<Definition> */, types) /* : CompileState */;
        }
        /* public Option<ObjectType> resolveType(String name)  */ {
            return /* this */ .types.iterate( /*  */) /* : unknown */.filter((type) => /* type */ .name.equals( /* name */) /* : unknown */) /* : unknown */.next( /*  */) /* : unknown */;
        }
    }
    /* public static */ main() {
        /* try  */ {
            let parent = /* Paths */ .get(".", "src", "java", "magma") /* : unknown */;
            let source = /* parent */ .resolve("Main.java") /* : unknown */;
            let target = /* parent */ .resolve("main.ts") /* : unknown */;
            let input = /* Files */ .readString( /* source */) /* : unknown */;
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
    /* private static */ compile(input) {
        let tuple = /* compileStatements */ (new CompileState( /*  */) /* : CompileState */, input /*  : string */, /* Main */ .compileRootSegment) /* : unknown */;
        let joined = /* tuple */ .left.structures.iterate( /*  */) /* : unknown */.collect(new Joiner( /*  */) /* : Joiner */) /* : unknown */.orElse("") /* : unknown */;
        return /* joined */ +.right;
    }
    /* private static */ compileStatements(state, input, mapper) {
        let parsed = /* parseStatements */ (state /*  : CompileState */, input /*  : string */, mapper /*  : (arg0 : CompileState, arg1 : string) => [CompileState, string] */) /* : unknown */;
        return new /* Tuple */ ( /* parsed */.left, /* generateStatements */ ( /* parsed */.right) /* : unknown */) /* : content-start Tuple content-end */;
    }
    /* private static */ generateStatements(statements) {
        return /* generateAll */ ( /* Main */.mergeStatements, statements /*  : content-start List content-end<string> */) /* : unknown */;
    }
    /* private static */ parseStatements(state, input, mapper) {
        return /* parseAll */ (state /*  : CompileState */, input /*  : string */, /* Main */ .foldStatementChar, mapper /*  : (arg0 : CompileState, arg1 : string) => [CompileState, string] */) /* : unknown */;
    }
    /* private static */ generateAll(merger, elements) {
        return elements /*  : content-start List content-end<string> */.iterate( /*  */) /* : unknown */.fold(new StringBuilder( /*  */) /* : StringBuilder */, merger /*  : (arg0 : StringBuilder, arg1 : string) => StringBuilder */) /* : unknown */.toString( /*  */) /* : unknown */;
    }
    /* private static  */ parseAll(state, input, folder, mapper) {
        return /* getCompileStateListTuple */ (state /*  : CompileState */, input /*  : string */, folder /*  : (arg0 : DivideState, arg1 : Character) => DivideState */, (state1, s) => new /* Some */ (mapper /*  : (arg0 : CompileState, arg1 : string) => [CompileState, T] */( /* state1 */) /* : [CompileState, T] */) /* : content-start Some content-end */) /* : unknown */.orElseGet(() => new /* Tuple */ (state /*  : CompileState */, /* Lists */ .empty( /*  */) /* : unknown */) /* : content-start Tuple content-end */) /* : unknown */;
    }
}
 > ;
({
    let, initial: /* Option */ , new /* Some */() { }
} /* Tuple */(state /*  : CompileState */, empty( /*  */) /* : unknown */)); /* : content-start Tuple content-end */
;
return /* divideAll */ (input /*  : string */, folder /*  : (arg0 : DivideState, arg1 : Character) => DivideState */) /* : unknown */.iterate( /*  */) /* : unknown */.fold((tuple, element) => {
    return /* tuple */ .flatMap((inner) => {
        let state1 = /* inner */ .left;
        let right = /* inner */ .right;
        return mapper /*  : (arg0 : CompileState, arg1 : string) => content-start Option content-end<[CompileState, T]> */( /* state1 */) /* : content-start Option content-end<[CompileState, T]> */.map((applied) => {
            return new /* Tuple */ ( /* applied */.left, /* right */ .addLast(right) /* : unknown */) /* : content-start Tuple content-end */;
        }) /* : unknown */;
    }) /* : unknown */;
}) /* : unknown */;
/* private static */ mergeStatements(stringBuilder, StringBuilder, str, string);
StringBuilder;
{
    return stringBuilder /*  : StringBuilder */.append(str /*  : string */) /* : unknown */;
}
/* private static */ divideAll(input, string, folder, (arg0, arg1) => DivideState);
({
    let, current: , var:  = new DivideState(input /*  : string */) /* : DivideState */
});
/* while (true)  */ {
    let maybePopped = /* current */ .pop( /*  */) /* : unknown */.map((tuple) => {
        return /* foldSingleQuotes */ ( /* tuple */) /* : unknown */.or(() => /* foldDoubleQuotes */ ( /* tuple */) /* : unknown */) /* : unknown */.orElseGet(() => folder /*  : (arg0 : DivideState, arg1 : Character) => DivideState */(right, left) /* : DivideState */) /* : unknown */;
    }) /* : unknown */;
    /* if (maybePopped.isPresent())  */ {
        let /* current  */ = /* maybePopped */ .orElse( /* current */) /* : unknown */;
    }
    /* else  */ {
        /* break */ ;
    }
}
return /* current */ .advance( /*  */) /* : unknown */.segments;
/* private static */ foldDoubleQuotes(tuple, [Character, DivideState]);
({
/* if (tuple.left == '\"')  */ });
/* if (tuple.left == '\"')  */ {
    let current = tuple /*  : [Character, DivideState] */.right.append(tuple /*  : [Character, DivideState] */[0 /*  : number */]) /* : unknown */;
    /* while (true)  */ {
        let maybePopped = /* current */ .popAndAppendToTuple( /*  */) /* : unknown */;
        /* if (maybePopped.isEmpty())  */ {
            /* break */ ;
        }
        let popped = /* maybePopped */ .orElse( /* null */) /* : unknown */;
        let /* current  */ = /* popped */ .right;
        /* if (popped.left == '\\')  */ {
            let /* current  */ = /* current */ .popAndAppendToOption( /*  */) /* : unknown */.orElse( /* current */) /* : unknown */;
        }
        /* if (popped.left == '\"')  */ {
            /* break */ ;
        }
    }
    return new /* Some */ ( /* current */) /* : content-start Some content-end */;
}
return new /* None */ ( /*  */) /* : content-start None content-end */;
/* private static */ foldSingleQuotes(tuple, [Character, DivideState]);
({
/* if (tuple.left != '\'')  */ });
/* if (tuple.left != '\'')  */ {
    return new /* None */ ( /*  */) /* : content-start None content-end */;
}
let appended = tuple /*  : [Character, DivideState] */.right.append(tuple /*  : [Character, DivideState] */[0 /*  : number */]) /* : unknown */;
return /* appended */ .popAndAppendToTuple( /*  */) /* : unknown */.map(foldEscaped) /* : unknown */.flatMap(popAndAppendToOption) /* : unknown */;
/* private static */ foldEscaped(escaped, [Character, DivideState]);
DivideState;
{
    /* if (escaped.left == '\\')  */ {
        return escaped /*  : [Character, DivideState] */.right.popAndAppendToOption( /*  */) /* : unknown */.orElse(escaped /*  : [Character, DivideState] */.right) /* : unknown */;
    }
    return escaped /*  : [Character, DivideState] */.right;
}
/* private static */ foldStatementChar(state, DivideState, c, char);
DivideState;
{
    let append = state /*  : DivideState */.append(c /*  : char */) /* : unknown */;
    /* if (c == ';' && append.isLevel())  */ {
        return /* append */ .advance( /*  */) /* : unknown */;
    }
    /* if (c == '}' && append.isShallow())  */ {
        return /* append */ .advance( /*  */) /* : unknown */.exit( /*  */) /* : unknown */;
    }
    /* if (c == '{' || c == '(')  */ {
        return /* append */ .enter( /*  */) /* : unknown */;
    }
    /* if (c == '}' || c == ')')  */ {
        return /* append */ .exit( /*  */) /* : unknown */;
    }
    return /* append */;
}
/* private static */ compileRootSegment(state, CompileState, input, string);
[CompileState, string];
{
    let stripped = input /*  : string */.strip( /*  */) /* : unknown */;
    /* if (stripped.startsWith("package ") || stripped.startsWith("import "))  */ {
        return new /* Tuple */ (state /*  : CompileState */, "") /* : content-start Tuple content-end */;
    }
    return /* compileClass */ ( /* stripped */, 0 /*  : number */, state /*  : CompileState */) /* : unknown */.orElseGet(() => new /* Tuple */ (state /*  : CompileState */, /* generatePlaceholder */ ( /* stripped */) /* : unknown */) /* : content-start Tuple content-end */) /* : unknown */;
}
/* private static */ compileClass(stripped, string, depth, number, state, CompileState);
({});
/* private static */ structure(stripped, string, sourceInfix, string, targetInfix, string, state, CompileState);
({
    return /* first */(stripped /*  : string */, sourceInfix /*  : string */) { }
}(beforeInfix, unknown, right, unknown));
{
    return /* first */ ( /* right */, "{", (beforeContent, withEnd) => {
        let strippedWithEnd = /* withEnd */ .strip( /*  */) /* : unknown */;
        return /* suffix */ ( /* strippedWithEnd */, "}", (content1) => {
            return /* first */ ( /* beforeContent */, " implements ", (s, s2) => {
                return /* structureWithMaybeParams */ (targetInfix /*  : string */, state /*  : CompileState */,  /* beforeInfix */,  /* s */,  /* content1 */) /* : unknown */;
            }) /* : unknown */.or(() => {
                return /* structureWithMaybeParams */ (targetInfix /*  : string */, state /*  : CompileState */,  /* beforeInfix */,  /* beforeContent */,  /* content1 */) /* : unknown */;
            }) /* : unknown */;
        }) /* : unknown */;
    }) /* : unknown */;
}
;
/* private static */ structureWithMaybeParams(targetInfix, string, state, CompileState, beforeInfix, string, beforeContent, string, content1, string);
({
    return /* suffix */(beforeContent /*  : string */, ) { }
}(s, unknown));
{
    return /* first */ ( /* s */, "(", (s1, s2) => {
        let parsed = /* parseParameters */ (state /*  : CompileState */,  /* s2 */) /* : unknown */;
        return /* getOred */ (targetInfix /*  : string */, /* parsed */ .left, beforeInfix /*  : string */,  /* s1 */, content1 /*  : string */, /* parsed */ .right) /* : unknown */;
    }) /* : unknown */;
}
or(() => {
    return /* getOred */ (targetInfix /*  : string */, state /*  : CompileState */, beforeInfix /*  : string */, beforeContent /*  : string */, content1 /*  : string */, /* Lists */ .empty( /*  */) /* : unknown */) /* : unknown */;
}) /* : unknown */;
/* private static */ getOred(targetInfix, string, state, CompileState, beforeInfix, string, beforeContent, string, content1, string, params, /* List */ );
({
    return /* first */(beforeContent /*  : string */, ) { }
}(name, unknown, withTypeParams, unknown));
{
    return /* first */ ( /* withTypeParams */, ">", (typeParamsString, afterTypeParams) => {
        let /* final */ compileStateStringTupleBiFunction = (state1, s) => new /* Tuple */ ( /* state1 */, /* s */ .strip( /*  */) /* : unknown */) /* : content-start Tuple content-end */;
        let typeParams = /* parseValuesOrEmpty */ (state /*  : CompileState */,  /* typeParamsString */, (state1, s) => new /* Some */ ( /* compileStateStringTupleBiFunction */.apply( /* state1 */) /* : unknown */) /* : content-start Some content-end */) /* : unknown */;
        return /* assemble */ ( /* typeParams */.left, targetInfix /*  : string */, beforeInfix /*  : string */,  /* name */, content1 /*  : string */, /* typeParams */ .right,  /* afterTypeParams */, params /*  : content-start List content-end<Parameter> */) /* : unknown */;
    }) /* : unknown */;
}
or(() => {
    return /* assemble */ (state /*  : CompileState */, targetInfix /*  : string */, beforeInfix /*  : string */, beforeContent /*  : string */, content1 /*  : string */, /* Lists */ .empty( /*  */) /* : unknown */, "", params /*  : content-start List content-end<Parameter> */) /* : unknown */;
}) /* : unknown */;
/* private static */ assemble(state, CompileState, targetInfix, string, beforeInfix, string, rawName, string, content, string, typeParams, /* List */ , afterTypeParams, string, params, /* List */ );
({
    let, name: , var:  = rawName /*  : string */.strip( /*  */) /* : unknown */
});
/* if (!isSymbol(name))  */ {
    return new /* None */ ( /*  */) /* : content-start None content-end */;
}
let joinedTypeParams = typeParams /*  : content-start List content-end<string> */.iterate( /*  */) /* : unknown */.collect(new Joiner(", ") /* : Joiner */) /* : unknown */.map((inner) => "<" + inner + ">") /* : unknown */.orElse("") /* : unknown */;
let parsed = /* parseStatements */ (state /*  : CompileState */, content /*  : string */, (state0, input) => /* compileClassSegment */ ( /* state0 */,  /* input */, 1 /*  : number */) /* : unknown */) /* : unknown */;
/* List<String> parsed1 */ ;
/* if (params.isEmpty())  */ {
    let /* parsed1  */ = /* parsed */ .right;
}
/* else  */ {
    let joined = /* joinValues */ ( /* retainDefinitions */(params /*  : content-start List content-end<Parameter> */) /* : unknown */) /* : unknown */;
    let constructorIndent = /* createIndent */ (1 /*  : number */) /* : unknown */;
    let /* parsed1  */ = /* parsed */ .right.addFirst(/* constructorIndent */ +"constructor (" + joined + ") {" + constructorIndent + "}\n") /* : unknown */;
}
let parsed2 = /* parsed1 */ .iterate( /*  */) /* : unknown */.collect(new Joiner( /*  */) /* : Joiner */) /* : unknown */.orElse("") /* : unknown */;
let generated = /* generatePlaceholder */ (beforeInfix /*  : string */.strip( /*  */) /* : unknown */) /* : unknown */ + targetInfix /*  : string */ + /* name */ + +(afterTypeParams /*  : string */) /* : unknown */ + " {" + parsed2 + "\n}\n";
return new /* Some */ (new /* Tuple */ ( /* parsed */.left.addStructure( /* generated */) /* : unknown */, "") /* : content-start Tuple content-end */) /* : content-start Some content-end */;
/* private static */ retainDefinition(parameter, Parameter);
({
/* if (parameter instanceof Definition definition)  */ });
/* if (parameter instanceof Definition definition)  */ {
    return new /* Some */ ( /* definition */) /* : content-start Some content-end */;
}
return new /* None */ ( /*  */) /* : content-start None content-end */;
/* private static */ isSymbol(input, string);
boolean;
{
    /* for (var i = 0; i < input.length(); i++)  */ {
        let c = input /*  : string */.charAt( /* i */) /* : unknown */;
        /* if (Character.isLetter(c) || (i != 0 && Character.isDigit(c)))  */ {
            /* continue */ ;
        }
        return /* false */;
    }
    return /* true */;
}
/* private static  */ suffix(input, string, suffix, string, mapper, (arg0) => /* Option */ );
({
/* if (!input.endsWith(suffix))  */ });
/* if (!input.endsWith(suffix))  */ {
    return new /* None */ ( /*  */) /* : content-start None content-end */;
}
let slice = input /*  : string */.substring(0 /*  : number */, input /*  : string */.length( /*  */) /* : unknown */ - suffix /*  : string */.length( /*  */) /* : unknown */) /* : unknown */;
return mapper /*  : (arg0 : string) => content-start Option content-end<T> */( /* slice */) /* : content-start Option content-end<T> */;
/* private static */ compileClassSegment(state, CompileState, input, string, depth, number);
[CompileState, string];
{
    return /* compileWhitespace */ (input /*  : string */, state /*  : CompileState */) /* : unknown */.or(() => /* compileClass */ (input /*  : string */, depth /*  : number */, state /*  : CompileState */) /* : unknown */) /* : unknown */.or(() => /* structure */ (input /*  : string */, "interface ", "interface ", state /*  : CompileState */) /* : unknown */) /* : unknown */.or(() => /* structure */ (input /*  : string */, "record ", "class ", state /*  : CompileState */) /* : unknown */) /* : unknown */.or(() => /* compileMethod */ (state /*  : CompileState */, input /*  : string */, depth /*  : number */) /* : unknown */) /* : unknown */.or(() => /* compileDefinitionStatement */ (input /*  : string */, depth /*  : number */, state /*  : CompileState */) /* : unknown */) /* : unknown */.orElseGet(() => new /* Tuple */ (state /*  : CompileState */, /* generatePlaceholder */ (input /*  : string */) /* : unknown */) /* : content-start Tuple content-end */) /* : unknown */;
}
/* private static */ compileWhitespace(input, string, state, CompileState);
({
/* if (input.isBlank())  */ });
/* if (input.isBlank())  */ {
    return new /* Some */ (new /* Tuple */ (state /*  : CompileState */, "") /* : content-start Tuple content-end */) /* : content-start Some content-end */;
}
return new /* None */ ( /*  */) /* : content-start None content-end */;
/* private static */ compileMethod(state, CompileState, input, string, depth, number);
({
    return /* first */(input /*  : string */, ) { }
}(definitionString, unknown, withParams, unknown));
{
    return /* first */ ( /* withParams */, ")", (parametersString, rawContent) => {
        return /* parseDefinition */ (state /*  : CompileState */,  /* definitionString */) /* : unknown */.flatMap((definitionTuple) => {
            let definitionState = /* definitionTuple */ .left;
            let definition = /* definitionTuple */ .right;
            let parametersTuple = /* parseParameters */ ( /* definitionState */,  /* parametersString */) /* : unknown */;
            let parameters = /* parametersTuple */ .right;
            let definitions = /* retainDefinitions */ ( /* parameters */) /* : unknown */;
            let joinedParameters = /* joinValues */ ( /* definitions */) /* : unknown */;
            let content = /* rawContent */ .strip( /*  */) /* : unknown */;
            let indent = /* createIndent */ (depth /*  : number */) /* : unknown */;
            let generatedHeader = /* definition */ .generateWithParams("(" + joinedParameters + ")") /* : unknown */;
            /* if (content.equals(";"))  */ {
                return new /* Some */ (new /* Tuple */ ( /* parametersTuple */.left, /* indent */ + +";") /* : content-start Tuple content-end */) /* : content-start Some content-end */;
            }
            /* if (content.startsWith("{") && content.endsWith("}"))  */ {
                let substring = /* content */ .substring(1 /*  : number */, length( /*  */) /* : unknown */ - 1 /*  : number */) /* : unknown */;
                let statementsTuple = /* compileFunctionSegments */ ( /* parametersTuple */.left.withDefinitions( /* definitions */) /* : unknown */,  /* substring */, depth /*  : number */) /* : unknown */;
                let generated = /* indent */ + +" {" + statementsTuple.right + indent + "}";
                return new /* Some */ (new /* Tuple */ ( /* statementsTuple */.left,  /* generated */) /* : content-start Tuple content-end */) /* : content-start Some content-end */;
            }
            return new /* None */ ( /*  */) /* : content-start None content-end */;
        }) /* : unknown */;
    }) /* : unknown */;
}
;
/* private static */ joinValues(retainParameters, /* List */ );
string;
{
    return retainParameters /*  : content-start List content-end<Definition> */.iterate( /*  */) /* : unknown */.map(generate) /* : unknown */.collect(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse("") /* : unknown */;
}
/* private static */ retainDefinitions(right, /* List */ );
({
    return: right /*  : content-start List content-end<Parameter> */.iterate( /*  */) /* : unknown */.map() /* Main */.retainDefinition, /* : unknown */ : /* : unknown */ .flatMap() /* Iterators */.fromOption, /* : unknown */ : /* : unknown */ .collect(new /* ListCollector */ ( /*  */) /* : content-start ListCollector content-end */) /* : unknown */
});
/* private static */ parseParameters(state, CompileState, params, string);
[CompileState, /* List */ ];
{
    return /* parseValuesOrEmpty */ (state /*  : CompileState */, params /*  : string */, (state1, s) => new /* Some */ ( /* compileParameter */( /* state1 */,  /* s */) /* : unknown */) /* : content-start Some content-end */) /* : unknown */;
}
/* private static */ compileFunctionSegments(state, CompileState, input, string, depth, number);
[CompileState, string];
{
    return /* compileStatements */ (state /*  : CompileState */, input /*  : string */, (state1, input1) => /* compileFunctionSegment */ ( /* state1 */,  /* input1 */, depth /*  : number */ + 1 /*  : number */) /* : unknown */) /* : unknown */;
}
/* private static */ compileFunctionSegment(state, CompileState, input, string, depth, number);
[CompileState, string];
{
    let stripped = input /*  : string */.strip( /*  */) /* : unknown */;
    /* if (stripped.isEmpty())  */ {
        return new /* Tuple */ (state /*  : CompileState */, "") /* : content-start Tuple content-end */;
    }
    return /* suffix */ ( /* stripped */, ";", (s) => {
        let tuple = /* statementValue */ (state /*  : CompileState */,  /* s */, depth /*  : number */) /* : unknown */;
        return new /* Some */ (new /* Tuple */ ( /* tuple */.left, /* createIndent */ (depth /*  : number */) /* : unknown */ + /* tuple */ .right + ";") /* : content-start Tuple content-end */) /* : content-start Some content-end */;
    }) /* : unknown */.or(() => {
        return /* block */ (state /*  : CompileState */, depth /*  : number */,  /* stripped */) /* : unknown */;
    }) /* : unknown */.orElseGet(() => {
        return new /* Tuple */ (state /*  : CompileState */, /* generatePlaceholder */ ( /* stripped */) /* : unknown */) /* : content-start Tuple content-end */;
    }) /* : unknown */;
}
/* private static */ block(state, CompileState, depth, number, stripped, string);
({
    return /* suffix */(stripped /*  : string */, ) { }
}(withoutEnd, unknown));
{
    return /* split */ (() => {
        let divisions = /* divideAll */ ( /* withoutEnd */, /* Main */ .foldBlockStart) /* : unknown */;
        return /* divisions */ .removeFirst( /*  */) /* : unknown */.map((removed) => {
            let right = /* removed */ .left;
            let left = /* removed */ .right.iterate( /*  */) /* : unknown */.collect(new Joiner("") /* : Joiner */) /* : unknown */.orElse("") /* : unknown */;
            return new /* Tuple */ ( /* right */,  /* left */) /* : content-start Tuple content-end */;
        }) /* : unknown */;
    }, (beforeContent, content) => {
        return /* suffix */ ( /* beforeContent */, "{", (s) => {
            let compiled = /* compileFunctionSegments */ (state /*  : CompileState */,  /* content */, depth /*  : number */) /* : unknown */;
            let indent = /* createIndent */ (depth /*  : number */) /* : unknown */;
            return new /* Some */ (new /* Tuple */ ( /* compiled */.left, /* indent */ +( /* s */) /* : unknown */ + "{" + compiled.right + indent + "}") /* : content-start Tuple content-end */) /* : content-start Some content-end */;
        }) /* : unknown */;
    }) /* : unknown */;
}
;
/* private static */ foldBlockStart(state, DivideState, c, Character);
DivideState;
{
    let appended = state /*  : DivideState */.append(c /*  : Character */) /* : unknown */;
    /* if (c == '{' && state.isLevel())  */ {
        return /* appended */ .advance( /*  */) /* : unknown */;
    }
    /* if (c == '{')  */ {
        return /* appended */ .enter( /*  */) /* : unknown */;
    }
    /* if (c == '}')  */ {
        return /* appended */ .exit( /*  */) /* : unknown */;
    }
    return /* appended */;
}
/* private static */ statementValue(state, CompileState, input, string, depth, number);
[CompileState, string];
{
    let stripped = input /*  : string */.strip( /*  */) /* : unknown */;
    /* if (stripped.startsWith("return "))  */ {
        let value = /* stripped */ .substring("return ".length( /*  */) /* : unknown */) /* : unknown */;
        let tuple = /* compileValue */ (state /*  : CompileState */,  /* value */, depth /*  : number */) /* : unknown */;
        return new /* Tuple */ ( /* tuple */.left, "return " + /* tuple */ .right) /* : content-start Tuple content-end */;
    }
    return /* first */ ( /* stripped */, "=", (s, s2) => {
        let definitionTuple = /* compileDefinition */ (state /*  : CompileState */,  /* s */) /* : unknown */;
        let valueTuple = /* compileValue */ ( /* definitionTuple */.left,  /* s2 */, depth /*  : number */) /* : unknown */;
        return new /* Some */ (new /* Tuple */ ( /* valueTuple */.left, "let " + /* definitionTuple */ .right + " = " + /* valueTuple */ .right) /* : content-start Tuple content-end */) /* : content-start Some content-end */;
    }) /* : unknown */.orElseGet(() => {
        return new /* Tuple */ (state /*  : CompileState */, /* generatePlaceholder */ ( /* stripped */) /* : unknown */) /* : content-start Tuple content-end */;
    }) /* : unknown */;
}
/* private static */ compileValue(state, CompileState, input, string, depth, number);
[CompileState, string];
{
    let tuple = /* parseValue */ (state /*  : CompileState */, input /*  : string */, depth /*  : number */) /* : unknown */;
    return new /* Tuple */ ( /* tuple */.left, /* tuple */ .right.generate( /*  */) /* : unknown */) /* : content-start Tuple content-end */;
}
/* private static */ parseValue(state, CompileState, input, string, depth, number);
[CompileState, Value];
{
    return /* parseLambda */ (state /*  : CompileState */, input /*  : string */, depth /*  : number */) /* : unknown */.or(() => /* parseString */ (state /*  : CompileState */, input /*  : string */) /* : unknown */) /* : unknown */.or(() => /* parseDataAccess */ (state /*  : CompileState */, input /*  : string */, depth /*  : number */) /* : unknown */) /* : unknown */.or(() => /* parseSymbolValue */ (state /*  : CompileState */, input /*  : string */) /* : unknown */) /* : unknown */.or(() => /* parseInvocation */ (state /*  : CompileState */, input /*  : string */, depth /*  : number */) /* : unknown */) /* : unknown */.or(() => /* parseOperation */ (state /*  : CompileState */, input /*  : string */, depth /*  : number */, "+") /* : unknown */) /* : unknown */.or(() => /* parseOperation */ (state /*  : CompileState */, input /*  : string */, depth /*  : number */, "-") /* : unknown */) /* : unknown */.or(() => /* parseDigits */ (state /*  : CompileState */, input /*  : string */) /* : unknown */) /* : unknown */.or(() => /* parseNot */ (state /*  : CompileState */, input /*  : string */, depth /*  : number */) /* : unknown */) /* : unknown */.or(() => /* parseMethodReference */ (state /*  : CompileState */, input /*  : string */, depth /*  : number */) /* : unknown */) /* : unknown */.orElseGet(() => new [CompileState, Value](state /*  : CompileState */, new Placeholder(input /*  : string */) /* : Placeholder */) /* : [CompileState, Value] */) /* : unknown */;
}
/* private static */ parseMethodReference(state, CompileState, input, string, depth, number);
({
    return /* last */(input /*  : string */, ) { }
}(s, unknown, s2, unknown));
{
    let tuple = /* parseValue */ (state /*  : CompileState */,  /* s */, depth /*  : number */) /* : unknown */;
    return new /* Some */ (new /* Tuple */ ( /* tuple */.left, new DataAccess(right) /* : DataAccess */) /* : content-start Tuple content-end */) /* : content-start Some content-end */;
}
;
/* private static */ parseNot(state, CompileState, input, string, depth, number);
({
    let, stripped: , var:  = input /*  : string */.strip( /*  */) /* : unknown */
});
/* if (stripped.startsWith("!"))  */ {
    let slice = /* stripped */ .substring(1 /*  : number */) /* : unknown */;
    let tuple = /* parseValue */ (state /*  : CompileState */,  /* slice */, depth /*  : number */) /* : unknown */;
    let value = /* tuple */ .right;
    return new /* Some */ (new /* Tuple */ ( /* tuple */.left, new Not( /* value */) /* : Not */) /* : content-start Tuple content-end */) /* : content-start Some content-end */;
}
return new /* None */ ( /*  */) /* : content-start None content-end */;
/* private static */ parseLambda(state, CompileState, input, string, depth, number);
({
    return /* first */(input /*  : string */, ) { }
}(beforeArrow, unknown, valueString, unknown));
{
    let strippedBeforeArrow = /* beforeArrow */ .strip( /*  */) /* : unknown */;
    /* if (isSymbol(strippedBeforeArrow))  */ {
        return /* assembleLambda */ (state /*  : CompileState */, /* Lists */ .of( /* strippedBeforeArrow */) /* : unknown */,  /* valueString */, depth /*  : number */) /* : unknown */;
    }
    /* if (strippedBeforeArrow.startsWith("(") && strippedBeforeArrow.endsWith(")"))  */ {
        let parameterNames = /* divideAll */ ( /* strippedBeforeArrow */.substring(1 /*  : number */, length( /*  */) /* : unknown */ - 1 /*  : number */) /* : unknown */, /* Main */ .foldValueChar) /* : unknown */.iterate( /*  */) /* : unknown */.map(strip) /* : unknown */.filter((value) => !.isEmpty( /*  */) /* : unknown */) /* : unknown */.collect(new /* ListCollector */ ( /*  */) /* : content-start ListCollector content-end */) /* : unknown */;
        return /* assembleLambda */ (state /*  : CompileState */,  /* parameterNames */,  /* valueString */, depth /*  : number */) /* : unknown */;
    }
    return new /* None */ ( /*  */) /* : content-start None content-end */;
}
;
/* private static */ assembleLambda(state, CompileState, paramNames, /* List */ , valueString, string, depth, number);
({
    let, strippedValueString: , var:  = valueString /*  : string */.strip( /*  */) /* : unknown */
});
/* if (strippedValueString.startsWith("{") && strippedValueString.endsWith("}"))  */ {
    let value1 = /* compileStatements */ (state /*  : CompileState */, /* strippedValueString */ .substring(1 /*  : number */, length( /*  */) /* : unknown */ - 1 /*  : number */) /* : unknown */, (state1, input1) => /* compileFunctionSegment */ ( /* state1 */,  /* input1 */, depth /*  : number */ + 1 /*  : number */) /* : unknown */) /* : unknown */;
    let right = /* value1 */ .right;
    let /* value  */ = new /* Tuple */ ( /* value1 */.left, new BlockLambdaValue(depth /*  : number */) /* : BlockLambdaValue */) /* : content-start Tuple content-end */;
}
/* else  */ {
    let value1 = /* parseValue */ (state /*  : CompileState */,  /* strippedValueString */, depth /*  : number */) /* : unknown */;
    let /* value  */ = new /* Tuple */ ( /* value1 */.left, /* value1 */ .right) /* : content-start Tuple content-end */;
}
let right = /* value */ .right;
return new /* Some */ (new /* Tuple */ ( /* value */.left, new Lambda(paramNames /*  : content-start List content-end<string> */) /* : Lambda */) /* : content-start Tuple content-end */) /* : content-start Some content-end */;
/* private static */ parseDigits(state, CompileState, input, string);
({
    let, stripped: , var:  = input /*  : string */.strip( /*  */) /* : unknown */
});
/* if (isNumber(stripped))  */ {
    return new /* Some */ (new [CompileState, Value](state /*  : CompileState */, new SymbolValue(Int) /* : SymbolValue */) /* : [CompileState, Value] */) /* : content-start Some content-end */;
}
return new /* None */ ( /*  */) /* : content-start None content-end */;
/* private static */ isNumber(input, string);
boolean;
{
    /* for (var i = 0; i < input.length(); i++)  */ {
        let c = input /*  : string */.charAt( /* i */) /* : unknown */;
        /* if (Character.isDigit(c))  */ {
            /* continue */ ;
        }
        return /* false */;
    }
    return /* true */;
}
/* private static */ parseInvocation(state, CompileState, input, string, depth, number);
({
    return /* suffix */(input) { } /*  : string */, /*  : string */ : /*  : string */ .strip( /*  */) /* : unknown */, ")": ,
}(withoutEnd, unknown));
{
    return /* split */ (() => /* toLast */ ( /* withoutEnd */, "", /* Main */ .foldInvocationStart) /* : unknown */, (callerWithEnd, argumentsString) => {
        return /* suffix */ ( /* callerWithEnd */, "(", (callerString) => {
            let callerString1 = /* callerString */ .strip( /*  */) /* : unknown */;
            let callerTuple = /* invocationHeader */ (state /*  : CompileState */, depth /*  : number */,  /* callerString1 */) /* : unknown */;
            let parsed = /* parseValues */ ( /* callerTuple */.left,  /* argumentsString */, (state3, s) => new /* Some */ ( /* parseValue */( /* state3 */,  /* s */, depth /*  : number */) /* : unknown */) /* : content-start Some content-end */) /* : unknown */.orElseGet(() => new /* Tuple */ ( /* callerTuple */.left, /* Lists */ .empty( /*  */) /* : unknown */) /* : content-start Tuple content-end */) /* : unknown */;
            let oldCaller = /* callerTuple */ .right;
            let arguments = /* parsed */ .right;
            let newCaller = /* modifyCaller */ ( /* parsed */.left,  /* oldCaller */) /* : unknown */;
            let;
            var Type = /* Primitive */ .Unknown;
            /* switch (newCaller)  */ {
                /* case ConstructionCaller constructionCaller ->  */ {
                    let /* var  */ = /* constructionCaller */ .type;
                }
                /* case Value value ->  */ {
                    let type = /* value */ .type( /*  */) /* : unknown */;
                    /* if (type instanceof FunctionType functionType)  */ {
                        let /* var  */ = /* functionType */ .returns;
                    }
                }
            }
            let invokable = new Invokable( /* newCaller */) /* : Invokable */;
            return new /* Some */ (new /* Tuple */ ( /* parsed */.left,  /* invokable */) /* : content-start Tuple content-end */) /* : content-start Some content-end */;
        }) /* : unknown */;
    }) /* : unknown */;
}
;
/* private static */ modifyCaller(state, CompileState, oldCaller, Caller);
Caller;
{
    /* if (oldCaller instanceof DataAccess access)  */ {
        let type = /* resolveType */ ( /* access */.parent, state /*  : CompileState */) /* : unknown */;
        /* if (type instanceof FunctionType)  */ {
            return /* access */ .parent;
        }
    }
    return oldCaller /*  : Caller */;
}
/* private static */ resolveType(value, Value, state, CompileState);
Type;
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
/* private static */ invocationHeader(state, CompileState, depth, number, callerString1, string);
[CompileState, Caller];
{
    /* if (callerString1.startsWith("new "))  */ {
        let input1 = callerString1 /*  : string */.substring("new ".length( /*  */) /* : unknown */) /* : unknown */;
        let map = /* parseType */ (state /*  : CompileState */,  /* input1 */) /* : unknown */.map((type) => {
            let right = /* type */ .right;
            return new [CompileState, Caller](left, new ConstructionCaller( /* right */) /* : ConstructionCaller */) /* : [CompileState, Caller] */;
        }) /* : unknown */;
        /* if (map.isPresent())  */ {
            return /* map */ .orElse( /* null */) /* : unknown */;
        }
    }
    let tuple = /* parseValue */ (state /*  : CompileState */, callerString1 /*  : string */, depth /*  : number */) /* : unknown */;
    return new /* Tuple */ ( /* tuple */.left, /* tuple */ .right) /* : content-start Tuple content-end */;
}
/* private static */ foldInvocationStart(state, DivideState, c, char);
DivideState;
{
    let appended = state /*  : DivideState */.append(c /*  : char */) /* : unknown */;
    /* if (c == '(')  */ {
        let enter = /* appended */ .enter( /*  */) /* : unknown */;
        /* if (enter.isShallow())  */ {
            return /* enter */ .advance( /*  */) /* : unknown */;
        }
        return /* enter */;
    }
    /* if (c == ')')  */ {
        return /* appended */ .exit( /*  */) /* : unknown */;
    }
    return /* appended */;
}
/* private static */ parseDataAccess(state, CompileState, input, string, depth, number);
({
    return /* last */(input) { } /*  : string */, /*  : string */ : /*  : string */ .strip( /*  */) /* : unknown */, ".": ,
}(parentString, unknown, rawProperty, unknown));
{
    let property = /* rawProperty */ .strip( /*  */) /* : unknown */;
    /* if (!isSymbol(property))  */ {
        return new /* None */ ( /*  */) /* : content-start None content-end */;
    }
    let tuple = /* parseValue */ (state /*  : CompileState */,  /* parentString */, depth /*  : number */) /* : unknown */;
    let parent = /* tuple */ .right;
    let type = /* resolveType */ ( /* parent */, state /*  : CompileState */) /* : unknown */;
    /* if (type instanceof TupleType)  */ {
        /* if (property.equals("left"))  */ {
            return new /* Some */ (new /* Tuple */ (state /*  : CompileState */, new IndexValue(new SymbolValue("0", Int) /* : SymbolValue */) /* : IndexValue */) /* : content-start Tuple content-end */) /* : content-start Some content-end */;
        }
        /* if (property.equals("type"))  */ {
            return new /* Some */ (new /* Tuple */ (state /*  : CompileState */, new IndexValue(new SymbolValue("1", Int) /* : SymbolValue */) /* : IndexValue */) /* : content-start Tuple content-end */) /* : content-start Some content-end */;
        }
    }
    return new /* Some */ (new /* Tuple */ ( /* tuple */.left, new DataAccess( /* parent */) /* : DataAccess */) /* : content-start Tuple content-end */) /* : content-start Some content-end */;
}
;
/* private static */ parseString(state, CompileState, input, string);
({
    let, stripped: , var:  = input /*  : string */.strip( /*  */) /* : unknown */
});
/* if (stripped.startsWith("\"") && stripped.endsWith("\""))  */ {
    return new /* Some */ (new /* Tuple */ (state /*  : CompileState */, new StringValue( /* stripped */) /* : StringValue */) /* : content-start Tuple content-end */) /* : content-start Some content-end */;
}
return new /* None */ ( /*  */) /* : content-start None content-end */;
/* private static */ parseSymbolValue(state, CompileState, value, string);
({
    let, stripped: , var:  = value /*  : string */.strip( /*  */) /* : unknown */
});
/* if (isSymbol(stripped))  */ {
    /* if (state.resolveValue(stripped) instanceof Some(var type))  */ {
        return new /* Some */ (new /* Tuple */ (state /*  : CompileState */, new SymbolValue( /* stripped */) /* : SymbolValue */) /* : content-start Tuple content-end */) /* : content-start Some content-end */;
    }
    return new /* Some */ (new /* Tuple */ (state /*  : CompileState */, new Placeholder( /* stripped */) /* : Placeholder */) /* : content-start Tuple content-end */) /* : content-start Some content-end */;
}
return new /* None */ ( /*  */) /* : content-start None content-end */;
/* private static */ parseOperation(state, CompileState, value, string, depth, number, infix, string);
({
    return /* first */(value /*  : string */, infix /*  : string */) { }
}(s, unknown, s2, unknown));
{
    let tuple = /* parseValue */ (state /*  : CompileState */,  /* s */, depth /*  : number */) /* : unknown */;
    let tuple1 = /* parseValue */ ( /* tuple */.left,  /* s2 */, depth /*  : number */) /* : unknown */;
    let left = /* tuple */ .right;
    let right = /* tuple1 */ .right;
    return new /* Some */ (new /* Tuple */ ( /* tuple1 */.left, new Operation(infix /*  : string */) /* : Operation */) /* : content-start Tuple content-end */) /* : content-start Some content-end */;
}
;
/* private static */ compileValues(state, CompileState, params, string, mapper, (arg0, arg1) => [CompileState, string]);
[CompileState, string];
{
    let parsed = /* parseValuesOrEmpty */ (state /*  : CompileState */, params /*  : string */, (state1, s) => new /* Some */ (mapper /*  : (arg0 : CompileState, arg1 : string) => [CompileState, string] */( /* state1 */) /* : [CompileState, string] */) /* : content-start Some content-end */) /* : unknown */;
    let generated = /* generateValues */ ( /* parsed */.right) /* : unknown */;
    return new /* Tuple */ ( /* parsed */.left,  /* generated */) /* : content-start Tuple content-end */;
}
/* private static */ generateValues(elements, /* List */ );
string;
{
    return /* generateAll */ ( /* Main */.mergeValues, elements /*  : content-start List content-end<string> */) /* : unknown */;
}
/* private static  */ parseValuesOrEmpty(state, CompileState, input, string, mapper, (arg0, arg1) => /* Option */ );
[CompileState, /* List */ ];
{
    return /* parseValues */ (state /*  : CompileState */, input /*  : string */, mapper /*  : (arg0 : CompileState, arg1 : string) => content-start Option content-end<[CompileState, T]> */) /* : unknown */.orElseGet(() => new /* Tuple */ (state /*  : CompileState */, /* Lists */ .empty( /*  */) /* : unknown */) /* : content-start Tuple content-end */) /* : unknown */;
}
/* private static  */ parseValues(state, CompileState, input, string, mapper, (arg0, arg1) => /* Option */ );
({
    return /* getCompileStateListTuple */(state /*  : CompileState */, input /*  : string */) { } /* Main */, /* Main */ : /* Main */ .foldValueChar, mapper /*  : (arg0 : CompileState, arg1 : string) => content-start Option content-end<[CompileState, T]> */
});
/* private static */ compileParameter(state, CompileState, input, string);
[CompileState, Parameter];
{
    /* if (input.isBlank())  */ {
        return new /* Tuple */ (state /*  : CompileState */, new Whitespace( /*  */) /* : Whitespace */) /* : content-start Tuple content-end */;
    }
    return /* parseDefinition */ (state /*  : CompileState */, input /*  : string */) /* : unknown */.map((tuple) => new [CompileState, Parameter](left, right) /* : [CompileState, Parameter] */) /* : unknown */.orElseGet(() => new /* Tuple */ (state /*  : CompileState */, new Placeholder(input /*  : string */) /* : Placeholder */) /* : content-start Tuple content-end */) /* : unknown */;
}
/* private static */ compileDefinition(state, CompileState, input, string);
[CompileState, string];
{
    return /* parseDefinition */ (state /*  : CompileState */, input /*  : string */) /* : unknown */.map((tuple) => new /* Tuple */ ( /* tuple */.left, /* tuple */ .right.generate( /*  */) /* : unknown */) /* : content-start Tuple content-end */) /* : unknown */.orElseGet(() => new /* Tuple */ (state /*  : CompileState */, /* generatePlaceholder */ (input /*  : string */) /* : unknown */) /* : content-start Tuple content-end */) /* : unknown */;
}
/* private static */ mergeValues(cache, StringBuilder, element, string);
StringBuilder;
{
    /* if (cache.isEmpty())  */ {
        return cache /*  : StringBuilder */.append(element /*  : string */) /* : unknown */;
    }
    return cache /*  : StringBuilder */.append(", ") /* : unknown */.append(element /*  : string */) /* : unknown */;
}
/* private static */ createIndent(depth, number);
string;
{
    return "\n" + "\t".repeat(depth /*  : number */) /* : unknown */;
}
/* private static */ compileDefinitionStatement(input, string, depth, number, state, CompileState);
({
    return /* suffix */(input) { } /*  : string */, /*  : string */ : /*  : string */ .strip( /*  */) /* : unknown */, ";": ,
}(withoutEnd, unknown));
{
    return /* parseDefinition */ (state /*  : CompileState */,  /* withoutEnd */) /* : unknown */.map((result) => {
        let generated = /* createIndent */ (depth /*  : number */) /* : unknown */ + /* result */ .right.generate( /*  */) /* : unknown */ + ";";
        return new /* Tuple */ ( /* result */.left,  /* generated */) /* : content-start Tuple content-end */;
    }) /* : unknown */;
}
;
/* private static */ parseDefinition(state, CompileState, input, string);
({
    return /* last */(input) { } /*  : string */, /*  : string */ : /*  : string */ .strip( /*  */) /* : unknown */, " ": ,
}(beforeName, unknown, name, unknown));
{
    return /* split */ (() => /* toLast */ ( /* beforeName */, " ", /* Main */ .foldTypeSeparator) /* : unknown */, (beforeType, type) => {
        return /* suffix */ ( /* beforeType */.strip( /*  */) /* : unknown */, ">", (withoutTypeParamStart) => {
            return /* first */ ( /* withoutTypeParamStart */, "<", (beforeTypeParams, typeParamsString) => {
                let /* final */ compileStateStringTupleBiFunction = (state1, s) => new /* Tuple */ ( /* state1 */, /* s */ .strip( /*  */) /* : unknown */) /* : content-start Tuple content-end */;
                let typeParams = /* parseValuesOrEmpty */ (state /*  : CompileState */,  /* typeParamsString */, (state1, s) => new /* Some */ ( /* compileStateStringTupleBiFunction */.apply( /* state1 */) /* : unknown */) /* : content-start Some content-end */) /* : unknown */;
                return /* assembleDefinition */ ( /* typeParams */.left, new /* Some */ ( /* beforeTypeParams */) /* : content-start Some content-end<string> */,  /* name */, /* typeParams */ .right,  /* type */) /* : unknown */;
            }) /* : unknown */;
        }) /* : unknown */.or(() => {
            return /* assembleDefinition */ (state /*  : CompileState */, new /* Some */ ( /* beforeType */) /* : content-start Some content-end<string> */,  /* name */, /* Lists */ .empty( /*  */) /* : unknown */,  /* type */) /* : unknown */;
        }) /* : unknown */;
    }) /* : unknown */.or(() => {
        return /* assembleDefinition */ (state /*  : CompileState */, new /* None */ ( /*  */) /* : content-start None content-end<string> */,  /* name */, /* Lists */ .empty( /*  */) /* : unknown */,  /* beforeName */) /* : unknown */;
    }) /* : unknown */;
}
;
/* private static */ toLast(input, string, separator, string, folder, (arg0, arg1) => DivideState);
({
    let, divisions: , var:  = /* divideAll */ (input /*  : string */, folder /*  : (arg0 : DivideState, arg1 : Character) => DivideState */) /* : unknown */,
    return /* divisions */: /* divisions */ .removeLast( /*  */) /* : unknown */.map((removed) => {
        let left = /* removed */ .left.iterate( /*  */) /* : unknown */.collect(new Joiner(separator /*  : string */) /* : Joiner */) /* : unknown */.orElse("") /* : unknown */;
        let right = /* removed */ .right;
        return new /* Tuple */ ( /* left */,  /* right */) /* : content-start Tuple content-end */;
    }) /* : unknown */
});
/* private static */ foldTypeSeparator(state, DivideState, c, Character);
DivideState;
{
    /* if (c == ' ' && state.isLevel())  */ {
        return state /*  : DivideState */.advance( /*  */) /* : unknown */;
    }
    let appended = state /*  : DivideState */.append(c /*  : Character */) /* : unknown */;
    /* if (c == '<')  */ {
        return /* appended */ .enter( /*  */) /* : unknown */;
    }
    /* if (c == '>')  */ {
        return /* appended */ .exit( /*  */) /* : unknown */;
    }
    return /* appended */;
}
/* private static */ assembleDefinition(state, CompileState, beforeTypeParams, /* Option */ , name, string, typeParams, /* List */ , type, string);
({
    return /* parseType */(state /*  : CompileState */, type /*  : string */) { } /* : unknown */, /* : unknown */ : /* : unknown */ .map((type1) => {
        let node = new Definition(beforeTypeParams /*  : content-start Option content-end<string> */) /* type1 */.right, name;
    }) /*  : string */.strip( /*  */) /* : unknown */, typeParams /*  : content-start List content-end<string> */,
    return: new /* Tuple */ ( /* type1 */.left,  /* node */) /* : content-start Tuple content-end */
});
;
/* private static */ foldValueChar(state, DivideState, c, char);
DivideState;
{
    /* if (c == ',' && state.isLevel())  */ {
        return state /*  : DivideState */.advance( /*  */) /* : unknown */;
    }
    let appended = state /*  : DivideState */.append(c /*  : char */) /* : unknown */;
    /* if (c == '-')  */ {
        let peeked = /* appended */ .peek( /*  */) /* : unknown */;
        /* if (peeked == '>')  */ {
            return /* appended */ .popAndAppendToOption( /*  */) /* : unknown */.orElse( /* appended */) /* : unknown */;
        }
        /* else  */ {
            return /* appended */;
        }
    }
    /* if (c == '<' || c == '(' || c == '{')  */ {
        return /* appended */ .enter( /*  */) /* : unknown */;
    }
    /* if (c == '>' || c == ')' || c == '}')  */ {
        return /* appended */ .exit( /*  */) /* : unknown */;
    }
    return /* appended */;
}
/* private static */ compileType(state, CompileState, input, string);
({
    return /* parseType */(state /*  : CompileState */, input /*  : string */) { } /* : unknown */, /* : unknown */ : /* : unknown */ .map((tuple) => new /* Tuple */ ( /* tuple */.left, /* tuple */ .right.generate( /*  */) /* : unknown */) /* : content-start Tuple content-end */) /* : unknown */
});
/* private static */ parseType(state, CompileState, input, string);
({
    let, stripped: , var:  = input /*  : string */.strip( /*  */) /* : unknown */
});
/* if (stripped.equals("int") || stripped.equals("Integer"))  */ {
    return new /* Some */ (new /* Tuple */ (state /*  : CompileState */, /* Primitive */ .Int) /* : content-start Tuple content-end */) /* : content-start Some content-end */;
}
/* if (stripped.equals("String"))  */ {
    return new /* Some */ (new /* Tuple */ (state /*  : CompileState */, /* Primitive */ .String) /* : content-start Tuple content-end */) /* : content-start Some content-end */;
}
/* if (stripped.equals("type"))  */ {
    return new /* Some */ (new /* Tuple */ (state /*  : CompileState */, /* Primitive */ .Unknown) /* : content-start Tuple content-end */) /* : content-start Some content-end */;
}
/* if (isSymbol(stripped))  */ {
    return new /* Some */ (new /* Tuple */ (state /*  : CompileState */, new SymbolType( /* stripped */) /* : SymbolType */) /* : content-start Tuple content-end */) /* : content-start Some content-end */;
}
return /* parseTemplate */ (state /*  : CompileState */, input /*  : string */) /* : unknown */.or(() => /* varArgs */ (state /*  : CompileState */, input /*  : string */) /* : unknown */) /* : unknown */;
/* private static */ varArgs(state, CompileState, input, string);
({
    return /* suffix */(input /*  : string */, ) { }
}(s, unknown));
{
    return /* parseType */ (state /*  : CompileState */,  /* s */) /* : unknown */.map((inner) => {
        let newState = /* inner */ .left;
        let child = /* inner */ .right;
        return new /* Tuple */ ( /* newState */, new ArrayType( /* child */) /* : ArrayType */) /* : content-start Tuple content-end */;
    }) /* : unknown */;
}
;
/* private static */ parseTemplate(state, CompileState, input, string);
({
    return /* suffix */(input) { } /*  : string */, /*  : string */ : /*  : string */ .strip( /*  */) /* : unknown */, ">": ,
}(withoutEnd, unknown));
{
    return /* first */ ( /* withoutEnd */, "<", (base, argumentsString) => {
        let strippedBase = /* base */ .strip( /*  */) /* : unknown */;
        return /* parseValues */ (state /*  : CompileState */,  /* argumentsString */, /* Main */ .argument) /* : unknown */.map((argumentsTuple) => {
            return /* assembleTemplate */ ( /* strippedBase */, /* argumentsTuple */ .left, /* argumentsTuple */ .right) /* : unknown */;
        }) /* : unknown */;
    }) /* : unknown */;
}
;
/* private static */ assembleTemplate(base, string, state, CompileState, arguments, /* List */ );
[CompileState, Type];
{
    let children = arguments /*  : content-start List content-end<Argument> */.iterate( /*  */) /* : unknown */.map(retainType) /* : unknown */.flatMap(fromOption) /* : unknown */.collect(new /* ListCollector */ ( /*  */) /* : content-start ListCollector content-end */) /* : unknown */;
    /* if (base.equals("BiFunction"))  */ {
        return new /* Tuple */ (state /*  : CompileState */, new FunctionType(of(get(0 /*  : number */) /* : unknown */, get(1 /*  : number */) /* : unknown */) /* : unknown */, get(2 /*  : number */) /* : unknown */) /* : FunctionType */) /* : content-start Tuple content-end */;
    }
    /* if (base.equals("Function"))  */ {
        return new /* Tuple */ (state /*  : CompileState */, new FunctionType(of(get(0 /*  : number */) /* : unknown */) /* : unknown */, get(1 /*  : number */) /* : unknown */) /* : FunctionType */) /* : content-start Tuple content-end */;
    }
    /* if (base.equals("Predicate"))  */ {
        return new /* Tuple */ (state /*  : CompileState */, new FunctionType(of(get(0 /*  : number */) /* : unknown */) /* : unknown */, Boolean) /* : FunctionType */) /* : content-start Tuple content-end */;
    }
    /* if (base.equals("Supplier"))  */ {
        return new /* Tuple */ (state /*  : CompileState */, new FunctionType(empty( /*  */) /* : unknown */, get(0 /*  : number */) /* : unknown */) /* : FunctionType */) /* : content-start Tuple content-end */;
    }
    /* if (base.equals("Tuple") && children.size() >= 2)  */ {
        return new /* Tuple */ (state /*  : CompileState */, new TupleType( /* children */) /* : TupleType */) /* : content-start Tuple content-end */;
    }
    /* if (state.resolveType(base) instanceof Some(var baseType))  */ {
        return new /* Tuple */ (state /*  : CompileState */, new Template( /* baseType */) /* : Template */) /* : content-start Tuple content-end */;
    }
    /* else  */ {
        return new /* Tuple */ (state /*  : CompileState */, new Template(new Placeholder(base /*  : string */) /* : Placeholder */) /* : Template */) /* : content-start Tuple content-end */;
    }
}
/* private static */ retainType(argument, Argument);
({
/* if (argument instanceof Type type)  */ });
/* if (argument instanceof Type type)  */ {
    return new /* Some */ ( /* type */) /* : content-start Some content-end */;
}
/* else  */ {
    return new /* None */ ( /*  */) /* : content-start None content-end<Type> */;
}
/* private static */ argument(state, CompileState, input, string);
({
/* if (input.isBlank())  */ });
/* if (input.isBlank())  */ {
    return new /* Some */ (new /* Tuple */ (state /*  : CompileState */, new Whitespace( /*  */) /* : Whitespace */) /* : content-start Tuple content-end */) /* : content-start Some content-end */;
}
return /* parseType */ (state /*  : CompileState */, input /*  : string */) /* : unknown */.map((tuple) => new /* Tuple */ ( /* tuple */.left, /* tuple */ .right) /* : content-start Tuple content-end */) /* : unknown */;
/* private static  */ last(input, string, infix, string, mapper, (arg0, arg1) => /* Option */ );
({
    return: infix /*  : string */(input /*  : string */, infix /*  : string */) /* Main */.findLast, mapper /*  : (arg0 : string, arg1 : string) => content-start Option content-end<T> */
});
/* private static */ findLast(input, string, infix, string);
({
    let, index: , var:  = input /*  : string */.lastIndexOf(infix /*  : string */) /* : unknown */
});
/* if (index == -1)  */ {
    return new /* None */ ( /*  */) /* : content-start None content-end<number> */;
}
return new /* Some */ ( /* index */) /* : content-start Some content-end */;
/* private static  */ first(input, string, infix, string, mapper, (arg0, arg1) => /* Option */ );
({
    return: infix /*  : string */(input /*  : string */, infix /*  : string */) /* Main */.findFirst, mapper /*  : (arg0 : string, arg1 : string) => content-start Option content-end<T> */
});
/* private static  */ infix(input, string, infix, string, locator, (arg0, arg1) => /* Option */ , mapper, (arg0, arg1) => /* Option */ );
({
    return /* split */() { }
}());
locator /*  : (arg0 : string, arg1 : string) => content-start Option content-end<number> */(input /*  : string */, infix /*  : string */) /* : content-start Option content-end<number> */.map((index) => {
    let left = input /*  : string */.substring(0 /*  : number */) /* : unknown */;
    let right = input /*  : string */.substring(/* index */ +infix /*  : string */.length( /*  */) /* : unknown */) /* : unknown */;
    return new /* Tuple */ ( /* left */,  /* right */) /* : content-start Tuple content-end */;
}) /* : unknown */, mapper; /*  : (arg0 : string, arg1 : string) => content-start Option content-end<T> */
;
/* private static  */ split(splitter, () => /* Option */ , mapper, (arg0, arg1) => /* Option */ );
({
    return: splitter /*  : () => content-start Option content-end<[string, string]> */( /*  */) /* : content-start Option content-end<[string, string]> */.flatMap((tuple) => mapper /*  : (arg0 : string, arg1 : string) => content-start Option content-end<T> */() /* tuple */.left) /* tuple */.right
});
/* private static */ findFirst(input, string, infix, string);
({
    let, index: , var:  = input /*  : string */.indexOf(infix /*  : string */) /* : unknown */
});
/* if (index == -1)  */ {
    return new /* None */ ( /*  */) /* : content-start None content-end<number> */;
}
return new /* Some */ ( /* index */) /* : content-start Some content-end */;
/* private static */ generatePlaceholder(input, string);
string;
{
    let replaced = input /*  : string */.replace("/*", "content-start") /* : unknown */.replace("*/", "content-end") /* : unknown */;
    return "/* " + replaced + " */";
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
} */
/*  */ 
