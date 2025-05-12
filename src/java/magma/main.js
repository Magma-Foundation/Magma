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
        return new Some(mapper /* : (arg0 : T) => R */(this /* : Some */.value /* : unknown */) /* : R */) /* : Some */;
    }
    /* @Override
        public */ isPresent() {
        return true;
    }
    /* @Override
        public */ orElse(other) {
        return this /* : Some */.value /* : unknown */;
    }
    /* @Override
        public */ filter(predicate) {
        if (predicate /* : (arg0 : T) => boolean */(this /* : Some */.value /* : unknown */) /* : boolean */) {
            return this /* : Some */;
        }
        return new None() /* : None */;
    }
    /* @Override
        public */ orElseGet(supplier) {
        return this /* : Some */.value /* : unknown */;
    }
    /* @Override
        public */ or(other) {
        return this /* : Some */;
    }
    /* @Override
        public  */ flatMap(mapper) {
        return mapper /* : (arg0 : T) => R */(this /* : Some */.value /* : unknown */) /* : R */;
    }
    /* @Override
        public */ isEmpty() {
        return false;
    }
    /* @Override
        public  */ and(other) {
        return other /* : T */.get /* : unknown */() /* : unknown */.map /* : unknown */((otherValue) => new Tuple2Impl(this /* : Some */.value /* : unknown */, otherValue /* : unknown */) /* : Tuple2Impl */) /* : unknown */;
    }
}
/* private static */ class SingleHead {
    constructor(value) {
        this /* : SingleHead */.value /* : unknown */ = value /* : T */;
        this /* : SingleHead */.retrieved /* : unknown */ = false;
    }
    /* @Override
        public */ next() {
        if (this /* : SingleHead */.retrieved /* : unknown */) {
            return new None() /* : None */;
        }
        this /* : SingleHead */.retrieved /* : unknown */ = true;
        return new Some(this /* : SingleHead */.value /* : unknown */) /* : Some */;
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
            let finalCurrent = current /* : R */;
            let option = this /* : HeadedIterator */.head /* : unknown */.next /* : unknown */() /* : unknown */.map /* : unknown */((inner) => folder /* : (arg0 : R, arg1 : T) => R */(finalCurrent /* : R */, inner /* : unknown */) /* : R */) /* : unknown */;
            if (option /* : unknown */._variant /* : unknown */ === Variant.Some /* : unknown */) {
                current /* : R */ = /* some */ .value /* : unknown */;
            }
            else {
                return current /* : R */;
            }
        }
    }
    /* @Override
        public  */ map(mapper) {
        return new HeadedIterator(() => this /* : HeadedIterator */.head /* : unknown */.next /* : unknown */() /* : unknown */.map /* : unknown */(mapper /* : (arg0 : T) => R */) /* : unknown */) /* : HeadedIterator */;
    }
    /* @Override
        public  */ collect(collector) {
        return this /* : HeadedIterator */.fold /* : (arg0 : R, arg1 : (arg0 : R, arg1 : T) => R) => R */(collector /* : Collector<T, R> */.createInitial /* : () => C */() /* : C */, collector /* : Collector<T, R> */.fold /* : unknown */) /* : R */;
    }
    /* @Override
        public */ filter(predicate) {
        return this /* : HeadedIterator */.flatMap /* : (arg0 : (arg0 : T) => Iterator<R>) => Iterator<R> */((element) => {
            if (predicate /* : (arg0 : T) => boolean */(element /* : T */) /* : boolean */) {
                return new HeadedIterator(new SingleHead(element /* : T */) /* : SingleHead */) /* : HeadedIterator */;
            }
            return new HeadedIterator(new EmptyHead() /* : EmptyHead */) /* : HeadedIterator */;
        }) /* : Iterator<R> */;
    }
    /* @Override
        public */ next() {
        return this /* : HeadedIterator */.head /* : unknown */.next /* : unknown */() /* : unknown */;
    }
    /* @Override
        public  */ flatMap(f) {
        return new HeadedIterator(new /* FlatMapHead */ (this /* : HeadedIterator */.head /* : unknown */, f /* : (arg0 : T) => Iterator<R> */) /* : content-start FlatMapHead content-end */) /* : HeadedIterator */;
    }
    /* @Override
        public  */ zip(other) {
        return new HeadedIterator(() => HeadedIterator /* : HeadedIterator */.this /* : HeadedIterator */.head /* : unknown */.next /* : unknown */() /* : unknown */.and /* : unknown */(other /* : Iterator<R> */.next /* : unknown */) /* : unknown */) /* : HeadedIterator */;
    }
}
/* private static */ class RangeHead /*  */ {
}
this /* : RangeHead */.length /* : unknown */ = length /* : number */;
/* @Override
    public */ next();
Option < number > {
    if() { } /* this.counter < this */, /* this.counter < this */ : /* this.counter < this */ .length /* : unknown */
};
{
    let value = this /* : RangeHead */.counter /* : unknown */;
    /* this.counter++ */ ;
    return new Some(value /* : unknown */) /* : Some */;
}
return new None() /* : None */;
/* private static final */ class JVMList {
}
this /* : JVMList */.elements /* : unknown */ = elements /* : content-start java.util.List content-end<T> */;
JVMList();
{
    /* this(new ArrayList<>()) */ ;
}
/* @Override
        public */ addLast(element, T);
List < T > {
    return: this /* : JVMList */
};
/* @Override
        public */ iterate();
Iterator < T > {
    return: this /* : JVMList */.iterateWithIndices /* : () => Iterator<[number, T]> */() /* : Iterator<[number, T]> */.map /* : (arg0 : (arg0 : [number, T]) => R) => Iterator<R> */(Tuple2 /* : Tuple2 */.right /* : unknown */) /* : Iterator<R> */
};
/* @Override
        public */ removeLast();
Option < [(List), T] > {
    : /* : JVMList */ .elements /* : unknown */.isEmpty /* : unknown */() /* : unknown */
};
{
    return new None() /* : None */;
}
let slice = this /* : JVMList */.elements /* : unknown */.subList /* : unknown */(0 /* : number */, this /* : JVMList */.elements /* : unknown */.size /* : unknown */() /* : unknown */ - 1 /* : number */) /* : unknown */;
let last = this /* : JVMList */.elements /* : unknown */.getLast /* : unknown */() /* : unknown */;
return new Some(new Tuple2Impl(new JVMList(slice /* : unknown */) /* : JVMList */, last /* : unknown */) /* : Tuple2Impl<List<T>, T> */) /* : Some */;
/* @Override
        public */ size();
number;
{
    return this /* : JVMList */.elements /* : unknown */.size /* : unknown */() /* : unknown */;
}
/* @Override
        public */ isEmpty();
boolean;
{
    return this /* : JVMList */.elements /* : unknown */.isEmpty /* : unknown */() /* : unknown */;
}
/* @Override
        public */ addFirst(element, T);
List < T > {
    return: this /* : JVMList */
};
/* @Override
        public */ iterateWithIndices();
Iterator < [number, T] > {
    return: new HeadedIterator(new RangeHead(this /* : JVMList */.elements /* : unknown */.size /* : unknown */() /* : unknown */) /* : RangeHead */) /* : HeadedIterator */.map /* : (arg0 : (arg0 : T) => R) => Iterator<R> */((index) => new Tuple2Impl(index /* : T */, this /* : JVMList */.elements /* : unknown */.get /* : unknown */(index /* : T */) /* : unknown */) /* : Tuple2Impl */) /* : Iterator<R> */
};
/* @Override
        public */ removeFirst();
Option < [T, (List)] > {
    : /* : JVMList */ .elements /* : unknown */.isEmpty /* : unknown */() /* : unknown */
};
{
    return new None() /* : None */;
}
let first = this /* : JVMList */.elements /* : unknown */.getFirst /* : unknown */() /* : unknown */;
let slice = this /* : JVMList */.elements /* : unknown */.subList /* : unknown */(1 /* : number */, this /* : JVMList */.elements /* : unknown */.size /* : unknown */() /* : unknown */) /* : unknown */;
return new Some(new Tuple2Impl(first /* : unknown */, new JVMList(slice /* : unknown */) /* : JVMList */) /* : Tuple2Impl<T, List<T>> */) /* : Some */;
/* @Override
        public */ addAllLast(others, (List));
List < T > {
    let, initial: (List) = this /* : JVMList */,
    return: others /* : List<T> */.iterate /* : () => Iterator<T> */() /* : Iterator<T> */.fold /* : (arg0 : R, arg1 : (arg0 : R, arg1 : T) => R) => R */(initial /* : List<T> */, List /* : List */.addLast /* : unknown */) /* : R */
};
/* @Override
        public */ last();
Option < T > {
    : /* : JVMList */ .elements /* : unknown */.isEmpty /* : unknown */() /* : unknown */
};
{
    return new None() /* : None */;
}
return new Some(this /* : JVMList */.elements /* : unknown */.getLast /* : unknown */() /* : unknown */) /* : Some */;
/* @Override
        public */ iterateReversed();
Iterator < T > {
    return: new HeadedIterator(new RangeHead(this /* : JVMList */.elements /* : unknown */.size /* : unknown */() /* : unknown */) /* : RangeHead */) /* : HeadedIterator */.map /* : (arg0 : (arg0 : T) => R) => Iterator<R> */((index) => this /* : JVMList */.elements /* : unknown */.size /* : unknown */() /* : unknown */ - index /* : T */ - 1 /* : number */) /* : Iterator<R> */.map /* : (arg0 : (arg0 : R) => R) => Iterator<R> */(this /* : JVMList */.elements /* : unknown */.get /* : unknown */) /* : Iterator<R> */
};
/* @Override
        public */ mapLast(mapper, (arg0) => T);
List < T > {
    return: this /* : JVMList */.last /* : () => Option<T> */() /* : Option<T> */.map /* : (arg0 : (arg0 : T) => R) => Option<R> */(mapper /* : (arg0 : T) => T */) /* : Option<R> */.map /* : unknown */((newLast) => this /* : JVMList */.set /* : (arg0 : number, arg1 : T) => JVMList<T> */(this /* : JVMList */.elements /* : unknown */.size /* : unknown */() /* : unknown */ - 1 /* : number */, newLast /* : unknown */) /* : JVMList<T> */) /* : unknown */.orElse /* : unknown */(this /* : JVMList */) /* : unknown */
};
/* private */ set(index, number, element, T);
JVMList < T > {
    return: this /* : JVMList */
};
/* @Override
        public */ get(index, number);
