#include "./Main.h"
export interface ListsInstance {
	static List<T> fromArray(T[] elements);
	static List<T> empty();
	static List<T> of(...T[] elements);
}
export interface ConsoleInstance {
	static void printErrLn(&[I8] message);
}
export interface FilesInstance {
	static Path get(&[I8] first, ...&[I8][] more);
}
export interface CharactersInstance {
	static Bool isDigit(I8 c);
	static Bool isLetter(I8 c);
}
export interface StringsInstance {
	static number length(&[I8] stripped);
	static &[I8] sliceBetween(&[I8] input, number startInclusive, number endExclusive);
	static &[I8] sliceFrom(&[I8] input, number startInclusive);
	static Bool isEmpty(&[I8] cache);
	static Bool equalsTo(&[I8] left, &[I8] right);
	static &[I8] strip(&[I8] input);
	static Bool isBlank(&[I8] value);
	static I8 charAt(&[I8] input, number index);
}
export interface Actual {
}
export interface Namespace {
}
export interface Collector<T, C> {
	C createInitial();
	C fold(C current, T element);
}
export class EmptyHead<T> implements Head<T> {
}
export class FlatMapHead<T, R> implements Head<R> {
	(arg0 : T) => Query<R> mapper;
	Head<T> head;
	Query<R> current;
}
export interface Head<T> {
	Option<T> next();
}
export class HeadedQuery<T> implements Query<T> {
	Head<T> head;
	constructor (Head<T> head) {
		this.head = head;
	}
}
export class MapHead<T, R> implements Head<R> {
	Head<T> head;
	(arg0 : T) => R mapper;
	constructor (Head<T> head, (arg0 : T) => R mapper) {
		this.head = head;
		this.mapper = mapper;
	}
}
export class RangeHead implements Head<number> {
	number length;
	number counter;
}
export class SingleHead<T> implements Head<T> {
	T element;
	Bool retrieved;
}
export class ZipHead<T, R> implements Head<Tuple2<T, R>> {
	Head<T> head;
	Query<R> other;
	constructor (Head<T> head, Query<R> other) {
		this.head = head;
		this.other = other;
	}
}
export class Joiner implements Collector<&[I8], Option<&[I8]>> {
	&[I8] delimiter;
	constructor (&[I8] delimiter) {
		this.delimiter = delimiter;
	}
}
export interface List<T> {
	List<T> addLast(T element);
	Query<T> query();
	number size();
	Option<List<T>> subList(number startInclusive, number endExclusive);
	Option<T> findLast();
	Option<T> findFirst();
	Option<T> find(number index);
	Query<Tuple2<number, T>> queryWithIndices();
	List<T> addAll(List<T> others);
	Bool contains(T element, (arg0 : T, arg1 : T) => Bool equator);
	Query<T> queryReversed();
	List<T> addFirst(T element);
	Bool isEmpty();
	Bool equalsTo(List<T> other, (arg0 : T, arg1 : T) => Bool equator);
	List<T> removeValue(T element, (arg0 : T, arg1 : T) => Bool equator);
	Option<List<T>> removeLast();
	List<T> sort((arg0 : T, arg1 : T) => number sorter);
}
export class ListCollector<T> implements Collector<T, List<T>> {
}
export class Queries {
}
export interface Query<T> {
	C collect(Collector<T, C> collector);
	Query<R> map((arg0 : T) => R mapper);
	R foldWithInitial(R initial, (arg0 : R, arg1 : T) => R folder);
	Option<R> foldWithMapper((arg0 : T) => R mapper, (arg0 : R, arg1 : T) => R folder);
	Query<R> flatMap((arg0 : T) => Query<R> mapper);
	Option<T> next();
	Bool allMatch((arg0 : T) => boolean predicate);
	Query<T> filter((arg0 : T) => boolean predicate);
	Bool anyMatch((arg0 : T) => boolean predicate);
	Query<Tuple2<T, R>> zip(Query<R> other);
	Result<R, X> foldWithInitialToResult(R initial, (arg0 : R, arg1 : T) => Result<R, X> mapper);
}
export interface IOError {
	&[I8] display();
}
export interface Path {
	&[I8] asString();
	Option<IOError> writeString(&[I8] output);
	Result<&[I8], IOError> readString();
	Result<List<Path>, IOError> walk();
	&[I8] findFileName();
	Bool endsWith(&[I8] suffix);
	Path relativize(Path source);
	Path getParent();
	Query<&[I8]> query();
	Path resolveChildSegments(List<&[I8]> children);
	Path resolveChild(&[I8] name);
	Bool exists();
	Option<IOError> createDirectories();
}
export class None<T> implements Option<T> {
}
export interface Option<T> {
	Option<R> map((arg0 : T) => R mapper);
	T orElse(T other);
	T orElseGet(() => T supplier);
	Bool isPresent();
	void ifPresent((arg0 : T) => void consumer);
	Option<T> or(() => Option<T> other);
	Option<R> flatMap((arg0 : T) => Option<R> mapper);
	Option<T> filter((arg0 : T) => boolean predicate);
	Tuple2<Bool, T> toTuple(T other);
	Option<Tuple2<T, R>> and(() => Option<R> other);
}
export class Some<T> implements Option<T> {
	T value;
	constructor (T value) {
		this.value = value;
	}
}
export class Err<T, X> implements Result<T, X> {
	X error;
	constructor (X error) {
		this.error = error;
	}
}
export class Ok<T, X> implements Result<T, X> {
	T value;
	constructor (T value) {
		this.value = value;
	}
}
export interface Result<T, X> {
	Option<X> findError();
	Option<T> findValue();
	R match((arg0 : T) => R whenOk, (arg0 : X) => R whenErr);
	Result<R, X> flatMapValue((arg0 : T) => Result<R, X> mapper);
	Result<R, X> mapValue((arg0 : T) => R mapper);
}
export interface Tuple2<A, B> {
	A left();
	B right();
}
export class Tuple2Impl<A, B> implements Tuple2<A, B> {
	A leftValue;
	B rightValue;
	constructor (A leftValue, B rightValue) {
		this.leftValue = leftValue;
		this.rightValue = rightValue;
	}
}
export interface Type {
	&[I8] generate();
	Bool isFunctional();
	Bool isVar();
	&[I8] generateBeforeName();
}
export interface CompileState {
	&[I8] functionName();
	Option<&[I8]> findLastStructureName();
	&[I8] createIndent();
	Bool isPlatform(Platform platform);
	Bool hasLastStructureNameOf(&[I8] name);
	CompileState addResolvedImportFromCache(&[I8] base);
	CompileState addResolvedImportWithNamespace(List<&[I8]> namespace, &[I8] child);
	CompileState withLocation(Location namespace);
	CompileState append(&[I8] element);
	CompileState pushStructureName(&[I8] name);
	CompileState enterDepth();
	CompileState exitDepth();
	CompileState defineAll(List<Definition> definitions);
	Option<Type> resolve(&[I8] name);
	CompileState clearImports();
	CompileState clearGenerated();
	CompileState addSource(Source source);
	Option<Source> findSource(&[I8] name);
	CompileState popStructureName();
	CompileState mapLocation((arg0 : Location) => Location mapper);
	CompileState withPlatform(Platform platform);
	List<Import> imports();
	&[I8] join();
	Option<Location> findCurrentLocation();
	Platform platform();
	CompileState addFunction(&[I8] function);
	List<&[I8]> findDefinedTypes();
	CompileState defineType(&[I8] name);
	CompileState clearDefinedTypes();
}
export class Definition {
	List<&[I8]> annotations;
	List<&[I8]> modifiers;
	List<&[I8]> typeParams;
	Type type;
	&[I8] name;
	constructor (List<&[I8]> annotations, List<&[I8]> modifiers, List<&[I8]> typeParams, Type type, &[I8] name) {
		this.annotations = annotations;
		this.modifiers = modifiers;
		this.typeParams = typeParams;
		this.type = type;
		this.name = name;
	}
}
export interface FunctionHeader<S extends FunctionHeader<S>> {
	&[I8] generateWithDefinitions(Platform platform, List<Definition> definitions);
	Bool hasAnnotation(&[I8] annotation);
	S removeModifier(&[I8] modifier);
	S addModifierLast(&[I8] modifier);
}
export interface Parameter {
	&[I8] generate(Platform platform);
	Option<Definition> asDefinition();
}
export class FunctionSegment<S extends FunctionHeader<S>> {
	FunctionHeader<S> header;
	List<Definition> definitions;
	Option<&[I8]> maybeContent;
	constructor (FunctionHeader<S> header, List<Definition> definitions, Option<&[I8]> maybeContent) {
		this.header = header;
		this.definitions = definitions;
		this.maybeContent = maybeContent;
	}
}
export class ImmutableCompileState implements CompileState {
	Platform platform;
	Option<Location> findCurrentLocation;
	List<Source> sources;
	List<Import> imports;
	List<&[I8]> structureNames;
	&[I8] structures;
	&[I8] functions;
	List<Definition> definitions;
	number depth;
	List<&[I8]> definedTypes;
	constructor (Platform platform, Option<Location> findCurrentLocation, List<Source> sources, List<Import> imports, List<&[I8]> structureNames, &[I8] structures, &[I8] functions, List<Definition> definitions, number depth, List<&[I8]> definedTypes) {
		this.platform = platform;
		this.findCurrentLocation = findCurrentLocation;
		this.sources = sources;
		this.imports = imports;
		this.structureNames = structureNames;
		this.structures = structures;
		this.functions = functions;
		this.definitions = definitions;
		this.depth = depth;
		this.definedTypes = definedTypes;
	}
}
export class Import {
	List<&[I8]> namespace;
	&[I8] child;
	constructor (List<&[I8]> namespace, &[I8] child) {
		this.namespace = namespace;
		this.child = child;
	}
}
export class DivideState {
	List<&[I8]> segments;
	&[I8] buffer;
	number depth;
	&[I8] input;
	number index;
	constructor (List<&[I8]> segments, &[I8] buffer, number depth, &[I8] input, number index) {
		this.segments = segments;
		this.buffer = buffer;
		this.depth = depth;
		this.input = input;
		this.index = index;
	}
}
export class Placeholder {
	&[I8] input;
	constructor (&[I8] input) {
		this.input = input;
	}
}
export class Whitespace implements Parameter {
}
export class ArrayType implements Type {
	Type child;
	constructor (Type child) {
		this.child = child;
	}
}
export class BooleanType implements Type {
	Platform platform;
	constructor (Platform platform) {
		this.platform = platform;
	}
}
export class FunctionType implements Type {
	List<&[I8]> args;
	&[I8] returns;
	constructor (List<&[I8]> args, &[I8] returns) {
		this.args = args;
		this.returns = returns;
	}
}
export class PrimitiveType implements Type {
	static String/*auto*/: PrimitiveType = new PrimitiveType("string");
	static Number/*auto*/: PrimitiveType = new PrimitiveType("number");
	static Var/*auto*/: PrimitiveType = new PrimitiveType("var");
	static Void/*auto*/: PrimitiveType = new PrimitiveType("void");
	static Auto/*auto*/: PrimitiveType = new PrimitiveType("auto");
	static I8/*auto*/: PrimitiveType = new PrimitiveType("I8");
	static I32/*auto*/: PrimitiveType = new PrimitiveType("I32");
	&[I8] value;
}
export class SliceType implements Type {
	Type type;
	constructor (Type type) {
		this.type = type;
	}
}
export class TemplateType implements Type {
	&[I8] base;
	List<&[I8]> args;
	constructor (&[I8] base, List<&[I8]> args) {
		this.base = base;
		this.args = args;
	}
}
export class VariadicType implements Type {
	Type type;
	constructor (Type type) {
		this.type = type;
	}
}
export class AccessNode implements Value {
	Value child;
	&[I8] property;
	constructor (Value child, &[I8] property) {
		this.child = child;
		this.property = property;
	}
}
export interface Argument {
	Option<Value> toValue();
}
export interface Caller {
	&[I8] generate(Platform platform);
	Option<Value> findChild();
}
export class ConstructionCaller implements Caller {
	&[I8] right;
	Platform platform;
	constructor (&[I8] right, Platform platform) {
		this.right = right;
		this.platform = platform;
	}
}
export class ConstructorHeader implements FunctionHeader<ConstructorHeader> {
}
export class InvokableNode implements Value {
	Caller caller;
	List<Value> args;
	constructor (Caller caller, List<Value> args) {
		this.caller = caller;
		this.args = args;
	}
}
export class LambdaNode implements Value {
	List<Definition> paramNames;
	&[I8] content;
	constructor (List<Definition> paramNames, &[I8] content) {
		this.paramNames = paramNames;
		this.content = content;
	}
}
export class NotNode implements Value {
	&[I8] child;
	constructor (&[I8] child) {
		this.child = child;
	}
}
export class OperationNode implements Value {
	Value left;
	&[I8] targetInfix;
	Value right;
	constructor (Value left, &[I8] targetInfix, Value right) {
		this.left = left;
		this.targetInfix = targetInfix;
		this.right = right;
	}
}
export class StringNode implements Value {
	&[I8] value;
	constructor (&[I8] value) {
		this.value = value;
	}
}
export class SymbolNode {
	&[I8] value;
	Type type;
	constructor (&[I8] value, Type type) {
		this.value = value;
		this.type = type;
	}
}
export interface Value extends Argument, Caller  {
	Option<&[I8]> generateAsEnumValue(&[I8] structureName, Platform platform);
	Type type();
}
export class Location {
	List<&[I8]> namespace;
	&[I8] name;
	constructor (List<&[I8]> namespace, &[I8] name) {
		this.namespace = namespace;
		this.name = name;
	}
}
export class Platform {
	static TypeScript/*auto*/: Platform = new Platform("node", "ts");
	static Magma/*auto*/: Platform = new Platform("magma", "mgs");
	static Windows/*auto*/: Platform = new Platform("windows", "h", "c");
	&[I8] root;
	&[I8][] extension;
}
export class Source {
	Path sourceDirectory;
	Path source;
	constructor (Path sourceDirectory, Path source) {
		this.sourceDirectory = sourceDirectory;
		this.source = source;
	}
}
class IncompleteRoot {
	Location location;
	List<IncompleteRootSegment> outputsByExtensions;
	constructor (Location location, List<IncompleteRootSegment> outputsByExtensions) {
		this.location = location;
		this.outputsByExtensions = outputsByExtensions;
	}
}
class IncompleteRootSegment {
	&[I8] value;
	constructor (&[I8] value) {
		this.value = value;
	}
}
export class Main {
}

