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
/* private sealed */interface Type/*  */ {
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
	hasVariant(name : string) : boolean;
	findName() : string;
}
enum FindableTypeVariant {
	ObjectType,
	Placeholder,
	Template
}
/* private sealed */interface FindableType/*  */ {
	_FindableTypeVariant : FindableTypeVariant;
	find(name : string) : Option<Type>;
	findBase() : Option<BaseType>;
}
enum DefinitionVariant {
	ImmutableDefinition
}
/* private sealed */interface Definition/*  */ {
	_DefinitionVariant : DefinitionVariant;
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
	EnumValues,
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
	_OptionVariant : OptionVariant = OptionVariant.None;
	public map<R>(mapper : (arg0 : T) => R) : Option<R> {
		return new None();
	}
	public isPresent() : boolean {
		return false;
	}
	public orElse(other : T) : T {
		return other;
	}
	public filter(predicate : (arg0 : T) => boolean) : Option<T> {
		return new None();
	}
	public orElseGet(supplier : () => T) : T {
		return supplier();
	}
	public or(other : () => Option<T>) : Option<T> {
		return other();
	}
	public flatMap<R>(mapper : (arg0 : T) => Option<R>) : Option<R> {
		return new None();
	}
	public isEmpty() : boolean {
		return true;
	}
	public and<R>(other : () => Option<R>) : Option<[T, R]> {
		return new None();
	}
	public ifPresent(consumer : (arg0 : T) => void) : void {
	}
}
/* private */class Some<T>/*  */ implements Option<T> {
	value : T;
	constructor (value : T) {
		this.value = value;
	}
	_OptionVariant : OptionVariant = OptionVariant.Some;
	public map<R>(mapper : (arg0 : T) => R) : Option<R> {
		return new Some(mapper(this.value));
	}
	public isPresent() : boolean {
		return true;
	}
	public orElse(other : T) : T {
		return this.value;
	}
	public filter(predicate : (arg0 : T) => boolean) : Option<T> {
		if (predicate(this.value)){
			return this;
		}
		return new None();
	}
	public orElseGet(supplier : () => T) : T {
		return this.value;
	}
	public or(other : () => Option<T>) : Option<T> {
		return this;
	}
	public flatMap<R>(mapper : (arg0 : T) => Option<R>) : Option<R> {
		return mapper(this.value);
	}
	public isEmpty() : boolean {
		return false;
	}
	public and<R>(other : () => Option<R>) : Option<[T, R]> {
		return other().map((otherValue : R) => [this.value, otherValue]);
	}
	public ifPresent(consumer : (arg0 : T) => void) : void {
		/* consumer.accept(this.value) */;
	}
}
/* private static */class SingleHead<T>/*  */ implements Head<T> {
	readonly retrievableValue : T;
	retrieved : boolean;
	constructor (retrievableValue : T) {
		this.retrievableValue = retrievableValue;
		this.retrieved = false;
	}
	public next() : Option<T> {
		if (this.retrieved){
			return new None();
		}
		this.retrieved = true;
		return new Some(this.retrievableValue);
	}
}
/* private static */class EmptyHead<T>/*  */ implements Head<T> {
	public next() : Option<T> {
		return new None();
	}
}
/* private */class HeadedQuery<T>/*  */ implements Query<T> {
	head : Head<T>;
	constructor (head : Head<T>) {
		this.head = head;
	}
	public fold<R>(initial : R, folder : (arg0 : R, arg1 : T) => R) : R {
		let current : R = initial;
		while (true){
			let finalCurrent : R = current;
			let option : Option<R> = this.head.next().map((inner : T) => folder(finalCurrent, inner));
			if (option._OptionVariant === OptionVariant.Some){
				let some : Some<R> = option as Some<R>;
				current = some.value;
			}
			else {
				return current;
			}
		}
	}
	public map<R>(mapper : (arg0 : T) => R) : Query<R> {
		return new HeadedQuery(new MapHead(this.head, mapper));
	}
	public collect<R>(collector : Collector<T, R>) : R {
		return this.fold(collector.createInitial(), collector.fold);
	}
	public filter(predicate : (arg0 : T) => boolean) : Query<T> {
		return this.flatMap((element : T) => {
			if (predicate(element)){
				return new HeadedQuery(new SingleHead(element));
			}
			return new HeadedQuery(new EmptyHead());
		});
	}
	public next() : Option<T> {
		return this.head.next();
	}
	public flatMap<R>(f : (arg0 : T) => Query<R>) : Query<R> {
		return new HeadedQuery(new FlatMapHead(this.head, f));
	}
	public zip<R>(other : Query<R>) : Query<[T, R]> {
		return new HeadedQuery(new ZipHead(this.head, other));
	}
}
/* private static */class RangeHead/*  */ implements Head<number> {
	readonly length : number;
	counter : number;
	constructor (length : number) {
		this.length = length;
		this.counter = 0;
	}
	public next() : Option<number> {
		if (this.counter < this.length){
			let value : number = this.counter;
			/* this.counter++ */;
			return new Some(value);
		}
		return new None();
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
		this.annotations = annotations;
		this.modifiers = modifiers;
		this.name = name;
		this.type = type;
		this.typeParams = typeParams;
	}
	_DefinitionVariant : DefinitionVariant = DefinitionVariant.ImmutableDefinition;
	public static createSimpleDefinition(name : string, type : Type) : Definition {
		return new ImmutableDefinition(Lists.empty(), Lists.empty(), name, type, Lists.empty());
	}
	public findName() : string {
		return this.name;
	}
	public findType() : Type {
		return this.type;
	}
	public generate() : string {
		return this.generateWithParams("");
	}
	generateType() : string {
		if (this.type === Primitive.Unknown){
			return "";
		}
		return " : " + this.type.generate();
	}
	joinModifiers() : string {
		return this.modifiers.query().map((value : string) => value + " ").collect(new Joiner("")).orElse("");
	}
	joinTypeParams() : string {
		return this.typeParams.query().collect(new Joiner(", ")).map((inner) => "<" + inner + ">").orElse("");
	}
	public mapType(mapper : (arg0 : Type) => Type) : Definition {
		return new ImmutableDefinition(this.annotations, this.modifiers, this.name, mapper(this.type), this.typeParams);
	}
	public generateWithParams(joinedParameters : string) : string {
		let joinedAnnotations = this.annotations.query().map((value : string) => "@" + value + " ").collect(Joiner.empty()).orElse("");
		let joined : string = this.joinTypeParams();
		let before : string = this.joinModifiers();
		let typeString : string = this.generateType();
		return joinedAnnotations + before + this.name + joined + joinedParameters + typeString;
	}
	public createDefinition(paramTypes : List<Type>) : Definition {
		let type1 : Type = new FunctionType(paramTypes, this.type);
		return new ImmutableDefinition(this.annotations, this.modifiers, this.name, type1, this.typeParams);
	}
	public containsAnnotation(annotation : string) : boolean {
		return this.annotations.contains(annotation);
	}
	public removeAnnotations() : Definition {
		return new ImmutableDefinition(Lists.empty(), this.modifiers, this.name, this.type, this.typeParams);
	}
	public toString() : string {
		return "ImmutableDefinition[" + "annotations=" + this.annotations + ", " + "maybeBefore=" + this.modifiers + ", " + "findName=" + this.name + ", " + "findType=" + this.type + ", " + "typeParams=" + this.typeParams + "]";
	}
}
/* private */class ObjectType/*  */ implements FindableType, BaseType {
	name : string;
	typeParams : List<string>;
	definitions : List<Definition>;
	variants : List<string>;
	constructor (name : string, typeParams : List<string>, definitions : List<Definition>, variants : List<string>) {
		this.name = name;
		this.typeParams = typeParams;
		this.definitions = definitions;
		this.variants = variants;
	}
	_FindableTypeVariant : FindableTypeVariant = FindableTypeVariant.ObjectType;
	public generate() : string {
		return this.name;
	}
	public replace(mapping : Map<string, Type>) : Type {
		return new ObjectType(this.name, this.typeParams, this.definitions.query().map((definition : Definition) => definition.mapType((type : Type) => type.replace(mapping))).collect(new ListCollector()), this.variants);
	}
	public find(name : string) : Option<Type> {
		return this.definitions.query().filter((definition : Definition) => definition.findName() === name).map(Definition.findType).next();
	}
	public findBase() : Option<BaseType> {
		return new Some(this);
	}
	public hasVariant(name : string) : boolean {
		return this.variants().contains(name);
	}
	public findName() : string {
		return this.name;
	}
}
/* private */class TypeParam/*  */ implements Type {
	value : string;
	constructor (value : string) {
		this.value = value;
	}
	public generate() : string {
		return this.value;
	}
	public replace(mapping : Map<string, Type>) : Type {
		return mapping.find(this.value).orElse(this);
	}
	public findName() : string {
		return "";
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
		this.structures = structures;
		this.definitions = definitions;
		this.objectTypes = objectTypes;
		this.structNames = structNames;
		this.typeParams = typeParams;
		this.typeRegister = typeRegister;
		this.functionSegments = functionSegments;
	}
	public static createInitial() : CompileState {
		return new CompileState(Lists.empty(), Lists.of(Lists.empty()), Lists.empty(), Lists.empty(), Lists.empty(), new None(), Lists.empty());
	}
	resolveValue(name : string) : Option<Type> {
		return this.definitions.iterateReversed().flatMap(List.query).filter((definition : T) => definition.findName() === name).next().map(Definition.findType);
	}
	public addStructure(structure : string) : CompileState {
		return new CompileState(this.structures.addLast(structure), this.definitions, this.objectTypes, this.structNames, this.typeParams, this.typeRegister, this.functionSegments);
	}
	public defineAll(definitions : List<Definition>) : CompileState {
		let defined : List<List<Definition>> = this.definitions.mapLast((frame : List<Definition>) => frame.addAllLast(definitions));
		return new CompileState(this.structures, defined, this.objectTypes, this.structNames, this.typeParams, this.typeRegister, this.functionSegments);
	}
	public resolveType(name : string) : Option<Type> {
		let maybe : Option<[string, List<string>]> = this.structNames.last().filter((inner : [string, List<string>]) => inner[0] === name);
		if (maybe._OptionVariant === OptionVariant.Some){
			let some : Some<[string, List<string>]> = maybe as Some<[string, List<string>]>;
			let found : [string, List<string>] = some.value;
			return new Some(new ObjectType(found[0], this.typeParams, this.definitions.last().orElse(Lists.empty()), found[1]));
		}
		let maybeTypeParam : Option<T> = this.typeParams.query().filter((param : string) => param === name).next();
		if (maybeTypeParam._OptionVariant === OptionVariant.Some){
			let some : Some<string> = maybeTypeParam as Some<string>;
			return new Some(new TypeParam(some.value));
		}
		return this.objectTypes.query().filter((type : ObjectType) => type.name === name).next().map((type : T) => type);
	}
	public define(definition : Definition) : CompileState {
		return new CompileState(this.structures, this.definitions.mapLast((frame : List<Definition>) => frame.addLast(definition)), this.objectTypes, this.structNames, this.typeParams, this.typeRegister, this.functionSegments);
	}
	public pushStructName(definition : Tuple2Impl<string, List<string>>) : CompileState {
		return new CompileState(this.structures, this.definitions, this.objectTypes, this.structNames.addLast(definition), this.typeParams, this.typeRegister, this.functionSegments);
	}
	public withTypeParams(typeParams : List<string>) : CompileState {
		return new CompileState(this.structures, this.definitions, this.objectTypes, this.structNames, this.typeParams.addAllLast(typeParams), this.typeRegister, this.functionSegments);
	}
	public withExpectedType(type : Type) : CompileState {
		return new CompileState(this.structures, this.definitions, this.objectTypes, this.structNames, this.typeParams, new Some(type), this.functionSegments);
	}
	public popStructName() : CompileState {
		return new CompileState(this.structures, this.definitions, this.objectTypes, this.structNames.removeLast().map(Tuple2.left).orElse(this.structNames), this.typeParams, this.typeRegister, this.functionSegments);
	}
	public enterDefinitions() : CompileState {
		return new CompileState(this.structures, this.definitions.addLast(Lists.empty()), this.objectTypes, this.structNames, this.typeParams, this.typeRegister, this.functionSegments);
	}
	public exitDefinitions() : CompileState {
		let removed : T = this.definitions.removeLast().map(Tuple2.left).orElse(this.definitions);
		return new CompileState(this.structures, removed, this.objectTypes, this.structNames, this.typeParams, this.typeRegister, this.functionSegments);
	}
	public addType(thisType : ObjectType) : CompileState {
		return new CompileState(this.structures, this.definitions, this.objectTypes.addLast(thisType), this.structNames, this.typeParams, this.typeRegister, this.functionSegments);
	}
	public addFunctionSegment(segment : FunctionSegment) : CompileState {
		return new CompileState(this.structures, this.definitions, this.objectTypes, this.structNames, this.typeParams, this.typeRegister, this.functionSegments.addLast(segment));
	}
	public clearFunctionSegments() : CompileState {
		return new CompileState(this.structures, this.definitions, this.objectTypes, this.structNames, this.typeParams, this.typeRegister, Lists.empty());
	}
	isCurrentStructName(stripped : string) : boolean {
		return stripped === this.structNames.last().map(Tuple2.left).orElse("");
	}
}
/* private static */class DivideState/*  */ {
	readonly input : string;
	readonly index : number;
	depth : number;
	segments : List<string>;
	buffer : string;
	constructor (input : string, index : number, segments : List<string>, buffer : string, depth : number) {
		this.segments = segments;
		this.buffer = buffer;
		this.depth = depth;
		this.input = input;
		this.index = index;
	}
	public static createInitial(input : string) : DivideState {
		return new DivideState(input, 0, Lists.empty(), "", 0);
	}
	advance() : DivideState {
		this.segments = this.segments.addLast(this.buffer);
		this.buffer = "";
		return this;
	}
	append(c : string) : DivideState {
		this.buffer = this.buffer + c;
		return this;
	}
	public enter() : DivideState {
		/* this.depth++ */;
		return this;
	}
	public isLevel() : boolean {
		return this.depth === 0;
	}
	public exit() : DivideState {
		/* this.depth-- */;
		return this;
	}
	public isShallow() : boolean {
		return this.depth === 1;
	}
	public pop() : Option<[string, DivideState]> {
		if (this.index < this.input.length()){
			let c = this.input.charAt(this.index);
			return new Some([c, new DivideState(this.input, this.index + 1, this.segments, this.buffer, this.depth)]);
		}
		return new None();
	}
	public popAndAppendToTuple() : Option<[string, DivideState]> {
		return this.pop().map((tuple : [string, DivideState]) => {
			let c = tuple[0];
			let right = tuple[1];
			return [c, right.append(c)];
		});
	}
	public popAndAppendToOption() : Option<DivideState> {
		return this.popAndAppendToTuple().map(Tuple2.right);
	}
	public peek() : string {
		return this.input.charAt(this.index);
	}
}
/* private */class Joiner/*  */ implements Collector<string, Option<string>> {
	delimiter : string;
	constructor (delimiter : string) {
		this.delimiter = delimiter;
	}
	static empty() : Joiner {
		return new Joiner("");
	}
	public createInitial() : Option<string> {
		return new None();
	}
	public fold(current : Option<string>, element : string) : Option<string> {
		return new Some(current.map((inner : string) => inner + this.delimiter + element).orElse(element));
	}
}
/* private static */class ListCollector<T>/*  */ implements Collector<T, List<T>> {
	public createInitial() : List<T> {
		return Lists.empty();
	}
	public fold(current : List<T>, element : T) : List<T> {
		return current.addLast(element);
	}
}
/* private static */class FlatMapHead<T, R>/*  */ implements Head<R> {
	readonly mapper : (arg0 : T) => Query<R>;
	readonly head : Head<T>;
	current : Option<Query<R>>;
	constructor (head : Head<T>, mapper : (arg0 : T) => Query<R>) {
		this.mapper = mapper;
		this.current = new None();
		this.head = head;
	}
	public next() : Option<R> {
		while (true){
			if (this.current.isPresent()){
				let inner : Query<R> = this.current.orElse(/* null */);
				let maybe : Option<R> = inner.next();
				if (maybe.isPresent()){
					return maybe;
				}
				else {
					this.current = new None();
				}
			}
			let outer : Option<T> = this.head.next();
			if (outer.isPresent()){
				this.current = outer.map(this.mapper);
			}
			else {
				return new None();
			}
		}
	}
}
/* private */class ArrayType/*  */ implements Type {
	right : Type;
	constructor (right : Type) {
		this.right = right;
	}
	public generate() : string {
		return this.right.generate() + "[]";
	}
	public replace(mapping : Map<string, Type>) : Type {
		return this;
	}
	public findName() : string {
		return "";
	}
}
/* private static final */class Whitespace/*  */ implements Argument, Parameter, ClassSegment, FunctionSegment, IncompleteClassSegment {
	_IncompleteClassSegmentVariant : IncompleteClassSegmentVariant = IncompleteClassSegmentVariant.Whitespace;
	_ParameterVariant : ParameterVariant = ParameterVariant.Whitespace;
	_ArgumentVariant : ArgumentVariant = ArgumentVariant.Whitespace;
	public generate() : string {
		return "";
	}
	public maybeCreateDefinition() : Option<Definition> {
		return new None();
	}
}
/* private static */class Queries/*  */ {
	public static fromOption<T>(option : Option<T>) : Query<T> {
		let single : Option<Head<T>> = option.map(SingleHead.new);
		return new HeadedQuery(single.orElseGet(EmptyHead.new));
	}
	public static from<T>(elements : T[]) : Query<T> {
		return new HeadedQuery(new RangeHead(elements.length)).map((index : T) => /* elements[index] */);
	}
}
/* private */class FunctionType/*  */ implements Type {
	arguments : List<Type>;
	returns : Type;
	constructor (arguments : List<Type>, returns : Type) {
		this.arguments = arguments;
		this.returns = returns;
	}
	public generate() : string {
		let joined = this.arguments().iterateWithIndices().map((pair) => "arg" + pair.left() + " : " + pair.right().generate()).collect(new Joiner(", ")).orElse("");
		return "(" + joined + ") => " + this.returns.generate();
	}
	public replace(mapping : Map<string, Type>) : Type {
		return new FunctionType(this.arguments.query().map((type : Type) => type.replace(mapping)).collect(new ListCollector()), this.returns.replace(mapping));
	}
	public findName() : string {
		return "";
	}
}
/* private */class TupleType/*  */ implements Type {
	arguments : List<Type>;
	constructor (arguments : List<Type>) {
		this.arguments = arguments;
	}
	public generate() : string {
		let joinedArguments = this.arguments.query().map(Type.generate).collect(new Joiner(", ")).orElse("");
		return "[" + joinedArguments + "]";
	}
	public replace(mapping : Map<string, Type>) : Type {
		return this;
	}
	public findName() : string {
		return "";
	}
}
/* private */class Template/*  */ implements FindableType {
	base : ObjectType;
	arguments : List<Type>;
	constructor (base : ObjectType, arguments : List<Type>) {
		this.base = base;
		this.arguments = arguments;
	}
	_FindableTypeVariant : FindableTypeVariant = FindableTypeVariant.Template;
	public generate() : string {
		let joinedArguments = this.arguments.query().map(Type.generate).collect(new Joiner(", ")).map((inner) => "<" + inner + ">").orElse("");
		return this.base.generate() + joinedArguments;
	}
	public find(name : string) : Option<Type> {
		return this.base.find(name).map((found : Type) => {
			let mapping = this.base.typeParams().query().zip(this.arguments.query()).collect(new MapCollector());
			return found.replace(mapping);
		});
	}
	public findBase() : Option<BaseType> {
		return new Some(this.base);
	}
	public replace(mapping : Map<string, Type>) : Type {
		let collect : R = this.arguments.query().map((argument : Type) => argument.replace(mapping)).collect(new ListCollector());
		return new Template(this.base, collect);
	}
	public findName() : string {
		return this.base.findName();
	}
}
/* private */class Placeholder/*  */ implements Parameter, Value, FindableType, ClassSegment, FunctionSegment, BlockHeader, StatementValue, IncompleteClassSegment {
	input : string;
	constructor (input : string) {
		this.input = input;
	}
	_IncompleteClassSegmentVariant : IncompleteClassSegmentVariant = IncompleteClassSegmentVariant.Placeholder;
	_FindableTypeVariant : FindableTypeVariant = FindableTypeVariant.Placeholder;
	_ValueVariant : ValueVariant = ValueVariant.Placeholder;
	_ParameterVariant : ParameterVariant = ParameterVariant.Placeholder;
	public generate() : string {
		return generatePlaceholder(this.input);
	}
	public type() : Type {
		return Primitive.Unknown;
	}
	public find(name : string) : Option<Type> {
		return new None();
	}
	public findBase() : Option<BaseType> {
		return new None();
	}
	public replace(mapping : Map<string, Type>) : Type {
		return this;
	}
	public findName() : string {
		return "";
	}
	public maybeCreateDefinition() : Option<Definition> {
		return new None();
	}
}
/* private */class StringValue/*  */ implements Value {
	value : string;
	constructor (value : string) {
		this.value = value;
	}
	_ValueVariant : ValueVariant = ValueVariant.StringValue;
	public generate() : string {
		return "\"" + this.value + "\"";
	}
	public type() : Type {
		return Primitive.Unknown;
	}
}
/* private */class DataAccess/*  */ implements Value {
	parent : Value;
	property : string;
	type : Type;
	constructor (parent : Value, property : string, type : Type) {
		this.parent = parent;
		this.property = property;
		this.type = type;
	}
	_ValueVariant : ValueVariant = ValueVariant.DataAccess;
	public generate() : string {
		return this.parent.generate() + "." + this.property + createDebugString(this.type);
	}
	public type() : Type {
		return this.type;
	}
}
/* private */class ConstructionCaller/*  */ implements Caller {
	type : Type;
	constructor (type : Type) {
		this.type = type;
	}
	_CallerVariant : CallerVariant = CallerVariant.ConstructionCaller;
	public generate() : string {
		return "new " + this.type.generate();
	}
	public toFunction() : FunctionType {
		return new FunctionType(Lists.empty(), this.type);
	}
}
/* private */class Operator/*  */ {
	sourceRepresentation : string;
	targetRepresentation : string;
	constructor (sourceRepresentation : string, targetRepresentation : string) {
		this.sourceRepresentation = sourceRepresentation;
		this.targetRepresentation = targetRepresentation;
	}
	static ADD : Operator = new Operator("+", "+");
	static AND : Operator = new Operator("&&", "&&");
	static EQUALS : Operator = new Operator("==", "===");
	static Operator GREATER_THAN_OR_EQUALS = Operator() : /* new */;
	static Operator LESS_THAN = Operator() : /* new */;
	static OR : Operator = new Operator("||", "||");
	static SUBTRACT : Operator = new Operator("-", "-");
}
/* private */class Operation/*  */ implements Value {
	left : Value;
	operator : Operator;
	right : Value;
	constructor (left : Value, operator : Operator, right : Value) {
		this.left = left;
		this.operator = operator;
		this.right = right;
	}
	_ValueVariant : ValueVariant = ValueVariant.Operation;
	public generate() : string {
		return this.left().generate() + " " + this.operator.targetRepresentation + " " + this.right().generate();
	}
	public type() : Type {
		return Primitive.Unknown;
	}
}
/* private */class Not/*  */ implements Value {
	value : Value;
	constructor (value : Value) {
		this.value = value;
	}
	_ValueVariant : ValueVariant = ValueVariant.Not;
	public generate() : string {
		return "!" + this.value.generate();
	}
	public type() : Type {
		return Primitive.Unknown;
	}
}
/* private */class BlockLambdaValue/*  */ implements LambdaValue {
	depth : number;
	statements : List<FunctionSegment>;
	constructor (depth : number, statements : List<FunctionSegment>) {
		this.depth = depth;
		this.statements = statements;
	}
	public generate() : string {
		return "{" + this.joinStatements() + createIndent(this.depth) + "}";
	}
	joinStatements() : string {
		return this.statements.query().map(FunctionSegment.generate).collect(Joiner.empty()).orElse("");
	}
}
/* private */class Lambda/*  */ implements Value {
	parameters : List<Definition>;
	body : LambdaValue;
	constructor (parameters : List<Definition>, body : LambdaValue) {
		this.parameters = parameters;
		this.body = body;
	}
	_ValueVariant : ValueVariant = ValueVariant.Lambda;
	public generate() : string {
		let joined = this.parameters.query().map(Definition.generate).collect(new Joiner(", ")).orElse("");
		return "(" + joined + ") => " + this.body.generate();
	}
	public type() : Type {
		return Primitive.Unknown;
	}
}
/* private */class Invokable/*  */ implements Value {
	caller : Caller;
	arguments : List<Value>;
	type : Type;
	constructor (caller : Caller, arguments : List<Value>, type : Type) {
		this.caller = caller;
		this.arguments = arguments;
		this.type = type;
	}
	_ValueVariant : ValueVariant = ValueVariant.Invokable;
	public generate() : string {
		let joined = this.arguments.query().map(Value.generate).collect(new Joiner(", ")).orElse("");
		return this.caller.generate() + "(" + joined + ")" + createDebugString(this.type);
	}
}
/* private */class IndexValue/*  */ implements Value {
	parent : Value;
	child : Value;
	constructor (parent : Value, child : Value) {
		this.parent = parent;
		this.child = child;
	}
	_ValueVariant : ValueVariant = ValueVariant.IndexValue;
	public generate() : string {
		return this.parent.generate() + "[" + this.child.generate() + "]";
	}
	public type() : Type {
		return Primitive.Unknown;
	}
}
/* private */class SymbolValue/*  */ implements Value {
	stripped : string;
	type : Type;
	constructor (stripped : string, type : Type) {
		this.stripped = stripped;
		this.type = type;
	}
	_ValueVariant : ValueVariant = ValueVariant.SymbolValue;
	public generate() : string {
		return this.stripped + createDebugString(this.type);
	}
}
/* private static */class Maps/*  */ {
	public static empty<V, K>() : Map<K, V>;
}
/* private */class MapCollector<K, V>/*  */ implements Collector<[K, V], Map<K, V>> {
	public createInitial() : Map<K, V> {
		return Maps.empty();
	}
	public fold(current : Map<K, V>, element : [K, V]) : Map<K, V> {
		return current.with(element[0], element[1]);
	}
}
/* private static */class ConstructorHeader/*  */ implements Header {
	public createDefinition(paramTypes : List<Type>) : Definition {
		return ImmutableDefinition.createSimpleDefinition("new", Primitive.Unknown);
	}
	public generateWithParams(joinedParameters : string) : string {
		return "constructor " + joinedParameters;
	}
}
/* private */class FunctionNode/*  */ implements ClassSegment {
	depth : number;
	header : Header;
	parameters : List<Definition>;
	maybeStatements : Option<List<FunctionSegment>>;
	constructor (depth : number, header : Header, parameters : List<Definition>, maybeStatements : Option<List<FunctionSegment>>) {
		this.depth = depth;
		this.header = header;
		this.parameters = parameters;
		this.maybeStatements = maybeStatements;
	}
	static joinStatements(statements : List<FunctionSegment>) : string {
		return statements.query().map(FunctionSegment.generate).collect(Joiner.empty()).orElse("");
	}
	public generate() : string {
		let indent : string = createIndent(this.depth);
		let generatedHeader : string = this.header.generateWithParams(joinValues(this.parameters));
		let generatedStatements : T = this.maybeStatements.map(FunctionNode.joinStatements).map((inner : T) => " {" + inner + indent + "}").orElse(";");
		return indent + generatedHeader + generatedStatements;
	}
}
/* private */class Block/*  */ implements FunctionSegment {
	depth : number;
	header : BlockHeader;
	statements : List<FunctionSegment>;
	constructor (depth : number, header : BlockHeader, statements : List<FunctionSegment>) {
		this.depth = depth;
		this.header = header;
		this.statements = statements;
	}
	public generate() : string {
		let indent : string = createIndent(this.depth);
		let collect = this.statements.query().map(FunctionSegment.generate).collect(Joiner.empty()).orElse("");
		return indent + this.header.generate() + "{" + collect + indent + "}";
	}
}
/* private */class Conditional/*  */ implements BlockHeader {
	prefix : string;
	value1 : Value;
	constructor (prefix : string, value1 : Value) {
		this.prefix = prefix;
		this.value1 = value1;
	}
	public generate() : string {
		return this.prefix + " (" + this.value1.generate() + ")";
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
		this.value = value;
	}
	public generate() : string {
		return "return " + this.value.generate();
	}
}
/* private */class Initialization/*  */ implements StatementValue {
	definition : Definition;
	source : Value;
	constructor (definition : Definition, source : Value) {
		this.definition = definition;
		this.source = source;
	}
	public generate() : string {
		return "let " + this.definition.generate() + " = " + this.source.generate();
	}
}
/* private */class FieldInitialization/*  */ implements StatementValue {
	definition : Definition;
	source : Value;
	constructor (definition : Definition, source : Value) {
		this.definition = definition;
		this.source = source;
	}
	public generate() : string {
		return this.definition.generate() + " = " + this.source.generate();
	}
}
/* private */class Assignment/*  */ implements StatementValue {
	destination : Value;
	source : Value;
	constructor (destination : Value, source : Value) {
		this.destination = destination;
		this.source = source;
	}
	public generate() : string {
		return this.destination.generate() + " = " + this.source.generate();
	}
}
/* private */class Statement/*  */ implements FunctionSegment, ClassSegment {
	depth : number;
	value : StatementValue;
	constructor (depth : number, value : StatementValue) {
		this.depth = depth;
		this.value = value;
	}
	public generate() : string {
		return createIndent(this.depth) + this.value.generate() + ";";
	}
}
/* private */class MethodPrototype/*  */ implements IncompleteClassSegment {
	depth : number;
	header : Header;
	parameters : List<Definition>;
	content : string;
	constructor (depth : number, header : Header, parameters : List<Definition>, content : string) {
		this.depth = depth;
		this.header = header;
		this.parameters = parameters;
		this.content = content;
	}
	_IncompleteClassSegmentVariant : IncompleteClassSegmentVariant = IncompleteClassSegmentVariant.MethodPrototype;
	createDefinition() : Definition {
		return this.header.createDefinition(this.findParamTypes());
	}
	findParamTypes() : List<Type> {
		return this.parameters().query().map(Definition.findType).collect(new ListCollector());
	}
	public maybeCreateDefinition() : Option<Definition> {
		return new Some(this.header.createDefinition(this.findParamTypes()));
	}
}
/* private */class IncompleteClassSegmentWrapper/*  */ implements IncompleteClassSegment {
	segment : ClassSegment;
	constructor (segment : ClassSegment) {
		this.segment = segment;
	}
	_IncompleteClassSegmentVariant : IncompleteClassSegmentVariant = IncompleteClassSegmentVariant.IncompleteClassSegmentWrapper;
	public maybeCreateDefinition() : Option<Definition> {
		return new None();
	}
}
/* private */class ClassDefinition/*  */ implements IncompleteClassSegment {
	depth : number;
	definition : Definition;
	constructor (depth : number, definition : Definition) {
		this.depth = depth;
		this.definition = definition;
	}
	_IncompleteClassSegmentVariant : IncompleteClassSegmentVariant = IncompleteClassSegmentVariant.ClassDefinition;
	public maybeCreateDefinition() : Option<Definition> {
		return new Some(this.definition);
	}
}
/* private */class ClassInitialization/*  */ implements IncompleteClassSegment {
	depth : number;
	definition : Definition;
	value : Value;
	constructor (depth : number, definition : Definition, value : Value) {
		this.depth = depth;
		this.definition = definition;
		this.value = value;
	}
	_IncompleteClassSegmentVariant : IncompleteClassSegmentVariant = IncompleteClassSegmentVariant.ClassInitialization;
	public maybeCreateDefinition() : Option<Definition> {
		return new Some(this.definition);
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
		this.targetInfix = targetInfix;
		this.beforeInfix = beforeInfix;
		this.name = name;
		this.typeParams = typeParams;
		this.parameters = parameters;
		this.after = after;
		this.segments = segments;
		this.variants = variants;
		this.interfaces = interfaces;
	}
	_IncompleteClassSegmentVariant : IncompleteClassSegmentVariant = IncompleteClassSegmentVariant.StructurePrototype;
	createObjectType() : ObjectType {
		let definitionFromSegments : R = this.segments.query().map(IncompleteClassSegment.maybeCreateDefinition).flatMap(Queries.fromOption).collect(new ListCollector());
		return new ObjectType(this.name, this.typeParams, definitionFromSegments.addAllLast(this.parameters), this.variants);
	}
	public maybeCreateDefinition() : Option<Definition> {
		return new None();
	}
	joinInterfaces() : string {
		return this.interfaces.query().map(Type.generate).collect(new Joiner(", ")).map((inner) => " implements " + inner).orElse("");
	}
	joinTypeParams() : string {
		return this.typeParams().query().collect(new Joiner(", ")).map((inner) => "<" + inner + ">").orElse("");
	}
}
/* private */class Cast/*  */ implements Value {
	value : Value;
	type : Type;
	constructor (value : Value, type : Type) {
		this.value = value;
		this.type = type;
	}
	_ValueVariant : ValueVariant = ValueVariant.Cast;
	public generate() : string {
		return this.value.generate() + " as " + this.type.generate();
	}
}
/* private */class Ok<T, X>/*  */ implements Result<T, X> {
	value : T;
	constructor (value : T) {
		this.value = value;
	}
	_ResultVariant : ResultVariant = ResultVariant.Ok;
	public mapValue<R>(mapper : (arg0 : T) => R) : Result<R, X> {
		return new Ok(mapper(this.value));
	}
	public match<R>(whenOk : (arg0 : T) => R, whenErr : (arg0 : X) => R) : R {
		return whenOk(this.value);
	}
}
/* private */class Err<T, X>/*  */ implements Result<T, X> {
	error : X;
	constructor (error : X) {
		this.error = error;
	}
	_ResultVariant : ResultVariant = ResultVariant.Err;
	public mapValue<R>(mapper : (arg0 : T) => R) : Result<R, X> {
		return new Err(this.error);
	}
	public match<R>(whenOk : (arg0 : T) => R, whenErr : (arg0 : X) => R) : R {
		return whenErr(this.error);
	}
}
/* private */class JVMIOError/*  */ implements IOError {
	error : /* IOException */;
	constructor (error : /* IOException */) {
		this.error = error;
	}
	public display() : string {
		let writer : /* StringWriter */ = new /* StringWriter */();
		/* this.error.printStackTrace(new PrintWriter(writer)) */;
		return writer.toString();
	}
}
/* private */class TupleNode/*  */ implements Value {
	values : List<Value>;
	constructor (values : List<Value>) {
		this.values = values;
	}
	_ValueVariant : ValueVariant = ValueVariant.TupleNode;
	public generate() : string {
		let joined = this.values.query().map(Value.generate).collect(new Joiner(", ")).orElse("");
		return "[" + joined + "]";
	}
	public type() : Type {
		return new TupleType(this.values.query().map(Value.type).collect(new ListCollector()));
	}
}
/* private */class MapHead<T, R>/*  */ implements Head<R> {
	head : Head<T>;
	mapper : (arg0 : T) => R;
	constructor (head : Head<T>, mapper : (arg0 : T) => R) {
		this.head = head;
		this.mapper = mapper;
	}
	public next() : Option<R> {
		return this.head.next().map(this.mapper);
	}
}
/* private */class ZipHead<T, R>/*  */ implements Head<[T, R]> {
	head : Head<T>;
	other : Query<R>;
	constructor (head : Head<T>, other : Query<R>) {
		this.head = head;
		this.other = other;
	}
	public next() : Option<[T, R]> {
		return this.head.next().and(this.other.next);
	}
}
/* private */class EnumValue/*  */ {
	value : string;
	values : List<Value>;
	constructor (value : string, values : List<Value>) {
		this.value = value;
		this.values = values;
	}
	public generate() : string {
		let s = this.values.query().map(Value.generate).collect(new Joiner(", ")).orElse("");
		return this.value + "(" + s + ")";
	}
}
/* private */class EnumValues/*  */ implements IncompleteClassSegment, ClassSegment {
	values : List<EnumValue>;
	constructor (values : List<EnumValue>) {
		this.values = values;
	}
	_IncompleteClassSegmentVariant : IncompleteClassSegmentVariant = IncompleteClassSegmentVariant.EnumValues;
	public generate() : string {
		return this.values.query().map(EnumValue.generate).collect(new Joiner(", ")).orElse("");
	}
	public maybeCreateDefinition() : Option<Definition> {
		return new None();
	}
}
/* private */class Primitive/*  */ implements Type {
	static Int : Primitive = new Primitive("number");
	static String : Primitive = new Primitive("string");
	static Boolean : Primitive = new Primitive("boolean");
	static Unknown : Primitive = new Primitive("unknown");
	static Void : Primitive = new Primitive("void");
	readonly value : string;
	constructor (value : string) {
		this.value = value;
	}
	public generate() : string {
		return this.value;
	}
	public replace(mapping : Map<string, Type>) : Type {
		return this;
	}
	public findName() : string {
		return this.name();
	}
}
/* private */class BooleanValue/*  */ implements Value {
	_ValueVariant : ValueVariant = ValueVariant.BooleanValue;
	static True : BooleanValue = new BooleanValue("true");
	static False : BooleanValue = new BooleanValue("false");
	readonly value : string;
	constructor (value : string) {
		this.value = value;
	}
	public generate() : string {
		return this.value;
	}
	public type() : Type {
		return Primitive.Boolean;
	}
}
/* public */class Main/*  */ {
	JVMPath() : /* record */;
	static readonly isDebugEnabled : boolean = false;
	static generatePlaceholder(input : string) : string {
		let replaced = input.replace("/*", "content-start").replace("*/", "content-end");
		return "/* " + replaced + " */";
	}
	static joinValues(retainParameters : List<Definition>) : string {
		let inner = retainParameters.query().map(Definition.generate).collect(new Joiner(", ")).orElse("");
		return "(" + inner + ")";
	}
	static createIndent(depth : number) : string {
		return "\n" + "\t".repeat(depth);
	}
	static createDebugString(type : Type) : string {
		if (!Main.isDebugEnabled){
			return "";
		}
		return generatePlaceholder(": " + type.generate());
	}
	static retainFindableType(type : Type) : Option<FindableType> {
		if (type._Variant === Variant.FindableType){
			let findableType : FindableType = type as FindableType;
			return new Some(findableType);
		}
		return new None();
	}
	public main() : void {
		let parent : Path = this.findRoot();
		let source : Path = parent.resolve("Main.java");
		let target : Path = parent.resolve("main.ts");
		/* source.readString()
                .mapValue(this::compile)
                .match(target::writeString, Some::new)
                .or(this::executeTSC)
                .ifPresent(error -> System.err.println(error.display())) */;
	}
	findRoot() : Path;
	executeTSC() : Option<IOError>;
	compile(input : string) : string {
		let state : CompileState = CompileState.createInitial();
		let parsed : [CompileState, List<T>] = this.parseStatements(state, input, this.compileRootSegment);
		let joined = parsed[0].structures.query().collect(Joiner.empty()).orElse("");
		return joined + this.generateStatements(parsed[1]);
	}
	generateStatements(statements : List<string>) : string {
		return this.generateAll(this.mergeStatements, statements);
	}
	parseStatements<T>(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => [CompileState, T]) : [CompileState, List<T>] {
		return this.parseAllWithIndices(state, input, this.foldStatementChar, (state3, tuple) => new Some(mapper(state3, tuple.right()))).orElseGet(() => [state, Lists.empty()]);
	}
	generateAll(merger : (arg0 : string, arg1 : string) => string, elements : List<string>) : string {
		return elements.query().fold("", merger);
	}
	parseAllWithIndices<T>(state : CompileState, input : string, folder : (arg0 : DivideState, arg1 : string) => DivideState, mapper : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]>) : Option<[CompileState, List<T>]> {
		let stringList : List<string> = this.divideAll(input, folder);
		return this.mapUsingState(state, stringList, mapper);
	}
	mapUsingState<T, R>(state : CompileState, elements : List<T>, mapper : (arg0 : CompileState, arg1 : [number, T]) => Option<[CompileState, R]>) : Option<[CompileState, List<R>]> {
		let initial : Option<[CompileState, List<R>]> = new Some([state, Lists.empty()]);
		return elements.iterateWithIndices().fold(initial, (tuple, element) => {
			return tuple.flatMap((inner) => {
				let state1 = inner.left();
				let right = inner.right();
				return mapper(state1, element).map((applied : [CompileState, R]) => {
					return [applied[0], right.addLast(applied[1])];
				});
			});
		});
	}
	mergeStatements(cache : string, statement : string) : string {
		return cache + statement;
	}
	divideAll(input : string, folder : (arg0 : DivideState, arg1 : string) => DivideState) : List<string> {
		let current : DivideState = DivideState.createInitial(input);
		while (true){
			let maybePopped : Option<R> = current.pop().map((tuple : [string, DivideState]) => {
				return this.foldSingleQuotes(tuple).or(() => this.foldDoubleQuotes(tuple)).orElseGet(() => folder(tuple[1], tuple[0]));
			});
			if (maybePopped.isPresent()){
				current = maybePopped.orElse(current);
			}
			else {
				/* break */;
			}
		}
		return current.advance().segments;
	}
	foldDoubleQuotes(tuple : [string, DivideState]) : Option<DivideState> {
		if (tuple[0] === "\""){
			let current = tuple[1].append(tuple[0]);
			while (true){
				let maybePopped = current.popAndAppendToTuple();
				if (maybePopped.isEmpty()){
					/* break */;
				}
				let popped = maybePopped.orElse(/* null */);
				current = popped.right();
				if (popped.left() === "\\"){
					current = current.popAndAppendToOption().orElse(current);
				}
				if (popped.left() === "\""){
					/* break */;
				}
			}
			return new Some(current);
		}
		return new None();
	}
	foldSingleQuotes(tuple : [string, DivideState]) : Option<DivideState> {
		if (/* tuple.left() != '\'' */){
			return new None();
		}
		let appended = tuple[1].append(tuple[0]);
		return appended.popAndAppendToTuple().map(this.foldEscaped).flatMap(DivideState.popAndAppendToOption);
	}
	foldEscaped(escaped : [string, DivideState]) : DivideState {
		if (escaped[0] === "\\"){
			return escaped[1].popAndAppendToOption().orElse(escaped[1]);
		}
		return escaped[1];
	}
	foldStatementChar(state : DivideState, c : string) : DivideState {
		let append : DivideState = state.append(c);
		if (c === ";" && append.isLevel()){
			return append.advance();
		}
		if (c === "}" && append.isShallow()){
			return append.advance().exit();
		}
		if (c === "{" || c === "("){
			return append.enter();
		}
		if (c === "}" || c === ")"){
			return append.exit();
		}
		return append;
	}
	compileRootSegment(state : CompileState, input : string) : [CompileState, string] {
		let stripped = input.strip();
		if (stripped.startsWith("package ") || stripped.startsWith("import ")){
			return [state, ""];
		}
		return this.parseClass(stripped, state).flatMap((tuple : [CompileState, IncompleteClassSegment]) => this.completeClassSegment(tuple[0], tuple[1])).map((tuple0 : T) => [tuple0.left(), tuple0.right().generate()]).orElseGet(() => [state, generatePlaceholder(stripped)]);
	}
	parseClass(stripped : string, state : CompileState) : Option<[CompileState, IncompleteClassSegment]> {
		return this.parseStructure(stripped, "class ", "class ", state);
	}
	parseStructure(stripped : string, sourceInfix : string, targetInfix : string, state : CompileState) : Option<[CompileState, IncompleteClassSegment]> {
		return this.first(stripped, sourceInfix, (beforeInfix, right) => {
			return this.first(right, "{", (beforeContent, withEnd) => {
				return this.suffix(withEnd.strip(), "}", (content1 : string) => {
					return this.last(beforeInfix.strip(), "\n", (annotationsString, s2) => {
						let annotations : List<string> = this.parseAnnotations(annotationsString);
						return this.parseStructureWithMaybePermits(targetInfix, state, beforeInfix, beforeContent, content1, annotations);
					}).or(() => {
						return this.parseStructureWithMaybePermits(targetInfix, state, beforeInfix, beforeContent, content1, Lists.empty());
					});
				});
			});
		});
	}
	parseAnnotations(annotationsString : string) : List<string> {
		return this.divideAll(annotationsString.strip(), (state1, c) => this.foldByDelimiter(state1, c, "\n")).query().map(/* String */.strip).filter((value : T) => !value.isEmpty()).map((value : T) => value.substring(1)).map(/* String */.strip).filter((value : T) => !value.isEmpty()).collect(new ListCollector());
	}
	foldByDelimiter(state1 : DivideState, c : string, delimiter : string) : DivideState {
		if (c === delimiter){
			return state1.advance();
		}
		return state1.append(c);
	}
	parseStructureWithMaybePermits(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string, annotations : List<string>) : Option<[CompileState, IncompleteClassSegment]> {
		return this.last(beforeContent, " permits ", (s, s2) => {
			let variants : R = this.divideAll(s2, this.foldValueChar).query().map(/* String */.strip).filter((value : T) => !value.isEmpty()).collect(new ListCollector());
			return this.parseStructureWithMaybeImplements(targetInfix, state, beforeInfix, s, content1, variants, annotations);
		}).or(() => {
			return this.parseStructureWithMaybeImplements(targetInfix, state, beforeInfix, beforeContent, content1, Lists.empty(), annotations);
		});
	}
	parseStructureWithMaybeImplements(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string, variants : List<string>, annotations : List<string>) : Option<[CompileState, IncompleteClassSegment]> {
		return this.first(beforeContent, " implements ", (s, s2) => {
			return this.parseValues(state, s2, this.parseType).flatMap((interfaces : [CompileState, List<T>]) => {
				return this.parseStructureWithMaybeExtends(targetInfix, state, beforeInfix, s, content1, variants, annotations, interfaces[1]);
			});
		}).or(() => {
			return this.parseStructureWithMaybeExtends(targetInfix, state, beforeInfix, beforeContent, content1, variants, annotations, Lists.empty());
		});
	}
	parseStructureWithMaybeExtends(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string, variants : List<string>, annotations : List<string>, interfaces : List<Type>) : Option<[CompileState, IncompleteClassSegment]> {
		return this.first(beforeContent, " extends ", (s, s2) => {
			return this.parseStructureWithMaybeParams(targetInfix, state, beforeInfix, s, content1, variants, annotations, interfaces);
		}).or(() => {
			return this.parseStructureWithMaybeParams(targetInfix, state, beforeInfix, beforeContent, content1, variants, annotations, interfaces);
		});
	}
	parseStructureWithMaybeParams(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string, variants : List<string>, annotations : List<string>, interfaces : List<Type>) : Option<[CompileState, IncompleteClassSegment]> {
		return this.suffix(beforeContent.strip(), ")", (s : string) => {
			return this.first(s, "(", (s1, s2) => {
				let parsed : [CompileState, List<Parameter>] = this.parseParameters(state, s2);
				return this.parseStructureWithMaybeTypeParams(targetInfix, parsed[0], beforeInfix, s1, content1, parsed[1], variants, annotations, interfaces);
			});
		}).or(() => {
			return this.parseStructureWithMaybeTypeParams(targetInfix, state, beforeInfix, beforeContent, content1, Lists.empty(), variants, annotations, interfaces);
		});
	}
	parseStructureWithMaybeTypeParams(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string, params : List<Parameter>, variants : List<string>, annotations : List<string>, interfaces : List<Type>) : Option<[CompileState, IncompleteClassSegment]> {
		return this.first(beforeContent, "<", (name, withTypeParams) => {
			return this.first(withTypeParams, ">", (typeParamsString, afterTypeParams) => {
				let readonly mapper : (arg0 : CompileState, arg1 : string) => [CompileState, string] = (state1, s) => [state1, s.strip()];
				let typeParams : [CompileState, List<T>] = this.parseValuesOrEmpty(state, typeParamsString, (state1, s) => new Some(mapper(state1, s)));
				return this.assembleStructure(typeParams[0], targetInfix, annotations, beforeInfix, name, content1, typeParams[1], afterTypeParams, params, variants, interfaces);
			});
		}).or(() => {
			return this.assembleStructure(state, targetInfix, annotations, beforeInfix, beforeContent, content1, Lists.empty(), "", params, variants, interfaces);
		});
	}
	assembleStructure(state : CompileState, targetInfix : string, annotations : List<string>, beforeInfix : string, rawName : string, content : string, typeParams : List<string>, after : string, rawParameters : List<Parameter>, variants : List<string>, interfaces : List<Type>) : Option<[CompileState, IncompleteClassSegment]> {
		let name = rawName.strip();
		if (!this.isSymbol(name)){
			return new None();
		}
		if (annotations.contains("Actual")){
			return new Some([state, new Whitespace()]);
		}
		let segmentsTuple : [CompileState, List<T>] = this.parseStatements(state.pushStructName([name, variants]).withTypeParams(typeParams), content, (state0, input) => this.parseClassSegment(state0, input, 1));
		let segmentsState = segmentsTuple[0];
		let segments = segmentsTuple[1];
		let parameters : List<Definition> = this.retainDefinitions(rawParameters);
		let prototype : StructurePrototype = new StructurePrototype(targetInfix, beforeInfix, name, typeParams, parameters, after, segments, variants, interfaces);
		return new Some([segmentsState.addType(prototype.createObjectType()), prototype]);
	}
	completeStructure(state : CompileState, prototype : StructurePrototype) : Option<[CompileState, ClassSegment]> {
		let thisType : ObjectType = prototype.createObjectType();
		let state2 : CompileState = state.enterDefinitions().define(ImmutableDefinition.createSimpleDefinition("this", thisType));
		let bases : R = prototype.interfaces.query().map(Main.retainFindableType).flatMap(Queries.fromOption).map(FindableType.findBase).flatMap(Queries.fromOption).collect(new ListCollector());
		let variantsSuper = bases.query().filter((type) => type.hasVariant(prototype.name)).map(BaseType.findName).collect(new ListCollector());
		return this.mapUsingState(state2, prototype.segments(), (state1, entry) => this.completeClassSegment(state1, entry.right())).map((oldStatementsTuple : [CompileState, List<R>]) => {
			let oldStatementsState = oldStatementsTuple[0];
			let oldStatements = oldStatementsTuple[1];
			let exited = oldStatementsState.exitDefinitions();
			let fold = variantsSuper.query().fold(oldStatements, (classSegmentList, superType) => {
				let name = superType + "Variant";
				let type : ObjectType = new ObjectType(name, Lists.empty(), Lists.empty(), Lists.empty());
				let definition : Definition = this.createVariantDefinition(type);
				return classSegmentList.addFirst(new Statement(1, new FieldInitialization(definition, new SymbolValue(name + "." + prototype.name, type))));
			});
			/* CompileState withEnum */;
			/* List<ClassSegment> newSegments */;
			if (prototype.variants.isEmpty()){
				/* withEnum */ = exited;
				/* newSegments */ = fold;
			}
			else {
				let joined = prototype.variants.query().map((inner : string) => "\n\t" + inner).collect(new Joiner(",")).orElse("");
				/* withEnum */ = exited.addStructure("enum " + prototype.name + "Variant" + " {" +
                        joined +
                        "\n}\n");
				let definition : Definition = this.createVariantDefinition(new ObjectType(prototype.name + "Variant", Lists.empty(), Lists.empty(), prototype.variants));
				/* newSegments */ = fold.addFirst(new Statement(1, definition));
			}
			let segmentsWithMaybeConstructor : R = this.attachConstructor(prototype, /* newSegments */).query().flatMap((segment : ClassSegment) => this.flattenEnumValues(segment, thisType)).collect(new ListCollector());
			let generatedSegments = segmentsWithMaybeConstructor.query().map(ClassSegment.generate).collect(Joiner.empty()).orElse("");
			let joinedTypeParams : string = prototype.joinTypeParams();
			let interfacesJoined : string = prototype.joinInterfaces();
			let generated = generatePlaceholder(prototype.beforeInfix().strip()) + prototype.targetInfix() + prototype.name() + joinedTypeParams + generatePlaceholder(prototype.after()) + interfacesJoined + " {" + generatedSegments + "\n}\n";
			let compileState = /* withEnum */.popStructName();
			let definedState = compileState.addStructure(generated);
			return [definedState, new Whitespace()];
		});
	}
	flattenEnumValues(segment : ClassSegment, thisType : ObjectType) : Query<ClassSegment> {
		if (segment._ClassSegmentVariant === ClassSegmentVariant.EnumValues){
			let enumValues : EnumValues = segment as EnumValues;
			return enumValues.values.query().map((enumValue : EnumValue) => {
				let definition : ImmutableDefinition = new ImmutableDefinition(Lists.empty(), Lists.of("static"), enumValue.value, thisType, Lists.empty());
				return new Statement(1, new FieldInitialization(definition, new Invokable(new ConstructionCaller(thisType), enumValue.values, thisType)));
			});
		}
		return Queries.from(segment);
	}
	createVariantDefinition(type : ObjectType) : Definition {
		return ImmutableDefinition.createSimpleDefinition("_" + type.name, type);
	}
	attachConstructor(prototype : StructurePrototype, segments : List<ClassSegment>) : List<ClassSegment> {
		let parameters = prototype.parameters();
		if (parameters.isEmpty()){
			return segments;
		}
		let definitions : List<ClassSegment> = parameters.query(). < /* ClassSegment>map */((definition) => new Statement(1, definition)).collect(new ListCollector());
		let collect = /* parameters.query()
                .map(definition  */ - /* > {
                    var destination = new DataAccess(new SymbolValue("this", Primitive.Unknown), definition.findName(), Primitive.Unknown);
                    return new Assignment */(/* destination */, /*  new SymbolValue(definition.findName(), Primitive.Unknown));
                } */). < /* FunctionSegment>map */((assignment) => new Statement(2, assignment)).collect(new ListCollector());
		let func : FunctionNode = new FunctionNode(1, new ConstructorHeader(), parameters, new Some(collect));
		return segments.addFirst(func).addAllFirst(definitions);
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
		let definition : Definition = classInitialization.definition;
		let statement : Statement = new Statement(classInitialization.depth, new FieldInitialization(definition, classInitialization.value));
		return new Some([state1, statement]);
	}
	completeDefinition(state1 : CompileState, classDefinition : ClassDefinition) : Option<[CompileState, ClassSegment]> {
		let definition : Definition = classDefinition.definition;
		let statement : Statement = new Statement(classDefinition.depth, definition);
		return new Some([state1, statement]);
	}
	retainDefinition(parameter : Parameter) : Option<Definition> {
		if (parameter._ParameterVariant === ParameterVariant.Definition){
			let definition : Definition = parameter as Definition;
			return new Some(definition);
		}
		return new None();
	}
	isSymbol(input : string) : boolean {
		/* for (var i = 0; i < input.length(); i++) */{
			let c = input.charAt(/* i */);
			if (/* Character */.isLetter(c) || /*  */(/* i != 0  */ && /* Character */.isDigit(c))){
				/* continue */;
			}
			return false;
		}
		return true;
	}
	prefix<T>(input : string, prefix : string, mapper : (arg0 : string) => Option<T>) : Option<T> {
		if (!input.startsWith(prefix)){
			return new None();
		}
		let slice = input.substring(prefix.length());
		return mapper(slice);
	}
	suffix<T>(input : string, suffix : string, mapper : (arg0 : string) => Option<T>) : Option<T> {
		if (!input.endsWith(suffix)){
			return new None();
		}
		let slice = input.substring(0, input.length() - suffix.length());
		return mapper(slice);
	}
	parseClassSegment(state : CompileState, input : string, depth : number) : [CompileState, IncompleteClassSegment] {
		return this. < /* Whitespace, IncompleteClassSegment>typed */(() => this.parseWhitespace(input, state)).or(() => this.typed(() => this.parseClass(input, state))).or(() => this.typed(() => this.parseStructure(input, "interface ", "interface ", state))).or(() => this.typed(() => this.parseStructure(input, "record ", "class ", state))).or(() => this.typed(() => this.parseStructure(input, "enum ", "class ", state))).or(() => this.typed(() => this.parseField(input, depth, state))).or(() => this.parseMethod(state, input, depth)).or(() => this.parseEnumValues(state, input)).orElseGet(() => [state, new Placeholder(input)]);
	}
	parseEnumValues(state : CompileState, input : string) : Option<[CompileState, IncompleteClassSegment]> {
		return this.suffix(input.strip(), ";", (withoutEnd : string) => {
			return this.parseValues(state, withoutEnd, (state2, enumValue) => {
				return this.suffix(enumValue.strip(), ")", (withoutValueEnd : string) => {
					return this.first(withoutValueEnd, "(", (s4, s2) => {
						return this.parseValues(state2, s2, (state1, s1) => new Some(Main.this.parseArgument(state1, s1, 1))).map((arguments : [CompileState, List<T>]) => {
							return [arguments[0], new EnumValue(s4, Main.this.retainValues(arguments[1]))];
						});
					});
				});
			}).map((tuple : [CompileState, List<T>]) => {
				return [tuple[0], new EnumValues(tuple[1])];
			});
		});
	}
	typed<T extends S, S>(action : () => Option<[CompileState, T]>) : Option<[CompileState, S]> {
		return action().map((tuple : [CompileState, T]) => [tuple[0], tuple[1]]);
	}
	parseWhitespace(input : string, state : CompileState) : Option<[CompileState, Whitespace]> {
		if (input.isBlank()){
			return new Some([state, new Whitespace()]);
		}
		return new None();
	}
	parseMethod(state : CompileState, input : string, depth : number) : Option<[CompileState, IncompleteClassSegment]> {
		return this.first(input, "(", (definitionString, withParams) => {
			return this.first(withParams, ")", (parametersString, rawContent) => {
				return this.parseDefinition(state, definitionString). < Tuple2 < /* CompileState, Header>>map */((tuple) => [tuple.left(), tuple.right()]).or(() => this.parseConstructor(state, definitionString)).flatMap((definitionTuple) => this.assembleMethod(depth, parametersString, rawContent, definitionTuple));
			});
		});
	}
	assembleMethod(depth : number, parametersString : string, rawContent : string, definitionTuple : [CompileState, Header]) : Option<[CompileState, IncompleteClassSegment]> {
		let definitionState = definitionTuple[0];
		let header = definitionTuple[1];
		let parametersTuple : [CompileState, List<Parameter>] = this.parseParameters(definitionState, parametersString);
		let rawParameters = parametersTuple[1];
		let parameters : List<Definition> = this.retainDefinitions(rawParameters);
		let prototype : MethodPrototype = new MethodPrototype(depth, header, parameters, rawContent.strip());
		return new Some([parametersTuple[0].define(prototype.createDefinition()), prototype]);
	}
	completeMethod(state : CompileState, prototype : MethodPrototype) : Option<[CompileState, ClassSegment]> {
		let definition : Definition = prototype.createDefinition();
		let oldHeader = prototype.header();
		/* Header newHeader */;
		if (oldHeader._UnknownVariant === UnknownVariant.Definition){
			let maybeDefinition : Definition = oldHeader as Definition;
			/* newHeader */ = maybeDefinition.removeAnnotations();
		}
		else {
			/* newHeader */ = oldHeader;
		}
		if (prototype.content() === ";" || definition.containsAnnotation("Actual")){
			return new Some([state.define(definition), new FunctionNode(prototype.depth(), /* newHeader */, prototype.parameters(), new None())]);
		}
		if (prototype.content().startsWith("{") && prototype.content().endsWith("}")){
			let substring = prototype.content().substring(1, prototype.content().length() - 1);
			let withDefined : CompileState = state.enterDefinitions().defineAll(prototype.parameters());
			let statementsTuple : [CompileState, List<T>] = this.parseStatements(withDefined, substring, (state1, input1) => this.parseFunctionSegment(state1, input1, prototype.depth() + 1));
			let statements = statementsTuple[1];
			return new Some([statementsTuple[0].exitDefinitions().define(definition), new FunctionNode(prototype.depth(), /* newHeader */, prototype.parameters(), new Some(statements))]);
		}
		return new None();
	}
	parseConstructor(state : CompileState, input : string) : Option<[CompileState, Header]> {
		let stripped = input.strip();
		if (state.isCurrentStructName(stripped)){
			return new Some([state, new ConstructorHeader()]);
		}
		return new None();
	}
	retainDefinitions(right : List<Parameter>) : List<Definition> {
		return right.query().map(this.retainDefinition).flatMap(Queries.fromOption).collect(new ListCollector());
	}
	parseParameters(state : CompileState, params : string) : [CompileState, List<Parameter>] {
		return this.parseValuesOrEmpty(state, params, (state1, s) => new Some(this.parseParameter(state1, s)));
	}
	parseFunctionSegments(state : CompileState, input : string, depth : number) : [CompileState, List<FunctionSegment>] {
		return this.parseStatements(state, input, (state1, input1) => this.parseFunctionSegment(state1, input1, depth + 1));
	}
	parseFunctionSegment(state : CompileState, input : string, depth : number) : [CompileState, FunctionSegment] {
		let stripped = input.strip();
		if (stripped.isEmpty()){
			return [state, new Whitespace()];
		}
		return this.parseFunctionStatement(state, depth, stripped).or(() => this.parseBlock(state, depth, stripped)).orElseGet(() => [state, new Placeholder(stripped)]);
	}
	parseFunctionStatement(state : CompileState, depth : number, stripped : string) : Option<[CompileState, FunctionSegment]> {
		return this.suffix(stripped, ";", (s : string) => {
			let tuple : [CompileState, StatementValue] = this.parseStatementValue(state, s, depth);
			let left = tuple[0];
			let right = tuple[1];
			return new Some([left, new Statement(depth, right)]);
		});
	}
	parseBlock(state : CompileState, depth : number, stripped : string) : Option<[CompileState, FunctionSegment]> {
		return this.suffix(stripped, "}", (withoutEnd : string) => {
			return this.split(() => this.toFirst(withoutEnd), (beforeContent, content) => {
				return this.suffix(beforeContent, "{", (headerString : string) => {
					let headerTuple : [CompileState, BlockHeader] = this.parseBlockHeader(state, headerString, depth);
					let headerState = headerTuple[0];
					let header = headerTuple[1];
					let statementsTuple : [CompileState, List<FunctionSegment>] = this.parseFunctionSegments(headerState, content, depth);
					let statementsState = statementsTuple[0];
					let statements = statementsTuple[1].addAllFirst(statementsState.functionSegments);
					return new Some([statementsState.clearFunctionSegments(), new Block(depth, header, statements)]);
				});
			});
		});
	}
	toFirst(input : string) : Option<[string, string]> {
		let divisions : List<string> = this.divideAll(input, this.foldBlockStart);
		return divisions.removeFirst().map((removed : [T, List<T>]) => {
			let right = removed[0];
			let left = removed[1].query().collect(new Joiner("")).orElse("");
			return [right, left];
		});
	}
	parseBlockHeader(state : CompileState, input : string, depth : number) : [CompileState, BlockHeader] {
		let stripped = input.strip();
		return this.parseConditional(state, stripped, "if", depth).or(() => this.parseConditional(state, stripped, "while", depth)).or(() => this.parseElse(state, input)).orElseGet(() => [state, new Placeholder(stripped)]);
	}
	parseElse(state : CompileState, input : string) : Option<[CompileState, BlockHeader]> {
		let stripped = input.strip();
		if (stripped === "else"){
			return new Some([state, new Else()]);
		}
		return new None();
	}
	parseConditional(state : CompileState, input : string, prefix : string, depth : number) : Option<[CompileState, BlockHeader]> {
		return this.prefix(input, prefix, (withoutPrefix : string) => {
			return this.prefix(withoutPrefix.strip(), "(", (withoutValueStart : string) => {
				return this.suffix(withoutValueStart, ")", (value : string) => {
					let valueTuple : [CompileState, Value] = this.parseValue(state, value, depth);
					let value1 = valueTuple[1];
					return new Some([valueTuple[0], new Conditional(prefix, value1)]);
				});
			});
		});
	}
	foldBlockStart(state : DivideState, c : string) : DivideState {
		let appended : DivideState = state.append(c);
		if (c === "{" && state.isLevel()){
			return appended.advance();
		}
		if (c === "{"){
			return appended.enter();
		}
		if (c === "}"){
			return appended.exit();
		}
		return appended;
	}
	parseStatementValue(state : CompileState, input : string, depth : number) : [CompileState, StatementValue] {
		let stripped = input.strip();
		if (stripped.startsWith("return ")){
			let value = stripped.substring("return ".length());
			let tuple : [CompileState, Value] = this.parseValue(state, value, depth);
			let value1 = tuple[1];
			return [tuple[0], new Return(value1)];
		}
		return this.parseAssignment(state, depth, stripped).orElseGet(() => {
			return [state, new Placeholder(stripped)];
		});
	}
	parseAssignment(state : CompileState, depth : number, stripped : string) : Option<[CompileState, StatementValue]> {
		return this.first(stripped, "=", (beforeEquals, valueString) => {
			let sourceTuple : [CompileState, Value] = this.parseValue(state, valueString, depth);
			let sourceState = sourceTuple[0];
			let source = sourceTuple[1];
			let destinationTuple : [CompileState, Value] = this.parseValue(sourceState, beforeEquals, depth);
			let destinationState = destinationTuple[0];
			let destination = destinationTuple[1];
			return this.parseDefinition(destinationState, beforeEquals).flatMap((definitionTuple : [CompileState, Definition]) => this.parseInitialization(definitionTuple[0], definitionTuple[1], source)).or(() => new Some([destinationState, new Assignment(destination, source)]));
		});
	}
	parseInitialization(state : CompileState, rawDefinition : Definition, source : Value) : Option<[CompileState, StatementValue]> {
		let definition : Definition = rawDefinition.mapType((type : Type) => {
			if (type === Primitive.Unknown){
				return source.type();
			}
			else {
				return type;
			}
		});
		return new Some([state.define(definition), new Initialization(definition, source)]);
	}
	parseValue(state : CompileState, input : string, depth : number) : [CompileState, Value] {
		return this.parseBoolean(state, input).or(() => this.parseLambda(state, input, depth)).or(() => this.parseString(state, input)).or(() => this.parseDataAccess(state, input, depth)).or(() => this.parseSymbolValue(state, input)).or(() => this.parseInvokable(state, input, depth)).or(() => this.parseDigits(state, input)).or(() => this.parseInstanceOf(state, input, depth)).or(() => this.parseOperation(state, input, depth, Operator.ADD)).or(() => this.parseOperation(state, input, depth, Operator.EQUALS)).or(() => this.parseOperation(state, input, depth, Operator.SUBTRACT)).or(() => this.parseOperation(state, input, depth, Operator.AND)).or(() => this.parseOperation(state, input, depth, Operator.OR)).or(() => this.parseOperation(state, input, depth, /*  Operator.GREATER_THAN_OR_EQUALS */)).or(() => this.parseOperation(state, input, depth, /*  Operator.LESS_THAN */)).or(() => this.parseNot(state, input, depth)).or(() => this.parseMethodReference(state, input, depth)).or(() => this.parseChar(state, input)).orElseGet(() => [state, new Placeholder(input)]);
	}
	parseChar(state : CompileState, input : string) : Option<[CompileState, Value]> {
		let stripped = input.strip();
		if (stripped.startsWith("'") && stripped.endsWith("'") && stripped.length() >= 2){
			return new Some([state, new StringValue(stripped.substring(1, stripped.length() - 1))]);
		}
		return new None();
	}
	parseBoolean(state : CompileState, input : string) : Option<[CompileState, Value]> {
		let stripped = input.strip();
		if (stripped === "false"){
			return new Some([state, BooleanValue.False]);
		}
		if (stripped === "true"){
			return new Some([state, BooleanValue.True]);
		}
		return new None();
	}
	parseInstanceOf(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return this.last(input, "instanceof", (s, s2) => {
			let childTuple : [CompileState, Value] = this.parseValue(state, s, depth);
			return this.parseDefinition(childTuple[0], s2).map((definitionTuple : [CompileState, Definition]) => {
				let value = childTuple[1];
				let definition = definitionTuple[1];
				let type = value.type();
				let variant : DataAccess = new DataAccess(value, "_" + type.findName() + "Variant", Primitive.Unknown);
				let generate = type.findName();
				let temp : SymbolValue = new SymbolValue(generate + "Variant." + definition.findType().findName(), Primitive.Unknown);
				let functionSegment : Statement = new Statement(depth + 1, new Initialization(definition, new Cast(value, definition.findType())));
				return [definitionTuple[0].addFunctionSegment(functionSegment).define(definition), new Operation(variant, Operator.EQUALS, temp)];
			});
		});
	}
	parseMethodReference(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return this.last(input, "::", (s, s2) => {
			let tuple : [CompileState, Value] = this.parseValue(state, s, depth);
			return new Some([tuple[0], new DataAccess(tuple[1], s2, Primitive.Unknown)]);
		});
	}
	parseNot(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		let stripped = input.strip();
		if (stripped.startsWith("!")){
			let slice = stripped.substring(1);
			let tuple : [CompileState, Value] = this.parseValue(state, slice, depth);
			let value = tuple[1];
			return new Some([tuple[0], new Not(value)]);
		}
		return new None();
	}
	parseLambda(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return this.first(input, "->", (beforeArrow, valueString) => {
			let strippedBeforeArrow = beforeArrow.strip();
			if (this.isSymbol(strippedBeforeArrow)){
				let type : Type = Primitive.Unknown;
				if (/* state.typeRegister instanceof Some */(/* var expectedType */)){
					if (/* expectedType */._UnknownVariant === UnknownVariant.FunctionType){
						let functionType : FunctionType = /* expectedType */ as FunctionType;
						type = functionType.arguments.get(0).orElse(/* null */);
					}
				}
				return this.assembleLambda(state, Lists.of(ImmutableDefinition.createSimpleDefinition(strippedBeforeArrow, type)), valueString, depth);
			}
			if (strippedBeforeArrow.startsWith("(") && strippedBeforeArrow.endsWith(")")){
				let parameterNames : R = this.divideAll(strippedBeforeArrow.substring(1, strippedBeforeArrow.length() - 1), this.foldValueChar).query().map(/* String */.strip).filter((value : T) => !value.isEmpty()).map((name : T) => ImmutableDefinition.createSimpleDefinition(name, Primitive.Unknown)).collect(new ListCollector());
				return this.assembleLambda(state, parameterNames, valueString, depth);
			}
			return new None();
		});
	}
	assembleLambda(state : CompileState, definitions : List<Definition>, valueString : string, depth : number) : Some<[CompileState, Value]> {
		let strippedValueString = valueString.strip();
		/* Tuple2Impl<CompileState, LambdaValue> value */;
		let state2 : CompileState = state.defineAll(definitions);
		if (strippedValueString.startsWith("{") && strippedValueString.endsWith("}")){
			let value1 : [CompileState, List<T>] = this.parseStatements(state2, strippedValueString.substring(1, strippedValueString.length() - 1), (state1, input1) => this.parseFunctionSegment(state1, input1, depth + 1));
			let right = value1[1];
			/* value */ = [value1[0], new BlockLambdaValue(depth, right)];
		}
		else {
			let value1 : [CompileState, Value] = this.parseValue(state2, strippedValueString, depth);
			/* value */ = [value1[0], value1[1]];
		}
		let right = /* value */.right();
		return new Some([/* value */.left(), new Lambda(definitions, right)]);
	}
	parseDigits(state : CompileState, input : string) : Option<[CompileState, Value]> {
		let stripped = input.strip();
		if (this.isNumber(stripped)){
			return new Some([state, new SymbolValue(stripped, Primitive.Int)]);
		}
		return new None();
	}
	isNumber(input : string) : boolean {
		/* String maybeTruncated */;
		if (input.startsWith("-")){
			/* maybeTruncated */ = input.substring(1);
		}
		else {
			/* maybeTruncated */ = input;
		}
		return this.areAllDigits(/* maybeTruncated */);
	}
	areAllDigits(input : string) : boolean {
		/* for (var i = 0; i < input.length(); i++) */{
			let c = input.charAt(/* i */);
			if (/* Character */.isDigit(c)){
				/* continue */;
			}
			return false;
		}
		return true;
	}
	parseInvokable(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return this.suffix(input.strip(), ")", (withoutEnd : string) => {
			return this.split(() => this.toLast(withoutEnd, "", this.foldInvocationStart), (callerWithEnd, argumentsString) => {
				return this.suffix(callerWithEnd, "(", (callerString : string) => {
					return this.assembleInvokable(state, depth, argumentsString, callerString.strip());
				});
			});
		});
	}
	assembleInvokable(state : CompileState, depth : number, argumentsString : string, callerString : string) : Some<[CompileState, Value]> {
		let callerTuple : [CompileState, Caller] = this.invocationHeader(state, depth, callerString);
		let oldCallerState = callerTuple[0];
		let oldCaller = callerTuple[1];
		let newCaller : Caller = this.modifyCaller(oldCallerState, oldCaller);
		let callerType : FunctionType = this.findCallerType(newCaller);
		let argumentsTuple : [CompileState, List<T>] = this.parseValuesWithIndices(oldCallerState, argumentsString, (currentState, pair) => this.getTuple2Some(depth, currentState, pair, callerType)).orElseGet(() => [oldCallerState, Lists.empty()]);
		let argumentsState = argumentsTuple[0];
		let argumentsWithActualTypes = argumentsTuple[1];
		let arguments : List<Value> = this.retainValues(argumentsWithActualTypes.query().map(Tuple2.left).collect(new ListCollector()));
		if (newCaller._CallerVariant === CallerVariant.ConstructionCaller){
			if (constructionCaller.type.findName() === "Tuple2Impl"){
			let constructionCaller : ConstructionCaller = newCaller as ConstructionCaller;
				return new Some([argumentsState, new TupleNode(Lists.of(arguments.get(0).orElse(/* null */), arguments.get(1).orElse(/* null */)))]);
			}
		}
		if (newCaller._CallerVariant === CallerVariant.Value){
			if (value._ValueVariant === ValueVariant.DataAccess){
				let parent : Value = access.parent;
				let property : string = access.property;
				let parentType : Type = parent.type();
				if (/* parentType instanceof TupleType */){
					if (property === "left"){
			let value : Value = newCaller as Value;
				let access : DataAccess = value as DataAccess;
						return new Some([argumentsState, new IndexValue(parent, new SymbolValue("0", Primitive.Int))]);
					}
					if (property === "right"){
						return new Some([argumentsState, new IndexValue(parent, new SymbolValue("1", Primitive.Int))]);
					}
				}
				if (property === "equals"){
					let first : Value = arguments.get(0).orElse(/* null */);
					return new Some([argumentsState, new Operation(parent, Operator.EQUALS, first)]);
				}
			}
		}
		let invokable : Invokable = new Invokable(newCaller, arguments, callerType.returns);
		return new Some([argumentsState, invokable]);
	}
	getTuple2Some(depth : number, currentState : CompileState, pair : [number, string], callerType : FunctionType) : Some<[CompileState, Tuple2Impl<Argument, Type>]> {
		let index = pair[0];
		let element = pair[1];
		let expectedType : Type = callerType.arguments.get(index).orElse(Primitive.Unknown);
		let withExpected : CompileState = currentState.withExpectedType(expectedType);
		let valueTuple : [CompileState, Argument] = this.parseArgument(withExpected, element, depth);
		let valueState = valueTuple[0];
		let value = valueTuple[1];
		let actualType = valueTuple[0].typeRegister.orElse(Primitive.Unknown);
		return new Some([valueState, [value, actualType]]);
	}
	retainValues(arguments : List<Argument>) : List<Value> {
		return arguments.query().map(this.retainValue).flatMap(Queries.fromOption).collect(new ListCollector());
	}
	retainValue(argument : Argument) : Option<Value> {
		if (argument._ArgumentVariant === ArgumentVariant.Value){
			let value : Value = argument as Value;
			return new Some(value);
		}
		return new None();
	}
	parseArgument(state : CompileState, element : string, depth : number) : [CompileState, Argument] {
		if (element.isEmpty()){
			return [state, new Whitespace()];
		}
		let tuple : [CompileState, Value] = this.parseValue(state, element, depth);
		return [tuple[0], tuple[1]];
	}
	findCallerType(newCaller : Caller) : FunctionType {
		let callerType : FunctionType = new FunctionType(Lists.empty(), Primitive.Unknown);
		/* switch (newCaller) */{
			/* case ConstructionCaller constructionCaller -> */{
				callerType = /* constructionCaller */.toFunction();
			}
			/* case Value value -> */{
				let type = /* value */.type();
				if (type._UnknownVariant === UnknownVariant.FunctionType){
					let functionType : FunctionType = type as FunctionType;
					callerType = functionType;
				}
			}
		}
		return callerType;
	}
	modifyCaller(state : CompileState, oldCaller : Caller) : Caller {
		if (oldCaller._CallerVariant === CallerVariant.DataAccess){
			let type : Type = this.resolveType(access.parent, state);
			if (/* type instanceof FunctionType */){
			let access : DataAccess = oldCaller as DataAccess;
				return access.parent;
			}
		}
		return oldCaller;
	}
	resolveType(value : Value, state : CompileState) : Type {
		return value.type();
	}
	invocationHeader(state : CompileState, depth : number, callerString1 : string) : [CompileState, Caller] {
		if (callerString1.startsWith("new ")){
			let input1 : string = callerString1.substring("new ".length());
			let map : Option<R> = this.parseType(state, input1).map((type : [CompileState, Type]) => {
				let right = type[1];
				return [type[0], new ConstructionCaller(right)];
			});
			if (map.isPresent()){
				return map.orElse(/* null */);
			}
		}
		let tuple : [CompileState, Value] = this.parseValue(state, callerString1, depth);
		return [tuple[0], tuple[1]];
	}
	foldInvocationStart(state : DivideState, c : string) : DivideState {
		let appended : DivideState = state.append(c);
		if (c === "("){
			let enter : DivideState = appended.enter();
			if (enter.isShallow()){
				return enter.advance();
			}
			return enter;
		}
		if (c === ")"){
			return appended.exit();
		}
		return appended;
	}
	parseDataAccess(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return this.last(input.strip(), ".", (parentString, rawProperty) => {
			let property = rawProperty.strip();
			if (!this.isSymbol(property)){
				return new None();
			}
			let tuple : [CompileState, Value] = this.parseValue(state, parentString, depth);
			let parent = tuple[1];
			let parentType = parent.type();
			let type : Type = Primitive.Unknown;
			if (parentType._UnknownVariant === UnknownVariant.FindableType){
				if (/* objectType.find(property) instanceof Some */(/* var memberType */)){
				let objectType : FindableType = parentType as FindableType;
					type = /* memberType */;
				}
			}
			return new Some([tuple[0], new DataAccess(parent, property, type)]);
		});
	}
	parseString(state : CompileState, input : string) : Option<[CompileState, Value]> {
		let stripped = input.strip();
		if (stripped.startsWith("\"") && stripped.endsWith("\"")){
			return new Some([state, new StringValue(stripped.substring(1, stripped.length() - 1))]);
		}
		return new None();
	}
	parseSymbolValue(state : CompileState, value : string) : Option<[CompileState, Value]> {
		let stripped = value.strip();
		if (this.isSymbol(stripped)){
			if (/* state.resolveValue(stripped) instanceof Some */(/* var type */)){
				return new Some([state, new SymbolValue(stripped, type)]);
			}
			if (/* state.resolveType(stripped) instanceof Some */(/* var type */)){
				return new Some([state, new SymbolValue(stripped, type)]);
			}
			return new Some([state, new Placeholder(stripped)]);
		}
		return new None();
	}
	parseOperation(state : CompileState, value : string, depth : number, operator : Operator) : Option<[CompileState, Value]> {
		return this.first(value, operator.sourceRepresentation, (leftString, rightString) => {
			let leftTuple : [CompileState, Value] = this.parseValue(state, leftString, depth);
			let rightTuple : [CompileState, Value] = this.parseValue(leftTuple[0], rightString, depth);
			let left = leftTuple[1];
			let right = rightTuple[1];
			return new Some([rightTuple[0], new Operation(left, operator, right)]);
		});
	}
	parseValuesOrEmpty<T>(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) : [CompileState, List<T>] {
		return this.parseValues(state, input, mapper).orElseGet(() => [state, Lists.empty()]);
	}
	parseValues<T>(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) : Option<[CompileState, List<T>]> {
		return this.parseValuesWithIndices(state, input, (state1, tuple) => mapper(state1, tuple.right()));
	}
	parseValuesWithIndices<T>(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]>) : Option<[CompileState, List<T>]> {
		return this.parseAllWithIndices(state, input, this.foldValueChar, mapper);
	}
	parseParameter(state : CompileState, input : string) : [CompileState, Parameter] {
		if (input.isBlank()){
			return [state, new Whitespace()];
		}
		return this.parseDefinition(state, input).map((tuple : [CompileState, Definition]) => [tuple[0], tuple[1]]).orElseGet(() => [state, new Placeholder(input)]);
	}
	parseField(input : string, depth : number, state : CompileState) : Option<[CompileState, IncompleteClassSegment]> {
		return this.suffix(input.strip(), ";", (withoutEnd : string) => {
			return this.parseClassInitialization(depth, state, withoutEnd).or(() => {
				return this.parseClassDefinition(depth, state, withoutEnd);
			});
		});
	}
	parseClassDefinition(depth : number, state : CompileState, withoutEnd : string) : Option<[CompileState, IncompleteClassSegment]> {
		return this.parseDefinition(state, withoutEnd).map((result : [CompileState, Definition]) => {
			return [result[0], new ClassDefinition(depth, result[1])];
		});
	}
	parseClassInitialization(depth : number, state : CompileState, withoutEnd : string) : Option<[CompileState, IncompleteClassSegment]> {
		return this.first(withoutEnd, "=", (s, s2) => {
			return this.parseDefinition(state, s).map((result : [CompileState, Definition]) => {
				let valueTuple : [CompileState, Value] = this.parseValue(result[0], s2, depth);
				return [valueTuple[0], new ClassInitialization(depth, result[1], valueTuple[1])];
			});
		});
	}
	parseDefinition(state : CompileState, input : string) : Option<[CompileState, Definition]> {
		return this.last(input.strip(), " ", (beforeName, name) => {
			return this.split(() => this.toLast(beforeName, " ", this.foldTypeSeparator), (beforeType, type) => {
				return this.last(beforeType, "\n", (s, s2) => {
					let annotations : List<string> = this.parseAnnotations(s);
					return this.getOr(state, name, s2, type, annotations);
				}).or(() => {
					return this.getOr(state, name, beforeType, type, Lists.empty());
				});
			}).or(() => this.assembleDefinition(state, Lists.empty(), Lists.empty(), name, Lists.empty(), beforeName));
		});
	}
	getOr(state : CompileState, name : string, beforeType : string, type : string, annotations : List<string>) : Option<[CompileState, Definition]> {
		return this.suffix(beforeType.strip(), ">", (withoutTypeParamStart : string) => {
			return this.first(withoutTypeParamStart, "<", (beforeTypeParams, typeParamsString) => {
				let typeParams : [CompileState, List<T>] = this.parseValuesOrEmpty(state, typeParamsString, (state1, s) => new Some([state1, s.strip()]));
				return this.assembleDefinition(typeParams[0], annotations, this.parseModifiers(beforeTypeParams), name, typeParams[1], type);
			});
		}).or(() => {
			return this.assembleDefinition(state, annotations, this.parseModifiers(beforeType), name, Lists.empty(), type);
		});
	}
	parseModifiers(modifiers : string) : List<string> {
		return this.divideAll(modifiers.strip(), (state1, c) => this.foldByDelimiter(state1, c, " ")).query().map(/* String */.strip).filter((value : T) => !value.isEmpty()).collect(new ListCollector());
	}
	toLast(input : string, separator : string, folder : (arg0 : DivideState, arg1 : string) => DivideState) : Option<[string, string]> {
		let divisions : List<string> = this.divideAll(input, folder);
		return divisions.removeLast().map((removed : [List<T>, T]) => {
			let left = removed[0].query().collect(new Joiner(separator)).orElse("");
			let right = removed[1];
			return [left, right];
		});
	}
	foldTypeSeparator(state : DivideState, c : string) : DivideState {
		if (c === " " && state.isLevel()){
			return state.advance();
		}
		let appended : DivideState = state.append(c);
		if (c === /*  ' */ < /* ' */){
			return appended.enter();
		}
		if (c === ">"){
			return appended.exit();
		}
		return appended;
	}
	assembleDefinition(state : CompileState, annotations : List<string>, modifiers : List<string>, rawName : string, typeParams : List<string>, type : string) : Option<[CompileState, Definition]> {
		return this.parseType(state.withTypeParams(typeParams), type).flatMap((type1 : [CompileState, Type]) => {
			let stripped = rawName.strip();
			if (!this.isSymbol(stripped)){
				return new None();
			}
			let newModifiers : R = modifiers.query().filter((value : string) => !this.isAccessor(value)).map((modifier : T) => /* modifier.equals("final") ? "readonly" : modifier */).collect(new ListCollector());
			let node : ImmutableDefinition = new ImmutableDefinition(annotations, newModifiers, stripped, type1[1], typeParams);
			return new Some([type1[0], node]);
		});
	}
	isAccessor(value : string) : boolean {
		return value === "private";
	}
	foldValueChar(state : DivideState, c : string) : DivideState {
		if (c === "," && state.isLevel()){
			return state.advance();
		}
		let appended : DivideState = state.append(c);
		if (c === /*  ' */ - /* ' */){
			let peeked : string = appended.peek();
			if (peeked === ">"){
				return appended.popAndAppendToOption().orElse(appended);
			}
			else {
				return appended;
			}
		}
		if (c === /*  ' */ < /* '  */ || c === "(" || c === "{"){
			return appended.enter();
		}
		if (c === ">" || c === ")" || c === "}"){
			return appended.exit();
		}
		return appended;
	}
	parseType(state : CompileState, input : string) : Option<[CompileState, Type]> {
		let stripped = input.strip();
		if (stripped === "int" || stripped === "Integer"){
			return new Some([state, Primitive.Int]);
		}
		if (stripped === "String" || stripped === "char" || stripped === "Character"){
			return new Some([state, Primitive.String]);
		}
		if (stripped === "var"){
			return new Some([state, Primitive.Unknown]);
		}
		if (stripped === "boolean"){
			return new Some([state, Primitive.Boolean]);
		}
		if (stripped === "void"){
			return new Some([state, Primitive.Void]);
		}
		if (this.isSymbol(stripped)){
			if (/* state.resolveType(stripped) instanceof Some */(/* var resolved */)){
				return new Some([state, /* resolved */]);
			}
			else {
				return new Some([state, new Placeholder(stripped)]);
			}
		}
		return this.parseTemplate(state, input).or(() => this.varArgs(state, input));
	}
	varArgs(state : CompileState, input : string) : Option<[CompileState, Type]> {
		return this.suffix(input, "...", (s : string) => {
			return this.parseType(state, s).map((inner : [CompileState, Type]) => {
				let newState = inner[0];
				let child = inner[1];
				return [newState, new ArrayType(child)];
			});
		});
	}
	assembleTemplate(base : string, state : CompileState, arguments : List<Argument>) : [CompileState, Type] {
		let children : R = arguments.query().map(this.retainType).flatMap(Queries.fromOption).collect(new ListCollector());
		if (base === "BiFunction"){
			return [state, new FunctionType(Lists.of(children.get(0).orElse(/* null */), children.get(1).orElse(/* null */)), children.get(2).orElse(/* null */))];
		}
		if (base === "Function"){
			return [state, new FunctionType(Lists.of(children.get(0).orElse(/* null */)), children.get(1).orElse(/* null */))];
		}
		if (base === "Predicate"){
			return [state, new FunctionType(Lists.of(children.get(0).orElse(/* null */)), Primitive.Boolean)];
		}
		if (base === "Supplier"){
			return [state, new FunctionType(Lists.empty(), children.get(0).orElse(/* null */))];
		}
		if (base === "Consumer"){
			return [state, new FunctionType(Lists.of(children.get(0).orElse(/* null */)), Primitive.Void)];
		}
		if (base === "Tuple2" && children.size() >= 2){
			return [state, new TupleType(children)];
		}
		if (state.resolveType(base)._OptionVariant === OptionVariant.Some){
			let baseType : Type = some.value;
			if (baseType._Variant === Variant.ObjectType){
			let some : Some<Type> = state.resolveType(base) as Some<Type>;
				let findableType : ObjectType = baseType as ObjectType;
				return [state, new Template(findableType, children)];
			}
		}
		return [state, new Template(new ObjectType(base, Lists.empty(), Lists.empty(), Lists.empty()), children)];
	}
	parseTemplate(state : CompileState, input : string) : Option<[CompileState, Type]> {
		return this.suffix(input.strip(), ">", (withoutEnd : string) => {
			return this.first(withoutEnd, "<", (base, argumentsString) => {
				let strippedBase = base.strip();
				return this.parseValues(state, argumentsString, this.parseArgument).map((argumentsTuple : [CompileState, List<T>]) => {
					return this.assembleTemplate(strippedBase, argumentsTuple[0], argumentsTuple[1]);
				});
			});
		});
	}
	retainType(argument : Argument) : Option<Type> {
		if (argument._ArgumentVariant === ArgumentVariant.){
			let type : Type = argument as Type;
			return new Some(type);
		}
		else {
			return new None<Type>();
		}
	}
	parseArgument(state : CompileState, input : string) : Option<[CompileState, Argument]> {
		if (input.isBlank()){
			return new Some([state, new Whitespace()]);
		}
		return this.parseType(state, input).map((tuple : [CompileState, Type]) => [tuple[0], tuple[1]]);
	}
	last<T>(input : string, infix : string, mapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return this.infix(input, infix, this.findLast, mapper);
	}
	findLast(input : string, infix : string) : Option<number> {
		let index = input.lastIndexOf(infix);
		if (index === -1){
			return new None<number>();
		}
		return new Some(index);
	}
	first<T>(input : string, infix : string, mapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return this.infix(input, infix, this.findFirst, mapper);
	}
	split<T>(splitter : () => Option<[string, string]>, splitMapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return splitter().flatMap((splitTuple : [string, string]) => splitMapper(splitTuple[0], splitTuple[1]));
	}
	infix<T>(input : string, infix : string, locator : (arg0 : string, arg1 : string) => Option<number>, mapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return this.split(() => locator(input, infix).map((index : number) => {
			let left = input.substring(0, index);
			let right = input.substring(index + infix.length());
			return [left, right];
		}), mapper);
	}
	findFirst(input : string, infix : string) : Option<number> {
		let index = input.indexOf(infix);
		if (index === -1){
			return new None<number>();
		}
		return new Some(index);
	}
}
/*  */