Option < T > {
    if() { } /* index < this */, /* index < this */ : /* index < this */ .elements /* : unknown */.size /* : unknown */() /* : unknown */
};
{
    return new Some(this /* : JVMList */.elements /* : unknown */.get /* : unknown */(index /* : T */) /* : unknown */) /* : Some */;
}
{
    return new None() /* : None */;
}
/* private static */ class Lists /*  */ {
    /* public static  */ empty() {
        return new JVMList() /* : JVMList */;
    }
    /* public static  */ of(elements) {
        return new JVMList(new /* ArrayList */ ( /* Arrays */.asList /* : unknown */(elements /* : T[] */) /* : unknown */) /* : content-start ArrayList content-end */) /* : JVMList */;
    }
}
/* private */ class ImmutableDefinition /*  */ {
    constructor(maybeBefore, name, type, typeParams) {
    }
    /* public static */ createSimpleDefinition(name, type) {
        return new ImmutableDefinition(new None() /* : None */, name /* : string */, type /* : Type */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */) /* : ImmutableDefinition */;
    }
    /* @Override
        public */ generate() {
        return this /* : ImmutableDefinition */.generateWithParams /* : (arg0 : string) => string */("") /* : string */;
    }
    /* @Override
        public */ generateType() {
        if (this /* : ImmutableDefinition */.type /* : unknown */.equals /* : unknown */(Unknown /* : unknown */) /* : unknown */) {
            return "";
        }
        return " : " + this /* : ImmutableDefinition */.type /* : unknown */.generate /* : unknown */() /* : unknown */;
    }
    /* @Override
        public */ joinBefore() {
        return !.maybeBefore /* : unknown */.filter /* : unknown */((value) => !value /* : unknown */.isEmpty /* : unknown */() /* : unknown */) /* : unknown */.map /* : unknown */(generatePlaceholder /* : unknown */) /* : unknown */.map /* : unknown */((inner) => inner /* : unknown */ + " ") /* : unknown */.orElse /* : unknown */("") /* : unknown */;
    }
    /* @Override
        public */ joinTypeParams() {
        return this /* : ImmutableDefinition */.typeParams /* : unknown */.iterate /* : unknown */() /* : unknown */.collect /* : unknown */(new /* Joiner */ () /* : content-start Joiner content-end */) /* : unknown */.map /* : unknown */((inner) => "<" + inner + ">") /* : unknown */.orElse /* : unknown */("") /* : unknown */;
    }
    /* @Override
        public */ mapType(mapper) {
        return new ImmutableDefinition(this /* : ImmutableDefinition */.maybeBefore /* : unknown */, this /* : ImmutableDefinition */.name /* : unknown */, mapper /* : (arg0 : Type) => Type */(this /* : ImmutableDefinition */.type /* : unknown */) /* : Type */, this /* : ImmutableDefinition */.typeParams /* : unknown */) /* : ImmutableDefinition */;
    }
    /* @Override
        public */ toString() {
        return "Definition[" + "maybeBefore=" + this /* : ImmutableDefinition */.maybeBefore /* : unknown */ + ", " + "name=" + this /* : ImmutableDefinition */.name /* : unknown */ + ", " + "type=" + this /* : ImmutableDefinition */.type /* : unknown */ + ", " + "typeParams=" + this /* : ImmutableDefinition */.typeParams /* : unknown */ +  /*  ']' */;
    }
    /* @Override
        public */ generateWithParams(joinedParameters) {
        let joined = this /* : ImmutableDefinition */.joinTypeParams /* : () => string */() /* : string */;
        let before = this /* : ImmutableDefinition */.joinBefore /* : () => string */() /* : string */;
        let typeString = this /* : ImmutableDefinition */.generateType /* : () => string */() /* : string */;
        return before /* : string */ + this /* : ImmutableDefinition */.name /* : unknown */ + joined /* : string */ + joinedParameters /* : string */ + typeString /* : string */;
    }
    /* @Override
        public */ createDefinition(paramTypes) {
        return ImmutableDefinition /* : ImmutableDefinition */.createSimpleDefinition /* : (arg0 : string, arg1 : Type) => Definition */(this /* : ImmutableDefinition */.name /* : unknown */, new /* FunctionType */ (paramTypes /* : List<Type> */, this /* : ImmutableDefinition */.type /* : unknown */) /* : content-start FunctionType content-end */) /* : Definition */;
    }
}
/* private */ class ObjectType /*  */ {
    constructor(name, typeParams, definitions) {
    }
    /* @Override
        public */ generate() {
        return this /* : ObjectType */.name /* : unknown */;
    }
    /* @Override
        public */ replace(mapping) {
        return new ObjectType(this /* : ObjectType */.name /* : unknown */, this /* : ObjectType */.typeParams /* : unknown */, this /* : ObjectType */.definitions /* : unknown */.iterate /* : unknown */() /* : unknown */.map /* : unknown */((definition) => definition /* : unknown */.mapType /* : unknown */((type) => type /* : unknown */.replace /* : unknown */(mapping /* : Map<string, Type> */) /* : unknown */) /* : unknown */) /* : unknown */.collect /* : unknown */(new /* ListCollector */ () /* : content-start ListCollector content-end */) /* : unknown */) /* : ObjectType */;
    }
    /* @Override
        public */ find(name) {
        return this /* : ObjectType */.definitions /* : unknown */.iterate /* : unknown */() /* : unknown */.filter /* : unknown */((definition) => definition /* : unknown */.name /* : unknown */() /* : unknown */.equals /* : unknown */(name /* : string */) /* : unknown */) /* : unknown */.map /* : unknown */(Definition /* : Definition */.type /* : unknown */) /* : unknown */.next /* : unknown */() /* : unknown */;
    }
    /* @Override
        public */ findName() {
        return new Some(this /* : ObjectType */.name /* : unknown */) /* : Some */;
    }
}
/* private */ class TypeParam /*  */ {
    constructor(value) {
    }
    /* @Override
        public */ generate() {
        return this /* : TypeParam */.value /* : unknown */;
    }
    /* @Override
        public */ replace(mapping) {
        return mapping /* : Map<string, Type> */.find /* : (arg0 : string) => Option<V> */(this /* : TypeParam */.value /* : unknown */) /* : Option<V> */.orElse /* : (arg0 : V) => T */(this /* : TypeParam */) /* : T */;
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
        return this /* : CompileState */.definitions /* : unknown */.iterateReversed /* : unknown */() /* : unknown */.flatMap /* : unknown */(List /* : List */.iterate /* : unknown */) /* : unknown */.filter /* : unknown */((definition) => definition /* : unknown */.name /* : unknown */() /* : unknown */.equals /* : unknown */(name /* : string */) /* : unknown */) /* : unknown */.next /* : unknown */() /* : unknown */.map /* : unknown */(Definition /* : Definition */.type /* : unknown */) /* : unknown */;
    }
    /* public */ addStructure(structure) {
        return new CompileState(this /* : CompileState */.structures /* : unknown */.addLast /* : unknown */(structure /* : string */) /* : unknown */, this /* : CompileState */.definitions /* : unknown */, this /* : CompileState */.objectTypes /* : unknown */, this /* : CompileState */.structNames /* : unknown */, this /* : CompileState */.typeParams /* : unknown */, this /* : CompileState */.typeRegister /* : unknown */) /* : CompileState */;
    }
    /* public */ withDefinitions(definitions) {
        let defined = this /* : CompileState */.definitions /* : unknown */.mapLast /* : unknown */((frame) => frame /* : unknown */.addAllLast /* : unknown */(definitions /* : List<Definition> */) /* : unknown */) /* : unknown */;
        return new CompileState(this /* : CompileState */.structures /* : unknown */, defined /* : unknown */, this /* : CompileState */.objectTypes /* : unknown */, this /* : CompileState */.structNames /* : unknown */, this /* : CompileState */.typeParams /* : unknown */, this /* : CompileState */.typeRegister /* : unknown */) /* : CompileState */;
    }
    /* public */ resolveType(name) {
        if (this /* : CompileState */.structNames /* : unknown */.last /* : unknown */() /* : unknown */.filter /* : unknown */((inner) => inner /* : unknown */.equals /* : unknown */(name /* : string */) /* : unknown */) /* : unknown */.isPresent /* : unknown */() /* : unknown */) {
            return new Some(new ObjectType(name /* : string */, this /* : CompileState */.typeParams /* : unknown */, this /* : CompileState */.definitions /* : unknown */.last /* : unknown */() /* : unknown */.orElse /* : unknown */(Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */) /* : unknown */) /* : ObjectType */) /* : Some */;
        }
        let maybeTypeParam = this /* : CompileState */.typeParams /* : unknown */.iterate /* : unknown */() /* : unknown */.filter /* : unknown */((param) => param /* : unknown */.equals /* : unknown */(name /* : string */) /* : unknown */) /* : unknown */.next /* : unknown */() /* : unknown */;
        if ( /* maybeTypeParam instanceof Some */( /* var value */) /* : unknown */) {
            return new Some(new TypeParam( /* value */) /* : TypeParam */) /* : Some */;
        }
        return this /* : CompileState */.objectTypes /* : unknown */.iterate /* : unknown */() /* : unknown */.filter /* : unknown */((type) => type /* : unknown */.name /* : unknown */.equals /* : unknown */(name /* : string */) /* : unknown */) /* : unknown */.next /* : unknown */() /* : unknown */.map /* : unknown */((type) => type /* : unknown */) /* : unknown */;
    }
    /* public */ withDefinition(definition) {
        return new CompileState(this /* : CompileState */.structures /* : unknown */, this /* : CompileState */.definitions /* : unknown */.mapLast /* : unknown */((frame) => frame /* : unknown */.addLast /* : unknown */(definition /* : unknown */) /* : unknown */) /* : unknown */, this /* : CompileState */.objectTypes /* : unknown */, this /* : CompileState */.structNames /* : unknown */, this /* : CompileState */.typeParams /* : unknown */, this /* : CompileState */.typeRegister /* : unknown */) /* : CompileState */;
    }
    /* public */ pushStructName(name) {
        return new CompileState(this /* : CompileState */.structures /* : unknown */, this /* : CompileState */.definitions /* : unknown */, this /* : CompileState */.objectTypes /* : unknown */, this /* : CompileState */.structNames /* : unknown */.addLast /* : unknown */(name /* : string */) /* : unknown */, this /* : CompileState */.typeParams /* : unknown */, this /* : CompileState */.typeRegister /* : unknown */) /* : CompileState */;
    }
    /* public */ withTypeParams(typeParams) {
        return new CompileState(this /* : CompileState */.structures /* : unknown */, this /* : CompileState */.definitions /* : unknown */, this /* : CompileState */.objectTypes /* : unknown */, this /* : CompileState */.structNames /* : unknown */, this /* : CompileState */.typeParams /* : unknown */.addAllLast /* : unknown */(typeParams /* : List<string> */) /* : unknown */, this /* : CompileState */.typeRegister /* : unknown */) /* : CompileState */;
    }
    /* public */ withExpectedType(type) {
        return new CompileState(this /* : CompileState */.structures /* : unknown */, this /* : CompileState */.definitions /* : unknown */, this /* : CompileState */.objectTypes /* : unknown */, this /* : CompileState */.structNames /* : unknown */, this /* : CompileState */.typeParams /* : unknown */, new Some(type /* : unknown */) /* : Some */) /* : CompileState */;
    }
    /* public */ popStructName() {
        return new CompileState(this /* : CompileState */.structures /* : unknown */, this /* : CompileState */.definitions /* : unknown */, this /* : CompileState */.objectTypes /* : unknown */, this /* : CompileState */.structNames /* : unknown */.removeLast /* : unknown */() /* : unknown */.map /* : unknown */(Tuple2 /* : Tuple2 */.left /* : unknown */) /* : unknown */.orElse /* : unknown */(this /* : CompileState */.structNames /* : unknown */) /* : unknown */, this /* : CompileState */.typeParams /* : unknown */, this /* : CompileState */.typeRegister /* : unknown */) /* : CompileState */;
    }
    /* public */ enterDefinitions() {
        return new CompileState(this /* : CompileState */.structures /* : unknown */, this /* : CompileState */.definitions /* : unknown */.addLast /* : unknown */(Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */) /* : unknown */, this /* : CompileState */.objectTypes /* : unknown */, this /* : CompileState */.structNames /* : unknown */, this /* : CompileState */.typeParams /* : unknown */, this /* : CompileState */.typeRegister /* : unknown */) /* : CompileState */;
    }
    /* public */ exitDefinitions() {
        let removed = this /* : CompileState */.definitions /* : unknown */.removeLast /* : unknown */() /* : unknown */.map /* : unknown */(Tuple2 /* : Tuple2 */.left /* : unknown */) /* : unknown */.orElse /* : unknown */(this /* : CompileState */.definitions /* : unknown */) /* : unknown */;
        return new CompileState(this /* : CompileState */.structures /* : unknown */, removed /* : unknown */, this /* : CompileState */.objectTypes /* : unknown */, this /* : CompileState */.structNames /* : unknown */, this /* : CompileState */.typeParams /* : unknown */, this /* : CompileState */.typeRegister /* : unknown */) /* : CompileState */;
    }
    /* public */ addType(thisType) {
        return new CompileState(this /* : CompileState */.structures /* : unknown */, this /* : CompileState */.definitions /* : unknown */, this /* : CompileState */.objectTypes /* : unknown */.addLast /* : unknown */(thisType /* : ObjectType */) /* : unknown */, this /* : CompileState */.structNames /* : unknown */, this /* : CompileState */.typeParams /* : unknown */, this /* : CompileState */.typeRegister /* : unknown */) /* : CompileState */;
    }
}
/* private static */ class DivideState /*  */ {
}
this /* : DivideState */.segments /* : unknown */ = segments /* : List<string> */;
this /* : DivideState */.buffer /* : unknown */ = buffer /* : string */;
this /* : DivideState */.depth /* : unknown */ = depth /* : number */;
this /* : DivideState */.input /* : unknown */ = input /* : string */;
this /* : DivideState */.index /* : unknown */ = index /* : number */;
DivideState(input, string);
{
    /* this(input, 0, Lists.empty(), "", 0) */ ;
}
/* private */ advance();
DivideState;
{
    this /* : DivideState */.segments /* : unknown */ = this /* : DivideState */.segments /* : unknown */.addLast /* : unknown */(this /* : DivideState */.buffer /* : unknown */) /* : unknown */;
    this /* : DivideState */.buffer /* : unknown */ = "";
    return this /* : DivideState */;
}
/* private */ append(c, string);
DivideState;
{
    this /* : DivideState */.buffer /* : unknown */ = this /* : DivideState */.buffer /* : unknown */ + c /* : string */;
    return this /* : DivideState */;
}
/* public */ enter();
DivideState;
{
    /* this.depth++ */ ;
    return this /* : DivideState */;
}
/* public */ isLevel();
boolean;
{
    return this /* : DivideState */.depth /* : unknown */ === 0 /* : number */;
}
/* public */ exit();
DivideState;
{
    /* this.depth-- */ ;
    return this /* : DivideState */;
}
/* public */ isShallow();
boolean;
{
    return this /* : DivideState */.depth /* : unknown */ === 1 /* : number */;
}
/* public */ pop();
Option < [string, DivideState] > {
    if() { } /* this.index < this */, /* this.index < this */ : /* this.index < this */ .input /* : unknown */.length /* : unknown */() /* : unknown */
};
{
    let c = this /* : DivideState */.input /* : unknown */.charAt /* : unknown */(this /* : DivideState */.index /* : unknown */) /* : unknown */;
    return new Some(new Tuple2Impl(c /* : string */, new DivideState(this /* : DivideState */.input /* : unknown */, this /* : DivideState */.index /* : unknown */ + 1 /* : number */, this /* : DivideState */.segments /* : unknown */, this /* : DivideState */.buffer /* : unknown */, this /* : DivideState */.depth /* : unknown */) /* : DivideState */) /* : Tuple2Impl */) /* : Some */;
}
return new None() /* : None */;
/* public */ popAndAppendToTuple();
Option < [string, DivideState] > {
    return: this /* : DivideState */.pop /* : () => Option<[string, DivideState]> */() /* : Option<[string, DivideState]> */.map /* : (arg0 : (arg0 : [string, DivideState]) => R) => Option<R> */((tuple) => {
        let c = tuple /* : [string, DivideState] */[0 /* : number */]() /* : unknown */;
        let right = tuple /* : [string, DivideState] */[1 /* : number */]() /* : unknown */;
        return new Tuple2Impl(c /* : string */, right /* : unknown */.append /* : unknown */(c /* : string */) /* : unknown */) /* : Tuple2Impl */;
    }) /* : Option<R> */
};
/* public */ popAndAppendToOption();
Option < DivideState > {
    return: this /* : DivideState */.popAndAppendToTuple /* : () => Option<[string, DivideState]> */() /* : Option<[string, DivideState]> */.map /* : (arg0 : (arg0 : [string, DivideState]) => R) => Option<R> */(Tuple2 /* : Tuple2 */.right /* : unknown */) /* : Option<R> */
};
/* public */ peek();
string;
{
    return this /* : DivideState */.input /* : unknown */.charAt /* : unknown */(this /* : DivideState */.index /* : unknown */) /* : unknown */;
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
        return new Some(current /* : Option<string> */.map /* : (arg0 : (arg0 : string) => R) => Option<R> */((inner) => inner /* : string */ + this /* : Joiner */.delimiter /* : unknown */ + element /* : string */) /* : Option<R> */.orElse /* : unknown */(element /* : string */) /* : unknown */) /* : Some */;
    }
}
/* private static */ class ListCollector {
    /* @Override
        public */ createInitial() {
        return Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */;
    }
    /* @Override
        public */ fold(current, element) {
        return current /* : List<T> */.addLast /* : (arg0 : T) => List<T> */(element /* : T */) /* : List<T> */;
    }
}
/* private static */ class FlatMapHead {
}
this /* : FlatMapHead */.mapper /* : unknown */ = mapper /* : (arg0 : T) => Iterator<R> */;
this /* : FlatMapHead */.current /* : unknown */ = new None() /* : None */;
this /* : FlatMapHead */.head /* : unknown */ = head /* : Head<T> */;
/* @Override
    public */ next();
