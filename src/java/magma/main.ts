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
		return new Some(mapper/* : (arg0 : T) => R */(this/* : this */.value/* : unknown */)/* : R */)/* : Some */;
	}
	/* @Override
        public */ isPresent() : boolean {
		return true;
	}
	/* @Override
        public */ orElse(other : T) : T {
		return this/* : this */.value/* : unknown */;
	}
	/* @Override
        public */ filter(predicate : (arg0 : T) => boolean) : Option<T> {
		if (predicate/* : (arg0 : T) => boolean */(this/* : this */.value/* : unknown */)/* : boolean */){
			return this/* : this */;
		}
		return new None()/* : None */;
	}
	/* @Override
        public */ orElseGet(supplier : () => T) : T {
		return this/* : this */.value/* : unknown */;
	}
	/* @Override
        public */ or(other : () => Option<T>) : Option<T> {
		return this/* : this */;
	}
	/* @Override
        public  */ flatMap<R>(mapper : (arg0 : T) => Option<R>) : Option<R> {
		return mapper/* : (arg0 : T) => R */(this/* : this */.value/* : unknown */)/* : R */;
	}
	/* @Override
        public */ isEmpty() : boolean {
		return false;
	}
	/* @Override
        public  */ and<R>(other : () => Option<R>) : Option<[T, R]> {
		return other/* : T */.get/* : unknown */()/* : unknown */.map/* : unknown */((otherValue) => new Tuple2Impl(this/* : this */.value/* : unknown */, otherValue/* : unknown */)/* : Tuple2Impl */)/* : unknown */;
	}
}
/* private static */class SingleHead<T>/*  */ {
	/* private final */ value : T;
	/* private */ retrieved : boolean;
	constructor (value : T) {
		this/* : this */.value/* : T */ = value/* : T */;
		this/* : this */.retrieved/* : unknown */ = false;
	}
	/* @Override
        public */ next() : Option<T> {
		if (this/* : this */.retrieved/* : unknown */){
			return new None()/* : None */;
		}
		this/* : this */.retrieved/* : unknown */ = true;
		return new Some(this/* : this */.value/* : T */)/* : Some */;
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
			let option = this/* : this */.head/* : unknown */.next/* : unknown */()/* : unknown */.map/* : unknown */((inner) => folder/* : (arg0 : R, arg1 : T) => R */(finalCurrent/* : R */, inner/* : unknown */)/* : R */)/* : unknown */;
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
		return new HeadedIterator(() => this/* : this */.head/* : unknown */.next/* : unknown */()/* : unknown */.map/* : unknown */(mapper/* : (arg0 : T) => R */)/* : unknown */)/* : HeadedIterator */;
	}
	/* @Override
        public  */ collect<R>(collector : Collector<T, R>) : R {
		return this/* : this */.fold/* : (arg0 : C, arg1 : T) => C */(collector/* : Collector<T, R> */.createInitial/* : () => C */()/* : C */, collector/* : Collector<T, R> */.fold/* : unknown */)/* : C */;
	}
	/* @Override
        public */ filter(predicate : (arg0 : T) => boolean) : Iterator<T> {
		return this/* : this */.flatMap/* : (arg0 : (arg0 : T) => Option<R>) => Option<R> */((element : T) => {
			if (predicate/* : (arg0 : T) => boolean */(element/* : T */)/* : boolean */){
				return new HeadedIterator(new SingleHead(element/* : T */)/* : SingleHead */)/* : HeadedIterator */;
			}
			return new HeadedIterator(new EmptyHead()/* : EmptyHead */)/* : HeadedIterator */;
		})/* : Option<R> */;
	}
	/* @Override
        public */ next() : Option<T> {
		return this/* : this */.head/* : unknown */.next/* : unknown */()/* : unknown */;
	}
	/* @Override
        public  */ flatMap<R>(f : (arg0 : T) => Iterator<R>) : Iterator<R> {
		return new HeadedIterator(new /* FlatMapHead */(this/* : this */.head/* : unknown */, f/* : (arg0 : T) => Iterator<R> */)/* : content-start FlatMapHead content-end */)/* : HeadedIterator */;
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
		this/* : this */.length/* : number */ = length/* : number */;
	}
	/* @Override
        public */ next() : Option<number> {
		if (/* this.counter < this */.length/* : unknown */){
			let value = this/* : this */.counter/* : unknown */;
			/* this.counter++ */;
			return new Some(value/* : T */)/* : Some */;
		}
		return new None()/* : None */;
	}
}
/* private static final */class JVMList<T>/*  */ {
	/* private final */ elements : /* java.util.List */<T>;
	JVMList(elements : /* java.util.List */<T>) : /* private */ {
		this/* : this */.elements/* : content-start java.util.List content-end<T> */ = elements/* : content-start java.util.List content-end<T> */;
	}
	JVMList() : /* public */ {
		/* this(new ArrayList<>()) */;
	}
	/* @Override
            public */ addLast(element : T) : List<T> {
		/* this.elements.add(element) */;
		return this/* : this */;
	}
	/* @Override
            public */ iterate() : Iterator<T> {
		return this/* : this */.iterateWithIndices/* : () => Iterator<[number, T]> */()/* : Iterator<[number, T]> */.map/* : (arg0 : (arg0 : [number, T]) => R) => Option<R> */(Tuple2/* : Tuple2 */.right/* : unknown */)/* : Option<R> */;
	}
	/* @Override
            public */ removeLast() : Option<[List<T>, T]> {
		if (this/* : this */.elements/* : content-start java.util.List content-end<T> */.isEmpty/* : unknown */()/* : unknown */){
			return new None()/* : None */;
		}
		let slice = this/* : this */.elements/* : content-start java.util.List content-end<T> */.subList/* : unknown */(0/* : number */, this/* : this */.elements/* : content-start java.util.List content-end<T> */.size/* : unknown */()/* : unknown */ - 1/* : number */)/* : unknown */;
		let last = this/* : this */.elements/* : content-start java.util.List content-end<T> */.getLast/* : unknown */()/* : unknown */;
		return new Some(new Tuple2Impl<List<T>, T>(new JVMList(slice/* : unknown */)/* : JVMList */, last/* : () => Option<T> */)/* : Tuple2Impl<List<T>, T> */)/* : Some */;
	}
	/* @Override
            public */ size() : number {
		return this/* : this */.elements/* : content-start java.util.List content-end<T> */.size/* : unknown */()/* : unknown */;
	}
	/* @Override
            public */ isEmpty() : boolean {
		return this/* : this */.elements/* : content-start java.util.List content-end<T> */.isEmpty/* : unknown */()/* : unknown */;
	}
	/* @Override
            public */ addFirst(element : T) : List<T> {
		/* this.elements.addFirst(element) */;
		return this/* : this */;
	}
	/* @Override
            public */ iterateWithIndices() : Iterator<[number, T]> {
		return new HeadedIterator(new RangeHead(this/* : this */.elements/* : content-start java.util.List content-end<T> */.size/* : unknown */()/* : unknown */)/* : RangeHead */)/* : HeadedIterator */.map/* : (arg0 : (arg0 : T) => R) => Option<R> */((index : T) => new Tuple2Impl(index/* : T */, this/* : this */.elements/* : content-start java.util.List content-end<T> */.get/* : unknown */(index/* : T */)/* : unknown */)/* : Tuple2Impl */)/* : Option<R> */;
	}
	/* @Override
            public */ removeFirst() : Option<[T, List<T>]> {
		if (this/* : this */.elements/* : content-start java.util.List content-end<T> */.isEmpty/* : unknown */()/* : unknown */){
			return new None()/* : None */;
		}
		let first = this/* : this */.elements/* : content-start java.util.List content-end<T> */.getFirst/* : unknown */()/* : unknown */;
		let slice = this/* : this */.elements/* : content-start java.util.List content-end<T> */.subList/* : unknown */(1/* : number */, this/* : this */.elements/* : content-start java.util.List content-end<T> */.size/* : unknown */()/* : unknown */)/* : unknown */;
		return new Some(new Tuple2Impl<T, List<T>>(first/* : unknown */, new JVMList(slice/* : unknown */)/* : JVMList */)/* : Tuple2Impl<T, List<T>> */)/* : Some */;
	}
	/* @Override
            public */ addAllLast(others : List<T>) : List<T> {
		let initial : List<T> = this/* : this */;
		return others/* : List<T> */.iterate/* : () => Iterator<T> */()/* : Iterator<T> */.fold/* : (arg0 : C, arg1 : T) => C */(initial/* : R */, List/* : List */.addLast/* : unknown */)/* : C */;
	}
	/* @Override
            public */ last() : Option<T> {
		if (this/* : this */.elements/* : content-start java.util.List content-end<T> */.isEmpty/* : unknown */()/* : unknown */){
			return new None()/* : None */;
		}
		return new Some(this/* : this */.elements/* : content-start java.util.List content-end<T> */.getLast/* : unknown */()/* : unknown */)/* : Some */;
	}
	/* @Override
            public */ get(index : number) : Option<T> {
		if (/* index < this */.elements/* : unknown */.size/* : unknown */()/* : unknown */){
			return new Some(this/* : this */.elements/* : content-start java.util.List content-end<T> */.get/* : unknown */(index/* : T */)/* : unknown */)/* : Some */;
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
		return this/* : this */.generateWithParams/* : (arg0 : string) => string */("")/* : string */;
	}
	/* @Override
        public */ generateType() : string {
		if (this/* : this */.type/* : () => Type */(/* Primitive */.Unknown/* : unknown */)/* : Type */){
			return "";
		}
		return " : " + this/* : this */.type/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
	/* @Override
        public */ joinBefore() : string {
		return !/* isDebug ? "" : this */.maybeBefore/* : unknown */.filter/* : unknown */((value) => !value/* : T */.isEmpty/* : unknown */()/* : unknown */)/* : unknown */.map/* : unknown */(/* Main */.generatePlaceholder/* : unknown */)/* : unknown */.map/* : unknown */((inner) => inner/* : unknown */ + " ")/* : unknown */.orElse/* : unknown */("")/* : unknown */;
	}
	/* @Override
        public */ joinTypeParams() : string {
		return this/* : this */.typeParams/* : () => List<string> */()/* : List<string> */.collect/* : (arg0 : Collector<T, R>) => R */(new /* Joiner */()/* : content-start Joiner content-end */)/* : R */.map/* : unknown */((inner) => "<" + inner + ">")/* : unknown */.orElse/* : unknown */("")/* : unknown */;
	}
	/* @Override
        public */ mapType(mapper : (arg0 : Type) => Type) : Definition {
		return new ImmutableDefinition(this/* : this */.maybeBefore/* : () => Option<string> */, this/* : this */.name/* : () => string */, mapper/* : (arg0 : T) => R */(this/* : this */.type/* : () => Type */)/* : R */, this/* : this */.typeParams/* : () => List<string> */)/* : ImmutableDefinition */;
	}
	/* @Override
        public */ toString() : string {
		return "Definition[" + "maybeBefore=" + this/* : this */.maybeBefore/* : () => Option<string> */ + ", " + "name=" + this/* : this */.name/* : () => string */ + ", " + "type=" + this/* : this */.type/* : () => Type */ + ", " + "typeParams=" + this/* : this */.typeParams/* : () => List<string> */ + /*  ']' */;
	}
	/* @Override
        public */ generateWithParams(joinedParameters : string) : string {
		let joined : string = this/* : this */.joinTypeParams/* : () => string */()/* : string */;
		let before : string = this/* : this */.joinBefore/* : () => string */()/* : string */;
		let typeString : string = this/* : this */.generateType/* : () => string */()/* : string */;
		return before/* : string */ + this/* : this */.name/* : () => string */ + joined/* : string */ + joinedParameters/* : string */ + typeString/* : string */;
	}
	/* @Override
        public */ createDefinition(paramTypes : List<Type>) : Definition {
		return ImmutableDefinition/* : ImmutableDefinition */.createSimpleDefinition/* : (arg0 : string, arg1 : Type) => Definition */(this/* : this */.name/* : () => string */, new /* FunctionType */(paramTypes/* : List<Type> */, this/* : this */.type/* : () => Type */)/* : content-start FunctionType content-end */)/* : Definition */;
	}
}
/* private */class ObjectType/*  */ {
	constructor (name : string, typeParams : List<string>, definitions : List<Definition>) {
	}
	/* @Override
        public */ generate() : string {
		return this/* : this */.name/* : () => string */;
	}
	/* @Override
        public */ replace(mapping : Map<string, Type>) : Type {
		return new ObjectType(this/* : this */.name/* : () => string */, this/* : this */.typeParams/* : () => List<string> */, this/* : this */.definitions/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */((definition) => definition/* : unknown */.mapType/* : unknown */((type) => type/* : () => Type */(mapping/* : Map<string, Type> */)/* : Type */)/* : unknown */)/* : unknown */.collect/* : unknown */(new /* ListCollector */()/* : content-start ListCollector content-end */)/* : unknown */)/* : ObjectType */;
	}
	/* @Override
        public */ find(name : string) : Option<Type> {
		return this/* : this */.definitions/* : unknown */.iterate/* : unknown */()/* : unknown */.filter/* : unknown */((definition) => definition/* : unknown */.name/* : unknown */()/* : unknown */.equals/* : unknown */(name/* : () => string */)/* : unknown */)/* : unknown */.map/* : unknown */(Definition/* : Definition */.type/* : unknown */)/* : unknown */.next/* : unknown */()/* : unknown */;
	}
	/* @Override
        public */ findName() : Option<string> {
		return new Some(this/* : this */.name/* : () => string */)/* : Some */;
	}
}
/* private */class TypeParam/*  */ {
	constructor (value : string) {
	}
	/* @Override
        public */ generate() : string {
		return this/* : this */.value/* : T */;
	}
	/* @Override
        public */ replace(mapping : Map<string, Type>) : Type {
		return mapping/* : Map<string, Type> */.find/* : (arg0 : string) => Option<V> */(this/* : this */.value/* : T */)/* : Option<V> */.orElse/* : (arg0 : V) => T */(this/* : this */)/* : T */;
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
		if (name/* : () => string */("this")/* : string */){
			return new Some(new ObjectType(name/* : () => string */, this/* : this */.typeParams/* : () => List<string> */, this/* : this */.definitions/* : unknown */)/* : ObjectType */)/* : Some */;
		}
		return this/* : this */.definitions/* : unknown */.iterate/* : unknown */()/* : unknown */.filter/* : unknown */((definition) => definition/* : unknown */.name/* : unknown */()/* : unknown */.equals/* : unknown */(name/* : () => string */)/* : unknown */)/* : unknown */.next/* : unknown */()/* : unknown */.map/* : unknown */(Definition/* : Definition */.type/* : unknown */)/* : unknown */;
	}
	/* public */ addStructure(structure : string) : CompileState {
		return new CompileState(this/* : this */.structures/* : unknown */.addLast/* : unknown */(structure/* : string */)/* : unknown */, this/* : this */.definitions/* : unknown */, this/* : this */.objectTypes/* : unknown */, this/* : this */.structNames/* : unknown */, this/* : this */.typeParams/* : () => List<string> */, this/* : this */.typeRegister/* : unknown */)/* : CompileState */;
	}
	/* public */ withDefinitions(definitions : List<Definition>) : CompileState {
		return new CompileState(this/* : this */.structures/* : unknown */, this/* : this */.definitions/* : List<Definition> */.addAllLast/* : (arg0 : List<T>) => List<T> */(definitions/* : List<Definition> */)/* : List<T> */, this/* : this */.objectTypes/* : unknown */, this/* : this */.structNames/* : unknown */, this/* : this */.typeParams/* : () => List<string> */, this/* : this */.typeRegister/* : unknown */)/* : CompileState */;
	}
	/* public */ resolveType(name : string) : Option<Type> {
		if (this/* : this */.structNames/* : unknown */.last/* : unknown */()/* : unknown */.filter/* : unknown */((inner) => inner/* : unknown */.equals/* : unknown */(name/* : () => string */)/* : unknown */)/* : unknown */.isPresent/* : unknown */()/* : unknown */){
			return new Some(new ObjectType(name/* : () => string */, this/* : this */.typeParams/* : () => List<string> */, this/* : this */.definitions/* : List<Definition> */)/* : ObjectType */)/* : Some */;
		}
		let maybeTypeParam : Option<T> = this/* : this */.typeParams/* : () => List<string> */()/* : List<string> */.filter/* : (arg0 : (arg0 : string) => boolean) => Option<T> */((param : string) => param/* : string */.equals/* : unknown */(name/* : () => string */)/* : unknown */)/* : Option<T> */.next/* : () => Option<T> */()/* : Option<T> */;
		if (/* maybeTypeParam instanceof Some */(/* var value */)/* : unknown */){
			return new Some(new TypeParam(value/* : T */)/* : TypeParam */)/* : Some */;
		}
		return this/* : this */.objectTypes/* : unknown */.iterate/* : unknown */()/* : unknown */.filter/* : unknown */((type) => type/* : () => Type */.name/* : unknown */.equals/* : unknown */(name/* : () => string */)/* : unknown */)/* : unknown */.next/* : unknown */()/* : unknown */.map/* : unknown */((type) => type/* : () => Type */)/* : unknown */;
	}
	/* public */ addType(type : ObjectType) : CompileState {
		return new CompileState(this/* : this */.structures/* : unknown */, this/* : this */.definitions/* : List<Definition> */, this/* : this */.objectTypes/* : unknown */.addLast/* : unknown */(type/* : () => Type */)/* : unknown */, this/* : this */.structNames/* : unknown */, this/* : this */.typeParams/* : () => List<string> */, this/* : this */.typeRegister/* : unknown */)/* : CompileState */;
	}
	/* public */ withDefinition(definition : Definition) : CompileState {
		return new CompileState(this/* : this */.structures/* : unknown */, this/* : this */.definitions/* : List<Definition> */.addLast/* : (arg0 : Definition) => List<T> */(definition/* : unknown */)/* : List<T> */, this/* : this */.objectTypes/* : unknown */, this/* : this */.structNames/* : unknown */, this/* : this */.typeParams/* : () => List<string> */, this/* : this */.typeRegister/* : unknown */)/* : CompileState */;
	}
	/* public */ pushStructName(name : string) : CompileState {
		return new CompileState(this/* : this */.structures/* : unknown */, this/* : this */.definitions/* : List<Definition> */, this/* : this */.objectTypes/* : unknown */, this/* : this */.structNames/* : unknown */.addLast/* : unknown */(name/* : () => string */)/* : unknown */, this/* : this */.typeParams/* : () => List<string> */, this/* : this */.typeRegister/* : unknown */)/* : CompileState */;
	}
	/* public */ withTypeParams(typeParams : List<string>) : CompileState {
		return new CompileState(this/* : this */.structures/* : unknown */, this/* : this */.definitions/* : List<Definition> */, this/* : this */.objectTypes/* : unknown */, this/* : this */.structNames/* : unknown */, this/* : this */.typeParams/* : () => List<string> */(typeParams/* : () => List<string> */)/* : List<string> */, this/* : this */.typeRegister/* : unknown */)/* : CompileState */;
	}
	/* public */ withExpectedType(type : Type) : CompileState {
		return new CompileState(this/* : this */.structures/* : unknown */, this/* : this */.definitions/* : List<Definition> */, this/* : this */.objectTypes/* : unknown */, this/* : this */.structNames/* : unknown */, this/* : this */.typeParams/* : () => List<string> */, new Some(type/* : () => Type */)/* : Some */)/* : CompileState */;
	}
	/* public */ popStructName() : CompileState {
		return new CompileState(this/* : this */.structures/* : unknown */, this/* : this */.definitions/* : List<Definition> */, this/* : this */.objectTypes/* : unknown */, this/* : this */.structNames/* : unknown */.removeLast/* : unknown */()/* : unknown */.map/* : unknown */(Tuple2/* : Tuple2 */.left/* : unknown */)/* : unknown */.orElse/* : unknown */(this/* : this */.structNames/* : unknown */)/* : unknown */, this/* : this */.typeParams/* : () => List<string> */, this/* : this */.typeRegister/* : unknown */)/* : CompileState */;
	}
}
/* private static */class DivideState/*  */ {
	/* private final */ input : string;
	/* private final */ index : number;
	/* private */ depth : number;
	/* private */ segments : List<string>;
	/* private */ buffer : string;
	DivideState(input : string, index : number, segments : List<string>, buffer : string, depth : number) : /* public */ {
		this/* : this */.segments/* : List<string> */ = segments/* : List<string> */;
		this/* : this */.buffer/* : string */ = buffer/* : string */;
		this/* : this */.depth/* : number */ = depth/* : number */;
		this/* : this */.input/* : string */ = input/* : string */;
		this/* : this */.index/* : T */ = index/* : T */;
	}
	DivideState(input : string) : /* public */ {
		/* this(input, 0, Lists.empty(), "", 0) */;
	}
	/* private */ advance() : DivideState {
		this/* : this */.segments/* : List<string> */ = this/* : this */.segments/* : List<string> */.addLast/* : (arg0 : string) => List<T> */(this/* : this */.buffer/* : string */)/* : List<T> */;
		this/* : this */.buffer/* : string */ = "";
		return this/* : this */;
	}
	/* private */ append(c : string) : DivideState {
		this/* : this */.buffer/* : string */ = this/* : this */.buffer/* : string */ + c/* : string */;
		return this/* : this */;
	}
	/* public */ enter() : DivideState {
		/* this.depth++ */;
		return this/* : this */;
	}
	/* public */ isLevel() : boolean {
		return this/* : this */.depth/* : number */ === 0/* : number */;
	}
	/* public */ exit() : DivideState {
		/* this.depth-- */;
		return this/* : this */;
	}
	/* public */ isShallow() : boolean {
		return this/* : this */.depth/* : number */ === 1/* : number */;
	}
	/* public */ pop() : Option<[string, DivideState]> {
		if (/* this.index < this */.input/* : unknown */.length/* : unknown */()/* : unknown */){
			let c = this/* : this */.input/* : string */.charAt/* : unknown */(this/* : this */.index/* : T */)/* : unknown */;
			return new Some(new Tuple2Impl(c/* : string */, new DivideState(this/* : this */.input/* : string */, this/* : this */.index/* : T */ + 1/* : number */, this/* : this */.segments/* : List<string> */, this/* : this */.buffer/* : string */, this/* : this */.depth/* : number */)/* : DivideState */)/* : Tuple2Impl */)/* : Some */;
		}
		return new None()/* : None */;
	}
	/* public */ popAndAppendToTuple() : Option<[string, DivideState]> {
		return this/* : this */.pop/* : () => Option<[string, DivideState]> */()/* : Option<[string, DivideState]> */.map/* : (arg0 : (arg0 : [string, DivideState]) => R) => Option<R> */((tuple : [string, DivideState]) => {
			let c = tuple/* : [string, DivideState] */[0/* : number */]()/* : unknown */;
			let right = tuple/* : [string, DivideState] */[1/* : number */]()/* : unknown */;
			return new Tuple2Impl(c/* : string */, right/* : () => B */(c/* : string */)/* : B */)/* : Tuple2Impl */;
		})/* : Option<R> */;
	}
	/* public */ popAndAppendToOption() : Option<DivideState> {
		return this/* : this */.popAndAppendToTuple/* : () => Option<[string, DivideState]> */()/* : Option<[string, DivideState]> */.map/* : (arg0 : (arg0 : [string, DivideState]) => R) => Option<R> */(Tuple2/* : Tuple2 */.right/* : unknown */)/* : Option<R> */;
	}
	/* public */ peek() : string {
		return this/* : this */.input/* : string */.charAt/* : unknown */(this/* : this */.index/* : T */)/* : unknown */;
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
		return new Some(current/* : R */.map/* : unknown */((inner) => inner/* : unknown */ + this/* : this */.delimiter/* : unknown */ + element/* : T */)/* : unknown */.orElse/* : unknown */(element/* : T */)/* : unknown */)/* : Some */;
	}
}
/* private static */class ListCollector<T>/*  */ {
	/* @Override
        public */ createInitial() : List<T> {
		return Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */;
	}
	/* @Override
        public */ fold(current : List<T>, element : T) : List<T> {
		return current/* : R */.addLast/* : unknown */(element/* : T */)/* : unknown */;
	}
}
/* private static */class FlatMapHead<T, R>/*  */ {
	/* private final */ mapper : (arg0 : T) => Iterator<R>;
	/* private final */ head : Head<T>;
	/* private */ current : Option<Iterator<R>>;
	FlatMapHead(head : Head<T>, mapper : (arg0 : T) => Iterator<R>) : /* public */ {
		this/* : this */.mapper/* : (arg0 : T) => R */ = mapper/* : (arg0 : T) => R */;
		this/* : this */.current/* : R */ = new None()/* : None */;
		this/* : this */.head/* : Head<T> */ = head/* : Head<T> */;
	}
	/* @Override
        public */ next() : Option<R> {
		while (true){
			if (this/* : this */.current/* : R */.isPresent/* : unknown */()/* : unknown */){
				let inner : Iterator<R> = this/* : this */.current/* : R */.orElse/* : unknown */(/* null */)/* : unknown */;
				let maybe : Option<R> = inner/* : unknown */.next/* : unknown */()/* : unknown */;
				if (maybe/* : Option<R> */.isPresent/* : () => boolean */()/* : boolean */){
					return maybe/* : Option<R> */;
				}
				else {
					this/* : this */.current/* : R */ = new None()/* : None */;
				}
			}
			let outer : Option<T> = this/* : this */.head/* : Head<T> */.next/* : () => Option<T> */()/* : Option<T> */;
			if (outer/* : Option<T> */.isPresent/* : () => boolean */()/* : boolean */){
				this/* : this */.current/* : R */ = outer/* : Option<T> */.map/* : (arg0 : (arg0 : T) => R) => Option<R> */(this/* : this */.mapper/* : (arg0 : T) => R */)/* : Option<R> */;
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
		return this/* : this */.right/* : () => B */()/* : B */.generate/* : unknown */()/* : unknown */ + "[]";
	}
	/* @Override
        public */ replace(mapping : Map<string, Type>) : Type {
		return this/* : this */;
	}
	/* @Override
        public */ findName() : Option<string> {
		return new None()/* : None */;
	}
}
/* private static */class Whitespace/*  */ {
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
		let joined = this/* : this */.arguments/* : unknown */()/* : unknown */.iterateWithIndices/* : unknown */()/* : unknown */.map/* : unknown */((pair) => "arg" + pair/* : unknown */.left/* : unknown */()/* : unknown */ + " : " + pair/* : unknown */.right/* : unknown */()/* : unknown */.generate/* : unknown */()/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return "(" + joined/* : string */ + ") => " + this/* : this */.returns/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
	/* @Override
        public */ replace(mapping : Map<string, Type>) : Type {
		return new FunctionType(this/* : this */.arguments/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */((type) => type/* : () => Type */(mapping/* : Map<string, Type> */)/* : Type */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */, this/* : this */.returns/* : unknown */)/* : FunctionType */;
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
		let joinedArguments = this/* : this */.arguments/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(Type/* : Type */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return "[" + joinedArguments + "]";
	}
	/* @Override
        public */ replace(mapping : Map<string, Type>) : Type {
		return this/* : this */;
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
		let joinedArguments = this/* : this */.arguments/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(Type/* : Type */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.map/* : unknown */((inner) => "<" + inner + ">")/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return this/* : this */.base/* : unknown */.generate/* : unknown */()/* : unknown */ + joinedArguments/* : unknown */;
	}
	/* @Override
        public */ typeParams() : List<string> {
		return this/* : this */.base/* : unknown */.typeParams/* : unknown */()/* : unknown */;
	}
	/* @Override
        public */ find(name : string) : Option<Type> {
		return this/* : this */.base/* : unknown */.find/* : unknown */(name/* : () => string */)/* : unknown */.map/* : unknown */((found) => {
			let mapping = this/* : this */.base/* : unknown */.typeParams/* : unknown */()/* : unknown */.iterate/* : unknown */()/* : unknown */.zip/* : unknown */(this/* : this */.arguments/* : unknown */.iterate/* : unknown */()/* : unknown */)/* : unknown */.collect/* : unknown */(new /* MapCollector */()/* : content-start MapCollector content-end */)/* : unknown */;
			return found/* : unknown */.replace/* : unknown */(mapping/* : Map<string, Type> */)/* : unknown */;
		})/* : unknown */;
	}
	/* @Override
        public */ name() : string {
		return this/* : this */.base/* : unknown */.name/* : unknown */()/* : unknown */;
	}
	/* @Override
        public */ replace(mapping : Map<string, Type>) : Type {
		return this/* : this */;
	}
	/* @Override
        public */ findName() : Option<string> {
		return this/* : this */.base/* : unknown */.findName/* : unknown */()/* : unknown */;
	}
}
/* private */class Placeholder/*  */ {
	constructor (input : string) {
	}
	/* @Override
        public */ generate() : string {
		return /* generatePlaceholder */(this/* : this */.input/* : string */)/* : unknown */;
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
		return this/* : this */.input/* : string */;
	}
	/* @Override
        public */ replace(mapping : Map<string, Type>) : Type {
		return this/* : this */;
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
		return this/* : this */.stripped/* : unknown */;
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
		return this/* : this */.parent/* : unknown */.generate/* : unknown */()/* : unknown */ + "." + this/* : this */.property/* : unknown */ + /* createDebugString */(this/* : this */.type/* : () => Type */)/* : unknown */;
	}
	/* @Override
        public */ type() : Type {
		return this/* : this */.type/* : () => Type */;
	}
}
/* private */class ConstructionCaller/*  */ {
	constructor (type : Type) {
	}
	/* @Override
        public */ generate() : string {
		return "new " + this/* : this */.type/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
	/* public */ toFunction() : FunctionType {
		return new FunctionType(Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, this/* : this */.type/* : () => Type */)/* : FunctionType */;
	}
}
/* private */class Operation/*  */ {
	constructor (left : Value, operator : /* Operator */, right : Value) {
	}
	/* @Override
        public */ generate() : string {
		return this/* : this */.left/* : () => A */()/* : A */.generate/* : unknown */()/* : unknown */ + " " + this/* : this */.operator/* : unknown */.targetRepresentation/* : unknown */ + " " + this/* : this */.right/* : unknown */()/* : unknown */.generate/* : unknown */()/* : unknown */;
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
		return "!" + this/* : this */.value/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
	/* @Override
        public */ type() : Type {
		return /* Primitive */.Unknown/* : unknown */;
	}
}
/* private */class BlockLambdaValue/*  */ {
	constructor (right : string, depth : number) {
	}
	/* @Override
        public */ generate() : string {
		return "{" + this.right() + createIndent(this.depth) + "}";
	}
}
/* private */class Lambda/*  */ {
	constructor (parameters : List<Definition>, body : LambdaValue) {
	}
	/* @Override
        public */ generate() : string {
		let joined = this/* : this */.parameters/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(Definition/* : Definition */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return "(" + joined/* : string */ + ") => " + this/* : this */.body/* : unknown */.generate/* : unknown */()/* : unknown */;
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
		let joined = this/* : this */.arguments/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(Value/* : Value */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return this/* : this */.caller/* : unknown */.generate/* : unknown */()/* : unknown */ + "(" + joined/* : string */ + ")" + /* createDebugString */(this/* : this */.type/* : () => Type */)/* : unknown */;
	}
}
/* private */class IndexValue/*  */ {
	constructor (parent : Value, child : Value) {
	}
	/* @Override
        public */ generate() : string {
		return this/* : this */.parent/* : unknown */.generate/* : unknown */()/* : unknown */ + "[" + this.child.generate() + "]";
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
		return this/* : this */.stripped/* : unknown */ + /* createDebugString */(this/* : this */.type/* : () => Type */)/* : unknown */;
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
		if (this/* : this */.map/* : (arg0 : (arg0 : T) => R) => Option<R> */(key/* : K */)/* : Option<R> */){
			return new Some(this/* : this */.map/* : (arg0 : (arg0 : T) => R) => Option<R> */(key/* : K */)/* : Option<R> */)/* : Some */;
		}
		return new None()/* : None */;
	}
	/* @Override
            public */ with(key : K, value : V) : Map<K, V> {
		/* this.map.put(key, value) */;
		return this/* : this */;
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
		return current/* : R */.with/* : unknown */(element/* : T */.left/* : unknown */()/* : unknown */, element/* : T */.right/* : unknown */()/* : unknown */)/* : unknown */;
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
/* private */class Primitive/*  */ {
	/* Int("number"),
        String("string"),
        Boolean("boolean"),
       */ Unknown("unknown") : /*  */;
	/* private final */ value : string;
	constructor (value : string) {
		this/* : this */.value/* : T */ = value/* : T */;
	}
	/* @Override
        public */ generate() : string {
		return this/* : this */.value/* : T */;
	}
	/* @Override
        public */ replace(mapping : Map<string, Type>) : Type {
		return this/* : this */;
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
		this/* : this */.sourceRepresentation/* : string */ = sourceRepresentation/* : string */;
		this/* : this */.targetRepresentation/* : string */ = targetRepresentation/* : string */;
	}
}
/* private */class BooleanValue/*  */ {/* True("true"), False("false"); */
	/* private final */ value : string;
	constructor (value : string) {
		this/* : this */.value/* : T */ = value/* : T */;
	}
	/* @Override
        public */ generate() : string {
		return this/* : this */.value/* : T */;
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
		let tuple = /* compileStatements */(new CompileState()/* : CompileState */, input/* : string */, Main/* : Main */.compileRootSegment/* : unknown */)/* : unknown */;
		let joined = tuple/* : [string, DivideState] */[0/* : number */]()/* : unknown */.structures/* : unknown */.iterate/* : unknown */()/* : unknown */.collect/* : unknown */(new Joiner()/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return joined/* : string */ + tuple/* : [string, DivideState] */.right/* : unknown */()/* : unknown */;
	}
	/* private static */ compileStatements(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => [CompileState, string]) : [CompileState, string] {
		let parsed = /* parseStatements */(state/* : CompileState */, input/* : string */, mapper/* : (arg0 : T) => R */)/* : unknown */;
		return new Tuple2Impl(parsed/* : unknown */.left/* : unknown */()/* : unknown */, /* generateStatements */(parsed/* : unknown */.right/* : unknown */()/* : unknown */)/* : unknown */)/* : Tuple2Impl */;
	}
	/* private static */ generateStatements(statements : List<string>) : string {
		return /* generateAll */(Main/* : Main */.mergeStatements/* : unknown */, statements/* : List<string> */)/* : unknown */;
	}
	/* private static */ parseStatements(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => [CompileState, string]) : [CompileState, List<string>] {
		return /* parseAll0 */(state/* : CompileState */, input/* : string */, Main/* : Main */.foldStatementChar/* : unknown */, mapper/* : (arg0 : T) => R */)/* : unknown */;
	}
	/* private static */ generateAll(merger : (arg0 : string, arg1 : string) => string, elements : List<string>) : string {
		return elements/* : content-start java.util.List content-end<T> */.iterate/* : unknown */()/* : unknown */.fold/* : unknown */("", merger/* : (arg0 : string, arg1 : string) => string */)/* : unknown */;
	}
	/* private static  */ parseAll0<T>(state : CompileState, input : string, folder : (arg0 : DivideState, arg1 : string) => DivideState, mapper : (arg0 : CompileState, arg1 : string) => [CompileState, T]) : [CompileState, List<T>] {
		return /* getCompileStateListTuple */(state/* : CompileState */, input/* : string */, folder/* : (arg0 : R, arg1 : T) => R */, (state1, s) => new Some(mapper/* : (arg0 : T) => R */(state1/* : unknown */, s/* : unknown */)/* : R */)/* : Some */)/* : unknown */.orElseGet/* : unknown */(() => new Tuple2Impl(state/* : CompileState */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */)/* : Tuple2Impl */)/* : unknown */;
	}
	/* private static  */ getCompileStateListTuple<T>(state : CompileState, input : string, folder : (arg0 : DivideState, arg1 : string) => DivideState, mapper : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) : Option<[CompileState, List<T>]> {
		return /* parseAll */(state/* : CompileState */, input/* : string */, folder/* : (arg0 : R, arg1 : T) => R */, (state1, tuple) => mapper/* : (arg0 : T) => R */(state1/* : unknown */, tuple/* : [string, DivideState] */[1/* : number */]()/* : unknown */)/* : R */)/* : unknown */;
	}
	/* private static  */ parseAll<T>(state : CompileState, input : string, folder : (arg0 : DivideState, arg1 : string) => DivideState, mapper : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]>) : Option<[CompileState, List<T>]> {
		let initial : Option<[CompileState, List<T>]> = new Some(new Tuple2Impl(state/* : CompileState */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */)/* : Tuple2Impl */)/* : Some */;
		return /* divideAll */(input/* : string */, folder/* : (arg0 : R, arg1 : T) => R */)/* : unknown */.iterateWithIndices/* : unknown */()/* : unknown */.fold/* : unknown */(initial/* : R */, (tuple, element) => {
			return tuple/* : [string, DivideState] */.flatMap/* : unknown */((inner) => {
				let state1 = inner/* : unknown */.left/* : unknown */()/* : unknown */;
				let right = inner/* : unknown */.right/* : unknown */()/* : unknown */;
				return mapper/* : (arg0 : T) => R */(state1/* : unknown */, element/* : T */)/* : R */.map/* : unknown */((applied) => {
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
		while (true){
			let maybePopped = current/* : R */.pop/* : unknown */()/* : unknown */.map/* : unknown */((tuple) => {
				return /* foldSingleQuotes */(tuple/* : [string, DivideState] */)/* : unknown */.or/* : unknown */(() => /* foldDoubleQuotes */(tuple/* : [string, DivideState] */)/* : unknown */)/* : unknown */.orElseGet/* : unknown */(() => folder/* : (arg0 : R, arg1 : T) => R */(tuple/* : [string, DivideState] */[1/* : number */]()/* : unknown */, tuple/* : [string, DivideState] */[0/* : number */]()/* : unknown */)/* : R */)/* : unknown */;
			})/* : unknown */;
			if (maybePopped/* : unknown */.isPresent/* : unknown */()/* : unknown */){
				current/* : R */ = maybePopped/* : unknown */.orElse/* : unknown */(current/* : R */)/* : unknown */;
			}
			else {
				/* break */;
			}
		}
		return current/* : R */.advance/* : unknown */()/* : unknown */.segments/* : unknown */;
	}
	/* private static */ foldDoubleQuotes(tuple : [string, DivideState]) : Option<DivideState> {
		if (tuple/* : [string, DivideState] */[0/* : number */]()/* : unknown */ === /*  '\"' */){
			let current = tuple/* : [string, DivideState] */[1/* : number */]()/* : unknown */.append/* : unknown */(tuple/* : [string, DivideState] */[0/* : number */]()/* : unknown */)/* : unknown */;
			while (true){
				let maybePopped = current/* : R */.popAndAppendToTuple/* : unknown */()/* : unknown */;
				if (maybePopped/* : unknown */.isEmpty/* : unknown */()/* : unknown */){
					/* break */;
				}
				let popped = maybePopped/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */;
				current/* : R */ = popped/* : unknown */.right/* : unknown */()/* : unknown */;
				if (popped/* : unknown */.left/* : unknown */()/* : unknown */ === /*  '\\' */){
					current/* : R */ = current/* : R */.popAndAppendToOption/* : unknown */()/* : unknown */.orElse/* : unknown */(current/* : R */)/* : unknown */;
				}
				if (popped/* : unknown */.left/* : unknown */()/* : unknown */ === /*  '\"' */){
					/* break */;
				}
			}
			return new Some(current/* : R */)/* : Some */;
		}
		return new None()/* : None */;
	}
	/* private static */ foldSingleQuotes(tuple : [string, DivideState]) : Option<DivideState> {
		if (/* tuple.left() != '\'' */){
			return new None()/* : None */;
		}
		let appended = tuple/* : [string, DivideState] */[1/* : number */]()/* : unknown */.append/* : unknown */(tuple/* : [string, DivideState] */[0/* : number */]()/* : unknown */)/* : unknown */;
		return appended/* : unknown */.popAndAppendToTuple/* : unknown */()/* : unknown */.map/* : unknown */(Main/* : Main */.foldEscaped/* : unknown */)/* : unknown */.flatMap/* : unknown */(DivideState/* : (arg0 : string, arg1 : number, arg2 : List<string>, arg3 : string, arg4 : number) => content-start public content-end */.popAndAppendToOption/* : unknown */)/* : unknown */;
	}
	/* private static */ foldEscaped(escaped : [string, DivideState]) : DivideState {
		if (escaped/* : [string, DivideState] */[0/* : number */]()/* : unknown */ === /*  '\\' */){
			return escaped/* : [string, DivideState] */[1/* : number */]()/* : unknown */.popAndAppendToOption/* : unknown */()/* : unknown */.orElse/* : unknown */(escaped/* : [string, DivideState] */[1/* : number */]()/* : unknown */)/* : unknown */;
		}
		return escaped/* : [string, DivideState] */[1/* : number */]()/* : unknown */;
	}
	/* private static */ foldStatementChar(state : DivideState, c : string) : DivideState {
		let append : DivideState = state/* : CompileState */.append/* : (arg0 : string) => DivideState */(c/* : string */)/* : DivideState */;
		if (c/* : string */ === /*  ';'  */ && append/* : (arg0 : string) => DivideState */.isLevel/* : unknown */()/* : unknown */){
			return append/* : (arg0 : string) => DivideState */()/* : DivideState */;
		}
		if (c/* : string */ === /*  '}'  */ && append/* : (arg0 : string) => DivideState */.isShallow/* : unknown */()/* : unknown */){
			return append/* : (arg0 : string) => DivideState */()/* : DivideState */.exit/* : () => DivideState */()/* : DivideState */;
		}
		if (c/* : string */ === /*  '{'  */ || c/* : string */ === /*  '(' */){
			return append/* : (arg0 : string) => DivideState */()/* : DivideState */;
		}
		if (c/* : string */ === /*  '}'  */ || c/* : string */ === /*  ')' */){
			return append/* : (arg0 : string) => DivideState */()/* : DivideState */;
		}
		return append/* : (arg0 : string) => DivideState */;
	}
	/* private static */ compileRootSegment(state : CompileState, input : string) : [CompileState, string] {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.startsWith/* : unknown */("package ")/* : unknown */ || stripped/* : unknown */.startsWith/* : unknown */("import ")/* : unknown */){
			return new Tuple2Impl(state/* : CompileState */, "")/* : Tuple2Impl */;
		}
		return /* compileClass */(stripped/* : unknown */, 0/* : number */, state/* : CompileState */)/* : unknown */.orElseGet/* : unknown */(() => new Tuple2Impl(state/* : CompileState */, /* generatePlaceholder */(stripped/* : unknown */)/* : unknown */)/* : Tuple2Impl */)/* : unknown */;
	}
	/* private static */ compileClass(stripped : string, depth : number, state : CompileState) : Option<[CompileState, string]> {
		return /* compileStructure */(stripped/* : unknown */, "class ", "class ", state/* : CompileState */)/* : unknown */;
	}
	/* private static */ compileStructure(stripped : string, sourceInfix : string, targetInfix : string, state : CompileState) : Option<[CompileState, string]> {
		return first/* : unknown */(stripped/* : unknown */, sourceInfix/* : string */, (beforeInfix, right) => {
			return first/* : unknown */(right/* : () => B */, "{", (beforeContent, withEnd) => {
				return /* suffix */(withEnd/* : unknown */.strip/* : unknown */()/* : unknown */, "}", (content1) => {
					return /* getOr */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : unknown */, beforeContent/* : unknown */, content1/* : unknown */)/* : unknown */;
				})/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ getOr(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string) : Option<[CompileState, string]> {
		return first/* : unknown */(beforeContent/* : unknown */, " implements ", (s, s2) => {
			return /* structureWithMaybeExtends */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : unknown */, s/* : unknown */, content1/* : unknown */)/* : unknown */;
		})/* : unknown */.or/* : unknown */(() => {
			return /* structureWithMaybeExtends */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : unknown */, beforeContent/* : unknown */, content1/* : unknown */)/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ structureWithMaybeExtends(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string) : Option<[CompileState, string]> {
		return first/* : unknown */(beforeContent/* : unknown */, " extends ", (s, s2) => {
			return /* structureWithMaybeParams */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : unknown */, s/* : unknown */, content1/* : unknown */)/* : unknown */;
		})/* : unknown */.or/* : unknown */(() => {
			return /* structureWithMaybeParams */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : unknown */, beforeContent/* : unknown */, content1/* : unknown */)/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ structureWithMaybeParams(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string) : Option<[CompileState, string]> {
		return /* suffix */(beforeContent/* : unknown */.strip/* : unknown */()/* : unknown */, ")", (s) => {
			return first/* : unknown */(s/* : unknown */, "(", (s1, s2) => {
				let parsed = /* parseParameters */(state/* : CompileState */, s2/* : unknown */)/* : unknown */;
				return /* getOred */(targetInfix/* : string */, parsed/* : unknown */.left/* : unknown */()/* : unknown */, beforeInfix/* : unknown */, s1/* : unknown */, content1/* : unknown */, parsed/* : unknown */.right/* : unknown */()/* : unknown */)/* : unknown */;
			})/* : unknown */;
		})/* : unknown */.or/* : unknown */(() => {
			return /* getOred */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : unknown */, beforeContent/* : unknown */, content1/* : unknown */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */)/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ getOred(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string, params : List<Parameter>) : Option<[CompileState, string]> {
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
	/* private static */ assembleStructure(state : CompileState, targetInfix : string, beforeInfix : string, rawName : string, content : string, typeParams : List<string>, afterTypeParams : string, params : List<Parameter>) : Option<[CompileState, string]> {
		let name = rawName/* : string */.strip/* : unknown */()/* : unknown */;
		if (!/* isSymbol */(name/* : () => string */)/* : unknown */){
			return new None()/* : None */;
		}
		let joinedTypeParams = typeParams/* : () => List<string> */()/* : List<string> */.collect/* : (arg0 : Collector<T, R>) => R */(new Joiner(", ")/* : Joiner */)/* : R */.map/* : unknown */((inner) => "<" + inner + ">")/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		let statementsTuple : [CompileState, List<string>] = parseStatements/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => [CompileState, string]) => [CompileState, List<string>] */(state/* : CompileState */.pushStructName/* : (arg0 : string) => CompileState */(name/* : () => string */)/* : CompileState */.withTypeParams/* : (arg0 : List<string>) => CompileState */(typeParams/* : () => List<string> */)/* : CompileState */, content/* : string */, (state0, input) => /* compileClassSegment */(state0/* : unknown */, input/* : string */, 1/* : number */)/* : unknown */)/* : [CompileState, List<string>] */;
		/* List<String> parsed1 */;
		if (params/* : List<Parameter> */.isEmpty/* : () => boolean */()/* : boolean */){
			/* parsed1 */ = statementsTuple/* : [CompileState, List<string>] */[1/* : number */]()/* : unknown */;
		}
		else {
			let joined = /* joinValues */(/* retainDefinitions */(params/* : List<Parameter> */)/* : unknown */)/* : unknown */;
			let constructorIndent = /* createIndent */(1/* : number */)/* : unknown */;
			/* parsed1 */ = statementsTuple/* : [CompileState, List<string>] */[1/* : number */]()/* : unknown */.addFirst/* : unknown */(constructorIndent/* : unknown */ + "constructor " + joined + " {" + constructorIndent + "}")/* : unknown */;
		}
		let parsed2 = /* parsed1 */.iterate/* : unknown */()/* : unknown */.collect/* : unknown */(new Joiner()/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		let generated = /* generatePlaceholder */(beforeInfix/* : unknown */.strip/* : unknown */()/* : unknown */)/* : unknown */ + targetInfix/* : string */ + name/* : () => string */ + joinedTypeParams/* : unknown */ + /* generatePlaceholder */(afterTypeParams/* : unknown */)/* : unknown */ + " {" + parsed2 + "\n}\n";
		return new Some(new Tuple2Impl(statementsTuple/* : [CompileState, List<string>] */[0/* : number */]()/* : unknown */.popStructName/* : unknown */()/* : unknown */.addStructure/* : unknown */(generated/* : unknown */)/* : unknown */.addType/* : unknown */(new ObjectType(name/* : () => string */, typeParams/* : () => List<string> */, statementsTuple/* : [CompileState, List<string>] */[0/* : number */]()/* : unknown */.definitions/* : unknown */)/* : ObjectType */)/* : unknown */, "")/* : Tuple2Impl */)/* : Some */;
	}
	/* private static */ retainDefinition(parameter : Parameter) : Option<Definition> {
		if (parameter/* : Parameter */._variant/* : unknown */ === ParameterVariant.Definition/* : unknown */){
			return new Some(definition/* : unknown */)/* : Some */;
		}
		return new None()/* : None */;
	}
	/* private static */ isSymbol(input : string) : boolean {
		/* for (var i = 0; i < input.length(); i++) */{
			let c = input/* : string */.charAt/* : unknown */(/* i */)/* : unknown */;
			if (/* Character */.isLetter/* : unknown */(c/* : string */)/* : unknown */ || /*  */(/* i != 0  */ && /* Character */.isDigit/* : unknown */(c/* : string */)/* : unknown */)/* : unknown */){
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
		return mapper/* : (arg0 : T) => R */(slice/* : unknown */)/* : R */;
	}
	/* private static  */ suffix<T>(input : string, suffix : string, mapper : (arg0 : string) => Option<T>) : Option<T> {
		if (!input/* : string */.endsWith/* : unknown */(suffix/* : string */)/* : unknown */){
			return new None()/* : None */;
		}
		let slice = input/* : string */.substring/* : unknown */(0/* : number */, input/* : string */.length/* : unknown */()/* : unknown */ - suffix/* : string */.length/* : unknown */()/* : unknown */)/* : unknown */;
		return mapper/* : (arg0 : T) => R */(slice/* : unknown */)/* : R */;
	}
	/* private static */ compileClassSegment(state : CompileState, input : string, depth : number) : [CompileState, string] {
		return /* compileWhitespace */(input/* : string */, state/* : CompileState */)/* : unknown */.or/* : unknown */(() => compileClass/* : (arg0 : string, arg1 : number, arg2 : CompileState) => Option<[CompileState, string]> */(input/* : string */, depth/* : number */, state/* : CompileState */)/* : Option<[CompileState, string]> */)/* : unknown */.or/* : unknown */(() => compileStructure/* : (arg0 : string, arg1 : string, arg2 : string, arg3 : CompileState) => Option<[CompileState, string]> */(input/* : string */, "interface ", "interface ", state/* : CompileState */)/* : Option<[CompileState, string]> */)/* : unknown */.or/* : unknown */(() => compileStructure/* : (arg0 : string, arg1 : string, arg2 : string, arg3 : CompileState) => Option<[CompileState, string]> */(input/* : string */, "record ", "class ", state/* : CompileState */)/* : Option<[CompileState, string]> */)/* : unknown */.or/* : unknown */(() => compileStructure/* : (arg0 : string, arg1 : string, arg2 : string, arg3 : CompileState) => Option<[CompileState, string]> */(input/* : string */, "enum ", "class ", state/* : CompileState */)/* : Option<[CompileState, string]> */)/* : unknown */.or/* : unknown */(() => /* compileMethod */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* compileDefinitionStatement */(input/* : string */, depth/* : number */, state/* : CompileState */)/* : unknown */)/* : unknown */.orElseGet/* : unknown */(() => new Tuple2Impl(state/* : CompileState */, /* generatePlaceholder */(input/* : string */)/* : unknown */)/* : Tuple2Impl */)/* : unknown */;
	}
	/* private static */ compileWhitespace(input : string, state : CompileState) : Option<[CompileState, string]> {
		if (input/* : string */.isBlank/* : unknown */()/* : unknown */){
			return new Some(new Tuple2Impl(state/* : CompileState */, "")/* : Tuple2Impl */)/* : Some */;
		}
		return new None()/* : None */;
	}
	/* private static */ compileMethod(state : CompileState, input : string, depth : number) : Option<[CompileState, string]> {
		return first/* : unknown */(input/* : string */, "(", (definitionString, withParams) => {
			return first/* : unknown */(withParams/* : unknown */, ")", (parametersString, rawContent) => {
				return /* parseDefinition(state, definitionString).<Tuple2<CompileState, Header>>map */((tuple) => new Tuple2Impl(tuple/* : [string, DivideState] */[0/* : number */]()/* : unknown */, tuple/* : [string, DivideState] */[1/* : number */]()/* : unknown */)/* : Tuple2Impl */)/* : unknown */.or/* : unknown */(() => /* parseConstructor */(state/* : CompileState */, definitionString/* : unknown */)/* : unknown */)/* : unknown */.flatMap/* : unknown */((definitionTuple) => {
					let definitionState = definitionTuple/* : unknown */.left/* : unknown */()/* : unknown */;
					let header = definitionTuple/* : unknown */.right/* : unknown */()/* : unknown */;
					let parametersTuple = /* parseParameters */(definitionState/* : unknown */, parametersString/* : unknown */)/* : unknown */;
					let rawParameters = parametersTuple/* : unknown */.right/* : unknown */()/* : unknown */;
					let parameters = /* retainDefinitions */(rawParameters/* : unknown */)/* : unknown */;
					let joinedParameters = /* joinValues */(parameters/* : unknown */)/* : unknown */;
					let content = rawContent/* : unknown */.strip/* : unknown */()/* : unknown */;
					let indent = /* createIndent */(depth/* : number */)/* : unknown */;
					let paramTypes = parameters/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(Definition/* : Definition */.type/* : unknown */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
					let toDefine = header/* : unknown */.createDefinition/* : unknown */(paramTypes/* : List<Type> */)/* : unknown */;
					let generatedHeader = header/* : unknown */.generateWithParams/* : unknown */(joinedParameters/* : string */)/* : unknown */;
					if (content/* : string */.equals/* : unknown */(";")/* : unknown */){
						return new Some(new Tuple2Impl(parametersTuple/* : unknown */.left/* : unknown */()/* : unknown */.withDefinition/* : unknown */(toDefine/* : unknown */)/* : unknown */, indent/* : unknown */ + generatedHeader/* : unknown */ + ";")/* : Tuple2Impl */)/* : Some */;
					}
					if (content/* : string */.startsWith/* : unknown */("{")/* : unknown */ && content/* : string */.endsWith/* : unknown */("}")/* : unknown */){
						let substring = content/* : string */.substring/* : unknown */(1/* : number */, content/* : string */.length/* : unknown */()/* : unknown */ - 1/* : number */)/* : unknown */;
						let statementsTuple = /* compileFunctionSegments */(parametersTuple/* : unknown */.left/* : unknown */()/* : unknown */.withDefinitions/* : unknown */(parameters/* : unknown */)/* : unknown */, substring/* : unknown */, depth/* : number */)/* : unknown */;
						let generated = indent/* : unknown */ + generatedHeader/* : unknown */ + " {" + statementsTuple.right() + indent + "}";
						return new Some(new Tuple2Impl(statementsTuple/* : [CompileState, List<string>] */[0/* : number */]()/* : unknown */.withDefinition/* : unknown */(toDefine/* : unknown */)/* : unknown */, generated/* : unknown */)/* : Tuple2Impl */)/* : Some */;
					}
					return new None()/* : None */;
				})/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ parseConstructor(state : CompileState, input : string) : Option<[CompileState, Header]> {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.equals/* : unknown */(state/* : CompileState */.structNames/* : unknown */.last/* : unknown */()/* : unknown */.orElse/* : unknown */("")/* : unknown */)/* : unknown */){
			return new Some(new Tuple2Impl(state/* : CompileState */, new ConstructorHeader()/* : ConstructorHeader */)/* : Tuple2Impl */)/* : Some */;
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
	/* private static */ compileFunctionSegments(state : CompileState, input : string, depth : number) : [CompileState, string] {
		return compileStatements/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => [CompileState, string]) => [CompileState, string] */(state/* : CompileState */, input/* : string */, (state1, input1) => /* compileFunctionSegment */(state1/* : unknown */, input1/* : unknown */, depth/* : number */ + 1/* : number */)/* : unknown */)/* : [CompileState, string] */;
	}
	/* private static */ compileFunctionSegment(state : CompileState, input : string, depth : number) : [CompileState, string] {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.isEmpty/* : unknown */()/* : unknown */){
			return new Tuple2Impl(state/* : CompileState */, "")/* : Tuple2Impl */;
		}
		return /* compileFunctionStatement */(state/* : CompileState */, depth/* : number */, stripped/* : unknown */)/* : unknown */.or/* : unknown */(() => /* compileBlock */(state/* : CompileState */, depth/* : number */, stripped/* : unknown */)/* : unknown */)/* : unknown */.orElseGet/* : unknown */(() => new Tuple2Impl(state/* : CompileState */, /* generatePlaceholder */(stripped/* : unknown */)/* : unknown */)/* : Tuple2Impl */)/* : unknown */;
	}
	/* private static */ compileFunctionStatement(state : CompileState, depth : number, stripped : string) : Option<[CompileState, string]> {
		return suffix/* : string */(stripped/* : unknown */, ";", (s) => {
			let tuple = /* compileStatementValue */(state/* : CompileState */, s/* : unknown */, depth/* : number */)/* : unknown */;
			return new Some(new Tuple2Impl(tuple/* : [string, DivideState] */[0/* : number */]()/* : unknown */, /* createIndent */(depth/* : number */)/* : unknown */ + tuple/* : [string, DivideState] */[1/* : number */]()/* : unknown */ + ";")/* : Tuple2Impl */)/* : Some */;
		})/* : unknown */;
	}
	/* private static */ compileBlock(state : CompileState, depth : number, stripped : string) : Option<[CompileState, string]> {
		return suffix/* : string */(stripped/* : unknown */, "}", (withoutEnd) => {
			return /* split */(() => /* toFirst */(withoutEnd/* : unknown */)/* : unknown */, (beforeContent, content) => {
				return suffix/* : string */(beforeContent/* : unknown */, "{", (s) => {
					let compiled : [CompileState, string] = compileFunctionSegments/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, string] */(state/* : CompileState */, content/* : string */, depth/* : number */)/* : [CompileState, string] */;
					let indent = /* createIndent */(depth/* : number */)/* : unknown */;
					let headerTuple = /* compileBlockHeader */(state/* : CompileState */, s/* : unknown */, depth/* : number */)/* : unknown */;
					let headerState = headerTuple/* : unknown */.left/* : unknown */()/* : unknown */;
					let header = headerTuple/* : unknown */.right/* : unknown */()/* : unknown */;
					return new Some(new Tuple2Impl(headerState/* : unknown */, indent/* : unknown */ + header/* : unknown */ + "{" + compiled.right() + indent + "}")/* : Tuple2Impl */)/* : Some */;
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
		if (stripped/* : unknown */.equals/* : unknown */("else")/* : unknown */){
			return new Some(new Tuple2Impl(state/* : CompileState */, "else ")/* : Tuple2Impl */)/* : Some */;
		}
		return new None()/* : None */;
	}
	/* private static */ compileConditional(state : CompileState, input : string, prefix : string, depth : number) : Option<[CompileState, string]> {
		return prefix/* : string */(input/* : string */, prefix/* : string */, (withoutPrefix) => {
			return prefix/* : string */(withoutPrefix/* : unknown */.strip/* : unknown */()/* : unknown */, "(", (withoutValueStart) => {
				return suffix/* : string */(withoutValueStart/* : unknown */, ")", (value) => {
					let compiled = /* compileValue */(state/* : CompileState */, value/* : T */, depth/* : number */)/* : unknown */;
					return new Some(new Tuple2Impl(compiled/* : [CompileState, string] */[0/* : number */]()/* : unknown */, prefix/* : string */ + " (" + compiled.right() + ")")/* : Tuple2Impl */)/* : Some */;
				})/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ foldBlockStart(state : DivideState, c : string) : DivideState {
		let appended : DivideState = state/* : CompileState */.append/* : (arg0 : string) => DivideState */(c/* : string */)/* : DivideState */;
		if (c/* : string */ === /*  '{'  */ && state/* : CompileState */.isLevel/* : unknown */()/* : unknown */){
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
	/* private static */ compileStatementValue(state : CompileState, input : string, depth : number) : [CompileState, string] {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.startsWith/* : unknown */("return ")/* : unknown */){
			let value = stripped/* : unknown */.substring/* : unknown */("return ".length/* : unknown */()/* : unknown */)/* : unknown */;
			let tuple = /* compileValue */(state/* : CompileState */, value/* : T */, depth/* : number */)/* : unknown */;
			return new Tuple2Impl(tuple/* : [string, DivideState] */[0/* : number */]()/* : unknown */, "return " + tuple/* : [string, DivideState] */.right/* : unknown */()/* : unknown */)/* : Tuple2Impl */;
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
					if (type/* : () => Type */(/* Primitive */.Unknown/* : unknown */)/* : Type */){
						return source/* : unknown */.type/* : unknown */()/* : unknown */;
					}
					else {
						return type/* : () => Type */;
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
		return new Tuple2Impl(tuple/* : [string, DivideState] */[0/* : number */]()/* : unknown */, tuple/* : [string, DivideState] */[1/* : number */]()/* : unknown */.generate/* : unknown */()/* : unknown */)/* : Tuple2Impl */;
	}
	/* private static */ parseValue(state : CompileState, input : string, depth : number) : [CompileState, Value] {
		return /* parseBoolean */(state/* : CompileState */, input/* : string */)/* : unknown */.or/* : unknown */(() => /* parseLambda */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseString */(state/* : CompileState */, input/* : string */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseDataAccess */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseSymbolValue */(state/* : CompileState */, input/* : string */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseInvokable */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseDigits */(state/* : CompileState */, input/* : string */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseOperation */(state/* : CompileState */, input/* : string */, depth/* : number */, /* Operator */.ADD/* : unknown */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseOperation */(state/* : CompileState */, input/* : string */, depth/* : number */, /* Operator */.EQUALS/* : unknown */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseOperation */(state/* : CompileState */, input/* : string */, depth/* : number */, /* Operator */.SUBTRACT/* : unknown */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseOperation */(state/* : CompileState */, input/* : string */, depth/* : number */, /* Operator */.AND/* : unknown */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseOperation */(state/* : CompileState */, input/* : string */, depth/* : number */, /* Operator */.OR/* : unknown */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseOperation */(state/* : CompileState */, input/* : string */, depth/* : number */, /*  Operator.GREATER_THAN_OR_EQUALS */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseNot */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseMethodReference */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseInstanceOf */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : unknown */)/* : unknown */.orElseGet/* : unknown */(() => new Tuple2Impl<CompileState, Value>(state/* : CompileState */, new Placeholder(input/* : string */)/* : Placeholder */)/* : Tuple2Impl<CompileState, Value> */)/* : unknown */;
	}
	/* private static */ parseBoolean(state : CompileState, input : string) : Option<[CompileState, Value]> {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.equals/* : unknown */("false")/* : unknown */){
			return new Some(new Tuple2Impl(state/* : CompileState */, /* BooleanValue */.False/* : unknown */)/* : Tuple2Impl */)/* : Some */;
		}
		if (stripped/* : unknown */.equals/* : unknown */("true")/* : unknown */){
			return new Some(new Tuple2Impl(state/* : CompileState */, /* BooleanValue */.True/* : unknown */)/* : Tuple2Impl */)/* : Some */;
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
			return new Some(new Tuple2Impl(tuple/* : [string, DivideState] */[0/* : number */]()/* : unknown */, new DataAccess(tuple/* : [string, DivideState] */[1/* : number */]()/* : unknown */, s2/* : unknown */, /* Primitive */.Unknown/* : unknown */)/* : DataAccess */)/* : Tuple2Impl */)/* : Some */;
		})/* : Option<T> */;
	}
	/* private static */ parseNot(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.startsWith/* : unknown */("!")/* : unknown */){
			let slice = stripped/* : unknown */.substring/* : unknown */(1/* : number */)/* : unknown */;
			let tuple : [CompileState, Value] = parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state/* : CompileState */, slice/* : unknown */, depth/* : number */)/* : [CompileState, Value] */;
			let value = tuple/* : [string, DivideState] */[1/* : number */]()/* : unknown */;
			return new Some(new Tuple2Impl(tuple/* : [string, DivideState] */[0/* : number */]()/* : unknown */, new Not(value/* : T */)/* : Not */)/* : Tuple2Impl */)/* : Some */;
		}
		return new None()/* : None */;
	}
	/* private static */ parseLambda(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return first/* : unknown */(input/* : string */, "->", (beforeArrow, valueString) => {
			let strippedBeforeArrow = beforeArrow/* : unknown */.strip/* : unknown */()/* : unknown */;
			if (isSymbol/* : (arg0 : string) => boolean */(strippedBeforeArrow/* : unknown */)/* : boolean */){
				let type : Type = /* Primitive */.Unknown/* : unknown */;
				if (/* state.typeRegister instanceof Some */(/* var expectedType */)/* : unknown */){
					if (/* expectedType */._variant/* : unknown */ === Variant.FunctionType/* : unknown */){
						type/* : () => Type */ = /* functionType */.arguments/* : unknown */.get/* : unknown */(0/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */;
					}
				}
				return /* assembleLambda */(state/* : CompileState */, Lists/* : Lists */.of/* : (arg0 : T[]) => List<T> */(ImmutableDefinition/* : ImmutableDefinition */.createSimpleDefinition/* : (arg0 : string, arg1 : Type) => Definition */(strippedBeforeArrow/* : unknown */, type/* : () => Type */)/* : Definition */)/* : List<T> */, valueString/* : unknown */, depth/* : number */)/* : unknown */;
			}
			if (strippedBeforeArrow/* : unknown */.startsWith/* : unknown */("(")/* : unknown */ && strippedBeforeArrow/* : unknown */.endsWith/* : unknown */(")")/* : unknown */){
				let parameterNames : R = divideAll/* : (arg0 : string, arg1 : (arg0 : DivideState, arg1 : string) => DivideState) => List<string> */(strippedBeforeArrow/* : unknown */.substring/* : unknown */(1/* : number */, strippedBeforeArrow/* : unknown */.length/* : unknown */()/* : unknown */ - 1/* : number */)/* : unknown */, Main/* : Main */.foldValueChar/* : unknown */)/* : List<string> */.iterate/* : () => Iterator<T> */()/* : Iterator<T> */.map/* : (arg0 : (arg0 : T) => R) => Option<R> */(/* String */.strip/* : unknown */)/* : Option<R> */.filter/* : (arg0 : (arg0 : T) => boolean) => Option<T> */((value : T) => !value/* : T */.isEmpty/* : unknown */()/* : unknown */)/* : Option<T> */.map/* : (arg0 : (arg0 : T) => R) => Option<R> */((name : T) => ImmutableDefinition/* : ImmutableDefinition */.createSimpleDefinition/* : (arg0 : string, arg1 : Type) => Definition */(name/* : () => string */, /* Primitive */.Unknown/* : unknown */)/* : Definition */)/* : Option<R> */.collect/* : (arg0 : Collector<T, R>) => R */(new ListCollector()/* : ListCollector */)/* : R */;
				return /* assembleLambda */(state/* : CompileState */, parameterNames/* : R */, valueString/* : unknown */, depth/* : number */)/* : unknown */;
			}
			return new None()/* : None */;
		})/* : unknown */;
	}
	/* private static */ assembleLambda(state : CompileState, definitions : List<Definition>, valueString : string, depth : number) : Some<[CompileState, Value]> {
		let strippedValueString = valueString/* : unknown */.strip/* : unknown */()/* : unknown */;
		/* Tuple2Impl<CompileState, LambdaValue> value */;
		let state2 : CompileState = state/* : CompileState */.withDefinitions/* : (arg0 : List<Definition>) => CompileState */(definitions/* : List<Definition> */)/* : CompileState */;
		if (strippedValueString/* : unknown */.startsWith/* : unknown */("{")/* : unknown */ && strippedValueString/* : unknown */.endsWith/* : unknown */("}")/* : unknown */){
			let value1 : [CompileState, string] = compileStatements/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => [CompileState, string]) => [CompileState, string] */(state2/* : CompileState */, strippedValueString/* : unknown */.substring/* : unknown */(1/* : number */, strippedValueString/* : unknown */.length/* : unknown */()/* : unknown */ - 1/* : number */)/* : unknown */, (state1, input1) => compileFunctionSegment/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, string] */(state1/* : unknown */, input1/* : unknown */, depth/* : number */ + 1/* : number */)/* : [CompileState, string] */)/* : [CompileState, string] */;
			let right = value1/* : [CompileState, string] */[1/* : number */]()/* : unknown */;
			value/* : T */ = new Tuple2Impl(value1/* : [CompileState, string] */[0/* : number */]()/* : unknown */, new BlockLambdaValue(right/* : () => B */, depth/* : number */)/* : BlockLambdaValue */)/* : Tuple2Impl */;
		}
		else {
			let value1 : [CompileState, Value] = parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state2/* : CompileState */, strippedValueString/* : unknown */, depth/* : number */)/* : [CompileState, Value] */;
			value/* : T */ = new Tuple2Impl(value1/* : [CompileState, string] */[0/* : number */]()/* : unknown */, value1/* : [CompileState, string] */[1/* : number */]()/* : unknown */)/* : Tuple2Impl */;
		}
		let right = value/* : T */.right/* : unknown */()/* : unknown */;
		return new Some(new Tuple2Impl(value/* : T */.left/* : unknown */()/* : unknown */, new Lambda(definitions/* : List<Definition> */, right/* : () => B */)/* : Lambda */)/* : Tuple2Impl */)/* : Some */;
	}
	/* private static */ parseDigits(state : CompileState, input : string) : Option<[CompileState, Value]> {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (/* isNumber */(stripped/* : unknown */)/* : unknown */){
			return new Some(new Tuple2Impl<CompileState, Value>(state/* : CompileState */, new SymbolValue(stripped/* : unknown */, /* Primitive */.Int/* : unknown */)/* : SymbolValue */)/* : Tuple2Impl<CompileState, Value> */)/* : Some */;
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
			if (/* Character */.isDigit/* : unknown */(c/* : string */)/* : unknown */){
				/* continue */;
			}
			return false;
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
			let valueTuple = /* parseArgument */(withExpected/* : unknown */, element/* : T */, depth/* : number */)/* : unknown */;
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
		if (argument/* : Argument */._variant/* : unknown */ === ArgumentVariant.Value/* : unknown */){
			return new Some(value/* : T */)/* : Some */;
		}
		return new None()/* : None */;
	}
	/* private static */ parseArgument(state : CompileState, element : string, depth : number) : [CompileState, Argument] {
		if (element/* : T */.isEmpty/* : unknown */()/* : unknown */){
			return new Tuple2Impl(state/* : CompileState */, new Whitespace()/* : Whitespace */)/* : Tuple2Impl */;
		}
		let tuple : [CompileState, Value] = parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state/* : CompileState */, element/* : T */, depth/* : number */)/* : [CompileState, Value] */;
		return new Tuple2Impl(tuple/* : [string, DivideState] */[0/* : number */]()/* : unknown */, tuple/* : [string, DivideState] */[1/* : number */]()/* : unknown */)/* : Tuple2Impl */;
	}
	/* private static */ findCallerType(newCaller : Caller) : FunctionType {
		let callerType : FunctionType = new FunctionType(Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, /* Primitive */.Unknown/* : unknown */)/* : FunctionType */;
		/* switch (newCaller) */{
			/* case ConstructionCaller constructionCaller -> */{
				callerType/* : unknown */ = /* constructionCaller */.toFunction/* : unknown */()/* : unknown */;
			}
			/* case Value value -> */{
				let type = value/* : T */.type/* : unknown */()/* : unknown */;
				if (type/* : () => Type */._variant/* : unknown */ === Variant.FunctionType/* : unknown */){
					callerType/* : unknown */ = /* functionType */;
				}
			}
		}
		return callerType/* : unknown */;
	}
	/* private static */ modifyCaller(state : CompileState, oldCaller : Caller) : Caller {
		if (oldCaller/* : unknown */._variant/* : unknown */ === Variant.DataAccess/* : unknown */){
			let type : Option<Type> = resolveType/* : (arg0 : string) => Option<Type> */(/* access */.parent/* : unknown */, state/* : CompileState */)/* : Option<Type> */;
			if (/* type instanceof FunctionType */){
				return /* access */.parent/* : unknown */;
			}
		}
		return oldCaller/* : unknown */;
	}
	/* private static */ resolveType(value : Value, state : CompileState) : Type {
		return value/* : T */.type/* : unknown */()/* : unknown */;
	}
	/* private static */ invocationHeader(state : CompileState, depth : number, callerString1 : string) : [CompileState, Caller] {
		if (callerString1/* : string */.startsWith/* : unknown */("new ")/* : unknown */){
			let input1 : string = callerString1/* : string */.substring/* : unknown */("new ".length/* : unknown */()/* : unknown */)/* : unknown */;
			let map = /* parseType */(state/* : CompileState */, input1/* : unknown */)/* : unknown */.map/* : unknown */((type) => {
				let right : Type = type/* : () => Type */()/* : Type */;
				return new Tuple2Impl<CompileState, Caller>(type/* : () => Type */()/* : Type */, new ConstructionCaller(right/* : () => B */)/* : ConstructionCaller */)/* : Tuple2Impl<CompileState, Caller> */;
			})/* : unknown */;
			if (map/* : (arg0 : (arg0 : T) => R) => Option<R> */()/* : Option<R> */){
				return map/* : (arg0 : (arg0 : T) => R) => Option<R> */(/* null */)/* : Option<R> */;
			}
		}
		let tuple : [CompileState, Value] = parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state/* : CompileState */, callerString1/* : string */, depth/* : number */)/* : [CompileState, Value] */;
		return new Tuple2Impl(tuple/* : [string, DivideState] */[0/* : number */]()/* : unknown */, tuple/* : [string, DivideState] */[1/* : number */]()/* : unknown */)/* : Tuple2Impl */;
	}
	/* private static */ foldInvocationStart(state : DivideState, c : string) : DivideState {
		let appended : DivideState = state/* : CompileState */.append/* : (arg0 : string) => DivideState */(c/* : string */)/* : DivideState */;
		if (c/* : string */ === /*  '(' */){
			let enter = appended/* : unknown */.enter/* : unknown */()/* : unknown */;
			if (enter/* : () => DivideState */()/* : DivideState */){
				return enter/* : () => DivideState */()/* : DivideState */;
			}
			return enter/* : () => DivideState */;
		}
		if (c/* : string */ === /*  ')' */){
			return appended/* : unknown */.exit/* : unknown */()/* : unknown */;
		}
		return appended/* : unknown */;
	}
	/* private static */ parseDataAccess(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return last/* : () => Option<T> */(input/* : string */.strip/* : unknown */()/* : unknown */, ".", (parentString, rawProperty) => {
			let property = rawProperty/* : unknown */.strip/* : unknown */()/* : unknown */;
			if (!isSymbol/* : (arg0 : string) => boolean */(property/* : unknown */)/* : unknown */){
				return new None()/* : None */;
			}
			let tuple : [CompileState, Value] = parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state/* : CompileState */, parentString/* : unknown */, depth/* : number */)/* : [CompileState, Value] */;
			let parent = tuple/* : [string, DivideState] */[1/* : number */]()/* : unknown */;
			let parentType = parent/* : unknown */.type/* : unknown */()/* : unknown */;
			if (/* parentType instanceof TupleType */){
				if (property/* : unknown */.equals/* : unknown */("left")/* : unknown */){
					return new Some(new Tuple2Impl(state/* : CompileState */, new IndexValue(parent/* : unknown */, new SymbolValue("0", /* Primitive */.Int/* : unknown */)/* : SymbolValue */)/* : IndexValue */)/* : Tuple2Impl */)/* : Some */;
				}
				if (property/* : unknown */.equals/* : unknown */("right")/* : unknown */){
					return new Some(new Tuple2Impl(state/* : CompileState */, new IndexValue(parent/* : unknown */, new SymbolValue("1", /* Primitive */.Int/* : unknown */)/* : SymbolValue */)/* : IndexValue */)/* : Tuple2Impl */)/* : Some */;
				}
			}
			let type : Type = /* Primitive */.Unknown/* : unknown */;
			if (parentType/* : unknown */._variant/* : unknown */ === Variant.FindableType/* : unknown */){
				if (/* objectType.find(property) instanceof Some */(/* var memberType */)/* : unknown */){
					type/* : () => Type */ = /* memberType */;
				}
			}
			return new Some(new Tuple2Impl(tuple/* : [string, DivideState] */[0/* : number */]()/* : unknown */, new DataAccess(parent/* : unknown */, property/* : unknown */, type/* : () => Type */)/* : DataAccess */)/* : Tuple2Impl */)/* : Some */;
		})/* : Option<T> */;
	}
	/* private static */ parseString(state : CompileState, input : string) : Option<[CompileState, Value]> {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.startsWith/* : unknown */("\"")/* : unknown */ && stripped/* : unknown */.endsWith/* : unknown */("\"")/* : unknown */){
			return new Some(new Tuple2Impl(state/* : CompileState */, new StringValue(stripped/* : unknown */)/* : StringValue */)/* : Tuple2Impl */)/* : Some */;
		}
		return new None()/* : None */;
	}
	/* private static */ parseSymbolValue(state : CompileState, value : string) : Option<[CompileState, Value]> {
		let stripped = value/* : T */.strip/* : unknown */()/* : unknown */;
		if (isSymbol/* : (arg0 : string) => boolean */(stripped/* : unknown */)/* : boolean */){
			if (/* state.resolveValue(stripped) instanceof Some */(/* var type */)/* : unknown */){
				return new Some(new Tuple2Impl(state/* : CompileState */, new SymbolValue(stripped/* : unknown */, type/* : () => Type */)/* : SymbolValue */)/* : Tuple2Impl */)/* : Some */;
			}
			if (/* state.resolveType(stripped) instanceof Some */(/* var type */)/* : unknown */){
				return new Some(new Tuple2Impl(state/* : CompileState */, new SymbolValue(stripped/* : unknown */, type/* : () => Type */)/* : SymbolValue */)/* : Tuple2Impl */)/* : Some */;
			}
			return new Some(new Tuple2Impl(state/* : CompileState */, new Placeholder(stripped/* : unknown */)/* : Placeholder */)/* : Tuple2Impl */)/* : Some */;
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
		return /* parseValuesWithIndices */(state/* : CompileState */, input/* : string */, (state1, tuple) => mapper/* : (arg0 : T) => R */(state1/* : unknown */, tuple/* : [string, DivideState] */[1/* : number */]()/* : unknown */)/* : R */)/* : unknown */;
	}
	/* private static  */ parseValuesWithIndices<T>(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]>) : Option<[CompileState, List<T>]> {
		return parseAll/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : DivideState, arg1 : string) => DivideState, arg3 : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]>) => Option<[CompileState, List<T>]> */(state/* : CompileState */, input/* : string */, Main/* : Main */.foldValueChar/* : unknown */, mapper/* : (arg0 : T) => R */)/* : Option<[CompileState, List<T>]> */;
	}
	/* private static */ compileParameter(state : CompileState, input : string) : [CompileState, Parameter] {
		if (input/* : string */.isBlank/* : unknown */()/* : unknown */){
			return new Tuple2Impl(state/* : CompileState */, new Whitespace()/* : Whitespace */)/* : Tuple2Impl */;
		}
		return /* parseDefinition */(state/* : CompileState */, input/* : string */)/* : unknown */.map/* : unknown */((tuple) => new Tuple2Impl<CompileState, Parameter>(tuple/* : [string, DivideState] */[0/* : number */]()/* : unknown */, tuple/* : [string, DivideState] */[1/* : number */]()/* : unknown */)/* : Tuple2Impl<CompileState, Parameter> */)/* : unknown */.orElseGet/* : unknown */(() => new Tuple2Impl(state/* : CompileState */, new Placeholder(input/* : string */)/* : Placeholder */)/* : Tuple2Impl */)/* : unknown */;
	}
	/* private static */ compileDefinition(state : CompileState, input : string) : [CompileState, string] {
		return /* parseDefinition */(state/* : CompileState */, input/* : string */)/* : unknown */.map/* : unknown */((tuple) => new Tuple2Impl(tuple/* : [string, DivideState] */[0/* : number */]()/* : unknown */, tuple/* : [string, DivideState] */[1/* : number */]()/* : unknown */.generate/* : unknown */()/* : unknown */)/* : Tuple2Impl */)/* : unknown */.orElseGet/* : unknown */(() => new Tuple2Impl(state/* : CompileState */, /* generatePlaceholder */(input/* : string */)/* : unknown */)/* : Tuple2Impl */)/* : unknown */;
	}
	/* private static */ createIndent(depth : number) : string {
		return "\n" + "\t".repeat/* : unknown */(depth/* : number */)/* : unknown */;
	}
	/* private static */ compileDefinitionStatement(input : string, depth : number, state : CompileState) : Option<[CompileState, string]> {
		return suffix/* : string */(input/* : string */.strip/* : unknown */()/* : unknown */, ";", (withoutEnd) => {
			return /* parseDefinition */(state/* : CompileState */, withoutEnd/* : unknown */)/* : unknown */.map/* : unknown */((result) => {
				let generated = createIndent/* : (arg0 : number) => string */(depth/* : number */)/* : string */ + result/* : unknown */.right/* : unknown */()/* : unknown */.generate/* : unknown */()/* : unknown */ + ";";
				return new Tuple2Impl(result/* : unknown */.left/* : unknown */()/* : unknown */, generated/* : unknown */)/* : Tuple2Impl */;
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
		if (c/* : string */ === /*  ' '  */ && state/* : CompileState */.isLevel/* : unknown */()/* : unknown */){
			return state/* : CompileState */.advance/* : () => DivideState */()/* : DivideState */;
		}
		let appended : DivideState = state/* : CompileState */.append/* : (arg0 : string) => DivideState */(c/* : string */)/* : DivideState */;
		if (c/* : string */ === /*  '<' */){
			return appended/* : unknown */.enter/* : unknown */()/* : unknown */;
		}
		if (c/* : string */ === /*  '>' */){
			return appended/* : unknown */.exit/* : unknown */()/* : unknown */;
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
		if (c/* : string */ === /*  ','  */ && state/* : CompileState */.isLevel/* : unknown */()/* : unknown */){
			return state/* : CompileState */.advance/* : () => DivideState */()/* : DivideState */;
		}
		let appended : DivideState = state/* : CompileState */.append/* : (arg0 : string) => DivideState */(c/* : string */)/* : DivideState */;
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
	/* private static */ compileType(state : CompileState, input : string) : Option<[CompileState, string]> {
		return /* parseType */(state/* : CompileState */, input/* : string */)/* : unknown */.map/* : unknown */((tuple) => new Tuple2Impl(tuple/* : [string, DivideState] */[0/* : number */]()/* : unknown */, tuple/* : [string, DivideState] */[1/* : number */]()/* : unknown */.generate/* : unknown */()/* : unknown */)/* : Tuple2Impl */)/* : unknown */;
	}
	/* private static */ parseType(state : CompileState, input : string) : Option<[CompileState, Type]> {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.equals/* : unknown */("int")/* : unknown */ || stripped/* : unknown */.equals/* : unknown */("Integer")/* : unknown */){
			return new Some(new Tuple2Impl(state/* : CompileState */, /* Primitive */.Int/* : unknown */)/* : Tuple2Impl */)/* : Some */;
		}
		if (stripped/* : unknown */.equals/* : unknown */("String")/* : unknown */ || stripped/* : unknown */.equals/* : unknown */("char")/* : unknown */ || stripped/* : unknown */.equals/* : unknown */("Character")/* : unknown */){
			return new Some(new Tuple2Impl(state/* : CompileState */, /* Primitive */.String/* : unknown */)/* : Tuple2Impl */)/* : Some */;
		}
		if (stripped/* : unknown */.equals/* : unknown */("var")/* : unknown */){
			return new Some(new Tuple2Impl(state/* : CompileState */, /* Primitive */.Unknown/* : unknown */)/* : Tuple2Impl */)/* : Some */;
		}
		if (stripped/* : unknown */.equals/* : unknown */("boolean")/* : unknown */){
			return new Some(new Tuple2Impl(state/* : CompileState */, /* Primitive */.Boolean/* : unknown */)/* : Tuple2Impl */)/* : Some */;
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
		if (base/* : string */.equals/* : unknown */("BiFunction")/* : unknown */){
			return new Tuple2Impl(state/* : CompileState */, new FunctionType(Lists/* : Lists */.of/* : (arg0 : T[]) => List<T> */(children/* : unknown */.get/* : unknown */(0/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */, children/* : unknown */.get/* : unknown */(1/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : List<T> */, children/* : unknown */.get/* : unknown */(2/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : FunctionType */)/* : Tuple2Impl */;
		}
		if (base/* : string */.equals/* : unknown */("Function")/* : unknown */){
			return new Tuple2Impl(state/* : CompileState */, new FunctionType(Lists/* : Lists */.of/* : (arg0 : T[]) => List<T> */(children/* : unknown */.get/* : unknown */(0/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : List<T> */, children/* : unknown */.get/* : unknown */(1/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : FunctionType */)/* : Tuple2Impl */;
		}
		if (base/* : string */.equals/* : unknown */("Predicate")/* : unknown */){
			return new Tuple2Impl(state/* : CompileState */, new FunctionType(Lists/* : Lists */.of/* : (arg0 : T[]) => List<T> */(children/* : unknown */.get/* : unknown */(0/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : List<T> */, /* Primitive */.Boolean/* : unknown */)/* : FunctionType */)/* : Tuple2Impl */;
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
		if (argument/* : Argument */._variant/* : unknown */ === ArgumentVariant.Type/* : unknown */){
			return new Some(type/* : () => Type */)/* : Some */;
		}
		else {
			return new None<Type>()/* : None<Type> */;
		}
	}
	/* private static */ argument(state : CompileState, input : string) : Option<[CompileState, Argument]> {
		if (input/* : string */.isBlank/* : unknown */()/* : unknown */){
			return new Some(new Tuple2Impl(state/* : CompileState */, new Whitespace()/* : Whitespace */)/* : Tuple2Impl */)/* : Some */;
		}
		return parseType/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Type]> */(state/* : CompileState */, input/* : string */)/* : Option<[CompileState, Type]> */.map/* : (arg0 : (arg0 : [CompileState, Type]) => R) => Option<R> */((tuple : [CompileState, Type]) => new Tuple2Impl(tuple/* : [string, DivideState] */[0/* : number */]()/* : unknown */, tuple/* : [string, DivideState] */[1/* : number */]()/* : unknown */)/* : Tuple2Impl */)/* : Option<R> */;
	}
	/* private static  */ last<T>(input : string, infix : string, mapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return infix/* : string */(input/* : string */, infix/* : string */, Main/* : Main */.findLast/* : unknown */, mapper/* : (arg0 : T) => R */)/* : unknown */;
	}
	/* private static */ findLast(input : string, infix : string) : Option<number> {
		let index = input/* : string */.lastIndexOf/* : unknown */(infix/* : string */)/* : unknown */;
		if (index/* : T */ === -1/* : number */){
			return new None<number>()/* : None<number> */;
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
		if (index/* : T */ === -1/* : number */){
			return new None<number>()/* : None<number> */;
		}
		return new Some(index/* : T */)/* : Some */;
	}
	/* private static */ generatePlaceholder(input : string) : string {
		let replaced = input/* : string */.replace/* : unknown */("/*", "content-start")/* : unknown */.replace/* : unknown */("*/", "content-end")/* : unknown */;
		return "/* " + replaced + " */";
	}
	/* private static */ createDebugString(type : Type) : string {
		if (!Main/* : Main */.isDebug/* : unknown */){
			return "";
		}
		return generatePlaceholder/* : (arg0 : string) => string */(": " + type/* : () => Type */.generate/* : unknown */()/* : unknown */)/* : string */;
	}
}
/*  */