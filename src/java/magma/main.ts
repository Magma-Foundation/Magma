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
	public next(): Option<T> {
		return this.head.next();
	}
	public collect<C>(collector: Collector<T, C>): C {
		return this.fold(collector.createInitial(), collector.fold);
	}
	public map<R>(mapper: (arg0 : T) => R): Query<R> {
		return new HeadedQuery<>(new MapHead<>(this.head, mapper));
	}
	public fold<R>(initial: R, folder: (arg0 : R, arg1 : T) => R): R {
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
	public flatMap<R>(mapper: (arg0 : T) => Query<R>): Query<R> {
		return new HeadedQuery<>(new FlatMapHead<T, R>(this.head, mapper));
	}
	public allMatch(predicate: (arg0 : T) => boolean): boolean {
		return this.fold(true, (maybeAllTrue, element) => maybeAllTrue && predicate(element));
	}
	public filter(predicate: (arg0 : T) => boolean): Query<T> {
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
	private final length: number;
	private counter: number;
	constructor (length: number) {
		this.length = length;
		this.counter = 0;
	}
	public next(): Option<number> {
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
	private advance(): DivideState {
		return new DivideState(this.segments.add(this.buffer), "", this.depth, this.input, this.index);
	}
	private append(c: string): DivideState {
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
		if (this.index >= this.input.length()){
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
	constructor () {
		this("", new None<string>(), 0, Lists.empty());
	}
	append(element: string): CompileState {
		return new CompileState(this.output + element, this.structureName, this.depth, this.definitions);
	}
	withStructureName(name: string): CompileState {
		return new CompileState(this.output, new Some<string>(name), this.depth, this.definitions);
	}
	public depth(): number {
		return this.depth;
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
	constructor () {
		this("");
	}
	public createInitial(): Option<string> {
		return new None<string>();
	}
	public fold(maybe: Option<string>, element: string): Option<string> {
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
	public generate(): string {
		return this.generateWithAfterName("");
	}
	public asDefinition(): Option<Definition> {
		return new Some<>(this);
	}
	public generateWithAfterName(afterName: string): string {
		let joinedTypeParams = this.joinTypeParams();
		let joinedModifiers = this.modifiers.query().map((value) => value + " ").collect(new Joiner("")).orElse("");
		return joinedModifiers + this.name + joinedTypeParams + afterName + this.generateType();
	}
	private generateType(): string {
		if (this.type.isVar()){
			return "";
		}
		return ": " + this.type.generate();
	}
	private joinTypeParams(): string {
		return Main.joinTypeParams(this.typeParams);
	}
}
class ConstructorHeader implements MethodHeader {
	public generateWithAfterName(afterName: string): string {
		return "constructor " + afterName;
	}
}
class Ok<T, X> implements Result<T, X> {
	value: T;
	constructor (value: T) {
		this.value = value;
	}
	public match<R>(whenOk: (arg0 : T) => R, whenErr: (arg0 : X) => R): R {
		return whenOk(this.value);
	}
}
class Err<T, X> implements Result<T, X> {
	error: X;
	constructor (error: X) {
		this.error = error;
	}
	public match<R>(whenOk: (arg0 : T) => R, whenErr: (arg0 : X) => R): R {
		return whenErr(this.error);
	}
}
class SingleHead<T> implements Head<T> {
	private final element: T;
	private retrieved: boolean;
	constructor (element: T) {
		this.element = element;
		this.retrieved = false;
	}
	public next(): Option<T> {
		if (this.retrieved){
			return new None<T>();
		}
		this.retrieved = true;
		return new Some<T>(this.element);
	}
}
class EmptyHead<T> implements Head<T> {
	public next(): Option<T> {
		return new None<T>();
	}
}
class ListCollector<T> implements Collector<T, List<T>> {
	public createInitial(): List<T> {
		return Lists.empty();
	}
	public fold(current: List<T>, element: T): List<T> {
		return current.add(element);
	}
}
class FlatMapHead<T, R> implements Head<R> {
	private final mapper: (arg0 : T) => Query<R>;
	private final head: Head<T>;
	private maybeCurrent: Option<Query<R>>;
	constructor (head: Head<T>, mapper: (arg0 : T) => Query<R>) {
		this.mapper = mapper;
		this.maybeCurrent = new None<Query<R>>();
		this.head = head;
	}
	public next(): Option<R> {
		while (true){
			if (this.maybeCurrent.isPresent()){
				let it: Query<R> = this.maybeCurrent.orElse(null);
				let next = it.next();
				if (next.isPresent()){
					return next;
				}
				this.maybeCurrent = new None<Query<R>>();
			}
			let outer: Option<T> = this.head.next();
			if (outer.isPresent()){
				this.maybeCurrent = new Some<Query<R>>(this.mapper.apply(outer.orElse(null)));
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
	public map<R>(mapper: (arg0 : T) => R): Option<R> {
		return new Some<>(mapper(this.value));
	}
	public isEmpty(): boolean {
		return false;
	}
	public orElse(other: T): T {
		return this.value;
	}
	public orElseGet(supplier: () => T): T {
		return this.value;
	}
	public isPresent(): boolean {
		return true;
	}
	public ifPresent(consumer: (arg0 : T) => void): void {
		consumer(this.value);
	}
	public or(other: () => Option<T>): Option<T> {
		return this;
	}
	public flatMap<R>(mapper: (arg0 : T) => Option<R>): Option<R> {
		return mapper(this.value);
	}
	public filter(predicate: (arg0 : T) => boolean): Option<T> {
		/*return predicate.test(this.value) ? this : new None<>()*/;
	}
	public toTuple(other: T): Tuple<Boolean, T> {
		return new Tuple<>(true, this.value);
	}
	public and<R>(other: () => Option<R>): Option<Tuple<T, R>> {
		return other.get().map((otherValue) => new Tuple<>(this.value, otherValue));
	}
}
class None<T> implements Option<T> {
	public map<R>(mapper: (arg0 : T) => R): Option<R> {
		return new None<>();
	}
	public isEmpty(): boolean {
		return true;
	}
	public orElse(other: T): T {
		return other;
	}
	public orElseGet(supplier: () => T): T {
		return supplier();
	}
	public isPresent(): boolean {
		return false;
	}
	public ifPresent(consumer: (arg0 : T) => void): void {
	}
	public or(other: () => Option<T>): Option<T> {
		return other.get();
	}
	public flatMap<R>(mapper: (arg0 : T) => Option<R>): Option<R> {
		return new None<>();
	}
	public filter(predicate: (arg0 : T) => boolean): Option<T> {
		return new None<>();
	}
	public toTuple(other: T): Tuple<Boolean, T> {
		return new Tuple<>(false, other);
	}
	public and<R>(other: () => Option<R>): Option<Tuple<T, R>> {
		return new None<>();
	}
}
class Placeholder {
	input: string;
	constructor (input: string) {
		this.input = input;
	}
	public generate(): string {
		return Main.generatePlaceholder(this.input);
	}
	public isFunctional(): boolean {
		return false;
	}
	public findChild(): Option<Value> {
		return new None<>();
	}
	public asDefinition(): Option<Definition> {
		return new None<>();
	}
	public toValue(): Option<Value> {
		return new None<>();
	}
	public resolve(state: CompileState): Type {
		return Primitive.Unknown;
	}
	public isVar(): boolean {
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
	public next(): Option<R> {
		return this.head.next().map(this.mapper);
	}
}
class Whitespace implements Parameter {
	public generate(): string {
		return "";
	}
	public asDefinition(): Option<Definition> {
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
	public generate(): string {
		return this.child.generate() + "." + this.property;
	}
	public toValue(): Option<Value> {
		return new Some<>(this);
	}
	public findChild(): Option<Value> {
		return new Some<>(this.child);
	}
	public resolve(state: CompileState): Type {
		return Primitive.Unknown;
	}
}
class Symbol {
	value: string;
	constructor (value: string) {
		this.value = value;
	}
	public generate(): string {
		return this.value;
	}
	public resolve(state: CompileState): Type {
		return state.resolve(this.value).orElse(Primitive.Unknown);
	}
	public toValue(): Option<Value> {
		return new Some<>(this);
	}
	public findChild(): Option<Value> {
		return new None<>();
	}
	public isFunctional(): boolean {
		return false;
	}
	public isVar(): boolean {
		return false;
	}
}
class StringValue implements Value {
	private final value: string;
	constructor (value: string) {
		this.value = value;
	}
	public generate(): string {
		return "\"" + this.value + "\"";
	}
	public toValue(): Option<Value> {
		return new Some<>(this);
	}
	public findChild(): Option<Value> {
		return new None<>();
	}
	public resolve(state: CompileState): Type {
		return Primitive.Unknown;
	}
}
class Not implements Value {
	private final child: string;
	constructor (child: string) {
		this.child = child;
	}
	public generate(): string {
		return this.child;
	}
	public toValue(): Option<Value> {
		return new Some<>(this);
	}
	public findChild(): Option<Value> {
		return new None<>();
	}
	public resolve(state: CompileState): Type {
		return Primitive.Unknown;
	}
}
class Lambda implements Value {
	private final paramNames: List<string>;
	private final content: string;
	constructor (paramNames: List<string>, content: string) {
		this.paramNames = paramNames;
		this.content = content;
	}
	public generate(): string {
		let joinedParamNames = this.paramNames.query().collect(new Joiner(", ")).orElse("");
		return "(" + joinedParamNames + ")" + " => " + this.content;
	}
	public toValue(): Option<Value> {
		return new Some<>(this);
	}
	public findChild(): Option<Value> {
		return new None<>();
	}
	public resolve(state: CompileState): Type {
		return Primitive.Unknown;
	}
}
class Invokable implements Value {
	caller: Caller;
	arguments: List<Value>;
	constructor (caller: Caller, arguments: List<Value>) {
		this.caller = caller;
		this.arguments = arguments;
	}
	public generate(): string {
		let joinedArguments = Main.generateValues(this.arguments);
		return this.caller.generate() + "(" + joinedArguments + ")";
	}
	public toValue(): Option<Value> {
		return new Some<>(this);
	}
	public findChild(): Option<Value> {
		return new None<>();
	}
	public resolve(state: CompileState): Type {
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
	public generate(): string {
		return this.left.generate() + " " + this.targetInfix + " " + this.right.generate();
	}
	public toValue(): Option<Value> {
		return new Some<>(this);
	}
	public findChild(): Option<Value> {
		return new None<>();
	}
	public resolve(state: CompileState): Type {
		return Primitive.Unknown;
	}
}
class ConstructionCaller implements Caller {
	right: string;
	constructor (right: string) {
		this.right = right;
	}
	public generate(): string {
		return "new " + this.right;
	}
	public findChild(): Option<Value> {
		return new None<>();
	}
}
class FunctionType implements Type {
	private final arguments: List<string>;
	private final returns: string;
	constructor (arguments: List<string>, returns: string) {
		this.arguments = arguments;
		this.returns = returns;
	}
	public generate(): string {
		let joinedArguments = this.arguments.queryWithIndices().map((tuple) => "arg" + tuple.left + " : " + tuple.right).collect(new Joiner(", ")).orElse("");
		return "(" + joinedArguments + ") => " + this.returns;
	}
	public isFunctional(): boolean {
		return true;
	}
	public isVar(): boolean {
		return false;
	}
}
class Generic implements Type {
	private final base: string;
	private final arguments: List<string>;
	constructor (base: string, arguments: List<string>) {
		this.base = base;
		this.arguments = arguments;
	}
	public generate(): string {
		return this.base + "<" + Main.generateValueStrings(this.arguments) + ">";
	}
	public isFunctional(): boolean {
		return false;
	}
	public isVar(): boolean {
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
	public add(element: T): List<T> {
		this.list.add(element);
		return this;
	}
	public query(): Query<T> {
		return this.queryWithIndices().map(Tuple.right);
	}
	public size(): number {
		return this.list.size();
	}
	private subList0(startInclusive: number, endExclusive: number): List<T> {
		return new JVMList<>(this.list.subList(startInclusive, endExclusive));
	}
	private getLast(): T {
		return this.list.getLast();
	}
	private getFirst(): T {
		return this.list.getFirst();
	}
	private get(index: number): T {
		return this.list.get(index);
	}
	public queryWithIndices(): Query<Tuple<number, T>> {
		return new /*HeadedQuery<>(new RangeHead(this.list.size()))
                        .map*/((index) => new Tuple<>(index, this.list.get(index)));
	}
	public addAll(others: List<T>): List<T> {
		/*return others.query().<List<T>>fold(this, List::add)*/;
	}
	public subList(startInclusive: number, endExclusive: number): Option<List<T>> {
		return new Some<>(this.subList0(startInclusive, endExclusive));
	}
	public findLast(): Option<T> {
		return new Some<>(this.getLast());
	}
	public findFirst(): Option<T> {
		return new Some<>(this.getFirst());
	}
	public find(index: number): Option<T> {
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
class Main  {
	Main(): private {
	}
	public static main(): void {
		let source = Paths.get(".", "src", "java", "magma", "Main.java");
		let target = source.resolveSibling("main.ts");
		Main.readString(source).match((input) => Main.compileAndWrite(input, target), Some.new).ifPresent(Throwable.printStackTrace);
	}
	private static compileAndWrite(input: string, target: Path): Option<IOException> {
		let output = Main.compileRoot(input);
		return Main.writeString(target, output);
	}
	private static writeString(target: Path, output: string): Option<IOException> {/*try {
            Files.writeString(target, output);
            return new None<IOException>();
        }*//* catch (IOException e) {
            return new Some<IOException>(e);
        }*/
	}
	private static readString(source: Path): Result<string, IOException> {/*try {
            return new Ok<>(Files.readString(source));
        }*//* catch (IOException e) {
            return new Err<>(e);
        }*/
	}
	private static compileRoot(input: string): string {
		let compiled = Main.compileStatements(new CompileState(), input, Main.compileRootSegment);
		return compiled.left.output + compiled.right;
	}
	private static compileStatements(state: CompileState, input: string, mapper: (arg0 : CompileState, arg1 : string) => Tuple<CompileState, string>): Tuple<CompileState, string> {
		return Main.compileAll(state, input, Main.foldStatements, mapper, Main.mergeStatements);
	}
	private static compileAll(state: CompileState, input: string, folder: (arg0 : DivideState, arg1 : string) => DivideState, mapper: (arg0 : CompileState, arg1 : string) => Tuple<CompileState, string>, merger: (arg0 : string, arg1 : string) => string): Tuple<CompileState, string> {
		let folded = Main.parseAll(state, input, folder, mapper);
		return new Tuple<>(folded.left, Main.generateAll(folded.right, merger));
	}
	private static generateAll(elements: List<string>, merger: (arg0 : string, arg1 : string) => string): string {
		return elements.query().fold("", merger);
	}
	private static parseAll<T>(state: CompileState, input: string, folder: (arg0 : DivideState, arg1 : string) => DivideState, mapper: (arg0 : CompileState, arg1 : string) => Tuple<CompileState, T>): Tuple<CompileState, List<T>> {
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
	private static mergeStatements(cache: string, element: string): string {
		return cache + element;
	}
	private static divide(input: string, folder: (arg0 : DivideState, arg1 : string) => DivideState): List<string> {
		let current = DivideState.createInitial(input);
		while (true){
			let maybePopped = current.pop();
			if (maybePopped.isEmpty()){
				break;
			}
			let poppedTuple = maybePopped.orElse(null);
			let poppedState = poppedTuple.left;
			let popped = poppedTuple.right;
			current = Main.foldSingleQuotes(poppedState, popped).or(() => Main.foldDoubleQuotes(poppedState, popped)).orElseGet(() => folder(poppedState, popped));
		}
		return current.advance().segments;
	}
	private static foldDoubleQuotes(state: DivideState, c: string): Option<DivideState> {
		if ("\"" !== c){
			return new None<DivideState>();
		}
		let appended = state.append(c);
		while (true){
			let maybeTuple = appended.popAndAppendToTuple();
			if (maybeTuple.isEmpty()){
				break;
			}
			let tuple = maybeTuple.orElse(null);
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
	private static foldSingleQuotes(state: DivideState, c: string): Option<DivideState> {
		if ("\'" !== c){
			return new None<DivideState>();
		}
		return state.append(c).popAndAppendToTuple().flatMap(Main.foldEscaped).flatMap(DivideState.popAndAppendToOption);
	}
	private static foldEscaped(tuple: Tuple<DivideState, string>): Option<DivideState> {
		let state = tuple.left;
		let c = tuple.right;
		if ("\\" === c){
			return state.popAndAppendToOption();
		}
		return new Some<DivideState>(state);
	}
	private static foldStatements(state: DivideState, c: string): DivideState {
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
	private static compileRootSegment(state: CompileState, input: string): Tuple<CompileState, string> {
		return Main.compileOrPlaceholder(state, input, Lists.of(Main.compileWhitespace, Main.compileNamespaced, Main.createStructureRule("class ", "class ")));
	}
	private static createStructureRule(sourceInfix: string, targetInfix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, string>> {
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
	private static getOr(targetInfix: string, state: CompileState, beforeContent: string, inputContent: string, maybeImplementing: Option<Type>): Option<Tuple<CompileState, string>> {
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
	private static retainDefinitionsFromParameters(parameters: List<Parameter>): List<Definition> {
		return parameters.query().map(Parameter.asDefinition).flatMap(Iterators.fromOption).collect(new ListCollector<>());
	}
	private static assembleStructureWithTypeParams(state: CompileState, infix: string, content: string, beforeParams: string, parameters: List<Definition>, maybeImplementing: Option<Type>): Option<Tuple<CompileState, string>> {
		return Main.compileSuffix(beforeParams.strip(), ">", (withoutTypeParamEnd) => {
			return Main.compileFirst(withoutTypeParamEnd, "<", (name, typeParamsString) => {
				let typeParams = Main.divideValues(typeParamsString);
				return Main.assembleStructure(state, infix, content, name, typeParams, parameters, maybeImplementing);
			});
		}).or(() => {
			return Main.assembleStructure(state, infix, content, beforeParams, Lists.empty(), parameters, maybeImplementing);
		});
	}
	private static assembleStructure(state: CompileState, infix: string, content: string, name: string, typeParams: List<string>, parameters: List<Definition>, maybeImplementing: Option<Type>): Option<Tuple<CompileState, string>> {
		let outputContentTuple = Main.compileStatements(state.withStructureName(name), content, Main.compileClassSegment);
		let outputContentState = outputContentTuple.left;
		let outputContent = outputContentTuple.right;
		let constructorString = Main.generateConstructorFromRecordParameters(parameters);
		let joinedTypeParams = Main.joinTypeParams(typeParams);
		let implementingString = Main.generateImplementing(maybeImplementing);
		let generated = infix + name + joinedTypeParams + implementingString + " {" + Main.joinParameters(parameters) + constructorString + outputContent + "\n}\n";
		return new Some<>(new Tuple<>(outputContentState.append(generated), ""));
	}
	private static generateImplementing(maybeImplementing: Option<Type>): string {
		return maybeImplementing.map(Type.generate).map((inner) => " implements " + inner).orElse("");
	}
	private static joinTypeParams(typeParams: List<string>): string {
		return typeParams.query().collect(new Joiner(", ")).map((inner) => "<" + inner + ">").orElse("");
	}
	private static generateConstructorFromRecordParameters(parameters: List<Definition>): string {
		return parameters.query().map(Definition.generate).collect(new Joiner(", ")).map((generatedParameters) => Main.generateConstructorWithParameterString(parameters, generatedParameters)).orElse("");
	}
	private static generateConstructorWithParameterString(parameters: List<Definition>, parametersString: string): string {
		let constructorAssignments = Main.generateConstructorAssignments(parameters);
		return "\n\tconstructor (" + parametersString + ") {" + constructorAssignments + "\n\t}";
	}
	private static generateConstructorAssignments(parameters: List<Definition>): string {
		return parameters.query().map((definition) => "\n\t\tthis." + definition.name + " = " + definition.name + ";").collect(new Joiner()).orElse("");
	}
	private static joinParameters(parameters: List<Definition>): string {
		return parameters.query().map(Definition.generate).map((generated) => "\n\t" + generated + ";").collect(new Joiner()).orElse("");
	}
	private static compileNamespaced(state: CompileState, input: string): Option<Tuple<CompileState, string>> {
		let stripped = input.strip();
		if (stripped.startsWith("package ") || stripped.startsWith("import ")){
			return new Some<>(new Tuple<>(state, ""));
		}
		else {
			return new None<>();
		}
	}
	private static compileOrPlaceholder(state: CompileState, input: string, rules: List<(arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, string>>>): Tuple<CompileState, string> {
		return Main.or(state, input, rules).orElseGet(() => new Tuple<>(state, Main.generatePlaceholder(input)));
	}
	private static or<T>(state: CompileState, input: string, rules: List<(arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, T>>>): Option<Tuple<CompileState, T>> {
		return rules.query().map((rule) => rule.apply(state, input)).flatMap(Iterators.fromOption).next();
	}
	private static compileClassSegment(state1: CompileState, input1: string): Tuple<CompileState, string> {
		return Main.compileOrPlaceholder(state1, input1, Lists.of(Main.compileWhitespace, Main.createStructureRule("class ", "class "), Main.createStructureRule("interface ", "interface "), Main.createStructureRule("record ", "class "), Main.compileMethod, Main.compileFieldDefinition));
	}
	private static compileMethod(state: CompileState, input: string): Option<Tuple<CompileState, string>> {
		return Main.compileFirst(input, "(", (beforeParams, withParams) => {
			return Main.compileLast(beforeParams.strip(), " ", (_, name) => {
				if (state.structureName.filter(name.equals).isPresent()){
					return Main.compileMethodWithBeforeParams(state, new ConstructorHeader(), withParams);
				}
				return Main.parseDefinition(state, beforeParams).flatMap((tuple) => Main.compileMethodWithBeforeParams(tuple.left, tuple.right, withParams));
			});
		});
	}
	private static compileMethodWithBeforeParams(state: CompileState, header: MethodHeader, withParams: string): Option<Tuple<CompileState, string>> {
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
	private static parseParameters(state: CompileState, params: string): Tuple<CompileState, List<Parameter>> {
		return Main.parseValues(state, params, Main.parseParameter);
	}
	private static compileFunctionStatements(state: CompileState, input: string): Tuple<CompileState, string> {
		return Main.compileStatements(state, input, Main.compileFunctionSegment);
	}
	private static compileFunctionSegment(state: CompileState, input: string): Tuple<CompileState, string> {
		return Main.compileOrPlaceholder(state, input, Lists.of(Main.compileWhitespace, Main.compileEmptySegment, Main.compileBlock, Main.compileFunctionStatement, Main.compileReturnWithoutSuffix));
	}
	private static compileEmptySegment(state: CompileState, input: string): Option<Tuple<CompileState, string>> {
		if (";".equals(input.strip())){
			return new Some<>(new Tuple<>(state, ";"));
		}
		else {
			return new None<>();
		}
	}
	private static compileReturnWithoutSuffix(state1: CompileState, input1: string): Option<Tuple<CompileState, string>> {
		return Main.compileReturn(input1, (withoutPrefix) => Main.compileValue(state1, withoutPrefix)).map((tuple) => new Tuple<>(tuple.left, Main.generateIndent(state1.depth) + tuple.right));
	}
	private static compileBlock(state: CompileState, input: string): Option<Tuple<CompileState, string>> {
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
	private static foldBlockStarts(state: DivideState, c: string): DivideState {
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
	private static compileBlockHeader(state: CompileState, input: string): Option<Tuple<CompileState, string>> {
		return Main.or(state, input, Lists.of(Main.createConditionalRule("if"), Main.createConditionalRule("while"), Main.compileElse));
	}
	private static createConditionalRule(prefix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, string>> {
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
	private static compileElse(state: CompileState, input: string): Option<Tuple<CompileState, string>> {
		if ("else".equals(input.strip())){
			return new Some<>(new Tuple<>(state, "else "));
		}
		else {
			return new None<>();
		}
	}
	private static compileFunctionStatement(state: CompileState, input: string): Option<Tuple<CompileState, string>> {
		return Main.compileSuffix(input.strip(), ";", (withoutEnd) => {
			let valueTuple = Main.compileFunctionStatementValue(state, withoutEnd);
			return new Some<>(new Tuple<>(valueTuple.left, Main.generateIndent(state.depth()) + valueTuple.right + ";"));
		});
	}
	private static generateIndent(indent: number): string {
		return "\n" + "\t".repeat(indent);
	}
	private static compileFunctionStatementValue(state: CompileState, withoutEnd: string): Tuple<CompileState, string> {
		return Main.compileOrPlaceholder(state, withoutEnd, Lists.of(Main.compileReturnWithValue, Main.compileAssignment, (state1, input) => Main.parseInvokable(state1, input).map((tuple) => new Tuple<>(tuple.left, tuple.right.generate())), Main.createPostRule("++"), Main.createPostRule("--"), Main.compileBreak));
	}
	private static compileBreak(state: CompileState, input: string): Option<Tuple<CompileState, string>> {
		if ("break".equals(input.strip())){
			return new Some<>(new Tuple<>(state, "break"));
		}
		else {
			return new None<>();
		}
	}
	private static createPostRule(suffix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, string>> {
		return (state1, input) => Main.compileSuffix(input.strip(), suffix, (child) => {
			let tuple = Main.compileValueOrPlaceholder(state1, child);
			return new Some<>(new Tuple<>(tuple.left, tuple.right + suffix));
		});
	}
	private static compileReturnWithValue(state: CompileState, input: string): Option<Tuple<CompileState, string>> {
		return Main.compileReturn(input, (value1) => Main.compileValue(state, value1));
	}
	private static compileReturn(input: string, mapper: (arg0 : string) => Option<Tuple<CompileState, string>>): Option<Tuple<CompileState, string>> {
		return Main.compilePrefix(input.strip(), "return ", (value) => {
			return mapper(value).flatMap((tuple) => {
				return new Some<>(new Tuple<>(tuple.left, "return " + tuple.right));
			});
		});
	}
	private static parseInvokable(state: CompileState, input: string): Option<Tuple<CompileState, Value>> {
		return Main.compileSuffix(input.strip(), ")", (withoutEnd) => {
			return Main.compileSplit(Main.splitFoldedLast(withoutEnd, "", Main.foldInvocationStarts), (callerWithArgStart, arguments) => {
				return Main.compileSuffix(callerWithArgStart, "(", (callerString) => {
					return Main.compilePrefix(callerString.strip(), "new ", (type) => {
						let callerTuple = Main.compileTypeOrPlaceholder(state, type);
						let callerState = callerTuple.right;
						let caller = callerTuple.left;
						return Main.assembleInvokable(caller, new ConstructionCaller(callerState), arguments);
					}).or(() => {
						return Main.parseValue(state, callerString).flatMap((callerTuple) => {
							return Main.assembleInvokable(callerTuple.left, callerTuple.right, arguments);
						});
					});
				});
			});
		});
	}
	private static splitFoldedLast(input: string, delimiter: string, folder: (arg0 : DivideState, arg1 : string) => DivideState): Option<Tuple<string, string>> {
		return Main.splitFolded(input, folder, (divisions1) => Main.selectLast(divisions1, delimiter));
	}
	private static splitFolded(input: string, folder: (arg0 : DivideState, arg1 : string) => DivideState, selector: (arg0 : List<string>) => Option<Tuple<string, string>>): Option<Tuple<string, string>> {
		let divisions = Main.divide(input, folder);
		if (/*2 > divisions.size()*/){
			return new None<Tuple<string, string>>();
		}
		return selector(divisions);
	}
	private static selectLast(divisions: List<string>, delimiter: string): Option<Tuple<string, string>> {
		let beforeLast = divisions.subList(0, divisions.size() - 1).orElse(divisions);
		let last = divisions.findLast().orElse(null);
		let joined = beforeLast.query().collect(new Joiner(delimiter)).orElse("");
		return new Some<Tuple<string, string>>(new Tuple<string, string>(joined, last));
	}
	private static foldInvocationStarts(state: DivideState, c: string): DivideState {
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
	private static assembleInvokable(state: CompileState, oldCaller: Caller, argumentsString: string): Option<Tuple<CompileState, Value>> {
		let argumentsTuple = Main.parseValues(state, argumentsString, Main.parseArgument);
		let argumentsState = argumentsTuple.left;
		let arguments = Main.retain(argumentsTuple.right, Argument.toValue);
		let newCaller = Main.transformCaller(argumentsState, oldCaller);
		return new Some<>(new Tuple<>(argumentsState, new Invokable(newCaller, arguments)));
	}
	private static transformCaller(state: CompileState, oldCaller: Caller): Caller {
		return oldCaller.findChild().flatMap((parent) => {
			let parentType = parent.resolve(state);
			if (parentType.isFunctional()){
				return new Some<Caller>(parent);
			}
			return new None<>();
		}).orElse(oldCaller);
	}
	private static retain<T, R>(arguments: List<T>, mapper: (arg0 : T) => Option<R>): List<R> {
		return arguments.query().map(mapper).flatMap(Iterators.fromOption).collect(new ListCollector<>());
	}
	private static parseArgument(state1: CompileState, input: string): Tuple<CompileState, Argument> {
		/*return Main.parseValue(state1, input)
                .<Tuple<CompileState, Argument>>map(tuple -> new Tuple<>(tuple.left, tuple.right))
                .orElseGet(() -> new Tuple<>(state1, new Placeholder(input)))*/;
	}
	private static compileAssignment(state: CompileState, input: string): Option<Tuple<CompileState, string>> {
		return Main.compileFirst(input, "=", (destination, source) => {
			let sourceTuple = Main.compileValueOrPlaceholder(state, source);
			let destinationTuple = Main.compileValue(sourceTuple.left, destination).or(() => Main.parseDefinition(sourceTuple.left, destination).map((tuple) => new Tuple<>(tuple.left, "let " + tuple.right.generate()))).orElseGet(() => new Tuple<>(sourceTuple.left, Main.generatePlaceholder(destination)));
			return new Some<>(new Tuple<>(destinationTuple.left, destinationTuple.right + " = " + sourceTuple.right));
		});
	}
	private static compileValueOrPlaceholder(state: CompileState, input: string): Tuple<CompileState, string> {
		return Main.compileValue(state, input).orElseGet(() => new Tuple<>(state, Main.generatePlaceholder(input)));
	}
	private static compileValue(state: CompileState, input: string): Option<Tuple<CompileState, string>> {
		return Main.parseValue(state, input).map((tuple) => new Tuple<>(tuple.left, tuple.right.generate()));
	}
	private static parseValue(state: CompileState, input: string): Option<Tuple<CompileState, Value>> {
		return Main.or(state, input, Lists.of(Main.parseLambda, Main.createOperatorRule("+"), Main.createOperatorRule("-"), Main.createOperatorRule("<="), Main.createOperatorRule("<"), Main.createOperatorRule("&&"), Main.createOperatorRule("||"), Main.createOperatorRule(">="), Main.parseInvokable, Main.createAccessRule("."), Main.createAccessRule("::"), Main.parseSymbol, Main.parseNot, Main.parseNumber, Main.createOperatorRuleWithDifferentInfix("==", "==="), Main.createOperatorRuleWithDifferentInfix("!=", "!=="), Main.createTextRule("\""), Main.createTextRule("'")));
	}
	private static createTextRule(slice: string): (arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, Value>> {
		return (state1, input1) => {
			let stripped = input1.strip();
			if (!stripped.startsWith(slice) || !stripped.endsWith(slice) || stripped.length() <= slice.length()){
				return new None<>();
			}
			let value = stripped.substring(slice.length()) - slice.length();
			return new Some<>(new Tuple<>(state1, new StringValue(value)));
		};
	}
	private static parseNot(state: CompileState, input: string): Option<Tuple<CompileState, Value>> {
		return Main.compilePrefix(input.strip(), "!", (withoutPrefix) => {
			let childTuple = Main.compileValueOrPlaceholder(state, withoutPrefix);
			let childState = childTuple.left;
			let child = "!" + childTuple.right;
			return new Some<>(new Tuple<>(childState, new Not(child)));
		});
	}
	private static parseLambda(state: CompileState, input: string): Option<Tuple<CompileState, Value>> {
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
	private static compileLambdaWithParameterNames(state: CompileState, paramNames: List<string>, afterArrow: string): Option<Tuple<CompileState, Value>> {
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
	private static assembleLambda(exited: CompileState, paramNames: List<string>, content: string): Option<Tuple<CompileState, Value>> {
		return new Some<>(new Tuple<>(exited, new Lambda(paramNames, content)));
	}
	private static createOperatorRule(infix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, Value>> {
		return Main.createOperatorRuleWithDifferentInfix(infix, infix);
	}
	private static createAccessRule(infix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, Value>> {
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
	private static createOperatorRuleWithDifferentInfix(sourceInfix: string, targetInfix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, Value>> {
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
	private static selectFirst(divisions: List<string>, delimiter: string): Option<Tuple<string, string>> {
		let first = divisions.findFirst().orElse(null);
		let afterFirst = divisions.subList(1, divisions.size()).orElse(divisions).query().collect(new Joiner(delimiter)).orElse("");
		return new Some<Tuple<string, string>>(new Tuple<string, string>(first, afterFirst));
	}
	private static foldOperator(infix: string): (arg0 : DivideState, arg1 : string) => DivideState {
		return (state, c) => {
			if (c === infix.charAt(0) && state.startsWith(infix.substring(1))){
				let length = infix.length() - 1;
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
	private static parseNumber(state: CompileState, input: string): Option<Tuple<CompileState, Value>> {
		let stripped = input.strip();
		if (Main.isNumber(stripped)){
			return new Some<>(new Tuple<>(state, new Symbol(stripped)));
		}
		else {
			return new None<>();
		}
	}
	private static isNumber(input: string): boolean {
		return IntStream.range(0, input.length()).mapToObj(input.charAt).allMatch(Character.isDigit);
	}
	private static parseSymbol(state: CompileState, input: string): Option<Tuple<CompileState, Value>> {
		let stripped = input.strip();
		if (Main.isSymbol(stripped)){
			return new Some<>(new Tuple<>(state, new Symbol(stripped)));
		}
		else {
			return new None<>();
		}
	}
	private static isSymbol(input: string): boolean {
		return IntStream.range(0, input.length()).allMatch((index) => Main.isSymbolChar(index, input.charAt(index)));
	}
	private static isSymbolChar(index: number, c: string): boolean {
		return "_" === c || Character.isLetter(c) || (0 !== index && Character.isDigit(c));
	}
	private static compilePrefix<T>(input: string, infix: string, mapper: (arg0 : string) => Option<Tuple<CompileState, T>>): Option<Tuple<CompileState, T>> {
		if (!input.startsWith(infix)){
			return new None<>();
		}
		let slice = input.substring(infix.length());
		return mapper(slice);
	}
	private static compileWhitespace(state: CompileState, input: string): Option<Tuple<CompileState, string>> {
		return Main.parseWhitespace(state, input).map((tuple) => new Tuple<>(tuple.left, tuple.right.generate()));
	}
	private static parseWhitespace(state: CompileState, input: string): Option<Tuple<CompileState, Whitespace>> {
		if (input.isBlank()){
			return new Some<Tuple<CompileState, Whitespace>>(new Tuple<>(state, new Whitespace()));
		}
		return new None<Tuple<CompileState, Whitespace>>();
	}
	private static compileFieldDefinition(state: CompileState, input: string): Option<Tuple<CompileState, string>> {
		return Main.compileSuffix(input.strip(), ";", (withoutEnd) => {
			let definitionTuple = Main.compileParameterOrPlaceholder(state, withoutEnd);
			return new Some<>(new Tuple<>(definitionTuple.left, "\n\t" + definitionTuple.right + ";"));
		});
	}
	private static compileParameterOrPlaceholder(state: CompileState, input: string): Tuple<CompileState, string> {
		let tuple = Main.parseParameter(state, input);
		return new Tuple<>(tuple.left, tuple.right.generate());
	}
	private static parseParameter(state: CompileState, input: string): Tuple<CompileState, Parameter> {
		/*return Main.parseWhitespace(state, input).<Tuple<CompileState, Parameter>>map(tuple -> new Tuple<>(tuple.left, tuple.right))
                .or(() -> Main.parseDefinition(state, input).map(tuple -> new Tuple<>(tuple.left, tuple.right)))
                .orElseGet(() -> new Tuple<>(state, new Placeholder(input)))*/;
	}
	private static parseDefinition(state: CompileState, input: string): Option<Tuple<CompileState, Definition>> {
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
	private static parseAnnotations(s: string): List<string> {
		return Main.divide(s, (state1, c) => Main.foldDelimited(state1, c, "\n")).query().map(String.strip).filter((value) => !value.isEmpty()).filter((value) => 1 <= value.length()).map((value) => value.substring(1)).map(String.strip).filter((value) => !value.isEmpty()).collect(new ListCollector<>());
	}
	private static parseDefinitionWithAnnotations(state: CompileState, annotations: List<string>, beforeType: string, type: string, name: string): Option<Tuple<CompileState, Definition>> {
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
	private static parseModifiers(beforeType: string): List<string> {
		return Main.divide(beforeType.strip(), (state1, c) => Main.foldDelimited(state1, c, " ")).query().map(String.strip).filter((value) => !value.isEmpty()).collect(new ListCollector<>());
	}
	private static foldDelimited(state1: DivideState, c: string, delimiter: string): DivideState {
		if (delimiter === c){
			return state1.advance();
		}
		return state1.append(c);
	}
	private static divideValues(input: string): List<string> {
		return Main.divide(input, Main.foldValues).query().map(String.strip).filter((value) => !value.isEmpty()).collect(new ListCollector<>());
	}
	private static foldTypeSeparators(state: DivideState, c: string): DivideState {
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
	private static parseDefinitionWithTypeParameters(state: CompileState, annotations: List<string>, typeParams: List<string>, modifiers: List<string>, type: string, name: string): Option<Tuple<CompileState, Definition>> {
		let typeTuple = Main.parseTypeOrPlaceholder(state, type);
		let generated = new Definition(annotations, modifiers, typeParams, typeTuple.right, name);
		return new Some<Tuple<CompileState, Definition>>(new Tuple<CompileState, Definition>(typeTuple.left, generated));
	}
	private static parseTypeOrPlaceholder(state: CompileState, type: string): Tuple<CompileState, Type> {
		return Main.parseType(state, type).map((tuple) => new Tuple<CompileState, Type>(tuple.left, tuple.right)).orElseGet(() => new Tuple<>(state, new Placeholder(type)));
	}
	private static compileTypeOrPlaceholder(state: CompileState, type: string): Tuple<CompileState, string> {
		return Main.compileType(state, type).orElseGet(() => new Tuple<>(state, Main.generatePlaceholder(type)));
	}
	private static compileType(state: CompileState, type: string): Option<Tuple<CompileState, string>> {
		return Main.parseType(state, type).map((tuple) => new Tuple<>(tuple.left, tuple.right.generate()));
	}
	private static parseType(state: CompileState, type: string): Option<Tuple<CompileState, Type>> {
		return Main.or(state, type, Lists.of(Main.parseGeneric, Main.parsePrimitive, Main.parseSymbolType));
	}
	private static parseSymbolType(state: CompileState, input: string): Option<Tuple<CompileState, Type>> {
		let stripped = input.strip();
		if (Main.isSymbol(stripped)){
			return new Some<>(new Tuple<>(state, new Symbol(stripped)));
		}
		return new None<>();
	}
	private static parsePrimitive(state: CompileState, input: string): Option<Tuple<CompileState, Type>> {
		return Main.findPrimitiveValue(input.strip()).map((result) => new Tuple<>(state, result));
	}
	private static findPrimitiveValue(input: string): Option<Type> {
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
	private static parseGeneric(state: CompileState, input: string): Option<Tuple<CompileState, Type>> {
		return Main.compileSuffix(input.strip(), ">", (withoutEnd) => {
			return Main.compileFirst(withoutEnd, "<", (baseString, argumentsString) => {
				let argumentsTuple = Main.parseValues(state, argumentsString, Main.compileTypeArgument);
				let argumentsState = argumentsTuple.left;
				let arguments = argumentsTuple.right;
				let base = baseString.strip();
				return Main.assembleFunctionType(argumentsState, base, arguments).or(() => {
					return new Some<>(new Tuple<>(argumentsState, new Generic(base, arguments)));
				});
			});
		});
	}
	private static assembleFunctionType(state: CompileState, base: string, arguments: List<string>): Option<Tuple<CompileState, Type>> {
		return Main.mapFunctionType(base, arguments).map((generated) => new Tuple<>(state, generated));
	}
	private static mapFunctionType(base: string, arguments: List<string>): Option<Type> {
		if ("Function".equals(base)){
			return arguments.findFirst().and(() => arguments.find(1)).map((tuple) => new FunctionType(Lists.of(tuple.left), tuple.right));
		}
		if ("BiFunction".equals(base)){
			return arguments.find(0).and(() => arguments.find(1)).and(() => arguments.find(2)).map((tuple) => new FunctionType(Lists.of(tuple.left.left, tuple.left.right), tuple.right));
		}
		if ("Supplier".equals(base)){
			return arguments.findFirst().map((first) => {
				return new FunctionType(Lists.empty(), first);
			});
		}
		if ("Consumer".equals(base)){
			return arguments.findFirst().map((first) => {
				return new FunctionType(Lists.of(first), "void");
			});
		}
		if ("Predicate".equals(base)){
			return arguments.findFirst().map((first) => {
				return new FunctionType(Lists.of(first), "boolean");
			});
		}
		return new None<>();
	}
	private static compileTypeArgument(state: CompileState, input: string): Tuple<CompileState, string> {
		return Main.compileOrPlaceholder(state, input, Lists.of(Main.compileWhitespace, Main.compileType));
	}
	private static generateValueStrings(values: List<string>): string {
		return Main.generateAll(values, Main.mergeValues);
	}
	private static parseValues<T>(state: CompileState, input: string, mapper: (arg0 : CompileState, arg1 : string) => Tuple<CompileState, T>): Tuple<CompileState, List<T>> {
		return Main.parseAll(state, input, Main.foldValues, mapper);
	}
	private static mergeValues(cache: string, element: string): string {
		if (cache.isEmpty()){
			return cache + element;
		}
		return cache + ", " + element;
	}
	private static foldValues(state: DivideState, c: string): DivideState {
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
	private static compileLast<T>(input: string, infix: string, mapper: (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return Main.compileInfix(input, infix, Main.findLast, mapper);
	}
	private static findLast(input: string, infix: string): number {
		return input.lastIndexOf(infix);
	}
	private static compileSuffix<T>(input: string, suffix: string, mapper: (arg0 : string) => Option<T>): Option<T> {
		if (!input.endsWith(suffix)){
			return new None<T>();
		}
		let content = input.substring(0) - suffix.length();
		return mapper(content);
	}
	private static compileFirst<T>(input: string, infix: string, mapper: (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return Main.compileInfix(input, infix, Main.findFirst, mapper);
	}
	private static compileInfix<T>(input: string, infix: string, locator: (arg0 : string, arg1 : string) => number, mapper: (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return Main.compileSplit(Main.split(input, infix, locator), mapper);
	}
	private static compileSplit<T>(splitter: Option<Tuple<string, string>>, mapper: (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return splitter.flatMap((tuple) => mapper(tuple.left, tuple.right));
	}
	private static split(input: string, infix: string, locator: (arg0 : string, arg1 : string) => number): Option<Tuple<string, string>> {
		let index = locator(input, infix);
		if (/*0 > index*/){
			return new None<Tuple<string, string>>();
		}
		let left = input.substring(0, index);
		let right = input.substring(index + infix.length());
		return new Some<Tuple<string, string>>(new Tuple<string, string>(left, right));
	}
	private static findFirst(input: string, infix: string): number {
		return input.indexOf(infix);
	}
	private static generatePlaceholder(input: string): string {
		let replaced = input.replace("/*", "start").replace("*/", "end");
		return "/*" + replaced + "*/";
	}
	private static generateValues(arguments: List<Value>): string {
		return arguments.query().map(Value.generate).collect(new Joiner(", ")).orElse("");
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
