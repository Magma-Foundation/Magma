"use strict";
/* private */ class Some {
    constructor(value) {
    }
    /* @Override
        public  */ map(mapper) {
        return new Some(mapper /* : (arg0 : T) => R */(value /* : unknown */) /* : R */) /* : Some */;
    }
    /* @Override
        public */ orElse(other) {
        return /* this */ .value /* : unknown */;
    }
    /* @Override
        public */ filter(predicate) {
        /* if (predicate.test(this.value))  */ {
            return /* this */;
        }
        return new /* None */ ( /*  */) /* : content-start None content-end */;
    }
    /* @Override
        public */ orElseGet(supplier) {
        return /* this */ .value /* : unknown */;
    }
    /* @Override
        public */ or(other) {
        return /* this */;
    }
    /* @Override
        public  */ flatMap(mapper) {
        return mapper /* : (arg0 : T) => Option<R> */(value /* : unknown */) /* : Option<R> */;
    }
}
/* private static */ class None {
    /* @Override
        public  */ map(mapper) {
        return new None( /*  */) /* : None */;
    }
    /* @Override
        public */ orElse(other) {
        return other /* : T */;
    }
    /* @Override
        public */ filter(predicate) {
        return new None( /*  */) /* : None */;
    }
    /* @Override
        public */ orElseGet(supplier) {
        return supplier /* : () => T */( /*  */) /* : T */;
    }
    /* @Override
        public */ or(other) {
        return other /* : () => Option<T> */( /*  */) /* : Option<T> */;
    }
    /* @Override
        public  */ flatMap(mapper) {
        return new None( /*  */) /* : None */;
    }
}
/* private static */ class SingleHead {
    constructor() {
        this.let /* this.value  */ = value /* : T */;
        this.let /* this.retrieved  */ = ;
    }
}
/* @Override
    public */ next();
Option < T > {
/* if (this.retrieved)  */ };
/* if (this.retrieved)  */ {
    return new None( /*  */) /* : None */;
}
let /* this.retrieved  */ =  /* true */;
return new Some(value /* : unknown */) /* : Some */;
/* private static */ class EmptyHead {
    /* @Override
        public */ next() {
        return new None( /*  */) /* : None */;
    }
}
/* private */ class HeadedIterator {
    constructor(head) {
    }
    /* @Override
        public  */ fold(initial, folder) {
        let current = initial /* : R */;
        /* while (true)  */ {
            let finalCurrent =  /* current */;
            let optional = /* this */ .head /* : unknown */.next /* : unknown */( /*  */) /* : unknown */.map /* : unknown */((inner) => folder /* : (arg0 : R, arg1 : T) => R */( /* finalCurrent */) /* : R */) /* : unknown */;
            /* if (optional.isPresent())  */ {
                let /* current  */ = /* optional */ .orElse /* : unknown */( /* null */) /* : unknown */;
            }
            /* else  */ {
                return /* current */;
            }
        }
    }
    /* @Override
        public  */ map(mapper) {
        return new HeadedIterator(() => /* this */ .head /* : unknown */.next /* : unknown */( /*  */) /* : unknown */.map /* : unknown */(mapper /* : (arg0 : T) => R */) /* : unknown */) /* : HeadedIterator */;
    }
    /* @Override
        public  */ collect(collector) {
        return /* this */ .fold /* : unknown */(collector /* : Collector<T, R> */.createInitial /* : () => C */( /*  */) /* : unknown */, collector /* : Collector<T, R> */.fold /* : unknown */) /* : unknown */;
    }
    /* @Override
        public */ filter(predicate) {
        return /* this */ .flatMap /* : unknown */((element) => {
            /* if (predicate.test(element))  */ {
                return new HeadedIterator(new SingleHead( /* element */) /* : SingleHead */) /* : HeadedIterator */;
            }
            return new HeadedIterator(new EmptyHead( /*  */) /* : EmptyHead */) /* : HeadedIterator */;
        }) /* : unknown */;
    }
    /* @Override
        public */ next() {
        return /* this */ .head /* : unknown */.next /* : unknown */( /*  */) /* : unknown */;
    }
    /* @Override
        public  */ flatMap(f) {
        return new HeadedIterator(new /* FlatMapHead */ ( /* this */.head /* : unknown */, f /* : (arg0 : T) => Iterator<R> */) /* : content-start FlatMapHead content-end */) /* : HeadedIterator */;
    }
}
/* private static */ class RangeHead /*  */ {
    constructor() {
        this.let /* this.length  */ = length /* : number */;
    }
}
/* @Override
    public */ next();