Option < R > {
    while() {
        if (this /* : FlatMapHead */.current /* : unknown */.isPresent /* : unknown */() /* : unknown */) {
            let inner = this /* : FlatMapHead */.current /* : unknown */.orElse /* : unknown */( /* null */) /* : unknown */;
            let maybe = inner /* : Iterator<R> */.next /* : () => Option<T> */() /* : Option<T> */;
            if (maybe /* : Option<R> */.isPresent /* : () => boolean */() /* : boolean */) {
                return maybe /* : Option<R> */;
            }
            else {
                this /* : FlatMapHead */.current /* : unknown */ = new None() /* : None */;
            }
        }
        let outer = this /* : FlatMapHead */.head /* : unknown */.next /* : unknown */() /* : unknown */;
        if (outer /* : Option<T> */.isPresent /* : () => boolean */() /* : boolean */) {
            this /* : FlatMapHead */.current /* : unknown */ = outer /* : Option<T> */.map /* : (arg0 : (arg0 : T) => R) => Option<R> */(this /* : FlatMapHead */.mapper /* : unknown */) /* : Option<R> */;
        }
        else {
            return new None() /* : None */;
        }
    }
};
/* private */ class ArrayType /*  */ {
    constructor(right) {
    }
    /* @Override
        public */ generate() {
        return this /* : ArrayType */.right /* : unknown */() /* : unknown */.generate /* : unknown */() /* : unknown */ + "[]";
    }
    /* @Override
        public */ replace(mapping) {
        return this /* : ArrayType */;
    }
    /* @Override
        public */ findName() {
        return new None() /* : None */;
    }
}
/* private static final */ class Whitespace /*  */ {
    /* @Override
        public */ generate() {
        return "";
    }
    /* @Override
        public */ createDefinition() {
        return new None() /* : None */;
    }
}
/* private static */ class Iterators /*  */ {
    /* public static  */ fromOption(option) {
        let single = option /* : Option<T> */.map /* : (arg0 : (arg0 : T) => R) => Option<R> */(SingleHead /* : SingleHead */.new /* : unknown */) /* : Option<R> */;
        return new HeadedIterator(single /* : Option<Head<T>> */.orElseGet /* : (arg0 : () => T) => T */(EmptyHead /* : EmptyHead */.new /* : unknown */) /* : T */) /* : HeadedIterator */;
    }
}
/* private */ class FunctionType /*  */ {
    constructor(arguments, returns) {
    }
    /* @Override
        public */ generate() {
        let joined = this /* : FunctionType */.arguments /* : unknown */() /* : unknown */.iterateWithIndices /* : unknown */() /* : unknown */.map /* : unknown */((pair) => "arg" + pair /* : unknown */.left /* : unknown */() /* : unknown */ + " : " + pair /* : unknown */.right /* : unknown */() /* : unknown */.generate /* : unknown */() /* : unknown */) /* : unknown */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        return "(" + joined /* : unknown */ + ") => " + this /* : FunctionType */.returns /* : unknown */.generate /* : unknown */() /* : unknown */;
    }
    /* @Override
        public */ replace(mapping) {
        return new FunctionType(this /* : FunctionType */.arguments /* : unknown */.iterate /* : unknown */() /* : unknown */.map /* : unknown */((type) => type /* : unknown */.replace /* : unknown */(mapping /* : Map<string, Type> */) /* : unknown */) /* : unknown */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */, this /* : FunctionType */.returns /* : unknown */) /* : FunctionType */;
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
        let joinedArguments = this /* : TupleType */.arguments /* : unknown */.iterate /* : unknown */() /* : unknown */.map /* : unknown */(Type /* : Type */.generate /* : unknown */) /* : unknown */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        return "[" + joinedArguments + "]";
    }
    /* @Override
        public */ replace(mapping) {
        return this /* : TupleType */;
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
        let joinedArguments = this /* : Template */.arguments /* : unknown */.iterate /* : unknown */() /* : unknown */.map /* : unknown */(Type /* : Type */.generate /* : unknown */) /* : unknown */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.map /* : unknown */((inner) => "<" + inner + ">") /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        return this /* : Template */.base /* : unknown */.generate /* : unknown */() /* : unknown */ + joinedArguments /* : unknown */;
    }
    /* @Override
        public */ typeParams() {
        return this /* : Template */.base /* : unknown */.typeParams /* : unknown */() /* : unknown */;
    }
    /* @Override
        public */ find(name) {
        return this /* : Template */.base /* : unknown */.find /* : unknown */(name /* : string */) /* : unknown */.map /* : unknown */((found) => {
            let mapping = this /* : Template */.base /* : unknown */.typeParams /* : unknown */() /* : unknown */.iterate /* : unknown */() /* : unknown */.zip /* : unknown */(this /* : Template */.arguments /* : unknown */.iterate /* : unknown */() /* : unknown */) /* : unknown */.collect /* : unknown */(new /* MapCollector */ () /* : content-start MapCollector content-end */) /* : unknown */;
            return found /* : unknown */.replace /* : unknown */(mapping /* : unknown */) /* : unknown */;
        }) /* : unknown */;
    }
    /* @Override
        public */ name() {
        return this /* : Template */.base /* : unknown */.name /* : unknown */() /* : unknown */;
    }
    /* @Override
        public */ replace(mapping) {
        return this /* : Template */;
    }
    /* @Override
        public */ findName() {
        return this /* : Template */.base /* : unknown */.findName /* : unknown */() /* : unknown */;
    }
}
/* private */ class Placeholder /*  */ {
    constructor(input) {
    }
    /* @Override
        public */ generate() {
        return /* generatePlaceholder */ (this /* : Placeholder */.input /* : unknown */) /* : unknown */;
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
        return this /* : Placeholder */.input /* : unknown */;
    }
    /* @Override
        public */ replace(mapping) {
        return this /* : Placeholder */;
    }
    /* @Override
        public */ findName() {
        return new None() /* : None */;
    }
    /* @Override
        public */ createDefinition() {
        return new None() /* : None */;
    }
}
/* private */ class StringValue /*  */ {
    constructor(stripped) {
    }
    /* @Override
        public */ generate() {
        return this /* : StringValue */.stripped /* : unknown */;
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
        return this /* : DataAccess */.parent /* : unknown */.generate /* : unknown */() /* : unknown */ + "." + this /* : DataAccess */.property /* : unknown */ + /* createDebugString */ (this /* : DataAccess */.type /* : () => Type */) /* : unknown */;
    }
    /* @Override
        public */ type() {
        return this /* : DataAccess */.type /* : () => Type */;
    }
}
/* private */ class ConstructionCaller /*  */ {
    constructor(type) {
    }
    /* @Override
        public */ generate() {
        return "new " + this /* : ConstructionCaller */.type /* : unknown */.generate /* : unknown */() /* : unknown */;
    }
    /* public */ toFunction() {
        return new FunctionType(Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */, this /* : ConstructionCaller */.type /* : unknown */) /* : FunctionType */;
    }
}
/* private */ class Operation /*  */ {
    constructor(left, operator /* Operator */, right) {
    }
    /* @Override
        public */ generate() {
        return this /* : Operation */.left /* : unknown */() /* : unknown */.generate /* : unknown */() /* : unknown */ + " " + this /* : Operation */.operator /* : unknown */.targetRepresentation /* : unknown */ + " " + this /* : Operation */.right /* : unknown */() /* : unknown */.generate /* : unknown */() /* : unknown */;
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
        return "!" + this /* : Not */.value /* : unknown */.generate /* : unknown */() /* : unknown */;
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
        return this /* : BlockLambdaValue */.statements /* : unknown */.iterate /* : unknown */() /* : unknown */.map /* : unknown */(FunctionSegment /* : FunctionSegment */.generate /* : unknown */) /* : unknown */.collect /* : unknown */(new Joiner() /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
    }
}
/* private */ class Lambda /*  */ {
    constructor(parameters, body) {
    }
    /* @Override
        public */ generate() {
        let joined = this /* : Lambda */.parameters /* : unknown */.iterate /* : unknown */() /* : unknown */.map /* : unknown */(Definition /* : Definition */.generate /* : unknown */) /* : unknown */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        return "(" + joined /* : unknown */ + ") => " + this /* : Lambda */.body /* : unknown */.generate /* : unknown */() /* : unknown */;
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
        let joined = this /* : Invokable */.arguments /* : unknown */.iterate /* : unknown */() /* : unknown */.map /* : unknown */(Value /* : Value */.generate /* : unknown */) /* : unknown */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        return this /* : Invokable */.caller /* : unknown */.generate /* : unknown */() /* : unknown */ + "(" + joined /* : unknown */ + ")" + /* createDebugString */ (this /* : Invokable */.type /* : unknown */) /* : unknown */;
    }
}
/* private */ class IndexValue /*  */ {
    constructor(parent, child) {
    }
    /* @Override
        public */ generate() {
        return this /* : IndexValue */.parent /* : unknown */.generate /* : unknown */() /* : unknown */ + "[" + this.child.generate() + "]";
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
        return this /* : SymbolValue */.stripped /* : unknown */ + /* createDebugString */ (this /* : SymbolValue */.type /* : unknown */) /* : unknown */;
    }
}
/* private */ class JVMMap {
    constructor(map) {
    }
    /* @Override
            public */ find(key) {
        if (this /* : JVMMap */.map /* : unknown */.containsKey /* : unknown */(key /* : K */) /* : unknown */) {
            return new Some(this /* : JVMMap */.map /* : unknown */.get /* : unknown */(key /* : K */) /* : unknown */) /* : Some */;
        }
        return new None() /* : None */;
    }
    /* @Override
            public */ with(key, value) {
        /* this.map.put(key, value) */ ;
        return this /* : JVMMap */;
    }
}
/* private static */ class Maps /*  */ {
    /* public static  */ empty() {
        return new JVMMap() /* : JVMMap */;
    }
}
/* private */ class MapCollector {
    /* @Override
        public */ createInitial() {
        return Maps /* : Maps */.empty /* : () => Map<K, V> */() /* : Map<K, V> */;
    }
    /* @Override
        public */ fold(current, element) {
        return current /* : Map<K, V> */.with /* : (arg0 : K, arg1 : V) => Map<K, V> */(element /* : [K, V] */[0 /* : number */]() /* : unknown */, element /* : [K, V] */[1 /* : number */]() /* : unknown */) /* : Map<K, V> */;
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
/* private */ class DefinitionStatement /*  */ {
    constructor(depth, definition) {
    }
    /* @Override
        public */ generate() {
        return /* createIndent */ (this /* : DefinitionStatement */.depth /* : unknown */) /* : unknown */ + this /* : DefinitionStatement */.definition /* : unknown */.generate /* : unknown */() /* : unknown */ + ";";
    }
}
/* private static */ class Method /*  */ {
}
this /* : Method */.depth /* : unknown */ = depth /* : number */;
this /* : Method */.header /* : unknown */ = header /* : Header */;
this /* : Method */.parameters /* : unknown */ = parameters /* : List<Definition> */;
this /* : Method */.statements /* : unknown */ = maybeStatements /* : Option<List<FunctionSegment>> */;
/* private static */ joinStatements(statements, (List));
string;
{
    return statements /* : List<FunctionSegment> */.iterate /* : () => Iterator<T> */() /* : Iterator<T> */.map /* : (arg0 : (arg0 : T) => R) => Iterator<R> */(FunctionSegment /* : FunctionSegment */.generate /* : unknown */) /* : Iterator<R> */.collect /* : unknown */(new Joiner() /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
}
/* @Override
    public */ generate();
string;
{
    let indent = /* createIndent */ (this /* : Method */.depth /* : unknown */) /* : unknown */;
    let generatedHeader = this /* : Method */.header /* : unknown */.generateWithParams /* : unknown */(/* joinValues */ (this /* : Method */.parameters /* : unknown */) /* : unknown */) /* : unknown */;
    let generatedStatements = this /* : Method */.statements /* : unknown */.map /* : unknown */(Method /* : (arg0 : number, arg1 : Header, arg2 : List<Definition>, arg3 : Option<List<FunctionSegment>>) => content-start public content-end */.joinStatements /* : unknown */) /* : unknown */.map /* : unknown */((inner) => " {" + inner + indent + "}") /* : unknown */.orElse /* : unknown */(";") /* : unknown */;
    return indent /* : unknown */ + generatedHeader /* : unknown */ + generatedStatements /* : unknown */;
}
/* private */ class Block /*  */ {
    constructor(depth, header, statements) {
    }
    /* @Override
        public */ generate() {
        let indent = /* createIndent */ (this /* : Block */.depth /* : unknown */) /* : unknown */;
        let collect = this /* : Block */.statements /* : unknown */.iterate /* : unknown */() /* : unknown */.map /* : unknown */(FunctionSegment /* : FunctionSegment */.generate /* : unknown */) /* : unknown */.collect /* : unknown */(new Joiner() /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        return indent /* : unknown */ + this /* : Block */.header /* : unknown */.generate /* : unknown */() /* : unknown */ + "{" + collect + indent + "}";
    }
}
/* private */ class Conditional /*  */ {
    constructor(prefix, value1) {
    }
    /* @Override
        public */ generate() {
        return this /* : Conditional */.prefix /* : unknown */ + " (" + this.value1.generate() + ")";
    }
}
/* private static */ class Else /*  */ {
    /* @Override
        public */ generate() {
        return "else ";
    }
}
/* private static */ class Return /*  */ {
}
this /* : Return */.value1 /* : unknown */ = value1 /* : Value */;
/* @Override
    public */ generate();
string;
{
    return "return " + this /* : Return */.value1 /* : unknown */.generate /* : unknown */() /* : unknown */;
}
/* private */ class Initialization /*  */ {
    constructor(definition, source) {
    }
    /* @Override
        public */ generate() {
        return "let " + this /* : Initialization */.definition /* : unknown */.generate /* : unknown */() /* : unknown */ + " = " + this /* : Initialization */.source /* : unknown */.generate /* : unknown */() /* : unknown */;
    }
}
/* private */ class Assignment /*  */ {
    constructor(destination, source) {
    }
    /* @Override
        public */ generate() {
        return this /* : Assignment */.destination /* : unknown */.generate /* : unknown */() /* : unknown */ + " = " + this /* : Assignment */.source /* : unknown */.generate /* : unknown */() /* : unknown */;
    }
}
/* private */ class Statement /*  */ {
    constructor(depth, value) {
    }
    /* @Override
        public */ generate() {
        return /* createIndent */ (this /* : Statement */.depth /* : unknown */) /* : unknown */ + this /* : Statement */.value /* : unknown */.generate /* : unknown */() /* : unknown */ + ";";
    }
}
/* private */ class MethodPrototype /*  */ {
    constructor(depth, header, parameters, content) {
    }
    /* private */ findParamTypes() {
        return this /* : MethodPrototype */.parameters /* : unknown */() /* : unknown */.iterate /* : unknown */() /* : unknown */.map /* : unknown */(Definition /* : Definition */.type /* : unknown */) /* : unknown */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */;
    }
    /* @Override
        public */ createDefinition() {
        return new Some(this /* : MethodPrototype */.header /* : unknown */.createDefinition /* : unknown */(this /* : MethodPrototype */.findParamTypes /* : () => List<Type> */() /* : List<Type> */) /* : unknown */) /* : Some */;
    }
}
/* private */ class IncompleteClassSegmentWrapper /*  */ {
    constructor(segment) {
    }
    /* @Override
        public */ createDefinition() {
        return new None() /* : None */;
    }
}
/* private */ class Primitive /*  */ {
    constructor(value) {
        this /* : Primitive */.value /* : unknown */ = value /* : string */;
    }
    /* @Override
        public */ generate() {
        return this /* : Primitive */.value /* : unknown */;
    }
    /* @Override
        public */ replace(mapping) {
        return this /* : Primitive */;
    }
    /* @Override
        public */ findName() {
        return new None() /* : None */;
    }
}
/* private */ class Operator /*  */ {
    constructor(sourceRepresentation, targetRepresentation) {
        this /* : Operator */.sourceRepresentation /* : unknown */ = sourceRepresentation /* : string */;
        this /* : Operator */.targetRepresentation /* : unknown */ = targetRepresentation /* : string */;
    }
}
/* private */ class BooleanValue /*  */ {
    constructor(value) {
        this /* : BooleanValue */.value /* : unknown */ = value /* : string */;
    }
    /* @Override
        public */ generate() {
        return this /* : BooleanValue */.value /* : unknown */;
    }
    /* @Override
        public */ type() {
        return Primitive /* : Primitive */.Boolean /* : unknown */;
    }
}
/* public */ class Main /*  */ {
    /* public static */ main() {
        let parent = /* Paths */ .get /* : unknown */(".", "src", "java", "magma") /* : unknown */;
        let source = parent /* : unknown */.resolve /* : unknown */("Main.java") /* : unknown */;
        let target = parent /* : unknown */.resolve /* : unknown */("main.ts") /* : unknown */;
        let input = /* Files */ .readString /* : unknown */(source /* : unknown */) /* : unknown */;
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
/* private static */ compile(input, string);
string;
{
    let state = new CompileState() /* : CompileState */;
    let parsed = /* parseStatements */ (state /* : CompileState */, input /* : unknown */, Main /* : Main */.compileRootSegment /* : unknown */) /* : unknown */;
    let joined = parsed /* : unknown */.left /* : unknown */() /* : unknown */.structures /* : unknown */.iterate /* : unknown */() /* : unknown */.collect /* : unknown */(new Joiner() /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
    return joined /* : unknown */ + /* generateStatements */ (parsed /* : unknown */.right /* : unknown */() /* : unknown */) /* : unknown */;
}
/* private static */ generateStatements(statements, (List));
string;
{
    return /* generateAll */ (Main /* : Main */.mergeStatements /* : unknown */, statements /* : List<string> */) /* : unknown */;
}
/* private static  */ parseStatements(state, CompileState, input, string, mapper, (arg0, arg1) => [CompileState, T]);
[CompileState, (List)];
{
    return /* parseAllWithIndices */ (state /* : CompileState */, input /* : unknown */, Main /* : Main */.foldStatementChar /* : unknown */, (state3, tuple) => ((BiFunction)(state1, s)));
    right /* : unknown */( /* ) */); /* : unknown */
    orElseGet /* : unknown */(() => new Tuple2Impl(state /* : CompileState */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */) /* : Tuple2Impl */) /* : unknown */;
}
/* private static */ generateAll(merger, (arg0, arg1) => string, elements, (List));
string;
{
    return elements /* : List<string> */.iterate /* : () => Iterator<T> */() /* : Iterator<T> */.fold /* : (arg0 : R, arg1 : (arg0 : R, arg1 : T) => R) => R */("", merger /* : (arg0 : string, arg1 : string) => string */) /* : R */;
}
/* private static  */ parseAllWithIndices(state, CompileState, input, string, folder, (arg0, arg1) => DivideState, mapper, (arg0, arg1) => Option);
Option < [CompileState, (List)] > {
    let, stringList = (input /* : unknown */, folder /* : (arg0 : DivideState, arg1 : string) => DivideState */) /* : unknown */
};
/* private static  */ mapUsingState(state, CompileState, elements, (List), mapper, (arg0, arg1) => Option);
Option < [CompileState, (List)] > {
    let, initial: (Option) = new Some(new Tuple2Impl(state /* : CompileState */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */) /* : Tuple2Impl */) /* : Some */,
    return: elements /* : List<string> */.iterateWithIndices /* : () => Iterator<[number, T]> */() /* : Iterator<[number, T]> */.fold /* : (arg0 : R, arg1 : (arg0 : R, arg1 : [number, T]) => R) => R */(initial /* : Option<[CompileState, List<R>]> */, (tuple, element) => {
        return tuple /* : unknown */.flatMap /* : unknown */((inner) => {
            let state1 = inner /* : unknown */.left /* : unknown */() /* : unknown */;
            let right = inner /* : unknown */.right /* : unknown */() /* : unknown */;
            return mapper /* : (arg0 : CompileState, arg1 : string) => [CompileState, T] */(state1 /* : unknown */, element /* : unknown */) /* : [CompileState, T] */.map /* : unknown */((applied) => {
                return new Tuple2Impl(applied /* : unknown */.left /* : unknown */() /* : unknown */, right /* : unknown */.addLast /* : unknown */(applied /* : unknown */.right /* : unknown */() /* : unknown */) /* : unknown */) /* : Tuple2Impl */;
            }) /* : unknown */;
        }) /* : unknown */;
    }) /* : R */
};
/* private static */ mergeStatements(cache, string, statement, string);
string;
{
    return cache /* : string */ + statement /* : string */;
}
/* private static */ divideAll(input, string, folder, (arg0, arg1) => DivideState);
List < string > {
    let, current: DivideState = new DivideState(input /* : unknown */) /* : DivideState */,
    while() {
        let maybePopped = current /* : DivideState */.pop /* : () => Option<[string, DivideState]> */() /* : Option<[string, DivideState]> */.map /* : (arg0 : (arg0 : [string, DivideState]) => R) => Option<R> */((tuple) => {
            return /* foldSingleQuotes */ (tuple /* : unknown */) /* : unknown */.or /* : unknown */(() => /* foldDoubleQuotes */ (tuple /* : unknown */) /* : unknown */) /* : unknown */.orElseGet /* : unknown */(() => folder /* : (arg0 : DivideState, arg1 : string) => DivideState */(tuple /* : unknown */.right /* : unknown */() /* : unknown */, tuple /* : unknown */.left /* : unknown */() /* : unknown */) /* : DivideState */) /* : unknown */;
        }) /* : Option<R> */;
        if (maybePopped /* : Option<R> */.isPresent /* : unknown */() /* : unknown */) {
            current /* : DivideState */ = maybePopped /* : Option<R> */.orElse /* : unknown */(current /* : DivideState */) /* : unknown */;
        }
        else {
            /* break */ ;
        }
    },
    return: current /* : DivideState */.advance /* : () => DivideState */() /* : DivideState */.segments /* : unknown */
};
/* private static */ foldDoubleQuotes(tuple, [string, DivideState]);
Option < DivideState > {
    if(tuple) { } /* : unknown */, /* : unknown */ : /* : unknown */ .left /* : unknown */() /* : unknown */ ===  /*  '\"' */
};
{
    let current = tuple /* : unknown */.right /* : unknown */() /* : unknown */.append /* : unknown */(tuple /* : unknown */.left /* : unknown */() /* : unknown */) /* : unknown */;
    while (true) {
        let maybePopped = current /* : DivideState */.popAndAppendToTuple /* : () => Option<[string, DivideState]> */() /* : Option<[string, DivideState]> */;
        if (maybePopped /* : Option<R> */.isEmpty /* : unknown */() /* : unknown */) {
            /* break */ ;
        }
        let popped = maybePopped /* : Option<R> */.orElse /* : unknown */( /* null */) /* : unknown */;
        current /* : DivideState */ = popped /* : unknown */.right /* : unknown */() /* : unknown */;
        if (popped /* : unknown */.left /* : unknown */() /* : unknown */ ===  /*  '\\' */) {
            current /* : DivideState */ = current /* : DivideState */.popAndAppendToOption /* : () => Option<DivideState> */() /* : Option<DivideState> */.orElse /* : (arg0 : DivideState) => T */(current /* : DivideState */) /* : T */;
        }
        if (popped /* : unknown */.left /* : unknown */() /* : unknown */ ===  /*  '\"' */) {
            /* break */ ;
        }
    }
    return new Some(current /* : DivideState */) /* : Some */;
}
return new None() /* : None */;
/* private static */ foldSingleQuotes(tuple, [string, DivideState]);
Option < DivideState > {
    if( /* tuple.left() != '\'' */) {
        return new None() /* : None */;
    },
    let, appended = tuple /* : unknown */.right /* : unknown */() /* : unknown */.append /* : unknown */(tuple /* : unknown */.left /* : unknown */() /* : unknown */) /* : unknown */,
    return: appended /* : unknown */.popAndAppendToTuple /* : unknown */() /* : unknown */.map /* : unknown */(Main /* : Main */.foldEscaped /* : unknown */) /* : unknown */.flatMap /* : unknown */(DivideState /* : DivideState */.popAndAppendToOption /* : unknown */) /* : unknown */
};
/* private static */ foldEscaped(escaped, [string, DivideState]);
DivideState;
{
    if (escaped /* : [string, DivideState] */[0 /* : number */]() /* : unknown */ ===  /*  '\\' */) {
        return escaped /* : [string, DivideState] */[1 /* : number */]() /* : unknown */.popAndAppendToOption /* : unknown */() /* : unknown */.orElse /* : unknown */(escaped /* : [string, DivideState] */[1 /* : number */]() /* : unknown */) /* : unknown */;
    }
    return escaped /* : [string, DivideState] */[1 /* : number */]() /* : unknown */;
}
/* private static */ foldStatementChar(state, DivideState, c, string);
DivideState;
{
    let append = state /* : CompileState */.append /* : unknown */(c /* : string */) /* : unknown */;
    if (c /* : string */ ===  /*  ';'  */ && append /* : unknown */.isLevel /* : unknown */() /* : unknown */) {
        return append /* : unknown */.advance /* : unknown */() /* : unknown */;
    }
    if (c /* : string */ ===  /*  '}'  */ && append /* : unknown */.isShallow /* : unknown */() /* : unknown */) {
        return append /* : unknown */.advance /* : unknown */() /* : unknown */.exit /* : unknown */() /* : unknown */;
    }
    if (c /* : string */ ===  /*  '{'  */ || c /* : string */ ===  /*  '(' */) {
        return append /* : unknown */.enter /* : unknown */() /* : unknown */;
    }
    if (c /* : string */ ===  /*  '}'  */ || c /* : string */ ===  /*  ')' */) {
        return append /* : unknown */.exit /* : unknown */() /* : unknown */;
    }
    return append /* : unknown */;
}
/* private static */ compileRootSegment(state, CompileState, input, string);
[CompileState, string];
{
    let stripped = input /* : unknown */.strip /* : unknown */() /* : unknown */;
    if (stripped /* : unknown */.startsWith /* : unknown */("package ") /* : unknown */ || stripped /* : unknown */.startsWith /* : unknown */("import ") /* : unknown */) {
        return new Tuple2Impl(state /* : CompileState */, "") /* : Tuple2Impl */;
    }
    return /* parseClass */ (stripped /* : unknown */, state /* : CompileState */) /* : unknown */.map /* : unknown */((tuple) => {
        return new Tuple2Impl(tuple /* : unknown */.left /* : unknown */() /* : unknown */, tuple /* : unknown */.right /* : unknown */() /* : unknown */.segment /* : unknown */.generate /* : unknown */() /* : unknown */) /* : Tuple2Impl */;
    }) /* : unknown */.orElseGet /* : unknown */(() => new Tuple2Impl(state /* : CompileState */, /* generatePlaceholder */ (stripped /* : unknown */) /* : unknown */) /* : Tuple2Impl */) /* : unknown */;
}
/* private static */ parseClass(stripped, string, state, CompileState);
Option < [CompileState, IncompleteClassSegmentWrapper] > {};
/* private static */ parseStructure(stripped, string, sourceInfix, string, targetInfix, string, state, CompileState);
Option < [CompileState, IncompleteClassSegmentWrapper] > {
    return /* first */(stripped /* : unknown */, sourceInfix /* : string */) { }
}(beforeInfix, right);
{
    return /* first */ (right /* : unknown */, "{", (beforeContent, withEnd) => {
        return /* suffix */ (withEnd /* : unknown */.strip /* : unknown */() /* : unknown */, "}", (content1) => {
            return /* getOr */ (targetInfix /* : string */, state /* : CompileState */, beforeInfix /* : unknown */, beforeContent /* : unknown */, content1 /* : unknown */) /* : unknown */;
        }) /* : unknown */;
    }) /* : unknown */;
}
;
/* private static */ getOr(targetInfix, string, state, CompileState, beforeInfix, string, beforeContent, string, content1, string);
Option < [CompileState, IncompleteClassSegmentWrapper] > {
    return /* first */(beforeContent /* : unknown */, ) { }
}(s, s2);
{
    return /* structureWithMaybeExtends */ (targetInfix /* : string */, state /* : CompileState */, beforeInfix /* : unknown */, s /* : unknown */, content1 /* : unknown */) /* : unknown */;
}
or /* : unknown */(() => {
    return /* structureWithMaybeExtends */ (targetInfix /* : string */, state /* : CompileState */, beforeInfix /* : unknown */, beforeContent /* : unknown */, content1 /* : unknown */) /* : unknown */;
}) /* : unknown */;
/* private static */ structureWithMaybeExtends(targetInfix, string, state, CompileState, beforeInfix, string, beforeContent, string, content1, string);
Option < [CompileState, IncompleteClassSegmentWrapper] > {
    return /* first */(beforeContent /* : unknown */, ) { }
}(s, s2);
{
    return /* structureWithMaybeParams */ (targetInfix /* : string */, state /* : CompileState */, beforeInfix /* : unknown */, s /* : unknown */, content1 /* : unknown */) /* : unknown */;
}
or /* : unknown */(() => {
    return /* structureWithMaybeParams */ (targetInfix /* : string */, state /* : CompileState */, beforeInfix /* : unknown */, beforeContent /* : unknown */, content1 /* : unknown */) /* : unknown */;
}) /* : unknown */;
/* private static */ structureWithMaybeParams(targetInfix, string, state, CompileState, beforeInfix, string, beforeContent, string, content1, string);
Option < [CompileState, IncompleteClassSegmentWrapper] > {
    return /* suffix */(beforeContent) { } /* : unknown */, /* : unknown */ : /* : unknown */ .strip /* : unknown */() /* : unknown */, ")": ,
}(s);
{
    return /* first */ (s /* : unknown */, "(", (s1, s2) => {
        let parsed = /* parseParameters */ (state /* : CompileState */, s2 /* : unknown */) /* : unknown */;
        return /* getOred */ (targetInfix /* : string */, parsed /* : unknown */.left /* : unknown */() /* : unknown */, beforeInfix /* : unknown */, s1 /* : unknown */, content1 /* : unknown */, parsed /* : unknown */.right /* : unknown */() /* : unknown */) /* : unknown */;
    }) /* : unknown */;
}
or /* : unknown */(() => {
    return /* getOred */ (targetInfix /* : string */, state /* : CompileState */, beforeInfix /* : unknown */, beforeContent /* : unknown */, content1 /* : unknown */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */) /* : unknown */;
}) /* : unknown */;
/* private static */ getOred(targetInfix, string, state, CompileState, beforeInfix, string, beforeContent, string, content1, string, params, (List));
Option < [CompileState, IncompleteClassSegmentWrapper] > {
    return /* first */(beforeContent /* : unknown */, ) { }
}(name, withTypeParams);
{
    return /* first */ (withTypeParams /* : unknown */, ">", (typeParamsString, afterTypeParams) => {
        let /* final */ mapper = (state1, s) => new Tuple2Impl(state1 /* : unknown */, s /* : unknown */.strip /* : unknown */() /* : unknown */) /* : Tuple2Impl */;
        let typeParams = /* parseValuesOrEmpty */ (state /* : CompileState */, typeParamsString /* : unknown */, (state1, s) => new Some(mapper /* : (arg0 : CompileState, arg1 : string) => [CompileState, T] */(state1 /* : unknown */, s /* : unknown */) /* : [CompileState, T] */) /* : Some */) /* : unknown */;
        return /* assembleStructure */ (typeParams /* : unknown */.left /* : unknown */() /* : unknown */, targetInfix /* : string */, beforeInfix /* : unknown */, name /* : unknown */, content1 /* : unknown */, typeParams /* : unknown */.right /* : unknown */() /* : unknown */, afterTypeParams /* : unknown */, params /* : List<Parameter> */) /* : unknown */;
    }) /* : unknown */;
}
or /* : unknown */(() => {
    return /* assembleStructure */ (state /* : CompileState */, targetInfix /* : string */, beforeInfix /* : unknown */, beforeContent /* : unknown */, content1 /* : unknown */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */, "", params /* : List<Parameter> */) /* : unknown */;
}) /* : unknown */;
/* private static */ assembleStructure(state, CompileState, targetInfix, string, beforeInfix, string, rawName, string, content, string, typeParams, (List), after, string, rawParameters, (List));
Option < [CompileState, IncompleteClassSegmentWrapper] > {
    let, name = rawName /* : string */.strip /* : unknown */() /* : unknown */,
    if() { }
} /* isSymbol */(name /* : unknown */); /* : unknown */
{
    return new None() /* : None */;
}
let segmentsTuple = parseStatements /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => [CompileState, T]) => [CompileState, List<T>] */(state /* : CompileState */.pushStructName /* : (arg0 : string) => CompileState */(name /* : unknown */) /* : CompileState */.withTypeParams /* : unknown */(typeParams /* : unknown */) /* : unknown */, content /* : string */, (state0, input) => /* parseClassSegment */ (state0 /* : unknown */, input /* : unknown */, 1 /* : number */) /* : unknown */) /* : [CompileState, List<T>] */;
let segmentsState = segmentsTuple /* : [CompileState, List<T>] */[0 /* : number */]() /* : unknown */;
let segments = segmentsTuple /* : [CompileState, List<T>] */[1 /* : number */]() /* : unknown */;
let definitions = segments /* : unknown */.iterate /* : unknown */() /* : unknown */.map /* : unknown */(IncompleteClassSegment /* : IncompleteClassSegment */.createDefinition /* : unknown */) /* : unknown */.flatMap /* : unknown */(Iterators /* : Iterators */.fromOption /* : unknown */) /* : unknown */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */;
let thisType = new ObjectType(name /* : unknown */, typeParams /* : unknown */, definitions /* : unknown */) /* : ObjectType */;
let state2 = segmentsState /* : unknown */.enterDefinitions /* : unknown */() /* : unknown */.withDefinition /* : unknown */(ImmutableDefinition /* : ImmutableDefinition */.createSimpleDefinition /* : (arg0 : string, arg1 : Type) => Definition */("this", thisType /* : ObjectType */) /* : Definition */) /* : unknown */;
return mapUsingState /* : (arg0 : CompileState, arg1 : List<T>, arg2 : (arg0 : CompileState, arg1 : [number, T]) => Option<[CompileState, R]>) => Option<[CompileState, List<R>]> */(state2 /* : unknown */, segments /* : unknown */, (state1, entry) => /* switch (entry.right()) {
    case IncompleteClassSegmentWrapper wrapper  */ - - - - /* > new Some<>(new Tuple2Impl<>(state1, placeholder));
} */) /* : Option<[CompileState, List<R>]> */.map /* : (arg0 : (arg0 : [CompileState, List<R>]) => R) => Option<R> */((completedTuple) => {
    let completedState = completedTuple /* : [CompileState, List<R>] */[0 /* : number */]() /* : unknown */;
    let completed = completedTuple /* : [CompileState, List<R>] */[1 /* : number */]() /* : unknown */;
    return /* completeStructure */ (completedState /* : unknown */.exitDefinitions /* : unknown */() /* : unknown */, targetInfix /* : string */, beforeInfix /* : unknown */, name /* : unknown */, typeParams /* : unknown */, /* retainDefinitions */ (rawParameters /* : List<Parameter> */) /* : unknown */, after /* : string */, completed /* : unknown */, thisType /* : ObjectType */) /* : unknown */;
}) /* : Option<R> */;
/* private static */ completeStructure(state, CompileState, targetInfix, string, beforeInfix, string, name, string, typeParams, (List), parameters, (List), after, string, segments, (List), thisType, ObjectType);
Tuple2Impl < CompileState, IncompleteClassSegmentWrapper > {
    if(parameters) { } /* : List<Definition> */, /* : List<Definition> */ : /* : List<Definition> */ .isEmpty /* : () => boolean */() /* : boolean */
};
{
    segments /* : unknown */;
}
{
    segments /* : unknown */.addFirst /* : unknown */(new Method(1 /* : number */, new ConstructorHeader() /* : ConstructorHeader */, parameters /* : List<Definition> */, new Some(Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */) /* : Some */) /* : Method */) /* : unknown */;
}
let parsed2 = /* withMaybeConstructor */ .iterate /* : unknown */() /* : unknown */.map /* : unknown */(ClassSegment /* : ClassSegment */.generate /* : unknown */) /* : unknown */.collect /* : unknown */(new Joiner() /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
let joinedTypeParams = typeParams /* : unknown */.iterate /* : unknown */() /* : unknown */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.map /* : unknown */((inner) => "<" + inner + ">") /* : unknown */.orElse /* : unknown */("") /* : unknown */;
let generated = /* generatePlaceholder */ (beforeInfix /* : unknown */.strip /* : unknown */() /* : unknown */) /* : unknown */ + targetInfix /* : string */ + name /* : unknown */ + joinedTypeParams /* : unknown */ + /* generatePlaceholder */ (after /* : string */) /* : unknown */ + " {" + parsed2 + "\n}\n";
let definedState = state /* : CompileState */.popStructName /* : () => CompileState */() /* : CompileState */.addStructure /* : unknown */(generated /* : unknown */) /* : unknown */.addType /* : unknown */(thisType /* : ObjectType */) /* : unknown */;
return new Tuple2Impl(definedState /* : unknown */, new IncompleteClassSegmentWrapper(new Whitespace() /* : Whitespace */) /* : IncompleteClassSegmentWrapper */) /* : Tuple2Impl */;
/* private static */ retainDefinition(parameter, Parameter);
Option < Definition > {
    if(parameter) { } /* : Parameter */, /* : Parameter */ : /* : Parameter */ ._variant /* : unknown */ === ParameterVariant.Definition /* : unknown */
};
{
    return new Some( /* definition */) /* : Some */;
}
return new None() /* : None */;
/* private static */ isSymbol(input, string);
boolean;
{
    /* for (var i = 0; i < input.length(); i++) */ {
        let c = input /* : unknown */.charAt /* : unknown */( /* i */) /* : unknown */;
        if ( /* Character */.isLetter /* : unknown */(c /* : string */) /* : unknown */ || /*  */ ( /* i != 0  */ && /* Character */ .isDigit /* : unknown */(c /* : string */) /* : unknown */) /* : unknown */) {
            /* continue */ ;
        }
        return false;
    }
    return true;
}
/* private static  */ prefix(input, string, prefix, string, mapper, (arg0) => Option);
Option < T > {
    if(, input) { } /* : unknown */, /* : unknown */ : /* : unknown */ .startsWith /* : unknown */(prefix /* : string */) /* : unknown */
};
{
    return new None() /* : None */;
}
let slice = input /* : unknown */.substring /* : unknown */(prefix /* : string */.length /* : unknown */() /* : unknown */) /* : unknown */;
return mapper /* : (arg0 : CompileState, arg1 : string) => [CompileState, T] */(slice /* : unknown */) /* : [CompileState, T] */;
/* private static  */ suffix(input, string, suffix, string, mapper, (arg0) => Option);
Option < T > {
    if(, input) { } /* : unknown */, /* : unknown */ : /* : unknown */ .endsWith /* : unknown */(suffix /* : string */) /* : unknown */
};
{
    return new None() /* : None */;
}
let slice = input /* : unknown */.substring /* : unknown */(0 /* : number */, input /* : unknown */.length /* : unknown */() /* : unknown */ - suffix /* : string */.length /* : unknown */() /* : unknown */) /* : unknown */;
return mapper /* : (arg0 : CompileState, arg1 : string) => [CompileState, T] */(slice /* : unknown */) /* : [CompileState, T] */;
/* private static */ parseClassSegment(state, CompileState, input, string, depth, number);
[CompileState, IncompleteClassSegment];
{
    return /* Main.<Whitespace, IncompleteClassSegment>typed */ (() => /* parseWhitespace */ (input /* : unknown */, state /* : CompileState */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* typed */ (() => parseClass /* : (arg0 : string, arg1 : CompileState) => Option<[CompileState, IncompleteClassSegmentWrapper]> */(input /* : unknown */, state /* : CompileState */) /* : Option<[CompileState, IncompleteClassSegmentWrapper]> */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* typed */ (() => parseStructure /* : (arg0 : string, arg1 : string, arg2 : string, arg3 : CompileState) => Option<[CompileState, IncompleteClassSegmentWrapper]> */(input /* : unknown */, "interface ", "interface ", state /* : CompileState */) /* : Option<[CompileState, IncompleteClassSegmentWrapper]> */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* typed */ (() => parseStructure /* : (arg0 : string, arg1 : string, arg2 : string, arg3 : CompileState) => Option<[CompileState, IncompleteClassSegmentWrapper]> */(input /* : unknown */, "record ", "class ", state /* : CompileState */) /* : Option<[CompileState, IncompleteClassSegmentWrapper]> */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* typed */ (() => parseStructure /* : (arg0 : string, arg1 : string, arg2 : string, arg3 : CompileState) => Option<[CompileState, IncompleteClassSegmentWrapper]> */(input /* : unknown */, "enum ", "class ", state /* : CompileState */) /* : Option<[CompileState, IncompleteClassSegmentWrapper]> */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseMethod */ (state /* : CompileState */, input /* : unknown */, depth /* : number */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* typed */ (() => /* parseDefinitionStatement */ (input /* : unknown */, depth /* : number */, state /* : CompileState */) /* : unknown */) /* : unknown */) /* : unknown */.orElseGet /* : unknown */(() => new Tuple2Impl(state /* : CompileState */, new Placeholder(input /* : unknown */) /* : Placeholder */) /* : Tuple2Impl */) /* : unknown */;
}
/* private static  */ typed(action, () => Option);
Option < [CompileState, S] > {
    return: action /* : () => Option<[CompileState, T]> */() /* : Option<[CompileState, T]> */.map /* : (arg0 : (arg0 : [CompileState, T]) => R) => Option<R> */((tuple) => new Tuple2Impl(tuple /* : unknown */.left /* : unknown */() /* : unknown */, tuple /* : unknown */.right /* : unknown */() /* : unknown */) /* : Tuple2Impl */) /* : Option<R> */
};
/* private static */ parseWhitespace(input, string, state, CompileState);
Option < [CompileState, Whitespace] > {
    if(input) { } /* : unknown */, /* : unknown */ : /* : unknown */ .isBlank /* : unknown */() /* : unknown */
};
{
    return new Some(new Tuple2Impl(state /* : CompileState */, new Whitespace() /* : Whitespace */) /* : Tuple2Impl */) /* : Some */;
}
return new None() /* : None */;
/* private static */ parseMethod(state, CompileState, input, string, depth, number);
Option < [CompileState, IncompleteClassSegment] > {
    return /* first */(input /* : unknown */, ) { }
}(definitionString, withParams);
{
    return /* first */ (withParams /* : unknown */, ")", (parametersString, rawContent) => {
        return /* parseDefinition(state, definitionString).<Tuple2<CompileState, Header>>map */ ((tuple) => new Tuple2Impl(tuple /* : unknown */.left /* : unknown */() /* : unknown */, tuple /* : unknown */.right /* : unknown */() /* : unknown */) /* : Tuple2Impl */) /* : unknown */.or /* : unknown */(() => /* parseConstructor */ (state /* : CompileState */, definitionString /* : unknown */) /* : unknown */) /* : unknown */.flatMap /* : unknown */((definitionTuple) => /* assembleMethod */ (depth /* : number */, parametersString /* : unknown */, rawContent /* : unknown */, definitionTuple /* : unknown */) /* : unknown */) /* : unknown */;
    }) /* : unknown */;
}
;
/* private static */ assembleMethod(depth, number, parametersString, string, rawContent, string, definitionTuple, [CompileState, Header]);
Option < [CompileState, IncompleteClassSegment] > {
    let, definitionState = definitionTuple /* : unknown */.left /* : unknown */() /* : unknown */,
    let, header = definitionTuple /* : unknown */.right /* : unknown */() /* : unknown */,
    let, parametersTuple = (definitionState /* : unknown */, parametersString /* : unknown */) /* : unknown */,
    let, rawParameters = parametersTuple /* : unknown */.right /* : unknown */() /* : unknown */,
    let, parameters = (rawParameters /* : List<Parameter> */) /* : unknown */,
    return: new Some(new Tuple2Impl(parametersTuple /* : unknown */.left /* : unknown */() /* : unknown */, new MethodPrototype(depth /* : number */, header /* : unknown */, parameters /* : List<Definition> */, rawContent /* : unknown */.strip /* : unknown */() /* : unknown */) /* : MethodPrototype */) /* : Tuple2Impl */) /* : Some */
};
/* private static */ completeMethod(state, CompileState, prototype, MethodPrototype);
Option < [CompileState, ClassSegment] > {
    let, paramTypes: (List) = prototype /* : MethodPrototype */.findParamTypes /* : () => List<Type> */() /* : List<Type> */,
    let, toDefine = prototype /* : MethodPrototype */.header /* : unknown */() /* : unknown */.createDefinition /* : unknown */(paramTypes /* : List<Type> */) /* : unknown */,
    if(prototype) { } /* : MethodPrototype */, /* : MethodPrototype */ : /* : MethodPrototype */ .content /* : unknown */() /* : unknown */.equals /* : unknown */(";") /* : unknown */
};
{
    return new Some(new Tuple2Impl(state /* : CompileState */.withDefinition /* : (arg0 : Definition) => CompileState */(toDefine /* : unknown */) /* : CompileState */, new Method(prototype /* : MethodPrototype */.depth /* : unknown */() /* : unknown */, prototype /* : MethodPrototype */.header /* : unknown */() /* : unknown */, prototype /* : MethodPrototype */.parameters /* : unknown */() /* : unknown */, new None() /* : None */) /* : Method */) /* : Tuple2Impl */) /* : Some */;
}
if (prototype /* : MethodPrototype */.content /* : unknown */() /* : unknown */.startsWith /* : unknown */("{") /* : unknown */ && prototype /* : MethodPrototype */.content /* : unknown */() /* : unknown */.endsWith /* : unknown */("}") /* : unknown */) {
    let substring = prototype /* : MethodPrototype */.content /* : unknown */() /* : unknown */.substring /* : unknown */(1 /* : number */, prototype /* : MethodPrototype */.content /* : unknown */() /* : unknown */.length /* : unknown */() /* : unknown */ - 1 /* : number */) /* : unknown */;
    let withDefined = state /* : CompileState */.withDefinitions /* : (arg0 : List<Definition>) => CompileState */(prototype /* : MethodPrototype */.parameters /* : unknown */() /* : unknown */) /* : CompileState */;
    let statementsTuple = parseStatements /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => [CompileState, T]) => [CompileState, List<T>] */(withDefined /* : CompileState */, substring /* : unknown */, (state1, input1) => /* parseFunctionSegment */ (state1 /* : unknown */, input1 /* : unknown */, prototype /* : MethodPrototype */.depth /* : unknown */() /* : unknown */ + 1 /* : number */) /* : unknown */) /* : [CompileState, List<T>] */;
    let statements = statementsTuple /* : [CompileState, List<T>] */[1 /* : number */]() /* : unknown */;
    return new Some(new Tuple2Impl(statementsTuple /* : [CompileState, List<T>] */[0 /* : number */]() /* : unknown */.withDefinition /* : unknown */(toDefine /* : unknown */) /* : unknown */, new Method(prototype /* : MethodPrototype */.depth /* : unknown */() /* : unknown */, prototype /* : MethodPrototype */.header /* : unknown */() /* : unknown */, prototype /* : MethodPrototype */.parameters /* : unknown */() /* : unknown */, new Some(statements /* : List<string> */) /* : Some */) /* : Method */) /* : Tuple2Impl */) /* : Some */;
}
return new None() /* : None */;
/* private static */ parseConstructor(state, CompileState, input, string);
Option < [CompileState, Header] > {
    let, stripped = input /* : unknown */.strip /* : unknown */() /* : unknown */,
    if(stripped) { } /* : unknown */, /* : unknown */ : /* : unknown */ .equals /* : unknown */(state /* : CompileState */.structNames /* : unknown */.last /* : unknown */() /* : unknown */.orElse /* : unknown */("") /* : unknown */) /* : unknown */
};
{
    return new Some(new Tuple2Impl(state /* : CompileState */, new ConstructorHeader() /* : ConstructorHeader */) /* : Tuple2Impl */) /* : Some */;
}
return new None() /* : None */;
/* private static */ joinValues(retainParameters, (List));
string;
{
    let inner = retainParameters /* : List<Definition> */.iterate /* : () => Iterator<T> */() /* : Iterator<T> */.map /* : (arg0 : (arg0 : T) => R) => Iterator<R> */(Definition /* : Definition */.generate /* : unknown */) /* : Iterator<R> */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
    return "(" + inner + ")";
}
/* private static */ retainDefinitions(right, (List));
List < Definition > {
    return: right /* : unknown */.iterate /* : unknown */() /* : unknown */.map /* : unknown */(Main /* : Main */.retainDefinition /* : unknown */) /* : unknown */.flatMap /* : unknown */(Iterators /* : Iterators */.fromOption /* : unknown */) /* : unknown */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */
};
/* private static */ parseParameters(state, CompileState, params, string);
[CompileState, (List)];
{
    return /* parseValuesOrEmpty */ (state /* : CompileState */, params /* : List<Parameter> */, (state1, s) => new Some(/* parseParameter */ (state1 /* : unknown */, s /* : unknown */) /* : unknown */) /* : Some */) /* : unknown */;
}
/* private static */ parseFunctionSegments(state, CompileState, input, string, depth, number);
[CompileState, (List)];
{
    return parseStatements /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => [CompileState, T]) => [CompileState, List<T>] */(state /* : CompileState */, input /* : unknown */, (state1, input1) => /* parseFunctionSegment */ (state1 /* : unknown */, input1 /* : unknown */, depth /* : number */ + 1 /* : number */) /* : unknown */) /* : [CompileState, List<T>] */;
}
/* private static */ parseFunctionSegment(state, CompileState, input, string, depth, number);
[CompileState, FunctionSegment];
{
    let stripped = input /* : unknown */.strip /* : unknown */() /* : unknown */;
    if (stripped /* : unknown */.isEmpty /* : unknown */() /* : unknown */) {
        return new Tuple2Impl(state /* : CompileState */, new Whitespace() /* : Whitespace */) /* : Tuple2Impl */;
    }
    return /* parseFunctionStatement */ (state /* : CompileState */, depth /* : number */, stripped /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseBlock */ (state /* : CompileState */, depth /* : number */, stripped /* : unknown */) /* : unknown */) /* : unknown */.orElseGet /* : unknown */(() => new Tuple2Impl(state /* : CompileState */, new Placeholder(stripped /* : unknown */) /* : Placeholder */) /* : Tuple2Impl */) /* : unknown */;
}
/* private static */ parseFunctionStatement(state, CompileState, depth, number, stripped, string);
Option < [CompileState, FunctionSegment] > {
    return: suffix /* : string */(stripped /* : unknown */, ";", (s) => {
        let tuple = /* parseStatementValue */ (state /* : CompileState */, s /* : unknown */, depth /* : number */) /* : unknown */;
        let left = tuple /* : unknown */.left /* : unknown */() /* : unknown */;
        let right = tuple /* : unknown */.right /* : unknown */() /* : unknown */;
        return new Some(new Tuple2Impl(left /* : unknown */, new Statement(depth /* : number */, right /* : unknown */) /* : Statement */) /* : Tuple2Impl */) /* : Some */;
    }) /* : unknown */
};
/* private static */ parseBlock(state, CompileState, depth, number, stripped, string);
Option < [CompileState, FunctionSegment] > {
    return: suffix /* : string */(stripped /* : unknown */, "}", (withoutEnd) => {
        return /* split */ (() => /* toFirst */ (withoutEnd /* : unknown */) /* : unknown */, (beforeContent, content) => {
            return suffix /* : string */(beforeContent /* : unknown */, "{", (s) => {
                let statements = parseFunctionSegments /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, List<FunctionSegment>] */(state /* : CompileState */, content /* : string */, depth /* : number */) /* : [CompileState, List<FunctionSegment>] */;
                let headerTuple = /* parseBlockHeader */ (state /* : CompileState */, s /* : unknown */, depth /* : number */) /* : unknown */;
                let headerState = headerTuple /* : unknown */.left /* : unknown */() /* : unknown */;
                let header = headerTuple /* : unknown */.right /* : unknown */() /* : unknown */;
                let right = statements /* : List<string> */.right /* : unknown */() /* : unknown */;
                return new Some(new Tuple2Impl(headerState /* : unknown */, new Block(depth /* : number */, header /* : unknown */, right /* : unknown */) /* : Block */) /* : Tuple2Impl */) /* : Some */;
            }) /* : unknown */;
        }) /* : unknown */;
    }) /* : unknown */
};
/* private static */ toFirst(input, string);
Option < [string, string] > {
    let, divisions: (List) = divideAll /* : (arg0 : string, arg1 : (arg0 : DivideState, arg1 : string) => DivideState) => List<string> */(input /* : unknown */, Main /* : Main */.foldBlockStart /* : unknown */) /* : List<string> */,
    return: divisions /* : List<string> */.removeFirst /* : () => Option<[T, List<T>]> */() /* : Option<[T, List<T>]> */.map /* : (arg0 : (arg0 : [T, List<T>]) => R) => Option<R> */((removed) => {
        let right = removed /* : [T, List<T>] */[0 /* : number */]() /* : unknown */;
        let left = removed /* : [T, List<T>] */[1 /* : number */]() /* : unknown */.iterate /* : unknown */() /* : unknown */.collect /* : unknown */(new Joiner("") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        return new Tuple2Impl(right /* : unknown */, left /* : unknown */) /* : Tuple2Impl */;
    }) /* : Option<R> */
};
/* private static */ parseBlockHeader(state, CompileState, input, string, depth, number);
[CompileState, BlockHeader];
{
    let stripped = input /* : unknown */.strip /* : unknown */() /* : unknown */;
    return /* parseConditional */ (state /* : CompileState */, stripped /* : unknown */, "if", depth /* : number */) /* : unknown */.or /* : unknown */(() => /* parseConditional */ (state /* : CompileState */, stripped /* : unknown */, "while", depth /* : number */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseElse */ (state /* : CompileState */, input /* : unknown */) /* : unknown */) /* : unknown */.orElseGet /* : unknown */(() => new Tuple2Impl(state /* : CompileState */, new Placeholder(stripped /* : unknown */) /* : Placeholder */) /* : Tuple2Impl */) /* : unknown */;
}
/* private static */ parseElse(state, CompileState, input, string);
Option < [CompileState, BlockHeader] > {
    let, stripped = input /* : unknown */.strip /* : unknown */() /* : unknown */,
    if(stripped) { } /* : unknown */, /* : unknown */ : /* : unknown */ .equals /* : unknown */("else") /* : unknown */
};
{
    return new Some(new Tuple2Impl(state /* : CompileState */, new Else() /* : Else */) /* : Tuple2Impl */) /* : Some */;
}
return new None() /* : None */;
/* private static */ parseConditional(state, CompileState, input, string, prefix, string, depth, number);
Option < [CompileState, BlockHeader] > {
    return: prefix /* : string */(input /* : unknown */, prefix /* : string */, (withoutPrefix) => {
        return prefix /* : string */(withoutPrefix /* : unknown */.strip /* : unknown */() /* : unknown */, "(", (withoutValueStart) => {
            return suffix /* : string */(withoutValueStart /* : unknown */, ")", (value) => {
                let valueTuple = /* parseValue */ (state /* : CompileState */, value /* : unknown */, depth /* : number */) /* : unknown */;
                let value1 = valueTuple /* : unknown */.right /* : unknown */() /* : unknown */;
                return new Some(new Tuple2Impl(valueTuple /* : unknown */.left /* : unknown */() /* : unknown */, new Conditional(prefix /* : string */, value1 /* : unknown */) /* : Conditional */) /* : Tuple2Impl */) /* : Some */;
            }) /* : unknown */;
        }) /* : unknown */;
    }) /* : unknown */
};
/* private static */ foldBlockStart(state, DivideState, c, string);
DivideState;
{
    let appended = state /* : CompileState */.append /* : unknown */(c /* : string */) /* : unknown */;
    if (c /* : string */ ===  /*  '{'  */ && state /* : CompileState */.isLevel /* : unknown */() /* : unknown */) {
        return appended /* : unknown */.advance /* : unknown */() /* : unknown */;
    }
    if (c /* : string */ ===  /*  '{' */) {
        return appended /* : unknown */.enter /* : unknown */() /* : unknown */;
    }
    if (c /* : string */ ===  /*  '}' */) {
        return appended /* : unknown */.exit /* : unknown */() /* : unknown */;
    }
    return appended /* : unknown */;
}
/* private static */ parseStatementValue(state, CompileState, input, string, depth, number);
[CompileState, StatementValue];
{
    let stripped = input /* : unknown */.strip /* : unknown */() /* : unknown */;
    if (stripped /* : unknown */.startsWith /* : unknown */("return ") /* : unknown */) {
        let value = stripped /* : unknown */.substring /* : unknown */("return ".length /* : unknown */() /* : unknown */) /* : unknown */;
        let tuple = /* parseValue */ (state /* : CompileState */, value /* : unknown */, depth /* : number */) /* : unknown */;
        let value1 = tuple /* : unknown */.right /* : unknown */() /* : unknown */;
        return new Tuple2Impl(tuple /* : unknown */.left /* : unknown */() /* : unknown */, new Return(value1 /* : unknown */) /* : Return */) /* : Tuple2Impl */;
    }
    return /* parseAssignment */ (state /* : CompileState */, depth /* : number */, stripped /* : unknown */) /* : unknown */.orElseGet /* : unknown */(() => {
        return new Tuple2Impl(state /* : CompileState */, new Placeholder(stripped /* : unknown */) /* : Placeholder */) /* : Tuple2Impl */;
    }) /* : unknown */;
}
/* private static */ parseAssignment(state, CompileState, depth, number, stripped, string);
Option < [CompileState, StatementValue] > {
    return /* first */(stripped /* : unknown */, ) { }
}(beforeEquals, valueString);
{
    let sourceTuple = /* parseValue */ (state /* : CompileState */, valueString /* : unknown */, depth /* : number */) /* : unknown */;
    let sourceState = sourceTuple /* : unknown */.left /* : unknown */() /* : unknown */;
    let source = sourceTuple /* : unknown */.right /* : unknown */() /* : unknown */;
    return /* parseDefinition */ (sourceState /* : unknown */, beforeEquals /* : unknown */) /* : unknown */.flatMap /* : unknown */((definitionTuple) => /* parseInitialization */ (definitionTuple /* : unknown */.left /* : unknown */() /* : unknown */, definitionTuple /* : unknown */.right /* : unknown */() /* : unknown */, source /* : unknown */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseAssignment */ (depth /* : number */, beforeEquals /* : unknown */, sourceState /* : unknown */, source /* : unknown */) /* : unknown */) /* : unknown */;
}
;
/* private static */ parseAssignment(depth, number, beforeEquals, string, sourceState, CompileState, source, Value);
Option < [CompileState, StatementValue] > {
    let, destinationTuple = (sourceState /* : unknown */, beforeEquals /* : unknown */, depth /* : number */) /* : unknown */,
    let, destinationState = destinationTuple /* : unknown */.left /* : unknown */() /* : unknown */,
    let, destination = destinationTuple /* : unknown */.right /* : unknown */() /* : unknown */,
    return: new Some(new Tuple2Impl(destinationState /* : unknown */, new Assignment(destination /* : unknown */, source /* : unknown */) /* : Assignment */) /* : Tuple2Impl */) /* : Some */
};
/* private static */ parseInitialization(state, CompileState, rawDefinition, Definition, source, Value);
Option < [CompileState, StatementValue] > {
    let, definition: Definition = rawDefinition /* : Definition */.mapType /* : (arg0 : (arg0 : Type) => Type) => Definition */((type) => {
        if (type /* : Type */.equals /* : unknown */(Primitive /* : Primitive */.Unknown /* : unknown */) /* : unknown */) {
            return source /* : unknown */.type /* : unknown */() /* : unknown */;
        }
        else {
            return type /* : Type */;
        }
    }) /* : Definition */,
    return: new Some(new Tuple2Impl(state /* : CompileState */.withDefinition /* : (arg0 : Definition) => CompileState */(definition /* : Definition */) /* : CompileState */, new Initialization(definition /* : Definition */, source /* : unknown */) /* : Initialization */) /* : Tuple2Impl */) /* : Some */
};
/* private static */ parseValue(state, CompileState, input, string, depth, number);
[CompileState, Value];
{
    return /* parseBoolean */ (state /* : CompileState */, input /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseLambda */ (state /* : CompileState */, input /* : unknown */, depth /* : number */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseString */ (state /* : CompileState */, input /* : unknown */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseDataAccess */ (state /* : CompileState */, input /* : unknown */, depth /* : number */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseSymbolValue */ (state /* : CompileState */, input /* : unknown */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseInvokable */ (state /* : CompileState */, input /* : unknown */, depth /* : number */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseDigits */ (state /* : CompileState */, input /* : unknown */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseOperation */ (state /* : CompileState */, input /* : unknown */, depth /* : number */, Operator /* : Operator */.ADD /* : unknown */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseOperation */ (state /* : CompileState */, input /* : unknown */, depth /* : number */, Operator /* : Operator */.EQUALS /* : unknown */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseOperation */ (state /* : CompileState */, input /* : unknown */, depth /* : number */, Operator /* : Operator */.SUBTRACT /* : unknown */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseOperation */ (state /* : CompileState */, input /* : unknown */, depth /* : number */, Operator /* : Operator */.AND /* : unknown */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseOperation */ (state /* : CompileState */, input /* : unknown */, depth /* : number */, Operator /* : Operator */.OR /* : unknown */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseOperation */ (state /* : CompileState */, input /* : unknown */, depth /* : number */,  /*  Operator.GREATER_THAN_OR_EQUALS */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseNot */ (state /* : CompileState */, input /* : unknown */, depth /* : number */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseMethodReference */ (state /* : CompileState */, input /* : unknown */, depth /* : number */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseInstanceOf */ (state /* : CompileState */, input /* : unknown */, depth /* : number */) /* : unknown */) /* : unknown */.orElseGet /* : unknown */(() => new Tuple2Impl(state /* : CompileState */, new Placeholder(input /* : unknown */) /* : Placeholder */) /* : Tuple2Impl<CompileState, Value> */) /* : unknown */;
}
/* private static */ parseBoolean(state, CompileState, input, string);
Option < [CompileState, Value] > {
    let, stripped = input /* : unknown */.strip /* : unknown */() /* : unknown */,
    if(stripped) { } /* : unknown */, /* : unknown */ : /* : unknown */ .equals /* : unknown */("false") /* : unknown */
};
{
    return new Some(new Tuple2Impl(state /* : CompileState */, BooleanValue /* : BooleanValue */.False /* : unknown */) /* : Tuple2Impl */) /* : Some */;
}
if (stripped /* : unknown */.equals /* : unknown */("true") /* : unknown */) {
    return new Some(new Tuple2Impl(state /* : CompileState */, BooleanValue /* : BooleanValue */.True /* : unknown */) /* : Tuple2Impl */) /* : Some */;
}
return new None() /* : None */;
/* private static */ parseInstanceOf(state, CompileState, input, string, depth, number);
Option < [CompileState, Value] > {
    return /* last */(input /* : unknown */, ) { }
}(s, s2);
{
    let childTuple = parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state /* : CompileState */, s /* : unknown */, depth /* : number */) /* : [CompileState, Value] */;
    return /* parseDefinition */ (childTuple /* : [CompileState, Value] */[0 /* : number */]() /* : unknown */, s2 /* : unknown */) /* : unknown */.map /* : unknown */((definitionTuple) => {
        let value = childTuple /* : [CompileState, Value] */[1 /* : number */]() /* : unknown */;
        let definition = definitionTuple /* : unknown */.right /* : unknown */() /* : unknown */;
        let variant = new DataAccess(value /* : unknown */, "_variant", Primitive /* : Primitive */.Unknown /* : unknown */) /* : DataAccess */;
        let type = value /* : unknown */.type /* : unknown */() /* : unknown */;
        let generate = type /* : Type */.findName /* : () => Option<string> */() /* : Option<string> */.orElse /* : (arg0 : string) => T */("") /* : T */;
        let temp = new SymbolValue(generate /* : T */ + "Variant." + definition /* : Definition */.type /* : unknown */() /* : unknown */.findName /* : unknown */() /* : unknown */.orElse /* : unknown */("") /* : unknown */, Primitive /* : Primitive */.Unknown /* : unknown */) /* : SymbolValue */;
        return new Tuple2Impl(definitionTuple /* : unknown */.left /* : unknown */() /* : unknown */, new Operation(variant /* : DataAccess */, Operator /* : Operator */.EQUALS /* : unknown */, temp /* : SymbolValue */) /* : Operation */) /* : Tuple2Impl */;
    }) /* : unknown */;
}
;
/* private static */ parseMethodReference(state, CompileState, input, string, depth, number);
Option < [CompileState, Value] > {
    return /* last */(input /* : unknown */, ) { }
}(s, s2);
{
    let tuple = parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state /* : CompileState */, s /* : unknown */, depth /* : number */) /* : [CompileState, Value] */;
    return new Some(new Tuple2Impl(tuple /* : unknown */.left /* : unknown */() /* : unknown */, new DataAccess(tuple /* : unknown */.right /* : unknown */() /* : unknown */, s2 /* : unknown */, Primitive /* : Primitive */.Unknown /* : unknown */) /* : DataAccess */) /* : Tuple2Impl */) /* : Some */;
}
;
/* private static */ parseNot(state, CompileState, input, string, depth, number);
Option < [CompileState, Value] > {
    let, stripped = input /* : unknown */.strip /* : unknown */() /* : unknown */,
    if(stripped) { } /* : unknown */, /* : unknown */ : /* : unknown */ .startsWith /* : unknown */("!") /* : unknown */
};
{
    let slice = stripped /* : unknown */.substring /* : unknown */(1 /* : number */) /* : unknown */;
    let tuple = parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state /* : CompileState */, slice /* : unknown */, depth /* : number */) /* : [CompileState, Value] */;
    let value = tuple /* : unknown */.right /* : unknown */() /* : unknown */;
    return new Some(new Tuple2Impl(tuple /* : unknown */.left /* : unknown */() /* : unknown */, new Not(value /* : unknown */) /* : Not */) /* : Tuple2Impl */) /* : Some */;
}
return new None() /* : None */;
/* private static */ parseLambda(state, CompileState, input, string, depth, number);
Option < [CompileState, Value] > {
    return /* first */(input /* : unknown */, ) { }
}(beforeArrow, valueString);
{
    let strippedBeforeArrow = beforeArrow /* : unknown */.strip /* : unknown */() /* : unknown */;
    if (isSymbol /* : (arg0 : string) => boolean */(strippedBeforeArrow /* : unknown */) /* : boolean */) {
        let type = Primitive /* : Primitive */.Unknown /* : unknown */;
        if ( /* state.typeRegister instanceof Some */( /* var expectedType */) /* : unknown */) {
            if ( /* expectedType */._variant /* : unknown */ === Variant.FunctionType /* : unknown */) {
                type /* : Type */ = /* functionType */ .arguments /* : unknown */.get /* : unknown */(0 /* : number */) /* : unknown */.orElse /* : unknown */( /* null */) /* : unknown */;
            }
        }
        return /* assembleLambda */ (state /* : CompileState */, Lists /* : Lists */.of /* : (arg0 : T[]) => List<T> */(ImmutableDefinition /* : ImmutableDefinition */.createSimpleDefinition /* : (arg0 : string, arg1 : Type) => Definition */(strippedBeforeArrow /* : unknown */, type /* : Type */) /* : Definition */) /* : List<T> */, valueString /* : unknown */, depth /* : number */) /* : unknown */;
    }
    if (strippedBeforeArrow /* : unknown */.startsWith /* : unknown */("(") /* : unknown */ && strippedBeforeArrow /* : unknown */.endsWith /* : unknown */(")") /* : unknown */) {
        let parameterNames = divideAll /* : (arg0 : string, arg1 : (arg0 : DivideState, arg1 : string) => DivideState) => List<string> */(strippedBeforeArrow /* : unknown */.substring /* : unknown */(1 /* : number */, strippedBeforeArrow /* : unknown */.length /* : unknown */() /* : unknown */ - 1 /* : number */) /* : unknown */, Main /* : Main */.foldValueChar /* : unknown */) /* : List<string> */.iterate /* : () => Iterator<T> */() /* : Iterator<T> */.map /* : (arg0 : (arg0 : T) => R) => Iterator<R> */(strip /* : unknown */) /* : Iterator<R> */.filter /* : unknown */((value) => !value /* : unknown */.isEmpty /* : unknown */() /* : unknown */) /* : unknown */.map /* : unknown */((name) => ImmutableDefinition /* : ImmutableDefinition */.createSimpleDefinition /* : (arg0 : string, arg1 : Type) => Definition */(name /* : unknown */, Primitive /* : Primitive */.Unknown /* : unknown */) /* : Definition */) /* : unknown */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */;
        return /* assembleLambda */ (state /* : CompileState */, parameterNames /* : unknown */, valueString /* : unknown */, depth /* : number */) /* : unknown */;
    }
    return new None() /* : None */;
}
;
/* private static */ assembleLambda(state, CompileState, definitions, (List), valueString, string, depth, number);
Some < [CompileState, Value] > {
    let, strippedValueString = valueString /* : unknown */.strip /* : unknown */() /* : unknown */,
    let, state2: CompileState = state /* : CompileState */.withDefinitions /* : (arg0 : List<Definition>) => CompileState */(definitions /* : unknown */) /* : CompileState */,
    if(strippedValueString) { } /* : unknown */, /* : unknown */ : /* : unknown */ .startsWith /* : unknown */("{") /* : unknown */ && strippedValueString /* : unknown */.endsWith /* : unknown */("}") /* : unknown */
};
{
    let value1 = parseStatements /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => [CompileState, T]) => [CompileState, List<T>] */(state2 /* : unknown */, strippedValueString /* : unknown */.substring /* : unknown */(1 /* : number */, strippedValueString /* : unknown */.length /* : unknown */() /* : unknown */ - 1 /* : number */) /* : unknown */, (state1, input1) => parseFunctionSegment /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, FunctionSegment] */(state1 /* : unknown */, input1 /* : unknown */, depth /* : number */ + 1 /* : number */) /* : [CompileState, FunctionSegment] */) /* : [CompileState, List<T>] */;
    let right = value1 /* : unknown */.right /* : unknown */() /* : unknown */;
    value /* : unknown */ = new Tuple2Impl(value1 /* : unknown */.left /* : unknown */() /* : unknown */, new BlockLambdaValue(depth /* : number */, right /* : unknown */) /* : BlockLambdaValue */) /* : Tuple2Impl */;
}
{
    let value1 = parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state2 /* : unknown */, strippedValueString /* : unknown */, depth /* : number */) /* : [CompileState, Value] */;
    value /* : unknown */ = new Tuple2Impl(value1 /* : unknown */.left /* : unknown */() /* : unknown */, value1 /* : unknown */.right /* : unknown */() /* : unknown */) /* : Tuple2Impl */;
}
let right = value /* : unknown */.right /* : unknown */() /* : unknown */;
return new Some(new Tuple2Impl(value /* : unknown */.left /* : unknown */() /* : unknown */, new Lambda(definitions /* : unknown */, right /* : unknown */) /* : Lambda */) /* : Tuple2Impl */) /* : Some */;
/* private static */ parseDigits(state, CompileState, input, string);
Option < [CompileState, Value] > {
    let, stripped = input /* : unknown */.strip /* : unknown */() /* : unknown */,
    if() { }
} /* isNumber */(stripped /* : unknown */); /* : unknown */
{
    return new Some(new Tuple2Impl(state /* : CompileState */, new SymbolValue(stripped /* : unknown */, Primitive /* : Primitive */.Int /* : unknown */) /* : SymbolValue */) /* : Tuple2Impl<CompileState, Value> */) /* : Some */;
}
return new None() /* : None */;
/* private static */ isNumber(input, string);
boolean;
{
    /* String maybeTruncated */ ;
    if (input /* : unknown */.startsWith /* : unknown */("-") /* : unknown */) {
        input /* : unknown */.substring /* : unknown */(1 /* : number */) /* : unknown */;
    }
    else {
        input /* : unknown */;
    }
    return /* areAllDigits */ ( /* maybeTruncated */) /* : unknown */;
}
/* private static */ areAllDigits(input, string);
boolean;
{
    /* for (var i = 0; i < input.length(); i++) */ {
        let c = input /* : unknown */.charAt /* : unknown */( /* i */) /* : unknown */;
        if ( /* Character */.isDigit /* : unknown */(c /* : string */) /* : unknown */) {
            /* continue */ ;
        }
        return false;
    }
    return true;
}
/* private static */ parseInvokable(state, CompileState, input, string, depth, number);
Option < [CompileState, Value] > {
    return: suffix /* : string */(input /* : unknown */.strip /* : unknown */() /* : unknown */, ")", (withoutEnd) => {
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
        let expectedType = callerType /* : unknown */.arguments /* : unknown */.get /* : unknown */(index /* : unknown */) /* : unknown */.orElse /* : unknown */(Primitive /* : Primitive */.Unknown /* : unknown */) /* : unknown */;
        let withExpected = currentState /* : unknown */.withExpectedType /* : unknown */(expectedType /* : unknown */) /* : unknown */;
        let valueTuple = /* parseArgument */ (withExpected /* : unknown */, element /* : unknown */, depth /* : number */) /* : unknown */;
        let valueState = valueTuple /* : unknown */.left /* : unknown */() /* : unknown */;
        let value = valueTuple /* : unknown */.right /* : unknown */() /* : unknown */;
        let actualType = valueTuple /* : unknown */.left /* : unknown */() /* : unknown */.typeRegister /* : unknown */.orElse /* : unknown */(Primitive /* : Primitive */.Unknown /* : unknown */) /* : unknown */;
        return new Some(new Tuple2Impl(valueState /* : unknown */, new Tuple2Impl(value /* : unknown */, actualType /* : unknown */) /* : Tuple2Impl */) /* : Tuple2Impl */) /* : Some */;
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
    return new Some(value /* : unknown */) /* : Some */;
}
return new None() /* : None */;
/* private static */ parseArgument(state, CompileState, element, string, depth, number);
[CompileState, Argument];
{
    if (element /* : unknown */.isEmpty /* : unknown */() /* : unknown */) {
        return new Tuple2Impl(state /* : CompileState */, new Whitespace() /* : Whitespace */) /* : Tuple2Impl */;
    }
    let tuple = parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state /* : CompileState */, element /* : unknown */, depth /* : number */) /* : [CompileState, Value] */;
    return new Tuple2Impl(tuple /* : unknown */.left /* : unknown */() /* : unknown */, tuple /* : unknown */.right /* : unknown */() /* : unknown */) /* : Tuple2Impl */;
}
/* private static */ findCallerType(newCaller, Caller);
FunctionType;
{
    let callerType = new FunctionType(Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */, Primitive /* : Primitive */.Unknown /* : unknown */) /* : FunctionType */;
    /* switch (newCaller) */ {
        /* case ConstructionCaller constructionCaller -> */ {
            callerType /* : unknown */ = /* constructionCaller */ .toFunction /* : unknown */() /* : unknown */;
        }
        /* case Value value -> */ {
            let type = value /* : unknown */.type /* : unknown */() /* : unknown */;
            if (type /* : Type */._variant /* : unknown */ === TypeVariant.FunctionType /* : unknown */) {
                callerType /* : unknown */ =  /* functionType */;
            }
        }
    }
    return callerType /* : unknown */;
}
/* private static */ modifyCaller(state, CompileState, oldCaller, Caller);
Caller;
{
    if (oldCaller /* : unknown */._variant /* : unknown */ === Variant.DataAccess /* : unknown */) {
        let type = /* resolveType */ ( /* access */.parent /* : unknown */, state /* : CompileState */) /* : unknown */;
        if ( /* type instanceof FunctionType */) {
            return /* access */ .parent /* : unknown */;
        }
    }
    return oldCaller /* : unknown */;
}
/* private static */ resolveType(value, Value, state, CompileState);
Type;
{
    return value /* : unknown */.type /* : unknown */() /* : unknown */;
}
/* private static */ invocationHeader(state, CompileState, depth, number, callerString1, string);
[CompileState, Caller];
{
    if (callerString1 /* : string */.startsWith /* : unknown */("new ") /* : unknown */) {
        let input1 = callerString1 /* : string */.substring /* : unknown */("new ".length /* : unknown */() /* : unknown */) /* : unknown */;
        let map = /* parseType */ (state /* : CompileState */, input1 /* : unknown */) /* : unknown */.map /* : unknown */((type) => {
            let right = type /* : Type */.right /* : unknown */() /* : unknown */;
            return new Tuple2Impl(type /* : Type */.left /* : unknown */() /* : unknown */, new ConstructionCaller(right /* : unknown */) /* : ConstructionCaller */) /* : Tuple2Impl<CompileState, Caller> */;
        }) /* : unknown */;
        if (map /* : unknown */.isPresent /* : unknown */() /* : unknown */) {
            return map /* : unknown */.orElse /* : unknown */( /* null */) /* : unknown */;
        }
    }
    let tuple = parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state /* : CompileState */, callerString1 /* : string */, depth /* : number */) /* : [CompileState, Value] */;
    return new Tuple2Impl(tuple /* : unknown */.left /* : unknown */() /* : unknown */, tuple /* : unknown */.right /* : unknown */() /* : unknown */) /* : Tuple2Impl */;
}
/* private static */ foldInvocationStart(state, DivideState, c, string);
DivideState;
{
    let appended = state /* : CompileState */.append /* : unknown */(c /* : string */) /* : unknown */;
    if (c /* : string */ ===  /*  '(' */) {
        let enter = appended /* : unknown */.enter /* : unknown */() /* : unknown */;
        if (enter /* : unknown */.isShallow /* : unknown */() /* : unknown */) {
            return enter /* : unknown */.advance /* : unknown */() /* : unknown */;
        }
        return enter /* : unknown */;
    }
    if (c /* : string */ ===  /*  ')' */) {
        return appended /* : unknown */.exit /* : unknown */() /* : unknown */;
    }
    return appended /* : unknown */;
}
/* private static */ parseDataAccess(state, CompileState, input, string, depth, number);
Option < [CompileState, Value] > {
    return /* last */(input) { } /* : unknown */, /* : unknown */ : /* : unknown */ .strip /* : unknown */() /* : unknown */, ".": ,
}(parentString, rawProperty);
{
    let property = rawProperty /* : unknown */.strip /* : unknown */() /* : unknown */;
    if (!isSymbol /* : (arg0 : string) => boolean */(property /* : unknown */) /* : unknown */) {
        return new None() /* : None */;
    }
    let tuple = parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state /* : CompileState */, parentString /* : unknown */, depth /* : number */) /* : [CompileState, Value] */;
    let parent = tuple /* : unknown */.right /* : unknown */() /* : unknown */;
    let parentType = parent /* : unknown */.type /* : unknown */() /* : unknown */;
    if ( /* parentType instanceof TupleType */) {
        if (property /* : unknown */.equals /* : unknown */("left") /* : unknown */) {
            return new Some(new Tuple2Impl(state /* : CompileState */, new IndexValue(parent /* : unknown */, new SymbolValue("0", Primitive /* : Primitive */.Int /* : unknown */) /* : SymbolValue */) /* : IndexValue */) /* : Tuple2Impl */) /* : Some */;
        }
        if (property /* : unknown */.equals /* : unknown */("right") /* : unknown */) {
            return new Some(new Tuple2Impl(state /* : CompileState */, new IndexValue(parent /* : unknown */, new SymbolValue("1", Primitive /* : Primitive */.Int /* : unknown */) /* : SymbolValue */) /* : IndexValue */) /* : Tuple2Impl */) /* : Some */;
        }
    }
    let type = Primitive /* : Primitive */.Unknown /* : unknown */;
    if (parentType /* : unknown */._variant /* : unknown */ === Variant.FindableType /* : unknown */) {
        if ( /* objectType.find(property) instanceof Some */( /* var memberType */) /* : unknown */) {
            type /* : Type */ =  /* memberType */;
        }
    }
    return new Some(new Tuple2Impl(tuple /* : unknown */.left /* : unknown */() /* : unknown */, new DataAccess(parent /* : unknown */, property /* : unknown */, type /* : Type */) /* : DataAccess */) /* : Tuple2Impl */) /* : Some */;
}
;
/* private static */ parseString(state, CompileState, input, string);
Option < [CompileState, Value] > {
    let, stripped = input /* : unknown */.strip /* : unknown */() /* : unknown */,
    if(stripped) { } /* : unknown */, /* : unknown */ : /* : unknown */ .startsWith /* : unknown */("\"") /* : unknown */ && stripped /* : unknown */.endsWith /* : unknown */("\"") /* : unknown */
};
{
    return new Some(new Tuple2Impl(state /* : CompileState */, new StringValue(stripped /* : unknown */) /* : StringValue */) /* : Tuple2Impl */) /* : Some */;
}
return new None() /* : None */;
/* private static */ parseSymbolValue(state, CompileState, value, string);
Option < [CompileState, Value] > {
    let, stripped = value /* : unknown */.strip /* : unknown */() /* : unknown */,
    if(isSymbol) { }
} /* : (arg0 : string) => boolean */(stripped /* : unknown */); /* : boolean */
{
    if ( /* state.resolveValue(stripped) instanceof Some */( /* var type */) /* : unknown */) {
        return new Some(new Tuple2Impl(state /* : CompileState */, new SymbolValue(stripped /* : unknown */, type /* : Type */) /* : SymbolValue */) /* : Tuple2Impl */) /* : Some */;
    }
    if ( /* state.resolveType(stripped) instanceof Some */( /* var type */) /* : unknown */) {
        return new Some(new Tuple2Impl(state /* : CompileState */, new SymbolValue(stripped /* : unknown */, type /* : Type */) /* : SymbolValue */) /* : Tuple2Impl */) /* : Some */;
    }
    return new Some(new Tuple2Impl(state /* : CompileState */, new Placeholder(stripped /* : unknown */) /* : Placeholder */) /* : Tuple2Impl */) /* : Some */;
}
return new None() /* : None */;
/* private static */ parseOperation(state, CompileState, value, string, depth, number, operator);
Option < [CompileState, Value] > {
    return /* first */(value /* : unknown */, operator) { } /* : content-start Operator content-end */, /* : content-start Operator content-end */ : /* : content-start Operator content-end */ .sourceRepresentation /* : unknown */,
}(leftString, rightString);
{
    let leftTuple = parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state /* : CompileState */, leftString /* : unknown */, depth /* : number */) /* : [CompileState, Value] */;
    let rightTuple = parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(leftTuple /* : [CompileState, Value] */[0 /* : number */]() /* : unknown */, rightString /* : unknown */, depth /* : number */) /* : [CompileState, Value] */;
    let left = leftTuple /* : [CompileState, Value] */[1 /* : number */]() /* : unknown */;
    let right = rightTuple /* : [CompileState, Value] */[1 /* : number */]() /* : unknown */;
    return new Some(new Tuple2Impl(rightTuple /* : [CompileState, Value] */[0 /* : number */]() /* : unknown */, new Operation(left /* : unknown */, operator /* : content-start Operator content-end */, right /* : unknown */) /* : Operation */) /* : Tuple2Impl */) /* : Some */;
}
;
/* private static  */ parseValuesOrEmpty(state, CompileState, input, string, mapper, (arg0, arg1) => Option);
[CompileState, (List)];
{
    return /* parseValues */ (state /* : CompileState */, input /* : unknown */, mapper /* : (arg0 : CompileState, arg1 : string) => [CompileState, T] */) /* : unknown */.orElseGet /* : unknown */(() => new Tuple2Impl(state /* : CompileState */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */) /* : Tuple2Impl */) /* : unknown */;
}
/* private static  */ parseValues(state, CompileState, input, string, mapper, (arg0, arg1) => Option);
Option < [CompileState, (List)] > {
    return /* parseValuesWithIndices */(state /* : CompileState */, input /* : unknown */) { }
}(state1, tuple);
mapper /* : (arg0 : CompileState, arg1 : string) => [CompileState, T] */(state1 /* : unknown */, tuple /* : unknown */.right /* : unknown */() /* : unknown */); /* : [CompileState, T] */
;
/* private static  */ parseValuesWithIndices(state, CompileState, input, string, mapper, (arg0, arg1) => Option);
Option < [CompileState, (List)] > {
    return: parseAllWithIndices /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : DivideState, arg1 : string) => DivideState, arg3 : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]>) => Option<[CompileState, List<T>]> */(state /* : CompileState */, input /* : unknown */, Main /* : Main */.foldValueChar /* : unknown */, mapper /* : (arg0 : CompileState, arg1 : string) => [CompileState, T] */) /* : Option<[CompileState, List<T>]> */
};
/* private static */ parseParameter(state, CompileState, input, string);
[CompileState, Parameter];
{
    if (input /* : unknown */.isBlank /* : unknown */() /* : unknown */) {
        return new Tuple2Impl(state /* : CompileState */, new Whitespace() /* : Whitespace */) /* : Tuple2Impl */;
    }
    return /* parseDefinition */ (state /* : CompileState */, input /* : unknown */) /* : unknown */.map /* : unknown */((tuple) => new Tuple2Impl(tuple /* : unknown */.left /* : unknown */() /* : unknown */, tuple /* : unknown */.right /* : unknown */() /* : unknown */) /* : Tuple2Impl<CompileState, Parameter> */) /* : unknown */.orElseGet /* : unknown */(() => new Tuple2Impl(state /* : CompileState */, new Placeholder(input /* : unknown */) /* : Placeholder */) /* : Tuple2Impl */) /* : unknown */;
}
/* private static */ createIndent(depth, number);
string;
{
    return "\n" + "\t".repeat /* : unknown */(depth /* : number */) /* : unknown */;
}
/* private static */ parseDefinitionStatement(input, string, depth, number, state, CompileState);
Option < [CompileState, IncompleteClassSegmentWrapper] > {
    return: suffix /* : string */(input /* : unknown */.strip /* : unknown */() /* : unknown */, ";", (withoutEnd) => {
        return /* parseDefinition */ (state /* : CompileState */, withoutEnd /* : unknown */) /* : unknown */.map /* : unknown */((result) => {
            let definition = result /* : unknown */.right /* : unknown */() /* : unknown */;
            return new Tuple2Impl(result /* : unknown */.left /* : unknown */() /* : unknown */, new IncompleteClassSegmentWrapper(new DefinitionStatement(depth /* : number */, definition /* : Definition */) /* : DefinitionStatement */) /* : IncompleteClassSegmentWrapper */) /* : Tuple2Impl */;
        }) /* : unknown */;
    }) /* : unknown */
};
/* private static */ parseDefinition(state, CompileState, input, string);
Option < [CompileState, Definition] > {
    return /* last */(input) { } /* : unknown */, /* : unknown */ : /* : unknown */ .strip /* : unknown */() /* : unknown */, " ": ,
}(beforeName, name);
{
    return /* split */ (() => /* toLast */ (beforeName /* : unknown */, " ", Main /* : Main */.foldTypeSeparator /* : unknown */) /* : unknown */, (beforeType, type) => {
        return suffix /* : string */(beforeType /* : unknown */.strip /* : unknown */() /* : unknown */, ">", (withoutTypeParamStart) => {
            return /* first */ (withoutTypeParamStart /* : unknown */, "<", (beforeTypeParams, typeParamsString) => {
                let typeParams = parseValuesOrEmpty /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) => [CompileState, List<T>] */(state /* : CompileState */, typeParamsString /* : unknown */, (state1, s) => new Some(new Tuple2Impl(state1 /* : unknown */, s /* : unknown */.strip /* : unknown */() /* : unknown */) /* : Tuple2Impl */) /* : Some */) /* : [CompileState, List<T>] */;
                return /* assembleDefinition */ (typeParams /* : unknown */.left /* : unknown */() /* : unknown */, new Some(beforeTypeParams /* : unknown */) /* : Some<string> */, name /* : unknown */, typeParams /* : unknown */.right /* : unknown */() /* : unknown */, type /* : Type */) /* : unknown */;
            }) /* : unknown */;
        }) /* : unknown */.or /* : unknown */(() => {
            return /* assembleDefinition */ (state /* : CompileState */, new Some(beforeType /* : unknown */) /* : Some<string> */, name /* : unknown */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */, type /* : Type */) /* : unknown */;
        }) /* : unknown */;
    }) /* : unknown */.or /* : unknown */(() => {
        return /* assembleDefinition */ (state /* : CompileState */, new None() /* : None<string> */, name /* : unknown */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */, beforeName /* : unknown */) /* : unknown */;
    }) /* : unknown */;
}
;
/* private static */ toLast(input, string, separator, string, folder, (arg0, arg1) => DivideState);
Option < [string, string] > {
    let, divisions: (List) = divideAll /* : (arg0 : string, arg1 : (arg0 : DivideState, arg1 : string) => DivideState) => List<string> */(input /* : unknown */, folder /* : (arg0 : DivideState, arg1 : string) => DivideState */) /* : List<string> */,
    return: divisions /* : List<string> */.removeLast /* : () => Option<[List<T>, T]> */() /* : Option<[List<T>, T]> */.map /* : (arg0 : (arg0 : [List<T>, T]) => R) => Option<R> */((removed) => {
        let left = removed /* : [T, List<T>] */[0 /* : number */]() /* : unknown */.iterate /* : unknown */() /* : unknown */.collect /* : unknown */(new Joiner(separator /* : string */) /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        let right = removed /* : [T, List<T>] */[1 /* : number */]() /* : unknown */;
        return new Tuple2Impl(left /* : unknown */, right /* : unknown */) /* : Tuple2Impl */;
    }) /* : Option<R> */
};
/* private static */ foldTypeSeparator(state, DivideState, c, string);
DivideState;
{
    if (c /* : string */ ===  /*  ' '  */ && state /* : CompileState */.isLevel /* : unknown */() /* : unknown */) {
        return state /* : CompileState */.advance /* : unknown */() /* : unknown */;
    }
    let appended = state /* : CompileState */.append /* : unknown */(c /* : string */) /* : unknown */;
    if (c /* : string */ ===  /*  '<' */) {
        return appended /* : unknown */.enter /* : unknown */() /* : unknown */;
    }
    if (c /* : string */ ===  /*  '>' */) {
        return appended /* : unknown */.exit /* : unknown */() /* : unknown */;
    }
    return appended /* : unknown */;
}
/* private static */ assembleDefinition(state, CompileState, beforeTypeParams, (Option), name, string, typeParams, (List), type, string);
Option < [CompileState, Definition] > {
    return /* parseType */(state) { } /* : CompileState */, /* : CompileState */ : /* : CompileState */ .withTypeParams /* : (arg0 : List<string>) => CompileState */(typeParams /* : unknown */) /* : CompileState */, type /* : Type */, /* : unknown */ : /* : unknown */ .map /* : unknown */((type1) => {
        let node = new ImmutableDefinition(beforeTypeParams /* : unknown */, name /* : unknown */.strip /* : unknown */() /* : unknown */, type1 /* : unknown */.right /* : unknown */() /* : unknown */, typeParams /* : unknown */) /* : ImmutableDefinition */;
        return new Tuple2Impl(type1 /* : unknown */.left /* : unknown */() /* : unknown */, node /* : ImmutableDefinition */) /* : Tuple2Impl */;
    }) /* : unknown */
};
/* private static */ foldValueChar(state, DivideState, c, string);
DivideState;
{
    if (c /* : string */ ===  /*  ','  */ && state /* : CompileState */.isLevel /* : unknown */() /* : unknown */) {
        return state /* : CompileState */.advance /* : unknown */() /* : unknown */;
    }
    let appended = state /* : CompileState */.append /* : unknown */(c /* : string */) /* : unknown */;
    if (c /* : string */ === /*  ' */ - /* ' */) {
        let peeked = appended /* : unknown */.peek /* : unknown */() /* : unknown */;
        if (peeked /* : unknown */ ===  /*  '>' */) {
            return appended /* : unknown */.popAndAppendToOption /* : unknown */() /* : unknown */.orElse /* : unknown */(appended /* : unknown */) /* : unknown */;
        }
        else {
            return appended /* : unknown */;
        }
    }
    if (c /* : string */ ===  /*  '<'  */ || c /* : string */ ===  /*  '('  */ || c /* : string */ ===  /*  '{' */) {
        return appended /* : unknown */.enter /* : unknown */() /* : unknown */;
    }
    if (c /* : string */ ===  /*  '>'  */ || c /* : string */ ===  /*  ')'  */ || c /* : string */ ===  /*  '}' */) {
        return appended /* : unknown */.exit /* : unknown */() /* : unknown */;
    }
    return appended /* : unknown */;
}
/* private static */ parseType(state, CompileState, input, string);
Option < [CompileState, Type] > {
    let, stripped = input /* : unknown */.strip /* : unknown */() /* : unknown */,
    if(stripped) { } /* : unknown */, /* : unknown */ : /* : unknown */ .equals /* : unknown */("int") /* : unknown */ || stripped /* : unknown */.equals /* : unknown */("Integer") /* : unknown */
};
{
    return new Some(new Tuple2Impl(state /* : CompileState */, Primitive /* : Primitive */.Int /* : unknown */) /* : Tuple2Impl */) /* : Some */;
}
if (stripped /* : unknown */.equals /* : unknown */("String") /* : unknown */ || stripped /* : unknown */.equals /* : unknown */("char") /* : unknown */ || stripped /* : unknown */.equals /* : unknown */("Character") /* : unknown */) {
    return new Some(new Tuple2Impl(state /* : CompileState */, Primitive /* : Primitive */.String /* : unknown */) /* : Tuple2Impl */) /* : Some */;
}
if (stripped /* : unknown */.equals /* : unknown */("var") /* : unknown */) {
    return new Some(new Tuple2Impl(state /* : CompileState */, Primitive /* : Primitive */.Unknown /* : unknown */) /* : Tuple2Impl */) /* : Some */;
}
if (stripped /* : unknown */.equals /* : unknown */("boolean") /* : unknown */) {
    return new Some(new Tuple2Impl(state /* : CompileState */, Primitive /* : Primitive */.Boolean /* : unknown */) /* : Tuple2Impl */) /* : Some */;
}
if (isSymbol /* : (arg0 : string) => boolean */(stripped /* : unknown */) /* : boolean */) {
    if ( /* state.resolveType(stripped) instanceof Some */( /* var resolved */) /* : unknown */) {
        return new Some(new Tuple2Impl(state /* : CompileState */) /* : Tuple2Impl */) /* : Some */;
    }
    else {
        return new Some(new Tuple2Impl(state /* : CompileState */, new Placeholder(stripped /* : unknown */) /* : Placeholder */) /* : Tuple2Impl */) /* : Some */;
    }
}
return /* parseTemplate */ (state /* : CompileState */, input /* : unknown */) /* : unknown */.or /* : unknown */(() => /* varArgs */ (state /* : CompileState */, input /* : unknown */) /* : unknown */) /* : unknown */;
/* private static */ varArgs(state, CompileState, input, string);
Option < [CompileState, Type] > {
    return: suffix /* : string */(input /* : unknown */, "...", (s) => {
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
        return new Tuple2Impl(state /* : CompileState */, new FunctionType(Lists /* : Lists */.of /* : (arg0 : T[]) => List<T> */(children /* : unknown */.get /* : unknown */(0 /* : number */) /* : unknown */.orElse /* : unknown */( /* null */) /* : unknown */, children /* : unknown */.get /* : unknown */(1 /* : number */) /* : unknown */.orElse /* : unknown */( /* null */) /* : unknown */) /* : List<T> */, children /* : unknown */.get /* : unknown */(2 /* : number */) /* : unknown */.orElse /* : unknown */( /* null */) /* : unknown */) /* : FunctionType */) /* : Tuple2Impl */;
    }
    if (base /* : string */.equals /* : unknown */("Function") /* : unknown */) {
        return new Tuple2Impl(state /* : CompileState */, new FunctionType(Lists /* : Lists */.of /* : (arg0 : T[]) => List<T> */(children /* : unknown */.get /* : unknown */(0 /* : number */) /* : unknown */.orElse /* : unknown */( /* null */) /* : unknown */) /* : List<T> */, children /* : unknown */.get /* : unknown */(1 /* : number */) /* : unknown */.orElse /* : unknown */( /* null */) /* : unknown */) /* : FunctionType */) /* : Tuple2Impl */;
    }
    if (base /* : string */.equals /* : unknown */("Predicate") /* : unknown */) {
        return new Tuple2Impl(state /* : CompileState */, new FunctionType(Lists /* : Lists */.of /* : (arg0 : T[]) => List<T> */(children /* : unknown */.get /* : unknown */(0 /* : number */) /* : unknown */.orElse /* : unknown */( /* null */) /* : unknown */) /* : List<T> */, Primitive /* : Primitive */.Boolean /* : unknown */) /* : FunctionType */) /* : Tuple2Impl */;
    }
    if (base /* : string */.equals /* : unknown */("Supplier") /* : unknown */) {
        return new Tuple2Impl(state /* : CompileState */, new FunctionType(Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */, children /* : unknown */.get /* : unknown */(0 /* : number */) /* : unknown */.orElse /* : unknown */( /* null */) /* : unknown */) /* : FunctionType */) /* : Tuple2Impl */;
    }
    if (base /* : string */.equals /* : unknown */("Tuple2") /* : unknown */ && children /* : unknown */.size /* : unknown */() /* : unknown */ >= 2 /* : number */) {
        return new Tuple2Impl(state /* : CompileState */, new TupleType(children /* : unknown */) /* : TupleType */) /* : Tuple2Impl */;
    }
    if (state /* : CompileState */.resolveType /* : (arg0 : string) => Option<Type> */(base /* : string */) /* : Option<Type> */._variant /* : unknown */ === OptionVariant.Some /* : unknown */) {
        let baseType = /* some */ .value /* : unknown */;
        if (baseType /* : unknown */._variant /* : unknown */ === Variant.FindableType /* : unknown */) {
            return new Tuple2Impl(state /* : CompileState */, new Template(children /* : unknown */) /* : Template */) /* : Tuple2Impl */;
        }
    }
    return new Tuple2Impl(state /* : CompileState */, new Template(new Placeholder(base /* : string */) /* : Placeholder */, children /* : unknown */) /* : Template */) /* : Tuple2Impl */;
}
/* private static */ parseTemplate(state, CompileState, input, string);
Option < [CompileState, Type] > {
    return: suffix /* : string */(input /* : unknown */.strip /* : unknown */() /* : unknown */, ">", (withoutEnd) => {
        return /* first */ (withoutEnd /* : unknown */, "<", (base, argumentsString) => {
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
    return new Some(type /* : Type */) /* : Some */;
}
{
    return new None() /* : None<Type> */;
}
/* private static */ argument(state, CompileState, input, string);
Option < [CompileState, Argument] > {
    if(input) { } /* : unknown */, /* : unknown */ : /* : unknown */ .isBlank /* : unknown */() /* : unknown */
};
{
    return new Some(new Tuple2Impl(state /* : CompileState */, new Whitespace() /* : Whitespace */) /* : Tuple2Impl */) /* : Some */;
}
return parseType /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Type]> */(state /* : CompileState */, input /* : unknown */) /* : Option<[CompileState, Type]> */.map /* : (arg0 : (arg0 : [CompileState, Type]) => R) => Option<R> */((tuple) => new Tuple2Impl(tuple /* : unknown */.left /* : unknown */() /* : unknown */, tuple /* : unknown */.right /* : unknown */() /* : unknown */) /* : Tuple2Impl */) /* : Option<R> */;
/* private static  */ last(input, string, infix, string, mapper, (arg0, arg1) => Option);
Option < T > {
    return: infix /* : string */(input /* : unknown */, infix /* : string */, Main /* : Main */.findLast /* : unknown */, mapper /* : (arg0 : CompileState, arg1 : string) => [CompileState, T] */) /* : unknown */
};
/* private static */ findLast(input, string, infix, string);
Option < number > {
    let, index = input /* : unknown */.lastIndexOf /* : unknown */(infix /* : string */) /* : unknown */,
    if(index) { }
} /* : unknown */ === -1; /* : number */
{
    return new None() /* : None<number> */;
}
return new Some(index /* : unknown */) /* : Some */;
/* private static  */ first(input, string, infix, string, mapper, (arg0, arg1) => Option);
Option < T > {
    return: infix /* : string */(input /* : unknown */, infix /* : string */, Main /* : Main */.findFirst /* : unknown */, mapper /* : (arg0 : CompileState, arg1 : string) => [CompileState, T] */) /* : unknown */
};
/* private static  */ split(splitter, () => Option, splitMapper, (arg0, arg1) => Option);
Option < T > {
    return: splitter /* : () => Option<[string, string]> */() /* : Option<[string, string]> */.flatMap /* : (arg0 : (arg0 : [string, string]) => Option<R>) => Option<R> */((splitTuple) => splitMapper /* : (arg0 : string, arg1 : string) => Option<T> */(splitTuple /* : [string, string] */[0 /* : number */]() /* : unknown */, splitTuple /* : [string, string] */[1 /* : number */]() /* : unknown */) /* : Option<T> */) /* : Option<R> */
};
/* private static  */ infix(input, string, infix, string, locator, (arg0, arg1) => Option, mapper, (arg0, arg1) => Option);
Option < T > {
    return: split /* : (arg0 : () => Option<[string, string]>, arg1 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(() => locator /* : (arg0 : string, arg1 : string) => Option<number> */(input /* : unknown */, infix /* : string */) /* : Option<number> */.map /* : (arg0 : (arg0 : number) => R) => Option<R> */((index) => {
        let left = input /* : unknown */.substring /* : unknown */(0 /* : number */, index /* : unknown */) /* : unknown */;
        let right = input /* : unknown */.substring /* : unknown */(index /* : unknown */ + infix /* : string */.length /* : unknown */() /* : unknown */) /* : unknown */;
        return new Tuple2Impl(left /* : unknown */, right /* : unknown */) /* : Tuple2Impl */;
    }) /* : Option<R> */, mapper /* : (arg0 : CompileState, arg1 : string) => [CompileState, T] */) /* : Option<T> */
};
/* private static */ findFirst(input, string, infix, string);
Option < number > {
    let, index = input /* : unknown */.indexOf /* : unknown */(infix /* : string */) /* : unknown */,
    if(index) { }
} /* : unknown */ === -1; /* : number */
{
    return new None() /* : None<number> */;
}
return new Some(index /* : unknown */) /* : Some */;
/* private static */ generatePlaceholder(input, string);
string;
{
    let replaced = input /* : unknown */.replace /* : unknown */("/*", "content-start") /* : unknown */.replace /* : unknown */("*/", "content-end") /* : unknown */;
    return "/* " + replaced + " */";
}
/* private static */ createDebugString(type, Type);
string;
{
    if (!Main /* : Main */.isDebug /* : unknown */) {
        return "";
    }
    return generatePlaceholder /* : (arg0 : string) => string */(": " + type /* : Type */.generate /* : unknown */() /* : unknown */) /* : string */;
}
/*  */ 
