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
	iterate() : Query<T>;
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
/* private sealed */interface Type/*  */ extends Argument {
	generate() : string;
	replace(mapping : Map<string, Type>) : Type;
	findName() : string;
}
enum ArgumentVariant {
	Type,
	Value,
	Whitespace
}
/* private sealed */interface Argument/*  */ {
	_ArgumentVariant : ArgumentVariant;
}
enum ParameterVariant {
	Definition,
	Placeholder,
	Whitespace
}
/* private sealed */interface Parameter/*  */ {
	_ParameterVariant : ParameterVariant;
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
/* private sealed */interface Value/*  */ extends LambdaValue, Caller, Argument {
	_ValueVariant : ValueVariant;
	generate() : string;
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
/* private */interface Header/*  */ {
	createDefinition(paramTypes : List<Type>) : /* Definition */;
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
	EnumValues,
	IncompleteClassSegmentWrapper,
	MethodPrototype,
	Placeholder,
	StructurePrototype,
	Whitespace
}
/* private sealed */interface IncompleteClassSegment/*  */ {
	_IncompleteClassSegmentVariant : IncompleteClassSegmentVariant;
	maybeCreateDefinition() : Option</* Definition */>;
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
		return supplier/* : () => T */.get/* : unknown */()/* : unknown */;
	}
	public or(other : () => Option<T>) : Option<T> {
		return other/* : () => Option<T> */.get/* : unknown */()/* : unknown */;
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
	public map<R>(mapper : (arg0 : T) => R) : Option<R> {
		return new Some(mapper/* : (arg0 : T) => R */.apply/* : unknown */(this/* : Some */.value/* : unknown */)/* : unknown */)/* : Some */;
	}
	public isPresent() : boolean {
		return true;
	}
	public orElse(other : T) : T {
		return this/* : Some */.value/* : unknown */;
	}
	public filter(predicate : (arg0 : T) => boolean) : Option<T> {
		if (predicate/* : (arg0 : T) => boolean */.test/* : unknown */(this/* : Some */.value/* : unknown */)/* : unknown */){
			return this/* : Some */;
		}
		return new None()/* : None */;
	}
	public orElseGet(supplier : () => T) : T {
		return this/* : Some */.value/* : unknown */;
	}
	public or(other : () => Option<T>) : Option<T> {
		return this/* : Some */;
	}
	public flatMap<R>(mapper : (arg0 : T) => Option<R>) : Option<R> {
		return mapper/* : (arg0 : T) => Option<R> */.apply/* : unknown */(this/* : Some */.value/* : unknown */)/* : unknown */;
	}
	public isEmpty() : boolean {
		return false;
	}
	public and<R>(other : () => Option<R>) : Option<[T, R]> {
		return other/* : () => Option<R> */.get/* : unknown */()/* : unknown */.map/* : unknown */((otherValue) => [this/* : Some */.value/* : unknown */, otherValue/* : unknown */])/* : unknown */;
	}
	public ifPresent(consumer : (arg0 : T) => void) : void {
		/* consumer.accept(this.value) */;
	}
}
/* private static */class SingleHead<T>/*  */ implements Head<T> {
	readonly retrievableValue : T;
	retrieved : boolean;
	constructor (retrievableValue : T) {
		this/* : SingleHead */.retrievableValue/* : unknown */ = retrievableValue/* : T */;
		this/* : SingleHead */.retrieved/* : unknown */ = false;
	}
	public next() : Option<T> {
		if (this/* : SingleHead */.retrieved/* : unknown */){
			return new None()/* : None */;
		}
		this/* : SingleHead */.retrieved/* : unknown */ = true;
		return new Some(this/* : SingleHead */.retrievableValue/* : unknown */)/* : Some */;
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
		let current = initial/* : R */;
		while (true){
			let finalCurrent = current/* : unknown */;
			let option = this/* : HeadedQuery */.head/* : unknown */.next/* : unknown */()/* : unknown */.map/* : unknown */((inner) => folder/* : (arg0 : R, arg1 : T) => R */.apply/* : unknown */(finalCurrent/* : unknown */, inner/* : unknown */)/* : unknown */)/* : unknown */;
			if (option/* : unknown */._UnknownVariant/* : unknown */ === UnknownVariant.Some/* : unknown */){
				let some : Some<R> = option/* : unknown */ as Some<R>;
				current/* : unknown */ = some/* : Some<R> */.value/* : unknown */;
			}
			else {
				return current/* : unknown */;
			}
		}
	}
	public map<R>(mapper : (arg0 : T) => R) : Query<R> {
		return new HeadedQuery(new MapHead(this/* : HeadedQuery */.head/* : unknown */, mapper/* : (arg0 : T) => R */)/* : MapHead */)/* : HeadedQuery */;
	}
	public collect<R>(collector : Collector<T, R>) : R {
		return this/* : HeadedQuery */.fold/* : unknown */(collector/* : Collector<T, R> */.createInitial/* : unknown */()/* : unknown */, collector/* : Collector<T, R> */.fold/* : unknown */)/* : unknown */;
	}
	public filter(predicate : (arg0 : T) => boolean) : Query<T> {
		return this/* : HeadedQuery */.flatMap/* : unknown */((element) => {
			if (predicate/* : (arg0 : T) => boolean */.test/* : unknown */(element/* : unknown */)/* : unknown */){
				return new HeadedQuery(new SingleHead(element/* : unknown */)/* : SingleHead */)/* : HeadedQuery */;
			}
			return new HeadedQuery(new EmptyHead()/* : EmptyHead */)/* : HeadedQuery */;
		})/* : unknown */;
	}
	public next() : Option<T> {
		return this/* : HeadedQuery */.head/* : unknown */.next/* : unknown */()/* : unknown */;
	}
	public flatMap<R>(f : (arg0 : T) => Query<R>) : Query<R> {
		return new HeadedQuery(new FlatMapHead(this/* : HeadedQuery */.head/* : unknown */, f/* : (arg0 : T) => Query<R> */)/* : FlatMapHead */)/* : HeadedQuery */;
	}
	public zip<R>(other : Query<R>) : Query<[T, R]> {
		return new HeadedQuery(new ZipHead(this/* : HeadedQuery */.head/* : unknown */, other/* : Query<R> */)/* : ZipHead */)/* : HeadedQuery */;
	}
}
/* private static */class RangeHead/*  */ implements Head<number> {
	readonly length : number;
	counter : number;
	constructor (length : number) {
		this/* : RangeHead */.length/* : unknown */ = length/* : number */;
		this/* : RangeHead */.counter/* : unknown */ = 0/* : number */;
	}
	public next() : Option<number> {
		if (this/* : RangeHead */.counter/* : unknown */ < this/* : RangeHead */.length/* : unknown */){
			let value = this/* : RangeHead */.counter/* : unknown */;
			/* this.counter++ */;
			return new Some(value/* : unknown */)/* : Some */;
		}
		return new None()/* : None */;
	}
}
/* private static */class Lists/*  */ {
	public static empty<T>() : List<T>;
	public static of<T>(elements : T[]) : List<T>;
}
/* private */class Definition/*  */ implements Parameter, Header, StatementValue {
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
		return new Definition(Lists/* : Lists */.empty/* : unknown */()/* : unknown */, Lists/* : Lists */.empty/* : unknown */()/* : unknown */, name/* : string */, type/* : Type */, Lists/* : Lists */.empty/* : unknown */()/* : unknown */)/* : Definition */;
	}
	public findName() : string {
		return this/* : Definition */.name/* : unknown */;
	}
	public findType() : Type {
		return this/* : Definition */.type/* : unknown */;
	}
	public generate() : string {
		return this/* : Definition */.generateWithParams/* : unknown */("")/* : unknown */;
	}
	generateType() : string {
		if (this/* : Definition */.type/* : unknown */ === Primitive/* : Primitive */.Unknown/* : unknown */){
			return "";
		}
		return " : " + this/* : Definition */.type/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
	joinModifiers() : string {
		return this/* : Definition */.modifiers/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */((value) => value/* : unknown */ + " ")/* : unknown */.collect/* : unknown */(new Joiner("")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
	}
	joinTypeParams() : string {
		return this/* : Definition */.typeParams/* : unknown */.iterate/* : unknown */()/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.map/* : unknown */((inner) => "<" + inner + ">")/* : unknown */.orElse/* : unknown */("")/* : unknown */;
	}
	public mapType(mapper : (arg0 : Type) => Type) : Definition {
		return new Definition(this/* : Definition */.annotations/* : unknown */, this/* : Definition */.modifiers/* : unknown */, this/* : Definition */.name/* : unknown */, mapper/* : (arg0 : Type) => Type */.apply/* : unknown */(this/* : Definition */.type/* : unknown */)/* : unknown */, this/* : Definition */.typeParams/* : unknown */)/* : Definition */;
	}
	public generateWithParams(joinedParameters : string) : string {
		let joinedAnnotations = this/* : Definition */.annotations/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */((value) => "@" + value + " ")/* : unknown */.collect/* : unknown */(Joiner/* : Joiner */.empty/* : unknown */()/* : unknown */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		let joined = this/* : Definition */.joinTypeParams/* : unknown */()/* : unknown */;
		let before = this/* : Definition */.joinModifiers/* : unknown */()/* : unknown */;
		let typeString = this/* : Definition */.generateType/* : unknown */()/* : unknown */;
		return joinedAnnotations/* : unknown */ + before/* : unknown */ + this/* : Definition */.name/* : unknown */ + joined/* : unknown */ + joinedParameters/* : string */ + typeString/* : unknown */;
	}
	public createDefinition(paramTypes : List<Type>) : Definition {
		let type1 : Type = new FunctionType(paramTypes/* : List<Type> */, this/* : Definition */.type/* : unknown */)/* : FunctionType */;
		return new Definition(this/* : Definition */.annotations/* : unknown */, this/* : Definition */.modifiers/* : unknown */, this/* : Definition */.name/* : unknown */, type1/* : Type */, this/* : Definition */.typeParams/* : unknown */)/* : Definition */;
	}
	public containsAnnotation(annotation : string) : boolean {
		return this/* : Definition */.annotations/* : unknown */.contains/* : unknown */(annotation/* : string */)/* : unknown */;
	}
	public removeAnnotations() : Definition {
		return new Definition(Lists/* : Lists */.empty/* : unknown */()/* : unknown */, this/* : Definition */.modifiers/* : unknown */, this/* : Definition */.name/* : unknown */, this/* : Definition */.type/* : unknown */, this/* : Definition */.typeParams/* : unknown */)/* : Definition */;
	}
	public toString() : string {
		return "ImmutableDefinition[" + "annotations=" + this/* : Definition */.annotations/* : unknown */ + ", " + "maybeBefore=" + this/* : Definition */.modifiers/* : unknown */ + ", " + "findName=" + this/* : Definition */.name/* : unknown */ + ", " + "findType=" + this/* : Definition */.type/* : unknown */ + ", " + "typeParams=" + this/* : Definition */.typeParams/* : unknown */ + "]";
	}
}
/* private */class ObjectRefType/*  */ implements Type {
	name : string;
	constructor (name : string) {
		this/* : unknown */.name/* : unknown */ = name/* : unknown */;
	}
	public generate() : string {
		return this/* : ObjectRefType */.name/* : unknown */;
	}
	public replace(mapping : Map<string, Type>) : Type {
		return new ObjectRefType(this/* : ObjectRefType */.name/* : unknown */)/* : ObjectRefType */;
	}
	public findName() : string {
		return this/* : ObjectRefType */.name/* : unknown */;
	}
}
/* private */class TypeParam/*  */ implements Type {
	value : string;
	constructor (value : string) {
		this/* : unknown */.value/* : unknown */ = value/* : unknown */;
	}
	public generate() : string {
		return this/* : TypeParam */.value/* : unknown */;
	}
	public replace(mapping : Map<string, Type>) : Type {
		return mapping/* : Map<string, Type> */.find/* : unknown */(this/* : TypeParam */.value/* : unknown */)/* : unknown */.orElse/* : unknown */(this/* : TypeParam */)/* : unknown */;
	}
	public findName() : string {
		return "";
	}
}
/* private */class CompileState/*  */ {
	structures : List<string>;
	definitions : List<List<Definition>>;
	structureTypes : List</* StructureType */>;
	structNames : List<[string, List<string>]>;
	typeParams : List<string>;
	typeRegister : Option<Type>;
	functionSegments : List<FunctionSegment>;
	constructor (structures : List<string>, definitions : List<List<Definition>>, structureTypes : List</* StructureType */>, structNames : List<[string, List<string>]>, typeParams : List<string>, typeRegister : Option<Type>, functionSegments : List<FunctionSegment>) {
		this/* : unknown */.structures/* : unknown */ = structures/* : unknown */;
		this/* : unknown */.definitions/* : unknown */ = definitions/* : unknown */;
		this/* : unknown */.structureTypes/* : unknown */ = structureTypes/* : unknown */;
		this/* : unknown */.structNames/* : unknown */ = structNames/* : unknown */;
		this/* : unknown */.typeParams/* : unknown */ = typeParams/* : unknown */;
		this/* : unknown */.typeRegister/* : unknown */ = typeRegister/* : unknown */;
		this/* : unknown */.functionSegments/* : unknown */ = functionSegments/* : unknown */;
	}
	public static createInitial() : CompileState {
		return new CompileState(Lists/* : Lists */.empty/* : unknown */()/* : unknown */, Lists/* : Lists */.of/* : unknown */(Lists/* : Lists */.empty/* : unknown */()/* : unknown */)/* : unknown */, Lists/* : Lists */.empty/* : unknown */()/* : unknown */, Lists/* : Lists */.empty/* : unknown */()/* : unknown */, Lists/* : Lists */.empty/* : unknown */()/* : unknown */, new None()/* : None */, Lists/* : Lists */.empty/* : unknown */()/* : unknown */)/* : CompileState */;
	}
	resolveValue(name : string) : Option<Type> {
		return this/* : CompileState */.definitions/* : unknown */.iterateReversed/* : unknown */()/* : unknown */.flatMap/* : unknown */(List/* : List */.iterate/* : unknown */)/* : unknown */.filter/* : unknown */((definition) => definition/* : unknown */.findName/* : unknown */()/* : unknown */ === name/* : string */)/* : unknown */.next/* : unknown */()/* : unknown */.map/* : unknown */(Definition/* : Definition */.findType/* : unknown */)/* : unknown */;
	}
	public addStructure(structure : string) : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : unknown */.addLast/* : unknown */(structure/* : string */)/* : unknown */, this/* : CompileState */.definitions/* : unknown */, this/* : CompileState */.structureTypes/* : unknown */, this/* : CompileState */.structNames/* : unknown */, this/* : CompileState */.typeParams/* : unknown */, this/* : CompileState */.typeRegister/* : unknown */, this/* : CompileState */.functionSegments/* : unknown */)/* : CompileState */;
	}
	public defineAll(definitions : List<Definition>) : CompileState {
		let defined = this/* : CompileState */.definitions/* : unknown */.mapLast/* : unknown */((frame) => frame/* : unknown */.addAllLast/* : unknown */(definitions/* : List<Definition> */)/* : unknown */)/* : unknown */;
		return new CompileState(this/* : CompileState */.structures/* : unknown */, defined/* : unknown */, this/* : CompileState */.structureTypes/* : unknown */, this/* : CompileState */.structNames/* : unknown */, this/* : CompileState */.typeParams/* : unknown */, this/* : CompileState */.typeRegister/* : unknown */, this/* : CompileState */.functionSegments/* : unknown */)/* : CompileState */;
	}
	public resolveType(name : string) : Option<Type> {
		let maybe : Option<[string, List<string>]> = this/* : CompileState */.structNames/* : unknown */.last/* : unknown */()/* : unknown */.filter/* : unknown */((inner) => inner/* : unknown */.left/* : unknown */()/* : unknown */ === name/* : string */)/* : unknown */;
		if (maybe/* : Option<[string, List<string>]> */._UnknownVariant/* : unknown */ === UnknownVariant.Some/* : unknown */){
			let some : Some<[string, List<string>]> = maybe/* : Option<[string, List<string>]> */ as Some<[string, List<string>]>;
			let found = some/* : Some<[string, List<string>]> */.value/* : unknown */;
			return new Some(new ObjectRefType(found/* : unknown */.left/* : unknown */()/* : unknown */)/* : ObjectRefType */)/* : Some */;
		}
		let maybeTypeParam = this/* : CompileState */.typeParams/* : unknown */.iterate/* : unknown */()/* : unknown */.filter/* : unknown */((param) => param/* : unknown */ === name/* : string */)/* : unknown */.next/* : unknown */()/* : unknown */;
		if (maybeTypeParam/* : unknown */._UnknownVariant/* : unknown */ === UnknownVariant.Some/* : unknown */){
			let some : Some<string> = maybeTypeParam/* : unknown */ as Some<string>;
			return new Some(new TypeParam(some/* : Some<[string, List<string>]> */.value/* : unknown */)/* : TypeParam */)/* : Some */;
		}
		return this/* : CompileState */.findStructure/* : unknown */(name/* : string */)/* : unknown */.map/* : unknown */((type) => type/* : unknown */)/* : unknown */;
	}
	public define(definition : Definition) : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : unknown */, this/* : CompileState */.definitions/* : unknown */.mapLast/* : unknown */((frame) => frame/* : unknown */.addLast/* : unknown */(definition/* : Definition */)/* : unknown */)/* : unknown */, this/* : CompileState */.structureTypes/* : unknown */, this/* : CompileState */.structNames/* : unknown */, this/* : CompileState */.typeParams/* : unknown */, this/* : CompileState */.typeRegister/* : unknown */, this/* : CompileState */.functionSegments/* : unknown */)/* : CompileState */;
	}
	public pushStructName(definition : [string, List<string>]) : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : unknown */, this/* : CompileState */.definitions/* : unknown */, this/* : CompileState */.structureTypes/* : unknown */, this/* : CompileState */.structNames/* : unknown */.addLast/* : unknown */(definition/* : [string, List<string>] */)/* : unknown */, this/* : CompileState */.typeParams/* : unknown */, this/* : CompileState */.typeRegister/* : unknown */, this/* : CompileState */.functionSegments/* : unknown */)/* : CompileState */;
	}
	public withTypeParams(typeParams : List<string>) : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : unknown */, this/* : CompileState */.definitions/* : unknown */, this/* : CompileState */.structureTypes/* : unknown */, this/* : CompileState */.structNames/* : unknown */, this/* : CompileState */.typeParams/* : unknown */.addAllLast/* : unknown */(typeParams/* : List<string> */)/* : unknown */, this/* : CompileState */.typeRegister/* : unknown */, this/* : CompileState */.functionSegments/* : unknown */)/* : CompileState */;
	}
	public withExpectedType(type : Type) : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : unknown */, this/* : CompileState */.definitions/* : unknown */, this/* : CompileState */.structureTypes/* : unknown */, this/* : CompileState */.structNames/* : unknown */, this/* : CompileState */.typeParams/* : unknown */, new Some(type/* : Type */)/* : Some */, this/* : CompileState */.functionSegments/* : unknown */)/* : CompileState */;
	}
	public popStructName() : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : unknown */, this/* : CompileState */.definitions/* : unknown */, this/* : CompileState */.structureTypes/* : unknown */, this/* : CompileState */.structNames/* : unknown */.removeLast/* : unknown */()/* : unknown */.map/* : unknown */(Tuple2/* : Tuple2 */.left/* : unknown */)/* : unknown */.orElse/* : unknown */(this/* : CompileState */.structNames/* : unknown */)/* : unknown */, this/* : CompileState */.typeParams/* : unknown */, this/* : CompileState */.typeRegister/* : unknown */, this/* : CompileState */.functionSegments/* : unknown */)/* : CompileState */;
	}
	public enterDefinitions() : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : unknown */, this/* : CompileState */.definitions/* : unknown */.addLast/* : unknown */(Lists/* : Lists */.empty/* : unknown */()/* : unknown */)/* : unknown */, this/* : CompileState */.structureTypes/* : unknown */, this/* : CompileState */.structNames/* : unknown */, this/* : CompileState */.typeParams/* : unknown */, this/* : CompileState */.typeRegister/* : unknown */, this/* : CompileState */.functionSegments/* : unknown */)/* : CompileState */;
	}
	public exitDefinitions() : CompileState {
		let removed = this/* : CompileState */.definitions/* : unknown */.removeLast/* : unknown */()/* : unknown */.map/* : unknown */(Tuple2/* : Tuple2 */.left/* : unknown */)/* : unknown */.orElse/* : unknown */(this/* : CompileState */.definitions/* : unknown */)/* : unknown */;
		return new CompileState(this/* : CompileState */.structures/* : unknown */, removed/* : unknown */, this/* : CompileState */.structureTypes/* : unknown */, this/* : CompileState */.structNames/* : unknown */, this/* : CompileState */.typeParams/* : unknown */, this/* : CompileState */.typeRegister/* : unknown */, this/* : CompileState */.functionSegments/* : unknown */)/* : CompileState */;
	}
	public addType(thisType : /* StructureType */) : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : unknown */, this/* : CompileState */.definitions/* : unknown */, this/* : CompileState */.structureTypes/* : unknown */.addLast/* : unknown */(thisType/* : content-start StructureType content-end */)/* : unknown */, this/* : CompileState */.structNames/* : unknown */, this/* : CompileState */.typeParams/* : unknown */, this/* : CompileState */.typeRegister/* : unknown */, this/* : CompileState */.functionSegments/* : unknown */)/* : CompileState */;
	}
	public addFunctionSegment(segment : FunctionSegment) : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : unknown */, this/* : CompileState */.definitions/* : unknown */, this/* : CompileState */.structureTypes/* : unknown */, this/* : CompileState */.structNames/* : unknown */, this/* : CompileState */.typeParams/* : unknown */, this/* : CompileState */.typeRegister/* : unknown */, this/* : CompileState */.functionSegments/* : unknown */.addLast/* : unknown */(segment/* : FunctionSegment */)/* : unknown */)/* : CompileState */;
	}
	public clearFunctionSegments() : CompileState {
		return new CompileState(this/* : CompileState */.structures/* : unknown */, this/* : CompileState */.definitions/* : unknown */, this/* : CompileState */.structureTypes/* : unknown */, this/* : CompileState */.structNames/* : unknown */, this/* : CompileState */.typeParams/* : unknown */, this/* : CompileState */.typeRegister/* : unknown */, Lists/* : Lists */.empty/* : unknown */()/* : unknown */)/* : CompileState */;
	}
	isCurrentStructName(stripped : string) : boolean {
		return stripped/* : string */ === this/* : CompileState */.structNames/* : unknown */.last/* : unknown */()/* : unknown */.map/* : unknown */(Tuple2/* : Tuple2 */.left/* : unknown */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
	}
	public findStructure(name : string) : Option</* StructureType */> {
		return this/* : CompileState */.structureTypes/* : unknown */.iterate/* : unknown */()/* : unknown */.filter/* : unknown */((structureType) => structureType/* : unknown */.name/* : unknown */ === name/* : string */)/* : unknown */.next/* : unknown */()/* : unknown */;
	}
}
/* private */class StructureType/*  */ implements Type {
	name : string;
	variants : List<string>;
	definitions : List<Definition>;
	constructor (name : string, variants : List<string>, definitions : List<Definition>) {
		this/* : unknown */.name/* : unknown */ = name/* : unknown */;
		this/* : unknown */.variants/* : unknown */ = variants/* : unknown */;
		this/* : unknown */.definitions/* : unknown */ = definitions/* : unknown */;
	}
	public hasVariant(name : string) : boolean {
		return this/* : StructureType */.variants/* : unknown */.contains/* : unknown */(name/* : string */)/* : unknown */;
	}
	public generate() : string {
		return this/* : StructureType */.name/* : unknown */;
	}
	public replace(mapping : Map<string, Type>) : Type {
		return this/* : StructureType */;
	}
	public findName() : string {
		return this/* : StructureType */.name/* : unknown */;
	}
	public find(property : string) : Option<Type> {
		return this/* : StructureType */.definitions/* : unknown */.iterate/* : unknown */()/* : unknown */.filter/* : unknown */((definition) => definition/* : unknown */.name/* : unknown */ === property/* : string */)/* : unknown */.map/* : unknown */(Definition/* : Definition */.type/* : unknown */)/* : unknown */.next/* : unknown */()/* : unknown */;
	}
}
/* private static */class DivideState/*  */ {
	readonly input : string;
	readonly index : number;
	depth : number;
	segments : List<string>;
	buffer : string;
	constructor (input : string, index : number, segments : List<string>, buffer : string, depth : number) {
		this/* : DivideState */.segments/* : unknown */ = segments/* : List<string> */;
		this/* : DivideState */.buffer/* : unknown */ = buffer/* : string */;
		this/* : DivideState */.depth/* : unknown */ = depth/* : number */;
		this/* : DivideState */.input/* : unknown */ = input/* : string */;
		this/* : DivideState */.index/* : unknown */ = index/* : number */;
	}
	public static createInitial(input : string) : DivideState {
		return new DivideState(input/* : string */, 0/* : number */, Lists/* : Lists */.empty/* : unknown */()/* : unknown */, "", 0/* : number */)/* : DivideState */;
	}
	advance() : DivideState {
		this/* : DivideState */.segments/* : unknown */ = this/* : DivideState */.segments/* : unknown */.addLast/* : unknown */(this/* : DivideState */.buffer/* : unknown */)/* : unknown */;
		this/* : DivideState */.buffer/* : unknown */ = "";
		return this/* : DivideState */;
	}
	append(c : string) : DivideState {
		this/* : DivideState */.buffer/* : unknown */ = this/* : DivideState */.buffer/* : unknown */ + c/* : string */;
		return this/* : DivideState */;
	}
	public enter() : DivideState {
		/* this.depth++ */;
		return this/* : DivideState */;
	}
	public isLevel() : boolean {
		return this/* : DivideState */.depth/* : unknown */ === 0/* : number */;
	}
	public exit() : DivideState {
		/* this.depth-- */;
		return this/* : DivideState */;
	}
	public isShallow() : boolean {
		return this/* : DivideState */.depth/* : unknown */ === 1/* : number */;
	}
	public pop() : Option<[string, DivideState]> {
		if (this/* : DivideState */.index/* : unknown */ < Strings/* : Strings */.length/* : unknown */(this/* : DivideState */.input/* : unknown */)/* : unknown */){
			let c = this/* : DivideState */.input/* : unknown */.charAt/* : unknown */(this/* : DivideState */.index/* : unknown */)/* : unknown */;
			return new Some([c/* : unknown */, new DivideState(this/* : DivideState */.input/* : unknown */, this/* : DivideState */.index/* : unknown */ + 1/* : number */, this/* : DivideState */.segments/* : unknown */, this/* : DivideState */.buffer/* : unknown */, this/* : DivideState */.depth/* : unknown */)/* : DivideState */])/* : Some */;
		}
		return new None()/* : None */;
	}
	public popAndAppendToTuple() : Option<[string, DivideState]> {
		return this/* : DivideState */.pop/* : unknown */()/* : unknown */.map/* : unknown */((tuple) => {
			let c = tuple/* : unknown */.left/* : unknown */()/* : unknown */;
			let right = tuple/* : unknown */.right/* : unknown */()/* : unknown */;
			return [c/* : unknown */, right/* : unknown */.append/* : unknown */(c/* : unknown */)/* : unknown */];
		})/* : unknown */;
	}
	public popAndAppendToOption() : Option<DivideState> {
		return this/* : DivideState */.popAndAppendToTuple/* : unknown */()/* : unknown */.map/* : unknown */(Tuple2/* : Tuple2 */.right/* : unknown */)/* : unknown */;
	}
	public peek() : string {
		return this/* : DivideState */.input/* : unknown */.charAt/* : unknown */(this/* : DivideState */.index/* : unknown */)/* : unknown */;
	}
}
/* private */class Joiner/*  */ implements Collector<string, Option<string>> {
	delimiter : string;
	constructor (delimiter : string) {
		this/* : unknown */.delimiter/* : unknown */ = delimiter/* : unknown */;
	}
	static empty() : Joiner {
		return new Joiner("")/* : Joiner */;
	}
	public createInitial() : Option<string> {
		return new None()/* : None */;
	}
	public fold(current : Option<string>, element : string) : Option<string> {
		return new Some(current/* : Option<string> */.map/* : unknown */((inner) => inner/* : unknown */ + this/* : Joiner */.delimiter/* : unknown */ + element/* : string */)/* : unknown */.orElse/* : unknown */(element/* : string */)/* : unknown */)/* : Some */;
	}
}
/* private static */class ListCollector<T>/*  */ implements Collector<T, List<T>> {
	public createInitial() : List<T> {
		return Lists/* : Lists */.empty/* : unknown */()/* : unknown */;
	}
	public fold(current : List<T>, element : T) : List<T> {
		return current/* : List<T> */.addLast/* : unknown */(element/* : T */)/* : unknown */;
	}
}
/* private static */class FlatMapHead<T, R>/*  */ implements Head<R> {
	readonly mapper : (arg0 : T) => Query<R>;
	readonly head : Head<T>;
	current : Option<Query<R>>;
	constructor (head : Head<T>, mapper : (arg0 : T) => Query<R>) {
		this/* : FlatMapHead */.mapper/* : unknown */ = mapper/* : (arg0 : T) => Query<R> */;
		this/* : FlatMapHead */.current/* : unknown */ = new None()/* : None */;
		this/* : FlatMapHead */.head/* : unknown */ = head/* : Head<T> */;
	}
	public next() : Option<R> {
		while (true){
			if (this/* : FlatMapHead */.current/* : unknown */.isPresent/* : unknown */()/* : unknown */){
				let inner : Query<R> = this/* : FlatMapHead */.current/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */;
				let maybe : Option<R> = inner/* : Query<R> */.next/* : unknown */()/* : unknown */;
				if (maybe/* : Option<R> */.isPresent/* : unknown */()/* : unknown */){
					return maybe/* : Option<R> */;
				}
				else {
					this/* : FlatMapHead */.current/* : unknown */ = new None()/* : None */;
				}
			}
			let outer : Option<T> = this/* : FlatMapHead */.head/* : unknown */.next/* : unknown */()/* : unknown */;
			if (outer/* : Option<T> */.isPresent/* : unknown */()/* : unknown */){
				this/* : FlatMapHead */.current/* : unknown */ = outer/* : Option<T> */.map/* : unknown */(this/* : FlatMapHead */.mapper/* : unknown */)/* : unknown */;
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
		return this/* : ArrayType */.right/* : unknown */.generate/* : unknown */()/* : unknown */ + "[]";
	}
	public replace(mapping : Map<string, Type>) : Type {
		return this/* : ArrayType */;
	}
	public findName() : string {
		return "";
	}
}
/* private static final */class Whitespace/*  */ implements Argument, Parameter, ClassSegment, FunctionSegment, IncompleteClassSegment {
	public generate() : string {
		return "";
	}
	public maybeCreateDefinition() : Option<Definition> {
		return new None()/* : None */;
	}
}
/* private static */class Queries/*  */ {
	public static fromOption<T>(option : Option<T>) : Query<T> {
		let single : Option<Head<T>> = option/* : Option<T> */.map/* : unknown */(SingleHead/* : SingleHead */.new/* : unknown */)/* : unknown */;
		return new HeadedQuery(single/* : Option<Head<T>> */.orElseGet/* : unknown */(EmptyHead/* : EmptyHead */.new/* : unknown */)/* : unknown */)/* : HeadedQuery */;
	}
	public static from<T>(elements : T[]) : Query<T> {
		return new HeadedQuery(new RangeHead(elements/* : T[] */.length/* : unknown */)/* : RangeHead */)/* : HeadedQuery */.map/* : unknown */((index) => /* elements[index] */)/* : unknown */;
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
		let joined = this/* : FunctionType */.arguments/* : unknown */()/* : unknown */.iterateWithIndices/* : unknown */()/* : unknown */.map/* : unknown */((pair) => "arg" + pair/* : unknown */.left/* : unknown */()/* : unknown */ + " : " + pair/* : unknown */.right/* : unknown */()/* : unknown */.generate/* : unknown */()/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return "(" + joined/* : unknown */ + ") => " + this/* : FunctionType */.returns/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
	public replace(mapping : Map<string, Type>) : Type {
		return new FunctionType(this/* : FunctionType */.arguments/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */((type) => type/* : unknown */.replace/* : unknown */(mapping/* : Map<string, Type> */)/* : unknown */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */, this/* : FunctionType */.returns/* : unknown */.replace/* : unknown */(mapping/* : Map<string, Type> */)/* : unknown */)/* : FunctionType */;
	}
	public findName() : string {
		return "";
	}
}
/* private */class TupleType/*  */ implements Type {
	arguments : List<Type>;
	constructor (arguments : List<Type>) {
		this/* : unknown */.arguments/* : unknown */ = arguments/* : unknown */;
	}
	public generate() : string {
		let joinedArguments = this/* : TupleType */.arguments/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(Type/* : Type */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return "[" + joinedArguments + "]";
	}
	public replace(mapping : Map<string, Type>) : Type {
		return new TupleType(this/* : TupleType */.arguments/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */((child) => child/* : unknown */.replace/* : unknown */(mapping/* : Map<string, Type> */)/* : unknown */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */)/* : TupleType */;
	}
	public findName() : string {
		return "";
	}
}
/* private */class Template/*  */ implements Type {
	base : ObjectRefType;
	arguments : List<Type>;
	constructor (base : ObjectRefType, arguments : List<Type>) {
		this/* : unknown */.base/* : unknown */ = base/* : unknown */;
		this/* : unknown */.arguments/* : unknown */ = arguments/* : unknown */;
	}
	public generate() : string {
		let joinedArguments = this/* : Template */.arguments/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(Type/* : Type */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.map/* : unknown */((inner) => "<" + inner + ">")/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return this/* : Template */.base/* : unknown */.generate/* : unknown */()/* : unknown */ + joinedArguments/* : unknown */;
	}
	public replace(mapping : Map<string, Type>) : Type {
		let collect = this/* : Template */.arguments/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */((argument) => argument/* : unknown */.replace/* : unknown */(mapping/* : Map<string, Type> */)/* : unknown */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
		return new Template(this/* : Template */.base/* : unknown */, collect/* : unknown */)/* : Template */;
	}
	public findName() : string {
		return this/* : Template */.base/* : unknown */.findName/* : unknown */()/* : unknown */;
	}
}
/* private */class Placeholder/*  */ implements Parameter, Value, ClassSegment, FunctionSegment, BlockHeader, StatementValue, IncompleteClassSegment, Type {
	input : string;
	constructor (input : string) {
		this/* : unknown */.input/* : unknown */ = input/* : unknown */;
	}
	public generate() : string {
		return generatePlaceholder/* : (arg0 : string) => string */(this/* : Placeholder */.input/* : unknown */)/* : unknown */;
	}
	public replace(mapping : Map<string, Type>) : Type {
		return this/* : Placeholder */;
	}
	public findName() : string {
		return "";
	}
	public maybeCreateDefinition() : Option<Definition> {
		return new None()/* : None */;
	}
}
/* private */class StringValue/*  */ implements Value {
	value : string;
	constructor (value : string) {
		this/* : unknown */.value/* : unknown */ = value/* : unknown */;
	}
	public generate() : string {
		return "\"" + this.value + "\"";
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
	public generate() : string {
		return this/* : DataAccess */.parent/* : unknown */.generate/* : unknown */()/* : unknown */ + "." + this/* : DataAccess */.property/* : unknown */ + createDebugString/* : (arg0 : Type) => string */(this/* : DataAccess */.type/* : unknown */)/* : unknown */;
	}
}
/* private */class ConstructionCaller/*  */ implements Caller {
	type : Type;
	constructor (type : Type) {
		this/* : unknown */.type/* : unknown */ = type/* : unknown */;
	}
	public generate() : string {
		return "new " + this/* : ConstructionCaller */.type/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
	public toFunction() : FunctionType {
		return new FunctionType(Lists/* : Lists */.empty/* : unknown */()/* : unknown */, this/* : ConstructionCaller */.type/* : unknown */)/* : FunctionType */;
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
	public generate() : string {
		return this/* : Operation */.left/* : unknown */()/* : unknown */.generate/* : unknown */()/* : unknown */ + " " + this/* : Operation */.operator/* : unknown */.targetRepresentation/* : unknown */ + " " + this/* : Operation */.right/* : unknown */()/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
}
/* private */class Not/*  */ implements Value {
	value : Value;
	constructor (value : Value) {
		this/* : unknown */.value/* : unknown */ = value/* : unknown */;
	}
	public generate() : string {
		return "!" + this/* : Not */.value/* : unknown */.generate/* : unknown */()/* : unknown */;
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
	joinStatements() : string {
		return this/* : BlockLambdaValue */.statements/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(FunctionSegment/* : FunctionSegment */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(Joiner/* : Joiner */.empty/* : unknown */()/* : unknown */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
	}
}
/* private */class Lambda/*  */ implements Value {
	parameters : List<Definition>;
	body : LambdaValue;
	constructor (parameters : List<Definition>, body : LambdaValue) {
		this/* : unknown */.parameters/* : unknown */ = parameters/* : unknown */;
		this/* : unknown */.body/* : unknown */ = body/* : unknown */;
	}
	public generate() : string {
		let joined = this/* : Lambda */.parameters/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(Definition/* : Definition */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return "(" + joined/* : unknown */ + ") => " + this/* : Lambda */.body/* : unknown */.generate/* : unknown */()/* : unknown */;
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
	public generate() : string {
		let joined = this/* : Invokable */.arguments/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(Value/* : Value */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return this/* : Invokable */.caller/* : unknown */.generate/* : unknown */()/* : unknown */ + "(" + joined/* : unknown */ + ")" + createDebugString/* : (arg0 : Type) => string */(this/* : Invokable */.type/* : unknown */)/* : unknown */;
	}
}
/* private */class IndexValue/*  */ implements Value {
	parent : Value;
	child : Value;
	constructor (parent : Value, child : Value) {
		this/* : unknown */.parent/* : unknown */ = parent/* : unknown */;
		this/* : unknown */.child/* : unknown */ = child/* : unknown */;
	}
	public generate() : string {
		return this/* : IndexValue */.parent/* : unknown */.generate/* : unknown */()/* : unknown */ + "[" + this.child.generate() + "]";
	}
}
/* private */class SymbolValue/*  */ implements Value {
	stripped : string;
	type : Type;
	constructor (stripped : string, type : Type) {
		this/* : unknown */.stripped/* : unknown */ = stripped/* : unknown */;
		this/* : unknown */.type/* : unknown */ = type/* : unknown */;
	}
	public generate() : string {
		return this/* : SymbolValue */.stripped/* : unknown */ + createDebugString/* : (arg0 : Type) => string */(this/* : SymbolValue */.type/* : unknown */)/* : unknown */;
	}
}
/* private static */class Maps/*  */ {
	public static empty<V, K>() : Map<K, V>;
}
/* private */class MapCollector<K, V>/*  */ implements Collector<[K, V], Map<K, V>> {
	public createInitial() : Map<K, V> {
		return Maps/* : Maps */.empty/* : unknown */()/* : unknown */;
	}
	public fold(current : Map<K, V>, element : [K, V]) : Map<K, V> {
		return current/* : Map<K, V> */.with/* : unknown */(element/* : [K, V] */.left/* : unknown */()/* : unknown */, element/* : [K, V] */.right/* : unknown */()/* : unknown */)/* : unknown */;
	}
}
/* private static */class ConstructorHeader/*  */ implements Header {
	public createDefinition(paramTypes : List<Type>) : Definition {
		return Definition/* : Definition */.createSimpleDefinition/* : unknown */("new", Primitive/* : Primitive */.Unknown/* : unknown */)/* : unknown */;
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
	static joinStatements(statements : List<FunctionSegment>) : string {
		return statements/* : List<FunctionSegment> */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(FunctionSegment/* : FunctionSegment */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(Joiner/* : Joiner */.empty/* : unknown */()/* : unknown */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
	}
	public generate() : string {
		let indent = createIndent/* : (arg0 : number) => string */(this/* : FunctionNode */.depth/* : unknown */)/* : unknown */;
		let generatedHeader = this/* : FunctionNode */.header/* : unknown */.generateWithParams/* : unknown */(joinValues/* : (arg0 : List<Definition>) => string */(this/* : FunctionNode */.parameters/* : unknown */)/* : unknown */)/* : unknown */;
		let generatedStatements = this/* : FunctionNode */.maybeStatements/* : unknown */.map/* : unknown */(FunctionNode/* : FunctionNode */.joinStatements/* : unknown */)/* : unknown */.map/* : unknown */((inner) => " {" + inner + indent + "}")/* : unknown */.orElse/* : unknown */(";")/* : unknown */;
		return indent/* : unknown */ + generatedHeader/* : unknown */ + generatedStatements/* : unknown */;
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
		let indent = createIndent/* : (arg0 : number) => string */(this/* : Block */.depth/* : unknown */)/* : unknown */;
		let collect = this/* : Block */.statements/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(FunctionSegment/* : FunctionSegment */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(Joiner/* : Joiner */.empty/* : unknown */()/* : unknown */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return indent/* : unknown */ + this/* : Block */.header/* : unknown */.generate/* : unknown */()/* : unknown */ + "{" + collect + indent + "}";
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
		return this/* : Conditional */.prefix/* : unknown */ + " (" + this.value1.generate() + ")";
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
		return "let " + this/* : Initialization */.definition/* : unknown */.generate/* : unknown */()/* : unknown */ + " = " + this/* : Initialization */.source/* : unknown */.generate/* : unknown */()/* : unknown */;
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
		return this/* : FieldInitialization */.definition/* : unknown */.generate/* : unknown */()/* : unknown */ + " = " + this/* : FieldInitialization */.source/* : unknown */.generate/* : unknown */()/* : unknown */;
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
		return this/* : Assignment */.destination/* : unknown */.generate/* : unknown */()/* : unknown */ + " = " + this/* : Assignment */.source/* : unknown */.generate/* : unknown */()/* : unknown */;
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
		return createIndent/* : (arg0 : number) => string */(this/* : Statement */.depth/* : unknown */)/* : unknown */ + this/* : Statement */.value/* : unknown */.generate/* : unknown */()/* : unknown */ + ";";
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
	createDefinition() : Definition {
		return this/* : MethodPrototype */.header/* : unknown */.createDefinition/* : unknown */(this/* : MethodPrototype */.findParamTypes/* : unknown */()/* : unknown */)/* : unknown */;
	}
	findParamTypes() : List<Type> {
		return this/* : MethodPrototype */.parameters/* : unknown */()/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(Definition/* : Definition */.findType/* : unknown */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
	}
	public maybeCreateDefinition() : Option<Definition> {
		return new Some(this/* : MethodPrototype */.header/* : unknown */.createDefinition/* : unknown */(this/* : MethodPrototype */.findParamTypes/* : unknown */()/* : unknown */)/* : unknown */)/* : Some */;
	}
}
/* private */class IncompleteClassSegmentWrapper/*  */ implements IncompleteClassSegment {
	segment : ClassSegment;
	constructor (segment : ClassSegment) {
		this/* : unknown */.segment/* : unknown */ = segment/* : unknown */;
	}
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
	public maybeCreateDefinition() : Option<Definition> {
		return new Some(this/* : ClassDefinition */.definition/* : unknown */)/* : Some */;
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
	public maybeCreateDefinition() : Option<Definition> {
		return new Some(this/* : ClassInitialization */.definition/* : unknown */)/* : Some */;
	}
}
/* private */class TypeRef/*  */ {
	value : string;
	constructor (value : string) {
		this/* : unknown */.value/* : unknown */ = value/* : unknown */;
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
	interfaces : List<TypeRef>;
	superTypes : List<TypeRef>;
	constructor (targetInfix : string, beforeInfix : string, name : string, typeParams : List<string>, parameters : List<Definition>, after : string, segments : List<IncompleteClassSegment>, variants : List<string>, interfaces : List<TypeRef>, superTypes : List<TypeRef>) {
		this/* : unknown */.targetInfix/* : unknown */ = targetInfix/* : unknown */;
		this/* : unknown */.beforeInfix/* : unknown */ = beforeInfix/* : unknown */;
		this/* : unknown */.name/* : unknown */ = name/* : unknown */;
		this/* : unknown */.typeParams/* : unknown */ = typeParams/* : unknown */;
		this/* : unknown */.parameters/* : unknown */ = parameters/* : unknown */;
		this/* : unknown */.after/* : unknown */ = after/* : unknown */;
		this/* : unknown */.segments/* : unknown */ = segments/* : unknown */;
		this/* : unknown */.variants/* : unknown */ = variants/* : unknown */;
		this/* : unknown */.interfaces/* : unknown */ = interfaces/* : unknown */;
		this/* : unknown */.superTypes/* : unknown */ = superTypes/* : unknown */;
	}
	static generateEnumEntries(variants : List<string>) : string {
		return variants/* : List<string> */.iterate/* : unknown */()/* : unknown */.map/* : unknown */((inner) => "\n\t" + inner/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner(",")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
	}
	createObjectType() : StructureType {
		let definitionFromSegments = this/* : StructurePrototype */.segments/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(IncompleteClassSegment/* : IncompleteClassSegment */.maybeCreateDefinition/* : unknown */)/* : unknown */.flatMap/* : unknown */(Queries/* : Queries */.fromOption/* : unknown */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
		return new StructureType(this/* : StructurePrototype */.name/* : unknown */, this/* : StructurePrototype */.variants/* : unknown */, definitionFromSegments/* : unknown */)/* : StructureType */;
	}
	public maybeCreateDefinition() : Option<Definition> {
		return new None()/* : None */;
	}
	joinTypeParams() : string {
		return this/* : StructurePrototype */.typeParams/* : unknown */()/* : unknown */.iterate/* : unknown */()/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.map/* : unknown */((inner) => "<" + inner + ">")/* : unknown */.orElse/* : unknown */("")/* : unknown */;
	}
	generateToEnum() : string {
		let variants = this/* : StructurePrototype */.variants/* : unknown */;
		let joined = generateEnumEntries/* : (arg0 : List<string>) => string */(variants/* : unknown */)/* : unknown */;
		return "enum " + this.name + "Variant" + " {" + joined + "\n}\n";
	}
}
/* private */class Cast/*  */ implements Value {
	value : Value;
	type : Type;
	constructor (value : Value, type : Type) {
		this/* : unknown */.value/* : unknown */ = value/* : unknown */;
		this/* : unknown */.type/* : unknown */ = type/* : unknown */;
	}
	public generate() : string {
		return this/* : Cast */.value/* : unknown */.generate/* : unknown */()/* : unknown */ + " as " + this/* : Cast */.type/* : unknown */.generate/* : unknown */()/* : unknown */;
	}
}
/* private */class Ok<T, X>/*  */ implements Result<T, X> {
	value : T;
	constructor (value : T) {
		this/* : unknown */.value/* : unknown */ = value/* : unknown */;
	}
	public mapValue<R>(mapper : (arg0 : T) => R) : Result<R, X> {
		return new Ok(mapper/* : (arg0 : T) => R */.apply/* : unknown */(this/* : Ok */.value/* : unknown */)/* : unknown */)/* : Ok */;
	}
	public match<R>(whenOk : (arg0 : T) => R, whenErr : (arg0 : X) => R) : R {
		return whenOk/* : (arg0 : T) => R */.apply/* : unknown */(this/* : Ok */.value/* : unknown */)/* : unknown */;
	}
}
/* private */class Err<T, X>/*  */ implements Result<T, X> {
	error : X;
	constructor (error : X) {
		this/* : unknown */.error/* : unknown */ = error/* : unknown */;
	}
	public mapValue<R>(mapper : (arg0 : T) => R) : Result<R, X> {
		return new Err(this/* : Err */.error/* : unknown */)/* : Err */;
	}
	public match<R>(whenOk : (arg0 : T) => R, whenErr : (arg0 : X) => R) : R {
		return whenErr/* : (arg0 : X) => R */.apply/* : unknown */(this/* : Err */.error/* : unknown */)/* : unknown */;
	}
}
/* private */class JVMIOError/*  */ implements IOError {
	error : /* IOException */;
	constructor (error : /* IOException */) {
		this/* : unknown */.error/* : unknown */ = error/* : unknown */;
	}
	public display() : string {
		let writer = new /* StringWriter */()/* : content-start StringWriter content-end */;
		/* this.error.printStackTrace(new PrintWriter(writer)) */;
		return writer/* : unknown */.toString/* : unknown */()/* : unknown */;
	}
}
/* private */class TupleNode/*  */ implements Value {
	values : List<Value>;
	constructor (values : List<Value>) {
		this/* : unknown */.values/* : unknown */ = values/* : unknown */;
	}
	public generate() : string {
		let joined = this/* : TupleNode */.values/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(Value/* : Value */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return "[" + joined + "]";
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
		return this/* : MapHead */.head/* : unknown */.next/* : unknown */()/* : unknown */.map/* : unknown */(this/* : MapHead */.mapper/* : unknown */)/* : unknown */;
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
		return this/* : ZipHead */.head/* : unknown */.next/* : unknown */()/* : unknown */.and/* : unknown */(this/* : ZipHead */.other/* : unknown */.next/* : unknown */)/* : unknown */;
	}
}
/* private */class EnumValue/*  */ {
	value : string;
	values : List<Value>;
	constructor (value : string, values : List<Value>) {
		this/* : unknown */.value/* : unknown */ = value/* : unknown */;
		this/* : unknown */.values/* : unknown */ = values/* : unknown */;
	}
	public generate() : string {
		let s = this/* : EnumValue */.values/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(Value/* : Value */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return this/* : EnumValue */.value/* : unknown */ + "(" + s + ")";
	}
}
/* private */class EnumValues/*  */ implements IncompleteClassSegment, ClassSegment {
	values : List<EnumValue>;
	constructor (values : List<EnumValue>) {
		this/* : unknown */.values/* : unknown */ = values/* : unknown */;
	}
	public generate() : string {
		return this/* : EnumValues */.values/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(EnumValue/* : EnumValue */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
	}
	public maybeCreateDefinition() : Option<Definition> {
		return new None()/* : None */;
	}
}
/* public static */class Strings/*  */ {
	static length(infix : string) : number;
	static isBlank(input : string) : boolean {
		return input/* : string */.isBlank/* : unknown */()/* : unknown */;
	}
}
/* private */class Primitive/*  */ implements Type {
	static Int : Primitive = new Primitive("number")/* : Primitive */;
	static String : Primitive = new Primitive("string")/* : Primitive */;
	static Boolean : Primitive = new Primitive("boolean")/* : Primitive */;
	static Unknown : Primitive = new Primitive("unknown")/* : Primitive */;
	static Void : Primitive = new Primitive("void")/* : Primitive */;
	readonly value : string;
	constructor (value : string) {
		this/* : Primitive */.value/* : unknown */ = value/* : string */;
	}
	public generate() : string {
		return this/* : Primitive */.value/* : unknown */;
	}
	public replace(mapping : Map<string, Type>) : Type {
		return this/* : Primitive */;
	}
	public findName() : string {
		return this/* : Primitive */.name/* : unknown */()/* : unknown */;
	}
}
/* private */class BooleanValue/*  */ implements Value {
	static True : BooleanValue = new BooleanValue("true")/* : BooleanValue */;
	static False : BooleanValue = new BooleanValue("false")/* : BooleanValue */;
	readonly value : string;
	constructor (value : string) {
		this/* : BooleanValue */.value/* : unknown */ = value/* : string */;
	}
	public generate() : string {
		return this/* : BooleanValue */.value/* : unknown */;
	}
}
/* public */class Main/*  */ {
	static readonly isDebugEnabled : boolean = true;
	static generatePlaceholder(input : string) : string {
		let replaced = input/* : string */.replace/* : unknown */("/*", "content-start")/* : unknown */.replace/* : unknown */("*/", "content-end")/* : unknown */;
		return "/* " + replaced + " */";
	}
	static joinValues(retainParameters : List<Definition>) : string {
		let inner = retainParameters/* : List<Definition> */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(Definition/* : Definition */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return "(" + inner + ")";
	}
	static createIndent(depth : number) : string {
		return "\n" + "\t".repeat/* : unknown */(depth/* : number */)/* : unknown */;
	}
	static createDebugString(type : Type) : string {
		if (!Main/* : Main */.isDebugEnabled/* : unknown */){
			return "";
		}
		return generatePlaceholder/* : (arg0 : string) => string */(": " + type/* : Type */.generate/* : unknown */()/* : unknown */)/* : unknown */;
	}
	static isSymbol(input : string) : boolean {
		/* for (var i = 0; i < Strings.length(input); i++) */{
			let c = input/* : string */.charAt/* : unknown */(/* i */)/* : unknown */;
			if (/* Character */.isLetter/* : unknown */(c/* : unknown */)/* : unknown */ || /*  */(/* i != 0  */ && /* Character */.isDigit/* : unknown */(c/* : unknown */)/* : unknown */)/* : unknown */){
				/* continue */;
			}
			return false;
		}
		return true;
	}
	static parseWhitespace(input : string, state : CompileState) : Option<[CompileState, Whitespace]> {
		if (Strings/* : Strings */.isBlank/* : unknown */(input/* : string */)/* : unknown */){
			return new Some([state/* : CompileState */, new Whitespace()/* : Whitespace */])/* : Some */;
		}
		return new None()/* : None */;
	}
	static retainObjectRefType(type : Type) : Option<ObjectRefType> {
		if (type/* : Type */._UnknownVariant/* : unknown */ === UnknownVariant.ObjectRefType/* : unknown */){
			let objectRefType : ObjectRefType = type/* : Type */ as ObjectRefType;
			return new Some(objectRefType/* : ObjectRefType */)/* : Some */;
		}
		else {
			return new None()/* : None */;
		}
	}
	public main() : void {
		let parent = this/* : Main */.findRoot/* : unknown */()/* : unknown */;
		let source = parent/* : unknown */.resolve/* : unknown */("Main.java")/* : unknown */;
		let target = parent/* : unknown */.resolve/* : unknown */("main.ts")/* : unknown */;
		/* source.readString()
                .mapValue(this::compile)
                .match(target::writeString, Some::new)
                .or(this::executeTSC)
                .ifPresent(error -> System.err.println(error.display())) */;
	}
	findRoot() : Path;
	executeTSC() : Option<IOError>;
	compile(input : string) : string {
		let state = CompileState/* : CompileState */.createInitial/* : unknown */()/* : unknown */;
		let parsed = this/* : Main */.parseStatements/* : unknown */(state/* : unknown */, input/* : string */, this/* : Main */.compileRootSegment/* : unknown */)/* : unknown */;
		let joined = parsed/* : unknown */.left/* : unknown */()/* : unknown */.structures/* : unknown */.iterate/* : unknown */()/* : unknown */.collect/* : unknown */(Joiner/* : Joiner */.empty/* : unknown */()/* : unknown */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return joined/* : unknown */ + this/* : Main */.generateStatements/* : unknown */(parsed/* : unknown */.right/* : unknown */()/* : unknown */)/* : unknown */;
	}
	generateStatements(statements : List<string>) : string {
		return this/* : Main */.generateAll/* : unknown */(this/* : Main */.mergeStatements/* : unknown */, statements/* : List<string> */)/* : unknown */;
	}
	parseStatements<T>(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => [CompileState, T]) : [CompileState, List<T>] {
		return this/* : Main */.parseAllWithIndices/* : unknown */(state/* : CompileState */, input/* : string */, this/* : Main */.foldStatementChar/* : unknown */, (state3, tuple) => new Some(mapper/* : (arg0 : CompileState, arg1 : string) => [CompileState, T] */.apply/* : unknown */(state3/* : unknown */, tuple/* : unknown */.right/* : unknown */()/* : unknown */)/* : unknown */)/* : Some */)/* : unknown */.orElseGet/* : unknown */(() => [state/* : CompileState */, Lists/* : Lists */.empty/* : unknown */()/* : unknown */])/* : unknown */;
	}
	generateAll(merger : (arg0 : string, arg1 : string) => string, elements : List<string>) : string {
		return elements/* : List<string> */.iterate/* : unknown */()/* : unknown */.fold/* : unknown */("", merger/* : (arg0 : string, arg1 : string) => string */)/* : unknown */;
	}
	parseAllWithIndices<T>(state : CompileState, input : string, folder : (arg0 : DivideState, arg1 : string) => DivideState, mapper : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]>) : Option<[CompileState, List<T>]> {
		let stringList = this/* : Main */.divideAll/* : unknown */(input/* : string */, folder/* : (arg0 : DivideState, arg1 : string) => DivideState */)/* : unknown */;
		return this/* : Main */.mapUsingState/* : unknown */(state/* : CompileState */, stringList/* : unknown */, mapper/* : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]> */)/* : unknown */;
	}
	mapUsingState<T, R>(state : CompileState, elements : List<T>, mapper : (arg0 : CompileState, arg1 : [number, T]) => Option<[CompileState, R]>) : Option<[CompileState, List<R>]> {
		return elements/* : List<T> */.iterateWithIndices/* : unknown */()/* : unknown */.fold/* : unknown */(new Some([state/* : CompileState */, Lists/* : Lists */.empty/* : unknown */()/* : unknown */])/* : Some */, this/* : Main */.getOptionTuple2OptionBiFunction/* : unknown */(mapper/* : (arg0 : CompileState, arg1 : [number, T]) => Option<[CompileState, R]> */)/* : unknown */)/* : unknown */;
	}
	getOptionTuple2OptionBiFunction<T, R>(mapper : (arg0 : CompileState, arg1 : [number, T]) => Option<[CompileState, R]>) : (arg0 : Option<[CompileState, List<R>]>, arg1 : [number, T]) => Option<[CompileState, List<R>]> {
		return (maybeCurrent, entry) => maybeCurrent/* : unknown */.flatMap/* : unknown */((current) => {
			let currentState = current/* : unknown */.left/* : unknown */()/* : unknown */;
			let currentList = current/* : unknown */.right/* : unknown */()/* : unknown */;
			return mapper/* : (arg0 : CompileState, arg1 : [number, T]) => Option<[CompileState, R]> */.apply/* : unknown */(currentState/* : unknown */, entry/* : unknown */)/* : unknown */.map/* : unknown */((applied) => {
				return [applied/* : unknown */.left/* : unknown */()/* : unknown */, currentList/* : unknown */.addLast/* : unknown */(applied/* : unknown */.right/* : unknown */()/* : unknown */)/* : unknown */];
			})/* : unknown */;
		})/* : unknown */;
	}
	mergeStatements(cache : string, statement : string) : string {
		return cache/* : string */ + statement/* : string */;
	}
	divideAll(input : string, folder : (arg0 : DivideState, arg1 : string) => DivideState) : List<string> {
		let current = DivideState/* : DivideState */.createInitial/* : unknown */(input/* : string */)/* : unknown */;
		while (true){
			let maybePopped = current/* : unknown */.pop/* : unknown */()/* : unknown */.map/* : unknown */((tuple) => this/* : Main */.foldDecorated/* : unknown */(folder/* : (arg0 : DivideState, arg1 : string) => DivideState */, tuple/* : unknown */)/* : unknown */)/* : unknown */;
			if (maybePopped/* : unknown */.isPresent/* : unknown */()/* : unknown */){
				current/* : unknown */ = maybePopped/* : unknown */.orElse/* : unknown */(current/* : unknown */)/* : unknown */;
			}
			else {
				/* break */;
			}
		}
		return current/* : unknown */.advance/* : unknown */()/* : unknown */.segments/* : unknown */;
	}
	foldDecorated(folder : (arg0 : DivideState, arg1 : string) => DivideState, tuple : [string, DivideState]) : DivideState {
		return this/* : Main */.foldSingleQuotes/* : unknown */(tuple/* : [string, DivideState] */)/* : unknown */.or/* : unknown */(() => this/* : Main */.foldDoubleQuotes/* : unknown */(tuple/* : [string, DivideState] */)/* : unknown */)/* : unknown */.orElseGet/* : unknown */(() => folder/* : (arg0 : DivideState, arg1 : string) => DivideState */.apply/* : unknown */(tuple/* : [string, DivideState] */.right/* : unknown */()/* : unknown */, tuple/* : [string, DivideState] */.left/* : unknown */()/* : unknown */)/* : unknown */)/* : unknown */;
	}
	foldDoubleQuotes(tuple : [string, DivideState]) : Option<DivideState> {
		if (tuple/* : [string, DivideState] */.left/* : unknown */()/* : unknown */ === "\""){
			let current = tuple/* : [string, DivideState] */.right/* : unknown */()/* : unknown */.append/* : unknown */(tuple/* : [string, DivideState] */.left/* : unknown */()/* : unknown */)/* : unknown */;
			while (true){
				let maybePopped = current/* : unknown */.popAndAppendToTuple/* : unknown */()/* : unknown */;
				if (maybePopped/* : unknown */.isEmpty/* : unknown */()/* : unknown */){
					/* break */;
				}
				let popped = maybePopped/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */;
				current/* : unknown */ = popped/* : unknown */.right/* : unknown */()/* : unknown */;
				if (popped/* : unknown */.left/* : unknown */()/* : unknown */ === "\\"){
					current/* : unknown */ = current/* : unknown */.popAndAppendToOption/* : unknown */()/* : unknown */.orElse/* : unknown */(current/* : unknown */)/* : unknown */;
				}
				if (popped/* : unknown */.left/* : unknown */()/* : unknown */ === "\""){
					/* break */;
				}
			}
			return new Some(current/* : unknown */)/* : Some */;
		}
		return new None()/* : None */;
	}
	foldSingleQuotes(tuple : [string, DivideState]) : Option<DivideState> {
		if (/* tuple.left() != '\'' */){
			return new None()/* : None */;
		}
		let appended = tuple/* : [string, DivideState] */.right/* : unknown */()/* : unknown */.append/* : unknown */(tuple/* : [string, DivideState] */.left/* : unknown */()/* : unknown */)/* : unknown */;
		return appended/* : unknown */.popAndAppendToTuple/* : unknown */()/* : unknown */.map/* : unknown */(this/* : Main */.foldEscaped/* : unknown */)/* : unknown */.flatMap/* : unknown */(DivideState/* : DivideState */.popAndAppendToOption/* : unknown */)/* : unknown */;
	}
	foldEscaped(escaped : [string, DivideState]) : DivideState {
		if (escaped/* : [string, DivideState] */.left/* : unknown */()/* : unknown */ === "\\"){
			return escaped/* : [string, DivideState] */.right/* : unknown */()/* : unknown */.popAndAppendToOption/* : unknown */()/* : unknown */.orElse/* : unknown */(escaped/* : [string, DivideState] */.right/* : unknown */()/* : unknown */)/* : unknown */;
		}
		return escaped/* : [string, DivideState] */.right/* : unknown */()/* : unknown */;
	}
	foldStatementChar(state : DivideState, c : string) : DivideState {
		let append = state/* : DivideState */.append/* : unknown */(c/* : string */)/* : unknown */;
		if (c/* : string */ === ";" && append/* : unknown */.isLevel/* : unknown */()/* : unknown */){
			return append/* : unknown */.advance/* : unknown */()/* : unknown */;
		}
		if (c/* : string */ === "}" && append/* : unknown */.isShallow/* : unknown */()/* : unknown */){
			return append/* : unknown */.advance/* : unknown */()/* : unknown */.exit/* : unknown */()/* : unknown */;
		}
		if (c/* : string */ === "{" || c/* : string */ === "("){
			return append/* : unknown */.enter/* : unknown */()/* : unknown */;
		}
		if (c/* : string */ === "}" || c/* : string */ === ")"){
			return append/* : unknown */.exit/* : unknown */()/* : unknown */;
		}
		return append/* : unknown */;
	}
	compileRootSegment(state : CompileState, input : string) : [CompileState, string] {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.startsWith/* : unknown */("package ")/* : unknown */ || stripped/* : unknown */.startsWith/* : unknown */("import ")/* : unknown */){
			return [state/* : CompileState */, ""];
		}
		return this/* : Main */.parseClass/* : unknown */(stripped/* : unknown */, state/* : CompileState */)/* : unknown */.flatMap/* : unknown */((tuple) => this/* : Main */.completeClassSegment/* : unknown */(tuple/* : unknown */.left/* : unknown */()/* : unknown */, tuple/* : unknown */.right/* : unknown */()/* : unknown */)/* : unknown */)/* : unknown */.map/* : unknown */((tuple0) => [tuple0/* : unknown */.left/* : unknown */()/* : unknown */, tuple0/* : unknown */.right/* : unknown */()/* : unknown */.generate/* : unknown */()/* : unknown */])/* : unknown */.orElseGet/* : unknown */(() => [state/* : CompileState */, generatePlaceholder/* : (arg0 : string) => string */(stripped/* : unknown */)/* : unknown */])/* : unknown */;
	}
	parseClass(stripped : string, state : CompileState) : Option<[CompileState, IncompleteClassSegment]> {
		return this/* : Main */.parseStructure/* : unknown */(stripped/* : string */, "class ", "class ", state/* : CompileState */)/* : unknown */;
	}
	parseStructure(stripped : string, sourceInfix : string, targetInfix : string, state : CompileState) : Option<[CompileState, IncompleteClassSegment]> {
		return this/* : Main */.first/* : unknown */(stripped/* : string */, sourceInfix/* : string */, (beforeInfix, right) => {
			return this/* : Main */.first/* : unknown */(right/* : unknown */, "{", (beforeContent, withEnd) => {
				return this/* : Main */.suffix/* : unknown */(withEnd/* : unknown */.strip/* : unknown */()/* : unknown */, "}", (content1) => {
					return this/* : Main */.last/* : unknown */(beforeInfix/* : unknown */.strip/* : unknown */()/* : unknown */, "\n", (annotationsString, s2) => {
						let annotations = this/* : Main */.parseAnnotations/* : unknown */(annotationsString/* : unknown */)/* : unknown */;
						return this/* : Main */.parseStructureWithMaybePermits/* : unknown */(targetInfix/* : string */, state/* : CompileState */, s2/* : unknown */, beforeContent/* : unknown */, content1/* : unknown */, annotations/* : unknown */)/* : unknown */;
					})/* : unknown */.or/* : unknown */(() => {
						return this/* : Main */.parseStructureWithMaybePermits/* : unknown */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : unknown */, beforeContent/* : unknown */, content1/* : unknown */, Lists/* : Lists */.empty/* : unknown */()/* : unknown */)/* : unknown */;
					})/* : unknown */;
				})/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	parseAnnotations(annotationsString : string) : List<string> {
		return this/* : Main */.divideAll/* : unknown */(annotationsString/* : string */.strip/* : unknown */()/* : unknown */, (state1, c) => this/* : Main */.foldByDelimiter/* : unknown */(state1/* : unknown */, c/* : unknown */, "\n")/* : unknown */)/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(/* String */.strip/* : unknown */)/* : unknown */.filter/* : unknown */((value) => !value/* : unknown */.isEmpty/* : unknown */()/* : unknown */)/* : unknown */.map/* : unknown */((value) => value/* : unknown */.substring/* : unknown */(1/* : number */)/* : unknown */)/* : unknown */.map/* : unknown */(/* String */.strip/* : unknown */)/* : unknown */.filter/* : unknown */((value) => !value/* : unknown */.isEmpty/* : unknown */()/* : unknown */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
	}
	foldByDelimiter(state1 : DivideState, c : string, delimiter : string) : DivideState {
		if (c/* : string */ === delimiter/* : string */){
			return state1/* : DivideState */.advance/* : unknown */()/* : unknown */;
		}
		return state1/* : DivideState */.append/* : unknown */(c/* : string */)/* : unknown */;
	}
	parseStructureWithMaybePermits(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string, annotations : List<string>) : Option<[CompileState, IncompleteClassSegment]> {
		return this/* : Main */.last/* : unknown */(beforeContent/* : string */, " permits ", (s, s2) => {
			let variants = this/* : Main */.divideAll/* : unknown */(s2/* : unknown */, this/* : Main */.foldValueChar/* : unknown */)/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(/* String */.strip/* : unknown */)/* : unknown */.filter/* : unknown */((value) => !value/* : unknown */.isEmpty/* : unknown */()/* : unknown */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
			return this/* : Main */.parseStructureWithMaybeImplements/* : unknown */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : string */, s/* : unknown */, content1/* : string */, variants/* : unknown */, annotations/* : List<string> */)/* : unknown */;
		})/* : unknown */.or/* : unknown */(() => this/* : Main */.parseStructureWithMaybeImplements/* : unknown */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : string */, beforeContent/* : string */, content1/* : string */, Lists/* : Lists */.empty/* : unknown */()/* : unknown */, annotations/* : List<string> */)/* : unknown */)/* : unknown */;
	}
	parseStructureWithMaybeImplements(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string, variants : List<string>, annotations : List<string>) : Option<[CompileState, IncompleteClassSegment]> {
		return this/* : Main */.first/* : unknown */(beforeContent/* : string */, " implements ", (s, s2) => {
			let stringList = this/* : Main */.parseTypeRefs/* : unknown */(s2/* : unknown */)/* : unknown */;
			return this/* : Main */.parseStructureWithMaybeExtends/* : unknown */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : string */, s/* : unknown */, content1/* : string */, variants/* : List<string> */, annotations/* : List<string> */, stringList/* : unknown */)/* : unknown */;
		})/* : unknown */.or/* : unknown */(() => this/* : Main */.parseStructureWithMaybeExtends/* : unknown */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : string */, beforeContent/* : string */, content1/* : string */, variants/* : List<string> */, annotations/* : List<string> */, Lists/* : Lists */.empty/* : unknown */()/* : unknown */)/* : unknown */)/* : unknown */;
	}
	parseTypeRefs(s2 : string) : List<TypeRef> {
		return this/* : Main */.divideAll/* : unknown */(s2/* : string */, this/* : Main */.foldValueChar/* : unknown */)/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(/* String */.strip/* : unknown */)/* : unknown */.filter/* : unknown */((value) => !value/* : unknown */.isEmpty/* : unknown */()/* : unknown */)/* : unknown */.map/* : unknown */(TypeRef/* : TypeRef */.new/* : unknown */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
	}
	parseStructureWithMaybeExtends(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string, variants : List<string>, annotations : List<string>, interfaces : List<TypeRef>) : Option<[CompileState, IncompleteClassSegment]> {
		return this/* : Main */.first/* : unknown */(beforeContent/* : string */, " extends ", (s, s2) => this/* : Main */.parseStructureWithMaybeParams/* : unknown */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : string */, s/* : unknown */, content1/* : string */, variants/* : List<string> */, annotations/* : List<string> */, this/* : Main */.parseTypeRefs/* : unknown */(s2/* : unknown */)/* : unknown */, interfaces/* : List<TypeRef> */)/* : unknown */)/* : unknown */.or/* : unknown */(() => this/* : Main */.parseStructureWithMaybeParams/* : unknown */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : string */, beforeContent/* : string */, content1/* : string */, variants/* : List<string> */, annotations/* : List<string> */, Lists/* : Lists */.empty/* : unknown */()/* : unknown */, interfaces/* : List<TypeRef> */)/* : unknown */)/* : unknown */;
	}
	parseStructureWithMaybeParams(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string, variants : List<string>, annotations : List<string>, superTypes : List<TypeRef>, interfaces : List<TypeRef>) : Option<[CompileState, IncompleteClassSegment]> {
		return this/* : Main */.suffix/* : unknown */(beforeContent/* : string */.strip/* : unknown */()/* : unknown */, ")", (s) => {
			return this/* : Main */.first/* : unknown */(s/* : unknown */, "(", (s1, s2) => {
				let parsed = this/* : Main */.parseParameters/* : unknown */(state/* : CompileState */, s2/* : unknown */)/* : unknown */;
				return this/* : Main */.parseStructureWithMaybeTypeParams/* : unknown */(targetInfix/* : string */, parsed/* : unknown */.left/* : unknown */()/* : unknown */, beforeInfix/* : string */, s1/* : unknown */, content1/* : string */, parsed/* : unknown */.right/* : unknown */()/* : unknown */, variants/* : List<string> */, annotations/* : List<string> */, interfaces/* : List<TypeRef> */, superTypes/* : List<TypeRef> */)/* : unknown */;
			})/* : unknown */;
		})/* : unknown */.or/* : unknown */(() => {
			return this/* : Main */.parseStructureWithMaybeTypeParams/* : unknown */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : string */, beforeContent/* : string */, content1/* : string */, Lists/* : Lists */.empty/* : unknown */()/* : unknown */, variants/* : List<string> */, annotations/* : List<string> */, interfaces/* : List<TypeRef> */, superTypes/* : List<TypeRef> */)/* : unknown */;
		})/* : unknown */;
	}
	parseStructureWithMaybeTypeParams(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string, params : List<Parameter>, variants : List<string>, annotations : List<string>, interfaces : List<TypeRef>, maybeSuperType : List<TypeRef>) : Option<[CompileState, IncompleteClassSegment]> {
		return this/* : Main */.first/* : unknown */(beforeContent/* : string */, "<", (name, withTypeParams) => {
			return this/* : Main */.first/* : unknown */(withTypeParams/* : unknown */, ">", (typeParamsString, afterTypeParams) => {
				let readonly mapper : (arg0 : CompileState, arg1 : string) => [CompileState, string] = (state1, s) => [state1/* : unknown */, s/* : unknown */.strip/* : unknown */()/* : unknown */];
				let typeParams = this/* : Main */.parseValuesOrEmpty/* : unknown */(state/* : CompileState */, typeParamsString/* : unknown */, (state1, s) => new Some(mapper/* : (arg0 : CompileState, arg1 : string) => [CompileState, string] */.apply/* : unknown */(state1/* : unknown */, s/* : unknown */)/* : unknown */)/* : Some */)/* : unknown */;
				return this/* : Main */.assembleStructure/* : unknown */(typeParams/* : unknown */.left/* : unknown */()/* : unknown */, targetInfix/* : string */, annotations/* : List<string> */, beforeInfix/* : string */, name/* : unknown */, content1/* : string */, typeParams/* : unknown */.right/* : unknown */()/* : unknown */, afterTypeParams/* : unknown */, params/* : List<Parameter> */, variants/* : List<string> */, interfaces/* : List<TypeRef> */, maybeSuperType/* : List<TypeRef> */)/* : unknown */;
			})/* : unknown */;
		})/* : unknown */.or/* : unknown */(() => {
			return this/* : Main */.assembleStructure/* : unknown */(state/* : CompileState */, targetInfix/* : string */, annotations/* : List<string> */, beforeInfix/* : string */, beforeContent/* : string */, content1/* : string */, Lists/* : Lists */.empty/* : unknown */()/* : unknown */, "", params/* : List<Parameter> */, variants/* : List<string> */, interfaces/* : List<TypeRef> */, maybeSuperType/* : List<TypeRef> */)/* : unknown */;
		})/* : unknown */;
	}
	assembleStructure(state : CompileState, targetInfix : string, annotations : List<string>, beforeInfix : string, rawName : string, content : string, typeParams : List<string>, after : string, rawParameters : List<Parameter>, variants : List<string>, interfaces : List<TypeRef>, maybeSuperType : List<TypeRef>) : Option<[CompileState, IncompleteClassSegment]> {
		let name = rawName/* : string */.strip/* : unknown */()/* : unknown */;
		if (!isSymbol/* : (arg0 : string) => boolean */(name/* : unknown */)/* : unknown */){
			return new None()/* : None */;
		}
		if (annotations/* : List<string> */.contains/* : unknown */("Actual")/* : unknown */){
			return new Some([state/* : CompileState */, new Whitespace()/* : Whitespace */])/* : Some */;
		}
		let segmentsTuple = this/* : Main */.parseStatements/* : unknown */(state/* : CompileState */.pushStructName/* : unknown */([name/* : unknown */, variants/* : List<string> */])/* : unknown */.withTypeParams/* : unknown */(typeParams/* : List<string> */)/* : unknown */, content/* : string */, (state0, input) => this/* : Main */.parseClassSegment/* : unknown */(state0/* : unknown */, input/* : unknown */, 1/* : number */)/* : unknown */)/* : unknown */;
		let segmentsState = segmentsTuple/* : unknown */.left/* : unknown */()/* : unknown */;
		let segments = segmentsTuple/* : unknown */.right/* : unknown */()/* : unknown */;
		let parameters = this/* : Main */.retainDefinitions/* : unknown */(rawParameters/* : List<Parameter> */)/* : unknown */;
		let prototype = new StructurePrototype(targetInfix/* : string */, beforeInfix/* : string */, name/* : unknown */, typeParams/* : List<string> */, parameters/* : unknown */, after/* : string */, segments/* : unknown */, variants/* : List<string> */, interfaces/* : List<TypeRef> */, maybeSuperType/* : List<TypeRef> */)/* : StructurePrototype */;
		return new Some([segmentsState/* : unknown */.addType/* : unknown */(prototype/* : unknown */.createObjectType/* : unknown */()/* : unknown */)/* : unknown */, prototype/* : unknown */])/* : Some */;
	}
	completeStructure(state : CompileState, prototype : StructurePrototype) : Option<[CompileState, ClassSegment]> {
		let thisType = prototype/* : StructurePrototype */.createObjectType/* : unknown */()/* : unknown */;
		let withThis = state/* : CompileState */.enterDefinitions/* : unknown */()/* : unknown */.define/* : unknown */(Definition/* : Definition */.createSimpleDefinition/* : unknown */("this", thisType/* : unknown */)/* : unknown */)/* : unknown */;
		return this/* : Main */.resolveTypeRefs/* : unknown */(withThis/* : unknown */, prototype/* : StructurePrototype */.interfaces/* : unknown */)/* : unknown */.flatMap/* : unknown */((interfacesTuple) => {
			return this/* : Main */.resolveTypeRefs/* : unknown */(interfacesTuple/* : unknown */.left/* : unknown */()/* : unknown */, prototype/* : StructurePrototype */.superTypes/* : unknown */)/* : unknown */.flatMap/* : unknown */((superTypesTuple) => {
				let interfaces = interfacesTuple/* : unknown */.right/* : unknown */()/* : unknown */;
				let superTypes = superTypesTuple/* : unknown */.right/* : unknown */()/* : unknown */;
				let bases = this/* : Main */.resolveBaseTypes/* : unknown */(interfaces/* : unknown */)/* : unknown */.addAllLast/* : unknown */(this/* : Main */.resolveBaseTypes/* : unknown */(superTypes/* : unknown */)/* : unknown */)/* : unknown */;
				let left = superTypesTuple/* : unknown */.left/* : unknown */()/* : unknown */;
				let variantsSuper = this/* : Main */.findBaseNamesOfVariants/* : unknown */(left/* : unknown */, bases/* : unknown */, prototype/* : StructurePrototype */.name/* : unknown */)/* : unknown */;
				return this/* : Main */.mapUsingState/* : unknown */(left/* : unknown */, prototype/* : StructurePrototype */.segments/* : unknown */()/* : unknown */, this/* : Main */.createClassSegmentRule/* : unknown */()/* : unknown */)/* : unknown */.map/* : unknown */(this/* : Main */.completeStructureWithStatements/* : unknown */(prototype/* : StructurePrototype */, variantsSuper/* : unknown */, thisType/* : unknown */, interfaces/* : unknown */)/* : unknown */)/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	completeStructureWithStatements(prototype : StructurePrototype, variantsSuper : List<string>, thisType : StructureType, interfaces : List<Type>) : (arg0 : [CompileState, List<ClassSegment>]) => [CompileState, ClassSegment] {
		/* return oldStatementsTuple -> */{
			let exited = /* oldStatementsTuple */.left/* : unknown */()/* : unknown */.exitDefinitions/* : unknown */()/* : unknown */;
			let oldStatements = /* oldStatementsTuple */.right/* : unknown */()/* : unknown */;
			let withEnumCategoriesDefinedTuple = this/* : Main */.defineEnumCategories/* : unknown */(exited/* : unknown */, oldStatements/* : unknown */, prototype/* : StructurePrototype */.name/* : unknown */, prototype/* : StructurePrototype */.variants/* : unknown */, prototype/* : StructurePrototype */.generateToEnum/* : unknown */()/* : unknown */)/* : unknown */;
			let withEnumCategoriesDefinedState = withEnumCategoriesDefinedTuple/* : unknown */.left/* : unknown */()/* : unknown */;
			let withEnumCategoriesDefined = withEnumCategoriesDefinedTuple/* : unknown */.right/* : unknown */()/* : unknown */;
			let withEnumCategoriesImplemented = this/* : Main */.implementEnumCategories/* : unknown */(prototype/* : StructurePrototype */.name/* : unknown */, variantsSuper/* : List<string> */, withEnumCategoriesDefined/* : unknown */)/* : unknown */;
			let withEnumValues = this/* : Main */.implementEnumValues/* : unknown */(withEnumCategoriesImplemented/* : unknown */, thisType/* : StructureType */)/* : unknown */;
			let withConstructor = this/* : Main */.defineConstructor/* : unknown */(withEnumValues/* : unknown */, prototype/* : StructurePrototype */.parameters/* : unknown */()/* : unknown */)/* : unknown */;
			let generatedSegments = this/* : Main */.joinSegments/* : unknown */(withConstructor/* : unknown */)/* : unknown */;
			let joinedTypeParams = prototype/* : StructurePrototype */.joinTypeParams/* : unknown */()/* : unknown */;
			let interfacesJoined = this/* : Main */.joinInterfaces/* : unknown */(interfaces/* : List<Type> */)/* : unknown */;
			let generatedSuperType = this/* : Main */.joinSuperTypes/* : unknown */(withEnumCategoriesDefinedState/* : unknown */, prototype/* : StructurePrototype */)/* : unknown */;
			let generated = generatePlaceholder/* : (arg0 : string) => string */(prototype/* : StructurePrototype */.beforeInfix/* : unknown */()/* : unknown */.strip/* : unknown */()/* : unknown */)/* : unknown */ + prototype/* : StructurePrototype */.targetInfix/* : unknown */()/* : unknown */ + prototype/* : StructurePrototype */.name/* : unknown */()/* : unknown */ + joinedTypeParams/* : unknown */ + generatePlaceholder/* : (arg0 : string) => string */(prototype/* : StructurePrototype */.after/* : unknown */()/* : unknown */)/* : unknown */ + generatedSuperType/* : unknown */ + interfacesJoined/* : unknown */ + " {" + generatedSegments + "\n}\n";
			let compileState = withEnumCategoriesDefinedState/* : unknown */.popStructName/* : unknown */()/* : unknown */;
			let definedState = compileState/* : unknown */.addStructure/* : unknown */(generated/* : unknown */)/* : unknown */;
			return [definedState/* : unknown */, new Whitespace()/* : Whitespace */];
		}
		/*  */;
	}
	createClassSegmentRule() : (arg0 : CompileState, arg1 : [number, IncompleteClassSegment]) => Option<[CompileState, ClassSegment]> {
		return (state1, entry) => this/* : Main */.completeClassSegment/* : unknown */(state1/* : unknown */, entry/* : unknown */.right/* : unknown */()/* : unknown */)/* : unknown */;
	}
	resolveTypeRefs(state : CompileState, refs : List<TypeRef>) : Option<[CompileState, List<Type>]> {
		return this/* : Main */.mapUsingState/* : unknown */(state/* : CompileState */, refs/* : List<TypeRef> */, (state2, tuple) => this/* : Main */.parseType/* : unknown */(state2/* : unknown */, tuple/* : unknown */.right/* : unknown */()/* : unknown */.value/* : unknown */)/* : unknown */)/* : unknown */;
	}
	joinSuperTypes(state : CompileState, prototype : StructurePrototype) : string {
		return prototype/* : StructurePrototype */.superTypes/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */((value) => state/* : CompileState */.resolveType/* : unknown */(value/* : unknown */.value/* : unknown */)/* : unknown */)/* : unknown */.flatMap/* : unknown */(Queries/* : Queries */.fromOption/* : unknown */)/* : unknown */.map/* : unknown */(Type/* : Type */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.map/* : unknown */((generated) => " extends " + generated/* : unknown */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
	}
	implementEnumValues(withConstructor : List<ClassSegment>, thisType : StructureType) : List<ClassSegment> {
		return withConstructor/* : List<ClassSegment> */.iterate/* : unknown */()/* : unknown */.flatMap/* : unknown */((segment) => this/* : Main */.flattenEnumValues/* : unknown */(segment/* : unknown */, thisType/* : StructureType */)/* : unknown */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
	}
	defineEnumCategories(state : CompileState, segments : List<ClassSegment>, name : string, variants : List<string>, enumGenerated : string) : [CompileState, List<ClassSegment>] {
		if (variants/* : List<string> */.isEmpty/* : unknown */()/* : unknown */){
			return [state/* : CompileState */, segments/* : List<ClassSegment> */];
		}
		let enumState = state/* : CompileState */.addStructure/* : unknown */(enumGenerated/* : string */)/* : unknown */;
		let enumType = new ObjectRefType(name/* : string */ + "Variant")/* : ObjectRefType */;
		let enumDefinition = this/* : Main */.createVariantDefinition/* : unknown */(enumType/* : unknown */)/* : unknown */;
		return [enumState/* : unknown */, segments/* : List<ClassSegment> */.addFirst/* : unknown */(new Statement(1/* : number */, enumDefinition/* : unknown */)/* : Statement */)/* : unknown */];
	}
	implementEnumCategories(name : string, variantsBases : List<string>, oldStatements : List<ClassSegment>) : List<ClassSegment> {
		return variantsBases/* : List<string> */.iterate/* : unknown */()/* : unknown */.fold/* : unknown */(oldStatements/* : List<ClassSegment> */, (classSegmentList, superType) => {
			let variantTypeName = superType/* : unknown */ + "Variant";
			let variantType = new ObjectRefType(variantTypeName/* : unknown */)/* : ObjectRefType */;
			let definition = this/* : Main */.createVariantDefinition/* : unknown */(variantType/* : unknown */)/* : unknown */;
			let source = new SymbolValue(variantTypeName/* : unknown */ + "." + name/* : string */, variantType/* : unknown */)/* : SymbolValue */;
			let initialization = new FieldInitialization(definition/* : unknown */, source/* : unknown */)/* : FieldInitialization */;
			return classSegmentList/* : unknown */.addFirst/* : unknown */(new Statement(1/* : number */, initialization/* : unknown */)/* : Statement */)/* : unknown */;
		})/* : unknown */;
	}
	findBaseNamesOfVariants(state : CompileState, refs : List<ObjectRefType>, name : string) : List<string> {
		return refs/* : List<ObjectRefType> */.iterate/* : unknown */()/* : unknown */.map/* : unknown */((base) => state/* : CompileState */.findStructure/* : unknown */(base/* : unknown */.name/* : unknown */)/* : unknown */)/* : unknown */.flatMap/* : unknown */(Queries/* : Queries */.fromOption/* : unknown */)/* : unknown */.filter/* : unknown */((type) => type/* : unknown */.hasVariant/* : unknown */(name/* : string */)/* : unknown */)/* : unknown */.map/* : unknown */(StructureType/* : StructureType */.name/* : unknown */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
	}
	resolveBaseTypes(interfaces : List<Type>) : List<ObjectRefType> {
		return interfaces/* : List<Type> */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(Main/* : Main */.retainObjectRefType/* : unknown */)/* : unknown */.flatMap/* : unknown */(Queries/* : Queries */.fromOption/* : unknown */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
	}
	joinSegments(segmentsWithMaybeConstructor : List<ClassSegment>) : string {
		return segmentsWithMaybeConstructor/* : List<ClassSegment> */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(ClassSegment/* : ClassSegment */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(Joiner/* : Joiner */.empty/* : unknown */()/* : unknown */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
	}
	joinInterfaces(interfaces : List<Type>) : string {
		return interfaces/* : List<Type> */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(Type/* : Type */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.map/* : unknown */((inner) => " implements " + inner/* : unknown */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
	}
	flattenEnumValues(segment : ClassSegment, thisType : StructureType) : Query<ClassSegment> {
		if (segment/* : ClassSegment */._UnknownVariant/* : unknown */ === UnknownVariant.EnumValues/* : unknown */){
			let enumValues : EnumValues = segment/* : ClassSegment */ as EnumValues;
			return enumValues/* : EnumValues */.values/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */((enumValue) => {
				let definition = new Definition(Lists/* : Lists */.empty/* : unknown */()/* : unknown */, Lists/* : Lists */.of/* : unknown */("static")/* : unknown */, enumValue/* : unknown */.value/* : unknown */, thisType/* : StructureType */, Lists/* : Lists */.empty/* : unknown */()/* : unknown */)/* : Definition */;
				return new Statement(1/* : number */, new FieldInitialization(definition/* : unknown */, new Invokable(new ConstructionCaller(thisType/* : StructureType */)/* : ConstructionCaller */, enumValue/* : unknown */.values/* : unknown */, thisType/* : StructureType */)/* : Invokable */)/* : FieldInitialization */)/* : Statement */;
			})/* : unknown */;
		}
		return Queries/* : Queries */.from/* : unknown */(segment/* : ClassSegment */)/* : unknown */;
	}
	createVariantDefinition(type : ObjectRefType) : Definition {
		return Definition/* : Definition */.createSimpleDefinition/* : unknown */("_" + type/* : ObjectRefType */.name/* : unknown */, type/* : ObjectRefType */)/* : unknown */;
	}
	defineConstructor(segments : List<ClassSegment>, parameters : List<Definition>) : List<ClassSegment> {
		if (parameters/* : List<Definition> */.isEmpty/* : unknown */()/* : unknown */){
			return segments/* : List<ClassSegment> */;
		}
		let definitions : List<ClassSegment> = parameters/* : List<Definition> */.iterate/* : unknown */()/* : unknown */./* : unknown */ < /* ClassSegment>map */((definition) => new Statement(1/* : number */, definition/* : unknown */)/* : Statement */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
		let collect = /* parameters.iterate()
                .map(definition  */ - /* > {
                    var destination = new DataAccess(new SymbolValue("this", Primitive.Unknown), definition.findName(), Primitive.Unknown);
                    return new Assignment */(/* destination */, /*  new SymbolValue(definition.findName(), Primitive.Unknown));
                } */)/* : unknown */./* : unknown */ < /* FunctionSegment>map */((assignment) => new Statement(2/* : number */, assignment/* : unknown */)/* : Statement */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
		let func = new FunctionNode(1/* : number */, new ConstructorHeader()/* : ConstructorHeader */, parameters/* : List<Definition> */, new Some(collect/* : unknown */)/* : Some */)/* : FunctionNode */;
		return segments/* : List<ClassSegment> */.addFirst/* : unknown */(func/* : unknown */)/* : unknown */.addAllFirst/* : unknown */(definitions/* : List<ClassSegment> */)/* : unknown */;
	}
	completeClassSegment(state1 : CompileState, segment : IncompleteClassSegment) : Option<[CompileState, ClassSegment]> {
		/* return switch (segment) */{
			/* case IncompleteClassSegmentWrapper wrapper -> new Some<>(new Tuple2Impl<>(state1, wrapper.segment)) */;
			/* case MethodPrototype methodPrototype -> this.completeMethod(state1, methodPrototype) */;
			/* case Whitespace whitespace -> new Some<>(new Tuple2Impl<>(state1, whitespace)) */;
			/* case Placeholder placeholder -> new Some<>(new Tuple2Impl<>(state1, placeholder)) */;
			/* case ClassDefinition classDefinition -> this.completeDefinition(state1, classDefinition) */;
			/* case ClassInitialization classInitialization -> this.completeInitialization(state1, classInitialization) */;
			/* case StructurePrototype structurePrototype -> this.completeStructure(state1, structurePrototype) */;
			/* case EnumValues enumValues -> new Some<>(new Tuple2Impl<>(state1, enumValues)) */;
		}
		/*  */;
	}
	completeInitialization(state1 : CompileState, classInitialization : ClassInitialization) : Option<[CompileState, ClassSegment]> {
		let definition = classInitialization/* : ClassInitialization */.definition/* : unknown */;
		let statement = new Statement(classInitialization/* : ClassInitialization */.depth/* : unknown */, new FieldInitialization(definition/* : unknown */, classInitialization/* : ClassInitialization */.value/* : unknown */)/* : FieldInitialization */)/* : Statement */;
		return new Some([state1/* : CompileState */, statement/* : unknown */])/* : Some */;
	}
	completeDefinition(state1 : CompileState, classDefinition : ClassDefinition) : Option<[CompileState, ClassSegment]> {
		let definition = classDefinition/* : ClassDefinition */.definition/* : unknown */;
		let statement = new Statement(classDefinition/* : ClassDefinition */.depth/* : unknown */, definition/* : unknown */)/* : Statement */;
		return new Some([state1/* : CompileState */, statement/* : unknown */])/* : Some */;
	}
	retainDefinition(parameter : Parameter) : Option<Definition> {
		if (parameter/* : Parameter */._UnknownVariant/* : unknown */ === UnknownVariant.Definition/* : unknown */){
			let definition : Definition = parameter/* : Parameter */ as Definition;
			return new Some(definition/* : Definition */)/* : Some */;
		}
		return new None()/* : None */;
	}
	prefix<T>(input : string, prefix : string, mapper : (arg0 : string) => Option<T>) : Option<T> {
		if (!input/* : string */.startsWith/* : unknown */(prefix/* : string */)/* : unknown */){
			return new None()/* : None */;
		}
		let slice = input/* : string */.substring/* : unknown */(Strings/* : Strings */.length/* : unknown */(prefix/* : string */)/* : unknown */)/* : unknown */;
		return mapper/* : (arg0 : string) => Option<T> */.apply/* : unknown */(slice/* : unknown */)/* : unknown */;
	}
	suffix<T>(input : string, suffix : string, mapper : (arg0 : string) => Option<T>) : Option<T> {
		if (!input/* : string */.endsWith/* : unknown */(suffix/* : string */)/* : unknown */){
			return new None()/* : None */;
		}
		let slice = input/* : string */.substring/* : unknown */(0/* : number */, Strings/* : Strings */.length/* : unknown */(input/* : string */)/* : unknown */ - Strings/* : Strings */.length/* : unknown */(suffix/* : string */)/* : unknown */)/* : unknown */;
		return mapper/* : (arg0 : string) => Option<T> */.apply/* : unknown */(slice/* : unknown */)/* : unknown */;
	}
	parseClassSegment(state : CompileState, input : string, depth : number) : [CompileState, IncompleteClassSegment] {
		return this/* : Main */./* : unknown */ < /* Whitespace, IncompleteClassSegment>typed */(() => parseWhitespace/* : (arg0 : string, arg1 : CompileState) => Option<[CompileState, Whitespace]> */(input/* : string */, state/* : CompileState */)/* : unknown */)/* : unknown */.or/* : unknown */(() => this/* : Main */.typed/* : unknown */(() => this/* : Main */.parseClass/* : unknown */(input/* : string */, state/* : CompileState */)/* : unknown */)/* : unknown */)/* : unknown */.or/* : unknown */(() => this/* : Main */.typed/* : unknown */(() => this/* : Main */.parseStructure/* : unknown */(input/* : string */, "interface ", "interface ", state/* : CompileState */)/* : unknown */)/* : unknown */)/* : unknown */.or/* : unknown */(() => this/* : Main */.typed/* : unknown */(() => this/* : Main */.parseStructure/* : unknown */(input/* : string */, "record ", "class ", state/* : CompileState */)/* : unknown */)/* : unknown */)/* : unknown */.or/* : unknown */(() => this/* : Main */.typed/* : unknown */(() => this/* : Main */.parseStructure/* : unknown */(input/* : string */, "enum ", "class ", state/* : CompileState */)/* : unknown */)/* : unknown */)/* : unknown */.or/* : unknown */(() => this/* : Main */.typed/* : unknown */(() => this/* : Main */.parseField/* : unknown */(input/* : string */, depth/* : number */, state/* : CompileState */)/* : unknown */)/* : unknown */)/* : unknown */.or/* : unknown */(() => this/* : Main */.parseMethod/* : unknown */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : unknown */)/* : unknown */.or/* : unknown */(() => this/* : Main */.parseEnumValues/* : unknown */(state/* : CompileState */, input/* : string */)/* : unknown */)/* : unknown */.orElseGet/* : unknown */(() => [state/* : CompileState */, new Placeholder(input/* : string */)/* : Placeholder */])/* : unknown */;
	}
	parseEnumValues(state : CompileState, input : string) : Option<[CompileState, IncompleteClassSegment]> {
		return this/* : Main */.suffix/* : unknown */(input/* : string */.strip/* : unknown */()/* : unknown */, ";", (withoutEnd) => {
			return this/* : Main */.parseValues/* : unknown */(state/* : CompileState */, withoutEnd/* : unknown */, (state2, enumValue) => {
				return this/* : Main */.suffix/* : unknown */(enumValue/* : unknown */.strip/* : unknown */()/* : unknown */, ")", (withoutValueEnd) => {
					return this/* : Main */.first/* : unknown */(withoutValueEnd/* : unknown */, "(", (s4, s2) => {
						return this/* : Main */.parseValues/* : unknown */(state2/* : unknown */, s2/* : unknown */, (state1, s1) => new Some(Main/* : Main */.this/* : unknown */.parseArgument/* : unknown */(state1/* : unknown */, s1/* : unknown */, 1/* : number */)/* : unknown */)/* : Some */)/* : unknown */.map/* : unknown */((arguments) => {
							return [arguments/* : unknown */.left/* : unknown */()/* : unknown */, new EnumValue(s4/* : unknown */, Main/* : Main */.this/* : unknown */.retainValues/* : unknown */(arguments/* : unknown */.right/* : unknown */()/* : unknown */)/* : unknown */)/* : EnumValue */];
						})/* : unknown */;
					})/* : unknown */;
				})/* : unknown */;
			})/* : unknown */.map/* : unknown */((tuple) => {
				return [tuple/* : unknown */.left/* : unknown */()/* : unknown */, new EnumValues(tuple/* : unknown */.right/* : unknown */()/* : unknown */)/* : EnumValues */];
			})/* : unknown */;
		})/* : unknown */;
	}
	typed<T extends S, S>(action : () => Option<[CompileState, T]>) : Option<[CompileState, S]> {
		return action/* : () => Option<[CompileState, T]> */.get/* : unknown */()/* : unknown */.map/* : unknown */((tuple) => [tuple/* : unknown */.left/* : unknown */()/* : unknown */, tuple/* : unknown */.right/* : unknown */()/* : unknown */])/* : unknown */;
	}
	parseMethod(state : CompileState, input : string, depth : number) : Option<[CompileState, IncompleteClassSegment]> {
		return this/* : Main */.first/* : unknown */(input/* : string */, "(", (definitionString, withParams) => {
			return this/* : Main */.first/* : unknown */(withParams/* : unknown */, ")", (parametersString, rawContent) => {
				return this/* : Main */.parseDefinition/* : unknown */(state/* : CompileState */, definitionString/* : unknown */)/* : unknown */./* : unknown */ < Tuple2/* : Tuple2 */ < /* CompileState, Header>>map */((tuple) => [tuple/* : unknown */.left/* : unknown */()/* : unknown */, tuple/* : unknown */.right/* : unknown */()/* : unknown */])/* : unknown */.or/* : unknown */(() => this/* : Main */.parseConstructor/* : unknown */(state/* : CompileState */, definitionString/* : unknown */)/* : unknown */)/* : unknown */.flatMap/* : unknown */((definitionTuple) => this/* : Main */.assembleMethod/* : unknown */(depth/* : number */, parametersString/* : unknown */, rawContent/* : unknown */, definitionTuple/* : unknown */)/* : unknown */)/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	assembleMethod(depth : number, parametersString : string, rawContent : string, definitionTuple : [CompileState, Header]) : Option<[CompileState, IncompleteClassSegment]> {
		let definitionState = definitionTuple/* : [CompileState, Header] */.left/* : unknown */()/* : unknown */;
		let header = definitionTuple/* : [CompileState, Header] */.right/* : unknown */()/* : unknown */;
		let parametersTuple = this/* : Main */.parseParameters/* : unknown */(definitionState/* : unknown */, parametersString/* : string */)/* : unknown */;
		let rawParameters = parametersTuple/* : unknown */.right/* : unknown */()/* : unknown */;
		let parameters = this/* : Main */.retainDefinitions/* : unknown */(rawParameters/* : unknown */)/* : unknown */;
		let prototype = new MethodPrototype(depth/* : number */, header/* : unknown */, parameters/* : unknown */, rawContent/* : string */.strip/* : unknown */()/* : unknown */)/* : MethodPrototype */;
		return new Some([parametersTuple/* : unknown */.left/* : unknown */()/* : unknown */.define/* : unknown */(prototype/* : unknown */.createDefinition/* : unknown */()/* : unknown */)/* : unknown */, prototype/* : unknown */])/* : Some */;
	}
	completeMethod(state : CompileState, prototype : MethodPrototype) : Option<[CompileState, ClassSegment]> {
		let definition = prototype/* : MethodPrototype */.createDefinition/* : unknown */()/* : unknown */;
		let oldHeader = prototype/* : MethodPrototype */.header/* : unknown */()/* : unknown */;
		/* Header newHeader */;
		if (oldHeader/* : unknown */._UnknownVariant/* : unknown */ === UnknownVariant.Definition/* : unknown */){
			let maybeDefinition : Definition = oldHeader/* : unknown */ as Definition;
			/* newHeader */ = maybeDefinition/* : Definition */.removeAnnotations/* : unknown */()/* : unknown */;
		}
		else {
			/* newHeader */ = oldHeader/* : unknown */;
		}
		if (prototype/* : MethodPrototype */.content/* : unknown */()/* : unknown */ === ";" || definition/* : unknown */.containsAnnotation/* : unknown */("Actual")/* : unknown */){
			return new Some([state/* : CompileState */.define/* : unknown */(definition/* : unknown */)/* : unknown */, new FunctionNode(prototype/* : MethodPrototype */.depth/* : unknown */()/* : unknown */, /* newHeader */, prototype/* : MethodPrototype */.parameters/* : unknown */()/* : unknown */, new None()/* : None */)/* : FunctionNode */])/* : Some */;
		}
		if (prototype/* : MethodPrototype */.content/* : unknown */()/* : unknown */.startsWith/* : unknown */("{")/* : unknown */ && prototype/* : MethodPrototype */.content/* : unknown */()/* : unknown */.endsWith/* : unknown */("}")/* : unknown */){
			let substring = prototype/* : MethodPrototype */.content/* : unknown */()/* : unknown */.substring/* : unknown */(1/* : number */, Strings/* : Strings */.length/* : unknown */(prototype/* : MethodPrototype */.content/* : unknown */()/* : unknown */)/* : unknown */ - 1/* : number */)/* : unknown */;
			let withDefined = state/* : CompileState */.enterDefinitions/* : unknown */()/* : unknown */.defineAll/* : unknown */(prototype/* : MethodPrototype */.parameters/* : unknown */()/* : unknown */)/* : unknown */;
			let statementsTuple = this/* : Main */.parseStatements/* : unknown */(withDefined/* : unknown */, substring/* : unknown */, (state1, input1) => this/* : Main */.parseFunctionSegment/* : unknown */(state1/* : unknown */, input1/* : unknown */, prototype/* : MethodPrototype */.depth/* : unknown */()/* : unknown */ + 1/* : number */)/* : unknown */)/* : unknown */;
			let statements = statementsTuple/* : unknown */.right/* : unknown */()/* : unknown */;
			return new Some([statementsTuple/* : unknown */.left/* : unknown */()/* : unknown */.exitDefinitions/* : unknown */()/* : unknown */.define/* : unknown */(definition/* : unknown */)/* : unknown */, new FunctionNode(prototype/* : MethodPrototype */.depth/* : unknown */()/* : unknown */, /* newHeader */, prototype/* : MethodPrototype */.parameters/* : unknown */()/* : unknown */, new Some(statements/* : unknown */)/* : Some */)/* : FunctionNode */])/* : Some */;
		}
		return new None()/* : None */;
	}
	parseConstructor(state : CompileState, input : string) : Option<[CompileState, Header]> {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (state/* : CompileState */.isCurrentStructName/* : unknown */(stripped/* : unknown */)/* : unknown */){
			return new Some([state/* : CompileState */, new ConstructorHeader()/* : ConstructorHeader */])/* : Some */;
		}
		return new None()/* : None */;
	}
	retainDefinitions(right : List<Parameter>) : List<Definition> {
		return right/* : List<Parameter> */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(this/* : Main */.retainDefinition/* : unknown */)/* : unknown */.flatMap/* : unknown */(Queries/* : Queries */.fromOption/* : unknown */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
	}
	parseParameters(state : CompileState, params : string) : [CompileState, List<Parameter>] {
		return this/* : Main */.parseValuesOrEmpty/* : unknown */(state/* : CompileState */, params/* : string */, (state1, s) => new Some(this/* : Main */.parseParameter/* : unknown */(state1/* : unknown */, s/* : unknown */)/* : unknown */)/* : Some */)/* : unknown */;
	}
	parseFunctionSegments(state : CompileState, input : string, depth : number) : [CompileState, List<FunctionSegment>] {
		return this/* : Main */.parseStatements/* : unknown */(state/* : CompileState */, input/* : string */, (state1, input1) => this/* : Main */.parseFunctionSegment/* : unknown */(state1/* : unknown */, input1/* : unknown */, depth/* : number */ + 1/* : number */)/* : unknown */)/* : unknown */;
	}
	parseFunctionSegment(state : CompileState, input : string, depth : number) : [CompileState, FunctionSegment] {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.isEmpty/* : unknown */()/* : unknown */){
			return [state/* : CompileState */, new Whitespace()/* : Whitespace */];
		}
		return this/* : Main */.parseFunctionStatement/* : unknown */(state/* : CompileState */, depth/* : number */, stripped/* : unknown */)/* : unknown */.or/* : unknown */(() => this/* : Main */.parseBlock/* : unknown */(state/* : CompileState */, depth/* : number */, stripped/* : unknown */)/* : unknown */)/* : unknown */.orElseGet/* : unknown */(() => [state/* : CompileState */, new Placeholder(stripped/* : unknown */)/* : Placeholder */])/* : unknown */;
	}
	parseFunctionStatement(state : CompileState, depth : number, stripped : string) : Option<[CompileState, FunctionSegment]> {
		return this/* : Main */.suffix/* : unknown */(stripped/* : string */, ";", (s) => {
			let tuple = this/* : Main */.parseStatementValue/* : unknown */(state/* : CompileState */, s/* : unknown */, depth/* : number */)/* : unknown */;
			let left = tuple/* : unknown */.left/* : unknown */()/* : unknown */;
			let right = tuple/* : unknown */.right/* : unknown */()/* : unknown */;
			return new Some([left/* : unknown */, new Statement(depth/* : number */, right/* : unknown */)/* : Statement */])/* : Some */;
		})/* : unknown */;
	}
	parseBlock(state : CompileState, depth : number, stripped : string) : Option<[CompileState, FunctionSegment]> {
		return this/* : Main */.suffix/* : unknown */(stripped/* : string */, "}", (withoutEnd) => {
			return this/* : Main */.split/* : unknown */(() => this/* : Main */.toFirst/* : unknown */(withoutEnd/* : unknown */)/* : unknown */, (beforeContent, content) => {
				return this/* : Main */.suffix/* : unknown */(beforeContent/* : unknown */, "{", (headerString) => {
					let headerTuple = this/* : Main */.parseBlockHeader/* : unknown */(state/* : CompileState */, headerString/* : unknown */, depth/* : number */)/* : unknown */;
					let headerState = headerTuple/* : unknown */.left/* : unknown */()/* : unknown */;
					let header = headerTuple/* : unknown */.right/* : unknown */()/* : unknown */;
					let statementsTuple = this/* : Main */.parseFunctionSegments/* : unknown */(headerState/* : unknown */, content/* : unknown */, depth/* : number */)/* : unknown */;
					let statementsState = statementsTuple/* : unknown */.left/* : unknown */()/* : unknown */;
					let statements = statementsTuple/* : unknown */.right/* : unknown */()/* : unknown */.addAllFirst/* : unknown */(statementsState/* : unknown */.functionSegments/* : unknown */)/* : unknown */;
					return new Some([statementsState/* : unknown */.clearFunctionSegments/* : unknown */()/* : unknown */, new Block(depth/* : number */, header/* : unknown */, statements/* : unknown */)/* : Block */])/* : Some */;
				})/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	toFirst(input : string) : Option<[string, string]> {
		let divisions = this/* : Main */.divideAll/* : unknown */(input/* : string */, this/* : Main */.foldBlockStart/* : unknown */)/* : unknown */;
		return divisions/* : unknown */.removeFirst/* : unknown */()/* : unknown */.map/* : unknown */((removed) => {
			let right = removed/* : unknown */.left/* : unknown */()/* : unknown */;
			let left = removed/* : unknown */.right/* : unknown */()/* : unknown */.iterate/* : unknown */()/* : unknown */.collect/* : unknown */(new Joiner("")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
			return [right/* : unknown */, left/* : unknown */];
		})/* : unknown */;
	}
	parseBlockHeader(state : CompileState, input : string, depth : number) : [CompileState, BlockHeader] {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		return this/* : Main */.parseConditional/* : unknown */(state/* : CompileState */, stripped/* : unknown */, "if", depth/* : number */)/* : unknown */.or/* : unknown */(() => this/* : Main */.parseConditional/* : unknown */(state/* : CompileState */, stripped/* : unknown */, "while", depth/* : number */)/* : unknown */)/* : unknown */.or/* : unknown */(() => this/* : Main */.parseElse/* : unknown */(state/* : CompileState */, input/* : string */)/* : unknown */)/* : unknown */.orElseGet/* : unknown */(() => [state/* : CompileState */, new Placeholder(stripped/* : unknown */)/* : Placeholder */])/* : unknown */;
	}
	parseElse(state : CompileState, input : string) : Option<[CompileState, BlockHeader]> {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */ === "else"){
			return new Some([state/* : CompileState */, new Else()/* : Else */])/* : Some */;
		}
		return new None()/* : None */;
	}
	parseConditional(state : CompileState, input : string, prefix : string, depth : number) : Option<[CompileState, BlockHeader]> {
		return this/* : Main */.prefix/* : unknown */(input/* : string */, prefix/* : string */, (withoutPrefix) => {
			return this/* : Main */.prefix/* : unknown */(withoutPrefix/* : unknown */.strip/* : unknown */()/* : unknown */, "(", (withoutValueStart) => {
				return this/* : Main */.suffix/* : unknown */(withoutValueStart/* : unknown */, ")", (value) => {
					let valueTuple = this/* : Main */.parseValue/* : unknown */(state/* : CompileState */, value/* : unknown */, depth/* : number */)/* : unknown */;
					let value1 = valueTuple/* : unknown */.right/* : unknown */()/* : unknown */;
					return new Some([valueTuple/* : unknown */.left/* : unknown */()/* : unknown */, new Conditional(prefix/* : string */, value1/* : unknown */)/* : Conditional */])/* : Some */;
				})/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	foldBlockStart(state : DivideState, c : string) : DivideState {
		let appended = state/* : DivideState */.append/* : unknown */(c/* : string */)/* : unknown */;
		if (c/* : string */ === "{" && state/* : DivideState */.isLevel/* : unknown */()/* : unknown */){
			return appended/* : unknown */.advance/* : unknown */()/* : unknown */;
		}
		if (c/* : string */ === "{"){
			return appended/* : unknown */.enter/* : unknown */()/* : unknown */;
		}
		if (c/* : string */ === "}"){
			return appended/* : unknown */.exit/* : unknown */()/* : unknown */;
		}
		return appended/* : unknown */;
	}
	parseStatementValue(state : CompileState, input : string, depth : number) : [CompileState, StatementValue] {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.startsWith/* : unknown */("return ")/* : unknown */){
			let value = stripped/* : unknown */.substring/* : unknown */(Strings/* : Strings */.length/* : unknown */("return ")/* : unknown */)/* : unknown */;
			let tuple = this/* : Main */.parseValue/* : unknown */(state/* : CompileState */, value/* : unknown */, depth/* : number */)/* : unknown */;
			let value1 = tuple/* : unknown */.right/* : unknown */()/* : unknown */;
			return [tuple/* : unknown */.left/* : unknown */()/* : unknown */, new Return(value1/* : unknown */)/* : Return */];
		}
		return this/* : Main */.parseAssignment/* : unknown */(state/* : CompileState */, depth/* : number */, stripped/* : unknown */)/* : unknown */.orElseGet/* : unknown */(() => {
			return [state/* : CompileState */, new Placeholder(stripped/* : unknown */)/* : Placeholder */];
		})/* : unknown */;
	}
	parseAssignment(state : CompileState, depth : number, stripped : string) : Option<[CompileState, StatementValue]> {
		return this/* : Main */.first/* : unknown */(stripped/* : string */, "=", (beforeEquals, valueString) => {
			let sourceTuple = this/* : Main */.parseValue/* : unknown */(state/* : CompileState */, valueString/* : unknown */, depth/* : number */)/* : unknown */;
			let sourceState = sourceTuple/* : unknown */.left/* : unknown */()/* : unknown */;
			let source = sourceTuple/* : unknown */.right/* : unknown */()/* : unknown */;
			let destinationTuple = this/* : Main */.parseValue/* : unknown */(sourceState/* : unknown */, beforeEquals/* : unknown */, depth/* : number */)/* : unknown */;
			let destinationState = destinationTuple/* : unknown */.left/* : unknown */()/* : unknown */;
			let destination = destinationTuple/* : unknown */.right/* : unknown */()/* : unknown */;
			return this/* : Main */.parseDefinition/* : unknown */(destinationState/* : unknown */, beforeEquals/* : unknown */)/* : unknown */.flatMap/* : unknown */((definitionTuple) => this/* : Main */.parseInitialization/* : unknown */(definitionTuple/* : unknown */.left/* : unknown */()/* : unknown */, definitionTuple/* : unknown */.right/* : unknown */()/* : unknown */, source/* : unknown */)/* : unknown */)/* : unknown */.or/* : unknown */(() => new Some([destinationState/* : unknown */, new Assignment(destination/* : unknown */, source/* : unknown */)/* : Assignment */])/* : Some */)/* : unknown */;
		})/* : unknown */;
	}
	parseInitialization(state : CompileState, destination : Definition, source : Value) : Option<[CompileState, StatementValue]> {
		let definition = destination/* : Definition */.mapType/* : unknown */((type) => {
			if (type/* : unknown */ === Primitive/* : Primitive */.Unknown/* : unknown */){
				return this/* : Main */.resolveValue/* : unknown */(state/* : CompileState */, source/* : Value */)/* : unknown */;
			}
			else {
				return type/* : unknown */;
			}
		})/* : unknown */;
		return new Some([state/* : CompileState */.define/* : unknown */(definition/* : unknown */)/* : unknown */, new Initialization(definition/* : unknown */, source/* : Value */)/* : Initialization */])/* : Some */;
	}
	resolveValue(state : CompileState, value : Value) : Type {
		/* return switch (value) */{
			/* case BooleanValue booleanValue -> Primitive.Unknown */;
			/* case Cast cast -> Primitive.Unknown */;
			/* case DataAccess dataAccess -> Primitive.Unknown */;
			/* case IndexValue indexValue -> Primitive.Unknown */;
			/* case Invokable invokable -> Primitive.Unknown */;
			/* case Lambda lambda -> Primitive.Unknown */;
			/* case Not not -> Primitive.Unknown */;
			/* case Operation operation -> Primitive.Unknown */;
			/* case Placeholder placeholder -> Primitive.Unknown */;
			/* case StringValue stringValue -> Primitive.Unknown */;
			/* case SymbolValue symbolValue -> Primitive.Unknown */;
			/* case TupleNode tupleNode -> Primitive.Unknown */;
		}
		/*  */;
	}
	parseValue(state : CompileState, input : string, depth : number) : [CompileState, Value] {
		return this/* : Main */.parseBoolean/* : unknown */(state/* : CompileState */, input/* : string */)/* : unknown */.or/* : unknown */(() => this/* : Main */.parseLambda/* : unknown */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : unknown */)/* : unknown */.or/* : unknown */(() => this/* : Main */.parseString/* : unknown */(state/* : CompileState */, input/* : string */)/* : unknown */)/* : unknown */.or/* : unknown */(() => this/* : Main */.parseDataAccess/* : unknown */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : unknown */)/* : unknown */.or/* : unknown */(() => this/* : Main */.parseSymbolValue/* : unknown */(state/* : CompileState */, input/* : string */)/* : unknown */)/* : unknown */.or/* : unknown */(() => this/* : Main */.parseInvokable/* : unknown */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : unknown */)/* : unknown */.or/* : unknown */(() => this/* : Main */.parseDigits/* : unknown */(state/* : CompileState */, input/* : string */)/* : unknown */)/* : unknown */.or/* : unknown */(() => this/* : Main */.parseInstanceOf/* : unknown */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : unknown */)/* : unknown */.or/* : unknown */(() => this/* : Main */.parseOperation/* : unknown */(state/* : CompileState */, input/* : string */, depth/* : number */, Operator/* : () => content-start new content-end */.ADD/* : unknown */)/* : unknown */)/* : unknown */.or/* : unknown */(() => this/* : Main */.parseOperation/* : unknown */(state/* : CompileState */, input/* : string */, depth/* : number */, Operator/* : () => content-start new content-end */.EQUALS/* : unknown */)/* : unknown */)/* : unknown */.or/* : unknown */(() => this/* : Main */.parseOperation/* : unknown */(state/* : CompileState */, input/* : string */, depth/* : number */, Operator/* : () => content-start new content-end */.SUBTRACT/* : unknown */)/* : unknown */)/* : unknown */.or/* : unknown */(() => this/* : Main */.parseOperation/* : unknown */(state/* : CompileState */, input/* : string */, depth/* : number */, Operator/* : () => content-start new content-end */.AND/* : unknown */)/* : unknown */)/* : unknown */.or/* : unknown */(() => this/* : Main */.parseOperation/* : unknown */(state/* : CompileState */, input/* : string */, depth/* : number */, Operator/* : () => content-start new content-end */.OR/* : unknown */)/* : unknown */)/* : unknown */.or/* : unknown */(() => this/* : Main */.parseOperation/* : unknown */(state/* : CompileState */, input/* : string */, depth/* : number */, /*  Operator.GREATER_THAN_OR_EQUALS */)/* : unknown */)/* : unknown */.or/* : unknown */(() => this/* : Main */.parseOperation/* : unknown */(state/* : CompileState */, input/* : string */, depth/* : number */, /*  Operator.LESS_THAN */)/* : unknown */)/* : unknown */.or/* : unknown */(() => this/* : Main */.parseNot/* : unknown */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : unknown */)/* : unknown */.or/* : unknown */(() => this/* : Main */.parseMethodReference/* : unknown */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : unknown */)/* : unknown */.or/* : unknown */(() => this/* : Main */.parseChar/* : unknown */(state/* : CompileState */, input/* : string */)/* : unknown */)/* : unknown */.orElseGet/* : unknown */(() => [state/* : CompileState */, new Placeholder(input/* : string */)/* : Placeholder */])/* : unknown */;
	}
	parseChar(state : CompileState, input : string) : Option<[CompileState, Value]> {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.startsWith/* : unknown */("'")/* : unknown */ && stripped/* : unknown */.endsWith/* : unknown */("'")/* : unknown */ && Strings/* : Strings */.length/* : unknown */(stripped/* : unknown */)/* : unknown */ >= 2/* : number */){
			return new Some([state/* : CompileState */, new StringValue(stripped/* : unknown */.substring/* : unknown */(1/* : number */, Strings/* : Strings */.length/* : unknown */(stripped/* : unknown */)/* : unknown */ - 1/* : number */)/* : unknown */)/* : StringValue */])/* : Some */;
		}
		return new None()/* : None */;
	}
	parseBoolean(state : CompileState, input : string) : Option<[CompileState, Value]> {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */ === "false"){
			return new Some([state/* : CompileState */, BooleanValue/* : BooleanValue */.False/* : unknown */])/* : Some */;
		}
		if (stripped/* : unknown */ === "true"){
			return new Some([state/* : CompileState */, BooleanValue/* : BooleanValue */.True/* : unknown */])/* : Some */;
		}
		return new None()/* : None */;
	}
	parseInstanceOf(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return this/* : Main */.last/* : unknown */(input/* : string */, "instanceof", (beforeKeyword, afterKeyword) => {
			let childTuple = this/* : Main */.parseValue/* : unknown */(state/* : CompileState */, beforeKeyword/* : unknown */, depth/* : number */)/* : unknown */;
			return this/* : Main */.parseDefinition/* : unknown */(childTuple/* : unknown */.left/* : unknown */()/* : unknown */, afterKeyword/* : unknown */)/* : unknown */.map/* : unknown */(this/* : Main */.parseInstanceOfWithValue/* : unknown */(depth/* : number */, childTuple/* : unknown */.right/* : unknown */()/* : unknown */)/* : unknown */)/* : unknown */;
		})/* : unknown */;
	}
	parseInstanceOfWithValue(depth : number, child : Value) : (arg0 : [CompileState, Definition]) => [CompileState, Value] {
		/* return definitionTuple -> */{
			let definitionSTate = /* definitionTuple */.left/* : unknown */()/* : unknown */;
			let definition = /* definitionTuple */.right/* : unknown */()/* : unknown */;
			let type = this/* : Main */.resolveValue/* : unknown */(definitionSTate/* : unknown */, child/* : Value */)/* : unknown */;
			let variant = new DataAccess(child/* : Value */, "_" + type.findName() + "Variant", Primitive/* : Primitive */.Unknown/* : unknown */)/* : DataAccess */;
			let generate = type/* : unknown */.findName/* : unknown */()/* : unknown */;
			let temp = new SymbolValue(generate/* : unknown */ + "Variant." + definition/* : unknown */.findType/* : unknown */()/* : unknown */.findName/* : unknown */()/* : unknown */, Primitive/* : Primitive */.Unknown/* : unknown */)/* : SymbolValue */;
			let functionSegment = new Statement(depth/* : number */ + 1/* : number */, new Initialization(definition/* : unknown */, new Cast(child/* : Value */, definition/* : unknown */.findType/* : unknown */()/* : unknown */)/* : Cast */)/* : Initialization */)/* : Statement */;
			return [definitionSTate/* : unknown */.addFunctionSegment/* : unknown */(functionSegment/* : unknown */)/* : unknown */.define/* : unknown */(definition/* : unknown */)/* : unknown */, new Operation(variant/* : unknown */, Operator/* : () => content-start new content-end */.EQUALS/* : unknown */, temp/* : unknown */)/* : Operation */];
		}
		/*  */;
	}
	parseMethodReference(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return this/* : Main */.last/* : unknown */(input/* : string */, "::", (s, s2) => {
			let tuple = this/* : Main */.parseValue/* : unknown */(state/* : CompileState */, s/* : unknown */, depth/* : number */)/* : unknown */;
			return new Some([tuple/* : unknown */.left/* : unknown */()/* : unknown */, new DataAccess(tuple/* : unknown */.right/* : unknown */()/* : unknown */, s2/* : unknown */, Primitive/* : Primitive */.Unknown/* : unknown */)/* : DataAccess */])/* : Some */;
		})/* : unknown */;
	}
	parseNot(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.startsWith/* : unknown */("!")/* : unknown */){
			let slice = stripped/* : unknown */.substring/* : unknown */(1/* : number */)/* : unknown */;
			let tuple = this/* : Main */.parseValue/* : unknown */(state/* : CompileState */, slice/* : unknown */, depth/* : number */)/* : unknown */;
			let value = tuple/* : unknown */.right/* : unknown */()/* : unknown */;
			return new Some([tuple/* : unknown */.left/* : unknown */()/* : unknown */, new Not(value/* : unknown */)/* : Not */])/* : Some */;
		}
		return new None()/* : None */;
	}
	parseLambda(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return this/* : Main */.first/* : unknown */(input/* : string */, "->", (beforeArrow, valueString) => {
			let strippedBeforeArrow = beforeArrow/* : unknown */.strip/* : unknown */()/* : unknown */;
			if (isSymbol/* : (arg0 : string) => boolean */(strippedBeforeArrow/* : unknown */)/* : unknown */){
				let type : Type = Primitive/* : Primitive */.Unknown/* : unknown */;
				if (/* state.typeRegister instanceof Some */(/* var expectedType */)/* : unknown */){
					if (/* expectedType */._UnknownVariant/* : unknown */ === UnknownVariant.FunctionType/* : unknown */){
						let functionType : FunctionType = /* expectedType */ as FunctionType;
						type/* : Type */ = functionType/* : FunctionType */.arguments/* : unknown */.get/* : unknown */(0/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */;
					}
				}
				return this/* : Main */.assembleLambda/* : unknown */(state/* : CompileState */, Lists/* : Lists */.of/* : unknown */(Definition/* : Definition */.createSimpleDefinition/* : unknown */(strippedBeforeArrow/* : unknown */, type/* : Type */)/* : unknown */)/* : unknown */, valueString/* : unknown */, depth/* : number */)/* : unknown */;
			}
			if (strippedBeforeArrow/* : unknown */.startsWith/* : unknown */("(")/* : unknown */ && strippedBeforeArrow/* : unknown */.endsWith/* : unknown */(")")/* : unknown */){
				let parameterNames = this/* : Main */.divideAll/* : unknown */(strippedBeforeArrow/* : unknown */.substring/* : unknown */(1/* : number */, Strings/* : Strings */.length/* : unknown */(strippedBeforeArrow/* : unknown */)/* : unknown */ - 1/* : number */)/* : unknown */, this/* : Main */.foldValueChar/* : unknown */)/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(/* String */.strip/* : unknown */)/* : unknown */.filter/* : unknown */((value) => !value/* : unknown */.isEmpty/* : unknown */()/* : unknown */)/* : unknown */.map/* : unknown */((name) => Definition/* : Definition */.createSimpleDefinition/* : unknown */(name/* : unknown */, Primitive/* : Primitive */.Unknown/* : unknown */)/* : unknown */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
				return this/* : Main */.assembleLambda/* : unknown */(state/* : CompileState */, parameterNames/* : unknown */, valueString/* : unknown */, depth/* : number */)/* : unknown */;
			}
			return new None()/* : None */;
		})/* : unknown */;
	}
	assembleLambda(state : CompileState, definitions : List<Definition>, valueString : string, depth : number) : Some<[CompileState, Value]> {
		let strippedValueString = valueString/* : string */.strip/* : unknown */()/* : unknown */;
		/* Tuple2<CompileState, LambdaValue> value */;
		let state2 = state/* : CompileState */.defineAll/* : unknown */(definitions/* : List<Definition> */)/* : unknown */;
		if (strippedValueString/* : unknown */.startsWith/* : unknown */("{")/* : unknown */ && strippedValueString/* : unknown */.endsWith/* : unknown */("}")/* : unknown */){
			let value1 = this/* : Main */.parseStatements/* : unknown */(state2/* : unknown */, strippedValueString/* : unknown */.substring/* : unknown */(1/* : number */, Strings/* : Strings */.length/* : unknown */(strippedValueString/* : unknown */)/* : unknown */ - 1/* : number */)/* : unknown */, (state1, input1) => this/* : Main */.parseFunctionSegment/* : unknown */(state1/* : unknown */, input1/* : unknown */, depth/* : number */ + 1/* : number */)/* : unknown */)/* : unknown */;
			let right = value1/* : unknown */.right/* : unknown */()/* : unknown */;
			/* value */ = [value1/* : unknown */.left/* : unknown */()/* : unknown */, new BlockLambdaValue(depth/* : number */, right/* : unknown */)/* : BlockLambdaValue */];
		}
		else {
			let value1 = this/* : Main */.parseValue/* : unknown */(state2/* : unknown */, strippedValueString/* : unknown */, depth/* : number */)/* : unknown */;
			/* value */ = [value1/* : unknown */.left/* : unknown */()/* : unknown */, value1/* : unknown */.right/* : unknown */()/* : unknown */];
		}
		let right = /* value */.right/* : unknown */()/* : unknown */;
		return new Some([/* value */.left/* : unknown */()/* : unknown */, new Lambda(definitions/* : List<Definition> */, right/* : unknown */)/* : Lambda */])/* : Some */;
	}
	parseDigits(state : CompileState, input : string) : Option<[CompileState, Value]> {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (this/* : Main */.isNumber/* : unknown */(stripped/* : unknown */)/* : unknown */){
			return new Some([state/* : CompileState */, new SymbolValue(stripped/* : unknown */, Primitive/* : Primitive */.Int/* : unknown */)/* : SymbolValue */])/* : Some */;
		}
		return new None()/* : None */;
	}
	isNumber(input : string) : boolean {
		/* String maybeTruncated */;
		if (input/* : string */.startsWith/* : unknown */("-")/* : unknown */){
			/* maybeTruncated */ = input/* : string */.substring/* : unknown */(1/* : number */)/* : unknown */;
		}
		else {
			/* maybeTruncated */ = input/* : string */;
		}
		return this/* : Main */.areAllDigits/* : unknown */(/* maybeTruncated */)/* : unknown */;
	}
	areAllDigits(input : string) : boolean {
		/* for (var i = 0; i < Strings.length(input); i++) */{
			let c = input/* : string */.charAt/* : unknown */(/* i */)/* : unknown */;
			if (/* Character */.isDigit/* : unknown */(c/* : unknown */)/* : unknown */){
				/* continue */;
			}
			return false;
		}
		return true;
	}
	parseInvokable(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return this/* : Main */.suffix/* : unknown */(input/* : string */.strip/* : unknown */()/* : unknown */, ")", (withoutEnd) => {
			return this/* : Main */.split/* : unknown */(() => this/* : Main */.toLast/* : unknown */(withoutEnd/* : unknown */, "", this/* : Main */.foldInvocationStart/* : unknown */)/* : unknown */, (callerWithEnd, argumentsString) => {
				return this/* : Main */.suffix/* : unknown */(callerWithEnd/* : unknown */, "(", (callerString) => {
					return this/* : Main */.assembleInvokable/* : unknown */(state/* : CompileState */, depth/* : number */, argumentsString/* : unknown */, callerString/* : unknown */.strip/* : unknown */()/* : unknown */)/* : unknown */;
				})/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	assembleInvokable(state : CompileState, depth : number, argumentsString : string, callerString : string) : Some<[CompileState, Value]> {
		let callerTuple = this/* : Main */.invocationHeader/* : unknown */(state/* : CompileState */, depth/* : number */, callerString/* : string */)/* : unknown */;
		let oldCallerState = callerTuple/* : unknown */.left/* : unknown */()/* : unknown */;
		let oldCaller = callerTuple/* : unknown */.right/* : unknown */()/* : unknown */;
		let newCaller = this/* : Main */.modifyCaller/* : unknown */(oldCallerState/* : unknown */, oldCaller/* : unknown */)/* : unknown */;
		let callerType = this/* : Main */.findCallerType/* : unknown */(newCaller/* : unknown */, oldCallerState/* : unknown */)/* : unknown */;
		let argumentsTuple = this/* : Main */.parseValuesWithIndices/* : unknown */(oldCallerState/* : unknown */, argumentsString/* : string */, (currentState, pair) => this/* : Main */.getTuple2Some/* : unknown */(depth/* : number */, currentState/* : unknown */, pair/* : unknown */, callerType/* : unknown */)/* : unknown */)/* : unknown */.orElseGet/* : unknown */(() => [oldCallerState/* : unknown */, Lists/* : Lists */.empty/* : unknown */()/* : unknown */])/* : unknown */;
		let argumentsState = argumentsTuple/* : unknown */.left/* : unknown */()/* : unknown */;
		let argumentsWithActualTypes = argumentsTuple/* : unknown */.right/* : unknown */()/* : unknown */;
		let arguments = this/* : Main */.retainValues/* : unknown */(argumentsWithActualTypes/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(Tuple2/* : Tuple2 */.left/* : unknown */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */)/* : unknown */;
		if (newCaller/* : unknown */._UnknownVariant/* : unknown */ === UnknownVariant.ConstructionCaller/* : unknown */){
			if (constructionCaller/* : ConstructionCaller */.type/* : unknown */.findName/* : unknown */()/* : unknown */ === "Tuple2Impl"){
			let constructionCaller : ConstructionCaller = newCaller/* : unknown */ as ConstructionCaller;
				return new Some([argumentsState/* : unknown */, new TupleNode(Lists/* : Lists */.of/* : unknown */(arguments/* : unknown */.get/* : unknown */(0/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */, arguments/* : unknown */.get/* : unknown */(1/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : unknown */)/* : TupleNode */])/* : Some */;
			}
		}
		if (newCaller/* : unknown */._UnknownVariant/* : unknown */ === UnknownVariant.Value/* : unknown */){
			if (value/* : Value */._UnknownVariant/* : unknown */ === UnknownVariant.DataAccess/* : unknown */){
				let parent = access/* : DataAccess */.parent/* : unknown */;
				let property = access/* : DataAccess */.property/* : unknown */;
				let parentType = this/* : Main */.resolveValue/* : unknown */(argumentsState/* : unknown */, parent/* : unknown */)/* : unknown */;
				if (/* parentType instanceof TupleType */){
					if (property/* : unknown */ === "left"){
			let value : Value = newCaller/* : unknown */ as Value;
				let access : DataAccess = value/* : Value */ as DataAccess;
						return new Some([argumentsState/* : unknown */, new IndexValue(parent/* : unknown */, new SymbolValue("0", Primitive/* : Primitive */.Int/* : unknown */)/* : SymbolValue */)/* : IndexValue */])/* : Some */;
					}
					if (property/* : unknown */ === "right"){
						return new Some([argumentsState/* : unknown */, new IndexValue(parent/* : unknown */, new SymbolValue("1", Primitive/* : Primitive */.Int/* : unknown */)/* : SymbolValue */)/* : IndexValue */])/* : Some */;
					}
				}
				if (property/* : unknown */ === "equals"){
					let first = arguments/* : unknown */.get/* : unknown */(0/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */;
					return new Some([argumentsState/* : unknown */, new Operation(parent/* : unknown */, Operator/* : () => content-start new content-end */.EQUALS/* : unknown */, first/* : unknown */)/* : Operation */])/* : Some */;
				}
			}
		}
		let invokable = new Invokable(newCaller/* : unknown */, arguments/* : unknown */, callerType/* : unknown */.returns/* : unknown */)/* : Invokable */;
		return new Some([argumentsState/* : unknown */, invokable/* : unknown */])/* : Some */;
	}
	getTuple2Some(depth : number, currentState : CompileState, pair : [number, string], callerType : FunctionType) : Some<[CompileState, [Argument, Type]]> {
		let index = pair/* : [number, string] */.left/* : unknown */()/* : unknown */;
		let element = pair/* : [number, string] */.right/* : unknown */()/* : unknown */;
		let expectedType = callerType/* : FunctionType */.arguments/* : unknown */.get/* : unknown */(index/* : unknown */)/* : unknown */.orElse/* : unknown */(Primitive/* : Primitive */.Unknown/* : unknown */)/* : unknown */;
		let withExpected = currentState/* : CompileState */.withExpectedType/* : unknown */(expectedType/* : unknown */)/* : unknown */;
		let valueTuple = this/* : Main */.parseArgument/* : unknown */(withExpected/* : unknown */, element/* : unknown */, depth/* : number */)/* : unknown */;
		let valueState = valueTuple/* : unknown */.left/* : unknown */()/* : unknown */;
		let value = valueTuple/* : unknown */.right/* : unknown */()/* : unknown */;
		let actualType = valueTuple/* : unknown */.left/* : unknown */()/* : unknown */.typeRegister/* : unknown */.orElse/* : unknown */(Primitive/* : Primitive */.Unknown/* : unknown */)/* : unknown */;
		return new Some([valueState/* : unknown */, [value/* : unknown */, actualType/* : unknown */]])/* : Some */;
	}
	retainValues(arguments : List<Argument>) : List<Value> {
		return arguments/* : List<Argument> */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(this/* : Main */.retainValue/* : unknown */)/* : unknown */.flatMap/* : unknown */(Queries/* : Queries */.fromOption/* : unknown */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
	}
	retainValue(argument : Argument) : Option<Value> {
		if (argument/* : Argument */._UnknownVariant/* : unknown */ === UnknownVariant.Value/* : unknown */){
			let value : Value = argument/* : Argument */ as Value;
			return new Some(value/* : Value */)/* : Some */;
		}
		return new None()/* : None */;
	}
	parseArgument(state : CompileState, element : string, depth : number) : [CompileState, Argument] {
		if (element/* : string */.isEmpty/* : unknown */()/* : unknown */){
			return [state/* : CompileState */, new Whitespace()/* : Whitespace */];
		}
		let tuple = this/* : Main */.parseValue/* : unknown */(state/* : CompileState */, element/* : string */, depth/* : number */)/* : unknown */;
		return [tuple/* : unknown */.left/* : unknown */()/* : unknown */, tuple/* : unknown */.right/* : unknown */()/* : unknown */];
	}
	findCallerType(newCaller : Caller, state : CompileState) : FunctionType {
		let callerType : FunctionType = new FunctionType(Lists/* : Lists */.empty/* : unknown */()/* : unknown */, Primitive/* : Primitive */.Unknown/* : unknown */)/* : FunctionType */;
		/* switch (newCaller) */{
			/* case ConstructionCaller constructionCaller -> */{
				callerType/* : FunctionType */ = /* constructionCaller */.toFunction/* : unknown */()/* : unknown */;
			}
			/* case Value value -> */{
				let type = this/* : Main */.resolveValue/* : unknown */(state/* : CompileState */, /* value */)/* : unknown */;
				if (type/* : unknown */._UnknownVariant/* : unknown */ === UnknownVariant.FunctionType/* : unknown */){
					let functionType : FunctionType = type/* : unknown */ as FunctionType;
					callerType/* : FunctionType */ = functionType/* : FunctionType */;
				}
			}
		}
		return callerType/* : FunctionType */;
	}
	modifyCaller(state : CompileState, oldCaller : Caller) : Caller {
		if (oldCaller/* : Caller */._UnknownVariant/* : unknown */ === UnknownVariant.DataAccess/* : unknown */){
			let type = this/* : Main */.resolveValue/* : unknown */(state/* : CompileState */, access/* : DataAccess */.parent/* : unknown */)/* : unknown */;
			if (/* type instanceof FunctionType */){
			let access : DataAccess = oldCaller/* : Caller */ as DataAccess;
				return access/* : DataAccess */.parent/* : unknown */;
			}
		}
		return oldCaller/* : Caller */;
	}
	invocationHeader(state : CompileState, depth : number, callerString1 : string) : [CompileState, Caller] {
		if (callerString1/* : string */.startsWith/* : unknown */("new ")/* : unknown */){
			let input1 : string = callerString1/* : string */.substring/* : unknown */(Strings/* : Strings */.length/* : unknown */("new ")/* : unknown */)/* : unknown */;
			let map = this/* : Main */.parseType/* : unknown */(state/* : CompileState */, input1/* : string */)/* : unknown */.map/* : unknown */((type) => {
				let right = type/* : unknown */.right/* : unknown */()/* : unknown */;
				return [type/* : unknown */.left/* : unknown */()/* : unknown */, new ConstructionCaller(right/* : unknown */)/* : ConstructionCaller */];
			})/* : unknown */;
			if (map/* : unknown */.isPresent/* : unknown */()/* : unknown */){
				return map/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */;
			}
		}
		let tuple = this/* : Main */.parseValue/* : unknown */(state/* : CompileState */, callerString1/* : string */, depth/* : number */)/* : unknown */;
		return [tuple/* : unknown */.left/* : unknown */()/* : unknown */, tuple/* : unknown */.right/* : unknown */()/* : unknown */];
	}
	foldInvocationStart(state : DivideState, c : string) : DivideState {
		let appended = state/* : DivideState */.append/* : unknown */(c/* : string */)/* : unknown */;
		if (c/* : string */ === "("){
			let enter = appended/* : unknown */.enter/* : unknown */()/* : unknown */;
			if (enter/* : unknown */.isShallow/* : unknown */()/* : unknown */){
				return enter/* : unknown */.advance/* : unknown */()/* : unknown */;
			}
			return enter/* : unknown */;
		}
		if (c/* : string */ === ")"){
			return appended/* : unknown */.exit/* : unknown */()/* : unknown */;
		}
		return appended/* : unknown */;
	}
	parseDataAccess(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return this/* : Main */.last/* : unknown */(input/* : string */.strip/* : unknown */()/* : unknown */, ".", (parentString, rawProperty) => {
			let property = rawProperty/* : unknown */.strip/* : unknown */()/* : unknown */;
			if (!isSymbol/* : (arg0 : string) => boolean */(property/* : unknown */)/* : unknown */){
				return new None()/* : None */;
			}
			let tuple = this/* : Main */.parseValue/* : unknown */(state/* : CompileState */, parentString/* : unknown */, depth/* : number */)/* : unknown */;
			let parent = tuple/* : unknown */.right/* : unknown */()/* : unknown */;
			let parentType = this/* : Main */.resolveValue/* : unknown */(tuple/* : unknown */.left/* : unknown */()/* : unknown */, parent/* : unknown */)/* : unknown */;
			let type : Type = Primitive/* : Primitive */.Unknown/* : unknown */;
			if (parentType/* : unknown */._UnknownVariant/* : unknown */ === UnknownVariant.StructureType/* : unknown */){
				if (/* structureType.find(property) instanceof Some */(/* var memberType */)/* : unknown */){
				let structureType : StructureType = parentType/* : unknown */ as StructureType;
					type/* : Type */ = /* memberType */;
				}
			}
			return new Some([tuple/* : unknown */.left/* : unknown */()/* : unknown */, new DataAccess(parent/* : unknown */, property/* : unknown */, type/* : Type */)/* : DataAccess */])/* : Some */;
		})/* : unknown */;
	}
	parseString(state : CompileState, input : string) : Option<[CompileState, Value]> {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */.startsWith/* : unknown */("\"")/* : unknown */ && stripped/* : unknown */.endsWith/* : unknown */("\"")/* : unknown */){
			return new Some([state/* : CompileState */, new StringValue(stripped/* : unknown */.substring/* : unknown */(1/* : number */, Strings/* : Strings */.length/* : unknown */(stripped/* : unknown */)/* : unknown */ - 1/* : number */)/* : unknown */)/* : StringValue */])/* : Some */;
		}
		return new None()/* : None */;
	}
	parseSymbolValue(state : CompileState, value : string) : Option<[CompileState, Value]> {
		let stripped = value/* : string */.strip/* : unknown */()/* : unknown */;
		if (isSymbol/* : (arg0 : string) => boolean */(stripped/* : unknown */)/* : unknown */){
			if (/* state.resolveValue(stripped) instanceof Some */(/* var type */)/* : unknown */){
				return new Some([state/* : CompileState */, new SymbolValue(stripped/* : unknown */, /* type */)/* : SymbolValue */])/* : Some */;
			}
			if (/* state.resolveType(stripped) instanceof Some */(/* var type */)/* : unknown */){
				return new Some([state/* : CompileState */, new SymbolValue(stripped/* : unknown */, /* type */)/* : SymbolValue */])/* : Some */;
			}
			return new Some([state/* : CompileState */, new Placeholder(stripped/* : unknown */)/* : Placeholder */])/* : Some */;
		}
		return new None()/* : None */;
	}
	parseOperation(state : CompileState, value : string, depth : number, operator : Operator) : Option<[CompileState, Value]> {
		return this/* : Main */.first/* : unknown */(value/* : string */, operator/* : Operator */.sourceRepresentation/* : unknown */, (leftString, rightString) => {
			let leftTuple = this/* : Main */.parseValue/* : unknown */(state/* : CompileState */, leftString/* : unknown */, depth/* : number */)/* : unknown */;
			let rightTuple = this/* : Main */.parseValue/* : unknown */(leftTuple/* : unknown */.left/* : unknown */()/* : unknown */, rightString/* : unknown */, depth/* : number */)/* : unknown */;
			let left = leftTuple/* : unknown */.right/* : unknown */()/* : unknown */;
			let right = rightTuple/* : unknown */.right/* : unknown */()/* : unknown */;
			return new Some([rightTuple/* : unknown */.left/* : unknown */()/* : unknown */, new Operation(left/* : unknown */, operator/* : Operator */, right/* : unknown */)/* : Operation */])/* : Some */;
		})/* : unknown */;
	}
	parseValuesOrEmpty<T>(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) : [CompileState, List<T>] {
		return this/* : Main */.parseValues/* : unknown */(state/* : CompileState */, input/* : string */, mapper/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]> */)/* : unknown */.orElseGet/* : unknown */(() => [state/* : CompileState */, Lists/* : Lists */.empty/* : unknown */()/* : unknown */])/* : unknown */;
	}
	parseValues<T>(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) : Option<[CompileState, List<T>]> {
		return this/* : Main */.parseValuesWithIndices/* : unknown */(state/* : CompileState */, input/* : string */, (state1, tuple) => mapper/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]> */.apply/* : unknown */(state1/* : unknown */, tuple/* : unknown */.right/* : unknown */()/* : unknown */)/* : unknown */)/* : unknown */;
	}
	parseValuesWithIndices<T>(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]>) : Option<[CompileState, List<T>]> {
		return this/* : Main */.parseAllWithIndices/* : unknown */(state/* : CompileState */, input/* : string */, this/* : Main */.foldValueChar/* : unknown */, mapper/* : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]> */)/* : unknown */;
	}
	parseParameter(state : CompileState, input : string) : [CompileState, Parameter] {
		if (Strings/* : Strings */.isBlank/* : unknown */(input/* : string */)/* : unknown */){
			return [state/* : CompileState */, new Whitespace()/* : Whitespace */];
		}
		return this/* : Main */.parseDefinition/* : unknown */(state/* : CompileState */, input/* : string */)/* : unknown */.map/* : unknown */((tuple) => [tuple/* : unknown */.left/* : unknown */()/* : unknown */, tuple/* : unknown */.right/* : unknown */()/* : unknown */])/* : unknown */.orElseGet/* : unknown */(() => [state/* : CompileState */, new Placeholder(input/* : string */)/* : Placeholder */])/* : unknown */;
	}
	parseField(input : string, depth : number, state : CompileState) : Option<[CompileState, IncompleteClassSegment]> {
		return this/* : Main */.suffix/* : unknown */(input/* : string */.strip/* : unknown */()/* : unknown */, ";", (withoutEnd) => {
			return this/* : Main */.parseClassInitialization/* : unknown */(depth/* : number */, state/* : CompileState */, withoutEnd/* : unknown */)/* : unknown */.or/* : unknown */(() => {
				return this/* : Main */.parseClassDefinition/* : unknown */(depth/* : number */, state/* : CompileState */, withoutEnd/* : unknown */)/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	parseClassDefinition(depth : number, state : CompileState, withoutEnd : string) : Option<[CompileState, IncompleteClassSegment]> {
		return this/* : Main */.parseDefinition/* : unknown */(state/* : CompileState */, withoutEnd/* : string */)/* : unknown */.map/* : unknown */((result) => {
			return [result/* : unknown */.left/* : unknown */()/* : unknown */, new ClassDefinition(depth/* : number */, result/* : unknown */.right/* : unknown */()/* : unknown */)/* : ClassDefinition */];
		})/* : unknown */;
	}
	parseClassInitialization(depth : number, state : CompileState, withoutEnd : string) : Option<[CompileState, IncompleteClassSegment]> {
		return this/* : Main */.first/* : unknown */(withoutEnd/* : string */, "=", (s, s2) => {
			return this/* : Main */.parseDefinition/* : unknown */(state/* : CompileState */, s/* : unknown */)/* : unknown */.map/* : unknown */((result) => {
				let valueTuple = this/* : Main */.parseValue/* : unknown */(result/* : unknown */.left/* : unknown */()/* : unknown */, s2/* : unknown */, depth/* : number */)/* : unknown */;
				return [valueTuple/* : unknown */.left/* : unknown */()/* : unknown */, new ClassInitialization(depth/* : number */, result/* : unknown */.right/* : unknown */()/* : unknown */, valueTuple/* : unknown */.right/* : unknown */()/* : unknown */)/* : ClassInitialization */];
			})/* : unknown */;
		})/* : unknown */;
	}
	parseDefinition(state : CompileState, input : string) : Option<[CompileState, Definition]> {
		return this/* : Main */.last/* : unknown */(input/* : string */.strip/* : unknown */()/* : unknown */, " ", (beforeName, name) => {
			return this/* : Main */.split/* : unknown */(() => this/* : Main */.toLast/* : unknown */(beforeName/* : unknown */, " ", this/* : Main */.foldTypeSeparator/* : unknown */)/* : unknown */, (beforeType, type) => {
				return this/* : Main */.last/* : unknown */(beforeType/* : unknown */, "\n", (s, s2) => {
					let annotations = this/* : Main */.parseAnnotations/* : unknown */(s/* : unknown */)/* : unknown */;
					return this/* : Main */.getOr/* : unknown */(state/* : CompileState */, name/* : unknown */, s2/* : unknown */, type/* : unknown */, annotations/* : unknown */)/* : unknown */;
				})/* : unknown */.or/* : unknown */(() => {
					return this/* : Main */.getOr/* : unknown */(state/* : CompileState */, name/* : unknown */, beforeType/* : unknown */, type/* : unknown */, Lists/* : Lists */.empty/* : unknown */()/* : unknown */)/* : unknown */;
				})/* : unknown */;
			})/* : unknown */.or/* : unknown */(() => this/* : Main */.assembleDefinition/* : unknown */(state/* : CompileState */, Lists/* : Lists */.empty/* : unknown */()/* : unknown */, Lists/* : Lists */.empty/* : unknown */()/* : unknown */, name/* : unknown */, Lists/* : Lists */.empty/* : unknown */()/* : unknown */, beforeName/* : unknown */)/* : unknown */)/* : unknown */;
		})/* : unknown */;
	}
	getOr(state : CompileState, name : string, beforeType : string, type : string, annotations : List<string>) : Option<[CompileState, Definition]> {
		return this/* : Main */.suffix/* : unknown */(beforeType/* : string */.strip/* : unknown */()/* : unknown */, ">", (withoutTypeParamStart) => {
			return this/* : Main */.first/* : unknown */(withoutTypeParamStart/* : unknown */, "<", (beforeTypeParams, typeParamsString) => {
				let typeParams = this/* : Main */.parseValuesOrEmpty/* : unknown */(state/* : CompileState */, typeParamsString/* : unknown */, (state1, s) => new Some([state1/* : unknown */, s/* : unknown */.strip/* : unknown */()/* : unknown */])/* : Some */)/* : unknown */;
				return this/* : Main */.assembleDefinition/* : unknown */(typeParams/* : unknown */.left/* : unknown */()/* : unknown */, annotations/* : List<string> */, this/* : Main */.parseModifiers/* : unknown */(beforeTypeParams/* : unknown */)/* : unknown */, name/* : string */, typeParams/* : unknown */.right/* : unknown */()/* : unknown */, type/* : string */)/* : unknown */;
			})/* : unknown */;
		})/* : unknown */.or/* : unknown */(() => {
			return this/* : Main */.assembleDefinition/* : unknown */(state/* : CompileState */, annotations/* : List<string> */, this/* : Main */.parseModifiers/* : unknown */(beforeType/* : string */)/* : unknown */, name/* : string */, Lists/* : Lists */.empty/* : unknown */()/* : unknown */, type/* : string */)/* : unknown */;
		})/* : unknown */;
	}
	parseModifiers(modifiers : string) : List<string> {
		return this/* : Main */.divideAll/* : unknown */(modifiers/* : string */.strip/* : unknown */()/* : unknown */, (state1, c) => this/* : Main */.foldByDelimiter/* : unknown */(state1/* : unknown */, c/* : unknown */, " ")/* : unknown */)/* : unknown */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(/* String */.strip/* : unknown */)/* : unknown */.filter/* : unknown */((value) => !value/* : unknown */.isEmpty/* : unknown */()/* : unknown */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
	}
	toLast(input : string, separator : string, folder : (arg0 : DivideState, arg1 : string) => DivideState) : Option<[string, string]> {
		let divisions = this/* : Main */.divideAll/* : unknown */(input/* : string */, folder/* : (arg0 : DivideState, arg1 : string) => DivideState */)/* : unknown */;
		return divisions/* : unknown */.removeLast/* : unknown */()/* : unknown */.map/* : unknown */((removed) => {
			let left = removed/* : unknown */.left/* : unknown */()/* : unknown */.iterate/* : unknown */()/* : unknown */.collect/* : unknown */(new Joiner(separator/* : string */)/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
			let right = removed/* : unknown */.right/* : unknown */()/* : unknown */;
			return [left/* : unknown */, right/* : unknown */];
		})/* : unknown */;
	}
	foldTypeSeparator(state : DivideState, c : string) : DivideState {
		if (c/* : string */ === " " && state/* : DivideState */.isLevel/* : unknown */()/* : unknown */){
			return state/* : DivideState */.advance/* : unknown */()/* : unknown */;
		}
		let appended = state/* : DivideState */.append/* : unknown */(c/* : string */)/* : unknown */;
		if (c/* : string */ === /*  ' */ < /* ' */){
			return appended/* : unknown */.enter/* : unknown */()/* : unknown */;
		}
		if (c/* : string */ === ">"){
			return appended/* : unknown */.exit/* : unknown */()/* : unknown */;
		}
		return appended/* : unknown */;
	}
	assembleDefinition(state : CompileState, annotations : List<string>, modifiers : List<string>, rawName : string, typeParams : List<string>, type : string) : Option<[CompileState, Definition]> {
		return this/* : Main */.parseType/* : unknown */(state/* : CompileState */.withTypeParams/* : unknown */(typeParams/* : List<string> */)/* : unknown */, type/* : string */)/* : unknown */.flatMap/* : unknown */((type1) => {
			let stripped = rawName/* : string */.strip/* : unknown */()/* : unknown */;
			if (!isSymbol/* : (arg0 : string) => boolean */(stripped/* : unknown */)/* : unknown */){
				return new None()/* : None */;
			}
			let newModifiers = modifiers/* : List<string> */.iterate/* : unknown */()/* : unknown */.filter/* : unknown */((value) => !this/* : Main */.isAccessor/* : unknown */(value/* : unknown */)/* : unknown */)/* : unknown */.map/* : unknown */((modifier) => /* modifier.equals("final") ? "readonly" : modifier */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
			let node = new Definition(annotations/* : List<string> */, newModifiers/* : unknown */, stripped/* : unknown */, type1/* : unknown */.right/* : unknown */()/* : unknown */, typeParams/* : List<string> */)/* : Definition */;
			return new Some([type1/* : unknown */.left/* : unknown */()/* : unknown */, node/* : unknown */])/* : Some */;
		})/* : unknown */;
	}
	isAccessor(value : string) : boolean {
		return value/* : string */ === "private";
	}
	foldValueChar(state : DivideState, c : string) : DivideState {
		if (c/* : string */ === "," && state/* : DivideState */.isLevel/* : unknown */()/* : unknown */){
			return state/* : DivideState */.advance/* : unknown */()/* : unknown */;
		}
		let appended = state/* : DivideState */.append/* : unknown */(c/* : string */)/* : unknown */;
		if (c/* : string */ === /*  ' */ - /* ' */){
			let peeked = appended/* : unknown */.peek/* : unknown */()/* : unknown */;
			if (peeked/* : unknown */ === ">"){
				return appended/* : unknown */.popAndAppendToOption/* : unknown */()/* : unknown */.orElse/* : unknown */(appended/* : unknown */)/* : unknown */;
			}
			else {
				return appended/* : unknown */;
			}
		}
		if (c/* : string */ === /*  ' */ < /* '  */ || c/* : string */ === "(" || c/* : string */ === "{"){
			return appended/* : unknown */.enter/* : unknown */()/* : unknown */;
		}
		if (c/* : string */ === ">" || c/* : string */ === ")" || c/* : string */ === "}"){
			return appended/* : unknown */.exit/* : unknown */()/* : unknown */;
		}
		return appended/* : unknown */;
	}
	parseType(state : CompileState, input : string) : Option<[CompileState, Type]> {
		let stripped = input/* : string */.strip/* : unknown */()/* : unknown */;
		if (stripped/* : unknown */ === "int" || stripped/* : unknown */ === "Integer"){
			return new Some([state/* : CompileState */, Primitive/* : Primitive */.Int/* : unknown */])/* : Some */;
		}
		if (stripped/* : unknown */ === "String" || stripped/* : unknown */ === "char" || stripped/* : unknown */ === "Character"){
			return new Some([state/* : CompileState */, Primitive/* : Primitive */.String/* : unknown */])/* : Some */;
		}
		if (stripped/* : unknown */ === "var"){
			return new Some([state/* : CompileState */, Primitive/* : Primitive */.Unknown/* : unknown */])/* : Some */;
		}
		if (stripped/* : unknown */ === "boolean"){
			return new Some([state/* : CompileState */, Primitive/* : Primitive */.Boolean/* : unknown */])/* : Some */;
		}
		if (stripped/* : unknown */ === "void"){
			return new Some([state/* : CompileState */, Primitive/* : Primitive */.Void/* : unknown */])/* : Some */;
		}
		if (isSymbol/* : (arg0 : string) => boolean */(stripped/* : unknown */)/* : unknown */){
			if (/* state.resolveType(stripped) instanceof Some */(/* var resolved */)/* : unknown */){
				return new Some([state/* : CompileState */, /* resolved */])/* : Some */;
			}
			else {
				return new Some([state/* : CompileState */, new Placeholder(stripped/* : unknown */)/* : Placeholder */])/* : Some */;
			}
		}
		return this/* : Main */.parseTemplate/* : unknown */(state/* : CompileState */, input/* : string */)/* : unknown */.or/* : unknown */(() => this/* : Main */.varArgs/* : unknown */(state/* : CompileState */, input/* : string */)/* : unknown */)/* : unknown */;
	}
	varArgs(state : CompileState, input : string) : Option<[CompileState, Type]> {
		return this/* : Main */.suffix/* : unknown */(input/* : string */, "...", (s) => {
			return this/* : Main */.parseType/* : unknown */(state/* : CompileState */, s/* : unknown */)/* : unknown */.map/* : unknown */((inner) => {
				let newState = inner/* : unknown */.left/* : unknown */()/* : unknown */;
				let child = inner/* : unknown */.right/* : unknown */()/* : unknown */;
				return [newState/* : unknown */, new ArrayType(child/* : unknown */)/* : ArrayType */];
			})/* : unknown */;
		})/* : unknown */;
	}
	assembleTemplate(base : string, state : CompileState, arguments : List<Argument>) : [CompileState, Type] {
		let children = arguments/* : List<Argument> */.iterate/* : unknown */()/* : unknown */.map/* : unknown */(this/* : Main */.retainType/* : unknown */)/* : unknown */.flatMap/* : unknown */(Queries/* : Queries */.fromOption/* : unknown */)/* : unknown */.collect/* : unknown */(new ListCollector()/* : ListCollector */)/* : unknown */;
		if (base/* : string */ === "BiFunction"){
			return [state/* : CompileState */, new FunctionType(Lists/* : Lists */.of/* : unknown */(children/* : unknown */.get/* : unknown */(0/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */, children/* : unknown */.get/* : unknown */(1/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : unknown */, children/* : unknown */.get/* : unknown */(2/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : FunctionType */];
		}
		if (base/* : string */ === "Function"){
			return [state/* : CompileState */, new FunctionType(Lists/* : Lists */.of/* : unknown */(children/* : unknown */.get/* : unknown */(0/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : unknown */, children/* : unknown */.get/* : unknown */(1/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : FunctionType */];
		}
		if (base/* : string */ === "Predicate"){
			return [state/* : CompileState */, new FunctionType(Lists/* : Lists */.of/* : unknown */(children/* : unknown */.get/* : unknown */(0/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : unknown */, Primitive/* : Primitive */.Boolean/* : unknown */)/* : FunctionType */];
		}
		if (base/* : string */ === "Supplier"){
			return [state/* : CompileState */, new FunctionType(Lists/* : Lists */.empty/* : unknown */()/* : unknown */, children/* : unknown */.get/* : unknown */(0/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : FunctionType */];
		}
		if (base/* : string */ === "Consumer"){
			return [state/* : CompileState */, new FunctionType(Lists/* : Lists */.of/* : unknown */(children/* : unknown */.get/* : unknown */(0/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : unknown */, Primitive/* : Primitive */.Void/* : unknown */)/* : FunctionType */];
		}
		if (base/* : string */ === "Tuple2" && children/* : unknown */.size/* : unknown */()/* : unknown */ >= 2/* : number */){
			return [state/* : CompileState */, new TupleType(children/* : unknown */)/* : TupleType */];
		}
		if (state/* : CompileState */.resolveType/* : unknown */(base/* : string */)/* : unknown */._UnknownVariant/* : unknown */ === UnknownVariant.Some/* : unknown */){
			let baseType = some/* : Some<Type> */.value/* : unknown */;
			if (baseType/* : unknown */._UnknownVariant/* : unknown */ === UnknownVariant.ObjectRefType/* : unknown */){
			let some : Some<Type> = state/* : CompileState */.resolveType/* : unknown */(base/* : string */)/* : unknown */ as Some<Type>;
				let findableType : ObjectRefType = baseType/* : unknown */ as ObjectRefType;
				return [state/* : CompileState */, new Template(findableType/* : ObjectRefType */, children/* : unknown */)/* : Template */];
			}
		}
		return [state/* : CompileState */, new Template(new ObjectRefType(base/* : string */)/* : ObjectRefType */, children/* : unknown */)/* : Template */];
	}
	parseTemplate(state : CompileState, input : string) : Option<[CompileState, Type]> {
		return this/* : Main */.suffix/* : unknown */(input/* : string */.strip/* : unknown */()/* : unknown */, ">", (withoutEnd) => {
			return this/* : Main */.first/* : unknown */(withoutEnd/* : unknown */, "<", (base, argumentsString) => {
				let strippedBase = base/* : unknown */.strip/* : unknown */()/* : unknown */;
				return this/* : Main */.parseValues/* : unknown */(state/* : CompileState */, argumentsString/* : unknown */, this/* : Main */.parseArgument/* : unknown */)/* : unknown */.map/* : unknown */((argumentsTuple) => {
					return this/* : Main */.assembleTemplate/* : unknown */(strippedBase/* : unknown */, argumentsTuple/* : unknown */.left/* : unknown */()/* : unknown */, argumentsTuple/* : unknown */.right/* : unknown */()/* : unknown */)/* : unknown */;
				})/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	retainType(argument : Argument) : Option<Type> {
		if (argument/* : Argument */._UnknownVariant/* : unknown */ === UnknownVariant.Type/* : unknown */){
			let type : Type = argument/* : Argument */ as Type;
			return new Some(type/* : Type */)/* : Some */;
		}
		else {
			return new None<Type>()/* : None<Type> */;
		}
	}
	parseArgument(state : CompileState, input : string) : Option<[CompileState, Argument]> {
		if (Strings/* : Strings */.isBlank/* : unknown */(input/* : string */)/* : unknown */){
			return new Some([state/* : CompileState */, new Whitespace()/* : Whitespace */])/* : Some */;
		}
		return this/* : Main */.parseType/* : unknown */(state/* : CompileState */, input/* : string */)/* : unknown */.map/* : unknown */((tuple) => [tuple/* : unknown */.left/* : unknown */()/* : unknown */, tuple/* : unknown */.right/* : unknown */()/* : unknown */])/* : unknown */;
	}
	last<T>(input : string, infix : string, mapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return this/* : Main */.infix/* : unknown */(input/* : string */, infix/* : string */, this/* : Main */.findLast/* : unknown */, mapper/* : (arg0 : string, arg1 : string) => Option<T> */)/* : unknown */;
	}
	findLast(input : string, infix : string) : Option<number> {
		let index = input/* : string */.lastIndexOf/* : unknown */(infix/* : string */)/* : unknown */;
		if (index/* : unknown */ === -1/* : number */){
			return new None<number>()/* : None<number> */;
		}
		return new Some(index/* : unknown */)/* : Some */;
	}
	first<T>(input : string, infix : string, mapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return this/* : Main */.infix/* : unknown */(input/* : string */, infix/* : string */, this/* : Main */.findFirst/* : unknown */, mapper/* : (arg0 : string, arg1 : string) => Option<T> */)/* : unknown */;
	}
	split<T>(splitter : () => Option<[string, string]>, splitMapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return splitter/* : () => Option<[string, string]> */.get/* : unknown */()/* : unknown */.flatMap/* : unknown */((splitTuple) => splitMapper/* : (arg0 : string, arg1 : string) => Option<T> */.apply/* : unknown */(splitTuple/* : unknown */.left/* : unknown */()/* : unknown */, splitTuple/* : unknown */.right/* : unknown */()/* : unknown */)/* : unknown */)/* : unknown */;
	}
	infix<T>(input : string, infix : string, locator : (arg0 : string, arg1 : string) => Option<number>, mapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return this/* : Main */.split/* : unknown */(() => locator/* : (arg0 : string, arg1 : string) => Option<number> */.apply/* : unknown */(input/* : string */, infix/* : string */)/* : unknown */.map/* : unknown */((index) => {
			let left = input/* : string */.substring/* : unknown */(0/* : number */, index/* : unknown */)/* : unknown */;
			let right = input/* : string */.substring/* : unknown */(index/* : unknown */ + Strings/* : Strings */.length/* : unknown */(infix/* : string */)/* : unknown */)/* : unknown */;
			return [left/* : unknown */, right/* : unknown */];
		})/* : unknown */, mapper/* : (arg0 : string, arg1 : string) => Option<T> */)/* : unknown */;
	}
	findFirst(input : string, infix : string) : Option<number> {
		let index = input/* : string */.indexOf/* : unknown */(infix/* : string */)/* : unknown */;
		if (index/* : unknown */ === -1/* : number */){
			return new None<number>()/* : None<number> */;
		}
		return new Some(index/* : unknown */)/* : Some */;
	}
}
/*  */