Option < number > {
/* if (this.counter < this.length)  */ };
/* if (this.counter < this.length)  */ {
    let value = /* this */ .counter /* : unknown */;
    /* this.counter++ */ ;
    return new Some( /* value */) /* : Some */;
}
return new None( /*  */) /* : None */;
/* private static final */ class JVMList {
    constructor() {
        this.let /* this.elements  */ = elements /* : content-start java.util.List content-end<T> */;
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
    return /* this */: /* this */ .iterateWithIndices /* : unknown */( /*  */) /* : unknown */.map /* : unknown */() /* Tuple */.right /* : unknown */
};
/* @Override
        public */ removeLast();
Option < [(List), T] > {
/* if (this.elements.isEmpty())  */ };
/* if (this.elements.isEmpty())  */ {
    return new None( /*  */) /* : None */;
}
let slice = /* this */ .elements /* : unknown */.subList /* : unknown */(0 /* : number */, elements /* : unknown */.size /* : unknown */( /*  */) /* : unknown */ - 1 /* : number */) /* : unknown */;
let last = /* this */ .elements /* : unknown */.getLast /* : unknown */( /*  */) /* : unknown */;
return new Some(new [(List), T](new JVMList( /* slice */) /* : JVMList */) /* : [List<T>, T] */) /* : Some */;
/* @Override
        public */ get(index, number);
T;
{
    return /* this */ .elements /* : unknown */.get /* : unknown */(index /* : number */) /* : unknown */;
}
/* @Override
        public */ size();
number;
{
    return /* this */ .elements /* : unknown */.size /* : unknown */( /*  */) /* : unknown */;
}
/* @Override
        public */ isEmpty();
{
    return /* this */ .elements /* : unknown */.isEmpty /* : unknown */( /*  */) /* : unknown */;
}
/* @Override
        public */ addFirst(element, T);
List < T > {
    return:  /* this */
};
/* @Override
        public */ iterateWithIndices();
Iterator < [number, T] > {
    return: new HeadedIterator(new RangeHead() /* this */.elements /* : unknown */.size /* : unknown */( /*  */) /* : unknown */) /* : RangeHead */, /* : HeadedIterator */ : /* : HeadedIterator */ .map /* : unknown */((index) => new /* Tuple */ ( /* index */, /* this */ .elements /* : unknown */.get /* : unknown */( /* index */) /* : unknown */) /* : content-start Tuple content-end */) /* : unknown */
};
/* @Override
        public */ removeFirst();
Option < [T, (List)] > {
/* if (this.elements.isEmpty())  */ };
/* if (this.elements.isEmpty())  */ {
    return new None( /*  */) /* : None */;
}
let first = /* this */ .elements /* : unknown */.getFirst /* : unknown */( /*  */) /* : unknown */;
let slice = /* this */ .elements /* : unknown */.subList /* : unknown */(1 /* : number */, elements /* : unknown */.size /* : unknown */( /*  */) /* : unknown */) /* : unknown */;
return new Some(new [T, (List)](new JVMList( /* slice */) /* : JVMList */) /* : [T, List<T>] */) /* : Some */;
/* @Override
        public */ addAllLast(others, (List));
List < T > {
    let, initial: (List) =  /* this */,
    return: others /* : List<T> */.iterate /* : () => Iterator<T> */( /*  */) /* : unknown */.fold /* : unknown */( /* initial */) /* List */.addLast /* : unknown */
};
/* private static */ class Lists /*  */ {
    /* public static  */ empty() {
        return new JVMList( /*  */) /* : JVMList */;
    }
    /* public static  */ of(elements) {
        return new JVMList(new /* ArrayList */ ( /* Arrays */.asList /* : unknown */(elements /* : T[] */) /* : unknown */) /* : content-start ArrayList content-end */) /* : JVMList */;
    }
}
/* private */ class ObjectType /*  */ {
    constructor(name, definitions) {
    }
    /* @Override
        public */ generate() {
        return /* this */ .name /* : unknown */;
    }
    /* @Override
        public */ find(name) {
        return /* this */ .definitions /* : unknown */.iterate /* : unknown */( /*  */) /* : unknown */.filter /* : unknown */((definition) => /* definition */ .name /* : unknown */.equals /* : unknown */(name /* : string */) /* : unknown */) /* : unknown */.map /* : unknown */(type /* : unknown */) /* : unknown */.next /* : unknown */( /*  */) /* : unknown */;
    }
}
/* private */ class TypeParam /*  */ {
    constructor(value) {
    }
    /* @Override
        public */ generate() {
        return /* this */ .value /* : unknown */;
    }
}
/* private static */ class DivideState /*  */ {
    constructor() {
        this.let /* this.segments  */ = segments /* : List<string> */;
        this.let /* this.buffer  */ = buffer /* : content-start StringBuilder content-end */;
        this.let /* this.depth  */ = depth /* : number */;
        this.let /* this.input  */ = input /* : string */;
        this.let /* this.index  */ = index /* : number */;
    }
}
DivideState(input, string);
{
    /* this(input, 0, Lists.empty(), new StringBuilder(), 0) */ ;
}
/* private */ advance();
DivideState;
{
    let /* this.segments  */ = /* this */ .segments /* : unknown */.addLast /* : unknown */(buffer /* : unknown */.toString /* : unknown */( /*  */) /* : unknown */) /* : unknown */;
    let /* this.buffer  */ = new /* StringBuilder */ ( /*  */) /* : content-start StringBuilder content-end */;
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
{
    return /* this.depth == 1 */;
}
/* public */ pop();
Option < [/* Character */ , DivideState] > {
/* if (this.index < this.input.length())  */ };
/* if (this.index < this.input.length())  */ {
    let c = /* this */ .input /* : unknown */.charAt /* : unknown */(index /* : unknown */) /* : unknown */;
    return new Some(new /* Tuple */ ( /* c */, new DivideState(input /* : unknown */, index /* : unknown */ + 1 /* : number */, segments /* : unknown */, buffer /* : unknown */, depth /* : unknown */) /* : DivideState */) /* : content-start Tuple content-end */) /* : Some */;
}
return new None( /*  */) /* : None */;
/* public */ popAndAppendToTuple();
Option < [/* Character */ , DivideState] > {
    return /* this */: /* this */ .pop /* : unknown */( /*  */) /* : unknown */.map /* : unknown */((tuple) => new /* Tuple */ ( /* tuple */.left /* : unknown */, /* tuple */ .right /* : unknown */.append /* : unknown */() /* tuple */.left /* : unknown */) /* : unknown */) /* : content-start Tuple content-end */
};
/* public */ popAndAppendToOption();
Option < DivideState > {
    return /* this */: /* this */ .popAndAppendToTuple /* : unknown */( /*  */) /* : unknown */.map /* : unknown */() /* Tuple */.right /* : unknown */
};
/* public */ peek();
{
    return /* this */ .input /* : unknown */.charAt /* : unknown */(index /* : unknown */) /* : unknown */;
}
/* private */ class Joiner /*  */ {
    constructor(delimiter) {
    }
    /* @Override
        public */ createInitial() {
        return new None( /*  */) /* : None */;
    }
    /* @Override
        public */ fold(current, element) {
        return new Some(current /* : Option<string> */.map /* : (arg0 : (arg0 : T) => R) => Option<R> */((inner) => /* inner */ +.delimiter /* : unknown */ + element /* : string */) /* : unknown */.orElse /* : unknown */(element /* : string */) /* : unknown */) /* : Some */;
    }
}
/* private */ class Definition /*  */ {
    constructor(maybeBefore, name, type /* Type */, typeParams) {
    }
    /* private */ generate() {
        return /* this */ .generateWithParams /* : unknown */("") /* : unknown */;
    }
    /* public */ generateWithParams(params) {
        let joined = /* this */ .joinTypeParams /* : unknown */( /*  */) /* : unknown */;
        let before = /* this */ .joinBefore /* : unknown */( /*  */) /* : unknown */;
        let typeString = /* this */ .generateType /* : unknown */( /*  */) /* : unknown */;
        return /* before */ +.name /* : unknown */ + /* joined */ +params /* : string */ +  /* typeString */;
    }
    /* private */ generateType() {
        /* if (this.type.equals(Primitive.Unknown))  */ {
            return "";
        }
        return " : " + /* this */ .type /* : unknown */.generate /* : unknown */( /*  */) /* : unknown */;
    }
    /* private */ joinBefore() {
        return /* this */ .maybeBefore /* : unknown */.filter /* : unknown */((value) => !.isEmpty /* : unknown */( /*  */) /* : unknown */) /* : unknown */.map /* : unknown */(generatePlaceholder /* : unknown */) /* : unknown */.map /* : unknown */((inner) => /* inner */ +" ") /* : unknown */.orElse /* : unknown */("") /* : unknown */;
    }
    /* private */ joinTypeParams() {
        return /* this */ .typeParams /* : unknown */.iterate /* : unknown */( /*  */) /* : unknown */.collect /* : unknown */(new Joiner( /*  */) /* : Joiner */) /* : unknown */.map /* : unknown */((inner) => "<" + inner + ">") /* : unknown */.orElse /* : unknown */("") /* : unknown */;
    }
}
/* private static */ class ListCollector {
    /* @Override
        public */ createInitial() {
        return /* Lists */ .empty /* : unknown */( /*  */) /* : unknown */;
    }
    /* @Override
        public */ fold(current, element) {
        return current /* : List<T> */.addLast /* : (arg0 : T) => List<T> */(element /* : T */) /* : unknown */;
    }
}
/* private */ class Tuple {
}
/* private static */ class FlatMapHead {
    constructor() {
        this.let /* this.mapper  */ = mapper /* : (arg0 : T) => Iterator<R> */;
        this.let /* this.current  */ = new None( /*  */) /* : None */;
        this.let /* this.head  */ = head /* : Head<T> */;
    }
}
/* @Override
    public */ next();
Option < R > {
/* while (true)  */ };
/* while (true)  */ {
    /* if (this.current.isPresent())  */ {
        let inner = /* this */ .current /* : unknown */.orElse /* : unknown */( /* null */) /* : unknown */;
        let maybe = /* inner */ .next /* : unknown */( /*  */) /* : unknown */;
        /* if (maybe.isPresent())  */ {
            return /* maybe */;
        }
        /* else  */ {
            let /* this.current  */ = new None( /*  */) /* : None */;
        }
    }
    let outer = /* this */ .head /* : unknown */.next /* : unknown */( /*  */) /* : unknown */;
    /* if (outer.isPresent())  */ {
        let /* this.current  */ = /* outer */ .map /* : unknown */(mapper /* : unknown */) /* : unknown */;
    }
    /* else  */ {
        return new None( /*  */) /* : None */;
    }
}
/* private */ class SymbolType /*  */ {
    constructor(input) {
    }
    /* @Override
        public */ generate() {
        return /* this */ .input /* : unknown */;
    }
}
/* private */ class ArrayType /*  */ {
    constructor(right /* Type */) {
    }
    /* @Override
        public */ generate() {
        return /* this */ .right /* : unknown */.generate /* : unknown */( /*  */) /* : unknown */ + "[]";
    }
}
/* private static */ class Whitespace /*  */ {
}
/* private static */ class Iterators /*  */ {
    /* public static  */ fromOption(option) {
        let single = option /* : Option<T> */.map /* : (arg0 : (arg0 : T) => R) => Option<R> */(new  /* : unknown */) /* : unknown */;
        return new HeadedIterator(orElseGet /* : unknown */(new  /* : unknown */) /* : unknown */) /* : HeadedIterator */;
    }
}
/* private */ class FunctionType /*  */ {
    constructor(arguments, returns /* Type */) {
    }
    /* @Override
        public */ generate() {
        let joined = /* this */ .arguments /* : unknown */( /*  */) /* : unknown */.iterateWithIndices /* : unknown */( /*  */) /* : unknown */.map /* : unknown */((pair) => "arg" + /* pair */ .left /* : unknown */ + " : " + /* pair */ .right /* : unknown */.generate /* : unknown */( /*  */) /* : unknown */) /* : unknown */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        return "(" + /* joined */ +") => " + /* this */ .returns /* : unknown */.generate /* : unknown */( /*  */) /* : unknown */;
    }
}
/* private */ class TupleType /*  */ {
    constructor(arguments) {
    }
    /* @Override
        public */ generate() {
        let joinedArguments = /* this */ .arguments /* : unknown */.iterate /* : unknown */( /*  */) /* : unknown */.map /* : unknown */(generate /* : unknown */) /* : unknown */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        return "[" + joinedArguments + "]";
    }
}
/* private */ class Template /*  */ {
    constructor(base /* FindableType */, arguments) {
    }
    /* @Override
        public */ generate() {
        let joinedArguments = /* this */ .arguments /* : unknown */.iterate /* : unknown */( /*  */) /* : unknown */.map /* : unknown */(generate /* : unknown */) /* : unknown */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.map /* : unknown */((inner) => "<" + inner + ">") /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        return /* this */ .base /* : unknown */.generate /* : unknown */( /*  */) /* : unknown */ +  /* joinedArguments */;
    }
    /* @Override
        public */ find(name) {
        return /* this */ .base /* : unknown */.find /* : unknown */(name /* : string */) /* : unknown */;
    }
}
/* private */ class Placeholder /*  */ {
    constructor(input) {
    }
    /* @Override
        public */ generate() {
        return /* generatePlaceholder */ ( /* this */.input /* : unknown */) /* : unknown */;
    }
}
return /* Primitive */ .Unknown /* : unknown */;
/* @Override
    public */ find(name, string);
Option <  /* Type */ > {
    return: new None( /*  */) /* : None */
};
/* private */ class StringValue /*  */ {
    constructor(stripped) {
    }
    /* @Override
        public */ generate() {
        return /* this */ .stripped /* : unknown */;
    }
}
return /* Primitive */ .Unknown /* : unknown */;
/* private */ class DataAccess /*  */ {
    constructor(parent /* Value */, property, type /* Type */) {
    }
    /* @Override
        public */ generate() {
        return /* this */ .parent /* : unknown */.generate /* : unknown */( /*  */) /* : unknown */ + "." + /* this */ .property /* : unknown */ + /* generatePlaceholder */ (": " + /* this */ .type /* : unknown */.generate /* : unknown */( /*  */) /* : unknown */) /* : unknown */;
    }
}
return /* Primitive */ .Unknown /* : unknown */;
/* private */ class ConstructionCaller /*  */ {
    constructor(type /* Type */) {
    }
    /* @Override
        public */ generate() {
        return "new " + /* this */ .type /* : unknown */.generate /* : unknown */( /*  */) /* : unknown */;
    }
}
/* private */ class Operation /*  */ {
    constructor(left /* Value */, infix, right /* Value */) {
    }
    /* @Override
        public */ generate() {
        return /* this */ .left /* : unknown */.generate /* : unknown */( /*  */) /* : unknown */ + " " + /* this */ .infix /* : unknown */ + " " + /* this */ .right /* : unknown */.generate /* : unknown */( /*  */) /* : unknown */;
    }
}
return /* Primitive */ .Unknown /* : unknown */;
/* private */ class Not /*  */ {
    constructor(value /* Value */) {
    }
    /* @Override
        public */ generate() {
        return "!" + /* this */ .value /* : unknown */.generate /* : unknown */( /*  */) /* : unknown */;
    }
}
return /* Primitive */ .Unknown /* : unknown */;
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
        let joined = /* this */ .parameterNames /* : unknown */.iterate /* : unknown */( /*  */) /* : unknown */.map /* : unknown */((inner) => /* inner */ +" : unknown") /* : unknown */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        return "(" + /* joined */ +") => " + /* this */ .body /* : unknown */.generate /* : unknown */( /*  */) /* : unknown */;
    }
}
return /* Primitive */ .Unknown /* : unknown */;
/* private */ class Invokable /*  */ {
    constructor(caller, arguments, type /* Type */) {
    }
    /* @Override
        public */ generate() {
        let joined = /* this */ .arguments /* : unknown */.iterate /* : unknown */( /*  */) /* : unknown */.map /* : unknown */(generate /* : unknown */) /* : unknown */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        return /* this */ .caller /* : unknown */.generate /* : unknown */( /*  */) /* : unknown */ + "(" + /* joined */ +")" + /* generatePlaceholder */ (": " + /* this */ .type /* : unknown */.generate /* : unknown */( /*  */) /* : unknown */) /* : unknown */;
    }
}
/* private */ class IndexValue /*  */ {
    constructor(parent /* Value */, child /* Value */) {
    }
    /* @Override
        public */ generate() {
        return /* this */ .parent /* : unknown */.generate /* : unknown */( /*  */) /* : unknown */ + "[" + this.child.generate() + "]";
    }
}
return /* Primitive */ .Unknown /* : unknown */;
/* private */ class SymbolValue /*  */ {
    constructor(stripped, type /* Type */) {
    }
    /* @Override
        public */ generate() {
        return /* this */ .stripped /* : unknown */ + /* generatePlaceholder */ (": " + /* this */ .type /* : unknown */.generate /* : unknown */( /*  */) /* : unknown */) /* : unknown */;
    }
}
/* public */ class Main /*  */ {
    /* private */ CompileState(structures, definitions, objectTypes, maybeStructName, typeParams) {
        /* this(Lists.empty(), Lists.empty(), Lists.empty(), new None<>(), Lists.empty()) */ ;
    }
}
/* private Option<Type> resolveValue(String name)  */ {
    return /* this */ .definitions /* : unknown */.iterate /* : unknown */( /*  */) /* : unknown */.filter /* : unknown */((definition) => /* definition */ .name /* : unknown */.equals /* : unknown */( /* name */) /* : unknown */) /* : unknown */.next /* : unknown */( /*  */) /* : unknown */.map /* : unknown */(type /* : unknown */) /* : unknown */;
}
/* public CompileState addStructure(String structure)  */ {
    return new /* CompileState */ ( /* this */.structures /* : unknown */.addLast /* : unknown */( /* structure */) /* : unknown */, /* this */ .definitions /* : unknown */, /* this */ .objectTypes /* : unknown */, /* this */ .maybeStructName /* : unknown */, /* this */ .typeParams /* : unknown */) /* : content-start CompileState content-end */;
}
/* public CompileState withDefinitions(List<Definition> definitions)  */ {
    return new /* CompileState */ ( /* this */.structures /* : unknown */, definitions /* : List<content-start Definition content-end> */, /* this */ .objectTypes /* : unknown */, /* this */ .maybeStructName /* : unknown */, /* this */ .typeParams /* : unknown */) /* : content-start CompileState content-end */;
}
/* public Option<Type> resolveType(String name)  */ {
    /* if (this.maybeStructName.filter(inner -> inner.equals(name)).isPresent())  */ {
        return new Some(new ObjectType(definitions /* : unknown */) /* : ObjectType */) /* : Some */;
    }
    let maybeTypeParam = /* this */ .typeParams /* : unknown */.iterate /* : unknown */( /*  */) /* : unknown */.filter /* : unknown */((param) => /* param */ .equals /* : unknown */( /* name */) /* : unknown */) /* : unknown */.next /* : unknown */( /*  */) /* : unknown */;
    /* if (maybeTypeParam instanceof Some(var value))  */ {
        return new Some(new TypeParam( /* value */) /* : TypeParam */) /* : Some */;
    }
    return /* this */ .objectTypes /* : unknown */.iterate /* : unknown */( /*  */) /* : unknown */.filter /* : unknown */((type) => /* type */ .name /* : unknown */.equals /* : unknown */( /* name */) /* : unknown */) /* : unknown */.next /* : unknown */( /*  */) /* : unknown */.map /* : unknown */((type) =>  /* type */) /* : unknown */;
}
/* public CompileState addType(ObjectType type)  */ {
    return new /* CompileState */ ( /* this */.structures /* : unknown */, /* this */ .definitions /* : unknown */, /* this */ .objectTypes /* : unknown */.addLast /* : unknown */( /* type */) /* : unknown */, /* this */ .maybeStructName /* : unknown */, /* this */ .typeParams /* : unknown */) /* : content-start CompileState content-end */;
}
/* public CompileState withDefinition(Definition definition)  */ {
    return new /* CompileState */ ( /* this */.structures /* : unknown */, /* this */ .definitions /* : unknown */.addLast /* : unknown */( /* definition */) /* : unknown */, /* this */ .objectTypes /* : unknown */, /* this */ .maybeStructName /* : unknown */, /* this */ .typeParams /* : unknown */) /* : content-start CompileState content-end */;
}
/* public CompileState withStructName(String name)  */ {
    return new /* CompileState */ ( /* this */.structures /* : unknown */, /* this */ .definitions /* : unknown */, /* this */ .objectTypes /* : unknown */, new Some( /* name */) /* : Some */, /* this */ .typeParams /* : unknown */) /* : content-start CompileState content-end */;
}
/* public CompileState withTypeParams(List<String> typeParams)  */ {
    return new /* CompileState */ ( /* this */.structures /* : unknown */, /* this */ .definitions /* : unknown */, /* this */ .objectTypes /* : unknown */, /* this */ .maybeStructName /* : unknown */, /* this */ .typeParams /* : unknown */.addAllLast /* : unknown */(typeParams /* : List<string> */) /* : unknown */) /* : content-start CompileState content-end */;
}
/* public static */ main();
{
    /* try  */ {
        let parent = /* Paths */ .get /* : unknown */(".", "src", "java", "magma") /* : unknown */;
        let source = /* parent */ .resolve /* : unknown */("Main.java") /* : unknown */;
        let target = /* parent */ .resolve /* : unknown */("main.ts") /* : unknown */;
        let input = /* Files */ .readString /* : unknown */( /* source */) /* : unknown */;
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
    let tuple = /* compileStatements */ (new /* CompileState */ ( /*  */) /* : content-start CompileState content-end */, input /* : string */, /* Main */ .compileRootSegment /* : unknown */) /* : unknown */;
    let joined = /* tuple */ .left /* : unknown */.structures /* : unknown */.iterate /* : unknown */( /*  */) /* : unknown */.collect /* : unknown */(new Joiner( /*  */) /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
    return /* joined */ +.right /* : unknown */;
}
/* private static */ compileStatements(state, input, string, mapper, (arg0 /* CompileState */, arg1) => [/* CompileState */ , string]);
[/* CompileState */ , string];
{
    let parsed = /* parseStatements */ (state /* : content-start CompileState content-end */, input /* : string */, mapper /* : (arg0 : content-start CompileState content-end, arg1 : string) => [content-start CompileState content-end, string] */) /* : unknown */;
    return new Tuple(left /* : unknown */, /* generateStatements */ ( /* parsed */.right /* : unknown */) /* : unknown */) /* : Tuple */;
}
/* private static */ generateStatements(statements, (List));
string;
{
    return /* generateAll */ ( /* Main */.mergeStatements /* : unknown */, statements /* : List<string> */) /* : unknown */;
}
/* private static */ parseStatements(state, input, string, mapper, (arg0 /* CompileState */, arg1) => [/* CompileState */ , string]);
[/* CompileState */ , (List)];
{
    return /* parseAll */ (state /* : content-start CompileState content-end */, input /* : string */, /* Main */ .foldStatementChar /* : unknown */, mapper /* : (arg0 : content-start CompileState content-end, arg1 : string) => [content-start CompileState content-end, string] */) /* : unknown */;
}
/* private static */ generateAll(merger, (arg0 /* StringBuilder */, arg1) =>  /* StringBuilder */, elements, (List));
string;
{
    return elements /* : List<string> */.iterate /* : () => Iterator<T> */( /*  */) /* : unknown */.fold /* : unknown */(new /* StringBuilder */ ( /*  */) /* : content-start StringBuilder content-end */, merger /* : (arg0 : content-start StringBuilder content-end, arg1 : string) => content-start StringBuilder content-end */) /* : unknown */.toString /* : unknown */( /*  */) /* : unknown */;
}
/* private static  */ parseAll(state, input, string, folder, (arg0, arg1 /* Character */) => DivideState, mapper, (arg0 /* CompileState */, arg1) => [/* CompileState */ , T]);
[/* CompileState */ , (List)];
{
    return /* getCompileStateListTuple */ (state /* : content-start CompileState content-end */, input /* : string */, folder /* : (arg0 : DivideState, arg1 : content-start Character content-end) => DivideState */, (state1, s) => new Some(mapper /* : (arg0 : content-start CompileState content-end, arg1 : string) => [content-start CompileState content-end, T] */( /* state1 */) /* : [content-start CompileState content-end, T] */) /* : Some */) /* : unknown */.orElseGet /* : unknown */(() => new Tuple(state /* : content-start CompileState content-end */, empty /* : unknown */( /*  */) /* : unknown */) /* : Tuple */) /* : unknown */;
}
/* private static  */ getCompileStateListTuple(state, input, string, folder, (arg0, arg1 /* Character */) => DivideState, mapper, (arg0 /* CompileState */, arg1) => Option);
Option < [/* CompileState */ , (List)] > {
    let, initial: (Option) = new Some(new Tuple(state /* : content-start CompileState content-end */) /* Lists */.empty /* : unknown */( /*  */) /* : unknown */) /* : Tuple */,
    return /* divideAll */(input /* : string */, folder /* : (arg0 : DivideState, arg1 : content-start Character content-end) => DivideState */) { } /* : unknown */, /* : unknown */ : /* : unknown */ .iterate /* : unknown */( /*  */) /* : unknown */.fold /* : unknown */((tuple, element) => {
        return /* tuple */ .flatMap /* : unknown */((inner) => {
            let state1 = /* inner */ .left /* : unknown */;
            let right = /* inner */ .right /* : unknown */;
            return mapper /* : (arg0 : content-start CompileState content-end, arg1 : string) => Option<[content-start CompileState content-end, T]> */( /* state1 */) /* : Option<[content-start CompileState content-end, T]> */.map /* : (arg0 : (arg0 : T) => R) => Option<R> */((applied) => {
                return new Tuple() /* applied */.left /* : unknown */, /* right */ .addLast /* : unknown */() /* applied */.right;
            } /* : unknown */);
        } /* : unknown */) /* : Tuple */;
    }) /* : unknown */
};
;
;
/* private static */ mergeStatements(stringBuilder, str, string);
{
    return stringBuilder /* : content-start StringBuilder content-end */.append /* : unknown */(str /* : string */) /* : unknown */;
}
/* private static */ divideAll(input, string, folder, (arg0, arg1 /* Character */) => DivideState);
List < string > {
    let, current: /* var */  = new DivideState(input /* : string */) /* : DivideState */
};
/* while (true)  */ {
    let maybePopped = /* current */ .pop /* : unknown */( /*  */) /* : unknown */.map /* : unknown */((tuple) => {
        return /* foldSingleQuotes */ ( /* tuple */) /* : unknown */.or /* : unknown */(() => /* foldDoubleQuotes */ ( /* tuple */) /* : unknown */) /* : unknown */.orElseGet /* : unknown */(() => folder /* : (arg0 : DivideState, arg1 : content-start Character content-end) => DivideState */(right /* : unknown */, left /* : unknown */) /* : DivideState */) /* : unknown */;
    }) /* : unknown */;
    /* if (maybePopped.isPresent())  */ {
        let /* current  */ = /* maybePopped */ .orElse /* : unknown */( /* current */) /* : unknown */;
    }
    /* else  */ {
        /* break */ ;
    }
}
return /* current */ .advance /* : unknown */( /*  */) /* : unknown */.segments /* : unknown */;
/* private static */ foldDoubleQuotes(tuple, [/* Character */ , DivideState]);
Option < DivideState > {
/* if (tuple.left == '\"')  */ };
/* if (tuple.left == '\"')  */ {
    let current = tuple /* : [content-start Character content-end, DivideState] */.right /* : unknown */.append /* : unknown */(tuple /* : [content-start Character content-end, DivideState] */[0 /* : number */]) /* : unknown */;
    /* while (true)  */ {
        let maybePopped = /* current */ .popAndAppendToTuple /* : unknown */( /*  */) /* : unknown */;
        /* if (maybePopped.isEmpty())  */ {
            /* break */ ;
        }
        let popped = /* maybePopped */ .orElse /* : unknown */( /* null */) /* : unknown */;
        let /* current  */ = /* popped */ .right /* : unknown */;
        /* if (popped.left == '\\')  */ {
            let /* current  */ = /* current */ .popAndAppendToOption /* : unknown */( /*  */) /* : unknown */.orElse /* : unknown */( /* current */) /* : unknown */;
        }
        /* if (popped.left == '\"')  */ {
            /* break */ ;
        }
    }
    return new Some( /* current */) /* : Some */;
}
return new None( /*  */) /* : None */;
/* private static */ foldSingleQuotes(tuple, [/* Character */ , DivideState]);
Option < DivideState > {
/* if (tuple.left != '\'')  */ };
/* if (tuple.left != '\'')  */ {
    return new None( /*  */) /* : None */;
}
let appended = tuple /* : [content-start Character content-end, DivideState] */.right /* : unknown */.append /* : unknown */(tuple /* : [content-start Character content-end, DivideState] */[0 /* : number */]) /* : unknown */;
return /* appended */ .popAndAppendToTuple /* : unknown */( /*  */) /* : unknown */.map /* : unknown */(foldEscaped /* : unknown */) /* : unknown */.flatMap /* : unknown */(popAndAppendToOption /* : unknown */) /* : unknown */;
/* private static */ foldEscaped(escaped, [/* Character */ , DivideState]);
DivideState;
{
    /* if (escaped.left == '\\')  */ {
        return escaped /* : [content-start Character content-end, DivideState] */.right /* : unknown */.popAndAppendToOption /* : unknown */( /*  */) /* : unknown */.orElse /* : unknown */(escaped /* : [content-start Character content-end, DivideState] */.right /* : unknown */) /* : unknown */;
    }
    return escaped /* : [content-start Character content-end, DivideState] */.right /* : unknown */;
}
/* private static */ foldStatementChar(state, DivideState, c);
DivideState;
{
    let append = state /* : DivideState */.append /* : unknown */(c /* : content-start char content-end */) /* : unknown */;
    /* if (c == ';' && append.isLevel())  */ {
        return /* append */ .advance /* : unknown */( /*  */) /* : unknown */;
    }
    /* if (c == '}' && append.isShallow())  */ {
        return /* append */ .advance /* : unknown */( /*  */) /* : unknown */.exit /* : unknown */( /*  */) /* : unknown */;
    }
    /* if (c == '{' || c == '(')  */ {
        return /* append */ .enter /* : unknown */( /*  */) /* : unknown */;
    }
    /* if (c == '}' || c == ')')  */ {
        return /* append */ .exit /* : unknown */( /*  */) /* : unknown */;
    }
    return /* append */;
}
/* private static */ compileRootSegment(state, input, string);
[/* CompileState */ , string];
{
    let stripped = input /* : string */.strip /* : unknown */( /*  */) /* : unknown */;
    /* if (stripped.startsWith("package ") || stripped.startsWith("import "))  */ {
        return new Tuple(state /* : content-start CompileState content-end */, "") /* : Tuple */;
    }
    return /* compileClass */ ( /* stripped */, 0 /* : number */, state /* : content-start CompileState content-end */) /* : unknown */.orElseGet /* : unknown */(() => new Tuple(state /* : content-start CompileState content-end */, /* generatePlaceholder */ ( /* stripped */) /* : unknown */) /* : Tuple */) /* : unknown */;
}
/* private static */ compileClass(stripped, string, depth, number, state);
Option < [/* CompileState */ , string] > {};
/* private static */ compileStructure(stripped, string, sourceInfix, string, targetInfix, string, state);
Option < [/* CompileState */ , string] > {
    return /* first */(stripped /* : string */, sourceInfix /* : string */) { }
}(beforeInfix, unknown, right, unknown);
{
    return /* first */ ( /* right */, "{", (beforeContent, withEnd) => {
        let strippedWithEnd = /* withEnd */ .strip /* : unknown */( /*  */) /* : unknown */;
        return /* suffix */ ( /* strippedWithEnd */, "}", (content1) => {
            return /* first */ ( /* beforeContent */, " implements ", (s, s2) => {
                return /* structureWithMaybeParams */ (targetInfix /* : string */, state /* : content-start CompileState content-end */,  /* beforeInfix */,  /* s */,  /* content1 */) /* : unknown */;
            }) /* : unknown */.or /* : unknown */(() => {
                return /* structureWithMaybeParams */ (targetInfix /* : string */, state /* : content-start CompileState content-end */,  /* beforeInfix */,  /* beforeContent */,  /* content1 */) /* : unknown */;
            }) /* : unknown */;
        }) /* : unknown */;
    }) /* : unknown */;
}
;
/* private static */ structureWithMaybeParams(targetInfix, string, state, beforeInfix, string, beforeContent, string, content1, string);
Option < [/* CompileState */ , string] > {
    return /* suffix */(beforeContent /* : string */, ) { }
}(s, unknown);
{
    return /* first */ ( /* s */, "(", (s1, s2) => {
        let parsed = /* parseParameters */ (state /* : content-start CompileState content-end */,  /* s2 */) /* : unknown */;
        return /* getOred */ (targetInfix /* : string */, /* parsed */ .left /* : unknown */, beforeInfix /* : string */,  /* s1 */, content1 /* : string */, /* parsed */ .right /* : unknown */) /* : unknown */;
    }) /* : unknown */;
}
or /* : unknown */(() => {
    return /* getOred */ (targetInfix /* : string */, state /* : content-start CompileState content-end */, beforeInfix /* : string */, beforeContent /* : string */, content1 /* : string */, /* Lists */ .empty /* : unknown */( /*  */) /* : unknown */) /* : unknown */;
}) /* : unknown */;
/* private static */ getOred(targetInfix, string, state, beforeInfix, string, beforeContent, string, content1, string, params, (List));
Option < [/* CompileState */ , string] > {
    return /* first */(beforeContent /* : string */, ) { }
}(name, unknown, withTypeParams, unknown);
{
    return /* first */ ( /* withTypeParams */, ">", (typeParamsString, afterTypeParams) => {
        let /* final */ compileStateStringTupleBiFunction = (state1, s) => new Tuple(strip /* : unknown */( /*  */) /* : unknown */) /* : Tuple */;
        let typeParams = /* parseValuesOrEmpty */ (state /* : content-start CompileState content-end */,  /* typeParamsString */, (state1, s) => new Some(apply /* : unknown */( /* state1 */) /* : unknown */) /* : Some */) /* : unknown */;
        return /* assembleStructure */ ( /* typeParams */.left /* : unknown */, targetInfix /* : string */, beforeInfix /* : string */,  /* name */, content1 /* : string */, /* typeParams */ .right /* : unknown */,  /* afterTypeParams */, params /* : List<Parameter> */) /* : unknown */;
    }) /* : unknown */;
}
or /* : unknown */(() => {
    return /* assembleStructure */ (state /* : content-start CompileState content-end */, targetInfix /* : string */, beforeInfix /* : string */, beforeContent /* : string */, content1 /* : string */, /* Lists */ .empty /* : unknown */( /*  */) /* : unknown */, "", params /* : List<Parameter> */) /* : unknown */;
}) /* : unknown */;
/* private static */ assembleStructure(state, targetInfix, string, beforeInfix, string, rawName, string, content, string, typeParams, (List), afterTypeParams, string, params, (List));
Option < [/* CompileState */ , string] > {
    let, name: /* var */  = rawName /* : string */.strip /* : unknown */( /*  */) /* : unknown */
};
/* if (!isSymbol(name))  */ {
    return new None( /*  */) /* : None */;
}
let joinedTypeParams = typeParams /* : List<string> */.iterate /* : () => Iterator<T> */( /*  */) /* : unknown */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.map /* : unknown */((inner) => "<" + inner + ">") /* : unknown */.orElse /* : unknown */("") /* : unknown */;
let parsed = /* parseStatements */ (state /* : content-start CompileState content-end */.withStructName /* : unknown */( /* name */) /* : unknown */.withTypeParams /* : unknown */(typeParams /* : List<string> */) /* : unknown */, content /* : string */, (state0, input) => /* compileClassSegment */ ( /* state0 */,  /* input */, 1 /* : number */) /* : unknown */) /* : unknown */;
/* List<String> parsed1 */ ;
/* if (params.isEmpty())  */ {
    let /* parsed1  */ = /* parsed */ .right /* : unknown */;
}
/* else  */ {
    let joined = /* joinValues */ ( /* retainDefinitions */(params /* : List<Parameter> */) /* : unknown */) /* : unknown */;
    let constructorIndent = /* createIndent */ (1 /* : number */) /* : unknown */;
    let /* parsed1  */ = /* parsed */ .right /* : unknown */.addFirst /* : unknown */(/* constructorIndent */ +"constructor (" + joined + ") {" + constructorIndent + "}\n") /* : unknown */;
}
let parsed2 = /* parsed1 */ .iterate /* : unknown */( /*  */) /* : unknown */.collect /* : unknown */(new Joiner( /*  */) /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
let generated = /* generatePlaceholder */ (beforeInfix /* : string */.strip /* : unknown */( /*  */) /* : unknown */) /* : unknown */ + targetInfix /* : string */ + /* name */ + +(afterTypeParams /* : string */) /* : unknown */ + " {" + parsed2 + "\n}\n";
return new Some(new Tuple(left /* : unknown */.addStructure /* : unknown */( /* generated */) /* : unknown */.addType /* : unknown */(new ObjectType(left /* : unknown */.definitions /* : unknown */) /* : ObjectType */) /* : unknown */, "") /* : Tuple */) /* : Some */;
/* private static */ retainDefinition(parameter, Parameter);
Option < Definition > {
/* if (parameter instanceof Definition definition)  */ };
/* if (parameter instanceof Definition definition)  */ {
    return new Some( /* definition */) /* : Some */;
}
return new None( /*  */) /* : None */;
/* private static */ isSymbol(input, string);
{
    /* for (var i = 0; i < input.length(); i++)  */ {
        let c = input /* : string */.charAt /* : unknown */( /* i */) /* : unknown */;
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
    return new None( /*  */) /* : None */;
}
let slice = input /* : string */.substring /* : unknown */(0 /* : number */, input /* : string */.length /* : unknown */( /*  */) /* : unknown */ - suffix /* : string */.length /* : unknown */( /*  */) /* : unknown */) /* : unknown */;
return mapper /* : (arg0 : string) => Option<T> */( /* slice */) /* : Option<T> */;
/* private static */ compileClassSegment(state, input, string, depth, number);
[/* CompileState */ , string];
{
    return /* compileWhitespace */ (input /* : string */, state /* : content-start CompileState content-end */) /* : unknown */.or /* : unknown */(() => /* compileClass */ (input /* : string */, depth /* : number */, state /* : content-start CompileState content-end */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* compileStructure */ (input /* : string */, "interface ", "interface ", state /* : content-start CompileState content-end */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* compileStructure */ (input /* : string */, "record ", "class ", state /* : content-start CompileState content-end */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* compileMethod */ (state /* : content-start CompileState content-end */, input /* : string */, depth /* : number */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* compileDefinitionStatement */ (input /* : string */, depth /* : number */, state /* : content-start CompileState content-end */) /* : unknown */) /* : unknown */.orElseGet /* : unknown */(() => new Tuple(state /* : content-start CompileState content-end */, /* generatePlaceholder */ (input /* : string */) /* : unknown */) /* : Tuple */) /* : unknown */;
}
/* private static */ compileWhitespace(input, string, state);
Option < [/* CompileState */ , string] > {
/* if (input.isBlank())  */ };
/* if (input.isBlank())  */ {
    return new Some(new Tuple(state /* : content-start CompileState content-end */, "") /* : Tuple */) /* : Some */;
}
return new None( /*  */) /* : None */;
/* private static */ compileMethod(state, input, string, depth, number);
Option < [/* CompileState */ , string] > {
    return /* first */(input /* : string */, ) { }
}(definitionString, unknown, withParams, unknown);
{
    return /* first */ ( /* withParams */, ")", (parametersString, rawContent) => {
        return /* parseDefinition */ (state /* : content-start CompileState content-end */,  /* definitionString */) /* : unknown */.flatMap /* : unknown */((definitionTuple) => {
            let definitionState = /* definitionTuple */ .left /* : unknown */;
            let definition = /* definitionTuple */ .right /* : unknown */;
            let parametersTuple = /* parseParameters */ ( /* definitionState */,  /* parametersString */) /* : unknown */;
            let rawParameters = /* parametersTuple */ .right /* : unknown */;
            let parameters = /* retainDefinitions */ ( /* rawParameters */) /* : unknown */;
            let joinedParameters = /* joinValues */ ( /* parameters */) /* : unknown */;
            let content = /* rawContent */ .strip /* : unknown */( /*  */) /* : unknown */;
            let indent = /* createIndent */ (depth /* : number */) /* : unknown */;
            let paramTypes = /* parameters */ .iterate /* : unknown */( /*  */) /* : unknown */.map /* : unknown */(type /* : unknown */) /* : unknown */.collect /* : unknown */(new ListCollector( /*  */) /* : ListCollector */) /* : unknown */;
            let toDefine = new Definition(name /* : unknown */, new FunctionType(type /* : unknown */) /* : FunctionType */) /* : Definition */;
            let generatedHeader = /* definition */ .generateWithParams /* : unknown */("(" + joinedParameters + ")") /* : unknown */;
            /* if (content.equals(";"))  */ {
                return new Some(new Tuple(left /* : unknown */.withDefinition /* : unknown */( /* toDefine */) /* : unknown */, /* indent */ + +";") /* : Tuple */) /* : Some */;
            }
            /* if (content.startsWith("{") && content.endsWith("}"))  */ {
                let substring = /* content */ .substring /* : unknown */(1 /* : number */, length /* : unknown */( /*  */) /* : unknown */ - 1 /* : number */) /* : unknown */;
                let statementsTuple = /* compileFunctionSegments */ ( /* parametersTuple */.left /* : unknown */.withDefinitions /* : unknown */( /* parameters */) /* : unknown */,  /* substring */, depth /* : number */) /* : unknown */;
                let generated = /* indent */ + +" {" + statementsTuple.right + indent + "}";
                return new Some(new Tuple(left /* : unknown */.withDefinition /* : unknown */( /* toDefine */) /* : unknown */) /* : Tuple */) /* : Some */;
            }
            return new None( /*  */) /* : None */;
        }) /* : unknown */;
    }) /* : unknown */;
}
;
/* private static */ joinValues(retainParameters, (List));
string;
{
    return retainParameters /* : List<Definition> */.iterate /* : () => Iterator<T> */( /*  */) /* : unknown */.map /* : unknown */(generate /* : unknown */) /* : unknown */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
}
/* private static */ retainDefinitions(right, (List));
List < Definition > {
    return: right /* : List<Parameter> */.iterate /* : () => Iterator<T> */( /*  */) /* : unknown */.map /* : unknown */() /* Main */.retainDefinition /* : unknown */, /* : unknown */ : /* : unknown */ .flatMap /* : unknown */() /* Iterators */.fromOption /* : unknown */, /* : unknown */ : /* : unknown */ .collect /* : unknown */(new ListCollector( /*  */) /* : ListCollector */) /* : unknown */
};
/* private static */ parseParameters(state, params, string);
[/* CompileState */ , (List)];
{
    return /* parseValuesOrEmpty */ (state /* : content-start CompileState content-end */, params /* : string */, (state1, s) => new Some(/* compileParameter */ ( /* state1 */,  /* s */) /* : unknown */) /* : Some */) /* : unknown */;
}
/* private static */ compileFunctionSegments(state, input, string, depth, number);
[/* CompileState */ , string];
{
    return /* compileStatements */ (state /* : content-start CompileState content-end */, input /* : string */, (state1, input1) => /* compileFunctionSegment */ ( /* state1 */,  /* input1 */, depth /* : number */ + 1 /* : number */) /* : unknown */) /* : unknown */;
}
/* private static */ compileFunctionSegment(state, input, string, depth, number);
[/* CompileState */ , string];
{
    let stripped = input /* : string */.strip /* : unknown */( /*  */) /* : unknown */;
    /* if (stripped.isEmpty())  */ {
        return new Tuple(state /* : content-start CompileState content-end */, "") /* : Tuple */;
    }
    return /* suffix */ ( /* stripped */, ";", (s) => {
        let tuple = /* statementValue */ (state /* : content-start CompileState content-end */,  /* s */, depth /* : number */) /* : unknown */;
        return new Some(new Tuple(left /* : unknown */, /* createIndent */ (depth /* : number */) /* : unknown */ + /* tuple */ .right /* : unknown */ + ";") /* : Tuple */) /* : Some */;
    }) /* : unknown */.or /* : unknown */(() => {
        return /* block */ (state /* : content-start CompileState content-end */, depth /* : number */,  /* stripped */) /* : unknown */;
    }) /* : unknown */.orElseGet /* : unknown */(() => {
        return new Tuple(state /* : content-start CompileState content-end */, /* generatePlaceholder */ ( /* stripped */) /* : unknown */) /* : Tuple */;
    }) /* : unknown */;
}
/* private static */ block(state, depth, number, stripped, string);
Option < [/* CompileState */ , string] > {
    return /* suffix */(stripped /* : string */, ) { }
}(withoutEnd, unknown);
{
    return /* split */ (() => {
        let divisions = /* divideAll */ ( /* withoutEnd */, /* Main */ .foldBlockStart /* : unknown */) /* : unknown */;
        return /* divisions */ .removeFirst /* : unknown */( /*  */) /* : unknown */.map /* : unknown */((removed) => {
            let right = /* removed */ .left /* : unknown */;
            let left = /* removed */ .right /* : unknown */.iterate /* : unknown */( /*  */) /* : unknown */.collect /* : unknown */(new Joiner("") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
            return new Tuple( /* right */) /* : Tuple */;
        }) /* : unknown */;
    }, (beforeContent, content) => {
        return /* suffix */ ( /* beforeContent */, "{", (s) => {
            let compiled = /* compileFunctionSegments */ (state /* : content-start CompileState content-end */,  /* content */, depth /* : number */) /* : unknown */;
            let indent = /* createIndent */ (depth /* : number */) /* : unknown */;
            return new Some(new Tuple(left /* : unknown */, /* indent */ +( /* s */) /* : unknown */ + "{" + compiled.right + indent + "}") /* : Tuple */) /* : Some */;
        }) /* : unknown */;
    }) /* : unknown */;
}
;
/* private static */ foldBlockStart(state, DivideState, c);
DivideState;
{
    let appended = state /* : DivideState */.append /* : unknown */(c /* : content-start Character content-end */) /* : unknown */;
    /* if (c == '{' && state.isLevel())  */ {
        return /* appended */ .advance /* : unknown */( /*  */) /* : unknown */;
    }
    /* if (c == '{')  */ {
        return /* appended */ .enter /* : unknown */( /*  */) /* : unknown */;
    }
    /* if (c == '}')  */ {
        return /* appended */ .exit /* : unknown */( /*  */) /* : unknown */;
    }
    return /* appended */;
}
/* private static */ statementValue(state, input, string, depth, number);
[/* CompileState */ , string];
{
    let stripped = input /* : string */.strip /* : unknown */( /*  */) /* : unknown */;
    /* if (stripped.startsWith("return "))  */ {
        let value = /* stripped */ .substring /* : unknown */("return ".length /* : unknown */( /*  */) /* : unknown */) /* : unknown */;
        let tuple = /* compileValue */ (state /* : content-start CompileState content-end */,  /* value */, depth /* : number */) /* : unknown */;
        return new Tuple(left /* : unknown */, "return " + /* tuple */ .right /* : unknown */) /* : Tuple */;
    }
    return /* first */ ( /* stripped */, "=", (s, s2) => {
        let definitionTuple = /* compileDefinition */ (state /* : content-start CompileState content-end */,  /* s */) /* : unknown */;
        let valueTuple = /* compileValue */ ( /* definitionTuple */.left /* : unknown */,  /* s2 */, depth /* : number */) /* : unknown */;
        return new Some(new Tuple(left /* : unknown */, "let " + /* definitionTuple */ .right /* : unknown */ + " = " + /* valueTuple */ .right /* : unknown */) /* : Tuple */) /* : Some */;
    }) /* : unknown */.orElseGet /* : unknown */(() => {
        return new Tuple(state /* : content-start CompileState content-end */, /* generatePlaceholder */ ( /* stripped */) /* : unknown */) /* : Tuple */;
    }) /* : unknown */;
}
/* private static */ compileValue(state, input, string, depth, number);
[/* CompileState */ , string];
{
    let tuple = /* parseValue */ (state /* : content-start CompileState content-end */, input /* : string */, depth /* : number */) /* : unknown */;
    return new Tuple(left /* : unknown */, right /* : unknown */.generate /* : unknown */( /*  */) /* : unknown */) /* : Tuple */;
}
/* private static */ parseValue(state, input, string, depth, number);
[/* CompileState */ , /* Value */];
{
    return /* parseLambda */ (state /* : content-start CompileState content-end */, input /* : string */, depth /* : number */) /* : unknown */.or /* : unknown */(() => /* parseString */ (state /* : content-start CompileState content-end */, input /* : string */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseDataAccess */ (state /* : content-start CompileState content-end */, input /* : string */, depth /* : number */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseSymbolValue */ (state /* : content-start CompileState content-end */, input /* : string */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseInvocation */ (state /* : content-start CompileState content-end */, input /* : string */, depth /* : number */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseOperation */ (state /* : content-start CompileState content-end */, input /* : string */, depth /* : number */, "+") /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseOperation */ (state /* : content-start CompileState content-end */, input /* : string */, depth /* : number */, "-") /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseDigits */ (state /* : content-start CompileState content-end */, input /* : string */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseNot */ (state /* : content-start CompileState content-end */, input /* : string */, depth /* : number */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseMethodReference */ (state /* : content-start CompileState content-end */, input /* : string */, depth /* : number */) /* : unknown */) /* : unknown */.orElseGet /* : unknown */(() => new [/* CompileState */ , /* Value */](state /* : content-start CompileState content-end */, new Placeholder(input /* : string */) /* : Placeholder */) /* : [content-start CompileState content-end, content-start Value content-end] */) /* : unknown */;
}
/* private static */ parseMethodReference(state, input, string, depth, number);
Option < [/* CompileState */ , /* Value */] > {
    return /* last */(input /* : string */, ) { }
}(s, unknown, s2, unknown);
{
    let tuple = /* parseValue */ (state /* : content-start CompileState content-end */,  /* s */, depth /* : number */) /* : unknown */;
    return new Some(new Tuple(left /* : unknown */, new DataAccess(right /* : unknown */, Unknown /* : unknown */) /* : DataAccess */) /* : Tuple */) /* : Some */;
}
;
/* private static */ parseNot(state, input, string, depth, number);
Option < [/* CompileState */ , /* Value */] > {
    let, stripped: /* var */  = input /* : string */.strip /* : unknown */( /*  */) /* : unknown */
};
/* if (stripped.startsWith("!"))  */ {
    let slice = /* stripped */ .substring /* : unknown */(1 /* : number */) /* : unknown */;
    let tuple = /* parseValue */ (state /* : content-start CompileState content-end */,  /* slice */, depth /* : number */) /* : unknown */;
    let value = /* tuple */ .right /* : unknown */;
    return new Some(new Tuple(left /* : unknown */, new Not( /* value */) /* : Not */) /* : Tuple */) /* : Some */;
}
return new None( /*  */) /* : None */;
/* private static */ parseLambda(state, input, string, depth, number);
Option < [/* CompileState */ , /* Value */] > {
    return /* first */(input /* : string */, ) { }
}(beforeArrow, unknown, valueString, unknown);
{
    let strippedBeforeArrow = /* beforeArrow */ .strip /* : unknown */( /*  */) /* : unknown */;
    /* if (isSymbol(strippedBeforeArrow))  */ {
        return /* assembleLambda */ (state /* : content-start CompileState content-end */, /* Lists */ .of /* : unknown */( /* strippedBeforeArrow */) /* : unknown */,  /* valueString */, depth /* : number */) /* : unknown */;
    }
    /* if (strippedBeforeArrow.startsWith("(") && strippedBeforeArrow.endsWith(")"))  */ {
        let parameterNames = /* divideAll */ ( /* strippedBeforeArrow */.substring /* : unknown */(1 /* : number */, length /* : unknown */( /*  */) /* : unknown */ - 1 /* : number */) /* : unknown */, /* Main */ .foldValueChar /* : unknown */) /* : unknown */.iterate /* : unknown */( /*  */) /* : unknown */.map /* : unknown */(strip /* : unknown */) /* : unknown */.filter /* : unknown */((value) => !.isEmpty /* : unknown */( /*  */) /* : unknown */) /* : unknown */.collect /* : unknown */(new ListCollector( /*  */) /* : ListCollector */) /* : unknown */;
        return /* assembleLambda */ (state /* : content-start CompileState content-end */,  /* parameterNames */,  /* valueString */, depth /* : number */) /* : unknown */;
    }
    return new None( /*  */) /* : None */;
}
;
/* private static */ assembleLambda(state, paramNames, (List), valueString, string, depth, number);
Some < [/* CompileState */ , /* Value */] > {
    let, strippedValueString: /* var */  = valueString /* : string */.strip /* : unknown */( /*  */) /* : unknown */
};
/* if (strippedValueString.startsWith("{") && strippedValueString.endsWith("}"))  */ {
    let value1 = /* compileStatements */ (state /* : content-start CompileState content-end */, /* strippedValueString */ .substring /* : unknown */(1 /* : number */, length /* : unknown */( /*  */) /* : unknown */ - 1 /* : number */) /* : unknown */, (state1, input1) => /* compileFunctionSegment */ ( /* state1 */,  /* input1 */, depth /* : number */ + 1 /* : number */) /* : unknown */) /* : unknown */;
    let right = /* value1 */ .right /* : unknown */;
    let /* value  */ = new Tuple(left /* : unknown */, new BlockLambdaValue(depth /* : number */) /* : BlockLambdaValue */) /* : Tuple */;
}
/* else  */ {
    let value1 = /* parseValue */ (state /* : content-start CompileState content-end */,  /* strippedValueString */, depth /* : number */) /* : unknown */;
    let /* value  */ = new Tuple(left /* : unknown */, right /* : unknown */) /* : Tuple */;
}
let right = /* value */ .right /* : unknown */;
return new Some(new Tuple(left /* : unknown */, new Lambda(paramNames /* : List<string> */) /* : Lambda */) /* : Tuple */) /* : Some */;
/* private static */ parseDigits(state, input, string);
Option < [/* CompileState */ , /* Value */] > {
    let, stripped: /* var */  = input /* : string */.strip /* : unknown */( /*  */) /* : unknown */
};
/* if (isNumber(stripped))  */ {
    return new Some(new [/* CompileState */ , /* Value */](state /* : content-start CompileState content-end */, new SymbolValue(Int /* : unknown */) /* : SymbolValue */) /* : [content-start CompileState content-end, content-start Value content-end] */) /* : Some */;
}
return new None( /*  */) /* : None */;
/* private static */ isNumber(input, string);
{
    /* for (var i = 0; i < input.length(); i++)  */ {
        let c = input /* : string */.charAt /* : unknown */( /* i */) /* : unknown */;
        /* if (Character.isDigit(c))  */ {
            /* continue */ ;
        }
        return /* false */;
    }
    return /* true */;
}
/* private static */ parseInvocation(state, input, string, depth, number);
Option < [/* CompileState */ , /* Value */] > {
    return /* suffix */(input) { } /* : string */, /* : string */ : /* : string */ .strip /* : unknown */( /*  */) /* : unknown */, ")": ,
}(withoutEnd, unknown);
{
    return /* split */ (() => /* toLast */ ( /* withoutEnd */, "", /* Main */ .foldInvocationStart /* : unknown */) /* : unknown */, (callerWithEnd, argumentsString) => {
        return /* suffix */ ( /* callerWithEnd */, "(", (callerString) => {
            let callerString1 = /* callerString */ .strip /* : unknown */( /*  */) /* : unknown */;
            let callerTuple = /* invocationHeader */ (state /* : content-start CompileState content-end */, depth /* : number */,  /* callerString1 */) /* : unknown */;
            let parsed = /* parseValues */ ( /* callerTuple */.left /* : unknown */,  /* argumentsString */, (state3, s) => new Some(/* parseValue */ ( /* state3 */,  /* s */, depth /* : number */) /* : unknown */) /* : Some */) /* : unknown */.orElseGet /* : unknown */(() => new Tuple(left /* : unknown */, empty /* : unknown */( /*  */) /* : unknown */) /* : Tuple */) /* : unknown */;
            let oldCaller = /* callerTuple */ .right /* : unknown */;
            let arguments = /* parsed */ .right /* : unknown */;
            let newCaller = /* modifyCaller */ ( /* parsed */.left /* : unknown */,  /* oldCaller */) /* : unknown */;
            let;
            var Unknown /* : unknown */;
            /* switch (newCaller)  */ {
                /* case ConstructionCaller constructionCaller ->  */ {
                    let /* var  */ = /* constructionCaller */ .type /* : unknown */;
                }
                /* case Value value ->  */ {
                    let type = /* value */ .type /* : unknown */( /*  */) /* : unknown */;
                    /* if (type instanceof FunctionType functionType)  */ {
                        let /* var  */ = /* functionType */ .returns /* : unknown */;
                    }
                }
            }
            let invokable = new Invokable( /* newCaller */) /* : Invokable */;
            return new Some(new Tuple(left /* : unknown */) /* : Tuple */) /* : Some */;
        }) /* : unknown */;
    }) /* : unknown */;
}
;
/* private static */ modifyCaller(state, oldCaller, Caller);
Caller;
{
    /* if (oldCaller instanceof DataAccess access)  */ {
        let type = /* resolveType */ ( /* access */.parent /* : unknown */, state /* : content-start CompileState content-end */) /* : unknown */;
        /* if (type instanceof FunctionType)  */ {
            return /* access */ .parent /* : unknown */;
        }
    }
    return oldCaller /* : Caller */;
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
        let input1 = callerString1 /* : string */.substring /* : unknown */("new ".length /* : unknown */( /*  */) /* : unknown */) /* : unknown */;
        let map = /* parseType */ (state /* : content-start CompileState content-end */,  /* input1 */) /* : unknown */.map /* : unknown */((type) => {
            let right = /* type */ .right /* : unknown */;
            return new [/* CompileState */ , Caller](left /* : unknown */, new ConstructionCaller( /* right */) /* : ConstructionCaller */) /* : [content-start CompileState content-end, Caller] */;
        }) /* : unknown */;
        /* if (map.isPresent())  */ {
            return /* map */ .orElse /* : unknown */( /* null */) /* : unknown */;
        }
    }
    let tuple = /* parseValue */ (state /* : content-start CompileState content-end */, callerString1 /* : string */, depth /* : number */) /* : unknown */;
    return new Tuple(left /* : unknown */, right /* : unknown */) /* : Tuple */;
}
/* private static */ foldInvocationStart(state, DivideState, c);
DivideState;
{
    let appended = state /* : DivideState */.append /* : unknown */(c /* : content-start char content-end */) /* : unknown */;
    /* if (c == '(')  */ {
        let enter = /* appended */ .enter /* : unknown */( /*  */) /* : unknown */;
        /* if (enter.isShallow())  */ {
            return /* enter */ .advance /* : unknown */( /*  */) /* : unknown */;
        }
        return /* enter */;
    }
    /* if (c == ')')  */ {
        return /* appended */ .exit /* : unknown */( /*  */) /* : unknown */;
    }
    return /* appended */;
}
/* private static */ parseDataAccess(state, input, string, depth, number);
Option < [/* CompileState */ , /* Value */] > {
    return /* last */(input) { } /* : string */, /* : string */ : /* : string */ .strip /* : unknown */( /*  */) /* : unknown */, ".": ,
}(parentString, unknown, rawProperty, unknown);
{
    let property = /* rawProperty */ .strip /* : unknown */( /*  */) /* : unknown */;
    /* if (!isSymbol(property))  */ {
        return new None( /*  */) /* : None */;
    }
    let tuple = /* parseValue */ (state /* : content-start CompileState content-end */,  /* parentString */, depth /* : number */) /* : unknown */;
    let parent = /* tuple */ .right /* : unknown */;
    let parentType = /* parent */ .type /* : unknown */( /*  */) /* : unknown */;
    /* if (parentType instanceof TupleType)  */ {
        /* if (property.equals("left"))  */ {
            return new Some(new Tuple(state /* : content-start CompileState content-end */, new IndexValue(new SymbolValue("0", Int /* : unknown */) /* : SymbolValue */) /* : IndexValue */) /* : Tuple */) /* : Some */;
        }
        /* if (property.equals("type"))  */ {
            return new Some(new Tuple(state /* : content-start CompileState content-end */, new IndexValue(new SymbolValue("1", Int /* : unknown */) /* : SymbolValue */) /* : IndexValue */) /* : Tuple */) /* : Some */;
        }
    }
    let type = /* Primitive */ .Unknown /* : unknown */;
    /* if (parentType instanceof FindableType objectType)  */ {
        /* if (objectType.find(property) instanceof Some(var memberType))  */ {
            let /* type  */ =  /* memberType */;
        }
    }
    return new Some(new Tuple(left /* : unknown */, new DataAccess( /* parent */) /* : DataAccess */) /* : Tuple */) /* : Some */;
}
;
/* private static */ parseString(state, input, string);
Option < [/* CompileState */ , /* Value */] > {
    let, stripped: /* var */  = input /* : string */.strip /* : unknown */( /*  */) /* : unknown */
};
/* if (stripped.startsWith("\"") && stripped.endsWith("\""))  */ {
    return new Some(new Tuple(state /* : content-start CompileState content-end */, new StringValue( /* stripped */) /* : StringValue */) /* : Tuple */) /* : Some */;
}
return new None( /*  */) /* : None */;
/* private static */ parseSymbolValue(state, value, string);
Option < [/* CompileState */ , /* Value */] > {
    let, stripped: /* var */  = value /* : string */.strip /* : unknown */( /*  */) /* : unknown */
};
/* if (isSymbol(stripped))  */ {
    /* if (state.resolveValue(stripped) instanceof Some(var type))  */ {
        return new Some(new Tuple(state /* : content-start CompileState content-end */, new SymbolValue( /* stripped */) /* : SymbolValue */) /* : Tuple */) /* : Some */;
    }
    return new Some(new Tuple(state /* : content-start CompileState content-end */, new Placeholder( /* stripped */) /* : Placeholder */) /* : Tuple */) /* : Some */;
}
return new None( /*  */) /* : None */;
/* private static */ parseOperation(state, value, string, depth, number, infix, string);
Option < [/* CompileState */ , /* Value */] > {
    return /* first */(value /* : string */, infix /* : string */) { }
}(s, unknown, s2, unknown);
{
    let tuple = /* parseValue */ (state /* : content-start CompileState content-end */,  /* s */, depth /* : number */) /* : unknown */;
    let tuple1 = /* parseValue */ ( /* tuple */.left /* : unknown */,  /* s2 */, depth /* : number */) /* : unknown */;
    let left = /* tuple */ .right /* : unknown */;
    let right = /* tuple1 */ .right /* : unknown */;
    return new Some(new Tuple(left /* : unknown */, new Operation(infix /* : string */) /* : Operation */) /* : Tuple */) /* : Some */;
}
;
/* private static */ compileValues(state, params, string, mapper, (arg0 /* CompileState */, arg1) => [/* CompileState */ , string]);
[/* CompileState */ , string];
{
    let parsed = /* parseValuesOrEmpty */ (state /* : content-start CompileState content-end */, params /* : string */, (state1, s) => new Some(mapper /* : (arg0 : content-start CompileState content-end, arg1 : string) => [content-start CompileState content-end, string] */( /* state1 */) /* : [content-start CompileState content-end, string] */) /* : Some */) /* : unknown */;
    let generated = /* generateValues */ ( /* parsed */.right /* : unknown */) /* : unknown */;
    return new Tuple(left /* : unknown */) /* : Tuple */;
}
/* private static */ generateValues(elements, (List));
string;
{
    return /* generateAll */ ( /* Main */.mergeValues /* : unknown */, elements /* : List<string> */) /* : unknown */;
}
/* private static  */ parseValuesOrEmpty(state, input, string, mapper, (arg0 /* CompileState */, arg1) => Option);
[/* CompileState */ , (List)];
{
    return /* parseValues */ (state /* : content-start CompileState content-end */, input /* : string */, mapper /* : (arg0 : content-start CompileState content-end, arg1 : string) => Option<[content-start CompileState content-end, T]> */) /* : unknown */.orElseGet /* : unknown */(() => new Tuple(state /* : content-start CompileState content-end */, empty /* : unknown */( /*  */) /* : unknown */) /* : Tuple */) /* : unknown */;
}
/* private static  */ parseValues(state, input, string, mapper, (arg0 /* CompileState */, arg1) => Option);
Option < [/* CompileState */ , (List)] > {
    return /* getCompileStateListTuple */(state /* : content-start CompileState content-end */, input /* : string */) { } /* Main */, /* Main */ : /* Main */ .foldValueChar /* : unknown */, mapper /* : (arg0 : content-start CompileState content-end, arg1 : string) => Option<[content-start CompileState content-end, T]> */
};
/* private static */ compileParameter(state, input, string);
[/* CompileState */ , Parameter];
{
    /* if (input.isBlank())  */ {
        return new Tuple(state /* : content-start CompileState content-end */, new Whitespace( /*  */) /* : Whitespace */) /* : Tuple */;
    }
    return /* parseDefinition */ (state /* : content-start CompileState content-end */, input /* : string */) /* : unknown */.map /* : unknown */((tuple) => new [/* CompileState */ , Parameter](left /* : unknown */, right /* : unknown */) /* : [content-start CompileState content-end, Parameter] */) /* : unknown */.orElseGet /* : unknown */(() => new Tuple(state /* : content-start CompileState content-end */, new Placeholder(input /* : string */) /* : Placeholder */) /* : Tuple */) /* : unknown */;
}
/* private static */ compileDefinition(state, input, string);
[/* CompileState */ , string];
{
    return /* parseDefinition */ (state /* : content-start CompileState content-end */, input /* : string */) /* : unknown */.map /* : unknown */((tuple) => new Tuple(left /* : unknown */, right /* : unknown */.generate /* : unknown */( /*  */) /* : unknown */) /* : Tuple */) /* : unknown */.orElseGet /* : unknown */(() => new Tuple(state /* : content-start CompileState content-end */, /* generatePlaceholder */ (input /* : string */) /* : unknown */) /* : Tuple */) /* : unknown */;
}
/* private static */ mergeValues(cache, element, string);
{
    /* if (cache.isEmpty())  */ {
        return cache /* : content-start StringBuilder content-end */.append /* : unknown */(element /* : string */) /* : unknown */;
    }
    return cache /* : content-start StringBuilder content-end */.append /* : unknown */(", ") /* : unknown */.append /* : unknown */(element /* : string */) /* : unknown */;
}
/* private static */ createIndent(depth, number);
string;
{
    return "\n" + "\t".repeat /* : unknown */(depth /* : number */) /* : unknown */;
}
/* private static */ compileDefinitionStatement(input, string, depth, number, state);
Option < [/* CompileState */ , string] > {
    return /* suffix */(input) { } /* : string */, /* : string */ : /* : string */ .strip /* : unknown */( /*  */) /* : unknown */, ";": ,
}(withoutEnd, unknown);
{
    return /* parseDefinition */ (state /* : content-start CompileState content-end */,  /* withoutEnd */) /* : unknown */.map /* : unknown */((result) => {
        let generated = /* createIndent */ (depth /* : number */) /* : unknown */ + /* result */ .right /* : unknown */.generate /* : unknown */( /*  */) /* : unknown */ + ";";
        return new Tuple(left /* : unknown */) /* : Tuple */;
    }) /* : unknown */;
}
;
/* private static */ parseDefinition(state, input, string);
Option < [/* CompileState */ , Definition] > {
    return /* last */(input) { } /* : string */, /* : string */ : /* : string */ .strip /* : unknown */( /*  */) /* : unknown */, " ": ,
}(beforeName, unknown, name, unknown);
{
    return /* split */ (() => /* toLast */ ( /* beforeName */, " ", /* Main */ .foldTypeSeparator /* : unknown */) /* : unknown */, (beforeType, type) => {
        return /* suffix */ ( /* beforeType */.strip /* : unknown */( /*  */) /* : unknown */, ">", (withoutTypeParamStart) => {
            return /* first */ ( /* withoutTypeParamStart */, "<", (beforeTypeParams, typeParamsString) => {
                let /* final */ compileStateStringTupleBiFunction = (state1, s) => new Tuple(strip /* : unknown */( /*  */) /* : unknown */) /* : Tuple */;
                let typeParams = /* parseValuesOrEmpty */ (state /* : content-start CompileState content-end */,  /* typeParamsString */, (state1, s) => new Some(apply /* : unknown */( /* state1 */) /* : unknown */) /* : Some */) /* : unknown */;
                return /* assembleDefinition */ ( /* typeParams */.left /* : unknown */, new Some( /* beforeTypeParams */) /* : Some<string> */,  /* name */, /* typeParams */ .right /* : unknown */,  /* type */) /* : unknown */;
            }) /* : unknown */;
        }) /* : unknown */.or /* : unknown */(() => {
            return /* assembleDefinition */ (state /* : content-start CompileState content-end */, new Some( /* beforeType */) /* : Some<string> */,  /* name */, /* Lists */ .empty /* : unknown */( /*  */) /* : unknown */,  /* type */) /* : unknown */;
        }) /* : unknown */;
    }) /* : unknown */.or /* : unknown */(() => {
        return /* assembleDefinition */ (state /* : content-start CompileState content-end */, new None( /*  */) /* : None<string> */,  /* name */, /* Lists */ .empty /* : unknown */( /*  */) /* : unknown */,  /* beforeName */) /* : unknown */;
    }) /* : unknown */;
}
;
/* private static */ toLast(input, string, separator, string, folder, (arg0, arg1 /* Character */) => DivideState);
Option < [string, string] > {
    let, divisions: /* var */  = /* divideAll */ (input /* : string */, folder /* : (arg0 : DivideState, arg1 : content-start Character content-end) => DivideState */) /* : unknown */,
    return /* divisions */: /* divisions */ .removeLast /* : unknown */( /*  */) /* : unknown */.map /* : unknown */((removed) => {
        let left = /* removed */ .left /* : unknown */.iterate /* : unknown */( /*  */) /* : unknown */.collect /* : unknown */(new Joiner(separator /* : string */) /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        let right = /* removed */ .right /* : unknown */;
        return new Tuple( /* left */) /* : Tuple */;
    }) /* : unknown */
};
/* private static */ foldTypeSeparator(state, DivideState, c);
DivideState;
{
    /* if (c == ' ' && state.isLevel())  */ {
        return state /* : DivideState */.advance /* : unknown */( /*  */) /* : unknown */;
    }
    let appended = state /* : DivideState */.append /* : unknown */(c /* : content-start Character content-end */) /* : unknown */;
    /* if (c == '<')  */ {
        return /* appended */ .enter /* : unknown */( /*  */) /* : unknown */;
    }
    /* if (c == '>')  */ {
        return /* appended */ .exit /* : unknown */( /*  */) /* : unknown */;
    }
    return /* appended */;
}
/* private static */ assembleDefinition(state, beforeTypeParams, (Option), name, string, typeParams, (List), type, string);
Option < [/* CompileState */ , Definition] > {
    return /* parseType */(state) { } /* : content-start CompileState content-end */, /* : content-start CompileState content-end */ : /* : content-start CompileState content-end */ .withTypeParams /* : unknown */(typeParams /* : List<string> */) /* : unknown */, type /* : string */, /* : unknown */ : /* : unknown */ .map /* : unknown */((type1) => {
        let node = new Definition(beforeTypeParams /* : Option<string> */, name /* : string */.strip /* : unknown */( /*  */) /* : unknown */) /* type1 */.right /* : unknown */, typeParams;
    } /* : List<string> */) /* : Definition */,
    return: new Tuple() /* type1 */.left /* : unknown */, /* node */
};
;
/* private static */ foldValueChar(state, DivideState, c);
DivideState;
{
    /* if (c == ',' && state.isLevel())  */ {
        return state /* : DivideState */.advance /* : unknown */( /*  */) /* : unknown */;
    }
    let appended = state /* : DivideState */.append /* : unknown */(c /* : content-start char content-end */) /* : unknown */;
    /* if (c == '-')  */ {
        let peeked = /* appended */ .peek /* : unknown */( /*  */) /* : unknown */;
        /* if (peeked == '>')  */ {
            return /* appended */ .popAndAppendToOption /* : unknown */( /*  */) /* : unknown */.orElse /* : unknown */( /* appended */) /* : unknown */;
        }
        /* else  */ {
            return /* appended */;
        }
    }
    /* if (c == '<' || c == '(' || c == '{')  */ {
        return /* appended */ .enter /* : unknown */( /*  */) /* : unknown */;
    }
    /* if (c == '>' || c == ')' || c == '}')  */ {
        return /* appended */ .exit /* : unknown */( /*  */) /* : unknown */;
    }
    return /* appended */;
}
/* private static */ compileType(state, input, string);
Option < [/* CompileState */ , string] > {
    return /* parseType */(state /* : content-start CompileState content-end */, input /* : string */) { } /* : unknown */, /* : unknown */ : /* : unknown */ .map /* : unknown */((tuple) => new Tuple() /* tuple */.left /* : unknown */) /* tuple */.right /* : unknown */.generate /* : unknown */( /*  */) /* : unknown */
};
/* private static */ parseType(state, input, string);
Option < [/* CompileState */ , /* Type */] > {
    let, stripped: /* var */  = input /* : string */.strip /* : unknown */( /*  */) /* : unknown */
};
/* if (stripped.equals("int") || stripped.equals("Integer"))  */ {
    return new Some(new Tuple(state /* : content-start CompileState content-end */, Int /* : unknown */) /* : Tuple */) /* : Some */;
}
/* if (stripped.equals("String"))  */ {
    return new Some(new Tuple(state /* : content-start CompileState content-end */, String /* : unknown */) /* : Tuple */) /* : Some */;
}
/* if (stripped.equals("type"))  */ {
    return new Some(new Tuple(state /* : content-start CompileState content-end */, Unknown /* : unknown */) /* : Tuple */) /* : Some */;
}
/* if (isSymbol(stripped))  */ {
    /* if (state.resolveType(stripped) instanceof Some(var resolved))  */ {
        return new Some(new Tuple(state /* : content-start CompileState content-end */) /* : Tuple */) /* : Some */;
    }
    /* else  */ {
        return new Some(new Tuple(state /* : content-start CompileState content-end */, new Placeholder( /* stripped */) /* : Placeholder */) /* : Tuple */) /* : Some */;
    }
}
return /* parseTemplate */ (state /* : content-start CompileState content-end */, input /* : string */) /* : unknown */.or /* : unknown */(() => /* varArgs */ (state /* : content-start CompileState content-end */, input /* : string */) /* : unknown */) /* : unknown */;
/* private static */ varArgs(state, input, string);
Option < [/* CompileState */ , /* Type */] > {
    return /* suffix */(input /* : string */, ) { }
}(s, unknown);
{
    return /* parseType */ (state /* : content-start CompileState content-end */,  /* s */) /* : unknown */.map /* : unknown */((inner) => {
        let newState = /* inner */ .left /* : unknown */;
        let child = /* inner */ .right /* : unknown */;
        return new Tuple(new ArrayType( /* child */) /* : ArrayType */) /* : Tuple */;
    }) /* : unknown */;
}
;
/* private static */ parseTemplate(state, input, string);
Option < [/* CompileState */ , /* Type */] > {
    return /* suffix */(input) { } /* : string */, /* : string */ : /* : string */ .strip /* : unknown */( /*  */) /* : unknown */, ">": ,
}(withoutEnd, unknown);
{
    return /* first */ ( /* withoutEnd */, "<", (base, argumentsString) => {
        let strippedBase = /* base */ .strip /* : unknown */( /*  */) /* : unknown */;
        return /* parseValues */ (state /* : content-start CompileState content-end */,  /* argumentsString */, /* Main */ .argument /* : unknown */) /* : unknown */.map /* : unknown */((argumentsTuple) => {
            return /* assembleTemplate */ ( /* strippedBase */, /* argumentsTuple */ .left /* : unknown */, /* argumentsTuple */ .right /* : unknown */) /* : unknown */;
        }) /* : unknown */;
    }) /* : unknown */;
}
;
/* private static */ assembleTemplate(base, string, state, arguments, (List));
[/* CompileState */ , /* Type */];
{
    let children = arguments /* : List<Argument> */.iterate /* : () => Iterator<T> */( /*  */) /* : unknown */.map /* : unknown */(retainType /* : unknown */) /* : unknown */.flatMap /* : unknown */(fromOption /* : unknown */) /* : unknown */.collect /* : unknown */(new ListCollector( /*  */) /* : ListCollector */) /* : unknown */;
    /* if (base.equals("BiFunction"))  */ {
        return new Tuple(state /* : content-start CompileState content-end */, new FunctionType(of /* : unknown */(get /* : unknown */(0 /* : number */) /* : unknown */, get /* : unknown */(1 /* : number */) /* : unknown */) /* : unknown */, get /* : unknown */(2 /* : number */) /* : unknown */) /* : FunctionType */) /* : Tuple */;
    }
    /* if (base.equals("Function"))  */ {
        return new Tuple(state /* : content-start CompileState content-end */, new FunctionType(of /* : unknown */(get /* : unknown */(0 /* : number */) /* : unknown */) /* : unknown */, get /* : unknown */(1 /* : number */) /* : unknown */) /* : FunctionType */) /* : Tuple */;
    }
    /* if (base.equals("Predicate"))  */ {
        return new Tuple(state /* : content-start CompileState content-end */, new FunctionType(of /* : unknown */(get /* : unknown */(0 /* : number */) /* : unknown */) /* : unknown */, Boolean /* : unknown */) /* : FunctionType */) /* : Tuple */;
    }
    /* if (base.equals("Supplier"))  */ {
        return new Tuple(state /* : content-start CompileState content-end */, new FunctionType(empty /* : unknown */( /*  */) /* : unknown */, get /* : unknown */(0 /* : number */) /* : unknown */) /* : FunctionType */) /* : Tuple */;
    }
    /* if (base.equals("Tuple") && children.size() >= 2)  */ {
        return new Tuple(state /* : content-start CompileState content-end */, new TupleType( /* children */) /* : TupleType */) /* : Tuple */;
    }
    /* if (state.resolveType(base) instanceof Some(var baseType) && baseType instanceof FindableType findableType)  */ {
        return new Tuple(state /* : content-start CompileState content-end */, new Template( /* findableType */) /* : Template */) /* : Tuple */;
    }
    return new Tuple(state /* : content-start CompileState content-end */, new Template(new Placeholder(base /* : string */) /* : Placeholder */) /* : Template */) /* : Tuple */;
}
/* private static */ retainType(argument, Argument);
Option <  /* Type */ > {
/* if (argument instanceof Type type)  */ };
/* if (argument instanceof Type type)  */ {
    return new Some( /* type */) /* : Some */;
}
/* else  */ {
    return new None( /*  */) /* : None<content-start Type content-end> */;
}
/* private static */ argument(state, input, string);
Option < [/* CompileState */ , Argument] > {
/* if (input.isBlank())  */ };
/* if (input.isBlank())  */ {
    return new Some(new Tuple(state /* : content-start CompileState content-end */, new Whitespace( /*  */) /* : Whitespace */) /* : Tuple */) /* : Some */;
}
return /* parseType */ (state /* : content-start CompileState content-end */, input /* : string */) /* : unknown */.map /* : unknown */((tuple) => new Tuple(left /* : unknown */, right /* : unknown */) /* : Tuple */) /* : unknown */;
/* private static  */ last(input, string, infix, string, mapper, (arg0, arg1) => Option);
Option < T > {
    return: infix /* : string */(input /* : string */, infix /* : string */) /* Main */.findLast /* : unknown */, mapper /* : (arg0 : string, arg1 : string) => Option<T> */
};
/* private static */ findLast(input, string, infix, string);
Option < number > {
    let, index: /* var */  = input /* : string */.lastIndexOf /* : unknown */(infix /* : string */) /* : unknown */
};
/* if (index == -1)  */ {
    return new None( /*  */) /* : None<number> */;
}
return new Some( /* index */) /* : Some */;
/* private static  */ first(input, string, infix, string, mapper, (arg0, arg1) => Option);
Option < T > {
    return: infix /* : string */(input /* : string */, infix /* : string */) /* Main */.findFirst /* : unknown */, mapper /* : (arg0 : string, arg1 : string) => Option<T> */
};
/* private static  */ infix(input, string, infix, string, locator, (arg0, arg1) => Option, mapper, (arg0, arg1) => Option);
Option < T > {
    return /* split */() { }
}();
locator /* : (arg0 : string, arg1 : string) => Option<number> */(input /* : string */, infix /* : string */) /* : Option<number> */.map /* : (arg0 : (arg0 : T) => R) => Option<R> */((index) => {
    let left = input /* : string */.substring /* : unknown */(0 /* : number */) /* : unknown */;
    let right = input /* : string */.substring /* : unknown */(/* index */ +infix /* : string */.length /* : unknown */( /*  */) /* : unknown */) /* : unknown */;
    return new Tuple( /* left */) /* : Tuple */;
}) /* : unknown */, mapper; /* : (arg0 : string, arg1 : string) => Option<T> */
;
/* private static  */ split(splitter, () => Option, mapper, (arg0, arg1) => Option);
Option < T > {
    return: splitter /* : () => Option<[string, string]> */( /*  */) /* : Option<[string, string]> */.flatMap /* : (arg0 : (arg0 : T) => Option<R>) => Option<R> */((tuple) => mapper /* : (arg0 : string, arg1 : string) => Option<T> */() /* tuple */.left /* : unknown */) /* tuple */.right /* : unknown */
};
/* private static */ findFirst(input, string, infix, string);
Option < number > {
    let, index: /* var */  = input /* : string */.indexOf /* : unknown */(infix /* : string */) /* : unknown */
};
/* if (index == -1)  */ {
    return new None( /*  */) /* : None<number> */;
}
return new Some( /* index */) /* : Some */;
/* private static */ generatePlaceholder(input, string);
string;
{
    let replaced = input /* : string */.replace /* : unknown */("/*", "content-start") /* : unknown */.replace /* : unknown */("*/", "content-end") /* : unknown */;
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
