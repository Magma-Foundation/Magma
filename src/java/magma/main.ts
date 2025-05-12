/* private */interface Tuple2<A, B>/*   */ {
	left() : A;
	right() : B;
}
enum OptionVariant {
	Some,
	None
}
/* private sealed */interface Option<T>/*  */ {
	_variant : OptionVariant;
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
	_variant : ValueVariant;
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
	_variant : CallerVariant;
	generate() : string;
}
/* private */interface FindableType/*  */ {
	typeParams() : List<string>;
	find(name : string) : Option<Type>;
	name() : string;
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
	IncompleteClassSegmentWrapper,
	MethodPrototype,
	Placeholder,
	StructurePrototype,
	Whitespace
}
/* private sealed */interface IncompleteClassSegment/*  */ {
	_variant : IncompleteClassSegmentVariant;
	maybeCreateDefinition() : Option<Definition>;
}
/* private @ */interface Actual/*  */ {
}
enum ResultVariant {
	Ok,
	Err
}
/* private sealed */interface Result<T, X>/*  */ {
	_variant : ResultVariant;
	mapValue<R>(mapper : (arg0 : T) => R) : Result<R, X>;
	match<R>(whenOk : (arg0 : T) => R, whenErr : (arg0 : X) => R) : R;
}
/* private */interface Path/*  */ {
	readString() : Result<string, /* IOException */>;
	writeString(output : string) : Option</* IOException */>;
	resolve(childName : string) : Path;
}
/* private static final */class None<T>/*  */ {
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
/* private */class Tuple2Impl<A, B>/*  */ {
	constructor (left : A, right : B) {
	}
}
/* private */class Some<T>/*  */ {
	constructor (value : T) {
	}
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
/* private static */class SingleHead<T>/*  */ {
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
/* private static */class EmptyHead<T>/*  */ {
	next() : Option<T> {
		return new None();
	}
}
/* private */class HeadedIterator<T>/*  */ {
	constructor (head : Head<T>) {
	}
	fold<R>(initial : R, folder : (arg0 : R, arg1 : T) => R) : R {
		let current : R = initial;
		while (true){
			let finalCurrent : R = current;
			let option : Option<R> = this.head.next().map((inner : T) => folder(finalCurrent, inner));
			if (option._variant === OptionVariant.Some){
				let some : Some<R> = option as Some<R>;
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
/* private static */class RangeHead/*  */ {
	length : number;
	counter : number;
	constructor (length : number) {
		this.length = length;
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
/* private */class ImmutableDefinition/*  */ {
	constructor (annotations : List<string>, maybeBefore : Option<string>, name : string, type : Type, typeParams : List<string>) {
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
		return this.typeParams.iterate().collect(Joiner.empty()).map((inner) => "<" + inner + ">").orElse("");
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
}
/* private */class ObjectType/*  */ {
	constructor (name : string, typeParams : List<string>, definitions : List<Definition>) {
	}
	generate() : string {
		return this.name;
	}
	replace(mapping : Map<string, Type>) : Type {
		return new ObjectType(this.name, this.typeParams, this.definitions.iterate().map((definition : T) => definition.mapType((type) => type.replace(mapping))).collect(new ListCollector()));
	}
	find(name : string) : Option<Type> {
		return this.definitions.iterate().filter((definition : T) => definition.name().equals(name)).map(Definition.type).next();
	}
	findName() : Option<string> {
		return new Some(this.name);
	}
}
/* private */class TypeParam/*  */ {
	constructor (value : string) {
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
	constructor (structures : List<string>, definitions : List<List<Definition>>, objectTypes : List<ObjectType>, structNames : List<string>, typeParams : List<string>, typeRegister : Option<Type>, functionSegments : List<FunctionSegment>) {
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
		let defined : List<T> = this.definitions.mapLast((frame : List<Definition>) => frame.addAllLast(definitions));
		return new CompileState(this.structures, defined, this.objectTypes, this.structNames, this.typeParams, this.typeRegister, this.functionSegments);
	}
	resolveType(name : string) : Option<Type> {
		if (this.structNames.last().filter((inner : T) => inner.equals(name)).isPresent()){
			return new Some(new ObjectType(name, this.typeParams, this.definitions.last().orElse(Lists.empty())));
		}
		let maybeTypeParam : Option<T> = this.typeParams.iterate().filter((param : T) => param.equals(name)).next();
		if (maybeTypeParam._variant === OptionVariant.Some){
			let some : Some<string> = maybeTypeParam as Some<string>;
			return new Some(new TypeParam(some.value));
		}
		return this.objectTypes.iterate().filter((type : T) => type.name.equals(name)).next().map((type : T) => type);
	}
	define(definition : Definition) : CompileState {
		return new CompileState(this.structures, this.definitions.mapLast((frame : List<Definition>) => frame.addLast(definition)), this.objectTypes, this.structNames, this.typeParams, this.typeRegister, this.functionSegments);
	}
	pushStructName(name : string) : CompileState {
		return new CompileState(this.structures, this.definitions, this.objectTypes, this.structNames.addLast(name), this.typeParams, this.typeRegister, this.functionSegments);
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
			return new Some(new Tuple2Impl(c, new DivideState(this.input, this.index + 1, this.segments, this.buffer, this.depth)));
		}
		return new None();
	}
	popAndAppendToTuple() : Option<[string, DivideState]> {
		return this.pop().map((tuple : [string, DivideState]) => {
			let c = tuple[0]();
			let right = tuple[1]();
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
/* private */class Joiner/*  */ {
	constructor (delimiter : string) {
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
/* private static */class ListCollector<T>/*  */ {
	createInitial() : List<T> {
		return Lists.empty();
	}
	fold(current : List<T>, element : T) : List<T> {
		return current.addLast(element);
	}
}
/* private static */class FlatMapHead<T, R>/*  */ {
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
				let inner : Iterator<R> = this.current.orElse(/* null */);
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
/* private */class ArrayType/*  */ {
	constructor (right : Type) {
	}
	generate() : string {
		return this.right().generate() + "[]";
	}
	replace(mapping : Map<string, Type>) : Type {
		return this;
	}
	findName() : Option<string> {
		return new None();
	}
}
/* private static final */class Whitespace/*  */ {
	generate() : string {
		return "";
	}
	maybeCreateDefinition() : Option<Definition> {
		return new None();
	}
}
/* private static */class Iterators/*  */ {
	fromOption<T>(option : Option<T>) : Iterator<T> {
		let single : Option<Head<T>> = option.map(SingleHead.new);
		return new HeadedIterator(single.orElseGet(EmptyHead.new));
	}
}
/* private */class FunctionType/*  */ {
	constructor (arguments : List<Type>, returns : Type) {
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
/* private */class TupleType/*  */ {
	constructor (arguments : List<Type>) {
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
/* private */class Template/*  */ {
	constructor (base : FindableType, arguments : List<Type>) {
	}
	generate() : string {
		let joinedArguments = this.arguments.iterate().map(Type.generate).collect(new Joiner(", ")).map((inner) => "<" + inner + ">").orElse("");
		return this.base.generate() + joinedArguments;
	}
	typeParams() : List<string> {
		return this.base.typeParams();
	}
	find(name : string) : Option<Type> {
		return this.base.find(name).map((found : Type) => {
			let mapping : R = this.base.typeParams().iterate().zip(this.arguments.iterate()).collect(new MapCollector());
			return found.replace(mapping);
		});
	}
	name() : string {
		return this.base.name();
	}
	replace(mapping : Map<string, Type>) : Type {
		return this;
	}
	findName() : Option<string> {
		return this.base.findName();
	}
}
/* private */class Placeholder/*  */ {
	constructor (input : string) {
	}
	generate() : string {
		return generatePlaceholder(this.input);
	}
	type() : Type {
		return Primitive.Unknown;
	}
	typeParams() : List<string> {
		return Lists.empty();
	}
	find(name : string) : Option<Type> {
		return new None();
	}
	name() : string {
		return this.input;
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
/* private */class StringValue/*  */ {
	constructor (stripped : string) {
	}
	generate() : string {
		return this.stripped;
	}
	type() : Type {
		return Primitive.Unknown;
	}
}
/* private */class DataAccess/*  */ {
	constructor (parent : Value, property : string, type : Type) {
	}
	generate() : string {
		return this.parent.generate() + "." + this.property + createDebugString(this.type);
	}
	type() : Type {
		return this.type;
	}
}
/* private */class ConstructionCaller/*  */ {
	constructor (type : Type) {
	}
	generate() : string {
		return "new " + this.type.generate();
	}
	toFunction() : FunctionType {
		return new FunctionType(Lists.empty(), this.type);
	}
}
/* private */class Operator/*  */ {
	constructor (sourceRepresentation : string, targetRepresentation : string) {
	}
	Operator() : /* new */;
	Operator() : /* new */;
	Operator() : /* new */;
	Operator() : /* new */;
	Operator() : /* new */;
	Operator() : /* new */;
	Operator() : /* new */;
}
/* private */class Operation/*  */ {
	constructor (left : Value, operator : Operator, right : Value) {
	}
	generate() : string {
		return this.left().generate() + " " + this.operator.targetRepresentation + " " + this.right().generate();
	}
	type() : Type {
		return Primitive.Unknown;
	}
}
/* private */class Not/*  */ {
	constructor (value : Value) {
	}
	generate() : string {
		return "!" + this.value.generate();
	}
	type() : Type {
		return Primitive.Unknown;
	}
}
/* private */class BlockLambdaValue/*  */ {
	constructor (depth : number, statements : List<FunctionSegment>) {
	}
	generate() : string {
		return "{" + this.joinStatements() + createIndent(this.depth) + "}";
	}
	joinStatements() : string {
		return this.statements.iterate().map(FunctionSegment.generate).collect(Joiner.empty()).orElse("");
	}
}
/* private */class Lambda/*  */ {
	constructor (parameters : List<Definition>, body : LambdaValue) {
	}
	generate() : string {
		let joined = this.parameters.iterate().map(Definition.generate).collect(new Joiner(", ")).orElse("");
		return "(" + joined + ") => " + this.body.generate();
	}
	type() : Type {
		return Primitive.Unknown;
	}
}
/* private */class Invokable/*  */ {
	constructor (caller : Caller, arguments : List<Value>, type : Type) {
	}
	generate() : string {
		let joined = this.arguments.iterate().map(Value.generate).collect(new Joiner(", ")).orElse("");
		return this.caller.generate() + "(" + joined + ")" + createDebugString(this.type);
	}
}
/* private */class IndexValue/*  */ {
	constructor (parent : Value, child : Value) {
	}
	generate() : string {
		return this.parent.generate() + "[" + this.child.generate() + "]";
	}
	type() : Type {
		return Primitive.Unknown;
	}
}
/* private */class SymbolValue/*  */ {
	constructor (stripped : string, type : Type) {
	}
	generate() : string {
		return this.stripped + createDebugString(this.type);
	}
}
/* private static */class Maps/*  */ {
	empty<VK>() : Map<K, V>;
}
/* private */class MapCollector<K, V>/*  */ {
	createInitial() : Map<K, V> {
		return Maps.empty();
	}
	fold(current : Map<K, V>, element : [K, V]) : Map<K, V> {
		return current.with(element[0](), element[1]());
	}
}
/* private static */class ConstructorHeader/*  */ {
	createDefinition(paramTypes : List<Type>) : Definition {
		return ImmutableDefinition.createSimpleDefinition("new", Primitive.Unknown);
	}
	generateWithParams(joinedParameters : string) : string {
		return "constructor " + joinedParameters;
	}
}
/* private */class Method/*  */ {
	constructor (depth : number, header : Header, parameters : List<Definition>, maybeStatements : Option<List<FunctionSegment>>) {
	}
	joinStatements(statements : List<FunctionSegment>) : string {
		return statements.iterate().map(FunctionSegment.generate).collect(Joiner.empty()).orElse("");
	}
	generate() : string {
		let indent : string = createIndent(this.depth);
		let generatedHeader : string = this.header.generateWithParams(joinValues(this.parameters));
		let generatedStatements : T = this.maybeStatements.map(Method.joinStatements).map((inner : T) => " {" + inner + indent + "}").orElse(";");
		return indent + generatedHeader + generatedStatements;
	}
}
/* private */class Block/*  */ {
	constructor (depth : number, header : BlockHeader, statements : List<FunctionSegment>) {
	}
	generate() : string {
		let indent : string = createIndent(this.depth);
		let collect = this.statements.iterate().map(FunctionSegment.generate).collect(Joiner.empty()).orElse("");
		return indent + this.header.generate() + "{" + collect + indent + "}";
	}
}
/* private */class Conditional/*  */ {
	constructor (prefix : string, value1 : Value) {
	}
	generate() : string {
		return this.prefix + " (" + this.value1.generate() + ")";
	}
}
/* private static */class Else/*  */ {
	generate() : string {
		return "else ";
	}
}
/* private */class Return/*  */ {
	constructor (value : Value) {
	}
	generate() : string {
		return "return " + this.value.generate();
	}
}
/* private */class Initialization/*  */ {
	constructor (definition : Definition, source : Value) {
	}
	generate() : string {
		return "let " + this.definition.generate() + " = " + this.source.generate();
	}
}
/* private */class Assignment/*  */ {
	constructor (destination : Value, source : Value) {
	}
	generate() : string {
		return this.destination.generate() + " = " + this.source.generate();
	}
}
/* private */class Statement/*  */ {
	constructor (depth : number, value : StatementValue) {
	}
	generate() : string {
		return createIndent(this.depth) + this.value.generate() + ";";
	}
}
/* private */class MethodPrototype/*  */ {
	constructor (depth : number, header : Header, parameters : List<Definition>, content : string) {
	}
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
/* private */class IncompleteClassSegmentWrapper/*  */ {
	constructor (segment : ClassSegment) {
	}
	maybeCreateDefinition() : Option<Definition> {
		return new None();
	}
}
/* private */class ClassDefinition/*  */ {
	constructor (definition : Definition, depth : number) {
	}
	maybeCreateDefinition() : Option<Definition> {
		return new Some(this.definition);
	}
}
/* private */class StructurePrototype/*  */ {
	constructor (targetInfix : string, beforeInfix : string, name : string, typeParams : List<string>, parameters : List<Definition>, after : string, segments : List<IncompleteClassSegment>, variants : List<string>) {
	}
	createObjectType() : ObjectType {
		let definitionFromSegments : R = this.segments.iterate().map(IncompleteClassSegment.maybeCreateDefinition).flatMap(Iterators.fromOption).collect(new ListCollector());
		return new ObjectType(this.name, this.typeParams, definitionFromSegments.addAllLast(this.parameters));
	}
	maybeCreateDefinition() : Option<Definition> {
		return new None();
	}
}
/* private */class Cast/*  */ {
	constructor (value : Value, type : Type) {
	}
	generate() : string {
		return this.value.generate() + " as " + this.type.generate();
	}
}
/* private */class Ok<T, X>/*  */ {
	constructor (value : T) {
	}
	mapValue<R>(mapper : (arg0 : T) => R) : Result<R, X> {
		return new Ok(mapper(this.value));
	}
	match<R>(whenOk : (arg0 : T) => R, whenErr : (arg0 : X) => R) : R {
		return whenOk(this.value);
	}
}
/* private */class Err<T, X>/*  */ {
	constructor (error : X) {
	}
	mapValue<R>(mapper : (arg0 : T) => R) : Result<R, X> {
		return new Err(this.error);
	}
	match<R>(whenOk : (arg0 : T) => R, whenErr : (arg0 : X) => R) : R {
		return whenErr(this.error);
	}
}
/* private */class Primitive/*  */ {
	@nt("number"), @tring("string"), @oolean("boolean"), @nknown("unknown"), Void("void") : /*  */;
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
/* private */class BooleanValue/*  */ {/* True("true"), False("false"); */
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
/* public */class Main/*  */ {/* 

    private static final boolean isDebug = false; */
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
	main() : void {
		let parent : Path = this.findRoot();
		let source : Path = parent.resolve("Main.java");
		let target : Path = parent.resolve("main.ts");
		/* source.readString()
                .mapValue(this::compile)
                .match(target::writeString, Some::new)
                .or(this::executeTSC)
                .ifPresent(Throwable::printStackTrace) */;
	}
	findRoot() : Path;
	executeTSC() : Option</* IOException */>;
	compile(input : string) : string {
		let state : CompileState = CompileState.createInitial();
		let parsed : [CompileState, List<T>] = this.parseStatements(state, input, this.compileRootSegment);
		let joined = parsed[0]().structures.iterate().collect(Joiner.empty()).orElse("");
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
		let stringList : List<string> = this.divideAll(input, folder);
		return this.mapUsingState(state, stringList, mapper);
	}
	mapUsingState<TR>(state : CompileState, elements : List<T>, mapper : (arg0 : CompileState, arg1 : [number, T]) => Option<[CompileState, R]>) : Option<[CompileState, List<R>]> {
		let initial : Option<[CompileState, List<R>]> = new Some(new Tuple2Impl(state, Lists.empty()));
		return elements.iterateWithIndices().fold(initial, (tuple, element) => {
			return tuple.flatMap((inner) => {
				let state1 = inner.left();
				let right = inner.right();
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
		let current : DivideState = DivideState.createInitial(input);
		while (true){
			let maybePopped : Option<R> = current.pop().map((tuple : [string, DivideState]) => {
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
			let current = tuple[1]().append(tuple[0]());
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
		let appended = tuple[1]().append(tuple[0]());
		return appended.popAndAppendToTuple().map(this.foldEscaped).flatMap(DivideState.popAndAppendToOption);
	}
	foldEscaped(escaped : [string, DivideState]) : DivideState {
		if (escaped[0]() === /*  '\\' */){
			return escaped[1]().popAndAppendToOption().orElse(escaped[1]());
		}
		return escaped[1]();
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
			return this.parseStructureWithMaybeExtends(targetInfix, state, beforeInfix, s, content1, variants, annotations);
		}).or(() => {
			return this.parseStructureWithMaybeExtends(targetInfix, state, beforeInfix, beforeContent, content1, variants, annotations);
		});
	}
	parseStructureWithMaybeExtends(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string, variants : List<string>, annotations : List<string>) : Option<[CompileState, IncompleteClassSegment]> {
		return this.first(beforeContent, " extends ", (s, s2) => {
			return this.parseStructureWithMaybeParams(targetInfix, state, beforeInfix, s, content1, variants, annotations);
		}).or(() => {
			return this.parseStructureWithMaybeParams(targetInfix, state, beforeInfix, beforeContent, content1, variants, annotations);
		});
	}
	parseStructureWithMaybeParams(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string, variants : List<string>, annotations : List<string>) : Option<[CompileState, IncompleteClassSegment]> {
		return this.suffix(beforeContent.strip(), ")", (s : string) => {
			return this.first(s, "(", (s1, s2) => {
				let parsed : [CompileState, List<Parameter>] = this.parseParameters(state, s2);
				return this.parseStructureWithMaybeTypeParams(targetInfix, parsed[0](), beforeInfix, s1, content1, parsed[1](), variants, annotations);
			});
		}).or(() => {
			return this.parseStructureWithMaybeTypeParams(targetInfix, state, beforeInfix, beforeContent, content1, Lists.empty(), variants, annotations);
		});
	}
	parseStructureWithMaybeTypeParams(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string, params : List<Parameter>, variants : List<string>, annotations : List<string>) : Option<[CompileState, IncompleteClassSegment]> {
		return this.first(beforeContent, "<", (name, withTypeParams) => {
			return this.first(withTypeParams, ">", (typeParamsString, afterTypeParams) => {
				let mapper : (arg0 : CompileState, arg1 : string) => [CompileState, string] = (state1, s) => new Tuple2Impl(state1, s.strip());
				let typeParams : [CompileState, List<T>] = this.parseValuesOrEmpty(state, typeParamsString, (state1, s) => new Some(mapper(state1, s)));
				return this.assembleStructure(typeParams[0](), targetInfix, annotations, beforeInfix, name, content1, typeParams[1](), afterTypeParams, params, variants);
			});
		}).or(() => {
			return this.assembleStructure(state, targetInfix, annotations, beforeInfix, beforeContent, content1, Lists.empty(), "", params, variants);
		});
	}
	assembleStructure(state : CompileState, targetInfix : string, annotations : List<string>, beforeInfix : string, rawName : string, content : string, typeParams : List<string>, after : string, rawParameters : List<Parameter>, variants : List<string>) : Option<[CompileState, IncompleteClassSegment]> {
		let name = rawName.strip();
		if (!this.isSymbol(name)){
			return new None();
		}
		if (annotations.contains("Actual")){
			return new Some(new Tuple2Impl(state, new Whitespace()));
		}
		let segmentsTuple : [CompileState, List<T>] = this.parseStatements(state.pushStructName(name).withTypeParams(typeParams), content, (state0, input) => this.parseClassSegment(state0, input, 1));
		let segmentsState = segmentsTuple[0]();
		let segments = segmentsTuple[1]();
		let parameters : List<Definition> = this.retainDefinitions(rawParameters);
		let prototype : StructurePrototype = new StructurePrototype(targetInfix, beforeInfix, name, typeParams, parameters, after, segments, variants);
		return new Some(new Tuple2Impl(segmentsState.addType(prototype.createObjectType()), prototype));
	}
	completeStructure(state : CompileState, prototype : StructurePrototype) : Option<[CompileState, ClassSegment]> {
		let thisType : ObjectType = prototype.createObjectType();
		let state2 : CompileState = state.enterDefinitions().define(ImmutableDefinition.createSimpleDefinition("this", thisType));
		return this.mapUsingState(state2, prototype.segments(), (state1, entry) => this.completeClassSegment(state1, entry.right())).map((completedTuple : [CompileState, List<R>]) => {
			let completedState = completedTuple[0]();
			let completed = completedTuple[1]();
			let exited = completedState.exitDefinitions();
			/* CompileState withEnum */;
			/* List<ClassSegment> completed1 */;
			if (prototype.variants.isEmpty()){
				/* withEnum */ = exited;
				/* completed1 */ = completed;
			}
			else {
				let joined = prototype.variants.iterate().map((inner : T) => "\n\t" + inner).collect(new Joiner(",")).orElse("");
				let enumName = prototype.name + "Variant";
				/* withEnum */ = exited.addStructure("enum " + enumName + " {" +
                        joined +
                        "\n}\n");
				let definition : Definition = ImmutableDefinition.createSimpleDefinition("_variant", new ObjectType(enumName, Lists.empty(), Lists.empty()));
				/* completed1 */ = completed.addFirst(new Statement(1, definition));
			}
			let withMaybeConstructor : List<ClassSegment> = this.atttachConstructor(prototype, /* completed1 */);
			let parsed2 = withMaybeConstructor.iterate().map(ClassSegment.generate).collect(Joiner.empty()).orElse("");
			let joinedTypeParams = prototype.typeParams().iterate().collect(new Joiner(", ")).map((inner) => "<" + inner + ">").orElse("");
			let generated = generatePlaceholder(prototype.beforeInfix().strip()) + prototype.targetInfix() + prototype.name() + joinedTypeParams + generatePlaceholder(prototype.after()) + " {" + parsed2 + "\n}\n";
			let compileState = /* withEnum */.popStructName();
			let definedState = compileState.addStructure(generated);
			return new Tuple2Impl(definedState, new Whitespace());
		});
	}
	atttachConstructor(prototype : StructurePrototype, segments : List<ClassSegment>) : List<ClassSegment> {
		/* List<ClassSegment> withMaybeConstructor */;
		if (prototype.parameters().isEmpty()){
			/* withMaybeConstructor */ = segments;
		}
		else {
			/* withMaybeConstructor */ = segments.addFirst(new Method(1, new ConstructorHeader(), prototype.parameters(), new Some(Lists.empty())));
		}
		return /* withMaybeConstructor */;
	}
	completeClassSegment(state1 : CompileState, segment : IncompleteClassSegment) : Option<[CompileState, ClassSegment]> {
		/* return switch (segment) */{
			/* case IncompleteClassSegmentWrapper wrapper -> new Some<>(new Tuple2Impl<>(state1, wrapper.segment)) */;
			/* case MethodPrototype methodPrototype -> this.completeMethod(state1, methodPrototype) */;
			/* case Whitespace whitespace -> new Some<>(new Tuple2Impl<>(state1, whitespace)) */;
			/* case Placeholder placeholder -> new Some<>(new Tuple2Impl<>(state1, placeholder)) */;
			/* case ClassDefinition classDefinition -> this.completeDefinition(state1, classDefinition) */;
			/* case StructurePrototype structurePrototype -> this.completeStructure(state1, structurePrototype) */;
		}
		/*  */;
	}
	completeDefinition(state1 : CompileState, classDefinition : ClassDefinition) : Option<[CompileState, ClassSegment]> {
		let definition : StatementValue = classDefinition.definition;
		let statement : Statement = new Statement(classDefinition.depth, definition);
		return new Some(new Tuple2Impl(state1, statement));
	}
	retainDefinition(parameter : Parameter) : Option<Definition> {
		if (parameter._variant === ParameterVariant.Definition){
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
		return this. < /* Whitespace, IncompleteClassSegment>typed */(() => this.parseWhitespace(input, state)).or(() => this.typed(() => this.parseClass(input, state))).or(() => this.typed(() => this.parseStructure(input, "interface ", "interface ", state))).or(() => this.typed(() => this.parseStructure(input, "record ", "class ", state))).or(() => this.typed(() => this.parseStructure(input, "enum ", "class ", state))).or(() => this.parseMethod(state, input, depth)).or(() => this.typed(() => this.parseDefinitionStatement(input, depth, state))).orElseGet(() => new Tuple2Impl(state, new Placeholder(input)));
	}
	typed<T extends SS>(action : () => Option<[CompileState, T]>) : Option<[CompileState, S]> {
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
		let definitionState = definitionTuple[0]();
		let header = definitionTuple[1]();
		let parametersTuple : [CompileState, List<Parameter>] = this.parseParameters(definitionState, parametersString);
		let rawParameters = parametersTuple[1]();
		let parameters : List<Definition> = this.retainDefinitions(rawParameters);
		let prototype : MethodPrototype = new MethodPrototype(depth, header, parameters, rawContent.strip());
		return new Some(new Tuple2Impl(parametersTuple[0]().define(prototype.createDefinition()), prototype));
	}
	completeMethod(state : CompileState, prototype : MethodPrototype) : Option<[CompileState, ClassSegment]> {
		let definition : Definition = prototype.createDefinition();
		let oldHeader = prototype.header();
		/* Header newHeader */;
		if (oldHeader._variant === Variant.Definition){
			let maybeDefinition : Definition = oldHeader as Definition;
			/* newHeader */ = maybeDefinition.removeAnnotations();
		}
		else {
			/* newHeader */ = oldHeader;
		}
		if (prototype.content().equals(";") || definition.containsAnnotation("Actual")){
			return new Some(new Tuple2Impl(state.define(definition), new Method(prototype.depth(), /* newHeader */, prototype.parameters(), new None())));
		}
		if (prototype.content().startsWith("{") && prototype.content().endsWith("}")){
			let substring = prototype.content().substring(1, prototype.content().length() - 1);
			let withDefined : CompileState = state.enterDefinitions().defineAll(prototype.parameters());
			let statementsTuple : [CompileState, List<T>] = this.parseStatements(withDefined, substring, (state1, input1) => this.parseFunctionSegment(state1, input1, prototype.depth() + 1));
			let statements = statementsTuple[1]();
			return new Some(new Tuple2Impl(statementsTuple[0]().exitDefinitions().define(definition), new Method(prototype.depth(), /* newHeader */, prototype.parameters(), new Some(statements))));
		}
		return new None();
	}
	parseConstructor(state : CompileState, input : string) : Option<[CompileState, Header]> {
		let stripped = input.strip();
		if (stripped.equals(state.structNames.last().orElse(""))){
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
		let stripped = input.strip();
		if (stripped.isEmpty()){
			return new Tuple2Impl(state, new Whitespace());
		}
		return this.parseFunctionStatement(state, depth, stripped).or(() => this.parseBlock(state, depth, stripped)).orElseGet(() => new Tuple2Impl(state, new Placeholder(stripped)));
	}
	parseFunctionStatement(state : CompileState, depth : number, stripped : string) : Option<[CompileState, FunctionSegment]> {
		return this.suffix(stripped, ";", (s : string) => {
			let tuple : [CompileState, StatementValue] = this.parseStatementValue(state, s, depth);
			let left = tuple[0]();
			let right = tuple[1]();
			return new Some(new Tuple2Impl(left, new Statement(depth, right)));
		});
	}
	parseBlock(state : CompileState, depth : number, stripped : string) : Option<[CompileState, FunctionSegment]> {
		return this.suffix(stripped, "}", (withoutEnd : string) => {
			return this.split(() => this.toFirst(withoutEnd), (beforeContent, content) => {
				return this.suffix(beforeContent, "{", (headerString : string) => {
					let headerTuple : [CompileState, BlockHeader] = this.parseBlockHeader(state, headerString, depth);
					let headerState = headerTuple[0]();
					let header = headerTuple[1]();
					let statementsTuple : [CompileState, List<FunctionSegment>] = this.parseFunctionSegments(headerState, content, depth);
					let statementsState = statementsTuple[0]();
					let statements = statementsTuple[1]().addAllFirst(statementsState.functionSegments);
					return new Some(new Tuple2Impl(statementsState.clearFunctionSegments(), new Block(depth, header, statements)));
				});
			});
		});
	}
	toFirst(input : string) : Option<[string, string]> {
		let divisions : List<string> = this.divideAll(input, this.foldBlockStart);
		return divisions.removeFirst().map((removed : [T, List<T>]) => {
			let right = removed[0]();
			let left = removed[1]().iterate().collect(new Joiner("")).orElse("");
			return new Tuple2Impl(right, left);
		});
	}
	parseBlockHeader(state : CompileState, input : string, depth : number) : [CompileState, BlockHeader] {
		let stripped = input.strip();
		return this.parseConditional(state, stripped, "if", depth).or(() => this.parseConditional(state, stripped, "while", depth)).or(() => this.parseElse(state, input)).orElseGet(() => new Tuple2Impl(state, new Placeholder(stripped)));
	}
	parseElse(state : CompileState, input : string) : Option<[CompileState, BlockHeader]> {
		let stripped = input.strip();
		if (stripped.equals("else")){
			return new Some(new Tuple2Impl(state, new Else()));
		}
		return new None();
	}
	parseConditional(state : CompileState, input : string, prefix : string, depth : number) : Option<[CompileState, BlockHeader]> {
		return this.prefix(input, prefix, (withoutPrefix : string) => {
			return this.prefix(withoutPrefix.strip(), "(", (withoutValueStart : string) => {
				return this.suffix(withoutValueStart, ")", (value : string) => {
					let valueTuple : [CompileState, Value] = this.parseValue(state, value, depth);
					let value1 = valueTuple[1]();
					return new Some(new Tuple2Impl(valueTuple[0](), new Conditional(prefix, value1)));
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
			let value1 = tuple[1]();
			return new Tuple2Impl(tuple[0](), new Return(value1));
		}
		return this.parseAssignment(state, depth, stripped).orElseGet(() => {
			return new Tuple2Impl(state, new Placeholder(stripped));
		});
	}
	parseAssignment(state : CompileState, depth : number, stripped : string) : Option<[CompileState, StatementValue]> {
		return this.first(stripped, "=", (beforeEquals, valueString) => {
			let sourceTuple : [CompileState, Value] = this.parseValue(state, valueString, depth);
			let sourceState = sourceTuple[0]();
			let source = sourceTuple[1]();
			return this.parseDefinition(sourceState, beforeEquals).flatMap((definitionTuple : [CompileState, Definition]) => this.parseInitialization(definitionTuple[0](), definitionTuple[1](), source)).or(() => this.parseAssignment(depth, beforeEquals, sourceState, source));
		});
	}
	parseAssignment(depth : number, beforeEquals : string, sourceState : CompileState, source : Value) : Option<[CompileState, StatementValue]> {
		let destinationTuple : [CompileState, Value] = this.parseValue(sourceState, beforeEquals, depth);
		let destinationState = destinationTuple[0]();
		let destination = destinationTuple[1]();
		return new Some(new Tuple2Impl(destinationState, new Assignment(destination, source)));
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
		return new Some(new Tuple2Impl(state.define(definition), new Initialization(definition, source)));
	}
	parseValue(state : CompileState, input : string, depth : number) : [CompileState, Value] {
		return this.parseBoolean(state, input).or(() => this.parseLambda(state, input, depth)).or(() => this.parseString(state, input)).or(() => this.parseDataAccess(state, input, depth)).or(() => this.parseSymbolValue(state, input)).or(() => this.parseInvokable(state, input, depth)).or(() => this.parseDigits(state, input)).or(() => this.parseInstanceOf(state, input, depth)).or(() => this.parseOperation(state, input, depth, Operator.ADD)).or(() => this.parseOperation(state, input, depth, Operator.EQUALS)).or(() => this.parseOperation(state, input, depth, Operator.SUBTRACT)).or(() => this.parseOperation(state, input, depth, Operator.AND)).or(() => this.parseOperation(state, input, depth, Operator.OR)).or(() => this.parseOperation(state, input, depth, /*  Operator.GREATER_THAN_OR_EQUALS */)).or(() => this.parseOperation(state, input, depth, /*  Operator.LESS_THAN */)).or(() => this.parseNot(state, input, depth)).or(() => this.parseMethodReference(state, input, depth)).orElseGet(() => new Tuple2Impl<CompileState, Value>(state, new Placeholder(input)));
	}
	parseBoolean(state : CompileState, input : string) : Option<[CompileState, Value]> {
		let stripped = input.strip();
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
			let childTuple : [CompileState, Value] = this.parseValue(state, s, depth);
			return this.parseDefinition(childTuple[0](), s2).map((definitionTuple : [CompileState, Definition]) => {
				let value = childTuple[1]();
				let definition = definitionTuple[1]();
				let variant : DataAccess = new DataAccess(value, "_variant", Primitive.Unknown);
				let type = value.type();
				let generate = type.findName().orElse("");
				let temp : SymbolValue = new SymbolValue(generate + "Variant." + definition.type().findName().orElse(""), Primitive.Unknown);
				let functionSegment : Statement = new Statement(depth + 1, new Initialization(definition, new Cast(value, definition.type())));
				return new Tuple2Impl(definitionTuple[0]().addFunctionSegment(functionSegment).define(definition), new Operation(variant, Operator.EQUALS, temp));
			});
		});
	}
	parseMethodReference(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return this.last(input, "::", (s, s2) => {
			let tuple : [CompileState, Value] = this.parseValue(state, s, depth);
			return new Some(new Tuple2Impl(tuple[0](), new DataAccess(tuple[1](), s2, Primitive.Unknown)));
		});
	}
	parseNot(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		let stripped = input.strip();
		if (stripped.startsWith("!")){
			let slice = stripped.substring(1);
			let tuple : [CompileState, Value] = this.parseValue(state, slice, depth);
			let value = tuple[1]();
			return new Some(new Tuple2Impl(tuple[0](), new Not(value)));
		}
		return new None();
	}
	parseLambda(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return this.first(input, "->", (beforeArrow, valueString) => {
			let strippedBeforeArrow = beforeArrow.strip();
			if (this.isSymbol(strippedBeforeArrow)){
				let type : Type = Primitive.Unknown;
				if (/* state.typeRegister instanceof Some */(/* var expectedType */)){
					if (/* expectedType */._variant === Variant.FunctionType){
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
			let right = value1[1]();
			/* value */ = new Tuple2Impl(value1[0](), new BlockLambdaValue(depth, right));
		}
		else {
			let value1 : [CompileState, Value] = this.parseValue(state2, strippedValueString, depth);
			/* value */ = new Tuple2Impl(value1[0](), value1[1]());
		}
		let right = /* value */.right();
		return new Some(new Tuple2Impl(/* value */.left(), new Lambda(definitions, right)));
	}
	parseDigits(state : CompileState, input : string) : Option<[CompileState, Value]> {
		let stripped = input.strip();
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
		let oldCallerState = callerTuple[0]();
		let oldCaller = callerTuple[1]();
		let newCaller : Caller = this.modifyCaller(oldCallerState, oldCaller);
		let callerType : FunctionType = this.findCallerType(newCaller);
		let argumentsTuple : T = this.parseValuesWithIndices(oldCallerState, argumentsString, (currentState, pair) => {
			let index = pair.left();
			let element = pair.right();
			let expectedType : T = callerType.arguments.get(index).orElse(Primitive.Unknown);
			let withExpected = currentState.withExpectedType(expectedType);
			let valueTuple : [CompileState, Argument] = this.parseArgument(withExpected, element, depth);
			let valueState = valueTuple[0]();
			let value = valueTuple[1]();
			let actualType = valueTuple[0]().typeRegister.orElse(Primitive.Unknown);
			return new Some(new Tuple2Impl(valueState, new Tuple2Impl(value, actualType)));
		}).orElseGet(() => new Tuple2Impl(oldCallerState, Lists.empty()));
		let argumentsState = argumentsTuple.left();
		let argumentsWithActualTypes = argumentsTuple.right();
		let arguments = argumentsWithActualTypes.iterate().map(Tuple2.left).map(this.retainValue).flatMap(Iterators.fromOption).collect(new ListCollector());
		let invokable : Invokable = new Invokable(newCaller, arguments, callerType.returns);
		return new Some(new Tuple2Impl(argumentsState, invokable));
	}
	retainValue(argument : Argument) : Option<Value> {
		if (argument._variant === ArgumentVariant.Value){
			let value : Value = argument as Value;
			return new Some(value);
		}
		return new None();
	}
	parseArgument(state : CompileState, element : string, depth : number) : [CompileState, Argument] {
		if (element.isEmpty()){
			return new Tuple2Impl(state, new Whitespace());
		}
		let tuple : [CompileState, Value] = this.parseValue(state, element, depth);
		return new Tuple2Impl(tuple[0](), tuple[1]());
	}
	findCallerType(newCaller : Caller) : FunctionType {
		let callerType : FunctionType = new FunctionType(Lists.empty(), Primitive.Unknown);
		/* switch (newCaller) */{
			/* case ConstructionCaller constructionCaller -> */{
				callerType = /* constructionCaller */.toFunction();
			}
			/* case Value value -> */{
				let type = /* value */.type();
				if (type._variant === Variant.FunctionType){
					let functionType : FunctionType = type as FunctionType;
					callerType = functionType;
				}
			}
		}
		return callerType;
	}
	modifyCaller(state : CompileState, oldCaller : Caller) : Caller {
		if (oldCaller._variant === CallerVariant.DataAccess){
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
				let right = type[1]();
				return new Tuple2Impl<CompileState, Caller>(type[0](), new ConstructionCaller(right));
			});
			if (map.isPresent()){
				return map.orElse(/* null */);
			}
		}
		let tuple : [CompileState, Value] = this.parseValue(state, callerString1, depth);
		return new Tuple2Impl(tuple[0](), tuple[1]());
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
			let parent = tuple[1]();
			let parentType = parent.type();
			if (/* parentType instanceof TupleType */){
				if (property.equals("left")){
					return new Some(new Tuple2Impl(state, new IndexValue(parent, new SymbolValue("0", Primitive.Int))));
				}
				if (property.equals("right")){
					return new Some(new Tuple2Impl(state, new IndexValue(parent, new SymbolValue("1", Primitive.Int))));
				}
			}
			let type : Type = Primitive.Unknown;
			if (parentType._variant === Variant.FindableType){
				if (/* objectType.find(property) instanceof Some */(/* var memberType */)){
				let objectType : FindableType = parentType as FindableType;
					type = /* memberType */;
				}
			}
			return new Some(new Tuple2Impl(tuple[0](), new DataAccess(parent, property, type)));
		});
	}
	parseString(state : CompileState, input : string) : Option<[CompileState, Value]> {
		let stripped = input.strip();
		if (stripped.startsWith("\"") && stripped.endsWith("\"")){
			return new Some(new Tuple2Impl(state, new StringValue(stripped)));
		}
		return new None();
	}
	parseSymbolValue(state : CompileState, value : string) : Option<[CompileState, Value]> {
		let stripped = value.strip();
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
			let leftTuple : [CompileState, Value] = this.parseValue(state, leftString, depth);
			let rightTuple : [CompileState, Value] = this.parseValue(leftTuple[0](), rightString, depth);
			let left = leftTuple[1]();
			let right = rightTuple[1]();
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
	parseDefinitionStatement(input : string, depth : number, state : CompileState) : Option<[CompileState, IncompleteClassSegment]> {
		return this.suffix(input.strip(), ";", (withoutEnd : string) => {
			return this.parseDefinition(state, withoutEnd).map((result : [CompileState, Definition]) => {
				let definition = result[1]();
				return new Tuple2Impl(result[0](), new ClassDefinition(definition, depth));
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
				let typeParams : [CompileState, List<T>] = this.parseValuesOrEmpty(state, typeParamsString, (state1, s) => new Some(new Tuple2Impl(state1, s.strip())));
				return this.assembleDefinition(typeParams[0](), annotations, new Some<string>(beforeTypeParams), name, typeParams[1](), type);
			});
		}).or(() => {
			return this.assembleDefinition(state, annotations, new Some<string>(beforeType), name, Lists.empty(), type);
		});
	}
	toLast(input : string, separator : string, folder : (arg0 : DivideState, arg1 : string) => DivideState) : Option<[string, string]> {
		let divisions : List<string> = this.divideAll(input, folder);
		return divisions.removeLast().map((removed : [List<T>, T]) => {
			let left = removed[0]().iterate().collect(new Joiner(separator)).orElse("");
			let right = removed[1]();
			return new Tuple2Impl(left, right);
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
	assembleDefinition(state : CompileState, annotations : List<string>, beforeTypeParams : Option<string>, name : string, typeParams : List<string>, type : string) : Option<[CompileState, Definition]> {
		return this.parseType(state.withTypeParams(typeParams), type).map((type1 : [CompileState, Type]) => {
			let node : ImmutableDefinition = new ImmutableDefinition(annotations, beforeTypeParams, name.strip(), type1[1](), typeParams);
			return new Tuple2Impl(type1[0](), node);
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
				let newState = inner[0]();
				let child = inner[1]();
				return new Tuple2Impl(newState, new ArrayType(child));
			});
		});
	}
	assembleTemplate(base : string, state : CompileState, arguments : List<Argument>) : [CompileState, Type] {
		let children : R = arguments.iterate().map(this.retainType).flatMap(Iterators.fromOption).collect(new ListCollector());
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
			let baseType : Type = some.value;
			if (baseType._variant === TypeVariant.FindableType){
			let some : Some<Type> = state.resolveType(base) as Some<Type>;
				let findableType : FindableType = baseType as FindableType;
				return new Tuple2Impl(state, new Template(findableType, children));
			}
		}
		return new Tuple2Impl(state, new Template(new Placeholder(base), children));
	}
	parseTemplate(state : CompileState, input : string) : Option<[CompileState, Type]> {
		return this.suffix(input.strip(), ">", (withoutEnd : string) => {
			return this.first(withoutEnd, "<", (base, argumentsString) => {
				let strippedBase = base.strip();
				return this.parseValues(state, argumentsString, this.argument).map((argumentsTuple : [CompileState, List<T>]) => {
					return this.assembleTemplate(strippedBase, argumentsTuple[0](), argumentsTuple[1]());
				});
			});
		});
	}
	retainType(argument : Argument) : Option<Type> {
		if (argument._variant === ArgumentVariant.Type){
			let type : Type = argument as Type;
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
		return splitter().flatMap((splitTuple : [string, string]) => splitMapper(splitTuple[0](), splitTuple[1]()));
	}
	infix<T>(input : string, infix : string, locator : (arg0 : string, arg1 : string) => Option<number>, mapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return this.split(() => locator(input, infix).map((index : number) => {
			let left = input.substring(0, index);
			let right = input.substring(index + infix.length());
			return new Tuple2Impl(left, right);
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