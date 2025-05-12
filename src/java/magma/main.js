"use strict";
/* private static */ class None {
    /* @Override
        public  */ map(mapper) {
        return new None() /* : None */;
    }
    /* @Override
        public */ isPresent() {
        return false;
    }
    /* @Override
        public */ orElse(other) {
        return other /* : T */;
    }
    /* @Override
        public */ filter(predicate) {
        return new None() /* : None */;
    }
    /* @Override
        public */ orElseGet(supplier) {
        return supplier /* : () => T */() /* : T */;
    }
    /* @Override
        public */ or(other) {
        return other /* : T */.get /* : unknown */() /* : unknown */;
    }
    /* @Override
        public  */ flatMap(mapper) {
        return new None() /* : None */;
    }
    /* @Override
        public */ isEmpty() {
        return true;
    }
    /* @Override
        public  */ and(other) {
        return new None() /* : None */;
    }
}
/* private */ class Tuple2Impl {
    constructor(left, right) {
    }
}
/* private */ class Some {
    constructor(value) {
    }
    /* @Override
        public  */ map(mapper) {
        return new Some(mapper /* : (arg0 : T) => R */(value /* : unknown */) /* : R */) /* : Some */;
    }
    /* @Override
        public */ isPresent() {
        return true;
    }
    /* @Override
        public */ orElse(other) {
        return /* this */ .value /* : unknown */;
    }
    /* @Override
        public */ filter(predicate) {
        if (predicate /* : (arg0 : T) => boolean */(value /* : unknown */) /* : boolean */) {
            magma.Main$Lists$JVMList;
        }
        return new None() /* : None */;
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
        return mapper /* : (arg0 : T) => R */(value /* : unknown */) /* : R */;
    }
    /* @Override
        public */ isEmpty() {
        return false;
    }
    /* @Override
        public  */ and(other) {
        return other /* : T */.get /* : unknown */() /* : unknown */.map /* : unknown */((otherValue) => new Tuple2Impl(value /* : unknown */, otherValue /* : unknown */) /* : Tuple2Impl */) /* : unknown */;
    }
}
/* private static */ class SingleHead {
    constructor(value) {
        value /* : unknown */ = value /* : T */;
        retrieved /* : unknown */ = false;
    }
    /* @Override
        public */ next() {
        if ( /* this */.retrieved /* : unknown */) {
            magma.Main$Lists$JVMList;
            c629d6e;
        }
        retrieved /* : unknown */ = true;
        return new Some(value /* : unknown */) /* : Some */;
    }
}
/* private static */ class EmptyHead {
    /* @Override
        public */ next() {
        return new None() /* : None */;
    }
}
/* private */ class HeadedIterator {
    constructor(head) {
    }
    /* @Override
        public  */ fold(initial, folder) {
        let current = initial /* : R */;
        while (true) {
            magma.Main$Lists$JVMList;
            f5f779;
        }
    }
    /* @Override
        public  */ map(mapper) {
        return new HeadedIterator(() => /* this */ .head /* : unknown */.next /* : unknown */() /* : unknown */.map /* : unknown */(mapper /* : (arg0 : T) => R */) /* : unknown */) /* : HeadedIterator */;
    }
    /* @Override
        public  */ collect(collector) {
        return /* this */ .fold /* : unknown */(collector /* : Collector<T, R> */.createInitial /* : () => C */() /* : C */, collector /* : Collector<T, R> */.fold /* : unknown */) /* : unknown */;
    }
    /* @Override
        public */ filter(predicate) {
        return /* this */ .flatMap /* : unknown */((element) => {
            if (predicate /* : (arg0 : T) => boolean */(element /* : unknown */) /* : boolean */) {
                magma.Main$Lists$JVMList;
                b1d2887;
            }
            return new HeadedIterator(new EmptyHead() /* : EmptyHead */) /* : HeadedIterator */;
        }) /* : unknown */;
    }
    /* @Override
        public */ next() {
        return /* this */ .head /* : unknown */.next /* : unknown */() /* : unknown */;
    }
    /* @Override
        public  */ flatMap(f) {
        return new HeadedIterator(new /* FlatMapHead */ ( /* this */.head /* : unknown */, f /* : (arg0 : T) => Iterator<R> */) /* : content-start FlatMapHead content-end */) /* : HeadedIterator */;
    }
    /* @Override
        public  */ zip(other) {
        return new HeadedIterator(() => HeadedIterator /* : HeadedIterator */.this /* : unknown */.head /* : unknown */.next /* : unknown */() /* : unknown */.and /* : unknown */(other /* : T */.next /* : unknown */) /* : unknown */) /* : HeadedIterator */;
    }
}
/* private static */ class RangeHead /*  */ {
    constructor() {
        this.length /* : unknown */ = length /* : number */;
    }
}
/* @Override
    public */ next();
