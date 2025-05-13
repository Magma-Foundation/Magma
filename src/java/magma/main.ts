/* private */interface Tuple2<A, B>/*   */ {
	left() : A;
	right() : B;
}
enum OptionVariant {
	Some,
	None
}
/* private sealed */interface Option<T>/*  */ {
	_OptionVariant : OptionVariant;
	map<R>(mapper : (arg0 : T) => R) : Option<R>;
	isPresent() : boolean;
	orElse(other : T) : T;
	filter(predicate : (arg0 : T) => boolean) : Option<T>;
	orElseGet(supplier : () => T) : T;
	or(other : () => Option<T>) : Option<T>;
	flatMap<R>(mapper : (arg0 : T) => Option<R>) : Option<R>;
	isEmpty() : boolean;
	and<R>(other : () => Option<R>) : Option<[T, R]>;
	ifPresent(consumer : (arg0 : T) => void) : void;
}
/* private */interface Collector<T, C>/*   */ {
	createInitial() : C;
	fold(current : C, element : T) : C;
}
/* private */interface Query<T>/*   */ {
	fold<R>(initial : R, folder : (arg0 : R, arg1 : T) => R) : R;
	map<R>(mapper : (arg0 : T) => R) : Query<R>;
	collect<R>(collector : Collector<T, R>) : R;
	filter(predicate : (arg0 : T) => boolean) : Query<T>;
	next() : Option<T>;
	flatMap<R>(f : (arg0 : T) => Query<R>) : Query<R>;
	zip<R>(other : Query<R>) : Query<[T, R]>;
}
/* private */interface List<T>/*   */ {
	addLast(element : T) : List<T>;
	query() : Query<T>;
	removeLast() : Option<[List<T>, T]>;
	get(index : number) : Option<T>;
	size() : number;
	isEmpty() : boolean;
	addFirst(element : T) : List<T>;
	iterateWithIndices() : Query<[number, T]>;
	removeFirst() : Option<[T, List<T>]>;
	addAllLast(others : List<T>) : List<T>;
	last() : Option<T>;
	iterateReversed() : Query<T>;
	mapLast(mapper : (arg0 : T) => T) : List<T>;
	addAllFirst(others : List<T>) : List<T>;
	contains(element : T) : boolean;
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
enum ValueVariant {
	BooleanValue,
	Cast,
	DataAccess,
	IndexValue,
	Invokable,
	Lambda,
	Not,
	Operation,
	Placeholder,
	StringValue,
	SymbolValue,
	TupleNode
}
/* private sealed */interface Value/*  */ {
	_ValueVariant : ValueVariant;
	generate() : string;
	type() : Type;
}
/* private */interface LambdaValue/*  */ {
	generate() : string;
}
enum CallerVariant {
	ConstructionCaller,
	Value
}
/* private sealed */interface Caller/*  */ {
	_CallerVariant : CallerVariant;
	generate() : string;
}
/* private */interface BaseType/*  */ {
	variants() : List<string>;
	name() : string;
}
/* private */interface FindableType/*  */ {
	find(name : string) : Option<Type>;
	name() : string;
	findBase() : Option<BaseType>;
}
/* private */interface Definition/*  */ {
	generate() : string;
	mapType(mapper : (arg0 : Type) => Type) : Definition;
	generateWithParams(joinedParameters : string) : string;
	createDefinition(paramTypes : List<Type>) : Definition;
	findName() : string;
	findType() : Type;
	containsAnnotation(annotation : string) : boolean;
	removeAnnotations() : Definition;
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
enum IncompleteClassSegmentVariant {
	ClassDefinition,
	ClassInitialization,
	IncompleteClassSegmentWrapper,
	MethodPrototype,
	Placeholder,
	StructurePrototype,
	Whitespace
}
/* private sealed */interface IncompleteClassSegment/*  */ {
	_IncompleteClassSegmentVariant : IncompleteClassSegmentVariant;
	maybeCreateDefinition() : Option<Definition>;
}
/* private @ */interface Actual/*  */ {
}
enum ResultVariant {
	Ok,
	Err
}
/* private sealed */interface Result<T, X>/*  */ {
	_ResultVariant : ResultVariant;
	mapValue<R>(mapper : (arg0 : T) => R) : Result<R, X>;
	match<R>(whenOk : (arg0 : T) => R, whenErr : (arg0 : X) => R) : R;
}
/* private */interface IOError/*  */ {
	display() : string;
}
/* private */interface Path/*  */ {
	readString() : Result<string, IOError>;
	writeString(output : string) : Option<IOError>;
	resolve(childName : string) : Path;
}
/* private static final */class None<T>/*  */ implements Option<T> {
	_OptionVariant : OptionVariant = OptionVariant.None/* : OptionVariant */;
	public map<R>(mapper : (arg0 : T) => R) : Option<R> {
		return new None()/* : None */;
	}
	public isPresent() : boolean {
		return false;
	}
	public orElse(other : T) : T {
		return other/* : T */;
	}
	public filter(predicate : (arg0 : T) => boolean) : Option<T> {
		return new None()/* : None */;
	}
	public orElseGet(supplier : () => T) : T {
		return supplier/* : () => T */()/* : T */;
	}
	public or(other : () => Option<T>) : Option<T> {
		return other/* : () => Option<T> */()/* : Option<T> */;
	}
	public flatMap<R>(mapper : (arg0 : T) => Option<R>) : Option<R> {
		return new None()/* : None */;
	}
	public isEmpty() : boolean {
		return true;
	}
	public and<R>(other : () => Option<R>) : Option<[T, R]> {
		return new None()/* : None */;
	}
	public ifPresent(consumer : (arg0 : T) => void) : void {
	}
}
/* private */class Some<T>/*  */ implements Option<T> {
	value : T;
	constructor (value : T) {
		this/* : unknown */.value/* : unknown */ = value/* : unknown */;
	}
	_OptionVariant : OptionVariant = OptionVariant.Some/* : OptionVariant */;
	public map<R>(mapper : (arg0 : T) => R) : Option<R> {
		return new Some(mapper/* : (arg0 : T) => R */(this/* : Some */.value/* : T */)/* : R */)/* : Some */;
	}
	public isPresent() : boolean {
		return true;
	}
	public orElse(other : T) : T {
		return this/* : Some */.value/* : T */;
	}
	public filter(predicate : (arg0 : T) => boolean) : Option<T> {
		if (predicate/* : (arg0 : T) => boolean */(this/* : Some */.value/* : T */)/* : boolean */){
			return this/* : Some */;
		}
		return new None()/* : None */;
	}
	public orElseGet(supplier : () => T) : T {
		return this/* : Some */.value/* : T */;
	}
	public or(other : () => Option<T>) : Option<T> {
		return this/* : Some */;
	}
	public flatMap<R>(mapper : (arg0 : T) => Option<R>) : Option<R> {
		return mapper/* : (arg0 : T) => Option<R> */(this/* : Some */.value/* : T */)/* : Option<R> */;
	}
	public isEmpty() : boolean {
		return false;
	}
	public and<R>(other : () => Option<R>) : Option<[T, R]> {
		return other/* : () => Option<R> */()/* : Option<R> */.map/* : (arg0 : (arg0 : R) => R) => Option<R> */((otherValue : R) => [this/* : Some */.value/* : T */, otherValue/* : R */])/* : Option<R> */;
	}
	public ifPresent(consumer : (arg0 : T) => void) : void {
		/* consumer.accept(this.value) */;
	}
}
/* private static */class SingleHead<T>/*  */ implements Head<T> {
	private final value : T;
	private retrieved : boolean;
	constructor (value : T) {
		this/* : SingleHead */.value/* : T */ = value/* : T */;
		this/* : SingleHead */.retrieved/* : boolean */ = false;
	}
	public next() : Option<T> {
		if (this/* : SingleHead */.retrieved/* : boolean */){
			return new None()/* : None */;
		}
		this/* : SingleHead */.retrieved/* : boolean */ = true;
		return new Some(this/* : SingleHead */.value/* : T */)/* : Some */;
	}
}
/* private static */class EmptyHead<T>/*  */ implements Head<T> {
	public next() : Option<T> {
		return new None()/* : None */;
	}
}
/* private */class HeadedQuery<T>/*  */ implements Query<T> {
	head : Head<T>;
	constructor (head : Head<T>) {
		this/* : unknown */.head/* : unknown */ = head/* : unknown */;
	}
	public fold<R>(initial : R, folder : (arg0 : R, arg1 : T) => R) : R {
		let current : R = initial/* : R */;
		while (true){
			let finalCurrent : R = current/* : R */;
			let option : Option<R> = this/* : HeadedQuery */.head/* : Head<T> */.next/* : () => Option<T> */()/* : Option<T> */.map/* : (arg0 : (arg0 : T) => R) => Option<R> */((inner : T) => folder/* : (arg0 : R, arg1 : T) => R */(finalCurrent/* : R */, inner/* : T */)/* : R */)/* : Option<R> */;
			if (option/* : Option<R> */._OptionVariant/* : unknown */ === OptionVariant.Some/* : unknown */){
				let some : Some<R> = option/* : Option<R> */ as Some<R>;
				current/* : R */ = some/* : Some<R> */.value/* : R */;
			}
			else {
				return current/* : R */;
			}
		}
	}
	public map<R>(mapper : (arg0 : T) => R) : Query<R> {
		return new HeadedQuery(new MapHead(this/* : HeadedQuery */.head/* : Head<T> */, mapper/* : (arg0 : T) => R */)/* : MapHead */)/* : HeadedQuery */;
	}
	public collect<R>(collector : Collector<T, R>) : R {
		return this/* : HeadedQuery */.fold/* : (arg0 : R, arg1 : (arg0 : R, arg1 : T) => R) => R */(collector/* : Collector<T, R> */.createInitial/* : () => C */()/* : C */, collector/* : Collector<T, R> */.fold/* : unknown */)/* : R */;
	}
	public filter(predicate : (arg0 : T) => boolean) : Query<T> {
		return this/* : HeadedQuery */.flatMap/* : (arg0 : (arg0 : T) => Query<R>) => Query<R> */((element : T) => {
			if (predicate/* : (arg0 : T) => boolean */(element/* : T */)/* : boolean */){
				return new HeadedQuery(new SingleHead(element/* : T */)/* : SingleHead */)/* : HeadedQuery */;
			}
			return new HeadedQuery(new EmptyHead()/* : EmptyHead */)/* : HeadedQuery */;
		})/* : Query<R> */;
	}
	public next() : Option<T> {
		return this/* : HeadedQuery */.head/* : Head<T> */.next/* : () => Option<T> */()/* : Option<T> */;
	}
	public flatMap<R>(f : (arg0 : T) => Query<R>) : Query<R> {
		return new HeadedQuery(new FlatMapHead(this/* : HeadedQuery */.head/* : Head<T> */, f/* : (arg0 : T) => Query<R> */)/* : FlatMapHead */)/* : HeadedQuery */;
	}
	public zip<R>(other : Query<R>) : Query<[T, R]> {
		return new HeadedQuery(new ZipHead(this/* : HeadedQuery */.head/* : Head<T> */, other/* : Query<R> */)/* : ZipHead */)/* : HeadedQuery */;
	}
}
/* private static */class RangeHead/*  */ implements Head<number> {
	private final length : number;
	private counter : number;
	constructor (length : number) {
		this/* : RangeHead */.length/* : number */ = length/* : number */;
		this/* : RangeHead */.counter/* : number */ = 0/* : number */;
	}
	public next() : Option<number> {
		if (this/* : RangeHead */.counter/* : number */ < this/* : RangeHead */.length/* : unknown */){
			let value : number = this/* : RangeHead */.counter/* : number */;
			/* this.counter++ */;
			return new Some(value/* : number */)/* : Some */;
		}
		return new None()/* : None */;
	}
}
/* private static */class Lists/*  */ {
	public static empty<T>() : List<T>;
	public static of<T>(elements : T[]) : List<T>;
}
/* private */class ImmutableDefinition/*  */ implements Definition {
	annotations : List<string>;
	modifiers : List<string>;
	name : string;
	type : Type;
	typeParams : List<string>;
	constructor (annotations : List<string>, modifiers : List<string>, name : string, type : Type, typeParams : List<string>) {
		this/* : unknown */.annotations/* : unknown */ = annotations/* : unknown */;
		this/* : unknown */.modifiers/* : unknown */ = modifiers/* : unknown */;
		this/* : unknown */.name/* : unknown */ = name/* : unknown */;
		this/* : unknown */.type/* : unknown */ = type/* : unknown */;
		this/* : unknown */.typeParams/* : unknown */ = typeParams/* : unknown */;
	}
	public static createSimpleDefinition(name : string, type : Type) : Definition {
		return new ImmutableDefinition(Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, name/* : string */, type/* : Type */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */)/* : ImmutableDefinition */;
	}
	public findName() : string {
		return this/* : ImmutableDefinition */.name/* : string */;
	}
	public findType() : Type {
		return this/* : ImmutableDefinition */.type/* : Type */;
	}
	public generate() : string {
		return this/* : ImmutableDefinition */.generateWithParams/* : (arg0 : string) => string */("")/* : string */;
	}
	private generateType() : string {
		if (this/* : ImmutableDefinition */.type/* : Type */.equals/* : unknown */(Primitive/* : Primitive */.Unknown/* : unknown */)/* : unknown */){
			return "";
		}
		return " : " + this/* : ImmutableDefinition */.type/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
	private joinModifiers() : string {
		return this/* : ImmutableDefinition */.modifiers/* : List<string> */.query/* : () => Query<T> */()/* : Query<T> */.map/* : (arg0 : (arg0 : T) => R) => Query<R> */((value : T) => value/* : T */ + " ")/* : Query<R> */.collect/* : (arg0 : Collector<T, R>) => R */(new Joiner("")/* : Joiner */)/* : R */.orElse/* : unknown */("")/* : unknown */;
	}
	private joinTypeParams() : string {
		return this/* : ImmutableDefinition */.typeParams/* : List<string> */.query/* : () => Query<T> */()/* : Query<T> */.collect/* : (arg0 : Collector<T, R>) => R */(new Joiner(", ")/* : Joiner */)/* : R */.map/* : unknown */((inner) => "<" + inner + ">")/* : unknown */.orElse/* : unknown */("")/* : unknown */;
	}
	public mapType(mapper : (arg0 : Type) => Type) : Definition {
		return new ImmutableDefinition(this/* : ImmutableDefinition */.annotations/* : List<string> */, this/* : ImmutableDefinition */.modifiers/* : List<string> */, this/* : ImmutableDefinition */.name/* : string */, mapper/* : (arg0 : Type) => Type */(this/* : ImmutableDefinition */.type/* : Type */)/* : Type */, this/* : ImmutableDefinition */.typeParams/* : List<string> */)/* : ImmutableDefinition */;
	}
	public generateWithParams(joinedParameters : string) : string {
		let joinedAnnotations = this/* : ImmutableDefinition */.annotations/* : List<string> */.query/* : () => Query<T> */()/* : Query<T> */.map/* : (arg0 : (arg0 : T) => R) => Query<R> */((value : T) => "@" + value + " ")/* : Query<R> */.collect/* : (arg0 : Collector<T, R>) => R */(Joiner/* : Joiner */.empty/* : () => Joiner */()/* : Joiner */)/* : R */.orElse/* : unknown */("")/* : unknown */;
		let joined : string = this/* : ImmutableDefinition */.joinTypeParams/* : () => string */()/* : string */;
		let before : string = this/* : ImmutableDefinition */.joinModifiers/* : () => string */()/* : string */;
		let typeString : string = this/* : ImmutableDefinition */.generateType/* : () => string */()/* : string */;
		return joinedAnnotations/* : unknown */ + before/* : string */ + this/* : ImmutableDefinition */.name/* : string */ + joined/* : string */ + joinedParameters/* : string */ + typeString/* : string */;
	}
	public createDefinition(paramTypes : List<Type>) : Definition {
		let type1 : Type = new FunctionType(paramTypes/* : List<Type> */, this/* : ImmutableDefinition */.type/* : Type */)/* : FunctionType */;
		return new ImmutableDefinition(this/* : ImmutableDefinition */.annotations/* : List<string> */, this/* : ImmutableDefinition */.modifiers/* : List<string> */, this/* : ImmutableDefinition */.name/* : string */, type1/* : Type */, this/* : ImmutableDefinition */.typeParams/* : List<string> */)/* : ImmutableDefinition */;
	}
	public containsAnnotation(annotation : string) : boolean {
		return this/* : ImmutableDefinition */.annotations/* : List<string> */.contains/* : (arg0 : string) => boolean */(annotation/* : string */)/* : boolean */;
	}
	public removeAnnotations() : Definition {
		return new ImmutableDefinition(Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, this/* : ImmutableDefinition */.modifiers/* : List<string> */, this/* : ImmutableDefinition */.name/* : string */, this/* : ImmutableDefinition */.type/* : Type */, this/* : ImmutableDefinition */.typeParams/* : List<string> */)/* : ImmutableDefinition */;
	}
	public toString() : string {
		return "ImmutableDefinition[" + "annotations=" + this/* : ImmutableDefinition */.annotations/* : List<string> */ + ", " + "maybeBefore=" + this/* : ImmutableDefinition */.modifiers/* : List<string> */ + ", " + "findName=" + this/* : ImmutableDefinition */.name/* : string */ + ", " + "findType=" + this/* : ImmutableDefinition */.type/* : Type */ + ", " + "typeParams=" + this/* : ImmutableDefinition */.typeParams/* : List<string> */ + /*  ']' */;
	}
}
/* private */class ObjectType/*  */ implements FindableType, BaseType {
	name : string;
	typeParams : List<string>;
	definitions : List<Definition>;
	variants : List<string>;
	constructor (name : string, typeParams : List<string>, definitions : List<Definition>, variants : List<string>) {
		this/* : unknown */.name/* : unknown */ = name/* : unknown */;
		this/* : unknown */.typeParams/* : unknown */ = typeParams/* : unknown */;
		this/* : unknown */.definitions/* : unknown */ = definitions/* : unknown */;
		this/* : unknown */.variants/* : unknown */ = variants/* : unknown */;
	}
	public generate() : string {
		return this/* : ObjectType */.name/* : string */;
	}
	public replace(mapping : Map<string, Type>) : Type {
		return new ObjectType(this/* : ObjectType */.name/* : string */, this/* : ObjectType */.typeParams/* : List<string> */, this/* : ObjectType */.definitions/* : List<Definition> */.query/* : () => Query<T> */()/* : Query<T> */.map/* : (arg0 : (arg0 : T) => R) => Query<R> */((definition : T) => definition/* : T */.mapType/* : unknown */((type) => type/* : unknown */.replace/* : unknown */(mapping/* : Map<string, Type> */)/* : unknown */)/* : unknown */)/* : Query<R> */.collect/* : (arg0 : Collector<T, R>) => R */(new ListCollector()/* : ListCollector */)/* : R */, this/* : ObjectType */.variants/* : List<string> */)/* : ObjectType */;
	}
	public find(name : string) : Option<Type> {
		return this/* : ObjectType */.definitions/* : List<Definition> */.query/* : () => Query<T> */()/* : Query<T> */.filter/* : (arg0 : (arg0 : T) => boolean) => Query<T> */((definition : T) => definition/* : T */.findName/* : unknown */()/* : unknown */.equals/* : unknown */(name/* : string */)/* : unknown */)/* : Query<T> */.map/* : (arg0 : (arg0 : T) => R) => Option<R> */(Definition/* : Definition */.findType/* : unknown */)/* : Option<R> */.next/* : () => Option<T> */()/* : Option<T> */;
	}
	public findBase() : Option<BaseType> {
		return new Some(this/* : ObjectType */)/* : Some */;
	}
	public findName() : Option<string> {
		return new Some(this/* : ObjectType */.name/* : string */)/* : Some */;
	}
}
/* private */class TypeParam/*  */ implements Type {
	value : string;
	constructor (value : string) {
		this/* : unknown */.value/* : unknown */ = value/* : unknown */;
	}
	public generate() : string {
		return this/* : TypeParam */.value/* : string */;
	}
	public replace(mapping : Map<string, Type>) : Type {
		return mapping/* : Map<string, Type> */.find/* : (arg0 : string) => Option<V> */(this/* : TypeParam */.value/* : string */)/* : Option<V> */.orElse/* : (arg0 : V) => T */(this/* : TypeParam */)/* : T */;
	}
	public findName() : Option<string> {
		return new None()/* : None */;
	}
}
/* private */class CompileState/*  */ {
	structures : List<string>;
	definitions : List<List<Definition>>;
	objectTypes : List<ObjectType>;
	structNames : List<[string, List<string>]>;
	typeParams : List<string>;
	typeRegister : Option<Type>;
	functionSegments : List<FunctionSegment>;
	constructor (structures : List<string>, definitions : List<List<Definition>>, objectTypes : List<ObjectType>, structNames : List<[string, List<string>]>, typeParams : List<string>, typeRegister : Option<Type>, functionSegments : List<FunctionSegment>) {
		this/* : unknown */.structures/* : unknown */ = structures/* : unknown */;
		this/* : unknown */.definitions/* : unknown */ = definitions/* : unknown */;
		this/* : unknown */.objectTypes/* : unknown */ = objectTypes/* : unknown */;
		this/* : unknown */.structNames/* : unknown */ = structNames/* : unknown */;
		this/* : unknown */.typeParams/* : unknown */ = typeParams/* : unknown */;
		this/* : unknown */.typeRegister/* : unknown */ = typeRegister/* : unknown */;
		this/* : unknown */.functionSegments/* : unknown */ = functionSegments/* : unknown */;
	}
	public static createInitial() : CompileState {
		return new CompileState(Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, Lists/* : Lists */.of/* : (arg0 : T[]) => List<T> */(Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */)/* : List<T> */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, new None()/* : None */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */)/* : CompileState */;
	}
	private resolveValue(name : string) : Option<Type> {
		return this/* : CompileState */.definitions/* : List<List<Definition>> */.iterateReversed/* : () => Query<T> */()/* : Query<T> */.flatMap/* : (arg0 : (arg0 : T) => Query<R>) => Query<R> */(List/* : List */.query/* : unknown */)/* : Query<R> */.filter/* : (arg0 : (arg0 : T) => boolean) => Option<T> */((definition : T) => definition/* : T */.findName/* : unknown */()/* : unknown */.equals/* : unknown */(name/* : string */)/* : unknown */)/* : Option<T> */.next/* : () => Option<T> */()/* : Option<T> */.map/* : (arg0 : (arg0 : T) => R) => Option<R> */(Definition/* : Definition */.findType/* : unknown */)/* : Option<R> */;
	}
	public addStructure(structure : string) : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : List<string> */.addLast/* : (arg0 : string) => List<T> */(structure/* : string */)/* : List<T> */, this/* : CompileState */.definitions/* : List<List<Definition>> */, this/* : CompileState */.objectTypes/* : List<ObjectType> */, this/* : CompileState */.structNames/* : List<[string, List<string>]> */, this/* : CompileState */.typeParams/* : List<string> */, this/* : CompileState */.typeRegister/* : Option<Type> */, this/* : CompileState */.functionSegments/* : List<FunctionSegment> */)/* : CompileState */;
	}
	public defineAll(definitions : List<Definition>) : CompileState {
		let defined : List<T> = this/* : CompileState */.definitions/* : List<List<Definition>> */.mapLast/* : (arg0 : (arg0 : List<Definition>) => T) => List<T> */((frame : List<Definition>) => frame/* : List<Definition> */.addAllLast/* : (arg0 : List<T>) => List<T> */(definitions/* : List<Definition> */)/* : List<T> */)/* : List<T> */;
		return new CompileState(this/* : CompileState */.structures/* : List<string> */, defined/* : List<T> */, this/* : CompileState */.objectTypes/* : List<ObjectType> */, this/* : CompileState */.structNames/* : List<[string, List<string>]> */, this/* : CompileState */.typeParams/* : List<string> */, this/* : CompileState */.typeRegister/* : Option<Type> */, this/* : CompileState */.functionSegments/* : List<FunctionSegment> */)/* : CompileState */;
	}
	public resolveType(name : string) : Option<Type> {
		let maybe : Option<[string, List<string>]> = this/* : CompileState */.structNames/* : List<[string, List<string>]> */.last/* : () => Option<T> */()/* : Option<T> */.filter/* : (arg0 : (arg0 : T) => boolean) => Option<T> */((inner : T) => inner/* : T */.left/* : unknown */()/* : unknown */.equals/* : unknown */(name/* : string */)/* : unknown */)/* : Option<T> */;
		if (maybe/* : Option<[string, List<string>]> */._OptionVariant/* : unknown */ === OptionVariant.Some/* : unknown */){
			let some : Some<[string, List<string>]> = maybe/* : Option<[string, List<string>]> */ as Some<[string, List<string>]>;
			let found : [string, List<string>] = some/* : Some<[string, List<string>]> */.value/* : [string, List<string>] */;
			return new Some(new ObjectType(found/* : [string, List<string>] */[0/* : number */], this/* : CompileState */.typeParams/* : List<string> */, this/* : CompileState */.definitions/* : List<List<Definition>> */.last/* : () => Option<T> */()/* : Option<T> */.orElse/* : (arg0 : T) => T */(Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */)/* : T */, found/* : [string, List<string>] */[1/* : number */])/* : ObjectType */)/* : Some */;
		}
		let maybeTypeParam : Option<T> = this/* : CompileState */.typeParams/* : List<string> */.query/* : () => Query<T> */()/* : Query<T> */.filter/* : (arg0 : (arg0 : T) => boolean) => Query<T> */((param : T) => param/* : T */.equals/* : unknown */(name/* : string */)/* : unknown */)/* : Query<T> */.next/* : () => Option<T> */()/* : Option<T> */;
		if (maybeTypeParam/* : Option<T> */._OptionVariant/* : unknown */ === OptionVariant.Some/* : unknown */){
			let some : Some<string> = maybeTypeParam/* : Option<T> */ as Some<string>;
			return new Some(new TypeParam(some/* : Some<[string, List<string>]> */.value/* : [string, List<string>] */)/* : TypeParam */)/* : Some */;
		}
		return this/* : CompileState */.objectTypes/* : List<ObjectType> */.query/* : () => Query<T> */()/* : Query<T> */.filter/* : (arg0 : (arg0 : T) => boolean) => Query<T> */((type : T) => type/* : T */.name/* : unknown */.equals/* : unknown */(name/* : string */)/* : unknown */)/* : Query<T> */.next/* : () => Option<T> */()/* : Option<T> */.map/* : (arg0 : (arg0 : T) => R) => Option<R> */((type : T) => type/* : T */)/* : Option<R> */;
	}
	public define(definition : Definition) : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : List<string> */, this/* : CompileState */.definitions/* : List<List<Definition>> */.mapLast/* : (arg0 : (arg0 : List<Definition>) => T) => List<T> */((frame : List<Definition>) => frame/* : List<Definition> */.addLast/* : (arg0 : Definition) => List<T> */(definition/* : Definition */)/* : List<T> */)/* : List<T> */, this/* : CompileState */.objectTypes/* : List<ObjectType> */, this/* : CompileState */.structNames/* : List<[string, List<string>]> */, this/* : CompileState */.typeParams/* : List<string> */, this/* : CompileState */.typeRegister/* : Option<Type> */, this/* : CompileState */.functionSegments/* : List<FunctionSegment> */)/* : CompileState */;
	}
	public pushStructName(definition : Tuple2Impl<string, List<string>>) : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : List<string> */, this/* : CompileState */.definitions/* : List<List<Definition>> */, this/* : CompileState */.objectTypes/* : List<ObjectType> */, this/* : CompileState */.structNames/* : List<[string, List<string>]> */.addLast/* : (arg0 : [string, List<string>]) => List<T> */(definition/* : Tuple2Impl<string, List<string>> */)/* : List<T> */, this/* : CompileState */.typeParams/* : List<string> */, this/* : CompileState */.typeRegister/* : Option<Type> */, this/* : CompileState */.functionSegments/* : List<FunctionSegment> */)/* : CompileState */;
	}
	public withTypeParams(typeParams : List<string>) : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : List<string> */, this/* : CompileState */.definitions/* : List<List<Definition>> */, this/* : CompileState */.objectTypes/* : List<ObjectType> */, this/* : CompileState */.structNames/* : List<[string, List<string>]> */, this/* : CompileState */.typeParams/* : List<string> */.addAllLast/* : (arg0 : List<T>) => List<T> */(typeParams/* : List<string> */)/* : List<T> */, this/* : CompileState */.typeRegister/* : Option<Type> */, this/* : CompileState */.functionSegments/* : List<FunctionSegment> */)/* : CompileState */;
	}
	public withExpectedType(type : Type) : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : List<string> */, this/* : CompileState */.definitions/* : List<List<Definition>> */, this/* : CompileState */.objectTypes/* : List<ObjectType> */, this/* : CompileState */.structNames/* : List<[string, List<string>]> */, this/* : CompileState */.typeParams/* : List<string> */, new Some(type/* : Type */)/* : Some */, this/* : CompileState */.functionSegments/* : List<FunctionSegment> */)/* : CompileState */;
	}
	public popStructName() : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : List<string> */, this/* : CompileState */.definitions/* : List<List<Definition>> */, this/* : CompileState */.objectTypes/* : List<ObjectType> */, this/* : CompileState */.structNames/* : List<[string, List<string>]> */.removeLast/* : () => Option<[List<T>, T]> */()/* : Option<[List<T>, T]> */.map/* : (arg0 : (arg0 : [List<T>, T]) => R) => Option<R> */(Tuple2/* : Tuple2 */.left/* : unknown */)/* : Option<R> */.orElse/* : (arg0 : T) => T */(this/* : CompileState */.structNames/* : List<[string, List<string>]> */)/* : T */, this/* : CompileState */.typeParams/* : List<string> */, this/* : CompileState */.typeRegister/* : Option<Type> */, this/* : CompileState */.functionSegments/* : List<FunctionSegment> */)/* : CompileState */;
	}
	public enterDefinitions() : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : List<string> */, this/* : CompileState */.definitions/* : List<List<Definition>> */.addLast/* : (arg0 : List<Definition>) => List<T> */(Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */)/* : List<T> */, this/* : CompileState */.objectTypes/* : List<ObjectType> */, this/* : CompileState */.structNames/* : List<[string, List<string>]> */, this/* : CompileState */.typeParams/* : List<string> */, this/* : CompileState */.typeRegister/* : Option<Type> */, this/* : CompileState */.functionSegments/* : List<FunctionSegment> */)/* : CompileState */;
	}
	public exitDefinitions() : CompileState {
		let removed : T = this/* : CompileState */.definitions/* : List<List<Definition>> */.removeLast/* : () => Option<[List<T>, T]> */()/* : Option<[List<T>, T]> */.map/* : (arg0 : (arg0 : [List<T>, T]) => R) => Option<R> */(Tuple2/* : Tuple2 */.left/* : unknown */)/* : Option<R> */.orElse/* : (arg0 : T) => T */(this/* : CompileState */.definitions/* : List<List<Definition>> */)/* : T */;
		return new CompileState(this/* : CompileState */.structures/* : List<string> */, removed/* : T */, this/* : CompileState */.objectTypes/* : List<ObjectType> */, this/* : CompileState */.structNames/* : List<[string, List<string>]> */, this/* : CompileState */.typeParams/* : List<string> */, this/* : CompileState */.typeRegister/* : Option<Type> */, this/* : CompileState */.functionSegments/* : List<FunctionSegment> */)/* : CompileState */;
	}
	public addType(thisType : ObjectType) : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : List<string> */, this/* : CompileState */.definitions/* : List<List<Definition>> */, this/* : CompileState */.objectTypes/* : List<ObjectType> */.addLast/* : (arg0 : ObjectType) => List<T> */(thisType/* : ObjectType */)/* : List<T> */, this/* : CompileState */.structNames/* : List<[string, List<string>]> */, this/* : CompileState */.typeParams/* : List<string> */, this/* : CompileState */.typeRegister/* : Option<Type> */, this/* : CompileState */.functionSegments/* : List<FunctionSegment> */)/* : CompileState */;
	}
	public addFunctionSegment(segment : FunctionSegment) : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : List<string> */, this/* : CompileState */.definitions/* : List<List<Definition>> */, this/* : CompileState */.objectTypes/* : List<ObjectType> */, this/* : CompileState */.structNames/* : List<[string, List<string>]> */, this/* : CompileState */.typeParams/* : List<string> */, this/* : CompileState */.typeRegister/* : Option<Type> */, this/* : CompileState */.functionSegments/* : List<FunctionSegment> */.addLast/* : (arg0 : FunctionSegment) => List<T> */(segment/* : FunctionSegment */)/* : List<T> */)/* : CompileState */;
	}
	public clearFunctionSegments() : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : List<string> */, this/* : CompileState */.definitions/* : List<List<Definition>> */, this/* : CompileState */.objectTypes/* : List<ObjectType> */, this/* : CompileState */.structNames/* : List<[string, List<string>]> */, this/* : CompileState */.typeParams/* : List<string> */, this/* : CompileState */.typeRegister/* : Option<Type> */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */)/* : CompileState */;
	}
	private isCurrentStructName(stripped : string) : boolean {
		return stripped/* : string */.equals/* : unknown */(this/* : CompileState */.structNames/* : List<[string, List<string>]> */.last/* : () => Option<T> */()/* : Option<T> */.map/* : (arg0 : (arg0 : T) => R) => Option<R> */(Tuple2/* : Tuple2 */.left/* : unknown */)/* : Option<R> */.orElse/* : (arg0 : T) => T */("")/* : T */)/* : unknown */;
	}
}
/* private static */class DivideState/*  */ {
	private final input : string;
	private final index : number;
	private depth : number;
	private segments : List<string>;
	private buffer : string;
	constructor (input : string, index : number, segments : List<string>, buffer : string, depth : number) {
		this/* : DivideState */.segments/* : List<string> */ = segments/* : List<string> */;
		this/* : DivideState */.buffer/* : string */ = buffer/* : string */;
		this/* : DivideState */.depth/* : number */ = depth/* : number */;
		this/* : DivideState */.input/* : string */ = input/* : string */;
		this/* : DivideState */.index/* : number */ = index/* : number */;
	}
	public static createInitial(input : string) : DivideState {
		return new DivideState(input/* : string */, 0/* : number */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, "", 0/* : number */)/* : DivideState */;
	}
	private advance() : DivideState {
		this/* : DivideState */.segments/* : List<string> */ = this/* : DivideState */.segments/* : List<string> */.addLast/* : (arg0 : string) => List<T> */(this/* : DivideState */.buffer/* : string */)/* : List<T> */;
		this/* : DivideState */.buffer/* : string */ = "";
		return this/* : DivideState */;
	}
	private append(c : string) : DivideState {
		this/* : DivideState */.buffer/* : string */ = this/* : DivideState */.buffer/* : string */ + c/* : string */;
		return this/* : DivideState */;
	}
	public enter() : DivideState {
		/* this.depth++ */;
		return this/* : DivideState */;
	}
	public isLevel() : boolean {
		return this/* : DivideState */.depth/* : number */ === 0/* : number */;
	}
	public exit() : DivideState {
		/* this.depth-- */;
		return this/* : DivideState */;
	}
	public isShallow() : boolean {
		return this/* : DivideState */.depth/* : number */ === 1/* : number */;
	}
	public pop() : Option<[string, DivideState]> {
		if (this/* : DivideState */.index/* : number */ < this/* : DivideState */.input/* : unknown */.length/* : unknown */()/* : unknown */){
			let c = this/* : DivideState */.input/* : string */.charAt/* : unknown */(this/* : DivideState */.index/* : number */)/* : unknown */;
			return new Some([c/* : unknown */, new DivideState(this/* : DivideState */.input/* : string */, this/* : DivideState */.index/* : number */ + 1/* : number */, this/* : DivideState */.segments/* : List<string> */, this/* : DivideState */.buffer/* : string */, this/* : DivideState */.depth/* : number */)/* : DivideState */])/* : Some */;
		}
		return new None()/* : None */;
	}
	public popAndAppendToTuple() : Option<[string, DivideState]> {
		return this/* : DivideState */.pop/* : () => Option<[string, DivideState]> */()/* : Option<[string, DivideState]> */.map/* : (arg0 : (arg0 : [string, DivideState]) => R) => Option<R> */((tuple : [string, DivideState]) => {
			let c = tuple/* : [string, DivideState] */[0/* : number */];
			let right = tuple/* : [string, DivideState] */[1/* : number */];
			return [c/* : unknown */, right/* : unknown */.append/* : unknown */(c/* : unknown */)/* : unknown */];
		})/* : Option<R> */;
	}
	public popAndAppendToOption() : Option<DivideState> {
		return this/* : DivideState */.popAndAppendToTuple/* : () => Option<[string, DivideState]> */()/* : Option<[string, DivideState]> */.map/* : (arg0 : (arg0 : [string, DivideState]) => R) => Option<R> */(Tuple2/* : Tuple2 */.right/* : unknown */)/* : Option<R> */;
	}
	public peek() : string {
		return this/* : DivideState */.input/* : string */.charAt/* : unknown */(this/* : DivideState */.index/* : number */)/* : unknown */;
	}
}
/* private */class Joiner/*  */ implements Collector<string, Option<string>> {
	delimiter : string;
	constructor (delimiter : string) {
		this/* : unknown */.delimiter/* : unknown */ = delimiter/* : unknown */;
	}
	private static empty() : Joiner {
		return new Joiner("")/* : Joiner */;
	}
	public createInitial() : Option<string> {
		return new None()/* : None */;
	}
	public fold(current : Option<string>, element : string) : Option<string> {
		return new Some(current/* : Option<string> */.map/* : (arg0 : (arg0 : string) => R) => Option<R> */((inner : string) => inner/* : string */ + this/* : Joiner */.delimiter/* : string */ + element/* : string */)/* : Option<R> */.orElse/* : (arg0 : T) => T */(element/* : string */)/* : T */)/* : Some */;
	}
}
/* private static */class ListCollector<T>/*  */ implements Collector<T, List<T>> {
	public createInitial() : List<T> {
		return Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */;
	}
	public fold(current : List<T>, element : T) : List<T> {
		return current/* : List<T> */.addLast/* : (arg0 : T) => List<T> */(element/* : T */)/* : List<T> */;
	}
}
/* private static */class FlatMapHead<T, R>/*  */ implements Head<R> {
	private final mapper : (arg0 : T) => Query<R>;
	private final head : Head<T>;
	private current : Option<Query<R>>;
	constructor (head : Head<T>, mapper : (arg0 : T) => Query<R>) {
		this/* : FlatMapHead */.mapper/* : (arg0 : T) => Query<R> */ = mapper/* : (arg0 : T) => Query<R> */;
		this/* : FlatMapHead */.current/* : Option<Query<R>> */ = new None()/* : None */;
		this/* : FlatMapHead */.head/* : Head<T> */ = head/* : Head<T> */;
	}
	public next() : Option<R> {
		while (true){
			if (this/* : FlatMapHead */.current/* : Option<Query<R>> */.isPresent/* : () => boolean */()/* : boolean */){
				let inner : Query<R> = this/* : FlatMapHead */.current/* : Option<Query<R>> */.orElse/* : (arg0 : Query<R>) => T */(/* null */)/* : T */;
				let maybe : Option<R> = inner/* : Query<R> */.next/* : () => Option<T> */()/* : Option<T> */;
				if (maybe/* : Option<R> */.isPresent/* : () => boolean */()/* : boolean */){
					return maybe/* : Option<R> */;
				}
				else {
					this/* : FlatMapHead */.current/* : Option<Query<R>> */ = new None()/* : None */;
				}
			}
			let outer : Option<T> = this/* : FlatMapHead */.head/* : Head<T> */.next/* : () => Option<T> */()/* : Option<T> */;
			if (outer/* : Option<T> */.isPresent/* : () => boolean */()/* : boolean */){
				this/* : FlatMapHead */.current/* : Option<Query<R>> */ = outer/* : Option<T> */.map/* : (arg0 : (arg0 : T) => R) => Option<R> */(this/* : FlatMapHead */.mapper/* : (arg0 : T) => Query<R> */)/* : Option<R> */;
			}
			else {
				return new None()/* : None */;
			}
		}
	}
}
/* private */class ArrayType/*  */ implements Type {
	right : Type;
	constructor (right : Type) {
		this/* : unknown */.right/* : unknown */ = right/* : unknown */;
	}
	public generate() : string {
		return this/* : ArrayType */.right/* : Type */.generate/* : unknown */()/* : unknown */ + "[]";
	}
	public replace(mapping : Map<string, Type>) : Type {
		return this/* : ArrayType */;
	}
	public findName() : Option<string> {
		return new None()/* : None */;
	}
}
/* private static final */class Whitespace/*  */ implements Argument, Parameter, ClassSegment, FunctionSegment, IncompleteClassSegment {
	_IncompleteClassSegmentVariant : IncompleteClassSegmentVariant = IncompleteClassSegmentVariant.Whitespace/* : IncompleteClassSegmentVariant */;
	public generate() : string {
		return "";
	}
	public maybeCreateDefinition() : Option<Definition> {
		return new None()/* : None */;
	}
}
/* private static */class Queries/*  */ {
	public static fromOption<T>(option : Option<T>) : Query<T> {
		let single : Option<Head<T>> = option/* : Option<T> */.map/* : (arg0 : (arg0 : T) => R) => Option<R> */(SingleHead/* : SingleHead */.new/* : unknown */)/* : Option<R> */;
		return new HeadedQuery(single/* : Option<Head<T>> */.orElseGet/* : (arg0 : () => T) => T */(EmptyHead/* : EmptyHead */.new/* : unknown */)/* : T */)/* : HeadedQuery */;
	}
}
/* private */class FunctionType/*  */ implements Type {
	arguments : List<Type>;
	returns : Type;
	constructor (arguments : List<Type>, returns : Type) {
		this/* : unknown */.arguments/* : unknown */ = arguments/* : unknown */;
		this/* : unknown */.returns/* : unknown */ = returns/* : unknown */;
	}
	public generate() : string {
		let joined = this/* : FunctionType */.arguments/* : List<Type> */()/* : unknown */.iterateWithIndices/* : unknown */()/* : unknown */.map/* : unknown */((pair) => "arg" + pair/* : unknown */.left/* : unknown */()/* : unknown */ + " : " + pair/* : unknown */.right/* : unknown */()/* : unknown */.generate/* : unknown */()/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return "(" + joined/* : unknown */ + ") => " + this/* : FunctionType */.returns/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
	public replace(mapping : Map<string, Type>) : Type {
		return new FunctionType(this/* : FunctionType */.arguments/* : List<Type> */.query/* : () => Query<T> */()/* : Query<T> */.map/* : (arg0 : (arg0 : T) => R) => Query<R> */((type : T) => type/* : T */.replace/* : unknown */(mapping/* : Map<string, Type> */)/* : unknown */)/* : Query<R> */.collect/* : (arg0 : Collector<T, R>) => R */(new ListCollector()/* : ListCollector */)/* : R */, this/* : FunctionType */.returns/* : Type */)/* : FunctionType */;
	}
	public findName() : Option<string> {
		return new None()/* : None */;
	}
}
/* private */class TupleType/*  */ implements Type {
	arguments : List<Type>;
	constructor (arguments : List<Type>) {
		this/* : unknown */.arguments/* : unknown */ = arguments/* : unknown */;
	}
	public generate() : string {
		let joinedArguments = this/* : TupleType */.arguments/* : List<Type> */.query/* : () => Query<T> */()/* : Query<T> */.map/* : (arg0 : (arg0 : T) => R) => Query<R> */(Type/* : Type */.generate/* : unknown */)/* : Query<R> */.collect/* : (arg0 : Collector<T, R>) => R */(new Joiner(", ")/* : Joiner */)/* : R */.orElse/* : unknown */("")/* : unknown */;
		return "[" + joinedArguments + "]";
	}
	public replace(mapping : Map<string, Type>) : Type {
		return this/* : TupleType */;
	}
	public findName() : Option<string> {
		return new None()/* : None */;
	}
}
/* private */class Template/*  */ implements FindableType {
	base : ObjectType;
	arguments : List<Type>;
	constructor (base : ObjectType, arguments : List<Type>) {
		this/* : unknown */.base/* : unknown */ = base/* : unknown */;
		this/* : unknown */.arguments/* : unknown */ = arguments/* : unknown */;
	}
	public generate() : string {
		let joinedArguments = this/* : Template */.arguments/* : List<Type> */.query/* : () => Query<T> */()/* : Query<T> */.map/* : (arg0 : (arg0 : T) => R) => Query<R> */(Type/* : Type */.generate/* : unknown */)/* : Query<R> */.collect/* : (arg0 : Collector<T, R>) => R */(new Joiner(", ")/* : Joiner */)/* : R */.map/* : unknown */((inner) => "<" + inner + ">")/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return this/* : Template */.base/* : ObjectType */.generate/* : () => string */()/* : string */ + joinedArguments/* : unknown */;
	}
	public find(name : string) : Option<Type> {
		return this/* : Template */.base/* : ObjectType */.find/* : (arg0 : string) => Option<Type> */(name/* : string */)/* : Option<Type> */.map/* : (arg0 : (arg0 : Type) => R) => Option<R> */((found : Type) => {
			let mapping = this/* : Template */.base/* : ObjectType */.typeParams/* : List<string> */()/* : unknown */.query/* : unknown */()/* : unknown */.zip/* : unknown */(this/* : Template */.arguments/* : List<Type> */.query/* : () => Query<T> */()/* : Query<T> */)/* : unknown */.collect/* : unknown */(new MapCollector()/* : MapCollector */)/* : unknown */;
			return found/* : Type */.replace/* : unknown */(mapping/* : unknown */)/* : unknown */;
		})/* : Option<R> */;
	}
	public name() : string {
		return this/* : Template */.base/* : ObjectType */.name/* : string */()/* : unknown */;
	}
	public findBase() : Option<BaseType> {
		return new Some(this/* : Template */.base/* : ObjectType */)/* : Some */;
	}
	public replace(mapping : Map<string, Type>) : Type {
		return this/* : Template */;
	}
	public findName() : Option<string> {
		return this/* : Template */.base/* : ObjectType */.findName/* : () => Option<string> */()/* : Option<string> */;
	}
}
/* private */class Placeholder/*  */ implements Parameter, Value, FindableType, ClassSegment, FunctionSegment, BlockHeader, StatementValue, IncompleteClassSegment {
	input : string;
	constructor (input : string) {
		this/* : unknown */.input/* : unknown */ = input/* : unknown */;
	}
	_IncompleteClassSegmentVariant : IncompleteClassSegmentVariant = IncompleteClassSegmentVariant.Placeholder/* : IncompleteClassSegmentVariant */;
	_ValueVariant : ValueVariant = ValueVariant.Placeholder/* : ValueVariant */;
	public generate() : string {
		return generatePlaceholder/* : (arg0 : string) => string */(this/* : Placeholder */.input/* : string */)/* : string */;
	}
	public type() : Type {
		return Primitive/* : Primitive */.Unknown/* : unknown */;
	}
	public find(name : string) : Option<Type> {
		return new None()/* : None */;
	}
	public name() : string {
		return this/* : Placeholder */.input/* : string */;
	}
	public findBase() : Option<BaseType> {
		return new None()/* : None */;
	}
	public replace(mapping : Map<string, Type>) : Type {
		return this/* : Placeholder */;
	}
	public findName() : Option<string> {
		return new None()/* : None */;
	}
	public maybeCreateDefinition() : Option<Definition> {
		return new None()/* : None */;
	}
}
/* private */class StringValue/*  */ implements Value {
	stripped : string;
	constructor (stripped : string) {
		this/* : unknown */.stripped/* : unknown */ = stripped/* : unknown */;
	}
	_ValueVariant : ValueVariant = ValueVariant.StringValue/* : ValueVariant */;
	public generate() : string {
		return this/* : StringValue */.stripped/* : string */;
	}
	public type() : Type {
		return Primitive/* : Primitive */.Unknown/* : unknown */;
	}
}
/* private */class DataAccess/*  */ implements Value {
	parent : Value;
	property : string;
	type : Type;
	constructor (parent : Value, property : string, type : Type) {
		this/* : unknown */.parent/* : unknown */ = parent/* : unknown */;
		this/* : unknown */.property/* : unknown */ = property/* : unknown */;
		this/* : unknown */.type/* : unknown */ = type/* : unknown */;
	}
	_ValueVariant : ValueVariant = ValueVariant.DataAccess/* : ValueVariant */;
	public generate() : string {
		return this/* : DataAccess */.parent/* : Value */.generate/* : () => string */()/* : string */ + "." + this/* : DataAccess */.property/* : string */ + createDebugString/* : (arg0 : Type) => string */(this/* : DataAccess */.type/* : () => Type */)/* : unknown */;
	}
	public type() : Type {
		return this/* : DataAccess */.type/* : () => Type */;
	}
}
/* private */class ConstructionCaller/*  */ implements Caller {
	type : Type;
	constructor (type : Type) {
		this/* : unknown */.type/* : unknown */ = type/* : unknown */;
	}
	_CallerVariant : CallerVariant = CallerVariant.ConstructionCaller/* : CallerVariant */;
	public generate() : string {
		return "new " + this/* : ConstructionCaller */.type/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
	public toFunction() : FunctionType {
		return new FunctionType(Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, this/* : ConstructionCaller */.type/* : Type */)/* : FunctionType */;
	}
}
/* private */class Operator/*  */ {
	sourceRepresentation : string;
	targetRepresentation : string;
	constructor (sourceRepresentation : string, targetRepresentation : string) {
		this/* : unknown */.sourceRepresentation/* : unknown */ = sourceRepresentation/* : unknown */;
		this/* : unknown */.targetRepresentation/* : unknown */ = targetRepresentation/* : unknown */;
	}
	static ADD : Operator = new Operator("+", "+")/* : Operator */;
	static AND : Operator = new Operator("&&", "&&")/* : Operator */;
	static EQUALS : Operator = new Operator("==", "===")/* : Operator */;
	static Operator GREATER_THAN_OR_EQUALS = Operator() : /* new */;
	static Operator LESS_THAN = Operator() : /* new */;
	static OR : Operator = new Operator("||", "||")/* : Operator */;
	static SUBTRACT : Operator = new Operator("-", "-")/* : Operator */;
}
/* private */class Operation/*  */ implements Value {
	left : Value;
	operator : Operator;
	right : Value;
	constructor (left : Value, operator : Operator, right : Value) {
		this/* : unknown */.left/* : unknown */ = left/* : unknown */;
		this/* : unknown */.operator/* : unknown */ = operator/* : unknown */;
		this/* : unknown */.right/* : unknown */ = right/* : unknown */;
	}
	_ValueVariant : ValueVariant = ValueVariant.Operation/* : ValueVariant */;
	public generate() : string {
		return this/* : Operation */.left/* : Value */()/* : unknown */.generate/* : unknown */()/* : unknown */ + " " + this/* : Operation */.operator/* : Operator */.targetRepresentation/* : unknown */ + " " + this/* : Operation */.right/* : unknown */()/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
	public type() : Type {
		return Primitive/* : Primitive */.Unknown/* : unknown */;
	}
}
/* private */class Not/*  */ implements Value {
	value : Value;
	constructor (value : Value) {
		this/* : unknown */.value/* : unknown */ = value/* : unknown */;
	}
	_ValueVariant : ValueVariant = ValueVariant.Not/* : ValueVariant */;
	public generate() : string {
		return "!" + this/* : Not */.value/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
	public type() : Type {
		return Primitive/* : Primitive */.Unknown/* : unknown */;
	}
}
/* private */class BlockLambdaValue/*  */ implements LambdaValue {
	depth : number;
	statements : List<FunctionSegment>;
	constructor (depth : number, statements : List<FunctionSegment>) {
		this/* : unknown */.depth/* : unknown */ = depth/* : unknown */;
		this/* : unknown */.statements/* : unknown */ = statements/* : unknown */;
	}
	public generate() : string {
		return "{" + this.joinStatements() + createIndent(this.depth) + "}";
	}
	private joinStatements() : string {
		return this/* : BlockLambdaValue */.statements/* : List<FunctionSegment> */.query/* : () => Query<T> */()/* : Query<T> */.map/* : (arg0 : (arg0 : T) => R) => Query<R> */(FunctionSegment/* : FunctionSegment */.generate/* : unknown */)/* : Query<R> */.collect/* : (arg0 : Collector<T, R>) => R */(Joiner/* : Joiner */.empty/* : () => Joiner */()/* : Joiner */)/* : R */.orElse/* : unknown */("")/* : unknown */;
	}
}
/* private */class Lambda/*  */ implements Value {
	parameters : List<Definition>;
	body : LambdaValue;
	constructor (parameters : List<Definition>, body : LambdaValue) {
		this/* : unknown */.parameters/* : unknown */ = parameters/* : unknown */;
		this/* : unknown */.body/* : unknown */ = body/* : unknown */;
	}
	_ValueVariant : ValueVariant = ValueVariant.Lambda/* : ValueVariant */;
	public generate() : string {
		let joined = this/* : Lambda */.parameters/* : List<Definition> */.query/* : () => Query<T> */()/* : Query<T> */.map/* : (arg0 : (arg0 : T) => R) => Query<R> */(Definition/* : Definition */.generate/* : unknown */)/* : Query<R> */.collect/* : (arg0 : Collector<T, R>) => R */(new Joiner(", ")/* : Joiner */)/* : R */.orElse/* : unknown */("")/* : unknown */;
		return "(" + joined/* : unknown */ + ") => " + this/* : Lambda */.body/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
	public type() : Type {
		return Primitive/* : Primitive */.Unknown/* : unknown */;
	}
}
/* private */class Invokable/*  */ implements Value {
	caller : Caller;
	arguments : List<Value>;
	type : Type;
	constructor (caller : Caller, arguments : List<Value>, type : Type) {
		this/* : unknown */.caller/* : unknown */ = caller/* : unknown */;
		this/* : unknown */.arguments/* : unknown */ = arguments/* : unknown */;
		this/* : unknown */.type/* : unknown */ = type/* : unknown */;
	}
	_ValueVariant : ValueVariant = ValueVariant.Invokable/* : ValueVariant */;
	public generate() : string {
		let joined = this/* : Invokable */.arguments/* : List<Value> */.query/* : () => Query<T> */()/* : Query<T> */.map/* : (arg0 : (arg0 : T) => R) => Query<R> */(Value/* : Value */.generate/* : unknown */)/* : Query<R> */.collect/* : (arg0 : Collector<T, R>) => R */(new Joiner(", ")/* : Joiner */)/* : R */.orElse/* : unknown */("")/* : unknown */;
		return this/* : Invokable */.caller/* : Caller */.generate/* : () => string */()/* : string */ + "(" + joined/* : unknown */ + ")" + createDebugString/* : (arg0 : Type) => string */(this/* : Invokable */.type/* : Type */)/* : unknown */;
	}
}
/* private */class IndexValue/*  */ implements Value {
	parent : Value;
	child : Value;
	constructor (parent : Value, child : Value) {
		this/* : unknown */.parent/* : unknown */ = parent/* : unknown */;
		this/* : unknown */.child/* : unknown */ = child/* : unknown */;
	}
	_ValueVariant : ValueVariant = ValueVariant.IndexValue/* : ValueVariant */;
	public generate() : string {
		return this/* : IndexValue */.parent/* : Value */.generate/* : () => string */()/* : string */ + "[" + this.child.generate() + "]";
	}
	public type() : Type {
		return Primitive/* : Primitive */.Unknown/* : unknown */;
	}
}
/* private */class SymbolValue/*  */ implements Value {
	stripped : string;
	type : Type;
	constructor (stripped : string, type : Type) {
		this/* : unknown */.stripped/* : unknown */ = stripped/* : unknown */;
		this/* : unknown */.type/* : unknown */ = type/* : unknown */;
	}
	_ValueVariant : ValueVariant = ValueVariant.SymbolValue/* : ValueVariant */;
	public generate() : string {
		return this/* : SymbolValue */.stripped/* : string */ + createDebugString/* : (arg0 : Type) => string */(this/* : SymbolValue */.type/* : Type */)/* : unknown */;
	}
}
/* private static */class Maps/*  */ {
	public static empty<V, K>() : Map<K, V>;
}
/* private */class MapCollector<K, V>/*  */ implements Collector<[K, V], Map<K, V>> {
	public createInitial() : Map<K, V> {
		return Maps/* : Maps */.empty/* : () => Map<K, V> */()/* : Map<K, V> */;
	}
	public fold(current : Map<K, V>, element : [K, V]) : Map<K, V> {
		return current/* : Map<K, V> */.with/* : (arg0 : K, arg1 : V) => Map<K, V> */(element/* : [K, V] */[0/* : number */], element/* : [K, V] */[1/* : number */])/* : Map<K, V> */;
	}
}
/* private static */class ConstructorHeader/*  */ implements Header {
	public createDefinition(paramTypes : List<Type>) : Definition {
		return ImmutableDefinition/* : ImmutableDefinition */.createSimpleDefinition/* : (arg0 : string, arg1 : Type) => Definition */("new", Primitive/* : Primitive */.Unknown/* : unknown */)/* : Definition */;
	}
	public generateWithParams(joinedParameters : string) : string {
		return "constructor " + joinedParameters/* : string */;
	}
}
/* private */class FunctionNode/*  */ implements ClassSegment {
	depth : number;
	header : Header;
	parameters : List<Definition>;
	maybeStatements : Option<List<FunctionSegment>>;
	constructor (depth : number, header : Header, parameters : List<Definition>, maybeStatements : Option<List<FunctionSegment>>) {
		this/* : unknown */.depth/* : unknown */ = depth/* : unknown */;
		this/* : unknown */.header/* : unknown */ = header/* : unknown */;
		this/* : unknown */.parameters/* : unknown */ = parameters/* : unknown */;
		this/* : unknown */.maybeStatements/* : unknown */ = maybeStatements/* : unknown */;
	}
	private static joinStatements(statements : List<FunctionSegment>) : string {
		return statements/* : List<FunctionSegment> */.query/* : () => Query<T> */()/* : Query<T> */.map/* : (arg0 : (arg0 : T) => R) => Query<R> */(FunctionSegment/* : FunctionSegment */.generate/* : unknown */)/* : Query<R> */.collect/* : (arg0 : Collector<T, R>) => R */(Joiner/* : Joiner */.empty/* : () => Joiner */()/* : Joiner */)/* : R */.orElse/* : unknown */("")/* : unknown */;
	}
	public generate() : string {
		let indent : string = createIndent/* : (arg0 : number) => string */(this/* : FunctionNode */.depth/* : number */)/* : string */;
		let generatedHeader : string = this/* : FunctionNode */.header/* : Header */.generateWithParams/* : (arg0 : string) => string */(joinValues/* : (arg0 : List<Definition>) => string */(this/* : FunctionNode */.parameters/* : List<Definition> */)/* : string */)/* : string */;
		let generatedStatements : T = this/* : FunctionNode */.maybeStatements/* : Option<List<FunctionSegment>> */.map/* : (arg0 : (arg0 : List<FunctionSegment>) => R) => Option<R> */(FunctionNode/* : FunctionNode */.joinStatements/* : unknown */)/* : Option<R> */.map/* : (arg0 : (arg0 : T) => R) => Option<R> */((inner : T) => " {" + inner + indent + "}")/* : Option<R> */.orElse/* : (arg0 : T) => T */(";")/* : T */;
		return indent/* : string */ + generatedHeader/* : string */ + generatedStatements/* : T */;
	}
}
/* private */class Block/*  */ implements FunctionSegment {
	depth : number;
	header : BlockHeader;
	statements : List<FunctionSegment>;
	constructor (depth : number, header : BlockHeader, statements : List<FunctionSegment>) {
		this/* : unknown */.depth/* : unknown */ = depth/* : unknown */;
		this/* : unknown */.header/* : unknown */ = header/* : unknown */;
		this/* : unknown */.statements/* : unknown */ = statements/* : unknown */;
	}
	public generate() : string {
		let indent : string = createIndent/* : (arg0 : number) => string */(this/* : Block */.depth/* : number */)/* : string */;
		let collect = this/* : Block */.statements/* : List<FunctionSegment> */.query/* : () => Query<T> */()/* : Query<T> */.map/* : (arg0 : (arg0 : T) => R) => Query<R> */(FunctionSegment/* : FunctionSegment */.generate/* : unknown */)/* : Query<R> */.collect/* : (arg0 : Collector<T, R>) => R */(Joiner/* : Joiner */.empty/* : () => Joiner */()/* : Joiner */)/* : R */.orElse/* : unknown */("")/* : unknown */;
		return indent/* : string */ + this/* : Block */.header/* : BlockHeader */.generate/* : () => string */()/* : string */ + "{" + collect + indent + "}";
	}
}
/* private */class Conditional/*  */ implements BlockHeader {
	prefix : string;
	value1 : Value;
	constructor (prefix : string, value1 : Value) {
		this/* : unknown */.prefix/* : unknown */ = prefix/* : unknown */;
		this/* : unknown */.value1/* : unknown */ = value1/* : unknown */;
	}
	public generate() : string {
		return this/* : Conditional */.prefix/* : string */ + " (" + this.value1.generate() + ")";
	}
}
/* private static */class Else/*  */ implements BlockHeader {
	public generate() : string {
		return "else ";
	}
}
/* private */class Return/*  */ implements StatementValue {
	value : Value;
	constructor (value : Value) {
		this/* : unknown */.value/* : unknown */ = value/* : unknown */;
	}
	public generate() : string {
		return "return " + this/* : Return */.value/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
}
/* private */class Initialization/*  */ implements StatementValue {
	definition : Definition;
	source : Value;
	constructor (definition : Definition, source : Value) {
		this/* : unknown */.definition/* : unknown */ = definition/* : unknown */;
		this/* : unknown */.source/* : unknown */ = source/* : unknown */;
	}
	public generate() : string {
		return "let " + this/* : Initialization */.definition/* : Definition */.generate/* : () => string */()/* : string */ + " = " + this/* : Initialization */.source/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
}
/* private */class FieldInitialization/*  */ implements StatementValue {
	definition : Definition;
	source : Value;
	constructor (definition : Definition, source : Value) {
		this/* : unknown */.definition/* : unknown */ = definition/* : unknown */;
		this/* : unknown */.source/* : unknown */ = source/* : unknown */;
	}
	public generate() : string {
		return this/* : FieldInitialization */.definition/* : Definition */.generate/* : () => string */()/* : string */ + " = " + this/* : FieldInitialization */.source/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
}
/* private */class Assignment/*  */ implements StatementValue {
	destination : Value;
	source : Value;
	constructor (destination : Value, source : Value) {
		this/* : unknown */.destination/* : unknown */ = destination/* : unknown */;
		this/* : unknown */.source/* : unknown */ = source/* : unknown */;
	}
	public generate() : string {
		return this/* : Assignment */.destination/* : Value */.generate/* : () => string */()/* : string */ + " = " + this/* : Assignment */.source/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
}
/* private */class Statement/*  */ implements FunctionSegment, ClassSegment {
	depth : number;
	value : StatementValue;
	constructor (depth : number, value : StatementValue) {
		this/* : unknown */.depth/* : unknown */ = depth/* : unknown */;
		this/* : unknown */.value/* : unknown */ = value/* : unknown */;
	}
	public generate() : string {
		return createIndent/* : (arg0 : number) => string */(this/* : Statement */.depth/* : number */)/* : string */ + this/* : Statement */.value/* : StatementValue */.generate/* : () => string */()/* : string */ + ";";
	}
}
/* private */class MethodPrototype/*  */ implements IncompleteClassSegment {
	depth : number;
	header : Header;
	parameters : List<Definition>;
	content : string;
	constructor (depth : number, header : Header, parameters : List<Definition>, content : string) {
		this/* : unknown */.depth/* : unknown */ = depth/* : unknown */;
		this/* : unknown */.header/* : unknown */ = header/* : unknown */;
		this/* : unknown */.parameters/* : unknown */ = parameters/* : unknown */;
		this/* : unknown */.content/* : unknown */ = content/* : unknown */;
	}
	_IncompleteClassSegmentVariant : IncompleteClassSegmentVariant = IncompleteClassSegmentVariant.MethodPrototype/* : IncompleteClassSegmentVariant */;
	private createDefinition() : Definition {
		return this/* : MethodPrototype */.header/* : Header */.createDefinition/* : (arg0 : List<Type>) => Definition */(this/* : MethodPrototype */.findParamTypes/* : () => List<Type> */()/* : List<Type> */)/* : Definition */;
	}
	private findParamTypes() : List<Type> {
		return this/* : MethodPrototype */.parameters/* : List<Definition> */()/* : unknown */.query/* : unknown */()/* : unknown */.map/* : unknown */(Definition/* : Definition */.findType/* : unknown */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
	}
	public maybeCreateDefinition() : Option<Definition> {
		return new Some(this/* : MethodPrototype */.header/* : Header */.createDefinition/* : (arg0 : List<Type>) => Definition */(this/* : MethodPrototype */.findParamTypes/* : () => List<Type> */()/* : List<Type> */)/* : Definition */)/* : Some */;
	}
}
/* private */class IncompleteClassSegmentWrapper/*  */ implements IncompleteClassSegment {
	segment : ClassSegment;
	constructor (segment : ClassSegment) {
		this/* : unknown */.segment/* : unknown */ = segment/* : unknown */;
	}
	_IncompleteClassSegmentVariant : IncompleteClassSegmentVariant = IncompleteClassSegmentVariant.IncompleteClassSegmentWrapper/* : IncompleteClassSegmentVariant */;
	public maybeCreateDefinition() : Option<Definition> {
		return new None()/* : None */;
	}
}
/* private */class ClassDefinition/*  */ implements IncompleteClassSegment {
	depth : number;
	definition : Definition;
	constructor (depth : number, definition : Definition) {
		this/* : unknown */.depth/* : unknown */ = depth/* : unknown */;
		this/* : unknown */.definition/* : unknown */ = definition/* : unknown */;
	}
	_IncompleteClassSegmentVariant : IncompleteClassSegmentVariant = IncompleteClassSegmentVariant.ClassDefinition/* : IncompleteClassSegmentVariant */;
	public maybeCreateDefinition() : Option<Definition> {
		return new Some(this/* : ClassDefinition */.definition/* : Definition */)/* : Some */;
	}
}
/* private */class ClassInitialization/*  */ implements IncompleteClassSegment {
	depth : number;
	definition : Definition;
	value : Value;
	constructor (depth : number, definition : Definition, value : Value) {
		this/* : unknown */.depth/* : unknown */ = depth/* : unknown */;
		this/* : unknown */.definition/* : unknown */ = definition/* : unknown */;
		this/* : unknown */.value/* : unknown */ = value/* : unknown */;
	}
	_IncompleteClassSegmentVariant : IncompleteClassSegmentVariant = IncompleteClassSegmentVariant.ClassInitialization/* : IncompleteClassSegmentVariant */;
	public maybeCreateDefinition() : Option<Definition> {
		return new Some(this/* : ClassInitialization */.definition/* : Definition */)/* : Some */;
	}
}
/* private */class StructurePrototype/*  */ implements IncompleteClassSegment {
	targetInfix : string;
	beforeInfix : string;
	name : string;
	typeParams : List<string>;
	parameters : List<Definition>;
	after : string;
	segments : List<IncompleteClassSegment>;
	variants : List<string>;
	interfaces : List<Type>;
	constructor (targetInfix : string, beforeInfix : string, name : string, typeParams : List<string>, parameters : List<Definition>, after : string, segments : List<IncompleteClassSegment>, variants : List<string>, interfaces : List<Type>) {
		this/* : unknown */.targetInfix/* : unknown */ = targetInfix/* : unknown */;
		this/* : unknown */.beforeInfix/* : unknown */ = beforeInfix/* : unknown */;
		this/* : unknown */.name/* : unknown */ = name/* : unknown */;
		this/* : unknown */.typeParams/* : unknown */ = typeParams/* : unknown */;
		this/* : unknown */.parameters/* : unknown */ = parameters/* : unknown */;
		this/* : unknown */.after/* : unknown */ = after/* : unknown */;
		this/* : unknown */.segments/* : unknown */ = segments/* : unknown */;
		this/* : unknown */.variants/* : unknown */ = variants/* : unknown */;
		this/* : unknown */.interfaces/* : unknown */ = interfaces/* : unknown */;
	}
	_IncompleteClassSegmentVariant : IncompleteClassSegmentVariant = IncompleteClassSegmentVariant.StructurePrototype/* : IncompleteClassSegmentVariant */;
	private createObjectType() : ObjectType {
		let definitionFromSegments : R = this/* : StructurePrototype */.segments/* : List<IncompleteClassSegment> */.query/* : () => Query<T> */()/* : Query<T> */.map/* : (arg0 : (arg0 : T) => R) => Query<R> */(IncompleteClassSegment/* : IncompleteClassSegment */.maybeCreateDefinition/* : unknown */)/* : Query<R> */.flatMap/* : (arg0 : (arg0 : T) => Option<R>) => Option<R> */(Queries/* : Queries */.fromOption/* : unknown */)/* : Option<R> */.collect/* : (arg0 : Collector<T, R>) => R */(new ListCollector()/* : ListCollector */)/* : R */;
		return new ObjectType(this/* : StructurePrototype */.name/* : string */, this/* : StructurePrototype */.typeParams/* : List<string> */, definitionFromSegments/* : R */.addAllLast/* : unknown */(this/* : StructurePrototype */.parameters/* : List<Definition> */)/* : unknown */, this/* : StructurePrototype */.variants/* : List<string> */)/* : ObjectType */;
	}
	public maybeCreateDefinition() : Option<Definition> {
		return new None()/* : None */;
	}
	private joinInterfaces() : string {
		return this/* : StructurePrototype */.interfaces/* : List<Type> */.query/* : () => Query<T> */()/* : Query<T> */.map/* : (arg0 : (arg0 : T) => R) => Query<R> */(Type/* : Type */.generate/* : unknown */)/* : Query<R> */.collect/* : (arg0 : Collector<T, R>) => R */(new Joiner(", ")/* : Joiner */)/* : R */.map/* : unknown */((inner) => " implements " + inner/* : unknown */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
	}
	private joinTypeParams() : string {
		return this/* : StructurePrototype */.typeParams/* : List<string> */()/* : unknown */.query/* : unknown */()/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.map/* : unknown */((inner) => "<" + inner + ">")/* : unknown */.orElse/* : unknown */("")/* : unknown */;
	}
}
/* private */class Cast/*  */ implements Value {
	value : Value;
	type : Type;
	constructor (value : Value, type : Type) {
		this/* : unknown */.value/* : unknown */ = value/* : unknown */;
		this/* : unknown */.type/* : unknown */ = type/* : unknown */;
	}
	_ValueVariant : ValueVariant = ValueVariant.Cast/* : ValueVariant */;
	public generate() : string {
		return this/* : Cast */.value/* : Value */.generate/* : () => string */()/* : string */ + " as " + this/* : Cast */.type/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
}
/* private */class Ok<T, X>/*  */ implements Result<T, X> {
	value : T;
	constructor (value : T) {
		this/* : unknown */.value/* : unknown */ = value/* : unknown */;
	}
	_ResultVariant : ResultVariant = ResultVariant.Ok/* : ResultVariant */;
	public mapValue<R>(mapper : (arg0 : T) => R) : Result<R, X> {
		return new Ok(mapper/* : (arg0 : T) => R */(this/* : Ok */.value/* : T */)/* : R */)/* : Ok */;
	}
	public match<R>(whenOk : (arg0 : T) => R, whenErr : (arg0 : X) => R) : R {
		return whenOk/* : (arg0 : T) => R */(this/* : Ok */.value/* : T */)/* : R */;
	}
}
/* private */class Err<T, X>/*  */ implements Result<T, X> {
	error : X;
	constructor (error : X) {
		this/* : unknown */.error/* : unknown */ = error/* : unknown */;
	}
	_ResultVariant : ResultVariant = ResultVariant.Err/* : ResultVariant */;
	public mapValue<R>(mapper : (arg0 : T) => R) : Result<R, X> {
		return new Err(this/* : Err */.error/* : X */)/* : Err */;
	}
	public match<R>(whenOk : (arg0 : T) => R, whenErr : (arg0 : X) => R) : R {
		return whenErr/* : (arg0 : X) => R */(this/* : Err */.error/* : X */)/* : R */;
	}
}
/* private */class JVMIOError/*  */ implements IOError {
	error : /* IOException */;
	constructor (error : /* IOException */) {
		this/* : unknown */.error/* : unknown */ = error/* : unknown */;
	}
	public display() : string {
		let writer : /* StringWriter */ = new /* StringWriter */()/* : content-start StringWriter content-end */;
		/* this.error.printStackTrace(new PrintWriter(writer)) */;
		return writer/* : content-start StringWriter content-end */.toString/* : unknown */()/* : unknown */;
	}
}
/* private */class TupleNode/*  */ implements Value {
	values : List<Value>;
	constructor (values : List<Value>) {
		this/* : unknown */.values/* : unknown */ = values/* : unknown */;
	}
	_ValueVariant : ValueVariant = ValueVariant.TupleNode/* : ValueVariant */;
	public generate() : string {
		let joined = this/* : TupleNode */.values/* : List<Value> */.query/* : () => Query<T> */()/* : Query<T> */.map/* : (arg0 : (arg0 : T) => R) => Query<R> */(Value/* : Value */.generate/* : unknown */)/* : Query<R> */.collect/* : (arg0 : Collector<T, R>) => R */(new Joiner(", ")/* : Joiner */)/* : R */.orElse/* : unknown */("")/* : unknown */;
		return "[" + joined + "]";
	}
	public type() : Type {
		return new TupleType(this/* : TupleNode */.values/* : List<Value> */.query/* : () => Query<T> */()/* : Query<T> */.map/* : (arg0 : (arg0 : T) => R) => Query<R> */(Value/* : Value */.type/* : unknown */)/* : Query<R> */.collect/* : (arg0 : Collector<T, R>) => R */(new ListCollector()/* : ListCollector */)/* : R */)/* : TupleType */;
	}
}
/* private */class MapHead<T, R>/*  */ implements Head<R> {
	head : Head<T>;
	mapper : (arg0 : T) => R;
	constructor (head : Head<T>, mapper : (arg0 : T) => R) {
		this/* : unknown */.head/* : unknown */ = head/* : unknown */;
		this/* : unknown */.mapper/* : unknown */ = mapper/* : unknown */;
	}
	public next() : Option<R> {
		return this/* : MapHead */.head/* : Head<T> */.next/* : () => Option<T> */()/* : Option<T> */.map/* : (arg0 : (arg0 : T) => R) => Option<R> */(this/* : MapHead */.mapper/* : (arg0 : T) => R */)/* : Option<R> */;
	}
}
/* private */class ZipHead<T, R>/*  */ implements Head<[T, R]> {
	head : Head<T>;
	other : Query<R>;
	constructor (head : Head<T>, other : Query<R>) {
		this/* : unknown */.head/* : unknown */ = head/* : unknown */;
		this/* : unknown */.other/* : unknown */ = other/* : unknown */;
	}
	public next() : Option<[T, R]> {
		return this/* : ZipHead */.head/* : Head<T> */.next/* : () => Option<T> */()/* : Option<T> */.and/* : (arg0 : () => Option<R>) => Option<[T, R]> */(this/* : ZipHead */.other/* : Query<R> */.next/* : unknown */)/* : Option<[T, R]> */;
	}
}
/* private */class Primitive/*  */ implements Type {/* Int("number"),
        String("string"),
        Boolean("boolean"),
        Unknown("unknown"),
        Void("void"); */
	private final value : string;
	constructor (value : string) {
		this/* : Primitive */.value/* : string */ = value/* : string */;
	}
	public generate() : string {
		return this/* : Primitive */.value/* : string */;
	}
	public replace(mapping : Map<string, Type>) : Type {
		return this/* : Primitive */;
	}
	public findName() : Option<string> {
		return new None()/* : None */;
	}
}
/* private */class BooleanValue/*  */ implements Value {
	_ValueVariant : ValueVariant = ValueVariant.BooleanValue/* : ValueVariant */;/* True("true"), False("false"); */
	private final value : string;
	constructor (value : string) {
		this/* : BooleanValue */.value/* : string */ = value/* : string */;
	}
	public generate() : string {
		return this/* : BooleanValue */.value/* : string */;
	}
	public type() : Type {
		return Primitive/* : Primitive */.Boolean/* : unknown */;
	}
}
/* public */class Main/*  */ {
	private JVMPath() : /* record */;
	private static final isDebugEnabled : boolean = true;
	private static generatePlaceholder(input : string) : string {
		let replaced = input/* : string */.replace/* : unknown */("/*", "content-start")/* : unknown */.replace/* : unknown */("*/", "content-end")/* : unknown */;
		return "/* " + replaced + " */";
	}
	private static joinValues(retainParameters : List<Definition>) : string {
		let inner = retainParameters/* : List<Definition> */.query/* : () => Query<T> */()/* : Query<T> */.map/* : (arg0 : (arg0 : T) => R) => Query<R> */(Definition/* : Definition */.generate/* : unknown */)/* : Query<R> */.collect/* : (arg0 : Collector<T, R>) => R */(new Joiner(", ")/* : Joiner */)/* : R */.orElse/* : unknown */("")/* : unknown */;
		return "(" + inner + ")";
	}
	private static createIndent(depth : number) : string {
		return "\n" + "\t".repeat/* : unknown */(depth/* : number */)/* : unknown */;
	}
	private static createDebugString(type : Type) : string {
		if (!Main/* : Main */.isDebugEnabled/* : unknown */){
			return "";
		}
		return generatePlaceholder/* : (arg0 : string) => string */(": " + type/* : Type */.generate/* : unknown */()/* : unknown */)/* : string */;
	}
	private static retainFindableType(type : Type) : Option<FindableType> {
		if (type/* : Type */._Variant/* : unknown */ === Variant.FindableType/* : unknown */){
			let findableType : FindableType = type/* : Type */ as FindableType;
			return new Some(findableType/* : FindableType */)/* : Some */;
		}
		return new None()/* : None */;
	}
	public main() : void {
		let parent : Path = this/* : Main */.findRoot/* : () => Path */()/* : Path */;
		let source : Path = parent/* : Path */.resolve/* : (arg0 : string) => Path */("Main.java")/* : Path */;
		let target : Path = parent/* : Path */.resolve/* : (arg0 : string) => Path */("main.ts")/* : Path */;
		/* source.readString()
                .mapValue(this::compile)
                .match(target::writeString, Some::new)
                .or(this::executeTSC)
                .ifPresent(error -> System.err.println(error.display())) */;
	}
	private findRoot() : Path;
	private executeTSC() : Option<IOError>;
	private compile(input : string) : string {
		let state : CompileState = CompileState/* : CompileState */.createInitial/* : () => CompileState */()/* : CompileState */;
		let parsed : [CompileState, List<T>] = this/* : Main */.parseStatements/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => [CompileState, T]) => [CompileState, List<T>] */(state/* : CompileState */, input/* : string */, this/* : Main */.compileRootSegment/* : unknown */)/* : [CompileState, List<T>] */;
		let joined = parsed/* : [CompileState, List<T>] */[0/* : number */].structures/* : unknown */.query/* : unknown */()/* : unknown */.collect/* : unknown */(Joiner/* : Joiner */.empty/* : () => Joiner */()/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return joined/* : unknown */ + this/* : Main */.generateStatements/* : unknown */(parsed/* : [CompileState, List<T>] */[1/* : number */])/* : unknown */;
	}
	private generateStatements(statements : List<string>) : string {
		return this/* : Main */.generateAll/* : (arg0 : (arg0 : string, arg1 : string) => string, arg1 : List<string>) => string */(this/* : Main */.mergeStatements/* : unknown */, statements/* : List<string> */)/* : string */;
	}
	private parseStatements<T>(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => [CompileState, T]) : [CompileState, List<T>] {
		return this/* : Main */.parseAllWithIndices/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : DivideState, arg1 : string) => DivideState, arg3 : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]>) => Option<[CompileState, List<T>]> */(state/* : CompileState */, input/* : string */, this/* : Main */.foldStatementChar/* : unknown */, (state3, tuple) => new Some(mapper/* : (arg0 : CompileState, arg1 : string) => [CompileState, T] */(state3/* : unknown */, tuple/* : unknown */.right/* : unknown */()/* : unknown */)/* : [CompileState, T] */)/* : Some */)/* : Option<[CompileState, List<T>]> */.orElseGet/* : (arg0 : () => T) => T */(() => [state/* : CompileState */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */])/* : T */;
	}
	private generateAll(merger : (arg0 : string, arg1 : string) => string, elements : List<string>) : string {
		return elements/* : List<string> */.query/* : () => Query<T> */()/* : Query<T> */.fold/* : (arg0 : R, arg1 : (arg0 : R, arg1 : T) => R) => R */("", merger/* : (arg0 : string, arg1 : string) => string */)/* : R */;
	}
	private parseAllWithIndices<T>(state : CompileState, input : string, folder : (arg0 : DivideState, arg1 : string) => DivideState, mapper : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]>) : Option<[CompileState, List<T>]> {
		let stringList : List<string> = this/* : Main */.divideAll/* : (arg0 : string, arg1 : (arg0 : DivideState, arg1 : string) => DivideState) => List<string> */(input/* : string */, folder/* : (arg0 : DivideState, arg1 : string) => DivideState */)/* : List<string> */;
		return this/* : Main */.mapUsingState/* : (arg0 : CompileState, arg1 : List<T>, arg2 : (arg0 : CompileState, arg1 : [number, T]) => Option<[CompileState, R]>) => Option<[CompileState, List<R>]> */(state/* : CompileState */, stringList/* : List<string> */, mapper/* : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]> */)/* : Option<[CompileState, List<R>]> */;
	}
	private mapUsingState<T, R>(state : CompileState, elements : List<T>, mapper : (arg0 : CompileState, arg1 : [number, T]) => Option<[CompileState, R]>) : Option<[CompileState, List<R>]> {
		let initial : Option<[CompileState, List<R>]> = new Some([state/* : CompileState */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */])/* : Some */;
		return elements/* : List<T> */.iterateWithIndices/* : () => Query<[number, T]> */()/* : Query<[number, T]> */.fold/* : (arg0 : R, arg1 : (arg0 : R, arg1 : [number, T]) => R) => R */(initial/* : Option<[CompileState, List<R>]> */, (tuple, element) => {
			return tuple/* : unknown */.flatMap/* : unknown */((inner) => {
				let state1 = inner/* : unknown */.left/* : unknown */()/* : unknown */;
				let right = inner/* : unknown */.right/* : unknown */()/* : unknown */;
				return mapper/* : (arg0 : CompileState, arg1 : [number, T]) => Option<[CompileState, R]> */(state1/* : unknown */, element/* : unknown */)/* : Option<[CompileState, R]> */.map/* : (arg0 : (arg0 : [CompileState, R]) => R) => Option<R> */((applied : [CompileState, R]) => {
					return [applied/* : [CompileState, R] */[0/* : number */], right/* : unknown */.addLast/* : unknown */(applied/* : [CompileState, R] */[1/* : number */])/* : unknown */];
				})/* : Option<R> */;
			})/* : unknown */;
		})/* : R */;
	}
	private mergeStatements(cache : string, statement : string) : string {
		return cache/* : string */ + statement/* : string */;
	}
	private divideAll(input : string, folder : (arg0 : DivideState, arg1 : string) => DivideState) : List<string> {
		let current : DivideState = DivideState/* : DivideState */.createInitial/* : (arg0 : string) => DivideState */(input/* : string */)/* : DivideState */;
		while (true){
			let maybePopped : Option<R> = current/* : DivideState */.pop/* : () => Option<[string, DivideState]> */()/* : Option<[string, DivideState]> */.map/* : (arg0 : (arg0 : [string, DivideState]) => R) => Option<R> */((tuple : [string, DivideState]) => {
				return this/* : Main */.foldSingleQuotes/* : (arg0 : [string, DivideState]) => Option<DivideState> */(tuple/* : [string, DivideState] */)/* : Option<DivideState> */.or/* : (arg0 : () => Option<T>) => Option<T> */(() => this/* : Main */.foldDoubleQuotes/* : (arg0 : [string, DivideState]) => Option<DivideState> */(tuple/* : [string, DivideState] */)/* : Option<DivideState> */)/* : Option<T> */.orElseGet/* : (arg0 : () => T) => T */(() => folder/* : (arg0 : DivideState, arg1 : string) => DivideState */(tuple/* : [string, DivideState] */[1/* : number */], tuple/* : [string, DivideState] */[0/* : number */])/* : DivideState */)/* : T */;
			})/* : Option<R> */;
			if (maybePopped/* : Option<R> */.isPresent/* : () => boolean */()/* : boolean */){
				current/* : DivideState */ = maybePopped/* : Option<R> */.orElse/* : (arg0 : T) => T */(current/* : DivideState */)/* : T */;
			}
			else {
				/* break */;
			}
		}
		return current/* : DivideState */.advance/* : () => DivideState */()/* : DivideState */.segments/* : unknown */;
	}
	private foldDoubleQuotes(tuple : [string, DivideState]) : Option<DivideState> {
		if (tuple/* : [string, DivideState] */[0/* : number */] === /*  '\"' */){
			let current = tuple/* : [string, DivideState] */[1/* : number */].append/* : unknown */(tuple/* : [string, DivideState] */[0/* : number */])/* : unknown */;
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
	private foldSingleQuotes(tuple : [string, DivideState]) : Option<DivideState> {
		if (/* tuple.left() != '\'' */){
			return new None()/* : None */;
		}
		let appended = tuple/* : [string, DivideState] */[1/* : number */].append/* : unknown */(tuple/* : [string, DivideState] */[0/* : number */])/* : unknown */;
		return appended/* : unknown */.popAndAppendToTuple/* : unknown */()/* : unknown */.map/* : unknown */(this/* : Main */.foldEscaped/* : unknown */)/* : unknown */.flatMap/* : unknown */(DivideState/* : DivideState */.popAndAppendToOption/* : unknown */)/* : unknown */;
	}
	private foldEscaped(escaped : [string, DivideState]) : DivideState {
		if (escaped/* : [string, DivideState] */[0/* : number */] === /*  '\\' */){
			return escaped/* : [string, DivideState] */[1/* : number */].popAndAppendToOption/* : unknown */()/* : unknown */.orElse/* : unknown */(escaped/* : [string, DivideState] */[1/* : number */])/* : unknown */;
		}
		return escaped/* : [string, DivideState] */[1/* : number */];
	}
	private foldStatementChar(state : DivideState, c : string) : DivideState {
		let append : DivideState = state/* : DivideState */.append/* : (arg0 : string) => DivideState */(c/* : string */)/* : DivideState */;
		if (c/* : string */ === /*  ';'  */ && append/* : DivideState */.isLevel/* : unknown */()/* : unknown */){
			return append/* : DivideState */.advance/* : () => DivideState */()/* : DivideState */;
		}
		if (c/* : string */ === /*  '}'  */ && append/* : DivideState */.isShallow/* : unknown */()/* : unknown */){
			return append/* : DivideState */.advance/* : () => DivideState */()/* : DivideState */.exit/* : () => DivideState */()/* : DivideState */;
		}
		if (c/* : string */ === /*  '{'  */ || c/* : string */ === /*  '(' */){
			return append/* : DivideState */.enter/* : () => DivideState */()/* : DivideState */;
		}
		if (c/* : string */ === /*  '}'  */ || c/* : string */ === /*  ')' */){
			return append/* : DivideState */.exit/* : () => DivideState */()/* : DivideState */;
		}
		return append/* : DivideState */;
	}
	private compileRootSegment(state : CompileState, input : string) : [CompileState, string] {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.startsWith/* : unknown */("package ")/* : unknown */ || stripped/* : unknown */.startsWith/* : unknown */("import ")/* : unknown */){
			return [state/* : CompileState */, ""];
		}
		return this/* : Main */.parseClass/* : (arg0 : string, arg1 : CompileState) => Option<[CompileState, IncompleteClassSegment]> */(stripped/* : unknown */, state/* : CompileState */)/* : Option<[CompileState, IncompleteClassSegment]> */.flatMap/* : (arg0 : (arg0 : [CompileState, IncompleteClassSegment]) => Option<R>) => Option<R> */((tuple : [CompileState, IncompleteClassSegment]) => this/* : Main */.completeClassSegment/* : (arg0 : CompileState, arg1 : IncompleteClassSegment) => Option<[CompileState, ClassSegment]> */(tuple/* : [CompileState, IncompleteClassSegment] */[0/* : number */], tuple/* : [CompileState, IncompleteClassSegment] */[1/* : number */])/* : Option<[CompileState, ClassSegment]> */)/* : Option<R> */.map/* : (arg0 : (arg0 : T) => R) => Option<R> */((tuple0 : T) => [tuple0/* : T */.left/* : unknown */()/* : unknown */, tuple0/* : T */.right/* : unknown */()/* : unknown */.generate/* : unknown */()/* : unknown */])/* : Option<R> */.orElseGet/* : (arg0 : () => T) => T */(() => [state/* : CompileState */, generatePlaceholder/* : (arg0 : string) => string */(stripped/* : unknown */)/* : string */])/* : T */;
	}
	private parseClass(stripped : string, state : CompileState) : Option<[CompileState, IncompleteClassSegment]> {
		return this/* : Main */.parseStructure/* : (arg0 : string, arg1 : string, arg2 : string, arg3 : CompileState) => Option<[CompileState, IncompleteClassSegment]> */(stripped/* : string */, "class ", "class ", state/* : CompileState */)/* : Option<[CompileState, IncompleteClassSegment]> */;
	}
	private parseStructure(stripped : string, sourceInfix : string, targetInfix : string, state : CompileState) : Option<[CompileState, IncompleteClassSegment]> {
		return this/* : Main */.first/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(stripped/* : string */, sourceInfix/* : string */, (beforeInfix, right) => {
			return this/* : Main */.first/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(right/* : unknown */, "{", (beforeContent, withEnd) => {
				return this/* : Main */.suffix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(withEnd/* : unknown */.strip/* : unknown */()/* : unknown */, "}", (content1 : string) => {
					return this/* : Main */.last/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(beforeInfix/* : unknown */.strip/* : unknown */()/* : unknown */, "\n", (annotationsString, s2) => {
						let annotations : List<string> = this/* : Main */.parseAnnotations/* : (arg0 : string) => List<string> */(annotationsString/* : unknown */)/* : List<string> */;
						return this/* : Main */.parseStructureWithMaybePermits/* : (arg0 : string, arg1 : CompileState, arg2 : string, arg3 : string, arg4 : string, arg5 : List<string>) => Option<[CompileState, IncompleteClassSegment]> */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : unknown */, beforeContent/* : unknown */, content1/* : string */, annotations/* : List<string> */)/* : Option<[CompileState, IncompleteClassSegment]> */;
					})/* : Option<T> */.or/* : (arg0 : () => Option<T>) => Option<T> */(() => {
						return this/* : Main */.parseStructureWithMaybePermits/* : (arg0 : string, arg1 : CompileState, arg2 : string, arg3 : string, arg4 : string, arg5 : List<string>) => Option<[CompileState, IncompleteClassSegment]> */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : unknown */, beforeContent/* : unknown */, content1/* : string */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */)/* : Option<[CompileState, IncompleteClassSegment]> */;
					})/* : Option<T> */;
				})/* : Option<T> */;
			})/* : Option<T> */;
		})/* : Option<T> */;
	}
	private parseAnnotations(annotationsString : string) : List<string> {
		return this/* : Main */.divideAll/* : (arg0 : string, arg1 : (arg0 : DivideState, arg1 : string) => DivideState) => List<string> */(annotationsString/* : string */.strip/* : unknown */()/* : unknown */, (state1, c) => this/* : Main */.foldByDelimiter/* : (arg0 : DivideState, arg1 : string, arg2 : string) => DivideState */(state1/* : unknown */, c/* : unknown */, /*  '\n' */)/* : DivideState */)/* : List<string> */.query/* : () => Query<T> */()/* : Query<T> */.map/* : (arg0 : (arg0 : T) => R) => Query<R> */(/* String */.strip/* : unknown */)/* : Query<R> */.filter/* : (arg0 : (arg0 : T) => boolean) => Option<T> */((value : T) => !value/* : T */.isEmpty/* : unknown */()/* : unknown */)/* : Option<T> */.map/* : (arg0 : (arg0 : T) => R) => Option<R> */((value : T) => value/* : T */.substring/* : unknown */(1/* : number */)/* : unknown */)/* : Option<R> */.map/* : (arg0 : (arg0 : T) => R) => Option<R> */(/* String */.strip/* : unknown */)/* : Option<R> */.filter/* : (arg0 : (arg0 : T) => boolean) => Option<T> */((value : T) => !value/* : T */.isEmpty/* : unknown */()/* : unknown */)/* : Option<T> */.collect/* : (arg0 : Collector<T, R>) => R */(new ListCollector()/* : ListCollector */)/* : R */;
	}
	private foldByDelimiter(state1 : DivideState, c : string, delimiter : string) : DivideState {
		if (c/* : string */ === delimiter/* : string */){
			return state1/* : DivideState */.advance/* : () => DivideState */()/* : DivideState */;
		}
		return state1/* : DivideState */.append/* : (arg0 : string) => DivideState */(c/* : string */)/* : DivideState */;
	}
	private parseStructureWithMaybePermits(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string, annotations : List<string>) : Option<[CompileState, IncompleteClassSegment]> {
		return this/* : Main */.last/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(beforeContent/* : string */, " permits ", (s, s2) => {
			let variants : R = this/* : Main */.divideAll/* : (arg0 : string, arg1 : (arg0 : DivideState, arg1 : string) => DivideState) => List<string> */(s2/* : unknown */, this/* : Main */.foldValueChar/* : unknown */)/* : List<string> */.query/* : () => Query<T> */()/* : Query<T> */.map/* : (arg0 : (arg0 : T) => R) => Query<R> */(/* String */.strip/* : unknown */)/* : Query<R> */.filter/* : (arg0 : (arg0 : T) => boolean) => Option<T> */((value : T) => !value/* : T */.isEmpty/* : unknown */()/* : unknown */)/* : Option<T> */.collect/* : (arg0 : Collector<T, R>) => R */(new ListCollector()/* : ListCollector */)/* : R */;
			return this/* : Main */.parseStructureWithMaybeImplements/* : (arg0 : string, arg1 : CompileState, arg2 : string, arg3 : string, arg4 : string, arg5 : List<string>, arg6 : List<string>) => Option<[CompileState, IncompleteClassSegment]> */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : string */, s/* : unknown */, content1/* : string */, variants/* : R */, annotations/* : List<string> */)/* : Option<[CompileState, IncompleteClassSegment]> */;
		})/* : Option<T> */.or/* : (arg0 : () => Option<T>) => Option<T> */(() => {
			return this/* : Main */.parseStructureWithMaybeImplements/* : (arg0 : string, arg1 : CompileState, arg2 : string, arg3 : string, arg4 : string, arg5 : List<string>, arg6 : List<string>) => Option<[CompileState, IncompleteClassSegment]> */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : string */, beforeContent/* : string */, content1/* : string */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, annotations/* : List<string> */)/* : Option<[CompileState, IncompleteClassSegment]> */;
		})/* : Option<T> */;
	}
	private parseStructureWithMaybeImplements(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string, variants : List<string>, annotations : List<string>) : Option<[CompileState, IncompleteClassSegment]> {
		return this/* : Main */.first/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(beforeContent/* : string */, " implements ", (s, s2) => {
			return this/* : Main */.parseValues/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) => Option<[CompileState, List<T>]> */(state/* : CompileState */, s2/* : unknown */, this/* : Main */.parseType/* : unknown */)/* : Option<[CompileState, List<T>]> */.flatMap/* : (arg0 : (arg0 : [CompileState, List<T>]) => Option<R>) => Option<R> */((interfaces : [CompileState, List<T>]) => {
				return this/* : Main */.parseStructureWithMaybeExtends/* : (arg0 : string, arg1 : CompileState, arg2 : string, arg3 : string, arg4 : string, arg5 : List<string>, arg6 : List<string>, arg7 : List<Type>) => Option<[CompileState, IncompleteClassSegment]> */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : string */, s/* : unknown */, content1/* : string */, variants/* : List<string> */, annotations/* : List<string> */, interfaces/* : [CompileState, List<T>] */[1/* : number */])/* : Option<[CompileState, IncompleteClassSegment]> */;
			})/* : Option<R> */;
		})/* : Option<T> */.or/* : (arg0 : () => Option<T>) => Option<T> */(() => {
			return this/* : Main */.parseStructureWithMaybeExtends/* : (arg0 : string, arg1 : CompileState, arg2 : string, arg3 : string, arg4 : string, arg5 : List<string>, arg6 : List<string>, arg7 : List<Type>) => Option<[CompileState, IncompleteClassSegment]> */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : string */, beforeContent/* : string */, content1/* : string */, variants/* : List<string> */, annotations/* : List<string> */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */)/* : Option<[CompileState, IncompleteClassSegment]> */;
		})/* : Option<T> */;
	}
	private parseStructureWithMaybeExtends(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string, variants : List<string>, annotations : List<string>, interfaces : List<Type>) : Option<[CompileState, IncompleteClassSegment]> {
		return this/* : Main */.first/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(beforeContent/* : string */, " extends ", (s, s2) => {
			return this/* : Main */.parseStructureWithMaybeParams/* : (arg0 : string, arg1 : CompileState, arg2 : string, arg3 : string, arg4 : string, arg5 : List<string>, arg6 : List<string>, arg7 : List<Type>) => Option<[CompileState, IncompleteClassSegment]> */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : string */, s/* : unknown */, content1/* : string */, variants/* : List<string> */, annotations/* : List<string> */, interfaces/* : List<Type> */)/* : Option<[CompileState, IncompleteClassSegment]> */;
		})/* : Option<T> */.or/* : (arg0 : () => Option<T>) => Option<T> */(() => {
			return this/* : Main */.parseStructureWithMaybeParams/* : (arg0 : string, arg1 : CompileState, arg2 : string, arg3 : string, arg4 : string, arg5 : List<string>, arg6 : List<string>, arg7 : List<Type>) => Option<[CompileState, IncompleteClassSegment]> */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : string */, beforeContent/* : string */, content1/* : string */, variants/* : List<string> */, annotations/* : List<string> */, interfaces/* : List<Type> */)/* : Option<[CompileState, IncompleteClassSegment]> */;
		})/* : Option<T> */;
	}
	private parseStructureWithMaybeParams(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string, variants : List<string>, annotations : List<string>, interfaces : List<Type>) : Option<[CompileState, IncompleteClassSegment]> {
		return this/* : Main */.suffix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(beforeContent/* : string */.strip/* : unknown */()/* : unknown */, ")", (s : string) => {
			return this/* : Main */.first/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(s/* : string */, "(", (s1, s2) => {
				let parsed : [CompileState, List<Parameter>] = this/* : Main */.parseParameters/* : (arg0 : CompileState, arg1 : string) => [CompileState, List<Parameter>] */(state/* : CompileState */, s2/* : unknown */)/* : [CompileState, List<Parameter>] */;
				return this/* : Main */.parseStructureWithMaybeTypeParams/* : (arg0 : string, arg1 : CompileState, arg2 : string, arg3 : string, arg4 : string, arg5 : List<Parameter>, arg6 : List<string>, arg7 : List<string>, arg8 : List<Type>) => Option<[CompileState, IncompleteClassSegment]> */(targetInfix/* : string */, parsed/* : [CompileState, List<Parameter>] */[0/* : number */], beforeInfix/* : string */, s1/* : unknown */, content1/* : string */, parsed/* : [CompileState, List<Parameter>] */[1/* : number */], variants/* : List<string> */, annotations/* : List<string> */, interfaces/* : List<Type> */)/* : Option<[CompileState, IncompleteClassSegment]> */;
			})/* : Option<T> */;
		})/* : Option<T> */.or/* : (arg0 : () => Option<T>) => Option<T> */(() => {
			return this/* : Main */.parseStructureWithMaybeTypeParams/* : (arg0 : string, arg1 : CompileState, arg2 : string, arg3 : string, arg4 : string, arg5 : List<Parameter>, arg6 : List<string>, arg7 : List<string>, arg8 : List<Type>) => Option<[CompileState, IncompleteClassSegment]> */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : string */, beforeContent/* : string */, content1/* : string */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, variants/* : List<string> */, annotations/* : List<string> */, interfaces/* : List<Type> */)/* : Option<[CompileState, IncompleteClassSegment]> */;
		})/* : Option<T> */;
	}
	private parseStructureWithMaybeTypeParams(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string, params : List<Parameter>, variants : List<string>, annotations : List<string>, interfaces : List<Type>) : Option<[CompileState, IncompleteClassSegment]> {
		return this/* : Main */.first/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(beforeContent/* : string */, "<", (name, withTypeParams) => {
			return this/* : Main */.first/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(withTypeParams/* : unknown */, ">", (typeParamsString, afterTypeParams) => {
				let final mapper : (arg0 : CompileState, arg1 : string) => [CompileState, string] = (state1, s) => [state1/* : unknown */, s/* : unknown */.strip/* : unknown */()/* : unknown */];
				let typeParams : [CompileState, List<T>] = this/* : Main */.parseValuesOrEmpty/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) => [CompileState, List<T>] */(state/* : CompileState */, typeParamsString/* : unknown */, (state1, s) => new Some(mapper/* : (arg0 : CompileState, arg1 : string) => [CompileState, string] */(state1/* : unknown */, s/* : unknown */)/* : [CompileState, string] */)/* : Some */)/* : [CompileState, List<T>] */;
				return this/* : Main */.assembleStructure/* : (arg0 : CompileState, arg1 : string, arg2 : List<string>, arg3 : string, arg4 : string, arg5 : string, arg6 : List<string>, arg7 : string, arg8 : List<Parameter>, arg9 : List<string>, arg10 : List<Type>) => Option<[CompileState, IncompleteClassSegment]> */(typeParams/* : [CompileState, List<T>] */[0/* : number */], targetInfix/* : string */, annotations/* : List<string> */, beforeInfix/* : string */, name/* : unknown */, content1/* : string */, typeParams/* : [CompileState, List<T>] */[1/* : number */], afterTypeParams/* : unknown */, params/* : List<Parameter> */, variants/* : List<string> */, interfaces/* : List<Type> */)/* : Option<[CompileState, IncompleteClassSegment]> */;
			})/* : Option<T> */;
		})/* : Option<T> */.or/* : (arg0 : () => Option<T>) => Option<T> */(() => {
			return this/* : Main */.assembleStructure/* : (arg0 : CompileState, arg1 : string, arg2 : List<string>, arg3 : string, arg4 : string, arg5 : string, arg6 : List<string>, arg7 : string, arg8 : List<Parameter>, arg9 : List<string>, arg10 : List<Type>) => Option<[CompileState, IncompleteClassSegment]> */(state/* : CompileState */, targetInfix/* : string */, annotations/* : List<string> */, beforeInfix/* : string */, beforeContent/* : string */, content1/* : string */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, "", params/* : List<Parameter> */, variants/* : List<string> */, interfaces/* : List<Type> */)/* : Option<[CompileState, IncompleteClassSegment]> */;
		})/* : Option<T> */;
	}
	private assembleStructure(state : CompileState, targetInfix : string, annotations : List<string>, beforeInfix : string, rawName : string, content : string, typeParams : List<string>, after : string, rawParameters : List<Parameter>, variants : List<string>, interfaces : List<Type>) : Option<[CompileState, IncompleteClassSegment]> {
		let name = rawName/* : string */.strip/* : unknown */()/* : unknown */;
		if (!this/* : Main */.isSymbol/* : unknown */(name/* : unknown */)/* : unknown */){
			return new None()/* : None */;
		}
		if (annotations/* : List<string> */.contains/* : (arg0 : string) => boolean */("Actual")/* : boolean */){
			return new Some([state/* : CompileState */, new Whitespace()/* : Whitespace */])/* : Some */;
		}
		let segmentsTuple : [CompileState, List<T>] = this/* : Main */.parseStatements/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => [CompileState, T]) => [CompileState, List<T>] */(state/* : CompileState */.pushStructName/* : (arg0 : Tuple2Impl<string, List<string>>) => CompileState */([name/* : unknown */, variants/* : List<string> */])/* : CompileState */.withTypeParams/* : (arg0 : List<string>) => CompileState */(typeParams/* : List<string> */)/* : CompileState */, content/* : string */, (state0, input) => this/* : Main */.parseClassSegment/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, IncompleteClassSegment] */(state0/* : unknown */, input/* : unknown */, 1/* : number */)/* : [CompileState, IncompleteClassSegment] */)/* : [CompileState, List<T>] */;
		let segmentsState = segmentsTuple/* : [CompileState, List<T>] */[0/* : number */];
		let segments = segmentsTuple/* : [CompileState, List<T>] */[1/* : number */];
		let parameters : List<Definition> = this/* : Main */.retainDefinitions/* : (arg0 : List<Parameter>) => List<Definition> */(rawParameters/* : List<Parameter> */)/* : List<Definition> */;
		let prototype : StructurePrototype = new StructurePrototype(targetInfix/* : string */, beforeInfix/* : string */, name/* : unknown */, typeParams/* : List<string> */, parameters/* : List<Definition> */, after/* : string */, segments/* : unknown */, variants/* : List<string> */, interfaces/* : List<Type> */)/* : StructurePrototype */;
		return new Some([segmentsState/* : unknown */.addType/* : unknown */(prototype/* : StructurePrototype */.createObjectType/* : () => ObjectType */()/* : ObjectType */)/* : unknown */, prototype/* : StructurePrototype */])/* : Some */;
	}
	private completeStructure(state : CompileState, prototype : StructurePrototype) : Option<[CompileState, ClassSegment]> {
		let thisType : ObjectType = prototype/* : StructurePrototype */.createObjectType/* : () => ObjectType */()/* : ObjectType */;
		let state2 : CompileState = state/* : CompileState */.enterDefinitions/* : () => CompileState */()/* : CompileState */.define/* : (arg0 : Definition) => CompileState */(ImmutableDefinition/* : ImmutableDefinition */.createSimpleDefinition/* : (arg0 : string, arg1 : Type) => Definition */("this", thisType/* : ObjectType */)/* : Definition */)/* : CompileState */;
		let bases : R = prototype/* : StructurePrototype */.interfaces/* : List<Type> */.query/* : () => Query<T> */()/* : Query<T> */.map/* : (arg0 : (arg0 : T) => R) => Query<R> */(Main/* : Main */.retainFindableType/* : unknown */)/* : Query<R> */.flatMap/* : (arg0 : (arg0 : T) => Option<R>) => Option<R> */(Queries/* : Queries */.fromOption/* : unknown */)/* : Option<R> */.map/* : (arg0 : (arg0 : T) => R) => Option<R> */(FindableType/* : FindableType */.findBase/* : unknown */)/* : Option<R> */.flatMap/* : (arg0 : (arg0 : T) => Option<R>) => Option<R> */(Queries/* : Queries */.fromOption/* : unknown */)/* : Option<R> */.collect/* : (arg0 : Collector<T, R>) => R */(new ListCollector()/* : ListCollector */)/* : R */;
		let variantsSuper = bases/* : R */.query/* : unknown */()/* : unknown */.filter/* : unknown */((type) => type/* : unknown */.variants/* : unknown */()/* : unknown */.contains/* : unknown */(prototype/* : StructurePrototype */.name/* : string */)/* : unknown */)/* : unknown */.map/* : unknown */(BaseType/* : BaseType */.name/* : unknown */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
		return this/* : Main */.mapUsingState/* : (arg0 : CompileState, arg1 : List<T>, arg2 : (arg0 : CompileState, arg1 : [number, T]) => Option<[CompileState, R]>) => Option<[CompileState, List<R>]> */(state2/* : CompileState */, prototype/* : StructurePrototype */.segments/* : List<IncompleteClassSegment> */()/* : unknown */, (state1, entry) => this/* : Main */.completeClassSegment/* : (arg0 : CompileState, arg1 : IncompleteClassSegment) => Option<[CompileState, ClassSegment]> */(state1/* : unknown */, entry/* : unknown */.right/* : unknown */()/* : unknown */)/* : Option<[CompileState, ClassSegment]> */)/* : Option<[CompileState, List<R>]> */.map/* : (arg0 : (arg0 : [CompileState, List<R>]) => R) => Option<R> */((oldStatementsTuple : [CompileState, List<R>]) => {
			let oldStatementsState = oldStatementsTuple/* : [CompileState, List<R>] */[0/* : number */];
			let oldStatements = oldStatementsTuple/* : [CompileState, List<R>] */[1/* : number */];
			let exited = oldStatementsState/* : unknown */.exitDefinitions/* : unknown */()/* : unknown */;
			let fold = variantsSuper/* : unknown */.query/* : unknown */()/* : unknown */.fold/* : unknown */(oldStatements/* : unknown */, (classSegmentList, superType) => {
				let name = superType/* : unknown */ + "Variant";
				let type : ObjectType = new ObjectType(name/* : unknown */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */)/* : ObjectType */;
				let definition : Definition = this/* : Main */.createVariantDefinition/* : (arg0 : ObjectType) => Definition */(type/* : unknown */)/* : Definition */;
				return classSegmentList/* : unknown */.addFirst/* : unknown */(new Statement(1/* : number */, new FieldInitialization(definition/* : Definition */, new SymbolValue(name/* : unknown */ + "." + prototype/* : StructurePrototype */.name/* : unknown */, type/* : unknown */)/* : SymbolValue */)/* : FieldInitialization */)/* : Statement */)/* : unknown */;
			})/* : unknown */;
			/* CompileState withEnum */;
			/* List<ClassSegment> newSegments */;
			if (prototype/* : StructurePrototype */.variants/* : List<string> */.isEmpty/* : () => boolean */()/* : boolean */){
				/* withEnum */ = exited/* : unknown */;
				/* newSegments */ = fold/* : unknown */;
			}
			else {
				let joined = prototype/* : StructurePrototype */.variants/* : List<string> */.query/* : () => Query<T> */()/* : Query<T> */.map/* : (arg0 : (arg0 : T) => R) => Query<R> */((inner : T) => "\n\t" + inner/* : T */)/* : Query<R> */.collect/* : (arg0 : Collector<T, R>) => R */(new Joiner(",")/* : Joiner */)/* : R */.orElse/* : unknown */("")/* : unknown */;
				/* withEnum */ = exited/* : unknown */.addStructure/* : unknown */("enum " + prototype.name + "Variant" + " {" +
                        joined +
                        "\n}\n")/* : unknown */;
				let definition : Definition = this/* : Main */.createVariantDefinition/* : (arg0 : ObjectType) => Definition */(new ObjectType(prototype/* : StructurePrototype */.name/* : string */ + "Variant", Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, prototype/* : StructurePrototype */.variants/* : List<string> */)/* : ObjectType */)/* : Definition */;
				/* newSegments */ = fold/* : unknown */.addFirst/* : unknown */(new Statement(1/* : number */, definition/* : Definition */)/* : Statement */)/* : unknown */;
			}
			let withMaybeConstructor : List<ClassSegment> = this/* : Main */.attachConstructor/* : (arg0 : StructurePrototype, arg1 : List<ClassSegment>) => List<ClassSegment> */(prototype/* : StructurePrototype */, /* newSegments */)/* : List<ClassSegment> */;
			let parsed2 = withMaybeConstructor/* : List<ClassSegment> */.query/* : () => Query<T> */()/* : Query<T> */.map/* : (arg0 : (arg0 : T) => R) => Query<R> */(ClassSegment/* : ClassSegment */.generate/* : unknown */)/* : Query<R> */.collect/* : (arg0 : Collector<T, R>) => R */(Joiner/* : Joiner */.empty/* : () => Joiner */()/* : Joiner */)/* : R */.orElse/* : unknown */("")/* : unknown */;
			let joinedTypeParams : string = prototype/* : StructurePrototype */.joinTypeParams/* : () => string */()/* : string */;
			let interfacesJoined : string = prototype/* : StructurePrototype */.joinInterfaces/* : () => string */()/* : string */;
			let generated = generatePlaceholder/* : (arg0 : string) => string */(prototype/* : StructurePrototype */.beforeInfix/* : string */()/* : unknown */.strip/* : unknown */()/* : unknown */)/* : string */ + prototype/* : StructurePrototype */.targetInfix/* : string */()/* : unknown */ + prototype/* : StructurePrototype */.name/* : string */()/* : unknown */ + joinedTypeParams/* : string */ + generatePlaceholder/* : (arg0 : string) => string */(prototype/* : StructurePrototype */.after/* : string */()/* : unknown */)/* : string */ + interfacesJoined/* : string */ + " {" + parsed2 + "\n}\n";
			let compileState = /* withEnum */.popStructName/* : unknown */()/* : unknown */;
			let definedState = compileState/* : unknown */.addStructure/* : unknown */(generated/* : unknown */)/* : unknown */;
			return [definedState/* : unknown */, new Whitespace()/* : Whitespace */];
		})/* : Option<R> */;
	}
	private createVariantDefinition(type : ObjectType) : Definition {
		return ImmutableDefinition/* : ImmutableDefinition */.createSimpleDefinition/* : (arg0 : string, arg1 : Type) => Definition */("_" + type/* : ObjectType */.name/* : unknown */, type/* : ObjectType */)/* : Definition */;
	}
	private attachConstructor(prototype : StructurePrototype, segments : List<ClassSegment>) : List<ClassSegment> {
		let parameters = prototype/* : StructurePrototype */.parameters/* : List<Definition> */()/* : unknown */;
		if (parameters/* : unknown */.isEmpty/* : unknown */()/* : unknown */){
			return segments/* : List<ClassSegment> */;
		}
		let definitions : List<ClassSegment> = parameters/* : unknown */.query/* : unknown */()/* : unknown */./* : unknown */ < /* ClassSegment>map */((definition) => new Statement(1/* : number */, definition/* : unknown */)/* : Statement */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
		let collect = /* parameters.query()
                .map(definition  */ - /* > {
                    var destination = new DataAccess(new SymbolValue("this", Primitive.Unknown), definition.findName(), Primitive.Unknown);
                    return new Assignment */(/* destination */, /*  new SymbolValue(definition.findName(), Primitive.Unknown));
                } */)/* : unknown */./* : unknown */ < /* FunctionSegment>map */((assignment) => new Statement(2/* : number */, assignment/* : unknown */)/* : Statement */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
		let func : FunctionNode = new FunctionNode(1/* : number */, new ConstructorHeader()/* : ConstructorHeader */, parameters/* : unknown */, new Some(collect/* : unknown */)/* : Some */)/* : FunctionNode */;
		return segments/* : List<ClassSegment> */.addFirst/* : (arg0 : ClassSegment) => List<T> */(func/* : FunctionNode */)/* : List<T> */.addAllFirst/* : (arg0 : List<T>) => List<T> */(definitions/* : List<ClassSegment> */)/* : List<T> */;
	}
	private completeClassSegment(state1 : CompileState, segment : IncompleteClassSegment) : Option<[CompileState, ClassSegment]> {
		/* return switch (segment) */{
			/* case IncompleteClassSegmentWrapper wrapper -> new Some<>(new Tuple2Impl<>(state1, wrapper.segment)) */;
			/* case MethodPrototype methodPrototype -> this.completeMethod(state1, methodPrototype) */;
			/* case Whitespace whitespace -> new Some<>(new Tuple2Impl<>(state1, whitespace)) */;
			/* case Placeholder placeholder -> new Some<>(new Tuple2Impl<>(state1, placeholder)) */;
			/* case ClassDefinition classDefinition -> this.completeDefinition(state1, classDefinition) */;
			/* case ClassInitialization classInitialization -> this.completeInitialization(state1, classInitialization) */;
			/* case StructurePrototype structurePrototype -> this.completeStructure(state1, structurePrototype) */;
		}
		/*  */;
	}
	private completeInitialization(state1 : CompileState, classInitialization : ClassInitialization) : Option<[CompileState, ClassSegment]> {
		let definition : Definition = classInitialization/* : ClassInitialization */.definition/* : Definition */;
		let statement : Statement = new Statement(classInitialization/* : ClassInitialization */.depth/* : number */, new FieldInitialization(definition/* : Definition */, classInitialization/* : ClassInitialization */.value/* : Value */)/* : FieldInitialization */)/* : Statement */;
		return new Some([state1/* : CompileState */, statement/* : Statement */])/* : Some */;
	}
	private completeDefinition(state1 : CompileState, classDefinition : ClassDefinition) : Option<[CompileState, ClassSegment]> {
		let definition : Definition = classDefinition/* : ClassDefinition */.definition/* : Definition */;
		let statement : Statement = new Statement(classDefinition/* : ClassDefinition */.depth/* : number */, definition/* : Definition */)/* : Statement */;
		return new Some([state1/* : CompileState */, statement/* : Statement */])/* : Some */;
	}
	private retainDefinition(parameter : Parameter) : Option<Definition> {
		if (parameter/* : Parameter */._ParameterVariant/* : unknown */ === ParameterVariant.Definition/* : unknown */){
			let definition : Definition = parameter/* : Parameter */ as Definition;
			return new Some(definition/* : Definition */)/* : Some */;
		}
		return new None()/* : None */;
	}
	private isSymbol(input : string) : boolean {
		/* for (var i = 0; i < input.length(); i++) */{
			let c = input/* : string */.charAt/* : unknown */(/* i */)/* : unknown */;
			if (/* Character */.isLetter/* : unknown */(c/* : unknown */)/* : unknown */ || /*  */(/* i != 0  */ && /* Character */.isDigit/* : unknown */(c/* : unknown */)/* : unknown */)/* : unknown */){
				/* continue */;
			}
			return false;
		}
		return true;
	}
	private prefix<T>(input : string, prefix : string, mapper : (arg0 : string) => Option<T>) : Option<T> {
		if (!input/* : string */.startsWith/* : unknown */(prefix/* : string */)/* : unknown */){
			return new None()/* : None */;
		}
		let slice = input/* : string */.substring/* : unknown */(prefix/* : string */.length/* : unknown */()/* : unknown */)/* : unknown */;
		return mapper/* : (arg0 : string) => Option<T> */(slice/* : unknown */)/* : Option<T> */;
	}
	private suffix<T>(input : string, suffix : string, mapper : (arg0 : string) => Option<T>) : Option<T> {
		if (!input/* : string */.endsWith/* : unknown */(suffix/* : string */)/* : unknown */){
			return new None()/* : None */;
		}
		let slice = input/* : string */.substring/* : unknown */(0/* : number */, input/* : string */.length/* : unknown */()/* : unknown */ - suffix/* : string */.length/* : unknown */()/* : unknown */)/* : unknown */;
		return mapper/* : (arg0 : string) => Option<T> */(slice/* : unknown */)/* : Option<T> */;
	}
	private parseClassSegment(state : CompileState, input : string, depth : number) : [CompileState, IncompleteClassSegment] {
		return this/* : Main */./* : unknown */ < /* Whitespace, IncompleteClassSegment>typed */(() => this/* : Main */.parseWhitespace/* : (arg0 : string, arg1 : CompileState) => Option<[CompileState, Whitespace]> */(input/* : string */, state/* : CompileState */)/* : Option<[CompileState, Whitespace]> */)/* : unknown */.or/* : unknown */(() => this/* : Main */.typed/* : (arg0 : () => Option<[CompileState, T]>) => Option<[CompileState, S]> */(() => this/* : Main */.parseClass/* : (arg0 : string, arg1 : CompileState) => Option<[CompileState, IncompleteClassSegment]> */(input/* : string */, state/* : CompileState */)/* : Option<[CompileState, IncompleteClassSegment]> */)/* : Option<[CompileState, S]> */)/* : unknown */.or/* : unknown */(() => this/* : Main */.typed/* : (arg0 : () => Option<[CompileState, T]>) => Option<[CompileState, S]> */(() => this/* : Main */.parseStructure/* : (arg0 : string, arg1 : string, arg2 : string, arg3 : CompileState) => Option<[CompileState, IncompleteClassSegment]> */(input/* : string */, "interface ", "interface ", state/* : CompileState */)/* : Option<[CompileState, IncompleteClassSegment]> */)/* : Option<[CompileState, S]> */)/* : unknown */.or/* : unknown */(() => this/* : Main */.typed/* : (arg0 : () => Option<[CompileState, T]>) => Option<[CompileState, S]> */(() => this/* : Main */.parseStructure/* : (arg0 : string, arg1 : string, arg2 : string, arg3 : CompileState) => Option<[CompileState, IncompleteClassSegment]> */(input/* : string */, "record ", "class ", state/* : CompileState */)/* : Option<[CompileState, IncompleteClassSegment]> */)/* : Option<[CompileState, S]> */)/* : unknown */.or/* : unknown */(() => this/* : Main */.typed/* : (arg0 : () => Option<[CompileState, T]>) => Option<[CompileState, S]> */(() => this/* : Main */.parseStructure/* : (arg0 : string, arg1 : string, arg2 : string, arg3 : CompileState) => Option<[CompileState, IncompleteClassSegment]> */(input/* : string */, "enum ", "class ", state/* : CompileState */)/* : Option<[CompileState, IncompleteClassSegment]> */)/* : Option<[CompileState, S]> */)/* : unknown */.or/* : unknown */(() => this/* : Main */.typed/* : (arg0 : () => Option<[CompileState, T]>) => Option<[CompileState, S]> */(() => this/* : Main */.parseField/* : (arg0 : string, arg1 : number, arg2 : CompileState) => Option<[CompileState, IncompleteClassSegment]> */(input/* : string */, depth/* : number */, state/* : CompileState */)/* : Option<[CompileState, IncompleteClassSegment]> */)/* : Option<[CompileState, S]> */)/* : unknown */.or/* : unknown */(() => this/* : Main */.parseMethod/* : (arg0 : CompileState, arg1 : string, arg2 : number) => Option<[CompileState, IncompleteClassSegment]> */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : Option<[CompileState, IncompleteClassSegment]> */)/* : unknown */.orElseGet/* : unknown */(() => [state/* : CompileState */, new Placeholder(input/* : string */)/* : Placeholder */])/* : unknown */;
	}
	private typed<T extends S, S>(action : () => Option<[CompileState, T]>) : Option<[CompileState, S]> {
		return action/* : () => Option<[CompileState, T]> */()/* : Option<[CompileState, T]> */.map/* : (arg0 : (arg0 : [CompileState, T]) => R) => Option<R> */((tuple : [CompileState, T]) => [tuple/* : [CompileState, T] */[0/* : number */], tuple/* : [CompileState, T] */[1/* : number */]])/* : Option<R> */;
	}
	private parseWhitespace(input : string, state : CompileState) : Option<[CompileState, Whitespace]> {
		if (input/* : string */.isBlank/* : unknown */()/* : unknown */){
			return new Some([state/* : CompileState */, new Whitespace()/* : Whitespace */])/* : Some */;
		}
		return new None()/* : None */;
	}
	private parseMethod(state : CompileState, input : string, depth : number) : Option<[CompileState, IncompleteClassSegment]> {
		return this/* : Main */.first/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(input/* : string */, "(", (definitionString, withParams) => {
			return this/* : Main */.first/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(withParams/* : unknown */, ")", (parametersString, rawContent) => {
				return this/* : Main */.parseDefinition/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Definition]> */(state/* : CompileState */, definitionString/* : unknown */)/* : Option<[CompileState, Definition]> */./* : unknown */ < Tuple2/* : Tuple2 */ < /* CompileState, Header>>map */((tuple) => [tuple/* : unknown */.left/* : unknown */()/* : unknown */, tuple/* : unknown */.right/* : unknown */()/* : unknown */])/* : unknown */.or/* : unknown */(() => this/* : Main */.parseConstructor/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Header]> */(state/* : CompileState */, definitionString/* : unknown */)/* : Option<[CompileState, Header]> */)/* : unknown */.flatMap/* : unknown */((definitionTuple) => this/* : Main */.assembleMethod/* : (arg0 : number, arg1 : string, arg2 : string, arg3 : [CompileState, Header]) => Option<[CompileState, IncompleteClassSegment]> */(depth/* : number */, parametersString/* : unknown */, rawContent/* : unknown */, definitionTuple/* : unknown */)/* : Option<[CompileState, IncompleteClassSegment]> */)/* : unknown */;
			})/* : Option<T> */;
		})/* : Option<T> */;
	}
	private assembleMethod(depth : number, parametersString : string, rawContent : string, definitionTuple : [CompileState, Header]) : Option<[CompileState, IncompleteClassSegment]> {
		let definitionState = definitionTuple/* : [CompileState, Header] */[0/* : number */];
		let header = definitionTuple/* : [CompileState, Header] */[1/* : number */];
		let parametersTuple : [CompileState, List<Parameter>] = this/* : Main */.parseParameters/* : (arg0 : CompileState, arg1 : string) => [CompileState, List<Parameter>] */(definitionState/* : unknown */, parametersString/* : string */)/* : [CompileState, List<Parameter>] */;
		let rawParameters = parametersTuple/* : [CompileState, List<Parameter>] */[1/* : number */];
		let parameters : List<Definition> = this/* : Main */.retainDefinitions/* : (arg0 : List<Parameter>) => List<Definition> */(rawParameters/* : unknown */)/* : List<Definition> */;
		let prototype : MethodPrototype = new MethodPrototype(depth/* : number */, header/* : unknown */, parameters/* : List<Definition> */, rawContent/* : string */.strip/* : unknown */()/* : unknown */)/* : MethodPrototype */;
		return new Some([parametersTuple/* : [CompileState, List<Parameter>] */[0/* : number */].define/* : unknown */(prototype/* : MethodPrototype */.createDefinition/* : () => Definition */()/* : Definition */)/* : unknown */, prototype/* : MethodPrototype */])/* : Some */;
	}
	private completeMethod(state : CompileState, prototype : MethodPrototype) : Option<[CompileState, ClassSegment]> {
		let definition : Definition = prototype/* : MethodPrototype */.createDefinition/* : () => Definition */()/* : Definition */;
		let oldHeader = prototype/* : MethodPrototype */.header/* : Header */()/* : unknown */;
		/* Header newHeader */;
		if (oldHeader/* : unknown */._Variant/* : unknown */ === Variant.Definition/* : unknown */){
			let maybeDefinition : Definition = oldHeader/* : unknown */ as Definition;
			/* newHeader */ = maybeDefinition/* : Definition */.removeAnnotations/* : () => Definition */()/* : Definition */;
		}
		else {
			/* newHeader */ = oldHeader/* : unknown */;
		}
		if (prototype/* : MethodPrototype */.content/* : string */()/* : unknown */.equals/* : unknown */(";")/* : unknown */ || definition/* : Definition */.containsAnnotation/* : unknown */("Actual")/* : unknown */){
			return new Some([state/* : CompileState */.define/* : (arg0 : Definition) => CompileState */(definition/* : Definition */)/* : CompileState */, new FunctionNode(prototype/* : MethodPrototype */.depth/* : number */()/* : unknown */, /* newHeader */, prototype/* : MethodPrototype */.parameters/* : List<Definition> */()/* : unknown */, new None()/* : None */)/* : FunctionNode */])/* : Some */;
		}
		if (prototype/* : MethodPrototype */.content/* : string */()/* : unknown */.startsWith/* : unknown */("{")/* : unknown */ && prototype/* : MethodPrototype */.content/* : unknown */()/* : unknown */.endsWith/* : unknown */("}")/* : unknown */){
			let substring = prototype/* : MethodPrototype */.content/* : string */()/* : unknown */.substring/* : unknown */(1/* : number */, prototype/* : MethodPrototype */.content/* : string */()/* : unknown */.length/* : unknown */()/* : unknown */ - 1/* : number */)/* : unknown */;
			let withDefined : CompileState = state/* : CompileState */.enterDefinitions/* : () => CompileState */()/* : CompileState */.defineAll/* : (arg0 : List<Definition>) => CompileState */(prototype/* : MethodPrototype */.parameters/* : List<Definition> */()/* : unknown */)/* : CompileState */;
			let statementsTuple : [CompileState, List<T>] = this/* : Main */.parseStatements/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => [CompileState, T]) => [CompileState, List<T>] */(withDefined/* : CompileState */, substring/* : unknown */, (state1, input1) => this/* : Main */.parseFunctionSegment/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, FunctionSegment] */(state1/* : unknown */, input1/* : unknown */, prototype/* : MethodPrototype */.depth/* : number */()/* : unknown */ + 1/* : number */)/* : [CompileState, FunctionSegment] */)/* : [CompileState, List<T>] */;
			let statements = statementsTuple/* : [CompileState, List<T>] */[1/* : number */];
			return new Some([statementsTuple/* : [CompileState, List<T>] */[0/* : number */].exitDefinitions/* : unknown */()/* : unknown */.define/* : unknown */(definition/* : Definition */)/* : unknown */, new FunctionNode(prototype/* : MethodPrototype */.depth/* : number */()/* : unknown */, /* newHeader */, prototype/* : MethodPrototype */.parameters/* : List<Definition> */()/* : unknown */, new Some(statements/* : unknown */)/* : Some */)/* : FunctionNode */])/* : Some */;
		}
		return new None()/* : None */;
	}
	private parseConstructor(state : CompileState, input : string) : Option<[CompileState, Header]> {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (state/* : CompileState */.isCurrentStructName/* : (arg0 : string) => boolean */(stripped/* : unknown */)/* : boolean */){
			return new Some([state/* : CompileState */, new ConstructorHeader()/* : ConstructorHeader */])/* : Some */;
		}
		return new None()/* : None */;
	}
	private retainDefinitions(right : List<Parameter>) : List<Definition> {
		return right/* : List<Parameter> */.query/* : () => Query<T> */()/* : Query<T> */.map/* : (arg0 : (arg0 : T) => R) => Query<R> */(this/* : Main */.retainDefinition/* : unknown */)/* : Query<R> */.flatMap/* : (arg0 : (arg0 : T) => Option<R>) => Option<R> */(Queries/* : Queries */.fromOption/* : unknown */)/* : Option<R> */.collect/* : (arg0 : Collector<T, R>) => R */(new ListCollector()/* : ListCollector */)/* : R */;
	}
	private parseParameters(state : CompileState, params : string) : [CompileState, List<Parameter>] {
		return this/* : Main */.parseValuesOrEmpty/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) => [CompileState, List<T>] */(state/* : CompileState */, params/* : string */, (state1, s) => new Some(this/* : Main */.parseParameter/* : (arg0 : CompileState, arg1 : string) => [CompileState, Parameter] */(state1/* : unknown */, s/* : unknown */)/* : [CompileState, Parameter] */)/* : Some */)/* : [CompileState, List<T>] */;
	}
	private parseFunctionSegments(state : CompileState, input : string, depth : number) : [CompileState, List<FunctionSegment>] {
		return this/* : Main */.parseStatements/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => [CompileState, T]) => [CompileState, List<T>] */(state/* : CompileState */, input/* : string */, (state1, input1) => this/* : Main */.parseFunctionSegment/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, FunctionSegment] */(state1/* : unknown */, input1/* : unknown */, depth/* : number */ + 1/* : number */)/* : [CompileState, FunctionSegment] */)/* : [CompileState, List<T>] */;
	}
	private parseFunctionSegment(state : CompileState, input : string, depth : number) : [CompileState, FunctionSegment] {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.isEmpty/* : unknown */()/* : unknown */){
			return [state/* : CompileState */, new Whitespace()/* : Whitespace */];
		}
		return this/* : Main */.parseFunctionStatement/* : (arg0 : CompileState, arg1 : number, arg2 : string) => Option<[CompileState, FunctionSegment]> */(state/* : CompileState */, depth/* : number */, stripped/* : unknown */)/* : Option<[CompileState, FunctionSegment]> */.or/* : (arg0 : () => Option<T>) => Option<T> */(() => this/* : Main */.parseBlock/* : (arg0 : CompileState, arg1 : number, arg2 : string) => Option<[CompileState, FunctionSegment]> */(state/* : CompileState */, depth/* : number */, stripped/* : unknown */)/* : Option<[CompileState, FunctionSegment]> */)/* : Option<T> */.orElseGet/* : (arg0 : () => T) => T */(() => [state/* : CompileState */, new Placeholder(stripped/* : unknown */)/* : Placeholder */])/* : T */;
	}
	private parseFunctionStatement(state : CompileState, depth : number, stripped : string) : Option<[CompileState, FunctionSegment]> {
		return this/* : Main */.suffix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(stripped/* : string */, ";", (s : string) => {
			let tuple : [CompileState, StatementValue] = this/* : Main */.parseStatementValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, StatementValue] */(state/* : CompileState */, s/* : string */, depth/* : number */)/* : [CompileState, StatementValue] */;
			let left = tuple/* : [CompileState, StatementValue] */[0/* : number */];
			let right = tuple/* : [CompileState, StatementValue] */[1/* : number */];
			return new Some([left/* : unknown */, new Statement(depth/* : number */, right/* : unknown */)/* : Statement */])/* : Some */;
		})/* : Option<T> */;
	}
	private parseBlock(state : CompileState, depth : number, stripped : string) : Option<[CompileState, FunctionSegment]> {
		return this/* : Main */.suffix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(stripped/* : string */, "}", (withoutEnd : string) => {
			return this/* : Main */.split/* : (arg0 : () => Option<[string, string]>, arg1 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(() => this/* : Main */.toFirst/* : (arg0 : string) => Option<[string, string]> */(withoutEnd/* : string */)/* : Option<[string, string]> */, (beforeContent, content) => {
				return this/* : Main */.suffix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(beforeContent/* : unknown */, "{", (headerString : string) => {
					let headerTuple : [CompileState, BlockHeader] = this/* : Main */.parseBlockHeader/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, BlockHeader] */(state/* : CompileState */, headerString/* : string */, depth/* : number */)/* : [CompileState, BlockHeader] */;
					let headerState = headerTuple/* : [CompileState, BlockHeader] */[0/* : number */];
					let header = headerTuple/* : [CompileState, BlockHeader] */[1/* : number */];
					let statementsTuple : [CompileState, List<FunctionSegment>] = this/* : Main */.parseFunctionSegments/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, List<FunctionSegment>] */(headerState/* : unknown */, content/* : unknown */, depth/* : number */)/* : [CompileState, List<FunctionSegment>] */;
					let statementsState = statementsTuple/* : [CompileState, List<FunctionSegment>] */[0/* : number */];
					let statements = statementsTuple/* : [CompileState, List<FunctionSegment>] */[1/* : number */].addAllFirst/* : unknown */(statementsState/* : unknown */.functionSegments/* : unknown */)/* : unknown */;
					return new Some([statementsState/* : unknown */.clearFunctionSegments/* : unknown */()/* : unknown */, new Block(depth/* : number */, header/* : unknown */, statements/* : unknown */)/* : Block */])/* : Some */;
				})/* : Option<T> */;
			})/* : Option<T> */;
		})/* : Option<T> */;
	}
	private toFirst(input : string) : Option<[string, string]> {
		let divisions : List<string> = this/* : Main */.divideAll/* : (arg0 : string, arg1 : (arg0 : DivideState, arg1 : string) => DivideState) => List<string> */(input/* : string */, this/* : Main */.foldBlockStart/* : unknown */)/* : List<string> */;
		return divisions/* : List<string> */.removeFirst/* : () => Option<[T, List<T>]> */()/* : Option<[T, List<T>]> */.map/* : (arg0 : (arg0 : [T, List<T>]) => R) => Option<R> */((removed : [T, List<T>]) => {
			let right = removed/* : [T, List<T>] */[0/* : number */];
			let left = removed/* : [T, List<T>] */[1/* : number */].query/* : unknown */()/* : unknown */.collect/* : unknown */(new Joiner("")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
			return [right/* : unknown */, left/* : unknown */];
		})/* : Option<R> */;
	}
	private parseBlockHeader(state : CompileState, input : string, depth : number) : [CompileState, BlockHeader] {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		return this/* : Main */.parseConditional/* : (arg0 : CompileState, arg1 : string, arg2 : string, arg3 : number) => Option<[CompileState, BlockHeader]> */(state/* : CompileState */, stripped/* : unknown */, "if", depth/* : number */)/* : Option<[CompileState, BlockHeader]> */.or/* : (arg0 : () => Option<T>) => Option<T> */(() => this/* : Main */.parseConditional/* : (arg0 : CompileState, arg1 : string, arg2 : string, arg3 : number) => Option<[CompileState, BlockHeader]> */(state/* : CompileState */, stripped/* : unknown */, "while", depth/* : number */)/* : Option<[CompileState, BlockHeader]> */)/* : Option<T> */.or/* : (arg0 : () => Option<T>) => Option<T> */(() => this/* : Main */.parseElse/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, BlockHeader]> */(state/* : CompileState */, input/* : string */)/* : Option<[CompileState, BlockHeader]> */)/* : Option<T> */.orElseGet/* : (arg0 : () => T) => T */(() => [state/* : CompileState */, new Placeholder(stripped/* : unknown */)/* : Placeholder */])/* : T */;
	}
	private parseElse(state : CompileState, input : string) : Option<[CompileState, BlockHeader]> {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.equals/* : unknown */("else")/* : unknown */){
			return new Some([state/* : CompileState */, new Else()/* : Else */])/* : Some */;
		}
		return new None()/* : None */;
	}
	private parseConditional(state : CompileState, input : string, prefix : string, depth : number) : Option<[CompileState, BlockHeader]> {
		return this/* : Main */.prefix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(input/* : string */, prefix/* : string */, (withoutPrefix : string) => {
			return this/* : Main */.prefix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(withoutPrefix/* : string */.strip/* : unknown */()/* : unknown */, "(", (withoutValueStart : string) => {
				return this/* : Main */.suffix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(withoutValueStart/* : string */, ")", (value : string) => {
					let valueTuple : [CompileState, Value] = this/* : Main */.parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state/* : CompileState */, value/* : string */, depth/* : number */)/* : [CompileState, Value] */;
					let value1 = valueTuple/* : [CompileState, Value] */[1/* : number */];
					return new Some([valueTuple/* : [CompileState, Value] */[0/* : number */], new Conditional(prefix/* : string */, value1/* : unknown */)/* : Conditional */])/* : Some */;
				})/* : Option<T> */;
			})/* : Option<T> */;
		})/* : Option<T> */;
	}
	private foldBlockStart(state : DivideState, c : string) : DivideState {
		let appended : DivideState = state/* : DivideState */.append/* : (arg0 : string) => DivideState */(c/* : string */)/* : DivideState */;
		if (c/* : string */ === /*  '{'  */ && state/* : DivideState */.isLevel/* : unknown */()/* : unknown */){
			return appended/* : DivideState */.advance/* : () => DivideState */()/* : DivideState */;
		}
		if (c/* : string */ === /*  '{' */){
			return appended/* : DivideState */.enter/* : () => DivideState */()/* : DivideState */;
		}
		if (c/* : string */ === /*  '}' */){
			return appended/* : DivideState */.exit/* : () => DivideState */()/* : DivideState */;
		}
		return appended/* : DivideState */;
	}
	private parseStatementValue(state : CompileState, input : string, depth : number) : [CompileState, StatementValue] {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.startsWith/* : unknown */("return ")/* : unknown */){
			let value = stripped/* : unknown */.substring/* : unknown */("return ".length/* : unknown */()/* : unknown */)/* : unknown */;
			let tuple : [CompileState, Value] = this/* : Main */.parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state/* : CompileState */, value/* : unknown */, depth/* : number */)/* : [CompileState, Value] */;
			let value1 = tuple/* : [CompileState, Value] */[1/* : number */];
			return [tuple/* : [CompileState, Value] */[0/* : number */], new Return(value1/* : unknown */)/* : Return */];
		}
		return this/* : Main */.parseAssignment/* : (arg0 : CompileState, arg1 : number, arg2 : string) => Option<[CompileState, StatementValue]> */(state/* : CompileState */, depth/* : number */, stripped/* : unknown */)/* : Option<[CompileState, StatementValue]> */.orElseGet/* : (arg0 : () => T) => T */(() => {
			return [state/* : CompileState */, new Placeholder(stripped/* : unknown */)/* : Placeholder */];
		})/* : T */;
	}
	private parseAssignment(state : CompileState, depth : number, stripped : string) : Option<[CompileState, StatementValue]> {
		return this/* : Main */.first/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(stripped/* : string */, "=", (beforeEquals, valueString) => {
			let sourceTuple : [CompileState, Value] = this/* : Main */.parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state/* : CompileState */, valueString/* : unknown */, depth/* : number */)/* : [CompileState, Value] */;
			let sourceState = sourceTuple/* : [CompileState, Value] */[0/* : number */];
			let source = sourceTuple/* : [CompileState, Value] */[1/* : number */];
			let destinationTuple : [CompileState, Value] = this/* : Main */.parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(sourceState/* : unknown */, beforeEquals/* : unknown */, depth/* : number */)/* : [CompileState, Value] */;
			let destinationState = destinationTuple/* : [CompileState, Value] */[0/* : number */];
			let destination = destinationTuple/* : [CompileState, Value] */[1/* : number */];
			return this/* : Main */.parseDefinition/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Definition]> */(destinationState/* : unknown */, beforeEquals/* : unknown */)/* : Option<[CompileState, Definition]> */.flatMap/* : (arg0 : (arg0 : [CompileState, Definition]) => Option<R>) => Option<R> */((definitionTuple : [CompileState, Definition]) => this/* : Main */.parseInitialization/* : (arg0 : CompileState, arg1 : Definition, arg2 : Value) => Option<[CompileState, StatementValue]> */(definitionTuple/* : [CompileState, Definition] */[0/* : number */], definitionTuple/* : [CompileState, Definition] */[1/* : number */], source/* : unknown */)/* : Option<[CompileState, StatementValue]> */)/* : Option<R> */.or/* : (arg0 : () => Option<T>) => Option<T> */(() => new Some([destinationState/* : unknown */, new Assignment(destination/* : unknown */, source/* : unknown */)/* : Assignment */])/* : Some */)/* : Option<T> */;
		})/* : Option<T> */;
	}
	private parseInitialization(state : CompileState, rawDefinition : Definition, source : Value) : Option<[CompileState, StatementValue]> {
		let definition : Definition = rawDefinition/* : Definition */.mapType/* : (arg0 : (arg0 : Type) => Type) => Definition */((type : Type) => {
			if (type/* : Type */.equals/* : unknown */(Primitive/* : Primitive */.Unknown/* : unknown */)/* : unknown */){
				return source/* : Value */.type/* : () => Type */()/* : Type */;
			}
			else {
				return type/* : Type */;
			}
		})/* : Definition */;
		return new Some([state/* : CompileState */.define/* : (arg0 : Definition) => CompileState */(definition/* : Definition */)/* : CompileState */, new Initialization(definition/* : Definition */, source/* : Value */)/* : Initialization */])/* : Some */;
	}
	private parseValue(state : CompileState, input : string, depth : number) : [CompileState, Value] {
		return this/* : Main */.parseBoolean/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Value]> */(state/* : CompileState */, input/* : string */)/* : Option<[CompileState, Value]> */.or/* : (arg0 : () => Option<T>) => Option<T> */(() => this/* : Main */.parseLambda/* : (arg0 : CompileState, arg1 : string, arg2 : number) => Option<[CompileState, Value]> */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : Option<[CompileState, Value]> */)/* : Option<T> */.or/* : (arg0 : () => Option<T>) => Option<T> */(() => this/* : Main */.parseString/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Value]> */(state/* : CompileState */, input/* : string */)/* : Option<[CompileState, Value]> */)/* : Option<T> */.or/* : (arg0 : () => Option<T>) => Option<T> */(() => this/* : Main */.parseDataAccess/* : (arg0 : CompileState, arg1 : string, arg2 : number) => Option<[CompileState, Value]> */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : Option<[CompileState, Value]> */)/* : Option<T> */.or/* : (arg0 : () => Option<T>) => Option<T> */(() => this/* : Main */.parseSymbolValue/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Value]> */(state/* : CompileState */, input/* : string */)/* : Option<[CompileState, Value]> */)/* : Option<T> */.or/* : (arg0 : () => Option<T>) => Option<T> */(() => this/* : Main */.parseInvokable/* : (arg0 : CompileState, arg1 : string, arg2 : number) => Option<[CompileState, Value]> */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : Option<[CompileState, Value]> */)/* : Option<T> */.or/* : (arg0 : () => Option<T>) => Option<T> */(() => this/* : Main */.parseDigits/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Value]> */(state/* : CompileState */, input/* : string */)/* : Option<[CompileState, Value]> */)/* : Option<T> */.or/* : (arg0 : () => Option<T>) => Option<T> */(() => this/* : Main */.parseInstanceOf/* : (arg0 : CompileState, arg1 : string, arg2 : number) => Option<[CompileState, Value]> */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : Option<[CompileState, Value]> */)/* : Option<T> */.or/* : (arg0 : () => Option<T>) => Option<T> */(() => this/* : Main */.parseOperation/* : (arg0 : CompileState, arg1 : string, arg2 : number, arg3 : Operator) => Option<[CompileState, Value]> */(state/* : CompileState */, input/* : string */, depth/* : number */, Operator/* : () => content-start new content-end */.ADD/* : unknown */)/* : Option<[CompileState, Value]> */)/* : Option<T> */.or/* : (arg0 : () => Option<T>) => Option<T> */(() => this/* : Main */.parseOperation/* : (arg0 : CompileState, arg1 : string, arg2 : number, arg3 : Operator) => Option<[CompileState, Value]> */(state/* : CompileState */, input/* : string */, depth/* : number */, Operator/* : () => content-start new content-end */.EQUALS/* : unknown */)/* : Option<[CompileState, Value]> */)/* : Option<T> */.or/* : (arg0 : () => Option<T>) => Option<T> */(() => this/* : Main */.parseOperation/* : (arg0 : CompileState, arg1 : string, arg2 : number, arg3 : Operator) => Option<[CompileState, Value]> */(state/* : CompileState */, input/* : string */, depth/* : number */, Operator/* : () => content-start new content-end */.SUBTRACT/* : unknown */)/* : Option<[CompileState, Value]> */)/* : Option<T> */.or/* : (arg0 : () => Option<T>) => Option<T> */(() => this/* : Main */.parseOperation/* : (arg0 : CompileState, arg1 : string, arg2 : number, arg3 : Operator) => Option<[CompileState, Value]> */(state/* : CompileState */, input/* : string */, depth/* : number */, Operator/* : () => content-start new content-end */.AND/* : unknown */)/* : Option<[CompileState, Value]> */)/* : Option<T> */.or/* : (arg0 : () => Option<T>) => Option<T> */(() => this/* : Main */.parseOperation/* : (arg0 : CompileState, arg1 : string, arg2 : number, arg3 : Operator) => Option<[CompileState, Value]> */(state/* : CompileState */, input/* : string */, depth/* : number */, Operator/* : () => content-start new content-end */.OR/* : unknown */)/* : Option<[CompileState, Value]> */)/* : Option<T> */.or/* : (arg0 : () => Option<T>) => Option<T> */(() => this/* : Main */.parseOperation/* : (arg0 : CompileState, arg1 : string, arg2 : number, arg3 : Operator) => Option<[CompileState, Value]> */(state/* : CompileState */, input/* : string */, depth/* : number */, /*  Operator.GREATER_THAN_OR_EQUALS */)/* : Option<[CompileState, Value]> */)/* : Option<T> */.or/* : (arg0 : () => Option<T>) => Option<T> */(() => this/* : Main */.parseOperation/* : (arg0 : CompileState, arg1 : string, arg2 : number, arg3 : Operator) => Option<[CompileState, Value]> */(state/* : CompileState */, input/* : string */, depth/* : number */, /*  Operator.LESS_THAN */)/* : Option<[CompileState, Value]> */)/* : Option<T> */.or/* : (arg0 : () => Option<T>) => Option<T> */(() => this/* : Main */.parseNot/* : (arg0 : CompileState, arg1 : string, arg2 : number) => Option<[CompileState, Value]> */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : Option<[CompileState, Value]> */)/* : Option<T> */.or/* : (arg0 : () => Option<T>) => Option<T> */(() => this/* : Main */.parseMethodReference/* : (arg0 : CompileState, arg1 : string, arg2 : number) => Option<[CompileState, Value]> */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : Option<[CompileState, Value]> */)/* : Option<T> */.orElseGet/* : (arg0 : () => T) => T */(() => [state/* : CompileState */, new Placeholder(input/* : string */)/* : Placeholder */])/* : T */;
	}
	private parseBoolean(state : CompileState, input : string) : Option<[CompileState, Value]> {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.equals/* : unknown */("false")/* : unknown */){
			return new Some([state/* : CompileState */, BooleanValue/* : BooleanValue */.False/* : unknown */])/* : Some */;
		}
		if (stripped/* : unknown */.equals/* : unknown */("true")/* : unknown */){
			return new Some([state/* : CompileState */, BooleanValue/* : BooleanValue */.True/* : unknown */])/* : Some */;
		}
		return new None()/* : None */;
	}
	private parseInstanceOf(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return this/* : Main */.last/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(input/* : string */, "instanceof", (s, s2) => {
			let childTuple : [CompileState, Value] = this/* : Main */.parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state/* : CompileState */, s/* : unknown */, depth/* : number */)/* : [CompileState, Value] */;
			return this/* : Main */.parseDefinition/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Definition]> */(childTuple/* : [CompileState, Value] */[0/* : number */], s2/* : unknown */)/* : Option<[CompileState, Definition]> */.map/* : (arg0 : (arg0 : [CompileState, Definition]) => R) => Option<R> */((definitionTuple : [CompileState, Definition]) => {
				let value = childTuple/* : [CompileState, Value] */[1/* : number */];
				let definition = definitionTuple/* : [CompileState, Definition] */[1/* : number */];
				let type = value/* : unknown */.type/* : unknown */()/* : unknown */;
				let variant : DataAccess = new DataAccess(value/* : unknown */, "_" + type.findName().orElse("") + "Variant", Primitive/* : Primitive */.Unknown/* : unknown */)/* : DataAccess */;
				let generate = type/* : unknown */.findName/* : unknown */()/* : unknown */.orElse/* : unknown */("")/* : unknown */;
				let temp : SymbolValue = new SymbolValue(generate/* : unknown */ + "Variant." + definition/* : unknown */.findType/* : unknown */()/* : unknown */.findName/* : unknown */()/* : unknown */.orElse/* : unknown */("")/* : unknown */, Primitive/* : Primitive */.Unknown/* : unknown */)/* : SymbolValue */;
				let functionSegment : Statement = new Statement(depth/* : number */ + 1/* : number */, new Initialization(definition/* : unknown */, new Cast(value/* : unknown */, definition/* : unknown */.findType/* : unknown */()/* : unknown */)/* : Cast */)/* : Initialization */)/* : Statement */;
				return [definitionTuple/* : [CompileState, Definition] */[0/* : number */].addFunctionSegment/* : unknown */(functionSegment/* : Statement */)/* : unknown */.define/* : unknown */(definition/* : unknown */)/* : unknown */, new Operation(variant/* : DataAccess */, Operator/* : () => content-start new content-end */.EQUALS/* : unknown */, temp/* : SymbolValue */)/* : Operation */];
			})/* : Option<R> */;
		})/* : Option<T> */;
	}
	private parseMethodReference(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return this/* : Main */.last/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(input/* : string */, "::", (s, s2) => {
			let tuple : [CompileState, Value] = this/* : Main */.parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state/* : CompileState */, s/* : unknown */, depth/* : number */)/* : [CompileState, Value] */;
			return new Some([tuple/* : [CompileState, Value] */[0/* : number */], new DataAccess(tuple/* : [CompileState, Value] */[1/* : number */], s2/* : unknown */, Primitive/* : Primitive */.Unknown/* : unknown */)/* : DataAccess */])/* : Some */;
		})/* : Option<T> */;
	}
	private parseNot(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.startsWith/* : unknown */("!")/* : unknown */){
			let slice = stripped/* : unknown */.substring/* : unknown */(1/* : number */)/* : unknown */;
			let tuple : [CompileState, Value] = this/* : Main */.parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state/* : CompileState */, slice/* : unknown */, depth/* : number */)/* : [CompileState, Value] */;
			let value = tuple/* : [CompileState, Value] */[1/* : number */];
			return new Some([tuple/* : [CompileState, Value] */[0/* : number */], new Not(value/* : unknown */)/* : Not */])/* : Some */;
		}
		return new None()/* : None */;
	}
	private parseLambda(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return this/* : Main */.first/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(input/* : string */, "->", (beforeArrow, valueString) => {
			let strippedBeforeArrow = beforeArrow/* : unknown */.strip/* : unknown */()/* : unknown */;
			if (this/* : Main */.isSymbol/* : (arg0 : string) => boolean */(strippedBeforeArrow/* : unknown */)/* : boolean */){
				let type : Type = Primitive/* : Primitive */.Unknown/* : unknown */;
				if (/* state.typeRegister instanceof Some */(/* var expectedType */)/* : unknown */){
					if (/* expectedType */._Variant/* : unknown */ === Variant.FunctionType/* : unknown */){
						let functionType : FunctionType = /* expectedType */ as FunctionType;
						type/* : Type */ = functionType/* : FunctionType */.arguments/* : List<Type> */.get/* : (arg0 : number) => Option<T> */(0/* : number */)/* : Option<T> */.orElse/* : (arg0 : T) => T */(/* null */)/* : T */;
					}
				}
				return this/* : Main */.assembleLambda/* : (arg0 : CompileState, arg1 : List<Definition>, arg2 : string, arg3 : number) => Some<[CompileState, Value]> */(state/* : CompileState */, Lists/* : Lists */.of/* : (arg0 : T[]) => List<T> */(ImmutableDefinition/* : ImmutableDefinition */.createSimpleDefinition/* : (arg0 : string, arg1 : Type) => Definition */(strippedBeforeArrow/* : unknown */, type/* : Type */)/* : Definition */)/* : List<T> */, valueString/* : unknown */, depth/* : number */)/* : Some<[CompileState, Value]> */;
			}
			if (strippedBeforeArrow/* : unknown */.startsWith/* : unknown */("(")/* : unknown */ && strippedBeforeArrow/* : unknown */.endsWith/* : unknown */(")")/* : unknown */){
				let parameterNames : R = this/* : Main */.divideAll/* : (arg0 : string, arg1 : (arg0 : DivideState, arg1 : string) => DivideState) => List<string> */(strippedBeforeArrow/* : unknown */.substring/* : unknown */(1/* : number */, strippedBeforeArrow/* : unknown */.length/* : unknown */()/* : unknown */ - 1/* : number */)/* : unknown */, this/* : Main */.foldValueChar/* : unknown */)/* : List<string> */.query/* : () => Query<T> */()/* : Query<T> */.map/* : (arg0 : (arg0 : T) => R) => Query<R> */(/* String */.strip/* : unknown */)/* : Query<R> */.filter/* : (arg0 : (arg0 : T) => boolean) => Option<T> */((value : T) => !value/* : T */.isEmpty/* : unknown */()/* : unknown */)/* : Option<T> */.map/* : (arg0 : (arg0 : T) => R) => Option<R> */((name : T) => ImmutableDefinition/* : ImmutableDefinition */.createSimpleDefinition/* : (arg0 : string, arg1 : Type) => Definition */(name/* : T */, Primitive/* : Primitive */.Unknown/* : unknown */)/* : Definition */)/* : Option<R> */.collect/* : (arg0 : Collector<T, R>) => R */(new ListCollector()/* : ListCollector */)/* : R */;
				return this/* : Main */.assembleLambda/* : (arg0 : CompileState, arg1 : List<Definition>, arg2 : string, arg3 : number) => Some<[CompileState, Value]> */(state/* : CompileState */, parameterNames/* : R */, valueString/* : unknown */, depth/* : number */)/* : Some<[CompileState, Value]> */;
			}
			return new None()/* : None */;
		})/* : Option<T> */;
	}
	private assembleLambda(state : CompileState, definitions : List<Definition>, valueString : string, depth : number) : Some<[CompileState, Value]> {
		let strippedValueString = valueString/* : string */.strip/* : unknown */()/* : unknown */;
		/* Tuple2Impl<CompileState, LambdaValue> value */;
		let state2 : CompileState = state/* : CompileState */.defineAll/* : (arg0 : List<Definition>) => CompileState */(definitions/* : List<Definition> */)/* : CompileState */;
		if (strippedValueString/* : unknown */.startsWith/* : unknown */("{")/* : unknown */ && strippedValueString/* : unknown */.endsWith/* : unknown */("}")/* : unknown */){
			let value1 : [CompileState, List<T>] = this/* : Main */.parseStatements/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => [CompileState, T]) => [CompileState, List<T>] */(state2/* : CompileState */, strippedValueString/* : unknown */.substring/* : unknown */(1/* : number */, strippedValueString/* : unknown */.length/* : unknown */()/* : unknown */ - 1/* : number */)/* : unknown */, (state1, input1) => this/* : Main */.parseFunctionSegment/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, FunctionSegment] */(state1/* : unknown */, input1/* : unknown */, depth/* : number */ + 1/* : number */)/* : [CompileState, FunctionSegment] */)/* : [CompileState, List<T>] */;
			let right = value1/* : [CompileState, List<T>] */[1/* : number */];
			/* value */ = [value1/* : [CompileState, List<T>] */[0/* : number */], new BlockLambdaValue(depth/* : number */, right/* : unknown */)/* : BlockLambdaValue */];
		}
		else {
			let value1 : [CompileState, Value] = this/* : Main */.parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state2/* : CompileState */, strippedValueString/* : unknown */, depth/* : number */)/* : [CompileState, Value] */;
			/* value */ = [value1/* : [CompileState, List<T>] */[0/* : number */], value1/* : [CompileState, List<T>] */[1/* : number */]];
		}
		let right = /* value */.right/* : unknown */()/* : unknown */;
		return new Some([/* value */.left/* : unknown */()/* : unknown */, new Lambda(definitions/* : List<Definition> */, right/* : unknown */)/* : Lambda */])/* : Some */;
	}
	private parseDigits(state : CompileState, input : string) : Option<[CompileState, Value]> {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (this/* : Main */.isNumber/* : (arg0 : string) => boolean */(stripped/* : unknown */)/* : boolean */){
			return new Some([state/* : CompileState */, new SymbolValue(stripped/* : unknown */, Primitive/* : Primitive */.Int/* : unknown */)/* : SymbolValue */])/* : Some */;
		}
		return new None()/* : None */;
	}
	private isNumber(input : string) : boolean {
		/* String maybeTruncated */;
		if (input/* : string */.startsWith/* : unknown */("-")/* : unknown */){
			/* maybeTruncated */ = input/* : string */.substring/* : unknown */(1/* : number */)/* : unknown */;
		}
		else {
			/* maybeTruncated */ = input/* : string */;
		}
		return this/* : Main */.areAllDigits/* : (arg0 : string) => boolean */(/* maybeTruncated */)/* : boolean */;
	}
	private areAllDigits(input : string) : boolean {
		/* for (var i = 0; i < input.length(); i++) */{
			let c = input/* : string */.charAt/* : unknown */(/* i */)/* : unknown */;
			if (/* Character */.isDigit/* : unknown */(c/* : unknown */)/* : unknown */){
				/* continue */;
			}
			return false;
		}
		return true;
	}
	private parseInvokable(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return this/* : Main */.suffix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(input/* : string */.strip/* : unknown */()/* : unknown */, ")", (withoutEnd : string) => {
			return this/* : Main */.split/* : (arg0 : () => Option<[string, string]>, arg1 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(() => this/* : Main */.toLast/* : (arg0 : string, arg1 : string, arg2 : (arg0 : DivideState, arg1 : string) => DivideState) => Option<[string, string]> */(withoutEnd/* : string */, "", this/* : Main */.foldInvocationStart/* : unknown */)/* : Option<[string, string]> */, (callerWithEnd, argumentsString) => {
				return this/* : Main */.suffix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(callerWithEnd/* : unknown */, "(", (callerString : string) => {
					return this/* : Main */.assembleInvokable/* : (arg0 : CompileState, arg1 : number, arg2 : string, arg3 : string) => Some<[CompileState, Value]> */(state/* : CompileState */, depth/* : number */, argumentsString/* : unknown */, callerString/* : string */.strip/* : unknown */()/* : unknown */)/* : Some<[CompileState, Value]> */;
				})/* : Option<T> */;
			})/* : Option<T> */;
		})/* : Option<T> */;
	}
	private assembleInvokable(state : CompileState, depth : number, argumentsString : string, callerString : string) : Some<[CompileState, Value]> {
		let callerTuple : [CompileState, Caller] = this/* : Main */.invocationHeader/* : (arg0 : CompileState, arg1 : number, arg2 : string) => [CompileState, Caller] */(state/* : CompileState */, depth/* : number */, callerString/* : string */)/* : [CompileState, Caller] */;
		let oldCallerState = callerTuple/* : [CompileState, Caller] */[0/* : number */];
		let oldCaller = callerTuple/* : [CompileState, Caller] */[1/* : number */];
		let newCaller : Caller = this/* : Main */.modifyCaller/* : (arg0 : CompileState, arg1 : Caller) => Caller */(oldCallerState/* : unknown */, oldCaller/* : unknown */)/* : Caller */;
		let callerType : FunctionType = this/* : Main */.findCallerType/* : (arg0 : Caller) => FunctionType */(newCaller/* : Caller */)/* : FunctionType */;
		let argumentsTuple : T = this/* : Main */.parseValuesWithIndices/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]>) => Option<[CompileState, List<T>]> */(oldCallerState/* : unknown */, argumentsString/* : string */, (currentState, pair) => {
			let index = pair/* : unknown */.left/* : unknown */()/* : unknown */;
			let element = pair/* : unknown */.right/* : unknown */()/* : unknown */;
			let expectedType : T = callerType/* : FunctionType */.arguments/* : List<Type> */.get/* : (arg0 : number) => Option<T> */(index/* : unknown */)/* : Option<T> */.orElse/* : (arg0 : T) => T */(Primitive/* : Primitive */.Unknown/* : unknown */)/* : T */;
			let withExpected = currentState/* : unknown */.withExpectedType/* : unknown */(expectedType/* : T */)/* : unknown */;
			let valueTuple : [CompileState, Argument] = this/* : Main */.parseArgument/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Argument] */(withExpected/* : unknown */, element/* : unknown */, depth/* : number */)/* : [CompileState, Argument] */;
			let valueState = valueTuple/* : [CompileState, Argument] */[0/* : number */];
			let value = valueTuple/* : [CompileState, Argument] */[1/* : number */];
			let actualType = valueTuple/* : [CompileState, Argument] */[0/* : number */].typeRegister/* : unknown */.orElse/* : unknown */(Primitive/* : Primitive */.Unknown/* : unknown */)/* : unknown */;
			return new Some([valueState/* : unknown */, [value/* : unknown */, actualType/* : unknown */]])/* : Some */;
		})/* : Option<[CompileState, List<T>]> */.orElseGet/* : (arg0 : () => T) => T */(() => [oldCallerState/* : unknown */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */])/* : T */;
		let argumentsState = argumentsTuple/* : T */.left/* : unknown */()/* : unknown */;
		let argumentsWithActualTypes = argumentsTuple/* : T */.right/* : unknown */()/* : unknown */;
		let arguments = argumentsWithActualTypes/* : unknown */.query/* : unknown */()/* : unknown */.map/* : unknown */(Tuple2/* : Tuple2 */.left/* : unknown */)/* : unknown */.map/* : unknown */(this/* : Main */.retainValue/* : unknown */)/* : unknown */.flatMap/* : unknown */(Queries/* : Queries */.fromOption/* : unknown */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
		if (newCaller/* : Caller */._CallerVariant/* : unknown */ === CallerVariant.ConstructionCaller/* : unknown */){
			if (constructionCaller/* : ConstructionCaller */.type/* : Type */.findName/* : unknown */()/* : unknown */.filter/* : unknown */((value) => value/* : unknown */.equals/* : unknown */("Tuple2Impl")/* : unknown */)/* : unknown */.isPresent/* : unknown */()/* : unknown */){
			let constructionCaller : ConstructionCaller = newCaller/* : Caller */ as ConstructionCaller;
				return new Some([argumentsState/* : unknown */, new TupleNode(Lists/* : Lists */.of/* : (arg0 : T[]) => List<T> */(arguments/* : unknown */.get/* : unknown */(0/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */, arguments/* : unknown */.get/* : unknown */(1/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : List<T> */)/* : TupleNode */])/* : Some */;
			}
		}
		if (newCaller/* : Caller */._CallerVariant/* : unknown */ === CallerVariant.Value/* : unknown */){
			if (value/* : unknown */._Variant/* : unknown */ === Variant.DataAccess/* : unknown */){
				let parent : Value = access/* : DataAccess */.parent/* : Value */;
				let property : string = access/* : DataAccess */.property/* : string */;
				let parentType : Type = parent/* : Value */.type/* : () => Type */()/* : Type */;
				if (/* parentType instanceof TupleType */){
					if (property/* : string */.equals/* : unknown */("left")/* : unknown */){
			let value : Value = newCaller/* : Caller */ as Value;
				let access : DataAccess = value/* : unknown */ as DataAccess;
						return new Some([state/* : CompileState */, new IndexValue(parent/* : Value */, new SymbolValue("0", Primitive/* : Primitive */.Int/* : unknown */)/* : SymbolValue */)/* : IndexValue */])/* : Some */;
					}
					if (property/* : string */.equals/* : unknown */("right")/* : unknown */){
						return new Some([state/* : CompileState */, new IndexValue(parent/* : Value */, new SymbolValue("1", Primitive/* : Primitive */.Int/* : unknown */)/* : SymbolValue */)/* : IndexValue */])/* : Some */;
					}
				}
			}
		}
		let invokable : Invokable = new Invokable(newCaller/* : Caller */, arguments/* : unknown */, callerType/* : FunctionType */.returns/* : Type */)/* : Invokable */;
		return new Some([argumentsState/* : unknown */, invokable/* : Invokable */])/* : Some */;
	}
	private retainValue(argument : Argument) : Option<Value> {
		if (argument/* : Argument */._ArgumentVariant/* : unknown */ === ArgumentVariant.Value/* : unknown */){
			let value : Value = argument/* : Argument */ as Value;
			return new Some(value/* : Value */)/* : Some */;
		}
		return new None()/* : None */;
	}
	private parseArgument(state : CompileState, element : string, depth : number) : [CompileState, Argument] {
		if (element/* : string */.isEmpty/* : unknown */()/* : unknown */){
			return [state/* : CompileState */, new Whitespace()/* : Whitespace */];
		}
		let tuple : [CompileState, Value] = this/* : Main */.parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state/* : CompileState */, element/* : string */, depth/* : number */)/* : [CompileState, Value] */;
		return [tuple/* : [CompileState, Value] */[0/* : number */], tuple/* : [CompileState, Value] */[1/* : number */]];
	}
	private findCallerType(newCaller : Caller) : FunctionType {
		let callerType : FunctionType = new FunctionType(Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, Primitive/* : Primitive */.Unknown/* : unknown */)/* : FunctionType */;
		/* switch (newCaller) */{
			/* case ConstructionCaller constructionCaller -> */{
				callerType/* : FunctionType */ = /* constructionCaller */.toFunction/* : unknown */()/* : unknown */;
			}
			/* case Value value -> */{
				let type = /* value */.type/* : unknown */()/* : unknown */;
				if (type/* : unknown */._Variant/* : unknown */ === Variant.FunctionType/* : unknown */){
					let functionType : FunctionType = type/* : unknown */ as FunctionType;
					callerType/* : FunctionType */ = functionType/* : FunctionType */;
				}
			}
		}
		return callerType/* : FunctionType */;
	}
	private modifyCaller(state : CompileState, oldCaller : Caller) : Caller {
		if (oldCaller/* : Caller */._CallerVariant/* : unknown */ === CallerVariant.DataAccess/* : unknown */){
			let type : Type = this/* : Main */.resolveType/* : (arg0 : Value, arg1 : CompileState) => Type */(access/* : DataAccess */.parent/* : Value */, state/* : CompileState */)/* : Type */;
			if (/* type instanceof FunctionType */){
			let access : DataAccess = oldCaller/* : Caller */ as DataAccess;
				return access/* : DataAccess */.parent/* : Value */;
			}
		}
		return oldCaller/* : Caller */;
	}
	private resolveType(value : Value, state : CompileState) : Type {
		return value/* : Value */.type/* : () => Type */()/* : Type */;
	}
	private invocationHeader(state : CompileState, depth : number, callerString1 : string) : [CompileState, Caller] {
		if (callerString1/* : string */.startsWith/* : unknown */("new ")/* : unknown */){
			let input1 : string = callerString1/* : string */.substring/* : unknown */("new ".length/* : unknown */()/* : unknown */)/* : unknown */;
			let map : Option<R> = this/* : Main */.parseType/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Type]> */(state/* : CompileState */, input1/* : string */)/* : Option<[CompileState, Type]> */.map/* : (arg0 : (arg0 : [CompileState, Type]) => R) => Option<R> */((type : [CompileState, Type]) => {
				let right = type/* : [CompileState, Type] */[1/* : number */];
				return [type/* : [CompileState, Type] */[0/* : number */], new ConstructionCaller(right/* : unknown */)/* : ConstructionCaller */];
			})/* : Option<R> */;
			if (map/* : Option<R> */.isPresent/* : () => boolean */()/* : boolean */){
				return map/* : Option<R> */.orElse/* : (arg0 : T) => T */(/* null */)/* : T */;
			}
		}
		let tuple : [CompileState, Value] = this/* : Main */.parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state/* : CompileState */, callerString1/* : string */, depth/* : number */)/* : [CompileState, Value] */;
		return [tuple/* : [CompileState, Value] */[0/* : number */], tuple/* : [CompileState, Value] */[1/* : number */]];
	}
	private foldInvocationStart(state : DivideState, c : string) : DivideState {
		let appended : DivideState = state/* : DivideState */.append/* : (arg0 : string) => DivideState */(c/* : string */)/* : DivideState */;
		if (c/* : string */ === /*  '(' */){
			let enter : DivideState = appended/* : DivideState */.enter/* : () => DivideState */()/* : DivideState */;
			if (enter/* : DivideState */.isShallow/* : () => boolean */()/* : boolean */){
				return enter/* : DivideState */.advance/* : () => DivideState */()/* : DivideState */;
			}
			return enter/* : DivideState */;
		}
		if (c/* : string */ === /*  ')' */){
			return appended/* : DivideState */.exit/* : () => DivideState */()/* : DivideState */;
		}
		return appended/* : DivideState */;
	}
	private parseDataAccess(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return this/* : Main */.last/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(input/* : string */.strip/* : unknown */()/* : unknown */, ".", (parentString, rawProperty) => {
			let property = rawProperty/* : unknown */.strip/* : unknown */()/* : unknown */;
			if (!this/* : Main */.isSymbol/* : unknown */(property/* : unknown */)/* : unknown */){
				return new None()/* : None */;
			}
			let tuple : [CompileState, Value] = this/* : Main */.parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state/* : CompileState */, parentString/* : unknown */, depth/* : number */)/* : [CompileState, Value] */;
			let parent = tuple/* : [CompileState, Value] */[1/* : number */];
			let parentType = parent/* : unknown */.type/* : unknown */()/* : unknown */;
			let type : Type = Primitive/* : Primitive */.Unknown/* : unknown */;
			if (parentType/* : unknown */._Variant/* : unknown */ === Variant.FindableType/* : unknown */){
				if (/* objectType.find(property) instanceof Some */(/* var memberType */)/* : unknown */){
				let objectType : FindableType = parentType/* : unknown */ as FindableType;
					type/* : Type */ = /* memberType */;
				}
			}
			return new Some([tuple/* : [CompileState, Value] */[0/* : number */], new DataAccess(parent/* : unknown */, property/* : unknown */, type/* : Type */)/* : DataAccess */])/* : Some */;
		})/* : Option<T> */;
	}
	private parseString(state : CompileState, input : string) : Option<[CompileState, Value]> {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.startsWith/* : unknown */("\"")/* : unknown */ && stripped/* : unknown */.endsWith/* : unknown */("\"")/* : unknown */){
			return new Some([state/* : CompileState */, new StringValue(stripped/* : unknown */)/* : StringValue */])/* : Some */;
		}
		return new None()/* : None */;
	}
	private parseSymbolValue(state : CompileState, value : string) : Option<[CompileState, Value]> {
		let stripped = value/* : string */.strip/* : unknown */()/* : unknown */;
		if (this/* : Main */.isSymbol/* : (arg0 : string) => boolean */(stripped/* : unknown */)/* : boolean */){
			if (/* state.resolveValue(stripped) instanceof Some */(/* var type */)/* : unknown */){
				return new Some([state/* : CompileState */, new SymbolValue(stripped/* : unknown */, type/* : () => Type */)/* : SymbolValue */])/* : Some */;
			}
			if (/* state.resolveType(stripped) instanceof Some */(/* var type */)/* : unknown */){
				return new Some([state/* : CompileState */, new SymbolValue(stripped/* : unknown */, type/* : () => Type */)/* : SymbolValue */])/* : Some */;
			}
			return new Some([state/* : CompileState */, new Placeholder(stripped/* : unknown */)/* : Placeholder */])/* : Some */;
		}
		return new None()/* : None */;
	}
	private parseOperation(state : CompileState, value : string, depth : number, operator : Operator) : Option<[CompileState, Value]> {
		return this/* : Main */.first/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(value/* : string */, operator/* : Operator */.sourceRepresentation/* : string */, (leftString, rightString) => {
			let leftTuple : [CompileState, Value] = this/* : Main */.parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(state/* : CompileState */, leftString/* : unknown */, depth/* : number */)/* : [CompileState, Value] */;
			let rightTuple : [CompileState, Value] = this/* : Main */.parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(leftTuple/* : [CompileState, Value] */[0/* : number */], rightString/* : unknown */, depth/* : number */)/* : [CompileState, Value] */;
			let left = leftTuple/* : [CompileState, Value] */[1/* : number */];
			let right = rightTuple/* : [CompileState, Value] */[1/* : number */];
			return new Some([rightTuple/* : [CompileState, Value] */[0/* : number */], new Operation(left/* : unknown */, operator/* : Operator */, right/* : unknown */)/* : Operation */])/* : Some */;
		})/* : Option<T> */;
	}
	private parseValuesOrEmpty<T>(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) : [CompileState, List<T>] {
		return this/* : Main */.parseValues/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) => Option<[CompileState, List<T>]> */(state/* : CompileState */, input/* : string */, mapper/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]> */)/* : Option<[CompileState, List<T>]> */.orElseGet/* : (arg0 : () => T) => T */(() => [state/* : CompileState */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */])/* : T */;
	}
	private parseValues<T>(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) : Option<[CompileState, List<T>]> {
		return this/* : Main */.parseValuesWithIndices/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]>) => Option<[CompileState, List<T>]> */(state/* : CompileState */, input/* : string */, (state1, tuple) => mapper/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]> */(state1/* : unknown */, tuple/* : unknown */.right/* : unknown */()/* : unknown */)/* : Option<[CompileState, T]> */)/* : Option<[CompileState, List<T>]> */;
	}
	private parseValuesWithIndices<T>(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]>) : Option<[CompileState, List<T>]> {
		return this/* : Main */.parseAllWithIndices/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : DivideState, arg1 : string) => DivideState, arg3 : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]>) => Option<[CompileState, List<T>]> */(state/* : CompileState */, input/* : string */, this/* : Main */.foldValueChar/* : unknown */, mapper/* : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]> */)/* : Option<[CompileState, List<T>]> */;
	}
	private parseParameter(state : CompileState, input : string) : [CompileState, Parameter] {
		if (input/* : string */.isBlank/* : unknown */()/* : unknown */){
			return [state/* : CompileState */, new Whitespace()/* : Whitespace */];
		}
		return this/* : Main */.parseDefinition/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Definition]> */(state/* : CompileState */, input/* : string */)/* : Option<[CompileState, Definition]> */.map/* : (arg0 : (arg0 : [CompileState, Definition]) => R) => Option<R> */((tuple : [CompileState, Definition]) => [tuple/* : [CompileState, Definition] */[0/* : number */], tuple/* : [CompileState, Definition] */[1/* : number */]])/* : Option<R> */.orElseGet/* : (arg0 : () => T) => T */(() => [state/* : CompileState */, new Placeholder(input/* : string */)/* : Placeholder */])/* : T */;
	}
	private parseField(input : string, depth : number, state : CompileState) : Option<[CompileState, IncompleteClassSegment]> {
		return this/* : Main */.suffix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(input/* : string */.strip/* : unknown */()/* : unknown */, ";", (withoutEnd : string) => {
			return this/* : Main */.parseClassInitialization/* : (arg0 : number, arg1 : CompileState, arg2 : string) => Option<[CompileState, IncompleteClassSegment]> */(depth/* : number */, state/* : CompileState */, withoutEnd/* : string */)/* : Option<[CompileState, IncompleteClassSegment]> */.or/* : (arg0 : () => Option<T>) => Option<T> */(() => {
				return this/* : Main */.parseClassDefinition/* : (arg0 : number, arg1 : CompileState, arg2 : string) => Option<[CompileState, IncompleteClassSegment]> */(depth/* : number */, state/* : CompileState */, withoutEnd/* : string */)/* : Option<[CompileState, IncompleteClassSegment]> */;
			})/* : Option<T> */;
		})/* : Option<T> */;
	}
	private parseClassDefinition(depth : number, state : CompileState, withoutEnd : string) : Option<[CompileState, IncompleteClassSegment]> {
		return this/* : Main */.parseDefinition/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Definition]> */(state/* : CompileState */, withoutEnd/* : string */)/* : Option<[CompileState, Definition]> */.map/* : (arg0 : (arg0 : [CompileState, Definition]) => R) => Option<R> */((result : [CompileState, Definition]) => {
			return [result/* : [CompileState, Definition] */[0/* : number */], new ClassDefinition(depth/* : number */, result/* : [CompileState, Definition] */[1/* : number */])/* : ClassDefinition */];
		})/* : Option<R> */;
	}
	private parseClassInitialization(depth : number, state : CompileState, withoutEnd : string) : Option<[CompileState, IncompleteClassSegment]> {
		return this/* : Main */.first/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(withoutEnd/* : string */, "=", (s, s2) => {
			return this/* : Main */.parseDefinition/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Definition]> */(state/* : CompileState */, s/* : unknown */)/* : Option<[CompileState, Definition]> */.map/* : (arg0 : (arg0 : [CompileState, Definition]) => R) => Option<R> */((result : [CompileState, Definition]) => {
				let valueTuple : [CompileState, Value] = this/* : Main */.parseValue/* : (arg0 : CompileState, arg1 : string, arg2 : number) => [CompileState, Value] */(result/* : [CompileState, Definition] */[0/* : number */], s2/* : unknown */, depth/* : number */)/* : [CompileState, Value] */;
				return [valueTuple/* : [CompileState, Value] */[0/* : number */], new ClassInitialization(depth/* : number */, result/* : [CompileState, Definition] */[1/* : number */], valueTuple/* : [CompileState, Value] */[1/* : number */])/* : ClassInitialization */];
			})/* : Option<R> */;
		})/* : Option<T> */;
	}
	private parseDefinition(state : CompileState, input : string) : Option<[CompileState, Definition]> {
		return this/* : Main */.last/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(input/* : string */.strip/* : unknown */()/* : unknown */, " ", (beforeName, name) => {
			return this/* : Main */.split/* : (arg0 : () => Option<[string, string]>, arg1 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(() => this/* : Main */.toLast/* : (arg0 : string, arg1 : string, arg2 : (arg0 : DivideState, arg1 : string) => DivideState) => Option<[string, string]> */(beforeName/* : unknown */, " ", this/* : Main */.foldTypeSeparator/* : unknown */)/* : Option<[string, string]> */, (beforeType, type) => {
				return this/* : Main */.last/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(beforeType/* : unknown */, "\n", (s, s2) => {
					let annotations : List<string> = this/* : Main */.parseAnnotations/* : (arg0 : string) => List<string> */(s/* : unknown */)/* : List<string> */;
					return this/* : Main */.getOr/* : (arg0 : CompileState, arg1 : string, arg2 : string, arg3 : string, arg4 : List<string>) => Option<[CompileState, Definition]> */(state/* : CompileState */, name/* : unknown */, s2/* : unknown */, type/* : unknown */, annotations/* : List<string> */)/* : Option<[CompileState, Definition]> */;
				})/* : Option<T> */.or/* : (arg0 : () => Option<T>) => Option<T> */(() => {
					return this/* : Main */.getOr/* : (arg0 : CompileState, arg1 : string, arg2 : string, arg3 : string, arg4 : List<string>) => Option<[CompileState, Definition]> */(state/* : CompileState */, name/* : unknown */, beforeType/* : unknown */, type/* : unknown */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */)/* : Option<[CompileState, Definition]> */;
				})/* : Option<T> */;
			})/* : Option<T> */.or/* : (arg0 : () => Option<T>) => Option<T> */(() => this/* : Main */.assembleDefinition/* : (arg0 : CompileState, arg1 : List<string>, arg2 : List<string>, arg3 : string, arg4 : List<string>, arg5 : string) => Option<[CompileState, Definition]> */(state/* : CompileState */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, name/* : unknown */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, beforeName/* : unknown */)/* : Option<[CompileState, Definition]> */)/* : Option<T> */;
		})/* : Option<T> */;
	}
	private getOr(state : CompileState, name : string, beforeType : string, type : string, annotations : List<string>) : Option<[CompileState, Definition]> {
		return this/* : Main */.suffix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(beforeType/* : string */.strip/* : unknown */()/* : unknown */, ">", (withoutTypeParamStart : string) => {
			return this/* : Main */.first/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(withoutTypeParamStart/* : string */, "<", (beforeTypeParams, typeParamsString) => {
				let typeParams : [CompileState, List<T>] = this/* : Main */.parseValuesOrEmpty/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) => [CompileState, List<T>] */(state/* : CompileState */, typeParamsString/* : unknown */, (state1, s) => new Some([state1/* : unknown */, s/* : unknown */.strip/* : unknown */()/* : unknown */])/* : Some */)/* : [CompileState, List<T>] */;
				return this/* : Main */.assembleDefinition/* : (arg0 : CompileState, arg1 : List<string>, arg2 : List<string>, arg3 : string, arg4 : List<string>, arg5 : string) => Option<[CompileState, Definition]> */(typeParams/* : [CompileState, List<T>] */[0/* : number */], annotations/* : List<string> */, this/* : Main */.parseModifiers/* : (arg0 : string) => List<string> */(beforeTypeParams/* : unknown */)/* : List<string> */, name/* : string */, typeParams/* : [CompileState, List<T>] */[1/* : number */], type/* : string */)/* : Option<[CompileState, Definition]> */;
			})/* : Option<T> */;
		})/* : Option<T> */.or/* : (arg0 : () => Option<T>) => Option<T> */(() => {
			return this/* : Main */.assembleDefinition/* : (arg0 : CompileState, arg1 : List<string>, arg2 : List<string>, arg3 : string, arg4 : List<string>, arg5 : string) => Option<[CompileState, Definition]> */(state/* : CompileState */, annotations/* : List<string> */, this/* : Main */.parseModifiers/* : (arg0 : string) => List<string> */(beforeType/* : string */)/* : List<string> */, name/* : string */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, type/* : string */)/* : Option<[CompileState, Definition]> */;
		})/* : Option<T> */;
	}
	private parseModifiers(modifiers : string) : List<string> {
		return this/* : Main */.divideAll/* : (arg0 : string, arg1 : (arg0 : DivideState, arg1 : string) => DivideState) => List<string> */(modifiers/* : string */.strip/* : unknown */()/* : unknown */, (state1, c) => this/* : Main */.foldByDelimiter/* : (arg0 : DivideState, arg1 : string, arg2 : string) => DivideState */(state1/* : unknown */, c/* : unknown */, /*  '\n' */)/* : DivideState */)/* : List<string> */.query/* : () => Query<T> */()/* : Query<T> */.map/* : (arg0 : (arg0 : T) => R) => Query<R> */(/* String */.strip/* : unknown */)/* : Query<R> */.filter/* : (arg0 : (arg0 : T) => boolean) => Option<T> */((value : T) => !value/* : T */.isEmpty/* : unknown */()/* : unknown */)/* : Option<T> */.collect/* : (arg0 : Collector<T, R>) => R */(new ListCollector()/* : ListCollector */)/* : R */;
	}
	private toLast(input : string, separator : string, folder : (arg0 : DivideState, arg1 : string) => DivideState) : Option<[string, string]> {
		let divisions : List<string> = this/* : Main */.divideAll/* : (arg0 : string, arg1 : (arg0 : DivideState, arg1 : string) => DivideState) => List<string> */(input/* : string */, folder/* : (arg0 : DivideState, arg1 : string) => DivideState */)/* : List<string> */;
		return divisions/* : List<string> */.removeLast/* : () => Option<[List<T>, T]> */()/* : Option<[List<T>, T]> */.map/* : (arg0 : (arg0 : [List<T>, T]) => R) => Option<R> */((removed : [List<T>, T]) => {
			let left = removed/* : [List<T>, T] */[0/* : number */].query/* : unknown */()/* : unknown */.collect/* : unknown */(new Joiner(separator/* : string */)/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
			let right = removed/* : [List<T>, T] */[1/* : number */];
			return [left/* : unknown */, right/* : unknown */];
		})/* : Option<R> */;
	}
	private foldTypeSeparator(state : DivideState, c : string) : DivideState {
		if (c/* : string */ === /*  ' '  */ && state/* : DivideState */.isLevel/* : unknown */()/* : unknown */){
			return state/* : DivideState */.advance/* : () => DivideState */()/* : DivideState */;
		}
		let appended : DivideState = state/* : DivideState */.append/* : (arg0 : string) => DivideState */(c/* : string */)/* : DivideState */;
		if (c/* : string */ === /*  ' */ < /* ' */){
			return appended/* : DivideState */.enter/* : () => DivideState */()/* : DivideState */;
		}
		if (c/* : string */ === /*  '>' */){
			return appended/* : DivideState */.exit/* : () => DivideState */()/* : DivideState */;
		}
		return appended/* : DivideState */;
	}
	private assembleDefinition(state : CompileState, annotations : List<string>, modifiers : List<string>, rawName : string, typeParams : List<string>, type : string) : Option<[CompileState, Definition]> {
		return this/* : Main */.parseType/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Type]> */(state/* : CompileState */.withTypeParams/* : (arg0 : List<string>) => CompileState */(typeParams/* : List<string> */)/* : CompileState */, type/* : string */)/* : Option<[CompileState, Type]> */.flatMap/* : (arg0 : (arg0 : [CompileState, Type]) => Option<R>) => Option<R> */((type1 : [CompileState, Type]) => {
			let stripped = rawName/* : string */.strip/* : unknown */()/* : unknown */;
			if (!this/* : Main */.isSymbol/* : unknown */(stripped/* : unknown */)/* : unknown */){
				return new None()/* : None */;
			}
			let node : ImmutableDefinition = new ImmutableDefinition(annotations/* : List<string> */, modifiers/* : List<string> */, stripped/* : unknown */, type1/* : [CompileState, Type] */[1/* : number */], typeParams/* : List<string> */)/* : ImmutableDefinition */;
			return new Some([type1/* : [CompileState, Type] */[0/* : number */], node/* : ImmutableDefinition */])/* : Some */;
		})/* : Option<R> */;
	}
	private foldValueChar(state : DivideState, c : string) : DivideState {
		if (c/* : string */ === /*  ','  */ && state/* : DivideState */.isLevel/* : unknown */()/* : unknown */){
			return state/* : DivideState */.advance/* : () => DivideState */()/* : DivideState */;
		}
		let appended : DivideState = state/* : DivideState */.append/* : (arg0 : string) => DivideState */(c/* : string */)/* : DivideState */;
		if (c/* : string */ === /*  ' */ - /* ' */){
			let peeked : string = appended/* : DivideState */.peek/* : () => string */()/* : string */;
			if (peeked/* : string */ === /*  '>' */){
				return appended/* : DivideState */.popAndAppendToOption/* : () => Option<DivideState> */()/* : Option<DivideState> */.orElse/* : (arg0 : DivideState) => T */(appended/* : DivideState */)/* : T */;
			}
			else {
				return appended/* : DivideState */;
			}
		}
		if (c/* : string */ === /*  ' */ < /* '  */ || c/* : string */ === /*  '('  */ || c/* : string */ === /*  '{' */){
			return appended/* : DivideState */.enter/* : () => DivideState */()/* : DivideState */;
		}
		if (c/* : string */ === /*  '>'  */ || c/* : string */ === /*  ')'  */ || c/* : string */ === /*  '}' */){
			return appended/* : DivideState */.exit/* : () => DivideState */()/* : DivideState */;
		}
		return appended/* : DivideState */;
	}
	private parseType(state : CompileState, input : string) : Option<[CompileState, Type]> {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.equals/* : unknown */("int")/* : unknown */ || stripped/* : unknown */.equals/* : unknown */("Integer")/* : unknown */){
			return new Some([state/* : CompileState */, Primitive/* : Primitive */.Int/* : unknown */])/* : Some */;
		}
		if (stripped/* : unknown */.equals/* : unknown */("String")/* : unknown */ || stripped/* : unknown */.equals/* : unknown */("char")/* : unknown */ || stripped/* : unknown */.equals/* : unknown */("Character")/* : unknown */){
			return new Some([state/* : CompileState */, Primitive/* : Primitive */.String/* : unknown */])/* : Some */;
		}
		if (stripped/* : unknown */.equals/* : unknown */("var")/* : unknown */){
			return new Some([state/* : CompileState */, Primitive/* : Primitive */.Unknown/* : unknown */])/* : Some */;
		}
		if (stripped/* : unknown */.equals/* : unknown */("boolean")/* : unknown */){
			return new Some([state/* : CompileState */, Primitive/* : Primitive */.Boolean/* : unknown */])/* : Some */;
		}
		if (stripped/* : unknown */.equals/* : unknown */("void")/* : unknown */){
			return new Some([state/* : CompileState */, Primitive/* : Primitive */.Void/* : unknown */])/* : Some */;
		}
		if (this/* : Main */.isSymbol/* : (arg0 : string) => boolean */(stripped/* : unknown */)/* : boolean */){
			if (/* state.resolveType(stripped) instanceof Some */(/* var resolved */)/* : unknown */){
				return new Some([state/* : CompileState */, /* resolved */])/* : Some */;
			}
			else {
				return new Some([state/* : CompileState */, new Placeholder(stripped/* : unknown */)/* : Placeholder */])/* : Some */;
			}
		}
		return this/* : Main */.parseTemplate/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Type]> */(state/* : CompileState */, input/* : string */)/* : Option<[CompileState, Type]> */.or/* : (arg0 : () => Option<T>) => Option<T> */(() => this/* : Main */.varArgs/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Type]> */(state/* : CompileState */, input/* : string */)/* : Option<[CompileState, Type]> */)/* : Option<T> */;
	}
	private varArgs(state : CompileState, input : string) : Option<[CompileState, Type]> {
		return this/* : Main */.suffix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(input/* : string */, "...", (s : string) => {
			return this/* : Main */.parseType/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Type]> */(state/* : CompileState */, s/* : string */)/* : Option<[CompileState, Type]> */.map/* : (arg0 : (arg0 : [CompileState, Type]) => R) => Option<R> */((inner : [CompileState, Type]) => {
				let newState = inner/* : [CompileState, Type] */[0/* : number */];
				let child = inner/* : [CompileState, Type] */[1/* : number */];
				return [newState/* : unknown */, new ArrayType(child/* : unknown */)/* : ArrayType */];
			})/* : Option<R> */;
		})/* : Option<T> */;
	}
	private assembleTemplate(base : string, state : CompileState, arguments : List<Argument>) : [CompileState, Type] {
		let children : R = arguments/* : List<Argument> */.query/* : () => Query<T> */()/* : Query<T> */.map/* : (arg0 : (arg0 : T) => R) => Query<R> */(this/* : Main */.retainType/* : unknown */)/* : Query<R> */.flatMap/* : (arg0 : (arg0 : T) => Option<R>) => Option<R> */(Queries/* : Queries */.fromOption/* : unknown */)/* : Option<R> */.collect/* : (arg0 : Collector<T, R>) => R */(new ListCollector()/* : ListCollector */)/* : R */;
		if (base/* : string */.equals/* : unknown */("BiFunction")/* : unknown */){
			return [state/* : CompileState */, new FunctionType(Lists/* : Lists */.of/* : (arg0 : T[]) => List<T> */(children/* : R */.get/* : unknown */(0/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */, children/* : R */.get/* : unknown */(1/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : List<T> */, children/* : R */.get/* : unknown */(2/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : FunctionType */];
		}
		if (base/* : string */.equals/* : unknown */("Function")/* : unknown */){
			return [state/* : CompileState */, new FunctionType(Lists/* : Lists */.of/* : (arg0 : T[]) => List<T> */(children/* : R */.get/* : unknown */(0/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : List<T> */, children/* : R */.get/* : unknown */(1/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : FunctionType */];
		}
		if (base/* : string */.equals/* : unknown */("Predicate")/* : unknown */){
			return [state/* : CompileState */, new FunctionType(Lists/* : Lists */.of/* : (arg0 : T[]) => List<T> */(children/* : R */.get/* : unknown */(0/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : List<T> */, Primitive/* : Primitive */.Boolean/* : unknown */)/* : FunctionType */];
		}
		if (base/* : string */.equals/* : unknown */("Supplier")/* : unknown */){
			return [state/* : CompileState */, new FunctionType(Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, children/* : R */.get/* : unknown */(0/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : FunctionType */];
		}
		if (base/* : string */.equals/* : unknown */("Consumer")/* : unknown */){
			return [state/* : CompileState */, new FunctionType(Lists/* : Lists */.of/* : (arg0 : T[]) => List<T> */(children/* : R */.get/* : unknown */(0/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : List<T> */, Primitive/* : Primitive */.Void/* : unknown */)/* : FunctionType */];
		}
		if (base/* : string */.equals/* : unknown */("Tuple2")/* : unknown */ && children/* : R */.size/* : unknown */()/* : unknown */ >= 2/* : number */){
			return [state/* : CompileState */, new TupleType(children/* : R */)/* : TupleType */];
		}
		if (state/* : CompileState */.resolveType/* : (arg0 : string) => Option<Type> */(base/* : string */)/* : Option<Type> */._OptionVariant/* : unknown */ === OptionVariant.Some/* : unknown */){
			let baseType : Type = some/* : Some<Type> */.value/* : Type */;
			if (baseType/* : Type */._Variant/* : unknown */ === Variant.ObjectType/* : unknown */){
			let some : Some<Type> = state/* : CompileState */.resolveType/* : (arg0 : string) => Option<Type> */(base/* : string */)/* : Option<Type> */ as Some<Type>;
				let findableType : ObjectType = baseType/* : Type */ as ObjectType;
				return [state/* : CompileState */, new Template(findableType/* : ObjectType */, children/* : R */)/* : Template */];
			}
		}
		return [state/* : CompileState */, new Template(new ObjectType(base/* : string */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */, Lists/* : Lists */.empty/* : () => List<T> */()/* : List<T> */)/* : ObjectType */, children/* : R */)/* : Template */];
	}
	private parseTemplate(state : CompileState, input : string) : Option<[CompileState, Type]> {
		return this/* : Main */.suffix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string) => Option<T>) => Option<T> */(input/* : string */.strip/* : unknown */()/* : unknown */, ">", (withoutEnd : string) => {
			return this/* : Main */.first/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(withoutEnd/* : string */, "<", (base, argumentsString) => {
				let strippedBase = base/* : unknown */.strip/* : unknown */()/* : unknown */;
				return this/* : Main */.parseValues/* : (arg0 : CompileState, arg1 : string, arg2 : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) => Option<[CompileState, List<T>]> */(state/* : CompileState */, argumentsString/* : unknown */, this/* : Main */.argument/* : unknown */)/* : Option<[CompileState, List<T>]> */.map/* : (arg0 : (arg0 : [CompileState, List<T>]) => R) => Option<R> */((argumentsTuple : [CompileState, List<T>]) => {
					return this/* : Main */.assembleTemplate/* : (arg0 : string, arg1 : CompileState, arg2 : List<Argument>) => [CompileState, Type] */(strippedBase/* : unknown */, argumentsTuple/* : [CompileState, List<T>] */[0/* : number */], argumentsTuple/* : [CompileState, List<T>] */[1/* : number */])/* : [CompileState, Type] */;
				})/* : Option<R> */;
			})/* : Option<T> */;
		})/* : Option<T> */;
	}
	private retainType(argument : Argument) : Option<Type> {
		if (argument/* : Argument */._ArgumentVariant/* : unknown */ === ArgumentVariant./* : unknown */){
			let type : Type = argument/* : Argument */ as Type;
			return new Some(type/* : Type */)/* : Some */;
		}
		else {
			return new None<Type>()/* : None<Type> */;
		}
	}
	private argument(state : CompileState, input : string) : Option<[CompileState, Argument]> {
		if (input/* : string */.isBlank/* : unknown */()/* : unknown */){
			return new Some([state/* : CompileState */, new Whitespace()/* : Whitespace */])/* : Some */;
		}
		return this/* : Main */.parseType/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, Type]> */(state/* : CompileState */, input/* : string */)/* : Option<[CompileState, Type]> */.map/* : (arg0 : (arg0 : [CompileState, Type]) => R) => Option<R> */((tuple : [CompileState, Type]) => [tuple/* : [CompileState, Type] */[0/* : number */], tuple/* : [CompileState, Type] */[1/* : number */]])/* : Option<R> */;
	}
	private last<T>(input : string, infix : string, mapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return this/* : Main */.infix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<number>, arg3 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(input/* : string */, infix/* : string */, this/* : Main */.findLast/* : unknown */, mapper/* : (arg0 : string, arg1 : string) => Option<T> */)/* : Option<T> */;
	}
	private findLast(input : string, infix : string) : Option<number> {
		let index = input/* : string */.lastIndexOf/* : unknown */(infix/* : string */)/* : unknown */;
		if (index/* : unknown */ === -1/* : number */){
			return new None<number>()/* : None<number> */;
		}
		return new Some(index/* : unknown */)/* : Some */;
	}
	private first<T>(input : string, infix : string, mapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return this/* : Main */.infix/* : (arg0 : string, arg1 : string, arg2 : (arg0 : string, arg1 : string) => Option<number>, arg3 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(input/* : string */, infix/* : string */, this/* : Main */.findFirst/* : unknown */, mapper/* : (arg0 : string, arg1 : string) => Option<T> */)/* : Option<T> */;
	}
	private split<T>(splitter : () => Option<[string, string]>, splitMapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return splitter/* : () => Option<[string, string]> */()/* : Option<[string, string]> */.flatMap/* : (arg0 : (arg0 : [string, string]) => Option<R>) => Option<R> */((splitTuple : [string, string]) => splitMapper/* : (arg0 : string, arg1 : string) => Option<T> */(splitTuple/* : [string, string] */[0/* : number */], splitTuple/* : [string, string] */[1/* : number */])/* : Option<T> */)/* : Option<R> */;
	}
	private infix<T>(input : string, infix : string, locator : (arg0 : string, arg1 : string) => Option<number>, mapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return this/* : Main */.split/* : (arg0 : () => Option<[string, string]>, arg1 : (arg0 : string, arg1 : string) => Option<T>) => Option<T> */(() => locator/* : (arg0 : string, arg1 : string) => Option<number> */(input/* : string */, infix/* : string */)/* : Option<number> */.map/* : (arg0 : (arg0 : number) => R) => Option<R> */((index : number) => {
			let left = input/* : string */.substring/* : unknown */(0/* : number */, index/* : number */)/* : unknown */;
			let right = input/* : string */.substring/* : unknown */(index/* : number */ + infix/* : string */.length/* : unknown */()/* : unknown */)/* : unknown */;
			return [left/* : unknown */, right/* : unknown */];
		})/* : Option<R> */, mapper/* : (arg0 : string, arg1 : string) => Option<T> */)/* : Option<T> */;
	}
	private findFirst(input : string, infix : string) : Option<number> {
		let index = input/* : string */.indexOf/* : unknown */(infix/* : string */)/* : unknown */;
		if (index/* : unknown */ === -1/* : number */){
			return new None<number>()/* : None<number> */;
		}
		return new Some(index/* : unknown */)/* : Some */;
	}
}
/*  */