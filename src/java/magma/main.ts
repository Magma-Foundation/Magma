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
	_OptionVariant : OptionVariant = OptionVariant.None;
	map<R>(mapper : (arg0 : T) => R) : Option<R> {
		return new None();
	}
	isPresent() : boolean {
		return false;
	}
	orElse(other : T) : T {
		return other;
	}
	filter(predicate : (arg0 : T) => boolean) : Option<T> {
		return new None();
	}
	orElseGet(supplier : () => T) : T {
		return supplier();
	}
	or(other : () => Option<T>) : Option<T> {
		return other();
	}
	flatMap<R>(mapper : (arg0 : T) => Option<R>) : Option<R> {
		return new None();
	}
	isEmpty() : boolean {
		return true;
	}
	and<R>(other : () => Option<R>) : Option<[T, R]> {
		return new None();
	}
	ifPresent(consumer : (arg0 : T) => void) : void {
	}
}
/* private */class Some<T>/*  */ implements Option<T> {
	value : T;
	constructor (value : T) {
		this.value = value;
	}
	_OptionVariant : OptionVariant = OptionVariant.Some;
	map<R>(mapper : (arg0 : T) => R) : Option<R> {
		return new Some(mapper(this.value));
	}
	isPresent() : boolean {
		return true;
	}
	orElse(other : T) : T {
		return this.value;
	}
	filter(predicate : (arg0 : T) => boolean) : Option<T> {
		if (predicate(this.value)){
			return this;
		}
		return new None();
	}
	orElseGet(supplier : () => T) : T {
		return this.value;
	}
	or(other : () => Option<T>) : Option<T> {
		return this;
	}
	flatMap<R>(mapper : (arg0 : T) => Option<R>) : Option<R> {
		return mapper(this.value);
	}
	isEmpty() : boolean {
		return false;
	}
	and<R>(other : () => Option<R>) : Option<[T, R]> {
		return other().map((otherValue : R) => [this.value, otherValue]);
	}
	ifPresent(consumer : (arg0 : T) => void) : void {
		/* consumer.accept(this.value) */;
	}
}
/* private static */class SingleHead<T>/*  */ implements Head<T> {
	value : T;
	retrieved : boolean;
	constructor (value : T) {
		this.value = value;
		this.retrieved = false;
	}
	next() : Option<T> {
		if (this.retrieved){
			return new None();
		}
		this.retrieved = true;
		return new Some(this.value);
	}
}
/* private static */class EmptyHead<T>/*  */ implements Head<T> {
	next() : Option<T> {
		return new None();
	}
}
/* private */class HeadedQuery<T>/*  */ implements Query<T> {
	head : Head<T>;
	constructor (head : Head<T>) {
		this.head = head;
	}
	fold<R>(initial : R, folder : (arg0 : R, arg1 : T) => R) : R {
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
	map<R>(mapper : (arg0 : T) => R) : Query<R> {
		return new HeadedQuery(new MapHead(this.head, mapper));
	}
	collect<R>(collector : Collector<T, R>) : R {
		return this.fold(collector.createInitial(), collector.fold);
	}
	filter(predicate : (arg0 : T) => boolean) : Query<T> {
		return this.flatMap((element : T) => {
			if (predicate(element)){
				return new HeadedQuery(new SingleHead(element));
			}
			return new HeadedQuery(new EmptyHead());
		});
	}
	next() : Option<T> {
		return this.head.next();
	}
	flatMap<R>(f : (arg0 : T) => Query<R>) : Query<R> {
		return new HeadedQuery(new FlatMapHead(this.head, f));
	}
	zip<R>(other : Query<R>) : Query<[T, R]> {
		return new HeadedQuery(new ZipHead(this.head, other));
	}
}
/* private static */class RangeHead/*  */ implements Head<number> {
	length : number;
	counter : number;
	constructor (length : number) {
		this.length = length;
		this.counter = 0;
	}
	next() : Option<number> {
		if (this.counter < this.length){
			let value : number = this.counter;
			/* this.counter++ */;
			return new Some(value);
		}
		return new None();
	}
}
/* private static */class Lists/*  */ {
	empty<T>() : List<T>;
	of<T>(elements : T[]) : List<T>;
}
/* private */class ImmutableDefinition/*  */ implements Definition {
	annotations : List<string>;
	maybeBefore : Option<string>;
	name : string;
	type : Type;
	typeParams : List<string>;
	constructor (annotations : List<string>, maybeBefore : Option<string>, name : string, type : Type, typeParams : List<string>) {
		this.annotations = annotations;
		this.maybeBefore = maybeBefore;
		this.name = name;
		this.type = type;
		this.typeParams = typeParams;
	}
	createSimpleDefinition(name : string, type : Type) : Definition {
		return new ImmutableDefinition(Lists.empty(), new None(), name, type, Lists.empty());
	}
	findName() : string {
		return this.name;
	}
	findType() : Type {
		return this.type;
	}
	generate() : string {
		return this.generateWithParams("");
	}
	generateType() : string {
		if (this.type.equals(Primitive.Unknown)){
			return "";
		}
		return " : " + this.type.generate();
	}
	joinBefore() : string {
		if (Main.isDebug){
			return this.generateBefore();
		}
		return "";
	}
	generateBefore() : string {
		return this.maybeBefore.filter((value : string) => !value.isEmpty()).map(Main.generatePlaceholder).map((inner : T) => inner + " ").orElse("");
	}
	joinTypeParams() : string {
		return this.typeParams.iterate().collect(new Joiner(", ")).map((inner) => "<" + inner + ">").orElse("");
	}
	mapType(mapper : (arg0 : Type) => Type) : Definition {
		return new ImmutableDefinition(this.annotations, this.maybeBefore, this.name, mapper(this.type), this.typeParams);
	}
	generateWithParams(joinedParameters : string) : string {
		let joinedAnnotations = this.annotations.iterate().map((value : T) => "@" + value + " ").collect(Joiner.empty()).orElse("");
		let joined : string = this.joinTypeParams();
		let before : string = this.joinBefore();
		let typeString : string = this.generateType();
		return joinedAnnotations + before + this.name + joined + joinedParameters + typeString;
	}
	createDefinition(paramTypes : List<Type>) : Definition {
		let type1 : Type = new FunctionType(paramTypes, this.type);
		return new ImmutableDefinition(this.annotations, this.maybeBefore, this.name, type1, this.typeParams);
	}
	containsAnnotation(annotation : string) : boolean {
		return this.annotations.contains(annotation);
	}
	removeAnnotations() : Definition {
		return new ImmutableDefinition(Lists.empty(), this.maybeBefore, this.name, this.type, this.typeParams);
	}
	toString() : string {
		return "ImmutableDefinition[" + "annotations=" + this.annotations + ", " + "maybeBefore=" + this.maybeBefore + ", " + "findName=" + this.name + ", " + "findType=" + this.type + ", " + "typeParams=" + this.typeParams + /*  ']' */;
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
	generate() : string {
		return this.name;
	}
	replace(mapping : Map<string, Type>) : Type {
		return new ObjectType(this.name, this.typeParams, this.definitions.iterate().map((definition : T) => definition.mapType((type) => type.replace(mapping))).collect(new ListCollector()), this.variants);
	}
	find(name : string) : Option<Type> {
		return this.definitions.iterate().filter((definition : T) => definition.findName().equals(name)).map(Definition.findType).next();
	}
	findBase() : Option<BaseType> {
		return new Some(this);
	}
	findName() : Option<string> {
		return new Some(this.name);
	}
}
/* private */class TypeParam/*  */ implements Type {
	value : string;
	constructor (value : string) {
		this.value = value;
	}
	generate() : string {
		return this.value;
	}
	replace(mapping : Map<string, Type>) : Type {
		return mapping.find(this.value).orElse(this);
	}
	findName() : Option<string> {
		return new None();
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
	createInitial() : CompileState {
		return new CompileState(Lists.empty(), Lists.of(Lists.empty()), Lists.empty(), Lists.empty(), Lists.empty(), new None(), Lists.empty());
	}
	resolveValue(name : string) : Option<Type> {
		return this.definitions.iterateReversed().flatMap(List.iterate).filter((definition : T) => definition.findName().equals(name)).next().map(Definition.findType);
	}
	addStructure(structure : string) : CompileState {
		return new CompileState(this.structures.addLast(structure), this.definitions, this.objectTypes, this.structNames, this.typeParams, this.typeRegister, this.functionSegments);
	}
	defineAll(definitions : List<Definition>) : CompileState {
		let defined : List<T> = this.definitions.mapLast((frame : List<Definition>) => frame.addAllLast(definitions));
		return new CompileState(this.structures, defined, this.objectTypes, this.structNames, this.typeParams, this.typeRegister, this.functionSegments);
	}
	resolveType(name : string) : Option<Type> {
		let maybe : Option<[string, List<string>]> = this.structNames.last().filter((inner : T) => inner.left().equals(name));
		if (maybe._OptionVariant === OptionVariant.Some){
			let some : Some<[string, List<string>]> = maybe as Some<[string, List<string>]>;
			let found : [string, List<string>] = some.value;
			return new Some(new ObjectType(found[0], this.typeParams, this.definitions.last().orElse(Lists.empty()), found[1]));
		}
		let maybeTypeParam : Option<T> = this.typeParams.iterate().filter((param : T) => param.equals(name)).next();
		if (maybeTypeParam._OptionVariant === OptionVariant.Some){
			let some : Some<string> = maybeTypeParam as Some<string>;
			return new Some(new TypeParam(some.value));
		}
		return this.objectTypes.iterate().filter((type : T) => type.name.equals(name)).next().map((type : T) => type);
	}
	define(definition : Definition) : CompileState {
		return new CompileState(this.structures, this.definitions.mapLast((frame : List<Definition>) => frame.addLast(definition)), this.objectTypes, this.structNames, this.typeParams, this.typeRegister, this.functionSegments);
	}
	pushStructName(definition : Tuple2Impl<string, List<string>>) : CompileState {
		return new CompileState(this.structures, this.definitions, this.objectTypes, this.structNames.addLast(definition), this.typeParams, this.typeRegister, this.functionSegments);
	}
	withTypeParams(typeParams : List<string>) : CompileState {
		return new CompileState(this.structures, this.definitions, this.objectTypes, this.structNames, this.typeParams.addAllLast(typeParams), this.typeRegister, this.functionSegments);
	}
	withExpectedType(type : Type) : CompileState {
		return new CompileState(this.structures, this.definitions, this.objectTypes, this.structNames, this.typeParams, new Some(type), this.functionSegments);
	}
	popStructName() : CompileState {
		return new CompileState(this.structures, this.definitions, this.objectTypes, this.structNames.removeLast().map(Tuple2.left).orElse(this.structNames), this.typeParams, this.typeRegister, this.functionSegments);
	}
	enterDefinitions() : CompileState {
		return new CompileState(this.structures, this.definitions.addLast(Lists.empty()), this.objectTypes, this.structNames, this.typeParams, this.typeRegister, this.functionSegments);
	}
	exitDefinitions() : CompileState {
		let removed : T = this.definitions.removeLast().map(Tuple2.left).orElse(this.definitions);
		return new CompileState(this.structures, removed, this.objectTypes, this.structNames, this.typeParams, this.typeRegister, this.functionSegments);
	}
	addType(thisType : ObjectType) : CompileState {
		return new CompileState(this.structures, this.definitions, this.objectTypes.addLast(thisType), this.structNames, this.typeParams, this.typeRegister, this.functionSegments);
	}
	addFunctionSegment(segment : FunctionSegment) : CompileState {
		return new CompileState(this.structures, this.definitions, this.objectTypes, this.structNames, this.typeParams, this.typeRegister, this.functionSegments.addLast(segment));
	}
	clearFunctionSegments() : CompileState {
		return new CompileState(this.structures, this.definitions, this.objectTypes, this.structNames, this.typeParams, this.typeRegister, Lists.empty());
	}
	isCurrentStructName(stripped : string) : boolean {
		return stripped.equals(this.structNames.last().map(Tuple2.left).orElse(""));
	}
}
/* private static */class DivideState/*  */ {
	input : string;
	index : number;
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
	createInitial(input : string) : DivideState {
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
	enter() : DivideState {
		/* this.depth++ */;
		return this;
	}
	isLevel() : boolean {
		return this.depth === 0;
	}
	exit() : DivideState {
		/* this.depth-- */;
		return this;
	}
	isShallow() : boolean {
		return this.depth === 1;
	}
	pop() : Option<[string, DivideState]> {
		if (this.index < this.input.length()){
			let c = this.input.charAt(this.index);
			return new Some([c, new DivideState(this.input, this.index + 1, this.segments, this.buffer, this.depth)]);
		}
		return new None();
	}
	popAndAppendToTuple() : Option<[string, DivideState]> {
		return this.pop().map((tuple : [string, DivideState]) => {
			let c = tuple[0];
			let right = tuple[1];
			return [c, right.append(c)];
		});
	}
	popAndAppendToOption() : Option<DivideState> {
		return this.popAndAppendToTuple().map(Tuple2.right);
	}
	peek() : string {
		return this.input.charAt(this.index);
	}
}
/* private */class Joiner/*  */ implements Collector<string, Option<string>> {
	delimiter : string;
	constructor (delimiter : string) {
		this.delimiter = delimiter;
	}
	empty() : Joiner {
		return new Joiner("");
	}
	createInitial() : Option<string> {
		return new None();
	}
	fold(current : Option<string>, element : string) : Option<string> {
		return new Some(current.map((inner : string) => inner + this.delimiter + element).orElse(element));
	}
}
/* private static */class ListCollector<T>/*  */ implements Collector<T, List<T>> {
	createInitial() : List<T> {
		return Lists.empty();
	}
	fold(current : List<T>, element : T) : List<T> {
		return current.addLast(element);
	}
}
/* private static */class FlatMapHead<T, R>/*  */ implements Head<R> {
	mapper : (arg0 : T) => Query<R>;
	head : Head<T>;
	current : Option<Query<R>>;
	constructor (head : Head<T>, mapper : (arg0 : T) => Query<R>) {
		this.mapper = mapper;
		this.current = new None();
		this.head = head;
	}
	next() : Option<R> {
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
	generate() : string {
		return this.right.generate() + "[]";
	}
	replace(mapping : Map<string, Type>) : Type {
		return this;
	}
	findName() : Option<string> {
		return new None();
	}
}
/* private static final */class Whitespace/*  */ implements Argument, Parameter, ClassSegment, FunctionSegment, IncompleteClassSegment {
	_IncompleteClassSegmentVariant : IncompleteClassSegmentVariant = IncompleteClassSegmentVariant.Whitespace;
	generate() : string {
		return "";
	}
	maybeCreateDefinition() : Option<Definition> {
		return new None();
	}
}
/* private static */class Queries/*  */ {
	fromOption<T>(option : Option<T>) : Query<T> {
		let single : Option<Head<T>> = option.map(SingleHead.new);
		return new HeadedQuery(single.orElseGet(EmptyHead.new));
	}
}
/* private */class FunctionType/*  */ implements Type {
	arguments : List<Type>;
	returns : Type;
	constructor (arguments : List<Type>, returns : Type) {
		this.arguments = arguments;
		this.returns = returns;
	}
	generate() : string {
		let joined = this.arguments().iterateWithIndices().map((pair) => "arg" + pair.left() + " : " + pair.right().generate()).collect(new Joiner(", ")).orElse("");
		return "(" + joined + ") => " + this.returns.generate();
	}
	replace(mapping : Map<string, Type>) : Type {
		return new FunctionType(this.arguments.iterate().map((type : T) => type.replace(mapping)).collect(new ListCollector()), this.returns);
	}
	findName() : Option<string> {
		return new None();
	}
}
/* private */class TupleType/*  */ implements Type {
	arguments : List<Type>;
	constructor (arguments : List<Type>) {
		this.arguments = arguments;
	}
	generate() : string {
		let joinedArguments = this.arguments.iterate().map(Type.generate).collect(new Joiner(", ")).orElse("");
		return "[" + joinedArguments + "]";
	}
	replace(mapping : Map<string, Type>) : Type {
		return this;
	}
	findName() : Option<string> {
		return new None();
	}
}
/* private */class Template/*  */ implements FindableType {
	base : ObjectType;
	arguments : List<Type>;
	constructor (base : ObjectType, arguments : List<Type>) {
		this.base = base;
		this.arguments = arguments;
	}
	generate() : string {
		let joinedArguments = this.arguments.iterate().map(Type.generate).collect(new Joiner(", ")).map((inner) => "<" + inner + ">").orElse("");
		return this.base.generate() + joinedArguments;
	}
	find(name : string) : Option<Type> {
		return this.base.find(name).map((found : Type) => {
			let mapping = this.base.typeParams().iterate().zip(this.arguments.iterate()).collect(new MapCollector());
			return found.replace(mapping);
		});
	}
	name() : string {
		return this.base.name();
	}
	findBase() : Option<BaseType> {
		return new Some(this.base);
	}
	replace(mapping : Map<string, Type>) : Type {
		return this;
	}
	findName() : Option<string> {
		return this.base.findName();
	}
}
/* private */class Placeholder/*  */ implements Parameter, Value, FindableType, ClassSegment, FunctionSegment, BlockHeader, StatementValue, IncompleteClassSegment {
	input : string;
	constructor (input : string) {
		this.input = input;
	}
	_IncompleteClassSegmentVariant : IncompleteClassSegmentVariant = IncompleteClassSegmentVariant.Placeholder;
	_ValueVariant : ValueVariant = ValueVariant.Placeholder;
	generate() : string {
		return generatePlaceholder(this.input);
	}
	type() : Type {
		return Primitive.Unknown;
	}
	find(name : string) : Option<Type> {
		return new None();
	}
	name() : string {
		return this.input;
	}
	findBase() : Option<BaseType> {
		return new None();
	}
	replace(mapping : Map<string, Type>) : Type {
		return this;
	}
	findName() : Option<string> {
		return new None();
	}
	maybeCreateDefinition() : Option<Definition> {
		return new None();
	}
}
/* private */class StringValue/*  */ implements Value {
	stripped : string;
	constructor (stripped : string) {
		this.stripped = stripped;
	}
	_ValueVariant : ValueVariant = ValueVariant.StringValue;
	generate() : string {
		return this.stripped;
	}
	type() : Type {
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
	generate() : string {
		return this.parent.generate() + "." + this.property + createDebugString(this.type);
	}
	type() : Type {
		return this.type;
	}
}
/* private */class ConstructionCaller/*  */ implements Caller {
	type : Type;
	constructor (type : Type) {
		this.type = type;
	}
	_CallerVariant : CallerVariant = CallerVariant.ConstructionCaller;
	generate() : string {
		return "new " + this.type.generate();
	}
	toFunction() : FunctionType {
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
	ADD : Operator = new Operator("+", "+");
	AND : Operator = new Operator("&&", "&&");
	EQUALS : Operator = new Operator("==", "===");
	Operator() : /* new */;
	Operator() : /* new */;
	OR : Operator = new Operator("||", "||");
	SUBTRACT : Operator = new Operator("-", "-");
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
	generate() : string {
		return this.left().generate() + " " + this.operator.targetRepresentation + " " + this.right().generate();
	}
	type() : Type {
		return Primitive.Unknown;
	}
}
/* private */class Not/*  */ implements Value {
	value : Value;
	constructor (value : Value) {
		this.value = value;
	}
	_ValueVariant : ValueVariant = ValueVariant.Not;
	generate() : string {
		return "!" + this.value.generate();
	}
	type() : Type {
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
	generate() : string {
		return "{" + this.joinStatements() + createIndent(this.depth) + "}";
	}
	joinStatements() : string {
		return this.statements.iterate().map(FunctionSegment.generate).collect(Joiner.empty()).orElse("");
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
	generate() : string {
		let joined = this.parameters.iterate().map(Definition.generate).collect(new Joiner(", ")).orElse("");
		return "(" + joined + ") => " + this.body.generate();
	}
	type() : Type {
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
	generate() : string {
		let joined = this.arguments.iterate().map(Value.generate).collect(new Joiner(", ")).orElse("");
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
	generate() : string {
		return this.parent.generate() + "[" + this.child.generate() + "]";
	}
	type() : Type {
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
	generate() : string {
		return this.stripped + createDebugString(this.type);
	}
}
/* private static */class Maps/*  */ {
	empty<V, K>() : Map<K, V>;
}
/* private */class MapCollector<K, V>/*  */ implements Collector<[K, V], Map<K, V>> {
	createInitial() : Map<K, V> {
		return Maps.empty();
	}
	fold(current : Map<K, V>, element : [K, V]) : Map<K, V> {
		return current.with(element[0], element[1]);
	}
}
/* private static */class ConstructorHeader/*  */ implements Header {
	createDefinition(paramTypes : List<Type>) : Definition {
		return ImmutableDefinition.createSimpleDefinition("new", Primitive.Unknown);
	}
	generateWithParams(joinedParameters : string) : string {
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
	joinStatements(statements : List<FunctionSegment>) : string {
		return statements.iterate().map(FunctionSegment.generate).collect(Joiner.empty()).orElse("");
	}
	generate() : string {
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
	generate() : string {
		let indent : string = createIndent(this.depth);
		let collect = this.statements.iterate().map(FunctionSegment.generate).collect(Joiner.empty()).orElse("");
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
	generate() : string {
		return this.prefix + " (" + this.value1.generate() + ")";
	}
}
/* private static */class Else/*  */ implements BlockHeader {
	generate() : string {
		return "else ";
	}
}
/* private */class Return/*  */ implements StatementValue {
	value : Value;
	constructor (value : Value) {
		this.value = value;
	}
	generate() : string {
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
	generate() : string {
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
	generate() : string {
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
	generate() : string {
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
	generate() : string {
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
		return this.parameters().iterate().map(Definition.findType).collect(new ListCollector());
	}
	maybeCreateDefinition() : Option<Definition> {
		return new Some(this.header.createDefinition(this.findParamTypes()));
	}
}
/* private */class IncompleteClassSegmentWrapper/*  */ implements IncompleteClassSegment {
	segment : ClassSegment;
	constructor (segment : ClassSegment) {
		this.segment = segment;
	}
	_IncompleteClassSegmentVariant : IncompleteClassSegmentVariant = IncompleteClassSegmentVariant.IncompleteClassSegmentWrapper;
	maybeCreateDefinition() : Option<Definition> {
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
	maybeCreateDefinition() : Option<Definition> {
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
	maybeCreateDefinition() : Option<Definition> {
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
		let definitionFromSegments : R = this.segments.iterate().map(IncompleteClassSegment.maybeCreateDefinition).flatMap(Queries.fromOption).collect(new ListCollector());
		return new ObjectType(this.name, this.typeParams, definitionFromSegments.addAllLast(this.parameters), this.variants);
	}
	maybeCreateDefinition() : Option<Definition> {
		return new None();
	}
	joinInterfaces() : string {
		return this.interfaces.iterate().map(Type.generate).collect(new Joiner(", ")).map((inner) => " implements " + inner).orElse("");
	}
	joinTypeParams() : string {
		return this.typeParams().iterate().collect(new Joiner(", ")).map((inner) => "<" + inner + ">").orElse("");
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
	generate() : string {
		return this.value.generate() + " as " + this.type.generate();
	}
}
/* private */class Ok<T, X>/*  */ implements Result<T, X> {
	value : T;
	constructor (value : T) {
		this.value = value;
	}
	_ResultVariant : ResultVariant = ResultVariant.Ok;
	mapValue<R>(mapper : (arg0 : T) => R) : Result<R, X> {
		return new Ok(mapper(this.value));
	}
	match<R>(whenOk : (arg0 : T) => R, whenErr : (arg0 : X) => R) : R {
		return whenOk(this.value);
	}
}
/* private */class Err<T, X>/*  */ implements Result<T, X> {
	error : X;
	constructor (error : X) {
		this.error = error;
	}
	_ResultVariant : ResultVariant = ResultVariant.Err;
	mapValue<R>(mapper : (arg0 : T) => R) : Result<R, X> {
		return new Err(this.error);
	}
	match<R>(whenOk : (arg0 : T) => R, whenErr : (arg0 : X) => R) : R {
		return whenErr(this.error);
	}
}
/* private */class JVMIOError/*  */ implements IOError {
	error : /* IOException */;
	constructor (error : /* IOException */) {
		this.error = error;
	}
	display() : string {
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
	generate() : string {
		let joined = this.values.iterate().map(Value.generate).collect(new Joiner(", ")).orElse("");
		return "[" + joined + "]";
	}
	type() : Type {
		return new TupleType(this.values.iterate().map(Value.type).collect(new ListCollector()));
	}
}
/* private */class MapHead<T, R>/*  */ implements Head<R> {
	head : Head<T>;
	mapper : (arg0 : T) => R;
	constructor (head : Head<T>, mapper : (arg0 : T) => R) {
		this.head = head;
		this.mapper = mapper;
	}
	next() : Option<R> {
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
	next() : Option<[T, R]> {
		return this.head.next().and(this.other.next);
	}
}
/* private */class Primitive/*  */ implements Type {/* Int("number"),
        String("string"),
        Boolean("boolean"),
        Unknown("unknown"),
        Void("void"); */
	value : string;
	constructor (value : string) {
		this.value = value;
	}
	generate() : string {
		return this.value;
	}
	replace(mapping : Map<string, Type>) : Type {
		return this;
	}
	findName() : Option<string> {
		return new None();
	}
}
/* private */class BooleanValue/*  */ implements Value {
	_ValueVariant : ValueVariant = ValueVariant.BooleanValue;/* True("true"), False("false"); */
	value : string;
	constructor (value : string) {
		this.value = value;
	}
	generate() : string {
		return this.value;
	}
	type() : Type {
		return Primitive.Boolean;
	}
}
/* public */class Main/*  */ {
	JVMPath() : /* record */;
	isDebug : boolean = false;
	generatePlaceholder(input : string) : string {
		let replaced = input.replace("/*", "content-start").replace("*/", "content-end");
		return "/* " + replaced + " */";
	}
	joinValues(retainParameters : List<Definition>) : string {
		let inner = retainParameters.iterate().map(Definition.generate).collect(new Joiner(", ")).orElse("");
		return "(" + inner + ")";
	}
	createIndent(depth : number) : string {
		return "\n" + "\t".repeat(depth);
	}
	createDebugString(type : Type) : string {
		if (!Main.isDebug){
			return "";
		}
		return generatePlaceholder(": " + type.generate());
	}
	retainFindableType(type : Type) : Option<FindableType> {
		if (type._Variant === Variant.FindableType){
			let findableType : FindableType = type as FindableType;
			return new Some(findableType);
		}
		return new None();
	}
	main() : void {
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
		let joined = parsed[0].structures.iterate().collect(Joiner.empty()).orElse("");
		return joined + this.generateStatements(parsed[1]);
	}
	generateStatements(statements : List<string>) : string {
		return this.generateAll(this.mergeStatements, statements);
	}
	parseStatements<T>(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => [CompileState, T]) : [CompileState, List<T>] {
		return this.parseAllWithIndices(state, input, this.foldStatementChar, (state3, tuple) => new Some(mapper(state3, tuple.right()))).orElseGet(() => [state, Lists.empty()]);
	}
	generateAll(merger : (arg0 : string, arg1 : string) => string, elements : List<string>) : string {
		return elements.iterate().fold("", merger);
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
		if (tuple[0] === /*  '\"' */){
			let current = tuple[1].append(tuple[0]);
			while (true){
				let maybePopped = current.popAndAppendToTuple();
				if (maybePopped.isEmpty()){
					/* break */;
				}
				let popped = maybePopped.orElse(/* null */);
				current = popped.right();
				if (popped.left() === /*  '\\' */){
					current = current.popAndAppendToOption().orElse(current);
				}
				if (popped.left() === /*  '\"' */){
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
		if (escaped[0] === /*  '\\' */){
			return escaped[1].popAndAppendToOption().orElse(escaped[1]);
		}
		return escaped[1];
	}
	foldStatementChar(state : DivideState, c : string) : DivideState {
		let append : DivideState = state.append(c);
		if (c === /*  ';'  */ && append.isLevel()){
			return append.advance();
		}
		if (c === /*  '}'  */ && append.isShallow()){
			return append.advance().exit();
		}
		if (c === /*  '{'  */ || c === /*  '(' */){
			return append.enter();
		}
		if (c === /*  '}'  */ || c === /*  ')' */){
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
		return this.divideAll(annotationsString.strip(), this.foldByDelimiter).iterate().map(/* String */.strip).filter((value : T) => !value.isEmpty()).map((value : T) => value.substring(1)).map(/* String */.strip).filter((value : T) => !value.isEmpty()).collect(new ListCollector());
	}
	foldByDelimiter(state1 : DivideState, c : string) : DivideState {
		if (c === /*  '\n' */){
			return state1.advance();
		}
		return state1.append(c);
	}
	parseStructureWithMaybePermits(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string, annotations : List<string>) : Option<[CompileState, IncompleteClassSegment]> {
		return this.last(beforeContent, " permits ", (s, s2) => {
			let variants : R = this.divideAll(s2, this.foldValueChar).iterate().map(/* String */.strip).filter((value : T) => !value.isEmpty()).collect(new ListCollector());
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
				let mapper : (arg0 : CompileState, arg1 : string) => [CompileState, string] = (state1, s) => [state1, s.strip()];
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
		let bases : R = prototype.interfaces.iterate().map(Main.retainFindableType).flatMap(Queries.fromOption).map(FindableType.findBase).flatMap(Queries.fromOption).collect(new ListCollector());
		let variantsSuper = bases.iterate().filter((type) => type.variants().contains(prototype.name)).map(BaseType.name).collect(new ListCollector());
		return this.mapUsingState(state2, prototype.segments(), (state1, entry) => this.completeClassSegment(state1, entry.right())).map((oldStatementsTuple : [CompileState, List<R>]) => {
			let oldStatementsState = oldStatementsTuple[0];
			let oldStatements = oldStatementsTuple[1];
			let exited = oldStatementsState.exitDefinitions();
			let fold = variantsSuper.iterate().fold(oldStatements, (classSegmentList, superType) => {
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
				let joined = prototype.variants.iterate().map((inner : T) => "\n\t" + inner).collect(new Joiner(",")).orElse("");
				/* withEnum */ = exited.addStructure("enum " + prototype.name + "Variant" + " {" +
                        joined +
                        "\n}\n");
				let definition : Definition = this.createVariantDefinition(new ObjectType(prototype.name + "Variant", Lists.empty(), Lists.empty(), prototype.variants));
				/* newSegments */ = fold.addFirst(new Statement(1, definition));
			}
			let withMaybeConstructor : List<ClassSegment> = this.attachConstructor(prototype, /* newSegments */);
			let parsed2 = withMaybeConstructor.iterate().map(ClassSegment.generate).collect(Joiner.empty()).orElse("");
			let joinedTypeParams : string = prototype.joinTypeParams();
			let interfacesJoined : string = prototype.joinInterfaces();
			let generated = generatePlaceholder(prototype.beforeInfix().strip()) + prototype.targetInfix() + prototype.name() + joinedTypeParams + generatePlaceholder(prototype.after()) + interfacesJoined + " {" + parsed2 + "\n}\n";
			let compileState = /* withEnum */.popStructName();
			let definedState = compileState.addStructure(generated);
			return [definedState, new Whitespace()];
		});
	}
	createVariantDefinition(type : ObjectType) : Definition {
		return ImmutableDefinition.createSimpleDefinition("_" + type.name, type);
	}
	attachConstructor(prototype : StructurePrototype, segments : List<ClassSegment>) : List<ClassSegment> {
		let parameters = prototype.parameters();
		if (parameters.isEmpty()){
			return segments;
		}
		let definitions : List<ClassSegment> = parameters.iterate(). < /* ClassSegment>map */((definition) => new Statement(1, definition)).collect(new ListCollector());
		let collect = /* parameters.iterate()
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
		return this. < /* Whitespace, IncompleteClassSegment>typed */(() => this.parseWhitespace(input, state)).or(() => this.typed(() => this.parseClass(input, state))).or(() => this.typed(() => this.parseStructure(input, "interface ", "interface ", state))).or(() => this.typed(() => this.parseStructure(input, "record ", "class ", state))).or(() => this.typed(() => this.parseStructure(input, "enum ", "class ", state))).or(() => this.typed(() => this.parseField(input, depth, state))).or(() => this.parseMethod(state, input, depth)).orElseGet(() => [state, new Placeholder(input)]);
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
		if (oldHeader._Variant === Variant.Definition){
			let maybeDefinition : Definition = oldHeader as Definition;
			/* newHeader */ = maybeDefinition.removeAnnotations();
		}
		else {
			/* newHeader */ = oldHeader;
		}
		if (prototype.content().equals(";") || definition.containsAnnotation("Actual")){
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
		return right.iterate().map(this.retainDefinition).flatMap(Queries.fromOption).collect(new ListCollector());
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
			let left = removed[1].iterate().collect(new Joiner("")).orElse("");
			return [right, left];
		});
	}
	parseBlockHeader(state : CompileState, input : string, depth : number) : [CompileState, BlockHeader] {
		let stripped = input.strip();
		return this.parseConditional(state, stripped, "if", depth).or(() => this.parseConditional(state, stripped, "while", depth)).or(() => this.parseElse(state, input)).orElseGet(() => [state, new Placeholder(stripped)]);
	}
	parseElse(state : CompileState, input : string) : Option<[CompileState, BlockHeader]> {
		let stripped = input.strip();
		if (stripped.equals("else")){
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
		if (c === /*  '{'  */ && state.isLevel()){
			return appended.advance();
		}
		if (c === /*  '{' */){
			return appended.enter();
		}
		if (c === /*  '}' */){
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
			if (type.equals(Primitive.Unknown)){
				return source.type();
			}
			else {
				return type;
			}
		});
		return new Some([state.define(definition), new Initialization(definition, source)]);
	}
	parseValue(state : CompileState, input : string, depth : number) : [CompileState, Value] {
		return this.parseBoolean(state, input).or(() => this.parseLambda(state, input, depth)).or(() => this.parseString(state, input)).or(() => this.parseDataAccess(state, input, depth)).or(() => this.parseSymbolValue(state, input)).or(() => this.parseInvokable(state, input, depth)).or(() => this.parseDigits(state, input)).or(() => this.parseInstanceOf(state, input, depth)).or(() => this.parseOperation(state, input, depth, Operator.ADD)).or(() => this.parseOperation(state, input, depth, Operator.EQUALS)).or(() => this.parseOperation(state, input, depth, Operator.SUBTRACT)).or(() => this.parseOperation(state, input, depth, Operator.AND)).or(() => this.parseOperation(state, input, depth, Operator.OR)).or(() => this.parseOperation(state, input, depth, /*  Operator.GREATER_THAN_OR_EQUALS */)).or(() => this.parseOperation(state, input, depth, /*  Operator.LESS_THAN */)).or(() => this.parseNot(state, input, depth)).or(() => this.parseMethodReference(state, input, depth)).orElseGet(() => [state, new Placeholder(input)]);
	}
	parseBoolean(state : CompileState, input : string) : Option<[CompileState, Value]> {
		let stripped = input.strip();
		if (stripped.equals("false")){
			return new Some([state, BooleanValue.False]);
		}
		if (stripped.equals("true")){
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
				let variant : DataAccess = new DataAccess(value, "_" + type.findName().orElse("") + "Variant", Primitive.Unknown);
				let generate = type.findName().orElse("");
				let temp : SymbolValue = new SymbolValue(generate + "Variant." + definition.findType().findName().orElse(""), Primitive.Unknown);
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
					if (/* expectedType */._Variant === Variant.FunctionType){
						let functionType : FunctionType = /* expectedType */ as FunctionType;
						type = functionType.arguments.get(0).orElse(/* null */);
					}
				}
				return this.assembleLambda(state, Lists.of(ImmutableDefinition.createSimpleDefinition(strippedBeforeArrow, type)), valueString, depth);
			}
			if (strippedBeforeArrow.startsWith("(") && strippedBeforeArrow.endsWith(")")){
				let parameterNames : R = this.divideAll(strippedBeforeArrow.substring(1, strippedBeforeArrow.length() - 1), this.foldValueChar).iterate().map(/* String */.strip).filter((value : T) => !value.isEmpty()).map((name : T) => ImmutableDefinition.createSimpleDefinition(name, Primitive.Unknown)).collect(new ListCollector());
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
		let argumentsTuple : T = this.parseValuesWithIndices(oldCallerState, argumentsString, (currentState, pair) => {
			let index = pair.left();
			let element = pair.right();
			let expectedType : T = callerType.arguments.get(index).orElse(Primitive.Unknown);
			let withExpected = currentState.withExpectedType(expectedType);
			let valueTuple : [CompileState, Argument] = this.parseArgument(withExpected, element, depth);
			let valueState = valueTuple[0];
			let value = valueTuple[1];
			let actualType = valueTuple[0].typeRegister.orElse(Primitive.Unknown);
			return new Some([valueState, [value, actualType]]);
		}).orElseGet(() => [oldCallerState, Lists.empty()]);
		let argumentsState = argumentsTuple.left();
		let argumentsWithActualTypes = argumentsTuple.right();
		let arguments = argumentsWithActualTypes.iterate().map(Tuple2.left).map(this.retainValue).flatMap(Queries.fromOption).collect(new ListCollector());
		if (newCaller._CallerVariant === CallerVariant.ConstructionCaller){
			if (constructionCaller.type.findName().filter((value) => value.equals("Tuple2Impl")).isPresent()){
			let constructionCaller : ConstructionCaller = newCaller as ConstructionCaller;
				return new Some([argumentsState, new TupleNode(Lists.of(arguments.get(0).orElse(/* null */), arguments.get(1).orElse(/* null */)))]);
			}
		}
		if (newCaller._CallerVariant === CallerVariant.Value){
			if (value._Variant === Variant.DataAccess){
				let parent : Value = access.parent;
				let property : string = access.property;
				let parentType : Type = parent.type();
				if (/* parentType instanceof TupleType */){
					if (property.equals("left")){
			let value : Value = newCaller as Value;
				let access : DataAccess = value as DataAccess;
						return new Some([state, new IndexValue(parent, new SymbolValue("0", Primitive.Int))]);
					}
					if (property.equals("right")){
						return new Some([state, new IndexValue(parent, new SymbolValue("1", Primitive.Int))]);
					}
				}
			}
		}
		let invokable : Invokable = new Invokable(newCaller, arguments, callerType.returns);
		return new Some([argumentsState, invokable]);
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
				if (type._Variant === Variant.FunctionType){
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
		if (c === /*  '(' */){
			let enter : DivideState = appended.enter();
			if (enter.isShallow()){
				return enter.advance();
			}
			return enter;
		}
		if (c === /*  ')' */){
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
			if (parentType._Variant === Variant.FindableType){
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
			return new Some([state, new StringValue(stripped)]);
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
			}).or(() => this.assembleDefinition(state, Lists.empty(), new None<string>(), name, Lists.empty(), beforeName));
		});
	}
	getOr(state : CompileState, name : string, beforeType : string, type : string, annotations : List<string>) : Option<[CompileState, Definition]> {
		return this.suffix(beforeType.strip(), ">", (withoutTypeParamStart : string) => {
			return this.first(withoutTypeParamStart, "<", (beforeTypeParams, typeParamsString) => {
				let typeParams : [CompileState, List<T>] = this.parseValuesOrEmpty(state, typeParamsString, (state1, s) => new Some([state1, s.strip()]));
				return this.assembleDefinition(typeParams[0], annotations, new Some<string>(beforeTypeParams), name, typeParams[1], type);
			});
		}).or(() => {
			return this.assembleDefinition(state, annotations, new Some<string>(beforeType), name, Lists.empty(), type);
		});
	}
	toLast(input : string, separator : string, folder : (arg0 : DivideState, arg1 : string) => DivideState) : Option<[string, string]> {
		let divisions : List<string> = this.divideAll(input, folder);
		return divisions.removeLast().map((removed : [List<T>, T]) => {
			let left = removed[0].iterate().collect(new Joiner(separator)).orElse("");
			let right = removed[1];
			return [left, right];
		});
	}
	foldTypeSeparator(state : DivideState, c : string) : DivideState {
		if (c === /*  ' '  */ && state.isLevel()){
			return state.advance();
		}
		let appended : DivideState = state.append(c);
		if (c === /*  ' */ < /* ' */){
			return appended.enter();
		}
		if (c === /*  '>' */){
			return appended.exit();
		}
		return appended;
	}
	assembleDefinition(state : CompileState, annotations : List<string>, beforeTypeParams : Option<string>, rawName : string, typeParams : List<string>, type : string) : Option<[CompileState, Definition]> {
		return this.parseType(state.withTypeParams(typeParams), type).flatMap((type1 : [CompileState, Type]) => {
			let stripped = rawName.strip();
			if (!this.isSymbol(stripped)){
				return new None();
			}
			let node : ImmutableDefinition = new ImmutableDefinition(annotations, beforeTypeParams, stripped, type1[1], typeParams);
			return new Some([type1[0], node]);
		});
	}
	foldValueChar(state : DivideState, c : string) : DivideState {
		if (c === /*  ','  */ && state.isLevel()){
			return state.advance();
		}
		let appended : DivideState = state.append(c);
		if (c === /*  ' */ - /* ' */){
			let peeked : string = appended.peek();
			if (peeked === /*  '>' */){
				return appended.popAndAppendToOption().orElse(appended);
			}
			else {
				return appended;
			}
		}
		if (c === /*  ' */ < /* '  */ || c === /*  '('  */ || c === /*  '{' */){
			return appended.enter();
		}
		if (c === /*  '>'  */ || c === /*  ')'  */ || c === /*  '}' */){
			return appended.exit();
		}
		return appended;
	}
	parseType(state : CompileState, input : string) : Option<[CompileState, Type]> {
		let stripped = input.strip();
		if (stripped.equals("int") || stripped.equals("Integer")){
			return new Some([state, Primitive.Int]);
		}
		if (stripped.equals("String") || stripped.equals("char") || stripped.equals("Character")){
			return new Some([state, Primitive.String]);
		}
		if (stripped.equals("var")){
			return new Some([state, Primitive.Unknown]);
		}
		if (stripped.equals("boolean")){
			return new Some([state, Primitive.Boolean]);
		}
		if (stripped.equals("void")){
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
		let children : R = arguments.iterate().map(this.retainType).flatMap(Queries.fromOption).collect(new ListCollector());
		if (base.equals("BiFunction")){
			return [state, new FunctionType(Lists.of(children.get(0).orElse(/* null */), children.get(1).orElse(/* null */)), children.get(2).orElse(/* null */))];
		}
		if (base.equals("Function")){
			return [state, new FunctionType(Lists.of(children.get(0).orElse(/* null */)), children.get(1).orElse(/* null */))];
		}
		if (base.equals("Predicate")){
			return [state, new FunctionType(Lists.of(children.get(0).orElse(/* null */)), Primitive.Boolean)];
		}
		if (base.equals("Supplier")){
			return [state, new FunctionType(Lists.empty(), children.get(0).orElse(/* null */))];
		}
		if (base.equals("Consumer")){
			return [state, new FunctionType(Lists.of(children.get(0).orElse(/* null */)), Primitive.Void)];
		}
		if (base.equals("Tuple2") && children.size() >= 2){
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
				return this.parseValues(state, argumentsString, this.argument).map((argumentsTuple : [CompileState, List<T>]) => {
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
	argument(state : CompileState, input : string) : Option<[CompileState, Argument]> {
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