Option < number > {
    if() { } /* this.counter < this */, /* this.counter < this */ : /* this.counter < this */ .length /* : unknown */
};
{
    magma.Main$Lists$JVMList;
    c2c22f3;
}
return new None() /* : None */;
/* private static final */ class JVMList {
    constructor() {
        this.elements /* : unknown */ = elements /* : content-start java.util.List content-end<T> */;
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
    return /* this */: /* this */ .iterateWithIndices /* : unknown */() /* : unknown */.map /* : unknown */(Tuple2 /* : Tuple2 */.right /* : unknown */) /* : unknown */
};
/* @Override
        public */ removeLast();
Option < [(List), T] > {
    if() { } /* this */, /* this */ : /* this */ .elements /* : unknown */.isEmpty /* : unknown */() /* : unknown */
};
{
    magma.Main$Lists$JVMList;
    ccce;
}
let slice = /* this */ .elements /* : unknown */.subList /* : unknown */(0 /* : number */, elements /* : unknown */.size /* : unknown */() /* : unknown */ - 1 /* : number */) /* : unknown */;
let last = /* this */ .elements /* : unknown */.getLast /* : unknown */() /* : unknown */;
return new Some(new Tuple2Impl(new JVMList(slice /* : unknown */) /* : JVMList */, last /* : () => Option<T> */) /* : Tuple2Impl<List<T>, T> */) /* : Some */;
/* @Override
        public */ size();
number;
{
    return /* this */ .elements /* : unknown */.size /* : unknown */() /* : unknown */;
}
/* @Override
        public */ isEmpty();
boolean;
{
    return /* this */ .elements /* : unknown */.isEmpty /* : unknown */() /* : unknown */;
}
/* @Override
        public */ addFirst(element, T);
List < T > {
    return:  /* this */
};
/* @Override
        public */ iterateWithIndices();
Iterator < [number, T] > {
    return: new HeadedIterator(new RangeHead() /* this */.elements /* : unknown */.size /* : unknown */() /* : unknown */) /* : RangeHead */, /* : HeadedIterator */ : /* : HeadedIterator */ .map /* : (arg0 : (arg0 : T) => R) => Option<R> */((index) => new Tuple2Impl(index /* : T */) /* this */.elements /* : unknown */.get /* : unknown */(index /* : T */) /* : unknown */) /* : Tuple2Impl */
};
/* @Override
        public */ removeFirst();
Option < [T, (List)] > {
    if() { } /* this */, /* this */ : /* this */ .elements /* : unknown */.isEmpty /* : unknown */() /* : unknown */
};
{
    magma.Main$Lists$JVMList;
    a42bbf4;
}
let first = /* this */ .elements /* : unknown */.getFirst /* : unknown */() /* : unknown */;
let slice = /* this */ .elements /* : unknown */.subList /* : unknown */(1 /* : number */, elements /* : unknown */.size /* : unknown */() /* : unknown */) /* : unknown */;
return new Some(new Tuple2Impl(first /* : unknown */, new JVMList(slice /* : unknown */) /* : JVMList */) /* : Tuple2Impl<T, List<T>> */) /* : Some */;
/* @Override
        public */ addAllLast(others, (List));
List < T > {
    let, initial: (List) =  /* this */,
    return: others /* : List<T> */.iterate /* : () => Iterator<T> */() /* : Iterator<T> */.fold /* : (arg0 : C, arg1 : T) => C */(initial /* : R */, List /* : List */.addLast /* : unknown */) /* : C */
};
/* @Override
        public */ last();
Option < T > {
    if() { } /* this */, /* this */ : /* this */ .elements /* : unknown */.isEmpty /* : unknown */() /* : unknown */
};
{
    magma.Main$Lists$JVMList;
    f5;
}
return new Some(elements /* : unknown */.getLast /* : unknown */() /* : unknown */) /* : Some */;
/* @Override
        public */ get(index, number);
Option < T > {
    if() { } /* index < this */, /* index < this */ : /* index < this */ .elements /* : unknown */.size /* : unknown */() /* : unknown */
};
{
    magma.Main$Lists$JVMList;
    d455b8;
}
{
    magma.Main$Lists$JVMList;
    f4a7090;
}
/* private static */ class Lists /*  */ {
    /* public static  */ empty() {
        return new JVMList() /* : JVMList */;
    }
    /* public static  */ of(elements) {
        return new JVMList(new /* ArrayList */ ( /* Arrays */.asList /* : unknown */(elements /* : content-start java.util.List content-end<T> */) /* : unknown */) /* : content-start ArrayList content-end */) /* : JVMList */;
    }
}
/* private */ class ImmutableDefinition /*  */ {
    constructor(maybeBefore, name, type, typeParams) {
    }
    /* public static */ createSimpleDefinition(name, type) {
        return new ImmutableDefinition(new None() /* : None */, name /* : () => string */, type /* : () => Type */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */) /* : ImmutableDefinition */;
    }
    /* @Override
        public */ generate() {
        return /* this */ .generateWithParams /* : unknown */("") /* : unknown */;
    }
    /* @Override
        public */ generateType() {
        if ( /* this */.type /* : unknown */.equals /* : unknown */(Unknown /* : unknown */) /* : unknown */) {
            magma.Main$Lists$JVMList;
            d6d8735;
        }
        return " : " + /* this */ .type /* : unknown */.generate /* : unknown */() /* : unknown */;
    }
    /* @Override
        public */ joinBefore() {
        return !.maybeBefore /* : unknown */.filter /* : unknown */((value) => !value /* : T */.isEmpty /* : unknown */() /* : unknown */) /* : unknown */.map /* : unknown */(generatePlaceholder /* : unknown */) /* : unknown */.map /* : unknown */((inner) => inner /* : unknown */ + " ") /* : unknown */.orElse /* : unknown */("") /* : unknown */;
    }
    /* @Override
        public */ joinTypeParams() {
        return /* this */ .typeParams /* : unknown */.iterate /* : unknown */() /* : unknown */.collect /* : unknown */(new /* Joiner */ () /* : content-start Joiner content-end */) /* : unknown */.map /* : unknown */((inner) => "<" + inner + ">") /* : unknown */.orElse /* : unknown */("") /* : unknown */;
    }
    /* @Override
        public */ mapType(mapper) {
        return new ImmutableDefinition(maybeBefore /* : unknown */, name /* : unknown */, mapper /* : (arg0 : T) => R */(type /* : unknown */) /* : R */, typeParams /* : unknown */) /* : ImmutableDefinition */;
    }
    /* @Override
        public */ toString() {
        return "Definition[" + "maybeBefore=" + /* this */ .maybeBefore /* : unknown */ + ", " + "name=" + /* this */ .name /* : unknown */ + ", " + "type=" + /* this */ .type /* : unknown */ + ", " + "typeParams=" + /* this */ .typeParams /* : unknown */ +  /*  ']' */;
    }
    /* @Override
        public */ generateWithParams(joinedParameters) {
        let joined = /* this */ .joinTypeParams /* : unknown */() /* : unknown */;
        let before = /* this */ .joinBefore /* : unknown */() /* : unknown */;
        let typeString = /* this */ .generateType /* : unknown */() /* : unknown */;
        return before /* : unknown */ + /* this */ .name /* : unknown */ + joined /* : unknown */ + joinedParameters /* : string */ + typeString /* : unknown */;
    }
    /* @Override
        public */ createDefinition(paramTypes) {
        return ImmutableDefinition /* : ImmutableDefinition */.createSimpleDefinition /* : (arg0 : string, arg1 : Type) => Definition */(name /* : unknown */, new /* FunctionType */ (paramTypes /* : List<Type> */, /* this */ .type /* : unknown */) /* : content-start FunctionType content-end */) /* : Definition */;
    }
}
/* private */ class ObjectType /*  */ {
    constructor(name, typeParams, definitions) {
    }
    /* @Override
        public */ generate() {
        return /* this */ .name /* : unknown */;
    }
    /* @Override
        public */ replace(mapping) {
        return new ObjectType(name /* : unknown */, typeParams /* : unknown */, definitions /* : unknown */.iterate /* : unknown */() /* : unknown */.map /* : unknown */((definition) => definition /* : unknown */.mapType /* : unknown */((type) => type /* : () => Type */(mapping /* : Map<string, Type> */) /* : Type */) /* : unknown */) /* : unknown */.collect /* : unknown */(new /* ListCollector */ () /* : content-start ListCollector content-end */) /* : unknown */) /* : ObjectType */;
    }
    /* @Override
        public */ find(name) {
        return /* this */ .definitions /* : unknown */.iterate /* : unknown */() /* : unknown */.filter /* : unknown */((definition) => definition /* : unknown */.name /* : unknown */() /* : unknown */.equals /* : unknown */(name /* : () => string */) /* : unknown */) /* : unknown */.map /* : unknown */(Definition /* : Definition */.type /* : unknown */) /* : unknown */.next /* : unknown */() /* : unknown */;
    }
    /* @Override
        public */ findName() {
        return new Some(name /* : unknown */) /* : Some */;
    }
}
/* private */ class TypeParam /*  */ {
    constructor(value) {
    }
    /* @Override
        public */ generate() {
        return /* this */ .value /* : unknown */;
    }
    /* @Override
        public */ replace(mapping) {
        return mapping /* : Map<string, Type> */.find /* : (arg0 : string) => Option<V> */(value /* : unknown */) /* : Option<V> */.orElse /* : (arg0 : V) => T */( /* this */) /* : T */;
    }
    /* @Override
        public */ findName() {
        return new None() /* : None */;
    }
}
/* private */ class CompileState /*  */ {
    constructor(structures, definitions, objectTypes, structNames, typeParams, typeRegister) {
    }
    /* private */ resolveValue(name) {
        return /* this */ .definitions /* : unknown */.iterate /* : unknown */() /* : unknown */.filter /* : unknown */((definition) => definition /* : unknown */.name /* : unknown */() /* : unknown */.equals /* : unknown */(name /* : () => string */) /* : unknown */) /* : unknown */.next /* : unknown */() /* : unknown */.map /* : unknown */(Definition /* : Definition */.type /* : unknown */) /* : unknown */;
    }
    /* public */ addStructure(structure) {
        return new CompileState(structures /* : unknown */.addLast /* : unknown */(structure /* : string */) /* : unknown */, definitions /* : unknown */, objectTypes /* : unknown */, structNames /* : unknown */, typeParams /* : unknown */, typeRegister /* : unknown */) /* : CompileState */;
    }
    /* public */ withDefinitions(definitions) {
        return new CompileState(structures /* : unknown */, definitions /* : unknown */.addAllLast /* : unknown */(definitions /* : List<Definition> */) /* : unknown */, objectTypes /* : unknown */, structNames /* : unknown */, typeParams /* : unknown */, typeRegister /* : unknown */) /* : CompileState */;
    }
    /* public */ resolveType(name) {
        if ( /* this */.structNames /* : unknown */.last /* : unknown */() /* : unknown */.filter /* : unknown */((inner) => inner /* : unknown */.equals /* : unknown */(name /* : () => string */) /* : unknown */) /* : unknown */.isPresent /* : unknown */() /* : unknown */) {
            magma.Main$Lists$JVMList;
        }
        let maybeTypeParam = /* this */ .typeParams /* : unknown */.iterate /* : unknown */() /* : unknown */.filter /* : unknown */((param) => param /* : unknown */.equals /* : unknown */(name /* : () => string */) /* : unknown */) /* : unknown */.next /* : unknown */() /* : unknown */;
        if ( /* maybeTypeParam instanceof Some */( /* var value */) /* : unknown */) {
            magma.Main$Lists$JVMList;
            bc6874;
        }
        return /* this */ .objectTypes /* : unknown */.iterate /* : unknown */() /* : unknown */.filter /* : unknown */((type) => type /* : () => Type */.name /* : unknown */.equals /* : unknown */(name /* : () => string */) /* : unknown */) /* : unknown */.next /* : unknown */() /* : unknown */.map /* : unknown */((type) => type /* : () => Type */) /* : unknown */;
    }
    /* public */ addType(type) {
        return new CompileState(structures /* : unknown */, definitions /* : unknown */, objectTypes /* : unknown */.addLast /* : unknown */(type /* : () => Type */) /* : unknown */, structNames /* : unknown */, typeParams /* : unknown */, typeRegister /* : unknown */) /* : CompileState */;
    }
    /* public */ withDefinition(definition) {
        return new CompileState(structures /* : unknown */, definitions /* : unknown */.addLast /* : unknown */(definition /* : unknown */) /* : unknown */, objectTypes /* : unknown */, structNames /* : unknown */, typeParams /* : unknown */, typeRegister /* : unknown */) /* : CompileState */;
    }
    /* public */ pushStructName(name) {
        return new CompileState(structures /* : unknown */, definitions /* : unknown */, objectTypes /* : unknown */, structNames /* : unknown */.addLast /* : unknown */(name /* : () => string */) /* : unknown */, typeParams /* : unknown */, typeRegister /* : unknown */) /* : CompileState */;
    }
    /* public */ withTypeParams(typeParams) {
        return new CompileState(structures /* : unknown */, definitions /* : unknown */, objectTypes /* : unknown */, structNames /* : unknown */, typeParams /* : unknown */.addAllLast /* : unknown */(typeParams /* : () => List<string> */) /* : unknown */, typeRegister /* : unknown */) /* : CompileState */;
    }
    /* public */ withExpectedType(type) {
        return new CompileState(structures /* : unknown */, definitions /* : unknown */, objectTypes /* : unknown */, structNames /* : unknown */, typeParams /* : unknown */, new Some(type /* : () => Type */) /* : Some */) /* : CompileState */;
    }
    /* public */ popStructName() {
        return new CompileState(structures /* : unknown */, definitions /* : unknown */, objectTypes /* : unknown */, structNames /* : unknown */.removeLast /* : unknown */() /* : unknown */.map /* : unknown */(Tuple2 /* : Tuple2 */.left /* : unknown */) /* : unknown */.orElse /* : unknown */(structNames /* : unknown */) /* : unknown */, typeParams /* : unknown */, typeRegister /* : unknown */) /* : CompileState */;
    }
}
/* private static */ class DivideState /*  */ {
    constructor() {
        this.segments /* : unknown */ = segments /* : List<string> */;
        this.buffer /* : unknown */ = buffer /* : string */;
        this.depth /* : unknown */ = depth /* : number */;
        this.input /* : unknown */ = input /* : string */;
        this.index /* : unknown */ = index /* : T */;
    }
}
DivideState(input, string);
{
    /* this(input, 0, Lists.empty(), "", 0) */ ;
}
/* private */ advance();
DivideState;
{
    segments /* : unknown */ = /* this */ .segments /* : unknown */.addLast /* : unknown */(buffer /* : unknown */) /* : unknown */;
    buffer /* : unknown */ = "";
    return /* this */;
}
/* private */ append(c, string);
DivideState;
{
    buffer /* : unknown */ = /* this */ .buffer /* : unknown */ + c /* : string */;
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
    return /* this */ .depth /* : unknown */ === 0 /* : number */;
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
    return /* this */ .depth /* : unknown */ === 1 /* : number */;
}
/* public */ pop();
Option < [string, DivideState] > {
    if() { } /* this.index < this */, /* this.index < this */ : /* this.index < this */ .input /* : unknown */.length /* : unknown */() /* : unknown */
};
{
    magma.Main$Lists$JVMList;
}
return new None() /* : None */;
/* public */ popAndAppendToTuple();
Option < [string, DivideState] > {
    return /* this */: /* this */ .pop /* : unknown */() /* : unknown */.map /* : unknown */((tuple) => {
        let c = tuple /* : unknown */.left /* : unknown */() /* : unknown */;
        let right = tuple /* : unknown */.right /* : unknown */() /* : unknown */;
        return new Tuple2Impl(c /* : string */, right /* : () => B */(c /* : string */) /* : B */) /* : Tuple2Impl */;
    }) /* : unknown */
};
/* public */ popAndAppendToOption();
Option < DivideState > {
    return /* this */: /* this */ .popAndAppendToTuple /* : unknown */() /* : unknown */.map /* : unknown */(Tuple2 /* : Tuple2 */.right /* : unknown */) /* : unknown */
};
/* public */ peek();
string;
{
    return /* this */ .input /* : unknown */.charAt /* : unknown */(index /* : unknown */) /* : unknown */;
}
/* private */ class Joiner /*  */ {
    constructor(delimiter) {
    }
    /* @Override
        public */ createInitial() {
        return new None() /* : None */;
    }
    /* @Override
        public */ fold(current, element) {
        return new Some(current /* : R */.map /* : unknown */((inner) => inner /* : unknown */ + /* this */ .delimiter /* : unknown */ + element /* : unknown */) /* : unknown */.orElse /* : unknown */(element /* : unknown */) /* : unknown */) /* : Some */;
    }
}
/* private static */ class ListCollector {
    /* @Override
        public */ createInitial() {
        return Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */;
    }
    /* @Override
        public */ fold(current, element) {
        return current /* : R */.addLast /* : unknown */(element /* : unknown */) /* : unknown */;
    }
}
/* private static */ class FlatMapHead {
    constructor() {
        this.mapper /* : unknown */ = mapper /* : (arg0 : T) => R */;
        this.current /* : unknown */ = new None() /* : None */;
        this.head /* : unknown */ = head /* : Head<T> */;
    }
}
/* @Override
    public */ next();
Option < R > {
    while() {
        magma.Main$Lists$JVMList;
        c75cab9;
    }
};
/* private */ class ArrayType /*  */ {
    constructor(right) {
    }
    /* @Override
        public */ generate() {
        return /* this */ .right /* : unknown */() /* : unknown */.generate /* : unknown */() /* : unknown */ + "[]";
    }
    /* @Override
        public */ replace(mapping) {
        return /* this */;
    }
    /* @Override
        public */ findName() {
        return new None() /* : None */;
    }
}
/* private static */ class Whitespace /*  */ {
    /* @Override
        public */ generate() {
        return "";
    }
}
/* private static */ class Iterators /*  */ {
    /* public static  */ fromOption(option) {
        let single = option /* : unknown */.map /* : unknown */(SingleHead /* : SingleHead */.new /* : unknown */) /* : unknown */;
        return new HeadedIterator(single /* : Option<Head<T>> */.orElseGet /* : (arg0 : () => T) => T */(EmptyHead /* : EmptyHead */.new /* : unknown */) /* : T */) /* : HeadedIterator */;
    }
}
/* private */ class FunctionType /*  */ {
    constructor(arguments, returns) {
    }
    /* @Override
        public */ generate() {
        let joined = /* this */ .arguments /* : unknown */() /* : unknown */.iterateWithIndices /* : unknown */() /* : unknown */.map /* : unknown */((pair) => "arg" + pair /* : unknown */.left /* : unknown */() /* : unknown */ + " : " + pair /* : unknown */.right /* : unknown */() /* : unknown */.generate /* : unknown */() /* : unknown */) /* : unknown */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        return "(" + joined /* : unknown */ + ") => " + /* this */ .returns /* : unknown */.generate /* : unknown */() /* : unknown */;
    }
    /* @Override
        public */ replace(mapping) {
        return new FunctionType(arguments /* : unknown */.iterate /* : unknown */() /* : unknown */.map /* : unknown */((type) => type /* : () => Type */(mapping /* : Map<string, Type> */) /* : Type */) /* : unknown */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */, returns /* : unknown */) /* : FunctionType */;
    }
    /* @Override
        public */ findName() {
        return new None() /* : None */;
    }
}
/* private */ class TupleType /*  */ {
    constructor(arguments) {
    }
    /* @Override
        public */ generate() {
        let joinedArguments = /* this */ .arguments /* : unknown */.iterate /* : unknown */() /* : unknown */.map /* : unknown */(Type /* : Type */.generate /* : unknown */) /* : unknown */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        return "[" + joinedArguments + "]";
    }
    /* @Override
        public */ replace(mapping) {
        return /* this */;
    }
    /* @Override
        public */ findName() {
        return new None() /* : None */;
    }
}
/* private */ class Template /*  */ {
    constructor(base, arguments) {
    }
    /* @Override
        public */ generate() {
        let joinedArguments = /* this */ .arguments /* : unknown */.iterate /* : unknown */() /* : unknown */.map /* : unknown */(Type /* : Type */.generate /* : unknown */) /* : unknown */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.map /* : unknown */((inner) => "<" + inner + ">") /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        return /* this */ .base /* : unknown */.generate /* : unknown */() /* : unknown */ + joinedArguments /* : unknown */;
    }
    /* @Override
        public */ typeParams() {
        return /* this */ .base /* : unknown */.typeParams /* : unknown */() /* : unknown */;
    }
    /* @Override
        public */ find(name) {
        return /* this */ .base /* : unknown */.find /* : unknown */(name /* : () => string */) /* : unknown */.map /* : unknown */((found) => {
            let mapping = /* this */ .base /* : unknown */.typeParams /* : unknown */() /* : unknown */.iterate /* : unknown */() /* : unknown */.zip /* : unknown */(arguments /* : unknown */.iterate /* : unknown */() /* : unknown */) /* : unknown */.collect /* : unknown */(new /* MapCollector */ () /* : content-start MapCollector content-end */) /* : unknown */;
            return found /* : unknown */.replace /* : unknown */(mapping /* : Map<string, Type> */) /* : unknown */;
        }) /* : unknown */;
    }
    /* @Override
        public */ name() {
        return /* this */ .base /* : unknown */.name /* : unknown */() /* : unknown */;
    }
    /* @Override
        public */ replace(mapping) {
        return /* this */;
    }
    /* @Override
        public */ findName() {
        return /* this */ .base /* : unknown */.findName /* : unknown */() /* : unknown */;
    }
}
/* private */ class Placeholder /*  */ {
    constructor(input) {
    }
    /* @Override
        public */ generate() {
        return /* generatePlaceholder */ ( /* this */.input /* : unknown */) /* : unknown */;
    }
    /* @Override
        public */ type() {
        return /* Primitive */ .Unknown /* : unknown */;
    }
    /* @Override
        public */ typeParams() {
        return Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */;
    }
    /* @Override
        public */ find(name) {
        return new None() /* : None */;
    }
    /* @Override
        public */ name() {
        return /* this */ .input /* : unknown */;
    }
    /* @Override
        public */ replace(mapping) {
        return /* this */;
    }
    /* @Override
        public */ findName() {
        return new None() /* : None */;
    }
}
/* private */ class StringValue /*  */ {
    constructor(stripped) {
    }
    /* @Override
        public */ generate() {
        return /* this */ .stripped /* : unknown */;
    }
    /* @Override
        public */ type() {
        return /* Primitive */ .Unknown /* : unknown */;
    }
}
/* private */ class DataAccess /*  */ {
    constructor(parent, property, type) {
    }
    /* @Override
        public */ generate() {
        return /* this */ .parent /* : unknown */.generate /* : unknown */() /* : unknown */ + "." + /* this */ .property /* : unknown */ + /* createDebugString */ ( /* this */.type /* : unknown */) /* : unknown */;
    }
    /* @Override
        public */ type() {
        return /* this */ .type /* : unknown */;
    }
}
/* private */ class ConstructionCaller /*  */ {
    constructor(type) {
    }
    /* @Override
        public */ generate() {
        return "new " + /* this */ .type /* : unknown */.generate /* : unknown */() /* : unknown */;
    }
    /* public */ toFunction() {
        return new FunctionType(Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */, type /* : unknown */) /* : FunctionType */;
    }
}
/* private */ class Operation /*  */ {
    constructor(left, operator /* Operator */, right) {
    }
    /* @Override
        public */ generate() {
        return /* this */ .left /* : unknown */() /* : unknown */.generate /* : unknown */() /* : unknown */ + " " + /* this */ .operator /* : unknown */.targetRepresentation /* : unknown */ + " " + /* this */ .right /* : unknown */() /* : unknown */.generate /* : unknown */() /* : unknown */;
    }
    /* @Override
        public */ type() {
        return /* Primitive */ .Unknown /* : unknown */;
    }
}
/* private */ class Not /*  */ {
    constructor(value) {
    }
    /* @Override
        public */ generate() {
        return "!" + /* this */ .value /* : unknown */.generate /* : unknown */() /* : unknown */;
    }
    /* @Override
        public */ type() {
        return /* Primitive */ .Unknown /* : unknown */;
    }
}
/* private */ class BlockLambdaValue /*  */ {
    constructor(depth, statements) {
    }
    /* @Override
        public */ generate() {
        return "{" + this.joinStatements() + createIndent(this.depth) + "}";
    }
    /* private */ joinStatements() {
        return /* this */ .statements /* : unknown */.iterate /* : unknown */() /* : unknown */.map /* : unknown */(FunctionSegment /* : FunctionSegment */.generate /* : unknown */) /* : unknown */.collect /* : unknown */(new Joiner() /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
    }
}
/* private */ class Lambda /*  */ {
    constructor(parameters, body) {
    }
    /* @Override
        public */ generate() {
        let joined = /* this */ .parameters /* : unknown */.iterate /* : unknown */() /* : unknown */.map /* : unknown */(Definition /* : Definition */.generate /* : unknown */) /* : unknown */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        return "(" + joined /* : unknown */ + ") => " + /* this */ .body /* : unknown */.generate /* : unknown */() /* : unknown */;
    }
    /* @Override
        public */ type() {
        return /* Primitive */ .Unknown /* : unknown */;
    }
}
/* private */ class Invokable /*  */ {
    constructor(caller, arguments, type) {
    }
    /* @Override
        public */ generate() {
        let joined = /* this */ .arguments /* : unknown */.iterate /* : unknown */() /* : unknown */.map /* : unknown */(Value /* : Value */.generate /* : unknown */) /* : unknown */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        return /* this */ .caller /* : unknown */.generate /* : unknown */() /* : unknown */ + "(" + joined /* : unknown */ + ")" + /* createDebugString */ ( /* this */.type /* : unknown */) /* : unknown */;
    }
}
/* private */ class IndexValue /*  */ {
    constructor(parent, child) {
    }
    /* @Override
        public */ generate() {
        return /* this */ .parent /* : unknown */.generate /* : unknown */() /* : unknown */ + "[" + this.child.generate() + "]";
    }
    /* @Override
        public */ type() {
        return /* Primitive */ .Unknown /* : unknown */;
    }
}
/* private */ class SymbolValue /*  */ {
    constructor(stripped, type) {
    }
    /* @Override
        public */ generate() {
        return /* this */ .stripped /* : unknown */ + /* createDebugString */ ( /* this */.type /* : unknown */) /* : unknown */;
    }
}
/* private */ class JVMMap {
    constructor(map) {
    }
    /* @Override
            public */ find(key) {
        if ( /* this */.map /* : unknown */.containsKey /* : unknown */(key /* : K */) /* : unknown */) {
            magma.Main$Lists$JVMList;
            f7fe8e;
        }
        return new None() /* : None */;
    }
    /* @Override
            public */ with(key, value) {
        /* this.map.put(key, value) */ ;
        return /* this */;
    }
}
/* private static */ class Maps /*  */ {
    /* public static  */ empty() {
        return new JVMMap() /* : JVMMap */;
    }
}
/* private */ class MapCollector {
    constructor() {
    }
    /* @Override
        public */ createInitial() {
        return Maps /* : Maps */.empty /* : () => List<T> */() /* : List<T> */;
    }
    /* @Override
        public */ fold(current, element) {
        return current /* : R */.with /* : unknown */(element /* : unknown */.left /* : unknown */() /* : unknown */, element /* : unknown */.right /* : unknown */() /* : unknown */) /* : unknown */;
    }
}
/* private static */ class ConstructorHeader /*  */ {
    /* @Override
        public */ createDefinition(paramTypes) {
        return ImmutableDefinition /* : ImmutableDefinition */.createSimpleDefinition /* : (arg0 : string, arg1 : Type) => Definition */("new", Unknown /* : unknown */) /* : Definition */;
    }
    /* @Override
        public */ generateWithParams(joinedParameters) {
        return "constructor " + joinedParameters /* : string */;
    }
}
/* private static */ class DefinitionStatement /*  */ {
    constructor() {
        this.depth /* : unknown */ = depth /* : number */;
        this.definition /* : unknown */ = definition /* : unknown */;
    }
}
/* @Override
    public */ generate();
string;
{
    return /* createIndent */ ( /* this */.depth /* : unknown */) /* : unknown */ + /* this */ .definition /* : unknown */.generate /* : unknown */() /* : unknown */ + ";";
}
/* private static */ class Method /*  */ {
    constructor() {
        this.depth /* : unknown */ = depth /* : number */;
        this.header /* : unknown */ = header /* : Header */;
        this.parameters /* : unknown */ = parameters /* : List<Definition> */;
        this.statements /* : unknown */ = maybeStatements /* : Option<List<FunctionSegment>> */;
    }
}
/* private static */ joinStatements(statements, (List));
string;
{
    return statements /* : List<FunctionSegment> */.iterate /* : () => Iterator<T> */() /* : Iterator<T> */.map /* : (arg0 : (arg0 : T) => R) => Option<R> */(FunctionSegment /* : FunctionSegment */.generate /* : unknown */) /* : Option<R> */.collect /* : (arg0 : Collector<T, R>) => R */(new Joiner() /* : Joiner */) /* : R */.orElse /* : unknown */("") /* : unknown */;
}
/* @Override
    public */ generate();
string;
{
    let indent = /* createIndent */ ( /* this */.depth /* : unknown */) /* : unknown */;
    let generatedHeader = /* this */ .header /* : unknown */.generateWithParams /* : unknown */(/* joinValues */ ( /* this */.parameters /* : unknown */) /* : unknown */) /* : unknown */;
    let generatedStatements = /* this */ .statements /* : unknown */.map /* : unknown */(Method /* : (arg0 : number, arg1 : Header, arg2 : List<Definition>, arg3 : Option<List<FunctionSegment>>) => content-start public content-end */.joinStatements /* : unknown */) /* : unknown */.map /* : unknown */((inner) => " {" + inner + indent + "}") /* : unknown */.orElse /* : unknown */(";") /* : unknown */;
    return indent /* : unknown */ + generatedHeader /* : unknown */ + generatedStatements /* : unknown */;
}
/* private */ class Primitive /*  */ {
    constructor(value) {
        value /* : unknown */ = value /* : T */;
    }
    /* @Override
        public */ generate() {
        return /* this */ .value /* : unknown */;
    }
    /* @Override
        public */ replace(mapping) {
        return /* this */;
    }
    /* @Override
        public */ findName() {
        return new None() /* : None */;
    }
}
/* private */ class Operator /*  */ {
    constructor(sourceRepresentation, targetRepresentation) {
        sourceRepresentation /* : unknown */ = sourceRepresentation /* : string */;
        targetRepresentation /* : unknown */ = targetRepresentation /* : string */;
    }
}
/* private */ class BooleanValue /*  */ {
    constructor(value) {
        value /* : unknown */ = value /* : T */;
    }
    /* @Override
        public */ generate() {
        return /* this */ .value /* : unknown */;
    }
    /* @Override
        public */ type() {
        return Primitive /* : Primitive */.Boolean /* : unknown */;
    }
}
/* public */ class Main /*  */ {
    /* public static */ main() {
        magma.Main$Lists$JVMList;
        be94b0f;
    }
}
/* catch (IOException | InterruptedException e) */ {
    magma.Main$Lists$JVMList;
}
/* private static */ compile(input, string);
string;
{
    let tuple = /* compileStatements */ (new CompileState() /* : CompileState */, input /* : string */, Main /* : Main */.compileRootSegment /* : unknown */) /* : unknown */;
    let joined = tuple /* : unknown */.left /* : unknown */() /* : unknown */.structures /* : unknown */.iterate /* : unknown */() /* : unknown */.collect /* : unknown */(new Joiner() /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
    return joined /* : unknown */ + tuple /* : unknown */.right /* : unknown */() /* : unknown */;
}
/* private static */ compileStatements(state, CompileState, input, string, mapper, (arg0, arg1) => [CompileState, string]);
[CompileState, string];
{
    let parsed = /* parseStatements */ (state /* : CompileState */, input /* : string */, mapper /* : (arg0 : T) => R */) /* : unknown */;
    return new Tuple2Impl(parsed /* : unknown */.left /* : unknown */() /* : unknown */, /* generateStatements */ (parsed /* : unknown */.right /* : unknown */() /* : unknown */) /* : unknown */) /* : Tuple2Impl */;
}
/* private static */ generateStatements(statements, (List));
string;
{
    return /* generateAll */ (Main /* : Main */.mergeStatements /* : unknown */, statements /* : List<FunctionSegment> */) /* : unknown */;
}
/* private static  */ parseStatements(state, CompileState, input, string, mapper, (arg0, arg1) => [CompileState, T]);
[CompileState, (List)];
{
    return /* parseAll0 */ (state /* : CompileState */, input /* : string */, Main /* : Main */.foldStatementChar /* : unknown */, mapper /* : (arg0 : T) => R */) /* : unknown */;
}
/* private static */ generateAll(merger, (arg0, arg1) => string, elements, (List));
string;
{
    return elements /* : content-start java.util.List content-end<T> */.iterate /* : unknown */() /* : unknown */.fold /* : unknown */("", merger /* : (arg0 : string, arg1 : string) => string */) /* : unknown */;
}
/* private static  */ parseAll0(state, CompileState, input, string, folder, (arg0, arg1) => DivideState, mapper, (arg0, arg1) => [CompileState, T]);
[CompileState, (List)];
{
    return /* getCompileStateListTuple */ (state /* : CompileState */, input /* : string */, folder /* : (arg0 : R, arg1 : T) => R */, (state1, s) => new Some(mapper /* : (arg0 : T) => R */(state1 /* : unknown */, s /* : unknown */) /* : R */) /* : Some */) /* : unknown */.orElseGet /* : unknown */(() => new Tuple2Impl(state /* : CompileState */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */) /* : Tuple2Impl */) /* : unknown */;
}
/* private static  */ getCompileStateListTuple(state, CompileState, input, string, folder, (arg0, arg1) => DivideState, mapper, (arg0, arg1) => Option);
Option < [CompileState, (List)] > {
    return /* parseAll */(state /* : CompileState */, input /* : string */, folder /* : (arg0 : R, arg1 : T) => R */) { }
}(state1, tuple);
mapper /* : (arg0 : T) => R */(state1 /* : unknown */, tuple /* : unknown */.right /* : unknown */() /* : unknown */); /* : R */
;
/* private static  */ parseAll(state, CompileState, input, string, folder, (arg0, arg1) => DivideState, mapper, (arg0, arg1) => Option);
Option < [CompileState, (List)] > {
    let, initial: (Option) = new Some(new Tuple2Impl(state /* : CompileState */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */) /* : Tuple2Impl */) /* : Some */,
    return /* divideAll */(input /* : string */, folder /* : (arg0 : R, arg1 : T) => R */) { } /* : unknown */, /* : unknown */ : /* : unknown */ .iterateWithIndices /* : unknown */() /* : unknown */.fold /* : unknown */(initial /* : R */, (tuple, element) => {
        return tuple /* : unknown */.flatMap /* : unknown */((inner) => {
            let state1 = inner /* : unknown */.left /* : unknown */() /* : unknown */;
            let right = inner /* : unknown */.right /* : unknown */() /* : unknown */;
            return mapper /* : (arg0 : T) => R */(state1 /* : unknown */, element /* : unknown */) /* : R */.map /* : unknown */((applied) => {
                return new Tuple2Impl(applied /* : unknown */.left /* : unknown */() /* : unknown */, right /* : () => B */(applied /* : unknown */.right /* : unknown */() /* : unknown */) /* : B */) /* : Tuple2Impl */;
            }) /* : unknown */;
        }) /* : unknown */;
    }) /* : unknown */
};
/* private static */ mergeStatements(cache, string, statement, string);
string;
{
    return cache /* : string */ + statement /* : string */;
}
/* private static */ divideAll(input, string, folder, (arg0, arg1) => DivideState);
List < string > {
    let, current: DivideState = new DivideState(input /* : string */) /* : DivideState */,
    while() {
        magma.Main$Lists$JVMList;
        d40e0;
    },
    return: current /* : R */.advance /* : unknown */() /* : unknown */.segments /* : unknown */
};
/* private static */ foldDoubleQuotes(tuple, [string, DivideState]);
Option < DivideState > {
    if(tuple) { } /* : unknown */, /* : unknown */ : /* : unknown */ .left /* : unknown */() /* : unknown */ ===  /*  '\"' */
};
{
    magma.Main$Lists$JVMList;
}
return new None() /* : None */;
/* private static */ foldSingleQuotes(tuple, [string, DivideState]);
Option < DivideState > {
    if( /* tuple.left() != '\'' */) {
        magma.Main$Lists$JVMList;
        b7dea0;
    },
    let, appended = tuple /* : unknown */.right /* : unknown */() /* : unknown */.append /* : unknown */(tuple /* : unknown */.left /* : unknown */() /* : unknown */) /* : unknown */,
    return: appended /* : unknown */.popAndAppendToTuple /* : unknown */() /* : unknown */.map /* : unknown */(Main /* : Main */.foldEscaped /* : unknown */) /* : unknown */.flatMap /* : unknown */(DivideState /* : (arg0 : string, arg1 : number, arg2 : List<string>, arg3 : string, arg4 : number) => content-start public content-end */.popAndAppendToOption /* : unknown */) /* : unknown */
};
/* private static */ foldEscaped(escaped, [string, DivideState]);
DivideState;
{
    if (escaped /* : [string, DivideState] */[0 /* : number */]() /* : unknown */ ===  /*  '\\' */) {
        magma.Main$Lists$JVMList;
        ac42916;
    }
    return escaped /* : [string, DivideState] */[1 /* : number */]() /* : unknown */;
}
/* private static */ foldStatementChar(state, DivideState, c, string);
DivideState;
{
    let append = state /* : CompileState */.append /* : (arg0 : string) => DivideState */(c /* : string */) /* : DivideState */;
    if (c /* : string */ ===  /*  ';'  */ && append /* : (arg0 : string) => DivideState */.isLevel /* : unknown */() /* : unknown */) {
        magma.Main$Lists$JVMList;
        d384ee;
    }
    if (c /* : string */ ===  /*  '}'  */ && append /* : (arg0 : string) => DivideState */.isShallow /* : unknown */() /* : unknown */) {
        magma.Main$Lists$JVMList;
        d6a9952;
    }
    if (c /* : string */ ===  /*  '{'  */ || c /* : string */ ===  /*  '(' */) {
        magma.Main$Lists$JVMList;
        a71081;
    }
    if (c /* : string */ ===  /*  '}'  */ || c /* : string */ ===  /*  ')' */) {
        magma.Main$Lists$JVMList;
        a;
    }
    return append /* : (arg0 : string) => DivideState */;
}
/* private static */ compileRootSegment(state, CompileState, input, string);
[CompileState, string];
{
    let stripped = input /* : string */.strip /* : unknown */() /* : unknown */;
    if (stripped /* : unknown */.startsWith /* : unknown */("package ") /* : unknown */ || stripped /* : unknown */.startsWith /* : unknown */("import ") /* : unknown */) {
        magma.Main$Lists$JVMList;
        f0666;
    }
    return /* compileClass */ (stripped /* : unknown */, 0 /* : number */, state /* : CompileState */) /* : unknown */.map /* : unknown */((tuple) => new Tuple2Impl(tuple /* : unknown */.left /* : unknown */() /* : unknown */, tuple /* : unknown */.right /* : unknown */() /* : unknown */.generate /* : unknown */() /* : unknown */) /* : Tuple2Impl */) /* : unknown */.orElseGet /* : unknown */(() => new Tuple2Impl(state /* : CompileState */, /* generatePlaceholder */ (stripped /* : unknown */) /* : unknown */) /* : Tuple2Impl */) /* : unknown */;
}
/* private static */ compileClass(stripped, string, depth, number, state, CompileState);
Option < [CompileState, ClassSegment] > {};
/* private static */ compileStructure(stripped, string, sourceInfix, string, targetInfix, string, state, CompileState);
Option < [CompileState, ClassSegment] > {
    return: first /* : unknown */(stripped /* : unknown */, sourceInfix /* : string */, (beforeInfix, right) => {
        return first /* : unknown */(right /* : () => B */, "{", (beforeContent, withEnd) => {
            return /* suffix */ (withEnd /* : unknown */.strip /* : unknown */() /* : unknown */, "}", (content1) => {
                return /* getOr */ (targetInfix /* : string */, state /* : CompileState */, beforeInfix /* : unknown */, beforeContent /* : unknown */, content1 /* : unknown */) /* : unknown */;
            }) /* : unknown */;
        }) /* : unknown */;
    }) /* : unknown */
};
/* private static */ getOr(targetInfix, string, state, CompileState, beforeInfix, string, beforeContent, string, content1, string);
Option < [CompileState, ClassSegment] > {
    return: first /* : unknown */(beforeContent /* : unknown */, " implements ", (s, s2) => {
        return /* structureWithMaybeExtends */ (targetInfix /* : string */, state /* : CompileState */, beforeInfix /* : unknown */, s /* : unknown */, content1 /* : unknown */) /* : unknown */;
    }) /* : unknown */.or /* : unknown */(() => {
        return /* structureWithMaybeExtends */ (targetInfix /* : string */, state /* : CompileState */, beforeInfix /* : unknown */, beforeContent /* : unknown */, content1 /* : unknown */) /* : unknown */;
    }) /* : unknown */
};
/* private static */ structureWithMaybeExtends(targetInfix, string, state, CompileState, beforeInfix, string, beforeContent, string, content1, string);
Option < [CompileState, ClassSegment] > {
    return: first /* : unknown */(beforeContent /* : unknown */, " extends ", (s, s2) => {
        return /* structureWithMaybeParams */ (targetInfix /* : string */, state /* : CompileState */, beforeInfix /* : unknown */, s /* : unknown */, content1 /* : unknown */) /* : unknown */;
    }) /* : unknown */.or /* : unknown */(() => {
        return /* structureWithMaybeParams */ (targetInfix /* : string */, state /* : CompileState */, beforeInfix /* : unknown */, beforeContent /* : unknown */, content1 /* : unknown */) /* : unknown */;
    }) /* : unknown */
};
/* private static */ structureWithMaybeParams(targetInfix, string, state, CompileState, beforeInfix, string, beforeContent, string, content1, string);
Option < [CompileState, ClassSegment] > {
    return /* suffix */(beforeContent) { } /* : unknown */, /* : unknown */ : /* : unknown */ .strip /* : unknown */() /* : unknown */, ")": ,
}(s);
{
    return first /* : unknown */(s /* : unknown */, "(", (s1, s2) => {
        let parsed = /* parseParameters */ (state /* : CompileState */, s2 /* : unknown */) /* : unknown */;
        return /* getOred */ (targetInfix /* : string */, parsed /* : unknown */.left /* : unknown */() /* : unknown */, beforeInfix /* : unknown */, s1 /* : unknown */, content1 /* : unknown */, parsed /* : unknown */.right /* : unknown */() /* : unknown */) /* : unknown */;
    }) /* : unknown */;
}
or /* : unknown */(() => {
    return /* getOred */ (targetInfix /* : string */, state /* : CompileState */, beforeInfix /* : unknown */, beforeContent /* : unknown */, content1 /* : unknown */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */) /* : unknown */;
}) /* : unknown */;
/* private static */ getOred(targetInfix, string, state, CompileState, beforeInfix, string, beforeContent, string, content1, string, params, (List));
Option < [CompileState, ClassSegment] > {
    return: first /* : unknown */(beforeContent /* : unknown */, "<", (name, withTypeParams) => {
        return first /* : unknown */(withTypeParams /* : (arg0 : List<string>) => CompileState */, ">", (typeParamsString, afterTypeParams) => {
            let /* final */ mapper = (state1, s) => new Tuple2Impl(state1 /* : unknown */, s /* : unknown */.strip /* : unknown */() /* : unknown */) /* : Tuple2Impl */;
            let typeParams = /* parseValuesOrEmpty */ (state /* : CompileState */, typeParamsString /* : unknown */, (state1, s) => new Some(mapper /* : (arg0 : T) => R */(state1 /* : unknown */, s /* : unknown */) /* : R */) /* : Some */) /* : unknown */;
            return /* assembleStructure */ (typeParams /* : () => List<string> */() /* : List<string> */, targetInfix /* : string */, beforeInfix /* : unknown */, name /* : () => string */, content1 /* : unknown */, typeParams /* : () => List<string> */() /* : List<string> */, afterTypeParams /* : unknown */, params /* : List<Parameter> */) /* : unknown */;
        }) /* : unknown */;
    }) /* : unknown */.or /* : unknown */(() => {
        return /* assembleStructure */ (state /* : CompileState */, targetInfix /* : string */, beforeInfix /* : unknown */, beforeContent /* : unknown */, content1 /* : unknown */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */, "", params /* : List<Parameter> */) /* : unknown */;
    }) /* : unknown */
};
/* private static */ assembleStructure(state, CompileState, targetInfix, string, beforeInfix, string, rawName, string, content, string, typeParams, (List), afterTypeParams, string, rawParameters, (List));
Option < [CompileState, ClassSegment] > {
    let, name = rawName /* : string */.strip /* : unknown */() /* : unknown */,
    if() { }
} /* isSymbol */(name /* : () => string */); /* : unknown */
{
    magma.Main$Lists$JVMList;
    bc6a36e;
}
let joinedTypeParams = typeParams /* : () => List<string> */() /* : List<string> */.collect /* : (arg0 : Collector<T, R>) => R */(new Joiner(", ") /* : Joiner */) /* : R */.map /* : unknown */((inner) => "<" + inner + ">") /* : unknown */.orElse /* : unknown */("") /* : unknown */;
let statementsTuple = parseStatements /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => [CompileState, T]) => [CompileState, List<T>] */(state /* : CompileState */.pushStructName /* : (arg0 : string) => CompileState */(name /* : () => string */) /* : CompileState */.withTypeParams /* : (arg0 : List<string>) => CompileState */(typeParams /* : () => List<string> */) /* : CompileState */, content /* : string */, (state0, input) => /* compileClassSegment */ (state0 /* : unknown */, input /* : string */, 1 /* : number */) /* : unknown */) /* : [CompileState, List<T>] */;
/* List<ClassSegment> withMaybeConstructor */ ;
if (rawParameters /* : List<Parameter> */.isEmpty /* : () => boolean */() /* : boolean */) {
    magma.Main$Lists$JVMList;
    ff8b8f;
}
else {
    magma.Main$Lists$JVMList;
    c703b;
}
let parsed2 = /* withMaybeConstructor */ .iterate /* : unknown */() /* : unknown */.map /* : unknown */(ClassSegment /* : ClassSegment */.generate /* : unknown */) /* : unknown */.collect /* : unknown */(new Joiner() /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
let generated = /* generatePlaceholder */ (beforeInfix /* : unknown */.strip /* : unknown */() /* : unknown */) /* : unknown */ + targetInfix /* : string */ + name /* : () => string */ + joinedTypeParams /* : unknown */ + /* generatePlaceholder */ (afterTypeParams /* : unknown */) /* : unknown */ + " {" + parsed2 + "\n}\n";
let compileState = statementsTuple /* : [CompileState, List<T>] */[0 /* : number */]() /* : unknown */.popStructName /* : unknown */() /* : unknown */.addStructure /* : unknown */(generated /* : unknown */) /* : unknown */.addType /* : unknown */(new ObjectType(name /* : () => string */, typeParams /* : () => List<string> */, statementsTuple /* : [CompileState, List<T>] */[0 /* : number */]() /* : unknown */.definitions /* : unknown */) /* : ObjectType */) /* : unknown */;
return new Some(new Tuple2Impl(compileState /* : unknown */, new Whitespace() /* : Whitespace */) /* : Tuple2Impl */) /* : Some */;
/* private static */ retainDefinition(parameter, Parameter);
Option < Definition > {
    if(parameter) { } /* : Parameter */, /* : Parameter */ : /* : Parameter */ ._variant /* : unknown */ === ParameterVariant.Definition /* : unknown */
};
{
    magma.Main$Lists$JVMList;
    aed64;
}
return new None() /* : None */;
/* private static */ isSymbol(input, string);
boolean;
{
    /* for (var i = 0; i < input.length(); i++) */ {
        magma.Main$Lists$JVMList;
    }
    return true;
}
/* private static  */ prefix(input, string, prefix, string, mapper, (arg0) => Option);
Option < T > {
    if(, input) { } /* : string */, /* : string */ : /* : string */ .startsWith /* : unknown */(prefix /* : string */) /* : unknown */
};
{
    magma.Main$Lists$JVMList;
    a66b;
}
let slice = input /* : string */.substring /* : unknown */(prefix /* : string */.length /* : unknown */() /* : unknown */) /* : unknown */;
return mapper /* : (arg0 : T) => R */(slice /* : unknown */) /* : R */;
/* private static  */ suffix(input, string, suffix, string, mapper, (arg0) => Option);
Option < T > {
    if(, input) { } /* : string */, /* : string */ : /* : string */ .endsWith /* : unknown */(suffix /* : string */) /* : unknown */
};
{
    magma.Main$Lists$JVMList;
    ac1fdc4;
}
let slice = input /* : string */.substring /* : unknown */(0 /* : number */, input /* : string */.length /* : unknown */() /* : unknown */ - suffix /* : string */.length /* : unknown */() /* : unknown */) /* : unknown */;
return mapper /* : (arg0 : T) => R */(slice /* : unknown */) /* : R */;
/* private static */ compileClassSegment(state, CompileState, input, string, depth, number);
[CompileState, ClassSegment];
{
    return /* Main.<Whitespace, ClassSegment>typed */ (() => /* compileWhitespace */ (input /* : string */, state /* : CompileState */) /* : unknown */) /* : unknown */.or /* : unknown */(() => compileClass /* : (arg0 : string, arg1 : number, arg2 : CompileState) => Option<[CompileState, ClassSegment]> */(input /* : string */, depth /* : number */, state /* : CompileState */) /* : Option<[CompileState, ClassSegment]> */) /* : unknown */.or /* : unknown */(() => compileStructure /* : (arg0 : string, arg1 : string, arg2 : string, arg3 : CompileState) => Option<[CompileState, ClassSegment]> */(input /* : string */, "interface ", "interface ", state /* : CompileState */) /* : Option<[CompileState, ClassSegment]> */) /* : unknown */.or /* : unknown */(() => compileStructure /* : (arg0 : string, arg1 : string, arg2 : string, arg3 : CompileState) => Option<[CompileState, ClassSegment]> */(input /* : string */, "record ", "class ", state /* : CompileState */) /* : Option<[CompileState, ClassSegment]> */) /* : unknown */.or /* : unknown */(() => compileStructure /* : (arg0 : string, arg1 : string, arg2 : string, arg3 : CompileState) => Option<[CompileState, ClassSegment]> */(input /* : string */, "enum ", "class ", state /* : CompileState */) /* : Option<[CompileState, ClassSegment]> */) /* : unknown */.or /* : unknown */(() => /* compileMethod */ (state /* : CompileState */, input /* : string */, depth /* : number */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* compileDefinitionStatement */ (input /* : string */, depth /* : number */, state /* : CompileState */) /* : unknown */) /* : unknown */.orElseGet /* : unknown */(() => new Tuple2Impl(state /* : CompileState */, new Placeholder(input /* : string */) /* : Placeholder */) /* : Tuple2Impl */) /* : unknown */;
}
/* private static  */ typed(action, () => Option);
Option < [CompileState, S] > {
    return: action /* : () => Option<[CompileState, T]> */() /* : Option<[CompileState, T]> */.map /* : (arg0 : (arg0 : [CompileState, T]) => R) => Option<R> */((tuple) => new Tuple2Impl(tuple /* : unknown */.left /* : unknown */() /* : unknown */, tuple /* : unknown */.right /* : unknown */() /* : unknown */) /* : Tuple2Impl */) /* : Option<R> */
};
/* private static */ compileWhitespace(input, string, state, CompileState);
Option < [CompileState, Whitespace] > {
    if(input) { } /* : string */, /* : string */ : /* : string */ .isBlank /* : unknown */() /* : unknown */
};
{
    magma.Main$Lists$JVMList;
    f150435;
}
return new None() /* : None */;
/* private static */ compileMethod(state, CompileState, input, string, depth, number);
Option < [CompileState, ClassSegment] > {
    return: first /* : unknown */(input /* : string */, "(", (definitionString, withParams) => {
        return first /* : unknown */(withParams /* : unknown */, ")", (parametersString, rawContent) => {
            return /* parseDefinition(state, definitionString).<Tuple2<CompileState, Header>>map */ ((tuple) => new Tuple2Impl(tuple /* : unknown */.left /* : unknown */() /* : unknown */, tuple /* : unknown */.right /* : unknown */() /* : unknown */) /* : Tuple2Impl */) /* : unknown */.or /* : unknown */(() => /* parseConstructor */ (state /* : CompileState */, definitionString /* : unknown */) /* : unknown */) /* : unknown */.flatMap /* : unknown */((definitionTuple) => {
                return /* assembleMethod */ (depth /* : number */, parametersString /* : unknown */, rawContent /* : unknown */, definitionTuple /* : unknown */) /* : unknown */;
            }) /* : unknown */;
        }) /* : unknown */;
    }) /* : unknown */
};
/* private static */ assembleMethod(depth, number, parametersString, string, rawContent, string, definitionTuple, [CompileState, Header]);
Option < [CompileState, ClassSegment] > {
    let, definitionState = definitionTuple /* : unknown */.left /* : unknown */() /* : unknown */,
    let, header = definitionTuple /* : unknown */.right /* : unknown */() /* : unknown */,
    let, parametersTuple = (definitionState /* : unknown */, parametersString /* : unknown */) /* : unknown */,
    let, rawParameters = parametersTuple /* : unknown */.right /* : unknown */() /* : unknown */,
    let, parameters = (rawParameters /* : List<Parameter> */) /* : unknown */,
    let, content = rawContent /* : unknown */.strip /* : unknown */() /* : unknown */,
    let, paramTypes: R = parameters /* : List<Definition> */.iterate /* : () => Iterator<T> */() /* : Iterator<T> */.map /* : (arg0 : (arg0 : T) => R) => Option<R> */(Definition /* : Definition */.type /* : unknown */) /* : Option<R> */.collect /* : (arg0 : Collector<T, R>) => R */(new ListCollector() /* : ListCollector */) /* : R */,
    let, toDefine: Definition = header /* : Header */.createDefinition /* : (arg0 : List<Type>) => Definition */(paramTypes /* : List<Type> */) /* : Definition */,
    if(content) { } /* : string */, /* : string */ : /* : string */ .equals /* : unknown */(";") /* : unknown */
};
{
    magma.Main$Lists$JVMList;
    c53fd30;
}
if (content /* : string */.startsWith /* : unknown */("{") /* : unknown */ && content /* : string */.endsWith /* : unknown */("}") /* : unknown */) {
    magma.Main$Lists$JVMList;
    cbc42f;
}
return new None() /* : None */;
/* private static */ parseConstructor(state, CompileState, input, string);
Option < [CompileState, Header] > {
    let, stripped = input /* : string */.strip /* : unknown */() /* : unknown */,
    if(stripped) { } /* : unknown */, /* : unknown */ : /* : unknown */ .equals /* : unknown */(state /* : CompileState */.structNames /* : unknown */.last /* : unknown */() /* : unknown */.orElse /* : unknown */("") /* : unknown */) /* : unknown */
};
{
    magma.Main$Lists$JVMList;
    c2f;
}
return new None() /* : None */;
/* private static */ joinValues(retainParameters, (List));
string;
{
    let inner = retainParameters /* : List<Definition> */.iterate /* : () => Iterator<T> */() /* : Iterator<T> */.map /* : (arg0 : (arg0 : T) => R) => Option<R> */(Definition /* : Definition */.generate /* : unknown */) /* : Option<R> */.collect /* : (arg0 : Collector<T, R>) => R */(new Joiner(", ") /* : Joiner */) /* : R */.orElse /* : unknown */("") /* : unknown */;
    return "(" + inner + ")";
}
/* private static */ retainDefinitions(right, (List));
List < Definition > {
    return: right /* : () => B */() /* : B */.map /* : unknown */(Main /* : Main */.retainDefinition /* : unknown */) /* : unknown */.flatMap /* : unknown */(Iterators /* : Iterators */.fromOption /* : unknown */) /* : unknown */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */
};
/* private static */ parseParameters(state, CompileState, params, string);
[CompileState, (List)];
{
    return /* parseValuesOrEmpty */ (state /* : CompileState */, params /* : List<Parameter> */, (state1, s) => new Some(/* compileParameter */ (state1 /* : unknown */, s /* : unknown */) /* : unknown */) /* : Some */) /* : unknown */;
}
/* private static */ parseFunctionSegments(state, CompileState, input, string, depth, number);
[CompileState, (List)];
{
    return parseStatements /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => [CompileState, T]) => [CompileState, List<T>] */(state /* : CompileState */, input /* : string */, (state1, input1) => /* parseFunctionSegment */ (state1 /* : unknown */, input1 /* : unknown */, depth /* : number */ + 1 /* : number */) /* : unknown */) /* : [CompileState, List<T>] */;
}
/* private static */ parseFunctionSegment(state, CompileState, input, string, depth, number);
[CompileState, FunctionSegment];
{
    let stripped = input /* : string */.strip /* : unknown */() /* : unknown */;
    if (stripped /* : unknown */.isEmpty /* : unknown */() /* : unknown */) {
        magma.Main$Lists$JVMList;
        ba1e;
    }
    return /* parseFunctionStatement */ (state /* : CompileState */, depth /* : number */, stripped /* : unknown */) /* : unknown */.or /* : unknown */(() => /* compileBlock */ (state /* : CompileState */, depth /* : number */, stripped /* : unknown */) /* : unknown */) /* : unknown */.orElseGet /* : unknown */(() => new Tuple2Impl(state /* : CompileState */, new Placeholder(stripped /* : unknown */) /* : Placeholder */) /* : Tuple2Impl */) /* : unknown */;
}
/* private static */ parseFunctionStatement(state, CompileState, depth, number, stripped, string);
Option < [CompileState, FunctionSegment] > {
    return: suffix /* : string */(stripped /* : unknown */, ";", (s) => {
        let tuple = /* compileStatementValue */ (state /* : CompileState */, s /* : unknown */, depth /* : number */) /* : unknown */;
        return new Some(new Tuple2Impl(tuple /* : unknown */.left /* : unknown */() /* : unknown */, () => /* createIndent */ (depth /* : number */) /* : unknown */ + tuple /* : unknown */.right /* : unknown */() /* : unknown */ + ";") /* : Tuple2Impl */) /* : Some */;
    }) /* : unknown */
};
/* private static */ compileBlock(state, CompileState, depth, number, stripped, string);
Option < [CompileState, FunctionSegment] > {
    return: suffix /* : string */(stripped /* : unknown */, "}", (withoutEnd) => {
        return /* split */ (() => /* toFirst */ (withoutEnd /* : unknown */) /* : unknown */, (beforeContent, content) => {
            return suffix /* : string */(beforeContent /* : unknown */, "{", (s) => {
                let compiled = parseFunctionSegments /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, List<FunctionSegment>] */(state /* : CompileState */, content /* : string */, depth /* : number */) /* : [CompileState, List<FunctionSegment>] */;
                let indent = /* createIndent */ (depth /* : number */) /* : unknown */;
                let headerTuple = /* compileBlockHeader */ (state /* : CompileState */, s /* : unknown */, depth /* : number */) /* : unknown */;
                let headerState = headerTuple /* : unknown */.left /* : unknown */() /* : unknown */;
                let header = headerTuple /* : unknown */.right /* : unknown */() /* : unknown */;
                return new Some(new Tuple2Impl(headerState /* : unknown */, () => indent /* : unknown */ + header /* : Header */ + "{" + compiled.right() + indent + "}") /* : Tuple2Impl */) /* : Some */;
            }) /* : unknown */;
        }) /* : unknown */;
    }) /* : unknown */
};
/* private static */ toFirst(input, string);
Option < [string, string] > {
    let, divisions: (List) = divideAll /* : (arg0 : string, arg1 : (arg0 : DivideState, arg1 : string) => DivideState) => List<string> */(input /* : string */, Main /* : Main */.foldBlockStart /* : unknown */) /* : List<string> */,
    return: divisions /* : List<string> */.removeFirst /* : () => Option<[T, List<T>]> */() /* : Option<[T, List<T>]> */.map /* : (arg0 : (arg0 : [T, List<T>]) => R) => Option<R> */((removed) => {
        let right = removed /* : [T, List<T>] */[0 /* : number */]() /* : unknown */;
        let left = removed /* : [T, List<T>] */[1 /* : number */]() /* : unknown */.iterate /* : unknown */() /* : unknown */.collect /* : unknown */(new Joiner("") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        return new Tuple2Impl(right /* : () => B */, left /* : () => A */) /* : Tuple2Impl */;
    }) /* : Option<R> */
};
/* private static */ compileBlockHeader(state, CompileState, input, string, depth, number);
[CompileState, string];
{
    let stripped = input /* : string */.strip /* : unknown */() /* : unknown */;
    return /* compileConditional */ (state /* : CompileState */, stripped /* : unknown */, "if", depth /* : number */) /* : unknown */.or /* : unknown */(() => /* compileConditional */ (state /* : CompileState */, stripped /* : unknown */, "while", depth /* : number */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* compileElse */ (state /* : CompileState */, input /* : string */) /* : unknown */) /* : unknown */.orElseGet /* : unknown */(() => new Tuple2Impl(state /* : CompileState */, /* generatePlaceholder */ (stripped /* : unknown */) /* : unknown */) /* : Tuple2Impl */) /* : unknown */;
}
/* private static */ compileElse(state, CompileState, input, string);
Option < [CompileState, string] > {
    let, stripped = input /* : string */.strip /* : unknown */() /* : unknown */,
    if(stripped) { } /* : unknown */, /* : unknown */ : /* : unknown */ .equals /* : unknown */("else") /* : unknown */
};
{
    magma.Main$Lists$JVMList;
    b6d03;
}
return new None() /* : None */;
/* private static */ compileConditional(state, CompileState, input, string, prefix, string, depth, number);
Option < [CompileState, string] > {
    return: prefix /* : string */(input /* : string */, prefix /* : string */, (withoutPrefix) => {
        return prefix /* : string */(withoutPrefix /* : unknown */.strip /* : unknown */() /* : unknown */, "(", (withoutValueStart) => {
            return suffix /* : string */(withoutValueStart /* : unknown */, ")", (value) => {
                let compiled = /* compileValue */ (state /* : CompileState */, value /* : T */, depth /* : number */) /* : unknown */;
                return new Some(new Tuple2Impl(compiled /* : [CompileState, List<FunctionSegment>] */[0 /* : number */]() /* : unknown */, prefix /* : string */ + " (" + compiled.right() + ")") /* : Tuple2Impl */) /* : Some */;
            }) /* : unknown */;
        }) /* : unknown */;
    }) /* : unknown */
};
/* private static */ foldBlockStart(state, DivideState, c, string);
DivideState;
{
    let appended = state /* : CompileState */.append /* : (arg0 : string) => DivideState */(c /* : string */) /* : DivideState */;
    if (c /* : string */ ===  /*  '{'  */ && state /* : CompileState */.isLevel /* : unknown */() /* : unknown */) {
        magma.Main$Lists$JVMList;
    }
    if (c /* : string */ ===  /*  '{' */) {
        magma.Main$Lists$JVMList;
    }
    if (c /* : string */ ===  /*  '}' */) {
        magma.Main$Lists$JVMList;
        c64813;
    }
    return appended /* : unknown */;
}
/* private static */ compileStatementValue(state, CompileState, input, string, depth, number);
[CompileState, string];
{
    let stripped = input /* : string */.strip /* : unknown */() /* : unknown */;
    if (stripped /* : unknown */.startsWith /* : unknown */("return ") /* : unknown */) {
        magma.Main$Lists$JVMList;
        cf72fd;
    }
    return /* compileAssignment */ (state /* : CompileState */, depth /* : number */, stripped /* : unknown */) /* : unknown */.orElseGet /* : unknown */(() => {
        return new Tuple2Impl(state /* : CompileState */, /* generatePlaceholder */ (stripped /* : unknown */) /* : unknown */) /* : Tuple2Impl */;
    }) /* : unknown */;
}
/* private static */ compileAssignment(state, CompileState, depth, number, stripped, string);
Option < Tuple2Impl < CompileState, string >> {
    return: first /* : unknown */(stripped /* : unknown */, "=", (beforeEquals, valueString) => {
        let sourceTuple = /* parseValue */ (state /* : CompileState */, valueString /* : unknown */, depth /* : number */) /* : unknown */;
        let sourceState = sourceTuple /* : unknown */.left /* : unknown */() /* : unknown */;
        let source = sourceTuple /* : unknown */.right /* : unknown */() /* : unknown */;
        let generatedSource = source /* : unknown */.generate /* : unknown */() /* : unknown */;
        return /* parseDefinition */ (sourceState /* : unknown */, beforeEquals /* : unknown */) /* : unknown */.flatMap /* : unknown */((definitionTuple) => {
            let definitionState = definitionTuple /* : unknown */.left /* : unknown */() /* : unknown */;
            let definition = definitionTuple /* : unknown */.right /* : unknown */() /* : unknown */.mapType /* : unknown */((type) => {
                if (type /* : () => Type */() /* Primitive */.Unknown /* : unknown */)
                    ;
            } /* : Type */), { magma };
        }).Main$Lists$JVMList;
        f44;
    }),
    else: { magma, : .Main$Lists$JVMList }
};
d3411d;
;
return new Some(new Tuple2Impl(definitionState /* : unknown */.withDefinition /* : unknown */(definition /* : unknown */) /* : unknown */, "let " + definition /* : unknown */.generate /* : unknown */() /* : unknown */ + " = " + generatedSource /* : unknown */) /* : Tuple2Impl */) /* : Some */;
or /* : unknown */(() => {
    let destinationTuple = /* compileValue */ (sourceState /* : unknown */, beforeEquals /* : unknown */, depth /* : number */) /* : unknown */;
    let destinationState = destinationTuple /* : unknown */.left /* : unknown */() /* : unknown */;
    let destinationString = destinationTuple /* : unknown */.right /* : unknown */() /* : unknown */;
    return new Some(new Tuple2Impl(destinationState /* : unknown */, destinationString /* : unknown */ + " = " + generatedSource /* : unknown */) /* : Tuple2Impl */) /* : Some */;
}) /* : unknown */;
;
/* private static */ compileValue(state, CompileState, input, string, depth, number);
[CompileState, string];
{
    let tuple = /* parseValue */ (state /* : CompileState */, input /* : string */, depth /* : number */) /* : unknown */;
    return new Tuple2Impl(tuple /* : unknown */.left /* : unknown */() /* : unknown */, tuple /* : unknown */.right /* : unknown */() /* : unknown */.generate /* : unknown */() /* : unknown */) /* : Tuple2Impl */;
}
/* private static */ parseValue(state, CompileState, input, string, depth, number);
[CompileState, Value];
{
    return /* parseBoolean */ (state /* : CompileState */, input /* : string */) /* : unknown */.or /* : unknown */(() => /* parseLambda */ (state /* : CompileState */, input /* : string */, depth /* : number */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseString */ (state /* : CompileState */, input /* : string */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseDataAccess */ (state /* : CompileState */, input /* : string */, depth /* : number */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseSymbolValue */ (state /* : CompileState */, input /* : string */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseInvokable */ (state /* : CompileState */, input /* : string */, depth /* : number */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseDigits */ (state /* : CompileState */, input /* : string */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseOperation */ (state /* : CompileState */, input /* : string */, depth /* : number */, /* Operator */ .ADD /* : unknown */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseOperation */ (state /* : CompileState */, input /* : string */, depth /* : number */, /* Operator */ .EQUALS /* : unknown */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseOperation */ (state /* : CompileState */, input /* : string */, depth /* : number */, /* Operator */ .SUBTRACT /* : unknown */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseOperation */ (state /* : CompileState */, input /* : string */, depth /* : number */, /* Operator */ .AND /* : unknown */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseOperation */ (state /* : CompileState */, input /* : string */, depth /* : number */, /* Operator */ .OR /* : unknown */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseOperation */ (state /* : CompileState */, input /* : string */, depth /* : number */,  /*  Operator.GREATER_THAN_OR_EQUALS */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseNot */ (state /* : CompileState */, input /* : string */, depth /* : number */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseMethodReference */ (state /* : CompileState */, input /* : string */, depth /* : number */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseInstanceOf */ (state /* : CompileState */, input /* : string */, depth /* : number */) /* : unknown */) /* : unknown */.orElseGet /* : unknown */(() => new Tuple2Impl(state /* : CompileState */, new Placeholder(input /* : string */) /* : Placeholder */) /* : Tuple2Impl<CompileState, Value> */) /* : unknown */;
}
/* private static */ parseBoolean(state, CompileState, input, string);
Option < [CompileState, Value] > {
    let, stripped = input /* : string */.strip /* : unknown */() /* : unknown */,
    if(stripped) { } /* : unknown */, /* : unknown */ : /* : unknown */ .equals /* : unknown */("false") /* : unknown */
};
{
    magma.Main$Lists$JVMList;
    bf400;
}
if (stripped /* : unknown */.equals /* : unknown */("true") /* : unknown */) {
    magma.Main$Lists$JVMList;
    a06946;
}
return new None() /* : None */;
/* private static */ parseInstanceOf(state, CompileState, input, string, depth, number);
Option < [CompileState, Value] > {
    return: last /* : () => Option<T> */(input /* : string */, "instanceof", (s, s2) => {
        let childTuple = parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state /* : CompileState */, s /* : unknown */, depth /* : number */) /* : [CompileState, Value] */;
        return /* parseDefinition */ (childTuple /* : [CompileState, Value] */[0 /* : number */]() /* : unknown */, s2 /* : unknown */) /* : unknown */.map /* : unknown */((definitionTuple) => {
            let value = childTuple /* : [CompileState, Value] */[1 /* : number */]() /* : unknown */;
            let definition = definitionTuple /* : unknown */.right /* : unknown */() /* : unknown */;
            let variant = new DataAccess(value /* : T */, "_variant") /* Primitive */.Unknown;
        } /* : unknown */) /* : DataAccess */;
        let type = value /* : T */.type /* : unknown */() /* : unknown */;
        let generate = type /* : () => Type */() /* : Type */.orElse /* : (arg0 : T) => T */("") /* : T */;
        let temp = new SymbolValue(generate /* : () => string */ + "Variant." + definition /* : unknown */.type /* : unknown */() /* : unknown */.findName /* : unknown */() /* : unknown */.orElse /* : unknown */("") /* : unknown */) /* Primitive */.Unknown;
    } /* : unknown */) /* : SymbolValue */,
    return: new Tuple2Impl(definitionTuple /* : unknown */.left /* : unknown */() /* : unknown */, new Operation(variant /* : DataAccess */) /* Operator */.EQUALS /* : unknown */, temp /* : SymbolValue */) /* : Operation */
};
;
;
/* private static */ parseMethodReference(state, CompileState, input, string, depth, number);
Option < [CompileState, Value] > {
    return: last /* : () => Option<T> */(input /* : string */, "::", (s, s2) => {
        let tuple = parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state /* : CompileState */, s /* : unknown */, depth /* : number */) /* : [CompileState, Value] */;
        return new Some(new Tuple2Impl(tuple /* : unknown */.left /* : unknown */() /* : unknown */, new DataAccess(tuple /* : unknown */.right /* : unknown */() /* : unknown */, s2 /* : unknown */) /* Primitive */.Unknown /* : unknown */) /* : DataAccess */);
    } /* : Tuple2Impl */) /* : Some */
};
;
/* private static */ parseNot(state, CompileState, input, string, depth, number);
Option < [CompileState, Value] > {
    let, stripped = input /* : string */.strip /* : unknown */() /* : unknown */,
    if(stripped) { } /* : unknown */, /* : unknown */ : /* : unknown */ .startsWith /* : unknown */("!") /* : unknown */
};
{
    magma.Main$Lists$JVMList;
    f03bb1;
}
return new None() /* : None */;
/* private static */ parseLambda(state, CompileState, input, string, depth, number);
Option < [CompileState, Value] > {
    return: first /* : unknown */(input /* : string */, "->", (beforeArrow, valueString) => {
        let strippedBeforeArrow = beforeArrow /* : unknown */.strip /* : unknown */() /* : unknown */;
        if (isSymbol /* : (arg0 : string) => boolean */(strippedBeforeArrow /* : unknown */) /* : boolean */) {
            magma.Main$Lists$JVMList;
            cca7;
        }
        if (strippedBeforeArrow /* : unknown */.startsWith /* : unknown */("(") /* : unknown */ && strippedBeforeArrow /* : unknown */.endsWith /* : unknown */(")") /* : unknown */) {
            magma.Main$Lists$JVMList;
            fe5c6f;
        }
        return new None() /* : None */;
    }) /* : unknown */
};
/* private static */ assembleLambda(state, CompileState, definitions, (List), valueString, string, depth, number);
Some < [CompileState, Value] > {
    let, strippedValueString = valueString /* : unknown */.strip /* : unknown */() /* : unknown */,
    let, state2: CompileState = state /* : CompileState */.withDefinitions /* : (arg0 : List<Definition>) => CompileState */(definitions /* : List<Definition> */) /* : CompileState */,
    if(strippedValueString) { } /* : unknown */, /* : unknown */ : /* : unknown */ .startsWith /* : unknown */("{") /* : unknown */ && strippedValueString /* : unknown */.endsWith /* : unknown */("}") /* : unknown */
};
{
    magma.Main$Lists$JVMList;
    de728;
}
{
    magma.Main$Lists$JVMList;
}
let right = value /* : T */.right /* : unknown */() /* : unknown */;
return new Some(new Tuple2Impl(value /* : T */.left /* : unknown */() /* : unknown */, new Lambda(definitions /* : List<Definition> */, right /* : () => B */) /* : Lambda */) /* : Tuple2Impl */) /* : Some */;
/* private static */ parseDigits(state, CompileState, input, string);
Option < [CompileState, Value] > {
    let, stripped = input /* : string */.strip /* : unknown */() /* : unknown */,
    if() { }
} /* isNumber */(stripped /* : unknown */); /* : unknown */
{
    magma.Main$Lists$JVMList;
    a92922;
}
return new None() /* : None */;
/* private static */ isNumber(input, string);
boolean;
{
    /* String maybeTruncated */ ;
    if (input /* : string */.startsWith /* : unknown */("-") /* : unknown */) {
        magma.Main$Lists$JVMList;
        f2a7d5;
    }
    else {
        magma.Main$Lists$JVMList;
        cfb4a64;
    }
    return /* areAllDigits */ ( /* maybeTruncated */) /* : unknown */;
}
/* private static */ areAllDigits(input, string);
boolean;
{
    /* for (var i = 0; i < input.length(); i++) */ {
        magma.Main$Lists$JVMList;
        c6c;
    }
    return true;
}
/* private static */ parseInvokable(state, CompileState, input, string, depth, number);
Option < [CompileState, Value] > {
    return: suffix /* : string */(input /* : string */.strip /* : unknown */() /* : unknown */, ")", (withoutEnd) => {
        return /* split */ (() => /* toLast */ (withoutEnd /* : unknown */, "", Main /* : Main */.foldInvocationStart /* : unknown */) /* : unknown */, (callerWithEnd, argumentsString) => {
            return suffix /* : string */(callerWithEnd /* : unknown */, "(", (callerString) => {
                return /* assembleInvokable */ (state /* : CompileState */, depth /* : number */, argumentsString /* : unknown */, callerString /* : unknown */.strip /* : unknown */() /* : unknown */) /* : unknown */;
            }) /* : unknown */;
        }) /* : unknown */;
    }) /* : unknown */
};
/* private static */ assembleInvokable(state, CompileState, depth, number, argumentsString, string, callerString, string);
Some < [CompileState, Value] > {
    let, callerTuple = (state /* : CompileState */, depth /* : number */, callerString /* : unknown */) /* : unknown */,
    let, oldCallerState = callerTuple /* : unknown */.left /* : unknown */() /* : unknown */,
    let, oldCaller = callerTuple /* : unknown */.right /* : unknown */() /* : unknown */,
    let, newCaller = (oldCallerState /* : unknown */, oldCaller /* : unknown */) /* : unknown */,
    let, callerType = (newCaller /* : unknown */) /* : unknown */,
    let, argumentsTuple = (oldCallerState /* : unknown */, argumentsString /* : unknown */, (currentState, pair) => {
        let index = pair /* : unknown */.left /* : unknown */() /* : unknown */;
        let element = pair /* : unknown */.right /* : unknown */() /* : unknown */;
        let expectedType = callerType /* : unknown */.arguments /* : unknown */.get /* : unknown */(index /* : T */) /* : unknown */.orElse /* : unknown */() /* Primitive */.Unknown /* : unknown */ /* : unknown */;
        let withExpected = currentState /* : unknown */.withExpectedType /* : unknown */(expectedType /* : unknown */) /* : unknown */;
        let valueTuple = /* parseArgument */ (withExpected /* : unknown */, element /* : unknown */, depth /* : number */) /* : unknown */;
        let valueState = valueTuple /* : unknown */.left /* : unknown */() /* : unknown */;
        let value = valueTuple /* : unknown */.right /* : unknown */() /* : unknown */;
        let actualType = valueTuple /* : unknown */.left /* : unknown */() /* : unknown */.typeRegister /* : unknown */.orElse /* : unknown */() /* Primitive */.Unknown /* : unknown */ /* : unknown */;
        return new Some(new Tuple2Impl(valueState /* : unknown */, new Tuple2Impl(value /* : T */, actualType /* : unknown */) /* : Tuple2Impl */) /* : Tuple2Impl */) /* : Some */;
    }) /* : unknown */.orElseGet /* : unknown */(() => new Tuple2Impl(oldCallerState /* : unknown */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */) /* : Tuple2Impl */) /* : unknown */,
    let, argumentsState = argumentsTuple /* : unknown */.left /* : unknown */() /* : unknown */,
    let, argumentsWithActualTypes = argumentsTuple /* : unknown */.right /* : unknown */() /* : unknown */,
    let, arguments = argumentsWithActualTypes /* : unknown */.iterate /* : unknown */() /* : unknown */.map /* : unknown */(Tuple2 /* : Tuple2 */.left /* : unknown */) /* : unknown */.map /* : unknown */(Main /* : Main */.retainValue /* : unknown */) /* : unknown */.flatMap /* : unknown */(Iterators /* : Iterators */.fromOption /* : unknown */) /* : unknown */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */,
    let, invokable: Invokable = new Invokable(newCaller /* : unknown */, arguments /* : unknown */, callerType /* : unknown */.returns /* : unknown */) /* : Invokable */,
    return: new Some(new Tuple2Impl(argumentsState /* : unknown */, invokable /* : Invokable */) /* : Tuple2Impl */) /* : Some */
};
/* private static */ retainValue(argument, Argument);
Option < Value > {
    if(argument) { } /* : Argument */, /* : Argument */ : /* : Argument */ ._variant /* : unknown */ === ArgumentVariant.Value /* : unknown */
};
{
    magma.Main$Lists$JVMList;
    b6995df;
}
return new None() /* : None */;
/* private static */ parseArgument(state, CompileState, element, string, depth, number);
[CompileState, Argument];
{
    if (element /* : unknown */.isEmpty /* : unknown */() /* : unknown */) {
        magma.Main$Lists$JVMList;
        fc14f68;
    }
    let tuple = parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state /* : CompileState */, element /* : unknown */, depth /* : number */) /* : [CompileState, Value] */;
    return new Tuple2Impl(tuple /* : unknown */.left /* : unknown */() /* : unknown */, tuple /* : unknown */.right /* : unknown */() /* : unknown */) /* : Tuple2Impl */;
}
/* private static */ findCallerType(newCaller, Caller);
FunctionType;
{
    let callerType = new FunctionType(Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */, Unknown /* : unknown */) /* : FunctionType */;
    /* switch (newCaller) */ {
        magma.Main$Lists$JVMList;
        f989e;
    }
    return callerType /* : unknown */;
}
/* private static */ modifyCaller(state, CompileState, oldCaller, Caller);
Caller;
{
    if (oldCaller /* : unknown */._variant /* : unknown */ === Variant.DataAccess /* : unknown */) {
        magma.Main$Lists$JVMList;
        bfd;
    }
    return oldCaller /* : unknown */;
}
/* private static */ resolveType(value, Value, state, CompileState);
Type;
{
    return value /* : T */.type /* : unknown */() /* : unknown */;
}
/* private static */ invocationHeader(state, CompileState, depth, number, callerString1, string);
[CompileState, Caller];
{
    if (callerString1 /* : string */.startsWith /* : unknown */("new ") /* : unknown */) {
        magma.Main$Lists$JVMList;
        d8f;
    }
    let tuple = parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state /* : CompileState */, callerString1 /* : string */, depth /* : number */) /* : [CompileState, Value] */;
    return new Tuple2Impl(tuple /* : unknown */.left /* : unknown */() /* : unknown */, tuple /* : unknown */.right /* : unknown */() /* : unknown */) /* : Tuple2Impl */;
}
/* private static */ foldInvocationStart(state, DivideState, c, string);
DivideState;
{
    let appended = state /* : CompileState */.append /* : (arg0 : string) => DivideState */(c /* : string */) /* : DivideState */;
    if (c /* : string */ ===  /*  '(' */) {
        magma.Main$Lists$JVMList;
        b84c0;
    }
    if (c /* : string */ ===  /*  ')' */) {
        magma.Main$Lists$JVMList;
        a52fbd;
    }
    return appended /* : unknown */;
}
/* private static */ parseDataAccess(state, CompileState, input, string, depth, number);
Option < [CompileState, Value] > {
    return: last /* : () => Option<T> */(input /* : string */.strip /* : unknown */() /* : unknown */, ".", (parentString, rawProperty) => {
        let property = rawProperty /* : unknown */.strip /* : unknown */() /* : unknown */;
        if (!isSymbol /* : (arg0 : string) => boolean */(property /* : unknown */) /* : unknown */) {
            magma.Main$Lists$JVMList;
            cb;
        }
        let tuple = parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state /* : CompileState */, parentString /* : unknown */, depth /* : number */) /* : [CompileState, Value] */;
        let parent = tuple /* : unknown */.right /* : unknown */() /* : unknown */;
        let parentType = parent /* : unknown */.type /* : unknown */() /* : unknown */;
        if ( /* parentType instanceof TupleType */) {
            magma.Main$Lists$JVMList;
            d9750;
        }
        let type = /* Primitive */ .Unknown /* : unknown */;
        if (parentType /* : unknown */._variant /* : unknown */ === Variant.FindableType /* : unknown */) {
            magma.Main$Lists$JVMList;
            c0369c4;
        }
        return new Some(new Tuple2Impl(tuple /* : unknown */.left /* : unknown */() /* : unknown */, new DataAccess(parent /* : unknown */, property /* : unknown */, type /* : () => Type */) /* : DataAccess */) /* : Tuple2Impl */) /* : Some */;
    }) /* : Option<T> */
};
/* private static */ parseString(state, CompileState, input, string);
Option < [CompileState, Value] > {
    let, stripped = input /* : string */.strip /* : unknown */() /* : unknown */,
    if(stripped) { } /* : unknown */, /* : unknown */ : /* : unknown */ .startsWith /* : unknown */("\"") /* : unknown */ && stripped /* : unknown */.endsWith /* : unknown */("\"") /* : unknown */
};
{
    magma.Main$Lists$JVMList;
    c0b17;
}
return new None() /* : None */;
/* private static */ parseSymbolValue(state, CompileState, value, string);
Option < [CompileState, Value] > {
    let, stripped = value /* : T */.strip /* : unknown */() /* : unknown */,
    if(isSymbol) { }
} /* : (arg0 : string) => boolean */(stripped /* : unknown */); /* : boolean */
{
    magma.Main$Lists$JVMList;
    d4e2ba;
}
return new None() /* : None */;
/* private static */ parseOperation(state, CompileState, value, string, depth, number, operator);
Option < [CompileState, Value] > {
    return: first /* : unknown */(value /* : T */, operator /* : content-start Operator content-end */.sourceRepresentation /* : unknown */, (leftString, rightString) => {
        let leftTuple = parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state /* : CompileState */, leftString /* : unknown */, depth /* : number */) /* : [CompileState, Value] */;
        let rightTuple = parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(leftTuple /* : [CompileState, Value] */[0 /* : number */]() /* : unknown */, rightString /* : unknown */, depth /* : number */) /* : [CompileState, Value] */;
        let left = leftTuple /* : [CompileState, Value] */[1 /* : number */]() /* : unknown */;
        let right = rightTuple /* : [CompileState, Value] */[1 /* : number */]() /* : unknown */;
        return new Some(new Tuple2Impl(rightTuple /* : [CompileState, Value] */[0 /* : number */]() /* : unknown */, new Operation(left /* : () => A */, operator /* : content-start Operator content-end */, right /* : () => B */) /* : Operation */) /* : Tuple2Impl */) /* : Some */;
    }) /* : unknown */
};
/* private static  */ parseValuesOrEmpty(state, CompileState, input, string, mapper, (arg0, arg1) => Option);
[CompileState, (List)];
{
    return /* parseValues */ (state /* : CompileState */, input /* : string */, mapper /* : (arg0 : T) => R */) /* : unknown */.orElseGet /* : unknown */(() => new Tuple2Impl(state /* : CompileState */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */) /* : Tuple2Impl */) /* : unknown */;
}
/* private static  */ parseValues(state, CompileState, input, string, mapper, (arg0, arg1) => Option);
Option < [CompileState, (List)] > {
    return /* parseValuesWithIndices */(state /* : CompileState */, input /* : string */) { }
}(state1, tuple);
mapper /* : (arg0 : T) => R */(state1 /* : unknown */, tuple /* : unknown */.right /* : unknown */() /* : unknown */); /* : R */
;
/* private static  */ parseValuesWithIndices(state, CompileState, input, string, mapper, (arg0, arg1) => Option);
Option < [CompileState, (List)] > {
    return: parseAll /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : DivideState, arg1 : string) => DivideState, arg3 : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]>) => Option<[CompileState, List<T>]> */(state /* : CompileState */, input /* : string */, Main /* : Main */.foldValueChar /* : unknown */, mapper /* : (arg0 : T) => R */) /* : Option<[CompileState, List<T>]> */
};
/* private static */ compileParameter(state, CompileState, input, string);
[CompileState, Parameter];
{
    if (input /* : string */.isBlank /* : unknown */() /* : unknown */) {
        magma.Main$Lists$JVMList;
        bb11784;
    }
    return /* parseDefinition */ (state /* : CompileState */, input /* : string */) /* : unknown */.map /* : unknown */((tuple) => new Tuple2Impl(tuple /* : unknown */.left /* : unknown */() /* : unknown */, tuple /* : unknown */.right /* : unknown */() /* : unknown */) /* : Tuple2Impl<CompileState, Parameter> */) /* : unknown */.orElseGet /* : unknown */(() => new Tuple2Impl(state /* : CompileState */, new Placeholder(input /* : string */) /* : Placeholder */) /* : Tuple2Impl */) /* : unknown */;
}
/* private static */ compileDefinition(state, CompileState, input, string);
[CompileState, string];
{
    return /* parseDefinition */ (state /* : CompileState */, input /* : string */) /* : unknown */.map /* : unknown */((tuple) => new Tuple2Impl(tuple /* : unknown */.left /* : unknown */() /* : unknown */, tuple /* : unknown */.right /* : unknown */() /* : unknown */.generate /* : unknown */() /* : unknown */) /* : Tuple2Impl */) /* : unknown */.orElseGet /* : unknown */(() => new Tuple2Impl(state /* : CompileState */, /* generatePlaceholder */ (input /* : string */) /* : unknown */) /* : Tuple2Impl */) /* : unknown */;
}
/* private static */ createIndent(depth, number);
string;
{
    return "\n" + "\t".repeat /* : unknown */(depth /* : number */) /* : unknown */;
}
/* private static */ compileDefinitionStatement(input, string, depth, number, state, CompileState);
Option < [CompileState, ClassSegment] > {
    return: suffix /* : string */(input /* : string */.strip /* : unknown */() /* : unknown */, ";", (withoutEnd) => {
        return /* parseDefinition */ (state /* : CompileState */, withoutEnd /* : unknown */) /* : unknown */.map /* : unknown */((result) => {
            let definition = result /* : unknown */.right /* : unknown */() /* : unknown */;
            return new Tuple2Impl(result /* : unknown */.left /* : unknown */() /* : unknown */, new DefinitionStatement(depth /* : number */, definition /* : unknown */) /* : DefinitionStatement */) /* : Tuple2Impl */;
        }) /* : unknown */;
    }) /* : unknown */
};
/* private static */ parseDefinition(state, CompileState, input, string);
Option < [CompileState, Definition] > {
    return: last /* : () => Option<T> */(input /* : string */.strip /* : unknown */() /* : unknown */, " ", (beforeName, name) => {
        return /* split */ (() => /* toLast */ (beforeName /* : unknown */, " ", Main /* : Main */.foldTypeSeparator /* : unknown */) /* : unknown */, (beforeType, type) => {
            return suffix /* : string */(beforeType /* : unknown */.strip /* : unknown */() /* : unknown */, ">", (withoutTypeParamStart) => {
                return first /* : unknown */(withoutTypeParamStart /* : unknown */, "<", (beforeTypeParams, typeParamsString) => {
                    let /* final */ compileStateStringTupleBiFunction = (state1, s) => new Tuple2Impl(state1 /* : unknown */, s /* : unknown */.strip /* : unknown */() /* : unknown */) /* : Tuple2Impl */;
                    let typeParams = parseValuesOrEmpty /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) => [CompileState, List<T>] */(state /* : CompileState */, typeParamsString /* : unknown */, (state1, s) => new Some(compileStateStringTupleBiFunction /* : (arg0 : CompileState, arg1 : string) => [CompileState, string] */(state1 /* : unknown */, s /* : unknown */) /* : [CompileState, string] */) /* : Some */) /* : [CompileState, List<T>] */;
                    return /* assembleDefinition */ (typeParams /* : () => List<string> */() /* : List<string> */, new Some(beforeTypeParams /* : unknown */) /* : Some<string> */, name /* : () => string */, typeParams /* : () => List<string> */() /* : List<string> */, type /* : () => Type */) /* : unknown */;
                }) /* : unknown */;
            }) /* : unknown */.or /* : unknown */(() => {
                return /* assembleDefinition */ (state /* : CompileState */, new Some(beforeType /* : unknown */) /* : Some<string> */, name /* : () => string */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */, type /* : () => Type */) /* : unknown */;
            }) /* : unknown */;
        }) /* : unknown */.or /* : unknown */(() => {
            return /* assembleDefinition */ (state /* : CompileState */, new None() /* : None<string> */, name /* : () => string */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */, beforeName /* : unknown */) /* : unknown */;
        }) /* : unknown */;
    }) /* : Option<T> */
};
/* private static */ toLast(input, string, separator, string, folder, (arg0, arg1) => DivideState);
Option < [string, string] > {
    let, divisions: (List) = divideAll /* : (arg0 : string, arg1 : (arg0 : DivideState, arg1 : string) => DivideState) => List<string> */(input /* : string */, folder /* : (arg0 : R, arg1 : T) => R */) /* : List<string> */,
    return: divisions /* : List<string> */.removeLast /* : () => Option<[List<T>, T]> */() /* : Option<[List<T>, T]> */.map /* : (arg0 : (arg0 : [List<T>, T]) => R) => Option<R> */((removed) => {
        let left = removed /* : [T, List<T>] */[0 /* : number */]() /* : unknown */.iterate /* : unknown */() /* : unknown */.collect /* : unknown */(new Joiner(separator /* : string */) /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        let right = removed /* : [T, List<T>] */[1 /* : number */]() /* : unknown */;
        return new Tuple2Impl(left /* : () => A */, right /* : () => B */) /* : Tuple2Impl */;
    }) /* : Option<R> */
};
/* private static */ foldTypeSeparator(state, DivideState, c, string);
DivideState;
{
    if (c /* : string */ ===  /*  ' '  */ && state /* : CompileState */.isLevel /* : unknown */() /* : unknown */) {
        magma.Main$Lists$JVMList;
        a10788;
    }
    let appended = state /* : CompileState */.append /* : (arg0 : string) => DivideState */(c /* : string */) /* : DivideState */;
    if (c /* : string */ ===  /*  '<' */) {
        magma.Main$Lists$JVMList;
        c658;
    }
    if (c /* : string */ ===  /*  '>' */) {
        magma.Main$Lists$JVMList;
        bd0;
    }
    return appended /* : unknown */;
}
/* private static */ assembleDefinition(state, CompileState, beforeTypeParams, (Option), name, string, typeParams, (List), type, string);
Option < [CompileState, Definition] > {
    return /* parseType */(state) { } /* : CompileState */, /* : CompileState */ : /* : CompileState */ .withTypeParams /* : (arg0 : List<string>) => CompileState */(typeParams /* : () => List<string> */) /* : CompileState */, type /* : () => Type */, /* : unknown */ : /* : unknown */ .map /* : unknown */((type1) => {
        let node = new ImmutableDefinition(beforeTypeParams /* : unknown */, name /* : () => string */() /* : string */, type1 /* : unknown */.right /* : unknown */() /* : unknown */, typeParams /* : () => List<string> */) /* : ImmutableDefinition */;
        return new Tuple2Impl(type1 /* : unknown */.left /* : unknown */() /* : unknown */, node /* : ImmutableDefinition */) /* : Tuple2Impl */;
    }) /* : unknown */
};
/* private static */ foldValueChar(state, DivideState, c, string);
DivideState;
{
    if (c /* : string */ ===  /*  ','  */ && state /* : CompileState */.isLevel /* : unknown */() /* : unknown */) {
        magma.Main$Lists$JVMList;
        fd17e3;
    }
    let appended = state /* : CompileState */.append /* : (arg0 : string) => DivideState */(c /* : string */) /* : DivideState */;
    if (c /* : string */ === /*  ' */ - /* ' */) {
        magma.Main$Lists$JVMList;
        cdbc5d3;
    }
    if (c /* : string */ ===  /*  '<'  */ || c /* : string */ ===  /*  '('  */ || c /* : string */ ===  /*  '{' */) {
        magma.Main$Lists$JVMList;
        aa9e816;
    }
    if (c /* : string */ ===  /*  '>'  */ || c /* : string */ ===  /*  ')'  */ || c /* : string */ ===  /*  '}' */) {
        magma.Main$Lists$JVMList;
        d99928;
    }
    return appended /* : unknown */;
}
/* private static */ compileType(state, CompileState, input, string);
Option < [CompileState, string] > {
    return /* parseType */(state /* : CompileState */, input /* : string */) { } /* : unknown */, /* : unknown */ : /* : unknown */ .map /* : unknown */((tuple) => new Tuple2Impl(tuple /* : unknown */.left /* : unknown */() /* : unknown */, tuple /* : unknown */.right /* : unknown */() /* : unknown */.generate /* : unknown */() /* : unknown */) /* : Tuple2Impl */) /* : unknown */
};
/* private static */ parseType(state, CompileState, input, string);
Option < [CompileState, Type] > {
    let, stripped = input /* : string */.strip /* : unknown */() /* : unknown */,
    if(stripped) { } /* : unknown */, /* : unknown */ : /* : unknown */ .equals /* : unknown */("int") /* : unknown */ || stripped /* : unknown */.equals /* : unknown */("Integer") /* : unknown */
};
{
    magma.Main$Lists$JVMList;
    d63f;
}
if (stripped /* : unknown */.equals /* : unknown */("String") /* : unknown */ || stripped /* : unknown */.equals /* : unknown */("char") /* : unknown */ || stripped /* : unknown */.equals /* : unknown */("Character") /* : unknown */) {
    magma.Main$Lists$JVMList;
    ae369b7;
}
if (stripped /* : unknown */.equals /* : unknown */("var") /* : unknown */) {
    magma.Main$Lists$JVMList;
    fffcba5;
}
if (stripped /* : unknown */.equals /* : unknown */("boolean") /* : unknown */) {
    magma.Main$Lists$JVMList;
    fab;
}
if (isSymbol /* : (arg0 : string) => boolean */(stripped /* : unknown */) /* : boolean */) {
    magma.Main$Lists$JVMList;
    aafb23c;
}
return /* parseTemplate */ (state /* : CompileState */, input /* : string */) /* : unknown */.or /* : unknown */(() => /* varArgs */ (state /* : CompileState */, input /* : string */) /* : unknown */) /* : unknown */;
/* private static */ varArgs(state, CompileState, input, string);
Option < [CompileState, Type] > {
    return: suffix /* : string */(input /* : string */, "...", (s) => {
        return parseType /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Type]> */(state /* : CompileState */, s /* : unknown */) /* : Option<[CompileState, Type]> */.map /* : (arg0 : (arg0 : [CompileState, Type]) => R) => Option<R> */((inner) => {
            let newState = inner /* : unknown */.left /* : unknown */() /* : unknown */;
            let child = inner /* : unknown */.right /* : unknown */() /* : unknown */;
            return new Tuple2Impl(newState /* : unknown */, new ArrayType(child /* : unknown */) /* : ArrayType */) /* : Tuple2Impl */;
        }) /* : Option<R> */;
    }) /* : unknown */
};
/* private static */ assembleTemplate(base, string, state, CompileState, arguments, (List));
[CompileState, Type];
{
    let children = arguments /* : unknown */.iterate /* : unknown */() /* : unknown */.map /* : unknown */(Main /* : Main */.retainType /* : unknown */) /* : unknown */.flatMap /* : unknown */(Iterators /* : Iterators */.fromOption /* : unknown */) /* : unknown */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */;
    if (base /* : string */.equals /* : unknown */("BiFunction") /* : unknown */) {
        magma.Main$Lists$JVMList;
        b80d80f;
    }
    if (base /* : string */.equals /* : unknown */("Function") /* : unknown */) {
        magma.Main$Lists$JVMList;
        ab39c39;
    }
    if (base /* : string */.equals /* : unknown */("Predicate") /* : unknown */) {
        magma.Main$Lists$JVMList;
        ee9593;
    }
    if (base /* : string */.equals /* : unknown */("Supplier") /* : unknown */) {
        magma.Main$Lists$JVMList;
        c20;
    }
    if (base /* : string */.equals /* : unknown */("Tuple2") /* : unknown */ && children /* : unknown */.size /* : unknown */() /* : unknown */ >= 2 /* : number */) {
        magma.Main$Lists$JVMList;
        a03af;
    }
    if (state /* : CompileState */.resolveType /* : (arg0 : string) => Option<Type> */(base /* : string */) /* : Option<Type> */._variant /* : unknown */ === OptionVariant.Some /* : unknown */) {
        magma.Main$Lists$JVMList;
        f4f;
    }
    return new Tuple2Impl(state /* : CompileState */, new Template(new Placeholder(base /* : string */) /* : Placeholder */, children /* : unknown */) /* : Template */) /* : Tuple2Impl */;
}
/* private static */ parseTemplate(state, CompileState, input, string);
Option < [CompileState, Type] > {
    return: suffix /* : string */(input /* : string */.strip /* : unknown */() /* : unknown */, ">", (withoutEnd) => {
        return first /* : unknown */(withoutEnd /* : unknown */, "<", (base, argumentsString) => {
            let strippedBase = base /* : string */.strip /* : unknown */() /* : unknown */;
            return parseValues /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) => Option<[CompileState, List<T>]> */(state /* : CompileState */, argumentsString /* : unknown */, Main /* : Main */.argument /* : unknown */) /* : Option<[CompileState, List<T>]> */.map /* : (arg0 : (arg0 : [CompileState, List<T>]) => R) => Option<R> */((argumentsTuple) => {
                return assembleTemplate /* : (arg0 : string, arg1 : CompileState, arg2 : List<Argument>) => [CompileState, Type] */(strippedBase /* : unknown */, argumentsTuple /* : unknown */.left /* : unknown */() /* : unknown */, argumentsTuple /* : unknown */.right /* : unknown */() /* : unknown */) /* : [CompileState, Type] */;
            }) /* : Option<R> */;
        }) /* : unknown */;
    }) /* : unknown */
};
/* private static */ retainType(argument, Argument);
Option < Type > {
    if(argument) { } /* : Argument */, /* : Argument */ : /* : Argument */ ._variant /* : unknown */ === ArgumentVariant.Type /* : unknown */
};
{
    magma.Main$Lists$JVMList;
}
{
    magma.Main$Lists$JVMList;
    a6d14e;
}
/* private static */ argument(state, CompileState, input, string);
Option < [CompileState, Argument] > {
    if(input) { } /* : string */, /* : string */ : /* : string */ .isBlank /* : unknown */() /* : unknown */
};
{
    magma.Main$Lists$JVMList;
    ad5c04e;
}
return parseType /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Type]> */(state /* : CompileState */, input /* : string */) /* : Option<[CompileState, Type]> */.map /* : (arg0 : (arg0 : [CompileState, Type]) => R) => Option<R> */((tuple) => new Tuple2Impl(tuple /* : unknown */.left /* : unknown */() /* : unknown */, tuple /* : unknown */.right /* : unknown */() /* : unknown */) /* : Tuple2Impl */) /* : Option<R> */;
/* private static  */ last(input, string, infix, string, mapper, (arg0, arg1) => Option);
Option < T > {
    return: infix /* : string */(input /* : string */, infix /* : string */, Main /* : Main */.findLast /* : unknown */, mapper /* : (arg0 : T) => R */) /* : unknown */
};
/* private static */ findLast(input, string, infix, string);
Option < number > {
    let, index = input /* : string */.lastIndexOf /* : unknown */(infix /* : string */) /* : unknown */,
    if(index) { }
} /* : T */ === -1; /* : number */
{
    magma.Main$Lists$JVMList;
    ce2c;
}
return new Some(index /* : T */) /* : Some */;
/* private static  */ first(input, string, infix, string, mapper, (arg0, arg1) => Option);
Option < T > {
    return: infix /* : string */(input /* : string */, infix /* : string */, Main /* : Main */.findFirst /* : unknown */, mapper /* : (arg0 : T) => R */) /* : unknown */
};
/* private static  */ split(splitter, () => Option, splitMapper, (arg0, arg1) => Option);
Option < T > {
    return: splitter /* : () => Option<[string, string]> */() /* : Option<[string, string]> */.flatMap /* : (arg0 : (arg0 : [string, string]) => Option<R>) => Option<R> */((splitTuple) => splitMapper /* : (arg0 : string, arg1 : string) => Option<T> */(splitTuple /* : [string, string] */[0 /* : number */]() /* : unknown */, splitTuple /* : [string, string] */[1 /* : number */]() /* : unknown */) /* : Option<T> */) /* : Option<R> */
};
/* private static  */ infix(input, string, infix, string, locator, (arg0, arg1) => Option, mapper, (arg0, arg1) => Option);
Option < T > {
    return: split /* : (arg0 : () => Option<[string, string]>, arg1 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(() => locator /* : (arg0 : string, arg1 : string) => Option<number> */(input /* : string */, infix /* : string */) /* : Option<number> */.map /* : (arg0 : (arg0 : number) => R) => Option<R> */((index) => {
        let left = input /* : string */.substring /* : unknown */(0 /* : number */, index /* : T */) /* : unknown */;
        let right = input /* : string */.substring /* : unknown */(index /* : T */ + infix /* : string */.length /* : unknown */() /* : unknown */) /* : unknown */;
        return new Tuple2Impl(left /* : () => A */, right /* : () => B */) /* : Tuple2Impl */;
    }) /* : Option<R> */, mapper /* : (arg0 : T) => R */) /* : Option<T> */
};
/* private static */ findFirst(input, string, infix, string);
Option < number > {
    let, index = input /* : string */.indexOf /* : unknown */(infix /* : string */) /* : unknown */,
    if(index) { }
} /* : T */ === -1; /* : number */
{
    magma.Main$Lists$JVMList;
    bef66;
}
return new Some(index /* : T */) /* : Some */;
/* private static */ generatePlaceholder(input, string);
string;
{
    let replaced = input /* : string */.replace /* : unknown */("/*", "content-start") /* : unknown */.replace /* : unknown */("*/", "content-end") /* : unknown */;
    return "/* " + replaced + " */";
}
/* private static */ createDebugString(type, Type);
string;
{
    if (!Main /* : Main */.isDebug /* : unknown */) {
        magma.Main$Lists$JVMList;
        aaf7cc2;
    }
    return generatePlaceholder /* : (arg0 : string) => string */(": " + type /* : () => Type */.generate /* : unknown */() /* : unknown */) /* : string */;
}
/*  */ 
