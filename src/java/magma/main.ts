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
	collect<R>(collector : Collector<T, R>) : R;
	filter(predicate : (arg0 : T) => boolean) : Iterator<T>;
	next() : Option<T>;
	flatMap<R>(f : (arg0 : T) => Iterator<R>) : Iterator<R>;
	zip<R>(other : Iterator<R>) : Iterator<[T, R]>;
}
/* private */interface List<T>/*   */ {
	addLast(element : T) : List<T>;
	iterate() : Iterator<T>;
	removeLast() : Option<[List<T>, T]>;
	get(index : number) : Option<T>;
	size() : number;
	isEmpty() : boolean;
	addFirst(element : T) : List<T>;
	iterateWithIndices() : Iterator<[number, T]>;
	removeFirst() : Option<[T, List<T>]>;
	addAllLast(others : List<T>) : List<T>;
	last() : Option<T>;
	iterateReversed() : Iterator<T>;
	mapLast(mapper : (arg0 : T) => T) : List<T>;
}
/* private */interface Head<T>/*   */ {
	next() : Option<T>;
}
/* private */interface Map<K, V>/*   */ {
	find(key : K) : Option<V>;
	with(key : K, value : V) : Map<K, V>;
}
/* private */interface Type/*  */ {
	generate() : string;
	replace(mapping : Map<string, Type>) : Type;
	findName() : Option<string>;
}
/* private */interface Argument/*  */ {
}
/* private */interface Parameter/*  */ {
}
/* private sealed */interface Value/*  */ {
	generate() : string;
	type() : Type;
}
/* private */interface LambdaValue/*  */ {
	generate() : string;
}
/* private sealed */interface Caller/*  */ {
	generate() : string;
}
/* private */interface FindableType/*  */ {
	typeParams() : List<string>;
	find(name : string) : Option<Type>;
	name() : string;
}
/* private */interface Definition/*  */ {
	generate() : string;
	generateType() : string;
	joinBefore() : string;
	joinTypeParams() : string;
	mapType(mapper : (arg0 : Type) => Type) : Definition;
	/* @Override
        */ toString() : string;
	/* @Override
        */ generateWithParams(joinedParameters : string) : string;
	/* @Override
        */ createDefinition(paramTypes : List<Type>) : Definition;
	maybeBefore() : Option<string>;
	name() : string;
	type() : Type;
	typeParams() : List<string>;
}
/* private */interface Header/*  */ {
	createDefinition(paramTypes : List<Type>) : Definition;
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
	createDefinition() : Option<Definition>;
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
		return other/* : () => Option<T> */()/* : Option<T> */;
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
        public */ filter(predicate : (arg0 : T) => boolean) : Option<T> {
		if (predicate/* : (arg0 : T) => boolean */(this/* : Some */.value/* : unknown */)/* : boolean */){
			return this/* : Some */;
		}
		return new None()/* : None */;
	}
	/* @Override
        public */ orElseGet(supplier : () => T) : T {
		return this/* : Some */.value/* : unknown */;
	}
	/* @Override
        public */ or(other : () => Option<T>) : Option<T> {
		return this/* : Some */;
	}
	/* @Override
        public  */ flatMap<R>(mapper : (arg0 : T) => Option<R>) : Option<R> {
		return mapper/* : (arg0 : T) => Option<R> */(this/* : Some */.value/* : unknown */)/* : Option<R> */;
	}
	/* @Override
        public */ isEmpty() : boolean {
		return false;
	}
	/* @Override
        public  */ and<R>(other : () => Option<R>) : Option<[T, R]> {
		return other/* : () => Option<R> */()/* : Option<R> */.map/* : (arg0 : (arg0 : R) => R) => Option<R> */((otherValue : R) => new Tuple2Impl(this/* : Some */.value/* : unknown */, otherValue/* : R */)/* : Tuple2Impl */)/* : Option<R> */;
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
        public */ next() : Option<T> {
		if (this/* : SingleHead */.retrieved/* : unknown */){
			return new None()/* : None */;
		}
		this/* : SingleHead */.retrieved/* : unknown */ = true;
		return new Some(this/* : SingleHead */.value/* : unknown */)/* : Some */;
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
		while (true){
			let finalCurrent : R = current/* : R */;
			let option = this/* : HeadedIterator */.head/* : unknown */.next/* : unknown */()/* : unknown */.map/* : unknown */((inner) => folder/* : (arg0 : R, arg1 : T) => R */(finalCurrent/* : R */, inner/* : unknown */)/* : R */)/* : unknown */;
			if (option/* : unknown */._variant/* : unknown */ === Variant.Some/* : unknown */){
				current/* : R */ = /* some */.value/* : unknown */;
			}
			else {
				return current/* : R */;
			}
		}
	}
	/* @Override
        public  */ map<R>(mapper : (arg0 : T) => R) : Iterator<R> {
		return new HeadedIterator(() => this/* : HeadedIterator */.head/* : unknown */.next/* : unknown */()/* : unknown */.map/* : unknown */(mapper/* : (arg0 : T) => R */)/* : unknown */)/* : HeadedIterator */;
	}
	/* @Override
        public  */ collect<R>(collector : Collector<T, R>) : R {
		return this/* : HeadedIterator */.fold/* : (arg0 : R, arg1 : (arg0 : R, arg1 : T) => R) => R */(collector/* : Collector<T, R> */.createInitial/* : () => C */()/* : C */, collector/* : Collector<T, R> */.fold/* : unknown */)/* : R */;
	}
	/* @Override
        public */ filter(predicate : (arg0 : T) => boolean) : Iterator<T> {
		return this/* : HeadedIterator */.flatMap/* : (arg0 : (arg0 : T) => Iterator<R>) => Iterator<R> */((element : T) => {
			if (predicate/* : (arg0 : T) => boolean */(element/* : T */)/* : boolean */){
				return new HeadedIterator(new SingleHead(element/* : T */)/* : SingleHead */)/* : HeadedIterator */;
			}
			return new HeadedIterator(new EmptyHead()/* : EmptyHead */)/* : HeadedIterator */;
		})/* : Iterator<R> */;
	}
	/* @Override
        public */ next() : Option<T> {
		return this/* : HeadedIterator */.head/* : unknown */.next/* : unknown */()/* : unknown */;
	}
	/* @Override
        public  */ flatMap<R>(f : (arg0 : T) => Iterator<R>) : Iterator<R> {
		return new HeadedIterator(new /* FlatMapHead */(this/* : HeadedIterator */.head/* : unknown */, f/* : (arg0 : T) => Iterator<R> */)/* : content-start FlatMapHead content-end */)/* : HeadedIterator */;
	}
	/* @Override
        public  */ zip<R>(other : Iterator<R>) : Iterator<[T, R]> {
		return new HeadedIterator(() => HeadedIterator/* : HeadedIterator */.this/* : unknown */.head/* : unknown */.next/* : unknown */()/* : unknown */.and/* : unknown */(other/* : Iterator<R> */.next/* : unknown */)/* : unknown */)/* : HeadedIterator */;
	}
}
/* private static */class RangeHead/*  */ {
	/* private final */ length : number;
	/* private */ counter : number;
	RangeHead(length : number) : /* public */ {
		this/* : RangeHead */.length/* : unknown */ = length/* : number */;
	}
	/* @Override
        public */ next() : Option<number> {
		if (/* this.counter < this */.length/* : unknown */){
			let value = this/* : RangeHead */.counter/* : unknown */;
			/* this.counter++ */;
			return new Some(value/* : unknown */)/* : Some */;
		}
		return new None()/* : None */;
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
            public */ addLast(element : T) : List<T> {
		/* this.elements.add(element) */;
		return this/* : JVMList */;
	}
	/* @Override
            public */ iterate() : Iterator<T> {
		return this/* : JVMList */.iterateWithIndices/* : () => Iterator<[number, T]> */()/* : Iterator<[number, T]> */.map/* : (arg0 : (arg0 : [number, T]) => R) => Iterator<R> */(Tuple2/* : Tuple2 */.right/* : unknown */)/* : Iterator<R> */;
	}
	/* @Override
            public */ removeLast() : Option<[List<T>, T]> {
		if (this/* : JVMList */.elements/* : unknown */.isEmpty/* : unknown */()/* : unknown */){
			return new None()/* : None */;
		}
		let slice = this/* : JVMList */.elements/* : unknown */.subList/* : unknown */(0/* : number */, this/* : JVMList */.elements/* : unknown */.size/* : unknown */()/* : unknown */ - 1/* : number */)/* : unknown */;
		let last = this/* : JVMList */.elements/* : unknown */.getLast/* : unknown */()/* : unknown */;
		return new Some(new Tuple2Impl<List<T>, T>(new JVMList(slice/* : unknown */)/* : JVMList */, last/* : unknown */)/* : Tuple2Impl<List<T>, T> */)/* : Some */;
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
            public */ addFirst(element : T) : List<T> {
		/* this.elements.addFirst(element) */;
		return this/* : JVMList */;
	}
	/* @Override
            public */ iterateWithIndices() : Iterator<[number, T]> {
		return new HeadedIterator(new RangeHead(this/* : JVMList */.elements/* : unknown */.size/* : unknown */()/* : unknown */)/* : RangeHead */)/* : HeadedIterator */.map/* : (arg0 : (arg0 : T) => R) => Iterator<R> */((index : T) => new Tuple2Impl(index/* : T */, this/* : JVMList */.elements/* : unknown */.get/* : unknown */(index/* : T */)/* : unknown */)/* : Tuple2Impl */)/* : Iterator<R> */;
	}
	/* @Override
            public */ removeFirst() : Option<[T, List<T>]> {
		if (this/* : JVMList */.elements/* : unknown */.isEmpty/* : unknown */()/* : unknown */){
			return new None()/* : None */;
		}
		let first = this/* : JVMList */.elements/* : unknown */.getFirst/* : unknown */()/* : unknown */;
		let slice = this/* : JVMList */.elements/* : unknown */.subList/* : unknown */(1/* : number */, this/* : JVMList */.elements/* : unknown */.size/* : unknown */()/* : unknown */)/* : unknown */;
		return new Some(new Tuple2Impl<T, List<T>>(first/* : unknown */, new JVMList(slice/* : unknown */)/* : JVMList */)/* : Tuple2Impl<T, List<T>> */)/* : Some */;
	}
	/* @Override
            public */ addAllLast(others : List<T>) : List<T> {
		let initial : List<T> = this/* : JVMList */;
		return others/* : List<T> */.iterate/* : () => Iterator<T> */()/* : Iterator<T> */.fold/* : (arg0 : R, arg1 : (arg0 : R, arg1 : T) => R) => R */(initial/* : List<T> */, List/* : List */.addLast/* : unknown */)/* : R */;
	}
	/* @Override
            public */ last() : Option<T> {
		if (this/* : JVMList */.elements/* : unknown */.isEmpty/* : unknown */()/* : unknown */){
			return new None()/* : None */;
		}
		return new Some(this/* : JVMList */.elements/* : unknown */.getLast/* : unknown */()/* : unknown */)/* : Some */;
	}
	/* @Override
            public */ iterateReversed() : Iterator<T> {
		return new HeadedIterator(new RangeHead(this/* : JVMList */.elements/* : unknown */.size/* : unknown */()/* : unknown */)/* : RangeHead */)/* : HeadedIterator */.map/* : (arg0 : (arg0 : T) => R) => Iterator<R> */((index : T) => this/* : JVMList */.elements/* : unknown */.size/* : unknown */()/* : unknown */ - index/* : T */ - 1/* : number */)/* : Iterator<R> */.map/* : (arg0 : (arg0 : R) => R) => Iterator<R> */(this/* : JVMList */.elements/* : unknown */.get/* : unknown */)/* : Iterator<R> */;
	}
	/* @Override
            public */ mapLast(mapper : (arg0 : T) => T) : List<T> {
		return this/* : JVMList */.last/* : () => Option<T> */()/* : Option<T> */.map/* : (arg0 : (arg0 : T) => R) => Option<R> */(mapper/* : (arg0 : T) => T */)/* : Option<R> */.map/* : unknown */((newLast) => this/* : JVMList */.set/* : (arg0 : number, arg1 : T) => JVMList<T> */(this/* : JVMList */.elements/* : unknown */.size/* : unknown */()/* : unknown */ - 1/* : number */, newLast/* : unknown */)/* : JVMList<T> */)/* : unknown */.orElse/* : unknown */(this/* : JVMList */)/* : unknown */;
	}
	/* private */ set(index : number, element : T) : JVMList<T> {
		/* this.elements.set(index, element) */;
		return this/* : JVMList */;
	}
	/* @Override
            public */ get(index : number) : Option<T> {
		if (/* index < this */.elements/* : unknown */.size/* : unknown */()/* : unknown */){
			return new Some(this/* : JVMList */.elements/* : unknown */.get/* : unknown */(index/* : number */)/* : unknown */)/* : Some */;
		}
		else {
			return new None()/* : None */;
		}
	}
}
/* private static */class Lists/*  */ {
	/* public static  */ empty<T>() : List<T> {
		return new JVMList()/* : JVMList */;
	}
	/* public static  */ of<T>(elements : T[]) : List<T> {
		return new JVMList(new /* ArrayList */(/* Arrays */.asList/* : unknown */(elements/* : T[] */)/* : unknown */)/* : content-start ArrayList content-end */)/* : JVMList */;
	}
}
/* private */class ImmutableDefinition/*  */ {
	constructor (maybeBefore : Option<string>, name : string, type : Type, typeParams : List<string>) {
	}
	/* public static */ createSimpleDefinition(name : string, type : Type) : Definition {
		return new ImmutableDefinition(new None()/* : None */, name/* : string */, type/* : Type */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */)/* : ImmutableDefinition */;
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
        public */ mapType(mapper : (arg0 : Type) => Type) : Definition {
		return new ImmutableDefinition(this/* : ImmutableDefinition */.maybeBefore/* : unknown */, this/* : ImmutableDefinition */.name/* : unknown */, mapper/* : (arg0 : Type) => Type */(this/* : ImmutableDefinition */.type/* : unknown */)/* : Type */, this/* : ImmutableDefinition */.typeParams/* : unknown */)/* : ImmutableDefinition */;
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
        public */ createDefinition(paramTypes : List<Type>) : Definition {
		return ImmutableDefinition/* : ImmutableDefinition */.createSimpleDefinition/* : unknown */(this/* : ImmutableDefinition */.name/* : unknown */, new /* FunctionType */(paramTypes/* : List<Type> */, this/* : ImmutableDefinition */.type/* : unknown */)/* : content-start FunctionType content-end */)/* : unknown */;
	}
}
/* private */class ObjectType/*  */ {
	constructor (name : string, typeParams : List<string>, definitions : List<Definition>) {
	}
	/* @Override
        public */ generate() : string {
		return this/* : ObjectType */.name/* : unknown */;
	}
	/* @Override
        public */ replace(mapping : Map<string, Type>) : Type {
		return new ObjectType(this/* : ObjectType */.name/* : unknown */, this/* : ObjectType */.typeParams/* : unknown */, this/* : ObjectType */.definitions/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */((definition) => definition/* : unknown */.mapType/* : unknown */((type) => type/* : unknown */.replace/* : unknown */(mapping/* : Map<string, Type> */)/* : unknown */)/* : unknown */)/* : unknown */.collect/* : unknown */(new /* ListCollector */()/* : content-start ListCollector content-end */)/* : unknown */)/* : ObjectType */;
	}
	/* @Override
        public */ find(name : string) : Option<Type> {
		return this/* : ObjectType */.definitions/* : unknown */.iterate/* : unknown */()/* : unknown */.filter/* : unknown */((definition) => definition/* : unknown */.name/* : unknown */()/* : unknown */.equals/* : unknown */(name/* : string */)/* : unknown */)/* : unknown */.map/* : unknown */(Definition/* : Definition */.type/* : unknown */)/* : unknown */.next/* : unknown */()/* : unknown */;
	}
	/* @Override
        public */ findName() : Option<string> {
		return new Some(this/* : ObjectType */.name/* : unknown */)/* : Some */;
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
        public */ replace(mapping : Map<string, Type>) : Type {
		return mapping/* : Map<string, Type> */.find/* : (arg0 : string) => Option<V> */(this/* : TypeParam */.value/* : unknown */)/* : Option<V> */.orElse/* : (arg0 : V) => T */(this/* : TypeParam */)/* : T */;
	}
	/* @Override
        public */ findName() : Option<string> {
		return new None()/* : None */;
	}
}
/* private */class CompileState/*  */ {
	constructor (structures : List<string>, definitions : List<List<Definition>>, objectTypes : List<ObjectType>, structNames : List<string>, typeParams : List<string>, typeRegister : Option<Type>) {
	}
	CompileState() : /* public */ {
		/* this(Lists.empty(), Lists.of(Lists.empty()), Lists.empty(), Lists.empty(), Lists.empty(), new None<>()) */;
	}
	/* private */ resolveValue(name : string) : Option<Type> {
		return this/* : CompileState */.definitions/* : unknown */.iterateReversed/* : unknown */()/* : unknown */.flatMap/* : unknown */(List/* : List */.iterate/* : unknown */)/* : unknown */.filter/* : unknown */((definition) => definition/* : unknown */.name/* : unknown */()/* : unknown */.equals/* : unknown */(name/* : string */)/* : unknown */)/* : unknown */.next/* : unknown */()/* : unknown */.map/* : unknown */(Definition/* : Definition */.type/* : unknown */)/* : unknown */;
	}
	/* public */ addStructure(structure : string) : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : unknown */.addLast/* : unknown */(structure/* : string */)/* : unknown */, this/* : CompileState */.definitions/* : unknown */, this/* : CompileState */.objectTypes/* : unknown */, this/* : CompileState */.structNames/* : unknown */, this/* : CompileState */.typeParams/* : unknown */, this/* : CompileState */.typeRegister/* : unknown */)/* : CompileState */;
	}
	/* public */ withDefinitions(definitions : List<Definition>) : CompileState {
		let defined = this/* : CompileState */.definitions/* : unknown */.mapLast/* : unknown */((frame) => frame/* : unknown */.addAllLast/* : unknown */(definitions/* : List<Definition> */)/* : unknown */)/* : unknown */;
		return new CompileState(this/* : CompileState */.structures/* : unknown */, defined/* : unknown */, this/* : CompileState */.objectTypes/* : unknown */, this/* : CompileState */.structNames/* : unknown */, this/* : CompileState */.typeParams/* : unknown */, this/* : CompileState */.typeRegister/* : unknown */)/* : CompileState */;
	}
	/* public */ resolveType(name : string) : Option<Type> {
		if (this/* : CompileState */.structNames/* : unknown */.last/* : unknown */()/* : unknown */.filter/* : unknown */((inner) => inner/* : unknown */.equals/* : unknown */(name/* : string */)/* : unknown */)/* : unknown */.isPresent/* : unknown */()/* : unknown */){
			return new Some(new ObjectType(name/* : string */, this/* : CompileState */.typeParams/* : unknown */, this/* : CompileState */.definitions/* : unknown */.last/* : unknown */()/* : unknown */.orElse/* : unknown */(Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */)/* : unknown */)/* : ObjectType */)/* : Some */;
		}
		let maybeTypeParam = this/* : CompileState */.typeParams/* : unknown */.iterate/* : unknown */()/* : unknown */.filter/* : unknown */((param) => param/* : unknown */.equals/* : unknown */(name/* : string */)/* : unknown */)/* : unknown */.next/* : unknown */()/* : unknown */;
		if (/* maybeTypeParam instanceof Some */(/* var value */)/* : unknown */){
			return new Some(new TypeParam(/* value */)/* : TypeParam */)/* : Some */;
		}
		return this/* : CompileState */.objectTypes/* : unknown */.iterate/* : unknown */()/* : unknown */.filter/* : unknown */((type) => type/* : unknown */.name/* : unknown */.equals/* : unknown */(name/* : string */)/* : unknown */)/* : unknown */.next/* : unknown */()/* : unknown */.map/* : unknown */((type) => type/* : unknown */)/* : unknown */;
	}
	/* public */ withDefinition(definition : Definition) : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : unknown */, this/* : CompileState */.definitions/* : unknown */.mapLast/* : unknown */((frame) => frame/* : unknown */.addLast/* : unknown */(definition/* : Definition */)/* : unknown */)/* : unknown */, this/* : CompileState */.objectTypes/* : unknown */, this/* : CompileState */.structNames/* : unknown */, this/* : CompileState */.typeParams/* : unknown */, this/* : CompileState */.typeRegister/* : unknown */)/* : CompileState */;
	}
	/* public */ pushStructName(name : string) : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : unknown */, this/* : CompileState */.definitions/* : unknown */, this/* : CompileState */.objectTypes/* : unknown */, this/* : CompileState */.structNames/* : unknown */.addLast/* : unknown */(name/* : string */)/* : unknown */, this/* : CompileState */.typeParams/* : unknown */, this/* : CompileState */.typeRegister/* : unknown */)/* : CompileState */;
	}
	/* public */ withTypeParams(typeParams : List<string>) : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : unknown */, this/* : CompileState */.definitions/* : unknown */, this/* : CompileState */.objectTypes/* : unknown */, this/* : CompileState */.structNames/* : unknown */, this/* : CompileState */.typeParams/* : unknown */.addAllLast/* : unknown */(typeParams/* : List<string> */)/* : unknown */, this/* : CompileState */.typeRegister/* : unknown */)/* : CompileState */;
	}
	/* public */ withExpectedType(type : Type) : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : unknown */, this/* : CompileState */.definitions/* : unknown */, this/* : CompileState */.objectTypes/* : unknown */, this/* : CompileState */.structNames/* : unknown */, this/* : CompileState */.typeParams/* : unknown */, new Some(type/* : Type */)/* : Some */)/* : CompileState */;
	}
	/* public */ popStructName() : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : unknown */, this/* : CompileState */.definitions/* : unknown */, this/* : CompileState */.objectTypes/* : unknown */, this/* : CompileState */.structNames/* : unknown */.removeLast/* : unknown */()/* : unknown */.map/* : unknown */(Tuple2/* : Tuple2 */.left/* : unknown */)/* : unknown */.orElse/* : unknown */(this/* : CompileState */.structNames/* : unknown */)/* : unknown */, this/* : CompileState */.typeParams/* : unknown */, this/* : CompileState */.typeRegister/* : unknown */)/* : CompileState */;
	}
	/* public */ enterDefinitions() : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : unknown */, this/* : CompileState */.definitions/* : unknown */.addLast/* : unknown */(Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */)/* : unknown */, this/* : CompileState */.objectTypes/* : unknown */, this/* : CompileState */.structNames/* : unknown */, this/* : CompileState */.typeParams/* : unknown */, this/* : CompileState */.typeRegister/* : unknown */)/* : CompileState */;
	}
	/* public */ exitDefinitions() : CompileState {
		let removed = this/* : CompileState */.definitions/* : unknown */.removeLast/* : unknown */()/* : unknown */.map/* : unknown */(Tuple2/* : Tuple2 */.left/* : unknown */)/* : unknown */.orElse/* : unknown */(this/* : CompileState */.definitions/* : unknown */)/* : unknown */;
		return new CompileState(this/* : CompileState */.structures/* : unknown */, removed/* : unknown */, this/* : CompileState */.objectTypes/* : unknown */, this/* : CompileState */.structNames/* : unknown */, this/* : CompileState */.typeParams/* : unknown */, this/* : CompileState */.typeRegister/* : unknown */)/* : CompileState */;
	}
	/* public */ addType(thisType : ObjectType) : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : unknown */, this/* : CompileState */.definitions/* : unknown */, this/* : CompileState */.objectTypes/* : unknown */.addLast/* : unknown */(thisType/* : ObjectType */)/* : unknown */, this/* : CompileState */.structNames/* : unknown */, this/* : CompileState */.typeParams/* : unknown */, this/* : CompileState */.typeRegister/* : unknown */)/* : CompileState */;
	}
}
/* private static */class DivideState/*  */ {
	/* private final */ input : string;
	/* private final */ index : number;
	/* private */ depth : number;
	/* private */ segments : List<string>;
	/* private */ buffer : string;
	DivideState(input : string, index : number, segments : List<string>, buffer : string, depth : number) : /* public */ {
		this/* : DivideState */.segments/* : unknown */ = segments/* : List<string> */;
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
	/* public */ pop() : Option<[string, DivideState]> {
		if (/* this.index < this */.input/* : unknown */.length/* : unknown */()/* : unknown */){
			let c = this/* : DivideState */.input/* : unknown */.charAt/* : unknown */(this/* : DivideState */.index/* : unknown */)/* : unknown */;
			return new Some(new Tuple2Impl(c/* : unknown */, new DivideState(this/* : DivideState */.input/* : unknown */, this/* : DivideState */.index/* : unknown */ + 1/* : number */, this/* : DivideState */.segments/* : unknown */, this/* : DivideState */.buffer/* : unknown */, this/* : DivideState */.depth/* : unknown */)/* : DivideState */)/* : Tuple2Impl */)/* : Some */;
		}
		return new None()/* : None */;
	}
	/* public */ popAndAppendToTuple() : Option<[string, DivideState]> {
		return this/* : DivideState */.pop/* : () => Option<[string, DivideState]> */()/* : Option<[string, DivideState]> */.map/* : (arg0 : (arg0 : [string, DivideState]) => R) => Option<R> */((tuple : [string, DivideState]) => {
			let c = tuple/* : [string, DivideState] */[0/* : number */]()/* : unknown */;
			let right = tuple/* : [string, DivideState] */[1/* : number */]()/* : unknown */;
			return new Tuple2Impl(c/* : unknown */, right/* : unknown */.append/* : unknown */(c/* : unknown */)/* : unknown */)/* : Tuple2Impl */;
		})/* : Option<R> */;
	}
	/* public */ popAndAppendToOption() : Option<DivideState> {
		return this/* : DivideState */.popAndAppendToTuple/* : () => Option<[string, DivideState]> */()/* : Option<[string, DivideState]> */.map/* : (arg0 : (arg0 : [string, DivideState]) => R) => Option<R> */(Tuple2/* : Tuple2 */.right/* : unknown */)/* : Option<R> */;
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
        public */ createInitial() : Option<string> {
		return new None()/* : None */;
	}
	/* @Override
        public */ fold(current : Option<string>, element : string) : Option<string> {
		return new Some(current/* : Option<string> */.map/* : (arg0 : (arg0 : string) => R) => Option<R> */((inner : string) => inner/* : string */ + this/* : Joiner */.delimiter/* : unknown */ + element/* : string */)/* : Option<R> */.orElse/* : unknown */(element/* : string */)/* : unknown */)/* : Some */;
	}
}
/* private static */class ListCollector<T>/*  */ {
	/* @Override
        public */ createInitial() : List<T> {
		return Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */;
	}
	/* @Override
        public */ fold(current : List<T>, element : T) : List<T> {
		return current/* : List<T> */.addLast/* : (arg0 : T) => List<T> */(element/* : T */)/* : List<T> */;
	}
}
/* private static */class FlatMapHead<T, R>/*  */ {
	/* private final */ mapper : (arg0 : T) => Iterator<R>;
	/* private final */ head : Head<T>;
	/* private */ current : Option<Iterator<R>>;
	FlatMapHead(head : Head<T>, mapper : (arg0 : T) => Iterator<R>) : /* public */ {
		this/* : FlatMapHead */.mapper/* : unknown */ = mapper/* : (arg0 : T) => Iterator<R> */;
		this/* : FlatMapHead */.current/* : unknown */ = new None()/* : None */;
		this/* : FlatMapHead */.head/* : unknown */ = head/* : Head<T> */;
	}
	/* @Override
        public */ next() : Option<R> {
		while (true){
			if (this/* : FlatMapHead */.current/* : unknown */.isPresent/* : unknown */()/* : unknown */){
				let inner : Iterator<R> = this/* : FlatMapHead */.current/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */;
				let maybe : Option<R> = inner/* : Iterator<R> */.next/* : () => Option<T> */()/* : Option<T> */;
				if (maybe/* : Option<R> */.isPresent/* : () => boolean */()/* : boolean */){
					return maybe/* : Option<R> */;
				}
				else {
					this/* : FlatMapHead */.current/* : unknown */ = new None()/* : None */;
				}
			}
			let outer : Option<T> = this/* : FlatMapHead */.head/* : unknown */.next/* : unknown */()/* : unknown */;
			if (outer/* : Option<T> */.isPresent/* : () => boolean */()/* : boolean */){
				this/* : FlatMapHead */.current/* : unknown */ = outer/* : Option<T> */.map/* : (arg0 : (arg0 : T) => R) => Option<R> */(this/* : FlatMapHead */.mapper/* : unknown */)/* : Option<R> */;
			}
			else {
				return new None()/* : None */;
			}
		}
	}
}
/* private */class ArrayType/*  */ {
	constructor (right : Type) {
	}
	/* @Override
        public */ generate() : string {
		return this/* : ArrayType */.right/* : unknown */()/* : unknown */.generate/* : unknown */()/* : unknown */ + "[]";
	}
	/* @Override
        public */ replace(mapping : Map<string, Type>) : Type {
		return this/* : ArrayType */;
	}
	/* @Override
        public */ findName() : Option<string> {
		return new None()/* : None */;
	}
}
/* private static final */class Whitespace/*  */ {
	/* @Override
        public */ generate() : string {
		return "";
	}
	/* @Override
        public */ createDefinition() : Option<Definition> {
		return new None()/* : None */;
	}
}
/* private static */class Iterators/*  */ {
	/* public static  */ fromOption<T>(option : Option<T>) : Iterator<T> {
		let single : Option<Head<T>> = option/* : Option<T> */.map/* : (arg0 : (arg0 : T) => R) => Option<R> */(SingleHead/* : SingleHead */.new/* : unknown */)/* : Option<R> */;
		return new HeadedIterator(single/* : Option<Head<T>> */.orElseGet/* : (arg0 : () => T) => T */(EmptyHead/* : EmptyHead */.new/* : unknown */)/* : T */)/* : HeadedIterator */;
	}
}
/* private */class FunctionType/*  */ {
	constructor (arguments : List<Type>, returns : Type) {
	}
	/* @Override
        public */ generate() : string {
		let joined = this/* : FunctionType */.arguments/* : unknown */()/* : unknown */.iterateWithIndices/* : unknown */()/* : unknown */.map/* : unknown */((pair) => "arg" + pair/* : unknown */.left/* : unknown */()/* : unknown */ + " : " + pair/* : unknown */.right/* : unknown */()/* : unknown */.generate/* : unknown */()/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return "(" + joined/* : unknown */ + ") => " + this/* : FunctionType */.returns/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
	/* @Override
        public */ replace(mapping : Map<string, Type>) : Type {
		return new FunctionType(this/* : FunctionType */.arguments/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */((type) => type/* : unknown */.replace/* : unknown */(mapping/* : Map<string, Type> */)/* : unknown */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */, this/* : FunctionType */.returns/* : unknown */)/* : FunctionType */;
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
		let joinedArguments = this/* : TupleType */.arguments/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(Type/* : Type */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return "[" + joinedArguments + "]";
	}
	/* @Override
        public */ replace(mapping : Map<string, Type>) : Type {
		return this/* : TupleType */;
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
		let joinedArguments = this/* : Template */.arguments/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(Type/* : Type */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.map/* : unknown */((inner) => "<" + inner + ">")/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return this/* : Template */.base/* : unknown */.generate/* : unknown */()/* : unknown */ + joinedArguments/* : unknown */;
	}
	/* @Override
        public */ typeParams() : List<string> {
		return this/* : Template */.base/* : unknown */.typeParams/* : unknown */()/* : unknown */;
	}
	/* @Override
        public */ find(name : string) : Option<Type> {
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
        public */ replace(mapping : Map<string, Type>) : Type {
		return this/* : Template */;
	}
	/* @Override
        public */ findName() : Option<string> {
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
		return this/* : Placeholder */.input/* : unknown */;
	}
	/* @Override
        public */ replace(mapping : Map<string, Type>) : Type {
		return this/* : Placeholder */;
	}
	/* @Override
        public */ findName() : Option<string> {
		return new None()/* : None */;
	}
	/* @Override
        public */ createDefinition() : Option<Definition> {
		return new None()/* : None */;
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
        public */ type() : Type {
		return /* Primitive */.Unknown/* : unknown */;
	}
}
/* private */class DataAccess/*  */ {
	constructor (parent : Value, property : string, type : Type) {
	}
	/* @Override
        public */ generate() : string {
		return this/* : DataAccess */.parent/* : unknown */.generate/* : unknown */()/* : unknown */ + "." + this/* : DataAccess */.property/* : unknown */ + /* createDebugString */(this/* : DataAccess */.type/* : () => Type */)/* : unknown */;
	}
	/* @Override
        public */ type() : Type {
		return this/* : DataAccess */.type/* : () => Type */;
	}
}
/* private */class ConstructionCaller/*  */ {
	constructor (type : Type) {
	}
	/* @Override
        public */ generate() : string {
		return "new " + this/* : ConstructionCaller */.type/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
	/* public */ toFunction() : FunctionType {
		return new FunctionType(Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, this/* : ConstructionCaller */.type/* : unknown */)/* : FunctionType */;
	}
}
/* private */class Operation/*  */ {
	constructor (left : Value, operator : /* Operator */, right : Value) {
	}
	/* @Override
        public */ generate() : string {
		return this/* : Operation */.left/* : unknown */()/* : unknown */.generate/* : unknown */()/* : unknown */ + " " + this/* : Operation */.operator/* : unknown */.targetRepresentation/* : unknown */ + " " + this/* : Operation */.right/* : unknown */()/* : unknown */.generate/* : unknown */()/* : unknown */;
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
		return "!" + this/* : Not */.value/* : unknown */.generate/* : unknown */()/* : unknown */;
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
		return this/* : BlockLambdaValue */.statements/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(FunctionSegment/* : FunctionSegment */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner()/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
	}
}
/* private */class Lambda/*  */ {
	constructor (parameters : List<Definition>, body : LambdaValue) {
	}
	/* @Override
        public */ generate() : string {
		let joined = this/* : Lambda */.parameters/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(Definition/* : Definition */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return "(" + joined/* : unknown */ + ") => " + this/* : Lambda */.body/* : unknown */.generate/* : unknown */()/* : unknown */;
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
		let joined = this/* : Invokable */.arguments/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(Value/* : Value */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return this/* : Invokable */.caller/* : unknown */.generate/* : unknown */()/* : unknown */ + "(" + joined/* : unknown */ + ")" + /* createDebugString */(this/* : Invokable */.type/* : unknown */)/* : unknown */;
	}
}
/* private */class IndexValue/*  */ {
	constructor (parent : Value, child : Value) {
	}
	/* @Override
        public */ generate() : string {
		return this/* : IndexValue */.parent/* : unknown */.generate/* : unknown */()/* : unknown */ + "[" + this.child.generate() + "]";
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
            public */ find(key : K) : Option<V> {
		if (this/* : JVMMap */.map/* : unknown */.containsKey/* : unknown */(key/* : K */)/* : unknown */){
			return new Some(this/* : JVMMap */.map/* : unknown */.get/* : unknown */(key/* : K */)/* : unknown */)/* : Some */;
		}
		return new None()/* : None */;
	}
	/* @Override
            public */ with(key : K, value : V) : Map<K, V> {
		/* this.map.put(key, value) */;
		return this/* : JVMMap */;
	}
}
/* private static */class Maps/*  */ {
	/* public static  */ empty<VK>() : Map<K, V> {
		return new JVMMap()/* : JVMMap */;
	}
}
/* private */class MapCollector<K, V>/*  */ {
	/* @Override
        public */ createInitial() : Map<K, V> {
		return Maps/* : Maps */.empty/* : () => Map<K, V> */()/* : Map<K, V> */;
	}
	/* @Override
        public */ fold(current : Map<K, V>, element : [K, V]) : Map<K, V> {
		return current/* : Map<K, V> */.with/* : (arg0 : K, arg1 : V) => Map<K, V> */(element/* : [K, V] */[0/* : number */]()/* : unknown */, element/* : [K, V] */[1/* : number */]()/* : unknown */)/* : Map<K, V> */;
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
/* private */class DefinitionStatement/*  */ {
	constructor (depth : number, definition : Definition) {
	}
	/* @Override
        public */ generate() : string {
		return /* createIndent */(this/* : DefinitionStatement */.depth/* : unknown */)/* : unknown */ + this/* : DefinitionStatement */.definition/* : unknown */.generate/* : unknown */()/* : unknown */ + ";";
	}
}
/* private static */class Method/*  */ {
	/* private final */ depth : number;
	/* private final */ header : Header;
	/* private final */ parameters : List<Definition>;
	/* private final */ statements : Option<List<FunctionSegment>>;
	Method(depth : number, header : Header, parameters : List<Definition>, maybeStatements : Option<List<FunctionSegment>>) : /* public */ {
		this/* : Method */.depth/* : unknown */ = depth/* : number */;
		this/* : Method */.header/* : unknown */ = header/* : Header */;
		this/* : Method */.parameters/* : unknown */ = parameters/* : List<Definition> */;
		this/* : Method */.statements/* : unknown */ = maybeStatements/* : Option<List<FunctionSegment>> */;
	}
	/* private static */ joinStatements(statements : List<FunctionSegment>) : string {
		return statements/* : List<FunctionSegment> */.iterate/* : () => Iterator<T> */()/* : Iterator<T> */.map/* : (arg0 : (arg0 : T) => R) => Iterator<R> */(FunctionSegment/* : FunctionSegment */.generate/* : unknown */)/* : Iterator<R> */.collect/* : unknown */(new Joiner()/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
	}
	/* @Override
        public */ generate() : string {
		let indent = /* createIndent */(this/* : Method */.depth/* : unknown */)/* : unknown */;
		let generatedHeader = this/* : Method */.header/* : unknown */.generateWithParams/* : unknown */(/* joinValues */(this/* : Method */.parameters/* : unknown */)/* : unknown */)/* : unknown */;
		let generatedStatements = this/* : Method */.statements/* : unknown */.map/* : unknown */(Method/* : (arg0 : number, arg1 : Header, arg2 : List<Definition>, arg3 : Option<List<FunctionSegment>>) => content-start public content-end */.joinStatements/* : unknown */)/* : unknown */.map/* : unknown */((inner) => " {" + inner + indent + "}")/* : unknown */.orElse/* : unknown */(";")/* : unknown */;
		return indent/* : unknown */ + generatedHeader/* : unknown */ + generatedStatements/* : unknown */;
	}
}
/* private */class Block/*  */ {
	constructor (depth : number, header : BlockHeader, statements : List<FunctionSegment>) {
	}
	/* @Override
        public */ generate() : string {
		let indent = /* createIndent */(this/* : Block */.depth/* : unknown */)/* : unknown */;
		let collect = this/* : Block */.statements/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(FunctionSegment/* : FunctionSegment */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner()/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return indent/* : unknown */ + this/* : Block */.header/* : unknown */.generate/* : unknown */()/* : unknown */ + "{" + collect + indent + "}";
	}
}
/* private */class Conditional/*  */ {
	constructor (prefix : string, value1 : Value) {
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
	/* private final */ value1 : Value;
	Return(value1 : Value) : /* public */ {
		this/* : Return */.value1/* : unknown */ = value1/* : Value */;
	}
	/* @Override
        public */ generate() : string {
		return "return " + this/* : Return */.value1/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
}
/* private */class Initialization/*  */ {
	constructor (definition : Definition, source : Value) {
	}
	/* @Override
        public */ generate() : string {
		return "let " + this/* : Initialization */.definition/* : unknown */.generate/* : unknown */()/* : unknown */ + " = " + this/* : Initialization */.source/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
}
/* private */class Assignment/*  */ {
	constructor (destination : Value, source : Value) {
	}
	/* @Override
        public */ generate() : string {
		return this/* : Assignment */.destination/* : unknown */.generate/* : unknown */()/* : unknown */ + " = " + this/* : Assignment */.source/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
}
/* private */class Statement/*  */ {
	constructor (depth : number, value : StatementValue) {
	}
	/* @Override
        public */ generate() : string {
		return /* createIndent */(this/* : Statement */.depth/* : unknown */)/* : unknown */ + this/* : Statement */.value/* : unknown */.generate/* : unknown */()/* : unknown */ + ";";
	}
}
/* private */class MethodPrototype/*  */ {
	constructor (depth : number, header : Header, parameters : List<Definition>, content : string) {
	}
	/* private */ findParamTypes() : List<Type> {
		return this/* : MethodPrototype */.parameters/* : unknown */()/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(Definition/* : Definition */.type/* : unknown */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
	}
	/* @Override
        public */ createDefinition() : Option<Definition> {
		return new Some(this/* : MethodPrototype */.header/* : unknown */.createDefinition/* : unknown */(this/* : MethodPrototype */.findParamTypes/* : () => List<Type> */()/* : List<Type> */)/* : unknown */)/* : Some */;
	}
}
/* private */class IncompleteClassSegmentWrapper/*  */ {
	constructor (segment : ClassSegment) {
	}
	/* @Override
        public */ createDefinition() : Option<Definition> {
		return new None()/* : None */;
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
        public */ replace(mapping : Map<string, Type>) : Type {
		return this/* : Primitive */;
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
        public */ type() : Type {
		return Primitive/* : Primitive */.Boolean/* : unknown */;
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
		let state : CompileState = new CompileState()/* : CompileState */;
		let parsed = /* parseStatements */(state/* : CompileState */, input/* : string */, Main/* : Main */.compileRootSegment/* : unknown */)/* : unknown */;
		let joined = parsed/* : unknown */.left/* : unknown */()/* : unknown */.structures/* : unknown */.iterate/* : unknown */()/* : unknown */.collect/* : unknown */(new Joiner()/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return joined/* : unknown */ + /* generateStatements */(parsed/* : unknown */.right/* : unknown */()/* : unknown */)/* : unknown */;
	}
	/* private static */ generateStatements(statements : List<string>) : string {
		return /* generateAll */(Main/* : Main */.mergeStatements/* : unknown */, statements/* : List<string> */)/* : unknown */;
	}
	/* private static  */ parseStatements<T>(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => [CompileState, T]) : [CompileState, List<T>] {
		return /* parseAllWithIndices */(state/* : CompileState */, input/* : string */, Main/* : Main */.foldStatementChar/* : unknown */, (state3, tuple) => ((BiFunction<CompileState, String, Option<Tuple2<CompileState, T>>>) (state1, s) => /* new Some<>(mapper.apply(state1, s))).apply(state3, tuple */.right/* : unknown */(/* ) */)/* : unknown */)/* : unknown */.orElseGet/* : unknown */(() => new Tuple2Impl(state/* : CompileState */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */)/* : Tuple2Impl */)/* : unknown */;
	}
	/* private static */ generateAll(merger : (arg0 : string, arg1 : string) => string, elements : List<string>) : string {
		return elements/* : List<string> */.iterate/* : () => Iterator<T> */()/* : Iterator<T> */.fold/* : (arg0 : R, arg1 : (arg0 : R, arg1 : T) => R) => R */("", merger/* : (arg0 : string, arg1 : string) => string */)/* : R */;
	}
	/* private static  */ parseAllWithIndices<T>(state : CompileState, input : string, folder : (arg0 : DivideState, arg1 : string) => DivideState, mapper : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]>) : Option<[CompileState, List<T>]> {
		let stringList = /* divideAll */(input/* : string */, folder/* : (arg0 : DivideState, arg1 : string) => DivideState */)/* : unknown */;
		return /* mapUsingState */(state/* : CompileState */, stringList/* : unknown */, mapper/* : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]> */)/* : unknown */;
	}
	/* private static  */ mapUsingState<TR>(state : CompileState, elements : List<T>, mapper : (arg0 : CompileState, arg1 : [number, T]) => Option<[CompileState, R]>) : Option<[CompileState, List<R>]> {
		let initial : Option<[CompileState, List<R>]> = new Some(new Tuple2Impl(state/* : CompileState */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */)/* : Tuple2Impl */)/* : Some */;
		return elements/* : List<T> */.iterateWithIndices/* : () => Iterator<[number, T]> */()/* : Iterator<[number, T]> */.fold/* : (arg0 : R, arg1 : (arg0 : R, arg1 : [number, T]) => R) => R */(initial/* : Option<[CompileState, List<R>]> */, (tuple, element) => {
			return tuple/* : unknown */.flatMap/* : unknown */((inner) => {
				let state1 = inner/* : unknown */.left/* : unknown */()/* : unknown */;
				let right = inner/* : unknown */.right/* : unknown */()/* : unknown */;
				return mapper/* : (arg0 : CompileState, arg1 : [number, T]) => Option<[CompileState, R]> */(state1/* : unknown */, element/* : unknown */)/* : Option<[CompileState, R]> */.map/* : (arg0 : (arg0 : [CompileState, R]) => R) => Option<R> */((applied : [CompileState, R]) => {
					return new Tuple2Impl(applied/* : [CompileState, R] */[0/* : number */]()/* : unknown */, right/* : unknown */.addLast/* : unknown */(applied/* : [CompileState, R] */[1/* : number */]()/* : unknown */)/* : unknown */)/* : Tuple2Impl */;
				})/* : Option<R> */;
			})/* : unknown */;
		})/* : R */;
	}
	/* private static */ mergeStatements(cache : string, statement : string) : string {
		return cache/* : string */ + statement/* : string */;
	}
	/* private static */ divideAll(input : string, folder : (arg0 : DivideState, arg1 : string) => DivideState) : List<string> {
		let current : DivideState = new DivideState(input/* : string */)/* : DivideState */;
		while (true){
			let maybePopped : Option<R> = current/* : DivideState */.pop/* : () => Option<[string, DivideState]> */()/* : Option<[string, DivideState]> */.map/* : (arg0 : (arg0 : [string, DivideState]) => R) => Option<R> */((tuple : [string, DivideState]) => {
				return /* foldSingleQuotes */(tuple/* : [string, DivideState] */)/* : unknown */.or/* : unknown */(() => /* foldDoubleQuotes */(tuple/* : [string, DivideState] */)/* : unknown */)/* : unknown */.orElseGet/* : unknown */(() => folder/* : (arg0 : DivideState, arg1 : string) => DivideState */(tuple/* : [string, DivideState] */[1/* : number */]()/* : unknown */, tuple/* : [string, DivideState] */[0/* : number */]()/* : unknown */)/* : DivideState */)/* : unknown */;
			})/* : Option<R> */;
			if (maybePopped/* : Option<R> */.isPresent/* : unknown */()/* : unknown */){
				current/* : DivideState */ = maybePopped/* : Option<R> */.orElse/* : unknown */(current/* : DivideState */)/* : unknown */;
			}
			else {
				/* break */;
			}
		}
		return current/* : DivideState */.advance/* : () => DivideState */()/* : DivideState */.segments/* : unknown */;
	}
	/* private static */ foldDoubleQuotes(tuple : [string, DivideState]) : Option<DivideState> {
		if (tuple/* : [string, DivideState] */[0/* : number */]()/* : unknown */ === /*  '\"' */){
			let current = tuple/* : [string, DivideState] */[1/* : number */]()/* : unknown */.append/* : unknown */(tuple/* : [string, DivideState] */[0/* : number */]()/* : unknown */)/* : unknown */;
			while (true){
				let maybePopped = current/* : unknown */.popAndAppendToTuple/* : unknown */()/* : unknown */;
				if (maybePopped/* : unknown */.isEmpty/* : unknown */()/* : unknown */){
					/* break */;
				}
				let popped = maybePopped/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */;
				current/* : unknown */ = popped/* : unknown */.right/* : unknown */()/* : unknown */;
				if (popped/* : unknown */.left/* : unknown */()/* : unknown */ === /*  '\\' */){
					current/* : unknown */ = current/* : unknown */.popAndAppendToOption/* : unknown */()/* : unknown */.orElse/* : unknown */(current/* : unknown */)/* : unknown */;
				}
				if (popped/* : unknown */.left/* : unknown */()/* : unknown */ === /*  '\"' */){
					/* break */;
				}
			}
			return new Some(current/* : unknown */)/* : Some */;
		}
		return new None()/* : None */;
	}
	/* private static */ foldSingleQuotes(tuple : [string, DivideState]) : Option<DivideState> {
		if (/* tuple.left() != '\'' */){
			return new None()/* : None */;
		}
		let appended = tuple/* : [string, DivideState] */[1/* : number */]()/* : unknown */.append/* : unknown */(tuple/* : [string, DivideState] */[0/* : number */]()/* : unknown */)/* : unknown */;
		return appended/* : unknown */.popAndAppendToTuple/* : unknown */()/* : unknown */.map/* : unknown */(Main/* : Main */.foldEscaped/* : unknown */)/* : unknown */.flatMap/* : unknown */(DivideState/* : DivideState */.popAndAppendToOption/* : unknown */)/* : unknown */;
	}
	/* private static */ foldEscaped(escaped : [string, DivideState]) : DivideState {
		if (escaped/* : [string, DivideState] */[0/* : number */]()/* : unknown */ === /*  '\\' */){
			return escaped/* : [string, DivideState] */[1/* : number */]()/* : unknown */.popAndAppendToOption/* : unknown */()/* : unknown */.orElse/* : unknown */(escaped/* : [string, DivideState] */[1/* : number */]()/* : unknown */)/* : unknown */;
		}
		return escaped/* : [string, DivideState] */[1/* : number */]()/* : unknown */;
	}
	/* private static */ foldStatementChar(state : DivideState, c : string) : DivideState {
		let append : DivideState = state/* : DivideState */.append/* : (arg0 : string) => DivideState */(c/* : string */)/* : DivideState */;
		if (c/* : string */ === /*  ';'  */ && append/* : DivideState */.isLevel/* : unknown */()/* : unknown */){
			return append/* : DivideState */.advance/* : unknown */()/* : unknown */;
		}
		if (c/* : string */ === /*  '}'  */ && append/* : DivideState */.isShallow/* : unknown */()/* : unknown */){
			return append/* : DivideState */.advance/* : unknown */()/* : unknown */.exit/* : unknown */()/* : unknown */;
		}
		if (c/* : string */ === /*  '{'  */ || c/* : string */ === /*  '(' */){
			return append/* : DivideState */.enter/* : unknown */()/* : unknown */;
		}
		if (c/* : string */ === /*  '}'  */ || c/* : string */ === /*  ')' */){
			return append/* : DivideState */.exit/* : unknown */()/* : unknown */;
		}
		return append/* : DivideState */;
	}
	/* private static */ compileRootSegment(state : CompileState, input : string) : [CompileState, string] {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.startsWith/* : unknown */("package ")/* : unknown */ || stripped/* : unknown */.startsWith/* : unknown */("import ")/* : unknown */){
			return new Tuple2Impl(state/* : CompileState */, "")/* : Tuple2Impl */;
		}
		return /* parseClass */(stripped/* : unknown */, state/* : CompileState */)/* : unknown */.map/* : unknown */((tuple) => {
			return new Tuple2Impl(tuple/* : unknown */.left/* : unknown */()/* : unknown */, tuple/* : unknown */.right/* : unknown */()/* : unknown */.segment/* : unknown */.generate/* : unknown */()/* : unknown */)/* : Tuple2Impl */;
		})/* : unknown */.orElseGet/* : unknown */(() => new Tuple2Impl(state/* : CompileState */, /* generatePlaceholder */(stripped/* : unknown */)/* : unknown */)/* : Tuple2Impl */)/* : unknown */;
	}
	/* private static */ parseClass(stripped : string, state : CompileState) : Option<[CompileState, IncompleteClassSegmentWrapper]> {
		return /* parseStructure */(stripped/* : string */, "class ", "class ", state/* : CompileState */)/* : unknown */;
	}
	/* private static */ parseStructure(stripped : string, sourceInfix : string, targetInfix : string, state : CompileState) : Option<[CompileState, IncompleteClassSegmentWrapper]> {
		return /* first */(stripped/* : string */, sourceInfix/* : string */, (beforeInfix, right) => {
			return /* first */(right/* : unknown */, "{", (beforeContent, withEnd) => {
				return /* suffix */(withEnd/* : unknown */.strip/* : unknown */()/* : unknown */, "}", (content1) => {
					return /* getOr */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : unknown */, beforeContent/* : unknown */, content1/* : unknown */)/* : unknown */;
				})/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ getOr(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string) : Option<[CompileState, IncompleteClassSegmentWrapper]> {
		return /* first */(beforeContent/* : string */, " implements ", (s, s2) => {
			return /* structureWithMaybeExtends */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : string */, s/* : unknown */, content1/* : string */)/* : unknown */;
		})/* : unknown */.or/* : unknown */(() => {
			return /* structureWithMaybeExtends */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : string */, beforeContent/* : string */, content1/* : string */)/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ structureWithMaybeExtends(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string) : Option<[CompileState, IncompleteClassSegmentWrapper]> {
		return /* first */(beforeContent/* : string */, " extends ", (s, s2) => {
			return /* structureWithMaybeParams */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : string */, s/* : unknown */, content1/* : string */)/* : unknown */;
		})/* : unknown */.or/* : unknown */(() => {
			return /* structureWithMaybeParams */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : string */, beforeContent/* : string */, content1/* : string */)/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ structureWithMaybeParams(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string) : Option<[CompileState, IncompleteClassSegmentWrapper]> {
		return /* suffix */(beforeContent/* : string */.strip/* : unknown */()/* : unknown */, ")", (s) => {
			return /* first */(s/* : unknown */, "(", (s1, s2) => {
				let parsed = /* parseParameters */(state/* : CompileState */, s2/* : unknown */)/* : unknown */;
				return /* getOred */(targetInfix/* : string */, parsed/* : unknown */.left/* : unknown */()/* : unknown */, beforeInfix/* : string */, s1/* : unknown */, content1/* : string */, parsed/* : unknown */.right/* : unknown */()/* : unknown */)/* : unknown */;
			})/* : unknown */;
		})/* : unknown */.or/* : unknown */(() => {
			return /* getOred */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : string */, beforeContent/* : string */, content1/* : string */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */)/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ getOred(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string, params : List<Parameter>) : Option<[CompileState, IncompleteClassSegmentWrapper]> {
		return /* first */(beforeContent/* : string */, "<", (name, withTypeParams) => {
			return /* first */(withTypeParams/* : unknown */, ">", (typeParamsString, afterTypeParams) => {
				let /* final */ mapper : (arg0 : CompileState, arg1 : string) => [CompileState, string] = (state1, s) => new Tuple2Impl(state1/* : unknown */, s/* : unknown */.strip/* : unknown */()/* : unknown */)/* : Tuple2Impl */;
				let typeParams = /* parseValuesOrEmpty */(state/* : CompileState */, typeParamsString/* : unknown */, (state1, s) => new Some(mapper/* : (arg0 : CompileState, arg1 : string) => [CompileState, string] */(state1/* : unknown */, s/* : unknown */)/* : [CompileState, string] */)/* : Some */)/* : unknown */;
				return /* assembleStructure */(typeParams/* : unknown */.left/* : unknown */()/* : unknown */, targetInfix/* : string */, beforeInfix/* : string */, name/* : unknown */, content1/* : string */, typeParams/* : unknown */.right/* : unknown */()/* : unknown */, afterTypeParams/* : unknown */, params/* : List<Parameter> */)/* : unknown */;
			})/* : unknown */;
		})/* : unknown */.or/* : unknown */(() => {
			return /* assembleStructure */(state/* : CompileState */, targetInfix/* : string */, beforeInfix/* : string */, beforeContent/* : string */, content1/* : string */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, "", params/* : List<Parameter> */)/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ assembleStructure(state : CompileState, targetInfix : string, beforeInfix : string, rawName : string, content : string, typeParams : List<string>, after : string, rawParameters : List<Parameter>) : Option<[CompileState, IncompleteClassSegmentWrapper]> {
		let name = rawName/* : string */.strip/* : unknown */()/* : unknown */;
		if (!/* isSymbol */(name/* : unknown */)/* : unknown */){
			return new None()/* : None */;
		}
		let segmentsTuple : [CompileState, List<T>] = parseStatements/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => [CompileState, T]) => [CompileState, List<T>] */(state/* : CompileState */.pushStructName/* : (arg0 : string) => CompileState */(name/* : unknown */)/* : CompileState */.withTypeParams/* : unknown */(typeParams/* : List<string> */)/* : unknown */, content/* : string */, (state0, input) => /* parseClassSegment */(state0/* : unknown */, input/* : unknown */, 1/* : number */)/* : unknown */)/* : [CompileState, List<T>] */;
		let segmentsState = segmentsTuple/* : [CompileState, List<T>] */[0/* : number */]()/* : unknown */;
		let segments = segmentsTuple/* : [CompileState, List<T>] */[1/* : number */]()/* : unknown */;
		let definitions = segments/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(IncompleteClassSegment/* : IncompleteClassSegment */.createDefinition/* : unknown */)/* : unknown */.flatMap/* : unknown */(Iterators/* : Iterators */.fromOption/* : unknown */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
		let thisType : ObjectType = new ObjectType(name/* : unknown */, typeParams/* : List<string> */, definitions/* : unknown */)/* : ObjectType */;
		let state2 = segmentsState/* : unknown */.enterDefinitions/* : unknown */()/* : unknown */.withDefinition/* : unknown */(ImmutableDefinition/* : ImmutableDefinition */.createSimpleDefinition/* : (arg0 : string, arg1 : Type) => Definition */("this", thisType/* : ObjectType */)/* : Definition */)/* : unknown */;
		return mapUsingState/* : (arg0 : CompileState, arg1 : List<T>, arg2 : (arg0 : CompileState, arg1 : [number, T]) => Option<[CompileState, R]>) => Option<[CompileState, List<R>]> */(state2/* : unknown */, segments/* : unknown */, (state1, entry) => /* switch (entry.right()) {
            case IncompleteClassSegmentWrapper wrapper  */ - /* > new Some<>(new Tuple2Impl<>(state1, wrapper.segment));
            case MethodPrototype methodPrototype  */ - /* > completeMethod(state1, methodPrototype);
            case Whitespace whitespace  */ - /* > new Some<>(new Tuple2Impl<>(state1, whitespace));
            case Placeholder placeholder  */ - /* > new Some<>(new Tuple2Impl<>(state1, placeholder));
        } */)/* : Option<[CompileState, List<R>]> */.map/* : (arg0 : (arg0 : [CompileState, List<R>]) => R) => Option<R> */((completedTuple : [CompileState, List<R>]) => {
			let completedState = completedTuple/* : [CompileState, List<R>] */[0/* : number */]()/* : unknown */;
			let completed = completedTuple/* : [CompileState, List<R>] */[1/* : number */]()/* : unknown */;
			return /* completeStructure */(completedState/* : unknown */.exitDefinitions/* : unknown */()/* : unknown */, targetInfix/* : string */, beforeInfix/* : string */, name/* : unknown */, typeParams/* : List<string> */, /* retainDefinitions */(rawParameters/* : List<Parameter> */)/* : unknown */, after/* : string */, completed/* : unknown */, thisType/* : ObjectType */)/* : unknown */;
		})/* : Option<R> */;
	}
	/* private static */ completeStructure(state : CompileState, targetInfix : string, beforeInfix : string, name : string, typeParams : List<string>, parameters : List<Definition>, after : string, segments : List<ClassSegment>, thisType : ObjectType) : Tuple2Impl<CompileState, IncompleteClassSegmentWrapper> {
		/* List<ClassSegment> withMaybeConstructor */;
		if (parameters/* : List<Definition> */.isEmpty/* : () => boolean */()/* : boolean */){
			/* withMaybeConstructor */ = segments/* : List<ClassSegment> */;
		}
		else {
			/* withMaybeConstructor */ = segments/* : List<ClassSegment> */.addFirst/* : (arg0 : ClassSegment) => List<T> */(new Method(1/* : number */, new ConstructorHeader()/* : ConstructorHeader */, parameters/* : List<Definition> */, new Some(Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */)/* : Some */)/* : Method */)/* : List<T> */;
		}
		let parsed2 = /* withMaybeConstructor */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(ClassSegment/* : ClassSegment */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner()/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		let joinedTypeParams = typeParams/* : List<string> */.iterate/* : () => Iterator<T> */()/* : Iterator<T> */.collect/* : (arg0 : Collector<T, R>) => R */(new Joiner(", ")/* : Joiner */)/* : R */.map/* : unknown */((inner) => "<" + inner + ">")/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		let generated = /* generatePlaceholder */(beforeInfix/* : string */.strip/* : unknown */()/* : unknown */)/* : unknown */ + targetInfix/* : string */ + name/* : string */ + joinedTypeParams/* : unknown */ + /* generatePlaceholder */(after/* : string */)/* : unknown */ + " {" + parsed2 + "\n}\n";
		let definedState = state/* : CompileState */.popStructName/* : () => CompileState */()/* : CompileState */.addStructure/* : unknown */(generated/* : unknown */)/* : unknown */.addType/* : unknown */(thisType/* : ObjectType */)/* : unknown */;
		return new Tuple2Impl(definedState/* : unknown */, new IncompleteClassSegmentWrapper(new Whitespace()/* : Whitespace */)/* : IncompleteClassSegmentWrapper */)/* : Tuple2Impl */;
	}
	/* private static */ retainDefinition(parameter : Parameter) : Option<Definition> {
		if (parameter/* : Parameter */._variant/* : unknown */ === ParameterVariant.Definition/* : unknown */){
			return new Some(/* definition */)/* : Some */;
		}
		return new None()/* : None */;
	}
	/* private static */ isSymbol(input : string) : boolean {
		/* for (var i = 0; i < input.length(); i++) */{
			let c = input/* : string */.charAt/* : unknown */(/* i */)/* : unknown */;
			if (/* Character */.isLetter/* : unknown */(c/* : unknown */)/* : unknown */ || /*  */(/* i != 0  */ && /* Character */.isDigit/* : unknown */(c/* : unknown */)/* : unknown */)/* : unknown */){
				/* continue */;
			}
			return false;
		}
		return true;
	}
	/* private static  */ prefix<T>(input : string, prefix : string, mapper : (arg0 : string) => Option<T>) : Option<T> {
		if (!input/* : string */.startsWith/* : unknown */(prefix/* : string */)/* : unknown */){
			return new None()/* : None */;
		}
		let slice = input/* : string */.substring/* : unknown */(prefix/* : string */.length/* : unknown */()/* : unknown */)/* : unknown */;
		return mapper/* : (arg0 : string) => Option<T> */(slice/* : unknown */)/* : Option<T> */;
	}
	/* private static  */ suffix<T>(input : string, suffix : string, mapper : (arg0 : string) => Option<T>) : Option<T> {
		if (!input/* : string */.endsWith/* : unknown */(suffix/* : string */)/* : unknown */){
			return new None()/* : None */;
		}
		let slice = input/* : string */.substring/* : unknown */(0/* : number */, input/* : string */.length/* : unknown */()/* : unknown */ - suffix/* : string */.length/* : unknown */()/* : unknown */)/* : unknown */;
		return mapper/* : (arg0 : string) => Option<T> */(slice/* : unknown */)/* : Option<T> */;
	}
	/* private static */ parseClassSegment(state : CompileState, input : string, depth : number) : [CompileState, IncompleteClassSegment] {
		return /* Main.<Whitespace, IncompleteClassSegment>typed */(() => /* parseWhitespace */(input/* : string */, state/* : CompileState */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* typed */(() => parseClass/* : (arg0 : string, arg1 : CompileState) => Option<[CompileState, IncompleteClassSegmentWrapper]> */(input/* : string */, state/* : CompileState */)/* : Option<[CompileState, IncompleteClassSegmentWrapper]> */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* typed */(() => parseStructure/* : (arg0 : string, arg1 : string, arg2 : string, arg3 : CompileState) => Option<[CompileState, IncompleteClassSegmentWrapper]> */(input/* : string */, "interface ", "interface ", state/* : CompileState */)/* : Option<[CompileState, IncompleteClassSegmentWrapper]> */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* typed */(() => parseStructure/* : (arg0 : string, arg1 : string, arg2 : string, arg3 : CompileState) => Option<[CompileState, IncompleteClassSegmentWrapper]> */(input/* : string */, "record ", "class ", state/* : CompileState */)/* : Option<[CompileState, IncompleteClassSegmentWrapper]> */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* typed */(() => parseStructure/* : (arg0 : string, arg1 : string, arg2 : string, arg3 : CompileState) => Option<[CompileState, IncompleteClassSegmentWrapper]> */(input/* : string */, "enum ", "class ", state/* : CompileState */)/* : Option<[CompileState, IncompleteClassSegmentWrapper]> */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseMethod */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* typed */(() => /* parseDefinitionStatement */(input/* : string */, depth/* : number */, state/* : CompileState */)/* : unknown */)/* : unknown */)/* : unknown */.orElseGet/* : unknown */(() => new Tuple2Impl(state/* : CompileState */, new Placeholder(input/* : string */)/* : Placeholder */)/* : Tuple2Impl */)/* : unknown */;
	}
	/* private static  */ typed<T extends SS>(action : () => Option<[CompileState, T]>) : Option<[CompileState, S]> {
		return action/* : () => Option<[CompileState, T]> */()/* : Option<[CompileState, T]> */.map/* : (arg0 : (arg0 : [CompileState, T]) => R) => Option<R> */((tuple : [CompileState, T]) => new Tuple2Impl(tuple/* : [CompileState, T] */[0/* : number */]()/* : unknown */, tuple/* : [CompileState, T] */[1/* : number */]()/* : unknown */)/* : Tuple2Impl */)/* : Option<R> */;
	}
	/* private static */ parseWhitespace(input : string, state : CompileState) : Option<[CompileState, Whitespace]> {
		if (input/* : string */.isBlank/* : unknown */()/* : unknown */){
			return new Some(new Tuple2Impl(state/* : CompileState */, new Whitespace()/* : Whitespace */)/* : Tuple2Impl */)/* : Some */;
		}
		return new None()/* : None */;
	}
	/* private static */ parseMethod(state : CompileState, input : string, depth : number) : Option<[CompileState, IncompleteClassSegment]> {
		return /* first */(input/* : string */, "(", (definitionString, withParams) => {
			return /* first */(withParams/* : unknown */, ")", (parametersString, rawContent) => {
				return /* parseDefinition(state, definitionString).<Tuple2<CompileState, Header>>map */((tuple) => new Tuple2Impl(tuple/* : unknown */.left/* : unknown */()/* : unknown */, tuple/* : unknown */.right/* : unknown */()/* : unknown */)/* : Tuple2Impl */)/* : unknown */.or/* : unknown */(() => /* parseConstructor */(state/* : CompileState */, definitionString/* : unknown */)/* : unknown */)/* : unknown */.flatMap/* : unknown */((definitionTuple) => /* assembleMethod */(depth/* : number */, parametersString/* : unknown */, rawContent/* : unknown */, definitionTuple/* : unknown */)/* : unknown */)/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ assembleMethod(depth : number, parametersString : string, rawContent : string, definitionTuple : [CompileState, Header]) : Option<[CompileState, IncompleteClassSegment]> {
		let definitionState = definitionTuple/* : [CompileState, Header] */[0/* : number */]()/* : unknown */;
		let header = definitionTuple/* : [CompileState, Header] */[1/* : number */]()/* : unknown */;
		let parametersTuple = /* parseParameters */(definitionState/* : unknown */, parametersString/* : string */)/* : unknown */;
		let rawParameters = parametersTuple/* : unknown */.right/* : unknown */()/* : unknown */;
		let parameters = /* retainDefinitions */(rawParameters/* : unknown */)/* : unknown */;
		return new Some(new Tuple2Impl(parametersTuple/* : unknown */.left/* : unknown */()/* : unknown */, new MethodPrototype(depth/* : number */, header/* : unknown */, parameters/* : unknown */, rawContent/* : string */.strip/* : unknown */()/* : unknown */)/* : MethodPrototype */)/* : Tuple2Impl */)/* : Some */;
	}
	/* private static */ completeMethod(state : CompileState, prototype : MethodPrototype) : Option<[CompileState, ClassSegment]> {
		let paramTypes : List<Type> = prototype/* : MethodPrototype */.findParamTypes/* : () => List<Type> */()/* : List<Type> */;
		let toDefine = prototype/* : MethodPrototype */.header/* : unknown */()/* : unknown */.createDefinition/* : unknown */(paramTypes/* : List<Type> */)/* : unknown */;
		if (prototype/* : MethodPrototype */.content/* : unknown */()/* : unknown */.equals/* : unknown */(";")/* : unknown */){
			return new Some(new Tuple2Impl(state/* : CompileState */.withDefinition/* : (arg0 : Definition) => CompileState */(toDefine/* : unknown */)/* : CompileState */, new Method(prototype/* : MethodPrototype */.depth/* : unknown */()/* : unknown */, prototype/* : MethodPrototype */.header/* : unknown */()/* : unknown */, prototype/* : MethodPrototype */.parameters/* : unknown */()/* : unknown */, new None()/* : None */)/* : Method */)/* : Tuple2Impl */)/* : Some */;
		}
		if (prototype/* : MethodPrototype */.content/* : unknown */()/* : unknown */.startsWith/* : unknown */("{")/* : unknown */ && prototype/* : MethodPrototype */.content/* : unknown */()/* : unknown */.endsWith/* : unknown */("}")/* : unknown */){
			let substring = prototype/* : MethodPrototype */.content/* : unknown */()/* : unknown */.substring/* : unknown */(1/* : number */, prototype/* : MethodPrototype */.content/* : unknown */()/* : unknown */.length/* : unknown */()/* : unknown */ - 1/* : number */)/* : unknown */;
			let withDefined = state/* : CompileState */.enterDefinitions/* : () => CompileState */()/* : CompileState */.withDefinitions/* : unknown */(prototype/* : MethodPrototype */.parameters/* : unknown */()/* : unknown */)/* : unknown */;
			let statementsTuple : [CompileState, List<T>] = parseStatements/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => [CompileState, T]) => [CompileState, List<T>] */(withDefined/* : unknown */, substring/* : unknown */, (state1, input1) => /* parseFunctionSegment */(state1/* : unknown */, input1/* : unknown */, prototype/* : MethodPrototype */.depth/* : unknown */()/* : unknown */ + 1/* : number */)/* : unknown */)/* : [CompileState, List<T>] */;
			let statements = statementsTuple/* : [CompileState, List<T>] */[1/* : number */]()/* : unknown */;
			return new Some(new Tuple2Impl(statementsTuple/* : [CompileState, List<T>] */[0/* : number */]()/* : unknown */.exitDefinitions/* : unknown */()/* : unknown */.withDefinition/* : unknown */(toDefine/* : unknown */)/* : unknown */, new Method(prototype/* : MethodPrototype */.depth/* : unknown */()/* : unknown */, prototype/* : MethodPrototype */.header/* : unknown */()/* : unknown */, prototype/* : MethodPrototype */.parameters/* : unknown */()/* : unknown */, new Some(statements/* : unknown */)/* : Some */)/* : Method */)/* : Tuple2Impl */)/* : Some */;
		}
		return new None()/* : None */;
	}
	/* private static */ parseConstructor(state : CompileState, input : string) : Option<[CompileState, Header]> {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.equals/* : unknown */(state/* : CompileState */.structNames/* : unknown */.last/* : unknown */()/* : unknown */.orElse/* : unknown */("")/* : unknown */)/* : unknown */){
			return new Some(new Tuple2Impl(state/* : CompileState */, new ConstructorHeader()/* : ConstructorHeader */)/* : Tuple2Impl */)/* : Some */;
		}
		return new None()/* : None */;
	}
	/* private static */ joinValues(retainParameters : List<Definition>) : string {
		let inner = retainParameters/* : List<Definition> */.iterate/* : () => Iterator<T> */()/* : Iterator<T> */.map/* : (arg0 : (arg0 : T) => R) => Iterator<R> */(Definition/* : Definition */.generate/* : unknown */)/* : Iterator<R> */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return "(" + inner + ")";
	}
	/* private static */ retainDefinitions(right : List<Parameter>) : List<Definition> {
		return right/* : List<Parameter> */.iterate/* : () => Iterator<T> */()/* : Iterator<T> */.map/* : (arg0 : (arg0 : T) => R) => Iterator<R> */(Main/* : Main */.retainDefinition/* : unknown */)/* : Iterator<R> */.flatMap/* : unknown */(Iterators/* : Iterators */.fromOption/* : unknown */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
	}
	/* private static */ parseParameters(state : CompileState, params : string) : [CompileState, List<Parameter>] {
		return /* parseValuesOrEmpty */(state/* : CompileState */, params/* : string */, (state1, s) => new Some(/* parseParameter */(state1/* : unknown */, s/* : unknown */)/* : unknown */)/* : Some */)/* : unknown */;
	}
	/* private static */ parseFunctionSegments(state : CompileState, input : string, depth : number) : [CompileState, List<FunctionSegment>] {
		return parseStatements/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => [CompileState, T]) => [CompileState, List<T>] */(state/* : CompileState */, input/* : string */, (state1, input1) => /* parseFunctionSegment */(state1/* : unknown */, input1/* : unknown */, depth/* : number */ + 1/* : number */)/* : unknown */)/* : [CompileState, List<T>] */;
	}
	/* private static */ parseFunctionSegment(state : CompileState, input : string, depth : number) : [CompileState, FunctionSegment] {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.isEmpty/* : unknown */()/* : unknown */){
			return new Tuple2Impl(state/* : CompileState */, new Whitespace()/* : Whitespace */)/* : Tuple2Impl */;
		}
		return /* parseFunctionStatement */(state/* : CompileState */, depth/* : number */, stripped/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseBlock */(state/* : CompileState */, depth/* : number */, stripped/* : unknown */)/* : unknown */)/* : unknown */.orElseGet/* : unknown */(() => new Tuple2Impl(state/* : CompileState */, new Placeholder(stripped/* : unknown */)/* : Placeholder */)/* : Tuple2Impl */)/* : unknown */;
	}
	/* private static */ parseFunctionStatement(state : CompileState, depth : number, stripped : string) : Option<[CompileState, FunctionSegment]> {
		return suffix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(stripped/* : string */, ";", (s : string) => {
			let tuple = /* parseStatementValue */(state/* : CompileState */, s/* : string */, depth/* : number */)/* : unknown */;
			let left = tuple/* : unknown */.left/* : unknown */()/* : unknown */;
			let right = tuple/* : unknown */.right/* : unknown */()/* : unknown */;
			return new Some(new Tuple2Impl(left/* : unknown */, new Statement(depth/* : number */, right/* : unknown */)/* : Statement */)/* : Tuple2Impl */)/* : Some */;
		})/* : Option<T> */;
	}
	/* private static */ parseBlock(state : CompileState, depth : number, stripped : string) : Option<[CompileState, FunctionSegment]> {
		return suffix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(stripped/* : string */, "}", (withoutEnd : string) => {
			return /* split */(() => /* toFirst */(withoutEnd/* : string */)/* : unknown */, (beforeContent, content) => {
				return suffix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(beforeContent/* : unknown */, "{", (s : string) => {
					let statements : [CompileState, List<FunctionSegment>] = parseFunctionSegments/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, List<FunctionSegment>] */(state/* : CompileState */, content/* : unknown */, depth/* : number */)/* : [CompileState, List<FunctionSegment>] */;
					let headerTuple = /* parseBlockHeader */(state/* : CompileState */, s/* : string */, depth/* : number */)/* : unknown */;
					let headerState = headerTuple/* : unknown */.left/* : unknown */()/* : unknown */;
					let header = headerTuple/* : unknown */.right/* : unknown */()/* : unknown */;
					let right = statements/* : [CompileState, List<FunctionSegment>] */[1/* : number */]()/* : unknown */;
					return new Some(new Tuple2Impl(headerState/* : unknown */, new Block(depth/* : number */, header/* : unknown */, right/* : unknown */)/* : Block */)/* : Tuple2Impl */)/* : Some */;
				})/* : Option<T> */;
			})/* : unknown */;
		})/* : Option<T> */;
	}
	/* private static */ toFirst(input : string) : Option<[string, string]> {
		let divisions : List<string> = divideAll/* : (arg0 : string, arg1 : (arg0 : DivideState, arg1 : string) => DivideState) => List<string> */(input/* : string */, Main/* : Main */.foldBlockStart/* : unknown */)/* : List<string> */;
		return divisions/* : List<string> */.removeFirst/* : () => Option<[T, List<T>]> */()/* : Option<[T, List<T>]> */.map/* : (arg0 : (arg0 : [T, List<T>]) => R) => Option<R> */((removed : [T, List<T>]) => {
			let right = removed/* : [T, List<T>] */[0/* : number */]()/* : unknown */;
			let left = removed/* : [T, List<T>] */[1/* : number */]()/* : unknown */.iterate/* : unknown */()/* : unknown */.collect/* : unknown */(new Joiner("")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
			return new Tuple2Impl(right/* : unknown */, left/* : unknown */)/* : Tuple2Impl */;
		})/* : Option<R> */;
	}
	/* private static */ parseBlockHeader(state : CompileState, input : string, depth : number) : [CompileState, BlockHeader] {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		return /* parseConditional */(state/* : CompileState */, stripped/* : unknown */, "if", depth/* : number */)/* : unknown */.or/* : unknown */(() => /* parseConditional */(state/* : CompileState */, stripped/* : unknown */, "while", depth/* : number */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseElse */(state/* : CompileState */, input/* : string */)/* : unknown */)/* : unknown */.orElseGet/* : unknown */(() => new Tuple2Impl(state/* : CompileState */, new Placeholder(stripped/* : unknown */)/* : Placeholder */)/* : Tuple2Impl */)/* : unknown */;
	}
	/* private static */ parseElse(state : CompileState, input : string) : Option<[CompileState, BlockHeader]> {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.equals/* : unknown */("else")/* : unknown */){
			return new Some(new Tuple2Impl(state/* : CompileState */, new Else()/* : Else */)/* : Tuple2Impl */)/* : Some */;
		}
		return new None()/* : None */;
	}
	/* private static */ parseConditional(state : CompileState, input : string, prefix : string, depth : number) : Option<[CompileState, BlockHeader]> {
		return prefix/* : string */(input/* : string */, prefix/* : string */, (withoutPrefix) => {
			return prefix/* : string */(withoutPrefix/* : unknown */.strip/* : unknown */()/* : unknown */, "(", (withoutValueStart) => {
				return suffix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(withoutValueStart/* : unknown */, ")", (value : string) => {
					let valueTuple = /* parseValue */(state/* : CompileState */, value/* : string */, depth/* : number */)/* : unknown */;
					let value1 = valueTuple/* : unknown */.right/* : unknown */()/* : unknown */;
					return new Some(new Tuple2Impl(valueTuple/* : unknown */.left/* : unknown */()/* : unknown */, new Conditional(prefix/* : string */, value1/* : unknown */)/* : Conditional */)/* : Tuple2Impl */)/* : Some */;
				})/* : Option<T> */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ foldBlockStart(state : DivideState, c : string) : DivideState {
		let appended : DivideState = state/* : DivideState */.append/* : (arg0 : string) => DivideState */(c/* : string */)/* : DivideState */;
		if (c/* : string */ === /*  '{'  */ && state/* : DivideState */.isLevel/* : unknown */()/* : unknown */){
			return appended/* : DivideState */.advance/* : unknown */()/* : unknown */;
		}
		if (c/* : string */ === /*  '{' */){
			return appended/* : DivideState */.enter/* : unknown */()/* : unknown */;
		}
		if (c/* : string */ === /*  '}' */){
			return appended/* : DivideState */.exit/* : unknown */()/* : unknown */;
		}
		return appended/* : DivideState */;
	}
	/* private static */ parseStatementValue(state : CompileState, input : string, depth : number) : [CompileState, StatementValue] {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.startsWith/* : unknown */("return ")/* : unknown */){
			let value = stripped/* : unknown */.substring/* : unknown */("return ".length/* : unknown */()/* : unknown */)/* : unknown */;
			let tuple = /* parseValue */(state/* : CompileState */, value/* : unknown */, depth/* : number */)/* : unknown */;
			let value1 = tuple/* : unknown */.right/* : unknown */()/* : unknown */;
			return new Tuple2Impl(tuple/* : unknown */.left/* : unknown */()/* : unknown */, new Return(value1/* : unknown */)/* : Return */)/* : Tuple2Impl */;
		}
		return /* parseAssignment */(state/* : CompileState */, depth/* : number */, stripped/* : unknown */)/* : unknown */.orElseGet/* : unknown */(() => {
			return new Tuple2Impl(state/* : CompileState */, new Placeholder(stripped/* : unknown */)/* : Placeholder */)/* : Tuple2Impl */;
		})/* : unknown */;
	}
	/* private static */ parseAssignment(state : CompileState, depth : number, stripped : string) : Option<[CompileState, StatementValue]> {
		return /* first */(stripped/* : string */, "=", (beforeEquals, valueString) => {
			let sourceTuple = /* parseValue */(state/* : CompileState */, valueString/* : unknown */, depth/* : number */)/* : unknown */;
			let sourceState = sourceTuple/* : unknown */.left/* : unknown */()/* : unknown */;
			let source = sourceTuple/* : unknown */.right/* : unknown */()/* : unknown */;
			return /* parseDefinition */(sourceState/* : unknown */, beforeEquals/* : unknown */)/* : unknown */.flatMap/* : unknown */((definitionTuple) => /* parseInitialization */(definitionTuple/* : unknown */.left/* : unknown */()/* : unknown */, definitionTuple/* : unknown */.right/* : unknown */()/* : unknown */, source/* : unknown */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseAssignment */(depth/* : number */, beforeEquals/* : unknown */, sourceState/* : unknown */, source/* : unknown */)/* : unknown */)/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ parseAssignment(depth : number, beforeEquals : string, sourceState : CompileState, source : Value) : Option<[CompileState, StatementValue]> {
		let destinationTuple = /* parseValue */(sourceState/* : CompileState */, beforeEquals/* : string */, depth/* : number */)/* : unknown */;
		let destinationState = destinationTuple/* : unknown */.left/* : unknown */()/* : unknown */;
		let destination = destinationTuple/* : unknown */.right/* : unknown */()/* : unknown */;
		return new Some(new Tuple2Impl(destinationState/* : unknown */, new Assignment(destination/* : unknown */, source/* : Value */)/* : Assignment */)/* : Tuple2Impl */)/* : Some */;
	}
	/* private static */ parseInitialization(state : CompileState, rawDefinition : Definition, source : Value) : Option<[CompileState, StatementValue]> {
		let definition : Definition = rawDefinition/* : Definition */.mapType/* : (arg0 : (arg0 : Type) => Type) => Definition */((type : Type) => {
			if (type/* : Type */.equals/* : unknown */(Primitive/* : Primitive */.Unknown/* : unknown */)/* : unknown */){
				return source/* : Value */.type/* : () => Type */()/* : Type */;
			}
			else {
				return type/* : Type */;
			}
		})/* : Definition */;
		return new Some(new Tuple2Impl(state/* : CompileState */.withDefinition/* : (arg0 : Definition) => CompileState */(definition/* : Definition */)/* : CompileState */, new Initialization(definition/* : Definition */, source/* : Value */)/* : Initialization */)/* : Tuple2Impl */)/* : Some */;
	}
	/* private static */ parseValue(state : CompileState, input : string, depth : number) : [CompileState, Value] {
		return /* parseBoolean */(state/* : CompileState */, input/* : string */)/* : unknown */.or/* : unknown */(() => /* parseLambda */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseString */(state/* : CompileState */, input/* : string */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseDataAccess */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseSymbolValue */(state/* : CompileState */, input/* : string */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseInvokable */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseDigits */(state/* : CompileState */, input/* : string */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseOperation */(state/* : CompileState */, input/* : string */, depth/* : number */, Operator/* : Operator */.ADD/* : unknown */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseOperation */(state/* : CompileState */, input/* : string */, depth/* : number */, Operator/* : Operator */.EQUALS/* : unknown */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseOperation */(state/* : CompileState */, input/* : string */, depth/* : number */, Operator/* : Operator */.SUBTRACT/* : unknown */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseOperation */(state/* : CompileState */, input/* : string */, depth/* : number */, Operator/* : Operator */.AND/* : unknown */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseOperation */(state/* : CompileState */, input/* : string */, depth/* : number */, Operator/* : Operator */.OR/* : unknown */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseOperation */(state/* : CompileState */, input/* : string */, depth/* : number */, /*  Operator.GREATER_THAN_OR_EQUALS */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseNot */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseMethodReference */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseInstanceOf */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : unknown */)/* : unknown */.orElseGet/* : unknown */(() => new Tuple2Impl<CompileState, Value>(state/* : CompileState */, new Placeholder(input/* : string */)/* : Placeholder */)/* : Tuple2Impl<CompileState, Value> */)/* : unknown */;
	}
	/* private static */ parseBoolean(state : CompileState, input : string) : Option<[CompileState, Value]> {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.equals/* : unknown */("false")/* : unknown */){
			return new Some(new Tuple2Impl(state/* : CompileState */, BooleanValue/* : BooleanValue */.False/* : unknown */)/* : Tuple2Impl */)/* : Some */;
		}
		if (stripped/* : unknown */.equals/* : unknown */("true")/* : unknown */){
			return new Some(new Tuple2Impl(state/* : CompileState */, BooleanValue/* : BooleanValue */.True/* : unknown */)/* : Tuple2Impl */)/* : Some */;
		}
		return new None()/* : None */;
	}
	/* private static */ parseInstanceOf(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return /* last */(input/* : string */, "instanceof", (s, s2) => {
			let childTuple : [CompileState, Value] = parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state/* : CompileState */, s/* : unknown */, depth/* : number */)/* : [CompileState, Value] */;
			return /* parseDefinition */(childTuple/* : [CompileState, Value] */[0/* : number */]()/* : unknown */, s2/* : unknown */)/* : unknown */.map/* : unknown */((definitionTuple) => {
				let value = childTuple/* : [CompileState, Value] */[1/* : number */]()/* : unknown */;
				let definition = definitionTuple/* : unknown */.right/* : unknown */()/* : unknown */;
				let variant : DataAccess = new DataAccess(value/* : unknown */, "_variant", Primitive/* : Primitive */.Unknown/* : unknown */)/* : DataAccess */;
				let type = value/* : unknown */.type/* : unknown */()/* : unknown */;
				let generate = type/* : unknown */.findName/* : unknown */()/* : unknown */.orElse/* : unknown */("")/* : unknown */;
				let temp : SymbolValue = new SymbolValue(generate/* : unknown */ + "Variant." + definition/* : unknown */.type/* : unknown */()/* : unknown */.findName/* : unknown */()/* : unknown */.orElse/* : unknown */("")/* : unknown */, Primitive/* : Primitive */.Unknown/* : unknown */)/* : SymbolValue */;
				return new Tuple2Impl(definitionTuple/* : unknown */.left/* : unknown */()/* : unknown */, new Operation(variant/* : DataAccess */, Operator/* : Operator */.EQUALS/* : unknown */, temp/* : SymbolValue */)/* : Operation */)/* : Tuple2Impl */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ parseMethodReference(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return /* last */(input/* : string */, "::", (s, s2) => {
			let tuple : [CompileState, Value] = parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state/* : CompileState */, s/* : unknown */, depth/* : number */)/* : [CompileState, Value] */;
			return new Some(new Tuple2Impl(tuple/* : [CompileState, Value] */[0/* : number */]()/* : unknown */, new DataAccess(tuple/* : [CompileState, Value] */[1/* : number */]()/* : unknown */, s2/* : unknown */, Primitive/* : Primitive */.Unknown/* : unknown */)/* : DataAccess */)/* : Tuple2Impl */)/* : Some */;
		})/* : unknown */;
	}
	/* private static */ parseNot(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.startsWith/* : unknown */("!")/* : unknown */){
			let slice = stripped/* : unknown */.substring/* : unknown */(1/* : number */)/* : unknown */;
			let tuple : [CompileState, Value] = parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state/* : CompileState */, slice/* : unknown */, depth/* : number */)/* : [CompileState, Value] */;
			let value = tuple/* : [CompileState, Value] */[1/* : number */]()/* : unknown */;
			return new Some(new Tuple2Impl(tuple/* : [CompileState, Value] */[0/* : number */]()/* : unknown */, new Not(value/* : unknown */)/* : Not */)/* : Tuple2Impl */)/* : Some */;
		}
		return new None()/* : None */;
	}
	/* private static */ parseLambda(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return /* first */(input/* : string */, "->", (beforeArrow, valueString) => {
			let strippedBeforeArrow = beforeArrow/* : unknown */.strip/* : unknown */()/* : unknown */;
			if (isSymbol/* : (arg0 : string) => boolean */(strippedBeforeArrow/* : unknown */)/* : boolean */){
				let type : Type = Primitive/* : Primitive */.Unknown/* : unknown */;
				if (/* state.typeRegister instanceof Some */(/* var expectedType */)/* : unknown */){
					if (/* expectedType */._variant/* : unknown */ === Variant.FunctionType/* : unknown */){
						type/* : Type */ = /* functionType */.arguments/* : unknown */.get/* : unknown */(0/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */;
					}
				}
				return /* assembleLambda */(state/* : CompileState */, Lists/* : Lists */.of/* : (arg0 : T[]) => List<T> */(ImmutableDefinition/* : ImmutableDefinition */.createSimpleDefinition/* : (arg0 : string, arg1 : Type) => Definition */(strippedBeforeArrow/* : unknown */, type/* : Type */)/* : Definition */)/* : List<T> */, valueString/* : unknown */, depth/* : number */)/* : unknown */;
			}
			if (strippedBeforeArrow/* : unknown */.startsWith/* : unknown */("(")/* : unknown */ && strippedBeforeArrow/* : unknown */.endsWith/* : unknown */(")")/* : unknown */){
				let parameterNames = divideAll/* : (arg0 : string, arg1 : (arg0 : DivideState, arg1 : string) => DivideState) => List<string> */(strippedBeforeArrow/* : unknown */.substring/* : unknown */(1/* : number */, strippedBeforeArrow/* : unknown */.length/* : unknown */()/* : unknown */ - 1/* : number */)/* : unknown */, Main/* : Main */.foldValueChar/* : unknown */)/* : List<string> */.iterate/* : () => Iterator<T> */()/* : Iterator<T> */.map/* : (arg0 : (arg0 : T) => R) => Iterator<R> */(/* String */.strip/* : unknown */)/* : Iterator<R> */.filter/* : unknown */((value) => !value/* : unknown */.isEmpty/* : unknown */()/* : unknown */)/* : unknown */.map/* : unknown */((name) => ImmutableDefinition/* : ImmutableDefinition */.createSimpleDefinition/* : (arg0 : string, arg1 : Type) => Definition */(name/* : unknown */, Primitive/* : Primitive */.Unknown/* : unknown */)/* : Definition */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
				return /* assembleLambda */(state/* : CompileState */, parameterNames/* : unknown */, valueString/* : unknown */, depth/* : number */)/* : unknown */;
			}
			return new None()/* : None */;
		})/* : unknown */;
	}
	/* private static */ assembleLambda(state : CompileState, definitions : List<Definition>, valueString : string, depth : number) : Some<[CompileState, Value]> {
		let strippedValueString = valueString/* : string */.strip/* : unknown */()/* : unknown */;
		/* Tuple2Impl<CompileState, LambdaValue> value */;
		let state2 : CompileState = state/* : CompileState */.withDefinitions/* : (arg0 : List<Definition>) => CompileState */(definitions/* : List<Definition> */)/* : CompileState */;
		if (strippedValueString/* : unknown */.startsWith/* : unknown */("{")/* : unknown */ && strippedValueString/* : unknown */.endsWith/* : unknown */("}")/* : unknown */){
			let value1 : [CompileState, List<T>] = parseStatements/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => [CompileState, T]) => [CompileState, List<T>] */(state2/* : CompileState */, strippedValueString/* : unknown */.substring/* : unknown */(1/* : number */, strippedValueString/* : unknown */.length/* : unknown */()/* : unknown */ - 1/* : number */)/* : unknown */, (state1, input1) => parseFunctionSegment/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, FunctionSegment] */(state1/* : unknown */, input1/* : unknown */, depth/* : number */ + 1/* : number */)/* : [CompileState, FunctionSegment] */)/* : [CompileState, List<T>] */;
			let right = value1/* : [CompileState, List<T>] */[1/* : number */]()/* : unknown */;
			/* value */ = new Tuple2Impl(value1/* : [CompileState, List<T>] */[0/* : number */]()/* : unknown */, new BlockLambdaValue(depth/* : number */, right/* : unknown */)/* : BlockLambdaValue */)/* : Tuple2Impl */;
		}
		else {
			let value1 : [CompileState, Value] = parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state2/* : CompileState */, strippedValueString/* : unknown */, depth/* : number */)/* : [CompileState, Value] */;
			/* value */ = new Tuple2Impl(value1/* : [CompileState, List<T>] */[0/* : number */]()/* : unknown */, value1/* : [CompileState, List<T>] */[1/* : number */]()/* : unknown */)/* : Tuple2Impl */;
		}
		let right = /* value */.right/* : unknown */()/* : unknown */;
		return new Some(new Tuple2Impl(/* value */.left/* : unknown */()/* : unknown */, new Lambda(definitions/* : List<Definition> */, right/* : unknown */)/* : Lambda */)/* : Tuple2Impl */)/* : Some */;
	}
	/* private static */ parseDigits(state : CompileState, input : string) : Option<[CompileState, Value]> {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (/* isNumber */(stripped/* : unknown */)/* : unknown */){
			return new Some(new Tuple2Impl<CompileState, Value>(state/* : CompileState */, new SymbolValue(stripped/* : unknown */, Primitive/* : Primitive */.Int/* : unknown */)/* : SymbolValue */)/* : Tuple2Impl<CompileState, Value> */)/* : Some */;
		}
		return new None()/* : None */;
	}
	/* private static */ isNumber(input : string) : boolean {
		/* String maybeTruncated */;
		if (input/* : string */.startsWith/* : unknown */("-")/* : unknown */){
			/* maybeTruncated */ = input/* : string */.substring/* : unknown */(1/* : number */)/* : unknown */;
		}
		else {
			/* maybeTruncated */ = input/* : string */;
		}
		return /* areAllDigits */(/* maybeTruncated */)/* : unknown */;
	}
	/* private static */ areAllDigits(input : string) : boolean {
		/* for (var i = 0; i < input.length(); i++) */{
			let c = input/* : string */.charAt/* : unknown */(/* i */)/* : unknown */;
			if (/* Character */.isDigit/* : unknown */(c/* : unknown */)/* : unknown */){
				/* continue */;
			}
			return false;
		}
		return true;
	}
	/* private static */ parseInvokable(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return suffix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(input/* : string */.strip/* : unknown */()/* : unknown */, ")", (withoutEnd : string) => {
			return /* split */(() => /* toLast */(withoutEnd/* : string */, "", Main/* : Main */.foldInvocationStart/* : unknown */)/* : unknown */, (callerWithEnd, argumentsString) => {
				return suffix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(callerWithEnd/* : unknown */, "(", (callerString : string) => {
					return /* assembleInvokable */(state/* : CompileState */, depth/* : number */, argumentsString/* : unknown */, callerString/* : string */.strip/* : unknown */()/* : unknown */)/* : unknown */;
				})/* : Option<T> */;
			})/* : unknown */;
		})/* : Option<T> */;
	}
	/* private static */ assembleInvokable(state : CompileState, depth : number, argumentsString : string, callerString : string) : Some<[CompileState, Value]> {
		let callerTuple = /* invocationHeader */(state/* : CompileState */, depth/* : number */, callerString/* : string */)/* : unknown */;
		let oldCallerState = callerTuple/* : unknown */.left/* : unknown */()/* : unknown */;
		let oldCaller = callerTuple/* : unknown */.right/* : unknown */()/* : unknown */;
		let newCaller = /* modifyCaller */(oldCallerState/* : unknown */, oldCaller/* : unknown */)/* : unknown */;
		let callerType = /* findCallerType */(newCaller/* : unknown */)/* : unknown */;
		let argumentsTuple = /* parseValuesWithIndices */(oldCallerState/* : unknown */, argumentsString/* : string */, (currentState, pair) => {
			let index = pair/* : unknown */.left/* : unknown */()/* : unknown */;
			let element = pair/* : unknown */.right/* : unknown */()/* : unknown */;
			let expectedType = callerType/* : unknown */.arguments/* : unknown */.get/* : unknown */(index/* : unknown */)/* : unknown */.orElse/* : unknown */(Primitive/* : Primitive */.Unknown/* : unknown */)/* : unknown */;
			let withExpected = currentState/* : unknown */.withExpectedType/* : unknown */(expectedType/* : unknown */)/* : unknown */;
			let valueTuple = /* parseArgument */(withExpected/* : unknown */, element/* : unknown */, depth/* : number */)/* : unknown */;
			let valueState = valueTuple/* : unknown */.left/* : unknown */()/* : unknown */;
			let value = valueTuple/* : unknown */.right/* : unknown */()/* : unknown */;
			let actualType = valueTuple/* : unknown */.left/* : unknown */()/* : unknown */.typeRegister/* : unknown */.orElse/* : unknown */(Primitive/* : Primitive */.Unknown/* : unknown */)/* : unknown */;
			return new Some(new Tuple2Impl(valueState/* : unknown */, new Tuple2Impl(value/* : unknown */, actualType/* : unknown */)/* : Tuple2Impl */)/* : Tuple2Impl */)/* : Some */;
		})/* : unknown */.orElseGet/* : unknown */(() => new Tuple2Impl(oldCallerState/* : unknown */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */)/* : Tuple2Impl */)/* : unknown */;
		let argumentsState = argumentsTuple/* : unknown */.left/* : unknown */()/* : unknown */;
		let argumentsWithActualTypes = argumentsTuple/* : unknown */.right/* : unknown */()/* : unknown */;
		let arguments = argumentsWithActualTypes/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(Tuple2/* : Tuple2 */.left/* : unknown */)/* : unknown */.map/* : unknown */(Main/* : Main */.retainValue/* : unknown */)/* : unknown */.flatMap/* : unknown */(Iterators/* : Iterators */.fromOption/* : unknown */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
		let invokable : Invokable = new Invokable(newCaller/* : unknown */, arguments/* : unknown */, callerType/* : unknown */.returns/* : unknown */)/* : Invokable */;
		return new Some(new Tuple2Impl(argumentsState/* : unknown */, invokable/* : Invokable */)/* : Tuple2Impl */)/* : Some */;
	}
	/* private static */ retainValue(argument : Argument) : Option<Value> {
		if (argument/* : Argument */._variant/* : unknown */ === ArgumentVariant.Value/* : unknown */){
			return new Some(/* value */)/* : Some */;
		}
		return new None()/* : None */;
	}
	/* private static */ parseArgument(state : CompileState, element : string, depth : number) : [CompileState, Argument] {
		if (element/* : string */.isEmpty/* : unknown */()/* : unknown */){
			return new Tuple2Impl(state/* : CompileState */, new Whitespace()/* : Whitespace */)/* : Tuple2Impl */;
		}
		let tuple : [CompileState, Value] = parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state/* : CompileState */, element/* : string */, depth/* : number */)/* : [CompileState, Value] */;
		return new Tuple2Impl(tuple/* : [CompileState, Value] */[0/* : number */]()/* : unknown */, tuple/* : [CompileState, Value] */[1/* : number */]()/* : unknown */)/* : Tuple2Impl */;
	}
	/* private static */ findCallerType(newCaller : Caller) : FunctionType {
		let callerType : FunctionType = new FunctionType(Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, Primitive/* : Primitive */.Unknown/* : unknown */)/* : FunctionType */;
		/* switch (newCaller) */{
			/* case ConstructionCaller constructionCaller -> */{
				callerType/* : FunctionType */ = /* constructionCaller */.toFunction/* : unknown */()/* : unknown */;
			}
			/* case Value value -> */{
				let type = /* value */.type/* : unknown */()/* : unknown */;
				if (type/* : unknown */._variant/* : unknown */ === Variant.FunctionType/* : unknown */){
					callerType/* : FunctionType */ = /* functionType */;
				}
			}
		}
		return callerType/* : FunctionType */;
	}
	/* private static */ modifyCaller(state : CompileState, oldCaller : Caller) : Caller {
		if (oldCaller/* : Caller */._variant/* : unknown */ === CallerVariant.DataAccess/* : unknown */){
			let type = /* resolveType */(/* access */.parent/* : unknown */, state/* : CompileState */)/* : unknown */;
			if (/* type instanceof FunctionType */){
				return /* access */.parent/* : unknown */;
			}
		}
		return oldCaller/* : Caller */;
	}
	/* private static */ resolveType(value : Value, state : CompileState) : Type {
		return value/* : Value */.type/* : () => Type */()/* : Type */;
	}
	/* private static */ invocationHeader(state : CompileState, depth : number, callerString1 : string) : [CompileState, Caller] {
		if (callerString1/* : string */.startsWith/* : unknown */("new ")/* : unknown */){
			let input1 : string = callerString1/* : string */.substring/* : unknown */("new ".length/* : unknown */()/* : unknown */)/* : unknown */;
			let map = /* parseType */(state/* : CompileState */, input1/* : string */)/* : unknown */.map/* : unknown */((type) => {
				let right = type/* : unknown */.right/* : unknown */()/* : unknown */;
				return new Tuple2Impl<CompileState, Caller>(type/* : unknown */.left/* : unknown */()/* : unknown */, new ConstructionCaller(right/* : unknown */)/* : ConstructionCaller */)/* : Tuple2Impl<CompileState, Caller> */;
			})/* : unknown */;
			if (map/* : unknown */.isPresent/* : unknown */()/* : unknown */){
				return map/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */;
			}
		}
		let tuple : [CompileState, Value] = parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state/* : CompileState */, callerString1/* : string */, depth/* : number */)/* : [CompileState, Value] */;
		return new Tuple2Impl(tuple/* : [CompileState, Value] */[0/* : number */]()/* : unknown */, tuple/* : [CompileState, Value] */[1/* : number */]()/* : unknown */)/* : Tuple2Impl */;
	}
	/* private static */ foldInvocationStart(state : DivideState, c : string) : DivideState {
		let appended : DivideState = state/* : DivideState */.append/* : (arg0 : string) => DivideState */(c/* : string */)/* : DivideState */;
		if (c/* : string */ === /*  '(' */){
			let enter = appended/* : DivideState */.enter/* : unknown */()/* : unknown */;
			if (enter/* : unknown */.isShallow/* : unknown */()/* : unknown */){
				return enter/* : unknown */.advance/* : unknown */()/* : unknown */;
			}
			return enter/* : unknown */;
		}
		if (c/* : string */ === /*  ')' */){
			return appended/* : DivideState */.exit/* : unknown */()/* : unknown */;
		}
		return appended/* : DivideState */;
	}
	/* private static */ parseDataAccess(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return /* last */(input/* : string */.strip/* : unknown */()/* : unknown */, ".", (parentString, rawProperty) => {
			let property = rawProperty/* : unknown */.strip/* : unknown */()/* : unknown */;
			if (!isSymbol/* : (arg0 : string) => boolean */(property/* : unknown */)/* : unknown */){
				return new None()/* : None */;
			}
			let tuple : [CompileState, Value] = parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state/* : CompileState */, parentString/* : unknown */, depth/* : number */)/* : [CompileState, Value] */;
			let parent = tuple/* : [CompileState, Value] */[1/* : number */]()/* : unknown */;
			let parentType = parent/* : unknown */.type/* : unknown */()/* : unknown */;
			if (/* parentType instanceof TupleType */){
				if (property/* : unknown */.equals/* : unknown */("left")/* : unknown */){
					return new Some(new Tuple2Impl(state/* : CompileState */, new IndexValue(parent/* : unknown */, new SymbolValue("0", Primitive/* : Primitive */.Int/* : unknown */)/* : SymbolValue */)/* : IndexValue */)/* : Tuple2Impl */)/* : Some */;
				}
				if (property/* : unknown */.equals/* : unknown */("right")/* : unknown */){
					return new Some(new Tuple2Impl(state/* : CompileState */, new IndexValue(parent/* : unknown */, new SymbolValue("1", Primitive/* : Primitive */.Int/* : unknown */)/* : SymbolValue */)/* : IndexValue */)/* : Tuple2Impl */)/* : Some */;
				}
			}
			let type : Type = Primitive/* : Primitive */.Unknown/* : unknown */;
			if (parentType/* : unknown */._variant/* : unknown */ === Variant.FindableType/* : unknown */){
				if (/* objectType.find(property) instanceof Some */(/* var memberType */)/* : unknown */){
					type/* : Type */ = /* memberType */;
				}
			}
			return new Some(new Tuple2Impl(tuple/* : [CompileState, Value] */[0/* : number */]()/* : unknown */, new DataAccess(parent/* : unknown */, property/* : unknown */, type/* : Type */)/* : DataAccess */)/* : Tuple2Impl */)/* : Some */;
		})/* : unknown */;
	}
	/* private static */ parseString(state : CompileState, input : string) : Option<[CompileState, Value]> {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.startsWith/* : unknown */("\"")/* : unknown */ && stripped/* : unknown */.endsWith/* : unknown */("\"")/* : unknown */){
			return new Some(new Tuple2Impl(state/* : CompileState */, new StringValue(stripped/* : unknown */)/* : StringValue */)/* : Tuple2Impl */)/* : Some */;
		}
		return new None()/* : None */;
	}
	/* private static */ parseSymbolValue(state : CompileState, value : string) : Option<[CompileState, Value]> {
		let stripped = value/* : string */.strip/* : unknown */()/* : unknown */;
		if (isSymbol/* : (arg0 : string) => boolean */(stripped/* : unknown */)/* : boolean */){
			if (/* state.resolveValue(stripped) instanceof Some */(/* var type */)/* : unknown */){
				return new Some(new Tuple2Impl(state/* : CompileState */, new SymbolValue(stripped/* : unknown */, /* type */)/* : SymbolValue */)/* : Tuple2Impl */)/* : Some */;
			}
			if (/* state.resolveType(stripped) instanceof Some */(/* var type */)/* : unknown */){
				return new Some(new Tuple2Impl(state/* : CompileState */, new SymbolValue(stripped/* : unknown */, /* type */)/* : SymbolValue */)/* : Tuple2Impl */)/* : Some */;
			}
			return new Some(new Tuple2Impl(state/* : CompileState */, new Placeholder(stripped/* : unknown */)/* : Placeholder */)/* : Tuple2Impl */)/* : Some */;
		}
		return new None()/* : None */;
	}
	/* private static */ parseOperation(state : CompileState, value : string, depth : number, operator : /* Operator */) : Option<[CompileState, Value]> {
		return /* first */(value/* : string */, operator/* : content-start Operator content-end */.sourceRepresentation/* : unknown */, (leftString, rightString) => {
			let leftTuple : [CompileState, Value] = parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state/* : CompileState */, leftString/* : unknown */, depth/* : number */)/* : [CompileState, Value] */;
			let rightTuple : [CompileState, Value] = parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(leftTuple/* : [CompileState, Value] */[0/* : number */]()/* : unknown */, rightString/* : unknown */, depth/* : number */)/* : [CompileState, Value] */;
			let left = leftTuple/* : [CompileState, Value] */[1/* : number */]()/* : unknown */;
			let right = rightTuple/* : [CompileState, Value] */[1/* : number */]()/* : unknown */;
			return new Some(new Tuple2Impl(rightTuple/* : [CompileState, Value] */[0/* : number */]()/* : unknown */, new Operation(left/* : unknown */, operator/* : content-start Operator content-end */, right/* : unknown */)/* : Operation */)/* : Tuple2Impl */)/* : Some */;
		})/* : unknown */;
	}
	/* private static  */ parseValuesOrEmpty<T>(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) : [CompileState, List<T>] {
		return /* parseValues */(state/* : CompileState */, input/* : string */, mapper/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]> */)/* : unknown */.orElseGet/* : unknown */(() => new Tuple2Impl(state/* : CompileState */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */)/* : Tuple2Impl */)/* : unknown */;
	}
	/* private static  */ parseValues<T>(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) : Option<[CompileState, List<T>]> {
		return /* parseValuesWithIndices */(state/* : CompileState */, input/* : string */, (state1, tuple) => mapper/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]> */(state1/* : unknown */, tuple/* : unknown */.right/* : unknown */()/* : unknown */)/* : Option<[CompileState, T]> */)/* : unknown */;
	}
	/* private static  */ parseValuesWithIndices<T>(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]>) : Option<[CompileState, List<T>]> {
		return parseAllWithIndices/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : DivideState, arg1 : string) => DivideState, arg3 : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]>) => Option<[CompileState, List<T>]> */(state/* : CompileState */, input/* : string */, Main/* : Main */.foldValueChar/* : unknown */, mapper/* : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]> */)/* : Option<[CompileState, List<T>]> */;
	}
	/* private static */ parseParameter(state : CompileState, input : string) : [CompileState, Parameter] {
		if (input/* : string */.isBlank/* : unknown */()/* : unknown */){
			return new Tuple2Impl(state/* : CompileState */, new Whitespace()/* : Whitespace */)/* : Tuple2Impl */;
		}
		return /* parseDefinition */(state/* : CompileState */, input/* : string */)/* : unknown */.map/* : unknown */((tuple) => new Tuple2Impl<CompileState, Parameter>(tuple/* : unknown */.left/* : unknown */()/* : unknown */, tuple/* : unknown */.right/* : unknown */()/* : unknown */)/* : Tuple2Impl<CompileState, Parameter> */)/* : unknown */.orElseGet/* : unknown */(() => new Tuple2Impl(state/* : CompileState */, new Placeholder(input/* : string */)/* : Placeholder */)/* : Tuple2Impl */)/* : unknown */;
	}
	/* private static */ createIndent(depth : number) : string {
		return "\n" + "\t".repeat/* : unknown */(depth/* : number */)/* : unknown */;
	}
	/* private static */ parseDefinitionStatement(input : string, depth : number, state : CompileState) : Option<[CompileState, IncompleteClassSegmentWrapper]> {
		return suffix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(input/* : string */.strip/* : unknown */()/* : unknown */, ";", (withoutEnd : string) => {
			return /* parseDefinition */(state/* : CompileState */, withoutEnd/* : string */)/* : unknown */.map/* : unknown */((result) => {
				let definition = result/* : unknown */.right/* : unknown */()/* : unknown */;
				return new Tuple2Impl(result/* : unknown */.left/* : unknown */()/* : unknown */, new IncompleteClassSegmentWrapper(new DefinitionStatement(depth/* : number */, definition/* : unknown */)/* : DefinitionStatement */)/* : IncompleteClassSegmentWrapper */)/* : Tuple2Impl */;
			})/* : unknown */;
		})/* : Option<T> */;
	}
	/* private static */ parseDefinition(state : CompileState, input : string) : Option<[CompileState, Definition]> {
		return /* last */(input/* : string */.strip/* : unknown */()/* : unknown */, " ", (beforeName, name) => {
			return /* split */(() => /* toLast */(beforeName/* : unknown */, " ", Main/* : Main */.foldTypeSeparator/* : unknown */)/* : unknown */, (beforeType, type) => {
				return suffix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(beforeType/* : unknown */.strip/* : unknown */()/* : unknown */, ">", (withoutTypeParamStart : string) => {
					return /* first */(withoutTypeParamStart/* : string */, "<", (beforeTypeParams, typeParamsString) => {
						let typeParams : [CompileState, List<T>] = parseValuesOrEmpty/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) => [CompileState, List<T>] */(state/* : CompileState */, typeParamsString/* : unknown */, (state1, s) => new Some(new Tuple2Impl(state1/* : unknown */, s/* : unknown */.strip/* : unknown */()/* : unknown */)/* : Tuple2Impl */)/* : Some */)/* : [CompileState, List<T>] */;
						return /* assembleDefinition */(typeParams/* : [CompileState, List<T>] */[0/* : number */]()/* : unknown */, new Some<string>(beforeTypeParams/* : unknown */)/* : Some<string> */, name/* : unknown */, typeParams/* : [CompileState, List<T>] */[1/* : number */]()/* : unknown */, type/* : unknown */)/* : unknown */;
					})/* : unknown */;
				})/* : Option<T> */.or/* : (arg0 : () => Option<T>) => Option<T> */(() => {
					return /* assembleDefinition */(state/* : CompileState */, new Some<string>(beforeType/* : unknown */)/* : Some<string> */, name/* : unknown */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, type/* : unknown */)/* : unknown */;
				})/* : Option<T> */;
			})/* : unknown */.or/* : unknown */(() => {
				return /* assembleDefinition */(state/* : CompileState */, new None<string>()/* : None<string> */, name/* : unknown */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, beforeName/* : unknown */)/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ toLast(input : string, separator : string, folder : (arg0 : DivideState, arg1 : string) => DivideState) : Option<[string, string]> {
		let divisions : List<string> = divideAll/* : (arg0 : string, arg1 : (arg0 : DivideState, arg1 : string) => DivideState) => List<string> */(input/* : string */, folder/* : (arg0 : DivideState, arg1 : string) => DivideState */)/* : List<string> */;
		return divisions/* : List<string> */.removeLast/* : () => Option<[List<T>, T]> */()/* : Option<[List<T>, T]> */.map/* : (arg0 : (arg0 : [List<T>, T]) => R) => Option<R> */((removed : [List<T>, T]) => {
			let left = removed/* : [List<T>, T] */[0/* : number */]()/* : unknown */.iterate/* : unknown */()/* : unknown */.collect/* : unknown */(new Joiner(separator/* : string */)/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
			let right = removed/* : [List<T>, T] */[1/* : number */]()/* : unknown */;
			return new Tuple2Impl(left/* : unknown */, right/* : unknown */)/* : Tuple2Impl */;
		})/* : Option<R> */;
	}
	/* private static */ foldTypeSeparator(state : DivideState, c : string) : DivideState {
		if (c/* : string */ === /*  ' '  */ && state/* : DivideState */.isLevel/* : unknown */()/* : unknown */){
			return state/* : DivideState */.advance/* : () => DivideState */()/* : DivideState */;
		}
		let appended : DivideState = state/* : DivideState */.append/* : (arg0 : string) => DivideState */(c/* : string */)/* : DivideState */;
		if (c/* : string */ === /*  '<' */){
			return appended/* : DivideState */.enter/* : unknown */()/* : unknown */;
		}
		if (c/* : string */ === /*  '>' */){
			return appended/* : DivideState */.exit/* : unknown */()/* : unknown */;
		}
		return appended/* : DivideState */;
	}
	/* private static */ assembleDefinition(state : CompileState, beforeTypeParams : Option<string>, name : string, typeParams : List<string>, type : string) : Option<[CompileState, Definition]> {
		return /* parseType */(state/* : CompileState */.withTypeParams/* : (arg0 : List<string>) => CompileState */(typeParams/* : List<string> */)/* : CompileState */, type/* : string */)/* : unknown */.map/* : unknown */((type1) => {
			let node : ImmutableDefinition = new ImmutableDefinition(beforeTypeParams/* : Option<string> */, name/* : string */.strip/* : unknown */()/* : unknown */, type1/* : unknown */.right/* : unknown */()/* : unknown */, typeParams/* : List<string> */)/* : ImmutableDefinition */;
			return new Tuple2Impl(type1/* : unknown */.left/* : unknown */()/* : unknown */, node/* : ImmutableDefinition */)/* : Tuple2Impl */;
		})/* : unknown */;
	}
	/* private static */ foldValueChar(state : DivideState, c : string) : DivideState {
		if (c/* : string */ === /*  ','  */ && state/* : DivideState */.isLevel/* : unknown */()/* : unknown */){
			return state/* : DivideState */.advance/* : () => DivideState */()/* : DivideState */;
		}
		let appended : DivideState = state/* : DivideState */.append/* : (arg0 : string) => DivideState */(c/* : string */)/* : DivideState */;
		if (c/* : string */ === /*  ' */ - /* ' */){
			let peeked = appended/* : DivideState */.peek/* : unknown */()/* : unknown */;
			if (peeked/* : unknown */ === /*  '>' */){
				return appended/* : DivideState */.popAndAppendToOption/* : unknown */()/* : unknown */.orElse/* : unknown */(appended/* : DivideState */)/* : unknown */;
			}
			else {
				return appended/* : DivideState */;
			}
		}
		if (c/* : string */ === /*  '<'  */ || c/* : string */ === /*  '('  */ || c/* : string */ === /*  '{' */){
			return appended/* : DivideState */.enter/* : unknown */()/* : unknown */;
		}
		if (c/* : string */ === /*  '>'  */ || c/* : string */ === /*  ')'  */ || c/* : string */ === /*  '}' */){
			return appended/* : DivideState */.exit/* : unknown */()/* : unknown */;
		}
		return appended/* : DivideState */;
	}
	/* private static */ parseType(state : CompileState, input : string) : Option<[CompileState, Type]> {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.equals/* : unknown */("int")/* : unknown */ || stripped/* : unknown */.equals/* : unknown */("Integer")/* : unknown */){
			return new Some(new Tuple2Impl(state/* : CompileState */, Primitive/* : Primitive */.Int/* : unknown */)/* : Tuple2Impl */)/* : Some */;
		}
		if (stripped/* : unknown */.equals/* : unknown */("String")/* : unknown */ || stripped/* : unknown */.equals/* : unknown */("char")/* : unknown */ || stripped/* : unknown */.equals/* : unknown */("Character")/* : unknown */){
			return new Some(new Tuple2Impl(state/* : CompileState */, Primitive/* : Primitive */.String/* : unknown */)/* : Tuple2Impl */)/* : Some */;
		}
		if (stripped/* : unknown */.equals/* : unknown */("var")/* : unknown */){
			return new Some(new Tuple2Impl(state/* : CompileState */, Primitive/* : Primitive */.Unknown/* : unknown */)/* : Tuple2Impl */)/* : Some */;
		}
		if (stripped/* : unknown */.equals/* : unknown */("boolean")/* : unknown */){
			return new Some(new Tuple2Impl(state/* : CompileState */, Primitive/* : Primitive */.Boolean/* : unknown */)/* : Tuple2Impl */)/* : Some */;
		}
		if (isSymbol/* : (arg0 : string) => boolean */(stripped/* : unknown */)/* : boolean */){
			if (/* state.resolveType(stripped) instanceof Some */(/* var resolved */)/* : unknown */){
				return new Some(new Tuple2Impl(state/* : CompileState */, /* resolved */)/* : Tuple2Impl */)/* : Some */;
			}
			else {
				return new Some(new Tuple2Impl(state/* : CompileState */, new Placeholder(stripped/* : unknown */)/* : Placeholder */)/* : Tuple2Impl */)/* : Some */;
			}
		}
		return /* parseTemplate */(state/* : CompileState */, input/* : string */)/* : unknown */.or/* : unknown */(() => /* varArgs */(state/* : CompileState */, input/* : string */)/* : unknown */)/* : unknown */;
	}
	/* private static */ varArgs(state : CompileState, input : string) : Option<[CompileState, Type]> {
		return suffix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(input/* : string */, "...", (s : string) => {
			return parseType/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Type]> */(state/* : CompileState */, s/* : string */)/* : Option<[CompileState, Type]> */.map/* : (arg0 : (arg0 : [CompileState, Type]) => R) => Option<R> */((inner : [CompileState, Type]) => {
				let newState = inner/* : [CompileState, Type] */[0/* : number */]()/* : unknown */;
				let child = inner/* : [CompileState, Type] */[1/* : number */]()/* : unknown */;
				return new Tuple2Impl(newState/* : unknown */, new ArrayType(child/* : unknown */)/* : ArrayType */)/* : Tuple2Impl */;
			})/* : Option<R> */;
		})/* : Option<T> */;
	}
	/* private static */ assembleTemplate(base : string, state : CompileState, arguments : List<Argument>) : [CompileState, Type] {
		let children = arguments/* : List<Argument> */.iterate/* : () => Iterator<T> */()/* : Iterator<T> */.map/* : (arg0 : (arg0 : T) => R) => Iterator<R> */(Main/* : Main */.retainType/* : unknown */)/* : Iterator<R> */.flatMap/* : unknown */(Iterators/* : Iterators */.fromOption/* : unknown */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
		if (base/* : string */.equals/* : unknown */("BiFunction")/* : unknown */){
			return new Tuple2Impl(state/* : CompileState */, new FunctionType(Lists/* : Lists */.of/* : (arg0 : T[]) => List<T> */(children/* : unknown */.get/* : unknown */(0/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */, children/* : unknown */.get/* : unknown */(1/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : List<T> */, children/* : unknown */.get/* : unknown */(2/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : FunctionType */)/* : Tuple2Impl */;
		}
		if (base/* : string */.equals/* : unknown */("Function")/* : unknown */){
			return new Tuple2Impl(state/* : CompileState */, new FunctionType(Lists/* : Lists */.of/* : (arg0 : T[]) => List<T> */(children/* : unknown */.get/* : unknown */(0/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : List<T> */, children/* : unknown */.get/* : unknown */(1/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : FunctionType */)/* : Tuple2Impl */;
		}
		if (base/* : string */.equals/* : unknown */("Predicate")/* : unknown */){
			return new Tuple2Impl(state/* : CompileState */, new FunctionType(Lists/* : Lists */.of/* : (arg0 : T[]) => List<T> */(children/* : unknown */.get/* : unknown */(0/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : List<T> */, Primitive/* : Primitive */.Boolean/* : unknown */)/* : FunctionType */)/* : Tuple2Impl */;
		}
		if (base/* : string */.equals/* : unknown */("Supplier")/* : unknown */){
			return new Tuple2Impl(state/* : CompileState */, new FunctionType(Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, children/* : unknown */.get/* : unknown */(0/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : FunctionType */)/* : Tuple2Impl */;
		}
		if (base/* : string */.equals/* : unknown */("Tuple2")/* : unknown */ && children/* : unknown */.size/* : unknown */()/* : unknown */ >= 2/* : number */){
			return new Tuple2Impl(state/* : CompileState */, new TupleType(children/* : unknown */)/* : TupleType */)/* : Tuple2Impl */;
		}
		if (state/* : CompileState */.resolveType/* : (arg0 : string) => Option<Type> */(base/* : string */)/* : Option<Type> */._variant/* : unknown */ === OptionVariant.Some/* : unknown */){
			let baseType = /* some */.value/* : unknown */;
			if (baseType/* : unknown */._variant/* : unknown */ === Variant.FindableType/* : unknown */){
				return new Tuple2Impl(state/* : CompileState */, new Template(/* findableType */, children/* : unknown */)/* : Template */)/* : Tuple2Impl */;
			}
		}
		return new Tuple2Impl(state/* : CompileState */, new Template(new Placeholder(base/* : string */)/* : Placeholder */, children/* : unknown */)/* : Template */)/* : Tuple2Impl */;
	}
	/* private static */ parseTemplate(state : CompileState, input : string) : Option<[CompileState, Type]> {
		return suffix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(input/* : string */.strip/* : unknown */()/* : unknown */, ">", (withoutEnd : string) => {
			return /* first */(withoutEnd/* : string */, "<", (base, argumentsString) => {
				let strippedBase = base/* : unknown */.strip/* : unknown */()/* : unknown */;
				return parseValues/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) => Option<[CompileState, List<T>]> */(state/* : CompileState */, argumentsString/* : unknown */, Main/* : Main */.argument/* : unknown */)/* : Option<[CompileState, List<T>]> */.map/* : (arg0 : (arg0 : [CompileState, List<T>]) => R) => Option<R> */((argumentsTuple : [CompileState, List<T>]) => {
					return assembleTemplate/* : (arg0 : string, arg1 : CompileState, arg2 : List<Argument>) => [CompileState, Type] */(strippedBase/* : unknown */, argumentsTuple/* : [CompileState, List<T>] */[0/* : number */]()/* : unknown */, argumentsTuple/* : [CompileState, List<T>] */[1/* : number */]()/* : unknown */)/* : [CompileState, Type] */;
				})/* : Option<R> */;
			})/* : unknown */;
		})/* : Option<T> */;
	}
	/* private static */ retainType(argument : Argument) : Option<Type> {
		if (argument/* : Argument */._variant/* : unknown */ === ArgumentVariant.Type/* : unknown */){
			return new Some(/* type */)/* : Some */;
		}
		else {
			return new None<Type>()/* : None<Type> */;
		}
	}
	/* private static */ argument(state : CompileState, input : string) : Option<[CompileState, Argument]> {
		if (input/* : string */.isBlank/* : unknown */()/* : unknown */){
			return new Some(new Tuple2Impl(state/* : CompileState */, new Whitespace()/* : Whitespace */)/* : Tuple2Impl */)/* : Some */;
		}
		return parseType/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Type]> */(state/* : CompileState */, input/* : string */)/* : Option<[CompileState, Type]> */.map/* : (arg0 : (arg0 : [CompileState, Type]) => R) => Option<R> */((tuple : [CompileState, Type]) => new Tuple2Impl(tuple/* : [CompileState, Type] */[0/* : number */]()/* : unknown */, tuple/* : [CompileState, Type] */[1/* : number */]()/* : unknown */)/* : Tuple2Impl */)/* : Option<R> */;
	}
	/* private static  */ last<T>(input : string, infix : string, mapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return infix/* : string */(input/* : string */, infix/* : string */, Main/* : Main */.findLast/* : unknown */, mapper/* : (arg0 : string, arg1 : string) => Option<T> */)/* : unknown */;
	}
	/* private static */ findLast(input : string, infix : string) : Option<number> {
		let index = input/* : string */.lastIndexOf/* : unknown */(infix/* : string */)/* : unknown */;
		if (index/* : unknown */ === -1/* : number */){
			return new None<number>()/* : None<number> */;
		}
		return new Some(index/* : unknown */)/* : Some */;
	}
	/* private static  */ first<T>(input : string, infix : string, mapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return infix/* : string */(input/* : string */, infix/* : string */, Main/* : Main */.findFirst/* : unknown */, mapper/* : (arg0 : string, arg1 : string) => Option<T> */)/* : unknown */;
	}
	/* private static  */ split<T>(splitter : () => Option<[string, string]>, splitMapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return splitter/* : () => Option<[string, string]> */()/* : Option<[string, string]> */.flatMap/* : (arg0 : (arg0 : [string, string]) => Option<R>) => Option<R> */((splitTuple : [string, string]) => splitMapper/* : (arg0 : string, arg1 : string) => Option<T> */(splitTuple/* : [string, string] */[0/* : number */]()/* : unknown */, splitTuple/* : [string, string] */[1/* : number */]()/* : unknown */)/* : Option<T> */)/* : Option<R> */;
	}
	/* private static  */ infix<T>(input : string, infix : string, locator : (arg0 : string, arg1 : string) => Option<number>, mapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return split/* : (arg0 : () => Option<[string, string]>, arg1 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(() => locator/* : (arg0 : string, arg1 : string) => Option<number> */(input/* : string */, infix/* : string */)/* : Option<number> */.map/* : (arg0 : (arg0 : number) => R) => Option<R> */((index : number) => {
			let left = input/* : string */.substring/* : unknown */(0/* : number */, index/* : number */)/* : unknown */;
			let right = input/* : string */.substring/* : unknown */(index/* : number */ + infix/* : string */.length/* : unknown */()/* : unknown */)/* : unknown */;
			return new Tuple2Impl(left/* : unknown */, right/* : unknown */)/* : Tuple2Impl */;
		})/* : Option<R> */, mapper/* : (arg0 : string, arg1 : string) => Option<T> */)/* : Option<T> */;
	}
	/* private static */ findFirst(input : string, infix : string) : Option<number> {
		let index = input/* : string */.indexOf/* : unknown */(infix/* : string */)/* : unknown */;
		if (index/* : unknown */ === -1/* : number */){
			return new None<number>()/* : None<number> */;
		}
		return new Some(index/* : unknown */)/* : Some */;
	}
	/* private static */ generatePlaceholder(input : string) : string {
		let replaced = input/* : string */.replace/* : unknown */("/*", "content-start")/* : unknown */.replace/* : unknown */("*/", "content-end")/* : unknown */;
		return "/* " + replaced + " */";
	}
	/* private static */ createDebugString(type : Type) : string {
		if (!Main/* : Main */.isDebug/* : unknown */){
			return "";
		}
		return generatePlaceholder/* : (arg0 : string) => string */(": " + type/* : Type */.generate/* : unknown */()/* : unknown */)/* : string */;
	}
}
/*  */