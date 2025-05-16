interface MethodHeader  {
	generateWithAfterName(afterName: string): string;
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
		return new HeadedQuery<>(new MapHead<>(this.head, mapper));
	}
	fold<R>(initial: R, folder: (arg0 : R, arg1 : T) => R): R {
		let result: R = initial;
		while (true){
			let finalResult: R = result;
			let maybeNext: Tuple<Boolean, R> = this.head.next().map((inner) => folder(finalResult, inner)).toTuple(finalResult);
			if (maybeNext.left){
				result = maybeNext.right;
			}
			else {
				return result;
			}
		}
	}
	flatMap<R>(mapper: (arg0 : T) => Query<R>): Query<R> {
		return this.head.next().map(mapper).map((initial) => new HeadedQuery<>(new FlatMapHead<T, R>(this.head, initial, mapper))).orElseGet(() => new HeadedQuery<>(new EmptyHead<>()));
	}
	allMatch(predicate: (arg0 : T) => boolean): boolean {
		return this.fold(true, (maybeAllTrue, element) => maybeAllTrue && predicate(element));
	}
	filter(predicate: (arg0 : T) => boolean): Query<T> {
		return this.flatMap((element) => {
			if (predicate(element)){
				return new HeadedQuery<>(new SingleHead<>(element));
			}
			else {
				return new HeadedQuery<>(new EmptyHead<>());
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
		return this.pop().map((inner) => new Tuple<>(inner.left.append(inner.right), inner.right));
	}
	popAndAppendToOption(): Option<DivideState> {
		return this.popAndAppendToTuple().map((tuple) => tuple.left());
	}
	peek(): string {
		return this.input.charAt(this.index);
	}
	startsWith(slice: string): boolean {
		return this.input.substring(this.index).startsWith(slice);
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
	structureName: Option<string>;
	depth: number;
	definitions: List<Definition>;
	constructor (output: string, structureName: Option<string>, depth: number, definitions: List<Definition>) {
		this.output = output;
		this.structureName = structureName;
		this.depth = depth;
		this.definitions = definitions;
	}
	static createInitial(): CompileState {
		return new CompileState("", new None<string>(), 0, Lists.empty());
	}
	append(element: string): CompileState {
		return new CompileState(this.output + element, this.structureName, this.depth, this.definitions);
	}
	withStructureName(name: string): CompileState {
		return new CompileState(this.output, new Some<string>(name), this.depth, this.definitions);
	}
	enterDepth(): CompileState {
		return new CompileState(this.output, this.structureName, this.depth + 1, this.definitions);
	}
	exitDepth(): CompileState {
		return new CompileState(this.output, this.structureName, this.depth - 1, this.definitions);
	}
	defineAll(definitions: List<Definition>): CompileState {
		return new CompileState(this.output, this.structureName, this.depth, this.definitions.addAll(definitions));
	}
	resolve(name: string): Option<Type> {
		return this.definitions.query().filter((definition) => definition.name.equals(name)).map(Definition.type).next();
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
		return new Some<string>(maybe.map((inner) => inner + this.delimiter + element).orElse(element));
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
		return new Some<>(this);
	}
	generateWithAfterName(afterName: string): string {
		let joinedTypeParams = this.joinTypeParams();
		let joinedModifiers = this.modifiers.query().map((value) => value + " ").collect(new Joiner("")).orElse("");
		return joinedModifiers + this.name + joinedTypeParams + afterName + this.generateType();
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
}
class ConstructorHeader implements MethodHeader {
	generateWithAfterName(afterName: string): string {
		return "constructor " + afterName;
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
				return new None<>();
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
		return new Some<>(mapper(this.value));
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
		return new None<>();
	}
	toTuple(other: T): Tuple<Boolean, T> {
		return new Tuple<>(true, this.value);
	}
	and<R>(other: () => Option<R>): Option<Tuple<T, R>> {
		return other.get().map((otherValue) => new Tuple<>(this.value, otherValue));
	}
}
class None<T> implements Option<T> {
	map<R>(mapper: (arg0 : T) => R): Option<R> {
		return new None<>();
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
		return other.get();
	}
	flatMap<R>(mapper: (arg0 : T) => Option<R>): Option<R> {
		return new None<>();
	}
	filter(predicate: (arg0 : T) => boolean): Option<T> {
		return new None<>();
	}
	toTuple(other: T): Tuple<Boolean, T> {
		return new Tuple<>(false, other);
	}
	and<R>(other: () => Option<R>): Option<Tuple<T, R>> {
		return new None<>();
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
		return new None<>();
	}
	asDefinition(): Option<Definition> {
		return new None<>();
	}
	toValue(): Option<Value> {
		return new None<>();
	}
	resolve(state: CompileState): Type {
		return Primitive.Unknown;
	}
	isVar(): boolean {
		return false;
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
		return new None<>();
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
		return new Some<>(this);
	}
	findChild(): Option<Value> {
		return new Some<>(this.child);
	}
	resolve(state: CompileState): Type {
		return Primitive.Unknown;
	}
}
class Symbol {
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
		return new Some<>(this);
	}
	findChild(): Option<Value> {
		return new None<>();
	}
	isFunctional(): boolean {
		return false;
	}
	isVar(): boolean {
		return false;
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
		return new Some<>(this);
	}
	findChild(): Option<Value> {
		return new None<>();
	}
	resolve(state: CompileState): Type {
		return Primitive.Unknown;
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
		return new Some<>(this);
	}
	findChild(): Option<Value> {
		return new None<>();
	}
	resolve(state: CompileState): Type {
		return Primitive.Unknown;
	}
}
class Lambda implements Value {
	paramNames: List<string>;
	content: string;
	constructor (paramNames: List<string>, content: string) {
		this.paramNames = paramNames;
		this.content = content;
	}
	generate(): string {
		let joinedParamNames = this.paramNames.query().collect(new Joiner(", ")).orElse("");
		return "(" + joinedParamNames + ")" + " => " + this.content;
	}
	toValue(): Option<Value> {
		return new Some<>(this);
	}
	findChild(): Option<Value> {
		return new None<>();
	}
	resolve(state: CompileState): Type {
		return Primitive.Unknown;
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
		let joinedArguments = Main.generateValues(this.args);
		return this.caller.generate() + "(" + joinedArguments + ")";
	}
	toValue(): Option<Value> {
		return new Some<>(this);
	}
	findChild(): Option<Value> {
		return new None<>();
	}
	resolve(state: CompileState): Type {
		return Primitive.Unknown;
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
		return new Some<>(this);
	}
	findChild(): Option<Value> {
		return new None<>();
	}
	resolve(state: CompileState): Type {
		return Primitive.Unknown;
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
		return new None<>();
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
		let joinedArguments = this.args.queryWithIndices().map((tuple) => "arg" + tuple.left + " : " + tuple.right).collect(new Joiner(", ")).orElse("");
		return "(" + joinedArguments + ") => " + this.returns;
	}
	isFunctional(): boolean {
		return true;
	}
	isVar(): boolean {
		return false;
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
}
class Iterators  {
	static fromOption<T>(option: Option<T>): Query<T> {
		return new HeadedQuery<>();
	}
}
class JVMList<T> implements List<T> {
	list: java.util.List<T>;
	constructor (list: java.util.List<T>) {
		this.list = list;
	}
	constructor () {
		this(new ArrayList<>());
	}
	add(element: T): List<T> {
		this.list.add(element);
		return this;
	}
	query(): Query<T> {
		return this.queryWithIndices().map(Tuple.right);
	}
	size(): number {
		return this.list.size();
	}
	subList0(startInclusive: number, endExclusive: number): List<T> {
		return new JVMList<>(this.list.subList(startInclusive, endExclusive));
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
		return new /*HeadedQuery<>(new RangeHead(this.list.size()))
                        .map*/((index) => new Tuple<>(index, this.list.get(index)));
	}
	addAll(others: List<T>): List<T> {
		/*return others.query().<List<T>>fold(this, List::add)*/;
	}
	contains(element: T): boolean {
		return this.list.contains(element);
	}
	subList(startInclusive: number, endExclusive: number): Option<List<T>> {
		return new Some<>(this.subList0(startInclusive, endExclusive));
	}
	findLast(): Option<T> {
		return new Some<>(this.getLast());
	}
	findFirst(): Option<T> {
		return new Some<>(this.getFirst());
	}
	find(index: number): Option<T> {
		return new Some<>(this.get(index));
	}
}
class Lists  {
	static empty<T>(): List<T> {
		return new JVMList<>();
	}
	static of<T>(elements: /*T...*/): List<T> {
		return new JVMList<>(new ArrayList<>(Arrays.asList(elements)));
	}
}
class Strings  {
	static length(stripped: string): number {
		return stripped.length();
	}
}
class Main  {
	static main(): void {
		let source = Paths.get(".", "src", "java", "magma", "Main.java");
		let target = source.resolveSibling("main.ts");
		Main.readString(source).match((input) => Main.compileAndWrite(input, target), Some.new).ifPresent(Throwable.printStackTrace);
	}
	static compileAndWrite(input: string, target: Path): Option<IOException> {
		let output = Main.compileRoot(input);
		return Main.writeString(target, output);
	}
	static writeString(target: Path, output: string): Option<IOException> {/*try {
            Files.writeString(target, output);
            return new None<IOException>();
        }*//* catch (IOException e) {
            return new Some<IOException>(e);
        }*/
	}
	static readString(source: Path): Result<string, IOException> {/*try {
            return new Ok<>(Files.readString(source));
        }*//* catch (IOException e) {
            return new Err<>(e);
        }*/
	}
	static compileRoot(input: string): string {
		let compiled = Main.compileStatements(CompileState.createInitial(), input, Main.compileRootSegment);
		return compiled.left.output + compiled.right;
	}
	static compileStatements(state: CompileState, input: string, mapper: (arg0 : CompileState, arg1 : string) => Tuple<CompileState, string>): Tuple<CompileState, string> {
		return Main.compileAll(state, input, Main.foldStatements, mapper, Main.mergeStatements);
	}
	static compileAll(state: CompileState, input: string, folder: (arg0 : DivideState, arg1 : string) => DivideState, mapper: (arg0 : CompileState, arg1 : string) => Tuple<CompileState, string>, merger: (arg0 : string, arg1 : string) => string): Tuple<CompileState, string> {
		let folded = Main.parseAll(state, input, folder, mapper);
		return new Tuple<>(folded.left, Main.generateAll(folded.right, merger));
	}
	static generateAll(elements: List<string>, merger: (arg0 : string, arg1 : string) => string): string {
		return elements.query().fold("", merger);
	}
	static parseAll<T>(state: CompileState, input: string, folder: (arg0 : DivideState, arg1 : string) => DivideState, mapper: (arg0 : CompileState, arg1 : string) => Tuple<CompileState, T>): Tuple<CompileState, List<T>> {
		return Main.divide(input, folder).query().fold(new Tuple<CompileState, List<T>>(state, Lists.empty()), (current, segment) => {
			let currentState = current.left;
			let currentElement = current.right;
			let mappedTuple = mapper(currentState, segment);
			let mappedState = mappedTuple.left;
			let mappedElement = mappedTuple.right;
			currentElement.add(mappedElement);
			return new Tuple<>(mappedState, currentElement);
		});
	}
	static mergeStatements(cache: string, element: string): string {
		return cache + element;
	}
	static divide(input: string, folder: (arg0 : DivideState, arg1 : string) => DivideState): List<string> {
		let current = DivideState.createInitial(input);
		while (true){
			let poppedTuple0 = current.pop().toTuple(new Tuple<>(current, "\0"));
			if (!poppedTuple0.left()){
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
			let maybeTuple = appended.popAndAppendToTuple().toTuple(new Tuple<>(appended, "\0"));
			if (!maybeTuple.left()){
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
		return state.append(c).popAndAppendToTuple().flatMap(Main.foldEscaped).flatMap(DivideState.popAndAppendToOption);
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
		return (state, input1) => Main.compileFirst(input1, sourceInfix, (_, right1) => {
			return Main.compileFirst(right1, "{", (beforeContent, withEnd) => {
				return Main.compileSuffix(withEnd.strip(), "}", (inputContent) => {
					return Main.compileLast(beforeContent, " implements ", (s, s2) => {
						return Main.parseType(state, s2).flatMap((implementingTuple) => {
							return Main.getOr(targetInfix, implementingTuple.left, s, inputContent, new Some<>(implementingTuple.right));
						});
					}).or(() => {
						return Main.getOr(targetInfix, state, beforeContent, inputContent, new None<>());
					});
				});
			});
		});
	}
	static getOr(targetInfix: string, state: CompileState, beforeContent: string, inputContent: string, maybeImplementing: Option<Type>): Option<Tuple<CompileState, string>> {
		return Main.compileFirst(beforeContent, "(", (rawName, withParameters) => {
			return Main.compileFirst(withParameters, ")", (parametersString, _) => {
				let name = rawName.strip();
				let parametersTuple = Main.parseParameters(state, parametersString);
				let parameters = Main.retainDefinitionsFromParameters(parametersTuple.right);
				return Main.assembleStructureWithTypeParams(parametersTuple.left, targetInfix, inputContent, name, parameters, maybeImplementing);
			});
		}).or(() => {
			return Main.assembleStructureWithTypeParams(state, targetInfix, inputContent, beforeContent, Lists.empty(), maybeImplementing);
		});
	}
	static retainDefinitionsFromParameters(parameters: List<Parameter>): List<Definition> {
		return parameters.query().map(Parameter.asDefinition).flatMap(Iterators.fromOption).collect(new ListCollector<>());
	}
	static assembleStructureWithTypeParams(state: CompileState, infix: string, content: string, beforeParams: string, parameters: List<Definition>, maybeImplementing: Option<Type>): Option<Tuple<CompileState, string>> {
		return Main.compileSuffix(beforeParams.strip(), ">", (withoutTypeParamEnd) => {
			return Main.compileFirst(withoutTypeParamEnd, "<", (name, typeParamsString) => {
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
		return new Some<>(new Tuple<>(outputContentState.append(generated), ""));
	}
	static generateImplementing(maybeImplementing: Option<Type>): string {
		return maybeImplementing.map(Type.generate).map((inner) => " implements " + inner).orElse("");
	}
	static joinTypeParams(typeParams: List<string>): string {
		return typeParams.query().collect(new Joiner(", ")).map((inner) => "<" + inner + ">").orElse("");
	}
	static generateConstructorFromRecordParameters(parameters: List<Definition>): string {
		return parameters.query().map(Definition.generate).collect(new Joiner(", ")).map((generatedParameters) => Main.generateConstructorWithParameterString(parameters, generatedParameters)).orElse("");
	}
	static generateConstructorWithParameterString(parameters: List<Definition>, parametersString: string): string {
		let constructorAssignments = Main.generateConstructorAssignments(parameters);
		return "\n\tconstructor (" + parametersString + ") {" + constructorAssignments + "\n\t}";
	}
	static generateConstructorAssignments(parameters: List<Definition>): string {
		return parameters.query().map((definition) => "\n\t\tthis." + definition.name + " = " + definition.name + ";").collect(Joiner.empty()).orElse("");
	}
	static joinParameters(parameters: List<Definition>): string {
		return parameters.query().map(Definition.generate).map((generated) => "\n\t" + generated + ";").collect(Joiner.empty()).orElse("");
	}
	static compileNamespaced(state: CompileState, input: string): Option<Tuple<CompileState, string>> {
		let stripped = input.strip();
		if (stripped.startsWith("package ") || stripped.startsWith("import ")){
			return new Some<>(new Tuple<>(state, ""));
		}
		else {
			return new None<>();
		}
	}
	static compileOrPlaceholder(state: CompileState, input: string, rules: List<(arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, string>>>): Tuple<CompileState, string> {
		return Main.or(state, input, rules).orElseGet(() => new Tuple<>(state, Main.generatePlaceholder(input)));
	}
	static or<T>(state: CompileState, input: string, rules: List<(arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, T>>>): Option<Tuple<CompileState, T>> {
		return rules.query().map((rule) => rule.apply(state, input)).flatMap(Iterators.fromOption).next();
	}
	static compileClassSegment(state1: CompileState, input1: string): Tuple<CompileState, string> {
		return Main.compileOrPlaceholder(state1, input1, Lists.of(Main.compileWhitespace, Main.createStructureRule("class ", "class "), Main.createStructureRule("interface ", "interface "), Main.createStructureRule("record ", "class "), Main.compileMethod, Main.compileFieldDefinition));
	}
	static compileMethod(state: CompileState, input: string): Option<Tuple<CompileState, string>> {
		return Main.compileFirst(input, "(", (beforeParams, withParams) => {
			return Main.compileLast(beforeParams.strip(), " ", (_, name) => {
				if (state.structureName.filter(name.equals).isPresent()){
					return Main.compileMethodWithBeforeParams(state, new ConstructorHeader(), withParams);
				}
				return Main.parseDefinition(state, beforeParams).flatMap((tuple) => Main.compileMethodWithBeforeParams(tuple.left, tuple.right, withParams));
			});
		});
	}
	static compileMethodWithBeforeParams(state: CompileState, header: MethodHeader, withParams: string): Option<Tuple<CompileState, string>> {
		return Main.compileFirst(withParams, ")", (params, afterParams) => {
			let parametersTuple = Main.parseParameters(state, params);
			let parametersState = parametersTuple.left;
			let parameters = parametersTuple.right;
			let definitions = Main.retainDefinitionsFromParameters(parameters);
			let joinedDefinitions = definitions.query().map(Definition.generate).collect(new Joiner(", ")).orElse("");
			let headerGenerated = header.generateWithAfterName("(" + joinedDefinitions + ")");
			return Main.compilePrefix(afterParams.strip(), "{", (withoutContentStart) => {
				return Main.compileSuffix(withoutContentStart.strip(), "}", (withoutContentEnd) => {
					let statementsTuple = Main.compileFunctionStatements(parametersState.enterDepth().enterDepth().defineAll(definitions), withoutContentEnd);
					return new Some<>(new Tuple<>(statementsTuple.left.exitDepth().exitDepth(), "\n\t" + headerGenerated + " {" + statementsTuple.right + "\n\t}"));
				});
			}).or(() => {
				if (";".equals(afterParams.strip())){
					return new Some<>(new Tuple<>(parametersState, "\n\t" + headerGenerated + ";"));
				}
				return new None<>();
			});
		});
	}
	static parseParameters(state: CompileState, params: string): Tuple<CompileState, List<Parameter>> {
		return Main.parseValues(state, params, Main.parseParameter);
	}
	static compileFunctionStatements(state: CompileState, input: string): Tuple<CompileState, string> {
		return Main.compileStatements(state, input, Main.compileFunctionSegment);
	}
	static compileFunctionSegment(state: CompileState, input: string): Tuple<CompileState, string> {
		return Main.compileOrPlaceholder(state, input, Lists.of(Main.compileWhitespace, Main.compileEmptySegment, Main.compileBlock, Main.compileFunctionStatement, Main.compileReturnWithoutSuffix));
	}
	static compileEmptySegment(state: CompileState, input: string): Option<Tuple<CompileState, string>> {
		if (";".equals(input.strip())){
			return new Some<>(new Tuple<>(state, ";"));
		}
		else {
			return new None<>();
		}
	}
	static compileReturnWithoutSuffix(state1: CompileState, input1: string): Option<Tuple<CompileState, string>> {
		return Main.compileReturn(input1, (withoutPrefix) => Main.compileValue(state1, withoutPrefix)).map((tuple) => new Tuple<>(tuple.left, Main.generateIndent(state1.depth) + tuple.right));
	}
	static compileBlock(state: CompileState, input: string): Option<Tuple<CompileState, string>> {
		return Main.compileSuffix(input.strip(), "}", (withoutEnd) => {
			return Main.compileSplit(Main.splitFoldedLast(withoutEnd, "", Main.foldBlockStarts), (beforeContentWithEnd, content) => {
				return Main.compileSuffix(beforeContentWithEnd, "{", (beforeContent) => {
					return Main.compileBlockHeader(state, beforeContent).flatMap((headerTuple) => {
						let contentTuple = Main.compileFunctionStatements(headerTuple.left.enterDepth(), content);
						let indent = Main.generateIndent(state.depth());
						return new Some<>(new Tuple<>(contentTuple.left.exitDepth(), indent + headerTuple.right + "{" + contentTuple.right + indent + "}"));
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
		return (state1, input1) => Main.compilePrefix(input1.strip(), prefix, (withoutPrefix) => {
			let strippedCondition = withoutPrefix.strip();
			return Main.compilePrefix(strippedCondition, "(", (withoutConditionStart) => {
				return Main.compileSuffix(withoutConditionStart, ")", (withoutConditionEnd) => {
					let tuple = Main.compileValueOrPlaceholder(state1, withoutConditionEnd);
					return new Some<>(new Tuple<>(tuple.left, prefix + " (" + tuple.right + ")"));
				});
			});
		});
	}
	static compileElse(state: CompileState, input: string): Option<Tuple<CompileState, string>> {
		if ("else".equals(input.strip())){
			return new Some<>(new Tuple<>(state, "else "));
		}
		else {
			return new None<>();
		}
	}
	static compileFunctionStatement(state: CompileState, input: string): Option<Tuple<CompileState, string>> {
		return Main.compileSuffix(input.strip(), ";", (withoutEnd) => {
			let valueTuple = Main.compileFunctionStatementValue(state, withoutEnd);
			return new Some<>(new Tuple<>(valueTuple.left, Main.generateIndent(state.depth()) + valueTuple.right + ";"));
		});
	}
	static generateIndent(indent: number): string {
		return "\n" + "\t".repeat(indent);
	}
	static compileFunctionStatementValue(state: CompileState, withoutEnd: string): Tuple<CompileState, string> {
		return Main.compileOrPlaceholder(state, withoutEnd, Lists.of(Main.compileReturnWithValue, Main.compileAssignment, (state1, input) => Main.parseInvokable(state1, input).map((tuple) => new Tuple<>(tuple.left, tuple.right.generate())), Main.createPostRule("++"), Main.createPostRule("--"), Main.compileBreak));
	}
	static compileBreak(state: CompileState, input: string): Option<Tuple<CompileState, string>> {
		if ("break".equals(input.strip())){
			return new Some<>(new Tuple<>(state, "break"));
		}
		else {
			return new None<>();
		}
	}
	static createPostRule(suffix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, string>> {
		return (state1, input) => Main.compileSuffix(input.strip(), suffix, (child) => {
			let tuple = Main.compileValueOrPlaceholder(state1, child);
			return new Some<>(new Tuple<>(tuple.left, tuple.right + suffix));
		});
	}
	static compileReturnWithValue(state: CompileState, input: string): Option<Tuple<CompileState, string>> {
		return Main.compileReturn(input, (value1) => Main.compileValue(state, value1));
	}
	static compileReturn(input: string, mapper: (arg0 : string) => Option<Tuple<CompileState, string>>): Option<Tuple<CompileState, string>> {
		return Main.compilePrefix(input.strip(), "return ", (value) => {
			return mapper(value).flatMap((tuple) => {
				return new Some<>(new Tuple<>(tuple.left, "return " + tuple.right));
			});
		});
	}
	static parseInvokable(state: CompileState, input: string): Option<Tuple<CompileState, Value>> {
		return Main.compileSuffix(input.strip(), ")", (withoutEnd) => {
			return Main.compileSplit(Main.splitFoldedLast(withoutEnd, "", Main.foldInvocationStarts), (callerWithArgStart, args) => {
				return Main.compileSuffix(callerWithArgStart, "(", (callerString) => {
					return Main.compilePrefix(callerString.strip(), "new ", (type) => {
						let callerTuple = Main.compileTypeOrPlaceholder(state, type);
						let callerState = callerTuple.right;
						let caller = callerTuple.left;
						return Main.assembleInvokable(caller, new ConstructionCaller(callerState), args);
					}).or(() => {
						return Main.parseValue(state, callerString).flatMap((callerTuple) => {
							return Main.assembleInvokable(callerTuple.left, callerTuple.right, args);
						});
					});
				});
			});
		});
	}
	static splitFoldedLast(input: string, delimiter: string, folder: (arg0 : DivideState, arg1 : string) => DivideState): Option<Tuple<string, string>> {
		return Main.splitFolded(input, folder, (divisions1) => Main.selectLast(divisions1, delimiter));
	}
	static splitFolded(input: string, folder: (arg0 : DivideState, arg1 : string) => DivideState, selector: (arg0 : List<string>) => Option<Tuple<string, string>>): Option<Tuple<string, string>> {
		let divisions = Main.divide(input, folder);
		if (/*2 > divisions.size()*/){
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
		let argsTuple = Main.parseValues(state, argsString, Main.parseArgument);
		let argsState = argsTuple.left;
		let args = Main.retain(argsTuple.right, Argument.toValue);
		let newCaller = Main.transformCaller(argsState, oldCaller);
		return new Some<>(new Tuple<>(argsState, new Invokable(newCaller, args)));
	}
	static transformCaller(state: CompileState, oldCaller: Caller): Caller {
		return oldCaller.findChild().flatMap((parent) => {
			let parentType = parent.resolve(state);
			if (parentType.isFunctional()){
				return new Some<Caller>(parent);
			}
			return new None<>();
		}).orElse(oldCaller);
	}
	static retain<T, R>(args: List<T>, mapper: (arg0 : T) => Option<R>): List<R> {
		return args.query().map(mapper).flatMap(Iterators.fromOption).collect(new ListCollector<>());
	}
	static parseArgument(state1: CompileState, input: string): Tuple<CompileState, Argument> {
		/*return Main.parseValue(state1, input)
                .<Tuple<CompileState, Argument>>map(tuple -> new Tuple<>(tuple.left, tuple.right))
                .orElseGet(() -> new Tuple<>(state1, new Placeholder(input)))*/;
	}
	static compileAssignment(state: CompileState, input: string): Option<Tuple<CompileState, string>> {
		return Main.compileFirst(input, "=", (destination, source) => {
			let sourceTuple = Main.compileValueOrPlaceholder(state, source);
			let destinationTuple = Main.compileValue(sourceTuple.left, destination).or(() => Main.parseDefinition(sourceTuple.left, destination).map((tuple) => new Tuple<>(tuple.left, "let " + tuple.right.generate()))).orElseGet(() => new Tuple<>(sourceTuple.left, Main.generatePlaceholder(destination)));
			return new Some<>(new Tuple<>(destinationTuple.left, destinationTuple.right + " = " + sourceTuple.right));
		});
	}
	static compileValueOrPlaceholder(state: CompileState, input: string): Tuple<CompileState, string> {
		return Main.compileValue(state, input).orElseGet(() => new Tuple<>(state, Main.generatePlaceholder(input)));
	}
	static compileValue(state: CompileState, input: string): Option<Tuple<CompileState, string>> {
		return Main.parseValue(state, input).map((tuple) => new Tuple<>(tuple.left, tuple.right.generate()));
	}
	static parseValue(state: CompileState, input: string): Option<Tuple<CompileState, Value>> {
		return Main.or(state, input, Lists.of(Main.parseLambda, Main.createOperatorRule("+"), Main.createOperatorRule("-"), Main.createOperatorRule("<="), Main.createOperatorRule("<"), Main.createOperatorRule("&&"), Main.createOperatorRule("||"), Main.createOperatorRule(">="), Main.parseInvokable, Main.createAccessRule("."), Main.createAccessRule("::"), Main.parseSymbol, Main.parseNot, Main.parseNumber, Main.createOperatorRuleWithDifferentInfix("==", "==="), Main.createOperatorRuleWithDifferentInfix("!=", "!=="), Main.createTextRule("\""), Main.createTextRule("'")));
	}
	static createTextRule(slice: string): (arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, Value>> {
		return (state1, input1) => {
			let stripped = input1.strip();
			if (!stripped.startsWith(slice) || !stripped.endsWith(slice) || Strings.length(stripped) <= Strings.length(slice)){
				return new None<>();
			}
			let value = stripped.substring(Strings.length(slice)) - Strings.length();
			return new Some<>(new Tuple<>(state1, new StringValue(value)));
		};
	}
	static parseNot(state: CompileState, input: string): Option<Tuple<CompileState, Value>> {
		return Main.compilePrefix(input.strip(), "!", (withoutPrefix) => {
			let childTuple = Main.compileValueOrPlaceholder(state, withoutPrefix);
			let childState = childTuple.left;
			let child = "!" + childTuple.right;
			return new Some<>(new Tuple<>(childState, new Not(child)));
		});
	}
	static parseLambda(state: CompileState, input: string): Option<Tuple<CompileState, Value>> {
		return Main.compileFirst(input, "->", (beforeArrow, afterArrow) => {
			let strippedBeforeArrow = beforeArrow.strip();
			if (Main.isSymbol(strippedBeforeArrow)){
				return Main.compileLambdaWithParameterNames(state, Lists.of(strippedBeforeArrow), afterArrow);
			}
			return Main.compilePrefix(strippedBeforeArrow, "(", (withoutStart) => {
				return Main.compileSuffix(withoutStart, ")", (withoutEnd) => {
					let paramNames = Main.divideValues(withoutEnd);
					if (paramNames.query().allMatch(Main.isSymbol)){
						return Main.compileLambdaWithParameterNames(state, paramNames, afterArrow);
					}
					else {
						return new None<>();
					}
				});
			});
		});
	}
	static compileLambdaWithParameterNames(state: CompileState, paramNames: List<string>, afterArrow: string): Option<Tuple<CompileState, Value>> {
		let strippedAfterArrow = afterArrow.strip();
		return Main.compilePrefix(strippedAfterArrow, "{", (withoutContentStart) => {
			return Main.compileSuffix(withoutContentStart, "}", (withoutContentEnd) => {
				let statementsTuple = Main.compileFunctionStatements(state.enterDepth(), withoutContentEnd);
				let statementsState = statementsTuple.left;
				let statements = statementsTuple.right;
				let exited = statementsState.exitDepth();
				return Main.assembleLambda(exited, paramNames, "{" + statements + Main.generateIndent(exited.depth) + "}");
			});
		}).or(() => {
			let tuple = Main.compileValueOrPlaceholder(state, strippedAfterArrow);
			return Main.assembleLambda(tuple.left, paramNames, tuple.right);
		});
	}
	static assembleLambda(exited: CompileState, paramNames: List<string>, content: string): Option<Tuple<CompileState, Value>> {
		return new Some<>(new Tuple<>(exited, new Lambda(paramNames, content)));
	}
	static createOperatorRule(infix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, Value>> {
		return Main.createOperatorRuleWithDifferentInfix(infix, infix);
	}
	static createAccessRule(infix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, Value>> {
		return (state, input) => Main.compileLast(input, infix, (childString, rawProperty) => {
			let property = rawProperty.strip();
			if (!Main.isSymbol(property)){
				return new None<>();
			}
			return Main.parseValue(state, childString).flatMap((childTuple) => {
				let childState = childTuple.left;
				let child = childTuple.right;
				return new Some<>(new Tuple<>(childState, new Access(child, property)));
			});
		});
	}
	static createOperatorRuleWithDifferentInfix(sourceInfix: string, targetInfix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, Value>> {
		return (state1, input1) => {
			return Main.compileSplit(Main.splitFolded(input1, Main.foldOperator(sourceInfix), (divisions) => Main.selectFirst(divisions, sourceInfix)), (leftString, rightString) => {
				return Main.parseValue(state1, leftString).flatMap((leftTuple) => {
					return Main.parseValue(leftTuple.left, rightString).flatMap((rightTuple) => {
						let left = leftTuple.right;
						let right = rightTuple.right;
						return new Some<>(new Tuple<>(rightTuple.left, new Operation(left, targetInfix, right)));
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
		return (state, c) => {
			if (c === infix.charAt(0) && state.startsWith(infix.substring(1))){
				let length = Strings.length(infix) - 1;
				let counter = 0;
				let current = state;
				while (counter < length){
					counter++;
					current = current.pop().map(Tuple.left).orElse(current);
				}
				return current.advance();
			}
			return state.append(c);
		};
	}
	static parseNumber(state: CompileState, input: string): Option<Tuple<CompileState, Value>> {
		let stripped = input.strip();
		if (Main.isNumber(stripped)){
			return new Some<>(new Tuple<>(state, new Symbol(stripped)));
		}
		else {
			return new None<>();
		}
	}
	static isNumber(input: string): boolean {
		return IntStream.range(0, Strings.length(input)).mapToObj(input.charAt).allMatch(Character.isDigit);
	}
	static parseSymbol(state: CompileState, input: string): Option<Tuple<CompileState, Value>> {
		let stripped = input.strip();
		if (Main.isSymbol(stripped)){
			return new Some<>(new Tuple<>(state, new Symbol(stripped)));
		}
		else {
			return new None<>();
		}
	}
	static isSymbol(input: string): boolean {
		return IntStream.range(0, Strings.length(input)).allMatch((index) => Main.isSymbolChar(index, input.charAt(index)));
	}
	static isSymbolChar(index: number, c: string): boolean {
		return "_" === c || Character.isLetter(c) || (0 !== index && Character.isDigit(c));
	}
	static compilePrefix<T>(input: string, infix: string, mapper: (arg0 : string) => Option<Tuple<CompileState, T>>): Option<Tuple<CompileState, T>> {
		if (!input.startsWith(infix)){
			return new None<>();
		}
		let slice = input.substring(Strings.length(infix));
		return mapper(slice);
	}
	static compileWhitespace(state: CompileState, input: string): Option<Tuple<CompileState, string>> {
		return Main.parseWhitespace(state, input).map((tuple) => new Tuple<>(tuple.left, tuple.right.generate()));
	}
	static parseWhitespace(state: CompileState, input: string): Option<Tuple<CompileState, Whitespace>> {
		if (input.isBlank()){
			return new Some<Tuple<CompileState, Whitespace>>(new Tuple<>(state, new Whitespace()));
		}
		return new None<Tuple<CompileState, Whitespace>>();
	}
	static compileFieldDefinition(state: CompileState, input: string): Option<Tuple<CompileState, string>> {
		return Main.compileSuffix(input.strip(), ";", (withoutEnd) => {
			let definitionTuple = Main.compileParameterOrPlaceholder(state, withoutEnd);
			return new Some<>(new Tuple<>(definitionTuple.left, "\n\t" + definitionTuple.right + ";"));
		});
	}
	static compileParameterOrPlaceholder(state: CompileState, input: string): Tuple<CompileState, string> {
		let tuple = Main.parseParameter(state, input);
		return new Tuple<>(tuple.left, tuple.right.generate());
	}
	static parseParameter(state: CompileState, input: string): Tuple<CompileState, Parameter> {
		/*return Main.parseWhitespace(state, input).<Tuple<CompileState, Parameter>>map(tuple -> new Tuple<>(tuple.left, tuple.right))
                .or(() -> Main.parseDefinition(state, input).map(tuple -> new Tuple<>(tuple.left, tuple.right)))
                .orElseGet(() -> new Tuple<>(state, new Placeholder(input)))*/;
	}
	static parseDefinition(state: CompileState, input: string): Option<Tuple<CompileState, Definition>> {
		return Main.compileLast(input.strip(), " ", (beforeName, name) => {
			return Main.compileSplit(Main.splitFoldedLast(beforeName.strip(), " ", Main.foldTypeSeparators), (beforeType, type) => {
				return Main.compileLast(beforeType.strip(), "\n", (annotationsString, afterAnnotations) => {
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
		return Main.divide(s, (state1, c) => Main.foldDelimited(state1, c, "\n")).query().map(String.strip).filter((value) => !value.isEmpty()).filter((value) => 1 <= Strings.length(value)).map((value) => value.substring(1)).map(String.strip).filter((value) => !value.isEmpty()).collect(new ListCollector<>());
	}
	static parseDefinitionWithAnnotations(state: CompileState, annotations: List<string>, beforeType: string, type: string, name: string): Option<Tuple<CompileState, Definition>> {
		return Main.compileSuffix(beforeType.strip(), ">", (withoutTypeParamEnd) => {
			return Main.compileFirst(withoutTypeParamEnd, "<", (beforeTypeParams, typeParamsString) => {
				let typeParams = Main.divideValues(typeParamsString);
				return Main.parseDefinitionWithTypeParameters(state, annotations, typeParams, Main.parseModifiers(beforeTypeParams), type, name);
			});
		}).or(() => {
			let divided = Main.parseModifiers(beforeType);
			return Main.parseDefinitionWithTypeParameters(state, annotations, Lists.empty(), divided, type, name);
		});
	}
	static parseModifiers(beforeType: string): List<string> {
		return Main.divide(beforeType.strip(), (state1, c) => Main.foldDelimited(state1, c, " ")).query().map(String.strip).filter((value) => !value.isEmpty()).collect(new ListCollector<>());
	}
	static foldDelimited(state1: DivideState, c: string, delimiter: string): DivideState {
		if (delimiter === c){
			return state1.advance();
		}
		return state1.append(c);
	}
	static divideValues(input: string): List<string> {
		return Main.divide(input, Main.foldValues).query().map(String.strip).filter((value) => !value.isEmpty()).collect(new ListCollector<>());
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
		let typeTuple = Main.parseTypeOrPlaceholder(state, type);
		let newModifiers = /* Lists.<String>empty()*/;
		if (oldModifiers.contains("static")){
			newModifiers = newModifiers.add("static");
		}
		let generated = new Definition(annotations, newModifiers, typeParams, typeTuple.right, name);
		return new Some<Tuple<CompileState, Definition>>(new Tuple<CompileState, Definition>(typeTuple.left, generated));
	}
	static parseTypeOrPlaceholder(state: CompileState, type: string): Tuple<CompileState, Type> {
		return Main.parseType(state, type).map((tuple) => new Tuple<CompileState, Type>(tuple.left, tuple.right)).orElseGet(() => new Tuple<>(state, new Placeholder(type)));
	}
	static compileTypeOrPlaceholder(state: CompileState, type: string): Tuple<CompileState, string> {
		return Main.compileType(state, type).orElseGet(() => new Tuple<>(state, Main.generatePlaceholder(type)));
	}
	static compileType(state: CompileState, type: string): Option<Tuple<CompileState, string>> {
		return Main.parseType(state, type).map((tuple) => new Tuple<>(tuple.left, tuple.right.generate()));
	}
	static parseType(state: CompileState, type: string): Option<Tuple<CompileState, Type>> {
		return Main.or(state, type, Lists.of(Main.parseGeneric, Main.parsePrimitive, Main.parseSymbolType));
	}
	static parseSymbolType(state: CompileState, input: string): Option<Tuple<CompileState, Type>> {
		let stripped = input.strip();
		if (Main.isSymbol(stripped)){
			return new Some<>(new Tuple<>(state, new Symbol(stripped)));
		}
		return new None<>();
	}
	static parsePrimitive(state: CompileState, input: string): Option<Tuple<CompileState, Type>> {
		return Main.findPrimitiveValue(input.strip()).map((result) => new Tuple<>(state, result));
	}
	static findPrimitiveValue(input: string): Option<Type> {
		let stripped = input.strip();
		if ("char".equals(stripped) || "Character".equals(stripped) || "String".equals(stripped)){
			return new Some<>(Primitive.String);
		}
		if ("int".equals(stripped) || "Integer".equals(stripped)){
			return new Some<>(Primitive.Number);
		}
		if ("boolean".equals(stripped)){
			return new Some<>(Primitive.Boolean);
		}
		if ("var".equals(stripped)){
			return new Some<>(Primitive.Var);
		}
		if ("void".equals(stripped)){
			return new Some<>(Primitive.Void);
		}
		return new None<>();
	}
	static parseGeneric(state: CompileState, input: string): Option<Tuple<CompileState, Type>> {
		return Main.compileSuffix(input.strip(), ">", (withoutEnd) => {
			return Main.compileFirst(withoutEnd, "<", (baseString, argsString) => {
				let argsTuple = Main.parseValues(state, argsString, Main.compileTypeArgument);
				let argsState = argsTuple.left;
				let args = argsTuple.right;
				let base = baseString.strip();
				return Main.assembleFunctionType(argsState, base, args).or(() => {
					return new Some<>(new Tuple<>(argsState, new Generic(base, args)));
				});
			});
		});
	}
	static assembleFunctionType(state: CompileState, base: string, args: List<string>): Option<Tuple<CompileState, Type>> {
		return Main.mapFunctionType(base, args).map((generated) => new Tuple<>(state, generated));
	}
	static mapFunctionType(base: string, args: List<string>): Option<Type> {
		if ("Function".equals(base)){
			return args.findFirst().and(() => args.find(1)).map((tuple) => new FunctionType(Lists.of(tuple.left), tuple.right));
		}
		if ("BiFunction".equals(base)){
			return args.find(0).and(() => args.find(1)).and(() => args.find(2)).map((tuple) => new FunctionType(Lists.of(tuple.left.left, tuple.left.right), tuple.right));
		}
		if ("Supplier".equals(base)){
			return args.findFirst().map((first) => {
				return new FunctionType(Lists.empty(), first);
			});
		}
		if ("Consumer".equals(base)){
			return args.findFirst().map((first) => {
				return new FunctionType(Lists.of(first), "void");
			});
		}
		if ("Predicate".equals(base)){
			return args.findFirst().map((first) => {
				return new FunctionType(Lists.of(first), "boolean");
			});
		}
		return new None<>();
	}
	static compileTypeArgument(state: CompileState, input: string): Tuple<CompileState, string> {
		return Main.compileOrPlaceholder(state, input, Lists.of(Main.compileWhitespace, Main.compileType));
	}
	static generateValueStrings(values: List<string>): string {
		return Main.generateAll(values, Main.mergeValues);
	}
	static parseValues<T>(state: CompileState, input: string, mapper: (arg0 : CompileState, arg1 : string) => Tuple<CompileState, T>): Tuple<CompileState, List<T>> {
		return Main.parseAll(state, input, Main.foldValues, mapper);
	}
	static mergeValues(cache: string, element: string): string {
		if (cache.isEmpty()){
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
		let content = input.substring(0) - Strings.length();
		return mapper(content);
	}
	static compileFirst<T>(input: string, infix: string, mapper: (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return Main.compileInfix(input, infix, Main.findFirst, mapper);
	}
	static compileInfix<T>(input: string, infix: string, locator: (arg0 : string, arg1 : string) => number, mapper: (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return Main.compileSplit(Main.split(input, infix, locator), mapper);
	}
	static compileSplit<T>(splitter: Option<Tuple<string, string>>, mapper: (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return splitter.flatMap((tuple) => mapper(tuple.left, tuple.right));
	}
	static split(input: string, infix: string, locator: (arg0 : string, arg1 : string) => number): Option<Tuple<string, string>> {
		let index = locator(input, infix);
		if (/*0 > index*/){
			return new None<Tuple<string, string>>();
		}
		let left = input.substring(0, index);
		let right = input.substring(index + Strings.length(infix));
		return new Some<Tuple<string, string>>(new Tuple<string, string>(left, right));
	}
	static findFirst(input: string, infix: string): number {
		return input.indexOf(infix);
	}
	static generatePlaceholder(input: string): string {
		let replaced = input.replace("/*", "start").replace("*/", "end");
		return "/*" + replaced + "*/";
	}
	static generateValues(args: List<Value>): string {
		return args.query().map(Value.generate).collect(new Joiner(", ")).orElse("");
	}/*

    private enum Primitive implements Type {
        String("string"),
        Number("number"),
        Boolean("boolean"),
        Var("var"),
        Void("void"),
        Unknown("unknown");

        private final String value;

        Primitive(String value) {
            this.value = value;
        }

        @Override
        public java.lang.String generate() {
            return this.value;
        }

        @Override
        public boolean isFunctional() {
            return false;
        }

        @Override
        public boolean isVar() {
            return Primitive.Var == this;
        }
    }*/
}
