interface MethodHeader  {
	generateWithAfterName(afterName: string): string;
	hasAnnotation(annotation: string): boolean;
}
interface Result<T, X> {
	match<R>(whenOk: (arg0 : T) => R, whenErr: (arg0 : X) => R): R;
}
interface Collector<T, C> {
	createInitial(): C;
	fold(current: C, element: T): C;
}
interface Option<T> {
	map<R>(mapper: (arg0 : T) => R): Option<R>;
	isEmpty(): boolean;
	orElse(other: T): T;
	orElseGet(supplier: () => T): T;
	isPresent(): boolean;
	ifPresent(consumer: (arg0 : T) => void): void;
	or(other: () => Option<T>): Option<T>;
	flatMap<R>(mapper: (arg0 : T) => Option<R>): Option<R>;
	filter(predicate: (arg0 : T) => boolean): Option<T>;
	toTuple(other: T): Tuple<Boolean, T>;
	and<R>(other: () => Option<R>): Option<Tuple<T, R>>;
}
interface Query<T> {
	collect<C>(collector: Collector<T, C>): C;
	map<R>(mapper: (arg0 : T) => R): Query<R>;
	fold<R>(initial: R, folder: (arg0 : R, arg1 : T) => R): R;
	flatMap<R>(mapper: (arg0 : T) => Query<R>): Query<R>;
	next(): Option<T>;
	allMatch(predicate: (arg0 : T) => boolean): boolean;
	filter(predicate: (arg0 : T) => boolean): Query<T>;
}
interface List<T> {
	add(element: T): List<T>;
	query(): Query<T>;
	size(): number;
	subList(startInclusive: number, endExclusive: number): Option<List<T>>;
	findLast(): Option<T>;
	findFirst(): Option<T>;
	find(index: number): Option<T>;
	queryWithIndices(): Query<Tuple<number, T>>;
	addAll(others: List<T>): List<T>;
	contains(element: T): boolean;
	queryReversed(): Query<T>;
}
interface Head<T> {
	next(): Option<T>;
}
interface Parameter  {
	generate(): string;
	asDefinition(): Option<Definition>;
}
interface Value extends Argument, Caller  {
	resolve(state: CompileState): Type;
	generateAsEnumValue(structureName: string): Option<string>;
}
interface Argument  {
	toValue(): Option<Value>;
}
interface Caller  {
	generate(): string;
	findChild(): Option<Value>;
}
interface Type  {
	generate(): string;
	isFunctional(): boolean;
	isVar(): boolean;
	generateBeforeName(): string;
}
interface Actual  {
}
class HeadedQuery<T> implements Query<T> {
	head: Head<T>;
	constructor (head: Head<T>) {
		this.head = head;
	}
	next(): Option<T> {
		return this.head.next();
	}
	collect<C>(collector: Collector<T, C>): C {
		return this.fold(collector.createInitial(), collector.fold);
	}
	map<R>(mapper: (arg0 : T) => R): Query<R> {
		return new HeadedQuery<R>(new MapHead<T, R>(this.head, mapper));
	}
	fold<R>(initial: R, folder: (arg0 : R, arg1 : T) => R): R {
		let result: R = initial;
		while (true){
			let finalResult: R = result;
			let maybeNext: Tuple<Boolean, R> = this.head.next().map((inner: T) => folder(finalResult, inner)).toTuple(finalResult);
			if (maybeNext.left){
				result = maybeNext.right;
			}
			else {
				return result;
			}
		}
	}
	flatMap<R>(mapper: (arg0 : T) => Query<R>): Query<R> {
		return this.head.next().map(mapper).map((initial: Query<R>) => new HeadedQuery<R>(new FlatMapHead<T, R>(this.head, initial, mapper))).orElseGet(() => new HeadedQuery<R>(new EmptyHead<R>()));
	}
	allMatch(predicate: (arg0 : T) => boolean): boolean {
		return this.fold(true, (maybeAllTrue: Boolean, element: T) => maybeAllTrue && predicate(element));
	}
	filter(predicate: (arg0 : T) => boolean): Query<T> {
		return this.flatMap((element: T) => {
			if (predicate(element)){
				return new HeadedQuery<T>(new SingleHead<T>(element));
			}
			else {
				return new HeadedQuery<T>(new EmptyHead<T>());
			}
		});
	}
}
class RangeHead implements Head<number> {
	length: number;
	counter: number;
	constructor (length: number) {
		this.length = length;
		this.counter = 0;
	}
	next(): Option<number> {
		if (this.counter >= this.length){
			return new None<number>();
		}
		let value = this.counter;
		this.counter++;
		return new Some<number>(value);
	}
}
class DivideState {
	segments: List<string>;
	buffer: string;
	depth: number;
	input: string;
	index: number;
	constructor (segments: List<string>, buffer: string, depth: number, input: string, index: number) {
		this.segments = segments;
		this.buffer = buffer;
		this.depth = depth;
		this.input = input;
		this.index = index;
	}
	static createInitial(input: string): DivideState {
		return new DivideState(Lists.empty(), "", 0, input, 0);
	}
	advance(): DivideState {
		return new DivideState(this.segments.add(this.buffer), "", this.depth, this.input, this.index);
	}
	append(c: string): DivideState {
		return new DivideState(this.segments, this.buffer + c, this.depth, this.input, this.index);
	}
	isLevel(): boolean {
		return 0 === this.depth;
	}
	enter(): DivideState {
		return new DivideState(this.segments, this.buffer, this.depth + 1, this.input, this.index);
	}
	exit(): DivideState {
		return new DivideState(this.segments, this.buffer, this.depth - 1, this.input, this.index);
	}
	isShallow(): boolean {
		return 1 === this.depth;
	}
	pop(): Option<Tuple<DivideState, string>> {
		if (this.index >= Strings.length(this.input)){
			return new None<Tuple<DivideState, string>>();
		}
		let c = this.input.charAt(this.index);
		let nextState = new DivideState(this.segments, this.buffer, this.depth, this.input, this.index + 1);
		return new Some<Tuple<DivideState, string>>(new Tuple<DivideState, string>(nextState, c));
	}
	popAndAppendToTuple(): Option<Tuple<DivideState, string>> {
		return this.pop().map((inner: Tuple<DivideState, string>) => new Tuple<DivideState, string>(inner.left.append(inner.right), inner.right));
	}
	popAndAppendToOption(): Option<DivideState> {
		return this.popAndAppendToTuple().map((tuple: Tuple<DivideState, string>) => tuple.left);
	}
	peek(): string {
		return this.input.charAt(this.index);
	}
	startsWith(slice: string): boolean {
		return Strings.sliceFrom(this.input, this.index).startsWith(slice);
	}
}
class Tuple<A, B> {
	left: A;
	right: B;
	constructor (left: A, right: B) {
		this.left = left;
		this.right = right;
	}
}
class CompileState {
	output: string;
	maybeStructureName: Option<string>;
	depth: number;
	definitions: List<Definition>;
	constructor (output: string, maybeStructureName: Option<string>, depth: number, definitions: List<Definition>) {
		this.output = output;
		this.maybeStructureName = maybeStructureName;
		this.depth = depth;
		this.definitions = definitions;
	}
	static createInitial(): CompileState {
		return new CompileState("", new None<string>(), 0, Lists.empty());
	}
	append(element: string): CompileState {
		return new CompileState(this.output + element, this.maybeStructureName, this.depth, this.definitions);
	}
	withStructureName(name: string): CompileState {
		return new CompileState(this.output, new Some<string>(name), this.depth, this.definitions);
	}
	enterDepth(): CompileState {
		return new CompileState(this.output, this.maybeStructureName, this.depth + 1, this.definitions);
	}
	exitDepth(): CompileState {
		return new CompileState(this.output, this.maybeStructureName, this.depth - 1, this.definitions);
	}
	defineAll(definitions: List<Definition>): CompileState {
		return new CompileState(this.output, this.maybeStructureName, this.depth, this.definitions.addAll(definitions));
	}
	resolve(name: string): Option<Type> {
		return this.definitions.queryReversed().filter((definition: Definition) => Strings.equalsTo(definition.name, name)).map((definition1: Definition) => definition1.type).next();
	}
}
class Joiner implements Collector<string, Option<string>> {
	delimiter: string;
	constructor (delimiter: string) {
		this.delimiter = delimiter;
	}
	static empty(): Joiner {
		return new Joiner("");
	}
	createInitial(): Option<string> {
		return new None<string>();
	}
	fold(maybe: Option<string>, element: string): Option<string> {
		return new Some<string>(maybe.map((inner: string) => inner + this.delimiter + element).orElse(element));
	}
}
class Definition {
	annotations: List<string>;
	modifiers: List<string>;
	typeParams: List<string>;
	type: Type;
	name: string;
	constructor (annotations: List<string>, modifiers: List<string>, typeParams: List<string>, type: Type, name: string) {
		this.annotations = annotations;
		this.modifiers = modifiers;
		this.typeParams = typeParams;
		this.type = type;
		this.name = name;
	}
	generate(): string {
		return this.generateWithAfterName("");
	}
	asDefinition(): Option<Definition> {
		return new Some<Definition>(this);
	}
	generateWithAfterName(afterName: string): string {
		let joinedTypeParams = this.joinTypeParams();
		let joinedModifiers = this.modifiers.query().map((value: string) => value + " ").collect(new Joiner("")).orElse("");
		return joinedModifiers + this.type.generateBeforeName() + this.name + joinedTypeParams + afterName + this.generateType();
	}
	generateType(): string {
		if (this.type.isVar()){
			return "";
		}
		return ": " + this.type.generate();
	}
	joinTypeParams(): string {
		return Main.joinTypeParams(this.typeParams);
	}
	hasAnnotation(annotation: string): boolean {
		return this.annotations.contains(annotation);
	}
}
class ConstructorHeader implements MethodHeader {
	generateWithAfterName(afterName: string): string {
		return "constructor " + afterName;
	}
	hasAnnotation(annotation: string): boolean {
		return false;
	}
}
class Ok<T, X> implements Result<T, X> {
	value: T;
	constructor (value: T) {
		this.value = value;
	}
	match<R>(whenOk: (arg0 : T) => R, whenErr: (arg0 : X) => R): R {
		return whenOk(this.value);
	}
}
class Err<T, X> implements Result<T, X> {
	error: X;
	constructor (error: X) {
		this.error = error;
	}
	match<R>(whenOk: (arg0 : T) => R, whenErr: (arg0 : X) => R): R {
		return whenErr(this.error);
	}
}
class SingleHead<T> implements Head<T> {
	element: T;
	retrieved: boolean;
	constructor (element: T) {
		this.element = element;
		this.retrieved = false;
	}
	next(): Option<T> {
		if (this.retrieved){
			return new None<T>();
		}
		this.retrieved = true;
		return new Some<T>(this.element);
	}
}
class EmptyHead<T> implements Head<T> {
	next(): Option<T> {
		return new None<T>();
	}
}
class ListCollector<T> implements Collector<T, List<T>> {
	createInitial(): List<T> {
		return Lists.empty();
	}
	fold(current: List<T>, element: T): List<T> {
		return current.add(element);
	}
}
class FlatMapHead<T, R> implements Head<R> {
	mapper: (arg0 : T) => Query<R>;
	head: Head<T>;
	current: Query<R>;
	constructor (head: Head<T>, initial: Query<R>, mapper: (arg0 : T) => Query<R>) {
		this.head = head;
		this.current = initial;
		this.mapper = mapper;
	}
	next(): Option<R> {
		while (true){
			let next = this.current.next();
			if (next.isPresent()){
				return next;
			}
			let tuple = this.head.next().map(this.mapper).toTuple(this.current);
			if (tuple.left){
				this.current = tuple.right;
			}
			else {
				return new None<R>();
			}
		}
	}
}
class Some<T> implements Option<T> {
	value: T;
	constructor (value: T) {
		this.value = value;
	}
	map<R>(mapper: (arg0 : T) => R): Option<R> {
		return new Some<R>(mapper(this.value));
	}
	isEmpty(): boolean {
		return false;
	}
	orElse(other: T): T {
		return this.value;
	}
	orElseGet(supplier: () => T): T {
		return this.value;
	}
	isPresent(): boolean {
		return true;
	}
	ifPresent(consumer: (arg0 : T) => void): void {
		consumer(this.value);
	}
	or(other: () => Option<T>): Option<T> {
		return this;
	}
	flatMap<R>(mapper: (arg0 : T) => Option<R>): Option<R> {
		return mapper(this.value);
	}
	filter(predicate: (arg0 : T) => boolean): Option<T> {
		if (predicate(this.value)){
			return this;
		}
		return new None<T>();
	}
	toTuple(other: T): Tuple<Boolean, T> {
		return new Tuple<Boolean, T>(true, this.value);
	}
	and<R>(other: () => Option<R>): Option<Tuple<T, R>> {
		return other().map((otherValue: R) => new Tuple<T, R>(this.value, otherValue));
	}
}
class None<T> implements Option<T> {
	map<R>(mapper: (arg0 : T) => R): Option<R> {
		return new None<R>();
	}
	isEmpty(): boolean {
		return true;
	}
	orElse(other: T): T {
		return other;
	}
	orElseGet(supplier: () => T): T {
		return supplier();
	}
	isPresent(): boolean {
		return false;
	}
	ifPresent(consumer: (arg0 : T) => void): void {
	}
	or(other: () => Option<T>): Option<T> {
		return other();
	}
	flatMap<R>(mapper: (arg0 : T) => Option<R>): Option<R> {
		return new None<R>();
	}
	filter(predicate: (arg0 : T) => boolean): Option<T> {
		return new None<T>();
	}
	toTuple(other: T): Tuple<Boolean, T> {
		return new Tuple<Boolean, T>(false, other);
	}
	and<R>(other: () => Option<R>): Option<Tuple<T, R>> {
		return new None<Tuple<T, R>>();
	}
}
class Placeholder {
	input: string;
	constructor (input: string) {
		this.input = input;
	}
	generate(): string {
		return Main.generatePlaceholder(this.input);
	}
	isFunctional(): boolean {
		return false;
	}
	findChild(): Option<Value> {
		return new None<Value>();
	}
	asDefinition(): Option<Definition> {
		return new None<Definition>();
	}
	toValue(): Option<Value> {
		return new None<Value>();
	}
	resolve(state: CompileState): Type {
		return Primitive.Unknown;
	}
	isVar(): boolean {
		return false;
	}
	generateBeforeName(): string {
		return "";
	}
	generateAsEnumValue(structureName: string): Option<string> {
		return new None<string>();
	}
}
class MapHead<T, R> implements Head<R> {
	head: Head<T>;
	mapper: (arg0 : T) => R;
	constructor (head: Head<T>, mapper: (arg0 : T) => R) {
		this.head = head;
		this.mapper = mapper;
	}
	next(): Option<R> {
		return this.head.next().map(this.mapper);
	}
}
class Whitespace implements Parameter {
	generate(): string {
		return "";
	}
	asDefinition(): Option<Definition> {
		return new None<Definition>();
	}
}
class Access implements Value {
	child: Value;
	property: string;
	constructor (child: Value, property: string) {
		this.child = child;
		this.property = property;
	}
	generate(): string {
		return this.child.generate() + "." + this.property;
	}
	toValue(): Option<Value> {
		return new Some<Value>(this);
	}
	findChild(): Option<Value> {
		return new Some<Value>(this.child);
	}
	resolve(state: CompileState): Type {
		return Primitive.Unknown;
	}
	generateAsEnumValue(structureName: string): Option<string> {
		return new None<string>();
	}
}
class SymbolNode {
	value: string;
	constructor (value: string) {
		this.value = value;
	}
	generate(): string {
		return this.value;
	}
	resolve(state: CompileState): Type {
		return state.resolve(this.value).orElse(Primitive.Unknown);
	}
	toValue(): Option<Value> {
		return new Some<Value>(this);
	}
	findChild(): Option<Value> {
		return new None<Value>();
	}
	isFunctional(): boolean {
		return false;
	}
	isVar(): boolean {
		return false;
	}
	generateBeforeName(): string {
		return "";
	}
	generateAsEnumValue(structureName: string): Option<string> {
		return new None<string>();
	}
}
class StringValue implements Value {
	value: string;
	constructor (value: string) {
		this.value = value;
	}
	generate(): string {
		return "\"" + this.value + "\"";
	}
	toValue(): Option<Value> {
		return new Some<Value>(this);
	}
	findChild(): Option<Value> {
		return new None<Value>();
	}
	resolve(state: CompileState): Type {
		return Primitive.Unknown;
	}
	generateAsEnumValue(structureName: string): Option<string> {
		return new None<string>();
	}
}
class Not implements Value {
	child: string;
	constructor (child: string) {
		this.child = child;
	}
	generate(): string {
		return this.child;
	}
	toValue(): Option<Value> {
		return new Some<Value>(this);
	}
	findChild(): Option<Value> {
		return new None<Value>();
	}
	resolve(state: CompileState): Type {
		return Primitive.Unknown;
	}
	generateAsEnumValue(structureName: string): Option<string> {
		return new None<string>();
	}
}
class Lambda implements Value {
	paramNames: List<Definition>;
	content: string;
	constructor (paramNames: List<Definition>, content: string) {
		this.paramNames = paramNames;
		this.content = content;
	}
	generate(): string {
		let joinedParamNames = this.paramNames.query().map((definition: Definition) => definition.generate()).collect(new Joiner(", ")).orElse("");
		return "(" + joinedParamNames + ")" + " => " + this.content;
	}
	toValue(): Option<Value> {
		return new Some<Value>(this);
	}
	findChild(): Option<Value> {
		return new None<Value>();
	}
	resolve(state: CompileState): Type {
		return Primitive.Unknown;
	}
	generateAsEnumValue(structureName: string): Option<string> {
		return new None<string>();
	}
}
class Invokable implements Value {
	caller: Caller;
	args: List<Value>;
	constructor (caller: Caller, args: List<Value>) {
		this.caller = caller;
		this.args = args;
	}
	generate(): string {
		let joinedArguments = this.joinArgs();
		return this.caller.generate() + "(" + joinedArguments + ")";
	}
	joinArgs(): string {
		return this.args.query().map((value: Value) => value.generate()).collect(new Joiner(", ")).orElse("");
	}
	toValue(): Option<Value> {
		return new Some<Value>(this);
	}
	findChild(): Option<Value> {
		return new None<Value>();
	}
	resolve(state: CompileState): Type {
		return Primitive.Unknown;
	}
	generateAsEnumValue(structureName: string): Option<string> {
		return new Some<string>("\n\tstatic " + this.caller.generate() + ": " + structureName + " = new " + structureName + "(" + this.joinArgs() + ");");
	}
}
class Operation implements Value {
	left: Value;
	targetInfix: string;
	right: Value;
	constructor (left: Value, targetInfix: string, right: Value) {
		this.left = left;
		this.targetInfix = targetInfix;
		this.right = right;
	}
	generate(): string {
		return this.left.generate() + " " + this.targetInfix + " " + this.right.generate();
	}
	toValue(): Option<Value> {
		return new Some<Value>(this);
	}
	findChild(): Option<Value> {
		return new None<Value>();
	}
	resolve(state: CompileState): Type {
		return Primitive.Unknown;
	}
	generateAsEnumValue(structureName: string): Option<string> {
		return new None<string>();
	}
}
class ConstructionCaller implements Caller {
	right: string;
	constructor (right: string) {
		this.right = right;
	}
	generate(): string {
		return "new " + this.right;
	}
	findChild(): Option<Value> {
		return new None<Value>();
	}
}
class FunctionType implements Type {
	args: List<string>;
	returns: string;
	constructor (args: List<string>, returns: string) {
		this.args = args;
		this.returns = returns;
	}
	generate(): string {
		let joinedArguments = this.args.queryWithIndices().map((tuple: Tuple<number, string>) => "arg" + tuple.left + " : " + tuple.right).collect(new Joiner(", ")).orElse("");
		return "(" + joinedArguments + ") => " + this.returns;
	}
	isFunctional(): boolean {
		return true;
	}
	isVar(): boolean {
		return false;
	}
	generateBeforeName(): string {
		return "";
	}
}
class Generic implements Type {
	base: string;
	args: List<string>;
	constructor (base: string, args: List<string>) {
		this.base = base;
		this.args = args;
	}
	generate(): string {
		return this.base + "<" + Main.generateValueStrings(this.args) + ">";
	}
	isFunctional(): boolean {
		return false;
	}
	isVar(): boolean {
		return false;
	}
	generateBeforeName(): string {
		return "";
	}
}
class Iterators  {
	static fromOption<T>(option: Option<T>): Query<T> {
		return new HeadedQuery<T>(option.map((element: T) => Iterators.getTSingleHead(element)).orElseGet(() => new EmptyHead<T>()));
	}
	static getTSingleHead<T>(element: T): Head<T> {
		return new SingleHead<T>(element);
	}
}
class JVMList<T> implements List<T> {
	list: java.util.List<T>;
	constructor (list: java.util.List<T>) {
		this.list = list;
	}
	add(element: T): List<T> {
		this.list.add(element);
		return this;
	}
	query(): Query<T> {
		return this.queryWithIndices().map((integerTTuple: Tuple<number, T>) => integerTTuple.right);
	}
	size(): number {
		return this.list.size();
	}
	subList0(startInclusive: number, endExclusive: number): List<T> {
		return new JVMList<T>(this.list.subList(startInclusive, endExclusive));
	}
	getLast(): T {
		return this.list.getLast();
	}
	getFirst(): T {
		return this.list.getFirst();
	}
	get(index: number): T {
		return this.list.get(index);
	}
	queryWithIndices(): Query<Tuple<number, T>> {
		let query = new HeadedQuery<number>(new RangeHead(this.list.size()));
		return query.map((index: number) => new Tuple<number, T>(index, this.list.get(index)));
	}
	addAll(others: List<T>): List<T> {
		return others.query().fold(this.toList(), (list1: List<T>, element: T) => list1.add(element));
	}
	toList(): List<T> {
		return this;
	}
	contains(element: T): boolean {
		return this.list.contains(element);
	}
	queryReversed(): Query<T> {
		let query = new HeadedQuery<number>(new RangeHead(this.list.size()));
		return query.map((index: number) => this.list.size() - index - 1).map((index1: number) => this.list.get(index1));
	}
	subList(startInclusive: number, endExclusive: number): Option<List<T>> {
		return new Some<List<T>>(this.subList0(startInclusive, endExclusive));
	}
	findLast(): Option<T> {
		return new Some<T>(this.getLast());
	}
	findFirst(): Option<T> {
		return new Some<T>(this.getFirst());
	}
	find(index: number): Option<T> {
		return new Some<T>(this.get(index));
	}
}
class Lists  {
	static empty<T>(): List<T>;
	static of<T>(...elements: T[]): List<T>;
}
class Strings  {
	static length(stripped: string): number;
	static sliceBetween(input: string, startInclusive: number, endExclusive: number): string;
	static sliceFrom(input: string, startInclusive: number): string;
	static isEmpty(cache: string): boolean;
	static equalsTo(left: string, right: string): boolean;
	static strip(input: string): string;
}
class Characters  {
	static isDigit(c: string): boolean;
	static isLetter(c: string): boolean;
}
class VarArgs implements Type {
	type: Type;
	constructor (type: Type) {
		this.type = type;
	}
	generate(): string {
		return this.type.generate() + "[]";
	}
	isFunctional(): boolean {
		return false;
	}
	isVar(): boolean {
		return false;
	}
	generateBeforeName(): string {
		return "...";
	}
}
class Files  {
	static writeString(target: Path, output: string): Option<IOException>;
	static readString(source: Path): Result<string, IOException>;
}
class Primitive implements Type {
	static String: Primitive = new Primitive("string");
	static Number: Primitive = new Primitive("number");
	static Boolean: Primitive = new Primitive("boolean");
	static Var: Primitive = new Primitive("var");
	static Void: Primitive = new Primitive("void");
	static Unknown: Primitive = new Primitive("unknown");
	value: string;
	constructor (value: string) {
		this.value = value;
	}
	generate(): string {
		return this.value;
	}
	isFunctional(): boolean {
		return false;
	}
	isVar(): boolean {
		return Primitive.Var === this;
	}
	generateBeforeName(): string {
		return "";
	}
}
class Main  {
	static main(): void {
		let source = Paths.get(".", "src", "java", "magma", "Main.java");
		let target = source.resolveSibling("main.ts");
		Files.readString(source).match((input: string) => Main.compileAndWrite(input, target), Some.new).ifPresent(Throwable.printStackTrace);
	}
	static compileAndWrite(input: string, target: Path): Option<IOException> {
		let output = Main.compileRoot(input);
		return Files.writeString(target, output);
	}
	static compileRoot(input: string): string {
		let compiled = Main.compileStatements(CompileState.createInitial(), input, Main.compileRootSegment);
		return compiled.left.output + compiled.right;
	}
	static compileStatements(state: CompileState, input: string, mapper: (arg0 : CompileState, arg1 : string) => Tuple<CompileState, string>): Tuple<CompileState, string> {
		return Main.compileAll(state, input, Main.foldStatements, mapper, Main.mergeStatements);
	}
	static compileAll(state: CompileState, input: string, folder: (arg0 : DivideState, arg1 : string) => DivideState, mapper: (arg0 : CompileState, arg1 : string) => Tuple<CompileState, string>, merger: (arg0 : string, arg1 : string) => string): Tuple<CompileState, string> {
		let folded = Main.parseAll(state, input, folder, (state1: CompileState, s: string) => new Some<Tuple<CompileState, string>>(mapper(state1, s))).orElse(new Tuple<CompileState, List<string>>(state, Lists.empty()));
		return new Tuple<CompileState, string>(folded.left, Main.generateAll(folded.right, merger));
	}
	static generateAll(elements: List<string>, merger: (arg0 : string, arg1 : string) => string): string {
		return elements.query().fold("", merger);
	}
	static parseAll<T>(state: CompileState, input: string, folder: (arg0 : DivideState, arg1 : string) => DivideState, biFunction: (arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, T>>): Option<Tuple<CompileState, List<T>>> {
		return Main.divide(input, folder).query().fold(new Some<Tuple<CompileState, List<T>>>(new Tuple<CompileState, List<T>>(state, Lists.empty())), (maybeCurrent: Option<Tuple<CompileState, List<T>>>, segment: string) => {
			return maybeCurrent.flatMap((current: Tuple<CompileState, List<T>>) => {
				let currentState = current.left;
				let currentElement = current.right;
				return biFunction(currentState, segment).map((mappedTuple: Tuple<CompileState, T>) => {
					let mappedState = mappedTuple.left;
					let mappedElement = mappedTuple.right;
					return new Tuple<CompileState, List<T>>(mappedState, currentElement.add(mappedElement));
				});
			});
		});
	}
	static mergeStatements(cache: string, element: string): string {
		return cache + element;
	}
	static divide(input: string, folder: (arg0 : DivideState, arg1 : string) => DivideState): List<string> {
		let current = DivideState.createInitial(input);
		while (true){
			let poppedTuple0 = current.pop().toTuple(new Tuple<DivideState, string>(current, "\0"));
			if (!poppedTuple0.left){
				break;
			}
			let poppedTuple = poppedTuple0.right;
			let poppedState = poppedTuple.left;
			let popped = poppedTuple.right;
			current = Main.foldSingleQuotes(poppedState, popped).or(() => Main.foldDoubleQuotes(poppedState, popped)).orElseGet(() => folder(poppedState, popped));
		}
		return current.advance().segments;
	}
	static foldDoubleQuotes(state: DivideState, c: string): Option<DivideState> {
		if ("\"" !== c){
			return new None<DivideState>();
		}
		let appended = state.append(c);
		while (true){
			let maybeTuple = appended.popAndAppendToTuple().toTuple(new Tuple<DivideState, string>(appended, "\0"));
			if (!maybeTuple.left){
				break;
			}
			let tuple = maybeTuple.right;
			appended = tuple.left;
			if ("\\" === tuple.right){
				appended = appended.popAndAppendToOption().orElse(appended);
			}
			if ("\"" === tuple.right){
				break;
			}
		}
		return new Some<DivideState>(appended);
	}
	static foldSingleQuotes(state: DivideState, c: string): Option<DivideState> {
		if ("\'" !== c){
			return new None<DivideState>();
		}
		return state.append(c).popAndAppendToTuple().flatMap(Main.foldEscaped).flatMap((state1: DivideState) => state1.popAndAppendToOption());
	}
	static foldEscaped(tuple: Tuple<DivideState, string>): Option<DivideState> {
		let state = tuple.left;
		let c = tuple.right;
		if ("\\" === c){
			return state.popAndAppendToOption();
		}
		return new Some<DivideState>(state);
	}
	static foldStatements(state: DivideState, c: string): DivideState {
		let appended = state.append(c);
		if (";" === c && appended.isLevel()){
			return appended.advance();
		}
		if ("}" === c && appended.isShallow()){
			return appended.advance().exit();
		}
		if ("{" === c || "(" === c){
			return appended.enter();
		}
		if ("}" === c || ")" === c){
			return appended.exit();
		}
		return appended;
	}
	static compileRootSegment(state: CompileState, input: string): Tuple<CompileState, string> {
		return Main.compileOrPlaceholder(state, input, Lists.of(Main.compileWhitespace, Main.compileNamespaced, Main.createStructureRule("class ", "class ")));
	}
	static createStructureRule(sourceInfix: string, targetInfix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, string>> {
		return (state: CompileState, input1: string) => Main.compileFirst(input1, sourceInfix, (_: string, right1: string) => {
			return Main.compileFirst(right1, "{", (beforeContent: string, withEnd: string) => {
				return Main.compileSuffix(Strings.strip(withEnd), "}", (inputContent: string) => {
					return Main.compileLast(beforeContent, " implements ", (s: string, s2: string) => {
						return Main.parseType(state, s2).flatMap((implementingTuple: Tuple<CompileState, Type>) => {
							return Main.getOr(targetInfix, implementingTuple.left, s, inputContent, new Some<Type>(implementingTuple.right));
						});
					}).or(() => {
						return Main.getOr(targetInfix, state, beforeContent, inputContent, new None<Type>());
					});
				});
			});
		});
	}
	static getOr(targetInfix: string, state: CompileState, beforeContent: string, inputContent: string, maybeImplementing: Option<Type>): Option<Tuple<CompileState, string>> {
		return Main.compileFirst(beforeContent, "(", (rawName: string, withParameters: string) => {
			return Main.compileFirst(withParameters, ")", (parametersString: string, _: string) => {
				let name = Strings.strip(rawName);
				let parametersTuple = Main.parseParameters(state, parametersString);
				let parameters = Main.retainDefinitionsFromParameters(parametersTuple.right);
				return Main.assembleStructureWithTypeParams(parametersTuple.left, targetInfix, inputContent, name, parameters, maybeImplementing);
			});
		}).or(() => {
			return Main.assembleStructureWithTypeParams(state, targetInfix, inputContent, beforeContent, Lists.empty(), maybeImplementing);
		});
	}
	static retainDefinitionsFromParameters(parameters: List<Parameter>): List<Definition> {
		return parameters.query().map((parameter: Parameter) => parameter.asDefinition()).flatMap(Iterators.fromOption).collect(new ListCollector<Definition>());
	}
	static assembleStructureWithTypeParams(state: CompileState, infix: string, content: string, beforeParams: string, parameters: List<Definition>, maybeImplementing: Option<Type>): Option<Tuple<CompileState, string>> {
		return Main.compileSuffix(Strings.strip(beforeParams), ">", (withoutTypeParamEnd: string) => {
			return Main.compileFirst(withoutTypeParamEnd, "<", (name: string, typeParamsString: string) => {
				let typeParams = Main.divideValues(typeParamsString);
				return Main.assembleStructure(state, infix, content, name, typeParams, parameters, maybeImplementing);
			});
		}).or(() => {
			return Main.assembleStructure(state, infix, content, beforeParams, Lists.empty(), parameters, maybeImplementing);
		});
	}
	static assembleStructure(state: CompileState, infix: string, content: string, name: string, typeParams: List<string>, parameters: List<Definition>, maybeImplementing: Option<Type>): Option<Tuple<CompileState, string>> {
		let outputContentTuple = Main.compileStatements(state.withStructureName(name), content, Main.compileClassSegment);
		let outputContentState = outputContentTuple.left;
		let outputContent = outputContentTuple.right;
		let constructorString = Main.generateConstructorFromRecordParameters(parameters);
		let joinedTypeParams = Main.joinTypeParams(typeParams);
		let implementingString = Main.generateImplementing(maybeImplementing);
		let generated = infix + name + joinedTypeParams + implementingString + " {" + Main.joinParameters(parameters) + constructorString + outputContent + "\n}\n";
		return new Some<Tuple<CompileState, string>>(new Tuple<CompileState, string>(outputContentState.append(generated), ""));
	}
	static generateImplementing(maybeImplementing: Option<Type>): string {
		return maybeImplementing.map((type: Type) => type.generate()).map((inner: string) => " implements " + inner).orElse("");
	}
	static joinTypeParams(typeParams: List<string>): string {
		return typeParams.query().collect(new Joiner(", ")).map((inner: string) => "<" + inner + ">").orElse("");
	}
	static generateConstructorFromRecordParameters(parameters: List<Definition>): string {
		return parameters.query().map((definition: Definition) => definition.generate()).collect(new Joiner(", ")).map((generatedParameters: string) => Main.generateConstructorWithParameterString(parameters, generatedParameters)).orElse("");
	}
	static generateConstructorWithParameterString(parameters: List<Definition>, parametersString: string): string {
		let constructorAssignments = Main.generateConstructorAssignments(parameters);
		return "\n\tconstructor (" + parametersString + ") {" + constructorAssignments + "\n\t}";
	}
	static generateConstructorAssignments(parameters: List<Definition>): string {
		return parameters.query().map((definition: Definition) => "\n\t\tthis." + definition.name + " = " + definition.name + ";").collect(Joiner.empty()).orElse("");
	}
	static joinParameters(parameters: List<Definition>): string {
		return parameters.query().map((definition: Definition) => definition.generate()).map((generated: string) => "\n\t" + generated + ";").collect(Joiner.empty()).orElse("");
	}
	static compileNamespaced(state: CompileState, input: string): Option<Tuple<CompileState, string>> {
		let stripped = Strings.strip(input);
		if (stripped.startsWith("package ") || stripped.startsWith("import ")){
			return new Some<Tuple<CompileState, string>>(new Tuple<CompileState, string>(state, ""));
		}
		else {
			return new None<Tuple<CompileState, string>>();
		}
	}
	static compileOrPlaceholder(state: CompileState, input: string, rules: List<(arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, string>>>): Tuple<CompileState, string> {
		return Main.or(state, input, rules).orElseGet(() => new Tuple<CompileState, string>(state, Main.generatePlaceholder(input)));
	}
	static or<T>(state: CompileState, input: string, rules: List<(arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, T>>>): Option<Tuple<CompileState, T>> {
		return rules.query().map((rule: (arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, T>>) => rule.apply(state, input)).flatMap(Iterators.fromOption).next();
	}
	static compileClassSegment(state1: CompileState, input1: string): Tuple<CompileState, string> {
		return Main.compileOrPlaceholder(state1, input1, Lists.of(Main.compileWhitespace, Main.createStructureRule("class ", "class "), Main.createStructureRule("interface ", "interface "), Main.createStructureRule("record ", "class "), Main.createStructureRule("enum ", "class "), Main.compileMethod, Main.compileFieldDefinition));
	}
	static compileMethod(state: CompileState, input: string): Option<Tuple<CompileState, string>> {
		return Main.compileFirst(input, "(", (beforeParams: string, withParams: string) => {
			let strippedBeforeParams = Strings.strip(beforeParams);
			return Main.compileLast(strippedBeforeParams, " ", (_: string, name: string) => {
				if (state.maybeStructureName.filter((anObject: string) => Strings.equalsTo(name, anObject)).isPresent()){
					return Main.compileMethodWithBeforeParams(state, new ConstructorHeader(), withParams);
				}
				return new None<Tuple<CompileState, string>>();
			}).or(() => {
				if (state.maybeStructureName.filter((anObject: string) => Strings.equalsTo(strippedBeforeParams, anObject)).isPresent()){
					return Main.compileMethodWithBeforeParams(state, new ConstructorHeader(), withParams);
				}
				return new None<Tuple<CompileState, string>>();
			}).or(() => {
				return Main.parseDefinition(state, beforeParams).flatMap((tuple: Tuple<CompileState, Definition>) => Main.compileMethodWithBeforeParams(tuple.left, tuple.right, withParams));
			});
		});
	}
	static compileMethodWithBeforeParams(state: CompileState, header: MethodHeader, withParams: string): Option<Tuple<CompileState, string>> {
		return Main.compileFirst(withParams, ")", (params: string, afterParams: string) => {
			let parametersTuple = Main.parseParameters(state, params);
			let parametersState = parametersTuple.left;
			let parameters = parametersTuple.right;
			let definitions = Main.retainDefinitionsFromParameters(parameters);
			let joinedDefinitions = definitions.query().map((definition: Definition) => definition.generate()).collect(new Joiner(", ")).orElse("");
			let headerGenerated = header.generateWithAfterName("(" + joinedDefinitions + ")");
			if (header.hasAnnotation("Actual")){
				return new Some<Tuple<CompileState, string>>(new Tuple<CompileState, string>(parametersState, "\n\t" + headerGenerated + ";"));
			}
			return Main.compilePrefix(Strings.strip(afterParams), "{", (withoutContentStart: string) => {
				return Main.compileSuffix(Strings.strip(withoutContentStart), "}", (withoutContentEnd: string) => {
					let statementsTuple = Main.compileFunctionStatements(parametersState.enterDepth().enterDepth().defineAll(definitions), withoutContentEnd);
					return new Some<Tuple<CompileState, string>>(new Tuple<CompileState, string>(statementsTuple.left.exitDepth().exitDepth(), "\n\t" + headerGenerated + " {" + statementsTuple.right + "\n\t}"));
				});
			}).or(() => {
				if (Strings.equalsTo(";", Strings.strip(afterParams))){
					return new Some<Tuple<CompileState, string>>(new Tuple<CompileState, string>(parametersState, "\n\t" + headerGenerated + ";"));
				}
				return new None<Tuple<CompileState, string>>();
			});
		});
	}
	static parseParameters(state: CompileState, params: string): Tuple<CompileState, List<Parameter>> {
		return Main.parseValuesOrEmpty(state, params, (state1: CompileState, s: string) => new Some<Tuple<CompileState, Parameter>>(Main.parseParameterOrPlaceholder(state1, s)));
	}
	static compileFunctionStatements(state: CompileState, input: string): Tuple<CompileState, string> {
		return Main.compileStatements(state, input, Main.compileFunctionSegment);
	}
	static compileFunctionSegment(state: CompileState, input: string): Tuple<CompileState, string> {
		return Main.compileOrPlaceholder(state, input, Lists.of(Main.compileWhitespace, Main.compileEmptySegment, Main.compileBlock, Main.compileFunctionStatement, Main.compileReturnWithoutSuffix));
	}
	static compileEmptySegment(state: CompileState, input: string): Option<Tuple<CompileState, string>> {
		if (Strings.equalsTo(";", Strings.strip(input))){
			return new Some<Tuple<CompileState, string>>(new Tuple<CompileState, string>(state, ";"));
		}
		else {
			return new None<Tuple<CompileState, string>>();
		}
	}
	static compileReturnWithoutSuffix(state1: CompileState, input1: string): Option<Tuple<CompileState, string>> {
		return Main.compileReturn(input1, (withoutPrefix: string) => Main.compileValue(state1, withoutPrefix)).map((tuple: Tuple<CompileState, string>) => new Tuple<CompileState, string>(tuple.left, Main.generateIndent(state1.depth) + tuple.right));
	}
	static compileBlock(state: CompileState, input: string): Option<Tuple<CompileState, string>> {
		return Main.compileSuffix(Strings.strip(input), "}", (withoutEnd: string) => {
			return Main.compileSplit(Main.splitFoldedLast(withoutEnd, "", Main.foldBlockStarts), (beforeContentWithEnd: string, content: string) => {
				return Main.compileSuffix(beforeContentWithEnd, "{", (beforeContent: string) => {
					return Main.compileBlockHeader(state, beforeContent).flatMap((headerTuple: Tuple<CompileState, string>) => {
						let contentTuple = Main.compileFunctionStatements(headerTuple.left.enterDepth(), content);
						let indent = Main.generateIndent(state.depth());
						return new Some<Tuple<CompileState, string>>(new Tuple<CompileState, string>(contentTuple.left.exitDepth(), indent + headerTuple.right + "{" + contentTuple.right + indent + "}"));
					});
				});
			});
		});
	}
	static foldBlockStarts(state: DivideState, c: string): DivideState {
		let appended = state.append(c);
		if ("{" === c){
			let entered = appended.enter();
			if (entered.isShallow()){
				return entered.advance();
			}
			else {
				return entered;
			}
		}
		if ("}" === c){
			return appended.exit();
		}
		return appended;
	}
	static compileBlockHeader(state: CompileState, input: string): Option<Tuple<CompileState, string>> {
		return Main.or(state, input, Lists.of(Main.createConditionalRule("if"), Main.createConditionalRule("while"), Main.compileElse));
	}
	static createConditionalRule(prefix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, string>> {
		return (state1: CompileState, input1: string) => Main.compilePrefix(Strings.strip(input1), prefix, (withoutPrefix: string) => {
			let strippedCondition = Strings.strip(withoutPrefix);
			return Main.compilePrefix(strippedCondition, "(", (withoutConditionStart: string) => {
				return Main.compileSuffix(withoutConditionStart, ")", (withoutConditionEnd: string) => {
					let tuple = Main.compileValueOrPlaceholder(state1, withoutConditionEnd);
					return new Some<Tuple<CompileState, string>>(new Tuple<CompileState, string>(tuple.left, prefix + " (" + tuple.right + ")"));
				});
			});
		});
	}
	static compileElse(state: CompileState, input: string): Option<Tuple<CompileState, string>> {
		if (Strings.equalsTo("else", Strings.strip(input))){
			return new Some<Tuple<CompileState, string>>(new Tuple<CompileState, string>(state, "else "));
		}
		else {
			return new None<Tuple<CompileState, string>>();
		}
	}
	static compileFunctionStatement(state: CompileState, input: string): Option<Tuple<CompileState, string>> {
		return Main.compileSuffix(Strings.strip(input), ";", (withoutEnd: string) => {
			let valueTuple = Main.compileFunctionStatementValue(state, withoutEnd);
			return new Some<Tuple<CompileState, string>>(new Tuple<CompileState, string>(valueTuple.left, Main.generateIndent(state.depth()) + valueTuple.right + ";"));
		});
	}
	static generateIndent(indent: number): string {
		return "\n" + "\t".repeat(indent);
	}
	static compileFunctionStatementValue(state: CompileState, withoutEnd: string): Tuple<CompileState, string> {
		return Main.compileOrPlaceholder(state, withoutEnd, Lists.of(Main.compileReturnWithValue, Main.compileAssignment, (state1: CompileState, input: string) => Main.parseInvokable(state1, input).map((tuple: Tuple<CompileState, Value>) => new Tuple<CompileState, string>(tuple.left, tuple.right.generate())), Main.createPostRule("++"), Main.createPostRule("--"), Main.compileBreak));
	}
	static compileBreak(state: CompileState, input: string): Option<Tuple<CompileState, string>> {
		if (Strings.equalsTo("break", Strings.strip(input))){
			return new Some<Tuple<CompileState, string>>(new Tuple<CompileState, string>(state, "break"));
		}
		else {
			return new None<Tuple<CompileState, string>>();
		}
	}
	static createPostRule(suffix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, string>> {
		return (state1: CompileState, input: string) => Main.compileSuffix(Strings.strip(input), suffix, (child: string) => {
			let tuple = Main.compileValueOrPlaceholder(state1, child);
			return new Some<Tuple<CompileState, string>>(new Tuple<CompileState, string>(tuple.left, tuple.right + suffix));
		});
	}
	static compileReturnWithValue(state: CompileState, input: string): Option<Tuple<CompileState, string>> {
		return Main.compileReturn(input, (value1: string) => Main.compileValue(state, value1));
	}
	static compileReturn(input: string, mapper: (arg0 : string) => Option<Tuple<CompileState, string>>): Option<Tuple<CompileState, string>> {
		return Main.compilePrefix(Strings.strip(input), "return ", (value: string) => {
			return mapper(value).flatMap((tuple: Tuple<CompileState, string>) => {
				return new Some<Tuple<CompileState, string>>(new Tuple<CompileState, string>(tuple.left, "return " + tuple.right));
			});
		});
	}
	static parseInvokable(state: CompileState, input: string): Option<Tuple<CompileState, Value>> {
		return Main.compileSuffix(Strings.strip(input), ")", (withoutEnd: string) => {
			return Main.compileSplit(Main.splitFoldedLast(withoutEnd, "", Main.foldInvocationStarts), (callerWithArgStart: string, args: string) => {
				return Main.compileSuffix(callerWithArgStart, "(", (callerString: string) => {
					return Main.compilePrefix(Strings.strip(callerString), "new ", (type: string) => {
						return Main.compileType(state, type).flatMap((callerTuple: Tuple<CompileState, string>) => {
							let callerState = callerTuple.right;
							let caller = callerTuple.left;
							return Main.assembleInvokable(caller, new ConstructionCaller(callerState), args);
						});
					}).or(() => {
						return Main.parseValue(state, callerString).flatMap((callerTuple: Tuple<CompileState, Value>) => {
							return Main.assembleInvokable(callerTuple.left, callerTuple.right, args);
						});
					});
				});
			});
		});
	}
	static splitFoldedLast(input: string, delimiter: string, folder: (arg0 : DivideState, arg1 : string) => DivideState): Option<Tuple<string, string>> {
		return Main.splitFolded(input, folder, (divisions1: List<string>) => Main.selectLast(divisions1, delimiter));
	}
	static splitFolded(input: string, folder: (arg0 : DivideState, arg1 : string) => DivideState, selector: (arg0 : List<string>) => Option<Tuple<string, string>>): Option<Tuple<string, string>> {
		let divisions = Main.divide(input, folder);
		if (2 > divisions.size()){
			return new None<Tuple<string, string>>();
		}
		return selector(divisions);
	}
	static selectLast(divisions: List<string>, delimiter: string): Option<Tuple<string, string>> {
		let beforeLast = divisions.subList(0, divisions.size() - 1).orElse(divisions);
		let last = divisions.findLast().orElse("");
		let joined = beforeLast.query().collect(new Joiner(delimiter)).orElse("");
		return new Some<Tuple<string, string>>(new Tuple<string, string>(joined, last));
	}
	static foldInvocationStarts(state: DivideState, c: string): DivideState {
		let appended = state.append(c);
		if ("(" === c){
			let entered = appended.enter();
			if (entered.isShallow()){
				return entered.advance();
			}
			else {
				return entered;
			}
		}
		if (")" === c){
			return appended.exit();
		}
		return appended;
	}
	static assembleInvokable(state: CompileState, oldCaller: Caller, argsString: string): Option<Tuple<CompileState, Value>> {
		return Main.parseValues(state, argsString, (state1: CompileState, s: string) => Main.parseArgument(state1, s)).flatMap((argsTuple: Tuple<CompileState, List<Argument>>) => {
			let argsState = argsTuple.left;
			let args = Main.retain(argsTuple.right, Argument.toValue);
			let newCaller = Main.transformCaller(argsState, oldCaller);
			return new Some<Tuple<CompileState, Value>>(new Tuple<CompileState, Value>(argsState, new Invokable(newCaller, args)));
		});
	}
	static transformCaller(state: CompileState, oldCaller: Caller): Caller {
		return oldCaller.findChild().flatMap((parent: Value) => {
			let parentType = parent.resolve(state);
			if (parentType.isFunctional()){
				return new Some<Caller>(parent);
			}
			return new None<Caller>();
		}).orElse(oldCaller);
	}
	static retain<T, R>(args: List<T>, mapper: (arg0 : T) => Option<R>): List<R> {
		return args.query().map(mapper).flatMap(Iterators.fromOption).collect(new ListCollector<R>());
	}
	static parseArgumentOrPlaceholder(state1: CompileState, input: string): Tuple<CompileState, Argument> {
		return Main.parseArgument(state1, input).orElseGet(() => new Tuple<CompileState, Argument>(state1, new Placeholder(input)));
	}
	static parseArgument(state1: CompileState, input: string): Option<Tuple<CompileState, Argument>> {
		return Main.parseValue(state1, input).map((tuple: Tuple<CompileState, Value>) => new Tuple<CompileState, Argument>(tuple.left, tuple.right));
	}
	static compileAssignment(state: CompileState, input: string): Option<Tuple<CompileState, string>> {
		return Main.compileFirst(input, "=", (destination: string, source: string) => {
			let sourceTuple = Main.compileValueOrPlaceholder(state, source);
			let destinationTuple = Main.compileValue(sourceTuple.left, destination).or(() => Main.parseDefinition(sourceTuple.left, destination).map((tuple: Tuple<CompileState, Definition>) => new Tuple<CompileState, string>(tuple.left, "let " + tuple.right.generate()))).orElseGet(() => new Tuple<CompileState, string>(sourceTuple.left, Main.generatePlaceholder(destination)));
			return new Some<Tuple<CompileState, string>>(new Tuple<CompileState, string>(destinationTuple.left, destinationTuple.right + " = " + sourceTuple.right));
		});
	}
	static compileValueOrPlaceholder(state: CompileState, input: string): Tuple<CompileState, string> {
		return Main.compileValue(state, input).orElseGet(() => new Tuple<CompileState, string>(state, Main.generatePlaceholder(input)));
	}
	static compileValue(state: CompileState, input: string): Option<Tuple<CompileState, string>> {
		return Main.parseValue(state, input).map((tuple: Tuple<CompileState, Value>) => new Tuple<CompileState, string>(tuple.left, tuple.right.generate()));
	}
	static parseValue(state: CompileState, input: string): Option<Tuple<CompileState, Value>> {
		return Main.or(state, input, Lists.of(Main.parseLambda, Main.createOperatorRule("+"), Main.createOperatorRule("-"), Main.createOperatorRule("<="), Main.createOperatorRule("<"), Main.createOperatorRule("&&"), Main.createOperatorRule("||"), Main.createOperatorRule(">"), Main.createOperatorRule(">="), Main.parseInvokable, Main.createAccessRule("."), Main.createAccessRule("::"), Main.parseSymbol, Main.parseNot, Main.parseNumber, Main.createOperatorRuleWithDifferentInfix("==", "==="), Main.createOperatorRuleWithDifferentInfix("!=", "!=="), Main.createTextRule("\""), Main.createTextRule("'")));
	}
	static createTextRule(slice: string): (arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, Value>> {
		return (state1: CompileState, input1: string) => {
			let stripped = Strings.strip(input1);
			return Main.compilePrefix(stripped, slice, (s: string) => {
				return Main.compileSuffix(s, slice, (s1: string) => new Some<Tuple<CompileState, Value>>(new Tuple<CompileState, Value>(state1, new StringValue(s1))));
			});
		};
	}
	static parseNot(state: CompileState, input: string): Option<Tuple<CompileState, Value>> {
		return Main.compilePrefix(Strings.strip(input), "!", (withoutPrefix: string) => {
			let childTuple = Main.compileValueOrPlaceholder(state, withoutPrefix);
			let childState = childTuple.left;
			let child = "!" + childTuple.right;
			return new Some<Tuple<CompileState, Value>>(new Tuple<CompileState, Value>(childState, new Not(child)));
		});
	}
	static parseLambda(state: CompileState, input: string): Option<Tuple<CompileState, Value>> {
		return Main.compileFirst(input, "->", (beforeArrow: string, afterArrow: string) => {
			let strippedBeforeArrow = Strings.strip(beforeArrow);
			return Main.compilePrefix(strippedBeforeArrow, "(", (withoutStart: string) => {
				return Main.compileSuffix(withoutStart, ")", (withoutEnd: string) => {
					/*return Main.parseValues(state, withoutEnd, (CompileState state1, String s) -> Main.parseParameter(state1, s)).flatMap(paramNames -> {
                        return Main.compileLambdaWithParameterNames(paramNames.left, Main.retainDefinitionsFromParameters(paramNames.right), afterArrow);
                    })*/;
				});
			});
		});
	}
	static compileLambdaWithParameterNames(state: CompileState, paramNames: List<Definition>, afterArrow: string): Option<Tuple<CompileState, Value>> {
		let strippedAfterArrow = Strings.strip(afterArrow);
		return Main.compilePrefix(strippedAfterArrow, "{", (withoutContentStart: string) => {
			return Main.compileSuffix(withoutContentStart, "}", (withoutContentEnd: string) => {
				let statementsTuple = Main.compileFunctionStatements(state.enterDepth().defineAll(paramNames), withoutContentEnd);
				let statementsState = statementsTuple.left;
				let statements = statementsTuple.right;
				let exited = statementsState.exitDepth();
				return Main.assembleLambda(exited, paramNames, "{" + statements + Main.generateIndent(exited.depth) + "}");
			});
		}).or(() => {
			return Main.compileValue(state, strippedAfterArrow).flatMap((tuple: Tuple<CompileState, string>) => {
				return Main.assembleLambda(tuple.left, paramNames, tuple.right);
			});
		});
	}
	static assembleLambda(exited: CompileState, paramNames: List<Definition>, content: string): Option<Tuple<CompileState, Value>> {
		return new Some<Tuple<CompileState, Value>>(new Tuple<CompileState, Value>(exited, new Lambda(paramNames, content)));
	}
	static createOperatorRule(infix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, Value>> {
		return Main.createOperatorRuleWithDifferentInfix(infix, infix);
	}
	static createAccessRule(infix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, Value>> {
		return (state: CompileState, input: string) => Main.compileLast(input, infix, (childString: string, rawProperty: string) => {
			let property = Strings.strip(rawProperty);
			if (!Main.isSymbol(property)){
				return new None<Tuple<CompileState, Value>>();
			}
			return Main.parseValue(state, childString).flatMap((childTuple: Tuple<CompileState, Value>) => {
				let childState = childTuple.left;
				let child = childTuple.right;
				return new Some<Tuple<CompileState, Value>>(new Tuple<CompileState, Value>(childState, new Access(child, property)));
			});
		});
	}
	static createOperatorRuleWithDifferentInfix(sourceInfix: string, targetInfix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, Value>> {
		return (state1: CompileState, input1: string) => {
			return Main.compileSplit(Main.splitFolded(input1, Main.foldOperator(sourceInfix), (divisions: List<string>) => Main.selectFirst(divisions, sourceInfix)), (leftString: string, rightString: string) => {
				return Main.parseValue(state1, leftString).flatMap((leftTuple: Tuple<CompileState, Value>) => {
					return Main.parseValue(leftTuple.left, rightString).flatMap((rightTuple: Tuple<CompileState, Value>) => {
						let left = leftTuple.right;
						let right = rightTuple.right;
						return new Some<Tuple<CompileState, Value>>(new Tuple<CompileState, Value>(rightTuple.left, new Operation(left, targetInfix, right)));
					});
				});
			});
		};
	}
	static selectFirst(divisions: List<string>, delimiter: string): Option<Tuple<string, string>> {
		let first = divisions.findFirst().orElse("");
		let afterFirst = divisions.subList(1, divisions.size()).orElse(divisions).query().collect(new Joiner(delimiter)).orElse("");
		return new Some<Tuple<string, string>>(new Tuple<string, string>(first, afterFirst));
	}
	static foldOperator(infix: string): (arg0 : DivideState, arg1 : string) => DivideState {
		return (state: DivideState, c: string) => {
			if (c === infix.charAt(0) && state.startsWith(Strings.sliceFrom(infix, 1))){
				let length = Strings.length(infix) - 1;
				let counter = 0;
				let current = state;
				while (counter < length){
					counter++;
					current = current.pop().map((tuple: Tuple<DivideState, string>) => tuple.left).orElse(current);
				}
				return current.advance();
			}
			return state.append(c);
		};
	}
	static parseNumber(state: CompileState, input: string): Option<Tuple<CompileState, Value>> {
		let stripped = Strings.strip(input);
		if (Main.isNumber(stripped)){
			return new Some<Tuple<CompileState, Value>>(new Tuple<CompileState, Value>(state, new SymbolNode(stripped)));
		}
		else {
			return new None<Tuple<CompileState, Value>>();
		}
	}
	static isNumber(input: string): boolean {
		let query = new HeadedQuery<number>(new RangeHead(Strings.length(input)));
		return query.map(input.charAt).allMatch((c: string) => Characters.isDigit(c));
	}
	static parseSymbol(state: CompileState, input: string): Option<Tuple<CompileState, Value>> {
		let stripped = Strings.strip(input);
		if (Main.isSymbol(stripped)){
			return new Some<Tuple<CompileState, Value>>(new Tuple<CompileState, Value>(state, new SymbolNode(stripped)));
		}
		else {
			return new None<Tuple<CompileState, Value>>();
		}
	}
	static isSymbol(input: string): boolean {
		let query = new HeadedQuery<number>(new RangeHead(Strings.length(input)));
		return query.allMatch((index: number) => Main.isSymbolChar(index, input.charAt(index)));
	}
	static isSymbolChar(index: number, c: string): boolean {
		return "_" === c || Characters.isLetter(c) || (0 !== index && Characters.isDigit(c));
	}
	static compilePrefix<T>(input: string, infix: string, mapper: (arg0 : string) => Option<Tuple<CompileState, T>>): Option<Tuple<CompileState, T>> {
		if (!input.startsWith(infix)){
			return new None<Tuple<CompileState, T>>();
		}
		let slice = Strings.sliceFrom(input, Strings.length(infix));
		return mapper(slice);
	}
	static compileWhitespace(state: CompileState, input: string): Option<Tuple<CompileState, string>> {
		return Main.parseWhitespace(state, input).map((tuple: Tuple<CompileState, Whitespace>) => new Tuple<CompileState, string>(tuple.left, tuple.right.generate()));
	}
	static parseWhitespace(state: CompileState, input: string): Option<Tuple<CompileState, Whitespace>> {
		if (input.isBlank()){
			return new Some<Tuple<CompileState, Whitespace>>(new Tuple<CompileState, Whitespace>(state, new Whitespace()));
		}
		return new None<Tuple<CompileState, Whitespace>>();
	}
	static compileFieldDefinition(state: CompileState, input: string): Option<Tuple<CompileState, string>> {
		return Main.compileSuffix(Strings.strip(input), ";", (withoutEnd: string) => {
			return Main.getTupleOption(state, withoutEnd).or(() => Main.compileEnumValues(state, withoutEnd));
		});
	}
	static getTupleOption(state: CompileState, withoutEnd: string): Option<Tuple<CompileState, string>> {
		return Main.parseParameter(state, withoutEnd).flatMap((definitionTuple: Tuple<CompileState, Parameter>) => {
			return new Some<Tuple<CompileState, string>>(new Tuple<CompileState, string>(definitionTuple.left, "\n\t" + definitionTuple.right.generate() + ";"));
		});
	}
	static compileEnumValues(state: CompileState, withoutEnd: string): Option<Tuple<CompileState, string>> {
		return Main.parseValues(state, withoutEnd, (state1: CompileState, s: string) => {
			return Main.parseInvokable(state1, s).flatMap((tuple: Tuple<CompileState, Value>) => {
				let structureName = state.maybeStructureName.orElse("");
				return tuple.right.generateAsEnumValue(structureName).map((stringOption: string) => {
					return new Tuple<CompileState, string>(tuple.left, stringOption);
				});
			});
		}).map((tuple: Tuple<CompileState, List<string>>) => {
			return new Tuple<CompileState, string>(tuple.left, tuple.right.query().collect(new Joiner("")).orElse(""));
		});
	}
	static parseParameterOrPlaceholder(state: CompileState, input: string): Tuple<CompileState, Parameter> {
		return Main.parseParameter(state, input).orElseGet(() => new Tuple<CompileState, Parameter>(state, new Placeholder(input)));
	}
	static parseParameter(state: CompileState, input: string): Option<Tuple<CompileState, Parameter>> {
		return Main.parseWhitespace(state, input).map((tuple: Tuple<CompileState, Whitespace>) => new Tuple<CompileState, Parameter>(tuple.left, tuple.right)).or(() => Main.parseDefinition(state, input).map((tuple: Tuple<CompileState, Definition>) => new Tuple<CompileState, Parameter>(tuple.left, tuple.right)));
	}
	static parseDefinition(state: CompileState, input: string): Option<Tuple<CompileState, Definition>> {
		return Main.compileLast(Strings.strip(input), " ", (beforeName: string, name: string) => {
			return Main.compileSplit(Main.splitFoldedLast(Strings.strip(beforeName), " ", Main.foldTypeSeparators), (beforeType: string, type: string) => {
				return Main.compileLast(Strings.strip(beforeType), "\n", (annotationsString: string, afterAnnotations: string) => {
					let annotations = Main.parseAnnotations(annotationsString);
					return Main.parseDefinitionWithAnnotations(state, annotations, afterAnnotations, type, name);
				}).or(() => {
					return Main.parseDefinitionWithAnnotations(state, Lists.empty(), beforeType, type, name);
				});
			}).or(() => {
				return Main.parseDefinitionWithTypeParameters(state, Lists.empty(), Lists.empty(), Lists.empty(), beforeName, name);
			});
		});
	}
	static parseAnnotations(s: string): List<string> {
		return Main.divide(s, (state1: DivideState, c: string) => Main.foldDelimited(state1, c, "\n")).query().map((s2: string) => Strings.strip(s2)).filter((value: string) => !Strings.isEmpty(value)).filter((value: string) => 1 <= Strings.length(value)).map((value: string) => Strings.sliceFrom(value, 1)).map((s1: string) => s1.strip()).filter((value: string) => !Strings.isEmpty(value)).collect(new ListCollector<string>());
	}
	static parseDefinitionWithAnnotations(state: CompileState, annotations: List<string>, beforeType: string, type: string, name: string): Option<Tuple<CompileState, Definition>> {
		return Main.compileSuffix(Strings.strip(beforeType), ">", (withoutTypeParamEnd: string) => {
			return Main.compileFirst(withoutTypeParamEnd, "<", (beforeTypeParams: string, typeParamsString: string) => {
				let typeParams = Main.divideValues(typeParamsString);
				return Main.parseDefinitionWithTypeParameters(state, annotations, typeParams, Main.parseModifiers(beforeTypeParams), type, name);
			});
		}).or(() => {
			let divided = Main.parseModifiers(beforeType);
			return Main.parseDefinitionWithTypeParameters(state, annotations, Lists.empty(), divided, type, name);
		});
	}
	static parseModifiers(beforeType: string): List<string> {
		return Main.divide(Strings.strip(beforeType), (state1: DivideState, c: string) => Main.foldDelimited(state1, c, " ")).query().map((s: string) => s.strip()).filter((value: string) => !Strings.isEmpty(value)).collect(new ListCollector<string>());
	}
	static foldDelimited(state1: DivideState, c: string, delimiter: string): DivideState {
		if (delimiter === c){
			return state1.advance();
		}
		return state1.append(c);
	}
	static divideValues(input: string): List<string> {
		return Main.divide(input, Main.foldValues).query().map((input1: string) => Strings.strip(input1)).filter((value: string) => !Strings.isEmpty(value)).collect(new ListCollector<string>());
	}
	static foldTypeSeparators(state: DivideState, c: string): DivideState {
		if (" " === c && state.isLevel()){
			return state.advance();
		}
		let appended = state.append(c);
		if ("<" === c){
			return appended.enter();
		}
		if (">" === c){
			return appended.exit();
		}
		return appended;
	}
	static parseDefinitionWithTypeParameters(state: CompileState, annotations: List<string>, typeParams: List<string>, oldModifiers: List<string>, type: string, name: string): Option<Tuple<CompileState, Definition>> {
		return Main.parseType(state, type).flatMap((typeTuple: Tuple<CompileState, Type>) => {
			let newModifiers = Main.modifyModifiers(oldModifiers);
			let generated = new Definition(annotations, newModifiers, typeParams, typeTuple.right, name);
			return new Some<Tuple<CompileState, Definition>>(new Tuple<CompileState, Definition>(typeTuple.left, generated));
		});
	}
	static modifyModifiers(oldModifiers: List<string>): List<string> {
		if (oldModifiers.contains("static")){
			return Lists.of("static");
		}
		return Lists.empty();
	}
	static parseTypeOrPlaceholder(state: CompileState, type: string): Tuple<CompileState, Type> {
		return Main.parseType(state, type).map((tuple: Tuple<CompileState, Type>) => new Tuple<CompileState, Type>(tuple.left, tuple.right)).orElseGet(() => new Tuple<CompileState, Type>(state, new Placeholder(type)));
	}
	static compileTypeOrPlaceholder(state: CompileState, type: string): Tuple<CompileState, string> {
		return Main.compileType(state, type).orElseGet(() => new Tuple<CompileState, string>(state, Main.generatePlaceholder(type)));
	}
	static compileType(state: CompileState, type: string): Option<Tuple<CompileState, string>> {
		return Main.parseType(state, type).map((tuple: Tuple<CompileState, Type>) => new Tuple<CompileState, string>(tuple.left, tuple.right.generate()));
	}
	static parseType(state: CompileState, type: string): Option<Tuple<CompileState, Type>> {
		return Main.or(state, type, Lists.of(Main.parseVarArgs, Main.parseGeneric, Main.parsePrimitive, Main.parseSymbolType));
	}
	static parseVarArgs(state: CompileState, input: string): Option<Tuple<CompileState, Type>> {
		let stripped = input.strip();
		return Main.compileSuffix(stripped, "...", (s: string) => {
			let child = Main.parseTypeOrPlaceholder(state, s);
			return new Some<Tuple<CompileState, Type>>(new Tuple<CompileState, Type>(child.left, new VarArgs(child.right)));
		});
	}
	static parseSymbolType(state: CompileState, input: string): Option<Tuple<CompileState, Type>> {
		let stripped = Strings.strip(input);
		if (Main.isSymbol(stripped)){
			return new Some<Tuple<CompileState, Type>>(new Tuple<CompileState, Type>(state, new SymbolNode(stripped)));
		}
		return new None<Tuple<CompileState, Type>>();
	}
	static parsePrimitive(state: CompileState, input: string): Option<Tuple<CompileState, Type>> {
		return Main.findPrimitiveValue(Strings.strip(input)).map((result: Type) => new Tuple<CompileState, Type>(state, result));
	}
	static findPrimitiveValue(input: string): Option<Type> {
		let stripped = Strings.strip(input);
		if (Strings.equalsTo("char", stripped) || Strings.equalsTo("Character", stripped) || Strings.equalsTo("String", stripped)){
			return new Some<Type>(Primitive.String);
		}
		if (Strings.equalsTo("int", stripped) || Strings.equalsTo("Integer", stripped)){
			return new Some<Type>(Primitive.Number);
		}
		if (Strings.equalsTo("boolean", stripped)){
			return new Some<Type>(Primitive.Boolean);
		}
		if (Strings.equalsTo("var", stripped)){
			return new Some<Type>(Primitive.Var);
		}
		if (Strings.equalsTo("void", stripped)){
			return new Some<Type>(Primitive.Void);
		}
		return new None<Type>();
	}
	static parseGeneric(state: CompileState, input: string): Option<Tuple<CompileState, Type>> {
		return Main.compileSuffix(Strings.strip(input), ">", (withoutEnd: string) => {
			return Main.compileFirst(withoutEnd, "<", (baseString: string, argsString: string) => {
				let argsTuple = Main.parseValuesOrEmpty(state, argsString, (state1: CompileState, s: string) => Main.compileTypeArgument(state1, s));
				let argsState = argsTuple.left;
				let args = argsTuple.right;
				let base = Strings.strip(baseString);
				return Main.assembleFunctionType(argsState, base, args).or(() => {
					return new Some<Tuple<CompileState, Type>>(new Tuple<CompileState, Type>(argsState, new Generic(base, args)));
				});
			});
		});
	}
	static assembleFunctionType(state: CompileState, base: string, args: List<string>): Option<Tuple<CompileState, Type>> {
		return Main.mapFunctionType(base, args).map((generated: Type) => new Tuple<CompileState, Type>(state, generated));
	}
	static mapFunctionType(base: string, args: List<string>): Option<Type> {
		if (Strings.equalsTo("Function", base)){
			return args.findFirst().and(() => args.find(1)).map((tuple: Tuple<string, string>) => new FunctionType(Lists.of(tuple.left), tuple.right));
		}
		if (Strings.equalsTo("BiFunction", base)){
			return args.find(0).and(() => args.find(1)).and(() => args.find(2)).map((tuple: Tuple<Tuple<string, string>, string>) => new FunctionType(Lists.of(tuple.left.left, tuple.left.right), tuple.right));
		}
		if (Strings.equalsTo("Supplier", base)){
			return args.findFirst().map((first: string) => {
				return new FunctionType(Lists.empty(), first);
			});
		}
		if (Strings.equalsTo("Consumer", base)){
			return args.findFirst().map((first: string) => {
				return new FunctionType(Lists.of(first), "void");
			});
		}
		if (Strings.equalsTo("Predicate", base)){
			return args.findFirst().map((first: string) => {
				return new FunctionType(Lists.of(first), "boolean");
			});
		}
		return new None<Type>();
	}
	static compileTypeArgument(state: CompileState, input: string): Option<Tuple<CompileState, string>> {
		return Main.or(state, input, Lists.of((state2: CompileState, input1: string) => Main.compileWhitespace(state2, input1), (state1: CompileState, type: string) => Main.compileType(state1, type)));
	}
	static generateValueStrings(values: List<string>): string {
		return Main.generateAll(values, Main.mergeValues);
	}
	static parseValuesOrEmpty<T>(state: CompileState, input: string, mapper: (arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, T>>): Tuple<CompileState, List<T>> {
		return Main.parseValues(state, input, mapper).orElse(new Tuple<CompileState, List<T>>(state, Lists.empty()));
	}
	static parseValues<T>(state: CompileState, input: string, mapper: (arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, T>>): Option<Tuple<CompileState, List<T>>> {
		return Main.parseAll(state, input, Main.foldValues, mapper);
	}
	static mergeValues(cache: string, element: string): string {
		if (Strings.isEmpty(cache)){
			return cache + element;
		}
		return cache + ", " + element;
	}
	static foldValues(state: DivideState, c: string): DivideState {
		if ("," === c && state.isLevel()){
			return state.advance();
		}
		let appended = state.append(c);
		if ("-" === c){
			let peeked = appended.peek();
			if (">" === peeked){
				return appended.popAndAppendToOption().orElse(appended);
			}
			else {
				return appended;
			}
		}
		if ("<" === c || "(" === c){
			return appended.enter();
		}
		if (">" === c || ")" === c){
			return appended.exit();
		}
		return appended;
	}
	static compileLast<T>(input: string, infix: string, mapper: (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return Main.compileInfix(input, infix, Main.findLast, mapper);
	}
	static findLast(input: string, infix: string): number {
		return input.lastIndexOf(infix);
	}
	static compileSuffix<T>(input: string, suffix: string, mapper: (arg0 : string) => Option<T>): Option<T> {
		if (!input.endsWith(suffix)){
			return new None<T>();
		}
		let length = Strings.length(input);
		let length1 = Strings.length(suffix);
		let content = Strings.sliceBetween(input, 0, length - length1);
		return mapper(content);
	}
	static compileFirst<T>(input: string, infix: string, mapper: (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return Main.compileInfix(input, infix, Main.findFirst, mapper);
	}
	static compileInfix<T>(input: string, infix: string, locator: (arg0 : string, arg1 : string) => number, mapper: (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return Main.compileSplit(Main.split(input, infix, locator), mapper);
	}
	static compileSplit<T>(splitter: Option<Tuple<string, string>>, mapper: (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return splitter.flatMap((tuple: Tuple<string, string>) => mapper(tuple.left, tuple.right));
	}
	static split(input: string, infix: string, locator: (arg0 : string, arg1 : string) => number): Option<Tuple<string, string>> {
		let index = locator(input, infix);
		if (0 > index){
			return new None<Tuple<string, string>>();
		}
		let left = Strings.sliceBetween(input, 0, index);
		let length = Strings.length(infix);
		let right = Strings.sliceFrom(input, index + length);
		return new Some<Tuple<string, string>>(new Tuple<string, string>(left, right));
	}
	static findFirst(input: string, infix: string): number {
		return input.indexOf(infix);
	}
	static generatePlaceholder(input: string): string {
		let replaced = input.replace("/*", "start").replace("*/", "end");
		return "/*" + replaced + "*/";
	}
}
