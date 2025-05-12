/* private */interface Tuple2<A, B>/*   */ {
	left() : A {;
	}
	right() : B {;
	}
}
/* private */interface Option<T>/*   */ {
	map<R>(mapper : (arg0 : T) => R) : Option<R> {;
	}
	isPresent() : boolean {;
	}
	orElse(other : T) : T {;
	}
	filter(predicate : (arg0 : T) => boolean) : Option<T> {;
	}
	orElseGet(supplier : () => T) : T {;
	}
	or(other : () => Option<T>) : Option<T> {;
	}
	flatMap<R>(mapper : (arg0 : T) => Option<R>) : Option<R> {;
	}
	isEmpty() : boolean {;
	}
	and<R>(other : () => Option<R>) : Option<[T, R]> {;
	}
}
/* private */interface Collector<T, C>/*   */ {
	createInitial() : C {;
	}
	fold(current : C, element : T) : C {;
	}
}
/* private */interface Iterator<T>/*   */ {
	fold<R>(initial : R, folder : (arg0 : R, arg1 : T) => R) : R {;
	}
	map<R>(mapper : (arg0 : T) => R) : Iterator<R> {;
	}
	collect<R>(collector : Collector<T, R>) : R {;
	}
	filter(predicate : (arg0 : T) => boolean) : Iterator<T> {;
	}
	next() : Option<T> {;
	}
	flatMap<R>(f : (arg0 : T) => Iterator<R>) : Iterator<R> {;
	}
	zip<R>(other : Iterator<R>) : Iterator<[T, R]> {;
	}
}
/* private */interface List<T>/*   */ {
	addLast(element : T) : List<T> {;
	}
	iterate() : Iterator<T> {;
	}
	removeLast() : Option<[List<T>, T]> {;
	}
	get(index : number) : Option<T> {;
	}
	size() : number {;
	}
	isEmpty() : boolean {;
	}
	addFirst(element : T) : List<T> {;
	}
	iterateWithIndices() : Iterator<[number, T]> {;
	}
	removeFirst() : Option<[T, List<T>]> {;
	}
	addAllLast(others : List<T>) : List<T> {;
	}
	last() : Option<T> {;
	}
}
/* private */interface Head<T>/*   */ {
	next() : Option<T> {;
	}
}
/* private */interface Map<K, V>/*   */ {
	find(key : K) : Option<V> {;
	}
	with(key : K, value : V) : Map<K, V> {;
	}
}
/* private */interface Type/*  */ {
	generate() : string {;
	}
	replace(mapping : Map<string, Type>) : Type {;
	}
	findName() : Option<string> {;
	}
}
/* private */interface Argument/*  */ {
}
/* private */interface Parameter/*  */ {
}
/* private sealed */interface Value/*  */ {
	generate() : string {;
	}
	type() : Type {;
	}
}
/* private */interface LambdaValue/*  */ {
	generate() : string {;
	}
}
/* private sealed */interface Caller/*  */ {
	generate() : string {;
	}
}
/* private */interface FindableType/*  */ {
	typeParams() : List<string> {;
	}
	find(name : string) : Option<Type> {;
	}
	name() : string {;
	}
}
/* private */interface Definition/*  */ {
	generate() : string {;
	}
	generateType() : string {;
	}
	joinBefore() : string {;
	}
	joinTypeParams() : string {;
	}
	mapType(mapper : (arg0 : Type) => Type) : Definition {;
	}
	/* @Override
        */ toString() : string {;
	}
	/* @Override
        */ generateWithParams(joinedParameters : string) : string {;
	}
	/* @Override
        */ createDefinition(paramTypes : List<Type>) : Definition {;
	}
	maybeBefore() : Option<string> {;
	}
	name() : string {;
	}
	type() : Type {;
	}
	typeParams() : List<string> {;
	}
}
/* private */interface Header/*  */ {
	createDefinition(paramTypes : List<Type>) : Definition {;
	}
	generateWithParams(joinedParameters : string) : string {;
	}
}
/* private */interface ClassSegment/*  */ {
	generate() : string {;
	}
}
/* private */interface FunctionSegment/*  */ {
	generate() : string {;
	}
}
/* private static */class None<T>/*  */ {
	/* @Override
        public  */ map<R>(mapper : (arg0 : T) => R) : Option<R> {
		return new None()/* : None */;
	}
	/* @Override
        public */ isPresent() : boolean {
		return false;
	}
	/* @Override
        public */ orElse(other : T) : T {
		return other/* : T */;
	}
	/* @Override
        public */ filter(predicate : (arg0 : T) => boolean) : Option<T> {
		return new None()/* : None */;
	}
	/* @Override
        public */ orElseGet(supplier : () => T) : T {
		return supplier/* : () => T */()/* : T */;
	}
	/* @Override
        public */ or(other : () => Option<T>) : Option<T> {
		return other/* : T */.get/* : unknown */()/* : unknown */;
	}
	/* @Override
        public  */ flatMap<R>(mapper : (arg0 : T) => Option<R>) : Option<R> {
		return new None()/* : None */;
	}
	/* @Override
        public */ isEmpty() : boolean {
		return true;
	}
	/* @Override
        public  */ and<R>(other : () => Option<R>) : Option<[T, R]> {
		return new None()/* : None */;
	}
}
/* private */class Tuple2Impl<A, B>/*  */ {
	constructor (left : A, right : B) {
	}
}
/* private */class Some<T>/*  */ {
	constructor (value : T) {
	}
	/* @Override
        public  */ map<R>(mapper : (arg0 : T) => R) : Option<R> {
		return new Some(mapper/* : (arg0 : T) => R */(/* this */.value/* : unknown */)/* : R */)/* : Some */;
	}
	/* @Override
        public */ isPresent() : boolean {
		return true;
	}
	/* @Override
        public */ orElse(other : T) : T {
		return /* this */.value/* : unknown */;
	}
	/* @Override
        public */ filter(predicate : (arg0 : T) => boolean) : Option<T> {
		if (predicate/* : (arg0 : T) => boolean */(/* this */.value/* : unknown */)/* : boolean */){magma.Main$Lists$JVMList@3ada9e37
		}
		return new None()/* : None */;
	}
	/* @Override
        public */ orElseGet(supplier : () => T) : T {
		return /* this */.value/* : unknown */;
	}
	/* @Override
        public */ or(other : () => Option<T>) : Option<T> {
		return /* this */;
	}
	/* @Override
        public  */ flatMap<R>(mapper : (arg0 : T) => Option<R>) : Option<R> {
		return mapper/* : (arg0 : T) => R */(/* this */.value/* : unknown */)/* : R */;
	}
	/* @Override
        public */ isEmpty() : boolean {
		return false;
	}
	/* @Override
        public  */ and<R>(other : () => Option<R>) : Option<[T, R]> {
		return other/* : T */.get/* : unknown */()/* : unknown */.map/* : unknown */((otherValue) => new Tuple2Impl(/* this */.value/* : unknown */, otherValue/* : unknown */)/* : Tuple2Impl */)/* : unknown */;
	}
}
/* private static */class SingleHead<T>/*  */ {
	/* private final */ value : T;
	/* private */ retrieved : boolean;
	constructor (value : T) {
		/* this */.value/* : unknown */ = value/* : T */;
		/* this */.retrieved/* : unknown */ = false;
	}
	/* @Override
        public */ next() : Option<T> {
		if (/* this */.retrieved/* : unknown */){magma.Main$Lists$JVMList@1761e840
		}
		/* this */.retrieved/* : unknown */ = true;
		return new Some(/* this */.value/* : unknown */)/* : Some */;
	}
}
/* private static */class EmptyHead<T>/*  */ {
	/* @Override
        public */ next() : Option<T> {
		return new None()/* : None */;
	}
}
/* private */class HeadedIterator<T>/*  */ {
	constructor (head : Head<T>) {
	}
	/* @Override
        public  */ fold<R>(initial : R, folder : (arg0 : R, arg1 : T) => R) : R {
		let current : R = initial/* : R */;
		while (true){magma.Main$Lists$JVMList@5b1d2887
		}
	}
	/* @Override
        public  */ map<R>(mapper : (arg0 : T) => R) : Iterator<R> {
		return new HeadedIterator(() => /* this */.head/* : unknown */.next/* : unknown */()/* : unknown */.map/* : unknown */(mapper/* : (arg0 : T) => R */)/* : unknown */)/* : HeadedIterator */;
	}
	/* @Override
        public  */ collect<R>(collector : Collector<T, R>) : R {
		return /* this */.fold/* : unknown */(collector/* : Collector<T, R> */.createInitial/* : () => C */()/* : C */, collector/* : Collector<T, R> */.fold/* : unknown */)/* : unknown */;
	}
	/* @Override
        public */ filter(predicate : (arg0 : T) => boolean) : Iterator<T> {
		return /* this */.flatMap/* : unknown */((element) => {
			if (predicate/* : (arg0 : T) => boolean */(element/* : unknown */)/* : boolean */){magma.Main$Lists$JVMList@24273305
			}
			return new HeadedIterator(new EmptyHead()/* : EmptyHead */)/* : HeadedIterator */;
		})/* : unknown */;
	}
	/* @Override
        public */ next() : Option<T> {
		return /* this */.head/* : unknown */.next/* : unknown */()/* : unknown */;
	}
	/* @Override
        public  */ flatMap<R>(f : (arg0 : T) => Iterator<R>) : Iterator<R> {
		return new HeadedIterator(new /* FlatMapHead */(/* this */.head/* : unknown */, f/* : (arg0 : T) => Iterator<R> */)/* : content-start FlatMapHead content-end */)/* : HeadedIterator */;
	}
	/* @Override
        public  */ zip<R>(other : Iterator<R>) : Iterator<[T, R]> {
		return new HeadedIterator(() => HeadedIterator/* : HeadedIterator */.this/* : unknown */.head/* : unknown */.next/* : unknown */()/* : unknown */.and/* : unknown */(other/* : T */.next/* : unknown */)/* : unknown */)/* : HeadedIterator */;
	}
}
/* private static */class RangeHead/*  */ {
	/* private final */ length : number;
	/* private */ counter : number;
	RangeHead(length : number) : /* public */ {
		/* this */.length/* : unknown */ = length/* : number */;
	}
	/* @Override
        public */ next() : Option<number> {
		if (/* this.counter < this */.length/* : unknown */){magma.Main$Lists$JVMList@46f5f779
		}
		return new None()/* : None */;
	}
}
/* private static final */class JVMList<T>/*  */ {
	/* private final */ elements : /* java.util.List */<T>;
	JVMList(elements : /* java.util.List */<T>) : /* private */ {
		/* this */.elements/* : unknown */ = elements/* : content-start java.util.List content-end<T> */;
	}
	JVMList() : /* public */ {
		/* this(new ArrayList<>()) */;
	}
	/* @Override
            public */ addLast(element : T) : List<T> {
		/* this.elements.add(element) */;
		return /* this */;
	}
	/* @Override
            public */ iterate() : Iterator<T> {
		return /* this */.iterateWithIndices/* : unknown */()/* : unknown */.map/* : unknown */(Tuple2/* : Tuple2 */.right/* : unknown */)/* : unknown */;
	}
	/* @Override
            public */ removeLast() : Option<[List<T>, T]> {
		if (/* this */.elements/* : unknown */.isEmpty/* : unknown */()/* : unknown */){magma.Main$Lists$JVMList@18e8568
		}
		let slice = /* this */.elements/* : unknown */.subList/* : unknown */(0/* : number */, /* this */.elements/* : unknown */.size/* : unknown */()/* : unknown */ - 1/* : number */)/* : unknown */;
		let last = /* this */.elements/* : unknown */.getLast/* : unknown */()/* : unknown */;
		return new Some(new Tuple2Impl<List<T>, T>(new JVMList(slice/* : unknown */)/* : JVMList */, last/* : () => Option<T> */)/* : Tuple2Impl<List<T>, T> */)/* : Some */;
	}
	/* @Override
            public */ size() : number {
		return /* this */.elements/* : unknown */.size/* : unknown */()/* : unknown */;
	}
	/* @Override
            public */ isEmpty() : boolean {
		return /* this */.elements/* : unknown */.isEmpty/* : unknown */()/* : unknown */;
	}
	/* @Override
            public */ addFirst(element : T) : List<T> {
		/* this.elements.addFirst(element) */;
		return /* this */;
	}
	/* @Override
            public */ iterateWithIndices() : Iterator<[number, T]> {
		return new HeadedIterator(new RangeHead(/* this */.elements/* : unknown */.size/* : unknown */()/* : unknown */)/* : RangeHead */)/* : HeadedIterator */.map/* : (arg0 : (arg0 : T) => R) => Option<R> */((index : T) => new Tuple2Impl(index/* : T */, /* this */.elements/* : unknown */.get/* : unknown */(index/* : T */)/* : unknown */)/* : Tuple2Impl */)/* : Option<R> */;
	}
	/* @Override
            public */ removeFirst() : Option<[T, List<T>]> {
		if (/* this */.elements/* : unknown */.isEmpty/* : unknown */()/* : unknown */){magma.Main$Lists$JVMList@33e5ccce
		}
		let first = /* this */.elements/* : unknown */.getFirst/* : unknown */()/* : unknown */;
		let slice = /* this */.elements/* : unknown */.subList/* : unknown */(1/* : number */, /* this */.elements/* : unknown */.size/* : unknown */()/* : unknown */)/* : unknown */;
		return new Some(new Tuple2Impl<T, List<T>>(first/* : unknown */, new JVMList(slice/* : unknown */)/* : JVMList */)/* : Tuple2Impl<T, List<T>> */)/* : Some */;
	}
	/* @Override
            public */ addAllLast(others : List<T>) : List<T> {
		let initial : List<T> = /* this */;
		return others/* : List<T> */.iterate/* : () => Iterator<T> */()/* : Iterator<T> */.fold/* : (arg0 : C, arg1 : T) => C */(initial/* : R */, List/* : List */.addLast/* : unknown */)/* : C */;
	}
	/* @Override
            public */ last() : Option<T> {
		if (/* this */.elements/* : unknown */.isEmpty/* : unknown */()/* : unknown */){magma.Main$Lists$JVMList@5a42bbf4
		}
		return new Some(/* this */.elements/* : unknown */.getLast/* : unknown */()/* : unknown */)/* : Some */;
	}
	/* @Override
            public */ get(index : number) : Option<T> {
		if (/* index < this */.elements/* : unknown */.size/* : unknown */()/* : unknown */){magma.Main$Lists$JVMList@270421f5
		}
		else {magma.Main$Lists$JVMList@52d455b8
		}
	}
}
/* private static */class Lists/*  */ {
	/* public static  */ empty<T>() : List<T> {
		return new JVMList()/* : JVMList */;
	}
	/* public static  */ of<T>(elements : T[]) : List<T> {
		return new JVMList(new /* ArrayList */(/* Arrays */.asList/* : unknown */(elements/* : content-start java.util.List content-end<T> */)/* : unknown */)/* : content-start ArrayList content-end */)/* : JVMList */;
	}
}
/* private */class ImmutableDefinition/*  */ {
	constructor (maybeBefore : Option<string>, name : string, type : Type, typeParams : List<string>) {
	}
	/* public static */ createSimpleDefinition(name : string, type : Type) : Definition {
		return new ImmutableDefinition(new None()/* : None */, name/* : () => string */, type/* : () => Type */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */)/* : ImmutableDefinition */;
	}
	/* @Override
        public */ generate() : string {
		return /* this */.generateWithParams/* : unknown */("")/* : unknown */;
	}
	/* @Override
        public */ generateType() : string {
		if (/* this */.type/* : unknown */.equals/* : unknown */(/* Primitive */.Unknown/* : unknown */)/* : unknown */){magma.Main$Lists$JVMList@6aceb1a5
		}
		return " : " + /* this */.type/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
	/* @Override
        public */ joinBefore() : string {
		return !/* isDebug ? "" : this */.maybeBefore/* : unknown */.filter/* : unknown */((value) => !value/* : T */.isEmpty/* : unknown */()/* : unknown */)/* : unknown */.map/* : unknown */(/* Main */.generatePlaceholder/* : unknown */)/* : unknown */.map/* : unknown */((inner) => inner/* : unknown */ + " ")/* : unknown */.orElse/* : unknown */("")/* : unknown */;
	}
	/* @Override
        public */ joinTypeParams() : string {
		return /* this */.typeParams/* : unknown */.iterate/* : unknown */()/* : unknown */.collect/* : unknown */(new /* Joiner */()/* : content-start Joiner content-end */)/* : unknown */.map/* : unknown */((inner) => "<" + inner + ">")/* : unknown */.orElse/* : unknown */("")/* : unknown */;
	}
	/* @Override
        public */ mapType(mapper : (arg0 : Type) => Type) : Definition {
		return new ImmutableDefinition(/* this */.maybeBefore/* : unknown */, /* this */.name/* : unknown */, mapper/* : (arg0 : T) => R */(/* this */.type/* : unknown */)/* : R */, /* this */.typeParams/* : unknown */)/* : ImmutableDefinition */;
	}
	/* @Override
        public */ toString() : string {
		return "Definition[" + "maybeBefore=" + /* this */.maybeBefore/* : unknown */ + ", " + "name=" + /* this */.name/* : unknown */ + ", " + "type=" + /* this */.type/* : unknown */ + ", " + "typeParams=" + /* this */.typeParams/* : unknown */ + /*  ']' */;
	}
	/* @Override
        public */ generateWithParams(joinedParameters : string) : string {
		let joined = /* this */.joinTypeParams/* : unknown */()/* : unknown */;
		let before = /* this */.joinBefore/* : unknown */()/* : unknown */;
		let typeString = /* this */.generateType/* : unknown */()/* : unknown */;
		return before/* : unknown */ + /* this */.name/* : unknown */ + joined/* : unknown */ + joinedParameters/* : string */ + typeString/* : unknown */;
	}
	/* @Override
        public */ createDefinition(paramTypes : List<Type>) : Definition {
		return ImmutableDefinition/* : ImmutableDefinition */.createSimpleDefinition/* : (arg0 : string, arg1 : Type) => Definition */(/* this */.name/* : unknown */, new /* FunctionType */(paramTypes/* : List<Type> */, /* this */.type/* : unknown */)/* : content-start FunctionType content-end */)/* : Definition */;
	}
}
/* private */class ObjectType/*  */ {
	constructor (name : string, typeParams : List<string>, definitions : List<Definition>) {
	}
	/* @Override
        public */ generate() : string {
		return /* this */.name/* : unknown */;
	}
	/* @Override
        public */ replace(mapping : Map<string, Type>) : Type {
		return new ObjectType(/* this */.name/* : unknown */, /* this */.typeParams/* : unknown */, /* this */.definitions/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */((definition) => definition/* : unknown */.mapType/* : unknown */((type) => type/* : () => Type */(mapping/* : Map<string, Type> */)/* : Type */)/* : unknown */)/* : unknown */.collect/* : unknown */(new /* ListCollector */()/* : content-start ListCollector content-end */)/* : unknown */)/* : ObjectType */;
	}
	/* @Override
        public */ find(name : string) : Option<Type> {
		return /* this */.definitions/* : unknown */.iterate/* : unknown */()/* : unknown */.filter/* : unknown */((definition) => definition/* : unknown */.name/* : unknown */()/* : unknown */.equals/* : unknown */(name/* : () => string */)/* : unknown */)/* : unknown */.map/* : unknown */(Definition/* : Definition */.type/* : unknown */)/* : unknown */.next/* : unknown */()/* : unknown */;
	}
	/* @Override
        public */ findName() : Option<string> {
		return new Some(/* this */.name/* : unknown */)/* : Some */;
	}
}
/* private */class TypeParam/*  */ {
	constructor (value : string) {
	}
	/* @Override
        public */ generate() : string {
		return /* this */.value/* : unknown */;
	}
	/* @Override
        public */ replace(mapping : Map<string, Type>) : Type {
		return mapping/* : Map<string, Type> */.find/* : (arg0 : string) => Option<V> */(/* this */.value/* : unknown */)/* : Option<V> */.orElse/* : (arg0 : V) => T */(/* this */)/* : T */;
	}
	/* @Override
        public */ findName() : Option<string> {
		return new None()/* : None */;
	}
}
/* private */class CompileState/*  */ {
	constructor (structures : List<string>, definitions : List<Definition>, objectTypes : List<ObjectType>, structNames : List<string>, typeParams : List<string>, typeRegister : Option<Type>) {
	}
	CompileState() : /* public */ {
		/* this(Lists.empty(), Lists.empty(), Lists.empty(), Lists.empty(), Lists.empty(), new None<>()) */;
	}
	/* private */ resolveValue(name : string) : Option<Type> {
		return /* this */.definitions/* : unknown */.iterate/* : unknown */()/* : unknown */.filter/* : unknown */((definition) => definition/* : unknown */.name/* : unknown */()/* : unknown */.equals/* : unknown */(name/* : () => string */)/* : unknown */)/* : unknown */.next/* : unknown */()/* : unknown */.map/* : unknown */(Definition/* : Definition */.type/* : unknown */)/* : unknown */;
	}
	/* public */ addStructure(structure : string) : CompileState {
		return new CompileState(/* this */.structures/* : unknown */.addLast/* : unknown */(structure/* : string */)/* : unknown */, /* this */.definitions/* : unknown */, /* this */.objectTypes/* : unknown */, /* this */.structNames/* : unknown */, /* this */.typeParams/* : unknown */, /* this */.typeRegister/* : unknown */)/* : CompileState */;
	}
	/* public */ withDefinitions(definitions : List<Definition>) : CompileState {
		return new CompileState(/* this */.structures/* : unknown */, /* this */.definitions/* : unknown */.addAllLast/* : unknown */(definitions/* : List<Definition> */)/* : unknown */, /* this */.objectTypes/* : unknown */, /* this */.structNames/* : unknown */, /* this */.typeParams/* : unknown */, /* this */.typeRegister/* : unknown */)/* : CompileState */;
	}
	/* public */ resolveType(name : string) : Option<Type> {
		if (/* this */.structNames/* : unknown */.last/* : unknown */()/* : unknown */.filter/* : unknown */((inner) => inner/* : unknown */.equals/* : unknown */(name/* : () => string */)/* : unknown */)/* : unknown */.isPresent/* : unknown */()/* : unknown */){magma.Main$Lists$JVMList@2d6d8735
		}
		let maybeTypeParam = /* this */.typeParams/* : unknown */.iterate/* : unknown */()/* : unknown */.filter/* : unknown */((param) => param/* : unknown */.equals/* : unknown */(name/* : () => string */)/* : unknown */)/* : unknown */.next/* : unknown */()/* : unknown */;
		if (/* maybeTypeParam instanceof Some */(/* var value */)/* : unknown */){magma.Main$Lists$JVMList@ba4d54
		}
		return /* this */.objectTypes/* : unknown */.iterate/* : unknown */()/* : unknown */.filter/* : unknown */((type) => type/* : () => Type */.name/* : unknown */.equals/* : unknown */(name/* : () => string */)/* : unknown */)/* : unknown */.next/* : unknown */()/* : unknown */.map/* : unknown */((type) => type/* : () => Type */)/* : unknown */;
	}
	/* public */ addType(type : ObjectType) : CompileState {
		return new CompileState(/* this */.structures/* : unknown */, /* this */.definitions/* : unknown */, /* this */.objectTypes/* : unknown */.addLast/* : unknown */(type/* : () => Type */)/* : unknown */, /* this */.structNames/* : unknown */, /* this */.typeParams/* : unknown */, /* this */.typeRegister/* : unknown */)/* : CompileState */;
	}
	/* public */ withDefinition(definition : Definition) : CompileState {
		return new CompileState(/* this */.structures/* : unknown */, /* this */.definitions/* : unknown */.addLast/* : unknown */(definition/* : unknown */)/* : unknown */, /* this */.objectTypes/* : unknown */, /* this */.structNames/* : unknown */, /* this */.typeParams/* : unknown */, /* this */.typeRegister/* : unknown */)/* : CompileState */;
	}
	/* public */ pushStructName(name : string) : CompileState {
		return new CompileState(/* this */.structures/* : unknown */, /* this */.definitions/* : unknown */, /* this */.objectTypes/* : unknown */, /* this */.structNames/* : unknown */.addLast/* : unknown */(name/* : () => string */)/* : unknown */, /* this */.typeParams/* : unknown */, /* this */.typeRegister/* : unknown */)/* : CompileState */;
	}
	/* public */ withTypeParams(typeParams : List<string>) : CompileState {
		return new CompileState(/* this */.structures/* : unknown */, /* this */.definitions/* : unknown */, /* this */.objectTypes/* : unknown */, /* this */.structNames/* : unknown */, /* this */.typeParams/* : unknown */.addAllLast/* : unknown */(typeParams/* : () => List<string> */)/* : unknown */, /* this */.typeRegister/* : unknown */)/* : CompileState */;
	}
	/* public */ withExpectedType(type : Type) : CompileState {
		return new CompileState(/* this */.structures/* : unknown */, /* this */.definitions/* : unknown */, /* this */.objectTypes/* : unknown */, /* this */.structNames/* : unknown */, /* this */.typeParams/* : unknown */, new Some(type/* : () => Type */)/* : Some */)/* : CompileState */;
	}
	/* public */ popStructName() : CompileState {
		return new CompileState(/* this */.structures/* : unknown */, /* this */.definitions/* : unknown */, /* this */.objectTypes/* : unknown */, /* this */.structNames/* : unknown */.removeLast/* : unknown */()/* : unknown */.map/* : unknown */(Tuple2/* : Tuple2 */.left/* : unknown */)/* : unknown */.orElse/* : unknown */(/* this */.structNames/* : unknown */)/* : unknown */, /* this */.typeParams/* : unknown */, /* this */.typeRegister/* : unknown */)/* : CompileState */;
	}
}
/* private static */class DivideState/*  */ {
	/* private final */ input : string;
	/* private final */ index : number;
	/* private */ depth : number;
	/* private */ segments : List<string>;
	/* private */ buffer : string;
	DivideState(input : string, index : number, segments : List<string>, buffer : string, depth : number) : /* public */ {
		/* this */.segments/* : unknown */ = segments/* : List<string> */;
		/* this */.buffer/* : unknown */ = buffer/* : string */;
		/* this */.depth/* : unknown */ = depth/* : number */;
		/* this */.input/* : unknown */ = input/* : string */;
		/* this */.index/* : unknown */ = index/* : T */;
	}
	DivideState(input : string) : /* public */ {
		/* this(input, 0, Lists.empty(), "", 0) */;
	}
	/* private */ advance() : DivideState {
		/* this */.segments/* : unknown */ = /* this */.segments/* : unknown */.addLast/* : unknown */(/* this */.buffer/* : unknown */)/* : unknown */;
		/* this */.buffer/* : unknown */ = "";
		return /* this */;
	}
	/* private */ append(c : string) : DivideState {
		/* this */.buffer/* : unknown */ = /* this */.buffer/* : unknown */ + c/* : string */;
		return /* this */;
	}
	/* public */ enter() : DivideState {
		/* this.depth++ */;
		return /* this */;
	}
	/* public */ isLevel() : boolean {
		return /* this */.depth/* : unknown */ === 0/* : number */;
	}
	/* public */ exit() : DivideState {
		/* this.depth-- */;
		return /* this */;
	}
	/* public */ isShallow() : boolean {
		return /* this */.depth/* : unknown */ === 1/* : number */;
	}
	/* public */ pop() : Option<[string, DivideState]> {
		if (/* this.index < this */.input/* : unknown */.length/* : unknown */()/* : unknown */){magma.Main$Lists$JVMList@12bc6874
		}
		return new None()/* : None */;
	}
	/* public */ popAndAppendToTuple() : Option<[string, DivideState]> {
		return /* this */.pop/* : unknown */()/* : unknown */.map/* : unknown */((tuple) => {
			let c = tuple/* : unknown */.left/* : unknown */()/* : unknown */;
			let right = tuple/* : unknown */.right/* : unknown */()/* : unknown */;
			return new Tuple2Impl(c/* : string */, right/* : () => B */(c/* : string */)/* : B */)/* : Tuple2Impl */;
		})/* : unknown */;
	}
	/* public */ popAndAppendToOption() : Option<DivideState> {
		return /* this */.popAndAppendToTuple/* : unknown */()/* : unknown */.map/* : unknown */(Tuple2/* : Tuple2 */.right/* : unknown */)/* : unknown */;
	}
	/* public */ peek() : string {
		return /* this */.input/* : unknown */.charAt/* : unknown */(/* this */.index/* : unknown */)/* : unknown */;
	}
}
/* private */class Joiner/*  */ {
	constructor (delimiter : string) {
	}
	Joiner() : /* private */ {
		/* this("") */;
	}
	/* @Override
        public */ createInitial() : Option<string> {
		return new None()/* : None */;
	}
	/* @Override
        public */ fold(current : Option<string>, element : string) : Option<string> {
		return new Some(current/* : R */.map/* : unknown */((inner) => inner/* : unknown */ + /* this */.delimiter/* : unknown */ + element/* : unknown */)/* : unknown */.orElse/* : unknown */(element/* : unknown */)/* : unknown */)/* : Some */;
	}
}
/* private static */class ListCollector<T>/*  */ {
	/* @Override
        public */ createInitial() : List<T> {
		return Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */;
	}
	/* @Override
        public */ fold(current : List<T>, element : T) : List<T> {
		return current/* : R */.addLast/* : unknown */(element/* : unknown */)/* : unknown */;
	}
}
/* private static */class FlatMapHead<T, R>/*  */ {
	/* private final */ mapper : (arg0 : T) => Iterator<R>;
	/* private final */ head : Head<T>;
	/* private */ current : Option<Iterator<R>>;
	FlatMapHead(head : Head<T>, mapper : (arg0 : T) => Iterator<R>) : /* public */ {
		/* this */.mapper/* : unknown */ = mapper/* : (arg0 : T) => R */;
		/* this */.current/* : unknown */ = new None()/* : None */;
		/* this */.head/* : unknown */ = head/* : Head<T> */;
	}
	/* @Override
        public */ next() : Option<R> {
		while (true){magma.Main$Lists$JVMList@de0a01f
		}
	}
}
/* private */class ArrayType/*  */ {
	constructor (right : Type) {
	}
	/* @Override
        public */ generate() : string {
		return /* this */.right/* : unknown */()/* : unknown */.generate/* : unknown */()/* : unknown */ + "[]";
	}
	/* @Override
        public */ replace(mapping : Map<string, Type>) : Type {
		return /* this */;
	}
	/* @Override
        public */ findName() : Option<string> {
		return new None()/* : None */;
	}
}
/* private static */class Whitespace/*  */ {
	/* @Override
        public */ generate() : string {
		return "";
	}
}
/* private static */class Iterators/*  */ {
	/* public static  */ fromOption<T>(option : Option<T>) : Iterator<T> {
		let single : Option<Head<T>> = option/* : unknown */.map/* : unknown */(SingleHead/* : SingleHead */.new/* : unknown */)/* : unknown */;
		return new HeadedIterator(single/* : Option<Head<T>> */.orElseGet/* : (arg0 : () => T) => T */(EmptyHead/* : EmptyHead */.new/* : unknown */)/* : T */)/* : HeadedIterator */;
	}
}
/* private */class FunctionType/*  */ {
	constructor (arguments : List<Type>, returns : Type) {
	}
	/* @Override
        public */ generate() : string {
		let joined = /* this */.arguments/* : unknown */()/* : unknown */.iterateWithIndices/* : unknown */()/* : unknown */.map/* : unknown */((pair) => "arg" + pair/* : unknown */.left/* : unknown */()/* : unknown */ + " : " + pair/* : unknown */.right/* : unknown */()/* : unknown */.generate/* : unknown */()/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return "(" + joined/* : unknown */ + ") => " + /* this */.returns/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
	/* @Override
        public */ replace(mapping : Map<string, Type>) : Type {
		return new FunctionType(/* this */.arguments/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */((type) => type/* : () => Type */(mapping/* : Map<string, Type> */)/* : Type */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */, /* this */.returns/* : unknown */)/* : FunctionType */;
	}
	/* @Override
        public */ findName() : Option<string> {
		return new None()/* : None */;
	}
}
/* private */class TupleType/*  */ {
	constructor (arguments : List<Type>) {
	}
	/* @Override
        public */ generate() : string {
		let joinedArguments = /* this */.arguments/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(Type/* : Type */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return "[" + joinedArguments + "]";
	}
	/* @Override
        public */ replace(mapping : Map<string, Type>) : Type {
		return /* this */;
	}
	/* @Override
        public */ findName() : Option<string> {
		return new None()/* : None */;
	}
}
/* private */class Template/*  */ {
	constructor (base : FindableType, arguments : List<Type>) {
	}
	/* @Override
        public */ generate() : string {
		let joinedArguments = /* this */.arguments/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(Type/* : Type */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.map/* : unknown */((inner) => "<" + inner + ">")/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return /* this */.base/* : unknown */.generate/* : unknown */()/* : unknown */ + joinedArguments/* : unknown */;
	}
	/* @Override
        public */ typeParams() : List<string> {
		return /* this */.base/* : unknown */.typeParams/* : unknown */()/* : unknown */;
	}
	/* @Override
        public */ find(name : string) : Option<Type> {
		return /* this */.base/* : unknown */.find/* : unknown */(name/* : () => string */)/* : unknown */.map/* : unknown */((found) => {
			let mapping = /* this */.base/* : unknown */.typeParams/* : unknown */()/* : unknown */.iterate/* : unknown */()/* : unknown */.zip/* : unknown */(/* this */.arguments/* : unknown */.iterate/* : unknown */()/* : unknown */)/* : unknown */.collect/* : unknown */(new /* MapCollector */()/* : content-start MapCollector content-end */)/* : unknown */;
			return found/* : unknown */.replace/* : unknown */(mapping/* : Map<string, Type> */)/* : unknown */;
		})/* : unknown */;
	}
	/* @Override
        public */ name() : string {
		return /* this */.base/* : unknown */.name/* : unknown */()/* : unknown */;
	}
	/* @Override
        public */ replace(mapping : Map<string, Type>) : Type {
		return /* this */;
	}
	/* @Override
        public */ findName() : Option<string> {
		return /* this */.base/* : unknown */.findName/* : unknown */()/* : unknown */;
	}
}
/* private */class Placeholder/*  */ {
	constructor (input : string) {
	}
	/* @Override
        public */ generate() : string {
		return /* generatePlaceholder */(/* this */.input/* : unknown */)/* : unknown */;
	}
	/* @Override
        public */ type() : Type {
		return /* Primitive */.Unknown/* : unknown */;
	}
	/* @Override
        public */ typeParams() : List<string> {
		return Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */;
	}
	/* @Override
        public */ find(name : string) : Option<Type> {
		return new None()/* : None */;
	}
	/* @Override
        public */ name() : string {
		return /* this */.input/* : unknown */;
	}
	/* @Override
        public */ replace(mapping : Map<string, Type>) : Type {
		return /* this */;
	}
	/* @Override
        public */ findName() : Option<string> {
		return new None()/* : None */;
	}
}
/* private */class StringValue/*  */ {
	constructor (stripped : string) {
	}
	/* @Override
        public */ generate() : string {
		return /* this */.stripped/* : unknown */;
	}
	/* @Override
        public */ type() : Type {
		return /* Primitive */.Unknown/* : unknown */;
	}
}
/* private */class DataAccess/*  */ {
	constructor (parent : Value, property : string, type : Type) {
	}
	/* @Override
        public */ generate() : string {
		return /* this */.parent/* : unknown */.generate/* : unknown */()/* : unknown */ + "." + /* this */.property/* : unknown */ + /* createDebugString */(/* this */.type/* : unknown */)/* : unknown */;
	}
	/* @Override
        public */ type() : Type {
		return /* this */.type/* : unknown */;
	}
}
/* private */class ConstructionCaller/*  */ {
	constructor (type : Type) {
	}
	/* @Override
        public */ generate() : string {
		return "new " + /* this */.type/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
	/* public */ toFunction() : FunctionType {
		return new FunctionType(Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, /* this */.type/* : unknown */)/* : FunctionType */;
	}
}
/* private */class Operation/*  */ {
	constructor (left : Value, operator : /* Operator */, right : Value) {
	}
	/* @Override
        public */ generate() : string {
		return /* this */.left/* : unknown */()/* : unknown */.generate/* : unknown */()/* : unknown */ + " " + /* this */.operator/* : unknown */.targetRepresentation/* : unknown */ + " " + /* this */.right/* : unknown */()/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
	/* @Override
        public */ type() : Type {
		return /* Primitive */.Unknown/* : unknown */;
	}
}
/* private */class Not/*  */ {
	constructor (value : Value) {
	}
	/* @Override
        public */ generate() : string {
		return "!" + /* this */.value/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
	/* @Override
        public */ type() : Type {
		return /* Primitive */.Unknown/* : unknown */;
	}
}
/* private */class BlockLambdaValue/*  */ {
	constructor (depth : number, statements : List<FunctionSegment>) {
	}
	/* @Override
        public */ generate() : string {
		return "{" + this.joinStatements() + createIndent(this.depth) + "}";
	}
	/* private */ joinStatements() : string {
		return /* this */.statements/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(FunctionSegment/* : FunctionSegment */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner()/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
	}
}
/* private */class Lambda/*  */ {
	constructor (parameters : List<Definition>, body : LambdaValue) {
	}
	/* @Override
        public */ generate() : string {
		let joined = /* this */.parameters/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(Definition/* : Definition */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return "(" + joined/* : unknown */ + ") => " + /* this */.body/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
	/* @Override
        public */ type() : Type {
		return /* Primitive */.Unknown/* : unknown */;
	}
}
/* private */class Invokable/*  */ {
	constructor (caller : Caller, arguments : List<Value>, type : Type) {
	}
	/* @Override
        public */ generate() : string {
		let joined = /* this */.arguments/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(Value/* : Value */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return /* this */.caller/* : unknown */.generate/* : unknown */()/* : unknown */ + "(" + joined/* : unknown */ + ")" + /* createDebugString */(/* this */.type/* : unknown */)/* : unknown */;
	}
}
/* private */class IndexValue/*  */ {
	constructor (parent : Value, child : Value) {
	}
	/* @Override
        public */ generate() : string {
		return /* this */.parent/* : unknown */.generate/* : unknown */()/* : unknown */ + "[" + this.child.generate() + "]";
	}
	/* @Override
        public */ type() : Type {
		return /* Primitive */.Unknown/* : unknown */;
	}
}
/* private */class SymbolValue/*  */ {
	constructor (stripped : string, type : Type) {
	}
	/* @Override
        public */ generate() : string {
		return /* this */.stripped/* : unknown */ + /* createDebugString */(/* this */.type/* : unknown */)/* : unknown */;
	}
}
/* private */class JVMMap<K, V>/*  */ {
	constructor (map : /* java.util.Map */<K, V>) {
	}
	JVMMap() : /* public */ {
		/* this(new HashMap<>()) */;
	}
	/* @Override
            public */ find(key : K) : Option<V> {
		if (/* this */.map/* : unknown */.containsKey/* : unknown */(key/* : K */)/* : unknown */){magma.Main$Lists$JVMList@4c75cab9
		}
		return new None()/* : None */;
	}
	/* @Override
            public */ with(key : K, value : V) : Map<K, V> {
		/* this.map.put(key, value) */;
		return /* this */;
	}
}
/* private static */class Maps/*  */ {
	/* public static  */ empty<VK>() : Map<K, V> {
		return new JVMMap()/* : JVMMap */;
	}
}
/* private */class MapCollector<K, V>/*  */ {
	constructor () {
	}
	/* @Override
        public */ createInitial() : Map<K, V> {
		return Maps/* : Maps */.empty/* : () => List<T> */()/* : List<T> */;
	}
	/* @Override
        public */ fold(current : Map<K, V>, element : [K, V]) : Map<K, V> {
		return current/* : R */.with/* : unknown */(element/* : unknown */.left/* : unknown */()/* : unknown */, element/* : unknown */.right/* : unknown */()/* : unknown */)/* : unknown */;
	}
}
/* private static */class ConstructorHeader/*  */ {
	/* @Override
        public */ createDefinition(paramTypes : List<Type>) : Definition {
		return ImmutableDefinition/* : ImmutableDefinition */.createSimpleDefinition/* : (arg0 : string, arg1 : Type) => Definition */("new", /* Primitive */.Unknown/* : unknown */)/* : Definition */;
	}
	/* @Override
        public */ generateWithParams(joinedParameters : string) : string {
		return "constructor " + joinedParameters/* : string */;
	}
}
/* private static */class DefinitionStatement/*  */ {
	/* private final */ depth : number;
	/* private final */ definition : Definition;
	DefinitionStatement(depth : number, definition : Definition) : /* public */ {
		/* this */.depth/* : unknown */ = depth/* : number */;
		/* this */.definition/* : unknown */ = definition/* : unknown */;
	}
	/* @Override
        public */ generate() : string {
		return /* createIndent */(/* this */.depth/* : unknown */)/* : unknown */ + /* this */.definition/* : unknown */.generate/* : unknown */()/* : unknown */ + ";";
	}
}
/* private static */class Method/*  */ {
	/* private final */ depth : number;
	/* private final */ header : Header;
	/* private final */ parameters : List<Definition>;
	/* private final */ statements : Option<List<FunctionSegment>>;
	Method(depth : number, header : Header, parameters : List<Definition>, maybeStatements : Option<List<FunctionSegment>>) : /* public */ {
		/* this */.depth/* : unknown */ = depth/* : number */;
		/* this */.header/* : unknown */ = header/* : Header */;
		/* this */.parameters/* : unknown */ = parameters/* : List<Definition> */;
		/* this */.statements/* : unknown */ = maybeStatements/* : Option<List<FunctionSegment>> */;
	}
	/* private static */ joinStatements(statements : List<FunctionSegment>) : string {
		return statements/* : List<FunctionSegment> */.iterate/* : () => Iterator<T> */()/* : Iterator<T> */.map/* : (arg0 : (arg0 : T) => R) => Option<R> */(FunctionSegment/* : FunctionSegment */.generate/* : unknown */)/* : Option<R> */.collect/* : (arg0 : Collector<T, R>) => R */(new Joiner()/* : Joiner */)/* : R */.orElse/* : unknown */("")/* : unknown */;
	}
	/* @Override
        public */ generate() : string {
		let generatedHeader = /* this */.header/* : unknown */.generateWithParams/* : unknown */(/* joinValues */(/* this */.parameters/* : unknown */)/* : unknown */)/* : unknown */;
		let generatedStatements = /* this */.statements/* : unknown */.map/* : unknown */(Method/* : (arg0 : number, arg1 : Header, arg2 : List<Definition>, arg3 : Option<List<FunctionSegment>>) => content-start public content-end */.joinStatements/* : unknown */)/* : unknown */.orElse/* : unknown */(";")/* : unknown */;
		let indent = /* createIndent */(/* this */.depth/* : unknown */)/* : unknown */;
		return indent/* : unknown */ + generatedHeader/* : unknown */ + " {" + generatedStatements + indent + "}";
	}
}
/* private */class Primitive/*  */ {
	/* Int("number"),
        String("string"),
        Boolean("boolean"),
       */ Unknown("unknown") : /*  */;
	/* private final */ value : string;
	constructor (value : string) {
		/* this */.value/* : unknown */ = value/* : T */;
	}
	/* @Override
        public */ generate() : string {
		return /* this */.value/* : unknown */;
	}
	/* @Override
        public */ replace(mapping : Map<string, Type>) : Type {
		return /* this */;
	}
	/* @Override
        public */ findName() : Option<string> {
		return new None()/* : None */;
	}
}
/* private */class Operator/*  */ {/* ADD("+", "+"),
        SUBTRACT("-", "-"),
        EQUALS("==", "==="),
        AND("&&", "&&"),
        GREATER_THAN_OR_EQUALS(">=", ">="),
        OR("||", "||"); */
	/* private final */ sourceRepresentation : string;
	/* private final */ targetRepresentation : string;
	constructor (sourceRepresentation : string, targetRepresentation : string) {
		/* this */.sourceRepresentation/* : unknown */ = sourceRepresentation/* : string */;
		/* this */.targetRepresentation/* : unknown */ = targetRepresentation/* : string */;
	}
}
/* private */class BooleanValue/*  */ {/* True("true"), False("false"); */
	/* private final */ value : string;
	constructor (value : string) {
		/* this */.value/* : unknown */ = value/* : T */;
	}
	/* @Override
        public */ generate() : string {
		return /* this */.value/* : unknown */;
	}
	/* @Override
        public */ type() : Type {
		return Primitive/* : Primitive */.Boolean/* : unknown */;
	}
}
/* public */class Main/*  */ {/* 

    private static final boolean isDebug = true; */
	/* public static */ main() : /* void */ {
		/* try */{magma.Main$Lists$JVMList@5c0369c4
		}
		/* catch (IOException | InterruptedException e) */{magma.Main$Lists$JVMList@2be94b0f
		}
	}
	/* private static */ compile(input : string) : string {
		let tuple = /* compileStatements */(new CompileState()/* : CompileState */, input/* : string */, Main/* : Main */.compileRootSegment/* : unknown */)/* : unknown */;
		let joined = tuple/* : unknown */.left/* : unknown */()/* : unknown */.structures/* : unknown */.iterate/* : unknown */()/* : unknown */.collect/* : unknown */(new Joiner()/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return joined/* : unknown */ + tuple/* : unknown */.right/* : unknown */()/* : unknown */;
	}
	/* private static */ compileStatements(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => [CompileState, string]) : [CompileState, string] {
		let parsed = /* parseStatements */(state/* : CompileState */, input/* : string */, mapper/* : (arg0 : T) => R */)/* : unknown */;
		return new Tuple2Impl(parsed/* : unknown */.left/* : unknown */()/* : unknown */, /* generateStatements */(parsed/* : unknown */.right/* : unknown */()/* : unknown */)/* : unknown */)/* : Tuple2Impl */;
	}
	/* private static */ generateStatements(statements : List<string>) : string {
		return /* generateAll */(Main/* : Main */.mergeStatements/* : unknown */, statements/* : List<FunctionSegment> */)/* : unknown */;
	}
	/* private static  */ parseStatements<T>(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => [CompileState, T]) : [CompileState, List<T>] {
		return /* parseAll0 */(state/* : CompileState */, input/* : string */, Main/* : Main */.foldStatementChar/* : unknown */, mapper/* : (arg0 : T) => R */)/* : unknown */;
	}
	/* private static */ generateAll(merger : (arg0 : string, arg1 : string) => string, elements : List<string>) : string {
		return elements/* : content-start java.util.List content-end<T> */.iterate/* : unknown */()/* : unknown */.fold/* : unknown */("", merger/* : (arg0 : string, arg1 : string) => string */)/* : unknown */;
	}
	/* private static  */ parseAll0<T>(state : CompileState, input : string, folder : (arg0 : DivideState, arg1 : string) => DivideState, mapper : (arg0 : CompileState, arg1 : string) => [CompileState, T]) : [CompileState, List<T>] {
		return /* getCompileStateListTuple */(state/* : CompileState */, input/* : string */, folder/* : (arg0 : R, arg1 : T) => R */, (state1, s) => new Some(mapper/* : (arg0 : T) => R */(state1/* : unknown */, s/* : unknown */)/* : R */)/* : Some */)/* : unknown */.orElseGet/* : unknown */(() => new Tuple2Impl(state/* : CompileState */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */)/* : Tuple2Impl */)/* : unknown */;
	}
	/* private static  */ getCompileStateListTuple<T>(state : CompileState, input : string, folder : (arg0 : DivideState, arg1 : string) => DivideState, mapper : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) : Option<[CompileState, List<T>]> {
		return /* parseAll */(state/* : CompileState */, input/* : string */, folder/* : (arg0 : R, arg1 : T) => R */, (state1, tuple) => mapper/* : (arg0 : T) => R */(state1/* : unknown */, tuple/* : unknown */.right/* : unknown */()/* : unknown */)/* : R */)/* : unknown */;
	}
	/* private static  */ parseAll<T>(state : CompileState, input : string, folder : (arg0 : DivideState, arg1 : string) => DivideState, mapper : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]>) : Option<[CompileState, List<T>]> {
		let initial : Option<[CompileState, List<T>]> = new Some(new Tuple2Impl(state/* : CompileState */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */)/* : Tuple2Impl */)/* : Some */;
		return /* divideAll */(input/* : string */, folder/* : (arg0 : R, arg1 : T) => R */)/* : unknown */.iterateWithIndices/* : unknown */()/* : unknown */.fold/* : unknown */(initial/* : R */, (tuple, element) => {
			return tuple/* : unknown */.flatMap/* : unknown */((inner) => {
				let state1 = inner/* : unknown */.left/* : unknown */()/* : unknown */;
				let right = inner/* : unknown */.right/* : unknown */()/* : unknown */;
				return mapper/* : (arg0 : T) => R */(state1/* : unknown */, element/* : unknown */)/* : R */.map/* : unknown */((applied) => {
					return new Tuple2Impl(applied/* : unknown */.left/* : unknown */()/* : unknown */, right/* : () => B */(applied/* : unknown */.right/* : unknown */()/* : unknown */)/* : B */)/* : Tuple2Impl */;
				})/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ mergeStatements(cache : string, statement : string) : string {
		return cache/* : string */ + statement/* : string */;
	}
	/* private static */ divideAll(input : string, folder : (arg0 : DivideState, arg1 : string) => DivideState) : List<string> {
		let current : DivideState = new DivideState(input/* : string */)/* : DivideState */;
		while (true){magma.Main$Lists$JVMList@d70c109
		}
		return current/* : R */.advance/* : unknown */()/* : unknown */.segments/* : unknown */;
	}
	/* private static */ foldDoubleQuotes(tuple : [string, DivideState]) : Option<DivideState> {
		if (tuple/* : unknown */.left/* : unknown */()/* : unknown */ === /*  '\"' */){magma.Main$Lists$JVMList@17ed40e0
		}
		return new None()/* : None */;
	}
	/* private static */ foldSingleQuotes(tuple : [string, DivideState]) : Option<DivideState> {
		if (/* tuple.left() != '\'' */){magma.Main$Lists$JVMList@50675690
		}
		let appended = tuple/* : unknown */.right/* : unknown */()/* : unknown */.append/* : unknown */(tuple/* : unknown */.left/* : unknown */()/* : unknown */)/* : unknown */;
		return appended/* : unknown */.popAndAppendToTuple/* : unknown */()/* : unknown */.map/* : unknown */(Main/* : Main */.foldEscaped/* : unknown */)/* : unknown */.flatMap/* : unknown */(DivideState/* : (arg0 : string, arg1 : number, arg2 : List<string>, arg3 : string, arg4 : number) => content-start public content-end */.popAndAppendToOption/* : unknown */)/* : unknown */;
	}
	/* private static */ foldEscaped(escaped : [string, DivideState]) : DivideState {
		if (escaped/* : [string, DivideState] */[0/* : number */]()/* : unknown */ === /*  '\\' */){magma.Main$Lists$JVMList@31b7dea0
		}
		return escaped/* : [string, DivideState] */[1/* : number */]()/* : unknown */;
	}
	/* private static */ foldStatementChar(state : DivideState, c : string) : DivideState {
		let append : DivideState = state/* : CompileState */.append/* : (arg0 : string) => DivideState */(c/* : string */)/* : DivideState */;
		if (c/* : string */ === /*  ';'  */ && append/* : (arg0 : string) => DivideState */.isLevel/* : unknown */()/* : unknown */){magma.Main$Lists$JVMList@3ac42916
		}
		if (c/* : string */ === /*  '}'  */ && append/* : (arg0 : string) => DivideState */.isShallow/* : unknown */()/* : unknown */){magma.Main$Lists$JVMList@47d384ee
		}
		if (c/* : string */ === /*  '{'  */ || c/* : string */ === /*  '(' */){magma.Main$Lists$JVMList@2d6a9952
		}
		if (c/* : string */ === /*  '}'  */ || c/* : string */ === /*  ')' */){magma.Main$Lists$JVMList@22a71081
		}
		return append/* : (arg0 : string) => DivideState */;
	}
	/* private static */ compileRootSegment(state : CompileState, input : string) : [CompileState, string] {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.startsWith/* : unknown */("package ")/* : unknown */ || stripped/* : unknown */.startsWith/* : unknown */("import ")/* : unknown */){magma.Main$Lists$JVMList@3930015a
		}
		return /* compileClass */(stripped/* : unknown */, 0/* : number */, state/* : CompileState */)/* : unknown */.map/* : unknown */((tuple) => new Tuple2Impl(tuple/* : unknown */.left/* : unknown */()/* : unknown */, tuple/* : unknown */.right/* : unknown */()/* : unknown */.generate/* : unknown */()/* : unknown */)/* : Tuple2Impl */)/* : unknown */.orElseGet/* : unknown */(() => new Tuple2Impl(state/* : CompileState */, /* generatePlaceholder */(stripped/* : unknown */)/* : unknown */)/* : Tuple2Impl */)/* : unknown */;
	}
	/* private static */ compileClass(stripped : string, depth : number, state : CompileState) : Option<[CompileState, ClassSegment]> {
		return /* compileStructure */(stripped/* : unknown */, "class ", "class ", state/* : CompileState */)/* : unknown */;
	}
	/* private static */ compileStructure(stripped : string, sourceInfix : string, targetInfix : string, state : CompileState) : Option<[CompileState, ClassSegment]> {
		return first/* : unknown */(stripped/* : unknown */, sourceInfix/* : string */, (beforeInfix, right) => {
			return first/* : unknown */(right/* : () => B */, "{", (beforeContent, withEnd) => {
				return /* suffix */(withEnd/* : unknown */.strip/* : unknown */()/* : unknown */, "}", (content1) => {
					return /* getOr */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : unknown */, beforeContent/* : unknown */, content1/* : unknown */)/* : unknown */;
				})/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ getOr(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string) : Option<[CompileState, ClassSegment]> {
		return first/* : unknown */(beforeContent/* : unknown */, " implements ", (s, s2) => {
			return /* structureWithMaybeExtends */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : unknown */, s/* : unknown */, content1/* : unknown */)/* : unknown */;
		})/* : unknown */.or/* : unknown */(() => {
			return /* structureWithMaybeExtends */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : unknown */, beforeContent/* : unknown */, content1/* : unknown */)/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ structureWithMaybeExtends(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string) : Option<[CompileState, ClassSegment]> {
		return first/* : unknown */(beforeContent/* : unknown */, " extends ", (s, s2) => {
			return /* structureWithMaybeParams */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : unknown */, s/* : unknown */, content1/* : unknown */)/* : unknown */;
		})/* : unknown */.or/* : unknown */(() => {
			return /* structureWithMaybeParams */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : unknown */, beforeContent/* : unknown */, content1/* : unknown */)/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ structureWithMaybeParams(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string) : Option<[CompileState, ClassSegment]> {
		return /* suffix */(beforeContent/* : unknown */.strip/* : unknown */()/* : unknown */, ")", (s) => {
			return first/* : unknown */(s/* : unknown */, "(", (s1, s2) => {
				let parsed = /* parseParameters */(state/* : CompileState */, s2/* : unknown */)/* : unknown */;
				return /* getOred */(targetInfix/* : string */, parsed/* : unknown */.left/* : unknown */()/* : unknown */, beforeInfix/* : unknown */, s1/* : unknown */, content1/* : unknown */, parsed/* : unknown */.right/* : unknown */()/* : unknown */)/* : unknown */;
			})/* : unknown */;
		})/* : unknown */.or/* : unknown */(() => {
			return /* getOred */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : unknown */, beforeContent/* : unknown */, content1/* : unknown */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */)/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ getOred(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string, params : List<Parameter>) : Option<[CompileState, ClassSegment]> {
		return first/* : unknown */(beforeContent/* : unknown */, "<", (name, withTypeParams) => {
			return first/* : unknown */(withTypeParams/* : (arg0 : List<string>) => CompileState */, ">", (typeParamsString, afterTypeParams) => {
				let /* final */ mapper : (arg0 : CompileState, arg1 : string) => [CompileState, string] = (state1, s) => new Tuple2Impl(state1/* : unknown */, s/* : unknown */.strip/* : unknown */()/* : unknown */)/* : Tuple2Impl */;
				let typeParams = /* parseValuesOrEmpty */(state/* : CompileState */, typeParamsString/* : unknown */, (state1, s) => new Some(mapper/* : (arg0 : T) => R */(state1/* : unknown */, s/* : unknown */)/* : R */)/* : Some */)/* : unknown */;
				return /* assembleStructure */(typeParams/* : () => List<string> */()/* : List<string> */, targetInfix/* : string */, beforeInfix/* : unknown */, name/* : () => string */, content1/* : unknown */, typeParams/* : () => List<string> */()/* : List<string> */, afterTypeParams/* : unknown */, params/* : List<Parameter> */)/* : unknown */;
			})/* : unknown */;
		})/* : unknown */.or/* : unknown */(() => {
			return /* assembleStructure */(state/* : CompileState */, targetInfix/* : string */, beforeInfix/* : unknown */, beforeContent/* : unknown */, content1/* : unknown */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, "", params/* : List<Parameter> */)/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ assembleStructure(state : CompileState, targetInfix : string, beforeInfix : string, rawName : string, content : string, typeParams : List<string>, afterTypeParams : string, rawParameters : List<Parameter>) : Option<[CompileState, ClassSegment]> {
		let name = rawName/* : string */.strip/* : unknown */()/* : unknown */;
		if (!/* isSymbol */(name/* : () => string */)/* : unknown */){magma.Main$Lists$JVMList@629f0666
		}
		let joinedTypeParams = typeParams/* : () => List<string> */()/* : List<string> */.collect/* : (arg0 : Collector<T, R>) => R */(new Joiner(", ")/* : Joiner */)/* : R */.map/* : unknown */((inner) => "<" + inner + ">")/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		let statementsTuple : [CompileState, List<T>] = parseStatements/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => [CompileState, T]) => [CompileState, List<T>] */(state/* : CompileState */.pushStructName/* : (arg0 : string) => CompileState */(name/* : () => string */)/* : CompileState */.withTypeParams/* : (arg0 : List<string>) => CompileState */(typeParams/* : () => List<string> */)/* : CompileState */, content/* : string */, (state0, input) => /* compileClassSegment */(state0/* : unknown */, input/* : string */, 1/* : number */)/* : unknown */)/* : [CompileState, List<T>] */;
		/* List<ClassSegment> withMaybeConstructor */;
		if (rawParameters/* : List<Parameter> */.isEmpty/* : () => boolean */()/* : boolean */){magma.Main$Lists$JVMList@1bc6a36e
		}
		else {magma.Main$Lists$JVMList@1ff8b8f
		}
		let parsed2 = /* withMaybeConstructor */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(ClassSegment/* : ClassSegment */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner()/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		let generated = /* generatePlaceholder */(beforeInfix/* : unknown */.strip/* : unknown */()/* : unknown */)/* : unknown */ + targetInfix/* : string */ + name/* : () => string */ + joinedTypeParams/* : unknown */ + /* generatePlaceholder */(afterTypeParams/* : unknown */)/* : unknown */ + " {" + parsed2 + "\n}\n";
		let compileState = statementsTuple/* : [CompileState, List<T>] */[0/* : number */]()/* : unknown */.popStructName/* : unknown */()/* : unknown */.addStructure/* : unknown */(generated/* : unknown */)/* : unknown */.addType/* : unknown */(new ObjectType(name/* : () => string */, typeParams/* : () => List<string> */, statementsTuple/* : [CompileState, List<T>] */[0/* : number */]()/* : unknown */.definitions/* : unknown */)/* : ObjectType */)/* : unknown */;
		return new Some(new Tuple2Impl(compileState/* : unknown */, new Whitespace()/* : Whitespace */)/* : Tuple2Impl */)/* : Some */;
	}
	/* private static */ retainDefinition(parameter : Parameter) : Option<Definition> {
		if (parameter/* : Parameter */._variant/* : unknown */ === ParameterVariant.Definition/* : unknown */){magma.Main$Lists$JVMList@387c703b
		}
		return new None()/* : None */;
	}
	/* private static */ isSymbol(input : string) : boolean {
		/* for (var i = 0; i < input.length(); i++) */{magma.Main$Lists$JVMList@224aed64
		}
		return true;
	}
	/* private static  */ prefix<T>(input : string, prefix : string, mapper : (arg0 : string) => Option<T>) : Option<T> {
		if (!input/* : string */.startsWith/* : unknown */(prefix/* : string */)/* : unknown */){magma.Main$Lists$JVMList@c39f790
		}
		let slice = input/* : string */.substring/* : unknown */(prefix/* : string */.length/* : unknown */()/* : unknown */)/* : unknown */;
		return mapper/* : (arg0 : T) => R */(slice/* : unknown */)/* : R */;
	}
	/* private static  */ suffix<T>(input : string, suffix : string, mapper : (arg0 : string) => Option<T>) : Option<T> {
		if (!input/* : string */.endsWith/* : unknown */(suffix/* : string */)/* : unknown */){magma.Main$Lists$JVMList@71e7a66b
		}
		let slice = input/* : string */.substring/* : unknown */(0/* : number */, input/* : string */.length/* : unknown */()/* : unknown */ - suffix/* : string */.length/* : unknown */()/* : unknown */)/* : unknown */;
		return mapper/* : (arg0 : T) => R */(slice/* : unknown */)/* : R */;
	}
	/* private static */ compileClassSegment(state : CompileState, input : string, depth : number) : [CompileState, ClassSegment] {
		return /* Main.<Whitespace, ClassSegment>typed */(() => /* compileWhitespace */(input/* : string */, state/* : CompileState */)/* : unknown */)/* : unknown */.or/* : unknown */(() => compileClass/* : (arg0 : string, arg1 : number, arg2 : CompileState) => Option<[CompileState, ClassSegment]> */(input/* : string */, depth/* : number */, state/* : CompileState */)/* : Option<[CompileState, ClassSegment]> */)/* : unknown */.or/* : unknown */(() => compileStructure/* : (arg0 : string, arg1 : string, arg2 : string, arg3 : CompileState) => Option<[CompileState, ClassSegment]> */(input/* : string */, "interface ", "interface ", state/* : CompileState */)/* : Option<[CompileState, ClassSegment]> */)/* : unknown */.or/* : unknown */(() => compileStructure/* : (arg0 : string, arg1 : string, arg2 : string, arg3 : CompileState) => Option<[CompileState, ClassSegment]> */(input/* : string */, "record ", "class ", state/* : CompileState */)/* : Option<[CompileState, ClassSegment]> */)/* : unknown */.or/* : unknown */(() => compileStructure/* : (arg0 : string, arg1 : string, arg2 : string, arg3 : CompileState) => Option<[CompileState, ClassSegment]> */(input/* : string */, "enum ", "class ", state/* : CompileState */)/* : Option<[CompileState, ClassSegment]> */)/* : unknown */.or/* : unknown */(() => /* compileMethod */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* compileDefinitionStatement */(input/* : string */, depth/* : number */, state/* : CompileState */)/* : unknown */)/* : unknown */.orElseGet/* : unknown */(() => new Tuple2Impl(state/* : CompileState */, new Placeholder(input/* : string */)/* : Placeholder */)/* : Tuple2Impl */)/* : unknown */;
	}
	/* private static  */ typed<T extends SS>(action : () => Option<[CompileState, T]>) : Option<[CompileState, S]> {
		return action/* : () => Option<[CompileState, T]> */()/* : Option<[CompileState, T]> */.map/* : (arg0 : (arg0 : [CompileState, T]) => R) => Option<R> */((tuple : [CompileState, T]) => new Tuple2Impl(tuple/* : unknown */.left/* : unknown */()/* : unknown */, tuple/* : unknown */.right/* : unknown */()/* : unknown */)/* : Tuple2Impl */)/* : Option<R> */;
	}
	/* private static */ compileWhitespace(input : string, state : CompileState) : Option<[CompileState, Whitespace]> {
		if (input/* : string */.isBlank/* : unknown */()/* : unknown */){magma.Main$Lists$JVMList@2ac1fdc4
		}
		return new None()/* : None */;
	}
	/* private static */ compileMethod(state : CompileState, input : string, depth : number) : Option<[CompileState, ClassSegment]> {
		return first/* : unknown */(input/* : string */, "(", (definitionString, withParams) => {
			return first/* : unknown */(withParams/* : unknown */, ")", (parametersString, rawContent) => {
				return /* parseDefinition(state, definitionString).<Tuple2<CompileState, Header>>map */((tuple) => new Tuple2Impl(tuple/* : unknown */.left/* : unknown */()/* : unknown */, tuple/* : unknown */.right/* : unknown */()/* : unknown */)/* : Tuple2Impl */)/* : unknown */.or/* : unknown */(() => /* parseConstructor */(state/* : CompileState */, definitionString/* : unknown */)/* : unknown */)/* : unknown */.flatMap/* : unknown */((definitionTuple) => {
					return /* assembleMethod */(depth/* : number */, parametersString/* : unknown */, rawContent/* : unknown */, definitionTuple/* : unknown */)/* : unknown */;
				})/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ assembleMethod(depth : number, parametersString : string, rawContent : string, definitionTuple : [CompileState, Header]) : Option<[CompileState, ClassSegment]> {
		let definitionState = definitionTuple/* : unknown */.left/* : unknown */()/* : unknown */;
		let header = definitionTuple/* : unknown */.right/* : unknown */()/* : unknown */;
		let parametersTuple = /* parseParameters */(definitionState/* : unknown */, parametersString/* : unknown */)/* : unknown */;
		let rawParameters = parametersTuple/* : unknown */.right/* : unknown */()/* : unknown */;
		let parameters = /* retainDefinitions */(rawParameters/* : List<Parameter> */)/* : unknown */;
		let content = rawContent/* : unknown */.strip/* : unknown */()/* : unknown */;
		let paramTypes : R = parameters/* : List<Definition> */.iterate/* : () => Iterator<T> */()/* : Iterator<T> */.map/* : (arg0 : (arg0 : T) => R) => Option<R> */(Definition/* : Definition */.type/* : unknown */)/* : Option<R> */.collect/* : (arg0 : Collector<T, R>) => R */(new ListCollector()/* : ListCollector */)/* : R */;
		let toDefine : Definition = header/* : Header */.createDefinition/* : (arg0 : List<Type>) => Definition */(paramTypes/* : List<Type> */)/* : Definition */;
		if (content/* : string */.equals/* : unknown */(";")/* : unknown */){magma.Main$Lists$JVMList@5f150435
		}
		if (content/* : string */.startsWith/* : unknown */("{")/* : unknown */ && content/* : string */.endsWith/* : unknown */("}")/* : unknown */){magma.Main$Lists$JVMList@1c53fd30
		}
		return new None()/* : None */;
	}
	/* private static */ parseConstructor(state : CompileState, input : string) : Option<[CompileState, Header]> {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.equals/* : unknown */(state/* : CompileState */.structNames/* : unknown */.last/* : unknown */()/* : unknown */.orElse/* : unknown */("")/* : unknown */)/* : unknown */){magma.Main$Lists$JVMList@50cbc42f
		}
		return new None()/* : None */;
	}
	/* private static */ joinValues(retainParameters : List<Definition>) : string {
		let inner = retainParameters/* : List<Definition> */.iterate/* : () => Iterator<T> */()/* : Iterator<T> */.map/* : (arg0 : (arg0 : T) => R) => Option<R> */(Definition/* : Definition */.generate/* : unknown */)/* : Option<R> */.collect/* : (arg0 : Collector<T, R>) => R */(new Joiner(", ")/* : Joiner */)/* : R */.orElse/* : unknown */("")/* : unknown */;
		return "(" + inner + ")";
	}
	/* private static */ retainDefinitions(right : List<Parameter>) : List<Definition> {
		return right/* : () => B */()/* : B */.map/* : unknown */(Main/* : Main */.retainDefinition/* : unknown */)/* : unknown */.flatMap/* : unknown */(Iterators/* : Iterators */.fromOption/* : unknown */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
	}
	/* private static */ parseParameters(state : CompileState, params : string) : [CompileState, List<Parameter>] {
		return /* parseValuesOrEmpty */(state/* : CompileState */, params/* : List<Parameter> */, (state1, s) => new Some(/* compileParameter */(state1/* : unknown */, s/* : unknown */)/* : unknown */)/* : Some */)/* : unknown */;
	}
	/* private static */ parseFunctionSegments(state : CompileState, input : string, depth : number) : [CompileState, List<FunctionSegment>] {
		return parseStatements/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => [CompileState, T]) => [CompileState, List<T>] */(state/* : CompileState */, input/* : string */, (state1, input1) => /* parseFunctionSegment */(state1/* : unknown */, input1/* : unknown */, depth/* : number */ + 1/* : number */)/* : unknown */)/* : [CompileState, List<T>] */;
	}
	/* private static */ parseFunctionSegment(state : CompileState, input : string, depth : number) : [CompileState, FunctionSegment] {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.isEmpty/* : unknown */()/* : unknown */){magma.Main$Lists$JVMList@75412c2f
		}
		return /* parseFunctionStatement */(state/* : CompileState */, depth/* : number */, stripped/* : unknown */)/* : unknown */.or/* : unknown */(() => /* compileBlock */(state/* : CompileState */, depth/* : number */, stripped/* : unknown */)/* : unknown */)/* : unknown */.orElseGet/* : unknown */(() => new Tuple2Impl(state/* : CompileState */, new Placeholder(stripped/* : unknown */)/* : Placeholder */)/* : Tuple2Impl */)/* : unknown */;
	}
	/* private static */ parseFunctionStatement(state : CompileState, depth : number, stripped : string) : Option<[CompileState, FunctionSegment]> {
		return suffix/* : string */(stripped/* : unknown */, ";", (s) => {
			let tuple = /* compileStatementValue */(state/* : CompileState */, s/* : unknown */, depth/* : number */)/* : unknown */;
			return new Some(new Tuple2Impl(tuple/* : unknown */.left/* : unknown */()/* : unknown */, () => /* createIndent */(depth/* : number */)/* : unknown */ + tuple/* : unknown */.right/* : unknown */()/* : unknown */ + ";")/* : Tuple2Impl */)/* : Some */;
		})/* : unknown */;
	}
	/* private static */ compileBlock(state : CompileState, depth : number, stripped : string) : Option<[CompileState, FunctionSegment]> {
		return suffix/* : string */(stripped/* : unknown */, "}", (withoutEnd) => {
			return /* split */(() => /* toFirst */(withoutEnd/* : unknown */)/* : unknown */, (beforeContent, content) => {
				return suffix/* : string */(beforeContent/* : unknown */, "{", (s) => {
					let compiled : [CompileState, List<FunctionSegment>] = parseFunctionSegments/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, List<FunctionSegment>] */(state/* : CompileState */, content/* : string */, depth/* : number */)/* : [CompileState, List<FunctionSegment>] */;
					let indent = /* createIndent */(depth/* : number */)/* : unknown */;
					let headerTuple = /* compileBlockHeader */(state/* : CompileState */, s/* : unknown */, depth/* : number */)/* : unknown */;
					let headerState = headerTuple/* : unknown */.left/* : unknown */()/* : unknown */;
					let header = headerTuple/* : unknown */.right/* : unknown */()/* : unknown */;
					return new Some(new Tuple2Impl(headerState/* : unknown */, () => indent/* : unknown */ + header/* : Header */ + "{" + compiled.right() + indent + "}")/* : Tuple2Impl */)/* : Some */;
				})/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ toFirst(input : string) : Option<[string, string]> {
		let divisions : List<string> = divideAll/* : (arg0 : string, arg1 : (arg0 : DivideState, arg1 : string) => DivideState) => List<string> */(input/* : string */, Main/* : Main */.foldBlockStart/* : unknown */)/* : List<string> */;
		return divisions/* : List<string> */.removeFirst/* : () => Option<[T, List<T>]> */()/* : Option<[T, List<T>]> */.map/* : (arg0 : (arg0 : [T, List<T>]) => R) => Option<R> */((removed : [T, List<T>]) => {
			let right = removed/* : [T, List<T>] */[0/* : number */]()/* : unknown */;
			let left = removed/* : [T, List<T>] */[1/* : number */]()/* : unknown */.iterate/* : unknown */()/* : unknown */.collect/* : unknown */(new Joiner("")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
			return new Tuple2Impl(right/* : () => B */, left/* : () => A */)/* : Tuple2Impl */;
		})/* : Option<R> */;
	}
	/* private static */ compileBlockHeader(state : CompileState, input : string, depth : number) : [CompileState, string] {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		return /* compileConditional */(state/* : CompileState */, stripped/* : unknown */, "if", depth/* : number */)/* : unknown */.or/* : unknown */(() => /* compileConditional */(state/* : CompileState */, stripped/* : unknown */, "while", depth/* : number */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* compileElse */(state/* : CompileState */, input/* : string */)/* : unknown */)/* : unknown */.orElseGet/* : unknown */(() => new Tuple2Impl(state/* : CompileState */, /* generatePlaceholder */(stripped/* : unknown */)/* : unknown */)/* : Tuple2Impl */)/* : unknown */;
	}
	/* private static */ compileElse(state : CompileState, input : string) : Option<[CompileState, string]> {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.equals/* : unknown */("else")/* : unknown */){magma.Main$Lists$JVMList@282ba1e
		}
		return new None()/* : None */;
	}
	/* private static */ compileConditional(state : CompileState, input : string, prefix : string, depth : number) : Option<[CompileState, string]> {
		return prefix/* : string */(input/* : string */, prefix/* : string */, (withoutPrefix) => {
			return prefix/* : string */(withoutPrefix/* : unknown */.strip/* : unknown */()/* : unknown */, "(", (withoutValueStart) => {
				return suffix/* : string */(withoutValueStart/* : unknown */, ")", (value) => {
					let compiled = /* compileValue */(state/* : CompileState */, value/* : T */, depth/* : number */)/* : unknown */;
					return new Some(new Tuple2Impl(compiled/* : [CompileState, List<FunctionSegment>] */[0/* : number */]()/* : unknown */, prefix/* : string */ + " (" + compiled.right() + ")")/* : Tuple2Impl */)/* : Some */;
				})/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ foldBlockStart(state : DivideState, c : string) : DivideState {
		let appended : DivideState = state/* : CompileState */.append/* : (arg0 : string) => DivideState */(c/* : string */)/* : DivideState */;
		if (c/* : string */ === /*  '{'  */ && state/* : CompileState */.isLevel/* : unknown */()/* : unknown */){magma.Main$Lists$JVMList@13b6d03
		}
		if (c/* : string */ === /*  '{' */){magma.Main$Lists$JVMList@f5f2bb7
		}
		if (c/* : string */ === /*  '}' */){magma.Main$Lists$JVMList@73035e27
		}
		return appended/* : unknown */;
	}
	/* private static */ compileStatementValue(state : CompileState, input : string, depth : number) : [CompileState, string] {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.startsWith/* : unknown */("return ")/* : unknown */){magma.Main$Lists$JVMList@64c64813
		}
		return /* compileAssignment */(state/* : CompileState */, depth/* : number */, stripped/* : unknown */)/* : unknown */.orElseGet/* : unknown */(() => {
			return new Tuple2Impl(state/* : CompileState */, /* generatePlaceholder */(stripped/* : unknown */)/* : unknown */)/* : Tuple2Impl */;
		})/* : unknown */;
	}
	/* private static */ compileAssignment(state : CompileState, depth : number, stripped : string) : Option<Tuple2Impl<CompileState, string>> {
		return first/* : unknown */(stripped/* : unknown */, "=", (beforeEquals, valueString) => {
			let sourceTuple = /* parseValue */(state/* : CompileState */, valueString/* : unknown */, depth/* : number */)/* : unknown */;
			let sourceState = sourceTuple/* : unknown */.left/* : unknown */()/* : unknown */;
			let source = sourceTuple/* : unknown */.right/* : unknown */()/* : unknown */;
			let generatedSource = source/* : unknown */.generate/* : unknown */()/* : unknown */;
			return /* parseDefinition */(sourceState/* : unknown */, beforeEquals/* : unknown */)/* : unknown */.flatMap/* : unknown */((definitionTuple) => {
				let definitionState = definitionTuple/* : unknown */.left/* : unknown */()/* : unknown */;
				let definition = definitionTuple/* : unknown */.right/* : unknown */()/* : unknown */.mapType/* : unknown */((type) => {
					if (type/* : () => Type */(/* Primitive */.Unknown/* : unknown */)/* : Type */){magma.Main$Lists$JVMList@6f79caec
					}
					else {magma.Main$Lists$JVMList@67117f44
					}
				})/* : unknown */;
				return new Some(new Tuple2Impl(definitionState/* : unknown */.withDefinition/* : unknown */(definition/* : unknown */)/* : unknown */, "let " + definition/* : unknown */.generate/* : unknown */()/* : unknown */ + " = " + generatedSource/* : unknown */)/* : Tuple2Impl */)/* : Some */;
			})/* : unknown */.or/* : unknown */(() => {
				let destinationTuple = /* compileValue */(sourceState/* : unknown */, beforeEquals/* : unknown */, depth/* : number */)/* : unknown */;
				let destinationState = destinationTuple/* : unknown */.left/* : unknown */()/* : unknown */;
				let destinationString = destinationTuple/* : unknown */.right/* : unknown */()/* : unknown */;
				return new Some(new Tuple2Impl(destinationState/* : unknown */, destinationString/* : unknown */ + " = " + generatedSource/* : unknown */)/* : Tuple2Impl */)/* : Some */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ compileValue(state : CompileState, input : string, depth : number) : [CompileState, string] {
		let tuple = /* parseValue */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : unknown */;
		return new Tuple2Impl(tuple/* : unknown */.left/* : unknown */()/* : unknown */, tuple/* : unknown */.right/* : unknown */()/* : unknown */.generate/* : unknown */()/* : unknown */)/* : Tuple2Impl */;
	}
	/* private static */ parseValue(state : CompileState, input : string, depth : number) : [CompileState, Value] {
		return /* parseBoolean */(state/* : CompileState */, input/* : string */)/* : unknown */.or/* : unknown */(() => /* parseLambda */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseString */(state/* : CompileState */, input/* : string */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseDataAccess */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseSymbolValue */(state/* : CompileState */, input/* : string */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseInvokable */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseDigits */(state/* : CompileState */, input/* : string */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseOperation */(state/* : CompileState */, input/* : string */, depth/* : number */, /* Operator */.ADD/* : unknown */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseOperation */(state/* : CompileState */, input/* : string */, depth/* : number */, /* Operator */.EQUALS/* : unknown */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseOperation */(state/* : CompileState */, input/* : string */, depth/* : number */, /* Operator */.SUBTRACT/* : unknown */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseOperation */(state/* : CompileState */, input/* : string */, depth/* : number */, /* Operator */.AND/* : unknown */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseOperation */(state/* : CompileState */, input/* : string */, depth/* : number */, /* Operator */.OR/* : unknown */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseOperation */(state/* : CompileState */, input/* : string */, depth/* : number */, /*  Operator.GREATER_THAN_OR_EQUALS */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseNot */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseMethodReference */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseInstanceOf */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : unknown */)/* : unknown */.orElseGet/* : unknown */(() => new Tuple2Impl<CompileState, Value>(state/* : CompileState */, new Placeholder(input/* : string */)/* : Placeholder */)/* : Tuple2Impl<CompileState, Value> */)/* : unknown */;
	}
	/* private static */ parseBoolean(state : CompileState, input : string) : Option<[CompileState, Value]> {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.equals/* : unknown */("false")/* : unknown */){magma.Main$Lists$JVMList@3ecf72fd
		}
		if (stripped/* : unknown */.equals/* : unknown */("true")/* : unknown */){magma.Main$Lists$JVMList@483bf400
		}
		return new None()/* : None */;
	}
	/* private static */ parseInstanceOf(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return last/* : () => Option<T> */(input/* : string */, "instanceof", (s, s2) => {
			let childTuple : [CompileState, Value] = parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state/* : CompileState */, s/* : unknown */, depth/* : number */)/* : [CompileState, Value] */;
			return /* parseDefinition */(childTuple/* : [CompileState, Value] */[0/* : number */]()/* : unknown */, s2/* : unknown */)/* : unknown */.map/* : unknown */((definitionTuple) => {
				let value = childTuple/* : [CompileState, Value] */[1/* : number */]()/* : unknown */;
				let definition = definitionTuple/* : unknown */.right/* : unknown */()/* : unknown */;
				let variant : DataAccess = new DataAccess(value/* : T */, "_variant", /* Primitive */.Unknown/* : unknown */)/* : DataAccess */;
				let type = value/* : T */.type/* : unknown */()/* : unknown */;
				let generate : T = type/* : () => Type */()/* : Type */.orElse/* : (arg0 : T) => T */("")/* : T */;
				let temp : SymbolValue = new SymbolValue(generate/* : () => string */ + "Variant." + definition/* : unknown */.type/* : unknown */()/* : unknown */.findName/* : unknown */()/* : unknown */.orElse/* : unknown */("")/* : unknown */, /* Primitive */.Unknown/* : unknown */)/* : SymbolValue */;
				return new Tuple2Impl(definitionTuple/* : unknown */.left/* : unknown */()/* : unknown */, new Operation(variant/* : DataAccess */, /* Operator */.EQUALS/* : unknown */, temp/* : SymbolValue */)/* : Operation */)/* : Tuple2Impl */;
			})/* : unknown */;
		})/* : Option<T> */;
	}
	/* private static */ parseMethodReference(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return last/* : () => Option<T> */(input/* : string */, "::", (s, s2) => {
			let tuple : [CompileState, Value] = parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state/* : CompileState */, s/* : unknown */, depth/* : number */)/* : [CompileState, Value] */;
			return new Some(new Tuple2Impl(tuple/* : unknown */.left/* : unknown */()/* : unknown */, new DataAccess(tuple/* : unknown */.right/* : unknown */()/* : unknown */, s2/* : unknown */, /* Primitive */.Unknown/* : unknown */)/* : DataAccess */)/* : Tuple2Impl */)/* : Some */;
		})/* : Option<T> */;
	}
	/* private static */ parseNot(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.startsWith/* : unknown */("!")/* : unknown */){magma.Main$Lists$JVMList@21a06946
		}
		return new None()/* : None */;
	}
	/* private static */ parseLambda(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return first/* : unknown */(input/* : string */, "->", (beforeArrow, valueString) => {
			let strippedBeforeArrow = beforeArrow/* : unknown */.strip/* : unknown */()/* : unknown */;
			if (isSymbol/* : (arg0 : string) => boolean */(strippedBeforeArrow/* : unknown */)/* : boolean */){magma.Main$Lists$JVMList@5d3411d
			}
			if (strippedBeforeArrow/* : unknown */.startsWith/* : unknown */("(")/* : unknown */ && strippedBeforeArrow/* : unknown */.endsWith/* : unknown */(")")/* : unknown */){magma.Main$Lists$JVMList@2471cca7
			}
			return new None()/* : None */;
		})/* : unknown */;
	}
	/* private static */ assembleLambda(state : CompileState, definitions : List<Definition>, valueString : string, depth : number) : Some<[CompileState, Value]> {
		let strippedValueString = valueString/* : unknown */.strip/* : unknown */()/* : unknown */;
		/* Tuple2Impl<CompileState, LambdaValue> value */;
		let state2 : CompileState = state/* : CompileState */.withDefinitions/* : (arg0 : List<Definition>) => CompileState */(definitions/* : List<Definition> */)/* : CompileState */;
		if (strippedValueString/* : unknown */.startsWith/* : unknown */("{")/* : unknown */ && strippedValueString/* : unknown */.endsWith/* : unknown */("}")/* : unknown */){magma.Main$Lists$JVMList@77f03bb1
		}
		else {magma.Main$Lists$JVMList@326de728
		}
		let right = value/* : T */.right/* : unknown */()/* : unknown */;
		return new Some(new Tuple2Impl(value/* : T */.left/* : unknown */()/* : unknown */, new Lambda(definitions/* : List<Definition> */, right/* : () => B */)/* : Lambda */)/* : Tuple2Impl */)/* : Some */;
	}
	/* private static */ parseDigits(state : CompileState, input : string) : Option<[CompileState, Value]> {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (/* isNumber */(stripped/* : unknown */)/* : unknown */){magma.Main$Lists$JVMList@25618e91
		}
		return new None()/* : None */;
	}
	/* private static */ isNumber(input : string) : boolean {
		/* String maybeTruncated */;
		if (input/* : string */.startsWith/* : unknown */("-")/* : unknown */){magma.Main$Lists$JVMList@7a92922
		}
		else {magma.Main$Lists$JVMList@71f2a7d5
		}
		return /* areAllDigits */(/* maybeTruncated */)/* : unknown */;
	}
	/* private static */ areAllDigits(input : string) : boolean {
		/* for (var i = 0; i < input.length(); i++) */{magma.Main$Lists$JVMList@2cfb4a64
		}
		return true;
	}
	/* private static */ parseInvokable(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return suffix/* : string */(input/* : string */.strip/* : unknown */()/* : unknown */, ")", (withoutEnd) => {
			return /* split */(() => /* toLast */(withoutEnd/* : unknown */, "", Main/* : Main */.foldInvocationStart/* : unknown */)/* : unknown */, (callerWithEnd, argumentsString) => {
				return suffix/* : string */(callerWithEnd/* : unknown */, "(", (callerString) => {
					return /* assembleInvokable */(state/* : CompileState */, depth/* : number */, argumentsString/* : unknown */, callerString/* : unknown */.strip/* : unknown */()/* : unknown */)/* : unknown */;
				})/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ assembleInvokable(state : CompileState, depth : number, argumentsString : string, callerString : string) : Some<[CompileState, Value]> {
		let callerTuple = /* invocationHeader */(state/* : CompileState */, depth/* : number */, callerString/* : unknown */)/* : unknown */;
		let oldCallerState = callerTuple/* : unknown */.left/* : unknown */()/* : unknown */;
		let oldCaller = callerTuple/* : unknown */.right/* : unknown */()/* : unknown */;
		let newCaller = /* modifyCaller */(oldCallerState/* : unknown */, oldCaller/* : unknown */)/* : unknown */;
		let callerType = /* findCallerType */(newCaller/* : unknown */)/* : unknown */;
		let argumentsTuple = /* parseValuesWithIndices */(oldCallerState/* : unknown */, argumentsString/* : unknown */, (currentState, pair) => {
			let index = pair/* : unknown */.left/* : unknown */()/* : unknown */;
			let element = pair/* : unknown */.right/* : unknown */()/* : unknown */;
			let expectedType = callerType/* : unknown */.arguments/* : unknown */.get/* : unknown */(index/* : T */)/* : unknown */.orElse/* : unknown */(/* Primitive */.Unknown/* : unknown */)/* : unknown */;
			let withExpected = currentState/* : unknown */.withExpectedType/* : unknown */(expectedType/* : unknown */)/* : unknown */;
			let valueTuple = /* parseArgument */(withExpected/* : unknown */, element/* : unknown */, depth/* : number */)/* : unknown */;
			let valueState = valueTuple/* : unknown */.left/* : unknown */()/* : unknown */;
			let value = valueTuple/* : unknown */.right/* : unknown */()/* : unknown */;
			let actualType = valueTuple/* : unknown */.left/* : unknown */()/* : unknown */.typeRegister/* : unknown */.orElse/* : unknown */(/* Primitive */.Unknown/* : unknown */)/* : unknown */;
			return new Some(new Tuple2Impl(valueState/* : unknown */, new Tuple2Impl(value/* : T */, actualType/* : unknown */)/* : Tuple2Impl */)/* : Tuple2Impl */)/* : Some */;
		})/* : unknown */.orElseGet/* : unknown */(() => new Tuple2Impl(oldCallerState/* : unknown */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */)/* : Tuple2Impl */)/* : unknown */;
		let argumentsState = argumentsTuple/* : unknown */.left/* : unknown */()/* : unknown */;
		let argumentsWithActualTypes = argumentsTuple/* : unknown */.right/* : unknown */()/* : unknown */;
		let arguments = argumentsWithActualTypes/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(Tuple2/* : Tuple2 */.left/* : unknown */)/* : unknown */.map/* : unknown */(Main/* : Main */.retainValue/* : unknown */)/* : unknown */.flatMap/* : unknown */(Iterators/* : Iterators */.fromOption/* : unknown */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
		let invokable : Invokable = new Invokable(newCaller/* : unknown */, arguments/* : unknown */, callerType/* : unknown */.returns/* : unknown */)/* : Invokable */;
		return new Some(new Tuple2Impl(argumentsState/* : unknown */, invokable/* : Invokable */)/* : Tuple2Impl */)/* : Some */;
	}
	/* private static */ retainValue(argument : Argument) : Option<Value> {
		if (argument/* : Argument */._variant/* : unknown */ === ArgumentVariant.Value/* : unknown */){magma.Main$Lists$JVMList@5474c6c
		}
		return new None()/* : None */;
	}
	/* private static */ parseArgument(state : CompileState, element : string, depth : number) : [CompileState, Argument] {
		if (element/* : unknown */.isEmpty/* : unknown */()/* : unknown */){magma.Main$Lists$JVMList@4b6995df
		}
		let tuple : [CompileState, Value] = parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state/* : CompileState */, element/* : unknown */, depth/* : number */)/* : [CompileState, Value] */;
		return new Tuple2Impl(tuple/* : unknown */.left/* : unknown */()/* : unknown */, tuple/* : unknown */.right/* : unknown */()/* : unknown */)/* : Tuple2Impl */;
	}
	/* private static */ findCallerType(newCaller : Caller) : FunctionType {
		let callerType : FunctionType = new FunctionType(Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, /* Primitive */.Unknown/* : unknown */)/* : FunctionType */;
		/* switch (newCaller) */{magma.Main$Lists$JVMList@2fc14f68
		}
		return callerType/* : unknown */;
	}
	/* private static */ modifyCaller(state : CompileState, oldCaller : Caller) : Caller {
		if (oldCaller/* : unknown */._variant/* : unknown */ === Variant.DataAccess/* : unknown */){magma.Main$Lists$JVMList@591f989e
		}
		return oldCaller/* : unknown */;
	}
	/* private static */ resolveType(value : Value, state : CompileState) : Type {
		return value/* : T */.type/* : unknown */()/* : unknown */;
	}
	/* private static */ invocationHeader(state : CompileState, depth : number, callerString1 : string) : [CompileState, Caller] {
		if (callerString1/* : string */.startsWith/* : unknown */("new ")/* : unknown */){magma.Main$Lists$JVMList@66048bfd
		}
		let tuple : [CompileState, Value] = parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state/* : CompileState */, callerString1/* : string */, depth/* : number */)/* : [CompileState, Value] */;
		return new Tuple2Impl(tuple/* : unknown */.left/* : unknown */()/* : unknown */, tuple/* : unknown */.right/* : unknown */()/* : unknown */)/* : Tuple2Impl */;
	}
	/* private static */ foldInvocationStart(state : DivideState, c : string) : DivideState {
		let appended : DivideState = state/* : CompileState */.append/* : (arg0 : string) => DivideState */(c/* : string */)/* : DivideState */;
		if (c/* : string */ === /*  '(' */){magma.Main$Lists$JVMList@61443d8f
		}
		if (c/* : string */ === /*  ')' */){magma.Main$Lists$JVMList@445b84c0
		}
		return appended/* : unknown */;
	}
	/* private static */ parseDataAccess(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return last/* : () => Option<T> */(input/* : string */.strip/* : unknown */()/* : unknown */, ".", (parentString, rawProperty) => {
			let property = rawProperty/* : unknown */.strip/* : unknown */()/* : unknown */;
			if (!isSymbol/* : (arg0 : string) => boolean */(property/* : unknown */)/* : unknown */){magma.Main$Lists$JVMList@5fe5c6f
			}
			let tuple : [CompileState, Value] = parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state/* : CompileState */, parentString/* : unknown */, depth/* : number */)/* : [CompileState, Value] */;
			let parent = tuple/* : unknown */.right/* : unknown */()/* : unknown */;
			let parentType = parent/* : unknown */.type/* : unknown */()/* : unknown */;
			if (/* parentType instanceof TupleType */){magma.Main$Lists$JVMList@6979e8cb
			}
			let type : Type = /* Primitive */.Unknown/* : unknown */;
			if (parentType/* : unknown */._variant/* : unknown */ === Variant.FindableType/* : unknown */){magma.Main$Lists$JVMList@763d9750
			}
			return new Some(new Tuple2Impl(tuple/* : unknown */.left/* : unknown */()/* : unknown */, new DataAccess(parent/* : unknown */, property/* : unknown */, type/* : () => Type */)/* : DataAccess */)/* : Tuple2Impl */)/* : Some */;
		})/* : Option<T> */;
	}
	/* private static */ parseString(state : CompileState, input : string) : Option<[CompileState, Value]> {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.startsWith/* : unknown */("\"")/* : unknown */ && stripped/* : unknown */.endsWith/* : unknown */("\"")/* : unknown */){magma.Main$Lists$JVMList@61a52fbd
		}
		return new None()/* : None */;
	}
	/* private static */ parseSymbolValue(state : CompileState, value : string) : Option<[CompileState, Value]> {
		let stripped = value/* : T */.strip/* : unknown */()/* : unknown */;
		if (isSymbol/* : (arg0 : string) => boolean */(stripped/* : unknown */)/* : boolean */){magma.Main$Lists$JVMList@233c0b17
		}
		return new None()/* : None */;
	}
	/* private static */ parseOperation(state : CompileState, value : string, depth : number, operator : /* Operator */) : Option<[CompileState, Value]> {
		return first/* : unknown */(value/* : T */, operator/* : content-start Operator content-end */.sourceRepresentation/* : unknown */, (leftString, rightString) => {
			let leftTuple : [CompileState, Value] = parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state/* : CompileState */, leftString/* : unknown */, depth/* : number */)/* : [CompileState, Value] */;
			let rightTuple : [CompileState, Value] = parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(leftTuple/* : [CompileState, Value] */[0/* : number */]()/* : unknown */, rightString/* : unknown */, depth/* : number */)/* : [CompileState, Value] */;
			let left = leftTuple/* : [CompileState, Value] */[1/* : number */]()/* : unknown */;
			let right = rightTuple/* : [CompileState, Value] */[1/* : number */]()/* : unknown */;
			return new Some(new Tuple2Impl(rightTuple/* : [CompileState, Value] */[0/* : number */]()/* : unknown */, new Operation(left/* : () => A */, operator/* : content-start Operator content-end */, right/* : () => B */)/* : Operation */)/* : Tuple2Impl */)/* : Some */;
		})/* : unknown */;
	}
	/* private static  */ parseValuesOrEmpty<T>(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) : [CompileState, List<T>] {
		return /* parseValues */(state/* : CompileState */, input/* : string */, mapper/* : (arg0 : T) => R */)/* : unknown */.orElseGet/* : unknown */(() => new Tuple2Impl(state/* : CompileState */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */)/* : Tuple2Impl */)/* : unknown */;
	}
	/* private static  */ parseValues<T>(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) : Option<[CompileState, List<T>]> {
		return /* parseValuesWithIndices */(state/* : CompileState */, input/* : string */, (state1, tuple) => mapper/* : (arg0 : T) => R */(state1/* : unknown */, tuple/* : unknown */.right/* : unknown */()/* : unknown */)/* : R */)/* : unknown */;
	}
	/* private static  */ parseValuesWithIndices<T>(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]>) : Option<[CompileState, List<T>]> {
		return parseAll/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : DivideState, arg1 : string) => DivideState, arg3 : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]>) => Option<[CompileState, List<T>]> */(state/* : CompileState */, input/* : string */, Main/* : Main */.foldValueChar/* : unknown */, mapper/* : (arg0 : T) => R */)/* : Option<[CompileState, List<T>]> */;
	}
	/* private static */ compileParameter(state : CompileState, input : string) : [CompileState, Parameter] {
		if (input/* : string */.isBlank/* : unknown */()/* : unknown */){magma.Main$Lists$JVMList@63d4e2ba
		}
		return /* parseDefinition */(state/* : CompileState */, input/* : string */)/* : unknown */.map/* : unknown */((tuple) => new Tuple2Impl<CompileState, Parameter>(tuple/* : unknown */.left/* : unknown */()/* : unknown */, tuple/* : unknown */.right/* : unknown */()/* : unknown */)/* : Tuple2Impl<CompileState, Parameter> */)/* : unknown */.orElseGet/* : unknown */(() => new Tuple2Impl(state/* : CompileState */, new Placeholder(input/* : string */)/* : Placeholder */)/* : Tuple2Impl */)/* : unknown */;
	}
	/* private static */ compileDefinition(state : CompileState, input : string) : [CompileState, string] {
		return /* parseDefinition */(state/* : CompileState */, input/* : string */)/* : unknown */.map/* : unknown */((tuple) => new Tuple2Impl(tuple/* : unknown */.left/* : unknown */()/* : unknown */, tuple/* : unknown */.right/* : unknown */()/* : unknown */.generate/* : unknown */()/* : unknown */)/* : Tuple2Impl */)/* : unknown */.orElseGet/* : unknown */(() => new Tuple2Impl(state/* : CompileState */, /* generatePlaceholder */(input/* : string */)/* : unknown */)/* : Tuple2Impl */)/* : unknown */;
	}
	/* private static */ createIndent(depth : number) : string {
		return "\n" + "\t".repeat/* : unknown */(depth/* : number */)/* : unknown */;
	}
	/* private static */ compileDefinitionStatement(input : string, depth : number, state : CompileState) : Option<[CompileState, ClassSegment]> {
		return suffix/* : string */(input/* : string */.strip/* : unknown */()/* : unknown */, ";", (withoutEnd) => {
			return /* parseDefinition */(state/* : CompileState */, withoutEnd/* : unknown */)/* : unknown */.map/* : unknown */((result) => {
				let definition = result/* : unknown */.right/* : unknown */()/* : unknown */;
				return new Tuple2Impl(result/* : unknown */.left/* : unknown */()/* : unknown */, new DefinitionStatement(depth/* : number */, definition/* : unknown */)/* : DefinitionStatement */)/* : Tuple2Impl */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ parseDefinition(state : CompileState, input : string) : Option<[CompileState, Definition]> {
		return last/* : () => Option<T> */(input/* : string */.strip/* : unknown */()/* : unknown */, " ", (beforeName, name) => {
			return /* split */(() => /* toLast */(beforeName/* : unknown */, " ", Main/* : Main */.foldTypeSeparator/* : unknown */)/* : unknown */, (beforeType, type) => {
				return suffix/* : string */(beforeType/* : unknown */.strip/* : unknown */()/* : unknown */, ">", (withoutTypeParamStart) => {
					return first/* : unknown */(withoutTypeParamStart/* : unknown */, "<", (beforeTypeParams, typeParamsString) => {
						let /* final */ compileStateStringTupleBiFunction : (arg0 : CompileState, arg1 : string) => [CompileState, string] = (state1, s) => new Tuple2Impl(state1/* : unknown */, s/* : unknown */.strip/* : unknown */()/* : unknown */)/* : Tuple2Impl */;
						let typeParams : [CompileState, List<T>] = parseValuesOrEmpty/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) => [CompileState, List<T>] */(state/* : CompileState */, typeParamsString/* : unknown */, (state1, s) => new Some(compileStateStringTupleBiFunction/* : (arg0 : CompileState, arg1 : string) => [CompileState, string] */(state1/* : unknown */, s/* : unknown */)/* : [CompileState, string] */)/* : Some */)/* : [CompileState, List<T>] */;
						return /* assembleDefinition */(typeParams/* : () => List<string> */()/* : List<string> */, new Some<string>(beforeTypeParams/* : unknown */)/* : Some<string> */, name/* : () => string */, typeParams/* : () => List<string> */()/* : List<string> */, type/* : () => Type */)/* : unknown */;
					})/* : unknown */;
				})/* : unknown */.or/* : unknown */(() => {
					return /* assembleDefinition */(state/* : CompileState */, new Some<string>(beforeType/* : unknown */)/* : Some<string> */, name/* : () => string */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, type/* : () => Type */)/* : unknown */;
				})/* : unknown */;
			})/* : unknown */.or/* : unknown */(() => {
				return /* assembleDefinition */(state/* : CompileState */, new None<string>()/* : None<string> */, name/* : () => string */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, beforeName/* : unknown */)/* : unknown */;
			})/* : unknown */;
		})/* : Option<T> */;
	}
	/* private static */ toLast(input : string, separator : string, folder : (arg0 : DivideState, arg1 : string) => DivideState) : Option<[string, string]> {
		let divisions : List<string> = divideAll/* : (arg0 : string, arg1 : (arg0 : DivideState, arg1 : string) => DivideState) => List<string> */(input/* : string */, folder/* : (arg0 : R, arg1 : T) => R */)/* : List<string> */;
		return divisions/* : List<string> */.removeLast/* : () => Option<[List<T>, T]> */()/* : Option<[List<T>, T]> */.map/* : (arg0 : (arg0 : [List<T>, T]) => R) => Option<R> */((removed : [List<T>, T]) => {
			let left = removed/* : [T, List<T>] */[0/* : number */]()/* : unknown */.iterate/* : unknown */()/* : unknown */.collect/* : unknown */(new Joiner(separator/* : string */)/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
			let right = removed/* : [T, List<T>] */[1/* : number */]()/* : unknown */;
			return new Tuple2Impl(left/* : () => A */, right/* : () => B */)/* : Tuple2Impl */;
		})/* : Option<R> */;
	}
	/* private static */ foldTypeSeparator(state : DivideState, c : string) : DivideState {
		if (c/* : string */ === /*  ' '  */ && state/* : CompileState */.isLevel/* : unknown */()/* : unknown */){magma.Main$Lists$JVMList@7bb11784
		}
		let appended : DivideState = state/* : CompileState */.append/* : (arg0 : string) => DivideState */(c/* : string */)/* : DivideState */;
		if (c/* : string */ === /*  '<' */){magma.Main$Lists$JVMList@33a10788
		}
		if (c/* : string */ === /*  '>' */){magma.Main$Lists$JVMList@7006c658
		}
		return appended/* : unknown */;
	}
	/* private static */ assembleDefinition(state : CompileState, beforeTypeParams : Option<string>, name : string, typeParams : List<string>, type : string) : Option<[CompileState, Definition]> {
		return /* parseType */(state/* : CompileState */.withTypeParams/* : (arg0 : List<string>) => CompileState */(typeParams/* : () => List<string> */)/* : CompileState */, type/* : () => Type */)/* : unknown */.map/* : unknown */((type1) => {
			let node : ImmutableDefinition = new ImmutableDefinition(beforeTypeParams/* : unknown */, name/* : () => string */()/* : string */, type1/* : unknown */.right/* : unknown */()/* : unknown */, typeParams/* : () => List<string> */)/* : ImmutableDefinition */;
			return new Tuple2Impl(type1/* : unknown */.left/* : unknown */()/* : unknown */, node/* : ImmutableDefinition */)/* : Tuple2Impl */;
		})/* : unknown */;
	}
	/* private static */ foldValueChar(state : DivideState, c : string) : DivideState {
		if (c/* : string */ === /*  ','  */ && state/* : CompileState */.isLevel/* : unknown */()/* : unknown */){magma.Main$Lists$JVMList@34033bd0
		}
		let appended : DivideState = state/* : CompileState */.append/* : (arg0 : string) => DivideState */(c/* : string */)/* : DivideState */;
		if (c/* : string */ === /*  ' */ - /* ' */){magma.Main$Lists$JVMList@47fd17e3
		}
		if (c/* : string */ === /*  '<'  */ || c/* : string */ === /*  '('  */ || c/* : string */ === /*  '{' */){magma.Main$Lists$JVMList@7cdbc5d3
		}
		if (c/* : string */ === /*  '>'  */ || c/* : string */ === /*  ')'  */ || c/* : string */ === /*  '}' */){magma.Main$Lists$JVMList@3aa9e816
		}
		return appended/* : unknown */;
	}
	/* private static */ compileType(state : CompileState, input : string) : Option<[CompileState, string]> {
		return /* parseType */(state/* : CompileState */, input/* : string */)/* : unknown */.map/* : unknown */((tuple) => new Tuple2Impl(tuple/* : unknown */.left/* : unknown */()/* : unknown */, tuple/* : unknown */.right/* : unknown */()/* : unknown */.generate/* : unknown */()/* : unknown */)/* : Tuple2Impl */)/* : unknown */;
	}
	/* private static */ parseType(state : CompileState, input : string) : Option<[CompileState, Type]> {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.equals/* : unknown */("int")/* : unknown */ || stripped/* : unknown */.equals/* : unknown */("Integer")/* : unknown */){magma.Main$Lists$JVMList@17d99928
		}
		if (stripped/* : unknown */.equals/* : unknown */("String")/* : unknown */ || stripped/* : unknown */.equals/* : unknown */("char")/* : unknown */ || stripped/* : unknown */.equals/* : unknown */("Character")/* : unknown */){magma.Main$Lists$JVMList@3834d63f
		}
		if (stripped/* : unknown */.equals/* : unknown */("var")/* : unknown */){magma.Main$Lists$JVMList@1ae369b7
		}
		if (stripped/* : unknown */.equals/* : unknown */("boolean")/* : unknown */){magma.Main$Lists$JVMList@6fffcba5
		}
		if (isSymbol/* : (arg0 : string) => boolean */(stripped/* : unknown */)/* : boolean */){magma.Main$Lists$JVMList@34340fab
		}
		return /* parseTemplate */(state/* : CompileState */, input/* : string */)/* : unknown */.or/* : unknown */(() => /* varArgs */(state/* : CompileState */, input/* : string */)/* : unknown */)/* : unknown */;
	}
	/* private static */ varArgs(state : CompileState, input : string) : Option<[CompileState, Type]> {
		return suffix/* : string */(input/* : string */, "...", (s) => {
			return parseType/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Type]> */(state/* : CompileState */, s/* : unknown */)/* : Option<[CompileState, Type]> */.map/* : (arg0 : (arg0 : [CompileState, Type]) => R) => Option<R> */((inner : [CompileState, Type]) => {
				let newState = inner/* : unknown */.left/* : unknown */()/* : unknown */;
				let child = inner/* : unknown */.right/* : unknown */()/* : unknown */;
				return new Tuple2Impl(newState/* : unknown */, new ArrayType(child/* : unknown */)/* : ArrayType */)/* : Tuple2Impl */;
			})/* : Option<R> */;
		})/* : unknown */;
	}
	/* private static */ assembleTemplate(base : string, state : CompileState, arguments : List<Argument>) : [CompileState, Type] {
		let children = arguments/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(Main/* : Main */.retainType/* : unknown */)/* : unknown */.flatMap/* : unknown */(Iterators/* : Iterators */.fromOption/* : unknown */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
		if (base/* : string */.equals/* : unknown */("BiFunction")/* : unknown */){magma.Main$Lists$JVMList@2aafb23c
		}
		if (base/* : string */.equals/* : unknown */("Function")/* : unknown */){magma.Main$Lists$JVMList@2b80d80f
		}
		if (base/* : string */.equals/* : unknown */("Predicate")/* : unknown */){magma.Main$Lists$JVMList@3ab39c39
		}
		if (base/* : string */.equals/* : unknown */("Supplier")/* : unknown */){magma.Main$Lists$JVMList@2eee9593
		}
		if (base/* : string */.equals/* : unknown */("Tuple2")/* : unknown */ && children/* : unknown */.size/* : unknown */()/* : unknown */ >= 2/* : number */){magma.Main$Lists$JVMList@7907ec20
		}
		if (state/* : CompileState */.resolveType/* : (arg0 : string) => Option<Type> */(base/* : string */)/* : Option<Type> */._variant/* : unknown */ === OptionVariant.Some/* : unknown */){magma.Main$Lists$JVMList@546a03af
		}
		return new Tuple2Impl(state/* : CompileState */, new Template(new Placeholder(base/* : string */)/* : Placeholder */, children/* : unknown */)/* : Template */)/* : Tuple2Impl */;
	}
	/* private static */ parseTemplate(state : CompileState, input : string) : Option<[CompileState, Type]> {
		return suffix/* : string */(input/* : string */.strip/* : unknown */()/* : unknown */, ">", (withoutEnd) => {
			return first/* : unknown */(withoutEnd/* : unknown */, "<", (base, argumentsString) => {
				let strippedBase = base/* : string */.strip/* : unknown */()/* : unknown */;
				return parseValues/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) => Option<[CompileState, List<T>]> */(state/* : CompileState */, argumentsString/* : unknown */, Main/* : Main */.argument/* : unknown */)/* : Option<[CompileState, List<T>]> */.map/* : (arg0 : (arg0 : [CompileState, List<T>]) => R) => Option<R> */((argumentsTuple : [CompileState, List<T>]) => {
					return assembleTemplate/* : (arg0 : string, arg1 : CompileState, arg2 : List<Argument>) => [CompileState, Type] */(strippedBase/* : unknown */, argumentsTuple/* : unknown */.left/* : unknown */()/* : unknown */, argumentsTuple/* : unknown */.right/* : unknown */()/* : unknown */)/* : [CompileState, Type] */;
				})/* : Option<R> */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ retainType(argument : Argument) : Option<Type> {
		if (argument/* : Argument */._variant/* : unknown */ === ArgumentVariant.Type/* : unknown */){magma.Main$Lists$JVMList@721e0f4f
		}
		else {magma.Main$Lists$JVMList@28864e92
		}
	}
	/* private static */ argument(state : CompileState, input : string) : Option<[CompileState, Argument]> {
		if (input/* : string */.isBlank/* : unknown */()/* : unknown */){magma.Main$Lists$JVMList@6ea6d14e
		}
		return parseType/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Type]> */(state/* : CompileState */, input/* : string */)/* : Option<[CompileState, Type]> */.map/* : (arg0 : (arg0 : [CompileState, Type]) => R) => Option<R> */((tuple : [CompileState, Type]) => new Tuple2Impl(tuple/* : unknown */.left/* : unknown */()/* : unknown */, tuple/* : unknown */.right/* : unknown */()/* : unknown */)/* : Tuple2Impl */)/* : Option<R> */;
	}
	/* private static  */ last<T>(input : string, infix : string, mapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return infix/* : string */(input/* : string */, infix/* : string */, Main/* : Main */.findLast/* : unknown */, mapper/* : (arg0 : T) => R */)/* : unknown */;
	}
	/* private static */ findLast(input : string, infix : string) : Option<number> {
		let index = input/* : string */.lastIndexOf/* : unknown */(infix/* : string */)/* : unknown */;
		if (index/* : T */ === -1/* : number */){magma.Main$Lists$JVMList@6ad5c04e
		}
		return new Some(index/* : T */)/* : Some */;
	}
	/* private static  */ first<T>(input : string, infix : string, mapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return infix/* : string */(input/* : string */, infix/* : string */, Main/* : Main */.findFirst/* : unknown */, mapper/* : (arg0 : T) => R */)/* : unknown */;
	}
	/* private static  */ split<T>(splitter : () => Option<[string, string]>, splitMapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return splitter/* : () => Option<[string, string]> */()/* : Option<[string, string]> */.flatMap/* : (arg0 : (arg0 : [string, string]) => Option<R>) => Option<R> */((splitTuple : [string, string]) => splitMapper/* : (arg0 : string, arg1 : string) => Option<T> */(splitTuple/* : [string, string] */[0/* : number */]()/* : unknown */, splitTuple/* : [string, string] */[1/* : number */]()/* : unknown */)/* : Option<T> */)/* : Option<R> */;
	}
	/* private static  */ infix<T>(input : string, infix : string, locator : (arg0 : string, arg1 : string) => Option<number>, mapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return split/* : (arg0 : () => Option<[string, string]>, arg1 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(() => locator/* : (arg0 : string, arg1 : string) => Option<number> */(input/* : string */, infix/* : string */)/* : Option<number> */.map/* : (arg0 : (arg0 : number) => R) => Option<R> */((index : number) => {
			let left = input/* : string */.substring/* : unknown */(0/* : number */, index/* : T */)/* : unknown */;
			let right = input/* : string */.substring/* : unknown */(index/* : T */ + infix/* : string */.length/* : unknown */()/* : unknown */)/* : unknown */;
			return new Tuple2Impl(left/* : () => A */, right/* : () => B */)/* : Tuple2Impl */;
		})/* : Option<R> */, mapper/* : (arg0 : T) => R */)/* : Option<T> */;
	}
	/* private static */ findFirst(input : string, infix : string) : Option<number> {
		let index = input/* : string */.indexOf/* : unknown */(infix/* : string */)/* : unknown */;
		if (index/* : T */ === -1/* : number */){magma.Main$Lists$JVMList@6833ce2c
		}
		return new Some(index/* : T */)/* : Some */;
	}
	/* private static */ generatePlaceholder(input : string) : string {
		let replaced = input/* : string */.replace/* : unknown */("/*", "content-start")/* : unknown */.replace/* : unknown */("*/", "content-end")/* : unknown */;
		return "/* " + replaced + " */";
	}
	/* private static */ createDebugString(type : Type) : string {
		if (!Main/* : Main */.isDebug/* : unknown */){magma.Main$Lists$JVMList@725bef66
		}
		return generatePlaceholder/* : (arg0 : string) => string */(": " + type/* : () => Type */.generate/* : unknown */()/* : unknown */)/* : string */;
	}
}
/*  */