interface MethodHeader  {
	generateWithAfterName(afterName : string): string;
}
interface Result<T, X> {
	match<R>(whenOk : (arg0 : T) => R, whenErr : (arg0 : X) => R): R;
}
interface Collector<T, C> {
	createInitial(): C;
	fold(current : C, element : T): C;
}
interface Option<T> {
	map<R>(mapper : (arg0 : T) => R): Option<R>;
	isEmpty(): boolean;
	orElse(other : T): T;
	orElseGet(supplier : () => T): T;
	isPresent(): boolean;
	ifPresent(consumer : (arg0 : T) => void): void;
	or(other : () => Option<T>): Option<T>;
	flatMap<R>(mapper : (arg0 : T) => Option<R>): Option<R>;
	filter(predicate : (arg0 : T) => boolean): Option<T>;
	toTuple(other : T): Tuple<Boolean, T>;
	and<R>(other : () => Option<R>): Option<Tuple<T, R>>;
}
interface Query<T> {
	collect<C>(collector : Collector<T, C>): C;
	map<R>(mapper : (arg0 : T) => R): Query<R>;
	fold<R>(initial : R, folder : (arg0 : R, arg1 : T) => R): R;
	flatMap<R>(mapper : (arg0 : T) => Query<R>): Query<R>;
	next(): Option<T>;
	allMatch(predicate : (arg0 : T) => boolean): boolean;
	filter(predicate : (arg0 : T) => boolean): Query<T>;
}
interface List<T> {
	add(element : T): List<T>;
	query(): Query<T>;
	size(): number;
	subList(startInclusive : number, endExclusive : number): Option<List<T>>;
	findLast(): Option<T>;
	findFirst(): Option<T>;
	find(index : number): Option<T>;
	queryWithIndices(): Query<Tuple<number, T>>;
	addAll(others : List<T>): List<T>;
}
interface Head<T> {
	next(): Option<T>;
}
interface Parameter  {
	generate(): string;
	asDefinition(): Option<Definition>;
}
interface Value extends Argument, Caller  {
	generate(): string;
	resolve(state : CompileState): Type;
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
}
class HeadedQuery<T> implements Query<T> {
	head : Head<T>;
	constructor (head : Head<T>) {
		this.head = head;
	}
	next(): Option<T> {
		return this.head.next();
	}
	collect<C>(collector : Collector<T, C>): C {
		return this.fold(collector.createInitial(), collector.fold);
	}
	map<R>(mapper : (arg0 : T) => R): Query<R> {
		return new HeadedQuery<>(new MapHead<>(this.head, mapper));
	}
	fold<R>(initial : R, folder : (arg0 : R, arg1 : T) => R): R {
		let result : R = initial;
		while (true){
			let finalResult : R = result;
			let maybeNext : Tuple<Boolean, R> = this.head.next().map((inner) => folder(finalResult, inner)).toTuple(finalResult);
			if (maybeNext.left){
				result = maybeNext.right;
			}
			else {
				return result;
			}
		}
	}
	flatMap<R>(mapper : (arg0 : T) => Query<R>): Query<R> {
		return new HeadedQuery<>(new FlatMapHead<T, R>(this.head, mapper));
	}
	allMatch(predicate : (arg0 : T) => boolean): boolean {
		return this.fold(true, (maybeAllTrue, element) => maybeAllTrue && predicate(element));
	}
	filter(predicate : (arg0 : T) => boolean): Query<T> {
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
	length : number;
	counter : number;
	constructor (length : number) {
		this.length = length;
		this.counter = 0;
	}
	next(): Option<number> {
		if (this.counter >= this.length){
			return new None<number>();
		}
		let value : var = this.counter;
		this.counter++;
		return new Some<number>(value);
	}
}
class JVMList<T> implements List<T> {
	list : java.util.List<T>;
	constructor (list : java.util.List<T>) {
		this.list = list;
	}
	constructor () {
		this(new ArrayList<>());
	}
	add(element : T): List<T> {
		this.list.add(element);
		return this;
	}
	query(): Query<T> {
		return this.queryWithIndices().map(Tuple.right);
	}
	size(): number {
		return this.list.size();
	}
	subList0(startInclusive : number, endExclusive : number): List<T> {
		return new JVMList<>(this.list.subList(startInclusive, endExclusive));
	}
	getLast(): T {
		return this.list.getLast();
	}
	getFirst(): T {
		return this.list.getFirst();
	}
	get(index : number): T {
		return this.list.get(index);
	}
	queryWithIndices(): Query<Tuple<number, T>> {
		return new /*HeadedQuery<>(new RangeHead(this.list.size()))
                        .map*/((index) => new Tuple<>(index, this.list.get(index)));
	}
	addAll(others : List<T>): List<T> {
		/*return others.query().<List<T>>fold(this, List::add)*/;
	}
	subList(startInclusive : number, endExclusive : number): Option<List<T>> {
		return new Some<>(this.subList0(startInclusive, endExclusive));
	}
	findLast(): Option<T> {
		return new Some<>(this.getLast());
	}
	findFirst(): Option<T> {
		return new Some<>(this.getFirst());
	}
	find(index : number): Option<T> {
		return new Some<>(this.get(index));
	}
}
class Lists  {
	empty<T>(): List<T> {
		return new JVMList<>();
	}
	of<T>(elements : /*T...*/): List<T> {
		return new JVMList<>(new ArrayList<>(Arrays.asList(elements)));
	}
}
class DivideState  {
	input : string;
	segments : List<string>;
	index : number;
	buffer : StringBuilder;
	depth : number;
	DivideState(segments : List<string>, buffer : StringBuilder, depth : number, input : string, index : number): private {
		this.segments = segments;
		this.buffer = buffer;
		this.depth = depth;
		this.input = input;
		this.index = index;
	}
	DivideState(input : string): public {
		this(Lists.empty(), new StringBuilder(), 0, input, 0);
	}
	advance(): DivideState {
		this.segments = this.segments.add(this.buffer.toString());
		this.buffer = new StringBuilder();
		return this;
	}
	append(c : string): DivideState {
		this.buffer.append(c);
		return this;
	}
	isLevel(): boolean {
		return this.depth === 0;
	}
	enter(): DivideState {
		this.depth++;
		return this;
	}
	exit(): DivideState {
		this.depth--;
		return this;
	}
	isShallow(): boolean {
		return this.depth === 1;
	}
	pop(): Option<Tuple<DivideState, string>> {
		if (this.index >= this.input.length()){
			return new None<Tuple<DivideState, string>>();
		}
		let c : var = this.input.charAt(this.index);
		this.index++;
		return new Some<Tuple<DivideState, string>>(new Tuple<DivideState, string>(this, c));
	}
	popAndAppendToTuple(): Option<Tuple<DivideState, string>> {
		return this.pop().map((inner) => new Tuple<>(inner.left.append(inner.right), inner.right));
	}
	popAndAppendToOption(): Option<DivideState> {
		return this.popAndAppendToTuple().map(Tuple.left);
	}
	peek(): string {
		return this.input.charAt(this.index);
	}
	startsWith(slice : string): boolean {
		return this.input.substring(this.index).startsWith(slice);
	}
}
class Tuple<A, B> {
	left : A;
	right : B;
	constructor (left : A, right : B) {
		this.left = left;
		this.right = right;
	}
}
class CompileState {
	output : string;
	structureName : Option<string>;
	depth : number;
	definitions : List<Definition>;
	constructor (output : string, structureName : Option<string>, depth : number, definitions : List<Definition>) {
		this.output = output;
		this.structureName = structureName;
		this.depth = depth;
		this.definitions = definitions;
	}
	constructor () {
		this("", new None<string>(), 0, Lists.empty());
	}
	append(element : string): CompileState {
		return new CompileState(this.output + element, this.structureName, this.depth, this.definitions);
	}
	withStructureName(name : string): CompileState {
		return new CompileState(this.output, new Some<string>(name), this.depth, this.definitions);
	}
	depth(): number {
		return this.depth;
	}
	enterDepth(): CompileState {
		return new CompileState(this.output, this.structureName, this.depth + 1, this.definitions);
	}
	exitDepth(): CompileState {
		return new CompileState(this.output, this.structureName, this.depth - 1, this.definitions);
	}
	defineAll(definitions : List<Definition>): CompileState {
		return new CompileState(this.output, this.structureName, this.depth, this.definitions.addAll(definitions));
	}
	resolve(name : string): Option<Type> {
		return this.definitions.query().filter((definition) => definition.name.equals(name)).map(Definition.type).next();
	}
}
class Joiner implements Collector<string, Option<string>> {
	delimiter : string;
	constructor (delimiter : string) {
		this.delimiter = delimiter;
	}
	constructor () {
		this("");
	}
	createInitial(): Option<string> {
		return new None<string>();
	}
	fold(maybe : Option<string>, element : string): Option<string> {
		return new Some<string>(maybe.map((inner) => inner + this.delimiter + element).orElse(element));
	}
}
class Definition {
	maybeBeforeType : Option<string>;
	name : string;
	typeParams : List<string>;
	type : Type;
	constructor (maybeBeforeType : Option<string>, name : string, typeParams : List<string>, type : Type) {
		this.maybeBeforeType = maybeBeforeType;
		this.name = name;
		this.typeParams = typeParams;
		this.type = type;
	}
	generate(): string {
		return this.generateWithAfterName(" ");
	}
	asDefinition(): Option<Definition> {
		return new Some<>(this);
	}
	generateWithAfterName(afterName : string): string {
		let joinedTypeParams : var = this.joinTypeParams();
		return this.name + joinedTypeParams + afterName + ": " + this.type.generate();
	}
	joinTypeParams(): string {
		return Main.joinTypeParams(this.typeParams);
	}
}
class ConstructorHeader implements MethodHeader {
	generateWithAfterName(afterName : string): string {
		return "constructor " + afterName;
	}
}
class Ok<T, X> implements Result<T, X> {
	value : T;
	constructor (value : T) {
		this.value = value;
	}
	match<R>(whenOk : (arg0 : T) => R, whenErr : (arg0 : X) => R): R {
		return whenOk(this.value);
	}
}
class Err<T, X> implements Result<T, X> {
	error : X;
	constructor (error : X) {
		this.error = error;
	}
	match<R>(whenOk : (arg0 : T) => R, whenErr : (arg0 : X) => R): R {
		return whenErr(this.error);
	}
}
class Iterators  {
	fromOption<T>(option : Option<T>): Query<T> {
		return new HeadedQuery<>();
	}
}
class SingleHead<T> implements Head<T> {
	element : T;
	false : /*=*/;
	constructor (element : T) {
		this.element = element;
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
	fold(current : List<T>, element : T): List<T> {
		return current.add(element);
	}
}
class FlatMapHead<T, R> implements Head<R> {
	mapper : (arg0 : T) => Query<R>;
	head : Head<T>;
	maybeCurrent : Option<Query<R>>;
	constructor (head : Head<T>, mapper : (arg0 : T) => Query<R>) {
		this.mapper = mapper;
		this.maybeCurrent = new None<Query<R>>();
		this.head = head;
	}
	next(): Option<R> {
		while (true){
			if (this.maybeCurrent.isPresent()){
				let it : Query<R> = this.maybeCurrent.orElse(null);
				let next : var = it.next();
				if (next.isPresent()){
					return next;
				}
				this.maybeCurrent = new None<Query<R>>();
			}
			let outer : Option<T> = this.head.next();
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
	value : T;
	constructor (value : T) {
		this.value = value;
	}
	map<R>(mapper : (arg0 : T) => R): Option<R> {
		return new Some<>(mapper(this.value));
	}
	isEmpty(): boolean {
		return false;
	}
	orElse(other : T): T {
		return this.value;
	}
	orElseGet(supplier : () => T): T {
		return this.value;
	}
	isPresent(): boolean {
		return true;
	}
	ifPresent(consumer : (arg0 : T) => void): void {
		consumer(this.value);
	}
	or(other : () => Option<T>): Option<T> {
		return this;
	}
	flatMap<R>(mapper : (arg0 : T) => Option<R>): Option<R> {
		return mapper(this.value);
	}
	filter(predicate : (arg0 : T) => boolean): Option<T> {
		/*return predicate.test(this.value) ? this : new None<>()*/;
	}
	toTuple(other : T): Tuple<Boolean, T> {
		return new Tuple<>(true, this.value);
	}
	and<R>(other : () => Option<R>): Option<Tuple<T, R>> {
		return other.get().map((otherValue) => new Tuple<>(this.value, otherValue));
	}
}
class None<T> implements Option<T> {
	map<R>(mapper : (arg0 : T) => R): Option<R> {
		return new None<>();
	}
	isEmpty(): boolean {
		return true;
	}
	orElse(other : T): T {
		return other;
	}
	orElseGet(supplier : () => T): T {
		return supplier();
	}
	isPresent(): boolean {
		return false;
	}
	ifPresent(consumer : (arg0 : T) => void): void {
	}
	or(other : () => Option<T>): Option<T> {
		return other.get();
	}
	flatMap<R>(mapper : (arg0 : T) => Option<R>): Option<R> {
		return new None<>();
	}
	filter(predicate : (arg0 : T) => boolean): Option<T> {
		return new None<>();
	}
	toTuple(other : T): Tuple<Boolean, T> {
		return new Tuple<>(false, other);
	}
	and<R>(other : () => Option<R>): Option<Tuple<T, R>> {
		return new None<>();
	}
}
class Placeholder {
	input : string;
	constructor (input : string) {
		this.input = input;
	}
	generate(): string {
		return generatePlaceholder(this.input);
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
	resolve(state : CompileState): Type {
		return Primitive.Unknown;
	}
}
class MapHead<T, R> implements Head<R> {
	head : Head<T>;
	mapper : (arg0 : T) => R;
	constructor (head : Head<T>, mapper : (arg0 : T) => R) {
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
	child : Value;
	property : string;
	constructor (child : Value, property : string) {
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
	resolve(state : CompileState): Type {
		return Primitive.Unknown;
	}
}
class Symbol {
	value : string;
	constructor (value : string) {
		this.value = value;
	}
	generate(): string {
		return this.value;
	}
	resolve(state : CompileState): Type {
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
}
class StringValue implements Value {
	value : string;
	constructor (value : string) {
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
	resolve(state : CompileState): Type {
		return Primitive.Unknown;
	}
}
class Not implements Value {
	child : string;
	constructor (child : string) {
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
	resolve(state : CompileState): Type {
		return Primitive.Unknown;
	}
}
class Lambda implements Value {
	paramNames : List<string>;
	content : string;
	constructor (paramNames : List<string>, content : string) {
		this.paramNames = paramNames;
		this.content = content;
	}
	generate(): string {
		let joinedParamNames : var = this.paramNames.query().collect(new Joiner(", ")).orElse("");
		return "(" + joinedParamNames + ")" + " => " + this.content;
	}
	toValue(): Option<Value> {
		return new Some<>(this);
	}
	findChild(): Option<Value> {
		return new None<>();
	}
	resolve(state : CompileState): Type {
		return Primitive.Unknown;
	}
}
class Invokable implements Value {
	caller : Caller;
	arguments : List<Value>;
	constructor (caller : Caller, arguments : List<Value>) {
		this.caller = caller;
		this.arguments = arguments;
	}
	generate(): string {
		let joinedArguments : var = generateValues(this.arguments);
		return this.caller.generate() + "(" + joinedArguments + ")";
	}
	toValue(): Option<Value> {
		return new Some<>(this);
	}
	findChild(): Option<Value> {
		return new None<>();
	}
	resolve(state : CompileState): Type {
		return Primitive.Unknown;
	}
}
class Operation implements Value {
	left : Value;
	targetInfix : string;
	right : Value;
	constructor (left : Value, targetInfix : string, right : Value) {
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
	resolve(state : CompileState): Type {
		return Primitive.Unknown;
	}
}
class ConstructionCaller implements Caller {
	right : string;
	constructor (right : string) {
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
	arguments : List<string>;
	returns : string;
	constructor (arguments : List<string>, returns : string) {
		this.arguments = arguments;
		this.returns = returns;
	}
	generate(): string {
		let joinedArguments : var = this.arguments.queryWithIndices().map((tuple) => "arg" + tuple.left + " : " + tuple.right).collect(new Joiner(", ")).orElse("");
		return "(" + joinedArguments + ") => " + this.returns;
	}
	isFunctional(): boolean {
		return true;
	}
}
class Generic implements Type {
	base : string;
	arguments : List<string>;
	constructor (base : string, arguments : List<string>) {
		this.base = base;
		this.arguments = arguments;
	}
	generate(): string {
		return this.base + "<" + generateValueStrings(this.arguments) + ">";
	}
	isFunctional(): boolean {
		return false;
	}
}
class Main  {
	main(): void {
		let source : var = Paths.get(".", "src", "java", "magma", "Main.java");
		let target : var = source.resolveSibling("main.ts");
		readString(source).match((input) => compileAndWrite(input, target), (value) => new Some<IOException>(value)).ifPresent(Throwable.printStackTrace);
	}
	compileAndWrite(input : string, target : Path): Option<IOException> {
		let output : var = compileRoot(input);
		return writeString(target, output);
	}
	writeString(target : Path, output : string): Option<IOException> {/*try {
            Files.writeString(target, output);
            return new None<IOException>();
        }*//* catch (IOException e) {
            return new Some<IOException>(e);
        }*/
	}
	readString(source : Path): Result<string, IOException> {/*try {
            return new Ok<>(Files.readString(source));
        }*//* catch (IOException e) {
            return new Err<>(e);
        }*/
	}
	compileRoot(input : string): string {
		let compiled : var = compileStatements(new CompileState(), input, Main.compileRootSegment);
		return compiled.left.output + compiled.right;
	}
	compileStatements(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => Tuple<CompileState, string>): Tuple<CompileState, string> {
		return compileAll(state, input, Main.foldStatements, mapper, Main.mergeStatements);
	}
	compileAll(state : CompileState, input : string, folder : (arg0 : DivideState, arg1 : string) => DivideState, mapper : (arg0 : CompileState, arg1 : string) => Tuple<CompileState, string>, merger : (arg0 : StringBuilder, arg1 : string) => StringBuilder): Tuple<CompileState, string> {
		let folded : var = parseAll(state, input, folder, mapper);
		return new Tuple<>(folded.left, generateAll(folded.right, merger));
	}
	generateAll(elements : List<string>, merger : (arg0 : StringBuilder, arg1 : string) => StringBuilder): string {
		return elements.query().fold(new StringBuilder(), merger).toString();
	}
	parseAll<T>(state : CompileState, input : string, folder : (arg0 : DivideState, arg1 : string) => DivideState, mapper : (arg0 : CompileState, arg1 : string) => Tuple<CompileState, T>): Tuple<CompileState, List<T>> {
		return divide(input, folder).query().fold(new Tuple<CompileState, List<T>>(state, Lists.empty()), (current, segment) => {
			let currentState : var = current.left;
			let currentElement : var = current.right;
			let mappedTuple : var = mapper(currentState, segment);
			let mappedState : var = mappedTuple.left;
			let mappedElement : var = mappedTuple.right;
			currentElement.add(mappedElement);
			return new Tuple<>(mappedState, currentElement);
		});
	}
	mergeStatements(cache : StringBuilder, element : string): StringBuilder {
		return cache.append(element);
	}
	divide(input : string, folder : (arg0 : DivideState, arg1 : string) => DivideState): List<string> {
		let current : var = new DivideState(input);
		while (true){
			let maybePopped : var = current.pop();
			if (maybePopped.isEmpty()){
				break;
			}
			let poppedTuple : var = maybePopped.orElse(null);
			let poppedState : var = poppedTuple.left;
			let popped : var = poppedTuple.right;
			current = foldSingleQuotes(poppedState, popped).or(() => foldDoubleQuotes(poppedState, popped)).orElseGet(() => folder(poppedState, popped));
		}
		return current.advance().segments;
	}
	foldDoubleQuotes(state : DivideState, c : string): Option<DivideState> {
		if (c !== "\""){
			return new None<DivideState>();
		}
		let appended : var = state.append(c);
		while (true){
			let maybeTuple : var = appended.popAndAppendToTuple();
			if (maybeTuple.isEmpty()){
				break;
			}
			let tuple : var = maybeTuple.orElse(null);
			appended = tuple.left;
			if (tuple.right === "\\"){
				appended = appended.popAndAppendToOption().orElse(appended);
			}
			if (tuple.right === "\""){
				break;
			}
		}
		return new Some<DivideState>(appended);
	}
	foldSingleQuotes(state : DivideState, c : string): Option<DivideState> {
		if (c !== "\'"){
			return new None<DivideState>();
		}
		return state.append(c).popAndAppendToTuple().flatMap(Main.foldEscaped).flatMap(DivideState.popAndAppendToOption);
	}
	foldEscaped(tuple : Tuple<DivideState, string>): Option<DivideState> {
		let state : var = tuple.left;
		let c : var = tuple.right;
		if (c === "\\"){
			return state.popAndAppendToOption();
		}
		return new Some<DivideState>(state);
	}
	foldStatements(state : DivideState, c : string): DivideState {
		let appended : var = state.append(c);
		if (c === ";" && appended.isLevel()){
			return appended.advance();
		}
		if (c === "}" && appended.isShallow()){
			return appended.advance().exit();
		}
		if (c === "{" || c === "("){
			return appended.enter();
		}
		if (c === "}" || c === ")"){
			return appended.exit();
		}
		return appended;
	}
	compileRootSegment(state : CompileState, input : string): Tuple<CompileState, string> {
		return compileOrPlaceholder(state, input, Lists.of(Main.compileWhitespace, Main.compileNamespaced, createStructureRule("class ", "class ")));
	}
	createStructureRule(sourceInfix : string, targetInfix : string): (arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, string>> {
		return (state, input1) => compileFirst(input1, sourceInfix, (_, right1) => {
			return compileFirst(right1, "{", (beforeContent, withEnd) => {
				return compileSuffix(withEnd.strip(), "}", (inputContent) => {
					return compileLast(beforeContent, " implements ", (s, s2) => {
						return parseType(state, s2).flatMap((implementingTuple) => {
							return getOr(targetInfix, implementingTuple.left, s, inputContent, new Some<>(implementingTuple.right));
						});
					}).or(() => {
						return getOr(targetInfix, state, beforeContent, inputContent, new None<>());
					});
				});
			});
		});
	}
	getOr(targetInfix : string, state : CompileState, beforeContent : string, inputContent : string, maybeImplementing : Option<Type>): Option<Tuple<CompileState, string>> {
		return compileFirst(beforeContent, "(", (rawName, withParameters) => {
			return compileFirst(withParameters, ")", (parametersString, _) => {
				let name : var = rawName.strip();
				let parametersTuple : var = parseParameters(state, parametersString);
				let parameters : var = retainDefinitionsFromParameters(parametersTuple.right);
				return assembleStructure(parametersTuple.left, targetInfix, inputContent, name, parameters, maybeImplementing);
			});
		}).or(() => {
			return assembleStructure(state, targetInfix, inputContent, beforeContent, Lists.empty(), maybeImplementing);
		});
	}
	retainDefinitionsFromParameters(parameters : List<Parameter>): List<Definition> {
		return parameters.query().map(Parameter.asDefinition).flatMap(Iterators.fromOption).collect(new ListCollector<>());
	}
	assembleStructure(state : CompileState, infix : string, content : string, beforeParams : string, parameters : List<Definition>, maybeImplementing : Option<Type>): Option<Tuple<CompileState, string>> {
		return compileSuffix(beforeParams.strip(), ">", (withoutTypeParamEnd) => {
			return compileFirst(withoutTypeParamEnd, "<", (name, typeParamsString) => {
				let typeParams : var = divideValues(typeParamsString);
				return assembleStructure(state, infix, content, name, typeParams, parameters, maybeImplementing);
			});
		}).or(() => {
			return assembleStructure(state, infix, content, beforeParams, Lists.empty(), parameters, maybeImplementing);
		});
	}
	assembleStructure(state : CompileState, infix : string, content : string, name : string, typeParams : List<string>, parameters : List<Definition>, maybeImplementing : Option<Type>): Option<Tuple<CompileState, string>> {
		let outputContentTuple : var = compileStatements(state.withStructureName(name), content, Main.compileClassSegment);
		let outputContentState : var = outputContentTuple.left;
		let outputContent : var = outputContentTuple.right;
		let joinedParametersAsClassDefinitions : var = joinParameters(parameters);
		let constructorString : var = generateConstructorFromRecordParameters(parameters);
		let joinedTypeParams : var = joinTypeParams(typeParams);
		let implementingString : var = generateImplementing(maybeImplementing);
		let generated : var = infix + name + joinedTypeParams + implementingString + " {" + joinedParametersAsClassDefinitions + constructorString + outputContent + "\n}\n";
		return new Some<>(new Tuple<>(outputContentState.append(generated), ""));
	}
	generateImplementing(maybeImplementing : Option<Type>): string {
		return maybeImplementing.map(Type.generate).map((inner) => " implements " + inner).orElse("");
	}
	joinTypeParams(typeParams : List<string>): string {
		return typeParams.query().collect(new Joiner(", ")).map((inner) => "<" + inner + ">").orElse("");
	}
	generateConstructorFromRecordParameters(parameters : List<Definition>): string {
		return parameters.query().map(Definition.generate).collect(new Joiner(", ")).map((generatedParameters) => generateConstructorWithParameterString(parameters, generatedParameters)).orElse("");
	}
	generateConstructorWithParameterString(parameters : List<Definition>, parametersString : string): string {
		let constructorAssignments : var = generateConstructorAssignments(parameters);
		return "\n\tconstructor (" + parametersString + ") {" + constructorAssignments + "\n\t}";
	}
	generateConstructorAssignments(parameters : List<Definition>): string {
		return parameters.query().map((definition) => "\n\t\tthis." + definition.name + " = " + definition.name + ";").collect(new Joiner()).orElse("");
	}
	joinParameters(parameters : List<Definition>): string {
		return parameters.query().map(Definition.generate).map((generated) => "\n\t" + generated + ";").collect(new Joiner()).orElse("");
	}
	compileNamespaced(state : CompileState, input : string): Option<Tuple<CompileState, string>> {
		let stripped : var = input.strip();
		if (stripped.startsWith("package ") || stripped.startsWith("import ")){
			return new Some<>(new Tuple<>(state, ""));
		}
		else {
			return new None<>();
		}
	}
	compileOrPlaceholder(state : CompileState, input : string, rules : List<(arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, string>>>): Tuple<CompileState, string> {
		return or(state, input, rules).orElseGet(() => new Tuple<>(state, generatePlaceholder(input)));
	}
	or<T>(state : CompileState, input : string, rules : List<(arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, T>>>): Option<Tuple<CompileState, T>> {
		return rules.query().map((rule) => rule.apply(state, input)).flatMap(Iterators.fromOption).next();
	}
	compileClassSegment(state1 : CompileState, input1 : string): Tuple<CompileState, string> {
		return compileOrPlaceholder(state1, input1, Lists.of(Main.compileWhitespace, createStructureRule("class ", "class "), createStructureRule("interface ", "interface "), createStructureRule("record ", "class "), Main.compileMethod, Main.compileFieldDefinition));
	}
	compileMethod(state : CompileState, input : string): Option<Tuple<CompileState, string>> {
		return compileFirst(input, "(", (beforeParams, withParams) => {
			return compileLast(beforeParams.strip(), " ", (_, name) => {
				if (state.structureName.filter(name.equals).isPresent()){
					return compileMethodWithBeforeParams(state, new ConstructorHeader(), withParams);
				}
				return parseDefinition(state, beforeParams).flatMap((tuple) => compileMethodWithBeforeParams(tuple.left, tuple.right, withParams));
			});
		});
	}
	compileMethodWithBeforeParams(state : CompileState, header : MethodHeader, withParams : string): Option<Tuple<CompileState, string>> {
		return compileFirst(withParams, ")", (params, afterParams) => {
			let parametersTuple : var = parseParameters(state, params);
			let parametersState : var = parametersTuple.left;
			let parameters : var = parametersTuple.right;
			let definitions : var = retainDefinitionsFromParameters(parameters);
			let joinedDefinitions : var = definitions.query().map(Definition.generate).collect(new Joiner(", ")).orElse("");
			let headerGenerated : var = header.generateWithAfterName("(" + joinedDefinitions + ")");
			return compilePrefix(afterParams.strip(), "{", (withoutContentStart) => {
				return compileSuffix(withoutContentStart.strip(), "}", (withoutContentEnd) => {
					let statementsTuple : var = compileFunctionStatements(parametersState.enterDepth().enterDepth().defineAll(definitions), withoutContentEnd);
					return new Some<>(new Tuple<>(statementsTuple.left.exitDepth().exitDepth(), "\n\t" + headerGenerated + " {" + statementsTuple.right + "\n\t}"));
				});
			}).or(() => {
				if (afterParams.strip().equals(";")){
					return new Some<>(new Tuple<>(parametersState, "\n\t" + headerGenerated + ";"));
				}
				return new None<>();
			});
		});
	}
	parseParameters(state : CompileState, params : string): Tuple<CompileState, List<Parameter>> {
		return parseValues(state, params, Main.parseParameter);
	}
	compileFunctionStatements(state : CompileState, input : string): Tuple<CompileState, string> {
		return compileStatements(state, input, Main.compileFunctionSegment);
	}
	compileFunctionSegment(state : CompileState, input : string): Tuple<CompileState, string> {
		return compileOrPlaceholder(state, input, Lists.of(Main.compileWhitespace, Main.compileEmptySegment, Main.compileBlock, Main.compileFunctionStatement, Main.compileReturnWithoutSuffix));
	}
	compileEmptySegment(state : CompileState, input : string): Option<Tuple<CompileState, string>> {
		if (input.strip().equals(";")){
			return new Some<>(new Tuple<>(state, ";"));
		}
		else {
			return new None<>();
		}
	}
	compileReturnWithoutSuffix(state1 : CompileState, input1 : string): Option<Tuple<CompileState, string>> {
		return compileReturn(input1, (withoutPrefix) => compileValue(state1, withoutPrefix)).map((tuple) => new Tuple<>(tuple.left, generateIndent(state1.depth) + tuple.right));
	}
	compileBlock(state : CompileState, input : string): Option<Tuple<CompileState, string>> {
		return compileSuffix(input.strip(), "}", (withoutEnd) => {
			return compileSplit(splitFoldedLast(withoutEnd, "", Main.foldBlockStarts), (beforeContentWithEnd, content) => {
				return compileSuffix(beforeContentWithEnd, "{", (beforeContent) => {
					return compileBlockHeader(state, beforeContent).flatMap((headerTuple) => {
						let contentTuple : var = compileFunctionStatements(headerTuple.left.enterDepth(), content);
						let indent : var = generateIndent(state.depth());
						return new Some<>(new Tuple<>(contentTuple.left.exitDepth(), indent + headerTuple.right + "{" + contentTuple.right + indent + "}"));
					});
				});
			});
		});
	}
	foldBlockStarts(state : DivideState, c : string): DivideState {
		let appended : var = state.append(c);
		if (c === "{"){
			let entered : var = appended.enter();
			if (appended.isShallow()){
				return entered.advance();
			}
			else {
				return entered;
			}
		}
		if (c === "}"){
			return appended.exit();
		}
		return appended;
	}
	compileBlockHeader(state : CompileState, input : string): Option<Tuple<CompileState, string>> {
		return or(state, input, Lists.of(createConditionalRule("if"), createConditionalRule("while"), Main.compileElse));
	}
	createConditionalRule(prefix : string): (arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, string>> {
		return (state1, input1) => compilePrefix(input1.strip(), prefix, (withoutPrefix) => {
			let strippedCondition : var = withoutPrefix.strip();
			return compilePrefix(strippedCondition, "(", (withoutConditionStart) => {
				return compileSuffix(withoutConditionStart, ")", (withoutConditionEnd) => {
					let tuple : var = compileValueOrPlaceholder(state1, withoutConditionEnd);
					return new Some<>(new Tuple<>(tuple.left, prefix + " (" + tuple.right + ")"));
				});
			});
		});
	}
	compileElse(state : CompileState, input : string): Option<Tuple<CompileState, string>> {
		if (input.strip().equals("else")){
			return new Some<>(new Tuple<>(state, "else "));
		}
		else {
			return new None<>();
		}
	}
	compileFunctionStatement(state : CompileState, input : string): Option<Tuple<CompileState, string>> {
		return compileSuffix(input.strip(), ";", (withoutEnd) => {
			let valueTuple : var = compileFunctionStatementValue(state, withoutEnd);
			return new Some<>(new Tuple<>(valueTuple.left, generateIndent(state.depth()) + valueTuple.right + ";"));
		});
	}
	generateIndent(indent : number): string {
		return "\n" + "\t".repeat(indent);
	}
	compileFunctionStatementValue(state : CompileState, withoutEnd : string): Tuple<CompileState, string> {
		return compileOrPlaceholder(state, withoutEnd, Lists.of(Main.compileReturnWithValue, Main.compileAssignment, (state1, input) => parseInvokable(state1, input).map((tuple) => new Tuple<>(tuple.left, tuple.right.generate())), createPostRule("++"), createPostRule("--"), Main.compileBreak));
	}
	compileBreak(state : CompileState, input : string): Option<Tuple<CompileState, string>> {
		if (input.strip().equals("break")){
			return new Some<>(new Tuple<>(state, "break"));
		}
		else {
			return new None<>();
		}
	}
	createPostRule(suffix : string): (arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, string>> {
		return (state1, input) => compileSuffix(input.strip(), suffix, (child) => {
			let tuple : var = compileValueOrPlaceholder(state1, child);
			return new Some<>(new Tuple<>(tuple.left, tuple.right + suffix));
		});
	}
	compileReturnWithValue(state : CompileState, input : string): Option<Tuple<CompileState, string>> {
		return compileReturn(input, (value1) => compileValue(state, value1));
	}
	compileReturn(input : string, mapper : (arg0 : string) => Option<Tuple<CompileState, string>>): Option<Tuple<CompileState, string>> {
		return compilePrefix(input.strip(), "return ", (value) => {
			return mapper(value).flatMap((tuple) => {
				return new Some<>(new Tuple<>(tuple.left, "return " + tuple.right));
			});
		});
	}
	parseInvokable(state : CompileState, input : string): Option<Tuple<CompileState, Value>> {
		return compileSuffix(input.strip(), ")", (withoutEnd) => {
			return compileSplit(splitFoldedLast(withoutEnd, "", Main.foldInvocationStarts), (callerWithArgStart, arguments) => {
				return compileSuffix(callerWithArgStart, "(", (callerString) => {
					return compilePrefix(callerString.strip(), "new ", (type) => {
						let callerTuple : var = compileTypeOrPlaceholder(state, type);
						let callerState : var = callerTuple.right;
						let caller : var = callerTuple.left;
						return assembleInvokable(caller, new ConstructionCaller(callerState), arguments);
					}).or(() => {
						return parseValue(state, callerString).flatMap((callerTuple) => {
							return assembleInvokable(callerTuple.left, callerTuple.right, arguments);
						});
					});
				});
			});
		});
	}
	parseValueOrPlaceholder(state : CompileState, callerString : string): Tuple<CompileState, Value> {
		return parseValue(state, callerString).orElseGet(() => new Tuple<>(state, new Placeholder(callerString)));
	}
	splitFoldedLast(input : string, delimiter : string, folder : (arg0 : DivideState, arg1 : string) => DivideState): Option<Tuple<string, string>> {
		return splitFolded(input, folder, (divisions1) => selectLast(divisions1, delimiter));
	}
	splitFolded(input : string, folder : (arg0 : DivideState, arg1 : string) => DivideState, selector : (arg0 : List<string>) => Option<Tuple<string, string>>): Option<Tuple<string, string>> {
		let divisions : var = divide(input, folder);
		if (divisions.size() < 2){
			return new None<Tuple<string, string>>();
		}
		return selector(divisions);
	}
	selectLast(divisions : List<string>, delimiter : string): Option<Tuple<string, string>> {
		let beforeLast : var = divisions.subList(0, divisions.size() - 1).orElse(divisions);
		let last : var = divisions.findLast().orElse(null);
		let joined : var = beforeLast.query().collect(new Joiner(delimiter)).orElse("");
		return new Some<Tuple<string, string>>(new Tuple<string, string>(joined, last));
	}
	foldInvocationStarts(state : DivideState, c : string): DivideState {
		let appended : var = state.append(c);
		if (c === "("){
			let entered : var = appended.enter();
			if (entered.isShallow()){
				return entered.advance();
			}
			else {
				return entered;
			}
		}
		if (c === ")"){
			return appended.exit();
		}
		return appended;
	}
	assembleInvokable(state : CompileState, oldCaller : Caller, argumentsString : string): Option<Tuple<CompileState, Value>> {
		let argumentsTuple : var = parseValues(state, argumentsString, Main.parseArgument);
		let argumentsState : var = argumentsTuple.left;
		let arguments : var = retain(argumentsTuple.right, Argument.toValue);
		let newCaller : var = transformCaller(argumentsState, oldCaller);
		return new Some<>(new Tuple<>(argumentsState, new Invokable(newCaller, arguments)));
	}
	transformCaller(state : CompileState, oldCaller : Caller): Caller {
		return oldCaller.findChild().flatMap((parent) => {
			let parentType : var = parent.resolve(state);
			if (parentType.isFunctional()){
				return new Some<Caller>(parent);
			}
			return new None<>();
		}).orElse(oldCaller);
	}
	retain<T, R>(arguments : List<T>, mapper : (arg0 : T) => Option<R>): List<R> {
		return arguments.query().map(mapper).flatMap(Iterators.fromOption).collect(new ListCollector<>());
	}
	parseArgument(state1 : CompileState, input : string): Tuple<CompileState, Argument> {
		/*return parseValue(state1, input)
                .<Tuple<CompileState, Argument>>map(tuple -> new Tuple<>(tuple.left, tuple.right))
                .orElseGet(() -> new Tuple<>(state1, new Placeholder(input)))*/;
	}
	compileAssignment(state : CompileState, input : string): Option<Tuple<CompileState, string>> {
		return compileFirst(input, "=", (destination, source) => {
			let sourceTuple : var = compileValueOrPlaceholder(state, source);
			let destinationTuple : var = compileValue(sourceTuple.left, destination).or(() => parseDefinition(sourceTuple.left, destination).map((tuple) => new Tuple<>(tuple.left, "let " + tuple.right.generate()))).orElseGet(() => new Tuple<>(sourceTuple.left, generatePlaceholder(destination)));
			return new Some<>(new Tuple<>(destinationTuple.left, destinationTuple.right + " = " + sourceTuple.right));
		});
	}
	compileValueOrPlaceholder(state : CompileState, input : string): Tuple<CompileState, string> {
		return compileValue(state, input).orElseGet(() => new Tuple<>(state, generatePlaceholder(input)));
	}
	compileValue(state : CompileState, input : string): Option<Tuple<CompileState, string>> {
		return parseValue(state, input).map((tuple) => new Tuple<>(tuple.left, tuple.right.generate()));
	}
	parseValue(state : CompileState, input : string): Option<Tuple<CompileState, Value>> {
		return or(state, input, Lists.of(Main.parseLambda, createOperatorRule("+"), createOperatorRule("-"), createOperatorRule("<="), createOperatorRule("<"), createOperatorRule("&&"), createOperatorRule("||"), createOperatorRule(">="), Main.parseInvokable, createAccessRule("."), createAccessRule("::"), Main.parseSymbol, Main.parseNot, Main.parseNumber, createOperatorRuleWithDifferentInfix("==", "==="), createOperatorRuleWithDifferentInfix("!=", "!=="), createTextRule("\""), createTextRule("'")));
	}
	createTextRule(slice : string): (arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, Value>> {
		return (state1, input1) => {
			let stripped : var = input1.strip();
			if (!stripped.startsWith(slice) || !stripped.endsWith(slice) || stripped.length() <= slice.length()){
				return new None<>();
			}
			let value : var = stripped.substring(slice.length()) - slice.length();
			return new Some<>(new Tuple<>(state1, new StringValue(value)));
		};
	}
	parseNot(state : CompileState, input : string): Option<Tuple<CompileState, Value>> {
		return compilePrefix(input.strip(), "!", (withoutPrefix) => {
			let childTuple : var = compileValueOrPlaceholder(state, withoutPrefix);
			let childState : var = childTuple.left;
			let child : var = "!" + childTuple.right;
			return new Some<>(new Tuple<>(childState, new Not(child)));
		});
	}
	parseLambda(state : CompileState, input : string): Option<Tuple<CompileState, Value>> {
		return compileFirst(input, "->", (beforeArrow, afterArrow) => {
			let strippedBeforeArrow : var = beforeArrow.strip();
			if (isSymbol(strippedBeforeArrow)){
				return compileLambdaWithParameterNames(state, Lists.of(strippedBeforeArrow), afterArrow);
			}
			return compilePrefix(strippedBeforeArrow, "(", (withoutStart) => {
				return compileSuffix(withoutStart, ")", (withoutEnd) => {
					let paramNames : var = divideValues(withoutEnd);
					if (paramNames.query().allMatch(Main.isSymbol)){
						return compileLambdaWithParameterNames(state, paramNames, afterArrow);
					}
					else {
						return new None<>();
					}
				});
			});
		});
	}
	compileLambdaWithParameterNames(state : CompileState, paramNames : List<string>, afterArrow : string): Option<Tuple<CompileState, Value>> {
		let strippedAfterArrow : var = afterArrow.strip();
		return compilePrefix(strippedAfterArrow, "{", (withoutContentStart) => {
			return compileSuffix(withoutContentStart, "}", (withoutContentEnd) => {
				let statementsTuple : var = compileFunctionStatements(state.enterDepth(), withoutContentEnd);
				let statementsState : var = statementsTuple.left;
				let statements : var = statementsTuple.right;
				let exited : var = statementsState.exitDepth();
				return assembleLambda(exited, paramNames, "{" + statements + generateIndent(exited.depth) + "}");
			});
		}).or(() => {
			let tuple : var = compileValueOrPlaceholder(state, strippedAfterArrow);
			return assembleLambda(tuple.left, paramNames, tuple.right);
		});
	}
	assembleLambda(exited : CompileState, paramNames : List<string>, content : string): Option<Tuple<CompileState, Value>> {
		return new Some<>(new Tuple<>(exited, new Lambda(paramNames, content)));
	}
	createOperatorRule(infix : string): (arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, Value>> {
		return createOperatorRuleWithDifferentInfix(infix, infix);
	}
	createAccessRule(infix : string): (arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, Value>> {
		return (state, input) => compileLast(input, infix, (childString, rawProperty) => {
			let property : var = rawProperty.strip();
			if (!isSymbol(property)){
				return new None<>();
			}
			return parseValue(state, childString).flatMap((childTuple) => {
				let childState : var = childTuple.left;
				let child : var = childTuple.right;
				return new Some<>(new Tuple<>(childState, new Access(child, property)));
			});
		});
	}
	createOperatorRuleWithDifferentInfix(sourceInfix : string, targetInfix : string): (arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, Value>> {
		return (state1, input1) => {
			return compileSplit(splitFolded(input1, foldOperator(sourceInfix), (divisions) => selectFirst(divisions, sourceInfix)), (leftString, rightString) => {
				return parseValue(state1, leftString).flatMap((leftTuple) => {
					return parseValue(leftTuple.left, rightString).flatMap((rightTuple) => {
						let left : var = leftTuple.right;
						let right : var = rightTuple.right;
						return new Some<>(new Tuple<>(rightTuple.left, new Operation(left, targetInfix, right)));
					});
				});
			});
		};
	}
	selectFirst(divisions : List<string>, delimiter : string): Option<Tuple<string, string>> {
		let first : var = divisions.findFirst().orElse(null);
		let afterFirst : var = divisions.subList(1, divisions.size()).orElse(divisions).query().collect(new Joiner(delimiter)).orElse("");
		return new Some<Tuple<string, string>>(new Tuple<string, string>(first, afterFirst));
	}
	foldOperator(infix : string): (arg0 : DivideState, arg1 : string) => DivideState {
		return (state, c) => {
			if (c === infix.charAt(0) && state.startsWith(infix.substring(1))){
				let length : var = infix.length() - 1;
				let counter : var = 0;
				let current : var = state;
				while (counter < length){
					counter++;
					current = current.pop().map(Tuple.left).orElse(current);
				}
				return current.advance();
			}
			return state.append(c);
		};
	}
	parseNumber(state : CompileState, input : string): Option<Tuple<CompileState, Value>> {
		let stripped : var = input.strip();
		if (isNumber(stripped)){
			return new Some<>(new Tuple<>(state, new Symbol(stripped)));
		}
		else {
			return new None<>();
		}
	}
	isNumber(input : string): boolean {
		return IntStream.range(0, input.length()).mapToObj(input.charAt).allMatch(Character.isDigit);
	}
	parseSymbol(state : CompileState, input : string): Option<Tuple<CompileState, Value>> {
		let stripped : var = input.strip();
		if (isSymbol(stripped)){
			return new Some<>(new Tuple<>(state, new Symbol(stripped)));
		}
		else {
			return new None<>();
		}
	}
	isSymbol(input : string): boolean {
		return IntStream.range(0, input.length()).allMatch((index) => isSymbolChar(index, input.charAt(index)));
	}
	isSymbolChar(index : number, c : string): boolean {
		return c === "_" || Character.isLetter(c) || (index !== 0 && Character.isDigit(c));
	}
	compilePrefix<T>(input : string, infix : string, mapper : (arg0 : string) => Option<Tuple<CompileState, T>>): Option<Tuple<CompileState, T>> {
		if (!input.startsWith(infix)){
			return new None<>();
		}
		let slice : var = input.substring(infix.length());
		return mapper(slice);
	}
	compileWhitespace(state : CompileState, input : string): Option<Tuple<CompileState, string>> {
		return parseWhitespace(state, input).map((tuple) => new Tuple<>(tuple.left, tuple.right.generate()));
	}
	parseWhitespace(state : CompileState, input : string): Option<Tuple<CompileState, Whitespace>> {
		if (input.isBlank()){
			return new Some<Tuple<CompileState, Whitespace>>(new Tuple<>(state, new Whitespace()));
		}
		return new None<Tuple<CompileState, Whitespace>>();
	}
	compileFieldDefinition(state : CompileState, input : string): Option<Tuple<CompileState, string>> {
		return compileSuffix(input.strip(), ";", (withoutEnd) => {
			let definitionTuple : var = compileParameterOrPlaceholder(state, withoutEnd);
			return new Some<>(new Tuple<>(definitionTuple.left, "\n\t" + definitionTuple.right + ";"));
		});
	}
	compileParameterOrPlaceholder(state : CompileState, input : string): Tuple<CompileState, string> {
		let tuple : var = parseParameter(state, input);
		return new Tuple<>(tuple.left, tuple.right.generate());
	}
	parseParameter(state : CompileState, input : string): Tuple<CompileState, Parameter> {
		/*return parseWhitespace(state, input).<Tuple<CompileState, Parameter>>map(tuple -> new Tuple<>(tuple.left, tuple.right))
                .or(() -> parseDefinition(state, input).map(tuple -> new Tuple<>(tuple.left, tuple.right)))
                .orElseGet(() -> new Tuple<>(state, new Placeholder(input)))*/;
	}
	parseDefinition(state : CompileState, input : string): Option<Tuple<CompileState, Definition>> {
		return compileLast(input.strip(), " ", (beforeName, name) => {
			return compileSplit(splitFoldedLast(beforeName.strip(), " ", Main.foldTypeSeparators), (beforeType, type) => {
				return compileSuffix(beforeType.strip(), ">", (withoutTypeParamEnd) => {
					return compileFirst(withoutTypeParamEnd, "<", (beforeTypeParams, typeParamsString) => {
						let typeParams : var = divideValues(typeParamsString);
						return assembleDefinition(state, new Some<string>(beforeTypeParams), name, typeParams, type);
					});
				}).or(() => {
					return assembleDefinition(state, new Some<string>(beforeType), name, Lists.empty(), type);
				});
			}).or(() => {
				return assembleDefinition(state, new None<string>(), name, Lists.empty(), beforeName);
			});
		});
	}
	divideValues(input : string): List<string> {
		return divide(input, Main.foldValues).query().map(String.strip).filter((value) => !value.isEmpty()).collect(new ListCollector<>());
	}
	foldTypeSeparators(state : DivideState, c : string): DivideState {
		if (c === " " && state.isLevel()){
			return state.advance();
		}
		let appended : var = state.append(c);
		if (c === "<"){
			return appended.enter();
		}
		if (c === ">"){
			return appended.exit();
		}
		return appended;
	}
	assembleDefinition(state : CompileState, maybeBeforeType : Option<string>, name : string, typeParams : List<string>, type : string): Option<Tuple<CompileState, Definition>> {
		let typeTuple : var = parseTypeOrPlaceholder(state, type);
		let generated : var = new Definition(maybeBeforeType, name, typeParams, typeTuple.right);
		return new Some<Tuple<CompileState, Definition>>(new Tuple<CompileState, Definition>(typeTuple.left, generated));
	}
	parseTypeOrPlaceholder(state : CompileState, type : string): Tuple<CompileState, Type> {
		return parseType(state, type).map((tuple) => new Tuple<CompileState, Type>(tuple.left, tuple.right)).orElseGet(() => new Tuple<>(state, new Placeholder(type)));
	}
	compileTypeOrPlaceholder(state : CompileState, type : string): Tuple<CompileState, string> {
		return compileType(state, type).orElseGet(() => new Tuple<>(state, generatePlaceholder(type)));
	}
	compileType(state : CompileState, type : string): Option<Tuple<CompileState, string>> {
		return parseType(state, type).map((tuple) => new Tuple<>(tuple.left, tuple.right.generate()));
	}
	parseType(state : CompileState, type : string): Option<Tuple<CompileState, Type>> {
		return or(state, type, Lists.of(Main.parseGeneric, Main.parsePrimitive, Main.parseSymbolType));
	}
	parseSymbolType(state : CompileState, input : string): Option<Tuple<CompileState, Type>> {
		let stripped : var = input.strip();
		if (isSymbol(stripped)){
			return new Some<>(new Tuple<>(state, new Symbol(stripped)));
		}
		return new None<>();
	}
	parsePrimitive(state : CompileState, input : string): Option<Tuple<CompileState, Type>> {
		return findPrimitiveValue(input.strip()).map((result) => new Tuple<>(state, result));
	}
	findPrimitiveValue(input : string): Option<Type> {
		let stripped : var = input.strip();
		if (stripped.equals("char") || stripped.equals("Character") || stripped.equals("String")){
			return new Some<>(Primitive.String);
		}
		if (stripped.equals("int") || stripped.equals("Integer")){
			return new Some<>(Primitive.Number);
		}
		if (stripped.equals("boolean")){
			return new Some<>(Primitive.Boolean);
		}
		if (stripped.equals("var")){
			return new Some<>(Primitive.Var);
		}
		if (stripped.equals("void")){
			return new Some<>(Primitive.Void);
		}
		return new None<>();
	}
	parseGeneric(state : CompileState, input : string): Option<Tuple<CompileState, Type>> {
		return compileSuffix(input.strip(), ">", (withoutEnd) => {
			return compileFirst(withoutEnd, "<", (baseString, argumentsString) => {
				let argumentsTuple : var = parseValues(state, argumentsString, Main.compileTypeArgument);
				let argumentsState : var = argumentsTuple.left;
				let arguments : var = argumentsTuple.right;
				let base : var = baseString.strip();
				return assembleFunctionType(argumentsState, base, arguments).or(() => {
					return new Some<>(new Tuple<>(argumentsState, new Generic(base, arguments)));
				});
			});
		});
	}
	assembleFunctionType(state : CompileState, base : string, arguments : List<string>): Option<Tuple<CompileState, Type>> {
		return mapFunctionType(base, arguments).map((generated) => new Tuple<>(state, generated));
	}
	mapFunctionType(base : string, arguments : List<string>): Option<Type> {
		if (base.equals("Function")){
			return arguments.findFirst().and(() => arguments.find(1)).map((tuple) => new FunctionType(Lists.of(tuple.left), tuple.right));
		}
		if (base.equals("BiFunction")){
			return arguments.find(0).and(() => arguments.find(1)).and(() => arguments.find(2)).map((tuple) => new FunctionType(Lists.of(tuple.left.left, tuple.left.right), tuple.right));
		}
		if (base.equals("Supplier")){
			return arguments.findFirst().map((first) => {
				return new FunctionType(Lists.empty(), first);
			});
		}
		if (base.equals("Consumer")){
			return arguments.findFirst().map((first) => {
				return new FunctionType(Lists.of(first), "void");
			});
		}
		if (base.equals("Predicate")){
			return arguments.findFirst().map((first) => {
				return new FunctionType(Lists.of(first), "boolean");
			});
		}
		return new None<>();
	}
	compileTypeArgument(state : CompileState, input : string): Tuple<CompileState, string> {
		return compileOrPlaceholder(state, input, Lists.of(Main.compileWhitespace, Main.compileType));
	}
	compileValues(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => Tuple<CompileState, string>): Tuple<CompileState, string> {
		let folded : var = parseValues(state, input, mapper);
		return new Tuple<>(folded.left, generateValueStrings(folded.right));
	}
	generateValueStrings(values : List<string>): string {
		return generateAll(values, Main.mergeValues);
	}
	parseValues<T>(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => Tuple<CompileState, T>): Tuple<CompileState, List<T>> {
		return parseAll(state, input, Main.foldValues, mapper);
	}
	mergeValues(cache : StringBuilder, element : string): StringBuilder {
		if (cache.isEmpty()){
			return cache.append(element);
		}
		return cache.append(", ").append(element);
	}
	foldValues(state : DivideState, c : string): DivideState {
		if (c === "," && state.isLevel()){
			return state.advance();
		}
		let appended : var = state.append(c);
		if (c === "-"){
			let peeked : var = appended.peek();
			if (peeked === ">"){
				return appended.popAndAppendToOption().orElse(appended);
			}
			else {
				return appended;
			}
		}
		if (c === "<" || c === "("){
			return appended.enter();
		}
		if (c === ">" || c === ")"){
			return appended.exit();
		}
		return appended;
	}
	compileLast<T>(input : string, infix : string, mapper : (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return compileInfix(input, infix, Main.findLast, mapper);
	}
	findLast(input : string, infix : string): number {
		return input.lastIndexOf(infix);
	}
	compileSuffix<T>(input : string, suffix : string, mapper : (arg0 : string) => Option<T>): Option<T> {
		if (!input.endsWith(suffix)){
			return new None<T>();
		}
		let content : var = input.substring(0) - suffix.length();
		return mapper(content);
	}
	compileFirst<T>(input : string, infix : string, mapper : (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return compileInfix(input, infix, Main.findFirst, mapper);
	}
	compileInfix<T>(input : string, infix : string, locator : (arg0 : string, arg1 : string) => number, mapper : (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return compileSplit(split(input, infix, locator), mapper);
	}
	compileSplit<T>(splitter : Option<Tuple<string, string>>, mapper : (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return splitter.flatMap((tuple) => mapper(tuple.left, tuple.right));
	}
	split(input : string, infix : string, locator : (arg0 : string, arg1 : string) => number): Option<Tuple<string, string>> {
		let index : var = locator(input, infix);
		if (index < 0){
			return new None<Tuple<string, string>>();
		}
		let left : var = input.substring(0, index);
		let right : var = input.substring(index + infix.length());
		return new Some<Tuple<string, string>>(new Tuple<string, string>(left, right));
	}
	findFirst(input : string, infix : string): number {
		return input.indexOf(infix);
	}
	generatePlaceholder(input : string): string {
		let replaced : var = input.replace("/*", "start").replace("*/", "end");
		return "/*" + replaced + "*/";
	}
	generateValues(arguments : List<Value>): string {
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
    }*/
}
