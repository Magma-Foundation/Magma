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
	SymbolValue
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
	toString() : string;
	generateWithParams(joinedParameters : string) : string;
	createDefinition(paramTypes : List<Type>) : Definition;
	maybeBefore() : Option<string>;
	name() : string;
	type() : Type;
	typeParams() : List<string>;
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
/* private */class Tuple2Impl<A, B>/*  */ implements [A, B] {
	left : A;
	right : B;
	constructor (left : A, right : B) {
		this.left = left;
		this.right = right;
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
		return other().map((otherValue : R) => new Tuple2Impl(this.value, otherValue));
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
/* private */class HeadedIterator<T>/*  */ implements Iterator<T> {
	head : Head<T>;
	constructor (head : Head<T>) {
		this.head = head;
	}
	fold<R>(initial : R, folder : (arg0 : R, arg1 : T) => R) : R {
		current : R = initial;
		while (true){
			finalCurrent : R = current;
			option : Option<R> = this.head.next().map((inner : T) => folder(finalCurrent, inner));
			if (option._variant === OptionVariant.Some){
				some : Some<R> = option as Some<R>;
				current = some.value;
			}
			else {
				return current;
			}
		}
	}
	map<R>(mapper : (arg0 : T) => R) : Iterator<R> {
		return new HeadedIterator(() => this.head.next().map(mapper));
	}
	collect<R>(collector : Collector<T, R>) : R {
		return this.fold(collector.createInitial(), collector.fold);
	}
	filter(predicate : (arg0 : T) => boolean) : Iterator<T> {
		return this.flatMap((element : T) => {
			if (predicate(element)){
				return new HeadedIterator(new SingleHead(element));
			}
			return new HeadedIterator(new EmptyHead());
		});
	}
	next() : Option<T> {
		return this.head.next();
	}
	flatMap<R>(f : (arg0 : T) => Iterator<R>) : Iterator<R> {
		return new HeadedIterator(new FlatMapHead(this.head, f));
	}
	zip<R>(other : Iterator<R>) : Iterator<[T, R]> {
		return new HeadedIterator(() => HeadedIterator.this.head.next().and(other.next));
	}
}
/* private static */class RangeHead/*  */ implements Head<number> {
	length : number;
	counter : number;
	constructor (length : number) {
		this.length = length;
	}
	next() : Option<number> {
		if (this.counter < this.length){
			value : number = this.counter;
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
		joinedAnnotations = this.annotations.iterate().map((value : T) => "@" + value + " ").collect(Joiner.empty()).orElse("");
		joined : string = this.joinTypeParams();
		before : string = this.joinBefore();
		typeString : string = this.generateType();
		return joinedAnnotations + before + this.name + joined + joinedParameters + typeString;
	}
	createDefinition(paramTypes : List<Type>) : Definition {
		type1 : Type = new FunctionType(paramTypes, this.type);
		return new ImmutableDefinition(this.annotations, this.maybeBefore, this.name, type1, this.typeParams);
	}
	containsAnnotation(annotation : string) : boolean {
		return this.annotations.contains(annotation);
	}
	removeAnnotations() : Definition {
		return new ImmutableDefinition(Lists.empty(), this.maybeBefore, this.name, this.type, this.typeParams);
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
		return this.definitions.iterate().filter((definition : T) => definition.name().equals(name)).map(Definition.type).next();
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
		return this.definitions.iterateReversed().flatMap(List.iterate).filter((definition : T) => definition.name().equals(name)).next().map(Definition.type);
	}
	addStructure(structure : string) : CompileState {
		return new CompileState(this.structures.addLast(structure), this.definitions, this.objectTypes, this.structNames, this.typeParams, this.typeRegister, this.functionSegments);
	}
	defineAll(definitions : List<Definition>) : CompileState {
		defined : List<T> = this.definitions.mapLast((frame : List<Definition>) => frame.addAllLast(definitions));
		return new CompileState(this.structures, defined, this.objectTypes, this.structNames, this.typeParams, this.typeRegister, this.functionSegments);
	}
	resolveType(name : string) : Option<Type> {
		maybe : Option<T> = this.structNames.last().filter((inner : T) => inner.left().equals(name));
		if (/* maybe instanceof Some */(/* var found */)){
			return new Some(new ObjectType(/* found */.left(), this.typeParams, this.definitions.last().orElse(Lists.empty()), /* found */.right()));
		}
		maybeTypeParam : Option<T> = this.typeParams.iterate().filter((param : T) => param.equals(name)).next();
		if (maybeTypeParam._variant === OptionVariant.Some){
			some : Some<string> = maybeTypeParam as Some<string>;
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
		removed : T = this.definitions.removeLast().map(Tuple2.left).orElse(this.definitions);
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
			c = this.input.charAt(this.index);
			return new Some(new Tuple2Impl(c, new DivideState(this.input, this.index + 1, this.segments, this.buffer, this.depth)));
		}
		return new None();
	}
	popAndAppendToTuple() : Option<[string, DivideState]> {
		return this.pop().map((tuple : [string, DivideState]) => {
			c = tuple[0]();
			right = tuple[1]();
			return new Tuple2Impl(c, right.append(c));
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
	mapper : (arg0 : T) => Iterator<R>;
	head : Head<T>;
	current : Option<Iterator<R>>;
	constructor (head : Head<T>, mapper : (arg0 : T) => Iterator<R>) {
		this.mapper = mapper;
		this.current = new None();
		this.head = head;
	}
	next() : Option<R> {
		while (true){
			if (this.current.isPresent()){
				inner : Iterator<R> = this.current.orElse(/* null */);
				maybe : Option<R> = inner.next();
				if (maybe.isPresent()){
					return maybe;
				}
				else {
					this.current = new None();
				}
			}
			outer : Option<T> = this.head.next();
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
/* private static */class Iterators/*  */ {
	fromOption<T>(option : Option<T>) : Iterator<T> {
		single : Option<Head<T>> = option.map(SingleHead.new);
		return new HeadedIterator(single.orElseGet(EmptyHead.new));
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
		joined = this.arguments().iterateWithIndices().map((pair) => "arg" + pair.left() + " : " + pair.right().generate()).collect(new Joiner(", ")).orElse("");
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
		joinedArguments = this.arguments.iterate().map(Type.generate).collect(new Joiner(", ")).orElse("");
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
		joinedArguments = this.arguments.iterate().map(Type.generate).collect(new Joiner(", ")).map((inner) => "<" + inner + ">").orElse("");
		return this.base.generate() + joinedArguments;
	}
	find(name : string) : Option<Type> {
		return this.base.find(name).map((found : Type) => {
			mapping = this.base.typeParams().iterate().zip(this.arguments.iterate()).collect(new MapCollector());
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
		joined = this.parameters.iterate().map(Definition.generate).collect(new Joiner(", ")).orElse("");
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
		joined = this.arguments.iterate().map(Value.generate).collect(new Joiner(", ")).orElse("");
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
		return current.with(element[0](), element[1]());
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
		indent : string = createIndent(this.depth);
		generatedHeader : string = this.header.generateWithParams(joinValues(this.parameters));
		generatedStatements : T = this.maybeStatements.map(FunctionNode.joinStatements).map((inner : T) => " {" + inner + indent + "}").orElse(";");
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
		indent : string = createIndent(this.depth);
		collect = this.statements.iterate().map(FunctionSegment.generate).collect(Joiner.empty()).orElse("");
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
		return this.parameters().iterate().map(Definition.type).collect(new ListCollector());
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
		definitionFromSegments : R = this.segments.iterate().map(IncompleteClassSegment.maybeCreateDefinition).flatMap(Iterators.fromOption).collect(new ListCollector());
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
		writer : /* StringWriter */ = new /* StringWriter */();
		/* this.error.printStackTrace(new PrintWriter(writer)) */;
		return writer.toString();
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
		replaced = input.replace("/*", "content-start").replace("*/", "content-end");
		return "/* " + replaced + " */";
	}
	joinValues(retainParameters : List<Definition>) : string {
		inner = retainParameters.iterate().map(Definition.generate).collect(new Joiner(", ")).orElse("");
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
		if (type._variant === Variant.FindableType){
			findableType : FindableType = type as FindableType;
			return new Some(findableType);
		}
		return new None();
	}
	main() : void {
		parent : Path = this.findRoot();
		source : Path = parent.resolve("Main.java");
		target : Path = parent.resolve("main.ts");
		/* source.readString()
                .mapValue(this::compile)
                .match(target::writeString, Some::new)
                .or(this::executeTSC)
                .ifPresent(error -> System.err.println(error.display())) */;
	}
	findRoot() : Path;
	executeTSC() : Option<IOError>;
	compile(input : string) : string {
		state : CompileState = CompileState.createInitial();
		parsed : [CompileState, List<T>] = this.parseStatements(state, input, this.compileRootSegment);
		joined = parsed[0]().structures.iterate().collect(Joiner.empty()).orElse("");
		return joined + this.generateStatements(parsed[1]());
	}
	generateStatements(statements : List<string>) : string {
		return this.generateAll(this.mergeStatements, statements);
	}
	parseStatements<T>(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => [CompileState, T]) : [CompileState, List<T>] {
		return this.parseAllWithIndices(state, input, this.foldStatementChar, (state3, tuple) => new Some(mapper(state3, tuple.right()))).orElseGet(() => new Tuple2Impl(state, Lists.empty()));
	}
	generateAll(merger : (arg0 : string, arg1 : string) => string, elements : List<string>) : string {
		return elements.iterate().fold("", merger);
	}
	parseAllWithIndices<T>(state : CompileState, input : string, folder : (arg0 : DivideState, arg1 : string) => DivideState, mapper : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]>) : Option<[CompileState, List<T>]> {
		stringList : List<string> = this.divideAll(input, folder);
		return this.mapUsingState(state, stringList, mapper);
	}
	mapUsingState<T, R>(state : CompileState, elements : List<T>, mapper : (arg0 : CompileState, arg1 : [number, T]) => Option<[CompileState, R]>) : Option<[CompileState, List<R>]> {
		initial : Option<[CompileState, List<R>]> = new Some(new Tuple2Impl(state, Lists.empty()));
		return elements.iterateWithIndices().fold(initial, (tuple, element) => {
			return tuple.flatMap((inner) => {
				state1 = inner.left();
				right = inner.right();
				return mapper(state1, element).map((applied : [CompileState, R]) => {
					return new Tuple2Impl(applied[0](), right.addLast(applied[1]()));
				});
			});
		});
	}
	mergeStatements(cache : string, statement : string) : string {
		return cache + statement;
	}
	divideAll(input : string, folder : (arg0 : DivideState, arg1 : string) => DivideState) : List<string> {
		current : DivideState = DivideState.createInitial(input);
		while (true){
			maybePopped : Option<R> = current.pop().map((tuple : [string, DivideState]) => {
				return this.foldSingleQuotes(tuple).or(() => this.foldDoubleQuotes(tuple)).orElseGet(() => folder(tuple[1](), tuple[0]()));
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
		if (tuple[0]() === /*  '\"' */){
			current = tuple[1]().append(tuple[0]());
			while (true){
				maybePopped = current.popAndAppendToTuple();
				if (maybePopped.isEmpty()){
					/* break */;
				}
				popped = maybePopped.orElse(/* null */);
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
		appended = tuple[1]().append(tuple[0]());
		return appended.popAndAppendToTuple().map(this.foldEscaped).flatMap(DivideState.popAndAppendToOption);
	}
	foldEscaped(escaped : [string, DivideState]) : DivideState {
		if (escaped[0]() === /*  '\\' */){
			return escaped[1]().popAndAppendToOption().orElse(escaped[1]());
		}
		return escaped[1]();
	}
	foldStatementChar(state : DivideState, c : string) : DivideState {
		append : DivideState = state.append(c);
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
		stripped = input.strip();
		if (stripped.startsWith("package ") || stripped.startsWith("import ")){
			return new Tuple2Impl(state, "");
		}
		return this.parseClass(stripped, state).flatMap((tuple : [CompileState, IncompleteClassSegment]) => this.completeClassSegment(tuple[0](), tuple[1]())).map((tuple0 : T) => new Tuple2Impl(tuple0.left(), tuple0.right().generate())).orElseGet(() => new Tuple2Impl(state, generatePlaceholder(stripped)));
	}
	parseClass(stripped : string, state : CompileState) : Option<[CompileState, IncompleteClassSegment]> {
		return this.parseStructure(stripped, "class ", "class ", state);
	}
	parseStructure(stripped : string, sourceInfix : string, targetInfix : string, state : CompileState) : Option<[CompileState, IncompleteClassSegment]> {
		return this.first(stripped, sourceInfix, (beforeInfix, right) => {
			return this.first(right, "{", (beforeContent, withEnd) => {
				return this.suffix(withEnd.strip(), "}", (content1 : string) => {
					return this.last(beforeInfix.strip(), "\n", (annotationsString, s2) => {
						annotations : List<string> = this.parseAnnotations(annotationsString);
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
			variants : R = this.divideAll(s2, this.foldValueChar).iterate().map(/* String */.strip).filter((value : T) => !value.isEmpty()).collect(new ListCollector());
			return this.parseStructureWithMaybeImplements(targetInfix, state, beforeInfix, s, content1, variants, annotations);
		}).or(() => {
			return this.parseStructureWithMaybeImplements(targetInfix, state, beforeInfix, beforeContent, content1, Lists.empty(), annotations);
		});
	}
	parseStructureWithMaybeImplements(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string, variants : List<string>, annotations : List<string>) : Option<[CompileState, IncompleteClassSegment]> {
		return this.first(beforeContent, " implements ", (s, s2) => {
			return this.parseValues(state, s2, this.parseType).flatMap((interfaces : [CompileState, List<T>]) => {
				return this.parseStructureWithMaybeExtends(targetInfix, state, beforeInfix, s, content1, variants, annotations, interfaces[1]());
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
				parsed : [CompileState, List<Parameter>] = this.parseParameters(state, s2);
				return this.parseStructureWithMaybeTypeParams(targetInfix, parsed[0](), beforeInfix, s1, content1, parsed[1](), variants, annotations, interfaces);
			});
		}).or(() => {
			return this.parseStructureWithMaybeTypeParams(targetInfix, state, beforeInfix, beforeContent, content1, Lists.empty(), variants, annotations, interfaces);
		});
	}
	parseStructureWithMaybeTypeParams(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string, params : List<Parameter>, variants : List<string>, annotations : List<string>, interfaces : List<Type>) : Option<[CompileState, IncompleteClassSegment]> {
		return this.first(beforeContent, "<", (name, withTypeParams) => {
			return this.first(withTypeParams, ">", (typeParamsString, afterTypeParams) => {
				mapper : (arg0 : CompileState, arg1 : string) => [CompileState, string] = (state1, s) => new Tuple2Impl(state1, s.strip());
				typeParams : [CompileState, List<T>] = this.parseValuesOrEmpty(state, typeParamsString, (state1, s) => new Some(mapper(state1, s)));
				return this.assembleStructure(typeParams[0](), targetInfix, annotations, beforeInfix, name, content1, typeParams[1](), afterTypeParams, params, variants, interfaces);
			});
		}).or(() => {
			return this.assembleStructure(state, targetInfix, annotations, beforeInfix, beforeContent, content1, Lists.empty(), "", params, variants, interfaces);
		});
	}
	assembleStructure(state : CompileState, targetInfix : string, annotations : List<string>, beforeInfix : string, rawName : string, content : string, typeParams : List<string>, after : string, rawParameters : List<Parameter>, variants : List<string>, interfaces : List<Type>) : Option<[CompileState, IncompleteClassSegment]> {
		name = rawName.strip();
		if (!this.isSymbol(name)){
			return new None();
		}
		if (annotations.contains("Actual")){
			return new Some(new Tuple2Impl(state, new Whitespace()));
		}
		segmentsTuple : [CompileState, List<T>] = this.parseStatements(state.pushStructName(new Tuple2Impl(name, variants)).withTypeParams(typeParams), content, (state0, input) => this.parseClassSegment(state0, input, 1));
		segmentsState = segmentsTuple[0]();
		segments = segmentsTuple[1]();
		parameters : List<Definition> = this.retainDefinitions(rawParameters);
		prototype : StructurePrototype = new StructurePrototype(targetInfix, beforeInfix, name, typeParams, parameters, after, segments, variants, interfaces);
		return new Some(new Tuple2Impl(segmentsState.addType(prototype.createObjectType()), prototype));
	}
	completeStructure(state : CompileState, prototype : StructurePrototype) : Option<[CompileState, ClassSegment]> {
		thisType : ObjectType = prototype.createObjectType();
		state2 : CompileState = state.enterDefinitions().define(ImmutableDefinition.createSimpleDefinition("this", thisType));
		bases : R = prototype.interfaces.iterate().map(Main.retainFindableType).flatMap(Iterators.fromOption).map(FindableType.findBase).flatMap(Iterators.fromOption).collect(new ListCollector());
		variantsSuper = bases.iterate().filter((type) => type.variants().contains(prototype.name)).map(BaseType.name).collect(new ListCollector());
		return this.mapUsingState(state2, prototype.segments(), (state1, entry) => this.completeClassSegment(state1, entry.right())).map((oldStatementsTuple : [CompileState, List<R>]) => {
			oldStatementsState = oldStatementsTuple[0]();
			oldStatements = oldStatementsTuple[1]();
			exited = oldStatementsState.exitDefinitions();
			fold = variantsSuper.iterate().fold(oldStatements, (classSegmentList, superType) => {
				name = superType + "Variant";
				type : ObjectType = new ObjectType(name, Lists.empty(), Lists.empty(), Lists.empty());
				definition : Definition = this.createVariantDefinition(type);
				return classSegmentList.addFirst(new Statement(1, new Initialization(definition, new SymbolValue(name + "." + prototype.name, type))));
			});
			/* CompileState withEnum */;
			/* List<ClassSegment> newSegments */;
			if (prototype.variants.isEmpty()){
				/* withEnum */ = exited;
				/* newSegments */ = fold;
			}
			else {
				joined = prototype.variants.iterate().map((inner : T) => "\n\t" + inner).collect(new Joiner(",")).orElse("");
				/* withEnum */ = exited.addStructure("enum " + prototype.name + "Variant" + " {" +
                        joined +
                        "\n}\n");
				definition : Definition = this.createVariantDefinition(new ObjectType(prototype.name + "Variant", Lists.empty(), Lists.empty(), prototype.variants));
				/* newSegments */ = fold.addFirst(new Statement(1, definition));
			}
			withMaybeConstructor : List<ClassSegment> = this.attachConstructor(prototype, /* newSegments */);
			parsed2 = withMaybeConstructor.iterate().map(ClassSegment.generate).collect(Joiner.empty()).orElse("");
			joinedTypeParams : string = prototype.joinTypeParams();
			interfacesJoined : string = prototype.joinInterfaces();
			generated = generatePlaceholder(prototype.beforeInfix().strip()) + prototype.targetInfix() + prototype.name() + joinedTypeParams + generatePlaceholder(prototype.after()) + interfacesJoined + " {" + parsed2 + "\n}\n";
			compileState = /* withEnum */.popStructName();
			definedState = compileState.addStructure(generated);
			return new Tuple2Impl(definedState, new Whitespace());
		});
	}
	createVariantDefinition(type : ObjectType) : Definition {
		return ImmutableDefinition.createSimpleDefinition("_" + type.name, type);
	}
	attachConstructor(prototype : StructurePrototype, segments : List<ClassSegment>) : List<ClassSegment> {
		parameters = prototype.parameters();
		if (parameters.isEmpty()){
			return segments;
		}
		definitions : List<ClassSegment> = parameters.iterate(). < /* ClassSegment>map */((definition) => new Statement(1, definition)).collect(new ListCollector());
		collect = /* parameters.iterate()
                .map(definition  */ - /* > {
                    var destination = new DataAccess(new SymbolValue("this", Primitive.Unknown), definition.name(), Primitive.Unknown);
                    return new Assignment */(/* destination */, /*  new SymbolValue(definition.name(), Primitive.Unknown));
                } */). < /* FunctionSegment>map */((assignment) => new Statement(2, assignment)).collect(new ListCollector());
		func : FunctionNode = new FunctionNode(1, new ConstructorHeader(), parameters, new Some(collect));
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
		definition : Definition = classInitialization.definition;
		statement : Statement = new Statement(classInitialization.depth, new Initialization(definition, classInitialization.value));
		return new Some(new Tuple2Impl(state1, statement));
	}
	completeDefinition(state1 : CompileState, classDefinition : ClassDefinition) : Option<[CompileState, ClassSegment]> {
		definition : Definition = classDefinition.definition;
		statement : Statement = new Statement(classDefinition.depth, definition);
		return new Some(new Tuple2Impl(state1, statement));
	}
	retainDefinition(parameter : Parameter) : Option<Definition> {
		if (parameter._variant === ParameterVariant.Definition){
			definition : Definition = parameter as Definition;
			return new Some(definition);
		}
		return new None();
	}
	isSymbol(input : string) : boolean {
		/* for (var i = 0; i < input.length(); i++) */{
			c = input.charAt(/* i */);
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
		slice = input.substring(prefix.length());
		return mapper(slice);
	}
	suffix<T>(input : string, suffix : string, mapper : (arg0 : string) => Option<T>) : Option<T> {
		if (!input.endsWith(suffix)){
			return new None();
		}
		slice = input.substring(0, input.length() - suffix.length());
		return mapper(slice);
	}
	parseClassSegment(state : CompileState, input : string, depth : number) : [CompileState, IncompleteClassSegment] {
		return this. < /* Whitespace, IncompleteClassSegment>typed */(() => this.parseWhitespace(input, state)).or(() => this.typed(() => this.parseClass(input, state))).or(() => this.typed(() => this.parseStructure(input, "interface ", "interface ", state))).or(() => this.typed(() => this.parseStructure(input, "record ", "class ", state))).or(() => this.typed(() => this.parseStructure(input, "enum ", "class ", state))).or(() => this.typed(() => this.parseField(input, depth, state))).or(() => this.parseMethod(state, input, depth)).orElseGet(() => new Tuple2Impl(state, new Placeholder(input)));
	}
	typed<T extends S, S>(action : () => Option<[CompileState, T]>) : Option<[CompileState, S]> {
		return action().map((tuple : [CompileState, T]) => new Tuple2Impl(tuple[0](), tuple[1]()));
	}
	parseWhitespace(input : string, state : CompileState) : Option<[CompileState, Whitespace]> {
		if (input.isBlank()){
			return new Some(new Tuple2Impl(state, new Whitespace()));
		}
		return new None();
	}
	parseMethod(state : CompileState, input : string, depth : number) : Option<[CompileState, IncompleteClassSegment]> {
		return this.first(input, "(", (definitionString, withParams) => {
			return this.first(withParams, ")", (parametersString, rawContent) => {
				return this.parseDefinition(state, definitionString). < Tuple2 < /* CompileState, Header>>map */((tuple) => new Tuple2Impl(tuple.left(), tuple.right())).or(() => this.parseConstructor(state, definitionString)).flatMap((definitionTuple) => this.assembleMethod(depth, parametersString, rawContent, definitionTuple));
			});
		});
	}
	assembleMethod(depth : number, parametersString : string, rawContent : string, definitionTuple : [CompileState, Header]) : Option<[CompileState, IncompleteClassSegment]> {
		definitionState = definitionTuple[0]();
		header = definitionTuple[1]();
		parametersTuple : [CompileState, List<Parameter>] = this.parseParameters(definitionState, parametersString);
		rawParameters = parametersTuple[1]();
		parameters : List<Definition> = this.retainDefinitions(rawParameters);
		prototype : MethodPrototype = new MethodPrototype(depth, header, parameters, rawContent.strip());
		return new Some(new Tuple2Impl(parametersTuple[0]().define(prototype.createDefinition()), prototype));
	}
	completeMethod(state : CompileState, prototype : MethodPrototype) : Option<[CompileState, ClassSegment]> {
		definition : Definition = prototype.createDefinition();
		oldHeader = prototype.header();
		/* Header newHeader */;
		if (oldHeader._variant === Variant.Definition){
			maybeDefinition : Definition = oldHeader as Definition;
			/* newHeader */ = maybeDefinition.removeAnnotations();
		}
		else {
			/* newHeader */ = oldHeader;
		}
		if (prototype.content().equals(";") || definition.containsAnnotation("Actual")){
			return new Some(new Tuple2Impl(state.define(definition), new FunctionNode(prototype.depth(), /* newHeader */, prototype.parameters(), new None())));
		}
		if (prototype.content().startsWith("{") && prototype.content().endsWith("}")){
			substring = prototype.content().substring(1, prototype.content().length() - 1);
			withDefined : CompileState = state.enterDefinitions().defineAll(prototype.parameters());
			statementsTuple : [CompileState, List<T>] = this.parseStatements(withDefined, substring, (state1, input1) => this.parseFunctionSegment(state1, input1, prototype.depth() + 1));
			statements = statementsTuple[1]();
			return new Some(new Tuple2Impl(statementsTuple[0]().exitDefinitions().define(definition), new FunctionNode(prototype.depth(), /* newHeader */, prototype.parameters(), new Some(statements))));
		}
		return new None();
	}
	parseConstructor(state : CompileState, input : string) : Option<[CompileState, Header]> {
		stripped = input.strip();
		if (state.isCurrentStructName(stripped)){
			return new Some(new Tuple2Impl(state, new ConstructorHeader()));
		}
		return new None();
	}
	retainDefinitions(right : List<Parameter>) : List<Definition> {
		return right.iterate().map(this.retainDefinition).flatMap(Iterators.fromOption).collect(new ListCollector());
	}
	parseParameters(state : CompileState, params : string) : [CompileState, List<Parameter>] {
		return this.parseValuesOrEmpty(state, params, (state1, s) => new Some(this.parseParameter(state1, s)));
	}
	parseFunctionSegments(state : CompileState, input : string, depth : number) : [CompileState, List<FunctionSegment>] {
		return this.parseStatements(state, input, (state1, input1) => this.parseFunctionSegment(state1, input1, depth + 1));
	}
	parseFunctionSegment(state : CompileState, input : string, depth : number) : [CompileState, FunctionSegment] {
		stripped = input.strip();
		if (stripped.isEmpty()){
			return new Tuple2Impl(state, new Whitespace());
		}
		return this.parseFunctionStatement(state, depth, stripped).or(() => this.parseBlock(state, depth, stripped)).orElseGet(() => new Tuple2Impl(state, new Placeholder(stripped)));
	}
	parseFunctionStatement(state : CompileState, depth : number, stripped : string) : Option<[CompileState, FunctionSegment]> {
		return this.suffix(stripped, ";", (s : string) => {
			tuple : [CompileState, StatementValue] = this.parseStatementValue(state, s, depth);
			left = tuple[0]();
			right = tuple[1]();
			return new Some(new Tuple2Impl(left, new Statement(depth, right)));
		});
	}
	parseBlock(state : CompileState, depth : number, stripped : string) : Option<[CompileState, FunctionSegment]> {
		return this.suffix(stripped, "}", (withoutEnd : string) => {
			return this.split(() => this.toFirst(withoutEnd), (beforeContent, content) => {
				return this.suffix(beforeContent, "{", (headerString : string) => {
					headerTuple : [CompileState, BlockHeader] = this.parseBlockHeader(state, headerString, depth);
					headerState = headerTuple[0]();
					header = headerTuple[1]();
					statementsTuple : [CompileState, List<FunctionSegment>] = this.parseFunctionSegments(headerState, content, depth);
					statementsState = statementsTuple[0]();
					statements = statementsTuple[1]().addAllFirst(statementsState.functionSegments);
					return new Some(new Tuple2Impl(statementsState.clearFunctionSegments(), new Block(depth, header, statements)));
				});
			});
		});
	}
	toFirst(input : string) : Option<[string, string]> {
		divisions : List<string> = this.divideAll(input, this.foldBlockStart);
		return divisions.removeFirst().map((removed : [T, List<T>]) => {
			right = removed[0]();
			left = removed[1]().iterate().collect(new Joiner("")).orElse("");
			return new Tuple2Impl(right, left);
		});
	}
	parseBlockHeader(state : CompileState, input : string, depth : number) : [CompileState, BlockHeader] {
		stripped = input.strip();
		return this.parseConditional(state, stripped, "if", depth).or(() => this.parseConditional(state, stripped, "while", depth)).or(() => this.parseElse(state, input)).orElseGet(() => new Tuple2Impl(state, new Placeholder(stripped)));
	}
	parseElse(state : CompileState, input : string) : Option<[CompileState, BlockHeader]> {
		stripped = input.strip();
		if (stripped.equals("else")){
			return new Some(new Tuple2Impl(state, new Else()));
		}
		return new None();
	}
	parseConditional(state : CompileState, input : string, prefix : string, depth : number) : Option<[CompileState, BlockHeader]> {
		return this.prefix(input, prefix, (withoutPrefix : string) => {
			return this.prefix(withoutPrefix.strip(), "(", (withoutValueStart : string) => {
				return this.suffix(withoutValueStart, ")", (value : string) => {
					valueTuple : [CompileState, Value] = this.parseValue(state, value, depth);
					value1 = valueTuple[1]();
					return new Some(new Tuple2Impl(valueTuple[0](), new Conditional(prefix, value1)));
				});
			});
		});
	}
	foldBlockStart(state : DivideState, c : string) : DivideState {
		appended : DivideState = state.append(c);
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
		stripped = input.strip();
		if (stripped.startsWith("return ")){
			value = stripped.substring("return ".length());
			tuple : [CompileState, Value] = this.parseValue(state, value, depth);
			value1 = tuple[1]();
			return new Tuple2Impl(tuple[0](), new Return(value1));
		}
		return this.parseAssignment(state, depth, stripped).orElseGet(() => {
			return new Tuple2Impl(state, new Placeholder(stripped));
		});
	}
	parseAssignment(state : CompileState, depth : number, stripped : string) : Option<[CompileState, StatementValue]> {
		return this.first(stripped, "=", (beforeEquals, valueString) => {
			sourceTuple : [CompileState, Value] = this.parseValue(state, valueString, depth);
			sourceState = sourceTuple[0]();
			source = sourceTuple[1]();
			return this.parseDefinition(sourceState, beforeEquals).flatMap((definitionTuple : [CompileState, Definition]) => this.parseInitialization(definitionTuple[0](), definitionTuple[1](), source)).or(() => this.parseAssignment(depth, beforeEquals, sourceState, source));
		});
	}
	parseAssignment(depth : number, beforeEquals : string, sourceState : CompileState, source : Value) : Option<[CompileState, StatementValue]> {
		destinationTuple : [CompileState, Value] = this.parseValue(sourceState, beforeEquals, depth);
		destinationState = destinationTuple[0]();
		destination = destinationTuple[1]();
		return new Some(new Tuple2Impl(destinationState, new Assignment(destination, source)));
	}
	parseInitialization(state : CompileState, rawDefinition : Definition, source : Value) : Option<[CompileState, StatementValue]> {
		definition : Definition = rawDefinition.mapType((type : Type) => {
			if (type.equals(Primitive.Unknown)){
				return source.type();
			}
			else {
				return type;
			}
		});
		return new Some(new Tuple2Impl(state.define(definition), new Initialization(definition, source)));
	}
	parseValue(state : CompileState, input : string, depth : number) : [CompileState, Value] {
		return this.parseBoolean(state, input).or(() => this.parseLambda(state, input, depth)).or(() => this.parseString(state, input)).or(() => this.parseDataAccess(state, input, depth)).or(() => this.parseSymbolValue(state, input)).or(() => this.parseInvokable(state, input, depth)).or(() => this.parseDigits(state, input)).or(() => this.parseInstanceOf(state, input, depth)).or(() => this.parseOperation(state, input, depth, Operator.ADD)).or(() => this.parseOperation(state, input, depth, Operator.EQUALS)).or(() => this.parseOperation(state, input, depth, Operator.SUBTRACT)).or(() => this.parseOperation(state, input, depth, Operator.AND)).or(() => this.parseOperation(state, input, depth, Operator.OR)).or(() => this.parseOperation(state, input, depth, /*  Operator.GREATER_THAN_OR_EQUALS */)).or(() => this.parseOperation(state, input, depth, /*  Operator.LESS_THAN */)).or(() => this.parseNot(state, input, depth)).or(() => this.parseMethodReference(state, input, depth)).orElseGet(() => new Tuple2Impl<CompileState, Value>(state, new Placeholder(input)));
	}
	parseBoolean(state : CompileState, input : string) : Option<[CompileState, Value]> {
		stripped = input.strip();
		if (stripped.equals("false")){
			return new Some(new Tuple2Impl(state, BooleanValue.False));
		}
		if (stripped.equals("true")){
			return new Some(new Tuple2Impl(state, BooleanValue.True));
		}
		return new None();
	}
	parseInstanceOf(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return this.last(input, "instanceof", (s, s2) => {
			childTuple : [CompileState, Value] = this.parseValue(state, s, depth);
			return this.parseDefinition(childTuple[0](), s2).map((definitionTuple : [CompileState, Definition]) => {
				value = childTuple[1]();
				definition = definitionTuple[1]();
				variant : DataAccess = new DataAccess(value, "_variant", Primitive.Unknown);
				type = value.type();
				generate = type.findName().orElse("");
				temp : SymbolValue = new SymbolValue(generate + "Variant." + definition.type().findName().orElse(""), Primitive.Unknown);
				functionSegment : Statement = new Statement(depth + 1, new Initialization(definition, new Cast(value, definition.type())));
				return new Tuple2Impl(definitionTuple[0]().addFunctionSegment(functionSegment).define(definition), new Operation(variant, Operator.EQUALS, temp));
			});
		});
	}
	parseMethodReference(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return this.last(input, "::", (s, s2) => {
			tuple : [CompileState, Value] = this.parseValue(state, s, depth);
			return new Some(new Tuple2Impl(tuple[0](), new DataAccess(tuple[1](), s2, Primitive.Unknown)));
		});
	}
	parseNot(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		stripped = input.strip();
		if (stripped.startsWith("!")){
			slice = stripped.substring(1);
			tuple : [CompileState, Value] = this.parseValue(state, slice, depth);
			value = tuple[1]();
			return new Some(new Tuple2Impl(tuple[0](), new Not(value)));
		}
		return new None();
	}
	parseLambda(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return this.first(input, "->", (beforeArrow, valueString) => {
			strippedBeforeArrow = beforeArrow.strip();
			if (this.isSymbol(strippedBeforeArrow)){
				type : Type = Primitive.Unknown;
				if (/* state.typeRegister instanceof Some */(/* var expectedType */)){
					if (/* expectedType */._variant === Variant.FunctionType){
						functionType : FunctionType = /* expectedType */ as FunctionType;
						type = functionType.arguments.get(0).orElse(/* null */);
					}
				}
				return this.assembleLambda(state, Lists.of(ImmutableDefinition.createSimpleDefinition(strippedBeforeArrow, type)), valueString, depth);
			}
			if (strippedBeforeArrow.startsWith("(") && strippedBeforeArrow.endsWith(")")){
				parameterNames : R = this.divideAll(strippedBeforeArrow.substring(1, strippedBeforeArrow.length() - 1), this.foldValueChar).iterate().map(/* String */.strip).filter((value : T) => !value.isEmpty()).map((name : T) => ImmutableDefinition.createSimpleDefinition(name, Primitive.Unknown)).collect(new ListCollector());
				return this.assembleLambda(state, parameterNames, valueString, depth);
			}
			return new None();
		});
	}
	assembleLambda(state : CompileState, definitions : List<Definition>, valueString : string, depth : number) : Some<[CompileState, Value]> {
		strippedValueString = valueString.strip();
		/* Tuple2Impl<CompileState, LambdaValue> value */;
		state2 : CompileState = state.defineAll(definitions);
		if (strippedValueString.startsWith("{") && strippedValueString.endsWith("}")){
			value1 : [CompileState, List<T>] = this.parseStatements(state2, strippedValueString.substring(1, strippedValueString.length() - 1), (state1, input1) => this.parseFunctionSegment(state1, input1, depth + 1));
			right = value1[1]();
			/* value */ = new Tuple2Impl(value1[0](), new BlockLambdaValue(depth, right));
		}
		else {
			value1 : [CompileState, Value] = this.parseValue(state2, strippedValueString, depth);
			/* value */ = new Tuple2Impl(value1[0](), value1[1]());
		}
		right = /* value */.right();
		return new Some(new Tuple2Impl(/* value */.left(), new Lambda(definitions, right)));
	}
	parseDigits(state : CompileState, input : string) : Option<[CompileState, Value]> {
		stripped = input.strip();
		if (this.isNumber(stripped)){
			return new Some(new Tuple2Impl<CompileState, Value>(state, new SymbolValue(stripped, Primitive.Int)));
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
			c = input.charAt(/* i */);
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
		callerTuple : [CompileState, Caller] = this.invocationHeader(state, depth, callerString);
		oldCallerState = callerTuple[0]();
		oldCaller = callerTuple[1]();
		newCaller : Caller = this.modifyCaller(oldCallerState, oldCaller);
		callerType : FunctionType = this.findCallerType(newCaller);
		argumentsTuple : T = this.parseValuesWithIndices(oldCallerState, argumentsString, (currentState, pair) => {
			index = pair.left();
			element = pair.right();
			expectedType : T = callerType.arguments.get(index).orElse(Primitive.Unknown);
			withExpected = currentState.withExpectedType(expectedType);
			valueTuple : [CompileState, Argument] = this.parseArgument(withExpected, element, depth);
			valueState = valueTuple[0]();
			value = valueTuple[1]();
			actualType = valueTuple[0]().typeRegister.orElse(Primitive.Unknown);
			return new Some(new Tuple2Impl(valueState, new Tuple2Impl(value, actualType)));
		}).orElseGet(() => new Tuple2Impl(oldCallerState, Lists.empty()));
		argumentsState = argumentsTuple.left();
		argumentsWithActualTypes = argumentsTuple.right();
		arguments = argumentsWithActualTypes.iterate().map(Tuple2.left).map(this.retainValue).flatMap(Iterators.fromOption).collect(new ListCollector());
		invokable : Invokable = new Invokable(newCaller, arguments, callerType.returns);
		return new Some(new Tuple2Impl(argumentsState, invokable));
	}
	retainValue(argument : Argument) : Option<Value> {
		if (argument._variant === ArgumentVariant.Value){
			value : Value = argument as Value;
			return new Some(value);
		}
		return new None();
	}
	parseArgument(state : CompileState, element : string, depth : number) : [CompileState, Argument] {
		if (element.isEmpty()){
			return new Tuple2Impl(state, new Whitespace());
		}
		tuple : [CompileState, Value] = this.parseValue(state, element, depth);
		return new Tuple2Impl(tuple[0](), tuple[1]());
	}
	findCallerType(newCaller : Caller) : FunctionType {
		callerType : FunctionType = new FunctionType(Lists.empty(), Primitive.Unknown);
		/* switch (newCaller) */{
			/* case ConstructionCaller constructionCaller -> */{
				callerType = /* constructionCaller */.toFunction();
			}
			/* case Value value -> */{
				type = /* value */.type();
				if (type._variant === Variant.FunctionType){
					functionType : FunctionType = type as FunctionType;
					callerType = functionType;
				}
			}
		}
		return callerType;
	}
	modifyCaller(state : CompileState, oldCaller : Caller) : Caller {
		if (oldCaller._variant === CallerVariant.DataAccess){
			type : Type = this.resolveType(access.parent, state);
			if (/* type instanceof FunctionType */){
			access : DataAccess = oldCaller as DataAccess;
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
			input1 : string = callerString1.substring("new ".length());
			map : Option<R> = this.parseType(state, input1).map((type : [CompileState, Type]) => {
				right = type[1]();
				return new Tuple2Impl<CompileState, Caller>(type[0](), new ConstructionCaller(right));
			});
			if (map.isPresent()){
				return map.orElse(/* null */);
			}
		}
		tuple : [CompileState, Value] = this.parseValue(state, callerString1, depth);
		return new Tuple2Impl(tuple[0](), tuple[1]());
	}
	foldInvocationStart(state : DivideState, c : string) : DivideState {
		appended : DivideState = state.append(c);
		if (c === /*  '(' */){
			enter : DivideState = appended.enter();
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
			property = rawProperty.strip();
			if (!this.isSymbol(property)){
				return new None();
			}
			tuple : [CompileState, Value] = this.parseValue(state, parentString, depth);
			parent = tuple[1]();
			parentType = parent.type();
			if (/* parentType instanceof TupleType */){
				if (property.equals("left")){
					return new Some(new Tuple2Impl(state, new IndexValue(parent, new SymbolValue("0", Primitive.Int))));
				}
				if (property.equals("right")){
					return new Some(new Tuple2Impl(state, new IndexValue(parent, new SymbolValue("1", Primitive.Int))));
				}
			}
			type : Type = Primitive.Unknown;
			if (parentType._variant === Variant.FindableType){
				if (/* objectType.find(property) instanceof Some */(/* var memberType */)){
				objectType : FindableType = parentType as FindableType;
					type = /* memberType */;
				}
			}
			return new Some(new Tuple2Impl(tuple[0](), new DataAccess(parent, property, type)));
		});
	}
	parseString(state : CompileState, input : string) : Option<[CompileState, Value]> {
		stripped = input.strip();
		if (stripped.startsWith("\"") && stripped.endsWith("\"")){
			return new Some(new Tuple2Impl(state, new StringValue(stripped)));
		}
		return new None();
	}
	parseSymbolValue(state : CompileState, value : string) : Option<[CompileState, Value]> {
		stripped = value.strip();
		if (this.isSymbol(stripped)){
			if (/* state.resolveValue(stripped) instanceof Some */(/* var type */)){
				return new Some(new Tuple2Impl(state, new SymbolValue(stripped, type)));
			}
			if (/* state.resolveType(stripped) instanceof Some */(/* var type */)){
				return new Some(new Tuple2Impl(state, new SymbolValue(stripped, type)));
			}
			return new Some(new Tuple2Impl(state, new Placeholder(stripped)));
		}
		return new None();
	}
	parseOperation(state : CompileState, value : string, depth : number, operator : Operator) : Option<[CompileState, Value]> {
		return this.first(value, operator.sourceRepresentation, (leftString, rightString) => {
			leftTuple : [CompileState, Value] = this.parseValue(state, leftString, depth);
			rightTuple : [CompileState, Value] = this.parseValue(leftTuple[0](), rightString, depth);
			left = leftTuple[1]();
			right = rightTuple[1]();
			return new Some(new Tuple2Impl(rightTuple[0](), new Operation(left, operator, right)));
		});
	}
	parseValuesOrEmpty<T>(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) : [CompileState, List<T>] {
		return this.parseValues(state, input, mapper).orElseGet(() => new Tuple2Impl(state, Lists.empty()));
	}
	parseValues<T>(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) : Option<[CompileState, List<T>]> {
		return this.parseValuesWithIndices(state, input, (state1, tuple) => mapper(state1, tuple.right()));
	}
	parseValuesWithIndices<T>(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]>) : Option<[CompileState, List<T>]> {
		return this.parseAllWithIndices(state, input, this.foldValueChar, mapper);
	}
	parseParameter(state : CompileState, input : string) : [CompileState, Parameter] {
		if (input.isBlank()){
			return new Tuple2Impl(state, new Whitespace());
		}
		return this.parseDefinition(state, input).map((tuple : [CompileState, Definition]) => new Tuple2Impl<CompileState, Parameter>(tuple[0](), tuple[1]())).orElseGet(() => new Tuple2Impl(state, new Placeholder(input)));
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
			return new Tuple2Impl(result[0](), new ClassDefinition(depth, result[1]()));
		});
	}
	parseClassInitialization(depth : number, state : CompileState, withoutEnd : string) : Option<[CompileState, IncompleteClassSegment]> {
		return this.first(withoutEnd, "=", (s, s2) => {
			return this.parseDefinition(state, s).map((result : [CompileState, Definition]) => {
				valueTuple : [CompileState, Value] = this.parseValue(result[0](), s2, depth);
				return new Tuple2Impl(valueTuple[0](), new ClassInitialization(depth, result[1](), valueTuple[1]()));
			});
		});
	}
	parseDefinition(state : CompileState, input : string) : Option<[CompileState, Definition]> {
		return this.last(input.strip(), " ", (beforeName, name) => {
			return this.split(() => this.toLast(beforeName, " ", this.foldTypeSeparator), (beforeType, type) => {
				return this.last(beforeType, "\n", (s, s2) => {
					annotations : List<string> = this.parseAnnotations(s);
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
				typeParams : [CompileState, List<T>] = this.parseValuesOrEmpty(state, typeParamsString, (state1, s) => new Some(new Tuple2Impl(state1, s.strip())));
				return this.assembleDefinition(typeParams[0](), annotations, new Some<string>(beforeTypeParams), name, typeParams[1](), type);
			});
		}).or(() => {
			return this.assembleDefinition(state, annotations, new Some<string>(beforeType), name, Lists.empty(), type);
		});
	}
	toLast(input : string, separator : string, folder : (arg0 : DivideState, arg1 : string) => DivideState) : Option<[string, string]> {
		divisions : List<string> = this.divideAll(input, folder);
		return divisions.removeLast().map((removed : [List<T>, T]) => {
			left = removed[0]().iterate().collect(new Joiner(separator)).orElse("");
			right = removed[1]();
			return new Tuple2Impl(left, right);
		});
	}
	foldTypeSeparator(state : DivideState, c : string) : DivideState {
		if (c === /*  ' '  */ && state.isLevel()){
			return state.advance();
		}
		appended : DivideState = state.append(c);
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
			stripped = rawName.strip();
			if (!this.isSymbol(stripped)){
				return new None();
			}
			node : ImmutableDefinition = new ImmutableDefinition(annotations, beforeTypeParams, stripped, type1[1](), typeParams);
			return new Some(new Tuple2Impl(type1[0](), node));
		});
	}
	foldValueChar(state : DivideState, c : string) : DivideState {
		if (c === /*  ','  */ && state.isLevel()){
			return state.advance();
		}
		appended : DivideState = state.append(c);
		if (c === /*  ' */ - /* ' */){
			peeked : string = appended.peek();
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
		stripped = input.strip();
		if (stripped.equals("int") || stripped.equals("Integer")){
			return new Some(new Tuple2Impl(state, Primitive.Int));
		}
		if (stripped.equals("String") || stripped.equals("char") || stripped.equals("Character")){
			return new Some(new Tuple2Impl(state, Primitive.String));
		}
		if (stripped.equals("var")){
			return new Some(new Tuple2Impl(state, Primitive.Unknown));
		}
		if (stripped.equals("boolean")){
			return new Some(new Tuple2Impl(state, Primitive.Boolean));
		}
		if (stripped.equals("void")){
			return new Some(new Tuple2Impl(state, Primitive.Void));
		}
		if (this.isSymbol(stripped)){
			if (/* state.resolveType(stripped) instanceof Some */(/* var resolved */)){
				return new Some(new Tuple2Impl(state, /* resolved */));
			}
			else {
				return new Some(new Tuple2Impl(state, new Placeholder(stripped)));
			}
		}
		return this.parseTemplate(state, input).or(() => this.varArgs(state, input));
	}
	varArgs(state : CompileState, input : string) : Option<[CompileState, Type]> {
		return this.suffix(input, "...", (s : string) => {
			return this.parseType(state, s).map((inner : [CompileState, Type]) => {
				newState = inner[0]();
				child = inner[1]();
				return new Tuple2Impl(newState, new ArrayType(child));
			});
		});
	}
	assembleTemplate(base : string, state : CompileState, arguments : List<Argument>) : [CompileState, Type] {
		children : R = arguments.iterate().map(this.retainType).flatMap(Iterators.fromOption).collect(new ListCollector());
		if (base.equals("BiFunction")){
			return new Tuple2Impl(state, new FunctionType(Lists.of(children.get(0).orElse(/* null */), children.get(1).orElse(/* null */)), children.get(2).orElse(/* null */)));
		}
		if (base.equals("Function")){
			return new Tuple2Impl(state, new FunctionType(Lists.of(children.get(0).orElse(/* null */)), children.get(1).orElse(/* null */)));
		}
		if (base.equals("Predicate")){
			return new Tuple2Impl(state, new FunctionType(Lists.of(children.get(0).orElse(/* null */)), Primitive.Boolean));
		}
		if (base.equals("Supplier")){
			return new Tuple2Impl(state, new FunctionType(Lists.empty(), children.get(0).orElse(/* null */)));
		}
		if (base.equals("Consumer")){
			return new Tuple2Impl(state, new FunctionType(Lists.of(children.get(0).orElse(/* null */)), Primitive.Void));
		}
		if (base.equals("Tuple2") && children.size() >= 2){
			return new Tuple2Impl(state, new TupleType(children));
		}
		if (state.resolveType(base)._variant === OptionVariant.Some){
			baseType : Type = some.value;
			if (baseType._variant === Variant.ObjectType){
			some : Some<Type> = state.resolveType(base) as Some<Type>;
				findableType : ObjectType = baseType as ObjectType;
				return new Tuple2Impl(state, new Template(findableType, children));
			}
		}
		return new Tuple2Impl(state, new Template(new ObjectType(base, Lists.empty(), Lists.empty(), Lists.empty()), children));
	}
	parseTemplate(state : CompileState, input : string) : Option<[CompileState, Type]> {
		return this.suffix(input.strip(), ">", (withoutEnd : string) => {
			return this.first(withoutEnd, "<", (base, argumentsString) => {
				strippedBase = base.strip();
				return this.parseValues(state, argumentsString, this.argument).map((argumentsTuple : [CompileState, List<T>]) => {
					return this.assembleTemplate(strippedBase, argumentsTuple[0](), argumentsTuple[1]());
				});
			});
		});
	}
	retainType(argument : Argument) : Option<Type> {
		if (argument._variant === ArgumentVariant.){
			type : Type = argument as Type;
			return new Some(type);
		}
		else {
			return new None<Type>();
		}
	}
	argument(state : CompileState, input : string) : Option<[CompileState, Argument]> {
		if (input.isBlank()){
			return new Some(new Tuple2Impl(state, new Whitespace()));
		}
		return this.parseType(state, input).map((tuple : [CompileState, Type]) => new Tuple2Impl(tuple[0](), tuple[1]()));
	}
	last<T>(input : string, infix : string, mapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return this.infix(input, infix, this.findLast, mapper);
	}
	findLast(input : string, infix : string) : Option<number> {
		index = input.lastIndexOf(infix);
		if (index === -1){
			return new None<number>();
		}
		return new Some(index);
	}
	first<T>(input : string, infix : string, mapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return this.infix(input, infix, this.findFirst, mapper);
	}
	split<T>(splitter : () => Option<[string, string]>, splitMapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return splitter().flatMap((splitTuple : [string, string]) => splitMapper(splitTuple[0](), splitTuple[1]()));
	}
	infix<T>(input : string, infix : string, locator : (arg0 : string, arg1 : string) => Option<number>, mapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return this.split(() => locator(input, infix).map((index : number) => {
			left = input.substring(0, index);
			right = input.substring(index + infix.length());
			return new Tuple2Impl(left, right);
		}), mapper);
	}
	findFirst(input : string, infix : string) : Option<number> {
		index = input.indexOf(infix);
		if (index === -1){
			return new None<number>();
		}
		return new Some(index);
	}
}
/*  */