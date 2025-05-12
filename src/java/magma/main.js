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
        return other /* : () => Option<T> */() /* : Option<T> */;
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
        return new Some(mapper /* : (arg0 : T) => R */(this /* : Some */.value /* : T */) /* : R */) /* : Some */;
    }
    /* @Override
        public */ isPresent() {
        return true;
    }
    /* @Override
        public */ orElse(other) {
        return this /* : Some */.value /* : T */;
    }
    /* @Override
        public */ filter(predicate) {
        if (predicate /* : (arg0 : T) => boolean */(this /* : Some */.value /* : T */) /* : boolean */) {
            return this /* : Some */;
        }
        return new None() /* : None */;
    }
    /* @Override
        public */ orElseGet(supplier) {
        return this /* : Some */.value /* : T */;
    }
    /* @Override
        public */ or(other) {
        return this /* : Some */;
    }
    /* @Override
        public  */ flatMap(mapper) {
        return mapper /* : (arg0 : T) => Option<R> */(this /* : Some */.value /* : T */) /* : Option<R> */;
    }
    /* @Override
        public */ isEmpty() {
        return false;
    }
    /* @Override
        public  */ and(other) {
        return other /* : () => Option<R> */() /* : Option<R> */.map /* : (arg0 : (arg0 : R) => R) => Option<R> */((otherValue) => new Tuple2Impl(this /* : Some */.value /* : T */, otherValue /* : R */) /* : Tuple2Impl */) /* : Option<R> */;
    }
}
/* private static */ class SingleHead {
    constructor(value) {
        this /* : SingleHead */.value /* : T */ = value /* : T */;
        this /* : SingleHead */.retrieved /* : boolean */ = false;
    }
    /* @Override
        public */ next() {
        if (this /* : SingleHead */.retrieved /* : boolean */) {
            return new None() /* : None */;
        }
        this /* : SingleHead */.retrieved /* : boolean */ = true;
        return new Some(this /* : SingleHead */.value /* : T */) /* : Some */;
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
            let option = this /* : HeadedIterator */.head /* : Head<T> */.next /* : () => Option<T> */() /* : Option<T> */.map /* : (arg0 : (arg0 : T) => R) => Option<R> */((inner) => folder /* : (arg0 : R, arg1 : T) => R */(finalCurrent /* : R */, inner /* : T */) /* : R */) /* : Option<R> */;
            if (option /* : Option<R> */._variant /* : unknown */ === OptionVariant.Some /* : unknown */) {
                current /* : R */ = /* some */ .value /* : unknown */;
            }
            else {
                return current /* : R */;
            }
        }
    }
    /* @Override
        public  */ map(mapper) {
        return new HeadedIterator(() => this /* : HeadedIterator */.head /* : Head<T> */.next /* : () => Option<T> */() /* : Option<T> */.map /* : (arg0 : (arg0 : T) => R) => Option<R> */(mapper /* : (arg0 : T) => R */) /* : Option<R> */) /* : HeadedIterator */;
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
        return this /* : HeadedIterator */.head /* : Head<T> */.next /* : () => Option<T> */() /* : Option<T> */;
    }
    /* @Override
        public  */ flatMap(f) {
        return new HeadedIterator(new /* FlatMapHead */ (this /* : HeadedIterator */.head /* : Head<T> */, f /* : (arg0 : T) => Iterator<R> */) /* : content-start FlatMapHead content-end */) /* : HeadedIterator */;
    }
    /* @Override
        public  */ zip(other) {
        return new HeadedIterator(() => HeadedIterator /* : HeadedIterator */.this /* : unknown */.head /* : unknown */.next /* : unknown */() /* : unknown */.and /* : unknown */(other /* : Iterator<R> */.next /* : unknown */) /* : unknown */) /* : HeadedIterator */;
    }
}
/* private static */ class RangeHead /*  */ {
}
this /* : RangeHead */.length /* : number */ = length /* : number */;
/* @Override
    public */ next();
Option < number > {
    if() { } /* this.counter < this */, /* this.counter < this */ : /* this.counter < this */ .length /* : unknown */
};
{
    let value = this /* : RangeHead */.counter /* : number */;
    /* this.counter++ */ ;
    return new Some(value /* : number */) /* : Some */;
}
return new None() /* : None */;
/* private static final */ class JVMList {
}
this /* : JVMList */.elements /* : content-start java.util.List content-end<T> */ = elements /* : content-start java.util.List content-end<T> */;
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
    : /* : JVMList */ .elements /* : content-start java.util.List content-end<T> */.isEmpty /* : unknown */() /* : unknown */
};
{
    return new None() /* : None */;
}
let slice = this /* : JVMList */.elements /* : content-start java.util.List content-end<T> */.subList /* : unknown */(0 /* : number */, this /* : JVMList */.elements /* : content-start java.util.List content-end<T> */.size /* : unknown */() /* : unknown */ - 1 /* : number */) /* : unknown */;
let last = this /* : JVMList */.elements /* : content-start java.util.List content-end<T> */.getLast /* : unknown */() /* : unknown */;
return new Some(new Tuple2Impl(new JVMList(slice /* : unknown */) /* : JVMList */, last /* : unknown */) /* : Tuple2Impl<List<T>, T> */) /* : Some */;
/* @Override
        public */ size();
number;
{
    return this /* : JVMList */.elements /* : content-start java.util.List content-end<T> */.size /* : unknown */() /* : unknown */;
}
/* @Override
        public */ isEmpty();
boolean;
{
    return this /* : JVMList */.elements /* : content-start java.util.List content-end<T> */.isEmpty /* : unknown */() /* : unknown */;
}
/* @Override
        public */ addFirst(element, T);
List < T > {
    return: this /* : JVMList */
};
/* @Override
        public */ iterateWithIndices();
Iterator < [number, T] > {
    return: new HeadedIterator(new RangeHead(this /* : JVMList */.elements /* : content-start java.util.List content-end<T> */.size /* : unknown */() /* : unknown */) /* : RangeHead */) /* : HeadedIterator */.map /* : (arg0 : (arg0 : T) => R) => Iterator<R> */((index) => new Tuple2Impl(index /* : T */, this /* : JVMList */.elements /* : content-start java.util.List content-end<T> */.get /* : unknown */(index /* : T */) /* : unknown */) /* : Tuple2Impl */) /* : Iterator<R> */
};
/* @Override
        public */ removeFirst();
Option < [T, (List)] > {
    : /* : JVMList */ .elements /* : content-start java.util.List content-end<T> */.isEmpty /* : unknown */() /* : unknown */
};
{
    return new None() /* : None */;
}
let first = this /* : JVMList */.elements /* : content-start java.util.List content-end<T> */.getFirst /* : unknown */() /* : unknown */;
let slice = this /* : JVMList */.elements /* : content-start java.util.List content-end<T> */.subList /* : unknown */(1 /* : number */, this /* : JVMList */.elements /* : content-start java.util.List content-end<T> */.size /* : unknown */() /* : unknown */) /* : unknown */;
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
    : /* : JVMList */ .elements /* : content-start java.util.List content-end<T> */.isEmpty /* : unknown */() /* : unknown */
};
{
    return new None() /* : None */;
}
return new Some(this /* : JVMList */.elements /* : content-start java.util.List content-end<T> */.getLast /* : unknown */() /* : unknown */) /* : Some */;
/* @Override
        public */ iterateReversed();
Iterator < T > {
    return: new HeadedIterator(new RangeHead(this /* : JVMList */.elements /* : content-start java.util.List content-end<T> */.size /* : unknown */() /* : unknown */) /* : RangeHead */) /* : HeadedIterator */.map /* : (arg0 : (arg0 : T) => R) => Iterator<R> */((index) => this /* : JVMList */.elements /* : content-start java.util.List content-end<T> */.size /* : unknown */() /* : unknown */ - index /* : T */ - 1 /* : number */) /* : Iterator<R> */.map /* : (arg0 : (arg0 : R) => R) => Iterator<R> */(this /* : JVMList */.elements /* : content-start java.util.List content-end<T> */.get /* : unknown */) /* : Iterator<R> */
};
/* @Override
        public */ mapLast(mapper, (arg0) => T);