Option<T> next() {
	return new None<T>(/*auto*/);
}
constructor (Head<T> head, Query<R> initial, (arg0 : T) => Query<R> mapper) {
	this/*auto*/.head = head/*Head<T>*/;
	this/*auto*/.current = initial/*Query<R>*/;
	this/*auto*/.mapper = mapper/*(arg0 : T) => Query<R>*/;
}
Option<R> next() {
	while (true/*auto*/) {
		var next = this/*auto*/.current.next(/*auto*/);
		if (next/*(arg0 : T) => R*/(/*auto*/)) {
			return next/*(arg0 : T) => R*/;
		}
		var tuple = this/*auto*/.head.next(/*auto*/).map(this/*auto*/.mapper).toTuple(this/*auto*/.current);
		if (tuple/*Tuple2<CompileState, Whitespace>*/.left(/*auto*/)) {
			this/*auto*/.current = tuple/*Tuple2<CompileState, Whitespace>*/.right(/*auto*/);
		}
		else {
			return new None<R>(/*auto*/);
		}
	}
}
Option<T> next() {
	return this/*auto*/.head.next(/*auto*/);
}
auto temp(C current, T element) {
	return collector/*Collector<T, C>*/.fold(current/*Tuple2<CompileState, List<T>>*/, element/*&[I8]*/);
}
C collect(Collector<T, C> collector) {
	return this/*auto*/.foldWithInitial(collector/*Collector<T, C>*/.createInitial(/*auto*/), lambdaDefinition/*auto*/);
}
Query<R> map((arg0 : T) => R mapper) {
	return new HeadedQuery<R>(new MapHead<T, R>(this/*auto*/.head, mapper/*(arg0 : T) => R*/));
}
auto temp(T inner) {
	return folder/*(arg0 : R, arg1 : T) => R*/(finalResult/*auto*/, inner/*auto*/);
}
R foldWithInitial(R initial, (arg0 : R, arg1 : T) => R folder) {
	var result = initial/*R*/;
	while (true/*auto*/) {
		var finalResult = result/*Tuple2<CompileState, &[I8]>*/;
		var maybeNext = this/*auto*/.head.next(/*auto*/).map(lambdaDefinition/*auto*/).toTuple(finalResult/*auto*/);
		if (maybeNext/*auto*/.left(/*auto*/)) {
			result/*Tuple2<CompileState, &[I8]>*/ = maybeNext/*auto*/.right(/*auto*/);
		}
		else {
			return result/*Tuple2<CompileState, &[I8]>*/;
		}
	}
}
auto temp(R maybeNext) {
	return this/*auto*/.foldWithInitial(maybeNext/*auto*/, folder/*(arg0 : R, arg1 : T) => R*/);
}
Option<R> foldWithMapper((arg0 : T) => R next, (arg0 : R, arg1 : T) => R folder) {
	return this/*auto*/.head.next(/*auto*/).map(next/*(arg0 : T) => R*/).map(lambdaDefinition/*auto*/);
}
auto temp(Query<R> initial) {
	return new HeadedQuery<R>(new FlatMapHead<T, R>(this/*auto*/.head, initial/*R*/, mapper/*(arg0 : T) => Query<R>*/));
}
auto temp() {
	return new HeadedQuery<R>(new EmptyHead<R>(/*auto*/));
}
Query<R> flatMap((arg0 : T) => Query<R> mapper) {
	return this/*auto*/.head.next(/*auto*/).map(mapper/*(arg0 : T) => Query<R>*/).map(lambdaDefinition/*auto*/).orElseGet(lambdaDefinition/*auto*/);
}
auto temp(Bool maybeAllTrue, T element) {
	return maybeAllTrue/*auto*/ && predicate/*(arg0 : T) => boolean*/(element/*&[I8]*/);
}
Bool allMatch((arg0 : T) => boolean predicate) {
	return this/*auto*/.foldWithInitial(true/*auto*/, lambdaDefinition/*auto*/);
}
auto temp(Bool aBoolean, T t) {
	return aBoolean/*auto*/ || predicate/*(arg0 : T) => boolean*/(t/*auto*/);
}
Bool anyMatch((arg0 : T) => boolean predicate) {
	return this/*auto*/.foldWithInitial(false/*auto*/, lambdaDefinition/*auto*/);
}
Query<Tuple2<T, R>> zip(Query<R> other) {
	return new HeadedQuery<Tuple2<T, R>>(new ZipHead<T, R>(this/*auto*/.head, other/*Query<R>*/));
}
auto temp(R current) {
	return mapper/*(arg0 : R, arg1 : T) => Result<R, X>*/(current/*Tuple2<CompileState, List<T>>*/, element/*&[I8]*/);
}
auto temp(Result<R, X> currentResult, T element) {
	return currentResult/*auto*/.flatMapValue(lambdaDefinition/*auto*/);
}
Result<R, X> foldWithInitialToResult(R initial, (arg0 : R, arg1 : T) => Result<R, X> mapper) {
	Result<R, X> initialResult = new Ok<R, X>(initial/*R*/);
	return this/*auto*/.foldWithInitial(initialResult/*auto*/, lambdaDefinition/*auto*/);
}
auto temp(T element) {{
		if (predicate/*(arg0 : T) => boolean*/(element/*T*/)) {
			return new HeadedQuery<T>(new SingleHead<T>(element/*T*/));
		}
		else {
			return new HeadedQuery<T>(new EmptyHead<T>(/*auto*/));
		}
	}
}
Query<T> filter((arg0 : T) => boolean predicate) {
	return this/*auto*/.flatMap(lambdaDefinition/*auto*/);
}
Option<R> next() {
	return this/*auto*/.head.next(/*auto*/).map(this/*auto*/.mapper);
}
constructor (number length) {
	this/*auto*/.length = length/*number*/;
	this/*auto*/.counter = 0/*auto*/;
}
Option<number> next() {
	if (this/*auto*/.counter >= this/*auto*/.length) {
		return new None<number>(/*auto*/);
	}
	var value = this/*auto*/.counter;
	this/*auto*/.counter++;
	return new Some<number>(value/*&[I8]*/);
}
constructor (T element) {
	this/*auto*/.element = element/*T*/;
	this/*auto*/.retrieved = false/*auto*/;
}
Option<T> next() {
	if (this/*auto*/.retrieved) {
		return new None<T>(/*auto*/);
	}
	this/*auto*/.retrieved = true/*auto*/;
	return new Some<T>(this/*auto*/.element);
}
auto temp() {
	return this/*auto*/.other.next(/*auto*/);
}
Option<Tuple2<T, R>> next() {
	return this/*auto*/.head.next(/*auto*/).and(lambdaDefinition/*auto*/);
}
static Joiner empty() {
	return new Joiner("");
}
auto temp(&[I8] inner) {
	return prefix/*&[I8]*/ + inner/*auto*/ + suffix/*&[I8]*/;
}
static &[I8] joinOrEmpty(List<&[I8]> items, &[I8] delimiter, &[I8] prefix, &[I8] suffix) {
	return items/*List<&[I8]>*/.query(/*auto*/).collect(new Joiner(delimiter/*&[I8]*/)).map(lambdaDefinition/*auto*/).orElse("");
}
Option<&[I8]> createInitial() {
	return new None<&[I8]>(/*auto*/);
}
auto temp(&[I8] inner) {
	return inner/*auto*/ + this/*auto*/.delimiter + element/*&[I8]*/;
}
Option<&[I8]> fold(Option<&[I8]> maybe, &[I8] element) {
	return new Some<&[I8]>(maybe/*Option<&[I8]>*/.map(lambdaDefinition/*auto*/).orElse(element/*&[I8]*/));
}
List<T> createInitial() {
	return Lists/*auto*/.empty(/*auto*/);
}
List<T> fold(List<T> current, T element) {
	return current/*List<T>*/.addLast(element/*T*/);
}
auto temp(T element) {
	return Queries/*auto*/.getTSingleHead(element/*T*/);
}
auto temp() {
	return new EmptyHead<T>(/*auto*/);
}
static Query<T> fromOption(Option<T> option) {
	return new HeadedQuery<T>(option/*Option<T>*/.map(lambdaDefinition/*auto*/).orElseGet(lambdaDefinition/*auto*/));
}
static Head<T> getTSingleHead(T element) {
	return new SingleHead<T>(element/*T*/);
}
static Query<T> fromArray(T[] elements) {
	/*return new HeadedQuery<Integer>(new RangeHead(elements.length)).map((Integer index) -> elements[index])*/;
}
Option<R> map((arg0 : T) => R mapper) {
	return new None<R>(/*auto*/);
}
T orElse(T other) {
	return other/*T*/;
}
T orElseGet(() => T supplier) {
	return supplier/*() => T*/(/*auto*/);
}
Bool isPresent() {
	return false/*auto*/;
}
void ifPresent((arg0 : T) => void consumer) {
}
Option<T> or(() => Option<T> other) {
	return other/*() => Option<T>*/(/*auto*/);
}
Option<R> flatMap((arg0 : T) => Option<R> mapper) {
	return new None<R>(/*auto*/);
}
Option<T> filter((arg0 : T) => boolean predicate) {
	return new None<T>(/*auto*/);
}
Tuple2<Bool, T> toTuple(T other) {
	return new Tuple2Impl<Bool, T>(false/*auto*/, other/*T*/);
}
Option<Tuple2<T, R>> and(() => Option<R> other) {
	return new None<Tuple2<T, R>>(/*auto*/);
}
Option<R> map((arg0 : T) => R mapper) {
	return new Some<R>(mapper/*(arg0 : T) => R*/(this/*auto*/.value));
}
T orElse(T other) {
	return this/*auto*/.value;
}
T orElseGet(() => T supplier) {
	return this/*auto*/.value;
}
Bool isPresent() {
	return true/*auto*/;
}
void ifPresent((arg0 : T) => void consumer) {
	consumer/*(arg0 : T) => void*/(this/*auto*/.value);
}
Option<T> or(() => Option<T> other) {
	return this/*auto*/;
}
Option<R> flatMap((arg0 : T) => Option<R> mapper) {
	return mapper/*(arg0 : T) => Option<R>*/(this/*auto*/.value);
}
Option<T> filter((arg0 : T) => boolean predicate) {
	if (predicate/*(arg0 : T) => boolean*/(this/*auto*/.value)) {
		return this/*auto*/;
	}
	return new None<T>(/*auto*/);
}
Tuple2<Bool, T> toTuple(T other) {
	return new Tuple2Impl<Bool, T>(true/*auto*/, this/*auto*/.value);
}
auto temp(R otherValue) {
	return new Tuple2Impl<T, R>(this/*auto*/.value, otherValue/*auto*/);
}
Option<Tuple2<T, R>> and(() => Option<R> other) {
	return other/*() => Option<R>*/(/*auto*/).map(lambdaDefinition/*auto*/);
}
Option<X> findError() {
	return new Some<X>(this/*auto*/.error);
}
Option<T> findValue() {
	return new None<T>(/*auto*/);
}
R match((arg0 : T) => R whenOk, (arg0 : X) => R whenErr) {
	return whenErr/*(arg0 : X) => R*/(this/*auto*/.error);
}
Result<R, X> flatMapValue((arg0 : T) => Result<R, X> mapper) {
	return new Err<>(this/*auto*/.error);
}
Result<R, X> mapValue((arg0 : T) => R mapper) {
	return new Err<>(this/*auto*/.error);
}
Option<X> findError() {
	return new None<X>(/*auto*/);
}
Option<T> findValue() {
	return new Some<T>(this/*auto*/.value);
}
R match((arg0 : T) => R whenOk, (arg0 : X) => R whenErr) {
	return whenOk/*(arg0 : T) => R*/(this/*auto*/.value);
}
Result<R, X> flatMapValue((arg0 : T) => Result<R, X> mapper) {
	return mapper/*(arg0 : T) => Result<R, X>*/(this/*auto*/.value);
}
Result<R, X> mapValue((arg0 : T) => R mapper) {
	return new Ok<>(mapper/*(arg0 : T) => R*/(this/*auto*/.value));
}
A left() {
	return this/*auto*/.leftValue;
}
B right() {
	return this/*auto*/.rightValue;
}
constructor (Type type, &[I8] name) {
	this/*auto*/(Lists/*auto*/.empty(/*auto*/), Lists/*auto*/.empty(/*auto*/), Lists/*auto*/.empty(/*auto*/), type/*Type*/, name/*&[I8]*/);
}
&[I8] generate(Platform platform) {
	return this/*auto*/.generateWithAfterName(platform/*Platform*/, "");
}
Option<Definition> asDefinition() {
	return new Some<Definition>(this/*auto*/);
}
&[I8] generateWithAfterName(Platform platform, &[I8] afterName) {
	var joinedTypeParams = this/*auto*/.joinTypeParams(/*auto*/);
	var joinedModifiers = this/*auto*/.joinModifiers(/*auto*/);
	if (Platform/*auto*/.Windows === platform/*Platform*/) {
		return joinedModifiers/*&[I8]*/ + this/*auto*/.type.generateBeforeName(/*auto*/) + this/*auto*/.type.generate(/*auto*/) + " " + this/*auto*/.name + afterName/*&[I8]*/;
	}
	return joinedModifiers/*&[I8]*/ + this/*auto*/.type.generateBeforeName(/*auto*/) + this/*auto*/.name + joinedTypeParams/*&[I8]*/ + afterName/*&[I8]*/ + this/*auto*/.generateType(/*auto*/);
}
auto temp(&[I8] value) {
	return value/*&[I8]*/ + " ";
}
&[I8] joinModifiers() {
	return this/*auto*/.modifiers.query(/*auto*/).map(lambdaDefinition/*auto*/).collect(new Joiner("")).orElse("");
}
&[I8] generateType() {
	if (this/*auto*/.type.isVar(/*auto*/)) {
		return "";
	}
	return ": " + this/*auto*/.type.generate(/*auto*/);
}
&[I8] joinTypeParams() {
	return Joiner/*auto*/.joinOrEmpty(this/*auto*/.typeParams, ", ", "<", ">");
}
Bool hasAnnotation(&[I8] annotation) {
	return this/*auto*/.annotations.contains(annotation/*&[I8]*/, Strings/*auto*/.equalsTo);
}
Definition removeModifier(&[I8] modifier) {
	return new Definition(this/*auto*/.annotations, this/*auto*/.modifiers.removeValue(modifier/*&[I8]*/, Strings/*auto*/.equalsTo), this/*auto*/.typeParams, this/*auto*/.type, this/*auto*/.name);
}
Definition addModifierLast(&[I8] modifier) {
	return new Definition(this/*auto*/.annotations, this/*auto*/.modifiers.addLast(modifier/*&[I8]*/), this/*auto*/.typeParams, this/*auto*/.type, this/*auto*/.name);
}
auto temp(Definition definition) {
	return definition/*Definition*/.generate(platform/*Platform*/);
}
&[I8] generateWithDefinitions(Platform platform, List<Definition> definitions) {
	var joinedDefinitions = definitions/*List<Definition>*/.query(/*auto*/).map(lambdaDefinition/*auto*/).collect(new Joiner(", ")).orElse("");
	return this/*auto*/.generateWithAfterName(platform/*Platform*/, "(" + joinedDefinitions/*auto*/ + ")");
}
auto temp(&[I8] inner) {
	return " {" + inner/*auto*/ + indent/*&[I8]*/ + "}";
}
&[I8] generate(Platform platform, &[I8] indent) {
	var content = this/*auto*/.maybeContent(/*auto*/).map(lambdaDefinition/*auto*/).orElse(";");
	return indent/*&[I8]*/ + this/*auto*/.header.generateWithDefinitions(platform/*Platform*/, this/*auto*/.definitions(/*auto*/)) + content/*&[I8]*/;
}
static CompileState createInitial() {
	return new ImmutableCompileState(Platform/*auto*/.Magma, new None<Location>(/*auto*/), Lists/*auto*/.empty(/*auto*/), Lists/*auto*/.empty(/*auto*/), Lists/*auto*/.empty(/*auto*/), "", "", Lists/*auto*/.empty(/*auto*/), 0/*auto*/, Lists/*auto*/.empty(/*auto*/));
}
auto temp(&[I8] anObject) {
	return Strings/*auto*/.equalsTo(name/*&[I8]*/, anObject/*auto*/);
}
Bool hasLastStructureNameOf(&[I8] name) {
	return this/*auto*/.structureNames.findLast(/*auto*/).filter(lambdaDefinition/*auto*/).isPresent(/*auto*/);
}
CompileState withLocation(Location namespace) {
	return new ImmutableCompileState(this/*auto*/.platform, new Some<Location>(namespace/*Location*/), this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
}
CompileState append(&[I8] element) {
	return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures + element/*&[I8]*/, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
}
CompileState pushStructureName(&[I8] name) {
	return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames.addLast(name/*&[I8]*/), this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
}
CompileState enterDepth() {
	return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth + 1/*auto*/, this/*auto*/.definedTypes);
}
CompileState exitDepth() {
	return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth - 1/*auto*/, this/*auto*/.definedTypes);
}
CompileState defineAll(List<Definition> definitions) {
	return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions.addAll(definitions/*List<Definition>*/), this/*auto*/.depth, this/*auto*/.definedTypes);
}
auto temp(Definition definition) {
	return Strings/*auto*/.equalsTo(definition/*Definition*/.name(/*auto*/), name/*&[I8]*/);
}
auto temp(Definition definition1) {
	return definition1/*auto*/.type(/*auto*/);
}
Option<Type> resolve(&[I8] name) {
	return this/*auto*/.definitions.queryReversed(/*auto*/).filter(lambdaDefinition/*auto*/).map(lambdaDefinition/*auto*/).next(/*auto*/);
}
CompileState clearImports() {
	return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, Lists/*auto*/.empty(/*auto*/), this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
}
CompileState clearGenerated() {
	return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, "", "", this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
}
CompileState addSource(Source source) {
	return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources.addLast(source/*Source*/), this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
}
auto temp(&[I8] inner) {
	return Strings/*auto*/.equalsTo(inner/*auto*/, base/*&[I8]*/);
}
auto temp(Source source) {
	return this/*auto*/.addResolvedImportWithNamespace(source/*Source*/.computeNamespace(/*auto*/), source/*Source*/.computeName(/*auto*/));
}
CompileState addResolvedImportFromCache(&[I8] base) {
	if (this/*auto*/.structureNames.query(/*auto*/).anyMatch(lambdaDefinition/*auto*/)) {
		return this/*auto*/;
	}
	return this/*auto*/.findSource(base/*&[I8]*/).map(lambdaDefinition/*auto*/).orElse(this/*auto*/);
}
CompileState popStructureName() {
	return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames.removeLast(/*auto*/).orElse(this/*auto*/.structureNames), this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
}
CompileState mapLocation((arg0 : Location) => Location mapper) {
	return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation.map(mapper/*(arg0 : Location) => Location*/), this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
}
CompileState withPlatform(Platform platform) {
	return new ImmutableCompileState(platform/*Platform*/, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
}
CompileState addFunction(&[I8] function) {
	return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions + function/*&[I8]*/, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
}
List<&[I8]> findDefinedTypes() {
	return this/*auto*/.definedTypes;
}
CompileState defineType(&[I8] name) {
	return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes.addLast(name/*&[I8]*/));
}
CompileState clearDefinedTypes() {
	return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth, Lists/*auto*/.empty(/*auto*/));
}
&[I8] join() {
	return this/*auto*/.structures + this/*auto*/.functions;
}
auto temp(Location location) {
	return location/*Location*/.namespace(/*auto*/);
}
auto temp(Import node) {
	return Strings/*auto*/.equalsTo(node/*auto*/.child(/*auto*/), child/*&[I8]*/);
}
CompileState addResolvedImportWithNamespace(List<&[I8]> oldParent, &[I8] child) {
	var namespace = this/*auto*/.findCurrentLocation.map(lambdaDefinition/*auto*/).orElse(Lists/*auto*/.empty(/*auto*/));
	var newParent = oldParent/*List<&[I8]>*/;
	if (Platform/*auto*/.TypeScript === this/*auto*/.platform) {
		if (namespace/*Location*/.isEmpty(/*auto*/)) {
			newParent/*auto*/ = newParent/*auto*/.addFirst(".");
		}
		var i = 0/*auto*/;
		var size = namespace/*Location*/.size(/*auto*/);
		while (i/*auto*/ < size/*auto*/) {
			newParent/*auto*/ = newParent/*auto*/.addFirst("..");
			i/*auto*/++;
		}
	}
	if (this/*auto*/.imports.query(/*auto*/).filter(lambdaDefinition/*auto*/).next(/*auto*/).isPresent(/*auto*/)) {
		return this/*auto*/;
	}
	var importString = new Import(newParent/*auto*/, child/*&[I8]*/);
	return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports.addLast(importString/*auto*/), this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
}
auto temp(Source source) {
	return Strings/*auto*/.equalsTo(source/*Source*/.computeName(/*auto*/), name/*&[I8]*/);
}
Option<Source> findSource(&[I8] name) {
	return this/*auto*/.sources.query(/*auto*/).filter(lambdaDefinition/*auto*/).next(/*auto*/);
}
Bool isPlatform(Platform platform) {
	return platform/*Platform*/ === this/*auto*/.platform(/*auto*/);
}
&[I8] createIndent() {
	var indent = this/*auto*/.depth(/*auto*/);
	return "\n" + "\t".repeat(indent/*&[I8]*/);
}
&[I8] functionName() {
	return "temp";
}
Option<&[I8]> findLastStructureName() {
	return this/*auto*/.structureNames(/*auto*/).findLast(/*auto*/);
}
&[I8] generate(Platform platform) {
	if (Platform/*auto*/.Magma === platform/*Platform*/) {
		var joinedNamespace = this/*auto*/.namespace.query(/*auto*/).collect(new Joiner(".")).orElse("");
		return "import " + joinedNamespace/*auto*/ + "." + this/*auto*/.child + ";\n";
	}
	var joinedNamespace = this/*auto*/.namespace.addLast(this/*auto*/.child).query(/*auto*/).collect(new Joiner("/")).orElse("");
	return "import { " + this/*auto*/.child + " } from \"" + joinedNamespace/*auto*/ + "\";\n";
}
static DivideState createInitial(&[I8] input) {
	return new DivideState(Lists/*auto*/.empty(/*auto*/), "", 0/*auto*/, input/*&[I8]*/, 0/*auto*/);
}
DivideState advance() {
	return new DivideState(this/*auto*/.segments.addLast(this/*auto*/.buffer), "", this/*auto*/.depth, this/*auto*/.input, this/*auto*/.index);
}
DivideState append(I8 c) {
	return new DivideState(this/*auto*/.segments, this/*auto*/.buffer + c/*I8*/, this/*auto*/.depth, this/*auto*/.input, this/*auto*/.index);
}
Bool isLevel() {
	return 0/*auto*/ === this/*auto*/.depth;
}
DivideState enter() {
	return new DivideState(this/*auto*/.segments, this/*auto*/.buffer, this/*auto*/.depth + 1/*auto*/, this/*auto*/.input, this/*auto*/.index);
}
DivideState exit() {
	return new DivideState(this/*auto*/.segments, this/*auto*/.buffer, this/*auto*/.depth - 1/*auto*/, this/*auto*/.input, this/*auto*/.index);
}
Bool isShallow() {
	return 1/*auto*/ === this/*auto*/.depth;
}
Option<Tuple2<DivideState, I8>> pop() {
	if (this/*auto*/.index >= Strings/*auto*/.length(this/*auto*/.input)) {
		return new None<Tuple2<DivideState, I8>>(/*auto*/);
	}
	var c = Strings/*auto*/.charAt(this/*auto*/.input, this/*auto*/.index);
	var nextState = new DivideState(this/*auto*/.segments, this/*auto*/.buffer, this/*auto*/.depth, this/*auto*/.input, this/*auto*/.index + 1/*auto*/);
	return new Some<Tuple2<DivideState, I8>>(new Tuple2Impl<DivideState, I8>(nextState/*auto*/, c/*I8*/));
}
auto temp(Tuple2<DivideState, I8> inner) {
	return new Tuple2Impl<DivideState, I8>(inner/*auto*/.left(/*auto*/).append(inner/*auto*/.right(/*auto*/)), inner/*auto*/.right(/*auto*/));
}
Option<Tuple2<DivideState, I8>> popAndAppendToTuple() {
	return this/*auto*/.pop(/*auto*/).map(lambdaDefinition/*auto*/);
}
auto temp(Tuple2<DivideState, I8> tuple) {
	return tuple/*Tuple2<CompileState, Whitespace>*/.left(/*auto*/);
}
Option<DivideState> popAndAppendToOption() {
	return this/*auto*/.popAndAppendToTuple(/*auto*/).map(lambdaDefinition/*auto*/);
}
I8 peek() {
	return Strings/*auto*/.charAt(this/*auto*/.input, this/*auto*/.index);
}
Bool startsWith(&[I8] slice) {
	return Strings/*auto*/.sliceFrom(this/*auto*/.input, this/*auto*/.index).startsWith(slice/*&[I8]*/);
}
&[I8] generate() {
	return Main/*auto*/.generatePlaceholder(this/*auto*/.input);
}
Bool isFunctional() {
	return false/*auto*/;
}
&[I8] generate(Platform platform) {
	return this/*auto*/.generate(/*auto*/);
}
Option<Value> findChild() {
	return new None<Value>(/*auto*/);
}
Option<Definition> asDefinition() {
	return new None<Definition>(/*auto*/);
}
Option<Value> toValue() {
	return new None<Value>(/*auto*/);
}
Bool isVar() {
	return false/*auto*/;
}
&[I8] generateBeforeName() {
	return "";
}
Option<&[I8]> generateAsEnumValue(&[I8] structureName, Platform platform) {
	return new None<&[I8]>(/*auto*/);
}
Type type() {
	return PrimitiveType/*auto*/.Auto;
}
&[I8] generate(Platform platform) {
	return "";
}
Option<Definition> asDefinition() {
	return new None<Definition>(/*auto*/);
}
&[I8] generate() {
	return this/*auto*/.child.generate(/*auto*/) + "[]";
}
Bool isFunctional() {
	return false/*auto*/;
}
Bool isVar() {
	return false/*auto*/;
}
&[I8] generateBeforeName() {
	return "";
}
&[I8] generate() {
	if (Platform/*auto*/.TypeScript === this/*auto*/.platform) {
		return "boolean";
	}
	return "Bool";
}
Bool isFunctional() {
	return false/*auto*/;
}
Bool isVar() {
	return false/*auto*/;
}
&[I8] generateBeforeName() {
	return "";
}
auto temp(Tuple2<number, &[I8]> tuple) {
	return "arg" + tuple/*Tuple2<CompileState, Whitespace>*/.left(/*auto*/) + " : " + tuple/*Tuple2<CompileState, Whitespace>*/.right(/*auto*/);
}
&[I8] generate() {
	var joinedArguments = this/*auto*/.args.queryWithIndices(/*auto*/).map(lambdaDefinition/*auto*/).collect(new Joiner(", ")).orElse("");
	return "(" + joinedArguments/*auto*/ + ") => " + this/*auto*/.returns;
}
Bool isFunctional() {
	return true/*auto*/;
}
Bool isVar() {
	return false/*auto*/;
}
&[I8] generateBeforeName() {
	return "";
}
constructor (&[I8] value) {
	this/*auto*/.value = value/*&[I8]*/;
}
&[I8] generate() {
	return this/*auto*/.value;
}
Bool isFunctional() {
	return false/*auto*/;
}
Bool isVar() {
	return PrimitiveType/*auto*/.Var === this/*auto*/;
}
&[I8] generateBeforeName() {
	return "";
}
&[I8] generate() {
	return "&[" + this/*auto*/.type.generate(/*auto*/) + "]";
}
Bool isFunctional() {
	return false/*auto*/;
}
Bool isVar() {
	return false/*auto*/;
}
&[I8] generateBeforeName() {
	return "";
}
static &[I8] generateValueStrings(List<&[I8]> values) {
	return Main/*auto*/.generateAll(values/*List<&[I8]>*/, TemplateType/*auto*/.mergeValues);
}
static &[I8] mergeValues(&[I8] cache, &[I8] element) {
	if (Strings/*auto*/.isEmpty(cache/*&[I8]*/)) {
		return cache/*&[I8]*/ + element/*&[I8]*/;
	}
	return cache/*&[I8]*/ + ", " + element/*&[I8]*/;
}
&[I8] generate() {
	return this/*auto*/.base + "<" + TemplateType/*auto*/.generateValueStrings(this/*auto*/.args) + ">";
}
Bool isFunctional() {
	return false/*auto*/;
}
Bool isVar() {
	return false/*auto*/;
}
&[I8] generateBeforeName() {
	return "";
}
&[I8] generate() {
	return this/*auto*/.type.generate(/*auto*/) + "[]";
}
Bool isFunctional() {
	return false/*auto*/;
}
Bool isVar() {
	return false/*auto*/;
}
&[I8] generateBeforeName() {
	return "...";
}
&[I8] generate(Platform platform) {
	return this/*auto*/.child.generate(platform/*Platform*/) + "." + this/*auto*/.property;
}
Option<Value> toValue() {
	return new Some<Value>(this/*auto*/);
}
Option<Value> findChild() {
	return new Some<Value>(this/*auto*/.child);
}
Option<&[I8]> generateAsEnumValue(&[I8] structureName, Platform platform) {
	return new None<&[I8]>(/*auto*/);
}
Type type() {
	return PrimitiveType/*auto*/.Auto;
}
&[I8] generate(Platform platform) {
	if (Platform/*auto*/.Magma === this/*auto*/.platform) {
		return this/*auto*/.right;
	}
	return "new " + this/*auto*/.right;
}
Option<Value> findChild() {
	return new None<Value>(/*auto*/);
}
&[I8] generateWithAfterName(Platform platform, &[I8] afterName) {
	return "constructor " + afterName/*&[I8]*/;
}
Bool hasAnnotation(&[I8] annotation) {
	return false/*auto*/;
}
ConstructorHeader removeModifier(&[I8] modifier) {
	return this/*auto*/;
}
ConstructorHeader addModifierLast(&[I8] modifier) {
	return this/*auto*/;
}
&[I8] generateWithDefinitions0(Platform platform, &[I8] definitions) {
	return generateWithAfterName/*auto*/(platform/*Platform*/, "(" + definitions/*&[I8]*/ + ")");
}
auto temp(Definition definition) {
	return definition/*Definition*/.generate(platform/*Platform*/);
}
&[I8] generateWithDefinitions(Platform platform, List<Definition> definitions) {
	var joinedDefinitions = definitions/*List<Definition>*/.query(/*auto*/).map(lambdaDefinition/*auto*/).collect(new Joiner(", ")).orElse("");
	return this/*auto*/.generateWithDefinitions0(platform/*Platform*/, joinedDefinitions/*auto*/);
}
&[I8] generate(Platform platform) {
	var joinedArguments = this/*auto*/.joinArgs(platform/*Platform*/);
	return this/*auto*/.caller.generate(platform/*Platform*/) + "(" + joinedArguments/*auto*/ + ")";
}
auto temp(Value value) {
	return value/*&[I8]*/.generate(platform/*Platform*/);
}
&[I8] joinArgs(Platform platform) {
	return this/*auto*/.args.query(/*auto*/).map(lambdaDefinition/*auto*/).collect(new Joiner(", ")).orElse("");
}
Option<Value> toValue() {
	return new Some<Value>(this/*auto*/);
}
Option<Value> findChild() {
	return new None<Value>(/*auto*/);
}
Option<&[I8]> generateAsEnumValue(&[I8] structureName, Platform platform) {
	return new Some<&[I8]>("\n\tstatic " + this/*auto*/.caller.generate(platform/*Platform*/) + ": " + structureName/*&[I8]*/ + " = new " + structureName/*&[I8]*/ + "(" + this/*auto*/.joinArgs(platform/*Platform*/) + ");");
}
Type type() {
	return PrimitiveType/*auto*/.Auto;
}
auto temp(Definition definition) {
	return definition/*Definition*/.generate(platform/*Platform*/);
}
&[I8] generate(Platform platform) {
	var joinedParamNames = this/*auto*/.paramNames.query(/*auto*/).map(lambdaDefinition/*auto*/).collect(new Joiner(", ")).orElse("");
	return "(" + joinedParamNames/*auto*/ + ")" + " => " + this/*auto*/.content;
}
Option<Value> toValue() {
	return new Some<Value>(this/*auto*/);
}
Option<Value> findChild() {
	return new None<Value>(/*auto*/);
}
Option<&[I8]> generateAsEnumValue(&[I8] structureName, Platform platform) {
	return new None<&[I8]>(/*auto*/);
}
Type type() {
	return PrimitiveType/*auto*/.Auto;
}
&[I8] generate(Platform platform) {
	return this/*auto*/.child;
}
Option<Value> toValue() {
	return new Some<Value>(this/*auto*/);
}
Option<Value> findChild() {
	return new None<Value>(/*auto*/);
}
Option<&[I8]> generateAsEnumValue(&[I8] structureName, Platform platform) {
	return new None<&[I8]>(/*auto*/);
}
Type type() {
	return PrimitiveType/*auto*/.Auto;
}
&[I8] generate(Platform platform) {
	return this/*auto*/.left.generate(platform/*Platform*/) + " " + this/*auto*/.targetInfix + " " + this/*auto*/.right.generate(platform/*Platform*/);
}
Option<Value> toValue() {
	return new Some<Value>(this/*auto*/);
}
Option<Value> findChild() {
	return new None<Value>(/*auto*/);
}
Option<&[I8]> generateAsEnumValue(&[I8] structureName, Platform platform) {
	return new None<&[I8]>(/*auto*/);
}
Type type() {
	return PrimitiveType/*auto*/.Auto;
}
&[I8] generate(Platform platform) {
	return "\"" + this/*auto*/.value + "\"";
}
Option<Value> toValue() {
	return new Some<Value>(this/*auto*/);
}
Option<Value> findChild() {
	return new None<Value>(/*auto*/);
}
Option<&[I8]> generateAsEnumValue(&[I8] structureName, Platform platform) {
	return new None<&[I8]>(/*auto*/);
}
Type type() {
	return PrimitiveType/*auto*/.Auto;
}
&[I8] generate(Platform platform) {
	return this/*auto*/.value + Main/*auto*/.generatePlaceholder(type/*Type*/.generate(/*auto*/));
}
Option<Value> toValue() {
	return new Some<Value>(this/*auto*/);
}
Option<Value> findChild() {
	return new None<Value>(/*auto*/);
}
&[I8] generate() {
	return this/*auto*/.value;
}
Bool isFunctional() {
	return false/*auto*/;
}
Bool isVar() {
	return false/*auto*/;
}
&[I8] generateBeforeName() {
	return "";
}
Option<&[I8]> generateAsEnumValue(&[I8] structureName, Platform platform) {
	return new None<&[I8]>(/*auto*/);
}
constructor (&[I8] root, ...&[I8][] extensions) {
	this/*auto*/.root = root/*&[I8]*/;
	this/*auto*/.extension = extensions/*&[I8][]*/;
}
Result<&[I8], IOError> read() {
	return this/*auto*/.source.readString(/*auto*/);
}
&[I8] computeName() {
	var fileName = this/*auto*/.source.findFileName(/*auto*/);
	var separator = fileName/*auto*/.lastIndexOf(".");
	return fileName/*auto*/.substring(0/*auto*/, separator/*auto*/);
}
List<&[I8]> computeNamespace() {
	return this/*auto*/.sourceDirectory.relativize(this/*auto*/.source).getParent(/*auto*/).query(/*auto*/).collect(new ListCollector<&[I8]>(/*auto*/));
}
Location computeLocation() {
	return new Location(this/*auto*/.computeNamespace(/*auto*/), this/*auto*/.computeName(/*auto*/));
}
auto temp(List<Path> children) {
	return Main/*auto*/.runWithSources(Main/*auto*/.findSources(children/*List<Path>*/, sourceDirectory/*Path*/));
}
auto temp(IOError value) {
	return new Some<IOError>(value/*&[I8]*/);
}
auto temp(IOError error) {
	return error/*auto*/.display(/*auto*/);
}
auto temp(&[I8] displayed) {
	return Console/*auto*/.printErrLn(displayed/*auto*/);
}
static void main() {
	var sourceDirectory = Files/*auto*/.get(".", "src", "java");
	sourceDirectory/*Path*/.walk(/*auto*/).match(lambdaDefinition/*auto*/, lambdaDefinition/*auto*/).map(lambdaDefinition/*auto*/).ifPresent(lambdaDefinition/*auto*/);
}
auto temp(CompileState current1, Platform platform) {
	return Main/*auto*/.runWithPlatform(current1/*auto*/, platform/*Platform*/, sources/*List<Source>*/);
}
static Option<IOError> runWithSources(List<Source> sources) {
	return Queries/*auto*/.fromArray(Platform/*auto*/.values(/*auto*/)).foldWithInitialToResult(Main/*auto*/.createInitialState(sources/*List<Source>*/), lambdaDefinition/*auto*/).findError(/*auto*/);
}
auto temp(Tuple2<CompileState, List<IncompleteRoot>> tuple, Source source) {
	return Main/*auto*/.foldWithInput(platform/*Platform*/, tuple/*Tuple2<CompileState, Whitespace>*/.left(/*auto*/), tuple/*Tuple2<CompileState, Whitespace>*/.right(/*auto*/), source/*Source*/);
}
auto temp(Tuple2<CompileState, List<IncompleteRoot>> result) {
	return Main/*auto*/.completeAll(platform/*Platform*/, result/*Tuple2<CompileState, &[I8]>*/.left(/*auto*/), result/*Tuple2<CompileState, &[I8]>*/.right(/*auto*/));
}
static Result<CompileState, IOError> runWithPlatform(CompileState initial, Platform platform, List<Source> sources) {
	/*final Tuple2<CompileState, List<IncompleteRoot>> compileStateListTuple2*/;
	compileStateListTuple2/*auto*/ = new Tuple2Impl<CompileState, List<IncompleteRoot>>(initial/*CompileState*/.clearDefinedTypes(/*auto*/), Lists/*auto*/.empty(/*auto*/));
	return sources/*List<Source>*/.query(/*auto*/).foldWithInitialToResult(compileStateListTuple2/*auto*/, lambdaDefinition/*auto*/).flatMapValue(lambdaDefinition/*auto*/);
}
auto temp(CompileState current, IncompleteRoot incompleteRoot) {
	return Main/*auto*/.complete(current/*List<T>*/, incompleteRoot/*auto*/, platform/*Platform*/);
}
static Result<CompileState, IOError> completeAll(Platform platform, CompileState state, List<IncompleteRoot> incomplete) {
	return incomplete/*List<IncompleteRoot>*/.query(/*auto*/).foldWithInitialToResult(state/*CompileState*/, lambdaDefinition/*auto*/);
}
auto temp(Tuple2<CompileState, IncompleteRoot> result) {{
		return new Tuple2Impl<>(result/*Tuple2<CompileState, IncompleteRoot>*/.left(/*auto*/), incomplete/*List<IncompleteRoot>*/.addLast(result/*Tuple2<CompileState, IncompleteRoot>*/.right(/*auto*/)));
	}
}
static Result<Tuple2<CompileState, List<IncompleteRoot>>, IOError> foldWithInput(Platform platform, CompileState state, List<IncompleteRoot> incomplete, Source source) {
	return Main/*auto*/.runWithInput(state/*CompileState*/, platform/*Platform*/, source/*Source*/).mapValue(lambdaDefinition/*auto*/);
}
auto temp(&[I8] input) {{
		return Main/*auto*/.prepareRoot(state/*CompileState*/, source/*Source*/, input/*&[I8]*/, platform/*Platform*/);
	}
}
static Result<Tuple2<CompileState, IncompleteRoot>, IOError> runWithInput(CompileState state, Platform platform, Source source) {
	return source/*Source*/.read(/*auto*/).mapValue(lambdaDefinition/*auto*/);
}
auto temp(CompileState state, Source source) {
	return state/*CompileState*/.addSource(source/*Source*/);
}
static CompileState createInitialState(List<Source> sources) {
	return sources/*List<Source>*/.query(/*auto*/).foldWithInitial(ImmutableCompileState/*auto*/.createInitial(/*auto*/), lambdaDefinition/*auto*/);
}
auto temp(Path source) {
	return source/*Source*/.endsWith(".java");
}
auto temp(Path child) {
	return new Source(sourceDirectory/*Path*/, child/*&[I8]*/);
}
static List<Source> findSources(List<Path> children, Path sourceDirectory) {
	return children/*List<Path>*/.query(/*auto*/).filter(lambdaDefinition/*auto*/).map(lambdaDefinition/*auto*/).collect(new ListCollector<Source>(/*auto*/));
}
static Tuple2Impl<CompileState, IncompleteRoot> prepareRoot(CompileState state, Source source, &[I8] input, Platform platform) {
	var location = source/*Source*/.computeLocation(/*auto*/);
	var initialized = state/*CompileState*/.withPlatform(platform/*Platform*/).withLocation(location/*Location*/);
	return Main/*auto*/.getCompileStateIncompleteRootTuple2(input/*&[I8]*/, initialized/*CompileState*/, location/*Location*/);
}
auto temp(CompileState state1, &[I8] s) {
	return new Some<>(Main/*auto*/.parseRootSegment(state1/*DivideState*/, s/*&[I8]*/));
}
static Tuple2Impl<CompileState, IncompleteRoot> getCompileStateIncompleteRootTuple2(&[I8] input, CompileState initialized, Location location) {
	var statementsTuple = Main/*auto*/.parseAll(initialized/*CompileState*/, input/*&[I8]*/, Main/*auto*/.foldStatements, lambdaDefinition/*auto*/).orElse(new Tuple2Impl<>(initialized/*CompileState*/, Lists/*auto*/.empty(/*auto*/)));
	var statementsState = statementsTuple/*auto*/.left(/*auto*/);
	var statements = statementsTuple/*auto*/.right(/*auto*/);
	var incomplete = new IncompleteRoot(location/*Location*/, statements/*auto*/);
	return new Tuple2Impl<CompileState, IncompleteRoot>(statementsState/*CompileState*/, incomplete/*List<IncompleteRoot>*/);
}
auto temp(&[I8] value) {
	return "\n\t" + value/*&[I8]*/;
}
static Tuple2<CompileState, Map<&[I8], &[I8]>> getStringStringHashMap(CompileState state, List<IncompleteRootSegment> segments) {
	var location = state/*CompileState*/.findCurrentLocation(/*auto*/).orElse(new Location(Lists/*auto*/.empty(/*auto*/), ""));
	var entries = new HashMap<&[I8], &[I8]>(/*auto*/);
	var namespace = location/*Location*/.namespace(/*auto*/);
	var name = location/*Location*/.name(/*auto*/);
	var joinedDefinedTypes = state/*CompileState*/.findDefinedTypes(/*auto*/).sort(String/*auto*/.compareTo).query(/*auto*/).map(lambdaDefinition/*auto*/).collect(new Joiner(", ")).orElse("");
	var debug = "/*[" + joinedDefinedTypes/*auto*/ + "\n]*/\n";
	var generatedMain = Main/*auto*/.createMain(name/*&[I8]*/);
	var imports = Main/*auto*/.generateOrFoldImports(state/*CompileState*/);
	var joinedOutput = segments/*List<IncompleteRootSegment>*/.query(/*auto*/).map(IncompleteRootSegment/*auto*/.value).collect(new Joiner("")).orElse("");
	if (state/*CompileState*/.isPlatform(Platform/*auto*/.Windows)) {
		var value = /* namespace.query().collect(new Joiner("_")).map((String inner) -> inner + "_").orElse("") + name*/;
		/*entries.put(Platform.Windows.extension[0], debug + Main.generateDirective("ifndef " + value) + Main.generateDirective("define " + value) + imports + Main.generateDirective("endif"))*/;
		/*entries.put(Platform.Windows.extension[1], Main.generateDirective("include \"./" + name + ".h\"") + state.join() + joinedOutput + generatedMain)*/;
	}
	else {
		/*entries.put(state.platform().extension[0], debug + imports + state.join() + joinedOutput + generatedMain)*/;
	}
	return new Tuple2Impl<>(state/*CompileState*/.clearImports(/*auto*/).clearGenerated(/*auto*/), entries/*auto*/);
}
static Result<CompileState, IOError> complete(CompileState state, IncompleteRoot incomplete, Platform platform) {
	var entries = Main/*auto*/.getStringStringHashMap(state/*CompileState*/, incomplete/*IncompleteRoot*/.outputsByExtensions(/*auto*/));
	/*return Main.writeOutputEntries(platform, incomplete.location(), entries.right())
                .<Result<CompileState, IOError>>map((IOError error) -> new Err<CompileState, IOError>(error))
                .orElseGet(() -> new Ok<>(entries.left()))*/;
}
auto temp() {
	return Main/*auto*/.writeOutputEntryWithParent(platformRoot/*auto*/, extension/*&[I8]*/, location/*Location*/, outputsByExtensions/*Map<&[I8], &[I8]>*/);
}
auto temp(Option<IOError> maybeError0, &[I8] extension) {
	return maybeError0/*auto*/.or(lambdaDefinition/*auto*/);
}
static Option<IOError> writeOutputEntries(Platform platform, Location location, Map<&[I8], &[I8]> outputsByExtensions) {
	Option<IOError> initial = new None<IOError>(/*auto*/);
	var platformRoot = Files/*auto*/.get(".", "src", platform/*Platform*/.root);
	return Queries/*auto*/.fromArray(platform/*Platform*/.extension).foldWithInitial(initial/*CompileState*/, lambdaDefinition/*auto*/);
}
auto temp(Path targetParent) {
	return Main/*auto*/.writeOutputEntry(targetParent/*Path*/, location/*Location*/, outputsByExtensions/*Map<&[I8], &[I8]>*/, extension/*&[I8]*/);
}
static Option<IOError> writeOutputEntryWithParent(Path directory, &[I8] extension, Location location, Map<&[I8], &[I8]> outputsByExtensions) {
	return Main/*auto*/.ensureTargetParent(directory/*Path*/, location/*Location*/.namespace(/*auto*/)).match(lambdaDefinition/*auto*/, Some/*auto*/.new);
}
static Option<IOError> writeOutputEntry(Path targetParent, Location location, Map<&[I8], &[I8]> outputsByExtensions, &[I8] extension) {
	var target = targetParent/*Path*/.resolveChild(location/*Location*/.name(/*auto*/) + "." + extension/*&[I8]*/);
	return target/*auto*/.writeString(outputsByExtensions/*Map<&[I8], &[I8]>*/.get(extension/*&[I8]*/));
}
static Result<Path, IOError> ensureTargetParent(Path directory, List<&[I8]> namespace) {
	var targetParent = directory/*Path*/.resolveChildSegments(namespace/*List<&[I8]>*/);
	if (targetParent/*Path*/.exists(/*auto*/)) {
		return new Ok<>(targetParent/*Path*/);
	}
	/*return targetParent.createDirectories()
                .<Result<Path, IOError>>map((IOError error) -> new Err<>(error))
                .orElseGet(() -> new Ok<>(targetParent))*/;
}
static &[I8] generateOrFoldImports(CompileState state) {
	if (state/*CompileState*/.isPlatform(Platform/*auto*/.Magma)) {
		return Main/*auto*/.foldImports(state/*CompileState*/);
	}
	return Main/*auto*/.generateImports(state/*CompileState*/);
}
auto temp(Import anImport) {
	return anImport/*Import*/.generate(state/*CompileState*/.platform(/*auto*/));
}
static &[I8] generateImports(CompileState state) {
	return state/*CompileState*/.imports(/*auto*/).query(/*auto*/).map(lambdaDefinition/*auto*/).collect(new Joiner("")).orElse("");
}
static &[I8] foldImports(CompileState statementsState) {
	return statementsState/*CompileState*/.imports(/*auto*/).query(/*auto*/).foldWithInitial(Lists/*auto*/.empty(/*auto*/), Main/*auto*/.foldImport).query(/*auto*/).foldWithInitial("", Main/*auto*/.generateEntry);
}
static &[I8] generateEntry(&[I8] current, Tuple2<List<&[I8]>, List<&[I8]>> entry) {
	var joinedNamespace = entry/*Tuple2<List<&[I8]>, List<&[I8]>>*/.left(/*auto*/).query(/*auto*/).collect(new Joiner(".")).orElse("");
	var joinedChildren = entry/*Tuple2<List<&[I8]>, List<&[I8]>>*/.right(/*auto*/).query(/*auto*/).collect(new Joiner(", ")).orElse("");
	return current/*&[I8]*/ + "import " + joinedNamespace/*auto*/ + ".{ " + joinedChildren/*auto*/ + " };\n";
}
static List<Tuple2<List<&[I8]>, List<&[I8]>>> foldImport(List<Tuple2<List<&[I8]>, List<&[I8]>>> current, Import anImport) {
	var namespace = anImport/*Import*/.namespace(/*auto*/);
	var child = anImport/*Import*/.child(/*auto*/);
	if (Main/*auto*/.hasNamespace(current/*List<Tuple2<List<&[I8]>, List<&[I8]>>>*/, namespace/*List<&[I8]>*/)) {
		return Main/*auto*/.attachChildToMapEntries(current/*List<Tuple2<List<&[I8]>, List<&[I8]>>>*/, namespace/*List<&[I8]>*/, child/*&[I8]*/);
	}
	else {
		return current/*List<Tuple2<List<&[I8]>, List<&[I8]>>>*/.addLast(new Tuple2Impl<>(namespace/*List<&[I8]>*/, Lists/*auto*/.of(child/*&[I8]*/)));
	}
}
auto temp(List<&[I8]> stringList) {
	return namespace/*List<&[I8]>*/.equalsTo(stringList/*auto*/, String/*auto*/.equals);
}
static Bool hasNamespace(List<Tuple2<List<&[I8]>, List<&[I8]>>> map, List<&[I8]> namespace) {
	return map/*List<Tuple2<List<&[I8]>, List<&[I8]>>>*/.query(/*auto*/).map(Tuple2/*auto*/.left).anyMatch(lambdaDefinition/*auto*/);
}
auto temp(Tuple2<List<&[I8]>, List<&[I8]>> tuple) {
	return Main/*auto*/.attachChildToMapEntry(namespace/*List<&[I8]>*/, child/*&[I8]*/, tuple/*Tuple2<CompileState, Whitespace>*/);
}
static List<Tuple2<List<&[I8]>, List<&[I8]>>> attachChildToMapEntries(List<Tuple2<List<&[I8]>, List<&[I8]>>> map, List<&[I8]> namespace, &[I8] child) {
	return map/*List<Tuple2<List<&[I8]>, List<&[I8]>>>*/.query(/*auto*/).map(lambdaDefinition/*auto*/).collect(new ListCollector<>(/*auto*/));
}
static Tuple2<List<&[I8]>, List<&[I8]>> attachChildToMapEntry(List<&[I8]> namespace, &[I8] child, Tuple2<List<&[I8]>, List<&[I8]>> tuple) {
	var entryNamespace = tuple/*Tuple2<List<&[I8]>, List<&[I8]>>*/.left(/*auto*/);
	var entryValues = tuple/*Tuple2<List<&[I8]>, List<&[I8]>>*/.right(/*auto*/);
	if (entryNamespace/*auto*/.equalsTo(namespace/*List<&[I8]>*/, String/*auto*/.equals)) {
		return new Tuple2Impl<>(entryNamespace/*auto*/, entryValues/*auto*/.addLast(child/*&[I8]*/));
	}
	else {
		return tuple/*Tuple2<List<&[I8]>, List<&[I8]>>*/;
	}
}
static &[I8] generateDirective(&[I8] content) {
	return "#" + content/*&[I8]*/ + "\n";
}
static &[I8] createMain(&[I8] name) {
	if (Strings/*auto*/.equalsTo(name/*&[I8]*/, "Main")) {
		return "Main.main();";
	}
	return "";
}
static Tuple2<CompileState, &[I8]> compileStatements(CompileState state, &[I8] input, (arg0 : CompileState, arg1 : &[I8]) => Tuple2<CompileState, &[I8]> mapper) {
	return Main/*auto*/.compileAll(state/*CompileState*/, input/*&[I8]*/, Main/*auto*/.foldStatements, mapper/*(arg0 : CompileState, arg1 : &[I8]) => Tuple2<CompileState, &[I8]>*/, Main/*auto*/.mergeStatements);
}
auto temp(CompileState state1, &[I8] s) {
	return new Some<Tuple2<CompileState, &[I8]>>(mapper/*(arg0 : CompileState, arg1 : &[I8]) => Tuple2<CompileState, &[I8]>*/(state1/*DivideState*/, s/*&[I8]*/));
}
static Tuple2<CompileState, &[I8]> compileAll(CompileState state, &[I8] input, (arg0 : DivideState, arg1 : I8) => DivideState folder, (arg0 : CompileState, arg1 : &[I8]) => Tuple2<CompileState, &[I8]> mapper, (arg0 : &[I8], arg1 : &[I8]) => &[I8] merger) {
	var folded = Main/*auto*/.parseAll(state/*CompileState*/, input/*&[I8]*/, folder/*(arg0 : DivideState, arg1 : I8) => DivideState*/, lambdaDefinition/*auto*/).orElse(new Tuple2Impl<CompileState, List<&[I8]>>(state/*CompileState*/, Lists/*auto*/.empty(/*auto*/)));
	return new Tuple2Impl<CompileState, &[I8]>(folded/*auto*/.left(/*auto*/), Main/*auto*/.generateAll(folded/*auto*/.right(/*auto*/), merger/*(arg0 : &[I8], arg1 : &[I8]) => &[I8]*/));
}
static &[I8] generateAll(List<&[I8]> elements, (arg0 : &[I8], arg1 : &[I8]) => &[I8] merger) {
	return elements/*List<&[I8]>*/.query(/*auto*/).foldWithInitial("", merger/*(arg0 : &[I8], arg1 : &[I8]) => &[I8]*/);
}
auto temp(Tuple2<CompileState, T> mappedTuple) {{
			var mappedState = mappedTuple/*Tuple2<CompileState, T>*/.left(/*auto*/);
			var mappedElement = mappedTuple/*Tuple2<CompileState, T>*/.right(/*auto*/);
			return new Tuple2Impl<CompileState, List<T>>(mappedState/*auto*/, currentElement/*auto*/.addLast(mappedElement/*auto*/));
		}
}
auto temp(Tuple2<CompileState, List<T>> current) {{
		var currentState = current/*Tuple2<CompileState, List<T>>*/.left(/*auto*/);
		var currentElement = current/*Tuple2<CompileState, List<T>>*/.right(/*auto*/);
		return biFunction/*(arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, T>>*/(currentState/*auto*/, segment/*auto*/).map(lambdaDefinition/*auto*/);
	}
}
auto temp(Option<Tuple2<CompileState, List<T>>> maybeCurrent, &[I8] segment) {
	return maybeCurrent/*auto*/.flatMap(lambdaDefinition/*auto*/);
}
static Option<Tuple2<CompileState, List<T>>> parseAll(CompileState state, &[I8] input, (arg0 : DivideState, arg1 : I8) => DivideState folder, (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, T>> biFunction) {
	return Main/*auto*/.divide(input/*&[I8]*/, folder/*(arg0 : DivideState, arg1 : I8) => DivideState*/).query(/*auto*/).foldWithInitial(new Some<Tuple2<CompileState, List<T>>>(new Tuple2Impl<CompileState, List<T>>(state/*CompileState*/, Lists/*auto*/.empty(/*auto*/))), lambdaDefinition/*auto*/);
}
static &[I8] mergeStatements(&[I8] cache, &[I8] element) {
	return cache/*&[I8]*/ + element/*&[I8]*/;
}
auto temp() {
	return Main/*auto*/.foldDoubleQuotes(poppedState/*auto*/, popped/*auto*/);
}
auto temp() {
	return folder/*(arg0 : DivideState, arg1 : I8) => DivideState*/(poppedState/*auto*/, popped/*auto*/);
}
static List<&[I8]> divide(&[I8] input, (arg0 : DivideState, arg1 : I8) => DivideState folder) {
	var current = DivideState/*auto*/.createInitial(input/*&[I8]*/);
	while (true/*auto*/) {
		var poppedTuple0 = current/*Tuple2<CompileState, List<T>>*/.pop(/*auto*/).toTuple(new Tuple2Impl<DivideState, I8>(current/*Tuple2<CompileState, List<T>>*/, "\0"));
		if (!poppedTuple0/*auto*/.left(/*auto*/)) {
			break;
		}
		var poppedTuple = poppedTuple0/*auto*/.right(/*auto*/);
		var poppedState = poppedTuple/*auto*/.left(/*auto*/);
		var popped = poppedTuple/*auto*/.right(/*auto*/);
		current/*Tuple2<CompileState, List<T>>*/ = Main/*auto*/.foldSingleQuotes(poppedState/*auto*/, popped/*auto*/).or(lambdaDefinition/*auto*/).orElseGet(lambdaDefinition/*auto*/);
	}
	return current/*Tuple2<CompileState, List<T>>*/.advance(/*auto*/).segments(/*auto*/);
}
static Option<DivideState> foldDoubleQuotes(DivideState state, I8 c) {
	if ("\"" !== c/*I8*/) {
		return new None<DivideState>(/*auto*/);
	}
	var appended = state/*DivideState*/.append(c/*I8*/);
	while (true/*auto*/) {
		var maybeTuple = appended/*auto*/.popAndAppendToTuple(/*auto*/).toTuple(new Tuple2Impl<DivideState, I8>(appended/*auto*/, "\0"));
		if (!maybeTuple/*auto*/.left(/*auto*/)) {
			break;
		}
		var tuple = maybeTuple/*auto*/.right(/*auto*/);
		appended/*auto*/ = tuple/*Tuple2<List<&[I8]>, List<&[I8]>>*/.left(/*auto*/);
		if ("\\" === tuple/*Tuple2<List<&[I8]>, List<&[I8]>>*/.right(/*auto*/)) {
			appended/*auto*/ = appended/*auto*/.popAndAppendToOption(/*auto*/).orElse(appended/*auto*/);
		}
		if ("\"" === tuple/*Tuple2<List<&[I8]>, List<&[I8]>>*/.right(/*auto*/)) {
			break;
		}
	}
	return new Some<DivideState>(appended/*auto*/);
}
auto temp(DivideState state1) {
	return state1/*DivideState*/.popAndAppendToOption(/*auto*/);
}
static Option<DivideState> foldSingleQuotes(DivideState state, I8 c) {
	if ("\'" !== c/*I8*/) {
		return new None<DivideState>(/*auto*/);
	}
	return state/*DivideState*/.append(c/*I8*/).popAndAppendToTuple(/*auto*/).flatMap(Main/*auto*/.foldEscaped).flatMap(lambdaDefinition/*auto*/);
}
static Option<DivideState> foldEscaped(Tuple2<DivideState, I8> tuple) {
	var state = tuple/*Tuple2<DivideState, I8>*/.left(/*auto*/);
	var c = tuple/*Tuple2<DivideState, I8>*/.right(/*auto*/);
	if ("\\" === c/*I8*/) {
		return state/*DivideState*/.popAndAppendToOption(/*auto*/);
	}
	return new Some<DivideState>(state/*DivideState*/);
}
static DivideState foldStatements(DivideState state, I8 c) {
	var appended = state/*DivideState*/.append(c/*I8*/);
	if (";" === c/*I8*/ && appended/*auto*/.isLevel(/*auto*/)) {
		return appended/*auto*/.advance(/*auto*/);
	}
	if ("}" === c/*I8*/ && appended/*auto*/.isShallow(/*auto*/)) {
		return appended/*auto*/.advance(/*auto*/).exit(/*auto*/);
	}
	if ("{" === c/*I8*/ || "(" === c/*I8*/) {
		return appended/*auto*/.enter(/*auto*/);
	}
	if ("}" === c/*I8*/ || ")" === c/*I8*/) {
		return appended/*auto*/.exit(/*auto*/);
	}
	return appended/*auto*/;
}
auto temp() {
	return new Tuple2Impl<CompileState, IncompleteRootSegment>(state/*CompileState*/, new IncompleteRootSegment(Main/*auto*/.generatePlaceholder(input/*&[I8]*/)));
}
static Tuple2<CompileState, IncompleteRootSegment> parseRootSegment(CompileState state, &[I8] input) {
	return Main/*auto*/.or(state/*CompileState*/, input/*&[I8]*/, Lists/*auto*/.of(Main/*auto*/.typed(Main/*auto*/.compileWhitespace), Main/*auto*/.typed(Main/*auto*/.compileNamespaced), Main/*auto*/.typed(Main/*auto*/.createStructureRule("class ", "class ")), Main/*auto*/.typed(Main/*auto*/.createStructureRule("interface ", "interface ")), Main/*auto*/.typed(Main/*auto*/.createStructureRule("record ", "class ")), Main/*auto*/.typed(Main/*auto*/.createStructureRule("enum ", "class ")))).orElseGet(lambdaDefinition/*auto*/);
}
auto temp(Tuple2<CompileState, &[I8]> result) {{
			return new Tuple2Impl<>(result/*Tuple2<CompileState, &[I8]>*/.left(/*auto*/), new IncompleteRootSegment(result/*Tuple2<CompileState, &[I8]>*/.right(/*auto*/)));
		}
}
auto temp(CompileState state, &[I8] s) {{
		var apply = mapper/*(arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, &[I8]>>*/(state/*CompileState*/, s/*&[I8]*/);
		return apply/*auto*/.map(lambdaDefinition/*auto*/);
	}
}
static (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, IncompleteRootSegment>> typed((arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, &[I8]>> mapper) {
	return lambdaDefinition/*auto*/;
}
auto temp(&[I8] s, &[I8] s2) {{
		var annotations = Main/*auto*/.parseAnnotations(s/*&[I8]*/);
		if (annotations/*List<&[I8]>*/.contains("Actual", Strings/*auto*/.equalsTo)) {
			return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(state/*CompileState*/, ""));
		}
		return Main/*auto*/.compileStructureWithImplementing(state/*CompileState*/, annotations/*List<&[I8]>*/, Main/*auto*/.parseModifiers(s2/*&[I8]*/), targetInfix/*&[I8]*/, beforeContent/*&[I8]*/, inputContent/*&[I8]*/);
	}
}
auto temp() {{
		var modifiers = Main/*auto*/.parseModifiers(beforeContent/*&[I8]*/);
		return Main/*auto*/.compileStructureWithImplementing(state/*CompileState*/, Lists/*auto*/.empty(/*auto*/), modifiers/*List<&[I8]>*/, targetInfix/*&[I8]*/, beforeContent/*&[I8]*/, inputContent/*&[I8]*/);
	}
}
auto temp(&[I8] inputContent) {
	return Main/*auto*/.compileLast(beforeInfix/*auto*/, "\n", lambdaDefinition/*auto*/).or(lambdaDefinition/*auto*/);
}
auto temp(&[I8] beforeContent, &[I8] withEnd) {
	return Main/*auto*/.compileSuffix(Strings/*auto*/.strip(withEnd/*auto*/), "}", lambdaDefinition/*auto*/);
}
auto temp(&[I8] beforeInfix, &[I8] afterInfix) {
	return Main/*auto*/.compileFirst(afterInfix/*auto*/, "{", lambdaDefinition/*auto*/);
}
auto temp(CompileState state, &[I8] input1) {
	return Main/*auto*/.compileFirst(input1/*&[I8]*/, sourceInfix/*&[I8]*/, lambdaDefinition/*auto*/);
}
static (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, &[I8]>> createStructureRule(&[I8] sourceInfix, &[I8] targetInfix) {
	return lambdaDefinition/*auto*/;
}
auto temp(Tuple2<CompileState, Type> implementingTuple) {
	return Main/*auto*/.compileStructureWithExtends(implementingTuple/*auto*/.left(/*auto*/), annotations/*List<&[I8]>*/, modifiers/*List<&[I8]>*/, targetInfix/*&[I8]*/, s/*&[I8]*/, new Some<Type>(implementingTuple/*auto*/.right(/*auto*/)), content/*&[I8]*/);
}
auto temp(&[I8] s, &[I8] s2) {
	return Main/*auto*/.parseType(state/*CompileState*/, s2/*&[I8]*/).flatMap(lambdaDefinition/*auto*/);
}
auto temp() {
	return Main/*auto*/.compileStructureWithExtends(state/*CompileState*/, annotations/*List<&[I8]>*/, modifiers/*List<&[I8]>*/, targetInfix/*&[I8]*/, beforeContent/*&[I8]*/, new None<Type>(/*auto*/), content/*&[I8]*/);
}
static Option<Tuple2<CompileState, &[I8]>> compileStructureWithImplementing(CompileState state, List<&[I8]> annotations, List<&[I8]> modifiers, &[I8] targetInfix, &[I8] beforeContent, &[I8] content) {
	return Main/*auto*/.compileLast(beforeContent/*&[I8]*/, " implements ", lambdaDefinition/*auto*/).or(lambdaDefinition/*auto*/);
}
auto temp(&[I8] beforeExtends, &[I8] afterExtends) {
	return Main/*auto*/.compileStructureWithParameters(state/*CompileState*/, annotations/*List<&[I8]>*/, modifiers/*List<&[I8]>*/, targetInfix/*&[I8]*/, beforeExtends/*auto*/, new Some<&[I8]>(afterExtends/*auto*/), maybeImplementing/*Option<Type>*/, inputContent/*&[I8]*/);
}
auto temp() {
	return Main/*auto*/.compileStructureWithParameters(state/*CompileState*/, annotations/*List<&[I8]>*/, modifiers/*List<&[I8]>*/, targetInfix/*&[I8]*/, beforeContent/*&[I8]*/, new None<&[I8]>(/*auto*/), maybeImplementing/*Option<Type>*/, inputContent/*&[I8]*/);
}
static Option<Tuple2<CompileState, &[I8]>> compileStructureWithExtends(CompileState state, List<&[I8]> annotations, List<&[I8]> modifiers, &[I8] targetInfix, &[I8] beforeContent, Option<Type> maybeImplementing, &[I8] inputContent) {
	return Main/*auto*/.compileFirst(beforeContent/*&[I8]*/, " extends ", lambdaDefinition/*auto*/).or(lambdaDefinition/*auto*/);
}
auto temp(&[I8] parametersString, &[I8] _) {{
		var name = Strings/*auto*/.strip(rawName/*&[I8]*/);
		var parametersTuple = Main/*auto*/.parseParameters(state/*CompileState*/, parametersString/*&[I8]*/);
		var parameters = Main/*auto*/.retainDefinitionsFromParameters(parametersTuple/*auto*/.right(/*auto*/));
		return Main/*auto*/.compileStructureWithTypeParams(parametersTuple/*auto*/.left(/*auto*/), targetInfix/*&[I8]*/, inputContent/*&[I8]*/, name/*&[I8]*/, parameters/*List<Definition>*/, maybeImplementing/*Option<Type>*/, annotations/*List<&[I8]>*/, modifiers/*List<&[I8]>*/, maybeSuperType/*Option<&[I8]>*/);
	}
}
auto temp(&[I8] rawName, &[I8] withParameters) {
	return Main/*auto*/.compileFirst(withParameters/*auto*/, ")", lambdaDefinition/*auto*/);
}
auto temp() {
	return Main/*auto*/.compileStructureWithTypeParams(state/*CompileState*/, targetInfix/*&[I8]*/, inputContent/*&[I8]*/, beforeContent/*&[I8]*/, Lists/*auto*/.empty(/*auto*/), maybeImplementing/*Option<Type>*/, annotations/*List<&[I8]>*/, modifiers/*List<&[I8]>*/, maybeSuperType/*Option<&[I8]>*/);
}
static Option<Tuple2<CompileState, &[I8]>> compileStructureWithParameters(CompileState state, List<&[I8]> annotations, List<&[I8]> modifiers, &[I8] targetInfix, &[I8] beforeContent, Option<&[I8]> maybeSuperType, Option<Type> maybeImplementing, &[I8] inputContent) {
	return Main/*auto*/.compileFirst(beforeContent/*&[I8]*/, "(", lambdaDefinition/*auto*/).or(lambdaDefinition/*auto*/);
}
auto temp(Parameter parameter) {
	return parameter/*auto*/.asDefinition(/*auto*/);
}
static List<Definition> retainDefinitionsFromParameters(List<Parameter> parameters) {
	return parameters/*List<Parameter>*/.query(/*auto*/).map(lambdaDefinition/*auto*/).flatMap(Queries/*auto*/.fromOption).collect(new ListCollector<Definition>(/*auto*/));
}
auto temp(&[I8] name, &[I8] typeParamsString) {{
		var typeParams = Main/*auto*/.divideValues(typeParamsString/*&[I8]*/);
		return Main/*auto*/.assembleStructure(state/*CompileState*/, annotations/*List<&[I8]>*/, modifiers/*List<&[I8]>*/, infix/*&[I8]*/, name/*&[I8]*/, typeParams/*List<&[I8]>*/, parameters/*List<Definition>*/, maybeImplementing/*Option<Type>*/, content/*&[I8]*/, maybeSuperType/*Option<&[I8]>*/);
	}
}
auto temp(&[I8] withoutTypeParamEnd) {
	return Main/*auto*/.compileFirst(withoutTypeParamEnd/*auto*/, "<", lambdaDefinition/*auto*/);
}
auto temp() {
	return Main/*auto*/.assembleStructure(state/*CompileState*/, annotations/*List<&[I8]>*/, modifiers/*List<&[I8]>*/, infix/*&[I8]*/, beforeParams/*&[I8]*/, Lists/*auto*/.empty(/*auto*/), parameters/*List<Definition>*/, maybeImplementing/*Option<Type>*/, content/*&[I8]*/, maybeSuperType/*Option<&[I8]>*/);
}
static Option<Tuple2<CompileState, &[I8]>> compileStructureWithTypeParams(CompileState state, &[I8] infix, &[I8] content, &[I8] beforeParams, List<Definition> parameters, Option<Type> maybeImplementing, List<&[I8]> annotations, List<&[I8]> modifiers, Option<&[I8]> maybeSuperType) {
	return Main/*auto*/.compileSuffix(Strings/*auto*/.strip(beforeParams/*&[I8]*/), ">", lambdaDefinition/*auto*/).or(lambdaDefinition/*auto*/);
}
auto temp(&[I8] value) {
	return value/*&[I8]*/ + " ";
}
static Option<Tuple2<CompileState, &[I8]>> assembleStructure(CompileState state, List<&[I8]> annotations, List<&[I8]> oldModifiers, &[I8] infix, &[I8] rawName, List<&[I8]> typeParams, List<Definition> parameters, Option<Type> maybeImplementing, &[I8] content, Option<&[I8]> maybeSuperType) {
	var name = Strings/*auto*/.strip(rawName/*&[I8]*/);
	if (!Main/*auto*/.isSymbol(name/*&[I8]*/)) {
		return new None<Tuple2<CompileState, &[I8]>>(/*auto*/);
	}
	var outputContentTuple = Main/*auto*/.compileStatements(state/*CompileState*/.pushStructureName(name/*&[I8]*/), content/*&[I8]*/, Main/*auto*/.compileClassSegment);
	var outputContentState = outputContentTuple/*auto*/.left(/*auto*/).popStructureName(/*auto*/);
	var outputContent = outputContentTuple/*auto*/.right(/*auto*/);
	var platform = outputContentState/*CompileState*/.platform(/*auto*/);
	var constructorString = Main/*auto*/.generateConstructorFromRecordParameters(parameters/*List<Definition>*/, platform/*Platform*/);
	var joinedTypeParams = Joiner/*auto*/.joinOrEmpty(typeParams/*List<&[I8]>*/, ", ", "<", ">");
	var implementingString = Main/*auto*/.generateImplementing(maybeImplementing/*Option<Type>*/);
	var newModifiers = Main/*auto*/.modifyModifiers0(oldModifiers/*List<&[I8]>*/);
	var joinedModifiers = newModifiers/*auto*/.query(/*auto*/).map(lambdaDefinition/*auto*/).collect(Joiner/*auto*/.empty(/*auto*/)).orElse("");
	return Main/*auto*/.getTuple2Some(outputContentState/*CompileState*/.defineType(name/*&[I8]*/), annotations/*List<&[I8]>*/, infix/*&[I8]*/, parameters/*List<Definition>*/, maybeSuperType/*Option<&[I8]>*/, name/*&[I8]*/, joinedModifiers/*&[I8]*/, joinedTypeParams/*&[I8]*/, implementingString/*&[I8]*/, platform/*Platform*/, constructorString/*&[I8]*/, outputContent/*&[I8]*/);
}
auto temp(Location location) {
	return new Location(location/*Location*/.namespace(/*auto*/), location/*Location*/.name(/*auto*/) + "Instance");
}
auto temp(&[I8] inner) {
	return " extends " + inner/*auto*/;
}
static Some<Tuple2<CompileState, &[I8]>> getTuple2Some(CompileState state, List<&[I8]> annotations, &[I8] infix, List<Definition> parameters, Option<&[I8]> maybeSuperType, &[I8] name, &[I8] joinedModifiers, &[I8] joinedTypeParams, &[I8] implementingString, Platform platform, &[I8] constructorString, &[I8] outputContent) {
	if (annotations/*List<&[I8]>*/.contains("Namespace", Strings/*auto*/.equalsTo)) {
		var actualInfix = "interface ";
		var newName = name/*&[I8]*/ + "Instance";
		var generated = joinedModifiers/*&[I8]*/ + actualInfix/*auto*/ + newName/*auto*/ + joinedTypeParams/*&[I8]*/ + implementingString/*&[I8]*/ + " {" + Main/*auto*/.joinParameters(parameters/*List<Definition>*/, platform/*Platform*/) + constructorString/*&[I8]*/ + outputContent/*&[I8]*/ + "\n}\n";
		var withNewLocation = state/*CompileState*/.append(generated/*auto*/).mapLocation(lambdaDefinition/*auto*/);
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(withNewLocation/*auto*/, ""));
	}
	else {
		var extendsString = maybeSuperType/*Option<&[I8]>*/.map(lambdaDefinition/*auto*/).orElse("");
		var infix1 = Main/*auto*/.retainStruct(infix/*&[I8]*/, state/*CompileState*/);
		var generated = joinedModifiers/*&[I8]*/ + infix1/*auto*/ + name/*&[I8]*/ + joinedTypeParams/*&[I8]*/ + extendsString/*auto*/ + implementingString/*&[I8]*/ + " {" + Main/*auto*/.joinParameters(parameters/*List<Definition>*/, platform/*Platform*/) + constructorString/*&[I8]*/ + outputContent/*&[I8]*/ + "\n}\n";
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(state/*CompileState*/.append(generated/*auto*/), ""));
	}
}
static &[I8] retainStruct(&[I8] infix, CompileState outputContentState) {
	if (outputContentState/*CompileState*/.isPlatform(Platform/*auto*/.Magma)) {
		return "struct ";
	}
	return infix/*&[I8]*/;
}
static List<&[I8]> modifyModifiers0(List<&[I8]> oldModifiers) {
	if (oldModifiers/*List<&[I8]>*/.contains("public", Strings/*auto*/.equalsTo)) {
		return Lists/*auto*/.of("export");
	}
	return Lists/*auto*/.empty(/*auto*/);
}
auto temp(Type type) {
	return type/*Type*/.generate(/*auto*/);
}
auto temp(&[I8] inner) {
	return " implements " + inner/*auto*/;
}
static &[I8] generateImplementing(Option<Type> maybeImplementing) {
	return maybeImplementing/*Option<Type>*/.map(lambdaDefinition/*auto*/).map(lambdaDefinition/*auto*/).orElse("");
}
auto temp(Definition definition) {
	return definition/*Definition*/.generate(platform/*Platform*/);
}
auto temp(&[I8] generatedParameters) {
	return Main/*auto*/.generateConstructorWithParameterString(parameters/*List<Definition>*/, generatedParameters/*auto*/);
}
static &[I8] generateConstructorFromRecordParameters(List<Definition> parameters, Platform platform) {
	return parameters/*List<Definition>*/.query(/*auto*/).map(lambdaDefinition/*auto*/).collect(new Joiner(", ")).map(lambdaDefinition/*auto*/).orElse("");
}
static &[I8] generateConstructorWithParameterString(List<Definition> parameters, &[I8] parametersString) {
	var constructorAssignments = Main/*auto*/.generateConstructorAssignments(parameters/*List<Definition>*/);
	return "\n\tconstructor (" + parametersString/*&[I8]*/ + ") {" + constructorAssignments/*auto*/ + "\n\t}";
}
auto temp(Definition definition) {
	return "\n\t\tthis." + definition/*Definition*/.name(/*auto*/) + " = " + definition/*Definition*/.name(/*auto*/) + ";";
}
static &[I8] generateConstructorAssignments(List<Definition> parameters) {
	return parameters/*List<Definition>*/.query(/*auto*/).map(lambdaDefinition/*auto*/).collect(Joiner/*auto*/.empty(/*auto*/)).orElse("");
}
auto temp(Definition definition) {
	return definition/*Definition*/.generate(platform/*Platform*/);
}
auto temp(&[I8] generated) {
	return "\n\t" + generated/*auto*/ + ";";
}
static &[I8] joinParameters(List<Definition> parameters, Platform platform) {
	return parameters/*List<Definition>*/.query(/*auto*/).map(lambdaDefinition/*auto*/).map(lambdaDefinition/*auto*/).collect(Joiner/*auto*/.empty(/*auto*/)).orElse("");
}
static Option<Tuple2<CompileState, &[I8]>> compileNamespaced(CompileState state, &[I8] input) {
	var stripped = Strings/*auto*/.strip(input/*&[I8]*/);
	if (stripped/*auto*/.startsWith("package ") || stripped/*auto*/.startsWith("import ")) {
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(state/*CompileState*/, ""));
	}
	return new None<>(/*auto*/);
}
auto temp() {
	return new Tuple2Impl<CompileState, &[I8]>(state/*CompileState*/, Main/*auto*/.generatePlaceholder(input/*&[I8]*/));
}
static Tuple2<CompileState, &[I8]> compileOrPlaceholder(CompileState state, &[I8] input, List<(arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, &[I8]>>> rules) {
	return Main/*auto*/.or(state/*CompileState*/, input/*&[I8]*/, rules/*List<(arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, &[I8]>>>*/).orElseGet(lambdaDefinition/*auto*/);
}
auto temp((arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, T>> rule) {
	return Main/*auto*/.getApply(state/*CompileState*/, input/*&[I8]*/, rule/*(arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, T>>*/);
}
static Option<Tuple2<CompileState, T>> or(CompileState state, &[I8] input, List<(arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, T>>> rules) {
	return rules/*List<(arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, T>>>*/.query(/*auto*/).map(lambdaDefinition/*auto*/).flatMap(Queries/*auto*/.fromOption).next(/*auto*/);
}
static Option<Tuple2<CompileState, T>> getApply(CompileState state, &[I8] input, (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, T>> rule) {
	return rule/*(arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, T>>*/(state/*CompileState*/, input/*&[I8]*/);
}
static Tuple2<CompileState, &[I8]> compileClassSegment(CompileState state1, &[I8] input1) {
	return Main/*auto*/.compileOrPlaceholder(state1/*CompileState*/, input1/*&[I8]*/, Lists/*auto*/.of(Main/*auto*/.compileWhitespace, Main/*auto*/.createStructureRule("class ", "class "), Main/*auto*/.createStructureRule("interface ", "interface "), Main/*auto*/.createStructureRule("record ", "class "), Main/*auto*/.createStructureRule("enum ", "class "), Main/*auto*/.compileMethod, Main/*auto*/.compileFieldDefinition));
}
auto temp(&[I8] _, &[I8] name) {{
			if (state/*CompileState*/.hasLastStructureNameOf(name/*&[I8]*/)) {
				return Main/*auto*/.compileMethodWithBeforeParams(state/*CompileState*/, new ConstructorHeader(/*auto*/), withParams/*&[I8]*/);
			}
			return new None<Tuple2<CompileState, &[I8]>>(/*auto*/);
		}
}
auto temp() {{
			if (state/*CompileState*/.hasLastStructureNameOf(strippedBeforeParams/*auto*/)) {
				return Main/*auto*/.compileMethodWithBeforeParams(state/*CompileState*/, new ConstructorHeader(/*auto*/), withParams/*&[I8]*/);
			}
			return new None<Tuple2<CompileState, &[I8]>>(/*auto*/);
		}
}
auto temp(Tuple2<CompileState, Definition> tuple) {
	return Main/*auto*/.compileMethodWithBeforeParams(tuple/*Tuple2<DivideState, I8>*/.left(/*auto*/), tuple/*Tuple2<DivideState, I8>*/.right(/*auto*/), withParams/*&[I8]*/);
}
auto temp() {
	return Main/*auto*/.parseDefinition(state/*CompileState*/, beforeParams/*&[I8]*/).flatMap(lambdaDefinition/*auto*/);
}
auto temp(&[I8] beforeParams, &[I8] withParams) {{
		var strippedBeforeParams = Strings/*auto*/.strip(beforeParams/*&[I8]*/);
		return Main/*auto*/.compileLast(strippedBeforeParams/*auto*/, " ", lambdaDefinition/*auto*/).or(lambdaDefinition/*auto*/).or(lambdaDefinition/*auto*/);
	}
}
static Option<Tuple2<CompileState, &[I8]>> compileMethod(CompileState state, &[I8] input) {
	return Main/*auto*/.compileFirst(input/*&[I8]*/, "(", lambdaDefinition/*auto*/);
}
auto temp(&[I8] withoutContentEnd) {{
			var compileState1 = parametersState/*CompileState*/.enterDepth(/*auto*/);
			var compileState = /* compileState1.isPlatform(Platform.Windows) ? compileState1 : compileState1.enterDepth()*/;
			var statementsTuple = Main/*auto*/.compileFunctionStatements(compileState/*auto*/.defineAll(definitions/*List<Definition>*/), withoutContentEnd/*&[I8]*/);
			var compileState2 = statementsTuple/*auto*/.left(/*auto*/).exitDepth(/*auto*/);
			var indent = compileState2/*auto*/.createIndent(/*auto*/);
			var exited = /* compileState2.isPlatform(Platform.Windows) ? compileState2 : compileState2.exitDepth()*/;
			var sFunctionSegment = new FunctionSegment<S>(newHeader/*auto*/, definitions/*List<Definition>*/, new Some<>(statementsTuple/*auto*/.right(/*auto*/)));
			var generated = sFunctionSegment/*auto*/.generate(parametersState/*CompileState*/.platform(/*auto*/), indent/*&[I8]*/);
			if (exited/*auto*/.isPlatform(Platform/*auto*/.Windows)) {
				return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(exited/*auto*/.addFunction(generated/*auto*/), ""));
			}
			return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(exited/*auto*/, generated/*auto*/));
		}
}
auto temp(&[I8] withoutContentStart) {
	return Main/*auto*/.compileSuffix(Strings/*auto*/.strip(withoutContentStart/*auto*/), "}", lambdaDefinition/*auto*/);
}
auto temp() {{
			if (Strings/*auto*/.equalsTo(";", Strings/*auto*/.strip(afterParams/*&[I8]*/))) {
				var sFunctionSegment = new FunctionSegment<S>(newHeader/*auto*/, definitions/*List<Definition>*/, new None<>(/*auto*/));
				var generate = sFunctionSegment/*auto*/.generate(parametersState/*CompileState*/.platform(/*auto*/), "\n\t");
				return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(parametersState/*CompileState*/, generate/*auto*/));
			}
			return new None<Tuple2<CompileState, &[I8]>>(/*auto*/);
		}
}
auto temp(&[I8] params, &[I8] afterParams) {{
		var parametersTuple = Main/*auto*/.parseParameters(state/*CompileState*/, params/*&[I8]*/);
		var parametersState = parametersTuple/*auto*/.left(/*auto*/);
		var parameters = parametersTuple/*auto*/.right(/*auto*/);
		var definitions = Main/*auto*/.retainDefinitionsFromParameters(parameters/*List<Definition>*/);
		var newHeader = Main/*auto*/.retainDef(header/*FunctionHeader<S>*/, parametersState/*CompileState*/);
		if (newHeader/*auto*/.hasAnnotation("Actual")) {
			var sFunctionSegment = new FunctionSegment<S>(newHeader/*auto*/, definitions/*List<Definition>*/, new None<>(/*auto*/));
			var generate = sFunctionSegment/*auto*/.generate(parametersState/*CompileState*/.platform(/*auto*/), "\n\t");
			return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(parametersState/*CompileState*/, generate/*auto*/));
		}
		return Main/*auto*/.compilePrefix(Strings/*auto*/.strip(afterParams/*&[I8]*/), "{", lambdaDefinition/*auto*/).or(lambdaDefinition/*auto*/);
	}
}
static Option<Tuple2<CompileState, &[I8]>> compileMethodWithBeforeParams(CompileState state, FunctionHeader<S> header, &[I8] withParams) {
	return Main/*auto*/.compileFirst(withParams/*&[I8]*/, ")", lambdaDefinition/*auto*/);
}
static FunctionHeader<S> retainDef(FunctionHeader<S> header, CompileState parametersState) {
	if (parametersState/*CompileState*/.isPlatform(Platform/*auto*/.Magma)) {
		return header/*FunctionHeader<S>*/.addModifierLast("def").removeModifier("mut");
	}
	return header/*FunctionHeader<S>*/;
}
auto temp(CompileState state1, &[I8] s) {
	return new Some<Tuple2<CompileState, Parameter>>(Main/*auto*/.parseParameterOrPlaceholder(state1/*CompileState*/, s/*&[I8]*/));
}
static Tuple2<CompileState, List<Parameter>> parseParameters(CompileState state, &[I8] params) {
	return Main/*auto*/.parseValuesOrEmpty(state/*CompileState*/, params/*&[I8]*/, lambdaDefinition/*auto*/);
}
static Tuple2<CompileState, &[I8]> compileFunctionStatements(CompileState state, &[I8] input) {
	return Main/*auto*/.compileStatements(state/*CompileState*/, input/*&[I8]*/, Main/*auto*/.compileFunctionSegment);
}
static Tuple2<CompileState, &[I8]> compileFunctionSegment(CompileState state, &[I8] input) {
	return Main/*auto*/.compileOrPlaceholder(state/*CompileState*/, input/*&[I8]*/, Lists/*auto*/.of(Main/*auto*/.compileWhitespace, Main/*auto*/.compileEmptySegment, Main/*auto*/.compileBlock, Main/*auto*/.compileFunctionStatement, Main/*auto*/.compileReturnWithoutSuffix));
}
static Option<Tuple2<CompileState, &[I8]>> compileEmptySegment(CompileState state, &[I8] input) {
	if (Strings/*auto*/.equalsTo(";", Strings/*auto*/.strip(input/*&[I8]*/))) {
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(state/*CompileState*/, ";"));
	}
	else {
		return new None<Tuple2<CompileState, &[I8]>>(/*auto*/);
	}
}
auto temp(&[I8] withoutPrefix) {
	return Main/*auto*/.compileValue(state1/*CompileState*/, withoutPrefix/*&[I8]*/);
}
auto temp(Tuple2<CompileState, &[I8]> tuple) {
	return new Tuple2Impl<CompileState, &[I8]>(tuple/*Tuple2<DivideState, I8>*/.left(/*auto*/), state1/*CompileState*/.createIndent(/*auto*/) + tuple/*Tuple2<DivideState, I8>*/.right(/*auto*/));
}
static Option<Tuple2<CompileState, &[I8]>> compileReturnWithoutSuffix(CompileState state1, &[I8] input1) {
	return Main/*auto*/.compileReturn(input1/*&[I8]*/, lambdaDefinition/*auto*/).map(lambdaDefinition/*auto*/);
}
auto temp(Tuple2<CompileState, &[I8]> headerTuple) {{
		var contentTuple = Main/*auto*/.compileFunctionStatements(headerTuple/*Tuple2<CompileState, &[I8]>*/.left(/*auto*/).enterDepth(/*auto*/), content/*&[I8]*/);
		var indent = state/*CompileState*/.createIndent(/*auto*/);
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(contentTuple/*auto*/.left(/*auto*/).exitDepth(/*auto*/), indent/*&[I8]*/ + headerTuple/*Tuple2<CompileState, &[I8]>*/.right(/*auto*/) + "{" + contentTuple/*auto*/.right(/*auto*/) + indent/*&[I8]*/ + "}"));
	}
}
auto temp(&[I8] beforeContent) {
	return Main/*auto*/.compileBlockHeader(state/*CompileState*/, beforeContent/*&[I8]*/).flatMap(lambdaDefinition/*auto*/);
}
auto temp(&[I8] beforeContentWithEnd, &[I8] content) {
	return Main/*auto*/.compileSuffix(beforeContentWithEnd/*auto*/, "{", lambdaDefinition/*auto*/);
}
auto temp(&[I8] withoutEnd) {
	return Main/*auto*/.compileSplit(Main/*auto*/.splitFoldedLast(withoutEnd/*&[I8]*/, "", Main/*auto*/.foldBlockStarts), lambdaDefinition/*auto*/);
}
static Option<Tuple2<CompileState, &[I8]>> compileBlock(CompileState state, &[I8] input) {
	return Main/*auto*/.compileSuffix(Strings/*auto*/.strip(input/*&[I8]*/), "}", lambdaDefinition/*auto*/);
}
static DivideState foldBlockStarts(DivideState state, I8 c) {
	var appended = state/*DivideState*/.append(c/*I8*/);
	if ("{" === c/*I8*/) {
		var entered = appended/*auto*/.enter(/*auto*/);
		if (entered/*auto*/.isShallow(/*auto*/)) {
			return entered/*auto*/.advance(/*auto*/);
		}
		else {
			return entered/*auto*/;
		}
	}
	if ("}" === c/*I8*/) {
		return appended/*auto*/.exit(/*auto*/);
	}
	return appended/*auto*/;
}
static Option<Tuple2<CompileState, &[I8]>> compileBlockHeader(CompileState state, &[I8] input) {
	return Main/*auto*/.or(state/*CompileState*/, input/*&[I8]*/, Lists/*auto*/.of(Main/*auto*/.createConditionalRule("if"), Main/*auto*/.createConditionalRule("while"), Main/*auto*/.compileElse));
}
auto temp(&[I8] withoutConditionEnd) {{
			var tuple = Main/*auto*/.compileValueOrPlaceholder(state1/*CompileState*/, withoutConditionEnd/*&[I8]*/);
			return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(tuple/*Tuple2<DivideState, I8>*/.left(/*auto*/), prefix/*&[I8]*/ + " (" + tuple/*Tuple2<DivideState, I8>*/.right(/*auto*/) + ") "));
		}
}
auto temp(&[I8] withoutConditionStart) {
	return Main/*auto*/.compileSuffix(withoutConditionStart/*auto*/, ")", lambdaDefinition/*auto*/);
}
auto temp(&[I8] withoutPrefix) {{
		var strippedCondition = Strings/*auto*/.strip(withoutPrefix/*&[I8]*/);
		return Main/*auto*/.compilePrefix(strippedCondition/*auto*/, "(", lambdaDefinition/*auto*/);
	}
}
auto temp(CompileState state1, &[I8] input1) {
	return Main/*auto*/.compilePrefix(Strings/*auto*/.strip(input1/*&[I8]*/), prefix/*&[I8]*/, lambdaDefinition/*auto*/);
}
static (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, &[I8]>> createConditionalRule(&[I8] prefix) {
	return lambdaDefinition/*auto*/;
}
static Option<Tuple2<CompileState, &[I8]>> compileElse(CompileState state, &[I8] input) {
	if (Strings/*auto*/.equalsTo("else", Strings/*auto*/.strip(input/*&[I8]*/))) {
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(state/*CompileState*/, "else "));
	}
	else {
		return new None<Tuple2<CompileState, &[I8]>>(/*auto*/);
	}
}
auto temp(&[I8] withoutEnd) {{
		var valueTuple = Main/*auto*/.compileFunctionStatementValue(state/*CompileState*/, withoutEnd/*&[I8]*/);
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(valueTuple/*auto*/.left(/*auto*/), state/*CompileState*/.createIndent(/*auto*/) + valueTuple/*auto*/.right(/*auto*/) + ";"));
	}
}
static Option<Tuple2<CompileState, &[I8]>> compileFunctionStatement(CompileState state, &[I8] input) {
	return Main/*auto*/.compileSuffix(Strings/*auto*/.strip(input/*&[I8]*/), ";", lambdaDefinition/*auto*/);
}
auto temp(Tuple2<CompileState, Value> tuple) {
	return new Tuple2Impl<CompileState, &[I8]>(tuple/*Tuple2<DivideState, I8>*/.left(/*auto*/), tuple/*Tuple2<DivideState, I8>*/.right(/*auto*/).generate(tuple/*Tuple2<DivideState, I8>*/.left(/*auto*/).platform(/*auto*/)));
}
auto temp(CompileState state1, &[I8] input) {
	return Main/*auto*/.parseInvokable(state1/*CompileState*/, input/*&[I8]*/).map(lambdaDefinition/*auto*/);
}
static Tuple2<CompileState, &[I8]> compileFunctionStatementValue(CompileState state, &[I8] withoutEnd) {
	return Main/*auto*/.compileOrPlaceholder(state/*CompileState*/, withoutEnd/*&[I8]*/, Lists/*auto*/.of(Main/*auto*/.compileReturnWithValue, Main/*auto*/.compileAssignment, lambdaDefinition/*auto*/, Main/*auto*/.createPostRule("++"), Main/*auto*/.createPostRule("--"), Main/*auto*/.compileBreak));
}
static Option<Tuple2<CompileState, &[I8]>> compileBreak(CompileState state, &[I8] input) {
	if (Strings/*auto*/.equalsTo("break", Strings/*auto*/.strip(input/*&[I8]*/))) {
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(state/*CompileState*/, "break"));
	}
	else {
		return new None<Tuple2<CompileState, &[I8]>>(/*auto*/);
	}
}
auto temp(&[I8] child) {{
		var tuple = Main/*auto*/.compileValueOrPlaceholder(state1/*CompileState*/, child/*&[I8]*/);
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(tuple/*Tuple2<DivideState, I8>*/.left(/*auto*/), tuple/*Tuple2<DivideState, I8>*/.right(/*auto*/) + suffix/*&[I8]*/));
	}
}
auto temp(CompileState state1, &[I8] input) {
	return Main/*auto*/.compileSuffix(Strings/*auto*/.strip(input/*&[I8]*/), suffix/*&[I8]*/, lambdaDefinition/*auto*/);
}
static (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, &[I8]>> createPostRule(&[I8] suffix) {
	return lambdaDefinition/*auto*/;
}
auto temp(&[I8] value1) {
	return Main/*auto*/.compileValue(state/*CompileState*/, value1/*auto*/);
}
static Option<Tuple2<CompileState, &[I8]>> compileReturnWithValue(CompileState state, &[I8] input) {
	return Main/*auto*/.compileReturn(input/*&[I8]*/, lambdaDefinition/*auto*/);
}
auto temp(Tuple2<CompileState, &[I8]> tuple) {
	return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(tuple/*Tuple2<DivideState, I8>*/.left(/*auto*/), "return " + tuple/*Tuple2<DivideState, I8>*/.right(/*auto*/)));
}
auto temp(&[I8] value) {
	return mapper/*(arg0 : &[I8]) => Option<Tuple2<CompileState, &[I8]>>*/(value/*&[I8]*/).flatMap(lambdaDefinition/*auto*/);
}
static Option<Tuple2<CompileState, &[I8]>> compileReturn(&[I8] input, (arg0 : &[I8]) => Option<Tuple2<CompileState, &[I8]>> mapper) {
	return Main/*auto*/.compilePrefix(Strings/*auto*/.strip(input/*&[I8]*/), "return ", lambdaDefinition/*auto*/);
}
auto temp(Tuple2<CompileState, &[I8]> callerTuple) {{
		var callerState = callerTuple/*Tuple2<CompileState, &[I8]>*/.left(/*auto*/);
		var caller = callerTuple/*Tuple2<CompileState, &[I8]>*/.right(/*auto*/);
		return Main/*auto*/.assembleInvokable(callerState/*auto*/, new ConstructionCaller(caller/*auto*/, callerState/*auto*/.platform(/*auto*/)), args/*List<&[I8]>*/);
	}
}
auto temp(&[I8] type) {
	return Main/*auto*/.compileType(state/*CompileState*/, type/*Type*/).flatMap(lambdaDefinition/*auto*/);
}
auto temp(Tuple2<CompileState, Value> callerTuple) {
	return Main/*auto*/.assembleInvokable(callerTuple/*Tuple2<CompileState, &[I8]>*/.left(/*auto*/), callerTuple/*Tuple2<CompileState, &[I8]>*/.right(/*auto*/), args/*List<&[I8]>*/);
}
auto temp() {
	return Main/*auto*/.parseValue(state/*CompileState*/, callerString/*auto*/).flatMap(lambdaDefinition/*auto*/);
}
auto temp(&[I8] callerString) {
	return Main/*auto*/.compilePrefix(Strings/*auto*/.strip(callerString/*auto*/), "new ", lambdaDefinition/*auto*/).or(lambdaDefinition/*auto*/);
}
auto temp(&[I8] callerWithArgStart, &[I8] args) {
	return Main/*auto*/.compileSuffix(callerWithArgStart/*auto*/, "(", lambdaDefinition/*auto*/);
}
auto temp(&[I8] withoutEnd) {
	return Main/*auto*/.compileSplit(Main/*auto*/.splitFoldedLast(withoutEnd/*&[I8]*/, "", Main/*auto*/.foldInvocationStarts), lambdaDefinition/*auto*/);
}
static Option<Tuple2<CompileState, Value>> parseInvokable(CompileState state, &[I8] input) {
	return Main/*auto*/.compileSuffix(Strings/*auto*/.strip(input/*&[I8]*/), ")", lambdaDefinition/*auto*/);
}
auto temp(List<&[I8]> divisions1) {
	return Main/*auto*/.selectLast(divisions1/*auto*/, delimiter/*&[I8]*/);
}
static Option<Tuple2<&[I8], &[I8]>> splitFoldedLast(&[I8] input, &[I8] delimiter, (arg0 : DivideState, arg1 : I8) => DivideState folder) {
	return Main/*auto*/.splitFolded(input/*&[I8]*/, folder/*(arg0 : DivideState, arg1 : I8) => DivideState*/, lambdaDefinition/*auto*/);
}
static Option<Tuple2<&[I8], &[I8]>> splitFolded(&[I8] input, (arg0 : DivideState, arg1 : I8) => DivideState folder, (arg0 : List<&[I8]>) => Option<Tuple2<&[I8], &[I8]>> selector) {
	var divisions = Main/*auto*/.divide(input/*&[I8]*/, folder/*(arg0 : DivideState, arg1 : I8) => DivideState*/);
	if (2/*auto*/ > divisions/*List<&[I8]>*/.size(/*auto*/)) {
		return new None<Tuple2<&[I8], &[I8]>>(/*auto*/);
	}
	return selector/*(arg0 : List<&[I8]>) => Option<Tuple2<&[I8], &[I8]>>*/(divisions/*List<&[I8]>*/);
}
static Option<Tuple2<&[I8], &[I8]>> selectLast(List<&[I8]> divisions, &[I8] delimiter) {
	var beforeLast = divisions/*List<&[I8]>*/.subList(0/*auto*/, divisions/*List<&[I8]>*/.size(/*auto*/) - 1/*auto*/).orElse(divisions/*List<&[I8]>*/);
	var last = divisions/*List<&[I8]>*/.findLast(/*auto*/).orElse("");
	var joined = beforeLast/*auto*/.query(/*auto*/).collect(new Joiner(delimiter/*&[I8]*/)).orElse("");
	return new Some<Tuple2<&[I8], &[I8]>>(new Tuple2Impl<&[I8], &[I8]>(joined/*auto*/, last/*auto*/));
}
static DivideState foldInvocationStarts(DivideState state, I8 c) {
	var appended = state/*DivideState*/.append(c/*I8*/);
	if ("(" === c/*I8*/) {
		var entered = appended/*auto*/.enter(/*auto*/);
		if (entered/*auto*/.isShallow(/*auto*/)) {
			return entered/*auto*/.advance(/*auto*/);
		}
		else {
			return entered/*auto*/;
		}
	}
	if (")" === c/*I8*/) {
		return appended/*auto*/.exit(/*auto*/);
	}
	return appended/*auto*/;
}
auto temp(CompileState state1, &[I8] s) {
	return Main/*auto*/.parseArgument(state1/*CompileState*/, s/*&[I8]*/);
}
auto temp(Argument argument) {
	return argument/*auto*/.toValue(/*auto*/);
}
auto temp(Tuple2<CompileState, List<Argument>> argsTuple) {{
		var argsState = argsTuple/*Tuple2<CompileState, List<Argument>>*/.left(/*auto*/);
		var args = Main/*auto*/.retain(argsTuple/*Tuple2<CompileState, List<Argument>>*/.right(/*auto*/), lambdaDefinition/*auto*/);
		var newCaller = Main/*auto*/.transformCaller(argsState/*auto*/, oldCaller/*Caller*/);
		return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(argsState/*auto*/, new InvokableNode(newCaller/*auto*/, args/*List<&[I8]>*/)));
	}
}
static Option<Tuple2<CompileState, Value>> assembleInvokable(CompileState state, Caller oldCaller, &[I8] argsString) {
	return Main/*auto*/.parseValues(state/*CompileState*/, argsString/*&[I8]*/, lambdaDefinition/*auto*/).flatMap(lambdaDefinition/*auto*/);
}
auto temp(Value parent) {{
		var parentType = parent/*Value*/.type(/*auto*/);
		if (parentType/*auto*/.isFunctional(/*auto*/)) {
			return new Some<Caller>(parent/*Value*/);
		}
		return new None<Caller>(/*auto*/);
	}
}
static Caller transformCaller(CompileState state, Caller oldCaller) {
	return oldCaller/*Caller*/.findChild(/*auto*/).flatMap(lambdaDefinition/*auto*/).orElse(oldCaller/*Caller*/);
}
static List<R> retain(List<T> args, (arg0 : T) => Option<R> mapper) {
	return args/*List<T>*/.query(/*auto*/).map(mapper/*(arg0 : T) => Option<R>*/).flatMap(Queries/*auto*/.fromOption).collect(new ListCollector<R>(/*auto*/));
}
auto temp(Tuple2<CompileState, Value> tuple) {
	return new Tuple2Impl<CompileState, Argument>(tuple/*Tuple2<DivideState, I8>*/.left(/*auto*/), tuple/*Tuple2<DivideState, I8>*/.right(/*auto*/));
}
static Option<Tuple2<CompileState, Argument>> parseArgument(CompileState state1, &[I8] input) {
	return Main/*auto*/.parseValue(state1/*CompileState*/, input/*&[I8]*/).map(lambdaDefinition/*auto*/);
}
auto temp(Tuple2<CompileState, Definition> definitionTuple) {{
			var definitionState = definitionTuple/*Tuple2<CompileState, Definition>*/.left(/*auto*/);
			var definition = definitionTuple/*Tuple2<CompileState, Definition>*/.right(/*auto*/);
			var let = Main/*auto*/.attachLet(definitionState/*CompileState*/, definition/*Definition*/);
			var generate = let/*auto*/.generate(definitionState/*CompileState*/.platform(/*auto*/));
			return new Tuple2Impl<CompileState, &[I8]>(definitionState/*CompileState*/, generate/*auto*/);
		}
}
auto temp() {
	return Main/*auto*/.parseDefinition(sourceTuple/*auto*/.left(/*auto*/), destination/*&[I8]*/).map(lambdaDefinition/*auto*/);
}
auto temp() {
	return new Tuple2Impl<CompileState, &[I8]>(sourceTuple/*auto*/.left(/*auto*/), Main/*auto*/.generatePlaceholder(destination/*&[I8]*/));
}
auto temp(&[I8] destination, &[I8] source) {{
		var sourceTuple = Main/*auto*/.compileValueOrPlaceholder(state/*CompileState*/, source/*&[I8]*/);
		var destinationTuple = Main/*auto*/.compileValue(sourceTuple/*auto*/.left(/*auto*/), destination/*&[I8]*/).or(lambdaDefinition/*auto*/).orElseGet(lambdaDefinition/*auto*/);
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(destinationTuple/*auto*/.left(/*auto*/), destinationTuple/*auto*/.right(/*auto*/) + " = " + sourceTuple/*auto*/.right(/*auto*/)));
	}
}
static Option<Tuple2<CompileState, &[I8]>> compileAssignment(CompileState state, &[I8] input) {
	return Main/*auto*/.compileFirst(input/*&[I8]*/, "=", lambdaDefinition/*auto*/);
}
static Definition attachLet(CompileState definitionState, Definition definition) {
	/*final Definition let*/;
	if (definitionState/*CompileState*/.isPlatform(Platform/*auto*/.Windows)) {
		let/*auto*/ = definition/*Definition*/;
	}
	else {
		let/*auto*/ = definition/*Definition*/.addModifierLast("let");
	}
	return let/*auto*/;
}
auto temp() {
	return new Tuple2Impl<CompileState, &[I8]>(state/*CompileState*/, Main/*auto*/.generatePlaceholder(input/*&[I8]*/));
}
static Tuple2<CompileState, &[I8]> compileValueOrPlaceholder(CompileState state, &[I8] input) {
	return Main/*auto*/.compileValue(state/*CompileState*/, input/*&[I8]*/).orElseGet(lambdaDefinition/*auto*/);
}
auto temp(Tuple2<CompileState, Value> tuple) {{
		var generated = tuple/*Tuple2<CompileState, Value>*/.right(/*auto*/).generate(tuple/*Tuple2<CompileState, Value>*/.left(/*auto*/).platform(/*auto*/));
		return new Tuple2Impl<CompileState, &[I8]>(tuple/*Tuple2<CompileState, Value>*/.left(/*auto*/), generated/*auto*/);
	}
}
static Option<Tuple2<CompileState, &[I8]>> compileValue(CompileState state, &[I8] input) {
	return Main/*auto*/.parseValue(state/*CompileState*/, input/*&[I8]*/).map(lambdaDefinition/*auto*/);
}
static Option<Tuple2<CompileState, Value>> parseValue(CompileState state, &[I8] input) {
	return Main/*auto*/.or(state/*CompileState*/, input/*&[I8]*/, Lists/*auto*/.of(Main/*auto*/.parseLambda, Main/*auto*/.createOperatorRule("+"), Main/*auto*/.createOperatorRule("-"), Main/*auto*/.createOperatorRule("<="), Main/*auto*/.createOperatorRule("<"), Main/*auto*/.createOperatorRule("&&"), Main/*auto*/.createOperatorRule("||"), Main/*auto*/.createOperatorRule(">"), Main/*auto*/.createOperatorRule(">="), Main/*auto*/.parseInvokable, Main/*auto*/.createAccessRule("."), Main/*auto*/.createAccessRule("::"), Main/*auto*/.parseSymbol, Main/*auto*/.parseNot, Main/*auto*/.parseNumber, Main/*auto*/.createOperatorRuleWithDifferentInfix("==", "==="), Main/*auto*/.createOperatorRuleWithDifferentInfix("!=", "!=="), Main/*auto*/.createTextRule("\""), Main/*auto*/.createTextRule("'")));
}
auto temp(&[I8] s1) {
	return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state1/*CompileState*/, new StringNode(s1/*auto*/)));
}
auto temp(&[I8] s) {
	return Main/*auto*/.compileSuffix(s/*&[I8]*/, slice/*&[I8]*/, lambdaDefinition/*auto*/);
}
auto temp(CompileState state1, &[I8] input1) {{
		var stripped = Strings/*auto*/.strip(input1/*&[I8]*/);
		return Main/*auto*/.compilePrefix(stripped/*auto*/, slice/*&[I8]*/, lambdaDefinition/*auto*/);
	}
}
static (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, Value>> createTextRule(&[I8] slice) {
	return lambdaDefinition/*auto*/;
}
auto temp(&[I8] withoutPrefix) {{
		var childTuple = Main/*auto*/.compileValueOrPlaceholder(state/*CompileState*/, withoutPrefix/*&[I8]*/);
		var childState = childTuple/*Tuple2<CompileState, Value>*/.left(/*auto*/);
		var child = "!" + childTuple/*Tuple2<CompileState, Value>*/.right(/*auto*/);
		return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(childState/*auto*/, new NotNode(child/*&[I8]*/)));
	}
}
static Option<Tuple2<CompileState, Value>> parseNot(CompileState state, &[I8] input) {
	return Main/*auto*/.compilePrefix(Strings/*auto*/.strip(input/*&[I8]*/), "!", lambdaDefinition/*auto*/);
}
auto temp(CompileState state1, &[I8] s) {
	return Main/*auto*/.parseParameter(state1/*CompileState*/, s/*&[I8]*/);
}
auto temp(Tuple2<CompileState, List<Parameter>> paramNames) {
	return Main/*auto*/.compileLambdaWithParameterNames(paramNames/*List<Definition>*/.left(/*auto*/), Main/*auto*/.retainDefinitionsFromParameters(paramNames/*List<Definition>*/.right(/*auto*/)), afterArrow/*&[I8]*/);
}
auto temp(&[I8] withoutEnd) {
	return Main/*auto*/.parseValues(state/*CompileState*/, withoutEnd/*&[I8]*/, lambdaDefinition/*auto*/).flatMap(lambdaDefinition/*auto*/);
}
auto temp(&[I8] withoutStart) {
	return Main/*auto*/.compileSuffix(withoutStart/*auto*/, ")", lambdaDefinition/*auto*/);
}
auto temp(&[I8] beforeArrow, &[I8] afterArrow) {{
		var strippedBeforeArrow = Strings/*auto*/.strip(beforeArrow/*&[I8]*/);
		return Main/*auto*/.compilePrefix(strippedBeforeArrow/*auto*/, "(", lambdaDefinition/*auto*/);
	}
}
static Option<Tuple2<CompileState, Value>> parseLambda(CompileState state, &[I8] input) {
	return Main/*auto*/.compileFirst(input/*&[I8]*/, "->", lambdaDefinition/*auto*/);
}
auto temp(&[I8] withoutContentEnd) {{
		var statementsTuple = Main/*auto*/.compileFunctionStatements(state/*CompileState*/.enterDepth(/*auto*/).defineAll(paramNames/*List<Definition>*/), withoutContentEnd/*&[I8]*/);
		var statementsState = statementsTuple/*auto*/.left(/*auto*/);
		var statements = statementsTuple/*auto*/.right(/*auto*/);
		var exited = statementsState/*CompileState*/.exitDepth(/*auto*/);
		var content = "{" + statements/*auto*/ + exited/*auto*/.createIndent(/*auto*/) + "}";
		if (exited/*auto*/.isPlatform(Platform/*auto*/.Windows)) {
			return Main/*auto*/.assembleLambdaWithContent(exited/*auto*/, paramNames/*List<Definition>*/, content/*&[I8]*/);
		}
		return Main/*auto*/.getSome(exited/*auto*/, paramNames/*List<Definition>*/, content/*&[I8]*/);
	}
}
auto temp(&[I8] withoutContentStart) {
	return Main/*auto*/.compileSuffix(withoutContentStart/*auto*/, "}", lambdaDefinition/*auto*/);
}
auto temp(Tuple2<CompileState, &[I8]> tuple) {{
		var state1 = tuple/*Tuple2<CompileState, &[I8]>*/.left(/*auto*/);
		var content = tuple/*Tuple2<CompileState, &[I8]>*/.right(/*auto*/);
		if (state1/*CompileState*/.isPlatform(Platform/*auto*/.Windows)) {
			return Main/*auto*/.assembleLambdaWithContent(state1/*CompileState*/, paramNames/*List<Definition>*/, "\n\treturn " + content/*&[I8]*/ + ";");
		}
		return Main/*auto*/.getSome(state1/*CompileState*/, paramNames/*List<Definition>*/, content/*&[I8]*/);
	}
}
auto temp() {
	return Main/*auto*/.compileValue(state/*CompileState*/, strippedAfterArrow/*auto*/).flatMap(lambdaDefinition/*auto*/);
}
static Option<Tuple2<CompileState, Value>> compileLambdaWithParameterNames(CompileState state, List<Definition> paramNames, &[I8] afterArrow) {
	var strippedAfterArrow = Strings/*auto*/.strip(afterArrow/*&[I8]*/);
	return Main/*auto*/.compilePrefix(strippedAfterArrow/*auto*/, "{", lambdaDefinition/*auto*/).or(lambdaDefinition/*auto*/);
}
static Some<Tuple2<CompileState, Value>> getSome(CompileState state, List<Definition> parameters, &[I8] content) {
	return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state/*CompileState*/, new LambdaNode(parameters/*List<Definition>*/, content/*&[I8]*/)));
}
static Some<Tuple2<CompileState, Value>> assembleLambdaWithContent(CompileState state, List<Definition> parameters, &[I8] content) {
	var lambdaDefinition = new Definition(PrimitiveType/*auto*/.Auto, state/*CompileState*/.functionName(/*auto*/));
	var value = new FunctionSegment<Definition>(lambdaDefinition/*auto*/, parameters/*List<Definition>*/, new Some<>(content/*&[I8]*/));
	return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state/*CompileState*/.addFunction(value/*&[I8]*/.generate(state/*CompileState*/.platform(/*auto*/), "\n")), new SymbolNode("lambdaDefinition", PrimitiveType/*auto*/.Auto)));
}
static (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, Value>> createOperatorRule(&[I8] infix) {
	return Main/*auto*/.createOperatorRuleWithDifferentInfix(infix/*&[I8]*/, infix/*&[I8]*/);
}
auto temp(Tuple2<CompileState, Value> childTuple) {{
			var childState = childTuple/*Tuple2<CompileState, Value>*/.left(/*auto*/);
			var child = childTuple/*Tuple2<CompileState, Value>*/.right(/*auto*/);
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(childState/*auto*/, new AccessNode(child/*&[I8]*/, property/*auto*/)));
		}
}
auto temp(&[I8] childString, &[I8] rawProperty) {{
		var property = Strings/*auto*/.strip(rawProperty/*&[I8]*/);
		if (!Main/*auto*/.isSymbol(property/*auto*/)) {
			return new None<Tuple2<CompileState, Value>>(/*auto*/);
		}
		return Main/*auto*/.parseValue(state/*CompileState*/, childString/*&[I8]*/).flatMap(lambdaDefinition/*auto*/);
	}
}
auto temp(CompileState state, &[I8] input) {
	return Main/*auto*/.compileLast(input/*&[I8]*/, infix/*&[I8]*/, lambdaDefinition/*auto*/);
}
static (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, Value>> createAccessRule(&[I8] infix) {
	return lambdaDefinition/*auto*/;
}
auto temp(List<&[I8]> divisions) {
	return Main/*auto*/.selectFirst(divisions/*List<&[I8]>*/, sourceInfix/*&[I8]*/);
}
auto temp(Tuple2<CompileState, Value> rightTuple) {{
		var left = leftTuple/*auto*/.right(/*auto*/);
		var right = rightTuple/*Tuple2<CompileState, Value>*/.right(/*auto*/);
		return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(rightTuple/*Tuple2<CompileState, Value>*/.left(/*auto*/), new OperationNode(left/*auto*/, targetInfix/*&[I8]*/, right/*auto*/)));
	}
}
auto temp(Tuple2<CompileState, Value> leftTuple) {
	return Main/*auto*/.parseValue(leftTuple/*auto*/.left(/*auto*/), rightString/*auto*/).flatMap(lambdaDefinition/*auto*/);
}
auto temp(&[I8] leftString, &[I8] rightString) {
	return Main/*auto*/.parseValue(state1/*CompileState*/, leftString/*auto*/).flatMap(lambdaDefinition/*auto*/);
}
auto temp(CompileState state1, &[I8] input1) {
	return Main/*auto*/.compileSplit(Main/*auto*/.splitFolded(input1/*&[I8]*/, Main/*auto*/.foldOperator(sourceInfix/*&[I8]*/), lambdaDefinition/*auto*/), lambdaDefinition/*auto*/);
}
static (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, Value>> createOperatorRuleWithDifferentInfix(&[I8] sourceInfix, &[I8] targetInfix) {
	return lambdaDefinition/*auto*/;
}
static Option<Tuple2<&[I8], &[I8]>> selectFirst(List<&[I8]> divisions, &[I8] delimiter) {
	var first = divisions/*List<&[I8]>*/.findFirst(/*auto*/).orElse("");
	var afterFirst = divisions/*List<&[I8]>*/.subList(1/*auto*/, divisions/*List<&[I8]>*/.size(/*auto*/)).orElse(divisions/*List<&[I8]>*/).query(/*auto*/).collect(new Joiner(delimiter/*&[I8]*/)).orElse("");
	return new Some<Tuple2<&[I8], &[I8]>>(new Tuple2Impl<&[I8], &[I8]>(first/*auto*/, afterFirst/*auto*/));
}
auto temp(Tuple2<DivideState, I8> tuple) {
	return tuple/*Tuple2<CompileState, &[I8]>*/.left(/*auto*/);
}
auto temp(DivideState state, I8 c) {{
		if (c/*I8*/ === Strings/*auto*/.charAt(infix/*&[I8]*/, 0/*auto*/) && state/*DivideState*/.startsWith(Strings/*auto*/.sliceFrom(infix/*&[I8]*/, 1/*auto*/))) {
			var length = Strings/*auto*/.length(infix/*&[I8]*/) - 1/*auto*/;
			var counter = 0/*auto*/;
			var current = state/*DivideState*/;
			while (counter/*auto*/ < length/*number*/) {
				counter/*auto*/++;
				current/*Tuple2<CompileState, List<T>>*/ = current/*Tuple2<CompileState, List<T>>*/.pop(/*auto*/).map(lambdaDefinition/*auto*/).orElse(current/*Tuple2<CompileState, List<T>>*/);
			}
			return current/*Tuple2<CompileState, List<T>>*/.advance(/*auto*/);
		}
		return state/*DivideState*/.append(c/*I8*/);
	}
}
static (arg0 : DivideState, arg1 : I8) => DivideState foldOperator(&[I8] infix) {
	return lambdaDefinition/*auto*/;
}
static Option<Tuple2<CompileState, Value>> parseNumber(CompileState state, &[I8] input) {
	var stripped = Strings/*auto*/.strip(input/*&[I8]*/);
	if (Main/*auto*/.isNumber(stripped/*auto*/)) {
		return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state/*CompileState*/, new SymbolNode(stripped/*auto*/, PrimitiveType/*auto*/.Auto)));
	}
	else {
		return new None<Tuple2<CompileState, Value>>(/*auto*/);
	}
}
auto temp(number index) {
	return input/*&[I8]*/.charAt(index/*I32*/);
}
auto temp(I8 c) {
	return Characters/*auto*/.isDigit(c/*I8*/);
}
static Bool isNumber(&[I8] input) {
	var query = new HeadedQuery<number>(new RangeHead(Strings/*auto*/.length(input/*&[I8]*/)));
	return query/*auto*/.map(lambdaDefinition/*auto*/).allMatch(lambdaDefinition/*auto*/);
}
static Option<Tuple2<CompileState, Value>> parseSymbol(CompileState state, &[I8] input) {
	var stripped = Strings/*auto*/.strip(input/*&[I8]*/);
	if (Main/*auto*/.isSymbol(stripped/*auto*/)) {
		var withImport = state/*CompileState*/.addResolvedImportFromCache(stripped/*auto*/);
		var symbolNode = new SymbolNode(stripped/*auto*/, state/*CompileState*/.resolve(stripped/*auto*/).orElse(PrimitiveType/*auto*/.Auto));
		return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(withImport/*auto*/, symbolNode/*auto*/));
	}
	else {
		return new None<Tuple2<CompileState, Value>>(/*auto*/);
	}
}
auto temp(number index) {
	return Main/*auto*/.isSymbolChar(index/*I32*/, Strings/*auto*/.charAt(input/*&[I8]*/, index/*I32*/));
}
static Bool isSymbol(&[I8] input) {
	var query = new HeadedQuery<number>(new RangeHead(Strings/*auto*/.length(input/*&[I8]*/)));
	return query/*auto*/.allMatch(lambdaDefinition/*auto*/);
}
static Bool isSymbolChar(number index, I8 c) {
	return "_" === c/*I8*/ || Characters/*auto*/.isLetter(c/*I8*/) || /*auto*/(0/*auto*/ !== index/*number*/ && Characters/*auto*/.isDigit(c/*I8*/));
}
static Option<Tuple2<CompileState, T>> compilePrefix(&[I8] input, &[I8] infix, (arg0 : &[I8]) => Option<Tuple2<CompileState, T>> mapper) {
	if (!input/*&[I8]*/.startsWith(infix/*&[I8]*/)) {
		return new None<Tuple2<CompileState, T>>(/*auto*/);
	}
	var slice = Strings/*auto*/.sliceFrom(input/*&[I8]*/, Strings/*auto*/.length(infix/*&[I8]*/));
	return mapper/*(arg0 : &[I8]) => Option<Tuple2<CompileState, T>>*/(slice/*&[I8]*/);
}
auto temp(Tuple2<CompileState, Whitespace> tuple) {{
		var generate = tuple/*Tuple2<CompileState, Whitespace>*/.right(/*auto*/).generate(tuple/*Tuple2<CompileState, Whitespace>*/.left(/*auto*/).platform(/*auto*/));
		return new Tuple2Impl<CompileState, &[I8]>(tuple/*Tuple2<CompileState, Whitespace>*/.left(/*auto*/), generate/*auto*/);
	}
}
static Option<Tuple2<CompileState, &[I8]>> compileWhitespace(CompileState state, &[I8] input) {
	return Main/*auto*/.parseWhitespace(state/*CompileState*/, input/*&[I8]*/).map(lambdaDefinition/*auto*/);
}
static Option<Tuple2<CompileState, Whitespace>> parseWhitespace(CompileState state, &[I8] input) {
	if (Strings/*auto*/.isBlank(input/*&[I8]*/)) {
		return new Some<Tuple2<CompileState, Whitespace>>(new Tuple2Impl<CompileState, Whitespace>(state/*CompileState*/, new Whitespace(/*auto*/)));
	}
	return new None<Tuple2<CompileState, Whitespace>>(/*auto*/);
}
auto temp() {
	return Main/*auto*/.compileEnumValues(state/*CompileState*/, withoutEnd/*&[I8]*/);
}
auto temp(&[I8] withoutEnd) {
	return Main/*auto*/.getTupleOption(state/*CompileState*/, withoutEnd/*&[I8]*/).or(lambdaDefinition/*auto*/);
}
static Option<Tuple2<CompileState, &[I8]>> compileFieldDefinition(CompileState state, &[I8] input) {
	return Main/*auto*/.compileSuffix(Strings/*auto*/.strip(input/*&[I8]*/), ";", lambdaDefinition/*auto*/);
}
auto temp(Tuple2<CompileState, Parameter> definitionTuple) {{
		var generate = "\n\t" + definitionTuple/*Tuple2<CompileState, Parameter>*/.right(/*auto*/).generate(definitionTuple/*Tuple2<CompileState, Parameter>*/.left(/*auto*/).platform(/*auto*/)) + ";";
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(definitionTuple/*Tuple2<CompileState, Parameter>*/.left(/*auto*/), generate/*auto*/));
	}
}
static Option<Tuple2<CompileState, &[I8]>> getTupleOption(CompileState state, &[I8] withoutEnd) {
	return Main/*auto*/.parseParameter(state/*CompileState*/, withoutEnd/*&[I8]*/).flatMap(lambdaDefinition/*auto*/);
}
auto temp(&[I8] stringOption) {
	return new Tuple2Impl<CompileState, &[I8]>(tuple/*Tuple2<CompileState, Value>*/.left(/*auto*/), stringOption/*auto*/);
}
auto temp(Tuple2<CompileState, Value> tuple) {{
		var structureName = state/*CompileState*/.findLastStructureName(/*auto*/).orElse("");
		return tuple/*Tuple2<CompileState, Value>*/.right(/*auto*/).generateAsEnumValue(structureName/*&[I8]*/, state/*CompileState*/.platform(/*auto*/)).map(lambdaDefinition/*auto*/);
	}
}
auto temp(CompileState state1, &[I8] s) {
	return Main/*auto*/.parseInvokable(state1/*CompileState*/, s/*&[I8]*/).flatMap(lambdaDefinition/*auto*/);
}
auto temp(Tuple2<CompileState, List<&[I8]>> tuple) {
	return new Tuple2Impl<CompileState, &[I8]>(tuple/*Tuple2<CompileState, Value>*/.left(/*auto*/), tuple/*Tuple2<CompileState, Value>*/.right(/*auto*/).query(/*auto*/).collect(new Joiner("")).orElse(""));
}
static Option<Tuple2<CompileState, &[I8]>> compileEnumValues(CompileState state, &[I8] withoutEnd) {
	return Main/*auto*/.parseValues(state/*CompileState*/, withoutEnd/*&[I8]*/, lambdaDefinition/*auto*/).map(lambdaDefinition/*auto*/);
}
auto temp() {
	return new Tuple2Impl<CompileState, Parameter>(state/*CompileState*/, new Placeholder(input/*&[I8]*/));
}
static Tuple2<CompileState, Parameter> parseParameterOrPlaceholder(CompileState state, &[I8] input) {
	return Main/*auto*/.parseParameter(state/*CompileState*/, input/*&[I8]*/).orElseGet(lambdaDefinition/*auto*/);
}
auto temp(Tuple2<CompileState, Whitespace> tuple) {
	return Main/*auto*/.getCompileStateParameterTuple2(tuple/*Tuple2<CompileState, Value>*/);
}
auto temp(Tuple2<CompileState, Definition> tuple) {
	return new Tuple2Impl<CompileState, Parameter>(tuple/*Tuple2<CompileState, Value>*/.left(/*auto*/), tuple/*Tuple2<CompileState, Value>*/.right(/*auto*/));
}
auto temp() {
	return Main/*auto*/.parseDefinition(state/*CompileState*/, input/*&[I8]*/).map(lambdaDefinition/*auto*/);
}
static Option<Tuple2<CompileState, Parameter>> parseParameter(CompileState state, &[I8] input) {
	return Main/*auto*/.parseWhitespace(state/*CompileState*/, input/*&[I8]*/).map(lambdaDefinition/*auto*/).or(lambdaDefinition/*auto*/);
}
static Tuple2<CompileState, Parameter> getCompileStateParameterTuple2(Tuple2<CompileState, Whitespace> tuple) {
	return new Tuple2Impl<CompileState, Parameter>(tuple/*Tuple2<CompileState, Whitespace>*/.left(/*auto*/), tuple/*Tuple2<CompileState, Whitespace>*/.right(/*auto*/));
}
auto temp(&[I8] annotationsString, &[I8] afterAnnotations) {{
		var annotations = Main/*auto*/.parseAnnotations(annotationsString/*&[I8]*/);
		return Main/*auto*/.parseDefinitionWithAnnotations(state/*CompileState*/, annotations/*List<&[I8]>*/, afterAnnotations/*&[I8]*/, type/*Type*/, name/*&[I8]*/);
	}
}
auto temp() {
	return Main/*auto*/.parseDefinitionWithAnnotations(state/*CompileState*/, Lists/*auto*/.empty(/*auto*/), beforeType/*&[I8]*/, type/*Type*/, name/*&[I8]*/);
}
auto temp(&[I8] beforeType, &[I8] type) {
	return Main/*auto*/.compileLast(Strings/*auto*/.strip(beforeType/*&[I8]*/), "\n", lambdaDefinition/*auto*/).or(lambdaDefinition/*auto*/);
}
auto temp() {
	return Main/*auto*/.parseDefinitionWithTypeParameters(state/*CompileState*/, Lists/*auto*/.empty(/*auto*/), Lists/*auto*/.empty(/*auto*/), Lists/*auto*/.empty(/*auto*/), beforeName/*auto*/, name/*&[I8]*/);
}
auto temp(&[I8] beforeName, &[I8] name) {
	return Main/*auto*/.compileSplit(Main/*auto*/.splitFoldedLast(Strings/*auto*/.strip(beforeName/*auto*/), " ", Main/*auto*/.foldTypeSeparators), lambdaDefinition/*auto*/).or(lambdaDefinition/*auto*/);
}
static Option<Tuple2<CompileState, Definition>> parseDefinition(CompileState state, &[I8] input) {
	return Main/*auto*/.compileLast(Strings/*auto*/.strip(input/*&[I8]*/), " ", lambdaDefinition/*auto*/);
}
auto temp(DivideState state1, I8 c) {
	return Main/*auto*/.foldDelimited(state1/*CompileState*/, c/*I8*/, "\n");
}
auto temp(&[I8] s2) {
	return Strings/*auto*/.strip(s2/*&[I8]*/);
}
auto temp(&[I8] value) {
	return !Strings/*auto*/.isEmpty(value/*&[I8]*/);
}
auto temp(&[I8] value) {
	return 1/*auto*/ <= Strings/*auto*/.length(value/*&[I8]*/);
}
auto temp(&[I8] value) {
	return Strings/*auto*/.sliceFrom(value/*&[I8]*/, 1/*auto*/);
}
auto temp(&[I8] s1) {
	return Strings/*auto*/.strip(s1/*auto*/);
}
auto temp(&[I8] value) {
	return !Strings/*auto*/.isEmpty(value/*&[I8]*/);
}
static List<&[I8]> parseAnnotations(&[I8] s) {
	return Main/*auto*/.divide(s/*&[I8]*/, lambdaDefinition/*auto*/).query(/*auto*/).map(lambdaDefinition/*auto*/).filter(lambdaDefinition/*auto*/).filter(lambdaDefinition/*auto*/).map(lambdaDefinition/*auto*/).map(lambdaDefinition/*auto*/).filter(lambdaDefinition/*auto*/).collect(new ListCollector<&[I8]>(/*auto*/));
}
auto temp(&[I8] beforeTypeParams, &[I8] typeParamsString) {{
		var typeParams = Main/*auto*/.divideValues(typeParamsString/*&[I8]*/);
		return Main/*auto*/.parseDefinitionWithTypeParameters(state/*CompileState*/, annotations/*List<&[I8]>*/, typeParams/*List<&[I8]>*/, Main/*auto*/.parseModifiers(beforeTypeParams/*&[I8]*/), type/*&[I8]*/, name/*&[I8]*/);
	}
}
auto temp(&[I8] withoutTypeParamEnd) {
	return Main/*auto*/.compileFirst(withoutTypeParamEnd/*auto*/, "<", lambdaDefinition/*auto*/);
}
auto temp() {{
		var divided = Main/*auto*/.parseModifiers(beforeType/*&[I8]*/);
		return Main/*auto*/.parseDefinitionWithTypeParameters(state/*CompileState*/, annotations/*List<&[I8]>*/, Lists/*auto*/.empty(/*auto*/), divided/*auto*/, type/*&[I8]*/, name/*&[I8]*/);
	}
}
static Option<Tuple2<CompileState, Definition>> parseDefinitionWithAnnotations(CompileState state, List<&[I8]> annotations, &[I8] beforeType, &[I8] type, &[I8] name) {
	return Main/*auto*/.compileSuffix(Strings/*auto*/.strip(beforeType/*&[I8]*/), ">", lambdaDefinition/*auto*/).or(lambdaDefinition/*auto*/);
}
auto temp(DivideState state1, I8 c) {
	return Main/*auto*/.foldDelimited(state1/*CompileState*/, c/*I8*/, " ");
}
auto temp(&[I8] s) {
	return Strings/*auto*/.strip(s/*&[I8]*/);
}
auto temp(&[I8] value) {
	return !Strings/*auto*/.isEmpty(value/*&[I8]*/);
}
static List<&[I8]> parseModifiers(&[I8] beforeType) {
	return Main/*auto*/.divide(Strings/*auto*/.strip(beforeType/*&[I8]*/), lambdaDefinition/*auto*/).query(/*auto*/).map(lambdaDefinition/*auto*/).filter(lambdaDefinition/*auto*/).collect(new ListCollector<&[I8]>(/*auto*/));
}
static DivideState foldDelimited(DivideState state1, I8 c, I8 delimiter) {
	if (delimiter/*I8*/ === c/*I8*/) {
		return state1/*DivideState*/.advance(/*auto*/);
	}
	return state1/*DivideState*/.append(c/*I8*/);
}
auto temp(&[I8] input1) {
	return Strings/*auto*/.strip(input1/*&[I8]*/);
}
auto temp(&[I8] value) {
	return !Strings/*auto*/.isEmpty(value/*&[I8]*/);
}
static List<&[I8]> divideValues(&[I8] input) {
	return Main/*auto*/.divide(input/*&[I8]*/, Main/*auto*/.foldValues).query(/*auto*/).map(lambdaDefinition/*auto*/).filter(lambdaDefinition/*auto*/).collect(new ListCollector<&[I8]>(/*auto*/));
}
static DivideState foldTypeSeparators(DivideState state, I8 c) {
	if (" " === c/*I8*/ && state/*DivideState*/.isLevel(/*auto*/)) {
		return state/*DivideState*/.advance(/*auto*/);
	}
	var appended = state/*DivideState*/.append(c/*I8*/);
	if ("<" === c/*I8*/) {
		return appended/*auto*/.enter(/*auto*/);
	}
	if (">" === c/*I8*/) {
		return appended/*auto*/.exit(/*auto*/);
	}
	return appended/*auto*/;
}
auto temp(Tuple2<CompileState, Type> typeTuple) {{
		var newModifiers = Main/*auto*/.modifyModifiers(oldModifiers/*List<&[I8]>*/, state/*CompileState*/.platform(/*auto*/));
		var generated = new Definition(annotations/*List<&[I8]>*/, newModifiers/*auto*/, typeParams/*List<&[I8]>*/, typeTuple/*Tuple2<CompileState, Type>*/.right(/*auto*/), name/*&[I8]*/);
		return new Some<Tuple2<CompileState, Definition>>(new Tuple2Impl<CompileState, Definition>(typeTuple/*Tuple2<CompileState, Type>*/.left(/*auto*/), generated/*auto*/));
	}
}
static Option<Tuple2<CompileState, Definition>> parseDefinitionWithTypeParameters(CompileState state, List<&[I8]> annotations, List<&[I8]> typeParams, List<&[I8]> oldModifiers, &[I8] type, &[I8] name) {
	return Main/*auto*/.parseType(state/*CompileState*/, type/*&[I8]*/).flatMap(lambdaDefinition/*auto*/);
}
static List<&[I8]> modifyModifiers(List<&[I8]> oldModifiers, Platform platform) {
	var list = Main/*auto*/.retainFinal(oldModifiers/*List<&[I8]>*/, platform/*Platform*/);
	if (oldModifiers/*List<&[I8]>*/.contains("static", Strings/*auto*/.equalsTo)) {
		return list/*auto*/.addLast("static");
	}
	return list/*auto*/;
}
static List<&[I8]> retainFinal(List<&[I8]> oldModifiers, Platform platform) {
	if (oldModifiers/*List<&[I8]>*/.contains("final", Strings/*auto*/.equalsTo) || Platform/*auto*/.Magma !== platform/*Platform*/) {
		return Lists/*auto*/.empty(/*auto*/);
	}
	return Lists/*auto*/.of("mut");
}
auto temp(Tuple2<CompileState, Type> tuple) {
	return new Tuple2Impl<CompileState, Type>(tuple/*Tuple2<CompileState, Whitespace>*/.left(/*auto*/), tuple/*Tuple2<CompileState, Whitespace>*/.right(/*auto*/));
}
auto temp() {
	return new Tuple2Impl<CompileState, Type>(state/*CompileState*/, new Placeholder(type/*&[I8]*/));
}
static Tuple2<CompileState, Type> parseTypeOrPlaceholder(CompileState state, &[I8] type) {
	return Main/*auto*/.parseType(state/*CompileState*/, type/*&[I8]*/).map(lambdaDefinition/*auto*/).orElseGet(lambdaDefinition/*auto*/);
}
auto temp(Tuple2<CompileState, Type> tuple) {
	return new Tuple2Impl<CompileState, &[I8]>(tuple/*Tuple2<CompileState, Whitespace>*/.left(/*auto*/), tuple/*Tuple2<CompileState, Whitespace>*/.right(/*auto*/).generate(/*auto*/));
}
static Option<Tuple2<CompileState, &[I8]>> compileType(CompileState state, &[I8] type) {
	return Main/*auto*/.parseType(state/*CompileState*/, type/*&[I8]*/).map(lambdaDefinition/*auto*/);
}
static Option<Tuple2<CompileState, Type>> parseType(CompileState state, &[I8] type) {
	return Main/*auto*/.or(state/*CompileState*/, type/*&[I8]*/, Lists/*auto*/.of(Main/*auto*/.parseArrayType, Main/*auto*/.parseVarArgs, Main/*auto*/.parseGeneric, Main/*auto*/.parsePrimitive, Main/*auto*/.parseSymbolType));
}
auto temp(&[I8] s) {{
		var child = Main/*auto*/.parseTypeOrPlaceholder(state/*CompileState*/, s/*&[I8]*/);
		return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(child/*&[I8]*/.left(/*auto*/), new ArrayType(child/*&[I8]*/.right(/*auto*/))));
	}
}
static Option<Tuple2<CompileState, Type>> parseArrayType(CompileState state, &[I8] input) {
	var stripped = Strings/*auto*/.strip(input/*&[I8]*/);
	return Main/*auto*/.compileSuffix(stripped/*auto*/, "[]", lambdaDefinition/*auto*/);
}
auto temp(&[I8] s) {{
		var child = Main/*auto*/.parseTypeOrPlaceholder(state/*CompileState*/, s/*&[I8]*/);
		return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(child/*&[I8]*/.left(/*auto*/), new VariadicType(child/*&[I8]*/.right(/*auto*/))));
	}
}
static Option<Tuple2<CompileState, Type>> parseVarArgs(CompileState state, &[I8] input) {
	var stripped = Strings/*auto*/.strip(input/*&[I8]*/);
	return Main/*auto*/.compileSuffix(stripped/*auto*/, "...", lambdaDefinition/*auto*/);
}
static Option<Tuple2<CompileState, Type>> parseSymbolType(CompileState state, &[I8] input) {
	var stripped = Strings/*auto*/.strip(input/*&[I8]*/);
	if (Main/*auto*/.isSymbol(stripped/*auto*/)) {
		var symbolNode = new SymbolNode(stripped/*auto*/, PrimitiveType/*auto*/.Auto);
		return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(state/*CompileState*/.addResolvedImportFromCache(stripped/*auto*/), symbolNode/*auto*/));
	}
	return new None<Tuple2<CompileState, Type>>(/*auto*/);
}
auto temp(Type result) {
	return new Tuple2Impl<CompileState, Type>(state/*CompileState*/, result/*Tuple2<CompileState, &[I8]>*/);
}
static Option<Tuple2<CompileState, Type>> parsePrimitive(CompileState state, &[I8] input) {
	return Main/*auto*/.findPrimitiveValue(Strings/*auto*/.strip(input/*&[I8]*/), state/*CompileState*/.platform(/*auto*/)).map(lambdaDefinition/*auto*/);
}
static Option<Type> findPrimitiveValue(&[I8] input, Platform platform) {
	var stripped = Strings/*auto*/.strip(input/*&[I8]*/);
	if (Strings/*auto*/.equalsTo("char", stripped/*auto*/) || Strings/*auto*/.equalsTo("Character", stripped/*auto*/)) {
		if (Platform/*auto*/.TypeScript === platform/*Platform*/) {
			return new Some<Type>(PrimitiveType/*auto*/.String);
		}
		else {
			return new Some<Type>(PrimitiveType/*auto*/.I8);
		}
	}
	if (Strings/*auto*/.equalsTo("String", stripped/*auto*/)) {
		if (Platform/*auto*/.TypeScript === platform/*Platform*/) {
			return new Some<Type>(PrimitiveType/*auto*/.String);
		}
		else {
			return new Some<Type>(new SliceType(PrimitiveType/*auto*/.I8));
		}
	}
	if (Strings/*auto*/.equalsTo("int", stripped/*auto*/) || Strings/*auto*/.equalsTo("Integer", stripped/*auto*/)) {
		if (Platform/*auto*/.Magma === platform/*Platform*/) {
			return new Some<Type>(PrimitiveType/*auto*/.I32);
		}
		else {
			return new Some<Type>(PrimitiveType/*auto*/.Number);
		}
	}
	if (Strings/*auto*/.equalsTo("boolean", stripped/*auto*/) || Strings/*auto*/.equalsTo("Boolean", stripped/*auto*/)) {
		return new Some<Type>(new BooleanType(platform/*Platform*/));
	}
	if (Strings/*auto*/.equalsTo("var", stripped/*auto*/)) {
		return new Some<Type>(PrimitiveType/*auto*/.Var);
	}
	if (Strings/*auto*/.equalsTo("void", stripped/*auto*/)) {
		return new Some<Type>(PrimitiveType/*auto*/.Void);
	}
	return new None<Type>(/*auto*/);
}
auto temp(CompileState state1, &[I8] s) {
	return Main/*auto*/.compileTypeArgument(state1/*DivideState*/, s/*&[I8]*/);
}
auto temp() {{
			var compileState = argsState/*auto*/.addResolvedImportFromCache(base/*&[I8]*/);
			return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(compileState/*auto*/, new TemplateType(base/*&[I8]*/, args/*List<T>*/)));
		}
}
auto temp(&[I8] baseString, &[I8] argsString) {{
		var argsTuple = Main/*auto*/.parseValuesOrEmpty(state/*CompileState*/, argsString/*&[I8]*/, lambdaDefinition/*auto*/);
		var argsState = argsTuple/*Tuple2<CompileState, List<Argument>>*/.left(/*auto*/);
		var args = argsTuple/*Tuple2<CompileState, List<Argument>>*/.right(/*auto*/);
		var base = Strings/*auto*/.strip(baseString/*&[I8]*/);
		return Main/*auto*/.assembleFunctionType(argsState/*auto*/, base/*&[I8]*/, args/*List<T>*/).or(lambdaDefinition/*auto*/);
	}
}
auto temp(&[I8] withoutEnd) {
	return Main/*auto*/.compileFirst(withoutEnd/*&[I8]*/, "<", lambdaDefinition/*auto*/);
}
static Option<Tuple2<CompileState, Type>> parseGeneric(CompileState state, &[I8] input) {
	return Main/*auto*/.compileSuffix(Strings/*auto*/.strip(input/*&[I8]*/), ">", lambdaDefinition/*auto*/);
}
auto temp(Type generated) {
	return new Tuple2Impl<CompileState, Type>(state/*CompileState*/, generated/*auto*/);
}
static Option<Tuple2<CompileState, Type>> assembleFunctionType(CompileState state, &[I8] base, List<&[I8]> args) {
	return Main/*auto*/.mapFunctionType(base/*&[I8]*/, args/*List<&[I8]>*/).map(lambdaDefinition/*auto*/);
}
auto temp() {
	return args/*List<&[I8]>*/.find(1/*auto*/);
}
auto temp(Tuple2<&[I8], &[I8]> tuple) {
	return new FunctionType(Lists/*auto*/.of(tuple/*Tuple2<CompileState, Whitespace>*/.left(/*auto*/)), tuple/*Tuple2<CompileState, Whitespace>*/.right(/*auto*/));
}
auto temp() {
	return args/*List<&[I8]>*/.find(1/*auto*/);
}
auto temp() {
	return args/*List<&[I8]>*/.find(2/*auto*/);
}
auto temp(Tuple2<Tuple2<&[I8], &[I8]>, &[I8]> tuple) {
	return new FunctionType(Lists/*auto*/.of(tuple/*Tuple2<CompileState, Whitespace>*/.left(/*auto*/).left(/*auto*/), tuple/*Tuple2<CompileState, Whitespace>*/.left(/*auto*/).right(/*auto*/)), tuple/*Tuple2<CompileState, Whitespace>*/.right(/*auto*/));
}
auto temp(&[I8] first) {
	return new FunctionType(Lists/*auto*/.empty(/*auto*/), first/*auto*/);
}
auto temp(&[I8] first) {
	return new FunctionType(Lists/*auto*/.of(first/*auto*/), "void");
}
auto temp(&[I8] first) {
	return new FunctionType(Lists/*auto*/.of(first/*auto*/), "boolean");
}
static Option<Type> mapFunctionType(&[I8] base, List<&[I8]> args) {
	if (Strings/*auto*/.equalsTo("Function", base/*&[I8]*/)) {
		return args/*List<&[I8]>*/.findFirst(/*auto*/).and(lambdaDefinition/*auto*/).map(lambdaDefinition/*auto*/);
	}
	if (Strings/*auto*/.equalsTo("BiFunction", base/*&[I8]*/)) {
		return args/*List<&[I8]>*/.find(0/*auto*/).and(lambdaDefinition/*auto*/).and(lambdaDefinition/*auto*/).map(lambdaDefinition/*auto*/);
	}
	if (Strings/*auto*/.equalsTo("Supplier", base/*&[I8]*/)) {
		return args/*List<&[I8]>*/.findFirst(/*auto*/).map(lambdaDefinition/*auto*/);
	}
	if (Strings/*auto*/.equalsTo("Consumer", base/*&[I8]*/)) {
		return args/*List<&[I8]>*/.findFirst(/*auto*/).map(lambdaDefinition/*auto*/);
	}
	if (Strings/*auto*/.equalsTo("Predicate", base/*&[I8]*/)) {
		return args/*List<&[I8]>*/.findFirst(/*auto*/).map(lambdaDefinition/*auto*/);
	}
	return new None<Type>(/*auto*/);
}
auto temp(CompileState state2, &[I8] input1) {
	return Main/*auto*/.compileWhitespace(state2/*auto*/, input1/*&[I8]*/);
}
auto temp(CompileState state1, &[I8] type) {
	return Main/*auto*/.compileType(state1/*DivideState*/, type/*&[I8]*/);
}
static Option<Tuple2<CompileState, &[I8]>> compileTypeArgument(CompileState state, &[I8] input) {
	return Main/*auto*/.or(state/*CompileState*/, input/*&[I8]*/, Lists/*auto*/.of(lambdaDefinition/*auto*/, lambdaDefinition/*auto*/));
}
static Tuple2<CompileState, List<T>> parseValuesOrEmpty(CompileState state, &[I8] input, (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, T>> mapper) {
	return Main/*auto*/.parseValues(state/*CompileState*/, input/*&[I8]*/, mapper/*(arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, T>>*/).orElse(new Tuple2Impl<CompileState, List<T>>(state/*CompileState*/, Lists/*auto*/.empty(/*auto*/)));
}
static Option<Tuple2<CompileState, List<T>>> parseValues(CompileState state, &[I8] input, (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, T>> mapper) {
	return Main/*auto*/.parseAll(state/*CompileState*/, input/*&[I8]*/, Main/*auto*/.foldValues, mapper/*(arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, T>>*/);
}
static DivideState foldValues(DivideState state, I8 c) {
	if ("," === c/*I8*/ && state/*DivideState*/.isLevel(/*auto*/)) {
		return state/*DivideState*/.advance(/*auto*/);
	}
	var appended = state/*DivideState*/.append(c/*I8*/);
	if ("-" === c/*I8*/) {
		var peeked = appended/*auto*/.peek(/*auto*/);
		if (">" === peeked/*auto*/) {
			return appended/*auto*/.popAndAppendToOption(/*auto*/).orElse(appended/*auto*/);
		}
		else {
			return appended/*auto*/;
		}
	}
	if ("<" === c/*I8*/ || "(" === c/*I8*/) {
		return appended/*auto*/.enter(/*auto*/);
	}
	if (">" === c/*I8*/ || ")" === c/*I8*/) {
		return appended/*auto*/.exit(/*auto*/);
	}
	return appended/*auto*/;
}
static Option<T> compileLast(&[I8] input, &[I8] infix, (arg0 : &[I8], arg1 : &[I8]) => Option<T> mapper) {
	return Main/*auto*/.compileInfix(input/*&[I8]*/, infix/*&[I8]*/, Main/*auto*/.findLast, mapper/*(arg0 : &[I8], arg1 : &[I8]) => Option<T>*/);
}
static number findLast(&[I8] input, &[I8] infix) {
	return input/*&[I8]*/.lastIndexOf(infix/*&[I8]*/);
}
static Option<T> compileSuffix(&[I8] input, &[I8] suffix, (arg0 : &[I8]) => Option<T> mapper) {
	if (!input/*&[I8]*/.endsWith(suffix/*&[I8]*/)) {
		return new None<T>(/*auto*/);
	}
	var length = Strings/*auto*/.length(input/*&[I8]*/);
	var length1 = Strings/*auto*/.length(suffix/*&[I8]*/);
	var content = Strings/*auto*/.sliceBetween(input/*&[I8]*/, 0/*auto*/, length/*number*/ - length1/*auto*/);
	return mapper/*(arg0 : &[I8]) => Option<T>*/(content/*&[I8]*/);
}
static Option<T> compileFirst(&[I8] input, &[I8] infix, (arg0 : &[I8], arg1 : &[I8]) => Option<T> mapper) {
	return Main/*auto*/.compileInfix(input/*&[I8]*/, infix/*&[I8]*/, Main/*auto*/.findFirst, mapper/*(arg0 : &[I8], arg1 : &[I8]) => Option<T>*/);
}
static Option<T> compileInfix(&[I8] input, &[I8] infix, (arg0 : &[I8], arg1 : &[I8]) => number locator, (arg0 : &[I8], arg1 : &[I8]) => Option<T> mapper) {
	return Main/*auto*/.compileSplit(Main/*auto*/.split(input/*&[I8]*/, infix/*&[I8]*/, locator/*(arg0 : &[I8], arg1 : &[I8]) => number*/), mapper/*(arg0 : &[I8], arg1 : &[I8]) => Option<T>*/);
}
auto temp(Tuple2<&[I8], &[I8]> tuple) {
	return mapper/*(arg0 : &[I8], arg1 : &[I8]) => Option<T>*/(tuple/*Tuple2<CompileState, Whitespace>*/.left(/*auto*/), tuple/*Tuple2<CompileState, Whitespace>*/.right(/*auto*/));
}
static Option<T> compileSplit(Option<Tuple2<&[I8], &[I8]>> splitter, (arg0 : &[I8], arg1 : &[I8]) => Option<T> mapper) {
	return splitter/*Option<Tuple2<&[I8], &[I8]>>*/.flatMap(lambdaDefinition/*auto*/);
}
static Option<Tuple2<&[I8], &[I8]>> split(&[I8] input, &[I8] infix, (arg0 : &[I8], arg1 : &[I8]) => number locator) {
	var index = locator/*(arg0 : &[I8], arg1 : &[I8]) => number*/(input/*&[I8]*/, infix/*&[I8]*/);
	if (0/*auto*/ > index/*number*/) {
		return new None<Tuple2<&[I8], &[I8]>>(/*auto*/);
	}
	var left = Strings/*auto*/.sliceBetween(input/*&[I8]*/, 0/*auto*/, index/*number*/);
	var length = Strings/*auto*/.length(infix/*&[I8]*/);
	var right = Strings/*auto*/.sliceFrom(input/*&[I8]*/, index/*number*/ + length/*number*/);
	return new Some<Tuple2<&[I8], &[I8]>>(new Tuple2Impl<&[I8], &[I8]>(left/*auto*/, right/*auto*/));
}
static number findFirst(&[I8] input, &[I8] infix) {
	return input/*&[I8]*/.indexOf(infix/*&[I8]*/);
}
static &[I8] generatePlaceholder(&[I8] input) {
	var replaced = input/*&[I8]*/.replace("/*", "start").replace("*/", "end");
	return "/*" + replaced/*auto*/ + "*/";
}Main.main();