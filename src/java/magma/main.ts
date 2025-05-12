/* private */interface Tuple2<A, B>/*   */ {
	left() : A;
	right() : B;
}
/* private */interface Option<T>/*   */ {
	map<R>(mapper : (arg0 : T) => R) : Option<R>;
	isPresent() : boolean;
	orElse(other : T) : T;
	filter(predicate : (arg0 : T) => boolean) : Option<T>;
	orElseGet(supplier : () => T) : T;
	or(other : () => Option<T>) : Option<T>;
	flatMap<R>(mapper : (arg0 : T) => Option<R>) : Option<R>;
	isEmpty() : boolean;
	and<R>(other : () => Option<R>) : Option<[T, R]>;
}
/* private */interface Collector<T, C>/*   */ {
	createInitial() : C;
	fold(current : C, element : T) : C;
}
/* private */interface Iterator<T>/*   */ {
	fold<R>(initial : R, folder : (arg0 : R, arg1 : T) => R) : R;
	map<R>(mapper : (arg0 : T) => R) : Iterator<R>;
	collect<R>(collector : /* Collector */<T, R>) : R;
	filter(predicate : (arg0 : T) => boolean) : Iterator<T>;
	next() : /* Option */<T>;
	flatMap<R>(f : (arg0 : T) => Iterator<R>) : Iterator<R>;
	zip<R>(other : Iterator<R>) : Iterator<[T, R]>;
}
/* private */interface List<T>/*   */ {
	addLast(element : T) : List<T>;
	iterate() : /* Iterator */<T>;
	removeLast() : /* Option */<[List<T>, T]>;
	get(index : number) : /* Option */<T>;
	size() : number;
	isEmpty() : boolean;
	addFirst(element : T) : List<T>;
	iterateWithIndices() : /* Iterator */<[number, T]>;
	removeFirst() : /* Option */<[T, List<T>]>;
	addAllLast(others : List<T>) : List<T>;
	last() : /* Option */<T>;
	iterateReversed() : /* Iterator */<T>;
	mapLast(mapper : (arg0 : T) => T) : List<T>;
}
/* private */interface Head<T>/*   */ {
	next() : /* Option */<T>;
}
/* private */interface Map<K, V>/*   */ {
	find(key : K) : /* Option */<V>;
	with(key : K, value : V) : Map<K, V>;
}
/* private */interface Type/*  */ {
	generate() : string;
	replace(mapping : /* Map */<string, Type>) : Type;
	findName() : /* Option */<string>;
}
/* private */interface Argument/*  */ {
}
/* private */interface Parameter/*  */ {
}
/* private sealed */interface Value/*  */ {
	generate() : string;
	type() : /* Type */;
}
/* private */interface LambdaValue/*  */ {
	generate() : string;
}
/* private sealed */interface Caller/*  */ {
	generate() : string;
}
/* private */interface FindableType/*  */ {
	typeParams() : /* List */<string>;
	find(name : string) : /* Option */</* Type */>;
	name() : string;
}
/* private */interface Definition/*  */ {
	generate() : string;
	generateType() : string;
	joinBefore() : string;
	joinTypeParams() : string;
	mapType(mapper : (arg0 : /* Type */) => /* Type */) : Definition;
	/* @Override
        */ toString() : string;
	/* @Override
        */ generateWithParams(joinedParameters : string) : string;
	/* @Override
        */ createDefinition(paramTypes : /* List */</* Type */>) : Definition;
	maybeBefore() : /* Option */<string>;
	name() : string;
	type() : /* Type */;
	typeParams() : /* List */<string>;
}
/* private */interface Header/*  */ {
	createDefinition(paramTypes : /* List */</* Type */>) : /* Definition */;
	generateWithParams(joinedParameters : string) : string;
}
/* private */interface ClassSegment/*  */ {
	generate() : string;
}
/* private */interface FunctionSegment/*  */ {
	generate() : string;
}
/* private */interface BlockHeader/*  */ {
	generate() : string;
}
/* private */interface StatementValue/*  */ {
	generate() : string;
}
/* private sealed */interface IncompleteClassSegment/*  */ {
	createDefinition() : /* Option */</* Definition */>;
}
/* private static */class None<T>/*  */ {
	/* @Override
        public  */ map<R>(mapper : (arg0 : T) => R) : /* Option */<R> {
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
        public */ filter(predicate : (arg0 : T) => boolean) : /* Option */<T> {
		return new None()/* : None */;
	}
	/* @Override
        public */ orElseGet(supplier : () => T) : T {
		return supplier/* : () => T */()/* : T */;
	}
	/* @Override
        public */ or(other : () => /* Option */<T>) : /* Option */<T> {
		return other/* : T */.get/* : unknown */()/* : unknown */;
	}
	/* @Override
        public  */ flatMap<R>(mapper : (arg0 : T) => /* Option */<R>) : /* Option */<R> {
		return new None()/* : None */;
	}
	/* @Override
        public */ isEmpty() : boolean {
		return true;
	}
	/* @Override
        public  */ and<R>(other : () => /* Option */<R>) : /* Option */<[T, R]> {
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
        public  */ map<R>(mapper : (arg0 : T) => R) : /* Option */<R> {
		return new Some(mapper/* : (arg0 : T) => R */(this/* : Some */.value/* : unknown */)/* : R */)/* : Some */;
	}
	/* @Override
        public */ isPresent() : boolean {
		return true;
	}
	/* @Override
        public */ orElse(other : T) : T {
		return this/* : Some */.value/* : unknown */;
	}
	/* @Override
        public */ filter(predicate : (arg0 : T) => boolean) : /* Option */<T> {
		if (predicate/* : (arg0 : T) => boolean */(this/* : Some */.value/* : unknown */)/* : boolean */){
			return this/* : Some */;
		}
		return new /* None */()/* : content-start None content-end */;
	}
	/* @Override
        public */ orElseGet(supplier : () => T) : T {
		return this/* : Some */.value/* : unknown */;
	}
	/* @Override
        public */ or(other : () => /* Option */<T>) : /* Option */<T> {
		return this/* : Some */;
	}
	/* @Override
        public  */ flatMap<R>(mapper : (arg0 : T) => /* Option */<R>) : /* Option */<R> {
		return mapper/* : (arg0 : T) => R */(this/* : Some */.value/* : unknown */)/* : R */;
	}
	/* @Override
        public */ isEmpty() : boolean {
		return false;
	}
	/* @Override
        public  */ and<R>(other : () => /* Option */<R>) : /* Option */<[T, R]> {
		return other/* : T */.get/* : unknown */()/* : unknown */.map/* : unknown */((otherValue) => new /* Tuple2Impl */(this/* : Some */.value/* : unknown */, otherValue/* : unknown */)/* : content-start Tuple2Impl content-end */)/* : unknown */;
	}
}
/* private static */class SingleHead<T>/*  */ {
	/* private final */ value : T;
	/* private */ retrieved : boolean;
	constructor (value : T) {
		this/* : SingleHead */.value/* : unknown */ = value/* : T */;
		this/* : SingleHead */.retrieved/* : unknown */ = false;
	}
	/* @Override
        public */ next() : /* Option */<T> {
		if (this/* : SingleHead */.retrieved/* : unknown */){
			return new /* None */()/* : content-start None content-end */;
		}
		this/* : SingleHead */.retrieved/* : unknown */ = true;
		return new /* Some */(this/* : SingleHead */.value/* : unknown */)/* : content-start Some content-end */;
	}
}
/* private static */class EmptyHead<T>/*  */ {
	/* @Override
        public */ next() : /* Option */<T> {
		return new /* None */()/* : content-start None content-end */;
	}
}
/* private */class HeadedIterator<T>/*  */ {
	constructor (head : /* Head */<T>) {
	}
	/* @Override
        public  */ fold<R>(initial : R, folder : (arg0 : R, arg1 : T) => R) : R {
		let current : R = initial/* : R */;
		while (true){
			let finalCurrent : R = current/* : R */;
			let option = this/* : HeadedIterator */.head/* : unknown */.next/* : unknown */()/* : unknown */.map/* : unknown */((inner) => folder/* : (arg0 : R, arg1 : T) => R */(finalCurrent/* : R */, inner/* : unknown */)/* : R */)/* : unknown */;
			if (option/* : unknown */._variant/* : unknown */ === Variant./* : unknown */){
				current/* : R */ = /* some */.value/* : unknown */;
			}
			else {
				return current/* : R */;
			}
		}
	}
	/* @Override
        public  */ map<R>(mapper : (arg0 : T) => R) : /* Iterator */<R> {
		return new HeadedIterator(() => this/* : HeadedIterator */.head/* : unknown */.next/* : unknown */()/* : unknown */.map/* : unknown */(mapper/* : (arg0 : T) => R */)/* : unknown */)/* : HeadedIterator */;
	}
	/* @Override
        public  */ collect<R>(collector : /* Collector */<T, R>) : R {
		return this/* : HeadedIterator */.fold/* : (arg0 : R, arg1 : (arg0 : R, arg1 : T) => R) => R */(collector/* : content-start Collector content-end<T, R> */.createInitial/* : unknown */()/* : unknown */, collector/* : content-start Collector content-end<T, R> */.fold/* : unknown */)/* : R */;
	}
	/* @Override
        public */ filter(predicate : (arg0 : T) => boolean) : /* Iterator */<T> {
		return this/* : HeadedIterator */.flatMap/* : (arg0 : (arg0 : T) => content-start Iterator content-end<R>) => content-start Iterator content-end<R> */((element : T) => {
			if (predicate/* : (arg0 : T) => boolean */(element/* : T */)/* : boolean */){
				return new HeadedIterator(new /* SingleHead */(element/* : T */)/* : content-start SingleHead content-end */)/* : HeadedIterator */;
			}
			return new HeadedIterator(new /* EmptyHead */()/* : content-start EmptyHead content-end */)/* : HeadedIterator */;
		})/* : content-start Iterator content-end<R> */;
	}
	/* @Override
        public */ next() : /* Option */<T> {
		return this/* : HeadedIterator */.head/* : unknown */.next/* : unknown */()/* : unknown */;
	}
	/* @Override
        public  */ flatMap<R>(f : (arg0 : T) => /* Iterator */<R>) : /* Iterator */<R> {
		return new HeadedIterator(new /* FlatMapHead */(this/* : HeadedIterator */.head/* : unknown */, f/* : (arg0 : T) => content-start Iterator content-end<R> */)/* : content-start FlatMapHead content-end */)/* : HeadedIterator */;
	}
	/* @Override
        public  */ zip<R>(other : /* Iterator */<R>) : /* Iterator */<[T, R]> {
		return new HeadedIterator(() => HeadedIterator/* : HeadedIterator */.this/* : HeadedIterator */.head/* : unknown */.next/* : unknown */()/* : unknown */.and/* : unknown */(other/* : content-start Iterator content-end<R> */.next/* : unknown */)/* : unknown */)/* : HeadedIterator */;
	}
}
/* private static */class RangeHead/*  */ {
	/* private final */ length : number;
	/* private */ counter : number;
	RangeHead(length : number) : /* public */ {
		this/* : RangeHead */.length/* : unknown */ = length/* : number */;
	}
	/* @Override
        public */ next() : /* Option */<number> {
		if (/* this.counter < this */.length/* : unknown */){
			let value = this/* : RangeHead */.counter/* : unknown */;
			/* this.counter++ */;
			return new /* Some */(value/* : unknown */)/* : content-start Some content-end */;
		}
		return new /* None */()/* : content-start None content-end */;
	}
}
/* private static final */class JVMList<T>/*  */ {
	/* private final */ elements : /* java.util.List */<T>;
	JVMList(elements : /* java.util.List */<T>) : /* private */ {
		this/* : JVMList */.elements/* : unknown */ = elements/* : content-start java.util.List content-end<T> */;
	}
	JVMList() : /* public */ {
		/* this(new ArrayList<>()) */;
	}
	/* @Override
            public */ addLast(element : T) : /* List */<T> {
		/* this.elements.add(element) */;
		return this/* : JVMList */;
	}
	/* @Override
            public */ iterate() : /* Iterator */<T> {
		return this/* : JVMList */.iterateWithIndices/* : () => content-start Iterator content-end<[number, T]> */()/* : content-start Iterator content-end<[number, T]> */.map/* : unknown */(/* Tuple2 */.right/* : unknown */)/* : unknown */;
	}
	/* @Override
            public */ removeLast() : /* Option */<[/* List */<T>, T]> {
		if (this/* : JVMList */.elements/* : unknown */.isEmpty/* : unknown */()/* : unknown */){
			return new /* None */()/* : content-start None content-end */;
		}
		let slice = this/* : JVMList */.elements/* : unknown */.subList/* : unknown */(0/* : number */, this/* : JVMList */.elements/* : unknown */.size/* : unknown */()/* : unknown */ - 1/* : number */)/* : unknown */;
		let last = this/* : JVMList */.elements/* : unknown */.getLast/* : unknown */()/* : unknown */;
		return new /* Some */(new /* Tuple2Impl */</* List */<T>, T>(new JVMList(slice/* : unknown */)/* : JVMList */, last/* : unknown */)/* : content-start Tuple2Impl content-end<content-start List content-end<T>, T> */)/* : content-start Some content-end */;
	}
	/* @Override
            public */ size() : number {
		return this/* : JVMList */.elements/* : unknown */.size/* : unknown */()/* : unknown */;
	}
	/* @Override
            public */ isEmpty() : boolean {
		return this/* : JVMList */.elements/* : unknown */.isEmpty/* : unknown */()/* : unknown */;
	}
	/* @Override
            public */ addFirst(element : T) : /* List */<T> {
		/* this.elements.addFirst(element) */;
		return this/* : JVMList */;
	}
	/* @Override
            public */ iterateWithIndices() : /* Iterator */<[number, T]> {
		return new /* HeadedIterator */(new /* RangeHead */(this/* : JVMList */.elements/* : unknown */.size/* : unknown */()/* : unknown */)/* : content-start RangeHead content-end */)/* : content-start HeadedIterator content-end */.map/* : unknown */((index) => new /* Tuple2Impl */(index/* : unknown */, this/* : JVMList */.elements/* : unknown */.get/* : unknown */(index/* : unknown */)/* : unknown */)/* : content-start Tuple2Impl content-end */)/* : unknown */;
	}
	/* @Override
            public */ removeFirst() : /* Option */<[T, /* List */<T>]> {
		if (this/* : JVMList */.elements/* : unknown */.isEmpty/* : unknown */()/* : unknown */){
			return new /* None */()/* : content-start None content-end */;
		}
		let first = this/* : JVMList */.elements/* : unknown */.getFirst/* : unknown */()/* : unknown */;
		let slice = this/* : JVMList */.elements/* : unknown */.subList/* : unknown */(1/* : number */, this/* : JVMList */.elements/* : unknown */.size/* : unknown */()/* : unknown */)/* : unknown */;
		return new /* Some */(new /* Tuple2Impl */<T, /* List */<T>>(first/* : unknown */, new JVMList(slice/* : unknown */)/* : JVMList */)/* : content-start Tuple2Impl content-end<T, content-start List content-end<T>> */)/* : content-start Some content-end */;
	}
	/* @Override
            public */ addAllLast(others : /* List */<T>) : /* List */<T> {
		let initial : /* List */<T> = this/* : JVMList */;
		return others/* : content-start List content-end<T> */.iterate/* : unknown */()/* : unknown */.fold/* : unknown */(initial/* : content-start List content-end<T> */, /* List */.addLast/* : unknown */)/* : unknown */;
	}
	/* @Override
            public */ last() : /* Option */<T> {
		if (this/* : JVMList */.elements/* : unknown */.isEmpty/* : unknown */()/* : unknown */){
			return new /* None */()/* : content-start None content-end */;
		}
		return new /* Some */(this/* : JVMList */.elements/* : unknown */.getLast/* : unknown */()/* : unknown */)/* : content-start Some content-end */;
	}
	/* @Override
            public */ iterateReversed() : /* Iterator */<T> {
		return new /* HeadedIterator */(new /* RangeHead */(this/* : JVMList */.elements/* : unknown */.size/* : unknown */()/* : unknown */)/* : content-start RangeHead content-end */)/* : content-start HeadedIterator content-end */.map/* : unknown */((index) => this/* : JVMList */.elements/* : unknown */.size/* : unknown */()/* : unknown */ - index/* : unknown */ - 1/* : number */)/* : unknown */.map/* : unknown */(this/* : JVMList */.elements/* : unknown */.get/* : unknown */)/* : unknown */;
	}
	/* @Override
            public */ mapLast(mapper : (arg0 : T) => T) : /* List */<T> {
		return this/* : JVMList */.last/* : () => content-start Option content-end<T> */()/* : content-start Option content-end<T> */.map/* : unknown */(mapper/* : (arg0 : T) => T */)/* : unknown */.map/* : unknown */((newLast) => this/* : JVMList */.set/* : (arg0 : number, arg1 : T) => JVMList<T> */(this/* : JVMList */.elements/* : unknown */.size/* : unknown */()/* : unknown */ - 1/* : number */, newLast/* : unknown */)/* : JVMList<T> */)/* : unknown */.orElse/* : unknown */(this/* : JVMList */)/* : unknown */;
	}
	/* private */ set(index : number, element : T) : JVMList<T> {
		/* this.elements.set(index, element) */;
		return this/* : JVMList */;
	}
	/* @Override
            public */ get(index : number) : /* Option */<T> {
		if (/* index < this */.elements/* : unknown */.size/* : unknown */()/* : unknown */){
			return new /* Some */(this/* : JVMList */.elements/* : unknown */.get/* : unknown */(index/* : unknown */)/* : unknown */)/* : content-start Some content-end */;
		}
		else {
			return new /* None */()/* : content-start None content-end */;
		}
	}
}
/* private static */class Lists/*  */ {
	/* public static  */ empty<T>() : /* List */<T> {
		return new /* JVMList */()/* : content-start JVMList content-end */;
	}
	/* public static  */ of<T>(elements : T[]) : /* List */<T> {
		return new /* JVMList */(new /* ArrayList */(/* Arrays */.asList/* : unknown */(elements/* : T[] */)/* : unknown */)/* : content-start ArrayList content-end */)/* : content-start JVMList content-end */;
	}
}
/* private */class ImmutableDefinition/*  */ {
	constructor (maybeBefore : /* Option */<string>, name : string, type : /* Type */, typeParams : /* List */<string>) {
	}
	/* public static */ createSimpleDefinition(name : string, type : /* Type */) : /* Definition */ {
		return new ImmutableDefinition(new /* None */()/* : content-start None content-end */, name/* : string */, type/* : content-start Type content-end */, /* Lists */.empty/* : unknown */()/* : unknown */)/* : ImmutableDefinition */;
	}
	/* @Override
        public */ generate() : string {
		return this/* : ImmutableDefinition */.generateWithParams/* : (arg0 : string) => string */("")/* : string */;
	}
	/* @Override
        public */ generateType() : string {
		if (this/* : ImmutableDefinition */.type/* : unknown */.equals/* : unknown */(/* Primitive */.Unknown/* : unknown */)/* : unknown */){
			return "";
		}
		return " : " + this/* : ImmutableDefinition */.type/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
	/* @Override
        public */ joinBefore() : string {
		return !/* isDebug ? "" : this */.maybeBefore/* : unknown */.filter/* : unknown */((value) => !value/* : unknown */.isEmpty/* : unknown */()/* : unknown */)/* : unknown */.map/* : unknown */(/* Main */.generatePlaceholder/* : unknown */)/* : unknown */.map/* : unknown */((inner) => inner/* : unknown */ + " ")/* : unknown */.orElse/* : unknown */("")/* : unknown */;
	}
	/* @Override
        public */ joinTypeParams() : string {
		return this/* : ImmutableDefinition */.typeParams/* : unknown */.iterate/* : unknown */()/* : unknown */.collect/* : unknown */(new /* Joiner */()/* : content-start Joiner content-end */)/* : unknown */.map/* : unknown */((inner) => "<" + inner + ">")/* : unknown */.orElse/* : unknown */("")/* : unknown */;
	}
	/* @Override
        public */ mapType(mapper : (arg0 : /* Type */) => /* Type */) : /* Definition */ {
		return new ImmutableDefinition(this/* : ImmutableDefinition */.maybeBefore/* : unknown */, this/* : ImmutableDefinition */.name/* : unknown */, mapper/* : (arg0 : content-start Type content-end) => content-start Type content-end */(this/* : ImmutableDefinition */.type/* : unknown */)/* : content-start Type content-end */, this/* : ImmutableDefinition */.typeParams/* : unknown */)/* : ImmutableDefinition */;
	}
	/* @Override
        public */ toString() : string {
		return "Definition[" + "maybeBefore=" + this/* : ImmutableDefinition */.maybeBefore/* : unknown */ + ", " + "name=" + this/* : ImmutableDefinition */.name/* : unknown */ + ", " + "type=" + this/* : ImmutableDefinition */.type/* : unknown */ + ", " + "typeParams=" + this/* : ImmutableDefinition */.typeParams/* : unknown */ + /*  ']' */;
	}
	/* @Override
        public */ generateWithParams(joinedParameters : string) : string {
		let joined : string = this/* : ImmutableDefinition */.joinTypeParams/* : () => string */()/* : string */;
		let before : string = this/* : ImmutableDefinition */.joinBefore/* : () => string */()/* : string */;
		let typeString : string = this/* : ImmutableDefinition */.generateType/* : () => string */()/* : string */;
		return before/* : string */ + this/* : ImmutableDefinition */.name/* : unknown */ + joined/* : string */ + joinedParameters/* : string */ + typeString/* : string */;
	}
	/* @Override
        public */ createDefinition(paramTypes : /* List */</* Type */>) : /* Definition */ {
		return ImmutableDefinition/* : ImmutableDefinition */.createSimpleDefinition/* : (arg0 : string, arg1 : content-start Type content-end) => content-start Definition content-end */(this/* : ImmutableDefinition */.name/* : unknown */, new /* FunctionType */(paramTypes/* : content-start List content-end<content-start Type content-end> */, this/* : ImmutableDefinition */.type/* : unknown */)/* : content-start FunctionType content-end */)/* : content-start Definition content-end */;
	}
}
/* private */class ObjectType/*  */ {
	constructor (name : string, typeParams : /* List */<string>, definitions : /* List */</* Definition */>) {
	}
	/* @Override
        public */ generate() : string {
		return this/* : ObjectType */.name/* : unknown */;
	}
	/* @Override
        public */ replace(mapping : /* Map */<string, /* Type */>) : /* Type */ {
		return new ObjectType(this/* : ObjectType */.name/* : unknown */, this/* : ObjectType */.typeParams/* : unknown */, this/* : ObjectType */.definitions/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */((definition) => definition/* : unknown */.mapType/* : unknown */((type) => type/* : unknown */.replace/* : unknown */(mapping/* : content-start Map content-end<string, content-start Type content-end> */)/* : unknown */)/* : unknown */)/* : unknown */.collect/* : unknown */(new /* ListCollector */()/* : content-start ListCollector content-end */)/* : unknown */)/* : ObjectType */;
	}
	/* @Override
        public */ find(name : string) : /* Option */</* Type */> {
		return this/* : ObjectType */.definitions/* : unknown */.iterate/* : unknown */()/* : unknown */.filter/* : unknown */((definition) => definition/* : unknown */.name/* : unknown */()/* : unknown */.equals/* : unknown */(name/* : string */)/* : unknown */)/* : unknown */.map/* : unknown */(/* Definition */.type/* : unknown */)/* : unknown */.next/* : unknown */()/* : unknown */;
	}
	/* @Override
        public */ findName() : /* Option */<string> {
		return new /* Some */(this/* : ObjectType */.name/* : unknown */)/* : content-start Some content-end */;
	}
}
/* private */class TypeParam/*  */ {
	constructor (value : string) {
	}
	/* @Override
        public */ generate() : string {
		return this/* : TypeParam */.value/* : unknown */;
	}
	/* @Override
        public */ replace(mapping : /* Map */<string, /* Type */>) : /* Type */ {
		return mapping/* : content-start Map content-end<string, content-start Type content-end> */.find/* : unknown */(this/* : TypeParam */.value/* : unknown */)/* : unknown */.orElse/* : unknown */(this/* : TypeParam */)/* : unknown */;
	}
	/* @Override
        public */ findName() : /* Option */<string> {
		return new /* None */()/* : content-start None content-end */;
	}
}
/* private */class CompileState/*  */ {
	constructor (structures : /* List */<string>, definitions : /* List */</* List */</* Definition */>>, objectTypes : /* List */</* ObjectType */>, structNames : /* List */<string>, typeParams : /* List */<string>, typeRegister : /* Option */</* Type */>) {
	}
	CompileState() : /* public */ {
		/* this(Lists.empty(), Lists.of(Lists.empty()), Lists.empty(), Lists.empty(), Lists.empty(), new None<>()) */;
	}
	/* private */ resolveValue(name : string) : /* Option */</* Type */> {
		return this/* : CompileState */.definitions/* : unknown */.iterateReversed/* : unknown */()/* : unknown */.flatMap/* : unknown */(/* List */.iterate/* : unknown */)/* : unknown */.filter/* : unknown */((definition) => definition/* : unknown */.name/* : unknown */()/* : unknown */.equals/* : unknown */(name/* : string */)/* : unknown */)/* : unknown */.next/* : unknown */()/* : unknown */.map/* : unknown */(/* Definition */.type/* : unknown */)/* : unknown */;
	}
	/* public */ addStructure(structure : string) : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : unknown */.addLast/* : unknown */(structure/* : string */)/* : unknown */, this/* : CompileState */.definitions/* : unknown */, this/* : CompileState */.objectTypes/* : unknown */, this/* : CompileState */.structNames/* : unknown */, this/* : CompileState */.typeParams/* : unknown */, this/* : CompileState */.typeRegister/* : unknown */)/* : CompileState */;
	}
	/* public */ withDefinitions(definitions : /* List */</* Definition */>) : CompileState {
		let defined = this/* : CompileState */.definitions/* : unknown */.mapLast/* : unknown */((frame) => frame/* : unknown */.addAllLast/* : unknown */(definitions/* : content-start List content-end<content-start Definition content-end> */)/* : unknown */)/* : unknown */;
		return new CompileState(this/* : CompileState */.structures/* : unknown */, defined/* : unknown */, this/* : CompileState */.objectTypes/* : unknown */, this/* : CompileState */.structNames/* : unknown */, this/* : CompileState */.typeParams/* : unknown */, this/* : CompileState */.typeRegister/* : unknown */)/* : CompileState */;
	}
	/* public */ resolveType(name : string) : /* Option */</* Type */> {
		if (this/* : CompileState */.structNames/* : unknown */.last/* : unknown */()/* : unknown */.filter/* : unknown */((inner) => inner/* : unknown */.equals/* : unknown */(name/* : string */)/* : unknown */)/* : unknown */.isPresent/* : unknown */()/* : unknown */){
			return new /* Some */(new /* ObjectType */(name/* : string */, this/* : CompileState */.typeParams/* : unknown */, this/* : CompileState */.definitions/* : unknown */.last/* : unknown */()/* : unknown */.orElse/* : unknown */(/* Lists */.empty/* : unknown */()/* : unknown */)/* : unknown */)/* : content-start ObjectType content-end */)/* : content-start Some content-end */;
		}
		let maybeTypeParam = this/* : CompileState */.typeParams/* : unknown */.iterate/* : unknown */()/* : unknown */.filter/* : unknown */((param) => param/* : unknown */.equals/* : unknown */(name/* : string */)/* : unknown */)/* : unknown */.next/* : unknown */()/* : unknown */;
		if (/* maybeTypeParam instanceof Some */(/* var value */)/* : unknown */){
			return new /* Some */(new /* TypeParam */(/* value */)/* : content-start TypeParam content-end */)/* : content-start Some content-end */;
		}
		return this/* : CompileState */.objectTypes/* : unknown */.iterate/* : unknown */()/* : unknown */.filter/* : unknown */((type) => type/* : unknown */.name/* : unknown */.equals/* : unknown */(name/* : string */)/* : unknown */)/* : unknown */.next/* : unknown */()/* : unknown */.map/* : unknown */((type) => type/* : unknown */)/* : unknown */;
	}
	/* public */ withDefinition(definition : /* Definition */) : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : unknown */, this/* : CompileState */.definitions/* : unknown */.mapLast/* : unknown */((frame) => frame/* : unknown */.addLast/* : unknown */(definition/* : unknown */)/* : unknown */)/* : unknown */, this/* : CompileState */.objectTypes/* : unknown */, this/* : CompileState */.structNames/* : unknown */, this/* : CompileState */.typeParams/* : unknown */, this/* : CompileState */.typeRegister/* : unknown */)/* : CompileState */;
	}
	/* public */ pushStructName(name : string) : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : unknown */, this/* : CompileState */.definitions/* : unknown */, this/* : CompileState */.objectTypes/* : unknown */, this/* : CompileState */.structNames/* : unknown */.addLast/* : unknown */(name/* : string */)/* : unknown */, this/* : CompileState */.typeParams/* : unknown */, this/* : CompileState */.typeRegister/* : unknown */)/* : CompileState */;
	}
	/* public */ withTypeParams(typeParams : /* List */<string>) : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : unknown */, this/* : CompileState */.definitions/* : unknown */, this/* : CompileState */.objectTypes/* : unknown */, this/* : CompileState */.structNames/* : unknown */, this/* : CompileState */.typeParams/* : unknown */.addAllLast/* : unknown */(typeParams/* : content-start List content-end<string> */)/* : unknown */, this/* : CompileState */.typeRegister/* : unknown */)/* : CompileState */;
	}
	/* public */ withExpectedType(type : /* Type */) : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : unknown */, this/* : CompileState */.definitions/* : unknown */, this/* : CompileState */.objectTypes/* : unknown */, this/* : CompileState */.structNames/* : unknown */, this/* : CompileState */.typeParams/* : unknown */, new /* Some */(type/* : unknown */)/* : content-start Some content-end */)/* : CompileState */;
	}
	/* public */ popStructName() : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : unknown */, this/* : CompileState */.definitions/* : unknown */, this/* : CompileState */.objectTypes/* : unknown */, this/* : CompileState */.structNames/* : unknown */.removeLast/* : unknown */()/* : unknown */.map/* : unknown */(/* Tuple2 */.left/* : unknown */)/* : unknown */.orElse/* : unknown */(this/* : CompileState */.structNames/* : unknown */)/* : unknown */, this/* : CompileState */.typeParams/* : unknown */, this/* : CompileState */.typeRegister/* : unknown */)/* : CompileState */;
	}
	/* public */ enterDefinitions() : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : unknown */, this/* : CompileState */.definitions/* : unknown */.addLast/* : unknown */(/* Lists */.empty/* : unknown */()/* : unknown */)/* : unknown */, this/* : CompileState */.objectTypes/* : unknown */, this/* : CompileState */.structNames/* : unknown */, this/* : CompileState */.typeParams/* : unknown */, this/* : CompileState */.typeRegister/* : unknown */)/* : CompileState */;
	}
	/* public */ exitDefinitions() : CompileState {
		let removed = this/* : CompileState */.definitions/* : unknown */.removeLast/* : unknown */()/* : unknown */.map/* : unknown */(/* Tuple2 */.left/* : unknown */)/* : unknown */.orElse/* : unknown */(this/* : CompileState */.definitions/* : unknown */)/* : unknown */;
		return new CompileState(this/* : CompileState */.structures/* : unknown */, removed/* : unknown */, this/* : CompileState */.objectTypes/* : unknown */, this/* : CompileState */.structNames/* : unknown */, this/* : CompileState */.typeParams/* : unknown */, this/* : CompileState */.typeRegister/* : unknown */)/* : CompileState */;
	}
}
/* private static */class DivideState/*  */ {
	/* private final */ input : string;
	/* private final */ index : number;
	/* private */ depth : number;
	/* private */ segments : /* List */<string>;
	/* private */ buffer : string;
	DivideState(input : string, index : number, segments : /* List */<string>, buffer : string, depth : number) : /* public */ {
		this/* : DivideState */.segments/* : unknown */ = segments/* : content-start List content-end<string> */;
		this/* : DivideState */.buffer/* : unknown */ = buffer/* : string */;
		this/* : DivideState */.depth/* : unknown */ = depth/* : number */;
		this/* : DivideState */.input/* : unknown */ = input/* : string */;
		this/* : DivideState */.index/* : unknown */ = index/* : number */;
	}
	DivideState(input : string) : /* public */ {
		/* this(input, 0, Lists.empty(), "", 0) */;
	}
	/* private */ advance() : DivideState {
		this/* : DivideState */.segments/* : unknown */ = this/* : DivideState */.segments/* : unknown */.addLast/* : unknown */(this/* : DivideState */.buffer/* : unknown */)/* : unknown */;
		this/* : DivideState */.buffer/* : unknown */ = "";
		return this/* : DivideState */;
	}
	/* private */ append(c : string) : DivideState {
		this/* : DivideState */.buffer/* : unknown */ = this/* : DivideState */.buffer/* : unknown */ + c/* : string */;
		return this/* : DivideState */;
	}
	/* public */ enter() : DivideState {
		/* this.depth++ */;
		return this/* : DivideState */;
	}
	/* public */ isLevel() : boolean {
		return this/* : DivideState */.depth/* : unknown */ === 0/* : number */;
	}
	/* public */ exit() : DivideState {
		/* this.depth-- */;
		return this/* : DivideState */;
	}
	/* public */ isShallow() : boolean {
		return this/* : DivideState */.depth/* : unknown */ === 1/* : number */;
	}
	/* public */ pop() : /* Option */<[string, DivideState]> {
		if (/* this.index < this */.input/* : unknown */.length/* : unknown */()/* : unknown */){
			let c = this/* : DivideState */.input/* : unknown */.charAt/* : unknown */(this/* : DivideState */.index/* : unknown */)/* : unknown */;
			return new /* Some */(new /* Tuple2Impl */(c/* : string */, new DivideState(this/* : DivideState */.input/* : unknown */, this/* : DivideState */.index/* : unknown */ + 1/* : number */, this/* : DivideState */.segments/* : unknown */, this/* : DivideState */.buffer/* : unknown */, this/* : DivideState */.depth/* : unknown */)/* : DivideState */)/* : content-start Tuple2Impl content-end */)/* : content-start Some content-end */;
		}
		return new /* None */()/* : content-start None content-end */;
	}
	/* public */ popAndAppendToTuple() : /* Option */<[string, DivideState]> {
		return this/* : DivideState */.pop/* : () => content-start Option content-end<[string, DivideState]> */()/* : content-start Option content-end<[string, DivideState]> */.map/* : unknown */((tuple) => {
			let c = tuple/* : unknown */.left/* : unknown */()/* : unknown */;
			let right = tuple/* : unknown */.right/* : unknown */()/* : unknown */;
			return new /* Tuple2Impl */(c/* : string */, right/* : unknown */.append/* : unknown */(c/* : string */)/* : unknown */)/* : content-start Tuple2Impl content-end */;
		})/* : unknown */;
	}
	/* public */ popAndAppendToOption() : /* Option */<DivideState> {
		return this/* : DivideState */.popAndAppendToTuple/* : () => content-start Option content-end<[string, DivideState]> */()/* : content-start Option content-end<[string, DivideState]> */.map/* : unknown */(/* Tuple2 */.right/* : unknown */)/* : unknown */;
	}
	/* public */ peek() : string {
		return this/* : DivideState */.input/* : unknown */.charAt/* : unknown */(this/* : DivideState */.index/* : unknown */)/* : unknown */;
	}
}
/* private */class Joiner/*  */ {
	constructor (delimiter : string) {
	}
	Joiner() : /* private */ {
		/* this("") */;
	}
	/* @Override
        public */ createInitial() : /* Option */<string> {
		return new /* None */()/* : content-start None content-end */;
	}
	/* @Override
        public */ fold(current : /* Option */<string>, element : string) : /* Option */<string> {
		return new /* Some */(current/* : content-start Option content-end<string> */.map/* : unknown */((inner) => inner/* : unknown */ + this/* : Joiner */.delimiter/* : unknown */ + element/* : string */)/* : unknown */.orElse/* : unknown */(element/* : string */)/* : unknown */)/* : content-start Some content-end */;
	}
}
/* private static */class ListCollector<T>/*  */ {
	/* @Override
        public */ createInitial() : /* List */<T> {
		return /* Lists */.empty/* : unknown */()/* : unknown */;
	}
	/* @Override
        public */ fold(current : /* List */<T>, element : T) : /* List */<T> {
		return current/* : content-start List content-end<T> */.addLast/* : unknown */(element/* : T */)/* : unknown */;
	}
}
/* private static */class FlatMapHead<T, R>/*  */ {
	/* private final */ mapper : (arg0 : T) => /* Iterator */<R>;
	/* private final */ head : /* Head */<T>;
	/* private */ current : /* Option */</* Iterator */<R>>;
	FlatMapHead(head : /* Head */<T>, mapper : (arg0 : T) => /* Iterator */<R>) : /* public */ {
		this/* : FlatMapHead */.mapper/* : unknown */ = mapper/* : (arg0 : T) => content-start Iterator content-end<R> */;
		this/* : FlatMapHead */.current/* : unknown */ = new /* None */()/* : content-start None content-end */;
		this/* : FlatMapHead */.head/* : unknown */ = head/* : content-start Head content-end<T> */;
	}
	/* @Override
        public */ next() : /* Option */<R> {
		while (true){
			if (this/* : FlatMapHead */.current/* : unknown */.isPresent/* : unknown */()/* : unknown */){
				let inner : /* Iterator */<R> = this/* : FlatMapHead */.current/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */;
				let maybe : /* Option */<R> = inner/* : content-start Iterator content-end<R> */.next/* : unknown */()/* : unknown */;
				if (maybe/* : content-start Option content-end<R> */.isPresent/* : unknown */()/* : unknown */){
					return maybe/* : content-start Option content-end<R> */;
				}
				else {
					this/* : FlatMapHead */.current/* : unknown */ = new /* None */()/* : content-start None content-end */;
				}
			}
			let outer : /* Option */<T> = this/* : FlatMapHead */.head/* : unknown */.next/* : unknown */()/* : unknown */;
			if (outer/* : content-start Option content-end<T> */.isPresent/* : unknown */()/* : unknown */){
				this/* : FlatMapHead */.current/* : unknown */ = outer/* : content-start Option content-end<T> */.map/* : unknown */(this/* : FlatMapHead */.mapper/* : unknown */)/* : unknown */;
			}
			else {
				return new /* None */()/* : content-start None content-end */;
			}
		}
	}
}
/* private */class ArrayType/*  */ {
	constructor (right : /* Type */) {
	}
	/* @Override
        public */ generate() : string {
		return this/* : ArrayType */.right/* : unknown */()/* : unknown */.generate/* : unknown */()/* : unknown */ + "[]";
	}
	/* @Override
        public */ replace(mapping : /* Map */<string, /* Type */>) : /* Type */ {
		return this/* : ArrayType */;
	}
	/* @Override
        public */ findName() : /* Option */<string> {
		return new /* None */()/* : content-start None content-end */;
	}
}
/* private static final */class Whitespace/*  */ {
	/* @Override
        public */ generate() : string {
		return "";
	}
	/* @Override
        public */ createDefinition() : /* Option */</* Definition */> {
		return new /* None */()/* : content-start None content-end */;
	}
}
/* private static */class Iterators/*  */ {
	/* public static  */ fromOption<T>(option : /* Option */<T>) : /* Iterator */<T> {
		let single : /* Option */</* Head */<T>> = option/* : content-start Option content-end<T> */.map/* : unknown */(/* SingleHead */.new/* : unknown */)/* : unknown */;
		return new /* HeadedIterator */(single/* : content-start Option content-end<content-start Head content-end<T>> */.orElseGet/* : unknown */(/* EmptyHead */.new/* : unknown */)/* : unknown */)/* : content-start HeadedIterator content-end */;
	}
}
/* private */class FunctionType/*  */ {
	constructor (arguments : /* List */</* Type */>, returns : /* Type */) {
	}
	/* @Override
        public */ generate() : string {
		let joined = this/* : FunctionType */.arguments/* : unknown */()/* : unknown */.iterateWithIndices/* : unknown */()/* : unknown */.map/* : unknown */((pair) => "arg" + pair/* : unknown */.left/* : unknown */()/* : unknown */ + " : " + pair/* : unknown */.right/* : unknown */()/* : unknown */.generate/* : unknown */()/* : unknown */)/* : unknown */.collect/* : unknown */(new /* Joiner */(", ")/* : content-start Joiner content-end */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return "(" + joined/* : unknown */ + ") => " + this/* : FunctionType */.returns/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
	/* @Override
        public */ replace(mapping : /* Map */<string, /* Type */>) : /* Type */ {
		return new FunctionType(this/* : FunctionType */.arguments/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */((type) => type/* : unknown */.replace/* : unknown */(mapping/* : content-start Map content-end<string, content-start Type content-end> */)/* : unknown */)/* : unknown */.collect/* : unknown */(new /* ListCollector */()/* : content-start ListCollector content-end */)/* : unknown */, this/* : FunctionType */.returns/* : unknown */)/* : FunctionType */;
	}
	/* @Override
        public */ findName() : /* Option */<string> {
		return new /* None */()/* : content-start None content-end */;
	}
}
/* private */class TupleType/*  */ {
	constructor (arguments : /* List */</* Type */>) {
	}
	/* @Override
        public */ generate() : string {
		let joinedArguments = this/* : TupleType */.arguments/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(/* Type */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new /* Joiner */(", ")/* : content-start Joiner content-end */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return "[" + joinedArguments + "]";
	}
	/* @Override
        public */ replace(mapping : /* Map */<string, /* Type */>) : /* Type */ {
		return this/* : TupleType */;
	}
	/* @Override
        public */ findName() : /* Option */<string> {
		return new /* None */()/* : content-start None content-end */;
	}
}
/* private */class Template/*  */ {
	constructor (base : /* FindableType */, arguments : /* List */</* Type */>) {
	}
	/* @Override
        public */ generate() : string {
		let joinedArguments = this/* : Template */.arguments/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(/* Type */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new /* Joiner */(", ")/* : content-start Joiner content-end */)/* : unknown */.map/* : unknown */((inner) => "<" + inner + ">")/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return this/* : Template */.base/* : unknown */.generate/* : unknown */()/* : unknown */ + joinedArguments/* : unknown */;
	}
	/* @Override
        public */ typeParams() : /* List */<string> {
		return this/* : Template */.base/* : unknown */.typeParams/* : unknown */()/* : unknown */;
	}
	/* @Override
        public */ find(name : string) : /* Option */</* Type */> {
		return this/* : Template */.base/* : unknown */.find/* : unknown */(name/* : string */)/* : unknown */.map/* : unknown */((found) => {
			let mapping = this/* : Template */.base/* : unknown */.typeParams/* : unknown */()/* : unknown */.iterate/* : unknown */()/* : unknown */.zip/* : unknown */(this/* : Template */.arguments/* : unknown */.iterate/* : unknown */()/* : unknown */)/* : unknown */.collect/* : unknown */(new /* MapCollector */()/* : content-start MapCollector content-end */)/* : unknown */;
			return found/* : unknown */.replace/* : unknown */(mapping/* : unknown */)/* : unknown */;
		})/* : unknown */;
	}
	/* @Override
        public */ name() : string {
		return this/* : Template */.base/* : unknown */.name/* : unknown */()/* : unknown */;
	}
	/* @Override
        public */ replace(mapping : /* Map */<string, /* Type */>) : /* Type */ {
		return this/* : Template */;
	}
	/* @Override
        public */ findName() : /* Option */<string> {
		return this/* : Template */.base/* : unknown */.findName/* : unknown */()/* : unknown */;
	}
}
/* private */class Placeholder/*  */ {
	constructor (input : string) {
	}
	/* @Override
        public */ generate() : string {
		return /* generatePlaceholder */(this/* : Placeholder */.input/* : unknown */)/* : unknown */;
	}
	/* @Override
        public */ type() : /* Type */ {
		return /* Primitive */.Unknown/* : unknown */;
	}
	/* @Override
        public */ typeParams() : /* List */<string> {
		return /* Lists */.empty/* : unknown */()/* : unknown */;
	}
	/* @Override
        public */ find(name : string) : /* Option */</* Type */> {
		return new /* None */()/* : content-start None content-end */;
	}
	/* @Override
        public */ name() : string {
		return this/* : Placeholder */.input/* : unknown */;
	}
	/* @Override
        public */ replace(mapping : /* Map */<string, /* Type */>) : /* Type */ {
		return this/* : Placeholder */;
	}
	/* @Override
        public */ findName() : /* Option */<string> {
		return new /* None */()/* : content-start None content-end */;
	}
	/* @Override
        public */ createDefinition() : /* Option */</* Definition */> {
		return new /* None */()/* : content-start None content-end */;
	}
}
/* private */class StringValue/*  */ {
	constructor (stripped : string) {
	}
	/* @Override
        public */ generate() : string {
		return this/* : StringValue */.stripped/* : unknown */;
	}
	/* @Override
        public */ type() : /* Type */ {
		return /* Primitive */.Unknown/* : unknown */;
	}
}
/* private */class DataAccess/*  */ {
	constructor (parent : /* Value */, property : string, type : /* Type */) {
	}
	/* @Override
        public */ generate() : string {
		return this/* : DataAccess */.parent/* : unknown */.generate/* : unknown */()/* : unknown */ + "." + this/* : DataAccess */.property/* : unknown */ + /* createDebugString */(this/* : DataAccess */.type/* : () => content-start Type content-end */)/* : unknown */;
	}
	/* @Override
        public */ type() : /* Type */ {
		return this/* : DataAccess */.type/* : () => content-start Type content-end */;
	}
}
/* private */class ConstructionCaller/*  */ {
	constructor (type : /* Type */) {
	}
	/* @Override
        public */ generate() : string {
		return "new " + this/* : ConstructionCaller */.type/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
	/* public */ toFunction() : /* FunctionType */ {
		return new /* FunctionType */(/* Lists */.empty/* : unknown */()/* : unknown */, this/* : ConstructionCaller */.type/* : unknown */)/* : content-start FunctionType content-end */;
	}
}
/* private */class Operation/*  */ {
	constructor (left : /* Value */, operator : /* Operator */, right : /* Value */) {
	}
	/* @Override
        public */ generate() : string {
		return this/* : Operation */.left/* : unknown */()/* : unknown */.generate/* : unknown */()/* : unknown */ + " " + this/* : Operation */.operator/* : unknown */.targetRepresentation/* : unknown */ + " " + this/* : Operation */.right/* : unknown */()/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
	/* @Override
        public */ type() : /* Type */ {
		return /* Primitive */.Unknown/* : unknown */;
	}
}
/* private */class Not/*  */ {
	constructor (value : /* Value */) {
	}
	/* @Override
        public */ generate() : string {
		return "!" + this/* : Not */.value/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
	/* @Override
        public */ type() : /* Type */ {
		return /* Primitive */.Unknown/* : unknown */;
	}
}
/* private */class BlockLambdaValue/*  */ {
	constructor (depth : number, statements : /* List */</* FunctionSegment */>) {
	}
	/* @Override
        public */ generate() : string {
		return "{" + this.joinStatements() + createIndent(this.depth) + "}";
	}
	/* private */ joinStatements() : string {
		return this/* : BlockLambdaValue */.statements/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(/* FunctionSegment */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new /* Joiner */()/* : content-start Joiner content-end */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
	}
}
/* private */class Lambda/*  */ {
	constructor (parameters : /* List */</* Definition */>, body : /* LambdaValue */) {
	}
	/* @Override
        public */ generate() : string {
		let joined = this/* : Lambda */.parameters/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(/* Definition */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new /* Joiner */(", ")/* : content-start Joiner content-end */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return "(" + joined/* : unknown */ + ") => " + this/* : Lambda */.body/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
	/* @Override
        public */ type() : /* Type */ {
		return /* Primitive */.Unknown/* : unknown */;
	}
}
/* private */class Invokable/*  */ {
	constructor (caller : /* Caller */, arguments : /* List */</* Value */>, type : /* Type */) {
	}
	/* @Override
        public */ generate() : string {
		let joined = this/* : Invokable */.arguments/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(/* Value */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new /* Joiner */(", ")/* : content-start Joiner content-end */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return this/* : Invokable */.caller/* : unknown */.generate/* : unknown */()/* : unknown */ + "(" + joined/* : unknown */ + ")" + /* createDebugString */(this/* : Invokable */.type/* : unknown */)/* : unknown */;
	}
}
/* private */class IndexValue/*  */ {
	constructor (parent : /* Value */, child : /* Value */) {
	}
	/* @Override
        public */ generate() : string {
		return this/* : IndexValue */.parent/* : unknown */.generate/* : unknown */()/* : unknown */ + "[" + this.child.generate() + "]";
	}
	/* @Override
        public */ type() : /* Type */ {
		return /* Primitive */.Unknown/* : unknown */;
	}
}
/* private */class SymbolValue/*  */ {
	constructor (stripped : string, type : /* Type */) {
	}
	/* @Override
        public */ generate() : string {
		return this/* : SymbolValue */.stripped/* : unknown */ + /* createDebugString */(this/* : SymbolValue */.type/* : unknown */)/* : unknown */;
	}
}
/* private */class JVMMap<K, V>/*  */ {
	constructor (map : /* java.util.Map */<K, V>) {
	}
	JVMMap() : /* public */ {
		/* this(new HashMap<>()) */;
	}
	/* @Override
            public */ find(key : K) : /* Option */<V> {
		if (this/* : JVMMap */.map/* : unknown */.containsKey/* : unknown */(key/* : K */)/* : unknown */){
			return new /* Some */(this/* : JVMMap */.map/* : unknown */.get/* : unknown */(key/* : K */)/* : unknown */)/* : content-start Some content-end */;
		}
		return new /* None */()/* : content-start None content-end */;
	}
	/* @Override
            public */ with(key : K, value : V) : /* Map */<K, V> {
		/* this.map.put(key, value) */;
		return this/* : JVMMap */;
	}
}
/* private static */class Maps/*  */ {
	/* public static  */ empty<VK>() : /* Map */<K, V> {
		return new /* JVMMap */()/* : content-start JVMMap content-end */;
	}
}
/* private */class MapCollector<K, V>/*  */ {
	/* @Override
        public */ createInitial() : /* Map */<K, V> {
		return /* Maps */.empty/* : unknown */()/* : unknown */;
	}
	/* @Override
        public */ fold(current : /* Map */<K, V>, element : [K, V]) : /* Map */<K, V> {
		return current/* : content-start Map content-end<K, V> */.with/* : unknown */(element/* : [K, V] */[0/* : number */]()/* : unknown */, element/* : [K, V] */[1/* : number */]()/* : unknown */)/* : unknown */;
	}
}
/* private static */class ConstructorHeader/*  */ {
	/* @Override
        public */ createDefinition(paramTypes : /* List */</* Type */>) : /* Definition */ {
		return /* ImmutableDefinition */.createSimpleDefinition/* : unknown */("new", /* Primitive */.Unknown/* : unknown */)/* : unknown */;
	}
	/* @Override
        public */ generateWithParams(joinedParameters : string) : string {
		return "constructor " + joinedParameters/* : string */;
	}
}
/* private */class DefinitionStatement/*  */ {
	constructor (depth : number, definition : /* Definition */) {
	}
	/* @Override
        public */ generate() : string {
		return /* createIndent */(this/* : DefinitionStatement */.depth/* : unknown */)/* : unknown */ + this/* : DefinitionStatement */.definition/* : unknown */.generate/* : unknown */()/* : unknown */ + ";";
	}
}
/* private static */class Method/*  */ {
	/* private final */ depth : number;
	/* private final */ header : /* Header */;
	/* private final */ parameters : /* List */</* Definition */>;
	/* private final */ statements : /* Option */</* List */</* FunctionSegment */>>;
	Method(depth : number, header : /* Header */, parameters : /* List */</* Definition */>, maybeStatements : /* Option */</* List */</* FunctionSegment */>>) : /* public */ {
		this/* : Method */.depth/* : unknown */ = depth/* : number */;
		this/* : Method */.header/* : unknown */ = header/* : content-start Header content-end */;
		this/* : Method */.parameters/* : unknown */ = parameters/* : content-start List content-end<content-start Definition content-end> */;
		this/* : Method */.statements/* : unknown */ = maybeStatements/* : content-start Option content-end<content-start List content-end<content-start FunctionSegment content-end>> */;
	}
	/* private static */ joinStatements(statements : /* List */</* FunctionSegment */>) : string {
		return statements/* : content-start List content-end<content-start FunctionSegment content-end> */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(/* FunctionSegment */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new /* Joiner */()/* : content-start Joiner content-end */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
	}
	/* @Override
        public */ generate() : string {
		let indent = /* createIndent */(this/* : Method */.depth/* : unknown */)/* : unknown */;
		let generatedHeader = this/* : Method */.header/* : unknown */.generateWithParams/* : unknown */(/* joinValues */(this/* : Method */.parameters/* : unknown */)/* : unknown */)/* : unknown */;
		let generatedStatements = this/* : Method */.statements/* : unknown */.map/* : unknown */(Method/* : (arg0 : number, arg1 : content-start Header content-end, arg2 : content-start List content-end<content-start Definition content-end>, arg3 : content-start Option content-end<content-start List content-end<content-start FunctionSegment content-end>>) => content-start public content-end */.joinStatements/* : unknown */)/* : unknown */.map/* : unknown */((inner) => " {" + inner + indent + "}")/* : unknown */.orElse/* : unknown */(";")/* : unknown */;
		return indent/* : unknown */ + generatedHeader/* : unknown */ + generatedStatements/* : unknown */;
	}
}
/* private */class Block/*  */ {
	constructor (depth : number, header : /* BlockHeader */, statements : /* List */</* FunctionSegment */>) {
	}
	/* @Override
        public */ generate() : string {
		let indent = /* createIndent */(this/* : Block */.depth/* : unknown */)/* : unknown */;
		let collect = this/* : Block */.statements/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(/* FunctionSegment */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new /* Joiner */()/* : content-start Joiner content-end */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return indent/* : unknown */ + this/* : Block */.header/* : unknown */.generate/* : unknown */()/* : unknown */ + "{" + collect + indent + "}";
	}
}
/* private */class Conditional/*  */ {
	constructor (prefix : string, value1 : /* Value */) {
	}
	/* @Override
        public */ generate() : string {
		return this/* : Conditional */.prefix/* : unknown */ + " (" + this.value1.generate() + ")";
	}
}
/* private static */class Else/*  */ {
	/* @Override
        public */ generate() : string {
		return "else ";
	}
}
/* private static */class Return/*  */ {
	/* private final */ value1 : /* Value */;
	Return(value1 : /* Value */) : /* public */ {
		this/* : Return */.value1/* : unknown */ = value1/* : content-start Value content-end */;
	}
	/* @Override
        public */ generate() : string {
		return "return " + this/* : Return */.value1/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
}
/* private */class Initialization/*  */ {
	constructor (definition : /* Definition */, source : /* Value */) {
	}
	/* @Override
        public */ generate() : string {
		return "let " + this/* : Initialization */.definition/* : unknown */.generate/* : unknown */()/* : unknown */ + " = " + this/* : Initialization */.source/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
}
/* private */class Assignment/*  */ {
	constructor (destination : /* Value */, source : /* Value */) {
	}
	/* @Override
        public */ generate() : string {
		return this/* : Assignment */.destination/* : unknown */.generate/* : unknown */()/* : unknown */ + " = " + this/* : Assignment */.source/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
}
/* private */class Statement/*  */ {
	constructor (depth : number, value : /* StatementValue */) {
	}
	/* @Override
        public */ generate() : string {
		return /* createIndent */(this/* : Statement */.depth/* : unknown */)/* : unknown */ + this/* : Statement */.value/* : unknown */.generate/* : unknown */()/* : unknown */ + ";";
	}
}
/* private */class MethodPrototype/*  */ {
	constructor (depth : number, header : /* Header */, parameters : /* List */</* Definition */>, content : string) {
	}
	/* private */ findParamTypes() : /* List */</* Type */> {
		return this/* : MethodPrototype */.parameters/* : unknown */()/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(/* Definition */.type/* : unknown */)/* : unknown */.collect/* : unknown */(new /* ListCollector */()/* : content-start ListCollector content-end */)/* : unknown */;
	}
	/* @Override
        public */ createDefinition() : /* Option */</* Definition */> {
		return new /* Some */(this/* : MethodPrototype */.header/* : unknown */.createDefinition/* : unknown */(this/* : MethodPrototype */.findParamTypes/* : () => content-start List content-end<content-start Type content-end> */()/* : content-start List content-end<content-start Type content-end> */)/* : unknown */)/* : content-start Some content-end */;
	}
}
/* private */class IncompleteClassSegmentWrapper/*  */ {
	constructor (segment : /* ClassSegment */) {
	}
	/* @Override
        public */ createDefinition() : /* Option */</* Definition */> {
		return new /* None */()/* : content-start None content-end */;
	}
}
/* private */class Primitive/*  */ {
	/* Int("number"),
        String("string"),
        Boolean("boolean"),
       */ Unknown("unknown") : /*  */;
	/* private final */ value : string;
	constructor (value : string) {
		this/* : Primitive */.value/* : unknown */ = value/* : string */;
	}
	/* @Override
        public */ generate() : string {
		return this/* : Primitive */.value/* : unknown */;
	}
	/* @Override
        public */ replace(mapping : /* Map */<string, /* Type */>) : /* Type */ {
		return this/* : Primitive */;
	}
	/* @Override
        public */ findName() : /* Option */<string> {
		return new /* None */()/* : content-start None content-end */;
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
		this/* : Operator */.sourceRepresentation/* : unknown */ = sourceRepresentation/* : string */;
		this/* : Operator */.targetRepresentation/* : unknown */ = targetRepresentation/* : string */;
	}
}
/* private */class BooleanValue/*  */ {/* True("true"), False("false"); */
	/* private final */ value : string;
	constructor (value : string) {
		this/* : BooleanValue */.value/* : unknown */ = value/* : string */;
	}
	/* @Override
        public */ generate() : string {
		return this/* : BooleanValue */.value/* : unknown */;
	}
	/* @Override
        public */ type() : /* Type */ {
		return /* Primitive */.Boolean/* : unknown */;
	}
}
/* public */class Main/*  */ {/* 

    private static final boolean isDebug = true; */
	/* public static */ main() : /* void */ {
		/* try */{
			let parent = /* Paths */.get/* : unknown */(".", "src", "java", "magma")/* : unknown */;
			let source = parent/* : unknown */.resolve/* : unknown */("Main.java")/* : unknown */;
			let target = parent/* : unknown */.resolve/* : unknown */("main.ts")/* : unknown */;
			let input = /* Files */.readString/* : unknown */(source/* : unknown */)/* : unknown */;
			/* Files.writeString(target, compile(input)) */;
			/* new ProcessBuilder("cmd", "/c", "npm", "exec", "tsc")
                    .inheritIO()
                    .start()
                    .waitFor() */;
		}
		/* catch (IOException | InterruptedException e) */{
			/* throw new RuntimeException(e) */;
		}
	}
	/* private static */ compile(input : string) : string {
		let state : /* CompileState */ = new /* CompileState */()/* : content-start CompileState content-end */;
		let parsed = /* parseStatements */(state/* : content-start CompileState content-end */, input/* : unknown */, Main/* : Main */.compileRootSegment/* : unknown */)/* : unknown */;
		let joined = parsed/* : unknown */.left/* : unknown */()/* : unknown */.structures/* : unknown */.iterate/* : unknown */()/* : unknown */.collect/* : unknown */(new /* Joiner */()/* : content-start Joiner content-end */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return joined/* : unknown */ + /* generateStatements */(parsed/* : unknown */.right/* : unknown */()/* : unknown */)/* : unknown */;
	}
	/* private static */ generateStatements(statements : /* List */<string>) : string {
		return /* generateAll */(Main/* : Main */.mergeStatements/* : unknown */, statements/* : content-start List content-end<string> */)/* : unknown */;
	}
	/* private static  */ parseStatements<T>(state : /* CompileState */, input : string, mapper : (arg0 : /* CompileState */, arg1 : string) => [/* CompileState */, T]) : [/* CompileState */, /* List */<T>] {
		return /* parseAllWithIndices */(state/* : content-start CompileState content-end */, input/* : unknown */, Main/* : Main */.foldStatementChar/* : unknown */, (state3, tuple) => ((BiFunction<CompileState, String, Option<Tuple2<CompileState, T>>>) (state1, s) => /* new Some<>(mapper.apply(state1, s))).apply(state3, tuple */.right/* : unknown */(/* ) */)/* : unknown */)/* : unknown */.orElseGet/* : unknown */(() => new /* Tuple2Impl */(state/* : content-start CompileState content-end */, /* Lists */.empty/* : unknown */()/* : unknown */)/* : content-start Tuple2Impl content-end */)/* : unknown */;
	}
	/* private static */ generateAll(merger : (arg0 : string, arg1 : string) => string, elements : /* List */<string>) : string {
		return elements/* : content-start List content-end<string> */.iterate/* : unknown */()/* : unknown */.fold/* : unknown */("", merger/* : (arg0 : string, arg1 : string) => string */)/* : unknown */;
	}
	/* private static  */ parseAllWithIndices<T>(state : /* CompileState */, input : string, folder : (arg0 : /* DivideState */, arg1 : string) => /* DivideState */, mapper : (arg0 : /* CompileState */, arg1 : [number, string]) => /* Option */<[/* CompileState */, T]>) : /* Option */<[/* CompileState */, /* List */<T>]> {
		let stringList = /* divideAll */(input/* : unknown */, folder/* : (arg0 : content-start DivideState content-end, arg1 : string) => content-start DivideState content-end */)/* : unknown */;
		return /* mapUsingState */(state/* : content-start CompileState content-end */, stringList/* : unknown */, mapper/* : (arg0 : content-start CompileState content-end, arg1 : string) => [content-start CompileState content-end, T] */)/* : unknown */;
	}
	/* private static  */ mapUsingState<TR>(state : /* CompileState */, elements : /* List */<T>, mapper : (arg0 : /* CompileState */, arg1 : [number, T]) => /* Option */<[/* CompileState */, R]>) : /* Option */<[/* CompileState */, /* List */<R>]> {
		let initial : /* Option */<[/* CompileState */, /* List */<R>]> = new /* Some */(new /* Tuple2Impl */(state/* : content-start CompileState content-end */, /* Lists */.empty/* : unknown */()/* : unknown */)/* : content-start Tuple2Impl content-end */)/* : content-start Some content-end */;
		return elements/* : content-start List content-end<string> */.iterateWithIndices/* : unknown */()/* : unknown */.fold/* : unknown */(initial/* : content-start Option content-end<[content-start CompileState content-end, content-start List content-end<R>]> */, (tuple, element) => {
			return tuple/* : unknown */.flatMap/* : unknown */((inner) => {
				let state1 = inner/* : unknown */.left/* : unknown */()/* : unknown */;
				let right = inner/* : unknown */.right/* : unknown */()/* : unknown */;
				return mapper/* : (arg0 : content-start CompileState content-end, arg1 : string) => [content-start CompileState content-end, T] */(state1/* : unknown */, element/* : unknown */)/* : [content-start CompileState content-end, T] */.map/* : unknown */((applied) => {
					return new /* Tuple2Impl */(applied/* : unknown */.left/* : unknown */()/* : unknown */, right/* : unknown */.addLast/* : unknown */(applied/* : unknown */.right/* : unknown */()/* : unknown */)/* : unknown */)/* : content-start Tuple2Impl content-end */;
				})/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ mergeStatements(cache : string, statement : string) : string {
		return cache/* : string */ + statement/* : string */;
	}
	/* private static */ divideAll(input : string, folder : (arg0 : /* DivideState */, arg1 : string) => /* DivideState */) : /* List */<string> {
		let current : /* DivideState */ = new /* DivideState */(input/* : unknown */)/* : content-start DivideState content-end */;
		while (true){
			let maybePopped = current/* : content-start DivideState content-end */.pop/* : unknown */()/* : unknown */.map/* : unknown */((tuple) => {
				return /* foldSingleQuotes */(tuple/* : unknown */)/* : unknown */.or/* : unknown */(() => /* foldDoubleQuotes */(tuple/* : unknown */)/* : unknown */)/* : unknown */.orElseGet/* : unknown */(() => folder/* : (arg0 : content-start DivideState content-end, arg1 : string) => content-start DivideState content-end */(tuple/* : unknown */.right/* : unknown */()/* : unknown */, tuple/* : unknown */.left/* : unknown */()/* : unknown */)/* : content-start DivideState content-end */)/* : unknown */;
			})/* : unknown */;
			if (maybePopped/* : unknown */.isPresent/* : unknown */()/* : unknown */){
				current/* : content-start DivideState content-end */ = maybePopped/* : unknown */.orElse/* : unknown */(current/* : content-start DivideState content-end */)/* : unknown */;
			}
			else {
				/* break */;
			}
		}
		return current/* : content-start DivideState content-end */.advance/* : unknown */()/* : unknown */.segments/* : unknown */;
	}
	/* private static */ foldDoubleQuotes(tuple : [string, /* DivideState */]) : /* Option */</* DivideState */> {
		if (tuple/* : unknown */.left/* : unknown */()/* : unknown */ === /*  '\"' */){
			let current = tuple/* : unknown */.right/* : unknown */()/* : unknown */.append/* : unknown */(tuple/* : unknown */.left/* : unknown */()/* : unknown */)/* : unknown */;
			while (true){
				let maybePopped = current/* : content-start DivideState content-end */.popAndAppendToTuple/* : unknown */()/* : unknown */;
				if (maybePopped/* : unknown */.isEmpty/* : unknown */()/* : unknown */){
					/* break */;
				}
				let popped = maybePopped/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */;
				current/* : content-start DivideState content-end */ = popped/* : unknown */.right/* : unknown */()/* : unknown */;
				if (popped/* : unknown */.left/* : unknown */()/* : unknown */ === /*  '\\' */){
					current/* : content-start DivideState content-end */ = current/* : content-start DivideState content-end */.popAndAppendToOption/* : unknown */()/* : unknown */.orElse/* : unknown */(current/* : content-start DivideState content-end */)/* : unknown */;
				}
				if (popped/* : unknown */.left/* : unknown */()/* : unknown */ === /*  '\"' */){
					/* break */;
				}
			}
			return new /* Some */(current/* : content-start DivideState content-end */)/* : content-start Some content-end */;
		}
		return new /* None */()/* : content-start None content-end */;
	}
	/* private static */ foldSingleQuotes(tuple : [string, /* DivideState */]) : /* Option */</* DivideState */> {
		if (/* tuple.left() != '\'' */){
			return new /* None */()/* : content-start None content-end */;
		}
		let appended = tuple/* : unknown */.right/* : unknown */()/* : unknown */.append/* : unknown */(tuple/* : unknown */.left/* : unknown */()/* : unknown */)/* : unknown */;
		return appended/* : unknown */.popAndAppendToTuple/* : unknown */()/* : unknown */.map/* : unknown */(Main/* : Main */.foldEscaped/* : unknown */)/* : unknown */.flatMap/* : unknown */(/* DivideState */.popAndAppendToOption/* : unknown */)/* : unknown */;
	}
	/* private static */ foldEscaped(escaped : [string, /* DivideState */]) : /* DivideState */ {
		if (escaped/* : [string, content-start DivideState content-end] */[0/* : number */]()/* : unknown */ === /*  '\\' */){
			return escaped/* : [string, content-start DivideState content-end] */[1/* : number */]()/* : unknown */.popAndAppendToOption/* : unknown */()/* : unknown */.orElse/* : unknown */(escaped/* : [string, content-start DivideState content-end] */[1/* : number */]()/* : unknown */)/* : unknown */;
		}
		return escaped/* : [string, content-start DivideState content-end] */[1/* : number */]()/* : unknown */;
	}
	/* private static */ foldStatementChar(state : /* DivideState */, c : string) : /* DivideState */ {
		let append = state/* : content-start CompileState content-end */.append/* : unknown */(c/* : string */)/* : unknown */;
		if (c/* : string */ === /*  ';'  */ && append/* : unknown */.isLevel/* : unknown */()/* : unknown */){
			return append/* : unknown */.advance/* : unknown */()/* : unknown */;
		}
		if (c/* : string */ === /*  '}'  */ && append/* : unknown */.isShallow/* : unknown */()/* : unknown */){
			return append/* : unknown */.advance/* : unknown */()/* : unknown */.exit/* : unknown */()/* : unknown */;
		}
		if (c/* : string */ === /*  '{'  */ || c/* : string */ === /*  '(' */){
			return append/* : unknown */.enter/* : unknown */()/* : unknown */;
		}
		if (c/* : string */ === /*  '}'  */ || c/* : string */ === /*  ')' */){
			return append/* : unknown */.exit/* : unknown */()/* : unknown */;
		}
		return append/* : unknown */;
	}
	/* private static */ compileRootSegment(state : /* CompileState */, input : string) : [/* CompileState */, string] {
		let stripped = input/* : unknown */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.startsWith/* : unknown */("package ")/* : unknown */ || stripped/* : unknown */.startsWith/* : unknown */("import ")/* : unknown */){
			return new /* Tuple2Impl */(state/* : content-start CompileState content-end */, "")/* : content-start Tuple2Impl content-end */;
		}
		return /* parseClass */(stripped/* : unknown */, state/* : content-start CompileState content-end */)/* : unknown */.map/* : unknown */((tuple) => {
			return new /* Tuple2Impl */(tuple/* : unknown */.left/* : unknown */()/* : unknown */, tuple/* : unknown */.right/* : unknown */()/* : unknown */.segment/* : unknown */.generate/* : unknown */()/* : unknown */)/* : content-start Tuple2Impl content-end */;
		})/* : unknown */.orElseGet/* : unknown */(() => new /* Tuple2Impl */(state/* : content-start CompileState content-end */, /* generatePlaceholder */(stripped/* : unknown */)/* : unknown */)/* : content-start Tuple2Impl content-end */)/* : unknown */;
	}
	/* private static */ parseClass(stripped : string, state : /* CompileState */) : /* Option */<[/* CompileState */, /* IncompleteClassSegmentWrapper */]> {
		return /* parseStructure */(stripped/* : unknown */, "class ", "class ", state/* : content-start CompileState content-end */)/* : unknown */;
	}
	/* private static */ parseStructure(stripped : string, sourceInfix : string, targetInfix : string, state : /* CompileState */) : /* Option */<[/* CompileState */, /* IncompleteClassSegmentWrapper */]> {
		return /* first */(stripped/* : unknown */, sourceInfix/* : string */, (beforeInfix, right) => {
			return /* first */(right/* : unknown */, "{", (beforeContent, withEnd) => {
				return /* suffix */(withEnd/* : unknown */.strip/* : unknown */()/* : unknown */, "}", (content1) => {
					return /* getOr */(targetInfix/* : string */, state/* : content-start CompileState content-end */, beforeInfix/* : unknown */, beforeContent/* : unknown */, content1/* : unknown */)/* : unknown */;
				})/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ getOr(targetInfix : string, state : /* CompileState */, beforeInfix : string, beforeContent : string, content1 : string) : /* Option */<[/* CompileState */, /* IncompleteClassSegmentWrapper */]> {
		return /* first */(beforeContent/* : unknown */, " implements ", (s, s2) => {
			return /* structureWithMaybeExtends */(targetInfix/* : string */, state/* : content-start CompileState content-end */, beforeInfix/* : unknown */, s/* : unknown */, content1/* : unknown */)/* : unknown */;
		})/* : unknown */.or/* : unknown */(() => {
			return /* structureWithMaybeExtends */(targetInfix/* : string */, state/* : content-start CompileState content-end */, beforeInfix/* : unknown */, beforeContent/* : unknown */, content1/* : unknown */)/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ structureWithMaybeExtends(targetInfix : string, state : /* CompileState */, beforeInfix : string, beforeContent : string, content1 : string) : /* Option */<[/* CompileState */, /* IncompleteClassSegmentWrapper */]> {
		return /* first */(beforeContent/* : unknown */, " extends ", (s, s2) => {
			return /* structureWithMaybeParams */(targetInfix/* : string */, state/* : content-start CompileState content-end */, beforeInfix/* : unknown */, s/* : unknown */, content1/* : unknown */)/* : unknown */;
		})/* : unknown */.or/* : unknown */(() => {
			return /* structureWithMaybeParams */(targetInfix/* : string */, state/* : content-start CompileState content-end */, beforeInfix/* : unknown */, beforeContent/* : unknown */, content1/* : unknown */)/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ structureWithMaybeParams(targetInfix : string, state : /* CompileState */, beforeInfix : string, beforeContent : string, content1 : string) : /* Option */<[/* CompileState */, /* IncompleteClassSegmentWrapper */]> {
		return /* suffix */(beforeContent/* : unknown */.strip/* : unknown */()/* : unknown */, ")", (s) => {
			return /* first */(s/* : unknown */, "(", (s1, s2) => {
				let parsed = /* parseParameters */(state/* : content-start CompileState content-end */, s2/* : unknown */)/* : unknown */;
				return /* getOred */(targetInfix/* : string */, parsed/* : unknown */.left/* : unknown */()/* : unknown */, beforeInfix/* : unknown */, s1/* : unknown */, content1/* : unknown */, parsed/* : unknown */.right/* : unknown */()/* : unknown */)/* : unknown */;
			})/* : unknown */;
		})/* : unknown */.or/* : unknown */(() => {
			return /* getOred */(targetInfix/* : string */, state/* : content-start CompileState content-end */, beforeInfix/* : unknown */, beforeContent/* : unknown */, content1/* : unknown */, /* Lists */.empty/* : unknown */()/* : unknown */)/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ getOred(targetInfix : string, state : /* CompileState */, beforeInfix : string, beforeContent : string, content1 : string, params : /* List */</* Parameter */>) : /* Option */<[/* CompileState */, /* IncompleteClassSegmentWrapper */]> {
		return /* first */(beforeContent/* : unknown */, "<", (name, withTypeParams) => {
			return /* first */(withTypeParams/* : unknown */, ">", (typeParamsString, afterTypeParams) => {
				let /* final */ mapper : (arg0 : /* CompileState */, arg1 : string) => [/* CompileState */, string] = (state1, s) => new /* Tuple2Impl */(state1/* : unknown */, s/* : unknown */.strip/* : unknown */()/* : unknown */)/* : content-start Tuple2Impl content-end */;
				let typeParams = /* parseValuesOrEmpty */(state/* : content-start CompileState content-end */, typeParamsString/* : unknown */, (state1, s) => new /* Some */(mapper/* : (arg0 : content-start CompileState content-end, arg1 : string) => [content-start CompileState content-end, T] */(state1/* : unknown */, s/* : unknown */)/* : [content-start CompileState content-end, T] */)/* : content-start Some content-end */)/* : unknown */;
				return /* assembleStructure */(typeParams/* : unknown */.left/* : unknown */()/* : unknown */, targetInfix/* : string */, beforeInfix/* : unknown */, name/* : unknown */, content1/* : unknown */, typeParams/* : unknown */.right/* : unknown */()/* : unknown */, afterTypeParams/* : unknown */, params/* : content-start List content-end<content-start Parameter content-end> */)/* : unknown */;
			})/* : unknown */;
		})/* : unknown */.or/* : unknown */(() => {
			return /* assembleStructure */(state/* : content-start CompileState content-end */, targetInfix/* : string */, beforeInfix/* : unknown */, beforeContent/* : unknown */, content1/* : unknown */, /* Lists */.empty/* : unknown */()/* : unknown */, "", params/* : content-start List content-end<content-start Parameter content-end> */)/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ assembleStructure(state : /* CompileState */, targetInfix : string, beforeInfix : string, rawName : string, content : string, typeParams : /* List */<string>, after : string, rawParameters : /* List */</* Parameter */>) : /* Option */<[/* CompileState */, /* IncompleteClassSegmentWrapper */]> {
		let name = rawName/* : string */.strip/* : unknown */()/* : unknown */;
		if (!/* isSymbol */(name/* : unknown */)/* : unknown */){
			return new /* None */()/* : content-start None content-end */;
		}
		let segmentsTuple : [/* CompileState */, /* List */<T>] = parseStatements/* : (arg0 : content-start CompileState content-end, arg1 : string, arg2 : (arg0 : content-start CompileState content-end, arg1 : string) => [content-start CompileState content-end, T]) => [content-start CompileState content-end, content-start List content-end<T>] */(state/* : content-start CompileState content-end */.pushStructName/* : unknown */(name/* : unknown */)/* : unknown */.withTypeParams/* : unknown */(typeParams/* : unknown */)/* : unknown */, content/* : string */, (state0, input) => /* parseClassSegment */(state0/* : unknown */, input/* : unknown */, 1/* : number */)/* : unknown */)/* : [content-start CompileState content-end, content-start List content-end<T>] */;
		let segmentsState = segmentsTuple/* : [content-start CompileState content-end, content-start List content-end<T>] */[0/* : number */]()/* : unknown */;
		let segments = segmentsTuple/* : [content-start CompileState content-end, content-start List content-end<T>] */[1/* : number */]()/* : unknown */;
		let definitions = segments/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(/* IncompleteClassSegment */.createDefinition/* : unknown */)/* : unknown */.flatMap/* : unknown */(/* Iterators */.fromOption/* : unknown */)/* : unknown */.collect/* : unknown */(new /* ListCollector */()/* : content-start ListCollector content-end */)/* : unknown */;
		let objectType : /* ObjectType */ = new /* ObjectType */(name/* : unknown */, typeParams/* : unknown */, definitions/* : unknown */)/* : content-start ObjectType content-end */;
		let state2 = segmentsState/* : unknown */.enterDefinitions/* : unknown */()/* : unknown */.withDefinition/* : unknown */(/* ImmutableDefinition */.createSimpleDefinition/* : unknown */("this", objectType/* : content-start ObjectType content-end */)/* : unknown */)/* : unknown */;
		return mapUsingState/* : (arg0 : content-start CompileState content-end, arg1 : content-start List content-end<T>, arg2 : (arg0 : content-start CompileState content-end, arg1 : [number, T]) => content-start Option content-end<[content-start CompileState content-end, R]>) => content-start Option content-end<[content-start CompileState content-end, content-start List content-end<R>]> */(state2/* : unknown */, segments/* : unknown */, (state1, entry) => /* switch (entry.right()) {
            case IncompleteClassSegmentWrapper wrapper  */ - /* > new Some<>(new Tuple2Impl<>(state1, wrapper.segment));
            case MethodPrototype methodPrototype  */ - /* > completeMethod(state1, methodPrototype);
            case Whitespace whitespace  */ - /* > new Some<>(new Tuple2Impl<>(state1, whitespace));
            case Placeholder placeholder  */ - /* > new Some<>(new Tuple2Impl<>(state1, placeholder));
        } */)/* : content-start Option content-end<[content-start CompileState content-end, content-start List content-end<R>]> */.map/* : unknown */((completedTuple) => {
			let completedState = completedTuple/* : unknown */.left/* : unknown */()/* : unknown */;
			let completed = completedTuple/* : unknown */.right/* : unknown */()/* : unknown */;
			return /* completeStructure */(completedState/* : unknown */.exitDefinitions/* : unknown */()/* : unknown */, targetInfix/* : string */, beforeInfix/* : unknown */, name/* : unknown */, typeParams/* : unknown */, /* retainDefinitions */(rawParameters/* : content-start List content-end<content-start Parameter content-end> */)/* : unknown */, after/* : string */, completed/* : unknown */)/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ completeStructure(state : /* CompileState */, targetInfix : string, beforeInfix : string, name : string, typeParams : /* List */<string>, parameters : /* List */</* Definition */>, after : string, segments : /* List */</* ClassSegment */>) : /* Tuple2Impl */</* CompileState */, /* IncompleteClassSegmentWrapper */> {
		/* List<ClassSegment> withMaybeConstructor */;
		if (parameters/* : content-start List content-end<content-start Definition content-end> */.isEmpty/* : unknown */()/* : unknown */){
			/* withMaybeConstructor */ = segments/* : unknown */;
		}
		else {
			/* withMaybeConstructor */ = segments/* : unknown */.addFirst/* : unknown */(new /* Method */(1/* : number */, new /* ConstructorHeader */()/* : content-start ConstructorHeader content-end */, parameters/* : content-start List content-end<content-start Definition content-end> */, new /* Some */(/* Lists */.empty/* : unknown */()/* : unknown */)/* : content-start Some content-end */)/* : content-start Method content-end */)/* : unknown */;
		}
		let parsed2 = /* withMaybeConstructor */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(/* ClassSegment */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new /* Joiner */()/* : content-start Joiner content-end */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		let joinedTypeParams = typeParams/* : unknown */.iterate/* : unknown */()/* : unknown */.collect/* : unknown */(new /* Joiner */(", ")/* : content-start Joiner content-end */)/* : unknown */.map/* : unknown */((inner) => "<" + inner + ">")/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		let generated = /* generatePlaceholder */(beforeInfix/* : unknown */.strip/* : unknown */()/* : unknown */)/* : unknown */ + targetInfix/* : string */ + name/* : unknown */ + joinedTypeParams/* : unknown */ + /* generatePlaceholder */(after/* : string */)/* : unknown */ + " {" + parsed2 + "\n}\n";
		let definedState = state/* : content-start CompileState content-end */.popStructName/* : unknown */()/* : unknown */.addStructure/* : unknown */(generated/* : unknown */)/* : unknown */;
		return new /* Tuple2Impl */(definedState/* : unknown */, new /* IncompleteClassSegmentWrapper */(new /* Whitespace */()/* : content-start Whitespace content-end */)/* : content-start IncompleteClassSegmentWrapper content-end */)/* : content-start Tuple2Impl content-end */;
	}
	/* private static */ retainDefinition(parameter : /* Parameter */) : /* Option */</* Definition */> {
		if (parameter/* : content-start Parameter content-end */._variant/* : unknown */ === Variant./* : unknown */){
			return new /* Some */(/* definition */)/* : content-start Some content-end */;
		}
		return new /* None */()/* : content-start None content-end */;
	}
	/* private static */ isSymbol(input : string) : boolean {
		/* for (var i = 0; i < input.length(); i++) */{
			let c = input/* : unknown */.charAt/* : unknown */(/* i */)/* : unknown */;
			if (/* Character */.isLetter/* : unknown */(c/* : string */)/* : unknown */ || /*  */(/* i != 0  */ && /* Character */.isDigit/* : unknown */(c/* : string */)/* : unknown */)/* : unknown */){
				/* continue */;
			}
			return false;
		}
		return true;
	}
	/* private static  */ prefix<T>(input : string, prefix : string, mapper : (arg0 : string) => /* Option */<T>) : /* Option */<T> {
		if (!input/* : unknown */.startsWith/* : unknown */(prefix/* : string */)/* : unknown */){
			return new /* None */()/* : content-start None content-end */;
		}
		let slice = input/* : unknown */.substring/* : unknown */(prefix/* : string */.length/* : unknown */()/* : unknown */)/* : unknown */;
		return mapper/* : (arg0 : content-start CompileState content-end, arg1 : string) => [content-start CompileState content-end, T] */(slice/* : unknown */)/* : [content-start CompileState content-end, T] */;
	}
	/* private static  */ suffix<T>(input : string, suffix : string, mapper : (arg0 : string) => /* Option */<T>) : /* Option */<T> {
		if (!input/* : unknown */.endsWith/* : unknown */(suffix/* : string */)/* : unknown */){
			return new /* None */()/* : content-start None content-end */;
		}
		let slice = input/* : unknown */.substring/* : unknown */(0/* : number */, input/* : unknown */.length/* : unknown */()/* : unknown */ - suffix/* : string */.length/* : unknown */()/* : unknown */)/* : unknown */;
		return mapper/* : (arg0 : content-start CompileState content-end, arg1 : string) => [content-start CompileState content-end, T] */(slice/* : unknown */)/* : [content-start CompileState content-end, T] */;
	}
	/* private static */ parseClassSegment(state : /* CompileState */, input : string, depth : number) : [/* CompileState */, /* IncompleteClassSegment */] {
		return /* Main.<Whitespace, IncompleteClassSegment>typed */(() => /* parseWhitespace */(input/* : unknown */, state/* : content-start CompileState content-end */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* typed */(() => parseClass/* : (arg0 : string, arg1 : content-start CompileState content-end) => content-start Option content-end<[content-start CompileState content-end, content-start IncompleteClassSegmentWrapper content-end]> */(input/* : unknown */, state/* : content-start CompileState content-end */)/* : content-start Option content-end<[content-start CompileState content-end, content-start IncompleteClassSegmentWrapper content-end]> */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* typed */(() => parseStructure/* : (arg0 : string, arg1 : string, arg2 : string, arg3 : content-start CompileState content-end) => content-start Option content-end<[content-start CompileState content-end, content-start IncompleteClassSegmentWrapper content-end]> */(input/* : unknown */, "interface ", "interface ", state/* : content-start CompileState content-end */)/* : content-start Option content-end<[content-start CompileState content-end, content-start IncompleteClassSegmentWrapper content-end]> */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* typed */(() => parseStructure/* : (arg0 : string, arg1 : string, arg2 : string, arg3 : content-start CompileState content-end) => content-start Option content-end<[content-start CompileState content-end, content-start IncompleteClassSegmentWrapper content-end]> */(input/* : unknown */, "record ", "class ", state/* : content-start CompileState content-end */)/* : content-start Option content-end<[content-start CompileState content-end, content-start IncompleteClassSegmentWrapper content-end]> */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* typed */(() => parseStructure/* : (arg0 : string, arg1 : string, arg2 : string, arg3 : content-start CompileState content-end) => content-start Option content-end<[content-start CompileState content-end, content-start IncompleteClassSegmentWrapper content-end]> */(input/* : unknown */, "enum ", "class ", state/* : content-start CompileState content-end */)/* : content-start Option content-end<[content-start CompileState content-end, content-start IncompleteClassSegmentWrapper content-end]> */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseMethod */(state/* : content-start CompileState content-end */, input/* : unknown */, depth/* : number */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* typed */(() => /* parseDefinitionStatement */(input/* : unknown */, depth/* : number */, state/* : content-start CompileState content-end */)/* : unknown */)/* : unknown */)/* : unknown */.orElseGet/* : unknown */(() => new /* Tuple2Impl */(state/* : content-start CompileState content-end */, new /* Placeholder */(input/* : unknown */)/* : content-start Placeholder content-end */)/* : content-start Tuple2Impl content-end */)/* : unknown */;
	}
	/* private static  */ typed<T extends SS>(action : () => /* Option */<[/* CompileState */, T]>) : /* Option */<[/* CompileState */, S]> {
		return action/* : () => content-start Option content-end<[content-start CompileState content-end, T]> */()/* : content-start Option content-end<[content-start CompileState content-end, T]> */.map/* : unknown */((tuple) => new /* Tuple2Impl */(tuple/* : unknown */.left/* : unknown */()/* : unknown */, tuple/* : unknown */.right/* : unknown */()/* : unknown */)/* : content-start Tuple2Impl content-end */)/* : unknown */;
	}
	/* private static */ parseWhitespace(input : string, state : /* CompileState */) : /* Option */<[/* CompileState */, /* Whitespace */]> {
		if (input/* : unknown */.isBlank/* : unknown */()/* : unknown */){
			return new /* Some */(new /* Tuple2Impl */(state/* : content-start CompileState content-end */, new /* Whitespace */()/* : content-start Whitespace content-end */)/* : content-start Tuple2Impl content-end */)/* : content-start Some content-end */;
		}
		return new /* None */()/* : content-start None content-end */;
	}
	/* private static */ parseMethod(state : /* CompileState */, input : string, depth : number) : /* Option */<[/* CompileState */, /* IncompleteClassSegment */]> {
		return /* first */(input/* : unknown */, "(", (definitionString, withParams) => {
			return /* first */(withParams/* : unknown */, ")", (parametersString, rawContent) => {
				return /* parseDefinition(state, definitionString).<Tuple2<CompileState, Header>>map */((tuple) => new /* Tuple2Impl */(tuple/* : unknown */.left/* : unknown */()/* : unknown */, tuple/* : unknown */.right/* : unknown */()/* : unknown */)/* : content-start Tuple2Impl content-end */)/* : unknown */.or/* : unknown */(() => /* parseConstructor */(state/* : content-start CompileState content-end */, definitionString/* : unknown */)/* : unknown */)/* : unknown */.flatMap/* : unknown */((definitionTuple) => /* assembleMethod */(depth/* : number */, parametersString/* : unknown */, rawContent/* : unknown */, definitionTuple/* : unknown */)/* : unknown */)/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ assembleMethod(depth : number, parametersString : string, rawContent : string, definitionTuple : [/* CompileState */, /* Header */]) : /* Option */<[/* CompileState */, /* IncompleteClassSegment */]> {
		let definitionState = definitionTuple/* : unknown */.left/* : unknown */()/* : unknown */;
		let header = definitionTuple/* : unknown */.right/* : unknown */()/* : unknown */;
		let parametersTuple = /* parseParameters */(definitionState/* : unknown */, parametersString/* : unknown */)/* : unknown */;
		let rawParameters = parametersTuple/* : unknown */.right/* : unknown */()/* : unknown */;
		let parameters = /* retainDefinitions */(rawParameters/* : content-start List content-end<content-start Parameter content-end> */)/* : unknown */;
		return new /* Some */(new /* Tuple2Impl */(parametersTuple/* : unknown */.left/* : unknown */()/* : unknown */, new /* MethodPrototype */(depth/* : number */, header/* : unknown */, parameters/* : content-start List content-end<content-start Definition content-end> */, rawContent/* : unknown */.strip/* : unknown */()/* : unknown */)/* : content-start MethodPrototype content-end */)/* : content-start Tuple2Impl content-end */)/* : content-start Some content-end */;
	}
	/* private static */ completeMethod(state : /* CompileState */, prototype : /* MethodPrototype */) : /* Option */<[/* CompileState */, /* ClassSegment */]> {
		let paramTypes = prototype/* : content-start MethodPrototype content-end */.findParamTypes/* : unknown */()/* : unknown */;
		let toDefine = prototype/* : content-start MethodPrototype content-end */.header/* : unknown */()/* : unknown */.createDefinition/* : unknown */(paramTypes/* : unknown */)/* : unknown */;
		if (prototype/* : content-start MethodPrototype content-end */.content/* : unknown */()/* : unknown */.equals/* : unknown */(";")/* : unknown */){
			return new /* Some */(new /* Tuple2Impl */(state/* : content-start CompileState content-end */.withDefinition/* : unknown */(toDefine/* : unknown */)/* : unknown */, new /* Method */(prototype/* : content-start MethodPrototype content-end */.depth/* : unknown */()/* : unknown */, prototype/* : content-start MethodPrototype content-end */.header/* : unknown */()/* : unknown */, prototype/* : content-start MethodPrototype content-end */.parameters/* : unknown */()/* : unknown */, new /* None */()/* : content-start None content-end */)/* : content-start Method content-end */)/* : content-start Tuple2Impl content-end */)/* : content-start Some content-end */;
		}
		if (prototype/* : content-start MethodPrototype content-end */.content/* : unknown */()/* : unknown */.startsWith/* : unknown */("{")/* : unknown */ && prototype/* : content-start MethodPrototype content-end */.content/* : unknown */()/* : unknown */.endsWith/* : unknown */("}")/* : unknown */){
			let substring = prototype/* : content-start MethodPrototype content-end */.content/* : unknown */()/* : unknown */.substring/* : unknown */(1/* : number */, prototype/* : content-start MethodPrototype content-end */.content/* : unknown */()/* : unknown */.length/* : unknown */()/* : unknown */ - 1/* : number */)/* : unknown */;
			let withDefined = state/* : content-start CompileState content-end */.withDefinitions/* : unknown */(prototype/* : content-start MethodPrototype content-end */.parameters/* : unknown */()/* : unknown */)/* : unknown */;
			let statementsTuple : [/* CompileState */, /* List */<T>] = parseStatements/* : (arg0 : content-start CompileState content-end, arg1 : string, arg2 : (arg0 : content-start CompileState content-end, arg1 : string) => [content-start CompileState content-end, T]) => [content-start CompileState content-end, content-start List content-end<T>] */(withDefined/* : unknown */, substring/* : unknown */, (state1, input1) => /* parseFunctionSegment */(state1/* : unknown */, input1/* : unknown */, prototype/* : content-start MethodPrototype content-end */.depth/* : unknown */()/* : unknown */ + 1/* : number */)/* : unknown */)/* : [content-start CompileState content-end, content-start List content-end<T>] */;
			let statements = statementsTuple/* : [content-start CompileState content-end, content-start List content-end<T>] */[1/* : number */]()/* : unknown */;
			return new /* Some */(new /* Tuple2Impl */(statementsTuple/* : [content-start CompileState content-end, content-start List content-end<T>] */[0/* : number */]()/* : unknown */.withDefinition/* : unknown */(toDefine/* : unknown */)/* : unknown */, new /* Method */(prototype/* : content-start MethodPrototype content-end */.depth/* : unknown */()/* : unknown */, prototype/* : content-start MethodPrototype content-end */.header/* : unknown */()/* : unknown */, prototype/* : content-start MethodPrototype content-end */.parameters/* : unknown */()/* : unknown */, new /* Some */(statements/* : content-start List content-end<string> */)/* : content-start Some content-end */)/* : content-start Method content-end */)/* : content-start Tuple2Impl content-end */)/* : content-start Some content-end */;
		}
		return new /* None */()/* : content-start None content-end */;
	}
	/* private static */ parseConstructor(state : /* CompileState */, input : string) : /* Option */<[/* CompileState */, /* Header */]> {
		let stripped = input/* : unknown */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.equals/* : unknown */(state/* : content-start CompileState content-end */.structNames/* : unknown */.last/* : unknown */()/* : unknown */.orElse/* : unknown */("")/* : unknown */)/* : unknown */){
			return new /* Some */(new /* Tuple2Impl */(state/* : content-start CompileState content-end */, new /* ConstructorHeader */()/* : content-start ConstructorHeader content-end */)/* : content-start Tuple2Impl content-end */)/* : content-start Some content-end */;
		}
		return new /* None */()/* : content-start None content-end */;
	}
	/* private static */ joinValues(retainParameters : /* List */</* Definition */>) : string {
		let inner = retainParameters/* : content-start List content-end<content-start Definition content-end> */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(/* Definition */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new /* Joiner */(", ")/* : content-start Joiner content-end */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return "(" + inner + ")";
	}
	/* private static */ retainDefinitions(right : /* List */</* Parameter */>) : /* List */</* Definition */> {
		return right/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(Main/* : Main */.retainDefinition/* : unknown */)/* : unknown */.flatMap/* : unknown */(/* Iterators */.fromOption/* : unknown */)/* : unknown */.collect/* : unknown */(new /* ListCollector */()/* : content-start ListCollector content-end */)/* : unknown */;
	}
	/* private static */ parseParameters(state : /* CompileState */, params : string) : [/* CompileState */, /* List */</* Parameter */>] {
		return /* parseValuesOrEmpty */(state/* : content-start CompileState content-end */, params/* : content-start List content-end<content-start Parameter content-end> */, (state1, s) => new /* Some */(/* parseParameter */(state1/* : unknown */, s/* : unknown */)/* : unknown */)/* : content-start Some content-end */)/* : unknown */;
	}
	/* private static */ parseFunctionSegments(state : /* CompileState */, input : string, depth : number) : [/* CompileState */, /* List */</* FunctionSegment */>] {
		return parseStatements/* : (arg0 : content-start CompileState content-end, arg1 : string, arg2 : (arg0 : content-start CompileState content-end, arg1 : string) => [content-start CompileState content-end, T]) => [content-start CompileState content-end, content-start List content-end<T>] */(state/* : content-start CompileState content-end */, input/* : unknown */, (state1, input1) => /* parseFunctionSegment */(state1/* : unknown */, input1/* : unknown */, depth/* : number */ + 1/* : number */)/* : unknown */)/* : [content-start CompileState content-end, content-start List content-end<T>] */;
	}
	/* private static */ parseFunctionSegment(state : /* CompileState */, input : string, depth : number) : [/* CompileState */, /* FunctionSegment */] {
		let stripped = input/* : unknown */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.isEmpty/* : unknown */()/* : unknown */){
			return new /* Tuple2Impl */(state/* : content-start CompileState content-end */, new /* Whitespace */()/* : content-start Whitespace content-end */)/* : content-start Tuple2Impl content-end */;
		}
		return /* parseFunctionStatement */(state/* : content-start CompileState content-end */, depth/* : number */, stripped/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseBlock */(state/* : content-start CompileState content-end */, depth/* : number */, stripped/* : unknown */)/* : unknown */)/* : unknown */.orElseGet/* : unknown */(() => new /* Tuple2Impl */(state/* : content-start CompileState content-end */, new /* Placeholder */(stripped/* : unknown */)/* : content-start Placeholder content-end */)/* : content-start Tuple2Impl content-end */)/* : unknown */;
	}
	/* private static */ parseFunctionStatement(state : /* CompileState */, depth : number, stripped : string) : /* Option */<[/* CompileState */, /* FunctionSegment */]> {
		return suffix/* : string */(stripped/* : unknown */, ";", (s) => {
			let tuple = /* parseStatementValue */(state/* : content-start CompileState content-end */, s/* : unknown */, depth/* : number */)/* : unknown */;
			let left = tuple/* : unknown */.left/* : unknown */()/* : unknown */;
			let right = tuple/* : unknown */.right/* : unknown */()/* : unknown */;
			return new /* Some */(new /* Tuple2Impl */(left/* : unknown */, new /* Statement */(depth/* : number */, right/* : unknown */)/* : content-start Statement content-end */)/* : content-start Tuple2Impl content-end */)/* : content-start Some content-end */;
		})/* : unknown */;
	}
	/* private static */ parseBlock(state : /* CompileState */, depth : number, stripped : string) : /* Option */<[/* CompileState */, /* FunctionSegment */]> {
		return suffix/* : string */(stripped/* : unknown */, "}", (withoutEnd) => {
			return /* split */(() => /* toFirst */(withoutEnd/* : unknown */)/* : unknown */, (beforeContent, content) => {
				return suffix/* : string */(beforeContent/* : unknown */, "{", (s) => {
					let statements : [/* CompileState */, /* List */</* FunctionSegment */>] = parseFunctionSegments/* : (arg0 : content-start CompileState content-end, arg1 : string, arg2 : number) => [content-start CompileState content-end, content-start List content-end<content-start FunctionSegment content-end>] */(state/* : content-start CompileState content-end */, content/* : string */, depth/* : number */)/* : [content-start CompileState content-end, content-start List content-end<content-start FunctionSegment content-end>] */;
					let headerTuple = /* parseBlockHeader */(state/* : content-start CompileState content-end */, s/* : unknown */, depth/* : number */)/* : unknown */;
					let headerState = headerTuple/* : unknown */.left/* : unknown */()/* : unknown */;
					let header = headerTuple/* : unknown */.right/* : unknown */()/* : unknown */;
					let right = statements/* : content-start List content-end<string> */.right/* : unknown */()/* : unknown */;
					return new /* Some */(new /* Tuple2Impl */(headerState/* : unknown */, new /* Block */(depth/* : number */, header/* : unknown */, right/* : unknown */)/* : content-start Block content-end */)/* : content-start Tuple2Impl content-end */)/* : content-start Some content-end */;
				})/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ toFirst(input : string) : /* Option */<[string, string]> {
		let divisions : /* List */<string> = divideAll/* : (arg0 : string, arg1 : (arg0 : content-start DivideState content-end, arg1 : string) => content-start DivideState content-end) => content-start List content-end<string> */(input/* : unknown */, Main/* : Main */.foldBlockStart/* : unknown */)/* : content-start List content-end<string> */;
		return divisions/* : content-start List content-end<string> */.removeFirst/* : unknown */()/* : unknown */.map/* : unknown */((removed) => {
			let right = removed/* : unknown */.left/* : unknown */()/* : unknown */;
			let left = removed/* : unknown */.right/* : unknown */()/* : unknown */.iterate/* : unknown */()/* : unknown */.collect/* : unknown */(new /* Joiner */("")/* : content-start Joiner content-end */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
			return new /* Tuple2Impl */(right/* : unknown */, left/* : unknown */)/* : content-start Tuple2Impl content-end */;
		})/* : unknown */;
	}
	/* private static */ parseBlockHeader(state : /* CompileState */, input : string, depth : number) : [/* CompileState */, /* BlockHeader */] {
		let stripped = input/* : unknown */.strip/* : unknown */()/* : unknown */;
		return /* parseConditional */(state/* : content-start CompileState content-end */, stripped/* : unknown */, "if", depth/* : number */)/* : unknown */.or/* : unknown */(() => /* parseConditional */(state/* : content-start CompileState content-end */, stripped/* : unknown */, "while", depth/* : number */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseElse */(state/* : content-start CompileState content-end */, input/* : unknown */)/* : unknown */)/* : unknown */.orElseGet/* : unknown */(() => new /* Tuple2Impl */(state/* : content-start CompileState content-end */, new /* Placeholder */(stripped/* : unknown */)/* : content-start Placeholder content-end */)/* : content-start Tuple2Impl content-end */)/* : unknown */;
	}
	/* private static */ parseElse(state : /* CompileState */, input : string) : /* Option */<[/* CompileState */, /* BlockHeader */]> {
		let stripped = input/* : unknown */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.equals/* : unknown */("else")/* : unknown */){
			return new /* Some */(new /* Tuple2Impl */(state/* : content-start CompileState content-end */, new /* Else */()/* : content-start Else content-end */)/* : content-start Tuple2Impl content-end */)/* : content-start Some content-end */;
		}
		return new /* None */()/* : content-start None content-end */;
	}
	/* private static */ parseConditional(state : /* CompileState */, input : string, prefix : string, depth : number) : /* Option */<[/* CompileState */, /* BlockHeader */]> {
		return prefix/* : string */(input/* : unknown */, prefix/* : string */, (withoutPrefix) => {
			return prefix/* : string */(withoutPrefix/* : unknown */.strip/* : unknown */()/* : unknown */, "(", (withoutValueStart) => {
				return suffix/* : string */(withoutValueStart/* : unknown */, ")", (value) => {
					let valueTuple = /* parseValue */(state/* : content-start CompileState content-end */, value/* : unknown */, depth/* : number */)/* : unknown */;
					let value1 = valueTuple/* : unknown */.right/* : unknown */()/* : unknown */;
					return new /* Some */(new /* Tuple2Impl */(valueTuple/* : unknown */.left/* : unknown */()/* : unknown */, new /* Conditional */(prefix/* : string */, value1/* : unknown */)/* : content-start Conditional content-end */)/* : content-start Tuple2Impl content-end */)/* : content-start Some content-end */;
				})/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ foldBlockStart(state : /* DivideState */, c : string) : /* DivideState */ {
		let appended = state/* : content-start CompileState content-end */.append/* : unknown */(c/* : string */)/* : unknown */;
		if (c/* : string */ === /*  '{'  */ && state/* : content-start CompileState content-end */.isLevel/* : unknown */()/* : unknown */){
			return appended/* : unknown */.advance/* : unknown */()/* : unknown */;
		}
		if (c/* : string */ === /*  '{' */){
			return appended/* : unknown */.enter/* : unknown */()/* : unknown */;
		}
		if (c/* : string */ === /*  '}' */){
			return appended/* : unknown */.exit/* : unknown */()/* : unknown */;
		}
		return appended/* : unknown */;
	}
	/* private static */ parseStatementValue(state : /* CompileState */, input : string, depth : number) : [/* CompileState */, /* StatementValue */] {
		let stripped = input/* : unknown */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.startsWith/* : unknown */("return ")/* : unknown */){
			let value = stripped/* : unknown */.substring/* : unknown */("return ".length/* : unknown */()/* : unknown */)/* : unknown */;
			let tuple = /* parseValue */(state/* : content-start CompileState content-end */, value/* : unknown */, depth/* : number */)/* : unknown */;
			let value1 = tuple/* : unknown */.right/* : unknown */()/* : unknown */;
			return new /* Tuple2Impl */(tuple/* : unknown */.left/* : unknown */()/* : unknown */, new /* Return */(value1/* : unknown */)/* : content-start Return content-end */)/* : content-start Tuple2Impl content-end */;
		}
		return /* parseAssignment */(state/* : content-start CompileState content-end */, depth/* : number */, stripped/* : unknown */)/* : unknown */.orElseGet/* : unknown */(() => {
			return new /* Tuple2Impl */(state/* : content-start CompileState content-end */, new /* Placeholder */(stripped/* : unknown */)/* : content-start Placeholder content-end */)/* : content-start Tuple2Impl content-end */;
		})/* : unknown */;
	}
	/* private static */ parseAssignment(state : /* CompileState */, depth : number, stripped : string) : /* Option */<[/* CompileState */, /* StatementValue */]> {
		return /* first */(stripped/* : unknown */, "=", (beforeEquals, valueString) => {
			let sourceTuple = /* parseValue */(state/* : content-start CompileState content-end */, valueString/* : unknown */, depth/* : number */)/* : unknown */;
			let sourceState = sourceTuple/* : unknown */.left/* : unknown */()/* : unknown */;
			let source = sourceTuple/* : unknown */.right/* : unknown */()/* : unknown */;
			return /* parseDefinition */(sourceState/* : unknown */, beforeEquals/* : unknown */)/* : unknown */.flatMap/* : unknown */((definitionTuple) => /* parseInitialization */(definitionTuple/* : unknown */.left/* : unknown */()/* : unknown */, definitionTuple/* : unknown */.right/* : unknown */()/* : unknown */, source/* : unknown */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseAssignment */(depth/* : number */, beforeEquals/* : unknown */, sourceState/* : unknown */, source/* : unknown */)/* : unknown */)/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ parseAssignment(depth : number, beforeEquals : string, sourceState : /* CompileState */, source : /* Value */) : /* Option */<[/* CompileState */, /* StatementValue */]> {
		let destinationTuple = /* parseValue */(sourceState/* : unknown */, beforeEquals/* : unknown */, depth/* : number */)/* : unknown */;
		let destinationState = destinationTuple/* : unknown */.left/* : unknown */()/* : unknown */;
		let destination = destinationTuple/* : unknown */.right/* : unknown */()/* : unknown */;
		return new /* Some */(new /* Tuple2Impl */(destinationState/* : unknown */, new /* Assignment */(destination/* : unknown */, source/* : unknown */)/* : content-start Assignment content-end */)/* : content-start Tuple2Impl content-end */)/* : content-start Some content-end */;
	}
	/* private static */ parseInitialization(state : /* CompileState */, rawDefinition : /* Definition */, source : /* Value */) : /* Option */<[/* CompileState */, /* StatementValue */]> {
		let definition = rawDefinition/* : content-start Definition content-end */.mapType/* : unknown */((type) => {
			if (type/* : unknown */.equals/* : unknown */(/* Primitive */.Unknown/* : unknown */)/* : unknown */){
				return source/* : unknown */.type/* : unknown */()/* : unknown */;
			}
			else {
				return type/* : unknown */;
			}
		})/* : unknown */;
		return new /* Some */(new /* Tuple2Impl */(state/* : content-start CompileState content-end */.withDefinition/* : unknown */(definition/* : unknown */)/* : unknown */, new /* Initialization */(definition/* : unknown */, source/* : unknown */)/* : content-start Initialization content-end */)/* : content-start Tuple2Impl content-end */)/* : content-start Some content-end */;
	}
	/* private static */ parseValue(state : /* CompileState */, input : string, depth : number) : [/* CompileState */, /* Value */] {
		return /* parseBoolean */(state/* : content-start CompileState content-end */, input/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseLambda */(state/* : content-start CompileState content-end */, input/* : unknown */, depth/* : number */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseString */(state/* : content-start CompileState content-end */, input/* : unknown */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseDataAccess */(state/* : content-start CompileState content-end */, input/* : unknown */, depth/* : number */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseSymbolValue */(state/* : content-start CompileState content-end */, input/* : unknown */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseInvokable */(state/* : content-start CompileState content-end */, input/* : unknown */, depth/* : number */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseDigits */(state/* : content-start CompileState content-end */, input/* : unknown */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseOperation */(state/* : content-start CompileState content-end */, input/* : unknown */, depth/* : number */, /* Operator */.ADD/* : unknown */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseOperation */(state/* : content-start CompileState content-end */, input/* : unknown */, depth/* : number */, /* Operator */.EQUALS/* : unknown */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseOperation */(state/* : content-start CompileState content-end */, input/* : unknown */, depth/* : number */, /* Operator */.SUBTRACT/* : unknown */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseOperation */(state/* : content-start CompileState content-end */, input/* : unknown */, depth/* : number */, /* Operator */.AND/* : unknown */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseOperation */(state/* : content-start CompileState content-end */, input/* : unknown */, depth/* : number */, /* Operator */.OR/* : unknown */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseOperation */(state/* : content-start CompileState content-end */, input/* : unknown */, depth/* : number */, /*  Operator.GREATER_THAN_OR_EQUALS */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseNot */(state/* : content-start CompileState content-end */, input/* : unknown */, depth/* : number */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseMethodReference */(state/* : content-start CompileState content-end */, input/* : unknown */, depth/* : number */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseInstanceOf */(state/* : content-start CompileState content-end */, input/* : unknown */, depth/* : number */)/* : unknown */)/* : unknown */.orElseGet/* : unknown */(() => new /* Tuple2Impl */</* CompileState */, /* Value */>(state/* : content-start CompileState content-end */, new /* Placeholder */(input/* : unknown */)/* : content-start Placeholder content-end */)/* : content-start Tuple2Impl content-end<content-start CompileState content-end, content-start Value content-end> */)/* : unknown */;
	}
	/* private static */ parseBoolean(state : /* CompileState */, input : string) : /* Option */<[/* CompileState */, /* Value */]> {
		let stripped = input/* : unknown */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.equals/* : unknown */("false")/* : unknown */){
			return new /* Some */(new /* Tuple2Impl */(state/* : content-start CompileState content-end */, /* BooleanValue */.False/* : unknown */)/* : content-start Tuple2Impl content-end */)/* : content-start Some content-end */;
		}
		if (stripped/* : unknown */.equals/* : unknown */("true")/* : unknown */){
			return new /* Some */(new /* Tuple2Impl */(state/* : content-start CompileState content-end */, /* BooleanValue */.True/* : unknown */)/* : content-start Tuple2Impl content-end */)/* : content-start Some content-end */;
		}
		return new /* None */()/* : content-start None content-end */;
	}
	/* private static */ parseInstanceOf(state : /* CompileState */, input : string, depth : number) : /* Option */<[/* CompileState */, /* Value */]> {
		return /* last */(input/* : unknown */, "instanceof", (s, s2) => {
			let childTuple : [/* CompileState */, /* Value */] = parseValue/* : (arg0 : content-start CompileState content-end, arg1 : string, arg2 : number) => [content-start CompileState content-end, content-start Value content-end] */(state/* : content-start CompileState content-end */, s/* : unknown */, depth/* : number */)/* : [content-start CompileState content-end, content-start Value content-end] */;
			return /* parseDefinition */(childTuple/* : [content-start CompileState content-end, content-start Value content-end] */[0/* : number */]()/* : unknown */, s2/* : unknown */)/* : unknown */.map/* : unknown */((definitionTuple) => {
				let value = childTuple/* : [content-start CompileState content-end, content-start Value content-end] */[1/* : number */]()/* : unknown */;
				let definition = definitionTuple/* : unknown */.right/* : unknown */()/* : unknown */;
				let variant : /* DataAccess */ = new /* DataAccess */(value/* : unknown */, "_variant", /* Primitive */.Unknown/* : unknown */)/* : content-start DataAccess content-end */;
				let type = value/* : unknown */.type/* : unknown */()/* : unknown */;
				let generate = type/* : unknown */.findName/* : unknown */()/* : unknown */.orElse/* : unknown */("")/* : unknown */;
				let temp : /* SymbolValue */ = new /* SymbolValue */(generate/* : unknown */ + "Variant." + definition/* : unknown */.type/* : unknown */()/* : unknown */.findName/* : unknown */()/* : unknown */.orElse/* : unknown */("")/* : unknown */, /* Primitive */.Unknown/* : unknown */)/* : content-start SymbolValue content-end */;
				return new /* Tuple2Impl */(definitionTuple/* : unknown */.left/* : unknown */()/* : unknown */, new /* Operation */(variant/* : content-start DataAccess content-end */, /* Operator */.EQUALS/* : unknown */, temp/* : content-start SymbolValue content-end */)/* : content-start Operation content-end */)/* : content-start Tuple2Impl content-end */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ parseMethodReference(state : /* CompileState */, input : string, depth : number) : /* Option */<[/* CompileState */, /* Value */]> {
		return /* last */(input/* : unknown */, "::", (s, s2) => {
			let tuple : [/* CompileState */, /* Value */] = parseValue/* : (arg0 : content-start CompileState content-end, arg1 : string, arg2 : number) => [content-start CompileState content-end, content-start Value content-end] */(state/* : content-start CompileState content-end */, s/* : unknown */, depth/* : number */)/* : [content-start CompileState content-end, content-start Value content-end] */;
			return new /* Some */(new /* Tuple2Impl */(tuple/* : unknown */.left/* : unknown */()/* : unknown */, new /* DataAccess */(tuple/* : unknown */.right/* : unknown */()/* : unknown */, s2/* : unknown */, /* Primitive */.Unknown/* : unknown */)/* : content-start DataAccess content-end */)/* : content-start Tuple2Impl content-end */)/* : content-start Some content-end */;
		})/* : unknown */;
	}
	/* private static */ parseNot(state : /* CompileState */, input : string, depth : number) : /* Option */<[/* CompileState */, /* Value */]> {
		let stripped = input/* : unknown */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.startsWith/* : unknown */("!")/* : unknown */){
			let slice = stripped/* : unknown */.substring/* : unknown */(1/* : number */)/* : unknown */;
			let tuple : [/* CompileState */, /* Value */] = parseValue/* : (arg0 : content-start CompileState content-end, arg1 : string, arg2 : number) => [content-start CompileState content-end, content-start Value content-end] */(state/* : content-start CompileState content-end */, slice/* : unknown */, depth/* : number */)/* : [content-start CompileState content-end, content-start Value content-end] */;
			let value = tuple/* : unknown */.right/* : unknown */()/* : unknown */;
			return new /* Some */(new /* Tuple2Impl */(tuple/* : unknown */.left/* : unknown */()/* : unknown */, new /* Not */(value/* : unknown */)/* : content-start Not content-end */)/* : content-start Tuple2Impl content-end */)/* : content-start Some content-end */;
		}
		return new /* None */()/* : content-start None content-end */;
	}
	/* private static */ parseLambda(state : /* CompileState */, input : string, depth : number) : /* Option */<[/* CompileState */, /* Value */]> {
		return /* first */(input/* : unknown */, "->", (beforeArrow, valueString) => {
			let strippedBeforeArrow = beforeArrow/* : unknown */.strip/* : unknown */()/* : unknown */;
			if (isSymbol/* : (arg0 : string) => boolean */(strippedBeforeArrow/* : unknown */)/* : boolean */){
				let type : /* Type */ = /* Primitive */.Unknown/* : unknown */;
				if (/* state.typeRegister instanceof Some */(/* var expectedType */)/* : unknown */){
					if (/* expectedType */._variant/* : unknown */ === Variant./* : unknown */){
						type/* : unknown */ = /* functionType */.arguments/* : unknown */.get/* : unknown */(0/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */;
					}
				}
				return /* assembleLambda */(state/* : content-start CompileState content-end */, /* Lists */.of/* : unknown */(/* ImmutableDefinition */.createSimpleDefinition/* : unknown */(strippedBeforeArrow/* : unknown */, type/* : unknown */)/* : unknown */)/* : unknown */, valueString/* : unknown */, depth/* : number */)/* : unknown */;
			}
			if (strippedBeforeArrow/* : unknown */.startsWith/* : unknown */("(")/* : unknown */ && strippedBeforeArrow/* : unknown */.endsWith/* : unknown */(")")/* : unknown */){
				let parameterNames = divideAll/* : (arg0 : string, arg1 : (arg0 : content-start DivideState content-end, arg1 : string) => content-start DivideState content-end) => content-start List content-end<string> */(strippedBeforeArrow/* : unknown */.substring/* : unknown */(1/* : number */, strippedBeforeArrow/* : unknown */.length/* : unknown */()/* : unknown */ - 1/* : number */)/* : unknown */, Main/* : Main */.foldValueChar/* : unknown */)/* : content-start List content-end<string> */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(/* String */.strip/* : unknown */)/* : unknown */.filter/* : unknown */((value) => !value/* : unknown */.isEmpty/* : unknown */()/* : unknown */)/* : unknown */.map/* : unknown */((name) => /* ImmutableDefinition */.createSimpleDefinition/* : unknown */(name/* : unknown */, /* Primitive */.Unknown/* : unknown */)/* : unknown */)/* : unknown */.collect/* : unknown */(new /* ListCollector */()/* : content-start ListCollector content-end */)/* : unknown */;
				return /* assembleLambda */(state/* : content-start CompileState content-end */, parameterNames/* : unknown */, valueString/* : unknown */, depth/* : number */)/* : unknown */;
			}
			return new /* None */()/* : content-start None content-end */;
		})/* : unknown */;
	}
	/* private static */ assembleLambda(state : /* CompileState */, definitions : /* List */</* Definition */>, valueString : string, depth : number) : /* Some */<[/* CompileState */, /* Value */]> {
		let strippedValueString = valueString/* : unknown */.strip/* : unknown */()/* : unknown */;
		/* Tuple2Impl<CompileState, LambdaValue> value */;
		let state2 = state/* : content-start CompileState content-end */.withDefinitions/* : unknown */(definitions/* : unknown */)/* : unknown */;
		if (strippedValueString/* : unknown */.startsWith/* : unknown */("{")/* : unknown */ && strippedValueString/* : unknown */.endsWith/* : unknown */("}")/* : unknown */){
			let value1 : [/* CompileState */, /* List */<T>] = parseStatements/* : (arg0 : content-start CompileState content-end, arg1 : string, arg2 : (arg0 : content-start CompileState content-end, arg1 : string) => [content-start CompileState content-end, T]) => [content-start CompileState content-end, content-start List content-end<T>] */(state2/* : unknown */, strippedValueString/* : unknown */.substring/* : unknown */(1/* : number */, strippedValueString/* : unknown */.length/* : unknown */()/* : unknown */ - 1/* : number */)/* : unknown */, (state1, input1) => parseFunctionSegment/* : (arg0 : content-start CompileState content-end, arg1 : string, arg2 : number) => [content-start CompileState content-end, content-start FunctionSegment content-end] */(state1/* : unknown */, input1/* : unknown */, depth/* : number */ + 1/* : number */)/* : [content-start CompileState content-end, content-start FunctionSegment content-end] */)/* : [content-start CompileState content-end, content-start List content-end<T>] */;
			let right = value1/* : unknown */.right/* : unknown */()/* : unknown */;
			value/* : unknown */ = new /* Tuple2Impl */(value1/* : unknown */.left/* : unknown */()/* : unknown */, new /* BlockLambdaValue */(depth/* : number */, right/* : unknown */)/* : content-start BlockLambdaValue content-end */)/* : content-start Tuple2Impl content-end */;
		}
		else {
			let value1 : [/* CompileState */, /* Value */] = parseValue/* : (arg0 : content-start CompileState content-end, arg1 : string, arg2 : number) => [content-start CompileState content-end, content-start Value content-end] */(state2/* : unknown */, strippedValueString/* : unknown */, depth/* : number */)/* : [content-start CompileState content-end, content-start Value content-end] */;
			value/* : unknown */ = new /* Tuple2Impl */(value1/* : unknown */.left/* : unknown */()/* : unknown */, value1/* : unknown */.right/* : unknown */()/* : unknown */)/* : content-start Tuple2Impl content-end */;
		}
		let right = value/* : unknown */.right/* : unknown */()/* : unknown */;
		return new /* Some */(new /* Tuple2Impl */(value/* : unknown */.left/* : unknown */()/* : unknown */, new /* Lambda */(definitions/* : unknown */, right/* : unknown */)/* : content-start Lambda content-end */)/* : content-start Tuple2Impl content-end */)/* : content-start Some content-end */;
	}
	/* private static */ parseDigits(state : /* CompileState */, input : string) : /* Option */<[/* CompileState */, /* Value */]> {
		let stripped = input/* : unknown */.strip/* : unknown */()/* : unknown */;
		if (/* isNumber */(stripped/* : unknown */)/* : unknown */){
			return new /* Some */(new /* Tuple2Impl */</* CompileState */, /* Value */>(state/* : content-start CompileState content-end */, new /* SymbolValue */(stripped/* : unknown */, /* Primitive */.Int/* : unknown */)/* : content-start SymbolValue content-end */)/* : content-start Tuple2Impl content-end<content-start CompileState content-end, content-start Value content-end> */)/* : content-start Some content-end */;
		}
		return new /* None */()/* : content-start None content-end */;
	}
	/* private static */ isNumber(input : string) : boolean {
		/* String maybeTruncated */;
		if (input/* : unknown */.startsWith/* : unknown */("-")/* : unknown */){
			/* maybeTruncated */ = input/* : unknown */.substring/* : unknown */(1/* : number */)/* : unknown */;
		}
		else {
			/* maybeTruncated */ = input/* : unknown */;
		}
		return /* areAllDigits */(/* maybeTruncated */)/* : unknown */;
	}
	/* private static */ areAllDigits(input : string) : boolean {
		/* for (var i = 0; i < input.length(); i++) */{
			let c = input/* : unknown */.charAt/* : unknown */(/* i */)/* : unknown */;
			if (/* Character */.isDigit/* : unknown */(c/* : string */)/* : unknown */){
				/* continue */;
			}
			return false;
		}
		return true;
	}
	/* private static */ parseInvokable(state : /* CompileState */, input : string, depth : number) : /* Option */<[/* CompileState */, /* Value */]> {
		return suffix/* : string */(input/* : unknown */.strip/* : unknown */()/* : unknown */, ")", (withoutEnd) => {
			return /* split */(() => /* toLast */(withoutEnd/* : unknown */, "", Main/* : Main */.foldInvocationStart/* : unknown */)/* : unknown */, (callerWithEnd, argumentsString) => {
				return suffix/* : string */(callerWithEnd/* : unknown */, "(", (callerString) => {
					return /* assembleInvokable */(state/* : content-start CompileState content-end */, depth/* : number */, argumentsString/* : unknown */, callerString/* : unknown */.strip/* : unknown */()/* : unknown */)/* : unknown */;
				})/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ assembleInvokable(state : /* CompileState */, depth : number, argumentsString : string, callerString : string) : /* Some */<[/* CompileState */, /* Value */]> {
		let callerTuple = /* invocationHeader */(state/* : content-start CompileState content-end */, depth/* : number */, callerString/* : unknown */)/* : unknown */;
		let oldCallerState = callerTuple/* : unknown */.left/* : unknown */()/* : unknown */;
		let oldCaller = callerTuple/* : unknown */.right/* : unknown */()/* : unknown */;
		let newCaller = /* modifyCaller */(oldCallerState/* : unknown */, oldCaller/* : unknown */)/* : unknown */;
		let callerType = /* findCallerType */(newCaller/* : unknown */)/* : unknown */;
		let argumentsTuple = /* parseValuesWithIndices */(oldCallerState/* : unknown */, argumentsString/* : unknown */, (currentState, pair) => {
			let index = pair/* : unknown */.left/* : unknown */()/* : unknown */;
			let element = pair/* : unknown */.right/* : unknown */()/* : unknown */;
			let expectedType = callerType/* : unknown */.arguments/* : unknown */.get/* : unknown */(index/* : unknown */)/* : unknown */.orElse/* : unknown */(/* Primitive */.Unknown/* : unknown */)/* : unknown */;
			let withExpected = currentState/* : unknown */.withExpectedType/* : unknown */(expectedType/* : unknown */)/* : unknown */;
			let valueTuple = /* parseArgument */(withExpected/* : unknown */, element/* : unknown */, depth/* : number */)/* : unknown */;
			let valueState = valueTuple/* : unknown */.left/* : unknown */()/* : unknown */;
			let value = valueTuple/* : unknown */.right/* : unknown */()/* : unknown */;
			let actualType = valueTuple/* : unknown */.left/* : unknown */()/* : unknown */.typeRegister/* : unknown */.orElse/* : unknown */(/* Primitive */.Unknown/* : unknown */)/* : unknown */;
			return new /* Some */(new /* Tuple2Impl */(valueState/* : unknown */, new /* Tuple2Impl */(value/* : unknown */, actualType/* : unknown */)/* : content-start Tuple2Impl content-end */)/* : content-start Tuple2Impl content-end */)/* : content-start Some content-end */;
		})/* : unknown */.orElseGet/* : unknown */(() => new /* Tuple2Impl */(oldCallerState/* : unknown */, /* Lists */.empty/* : unknown */()/* : unknown */)/* : content-start Tuple2Impl content-end */)/* : unknown */;
		let argumentsState = argumentsTuple/* : unknown */.left/* : unknown */()/* : unknown */;
		let argumentsWithActualTypes = argumentsTuple/* : unknown */.right/* : unknown */()/* : unknown */;
		let arguments = argumentsWithActualTypes/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(/* Tuple2 */.left/* : unknown */)/* : unknown */.map/* : unknown */(Main/* : Main */.retainValue/* : unknown */)/* : unknown */.flatMap/* : unknown */(/* Iterators */.fromOption/* : unknown */)/* : unknown */.collect/* : unknown */(new /* ListCollector */()/* : content-start ListCollector content-end */)/* : unknown */;
		let invokable : /* Invokable */ = new /* Invokable */(newCaller/* : unknown */, arguments/* : unknown */, callerType/* : unknown */.returns/* : unknown */)/* : content-start Invokable content-end */;
		return new /* Some */(new /* Tuple2Impl */(argumentsState/* : unknown */, invokable/* : content-start Invokable content-end */)/* : content-start Tuple2Impl content-end */)/* : content-start Some content-end */;
	}
	/* private static */ retainValue(argument : /* Argument */) : /* Option */</* Value */> {
		if (argument/* : content-start Argument content-end */._variant/* : unknown */ === Variant./* : unknown */){
			return new /* Some */(value/* : unknown */)/* : content-start Some content-end */;
		}
		return new /* None */()/* : content-start None content-end */;
	}
	/* private static */ parseArgument(state : /* CompileState */, element : string, depth : number) : [/* CompileState */, /* Argument */] {
		if (element/* : unknown */.isEmpty/* : unknown */()/* : unknown */){
			return new /* Tuple2Impl */(state/* : content-start CompileState content-end */, new /* Whitespace */()/* : content-start Whitespace content-end */)/* : content-start Tuple2Impl content-end */;
		}
		let tuple : [/* CompileState */, /* Value */] = parseValue/* : (arg0 : content-start CompileState content-end, arg1 : string, arg2 : number) => [content-start CompileState content-end, content-start Value content-end] */(state/* : content-start CompileState content-end */, element/* : unknown */, depth/* : number */)/* : [content-start CompileState content-end, content-start Value content-end] */;
		return new /* Tuple2Impl */(tuple/* : unknown */.left/* : unknown */()/* : unknown */, tuple/* : unknown */.right/* : unknown */()/* : unknown */)/* : content-start Tuple2Impl content-end */;
	}
	/* private static */ findCallerType(newCaller : /* Caller */) : /* FunctionType */ {
		let callerType : /* FunctionType */ = new /* FunctionType */(/* Lists */.empty/* : unknown */()/* : unknown */, /* Primitive */.Unknown/* : unknown */)/* : content-start FunctionType content-end */;
		/* switch (newCaller) */{
			/* case ConstructionCaller constructionCaller -> */{
				callerType/* : unknown */ = /* constructionCaller */.toFunction/* : unknown */()/* : unknown */;
			}
			/* case Value value -> */{
				let type = value/* : unknown */.type/* : unknown */()/* : unknown */;
				if (type/* : unknown */._variant/* : unknown */ === Variant./* : unknown */){
					callerType/* : unknown */ = /* functionType */;
				}
			}
		}
		return callerType/* : unknown */;
	}
	/* private static */ modifyCaller(state : /* CompileState */, oldCaller : /* Caller */) : /* Caller */ {
		if (oldCaller/* : unknown */._variant/* : unknown */ === Variant./* : unknown */){
			let type = /* resolveType */(/* access */.parent/* : unknown */, state/* : content-start CompileState content-end */)/* : unknown */;
			if (/* type instanceof FunctionType */){
				return /* access */.parent/* : unknown */;
			}
		}
		return oldCaller/* : unknown */;
	}
	/* private static */ resolveType(value : /* Value */, state : /* CompileState */) : /* Type */ {
		return value/* : unknown */.type/* : unknown */()/* : unknown */;
	}
	/* private static */ invocationHeader(state : /* CompileState */, depth : number, callerString1 : string) : [/* CompileState */, /* Caller */] {
		if (callerString1/* : string */.startsWith/* : unknown */("new ")/* : unknown */){
			let input1 : string = callerString1/* : string */.substring/* : unknown */("new ".length/* : unknown */()/* : unknown */)/* : unknown */;
			let map = /* parseType */(state/* : content-start CompileState content-end */, input1/* : unknown */)/* : unknown */.map/* : unknown */((type) => {
				let right = type/* : unknown */.right/* : unknown */()/* : unknown */;
				return new /* Tuple2Impl */</* CompileState */, /* Caller */>(type/* : unknown */.left/* : unknown */()/* : unknown */, new /* ConstructionCaller */(right/* : unknown */)/* : content-start ConstructionCaller content-end */)/* : content-start Tuple2Impl content-end<content-start CompileState content-end, content-start Caller content-end> */;
			})/* : unknown */;
			if (map/* : unknown */.isPresent/* : unknown */()/* : unknown */){
				return map/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */;
			}
		}
		let tuple : [/* CompileState */, /* Value */] = parseValue/* : (arg0 : content-start CompileState content-end, arg1 : string, arg2 : number) => [content-start CompileState content-end, content-start Value content-end] */(state/* : content-start CompileState content-end */, callerString1/* : string */, depth/* : number */)/* : [content-start CompileState content-end, content-start Value content-end] */;
		return new /* Tuple2Impl */(tuple/* : unknown */.left/* : unknown */()/* : unknown */, tuple/* : unknown */.right/* : unknown */()/* : unknown */)/* : content-start Tuple2Impl content-end */;
	}
	/* private static */ foldInvocationStart(state : /* DivideState */, c : string) : /* DivideState */ {
		let appended = state/* : content-start CompileState content-end */.append/* : unknown */(c/* : string */)/* : unknown */;
		if (c/* : string */ === /*  '(' */){
			let enter = appended/* : unknown */.enter/* : unknown */()/* : unknown */;
			if (enter/* : unknown */.isShallow/* : unknown */()/* : unknown */){
				return enter/* : unknown */.advance/* : unknown */()/* : unknown */;
			}
			return enter/* : unknown */;
		}
		if (c/* : string */ === /*  ')' */){
			return appended/* : unknown */.exit/* : unknown */()/* : unknown */;
		}
		return appended/* : unknown */;
	}
	/* private static */ parseDataAccess(state : /* CompileState */, input : string, depth : number) : /* Option */<[/* CompileState */, /* Value */]> {
		return /* last */(input/* : unknown */.strip/* : unknown */()/* : unknown */, ".", (parentString, rawProperty) => {
			let property = rawProperty/* : unknown */.strip/* : unknown */()/* : unknown */;
			if (!isSymbol/* : (arg0 : string) => boolean */(property/* : unknown */)/* : unknown */){
				return new /* None */()/* : content-start None content-end */;
			}
			let tuple : [/* CompileState */, /* Value */] = parseValue/* : (arg0 : content-start CompileState content-end, arg1 : string, arg2 : number) => [content-start CompileState content-end, content-start Value content-end] */(state/* : content-start CompileState content-end */, parentString/* : unknown */, depth/* : number */)/* : [content-start CompileState content-end, content-start Value content-end] */;
			let parent = tuple/* : unknown */.right/* : unknown */()/* : unknown */;
			let parentType = parent/* : unknown */.type/* : unknown */()/* : unknown */;
			if (/* parentType instanceof TupleType */){
				if (property/* : unknown */.equals/* : unknown */("left")/* : unknown */){
					return new /* Some */(new /* Tuple2Impl */(state/* : content-start CompileState content-end */, new /* IndexValue */(parent/* : unknown */, new /* SymbolValue */("0", /* Primitive */.Int/* : unknown */)/* : content-start SymbolValue content-end */)/* : content-start IndexValue content-end */)/* : content-start Tuple2Impl content-end */)/* : content-start Some content-end */;
				}
				if (property/* : unknown */.equals/* : unknown */("right")/* : unknown */){
					return new /* Some */(new /* Tuple2Impl */(state/* : content-start CompileState content-end */, new /* IndexValue */(parent/* : unknown */, new /* SymbolValue */("1", /* Primitive */.Int/* : unknown */)/* : content-start SymbolValue content-end */)/* : content-start IndexValue content-end */)/* : content-start Tuple2Impl content-end */)/* : content-start Some content-end */;
				}
			}
			let type : /* Type */ = /* Primitive */.Unknown/* : unknown */;
			if (parentType/* : unknown */._variant/* : unknown */ === Variant./* : unknown */){
				if (/* objectType.find(property) instanceof Some */(/* var memberType */)/* : unknown */){
					type/* : unknown */ = /* memberType */;
				}
			}
			return new /* Some */(new /* Tuple2Impl */(tuple/* : unknown */.left/* : unknown */()/* : unknown */, new /* DataAccess */(parent/* : unknown */, property/* : unknown */, type/* : unknown */)/* : content-start DataAccess content-end */)/* : content-start Tuple2Impl content-end */)/* : content-start Some content-end */;
		})/* : unknown */;
	}
	/* private static */ parseString(state : /* CompileState */, input : string) : /* Option */<[/* CompileState */, /* Value */]> {
		let stripped = input/* : unknown */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.startsWith/* : unknown */("\"")/* : unknown */ && stripped/* : unknown */.endsWith/* : unknown */("\"")/* : unknown */){
			return new /* Some */(new /* Tuple2Impl */(state/* : content-start CompileState content-end */, new /* StringValue */(stripped/* : unknown */)/* : content-start StringValue content-end */)/* : content-start Tuple2Impl content-end */)/* : content-start Some content-end */;
		}
		return new /* None */()/* : content-start None content-end */;
	}
	/* private static */ parseSymbolValue(state : /* CompileState */, value : string) : /* Option */<[/* CompileState */, /* Value */]> {
		let stripped = value/* : unknown */.strip/* : unknown */()/* : unknown */;
		if (isSymbol/* : (arg0 : string) => boolean */(stripped/* : unknown */)/* : boolean */){
			if (/* state.resolveValue(stripped) instanceof Some */(/* var type */)/* : unknown */){
				return new /* Some */(new /* Tuple2Impl */(state/* : content-start CompileState content-end */, new /* SymbolValue */(stripped/* : unknown */, type/* : unknown */)/* : content-start SymbolValue content-end */)/* : content-start Tuple2Impl content-end */)/* : content-start Some content-end */;
			}
			if (/* state.resolveType(stripped) instanceof Some */(/* var type */)/* : unknown */){
				return new /* Some */(new /* Tuple2Impl */(state/* : content-start CompileState content-end */, new /* SymbolValue */(stripped/* : unknown */, type/* : unknown */)/* : content-start SymbolValue content-end */)/* : content-start Tuple2Impl content-end */)/* : content-start Some content-end */;
			}
			return new /* Some */(new /* Tuple2Impl */(state/* : content-start CompileState content-end */, new /* Placeholder */(stripped/* : unknown */)/* : content-start Placeholder content-end */)/* : content-start Tuple2Impl content-end */)/* : content-start Some content-end */;
		}
		return new /* None */()/* : content-start None content-end */;
	}
	/* private static */ parseOperation(state : /* CompileState */, value : string, depth : number, operator : /* Operator */) : /* Option */<[/* CompileState */, /* Value */]> {
		return /* first */(value/* : unknown */, operator/* : content-start Operator content-end */.sourceRepresentation/* : unknown */, (leftString, rightString) => {
			let leftTuple : [/* CompileState */, /* Value */] = parseValue/* : (arg0 : content-start CompileState content-end, arg1 : string, arg2 : number) => [content-start CompileState content-end, content-start Value content-end] */(state/* : content-start CompileState content-end */, leftString/* : unknown */, depth/* : number */)/* : [content-start CompileState content-end, content-start Value content-end] */;
			let rightTuple : [/* CompileState */, /* Value */] = parseValue/* : (arg0 : content-start CompileState content-end, arg1 : string, arg2 : number) => [content-start CompileState content-end, content-start Value content-end] */(leftTuple/* : [content-start CompileState content-end, content-start Value content-end] */[0/* : number */]()/* : unknown */, rightString/* : unknown */, depth/* : number */)/* : [content-start CompileState content-end, content-start Value content-end] */;
			let left = leftTuple/* : [content-start CompileState content-end, content-start Value content-end] */[1/* : number */]()/* : unknown */;
			let right = rightTuple/* : [content-start CompileState content-end, content-start Value content-end] */[1/* : number */]()/* : unknown */;
			return new /* Some */(new /* Tuple2Impl */(rightTuple/* : [content-start CompileState content-end, content-start Value content-end] */[0/* : number */]()/* : unknown */, new /* Operation */(left/* : unknown */, operator/* : content-start Operator content-end */, right/* : unknown */)/* : content-start Operation content-end */)/* : content-start Tuple2Impl content-end */)/* : content-start Some content-end */;
		})/* : unknown */;
	}
	/* private static  */ parseValuesOrEmpty<T>(state : /* CompileState */, input : string, mapper : (arg0 : /* CompileState */, arg1 : string) => /* Option */<[/* CompileState */, T]>) : [/* CompileState */, /* List */<T>] {
		return /* parseValues */(state/* : content-start CompileState content-end */, input/* : unknown */, mapper/* : (arg0 : content-start CompileState content-end, arg1 : string) => [content-start CompileState content-end, T] */)/* : unknown */.orElseGet/* : unknown */(() => new /* Tuple2Impl */(state/* : content-start CompileState content-end */, /* Lists */.empty/* : unknown */()/* : unknown */)/* : content-start Tuple2Impl content-end */)/* : unknown */;
	}
	/* private static  */ parseValues<T>(state : /* CompileState */, input : string, mapper : (arg0 : /* CompileState */, arg1 : string) => /* Option */<[/* CompileState */, T]>) : /* Option */<[/* CompileState */, /* List */<T>]> {
		return /* parseValuesWithIndices */(state/* : content-start CompileState content-end */, input/* : unknown */, (state1, tuple) => mapper/* : (arg0 : content-start CompileState content-end, arg1 : string) => [content-start CompileState content-end, T] */(state1/* : unknown */, tuple/* : unknown */.right/* : unknown */()/* : unknown */)/* : [content-start CompileState content-end, T] */)/* : unknown */;
	}
	/* private static  */ parseValuesWithIndices<T>(state : /* CompileState */, input : string, mapper : (arg0 : /* CompileState */, arg1 : [number, string]) => /* Option */<[/* CompileState */, T]>) : /* Option */<[/* CompileState */, /* List */<T>]> {
		return parseAllWithIndices/* : (arg0 : content-start CompileState content-end, arg1 : string, arg2 : (arg0 : content-start DivideState content-end, arg1 : string) => content-start DivideState content-end, arg3 : (arg0 : content-start CompileState content-end, arg1 : [number, string]) => content-start Option content-end<[content-start CompileState content-end, T]>) => content-start Option content-end<[content-start CompileState content-end, content-start List content-end<T>]> */(state/* : content-start CompileState content-end */, input/* : unknown */, Main/* : Main */.foldValueChar/* : unknown */, mapper/* : (arg0 : content-start CompileState content-end, arg1 : string) => [content-start CompileState content-end, T] */)/* : content-start Option content-end<[content-start CompileState content-end, content-start List content-end<T>]> */;
	}
	/* private static */ parseParameter(state : /* CompileState */, input : string) : [/* CompileState */, /* Parameter */] {
		if (input/* : unknown */.isBlank/* : unknown */()/* : unknown */){
			return new /* Tuple2Impl */(state/* : content-start CompileState content-end */, new /* Whitespace */()/* : content-start Whitespace content-end */)/* : content-start Tuple2Impl content-end */;
		}
		return /* parseDefinition */(state/* : content-start CompileState content-end */, input/* : unknown */)/* : unknown */.map/* : unknown */((tuple) => new /* Tuple2Impl */</* CompileState */, /* Parameter */>(tuple/* : unknown */.left/* : unknown */()/* : unknown */, tuple/* : unknown */.right/* : unknown */()/* : unknown */)/* : content-start Tuple2Impl content-end<content-start CompileState content-end, content-start Parameter content-end> */)/* : unknown */.orElseGet/* : unknown */(() => new /* Tuple2Impl */(state/* : content-start CompileState content-end */, new /* Placeholder */(input/* : unknown */)/* : content-start Placeholder content-end */)/* : content-start Tuple2Impl content-end */)/* : unknown */;
	}
	/* private static */ createIndent(depth : number) : string {
		return "\n" + "\t".repeat/* : unknown */(depth/* : number */)/* : unknown */;
	}
	/* private static */ parseDefinitionStatement(input : string, depth : number, state : /* CompileState */) : /* Option */<[/* CompileState */, /* IncompleteClassSegmentWrapper */]> {
		return suffix/* : string */(input/* : unknown */.strip/* : unknown */()/* : unknown */, ";", (withoutEnd) => {
			return /* parseDefinition */(state/* : content-start CompileState content-end */, withoutEnd/* : unknown */)/* : unknown */.map/* : unknown */((result) => {
				let definition = result/* : unknown */.right/* : unknown */()/* : unknown */;
				return new /* Tuple2Impl */(result/* : unknown */.left/* : unknown */()/* : unknown */, new /* IncompleteClassSegmentWrapper */(new /* DefinitionStatement */(depth/* : number */, definition/* : unknown */)/* : content-start DefinitionStatement content-end */)/* : content-start IncompleteClassSegmentWrapper content-end */)/* : content-start Tuple2Impl content-end */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ parseDefinition(state : /* CompileState */, input : string) : /* Option */<[/* CompileState */, /* Definition */]> {
		return /* last */(input/* : unknown */.strip/* : unknown */()/* : unknown */, " ", (beforeName, name) => {
			return /* split */(() => /* toLast */(beforeName/* : unknown */, " ", Main/* : Main */.foldTypeSeparator/* : unknown */)/* : unknown */, (beforeType, type) => {
				return suffix/* : string */(beforeType/* : unknown */.strip/* : unknown */()/* : unknown */, ">", (withoutTypeParamStart) => {
					return /* first */(withoutTypeParamStart/* : unknown */, "<", (beforeTypeParams, typeParamsString) => {
						let typeParams : [/* CompileState */, /* List */<T>] = parseValuesOrEmpty/* : (arg0 : content-start CompileState content-end, arg1 : string, arg2 : (arg0 : content-start CompileState content-end, arg1 : string) => content-start Option content-end<[content-start CompileState content-end, T]>) => [content-start CompileState content-end, content-start List content-end<T>] */(state/* : content-start CompileState content-end */, typeParamsString/* : unknown */, (state1, s) => new /* Some */(new /* Tuple2Impl */(state1/* : unknown */, s/* : unknown */.strip/* : unknown */()/* : unknown */)/* : content-start Tuple2Impl content-end */)/* : content-start Some content-end */)/* : [content-start CompileState content-end, content-start List content-end<T>] */;
						return /* assembleDefinition */(typeParams/* : unknown */.left/* : unknown */()/* : unknown */, new /* Some */<string>(beforeTypeParams/* : unknown */)/* : content-start Some content-end<string> */, name/* : unknown */, typeParams/* : unknown */.right/* : unknown */()/* : unknown */, type/* : unknown */)/* : unknown */;
					})/* : unknown */;
				})/* : unknown */.or/* : unknown */(() => {
					return /* assembleDefinition */(state/* : content-start CompileState content-end */, new /* Some */<string>(beforeType/* : unknown */)/* : content-start Some content-end<string> */, name/* : unknown */, /* Lists */.empty/* : unknown */()/* : unknown */, type/* : unknown */)/* : unknown */;
				})/* : unknown */;
			})/* : unknown */.or/* : unknown */(() => {
				return /* assembleDefinition */(state/* : content-start CompileState content-end */, new /* None */<string>()/* : content-start None content-end<string> */, name/* : unknown */, /* Lists */.empty/* : unknown */()/* : unknown */, beforeName/* : unknown */)/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ toLast(input : string, separator : string, folder : (arg0 : /* DivideState */, arg1 : string) => /* DivideState */) : /* Option */<[string, string]> {
		let divisions : /* List */<string> = divideAll/* : (arg0 : string, arg1 : (arg0 : content-start DivideState content-end, arg1 : string) => content-start DivideState content-end) => content-start List content-end<string> */(input/* : unknown */, folder/* : (arg0 : content-start DivideState content-end, arg1 : string) => content-start DivideState content-end */)/* : content-start List content-end<string> */;
		return divisions/* : content-start List content-end<string> */.removeLast/* : unknown */()/* : unknown */.map/* : unknown */((removed) => {
			let left = removed/* : unknown */.left/* : unknown */()/* : unknown */.iterate/* : unknown */()/* : unknown */.collect/* : unknown */(new /* Joiner */(separator/* : string */)/* : content-start Joiner content-end */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
			let right = removed/* : unknown */.right/* : unknown */()/* : unknown */;
			return new /* Tuple2Impl */(left/* : unknown */, right/* : unknown */)/* : content-start Tuple2Impl content-end */;
		})/* : unknown */;
	}
	/* private static */ foldTypeSeparator(state : /* DivideState */, c : string) : /* DivideState */ {
		if (c/* : string */ === /*  ' '  */ && state/* : content-start CompileState content-end */.isLevel/* : unknown */()/* : unknown */){
			return state/* : content-start CompileState content-end */.advance/* : unknown */()/* : unknown */;
		}
		let appended = state/* : content-start CompileState content-end */.append/* : unknown */(c/* : string */)/* : unknown */;
		if (c/* : string */ === /*  '<' */){
			return appended/* : unknown */.enter/* : unknown */()/* : unknown */;
		}
		if (c/* : string */ === /*  '>' */){
			return appended/* : unknown */.exit/* : unknown */()/* : unknown */;
		}
		return appended/* : unknown */;
	}
	/* private static */ assembleDefinition(state : /* CompileState */, beforeTypeParams : /* Option */<string>, name : string, typeParams : /* List */<string>, type : string) : /* Option */<[/* CompileState */, /* Definition */]> {
		return /* parseType */(state/* : content-start CompileState content-end */.withTypeParams/* : unknown */(typeParams/* : unknown */)/* : unknown */, type/* : unknown */)/* : unknown */.map/* : unknown */((type1) => {
			let node : /* ImmutableDefinition */ = new /* ImmutableDefinition */(beforeTypeParams/* : unknown */, name/* : unknown */.strip/* : unknown */()/* : unknown */, type1/* : unknown */.right/* : unknown */()/* : unknown */, typeParams/* : unknown */)/* : content-start ImmutableDefinition content-end */;
			return new /* Tuple2Impl */(type1/* : unknown */.left/* : unknown */()/* : unknown */, node/* : content-start ImmutableDefinition content-end */)/* : content-start Tuple2Impl content-end */;
		})/* : unknown */;
	}
	/* private static */ foldValueChar(state : /* DivideState */, c : string) : /* DivideState */ {
		if (c/* : string */ === /*  ','  */ && state/* : content-start CompileState content-end */.isLevel/* : unknown */()/* : unknown */){
			return state/* : content-start CompileState content-end */.advance/* : unknown */()/* : unknown */;
		}
		let appended = state/* : content-start CompileState content-end */.append/* : unknown */(c/* : string */)/* : unknown */;
		if (c/* : string */ === /*  ' */ - /* ' */){
			let peeked = appended/* : unknown */.peek/* : unknown */()/* : unknown */;
			if (peeked/* : unknown */ === /*  '>' */){
				return appended/* : unknown */.popAndAppendToOption/* : unknown */()/* : unknown */.orElse/* : unknown */(appended/* : unknown */)/* : unknown */;
			}
			else {
				return appended/* : unknown */;
			}
		}
		if (c/* : string */ === /*  '<'  */ || c/* : string */ === /*  '('  */ || c/* : string */ === /*  '{' */){
			return appended/* : unknown */.enter/* : unknown */()/* : unknown */;
		}
		if (c/* : string */ === /*  '>'  */ || c/* : string */ === /*  ')'  */ || c/* : string */ === /*  '}' */){
			return appended/* : unknown */.exit/* : unknown */()/* : unknown */;
		}
		return appended/* : unknown */;
	}
	/* private static */ parseType(state : /* CompileState */, input : string) : /* Option */<[/* CompileState */, /* Type */]> {
		let stripped = input/* : unknown */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.equals/* : unknown */("int")/* : unknown */ || stripped/* : unknown */.equals/* : unknown */("Integer")/* : unknown */){
			return new /* Some */(new /* Tuple2Impl */(state/* : content-start CompileState content-end */, /* Primitive */.Int/* : unknown */)/* : content-start Tuple2Impl content-end */)/* : content-start Some content-end */;
		}
		if (stripped/* : unknown */.equals/* : unknown */("String")/* : unknown */ || stripped/* : unknown */.equals/* : unknown */("char")/* : unknown */ || stripped/* : unknown */.equals/* : unknown */("Character")/* : unknown */){
			return new /* Some */(new /* Tuple2Impl */(state/* : content-start CompileState content-end */, /* Primitive */.String/* : unknown */)/* : content-start Tuple2Impl content-end */)/* : content-start Some content-end */;
		}
		if (stripped/* : unknown */.equals/* : unknown */("var")/* : unknown */){
			return new /* Some */(new /* Tuple2Impl */(state/* : content-start CompileState content-end */, /* Primitive */.Unknown/* : unknown */)/* : content-start Tuple2Impl content-end */)/* : content-start Some content-end */;
		}
		if (stripped/* : unknown */.equals/* : unknown */("boolean")/* : unknown */){
			return new /* Some */(new /* Tuple2Impl */(state/* : content-start CompileState content-end */, /* Primitive */.Boolean/* : unknown */)/* : content-start Tuple2Impl content-end */)/* : content-start Some content-end */;
		}
		if (isSymbol/* : (arg0 : string) => boolean */(stripped/* : unknown */)/* : boolean */){
			if (/* state.resolveType(stripped) instanceof Some */(/* var resolved */)/* : unknown */){
				return new /* Some */(new /* Tuple2Impl */(state/* : content-start CompileState content-end */, /* resolved */)/* : content-start Tuple2Impl content-end */)/* : content-start Some content-end */;
			}
			else {
				return new /* Some */(new /* Tuple2Impl */(state/* : content-start CompileState content-end */, new /* Placeholder */(stripped/* : unknown */)/* : content-start Placeholder content-end */)/* : content-start Tuple2Impl content-end */)/* : content-start Some content-end */;
			}
		}
		return /* parseTemplate */(state/* : content-start CompileState content-end */, input/* : unknown */)/* : unknown */.or/* : unknown */(() => /* varArgs */(state/* : content-start CompileState content-end */, input/* : unknown */)/* : unknown */)/* : unknown */;
	}
	/* private static */ varArgs(state : /* CompileState */, input : string) : /* Option */<[/* CompileState */, /* Type */]> {
		return suffix/* : string */(input/* : unknown */, "...", (s) => {
			return parseType/* : (arg0 : content-start CompileState content-end, arg1 : string) => content-start Option content-end<[content-start CompileState content-end, content-start Type content-end]> */(state/* : content-start CompileState content-end */, s/* : unknown */)/* : content-start Option content-end<[content-start CompileState content-end, content-start Type content-end]> */.map/* : unknown */((inner) => {
				let newState = inner/* : unknown */.left/* : unknown */()/* : unknown */;
				let child = inner/* : unknown */.right/* : unknown */()/* : unknown */;
				return new /* Tuple2Impl */(newState/* : unknown */, new /* ArrayType */(child/* : unknown */)/* : content-start ArrayType content-end */)/* : content-start Tuple2Impl content-end */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ assembleTemplate(base : string, state : /* CompileState */, arguments : /* List */</* Argument */>) : [/* CompileState */, /* Type */] {
		let children = arguments/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(Main/* : Main */.retainType/* : unknown */)/* : unknown */.flatMap/* : unknown */(/* Iterators */.fromOption/* : unknown */)/* : unknown */.collect/* : unknown */(new /* ListCollector */()/* : content-start ListCollector content-end */)/* : unknown */;
		if (base/* : string */.equals/* : unknown */("BiFunction")/* : unknown */){
			return new /* Tuple2Impl */(state/* : content-start CompileState content-end */, new /* FunctionType */(/* Lists */.of/* : unknown */(children/* : unknown */.get/* : unknown */(0/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */, children/* : unknown */.get/* : unknown */(1/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : unknown */, children/* : unknown */.get/* : unknown */(2/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : content-start FunctionType content-end */)/* : content-start Tuple2Impl content-end */;
		}
		if (base/* : string */.equals/* : unknown */("Function")/* : unknown */){
			return new /* Tuple2Impl */(state/* : content-start CompileState content-end */, new /* FunctionType */(/* Lists */.of/* : unknown */(children/* : unknown */.get/* : unknown */(0/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : unknown */, children/* : unknown */.get/* : unknown */(1/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : content-start FunctionType content-end */)/* : content-start Tuple2Impl content-end */;
		}
		if (base/* : string */.equals/* : unknown */("Predicate")/* : unknown */){
			return new /* Tuple2Impl */(state/* : content-start CompileState content-end */, new /* FunctionType */(/* Lists */.of/* : unknown */(children/* : unknown */.get/* : unknown */(0/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : unknown */, /* Primitive */.Boolean/* : unknown */)/* : content-start FunctionType content-end */)/* : content-start Tuple2Impl content-end */;
		}
		if (base/* : string */.equals/* : unknown */("Supplier")/* : unknown */){
			return new /* Tuple2Impl */(state/* : content-start CompileState content-end */, new /* FunctionType */(/* Lists */.empty/* : unknown */()/* : unknown */, children/* : unknown */.get/* : unknown */(0/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : content-start FunctionType content-end */)/* : content-start Tuple2Impl content-end */;
		}
		if (base/* : string */.equals/* : unknown */("Tuple2")/* : unknown */ && children/* : unknown */.size/* : unknown */()/* : unknown */ >= 2/* : number */){
			return new /* Tuple2Impl */(state/* : content-start CompileState content-end */, new /* TupleType */(children/* : unknown */)/* : content-start TupleType content-end */)/* : content-start Tuple2Impl content-end */;
		}
		if (state/* : content-start CompileState content-end */.resolveType/* : unknown */(base/* : string */)/* : unknown */._variant/* : unknown */ === Variant./* : unknown */){
			let baseType = /* some */.value/* : unknown */;
			if (baseType/* : unknown */._variant/* : unknown */ === Variant./* : unknown */){
				return new /* Tuple2Impl */(state/* : content-start CompileState content-end */, new /* Template */(/* findableType */, children/* : unknown */)/* : content-start Template content-end */)/* : content-start Tuple2Impl content-end */;
			}
		}
		return new /* Tuple2Impl */(state/* : content-start CompileState content-end */, new /* Template */(new /* Placeholder */(base/* : string */)/* : content-start Placeholder content-end */, children/* : unknown */)/* : content-start Template content-end */)/* : content-start Tuple2Impl content-end */;
	}
	/* private static */ parseTemplate(state : /* CompileState */, input : string) : /* Option */<[/* CompileState */, /* Type */]> {
		return suffix/* : string */(input/* : unknown */.strip/* : unknown */()/* : unknown */, ">", (withoutEnd) => {
			return /* first */(withoutEnd/* : unknown */, "<", (base, argumentsString) => {
				let strippedBase = base/* : string */.strip/* : unknown */()/* : unknown */;
				return parseValues/* : (arg0 : content-start CompileState content-end, arg1 : string, arg2 : (arg0 : content-start CompileState content-end, arg1 : string) => content-start Option content-end<[content-start CompileState content-end, T]>) => content-start Option content-end<[content-start CompileState content-end, content-start List content-end<T>]> */(state/* : content-start CompileState content-end */, argumentsString/* : unknown */, Main/* : Main */.argument/* : unknown */)/* : content-start Option content-end<[content-start CompileState content-end, content-start List content-end<T>]> */.map/* : unknown */((argumentsTuple) => {
					return assembleTemplate/* : (arg0 : string, arg1 : content-start CompileState content-end, arg2 : content-start List content-end<content-start Argument content-end>) => [content-start CompileState content-end, content-start Type content-end] */(strippedBase/* : unknown */, argumentsTuple/* : unknown */.left/* : unknown */()/* : unknown */, argumentsTuple/* : unknown */.right/* : unknown */()/* : unknown */)/* : [content-start CompileState content-end, content-start Type content-end] */;
				})/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ retainType(argument : /* Argument */) : /* Option */</* Type */> {
		if (argument/* : content-start Argument content-end */._variant/* : unknown */ === Variant./* : unknown */){
			return new /* Some */(type/* : unknown */)/* : content-start Some content-end */;
		}
		else {
			return new /* None */</* Type */>()/* : content-start None content-end<content-start Type content-end> */;
		}
	}
	/* private static */ argument(state : /* CompileState */, input : string) : /* Option */<[/* CompileState */, /* Argument */]> {
		if (input/* : unknown */.isBlank/* : unknown */()/* : unknown */){
			return new /* Some */(new /* Tuple2Impl */(state/* : content-start CompileState content-end */, new /* Whitespace */()/* : content-start Whitespace content-end */)/* : content-start Tuple2Impl content-end */)/* : content-start Some content-end */;
		}
		return parseType/* : (arg0 : content-start CompileState content-end, arg1 : string) => content-start Option content-end<[content-start CompileState content-end, content-start Type content-end]> */(state/* : content-start CompileState content-end */, input/* : unknown */)/* : content-start Option content-end<[content-start CompileState content-end, content-start Type content-end]> */.map/* : unknown */((tuple) => new /* Tuple2Impl */(tuple/* : unknown */.left/* : unknown */()/* : unknown */, tuple/* : unknown */.right/* : unknown */()/* : unknown */)/* : content-start Tuple2Impl content-end */)/* : unknown */;
	}
	/* private static  */ last<T>(input : string, infix : string, mapper : (arg0 : string, arg1 : string) => /* Option */<T>) : /* Option */<T> {
		return infix/* : string */(input/* : unknown */, infix/* : string */, Main/* : Main */.findLast/* : unknown */, mapper/* : (arg0 : content-start CompileState content-end, arg1 : string) => [content-start CompileState content-end, T] */)/* : unknown */;
	}
	/* private static */ findLast(input : string, infix : string) : /* Option */<number> {
		let index = input/* : unknown */.lastIndexOf/* : unknown */(infix/* : string */)/* : unknown */;
		if (index/* : unknown */ === -1/* : number */){
			return new /* None */<number>()/* : content-start None content-end<number> */;
		}
		return new /* Some */(index/* : unknown */)/* : content-start Some content-end */;
	}
	/* private static  */ first<T>(input : string, infix : string, mapper : (arg0 : string, arg1 : string) => /* Option */<T>) : /* Option */<T> {
		return infix/* : string */(input/* : unknown */, infix/* : string */, Main/* : Main */.findFirst/* : unknown */, mapper/* : (arg0 : content-start CompileState content-end, arg1 : string) => [content-start CompileState content-end, T] */)/* : unknown */;
	}
	/* private static  */ split<T>(splitter : () => /* Option */<[string, string]>, splitMapper : (arg0 : string, arg1 : string) => /* Option */<T>) : /* Option */<T> {
		return splitter/* : () => content-start Option content-end<[string, string]> */()/* : content-start Option content-end<[string, string]> */.flatMap/* : unknown */((splitTuple) => splitMapper/* : (arg0 : string, arg1 : string) => content-start Option content-end<T> */(splitTuple/* : unknown */.left/* : unknown */()/* : unknown */, splitTuple/* : unknown */.right/* : unknown */()/* : unknown */)/* : content-start Option content-end<T> */)/* : unknown */;
	}
	/* private static  */ infix<T>(input : string, infix : string, locator : (arg0 : string, arg1 : string) => /* Option */<number>, mapper : (arg0 : string, arg1 : string) => /* Option */<T>) : /* Option */<T> {
		return split/* : (arg0 : () => content-start Option content-end<[string, string]>, arg1 : (arg0 : string, arg1 : string) => content-start Option content-end<T>) => content-start Option content-end<T> */(() => locator/* : (arg0 : string, arg1 : string) => content-start Option content-end<number> */(input/* : unknown */, infix/* : string */)/* : content-start Option content-end<number> */.map/* : unknown */((index) => {
			let left = input/* : unknown */.substring/* : unknown */(0/* : number */, index/* : unknown */)/* : unknown */;
			let right = input/* : unknown */.substring/* : unknown */(index/* : unknown */ + infix/* : string */.length/* : unknown */()/* : unknown */)/* : unknown */;
			return new /* Tuple2Impl */(left/* : unknown */, right/* : unknown */)/* : content-start Tuple2Impl content-end */;
		})/* : unknown */, mapper/* : (arg0 : content-start CompileState content-end, arg1 : string) => [content-start CompileState content-end, T] */)/* : content-start Option content-end<T> */;
	}
	/* private static */ findFirst(input : string, infix : string) : /* Option */<number> {
		let index = input/* : unknown */.indexOf/* : unknown */(infix/* : string */)/* : unknown */;
		if (index/* : unknown */ === -1/* : number */){
			return new /* None */<number>()/* : content-start None content-end<number> */;
		}
		return new /* Some */(index/* : unknown */)/* : content-start Some content-end */;
	}
	/* private static */ generatePlaceholder(input : string) : string {
		let replaced = input/* : unknown */.replace/* : unknown */("/*", "content-start")/* : unknown */.replace/* : unknown */("*/", "content-end")/* : unknown */;
		return "/* " + replaced + " */";
	}
	/* private static */ createDebugString(type : /* Type */) : string {
		if (!Main/* : Main */.isDebug/* : unknown */){
			return "";
		}
		return generatePlaceholder/* : (arg0 : string) => string */(": " + type/* : unknown */.generate/* : unknown */()/* : unknown */)/* : string */;
	}
}
/*  */