List < T > {
    return: this /* : JVMList */.last /* : () => Option<T> */() /* : Option<T> */.map /* : (arg0 : (arg0 : T) => R) => Option<R> */(mapper /* : (arg0 : T) => T */) /* : Option<R> */.map /* : unknown */((newLast) => this /* : JVMList */.set /* : (arg0 : number, arg1 : T) => JVMList<T> */(this /* : JVMList */.elements /* : content-start java.util.List content-end<T> */.size /* : unknown */() /* : unknown */ - 1 /* : number */, newLast /* : unknown */) /* : JVMList<T> */) /* : unknown */.orElse /* : unknown */(this /* : JVMList */) /* : unknown */
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
    return new Some(this /* : JVMList */.elements /* : content-start java.util.List content-end<T> */.get /* : unknown */(index /* : number */) /* : unknown */) /* : Some */;
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
        if (this /* : ImmutableDefinition */.type /* : Type */.equals /* : unknown */(Unknown /* : unknown */) /* : unknown */) {
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
        return this /* : ImmutableDefinition */.typeParams /* : List<string> */.iterate /* : () => Iterator<T> */() /* : Iterator<T> */.collect /* : (arg0 : Collector<T, R>) => R */(new /* Joiner */ () /* : content-start Joiner content-end */) /* : R */.map /* : unknown */((inner) => "<" + inner + ">") /* : unknown */.orElse /* : unknown */("") /* : unknown */;
    }
    /* @Override
        public */ mapType(mapper) {
        return new ImmutableDefinition(this /* : ImmutableDefinition */.maybeBefore /* : Option<string> */, this /* : ImmutableDefinition */.name /* : string */, mapper /* : (arg0 : Type) => Type */(this /* : ImmutableDefinition */.type /* : Type */) /* : Type */, this /* : ImmutableDefinition */.typeParams /* : List<string> */) /* : ImmutableDefinition */;
    }
    /* @Override
        public */ toString() {
        return "Definition[" + "maybeBefore=" + this /* : ImmutableDefinition */.maybeBefore /* : Option<string> */ + ", " + "name=" + this /* : ImmutableDefinition */.name /* : string */ + ", " + "type=" + this /* : ImmutableDefinition */.type /* : Type */ + ", " + "typeParams=" + this /* : ImmutableDefinition */.typeParams /* : List<string> */ +  /*  ']' */;
    }
    /* @Override
        public */ generateWithParams(joinedParameters) {
        let joined = this /* : ImmutableDefinition */.joinTypeParams /* : () => string */() /* : string */;
        let before = this /* : ImmutableDefinition */.joinBefore /* : () => string */() /* : string */;
        let typeString = this /* : ImmutableDefinition */.generateType /* : () => string */() /* : string */;
        return before /* : string */ + this /* : ImmutableDefinition */.name /* : string */ + joined /* : string */ + joinedParameters /* : string */ + typeString /* : string */;
    }
    /* @Override
        public */ createDefinition(paramTypes) {
        return ImmutableDefinition /* : ImmutableDefinition */.createSimpleDefinition /* : unknown */(this /* : ImmutableDefinition */.name /* : string */, new /* FunctionType */ (paramTypes /* : List<Type> */, this /* : ImmutableDefinition */.type /* : Type */) /* : content-start FunctionType content-end */) /* : unknown */;
    }
}
/* private */ class ObjectType /*  */ {
    constructor(name, typeParams, definitions) {
    }
    /* @Override
        public */ generate() {
        return this /* : ObjectType */.name /* : string */;
    }
    /* @Override
        public */ replace(mapping) {
        return new ObjectType(this /* : ObjectType */.name /* : string */, this /* : ObjectType */.typeParams /* : List<string> */, this /* : ObjectType */.definitions /* : List<Definition> */.iterate /* : () => Iterator<T> */() /* : Iterator<T> */.map /* : (arg0 : (arg0 : T) => R) => Iterator<R> */((definition) => definition /* : T */.mapType /* : unknown */((type) => type /* : unknown */.replace /* : unknown */(mapping /* : Map<string, Type> */) /* : unknown */) /* : unknown */) /* : Iterator<R> */.collect /* : unknown */(new /* ListCollector */ () /* : content-start ListCollector content-end */) /* : unknown */) /* : ObjectType */;
    }
    /* @Override
        public */ find(name) {
        return this /* : ObjectType */.definitions /* : List<Definition> */.iterate /* : () => Iterator<T> */() /* : Iterator<T> */.filter /* : (arg0 : (arg0 : T) => boolean) => Iterator<T> */((definition) => definition /* : T */.name /* : unknown */() /* : unknown */.equals /* : unknown */(name /* : string */) /* : unknown */) /* : Iterator<T> */.map /* : unknown */(Definition /* : Definition */.type /* : unknown */) /* : unknown */.next /* : unknown */() /* : unknown */;
    }
    /* @Override
        public */ findName() {
        return new Some(this /* : ObjectType */.name /* : string */) /* : Some */;
    }
}
/* private */ class TypeParam /*  */ {
    constructor(value) {
    }
    /* @Override
        public */ generate() {
        return this /* : TypeParam */.value /* : string */;
    }
    /* @Override
        public */ replace(mapping) {
        return mapping /* : Map<string, Type> */.find /* : (arg0 : string) => Option<V> */(this /* : TypeParam */.value /* : string */) /* : Option<V> */.orElse /* : (arg0 : V) => T */(this /* : TypeParam */) /* : T */;
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
        return this /* : CompileState */.definitions /* : List<List<Definition>> */.iterateReversed /* : () => Iterator<T> */() /* : Iterator<T> */.flatMap /* : (arg0 : (arg0 : T) => Iterator<R>) => Iterator<R> */(List /* : List */.iterate /* : unknown */) /* : Iterator<R> */.filter /* : unknown */((definition) => definition /* : unknown */.name /* : unknown */() /* : unknown */.equals /* : unknown */(name /* : string */) /* : unknown */) /* : unknown */.next /* : unknown */() /* : unknown */.map /* : unknown */(Definition /* : Definition */.type /* : unknown */) /* : unknown */;
    }
    /* public */ addStructure(structure) {
        return new CompileState(this /* : CompileState */.structures /* : List<string> */.addLast /* : (arg0 : string) => List<T> */(structure /* : string */) /* : List<T> */, this /* : CompileState */.definitions /* : List<List<Definition>> */, this /* : CompileState */.objectTypes /* : List<ObjectType> */, this /* : CompileState */.structNames /* : List<string> */, this /* : CompileState */.typeParams /* : List<string> */, this /* : CompileState */.typeRegister /* : Option<Type> */) /* : CompileState */;
    }
    /* public */ defineAll(definitions) {
        let defined = this /* : CompileState */.definitions /* : List<List<Definition>> */.mapLast /* : (arg0 : (arg0 : List<Definition>) => T) => List<T> */((frame) => frame /* : List<Definition> */.addAllLast /* : (arg0 : List<T>) => List<T> */(definitions /* : List<Definition> */) /* : List<T> */) /* : List<T> */;
        return new CompileState(this /* : CompileState */.structures /* : List<string> */, defined /* : List<T> */, this /* : CompileState */.objectTypes /* : List<ObjectType> */, this /* : CompileState */.structNames /* : List<string> */, this /* : CompileState */.typeParams /* : List<string> */, this /* : CompileState */.typeRegister /* : Option<Type> */) /* : CompileState */;
    }
    /* public */ resolveType(name) {
        if (this /* : CompileState */.structNames /* : List<string> */.last /* : () => Option<T> */() /* : Option<T> */.filter /* : (arg0 : (arg0 : T) => boolean) => Option<T> */((inner) => inner /* : T */.equals /* : unknown */(name /* : string */) /* : unknown */) /* : Option<T> */.isPresent /* : unknown */() /* : unknown */) {
            return new Some(new ObjectType(name /* : string */, this /* : CompileState */.typeParams /* : List<string> */, this /* : CompileState */.definitions /* : List<List<Definition>> */.last /* : () => Option<T> */() /* : Option<T> */.orElse /* : (arg0 : T) => T */(Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */) /* : T */) /* : ObjectType */) /* : Some */;
        }
        let maybeTypeParam = this /* : CompileState */.typeParams /* : List<string> */.iterate /* : () => Iterator<T> */() /* : Iterator<T> */.filter /* : (arg0 : (arg0 : T) => boolean) => Iterator<T> */((param) => param /* : T */.equals /* : unknown */(name /* : string */) /* : unknown */) /* : Iterator<T> */.next /* : unknown */() /* : unknown */;
        if ( /* maybeTypeParam instanceof Some */( /* var value */) /* : unknown */) {
            return new Some(new TypeParam( /* value */) /* : TypeParam */) /* : Some */;
        }
        return this /* : CompileState */.objectTypes /* : List<ObjectType> */.iterate /* : () => Iterator<T> */() /* : Iterator<T> */.filter /* : (arg0 : (arg0 : T) => boolean) => Iterator<T> */((type) => type /* : T */.name /* : unknown */.equals /* : unknown */(name /* : string */) /* : unknown */) /* : Iterator<T> */.next /* : unknown */() /* : unknown */.map /* : unknown */((type) => type /* : T */) /* : unknown */;
    }
    /* public */ define(definition) {
        return new CompileState(this /* : CompileState */.structures /* : List<string> */, this /* : CompileState */.definitions /* : List<List<Definition>> */.mapLast /* : (arg0 : (arg0 : List<Definition>) => T) => List<T> */((frame) => frame /* : List<Definition> */.addLast /* : (arg0 : Definition) => List<T> */(definition /* : Definition */) /* : List<T> */) /* : List<T> */, this /* : CompileState */.objectTypes /* : List<ObjectType> */, this /* : CompileState */.structNames /* : List<string> */, this /* : CompileState */.typeParams /* : List<string> */, this /* : CompileState */.typeRegister /* : Option<Type> */) /* : CompileState */;
    }
    /* public */ pushStructName(name) {
        return new CompileState(this /* : CompileState */.structures /* : List<string> */, this /* : CompileState */.definitions /* : List<List<Definition>> */, this /* : CompileState */.objectTypes /* : List<ObjectType> */, this /* : CompileState */.structNames /* : List<string> */.addLast /* : (arg0 : string) => List<T> */(name /* : string */) /* : List<T> */, this /* : CompileState */.typeParams /* : List<string> */, this /* : CompileState */.typeRegister /* : Option<Type> */) /* : CompileState */;
    }
    /* public */ withTypeParams(typeParams) {
        return new CompileState(this /* : CompileState */.structures /* : List<string> */, this /* : CompileState */.definitions /* : List<List<Definition>> */, this /* : CompileState */.objectTypes /* : List<ObjectType> */, this /* : CompileState */.structNames /* : List<string> */, this /* : CompileState */.typeParams /* : List<string> */.addAllLast /* : (arg0 : List<T>) => List<T> */(typeParams /* : List<string> */) /* : List<T> */, this /* : CompileState */.typeRegister /* : Option<Type> */) /* : CompileState */;
    }
    /* public */ withExpectedType(type) {
        return new CompileState(this /* : CompileState */.structures /* : List<string> */, this /* : CompileState */.definitions /* : List<List<Definition>> */, this /* : CompileState */.objectTypes /* : List<ObjectType> */, this /* : CompileState */.structNames /* : List<string> */, this /* : CompileState */.typeParams /* : List<string> */, new Some(type /* : Type */) /* : Some */) /* : CompileState */;
    }
    /* public */ popStructName() {
        return new CompileState(this /* : CompileState */.structures /* : List<string> */, this /* : CompileState */.definitions /* : List<List<Definition>> */, this /* : CompileState */.objectTypes /* : List<ObjectType> */, this /* : CompileState */.structNames /* : List<string> */.removeLast /* : () => Option<[List<T>, T]> */() /* : Option<[List<T>, T]> */.map /* : (arg0 : (arg0 : [List<T>, T]) => R) => Option<R> */(Tuple2 /* : Tuple2 */.left /* : unknown */) /* : Option<R> */.orElse /* : unknown */(this /* : CompileState */.structNames /* : List<string> */) /* : unknown */, this /* : CompileState */.typeParams /* : List<string> */, this /* : CompileState */.typeRegister /* : Option<Type> */) /* : CompileState */;
    }
    /* public */ enterDefinitions() {
        return new CompileState(this /* : CompileState */.structures /* : List<string> */, this /* : CompileState */.definitions /* : List<List<Definition>> */.addLast /* : (arg0 : List<Definition>) => List<T> */(Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */) /* : List<T> */, this /* : CompileState */.objectTypes /* : List<ObjectType> */, this /* : CompileState */.structNames /* : List<string> */, this /* : CompileState */.typeParams /* : List<string> */, this /* : CompileState */.typeRegister /* : Option<Type> */) /* : CompileState */;
    }
    /* public */ exitDefinitions() {
        let removed = this /* : CompileState */.definitions /* : List<List<Definition>> */.removeLast /* : () => Option<[List<T>, T]> */() /* : Option<[List<T>, T]> */.map /* : (arg0 : (arg0 : [List<T>, T]) => R) => Option<R> */(Tuple2 /* : Tuple2 */.left /* : unknown */) /* : Option<R> */.orElse /* : unknown */(this /* : CompileState */.definitions /* : List<List<Definition>> */) /* : unknown */;
        return new CompileState(this /* : CompileState */.structures /* : List<string> */, removed /* : unknown */, this /* : CompileState */.objectTypes /* : List<ObjectType> */, this /* : CompileState */.structNames /* : List<string> */, this /* : CompileState */.typeParams /* : List<string> */, this /* : CompileState */.typeRegister /* : Option<Type> */) /* : CompileState */;
    }
    /* public */ addType(thisType) {
        return new CompileState(this /* : CompileState */.structures /* : List<string> */, this /* : CompileState */.definitions /* : List<List<Definition>> */, this /* : CompileState */.objectTypes /* : List<ObjectType> */.addLast /* : (arg0 : ObjectType) => List<T> */(thisType /* : ObjectType */) /* : List<T> */, this /* : CompileState */.structNames /* : List<string> */, this /* : CompileState */.typeParams /* : List<string> */, this /* : CompileState */.typeRegister /* : Option<Type> */) /* : CompileState */;
    }
}
/* private static */ class DivideState /*  */ {
}
this /* : DivideState */.segments /* : List<string> */ = segments /* : List<string> */;
this /* : DivideState */.buffer /* : string */ = buffer /* : string */;
this /* : DivideState */.depth /* : number */ = depth /* : number */;
this /* : DivideState */.input /* : string */ = input /* : string */;
this /* : DivideState */.index /* : number */ = index /* : number */;
DivideState(input, string);
{
    /* this(input, 0, Lists.empty(), "", 0) */ ;
}
/* private */ advance();
DivideState;
{
    this /* : DivideState */.segments /* : List<string> */ = this /* : DivideState */.segments /* : List<string> */.addLast /* : (arg0 : string) => List<T> */(this /* : DivideState */.buffer /* : string */) /* : List<T> */;
    this /* : DivideState */.buffer /* : string */ = "";
    return this /* : DivideState */;
}
/* private */ append(c, string);
DivideState;
{
    this /* : DivideState */.buffer /* : string */ = this /* : DivideState */.buffer /* : string */ + c /* : string */;
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
    return this /* : DivideState */.depth /* : number */ === 0 /* : number */;
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
    return this /* : DivideState */.depth /* : number */ === 1 /* : number */;
}
/* public */ pop();
Option < [string, DivideState] > {
    if() { } /* this.index < this */, /* this.index < this */ : /* this.index < this */ .input /* : unknown */.length /* : unknown */() /* : unknown */
};
{
    let c = this /* : DivideState */.input /* : string */.charAt /* : unknown */(this /* : DivideState */.index /* : number */) /* : unknown */;
    return new Some(new Tuple2Impl(c /* : unknown */, new DivideState(this /* : DivideState */.input /* : string */, this /* : DivideState */.index /* : number */ + 1 /* : number */, this /* : DivideState */.segments /* : List<string> */, this /* : DivideState */.buffer /* : string */, this /* : DivideState */.depth /* : number */) /* : DivideState */) /* : Tuple2Impl */) /* : Some */;
}
return new None() /* : None */;
/* public */ popAndAppendToTuple();
Option < [string, DivideState] > {
    return: this /* : DivideState */.pop /* : () => Option<[string, DivideState]> */() /* : Option<[string, DivideState]> */.map /* : (arg0 : (arg0 : [string, DivideState]) => R) => Option<R> */((tuple) => {
        let c = tuple /* : [string, DivideState] */[0 /* : number */]() /* : unknown */;
        let right = tuple /* : [string, DivideState] */[1 /* : number */]() /* : unknown */;
        return new Tuple2Impl(c /* : unknown */, right /* : unknown */.append /* : unknown */(c /* : unknown */) /* : unknown */) /* : Tuple2Impl */;
    }) /* : Option<R> */
};
/* public */ popAndAppendToOption();
Option < DivideState > {
    return: this /* : DivideState */.popAndAppendToTuple /* : () => Option<[string, DivideState]> */() /* : Option<[string, DivideState]> */.map /* : (arg0 : (arg0 : [string, DivideState]) => R) => Option<R> */(Tuple2 /* : Tuple2 */.right /* : unknown */) /* : Option<R> */
};
/* public */ peek();
string;
{
    return this /* : DivideState */.input /* : string */.charAt /* : unknown */(this /* : DivideState */.index /* : number */) /* : unknown */;
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
        return new Some(current /* : Option<string> */.map /* : (arg0 : (arg0 : string) => R) => Option<R> */((inner) => inner /* : string */ + this /* : Joiner */.delimiter /* : string */ + element /* : string */) /* : Option<R> */.orElse /* : unknown */(element /* : string */) /* : unknown */) /* : Some */;
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
this /* : FlatMapHead */.mapper /* : (arg0 : T) => Iterator<R> */ = mapper /* : (arg0 : T) => Iterator<R> */;
this /* : FlatMapHead */.current /* : Option<Iterator<R>> */ = new None() /* : None */;
this /* : FlatMapHead */.head /* : Head<T> */ = head /* : Head<T> */;
/* @Override
    public */ next();
Option < R > {
    while() {
        if (this /* : FlatMapHead */.current /* : Option<Iterator<R>> */.isPresent /* : () => boolean */() /* : boolean */) {
            let inner = this /* : FlatMapHead */.current /* : Option<Iterator<R>> */.orElse /* : (arg0 : Iterator<R>) => T */( /* null */) /* : T */;
            let maybe = inner /* : Iterator<R> */.next /* : () => Option<T> */() /* : Option<T> */;
            if (maybe /* : Option<R> */.isPresent /* : () => boolean */() /* : boolean */) {
                return maybe /* : Option<R> */;
            }
            else {
                this /* : FlatMapHead */.current /* : Option<Iterator<R>> */ = new None() /* : None */;
            }
        }
        let outer = this /* : FlatMapHead */.head /* : Head<T> */.next /* : () => Option<T> */() /* : Option<T> */;
        if (outer /* : Option<T> */.isPresent /* : () => boolean */() /* : boolean */) {
            this /* : FlatMapHead */.current /* : Option<Iterator<R>> */ = outer /* : Option<T> */.map /* : (arg0 : (arg0 : T) => R) => Option<R> */(this /* : FlatMapHead */.mapper /* : (arg0 : T) => Iterator<R> */) /* : Option<R> */;
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
        return this /* : ArrayType */.right /* : Type */() /* : unknown */.generate /* : unknown */() /* : unknown */ + "[]";
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
        let joined = this /* : FunctionType */.arguments /* : List<Type> */() /* : unknown */.iterateWithIndices /* : unknown */() /* : unknown */.map /* : unknown */((pair) => "arg" + pair /* : unknown */.left /* : unknown */() /* : unknown */ + " : " + pair /* : unknown */.right /* : unknown */() /* : unknown */.generate /* : unknown */() /* : unknown */) /* : unknown */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        return "(" + joined /* : unknown */ + ") => " + this /* : FunctionType */.returns /* : unknown */.generate /* : unknown */() /* : unknown */;
    }
    /* @Override
        public */ replace(mapping) {
        return new FunctionType(this /* : FunctionType */.arguments /* : List<Type> */.iterate /* : () => Iterator<T> */() /* : Iterator<T> */.map /* : (arg0 : (arg0 : T) => R) => Iterator<R> */((type) => type /* : T */.replace /* : unknown */(mapping /* : Map<string, Type> */) /* : unknown */) /* : Iterator<R> */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */, this /* : FunctionType */.returns /* : Type */) /* : FunctionType */;
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
        let joinedArguments = this /* : TupleType */.arguments /* : List<Type> */.iterate /* : () => Iterator<T> */() /* : Iterator<T> */.map /* : (arg0 : (arg0 : T) => R) => Iterator<R> */(Type /* : Type */.generate /* : unknown */) /* : Iterator<R> */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
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
        let joinedArguments = this /* : Template */.arguments /* : List<Type> */.iterate /* : () => Iterator<T> */() /* : Iterator<T> */.map /* : (arg0 : (arg0 : T) => R) => Iterator<R> */(Type /* : Type */.generate /* : unknown */) /* : Iterator<R> */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.map /* : unknown */((inner) => "<" + inner + ">") /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        return this /* : Template */.base /* : FindableType */.generate /* : unknown */() /* : unknown */ + joinedArguments /* : unknown */;
    }
    /* @Override
        public */ typeParams() {
        return this /* : Template */.base /* : FindableType */.typeParams /* : () => List<string> */() /* : List<string> */;
    }
    /* @Override
        public */ find(name) {
        return this /* : Template */.base /* : FindableType */.find /* : (arg0 : string) => Option<Type> */(name /* : string */) /* : Option<Type> */.map /* : (arg0 : (arg0 : Type) => R) => Option<R> */((found) => {
            let mapping = this /* : Template */.base /* : FindableType */.typeParams /* : () => List<string> */() /* : List<string> */.iterate /* : () => Iterator<T> */() /* : Iterator<T> */.zip /* : (arg0 : Iterator<R>) => Iterator<[T, R]> */(this /* : Template */.arguments /* : List<Type> */.iterate /* : () => Iterator<T> */() /* : Iterator<T> */) /* : Iterator<[T, R]> */.collect /* : unknown */(new /* MapCollector */ () /* : content-start MapCollector content-end */) /* : unknown */;
            return found /* : Type */.replace /* : (arg0 : Map<string, Type>) => Type */(mapping /* : unknown */) /* : Type */;
        }) /* : Option<R> */;
    }
    /* @Override
        public */ name() {
        return this /* : Template */.base /* : FindableType */.name /* : () => string */() /* : string */;
    }
    /* @Override
        public */ replace(mapping) {
        return this /* : Template */;
    }
    /* @Override
        public */ findName() {
        return this /* : Template */.base /* : FindableType */.findName /* : unknown */() /* : unknown */;
    }
}
/* private */ class Placeholder /*  */ {
    constructor(input) {
    }
    /* @Override
        public */ generate() {
        return /* generatePlaceholder */ (this /* : Placeholder */.input /* : string */) /* : unknown */;
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
        return this /* : Placeholder */.input /* : string */;
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
        return this /* : StringValue */.stripped /* : string */;
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
        return this /* : DataAccess */.parent /* : Value */.generate /* : () => string */() /* : string */ + "." + this /* : DataAccess */.property /* : string */ + /* createDebugString */ (this /* : DataAccess */.type /* : () => Type */) /* : unknown */;
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
        return new FunctionType(Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */, this /* : ConstructionCaller */.type /* : Type */) /* : FunctionType */;
    }
}
/* private */ class Operation /*  */ {
    constructor(left, operator /* Operator */, right) {
    }
    /* @Override
        public */ generate() {
        return this /* : Operation */.left /* : Value */() /* : unknown */.generate /* : unknown */() /* : unknown */ + " " + this /* : Operation */.operator /* : content-start Operator content-end */.targetRepresentation /* : unknown */ + " " + this /* : Operation */.right /* : unknown */() /* : unknown */.generate /* : unknown */() /* : unknown */;
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
        return this /* : BlockLambdaValue */.statements /* : List<FunctionSegment> */.iterate /* : () => Iterator<T> */() /* : Iterator<T> */.map /* : (arg0 : (arg0 : T) => R) => Iterator<R> */(FunctionSegment /* : FunctionSegment */.generate /* : unknown */) /* : Iterator<R> */.collect /* : unknown */(new Joiner() /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
    }
}
/* private */ class Lambda /*  */ {
    constructor(parameters, body) {
    }
    /* @Override
        public */ generate() {
        let joined = this /* : Lambda */.parameters /* : List<Definition> */.iterate /* : () => Iterator<T> */() /* : Iterator<T> */.map /* : (arg0 : (arg0 : T) => R) => Iterator<R> */(Definition /* : Definition */.generate /* : unknown */) /* : Iterator<R> */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
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
        let joined = this /* : Invokable */.arguments /* : List<Value> */.iterate /* : () => Iterator<T> */() /* : Iterator<T> */.map /* : (arg0 : (arg0 : T) => R) => Iterator<R> */(Value /* : Value */.generate /* : unknown */) /* : Iterator<R> */.collect /* : unknown */(new Joiner(", ") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        return this /* : Invokable */.caller /* : Caller */.generate /* : () => string */() /* : string */ + "(" + joined /* : unknown */ + ")" + /* createDebugString */ (this /* : Invokable */.type /* : Type */) /* : unknown */;
    }
}
/* private */ class IndexValue /*  */ {
    constructor(parent, child) {
    }
    /* @Override
        public */ generate() {
        return this /* : IndexValue */.parent /* : Value */.generate /* : () => string */() /* : string */ + "[" + this.child.generate() + "]";
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
        return this /* : SymbolValue */.stripped /* : string */ + /* createDebugString */ (this /* : SymbolValue */.type /* : Type */) /* : unknown */;
    }
}
/* private */ class JVMMap {
    constructor(map) {
    }
    /* @Override
            public */ find(key) {
        if (this /* : JVMMap */.map /* : content-start java.util.Map content-end<K, V> */.containsKey /* : unknown */(key /* : K */) /* : unknown */) {
            return new Some(this /* : JVMMap */.map /* : content-start java.util.Map content-end<K, V> */.get /* : unknown */(key /* : K */) /* : unknown */) /* : Some */;
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
/* private static */ class Method /*  */ {
}
this /* : Method */.depth /* : number */ = depth /* : number */;
this /* : Method */.header /* : Header */ = header /* : Header */;
this /* : Method */.parameters /* : List<Definition> */ = parameters /* : List<Definition> */;
this /* : Method */.statements /* : Option<List<FunctionSegment>> */ = maybeStatements /* : Option<List<FunctionSegment>> */;
/* private static */ joinStatements(statements, (List));
string;
{
    return statements /* : List<FunctionSegment> */.iterate /* : () => Iterator<T> */() /* : Iterator<T> */.map /* : (arg0 : (arg0 : T) => R) => Iterator<R> */(FunctionSegment /* : FunctionSegment */.generate /* : unknown */) /* : Iterator<R> */.collect /* : unknown */(new Joiner() /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
}
/* @Override
    public */ generate();
string;
{
    let indent = /* createIndent */ (this /* : Method */.depth /* : number */) /* : unknown */;
    let generatedHeader = this /* : Method */.header /* : Header */.generateWithParams /* : (arg0 : string) => string */(/* joinValues */ (this /* : Method */.parameters /* : List<Definition> */) /* : unknown */) /* : string */;
    let generatedStatements = this /* : Method */.statements /* : Option<List<FunctionSegment>> */.map /* : (arg0 : (arg0 : List<FunctionSegment>) => R) => Option<R> */(Method /* : (arg0 : number, arg1 : Header, arg2 : List<Definition>, arg3 : Option<List<FunctionSegment>>) => content-start public content-end */.joinStatements /* : unknown */) /* : Option<R> */.map /* : unknown */((inner) => " {" + inner + indent + "}") /* : unknown */.orElse /* : unknown */(";") /* : unknown */;
    return indent /* : unknown */ + generatedHeader /* : string */ + generatedStatements /* : unknown */;
}
/* private */ class Block /*  */ {
    constructor(depth, header, statements) {
    }
    /* @Override
        public */ generate() {
        let indent = /* createIndent */ (this /* : Block */.depth /* : number */) /* : unknown */;
        let collect = this /* : Block */.statements /* : List<FunctionSegment> */.iterate /* : () => Iterator<T> */() /* : Iterator<T> */.map /* : (arg0 : (arg0 : T) => R) => Iterator<R> */(FunctionSegment /* : FunctionSegment */.generate /* : unknown */) /* : Iterator<R> */.collect /* : unknown */(new Joiner() /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        return indent /* : unknown */ + this /* : Block */.header /* : BlockHeader */.generate /* : () => string */() /* : string */ + "{" + collect + indent + "}";
    }
}
/* private */ class Conditional /*  */ {
    constructor(prefix, value1) {
    }
    /* @Override
        public */ generate() {
        return this /* : Conditional */.prefix /* : string */ + " (" + this.value1.generate() + ")";
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
this /* : Return */.value1 /* : Value */ = value1 /* : Value */;
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
        return "let " + this /* : Initialization */.definition /* : Definition */.generate /* : () => string */() /* : string */ + " = " + this /* : Initialization */.source /* : unknown */.generate /* : unknown */() /* : unknown */;
    }
}
/* private */ class Assignment /*  */ {
    constructor(destination, source) {
    }
    /* @Override
        public */ generate() {
        return this /* : Assignment */.destination /* : Value */.generate /* : () => string */() /* : string */ + " = " + this /* : Assignment */.source /* : unknown */.generate /* : unknown */() /* : unknown */;
    }
}
/* private */ class Statement /*  */ {
    constructor(depth, value) {
    }
    /* @Override
        public */ generate() {
        return /* createIndent */ (this /* : Statement */.depth /* : number */) /* : unknown */ + this /* : Statement */.value /* : StatementValue */.generate /* : () => string */() /* : string */ + ";";
    }
}
/* private */ class MethodPrototype /*  */ {
    constructor(depth, header, parameters, content) {
    }
    /* private */ findParamTypes() {
        return this /* : MethodPrototype */.parameters /* : List<Definition> */() /* : unknown */.iterate /* : unknown */() /* : unknown */.map /* : unknown */(Definition /* : Definition */.type /* : unknown */) /* : unknown */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */;
    }
    /* @Override
        public */ createDefinition() {
        return new Some(this /* : MethodPrototype */.header /* : Header */.createDefinition /* : (arg0 : List<Type>) => Definition */(this /* : MethodPrototype */.findParamTypes /* : () => List<Type> */() /* : List<Type> */) /* : Definition */) /* : Some */;
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
/* private */ class ClassDefinition /*  */ {
    constructor(definition, depth) {
    }
    /* @Override
        public */ createDefinition() {
        return new Some(this /* : ClassDefinition */.definition /* : Definition */) /* : Some */;
    }
}
/* private */ class Primitive /*  */ {
    constructor(value) {
        this /* : Primitive */.value /* : string */ = value /* : string */;
    }
    /* @Override
        public */ generate() {
        return this /* : Primitive */.value /* : string */;
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
        this /* : Operator */.sourceRepresentation /* : string */ = sourceRepresentation /* : string */;
        this /* : Operator */.targetRepresentation /* : string */ = targetRepresentation /* : string */;
    }
}
/* private */ class BooleanValue /*  */ {
    constructor(value) {
        this /* : BooleanValue */.value /* : string */ = value /* : string */;
    }
    /* @Override
        public */ generate() {
        return this /* : BooleanValue */.value /* : string */;
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
    let parsed = /* parseStatements */ (state /* : CompileState */, input /* : string */, Main /* : Main */.compileRootSegment /* : unknown */) /* : unknown */;
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
    return /* parseAllWithIndices */ (state /* : CompileState */, input /* : string */, Main /* : Main */.foldStatementChar /* : unknown */, (state3, tuple) => new Some(mapper /* : (arg0 : CompileState, arg1 : string) => [CompileState, T] */(state3 /* : unknown */, tuple /* : unknown */.right /* : unknown */() /* : unknown */) /* : [CompileState, T] */) /* : Some */) /* : unknown */.orElseGet /* : unknown */(() => new Tuple2Impl(state /* : CompileState */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */) /* : Tuple2Impl */) /* : unknown */;
}
/* private static */ generateAll(merger, (arg0, arg1) => string, elements, (List));
string;
{
    return elements /* : List<string> */.iterate /* : () => Iterator<T> */() /* : Iterator<T> */.fold /* : (arg0 : R, arg1 : (arg0 : R, arg1 : T) => R) => R */("", merger /* : (arg0 : string, arg1 : string) => string */) /* : R */;
}
/* private static  */ parseAllWithIndices(state, CompileState, input, string, folder, (arg0, arg1) => DivideState, mapper, (arg0, arg1) => Option);
Option < [CompileState, (List)] > {
    let, stringList = (input /* : string */, folder /* : (arg0 : DivideState, arg1 : string) => DivideState */) /* : unknown */
};
/* private static  */ mapUsingState(state, CompileState, elements, (List), mapper, (arg0, arg1) => Option);
Option < [CompileState, (List)] > {
    let, initial: (Option) = new Some(new Tuple2Impl(state /* : CompileState */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */) /* : Tuple2Impl */) /* : Some */,
    return: elements /* : List<T> */.iterateWithIndices /* : () => Iterator<[number, T]> */() /* : Iterator<[number, T]> */.fold /* : (arg0 : R, arg1 : (arg0 : R, arg1 : [number, T]) => R) => R */(initial /* : Option<[CompileState, List<R>]> */, (tuple, element) => {
        return tuple /* : unknown */.flatMap /* : unknown */((inner) => {
            let state1 = inner /* : unknown */.left /* : unknown */() /* : unknown */;
            let right = inner /* : unknown */.right /* : unknown */() /* : unknown */;
            return mapper /* : (arg0 : CompileState, arg1 : [number, T]) => Option<[CompileState, R]> */(state1 /* : unknown */, element /* : unknown */) /* : Option<[CompileState, R]> */.map /* : (arg0 : (arg0 : [CompileState, R]) => R) => Option<R> */((applied) => {
                return new Tuple2Impl(applied /* : [CompileState, R] */[0 /* : number */]() /* : unknown */, right /* : unknown */.addLast /* : unknown */(applied /* : [CompileState, R] */[1 /* : number */]() /* : unknown */) /* : unknown */) /* : Tuple2Impl */;
            }) /* : Option<R> */;
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
    let, current: DivideState = new DivideState(input /* : string */) /* : DivideState */,
    while() {
        let maybePopped = current /* : DivideState */.pop /* : () => Option<[string, DivideState]> */() /* : Option<[string, DivideState]> */.map /* : (arg0 : (arg0 : [string, DivideState]) => R) => Option<R> */((tuple) => {
            return /* foldSingleQuotes */ (tuple /* : [string, DivideState] */) /* : unknown */.or /* : unknown */(() => /* foldDoubleQuotes */ (tuple /* : [string, DivideState] */) /* : unknown */) /* : unknown */.orElseGet /* : unknown */(() => folder /* : (arg0 : DivideState, arg1 : string) => DivideState */(tuple /* : [string, DivideState] */[1 /* : number */]() /* : unknown */, tuple /* : [string, DivideState] */[0 /* : number */]() /* : unknown */) /* : DivideState */) /* : unknown */;
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
    if(tuple /* : [string, DivideState] */, /* : [string, DivideState] */ [], ) { } /* : number */
}() /* : unknown */ === ; /*  '\"' */
{
    let current = tuple /* : [string, DivideState] */[1 /* : number */]() /* : unknown */.append /* : unknown */(tuple /* : [string, DivideState] */[0 /* : number */]() /* : unknown */) /* : unknown */;
    while (true) {
        let maybePopped = current /* : unknown */.popAndAppendToTuple /* : unknown */() /* : unknown */;
        if (maybePopped /* : unknown */.isEmpty /* : unknown */() /* : unknown */) {
            /* break */ ;
        }
        let popped = maybePopped /* : unknown */.orElse /* : unknown */( /* null */) /* : unknown */;
        current /* : unknown */ = popped /* : unknown */.right /* : unknown */() /* : unknown */;
        if (popped /* : unknown */.left /* : unknown */() /* : unknown */ ===  /*  '\\' */) {
            current /* : unknown */ = current /* : unknown */.popAndAppendToOption /* : unknown */() /* : unknown */.orElse /* : unknown */(current /* : unknown */) /* : unknown */;
        }
        if (popped /* : unknown */.left /* : unknown */() /* : unknown */ ===  /*  '\"' */) {
            /* break */ ;
        }
    }
    return new Some(current /* : unknown */) /* : Some */;
}
return new None() /* : None */;
/* private static */ foldSingleQuotes(tuple, [string, DivideState]);
Option < DivideState > {
    if( /* tuple.left() != '\'' */) {
        return new None() /* : None */;
    },
    let, appended = tuple /* : [string, DivideState] */[1 /* : number */]() /* : unknown */.append /* : unknown */(tuple /* : [string, DivideState] */[0 /* : number */]() /* : unknown */) /* : unknown */,
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
    let append = state /* : DivideState */.append /* : (arg0 : string) => DivideState */(c /* : string */) /* : DivideState */;
    if (c /* : string */ ===  /*  ';'  */ && append /* : DivideState */.isLevel /* : unknown */() /* : unknown */) {
        return append /* : DivideState */.advance /* : unknown */() /* : unknown */;
    }
    if (c /* : string */ ===  /*  '}'  */ && append /* : DivideState */.isShallow /* : unknown */() /* : unknown */) {
        return append /* : DivideState */.advance /* : unknown */() /* : unknown */.exit /* : unknown */() /* : unknown */;
    }
    if (c /* : string */ ===  /*  '{'  */ || c /* : string */ ===  /*  '(' */) {
        return append /* : DivideState */.enter /* : unknown */() /* : unknown */;
    }
    if (c /* : string */ ===  /*  '}'  */ || c /* : string */ ===  /*  ')' */) {
        return append /* : DivideState */.exit /* : unknown */() /* : unknown */;
    }
    return append /* : DivideState */;
}
/* private static */ compileRootSegment(state, CompileState, input, string);
[CompileState, string];
{
    let stripped = input /* : string */.strip /* : unknown */() /* : unknown */;
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
    return /* first */(stripped /* : string */, sourceInfix /* : string */) { }
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
    return /* first */(beforeContent /* : string */, ) { }
}(s, s2);
{
    return /* structureWithMaybeExtends */ (targetInfix /* : string */, state /* : CompileState */, beforeInfix /* : string */, s /* : unknown */, content1 /* : string */) /* : unknown */;
}
or /* : unknown */(() => {
    return /* structureWithMaybeExtends */ (targetInfix /* : string */, state /* : CompileState */, beforeInfix /* : string */, beforeContent /* : string */, content1 /* : string */) /* : unknown */;
}) /* : unknown */;
/* private static */ structureWithMaybeExtends(targetInfix, string, state, CompileState, beforeInfix, string, beforeContent, string, content1, string);
Option < [CompileState, IncompleteClassSegmentWrapper] > {
    return /* first */(beforeContent /* : string */, ) { }
}(s, s2);
{
    return /* structureWithMaybeParams */ (targetInfix /* : string */, state /* : CompileState */, beforeInfix /* : string */, s /* : unknown */, content1 /* : string */) /* : unknown */;
}
or /* : unknown */(() => {
    return /* structureWithMaybeParams */ (targetInfix /* : string */, state /* : CompileState */, beforeInfix /* : string */, beforeContent /* : string */, content1 /* : string */) /* : unknown */;
}) /* : unknown */;
/* private static */ structureWithMaybeParams(targetInfix, string, state, CompileState, beforeInfix, string, beforeContent, string, content1, string);
Option < [CompileState, IncompleteClassSegmentWrapper] > {
    return /* suffix */(beforeContent) { } /* : string */, /* : string */ : /* : string */ .strip /* : unknown */() /* : unknown */, ")": ,
}(s);
{
    return /* first */ (s /* : unknown */, "(", (s1, s2) => {
        let parsed = /* parseParameters */ (state /* : CompileState */, s2 /* : unknown */) /* : unknown */;
        return /* getOred */ (targetInfix /* : string */, parsed /* : unknown */.left /* : unknown */() /* : unknown */, beforeInfix /* : string */, s1 /* : unknown */, content1 /* : string */, parsed /* : unknown */.right /* : unknown */() /* : unknown */) /* : unknown */;
    }) /* : unknown */;
}
or /* : unknown */(() => {
    return /* getOred */ (targetInfix /* : string */, state /* : CompileState */, beforeInfix /* : string */, beforeContent /* : string */, content1 /* : string */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */) /* : unknown */;
}) /* : unknown */;
/* private static */ getOred(targetInfix, string, state, CompileState, beforeInfix, string, beforeContent, string, content1, string, params, (List));
Option < [CompileState, IncompleteClassSegmentWrapper] > {
    return /* first */(beforeContent /* : string */, ) { }
}(name, withTypeParams);
{
    return /* first */ (withTypeParams /* : unknown */, ">", (typeParamsString, afterTypeParams) => {
        let /* final */ mapper = (state1, s) => new Tuple2Impl(state1 /* : unknown */, s /* : unknown */.strip /* : unknown */() /* : unknown */) /* : Tuple2Impl */;
        let typeParams = /* parseValuesOrEmpty */ (state /* : CompileState */, typeParamsString /* : unknown */, (state1, s) => new Some(mapper /* : (arg0 : CompileState, arg1 : string) => [CompileState, string] */(state1 /* : unknown */, s /* : unknown */) /* : [CompileState, string] */) /* : Some */) /* : unknown */;
        return /* assembleStructure */ (typeParams /* : unknown */.left /* : unknown */() /* : unknown */, targetInfix /* : string */, beforeInfix /* : string */, name /* : unknown */, content1 /* : string */, typeParams /* : unknown */.right /* : unknown */() /* : unknown */, afterTypeParams /* : unknown */, params /* : List<Parameter> */) /* : unknown */;
    }) /* : unknown */;
}
or /* : unknown */(() => {
    return /* assembleStructure */ (state /* : CompileState */, targetInfix /* : string */, beforeInfix /* : string */, beforeContent /* : string */, content1 /* : string */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */, "", params /* : List<Parameter> */) /* : unknown */;
}) /* : unknown */;
/* private static */ assembleStructure(state, CompileState, targetInfix, string, beforeInfix, string, rawName, string, content, string, typeParams, (List), after, string, rawParameters, (List));
Option < [CompileState, IncompleteClassSegmentWrapper] > {
    let, name = rawName /* : string */.strip /* : unknown */() /* : unknown */,
    if() { }
} /* isSymbol */(name /* : unknown */); /* : unknown */
{
    return new None() /* : None */;
}
let segmentsTuple = parseStatements /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => [CompileState, T]) => [CompileState, List<T>] */(state /* : CompileState */.pushStructName /* : (arg0 : string) => CompileState */(name /* : unknown */) /* : CompileState */.withTypeParams /* : unknown */(typeParams /* : List<string> */) /* : unknown */, content /* : string */, (state0, input) => /* parseClassSegment */ (state0 /* : unknown */, input /* : unknown */, 1 /* : number */) /* : unknown */) /* : [CompileState, List<T>] */;
let segmentsState = segmentsTuple /* : [CompileState, List<T>] */[0 /* : number */]() /* : unknown */;
let segments = segmentsTuple /* : [CompileState, List<T>] */[1 /* : number */]() /* : unknown */;
let definitions = segments /* : unknown */.iterate /* : unknown */() /* : unknown */.map /* : unknown */(IncompleteClassSegment /* : IncompleteClassSegment */.createDefinition /* : unknown */) /* : unknown */.flatMap /* : unknown */(Iterators /* : Iterators */.fromOption /* : unknown */) /* : unknown */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */;
let parameters = /* retainDefinitions */ (rawParameters /* : List<Parameter> */) /* : unknown */;
let thisType = new ObjectType(name /* : unknown */, typeParams /* : List<string> */, definitions /* : unknown */.addAllLast /* : unknown */(parameters /* : unknown */) /* : unknown */) /* : ObjectType */;
let state2 = segmentsState /* : unknown */.enterDefinitions /* : unknown */() /* : unknown */.define /* : unknown */(ImmutableDefinition /* : ImmutableDefinition */.createSimpleDefinition /* : (arg0 : string, arg1 : Type) => Definition */("this", thisType /* : ObjectType */) /* : Definition */) /* : unknown */;
return mapUsingState /* : (arg0 : CompileState, arg1 : List<T>, arg2 : (arg0 : CompileState, arg1 : [number, T]) => Option<[CompileState, R]>) => Option<[CompileState, List<R>]> */(state2 /* : unknown */, segments /* : unknown */, (state1, entry) => /* getTuple2Option */ (state1 /* : unknown */, entry /* : unknown */) /* : unknown */) /* : Option<[CompileState, List<R>]> */.map /* : (arg0 : (arg0 : [CompileState, List<R>]) => R) => Option<R> */((completedTuple) => {
    let completedState = completedTuple /* : [CompileState, List<R>] */[0 /* : number */]() /* : unknown */;
    let completed = completedTuple /* : [CompileState, List<R>] */[1 /* : number */]() /* : unknown */;
    return /* completeStructure */ (completedState /* : unknown */.exitDefinitions /* : unknown */() /* : unknown */, targetInfix /* : string */, beforeInfix /* : string */, name /* : unknown */, typeParams /* : List<string> */, parameters /* : unknown */, after /* : string */, completed /* : unknown */, thisType /* : ObjectType */) /* : unknown */;
}) /* : Option<R> */;
/* private static */ getTuple2Option(state1, CompileState, entry, [number, IncompleteClassSegment]);
Option < [CompileState, ClassSegment] > {
/* return switch (entry.right()) */ };
/* return switch (entry.right()) */ {
    /* case IncompleteClassSegmentWrapper wrapper -> new Some<>(new Tuple2Impl<>(state1, wrapper.segment)) */ ;
    /* case MethodPrototype methodPrototype -> completeMethod(state1, methodPrototype) */ ;
    /* case Whitespace whitespace -> new Some<>(new Tuple2Impl<>(state1, whitespace)) */ ;
    /* case Placeholder placeholder -> new Some<>(new Tuple2Impl<>(state1, placeholder)) */ ;
    /* case ClassDefinition classDefinition -> completeDefinition(state1, classDefinition) */ ;
}
/*  */ ;
/* private static */ completeDefinition(state1, CompileState, classDefinition, ClassDefinition);
Option < [CompileState, ClassSegment] > {
    let, definition: StatementValue = classDefinition /* : ClassDefinition */.definition /* : Definition */,
    let, statement: Statement = new Statement(classDefinition /* : ClassDefinition */.depth /* : number */, definition /* : StatementValue */) /* : Statement */,
    return: new Some(new Tuple2Impl(state1 /* : CompileState */, statement /* : Statement */) /* : Tuple2Impl */) /* : Some */
};
/* private static */ completeStructure(state, CompileState, targetInfix, string, beforeInfix, string, name, string, typeParams, (List), parameters, (List), after, string, segments, (List), thisType, ObjectType);
Tuple2Impl < CompileState, IncompleteClassSegmentWrapper > {
    if(parameters) { } /* : List<Definition> */, /* : List<Definition> */ : /* : List<Definition> */ .isEmpty /* : () => boolean */() /* : boolean */
};
{
    segments /* : List<ClassSegment> */;
}
{
    segments /* : List<ClassSegment> */.addFirst /* : (arg0 : ClassSegment) => List<T> */(new Method(1 /* : number */, new ConstructorHeader() /* : ConstructorHeader */, parameters /* : List<Definition> */, new Some(Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */) /* : Some */) /* : Method */) /* : List<T> */;
}
let parsed2 = /* withMaybeConstructor */ .iterate /* : unknown */() /* : unknown */.map /* : unknown */(ClassSegment /* : ClassSegment */.generate /* : unknown */) /* : unknown */.collect /* : unknown */(new Joiner() /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
let joinedTypeParams = typeParams /* : List<string> */.iterate /* : () => Iterator<T> */() /* : Iterator<T> */.collect /* : (arg0 : Collector<T, R>) => R */(new Joiner(", ") /* : Joiner */) /* : R */.map /* : unknown */((inner) => "<" + inner + ">") /* : unknown */.orElse /* : unknown */("") /* : unknown */;
let generated = /* generatePlaceholder */ (beforeInfix /* : string */.strip /* : unknown */() /* : unknown */) /* : unknown */ + targetInfix /* : string */ + name /* : string */ + joinedTypeParams /* : unknown */ + /* generatePlaceholder */ (after /* : string */) /* : unknown */ + " {" + parsed2 + "\n}\n";
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
        let c = input /* : string */.charAt /* : unknown */( /* i */) /* : unknown */;
        if ( /* Character */.isLetter /* : unknown */(c /* : unknown */) /* : unknown */ || /*  */ ( /* i != 0  */ && /* Character */ .isDigit /* : unknown */(c /* : unknown */) /* : unknown */) /* : unknown */) {
            /* continue */ ;
        }
        return false;
    }
    return true;
}
/* private static  */ prefix(input, string, prefix, string, mapper, (arg0) => Option);
Option < T > {
    if(, input) { } /* : string */, /* : string */ : /* : string */ .startsWith /* : unknown */(prefix /* : string */) /* : unknown */
};
{
    return new None() /* : None */;
}
let slice = input /* : string */.substring /* : unknown */(prefix /* : string */.length /* : unknown */() /* : unknown */) /* : unknown */;
return mapper /* : (arg0 : string) => Option<T> */(slice /* : unknown */) /* : Option<T> */;
/* private static  */ suffix(input, string, suffix, string, mapper, (arg0) => Option);
Option < T > {
    if(, input) { } /* : string */, /* : string */ : /* : string */ .endsWith /* : unknown */(suffix /* : string */) /* : unknown */
};
{
    return new None() /* : None */;
}
let slice = input /* : string */.substring /* : unknown */(0 /* : number */, input /* : string */.length /* : unknown */() /* : unknown */ - suffix /* : string */.length /* : unknown */() /* : unknown */) /* : unknown */;
return mapper /* : (arg0 : string) => Option<T> */(slice /* : unknown */) /* : Option<T> */;
/* private static */ parseClassSegment(state, CompileState, input, string, depth, number);
[CompileState, IncompleteClassSegment];
{
    return /* Main.<Whitespace, IncompleteClassSegment>typed */ (() => /* parseWhitespace */ (input /* : string */, state /* : CompileState */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* typed */ (() => parseClass /* : (arg0 : string, arg1 : CompileState) => Option<[CompileState, IncompleteClassSegmentWrapper]> */(input /* : string */, state /* : CompileState */) /* : Option<[CompileState, IncompleteClassSegmentWrapper]> */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* typed */ (() => parseStructure /* : (arg0 : string, arg1 : string, arg2 : string, arg3 : CompileState) => Option<[CompileState, IncompleteClassSegmentWrapper]> */(input /* : string */, "interface ", "interface ", state /* : CompileState */) /* : Option<[CompileState, IncompleteClassSegmentWrapper]> */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* typed */ (() => parseStructure /* : (arg0 : string, arg1 : string, arg2 : string, arg3 : CompileState) => Option<[CompileState, IncompleteClassSegmentWrapper]> */(input /* : string */, "record ", "class ", state /* : CompileState */) /* : Option<[CompileState, IncompleteClassSegmentWrapper]> */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* typed */ (() => parseStructure /* : (arg0 : string, arg1 : string, arg2 : string, arg3 : CompileState) => Option<[CompileState, IncompleteClassSegmentWrapper]> */(input /* : string */, "enum ", "class ", state /* : CompileState */) /* : Option<[CompileState, IncompleteClassSegmentWrapper]> */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseMethod */ (state /* : CompileState */, input /* : string */, depth /* : number */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* typed */ (() => /* parseDefinitionStatement */ (input /* : string */, depth /* : number */, state /* : CompileState */) /* : unknown */) /* : unknown */) /* : unknown */.orElseGet /* : unknown */(() => new Tuple2Impl(state /* : CompileState */, new Placeholder(input /* : string */) /* : Placeholder */) /* : Tuple2Impl */) /* : unknown */;
}
/* private static  */ typed(action, () => Option);
Option < [CompileState, S] > {
    return: action /* : () => Option<[CompileState, T]> */() /* : Option<[CompileState, T]> */.map /* : (arg0 : (arg0 : [CompileState, T]) => R) => Option<R> */((tuple) => new Tuple2Impl(tuple /* : [CompileState, T] */[0 /* : number */]() /* : unknown */, tuple /* : [CompileState, T] */[1 /* : number */]() /* : unknown */) /* : Tuple2Impl */) /* : Option<R> */
};
/* private static */ parseWhitespace(input, string, state, CompileState);
Option < [CompileState, Whitespace] > {
    if(input) { } /* : string */, /* : string */ : /* : string */ .isBlank /* : unknown */() /* : unknown */
};
{
    return new Some(new Tuple2Impl(state /* : CompileState */, new Whitespace() /* : Whitespace */) /* : Tuple2Impl */) /* : Some */;
}
return new None() /* : None */;
/* private static */ parseMethod(state, CompileState, input, string, depth, number);
Option < [CompileState, IncompleteClassSegment] > {
    return /* first */(input /* : string */, ) { }
}(definitionString, withParams);
{
    return /* first */ (withParams /* : unknown */, ")", (parametersString, rawContent) => {
        return /* parseDefinition(state, definitionString).<Tuple2<CompileState, Header>>map */ ((tuple) => new Tuple2Impl(tuple /* : unknown */.left /* : unknown */() /* : unknown */, tuple /* : unknown */.right /* : unknown */() /* : unknown */) /* : Tuple2Impl */) /* : unknown */.or /* : unknown */(() => /* parseConstructor */ (state /* : CompileState */, definitionString /* : unknown */) /* : unknown */) /* : unknown */.flatMap /* : unknown */((definitionTuple) => /* assembleMethod */ (depth /* : number */, parametersString /* : unknown */, rawContent /* : unknown */, definitionTuple /* : unknown */) /* : unknown */) /* : unknown */;
    }) /* : unknown */;
}
;
/* private static */ assembleMethod(depth, number, parametersString, string, rawContent, string, definitionTuple, [CompileState, Header]);
Option < [CompileState, IncompleteClassSegment] > {
    let, definitionState = definitionTuple /* : [CompileState, Header] */[0 /* : number */]() /* : unknown */,
    let, header = definitionTuple /* : [CompileState, Header] */[1 /* : number */]() /* : unknown */,
    let, parametersTuple = (definitionState /* : unknown */, parametersString /* : string */) /* : unknown */,
    let, rawParameters = parametersTuple /* : unknown */.right /* : unknown */() /* : unknown */,
    let, parameters = (rawParameters /* : unknown */) /* : unknown */,
    return: new Some(new Tuple2Impl(parametersTuple /* : unknown */.left /* : unknown */() /* : unknown */, new MethodPrototype(depth /* : number */, header /* : unknown */, parameters /* : unknown */, rawContent /* : string */.strip /* : unknown */() /* : unknown */) /* : MethodPrototype */) /* : Tuple2Impl */) /* : Some */
};
/* private static */ completeMethod(state, CompileState, prototype, MethodPrototype);
Option < [CompileState, ClassSegment] > {
    let, paramTypes: (List) = prototype /* : MethodPrototype */.findParamTypes /* : () => List<Type> */() /* : List<Type> */,
    let, toDefine = prototype /* : MethodPrototype */.header /* : Header */() /* : unknown */.createDefinition /* : unknown */(paramTypes /* : List<Type> */) /* : unknown */,
    if(prototype) { } /* : MethodPrototype */, /* : MethodPrototype */ : /* : MethodPrototype */ .content /* : string */() /* : unknown */.equals /* : unknown */(";") /* : unknown */
};
{
    return new Some(new Tuple2Impl(state /* : CompileState */.define /* : (arg0 : Definition) => CompileState */(toDefine /* : unknown */) /* : CompileState */, new Method(prototype /* : MethodPrototype */.depth /* : number */() /* : unknown */, prototype /* : MethodPrototype */.header /* : Header */() /* : unknown */, prototype /* : MethodPrototype */.parameters /* : List<Definition> */() /* : unknown */, new None() /* : None */) /* : Method */) /* : Tuple2Impl */) /* : Some */;
}
if (prototype /* : MethodPrototype */.content /* : string */() /* : unknown */.startsWith /* : unknown */("{") /* : unknown */ && prototype /* : MethodPrototype */.content /* : unknown */() /* : unknown */.endsWith /* : unknown */("}") /* : unknown */) {
    let substring = prototype /* : MethodPrototype */.content /* : string */() /* : unknown */.substring /* : unknown */(1 /* : number */, prototype /* : MethodPrototype */.content /* : string */() /* : unknown */.length /* : unknown */() /* : unknown */ - 1 /* : number */) /* : unknown */;
    let withDefined = state /* : CompileState */.enterDefinitions /* : () => CompileState */() /* : CompileState */.defineAll /* : unknown */(prototype /* : MethodPrototype */.parameters /* : List<Definition> */() /* : unknown */) /* : unknown */;
    let statementsTuple = parseStatements /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => [CompileState, T]) => [CompileState, List<T>] */(withDefined /* : unknown */, substring /* : unknown */, (state1, input1) => /* parseFunctionSegment */ (state1 /* : unknown */, input1 /* : unknown */, prototype /* : MethodPrototype */.depth /* : number */() /* : unknown */ + 1 /* : number */) /* : unknown */) /* : [CompileState, List<T>] */;
    let statements = statementsTuple /* : [CompileState, List<T>] */[1 /* : number */]() /* : unknown */;
    return new Some(new Tuple2Impl(statementsTuple /* : [CompileState, List<T>] */[0 /* : number */]() /* : unknown */.exitDefinitions /* : unknown */() /* : unknown */.define /* : unknown */(toDefine /* : unknown */) /* : unknown */, new Method(prototype /* : MethodPrototype */.depth /* : number */() /* : unknown */, prototype /* : MethodPrototype */.header /* : Header */() /* : unknown */, prototype /* : MethodPrototype */.parameters /* : List<Definition> */() /* : unknown */, new Some(statements /* : unknown */) /* : Some */) /* : Method */) /* : Tuple2Impl */) /* : Some */;
}
return new None() /* : None */;
/* private static */ parseConstructor(state, CompileState, input, string);
Option < [CompileState, Header] > {
    let, stripped = input /* : string */.strip /* : unknown */() /* : unknown */,
    if(stripped) { } /* : unknown */, /* : unknown */ : /* : unknown */ .equals /* : unknown */(state /* : CompileState */.structNames /* : List<string> */.last /* : () => Option<T> */() /* : Option<T> */.orElse /* : (arg0 : T) => T */("") /* : T */) /* : unknown */
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
    return: right /* : List<Parameter> */.iterate /* : () => Iterator<T> */() /* : Iterator<T> */.map /* : (arg0 : (arg0 : T) => R) => Iterator<R> */(Main /* : Main */.retainDefinition /* : unknown */) /* : Iterator<R> */.flatMap /* : unknown */(Iterators /* : Iterators */.fromOption /* : unknown */) /* : unknown */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */
};
/* private static */ parseParameters(state, CompileState, params, string);
[CompileState, (List)];
{
    return /* parseValuesOrEmpty */ (state /* : CompileState */, params /* : string */, (state1, s) => new Some(/* parseParameter */ (state1 /* : unknown */, s /* : unknown */) /* : unknown */) /* : Some */) /* : unknown */;
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
        return new Tuple2Impl(state /* : CompileState */, new Whitespace() /* : Whitespace */) /* : Tuple2Impl */;
    }
    return /* parseFunctionStatement */ (state /* : CompileState */, depth /* : number */, stripped /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseBlock */ (state /* : CompileState */, depth /* : number */, stripped /* : unknown */) /* : unknown */) /* : unknown */.orElseGet /* : unknown */(() => new Tuple2Impl(state /* : CompileState */, new Placeholder(stripped /* : unknown */) /* : Placeholder */) /* : Tuple2Impl */) /* : unknown */;
}
/* private static */ parseFunctionStatement(state, CompileState, depth, number, stripped, string);
Option < [CompileState, FunctionSegment] > {
    return: suffix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(stripped /* : string */, ";", (s) => {
        let tuple = /* parseStatementValue */ (state /* : CompileState */, s /* : string */, depth /* : number */) /* : unknown */;
        let left = tuple /* : unknown */.left /* : unknown */() /* : unknown */;
        let right = tuple /* : unknown */.right /* : unknown */() /* : unknown */;
        return new Some(new Tuple2Impl(left /* : unknown */, new Statement(depth /* : number */, right /* : unknown */) /* : Statement */) /* : Tuple2Impl */) /* : Some */;
    }) /* : Option<T> */
};
/* private static */ parseBlock(state, CompileState, depth, number, stripped, string);
Option < [CompileState, FunctionSegment] > {
    return: suffix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(stripped /* : string */, "}", (withoutEnd) => {
        return /* split */ (() => /* toFirst */ (withoutEnd /* : string */) /* : unknown */, (beforeContent, content) => {
            return suffix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(beforeContent /* : unknown */, "{", (s) => {
                let statements = parseFunctionSegments /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, List<FunctionSegment>] */(state /* : CompileState */, content /* : unknown */, depth /* : number */) /* : [CompileState, List<FunctionSegment>] */;
                let headerTuple = /* parseBlockHeader */ (state /* : CompileState */, s /* : string */, depth /* : number */) /* : unknown */;
                let headerState = headerTuple /* : unknown */.left /* : unknown */() /* : unknown */;
                let header = headerTuple /* : unknown */.right /* : unknown */() /* : unknown */;
                let right = statements /* : [CompileState, List<FunctionSegment>] */[1 /* : number */]() /* : unknown */;
                return new Some(new Tuple2Impl(headerState /* : unknown */, new Block(depth /* : number */, header /* : unknown */, right /* : unknown */) /* : Block */) /* : Tuple2Impl */) /* : Some */;
            }) /* : Option<T> */;
        }) /* : unknown */;
    }) /* : Option<T> */
};
/* private static */ toFirst(input, string);
Option < [string, string] > {
    let, divisions: (List) = divideAll /* : (arg0 : string, arg1 : (arg0 : DivideState, arg1 : string) => DivideState) => List<string> */(input /* : string */, Main /* : Main */.foldBlockStart /* : unknown */) /* : List<string> */,
    return: divisions /* : List<string> */.removeFirst /* : () => Option<[T, List<T>]> */() /* : Option<[T, List<T>]> */.map /* : (arg0 : (arg0 : [T, List<T>]) => R) => Option<R> */((removed) => {
        let right = removed /* : [T, List<T>] */[0 /* : number */]() /* : unknown */;
        let left = removed /* : [T, List<T>] */[1 /* : number */]() /* : unknown */.iterate /* : unknown */() /* : unknown */.collect /* : unknown */(new Joiner("") /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        return new Tuple2Impl(right /* : unknown */, left /* : unknown */) /* : Tuple2Impl */;
    }) /* : Option<R> */
};
/* private static */ parseBlockHeader(state, CompileState, input, string, depth, number);
[CompileState, BlockHeader];
{
    let stripped = input /* : string */.strip /* : unknown */() /* : unknown */;
    return /* parseConditional */ (state /* : CompileState */, stripped /* : unknown */, "if", depth /* : number */) /* : unknown */.or /* : unknown */(() => /* parseConditional */ (state /* : CompileState */, stripped /* : unknown */, "while", depth /* : number */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseElse */ (state /* : CompileState */, input /* : string */) /* : unknown */) /* : unknown */.orElseGet /* : unknown */(() => new Tuple2Impl(state /* : CompileState */, new Placeholder(stripped /* : unknown */) /* : Placeholder */) /* : Tuple2Impl */) /* : unknown */;
}
/* private static */ parseElse(state, CompileState, input, string);
Option < [CompileState, BlockHeader] > {
    let, stripped = input /* : string */.strip /* : unknown */() /* : unknown */,
    if(stripped) { } /* : unknown */, /* : unknown */ : /* : unknown */ .equals /* : unknown */("else") /* : unknown */
};
{
    return new Some(new Tuple2Impl(state /* : CompileState */, new Else() /* : Else */) /* : Tuple2Impl */) /* : Some */;
}
return new None() /* : None */;
/* private static */ parseConditional(state, CompileState, input, string, prefix, string, depth, number);
Option < [CompileState, BlockHeader] > {
    return: prefix /* : string */(input /* : string */, prefix /* : string */, (withoutPrefix) => {
        return prefix /* : string */(withoutPrefix /* : unknown */.strip /* : unknown */() /* : unknown */, "(", (withoutValueStart) => {
            return suffix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(withoutValueStart /* : unknown */, ")", (value) => {
                let valueTuple = /* parseValue */ (state /* : CompileState */, value /* : string */, depth /* : number */) /* : unknown */;
                let value1 = valueTuple /* : unknown */.right /* : unknown */() /* : unknown */;
                return new Some(new Tuple2Impl(valueTuple /* : unknown */.left /* : unknown */() /* : unknown */, new Conditional(prefix /* : string */, value1 /* : unknown */) /* : Conditional */) /* : Tuple2Impl */) /* : Some */;
            }) /* : Option<T> */;
        }) /* : unknown */;
    }) /* : unknown */
};
/* private static */ foldBlockStart(state, DivideState, c, string);
DivideState;
{
    let appended = state /* : DivideState */.append /* : (arg0 : string) => DivideState */(c /* : string */) /* : DivideState */;
    if (c /* : string */ ===  /*  '{'  */ && state /* : DivideState */.isLevel /* : unknown */() /* : unknown */) {
        return appended /* : DivideState */.advance /* : unknown */() /* : unknown */;
    }
    if (c /* : string */ ===  /*  '{' */) {
        return appended /* : DivideState */.enter /* : unknown */() /* : unknown */;
    }
    if (c /* : string */ ===  /*  '}' */) {
        return appended /* : DivideState */.exit /* : unknown */() /* : unknown */;
    }
    return appended /* : DivideState */;
}
/* private static */ parseStatementValue(state, CompileState, input, string, depth, number);
[CompileState, StatementValue];
{
    let stripped = input /* : string */.strip /* : unknown */() /* : unknown */;
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
    return /* first */(stripped /* : string */, ) { }
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
    let, destinationTuple = (sourceState /* : CompileState */, beforeEquals /* : string */, depth /* : number */) /* : unknown */,
    let, destinationState = destinationTuple /* : unknown */.left /* : unknown */() /* : unknown */,
    let, destination = destinationTuple /* : unknown */.right /* : unknown */() /* : unknown */,
    return: new Some(new Tuple2Impl(destinationState /* : unknown */, new Assignment(destination /* : unknown */, source /* : Value */) /* : Assignment */) /* : Tuple2Impl */) /* : Some */
};
/* private static */ parseInitialization(state, CompileState, rawDefinition, Definition, source, Value);
Option < [CompileState, StatementValue] > {
    let, definition: Definition = rawDefinition /* : Definition */.mapType /* : (arg0 : (arg0 : Type) => Type) => Definition */((type) => {
        if (type /* : Type */.equals /* : unknown */(Primitive /* : Primitive */.Unknown /* : unknown */) /* : unknown */) {
            return source /* : Value */.type /* : () => Type */() /* : Type */;
        }
        else {
            return type /* : Type */;
        }
    }) /* : Definition */,
    return: new Some(new Tuple2Impl(state /* : CompileState */.define /* : (arg0 : Definition) => CompileState */(definition /* : Definition */) /* : CompileState */, new Initialization(definition /* : Definition */, source /* : Value */) /* : Initialization */) /* : Tuple2Impl */) /* : Some */
};
/* private static */ parseValue(state, CompileState, input, string, depth, number);
[CompileState, Value];
{
    return /* parseBoolean */ (state /* : CompileState */, input /* : string */) /* : unknown */.or /* : unknown */(() => /* parseLambda */ (state /* : CompileState */, input /* : string */, depth /* : number */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseString */ (state /* : CompileState */, input /* : string */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseDataAccess */ (state /* : CompileState */, input /* : string */, depth /* : number */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseSymbolValue */ (state /* : CompileState */, input /* : string */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseInvokable */ (state /* : CompileState */, input /* : string */, depth /* : number */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseDigits */ (state /* : CompileState */, input /* : string */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseOperation */ (state /* : CompileState */, input /* : string */, depth /* : number */, Operator /* : Operator */.ADD /* : unknown */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseOperation */ (state /* : CompileState */, input /* : string */, depth /* : number */, Operator /* : Operator */.EQUALS /* : unknown */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseOperation */ (state /* : CompileState */, input /* : string */, depth /* : number */, Operator /* : Operator */.SUBTRACT /* : unknown */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseOperation */ (state /* : CompileState */, input /* : string */, depth /* : number */, Operator /* : Operator */.AND /* : unknown */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseOperation */ (state /* : CompileState */, input /* : string */, depth /* : number */, Operator /* : Operator */.OR /* : unknown */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseOperation */ (state /* : CompileState */, input /* : string */, depth /* : number */,  /*  Operator.GREATER_THAN_OR_EQUALS */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseNot */ (state /* : CompileState */, input /* : string */, depth /* : number */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseMethodReference */ (state /* : CompileState */, input /* : string */, depth /* : number */) /* : unknown */) /* : unknown */.or /* : unknown */(() => /* parseInstanceOf */ (state /* : CompileState */, input /* : string */, depth /* : number */) /* : unknown */) /* : unknown */.orElseGet /* : unknown */(() => new Tuple2Impl(state /* : CompileState */, new Placeholder(input /* : string */) /* : Placeholder */) /* : Tuple2Impl<CompileState, Value> */) /* : unknown */;
}
/* private static */ parseBoolean(state, CompileState, input, string);
Option < [CompileState, Value] > {
    let, stripped = input /* : string */.strip /* : unknown */() /* : unknown */,
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
    return /* last */(input /* : string */, ) { }
}(s, s2);
{
    let childTuple = parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state /* : CompileState */, s /* : unknown */, depth /* : number */) /* : [CompileState, Value] */;
    return /* parseDefinition */ (childTuple /* : [CompileState, Value] */[0 /* : number */]() /* : unknown */, s2 /* : unknown */) /* : unknown */.map /* : unknown */((definitionTuple) => {
        let value = childTuple /* : [CompileState, Value] */[1 /* : number */]() /* : unknown */;
        let definition = definitionTuple /* : unknown */.right /* : unknown */() /* : unknown */;
        let variant = new DataAccess(value /* : unknown */, "_variant", Primitive /* : Primitive */.Unknown /* : unknown */) /* : DataAccess */;
        let type = value /* : unknown */.type /* : unknown */() /* : unknown */;
        let generate = type /* : unknown */.findName /* : unknown */() /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        let temp = new SymbolValue(generate /* : unknown */ + "Variant." + definition /* : unknown */.type /* : unknown */() /* : unknown */.findName /* : unknown */() /* : unknown */.orElse /* : unknown */("") /* : unknown */, Primitive /* : Primitive */.Unknown /* : unknown */) /* : SymbolValue */;
        return new Tuple2Impl(definitionTuple /* : unknown */.left /* : unknown */() /* : unknown */, new Operation(variant /* : DataAccess */, Operator /* : Operator */.EQUALS /* : unknown */, temp /* : SymbolValue */) /* : Operation */) /* : Tuple2Impl */;
    }) /* : unknown */;
}
;
/* private static */ parseMethodReference(state, CompileState, input, string, depth, number);
Option < [CompileState, Value] > {
    return /* last */(input /* : string */, ) { }
}(s, s2);
{
    let tuple = parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state /* : CompileState */, s /* : unknown */, depth /* : number */) /* : [CompileState, Value] */;
    return new Some(new Tuple2Impl(tuple /* : [CompileState, Value] */[0 /* : number */]() /* : unknown */, new DataAccess(tuple /* : [CompileState, Value] */[1 /* : number */]() /* : unknown */, s2 /* : unknown */, Primitive /* : Primitive */.Unknown /* : unknown */) /* : DataAccess */) /* : Tuple2Impl */) /* : Some */;
}
;
/* private static */ parseNot(state, CompileState, input, string, depth, number);
Option < [CompileState, Value] > {
    let, stripped = input /* : string */.strip /* : unknown */() /* : unknown */,
    if(stripped) { } /* : unknown */, /* : unknown */ : /* : unknown */ .startsWith /* : unknown */("!") /* : unknown */
};
{
    let slice = stripped /* : unknown */.substring /* : unknown */(1 /* : number */) /* : unknown */;
    let tuple = parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state /* : CompileState */, slice /* : unknown */, depth /* : number */) /* : [CompileState, Value] */;
    let value = tuple /* : [CompileState, Value] */[1 /* : number */]() /* : unknown */;
    return new Some(new Tuple2Impl(tuple /* : [CompileState, Value] */[0 /* : number */]() /* : unknown */, new Not(value /* : unknown */) /* : Not */) /* : Tuple2Impl */) /* : Some */;
}
return new None() /* : None */;
/* private static */ parseLambda(state, CompileState, input, string, depth, number);
Option < [CompileState, Value] > {
    return /* first */(input /* : string */, ) { }
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
    let, strippedValueString = valueString /* : string */.strip /* : unknown */() /* : unknown */,
    let, state2: CompileState = state /* : CompileState */.defineAll /* : (arg0 : List<Definition>) => CompileState */(definitions /* : List<Definition> */) /* : CompileState */,
    if(strippedValueString) { } /* : unknown */, /* : unknown */ : /* : unknown */ .startsWith /* : unknown */("{") /* : unknown */ && strippedValueString /* : unknown */.endsWith /* : unknown */("}") /* : unknown */
};
{
    let value1 = parseStatements /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => [CompileState, T]) => [CompileState, List<T>] */(state2 /* : CompileState */, strippedValueString /* : unknown */.substring /* : unknown */(1 /* : number */, strippedValueString /* : unknown */.length /* : unknown */() /* : unknown */ - 1 /* : number */) /* : unknown */, (state1, input1) => parseFunctionSegment /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, FunctionSegment] */(state1 /* : unknown */, input1 /* : unknown */, depth /* : number */ + 1 /* : number */) /* : [CompileState, FunctionSegment] */) /* : [CompileState, List<T>] */;
    let right = value1 /* : [CompileState, List<T>] */[1 /* : number */]() /* : unknown */;
    new Tuple2Impl(value1 /* : [CompileState, List<T>] */[0 /* : number */]() /* : unknown */, new BlockLambdaValue(depth /* : number */, right /* : unknown */) /* : BlockLambdaValue */) /* : Tuple2Impl */;
}
{
    let value1 = parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state2 /* : CompileState */, strippedValueString /* : unknown */, depth /* : number */) /* : [CompileState, Value] */;
    new Tuple2Impl(value1 /* : [CompileState, List<T>] */[0 /* : number */]() /* : unknown */, value1 /* : [CompileState, List<T>] */[1 /* : number */]() /* : unknown */) /* : Tuple2Impl */;
}
let right = /* value */ .right /* : unknown */() /* : unknown */;
return new Some(new Tuple2Impl(left /* : unknown */() /* : unknown */, new Lambda(definitions /* : List<Definition> */, right /* : unknown */) /* : Lambda */) /* : Tuple2Impl */) /* : Some */;
/* private static */ parseDigits(state, CompileState, input, string);
Option < [CompileState, Value] > {
    let, stripped = input /* : string */.strip /* : unknown */() /* : unknown */,
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
    if (input /* : string */.startsWith /* : unknown */("-") /* : unknown */) {
        input /* : string */.substring /* : unknown */(1 /* : number */) /* : unknown */;
    }
    else {
        input /* : string */;
    }
    return /* areAllDigits */ ( /* maybeTruncated */) /* : unknown */;
}
/* private static */ areAllDigits(input, string);
boolean;
{
    /* for (var i = 0; i < input.length(); i++) */ {
        let c = input /* : string */.charAt /* : unknown */( /* i */) /* : unknown */;
        if ( /* Character */.isDigit /* : unknown */(c /* : unknown */) /* : unknown */) {
            /* continue */ ;
        }
        return false;
    }
    return true;
}
/* private static */ parseInvokable(state, CompileState, input, string, depth, number);
Option < [CompileState, Value] > {
    return: suffix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(input /* : string */.strip /* : unknown */() /* : unknown */, ")", (withoutEnd) => {
        return /* split */ (() => /* toLast */ (withoutEnd /* : string */, "", Main /* : Main */.foldInvocationStart /* : unknown */) /* : unknown */, (callerWithEnd, argumentsString) => {
            return suffix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(callerWithEnd /* : unknown */, "(", (callerString) => {
                return /* assembleInvokable */ (state /* : CompileState */, depth /* : number */, argumentsString /* : unknown */, callerString /* : string */.strip /* : unknown */() /* : unknown */) /* : unknown */;
            }) /* : Option<T> */;
        }) /* : unknown */;
    }) /* : Option<T> */
};
/* private static */ assembleInvokable(state, CompileState, depth, number, argumentsString, string, callerString, string);
Some < [CompileState, Value] > {
    let, callerTuple = (state /* : CompileState */, depth /* : number */, callerString /* : string */) /* : unknown */,
    let, oldCallerState = callerTuple /* : unknown */.left /* : unknown */() /* : unknown */,
    let, oldCaller = callerTuple /* : unknown */.right /* : unknown */() /* : unknown */,
    let, newCaller = (oldCallerState /* : unknown */, oldCaller /* : unknown */) /* : unknown */,
    let, callerType = (newCaller /* : unknown */) /* : unknown */,
    let, argumentsTuple = (oldCallerState /* : unknown */, argumentsString /* : string */, (currentState, pair) => {
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
    return new Some( /* value */) /* : Some */;
}
return new None() /* : None */;
/* private static */ parseArgument(state, CompileState, element, string, depth, number);
[CompileState, Argument];
{
    if (element /* : string */.isEmpty /* : unknown */() /* : unknown */) {
        return new Tuple2Impl(state /* : CompileState */, new Whitespace() /* : Whitespace */) /* : Tuple2Impl */;
    }
    let tuple = parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state /* : CompileState */, element /* : string */, depth /* : number */) /* : [CompileState, Value] */;
    return new Tuple2Impl(tuple /* : [CompileState, Value] */[0 /* : number */]() /* : unknown */, tuple /* : [CompileState, Value] */[1 /* : number */]() /* : unknown */) /* : Tuple2Impl */;
}
/* private static */ findCallerType(newCaller, Caller);
FunctionType;
{
    let callerType = new FunctionType(Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */, Primitive /* : Primitive */.Unknown /* : unknown */) /* : FunctionType */;
    /* switch (newCaller) */ {
        /* case ConstructionCaller constructionCaller -> */ {
            callerType /* : FunctionType */ = /* constructionCaller */ .toFunction /* : unknown */() /* : unknown */;
        }
        /* case Value value -> */ {
            let type = /* value */ .type /* : unknown */() /* : unknown */;
            if (type /* : unknown */._variant /* : unknown */ === Variant.FunctionType /* : unknown */) {
                callerType /* : FunctionType */ =  /* functionType */;
            }
        }
    }
    return callerType /* : FunctionType */;
}
/* private static */ modifyCaller(state, CompileState, oldCaller, Caller);
Caller;
{
    if (oldCaller /* : Caller */._variant /* : unknown */ === CallerVariant.DataAccess /* : unknown */) {
        let type = /* resolveType */ ( /* access */.parent /* : unknown */, state /* : CompileState */) /* : unknown */;
        if ( /* type instanceof FunctionType */) {
            return /* access */ .parent /* : unknown */;
        }
    }
    return oldCaller /* : Caller */;
}
/* private static */ resolveType(value, Value, state, CompileState);
Type;
{
    return value /* : Value */.type /* : () => Type */() /* : Type */;
}
/* private static */ invocationHeader(state, CompileState, depth, number, callerString1, string);
[CompileState, Caller];
{
    if (callerString1 /* : string */.startsWith /* : unknown */("new ") /* : unknown */) {
        let input1 = callerString1 /* : string */.substring /* : unknown */("new ".length /* : unknown */() /* : unknown */) /* : unknown */;
        let map = /* parseType */ (state /* : CompileState */, input1 /* : string */) /* : unknown */.map /* : unknown */((type) => {
            let right = type /* : unknown */.right /* : unknown */() /* : unknown */;
            return new Tuple2Impl(type /* : unknown */.left /* : unknown */() /* : unknown */, new ConstructionCaller(right /* : unknown */) /* : ConstructionCaller */) /* : Tuple2Impl<CompileState, Caller> */;
        }) /* : unknown */;
        if (map /* : unknown */.isPresent /* : unknown */() /* : unknown */) {
            return map /* : unknown */.orElse /* : unknown */( /* null */) /* : unknown */;
        }
    }
    let tuple = parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state /* : CompileState */, callerString1 /* : string */, depth /* : number */) /* : [CompileState, Value] */;
    return new Tuple2Impl(tuple /* : [CompileState, Value] */[0 /* : number */]() /* : unknown */, tuple /* : [CompileState, Value] */[1 /* : number */]() /* : unknown */) /* : Tuple2Impl */;
}
/* private static */ foldInvocationStart(state, DivideState, c, string);
DivideState;
{
    let appended = state /* : DivideState */.append /* : (arg0 : string) => DivideState */(c /* : string */) /* : DivideState */;
    if (c /* : string */ ===  /*  '(' */) {
        let enter = appended /* : DivideState */.enter /* : unknown */() /* : unknown */;
        if (enter /* : unknown */.isShallow /* : unknown */() /* : unknown */) {
            return enter /* : unknown */.advance /* : unknown */() /* : unknown */;
        }
        return enter /* : unknown */;
    }
    if (c /* : string */ ===  /*  ')' */) {
        return appended /* : DivideState */.exit /* : unknown */() /* : unknown */;
    }
    return appended /* : DivideState */;
}
/* private static */ parseDataAccess(state, CompileState, input, string, depth, number);
Option < [CompileState, Value] > {
    return /* last */(input) { } /* : string */, /* : string */ : /* : string */ .strip /* : unknown */() /* : unknown */, ".": ,
}(parentString, rawProperty);
{
    let property = rawProperty /* : unknown */.strip /* : unknown */() /* : unknown */;
    if (!isSymbol /* : (arg0 : string) => boolean */(property /* : unknown */) /* : unknown */) {
        return new None() /* : None */;
    }
    let tuple = parseValue /* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state /* : CompileState */, parentString /* : unknown */, depth /* : number */) /* : [CompileState, Value] */;
    let parent = tuple /* : [CompileState, Value] */[1 /* : number */]() /* : unknown */;
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
    return new Some(new Tuple2Impl(tuple /* : [CompileState, Value] */[0 /* : number */]() /* : unknown */, new DataAccess(parent /* : unknown */, property /* : unknown */, type /* : Type */) /* : DataAccess */) /* : Tuple2Impl */) /* : Some */;
}
;
/* private static */ parseString(state, CompileState, input, string);
Option < [CompileState, Value] > {
    let, stripped = input /* : string */.strip /* : unknown */() /* : unknown */,
    if(stripped) { } /* : unknown */, /* : unknown */ : /* : unknown */ .startsWith /* : unknown */("\"") /* : unknown */ && stripped /* : unknown */.endsWith /* : unknown */("\"") /* : unknown */
};
{
    return new Some(new Tuple2Impl(state /* : CompileState */, new StringValue(stripped /* : unknown */) /* : StringValue */) /* : Tuple2Impl */) /* : Some */;
}
return new None() /* : None */;
/* private static */ parseSymbolValue(state, CompileState, value, string);
Option < [CompileState, Value] > {
    let, stripped = value /* : string */.strip /* : unknown */() /* : unknown */,
    if(isSymbol) { }
} /* : (arg0 : string) => boolean */(stripped /* : unknown */); /* : boolean */
{
    if ( /* state.resolveValue(stripped) instanceof Some */( /* var type */) /* : unknown */) {
        return new Some(new Tuple2Impl(state /* : CompileState */, new SymbolValue(stripped /* : unknown */) /* : SymbolValue */) /* : Tuple2Impl */) /* : Some */;
    }
    if ( /* state.resolveType(stripped) instanceof Some */( /* var type */) /* : unknown */) {
        return new Some(new Tuple2Impl(state /* : CompileState */, new SymbolValue(stripped /* : unknown */) /* : SymbolValue */) /* : Tuple2Impl */) /* : Some */;
    }
    return new Some(new Tuple2Impl(state /* : CompileState */, new Placeholder(stripped /* : unknown */) /* : Placeholder */) /* : Tuple2Impl */) /* : Some */;
}
return new None() /* : None */;
/* private static */ parseOperation(state, CompileState, value, string, depth, number, operator);
Option < [CompileState, Value] > {
    return /* first */(value /* : string */, operator) { } /* : content-start Operator content-end */, /* : content-start Operator content-end */ : /* : content-start Operator content-end */ .sourceRepresentation /* : unknown */,
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
    return /* parseValues */ (state /* : CompileState */, input /* : string */, mapper /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]> */) /* : unknown */.orElseGet /* : unknown */(() => new Tuple2Impl(state /* : CompileState */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */) /* : Tuple2Impl */) /* : unknown */;
}
/* private static  */ parseValues(state, CompileState, input, string, mapper, (arg0, arg1) => Option);
Option < [CompileState, (List)] > {
    return /* parseValuesWithIndices */(state /* : CompileState */, input /* : string */) { }
}(state1, tuple);
mapper /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]> */(state1 /* : unknown */, tuple /* : unknown */.right /* : unknown */() /* : unknown */); /* : Option<[CompileState, T]> */
;
/* private static  */ parseValuesWithIndices(state, CompileState, input, string, mapper, (arg0, arg1) => Option);
Option < [CompileState, (List)] > {
    return: parseAllWithIndices /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : DivideState, arg1 : string) => DivideState, arg3 : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]>) => Option<[CompileState, List<T>]> */(state /* : CompileState */, input /* : string */, Main /* : Main */.foldValueChar /* : unknown */, mapper /* : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]> */) /* : Option<[CompileState, List<T>]> */
};
/* private static */ parseParameter(state, CompileState, input, string);
[CompileState, Parameter];
{
    if (input /* : string */.isBlank /* : unknown */() /* : unknown */) {
        return new Tuple2Impl(state /* : CompileState */, new Whitespace() /* : Whitespace */) /* : Tuple2Impl */;
    }
    return /* parseDefinition */ (state /* : CompileState */, input /* : string */) /* : unknown */.map /* : unknown */((tuple) => new Tuple2Impl(tuple /* : unknown */.left /* : unknown */() /* : unknown */, tuple /* : unknown */.right /* : unknown */() /* : unknown */) /* : Tuple2Impl<CompileState, Parameter> */) /* : unknown */.orElseGet /* : unknown */(() => new Tuple2Impl(state /* : CompileState */, new Placeholder(input /* : string */) /* : Placeholder */) /* : Tuple2Impl */) /* : unknown */;
}
/* private static */ createIndent(depth, number);
string;
{
    return "\n" + "\t".repeat /* : unknown */(depth /* : number */) /* : unknown */;
}
/* private static */ parseDefinitionStatement(input, string, depth, number, state, CompileState);
Option < [CompileState, IncompleteClassSegment] > {
    return: suffix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(input /* : string */.strip /* : unknown */() /* : unknown */, ";", (withoutEnd) => {
        return /* parseDefinition */ (state /* : CompileState */, withoutEnd /* : string */) /* : unknown */.map /* : unknown */((result) => {
            let definition = result /* : unknown */.right /* : unknown */() /* : unknown */;
            return new Tuple2Impl(result /* : unknown */.left /* : unknown */() /* : unknown */, new ClassDefinition(definition /* : unknown */, depth /* : number */) /* : ClassDefinition */) /* : Tuple2Impl */;
        }) /* : unknown */;
    }) /* : Option<T> */
};
/* private static */ parseDefinition(state, CompileState, input, string);
Option < [CompileState, Definition] > {
    return /* last */(input) { } /* : string */, /* : string */ : /* : string */ .strip /* : unknown */() /* : unknown */, " ": ,
}(beforeName, name);
{
    return /* split */ (() => /* toLast */ (beforeName /* : unknown */, " ", Main /* : Main */.foldTypeSeparator /* : unknown */) /* : unknown */, (beforeType, type) => {
        return suffix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(beforeType /* : unknown */.strip /* : unknown */() /* : unknown */, ">", (withoutTypeParamStart) => {
            return /* first */ (withoutTypeParamStart /* : string */, "<", (beforeTypeParams, typeParamsString) => {
                let typeParams = parseValuesOrEmpty /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) => [CompileState, List<T>] */(state /* : CompileState */, typeParamsString /* : unknown */, (state1, s) => new Some(new Tuple2Impl(state1 /* : unknown */, s /* : unknown */.strip /* : unknown */() /* : unknown */) /* : Tuple2Impl */) /* : Some */) /* : [CompileState, List<T>] */;
                return /* assembleDefinition */ (typeParams /* : [CompileState, List<T>] */[0 /* : number */]() /* : unknown */, new Some(beforeTypeParams /* : unknown */) /* : Some<string> */, name /* : unknown */, typeParams /* : [CompileState, List<T>] */[1 /* : number */]() /* : unknown */, type /* : unknown */) /* : unknown */;
            }) /* : unknown */;
        }) /* : Option<T> */.or /* : (arg0 : () => Option<T>) => Option<T> */(() => {
            return /* assembleDefinition */ (state /* : CompileState */, new Some(beforeType /* : unknown */) /* : Some<string> */, name /* : unknown */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */, type /* : unknown */) /* : unknown */;
        }) /* : Option<T> */;
    }) /* : unknown */.or /* : unknown */(() => {
        return /* assembleDefinition */ (state /* : CompileState */, new None() /* : None<string> */, name /* : unknown */, Lists /* : Lists */.empty /* : () => List<T> */() /* : List<T> */, beforeName /* : unknown */) /* : unknown */;
    }) /* : unknown */;
}
;
/* private static */ toLast(input, string, separator, string, folder, (arg0, arg1) => DivideState);
Option < [string, string] > {
    let, divisions: (List) = divideAll /* : (arg0 : string, arg1 : (arg0 : DivideState, arg1 : string) => DivideState) => List<string> */(input /* : string */, folder /* : (arg0 : DivideState, arg1 : string) => DivideState */) /* : List<string> */,
    return: divisions /* : List<string> */.removeLast /* : () => Option<[List<T>, T]> */() /* : Option<[List<T>, T]> */.map /* : (arg0 : (arg0 : [List<T>, T]) => R) => Option<R> */((removed) => {
        let left = removed /* : [List<T>, T] */[0 /* : number */]() /* : unknown */.iterate /* : unknown */() /* : unknown */.collect /* : unknown */(new Joiner(separator /* : string */) /* : Joiner */) /* : unknown */.orElse /* : unknown */("") /* : unknown */;
        let right = removed /* : [List<T>, T] */[1 /* : number */]() /* : unknown */;
        return new Tuple2Impl(left /* : unknown */, right /* : unknown */) /* : Tuple2Impl */;
    }) /* : Option<R> */
};
/* private static */ foldTypeSeparator(state, DivideState, c, string);
DivideState;
{
    if (c /* : string */ ===  /*  ' '  */ && state /* : DivideState */.isLevel /* : unknown */() /* : unknown */) {
        return state /* : DivideState */.advance /* : () => DivideState */() /* : DivideState */;
    }
    let appended = state /* : DivideState */.append /* : (arg0 : string) => DivideState */(c /* : string */) /* : DivideState */;
    if (c /* : string */ ===  /*  '<' */) {
        return appended /* : DivideState */.enter /* : unknown */() /* : unknown */;
    }
    if (c /* : string */ ===  /*  '>' */) {
        return appended /* : DivideState */.exit /* : unknown */() /* : unknown */;
    }
    return appended /* : DivideState */;
}
/* private static */ assembleDefinition(state, CompileState, beforeTypeParams, (Option), name, string, typeParams, (List), type, string);
Option < [CompileState, Definition] > {
    return /* parseType */(state) { } /* : CompileState */, /* : CompileState */ : /* : CompileState */ .withTypeParams /* : (arg0 : List<string>) => CompileState */(typeParams /* : List<string> */) /* : CompileState */, type /* : string */, /* : unknown */ : /* : unknown */ .map /* : unknown */((type1) => {
        let node = new ImmutableDefinition(beforeTypeParams /* : Option<string> */, name /* : string */.strip /* : unknown */() /* : unknown */, type1 /* : unknown */.right /* : unknown */() /* : unknown */, typeParams /* : List<string> */) /* : ImmutableDefinition */;
        return new Tuple2Impl(type1 /* : unknown */.left /* : unknown */() /* : unknown */, node /* : ImmutableDefinition */) /* : Tuple2Impl */;
    }) /* : unknown */
};
/* private static */ foldValueChar(state, DivideState, c, string);
DivideState;
{
    if (c /* : string */ ===  /*  ','  */ && state /* : DivideState */.isLevel /* : unknown */() /* : unknown */) {
        return state /* : DivideState */.advance /* : () => DivideState */() /* : DivideState */;
    }
    let appended = state /* : DivideState */.append /* : (arg0 : string) => DivideState */(c /* : string */) /* : DivideState */;
    if (c /* : string */ === /*  ' */ - /* ' */) {
        let peeked = appended /* : DivideState */.peek /* : unknown */() /* : unknown */;
        if (peeked /* : unknown */ ===  /*  '>' */) {
            return appended /* : DivideState */.popAndAppendToOption /* : unknown */() /* : unknown */.orElse /* : unknown */(appended /* : DivideState */) /* : unknown */;
        }
        else {
            return appended /* : DivideState */;
        }
    }
    if (c /* : string */ ===  /*  '<'  */ || c /* : string */ ===  /*  '('  */ || c /* : string */ ===  /*  '{' */) {
        return appended /* : DivideState */.enter /* : unknown */() /* : unknown */;
    }
    if (c /* : string */ ===  /*  '>'  */ || c /* : string */ ===  /*  ')'  */ || c /* : string */ ===  /*  '}' */) {
        return appended /* : DivideState */.exit /* : unknown */() /* : unknown */;
    }
    return appended /* : DivideState */;
}
/* private static */ parseType(state, CompileState, input, string);
Option < [CompileState, Type] > {
    let, stripped = input /* : string */.strip /* : unknown */() /* : unknown */,
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
return /* parseTemplate */ (state /* : CompileState */, input /* : string */) /* : unknown */.or /* : unknown */(() => /* varArgs */ (state /* : CompileState */, input /* : string */) /* : unknown */) /* : unknown */;
/* private static */ varArgs(state, CompileState, input, string);
Option < [CompileState, Type] > {
    return: suffix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(input /* : string */, "...", (s) => {
        return parseType /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Type]> */(state /* : CompileState */, s /* : string */) /* : Option<[CompileState, Type]> */.map /* : (arg0 : (arg0 : [CompileState, Type]) => R) => Option<R> */((inner) => {
            let newState = inner /* : [CompileState, Type] */[0 /* : number */]() /* : unknown */;
            let child = inner /* : [CompileState, Type] */[1 /* : number */]() /* : unknown */;
            return new Tuple2Impl(newState /* : unknown */, new ArrayType(child /* : unknown */) /* : ArrayType */) /* : Tuple2Impl */;
        }) /* : Option<R> */;
    }) /* : Option<T> */
};
/* private static */ assembleTemplate(base, string, state, CompileState, arguments, (List));
[CompileState, Type];
{
    let children = arguments /* : List<Argument> */.iterate /* : () => Iterator<T> */() /* : Iterator<T> */.map /* : (arg0 : (arg0 : T) => R) => Iterator<R> */(Main /* : Main */.retainType /* : unknown */) /* : Iterator<R> */.flatMap /* : unknown */(Iterators /* : Iterators */.fromOption /* : unknown */) /* : unknown */.collect /* : unknown */(new ListCollector() /* : ListCollector */) /* : unknown */;
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
    return: suffix /* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(input /* : string */.strip /* : unknown */() /* : unknown */, ">", (withoutEnd) => {
        return /* first */ (withoutEnd /* : string */, "<", (base, argumentsString) => {
            let strippedBase = base /* : unknown */.strip /* : unknown */() /* : unknown */;
            return parseValues /* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) => Option<[CompileState, List<T>]> */(state /* : CompileState */, argumentsString /* : unknown */, Main /* : Main */.argument /* : unknown */) /* : Option<[CompileState, List<T>]> */.map /* : (arg0 : (arg0 : [CompileState, List<T>]) => R) => Option<R> */((argumentsTuple) => {
                return assembleTemplate /* : (arg0 : string, arg1 : CompileState, arg2 : List<Argument>) => [CompileState, Type] */(strippedBase /* : unknown */, argumentsTuple /* : [CompileState, List<T>] */[0 /* : number */]() /* : unknown */, argumentsTuple /* : [CompileState, List<T>] */[1 /* : number */]() /* : unknown */) /* : [CompileState, Type] */;
            }) /* : Option<R> */;
        }) /* : unknown */;
    }) /* : Option<T> */
};
/* private static */ retainType(argument, Argument);
Option < Type > {
    if(argument) { } /* : Argument */, /* : Argument */ : /* : Argument */ ._variant /* : unknown */ === ArgumentVariant.Type /* : unknown */
};
{
    return new Some( /* type */) /* : Some */;
}
{
    return new None() /* : None<Type> */;
}
/* private static */ argument(state, CompileState, input, string);
Option < [CompileState, Argument] > {
    if(input) { } /* : string */, /* : string */ : /* : string */ .isBlank /* : unknown */() /* : unknown */
};
{
    return new Some(new Tuple2Impl(state /* : CompileState */, new Whitespace() /* : Whitespace */) /* : Tuple2Impl */) /* : Some */;
}
return parseType /* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Type]> */(state /* : CompileState */, input /* : string */) /* : Option<[CompileState, Type]> */.map /* : (arg0 : (arg0 : [CompileState, Type]) => R) => Option<R> */((tuple) => new Tuple2Impl(tuple /* : [CompileState, Type] */[0 /* : number */]() /* : unknown */, tuple /* : [CompileState, Type] */[1 /* : number */]() /* : unknown */) /* : Tuple2Impl */) /* : Option<R> */;
/* private static  */ last(input, string, infix, string, mapper, (arg0, arg1) => Option);
Option < T > {
    return: infix /* : string */(input /* : string */, infix /* : string */, Main /* : Main */.findLast /* : unknown */, mapper /* : (arg0 : string, arg1 : string) => Option<T> */) /* : unknown */
};
/* private static */ findLast(input, string, infix, string);
Option < number > {
    let, index = input /* : string */.lastIndexOf /* : unknown */(infix /* : string */) /* : unknown */,
    if(index) { }
} /* : unknown */ === -1; /* : number */
{
    return new None() /* : None<number> */;
}
return new Some(index /* : unknown */) /* : Some */;
/* private static  */ first(input, string, infix, string, mapper, (arg0, arg1) => Option);
Option < T > {
    return: infix /* : string */(input /* : string */, infix /* : string */, Main /* : Main */.findFirst /* : unknown */, mapper /* : (arg0 : string, arg1 : string) => Option<T> */) /* : unknown */
};
/* private static  */ split(splitter, () => Option, splitMapper, (arg0, arg1) => Option);
Option < T > {
    return: splitter /* : () => Option<[string, string]> */() /* : Option<[string, string]> */.flatMap /* : (arg0 : (arg0 : [string, string]) => Option<R>) => Option<R> */((splitTuple) => splitMapper /* : (arg0 : string, arg1 : string) => Option<T> */(splitTuple /* : [string, string] */[0 /* : number */]() /* : unknown */, splitTuple /* : [string, string] */[1 /* : number */]() /* : unknown */) /* : Option<T> */) /* : Option<R> */
};
/* private static  */ infix(input, string, infix, string, locator, (arg0, arg1) => Option, mapper, (arg0, arg1) => Option);
Option < T > {
    return: split /* : (arg0 : () => Option<[string, string]>, arg1 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(() => locator /* : (arg0 : string, arg1 : string) => Option<number> */(input /* : string */, infix /* : string */) /* : Option<number> */.map /* : (arg0 : (arg0 : number) => R) => Option<R> */((index) => {
        let left = input /* : string */.substring /* : unknown */(0 /* : number */, index /* : number */) /* : unknown */;
        let right = input /* : string */.substring /* : unknown */(index /* : number */ + infix /* : string */.length /* : unknown */() /* : unknown */) /* : unknown */;
        return new Tuple2Impl(left /* : unknown */, right /* : unknown */) /* : Tuple2Impl */;
    }) /* : Option<R> */, mapper /* : (arg0 : string, arg1 : string) => Option<T> */) /* : Option<T> */
};
/* private static */ findFirst(input, string, infix, string);
Option < number > {
    let, index = input /* : string */.indexOf /* : unknown */(infix /* : string */) /* : unknown */,
    if(index) { }
} /* : unknown */ === -1; /* : number */
{
    return new None() /* : None<number> */;
}
return new Some(index /* : unknown */) /* : Some */;
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
        return "";
    }
    return generatePlaceholder /* : (arg0 : string) => string */(": " + type /* : Type */.generate /* : unknown */() /* : unknown */) /* : string */;
}
/*  */ 
