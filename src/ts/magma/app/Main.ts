import { Console } from "../../magma/api/Console";
import { Tuple2 } from "../../magma/api/Tuple2";
import { Tuple2Impl } from "../../magma/api/Tuple2Impl";
import { Collector } from "../../magma/api/collect/Collector";
import { Head } from "../../magma/api/collect/Head";
import { List } from "../../magma/api/collect/List";
import { Lists } from "../../magma/api/collect/Lists";
import { RangeHead } from "../../magma/api/collect/RangeHead";
import { HeadedQuery } from "../../magma/api/collect/query/HeadedQuery";
import { Query } from "../../magma/api/collect/query/Query";
import { IOError } from "../../magma/api/io/IOError";
import { Path } from "../../magma/api/io/Path";
import { Option } from "../../magma/api/option/Option";
import { Result } from "../../magma/api/result/Result";
import { Characters } from "../../magma/api/text/Characters";
import { Strings } from "../../magma/api/text/Strings";
import { Files } from "../../magma/jvm/io/Files";
import { None } from "./None";
import { Some } from "./Some";
import { EmptyHead } from "./EmptyHead";
import { SingleHead } from "./SingleHead";
import { ListCollector } from "./ListCollector";
interface MethodHeader {
	generateWithAfterName(afterName: string): string;
	hasAnnotation(annotation: string): boolean;
	addModifier(modifier: string): MethodHeader;
	removeModifier(modifier: string): MethodHeader;
}
interface Parameter {
	generate(): string;
	asDefinition(): Option<Definition>;
}
interface Value extends Argument, Caller  {
	resolve(state: CompileState): Type;
	generateAsEnumValue(structureName: string): Option<string>;
}
interface Argument {
	toValue(): Option<Value>;
}
interface Caller {
	generate(): string;
	findChild(): Option<Value>;
}
interface Type {
	generate(): string;
	isFunctional(): boolean;
	isVar(): boolean;
	generateBeforeName(): string;
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
		return new DivideState(this.segments.addLast(this.buffer), "", this.depth, this.input, this.index);
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
	pop(): Option<Tuple2<DivideState, string>> {
		if (this.index >= Strings.length(this.input)){
			return new None<Tuple2<DivideState, string>>();
		}
		let c = this.input.charAt(this.index);
		let nextState = new DivideState(this.segments, this.buffer, this.depth, this.input, this.index + 1);
		return new Some<Tuple2<DivideState, string>>(new Tuple2Impl<DivideState, string>(nextState, c));
	}
	popAndAppendToTuple(): Option<Tuple2<DivideState, string>> {
		return this.pop().map((inner: Tuple2<DivideState, string>) => new Tuple2Impl<DivideState, string>(inner.left().append(inner.right()), inner.right()));
	}
	popAndAppendToOption(): Option<DivideState> {
		return this.popAndAppendToTuple().map((tuple: Tuple2<DivideState, string>) => tuple.left());
	}
	peek(): string {
		return this.input.charAt(this.index);
	}
	startsWith(slice: string): boolean {
		return Strings.sliceFrom(this.input, this.index).startsWith(slice);
	}
}
class CompileState {
	imports: List<Import>;
	output: string;
	maybeStructureName: Option<string>;
	depth: number;
	definitions: List<Definition>;
	namespace: List<string>;
	constructor (imports: List<Import>, output: string, maybeStructureName: Option<string>, depth: number, definitions: List<Definition>, namespace: List<string>) {
		this.imports = imports;
		this.output = output;
		this.maybeStructureName = maybeStructureName;
		this.depth = depth;
		this.definitions = definitions;
		this.namespace = namespace;
	}
	static createInitial(namespace: List<string>): CompileState {
		return new CompileState(Lists.empty(), "", new None<string>(), 0, Lists.empty(), namespace);
	}
	append(element: string): CompileState {
		return new CompileState(this.imports, this.output + element, this.maybeStructureName, this.depth, this.definitions, this.namespace);
	}
	withStructureName(name: string): CompileState {
		return new CompileState(this.imports, this.output, new Some<string>(name), this.depth, this.definitions, this.namespace);
	}
	enterDepth(): CompileState {
		return new CompileState(this.imports, this.output, this.maybeStructureName, this.depth + 1, this.definitions, this.namespace);
	}
	exitDepth(): CompileState {
		return new CompileState(this.imports, this.output, this.maybeStructureName, this.depth - 1, this.definitions, this.namespace);
	}
	defineAll(definitions: List<Definition>): CompileState {
		return new CompileState(this.imports, this.output, this.maybeStructureName, this.depth, this.definitions.addAll(definitions), this.namespace);
	}
	resolve(name: string): Option<Type> {
		return this.definitions.queryReversed().filter((definition: Definition) => Strings.equalsTo(definition.name, name)).map((definition1: Definition) => definition1.type).next();
	}
	addImport(importString: Import): CompileState {
		if (this.imports.query().filter((node: Import) => node.child.equals(importString.child)).next().isPresent()){
			return this;
		}
		return new CompileState(this.imports.addLast(importString), this.output, this.maybeStructureName, this.depth, this.definitions, this.namespace);
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
	addModifier(modifier: string): MethodHeader {
		return new Definition(this.annotations, this.modifiers.addLast(modifier), this.typeParams, this.type, this.name);
	}
	removeModifier(modifier: string): MethodHeader {
		return new Definition(this.annotations, this.modifiers.removeValue(modifier), this.typeParams, this.type, this.name);
	}
}
class ConstructorHeader implements MethodHeader {
	generateWithAfterName(afterName: string): string {
		return "constructor " + afterName;
	}
	hasAnnotation(annotation: string): boolean {
		return false;
	}
	addModifier(modifier: string): MethodHeader {
		return this;
	}
	removeModifier(modifier: string): MethodHeader {
		return this;
	}
}
export class Ok<T, X> implements Result<T, X> {
	value: T;
	constructor (value: T) {
		this.value = value;
	}
	match<R>(whenOk: (arg0 : T) => R, whenErr: (arg0 : X) => R): R {
		return whenOk(this.value);
	}
}
export class Err<T, X> implements Result<T, X> {
	error: X;
	constructor (error: X) {
		this.error = error;
	}
	match<R>(whenOk: (arg0 : T) => R, whenErr: (arg0 : X) => R): R {
		return whenErr(this.error);
	}
}
export class SingleHead<T> implements Head<T> {
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
export class EmptyHead<T> implements Head<T> {
	next(): Option<T> {
		return new None<T>();
	}
}
class ListCollector<T> implements Collector<T, List<T>> {
	createInitial(): List<T> {
		return Lists.empty();
	}
	fold(current: List<T>, element: T): List<T> {
		return current.addLast(element);
	}
}
export class FlatMapHead<T, R> implements Head<R> {
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
			if (tuple.left()){
				this.current = tuple.right();
			}
			else {
				return new None<R>();
			}
		}
	}
}
export class Some<T> implements Option<T> {
	value: T;
	constructor (value: T) {
		this.value = value;
	}
	map<R>(mapper: (arg0 : T) => R): Option<R> {
		return new Some<R>(mapper(this.value));
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
	toTuple(other: T): Tuple2<Boolean, T> {
		return new Tuple2Impl<Boolean, T>(true, this.value);
	}
	and<R>(other: () => Option<R>): Option<Tuple2<T, R>> {
		return other().map((otherValue: R) => new Tuple2Impl<T, R>(this.value, otherValue));
	}
}
export class None<T> implements Option<T> {
	map<R>(mapper: (arg0 : T) => R): Option<R> {
		return new None<R>();
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
	toTuple(other: T): Tuple2<Boolean, T> {
		return new Tuple2Impl<Boolean, T>(false, other);
	}
	and<R>(other: () => Option<R>): Option<Tuple2<T, R>> {
		return new None<Tuple2<T, R>>();
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
export class MapHead<T, R> implements Head<R> {
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
		let joinedArguments = this.args.queryWithIndices().map((tuple: Tuple2<number, string>) => "arg" + tuple.left() + " : " + tuple.right()).collect(new Joiner(", ")).orElse("");
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
class Iterators {
	static fromOption<T>(option: Option<T>): Query<T> {
		return new HeadedQuery<T>(option.map((element: T) => Iterators.getTSingleHead(element)).orElseGet(() => new EmptyHead<T>()));
	}
	static getTSingleHead<T>(element: T): Head<T> {
		return new SingleHead<T>(element);
	}
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
class Import {
	namespace: List<string>;
	child: string;
	constructor (namespace: List<string>, child: string) {
		this.namespace = namespace;
		this.child = child;
	}
	generate(): string {
		let joinedNamespace = this.namespace().query().collect(new Joiner("/")).orElse("");
		return "import { " + this.child() + " } from \"" + joinedNamespace + "\";\n";
	}
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
export class Main {
	static main(): void {
		let sourceDirectory = Files.get(".", "src", "java");
		sourceDirectory.walk().match((children: List<Path>) => Main.runWithChildren(children, sourceDirectory).next(), (value: IOError) => new Some<IOError>(value)).map((error: IOError) => error.display()).ifPresent((displayed: string) => Console.printErrLn(displayed));
	}
	static runWithChildren(children: List<Path>, sourceDirectory: Path): Query<IOError> {
		return children.query().filter((source: Path) => source.endsWith(".java")).map((source: Path) => Main.runWithSource(sourceDirectory, source)).flatMap(Iterators.fromOption);
	}
	static runWithSource(sourceDirectory: Path, source: Path): Option<IOError> {
		let relative = sourceDirectory.relativize(source);
		let namespace = relative.getParent().query().collect(new ListCollector<string>());
		let fileName = source.findFileName();
		let separator = fileName.lastIndexOf(".");
		let name = fileName.substring(0, separator);
		let target = Files.get(".", "src", "ts").resolveChildSegments(namespace).resolveChild(name + ".ts");
		return source.readString().match((input: string) => Main.compileAndWrite(input, target, namespace), (value: IOError) => new Some<IOError>(value));
	}
	static compileAndWrite(input: string, target: Path, namespace: List<string>): Option<IOError> {
		let output = Main.compileRoot(input, namespace);
		let parent = target.getParent();
		if (!parent.exists()){
			parent.createDirectories();
		}
		return target.writeString(output);
	}
	static compileRoot(input: string, namespace: List<string>): string {
		let compiled = Main.compileStatements(CompileState.createInitial(namespace), input, Main.compileRootSegment);
		let compiledState = compiled.left();
		let imports = compiledState.imports.query().map(Import.generate).collect(new Joiner("")).orElse("");
		return imports + compiledState.output + compiled.right();
	}
	static compileStatements(state: CompileState, input: string, mapper: (arg0 : CompileState, arg1 : string) => Tuple2<CompileState, string>): Tuple2<CompileState, string> {
		return Main.compileAll(state, input, Main.foldStatements, mapper, Main.mergeStatements);
	}
	static compileAll(state: CompileState, input: string, folder: (arg0 : DivideState, arg1 : string) => DivideState, mapper: (arg0 : CompileState, arg1 : string) => Tuple2<CompileState, string>, merger: (arg0 : string, arg1 : string) => string): Tuple2<CompileState, string> {
		let folded = Main.parseAll(state, input, folder, (state1: CompileState, s: string) => new Some<Tuple2<CompileState, string>>(mapper(state1, s))).orElse(new Tuple2Impl<CompileState, List<string>>(state, Lists.empty()));
		return new Tuple2Impl<CompileState, string>(folded.left(), Main.generateAll(folded.right(), merger));
	}
	static generateAll(elements: List<string>, merger: (arg0 : string, arg1 : string) => string): string {
		return elements.query().foldWithInitial("", merger);
	}
	static parseAll<T>(state: CompileState, input: string, folder: (arg0 : DivideState, arg1 : string) => DivideState, biFunction: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>): Option<Tuple2<CompileState, List<T>>> {
		return Main.divide(input, folder).query().foldWithInitial(new Some<Tuple2<CompileState, List<T>>>(new Tuple2Impl<CompileState, List<T>>(state, Lists.empty())), (maybeCurrent: Option<Tuple2<CompileState, List<T>>>, segment: string) => {
			return maybeCurrent.flatMap((current: Tuple2<CompileState, List<T>>) => {
				let currentState = current.left();
				let currentElement = current.right();
				return biFunction(currentState, segment).map((mappedTuple: Tuple2<CompileState, T>) => {
					let mappedState = mappedTuple.left();
					let mappedElement = mappedTuple.right();
					return new Tuple2Impl<CompileState, List<T>>(mappedState, currentElement.addLast(mappedElement));
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
			let poppedTuple0 = current.pop().toTuple(new Tuple2Impl<DivideState, string>(current, "\0"));
			if (!poppedTuple0.left()){
				break;
			}
			let poppedTuple = poppedTuple0.right();
			let poppedState = poppedTuple.left();
			let popped = poppedTuple.right();
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
			let maybeTuple = appended.popAndAppendToTuple().toTuple(new Tuple2Impl<DivideState, string>(appended, "\0"));
			if (!maybeTuple.left()){
				break;
			}
			let tuple = maybeTuple.right();
			appended = tuple.left();
			if ("\\" === tuple.right()){
				appended = appended.popAndAppendToOption().orElse(appended);
			}
			if ("\"" === tuple.right()){
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
	static foldEscaped(tuple: Tuple2<DivideState, string>): Option<DivideState> {
		let state = tuple.left();
		let c = tuple.right();
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
	static compileRootSegment(state: CompileState, input: string): Tuple2<CompileState, string> {
		return Main.compileOrPlaceholder(state, input, Lists.of(Main.compileWhitespace, Main.compileNamespaced, Main.createStructureRule("class ", "class "), Main.createStructureRule("interface ", "interface "), Main.createStructureRule("record ", "class "), Main.createStructureRule("enum ", "class ")));
	}
	static createStructureRule(sourceInfix: string, targetInfix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, string>> {
		return (state: CompileState, input1: string) => {
			return Main.compileFirst(input1, sourceInfix, (beforeInfix: string, afterInfix: string) => {
				return Main.compileFirst(afterInfix, "{", (beforeContent: string, withEnd: string) => {
					return Main.compileSuffix(Strings.strip(withEnd), "}", (inputContent: string) => {
						return Main.compileLast(beforeInfix, "\n", (s: string, s2: string) => {
							let annotations = Main.parseAnnotations(s);
							if (annotations.contains("Actual")){
								return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state, ""));
							}
							return Main.compileStructureWithImplementing(state, annotations, Main.parseModifiers(s2), targetInfix, beforeContent, inputContent);
						}).or(() => {
							let modifiers = Main.parseModifiers(beforeContent);
							return Main.compileStructureWithImplementing(state, Lists.empty(), modifiers, targetInfix, beforeContent, inputContent);
						});
					});
				});
			});
		};
	}
	static compileStructureWithImplementing(state: CompileState, annotations: List<string>, modifiers: List<string>, targetInfix: string, beforeContent: string, content: string): Option<Tuple2<CompileState, string>> {
		return Main.compileLast(beforeContent, " implements ", (s: string, s2: string) => {
			return Main.parseType(state, s2).flatMap((implementingTuple: Tuple2<CompileState, Type>) => {
				return Main.compileStructureWithExtends(implementingTuple.left(), annotations, modifiers, targetInfix, s, new Some<Type>(implementingTuple.right()), content);
			});
		}).or(() => {
			return Main.compileStructureWithExtends(state, annotations, modifiers, targetInfix, beforeContent, new None<Type>(), content);
		});
	}
	static compileStructureWithExtends(state: CompileState, annotations: List<string>, modifiers: List<string>, targetInfix: string, beforeContent: string, maybeImplementing: Option<Type>, inputContent: string): Option<Tuple2<CompileState, string>> {
		return Main.compileFirst(beforeContent, " extends ", (beforeExtends: string, afterExtends: string) => Main.compileStructureWithParameters(state, annotations, modifiers, targetInfix, beforeExtends, new Some<string>(afterExtends), maybeImplementing, inputContent)).or(() => {
			return Main.compileStructureWithParameters(state, annotations, modifiers, targetInfix, beforeContent, new None<string>(), maybeImplementing, inputContent);
		});
	}
	static compileStructureWithParameters(state: CompileState, annotations: List<string>, modifiers: List<string>, targetInfix: string, beforeContent: string, maybeSuperType: Option<string>, maybeImplementing: Option<Type>, inputContent: string): Option<Tuple2<CompileState, string>> {
		return Main.compileFirst(beforeContent, "(", (rawName: string, withParameters: string) => {
			return Main.compileFirst(withParameters, ")", (parametersString: string, _: string) => {
				let name = Strings.strip(rawName);
				let parametersTuple = Main.parseParameters(state, parametersString);
				let parameters = Main.retainDefinitionsFromParameters(parametersTuple.right());
				return Main.compileStructureWithTypeParams(parametersTuple.left(), targetInfix, inputContent, name, parameters, maybeImplementing, annotations, modifiers, maybeSuperType);
			});
		}).or(() => {
			return Main.compileStructureWithTypeParams(state, targetInfix, inputContent, beforeContent, Lists.empty(), maybeImplementing, annotations, modifiers, maybeSuperType);
		});
	}
	static retainDefinitionsFromParameters(parameters: List<Parameter>): List<Definition> {
		return parameters.query().map((parameter: Parameter) => parameter.asDefinition()).flatMap(Iterators.fromOption).collect(new ListCollector<Definition>());
	}
	static compileStructureWithTypeParams(state: CompileState, infix: string, content: string, beforeParams: string, parameters: List<Definition>, maybeImplementing: Option<Type>, annotations: List<string>, modifiers: List<string>, maybeSuperType: Option<string>): Option<Tuple2<CompileState, string>> {
		return Main.compileSuffix(Strings.strip(beforeParams), ">", (withoutTypeParamEnd: string) => {
			return Main.compileFirst(withoutTypeParamEnd, "<", (name: string, typeParamsString: string) => {
				let typeParams = Main.divideValues(typeParamsString);
				return Main.assembleStructure(state, annotations, modifiers, infix, name, typeParams, parameters, maybeImplementing, content, maybeSuperType);
			});
		}).or(() => {
			return Main.assembleStructure(state, annotations, modifiers, infix, beforeParams, Lists.empty(), parameters, maybeImplementing, content, maybeSuperType);
		});
	}
	static assembleStructure(state: CompileState, annotations: List<string>, oldModifiers: List<string>, infix: string, rawName: string, typeParams: List<string>, parameters: List<Definition>, maybeImplementing: Option<Type>, content: string, maybeSuperType: Option<string>): Option<Tuple2<CompileState, string>> {
		let name = Strings.strip(rawName);
		if (!Main.isSymbol(name)){
			return new None<Tuple2<CompileState, string>>();
		}
		let outputContentTuple = Main.compileStatements(state.withStructureName(name), content, Main.compileClassSegment);
		let outputContentState = outputContentTuple.left();
		let outputContent = outputContentTuple.right();
		let constructorString = Main.generateConstructorFromRecordParameters(parameters);
		let joinedTypeParams = Main.joinTypeParams(typeParams);
		let implementingString = Main.generateImplementing(maybeImplementing);
		let newModifiers = Main.modifyModifiers0(oldModifiers);
		let joinedModifiers = newModifiers.query().map((value: string) => value + " ").collect(Joiner.empty()).orElse("");
		if (annotations.contains("Namespace")){
			let actualInfix: string = "interface ";
			let newName: string = name + "Instance";
			let generated = joinedModifiers + actualInfix + newName + joinedTypeParams + implementingString + " {" + Main.joinParameters(parameters) + constructorString + outputContent + "\n}\n";
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(outputContentState.append(generated).append("export declare const " + name + ": " + newName + ";\n"), ""));
		}
		else {
			let extendsString = maybeSuperType.map((inner: string) => " extends " + inner).orElse("");
			let generated = joinedModifiers + infix + name + joinedTypeParams + extendsString + implementingString + " {" + Main.joinParameters(parameters) + constructorString + outputContent + "\n}\n";
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(outputContentState.append(generated), ""));
		}
	}
	static modifyModifiers0(oldModifiers: List<string>): List<string> {
		if (oldModifiers.contains("public")){
			return Lists.of("export");
		}
		return Lists.empty();
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
	static compileNamespaced(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		let stripped = Strings.strip(input);
		if (stripped.startsWith("package ")){
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state, ""));
		}
		return Main.compileImport(state, stripped);
	}
	static compileImport(state: CompileState, stripped: string): Option<Tuple2<CompileState, string>> {
		return Main.compilePrefix(stripped, "import ", (s: string) => {
			return Main.compileSuffix(s, ";", (s1: string) => {
				let divisions = Main.divide(s1, (divideState: DivideState, c: string) => Main.foldDelimited(divideState, c, "."));
				let child = Strings.strip(divisions.findLast().orElse(""));
				let parent = divisions.subList(0, divisions.size() - 1).orElse(Lists.empty());
				let parent1 = parent;
				let namespace = state.namespace;
				if (namespace.isEmpty()){
					parent1 = parent1.addFirst(".");
				}
				let i = 0;
				while (i < namespace.size()){
					parent1 = parent1.addFirst("..");
					i++;
				}
				if (parent.equalsTo(Lists.of("java", "util", "function"))){
					return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state, ""));
				}
				let stringList = parent1.addLast(child);
				let importString = new Import(stringList, child);
				return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state.addImport(importString), ""));
			});
		});
	}
	static compileOrPlaceholder(state: CompileState, input: string, rules: List<(arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, string>>>): Tuple2<CompileState, string> {
		return Main.or(state, input, rules).orElseGet(() => new Tuple2Impl<CompileState, string>(state, Main.generatePlaceholder(input)));
	}
	static or<T>(state: CompileState, input: string, rules: List<(arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>>): Option<Tuple2<CompileState, T>> {
		return rules.query().map((rule: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>) => Main.getApply(state, input, rule)).flatMap(Iterators.fromOption).next();
	}
	static getApply<T>(state: CompileState, input: string, rule: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>): Option<Tuple2<CompileState, T>> {
		return rule(state, input);
	}
	static compileClassSegment(state1: CompileState, input1: string): Tuple2<CompileState, string> {
		return Main.compileOrPlaceholder(state1, input1, Lists.of(Main.compileWhitespace, Main.createStructureRule("class ", "class "), Main.createStructureRule("interface ", "interface "), Main.createStructureRule("record ", "class "), Main.createStructureRule("enum ", "class "), Main.compileMethod, Main.compileFieldDefinition));
	}
	static compileMethod(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main.compileFirst(input, "(", (beforeParams: string, withParams: string) => {
			let strippedBeforeParams = Strings.strip(beforeParams);
			return Main.compileLast(strippedBeforeParams, " ", (_: string, name: string) => {
				if (state.maybeStructureName.filter((anObject: string) => Strings.equalsTo(name, anObject)).isPresent()){
					return Main.compileMethodWithBeforeParams(state, new ConstructorHeader(), withParams);
				}
				return new None<Tuple2<CompileState, string>>();
			}).or(() => {
				if (state.maybeStructureName.filter((anObject: string) => Strings.equalsTo(strippedBeforeParams, anObject)).isPresent()){
					return Main.compileMethodWithBeforeParams(state, new ConstructorHeader(), withParams);
				}
				return new None<Tuple2<CompileState, string>>();
			}).or(() => {
				return Main.parseDefinition(state, beforeParams).flatMap((tuple: Tuple2<CompileState, Definition>) => Main.compileMethodWithBeforeParams(tuple.left(), tuple.right(), withParams));
			});
		});
	}
	static compileMethodWithBeforeParams(state: CompileState, header: MethodHeader, withParams: string): Option<Tuple2<CompileState, string>> {
		return Main.compileFirst(withParams, ")", (params: string, afterParams: string) => {
			let parametersTuple = Main.parseParameters(state, params);
			let parametersState = parametersTuple.left();
			let parameters = parametersTuple.right();
			let definitions = Main.retainDefinitionsFromParameters(parameters);
			let joinedDefinitions = definitions.query().map((definition: Definition) => definition.generate()).collect(new Joiner(", ")).orElse("");
			if (header.hasAnnotation("Actual")){
				let headerGenerated = header.removeModifier("static").generateWithAfterName("(" + joinedDefinitions + ")");
				return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(parametersState, "\n\t" + headerGenerated + ";\n"));
			}
			let headerGenerated = header.generateWithAfterName("(" + joinedDefinitions + ")");
			return Main.compilePrefix(Strings.strip(afterParams), "{", (withoutContentStart: string) => {
				return Main.compileSuffix(Strings.strip(withoutContentStart), "}", (withoutContentEnd: string) => {
					let statementsTuple = Main.compileFunctionStatements(parametersState.enterDepth().enterDepth().defineAll(definitions), withoutContentEnd);
					return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(statementsTuple.left().exitDepth().exitDepth(), "\n\t" + headerGenerated + " {" + statementsTuple.right() + "\n\t}"));
				});
			}).or(() => {
				if (Strings.equalsTo(";", Strings.strip(afterParams))){
					return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(parametersState, "\n\t" + headerGenerated + ";"));
				}
				return new None<Tuple2<CompileState, string>>();
			});
		});
	}
	static parseParameters(state: CompileState, params: string): Tuple2<CompileState, List<Parameter>> {
		return Main.parseValuesOrEmpty(state, params, (state1: CompileState, s: string) => new Some<Tuple2<CompileState, Parameter>>(Main.parseParameterOrPlaceholder(state1, s)));
	}
	static compileFunctionStatements(state: CompileState, input: string): Tuple2<CompileState, string> {
		return Main.compileStatements(state, input, Main.compileFunctionSegment);
	}
	static compileFunctionSegment(state: CompileState, input: string): Tuple2<CompileState, string> {
		return Main.compileOrPlaceholder(state, input, Lists.of(Main.compileWhitespace, Main.compileEmptySegment, Main.compileBlock, Main.compileFunctionStatement, Main.compileReturnWithoutSuffix));
	}
	static compileEmptySegment(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		if (Strings.equalsTo(";", Strings.strip(input))){
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state, ";"));
		}
		else {
			return new None<Tuple2<CompileState, string>>();
		}
	}
	static compileReturnWithoutSuffix(state1: CompileState, input1: string): Option<Tuple2<CompileState, string>> {
		return Main.compileReturn(input1, (withoutPrefix: string) => Main.compileValue(state1, withoutPrefix)).map((tuple: Tuple2<CompileState, string>) => new Tuple2Impl<CompileState, string>(tuple.left(), Main.generateIndent(state1.depth) + tuple.right()));
	}
	static compileBlock(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main.compileSuffix(Strings.strip(input), "}", (withoutEnd: string) => {
			return Main.compileSplit(Main.splitFoldedLast(withoutEnd, "", Main.foldBlockStarts), (beforeContentWithEnd: string, content: string) => {
				return Main.compileSuffix(beforeContentWithEnd, "{", (beforeContent: string) => {
					return Main.compileBlockHeader(state, beforeContent).flatMap((headerTuple: Tuple2<CompileState, string>) => {
						let contentTuple = Main.compileFunctionStatements(headerTuple.left().enterDepth(), content);
						let indent = Main.generateIndent(state.depth);
						return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(contentTuple.left().exitDepth(), indent + headerTuple.right() + "{" + contentTuple.right() + indent + "}"));
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
	static compileBlockHeader(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main.or(state, input, Lists.of(Main.createConditionalRule("if"), Main.createConditionalRule("while"), Main.compileElse));
	}
	static createConditionalRule(prefix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, string>> {
		return (state1: CompileState, input1: string) => Main.compilePrefix(Strings.strip(input1), prefix, (withoutPrefix: string) => {
			let strippedCondition = Strings.strip(withoutPrefix);
			return Main.compilePrefix(strippedCondition, "(", (withoutConditionStart: string) => {
				return Main.compileSuffix(withoutConditionStart, ")", (withoutConditionEnd: string) => {
					let tuple = Main.compileValueOrPlaceholder(state1, withoutConditionEnd);
					return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(tuple.left(), prefix + " (" + tuple.right() + ")"));
				});
			});
		});
	}
	static compileElse(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		if (Strings.equalsTo("else", Strings.strip(input))){
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state, "else "));
		}
		else {
			return new None<Tuple2<CompileState, string>>();
		}
	}
	static compileFunctionStatement(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main.compileSuffix(Strings.strip(input), ";", (withoutEnd: string) => {
			let valueTuple = Main.compileFunctionStatementValue(state, withoutEnd);
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(valueTuple.left(), Main.generateIndent(state.depth) + valueTuple.right() + ";"));
		});
	}
	static generateIndent(indent: number): string {
		return "\n" + "\t".repeat(indent);
	}
	static compileFunctionStatementValue(state: CompileState, withoutEnd: string): Tuple2<CompileState, string> {
		return Main.compileOrPlaceholder(state, withoutEnd, Lists.of(Main.compileReturnWithValue, Main.compileAssignment, (state1: CompileState, input: string) => Main.parseInvokable(state1, input).map((tuple: Tuple2<CompileState, Value>) => new Tuple2Impl<CompileState, string>(tuple.left(), tuple.right().generate())), Main.createPostRule("++"), Main.createPostRule("--"), Main.compileBreak));
	}
	static compileBreak(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		if (Strings.equalsTo("break", Strings.strip(input))){
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state, "break"));
		}
		else {
			return new None<Tuple2<CompileState, string>>();
		}
	}
	static createPostRule(suffix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, string>> {
		return (state1: CompileState, input: string) => Main.compileSuffix(Strings.strip(input), suffix, (child: string) => {
			let tuple = Main.compileValueOrPlaceholder(state1, child);
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(tuple.left(), tuple.right() + suffix));
		});
	}
	static compileReturnWithValue(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main.compileReturn(input, (value1: string) => Main.compileValue(state, value1));
	}
	static compileReturn(input: string, mapper: (arg0 : string) => Option<Tuple2<CompileState, string>>): Option<Tuple2<CompileState, string>> {
		return Main.compilePrefix(Strings.strip(input), "return ", (value: string) => {
			return mapper(value).flatMap((tuple: Tuple2<CompileState, string>) => {
				return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(tuple.left(), "return " + tuple.right()));
			});
		});
	}
	static parseInvokable(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		return Main.compileSuffix(Strings.strip(input), ")", (withoutEnd: string) => {
			return Main.compileSplit(Main.splitFoldedLast(withoutEnd, "", Main.foldInvocationStarts), (callerWithArgStart: string, args: string) => {
				return Main.compileSuffix(callerWithArgStart, "(", (callerString: string) => {
					return Main.compilePrefix(Strings.strip(callerString), "new ", (type: string) => {
						return Main.compileType(state, type).flatMap((callerTuple: Tuple2<CompileState, string>) => {
							let callerState = callerTuple.right();
							let caller = callerTuple.left();
							return Main.assembleInvokable(caller, new ConstructionCaller(callerState), args);
						});
					}).or(() => {
						return Main.parseValue(state, callerString).flatMap((callerTuple: Tuple2<CompileState, Value>) => {
							return Main.assembleInvokable(callerTuple.left(), callerTuple.right(), args);
						});
					});
				});
			});
		});
	}
	static splitFoldedLast(input: string, delimiter: string, folder: (arg0 : DivideState, arg1 : string) => DivideState): Option<Tuple2<string, string>> {
		return Main.splitFolded(input, folder, (divisions1: List<string>) => Main.selectLast(divisions1, delimiter));
	}
	static splitFolded(input: string, folder: (arg0 : DivideState, arg1 : string) => DivideState, selector: (arg0 : List<string>) => Option<Tuple2<string, string>>): Option<Tuple2<string, string>> {
		let divisions = Main.divide(input, folder);
		if (2 > divisions.size()){
			return new None<Tuple2<string, string>>();
		}
		return selector(divisions);
	}
	static selectLast(divisions: List<string>, delimiter: string): Option<Tuple2<string, string>> {
		let beforeLast = divisions.subList(0, divisions.size() - 1).orElse(divisions);
		let last = divisions.findLast().orElse("");
		let joined = beforeLast.query().collect(new Joiner(delimiter)).orElse("");
		return new Some<Tuple2<string, string>>(new Tuple2Impl<string, string>(joined, last));
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
	static assembleInvokable(state: CompileState, oldCaller: Caller, argsString: string): Option<Tuple2<CompileState, Value>> {
		return Main.parseValues(state, argsString, (state1: CompileState, s: string) => Main.parseArgument(state1, s)).flatMap((argsTuple: Tuple2<CompileState, List<Argument>>) => {
			let argsState = argsTuple.left();
			let args = Main.retain(argsTuple.right(), (argument: Argument) => argument.toValue());
			let newCaller = Main.transformCaller(argsState, oldCaller);
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(argsState, new Invokable(newCaller, args)));
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
	static parseArgumentOrPlaceholder(state1: CompileState, input: string): Tuple2<CompileState, Argument> {
		return Main.parseArgument(state1, input).orElseGet(() => new Tuple2Impl<CompileState, Argument>(state1, new Placeholder(input)));
	}
	static parseArgument(state1: CompileState, input: string): Option<Tuple2<CompileState, Argument>> {
		return Main.parseValue(state1, input).map((tuple: Tuple2<CompileState, Value>) => new Tuple2Impl<CompileState, Argument>(tuple.left(), tuple.right()));
	}
	static compileAssignment(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main.compileFirst(input, "=", (destination: string, source: string) => {
			let sourceTuple = Main.compileValueOrPlaceholder(state, source);
			let destinationTuple = Main.compileValue(sourceTuple.left(), destination).or(() => Main.parseDefinition(sourceTuple.left(), destination).map((tuple: Tuple2<CompileState, Definition>) => new Tuple2Impl<CompileState, string>(tuple.left(), "let " + tuple.right().generate()))).orElseGet(() => new Tuple2Impl<CompileState, string>(sourceTuple.left(), Main.generatePlaceholder(destination)));
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(destinationTuple.left(), destinationTuple.right() + " = " + sourceTuple.right()));
		});
	}
	static compileValueOrPlaceholder(state: CompileState, input: string): Tuple2<CompileState, string> {
		return Main.compileValue(state, input).orElseGet(() => new Tuple2Impl<CompileState, string>(state, Main.generatePlaceholder(input)));
	}
	static compileValue(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main.parseValue(state, input).map((tuple: Tuple2<CompileState, Value>) => new Tuple2Impl<CompileState, string>(tuple.left(), tuple.right().generate()));
	}
	static parseValue(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		return Main.or(state, input, Lists.of(Main.parseLambda, Main.createOperatorRule("+"), Main.createOperatorRule("-"), Main.createOperatorRule("<="), Main.createOperatorRule("<"), Main.createOperatorRule("&&"), Main.createOperatorRule("||"), Main.createOperatorRule(">"), Main.createOperatorRule(">="), Main.parseInvokable, Main.createAccessRule("."), Main.createAccessRule("::"), Main.parseSymbol, Main.parseNot, Main.parseNumber, Main.createOperatorRuleWithDifferentInfix("==", "==="), Main.createOperatorRuleWithDifferentInfix("!=", "!=="), Main.createTextRule("\""), Main.createTextRule("'")));
	}
	static createTextRule(slice: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, Value>> {
		return (state1: CompileState, input1: string) => {
			let stripped = Strings.strip(input1);
			return Main.compilePrefix(stripped, slice, (s: string) => {
				return Main.compileSuffix(s, slice, (s1: string) => new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state1, new StringValue(s1))));
			});
		};
	}
	static parseNot(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		return Main.compilePrefix(Strings.strip(input), "!", (withoutPrefix: string) => {
			let childTuple = Main.compileValueOrPlaceholder(state, withoutPrefix);
			let childState = childTuple.left();
			let child = "!" + childTuple.right();
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(childState, new Not(child)));
		});
	}
	static parseLambda(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		return Main.compileFirst(input, "->", (beforeArrow: string, afterArrow: string) => {
			let strippedBeforeArrow = Strings.strip(beforeArrow);
			return Main.compilePrefix(strippedBeforeArrow, "(", (withoutStart: string) => {
				return Main.compileSuffix(withoutStart, ")", (withoutEnd: string) => {
					return Main.parseValues(state, withoutEnd, (state1: CompileState, s: string) => Main.parseParameter(state1, s)).flatMap((paramNames: Tuple2<CompileState, List<Parameter>>) => {
						return Main.compileLambdaWithParameterNames(paramNames.left(), Main.retainDefinitionsFromParameters(paramNames.right()), afterArrow);
					});
				});
			});
		});
	}
	static compileLambdaWithParameterNames(state: CompileState, paramNames: List<Definition>, afterArrow: string): Option<Tuple2<CompileState, Value>> {
		let strippedAfterArrow = Strings.strip(afterArrow);
		return Main.compilePrefix(strippedAfterArrow, "{", (withoutContentStart: string) => {
			return Main.compileSuffix(withoutContentStart, "}", (withoutContentEnd: string) => {
				let statementsTuple = Main.compileFunctionStatements(state.enterDepth().defineAll(paramNames), withoutContentEnd);
				let statementsState = statementsTuple.left();
				let statements = statementsTuple.right();
				let exited = statementsState.exitDepth();
				return Main.assembleLambda(exited, paramNames, "{" + statements + Main.generateIndent(exited.depth) + "}");
			});
		}).or(() => {
			return Main.compileValue(state, strippedAfterArrow).flatMap((tuple: Tuple2<CompileState, string>) => {
				return Main.assembleLambda(tuple.left(), paramNames, tuple.right());
			});
		});
	}
	static assembleLambda(exited: CompileState, paramNames: List<Definition>, content: string): Option<Tuple2<CompileState, Value>> {
		return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(exited, new Lambda(paramNames, content)));
	}
	static createOperatorRule(infix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, Value>> {
		return Main.createOperatorRuleWithDifferentInfix(infix, infix);
	}
	static createAccessRule(infix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, Value>> {
		return (state: CompileState, input: string) => Main.compileLast(input, infix, (childString: string, rawProperty: string) => {
			let property = Strings.strip(rawProperty);
			if (!Main.isSymbol(property)){
				return new None<Tuple2<CompileState, Value>>();
			}
			return Main.parseValue(state, childString).flatMap((childTuple: Tuple2<CompileState, Value>) => {
				let childState = childTuple.left();
				let child = childTuple.right();
				return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(childState, new Access(child, property)));
			});
		});
	}
	static createOperatorRuleWithDifferentInfix(sourceInfix: string, targetInfix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, Value>> {
		return (state1: CompileState, input1: string) => {
			return Main.compileSplit(Main.splitFolded(input1, Main.foldOperator(sourceInfix), (divisions: List<string>) => Main.selectFirst(divisions, sourceInfix)), (leftString: string, rightString: string) => {
				return Main.parseValue(state1, leftString).flatMap((leftTuple: Tuple2<CompileState, Value>) => {
					return Main.parseValue(leftTuple.left(), rightString).flatMap((rightTuple: Tuple2<CompileState, Value>) => {
						let left = leftTuple.right();
						let right = rightTuple.right();
						return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(rightTuple.left(), new Operation(left, targetInfix, right)));
					});
				});
			});
		};
	}
	static selectFirst(divisions: List<string>, delimiter: string): Option<Tuple2<string, string>> {
		let first = divisions.findFirst().orElse("");
		let afterFirst = divisions.subList(1, divisions.size()).orElse(divisions).query().collect(new Joiner(delimiter)).orElse("");
		return new Some<Tuple2<string, string>>(new Tuple2Impl<string, string>(first, afterFirst));
	}
	static foldOperator(infix: string): (arg0 : DivideState, arg1 : string) => DivideState {
		return (state: DivideState, c: string) => {
			if (c === infix.charAt(0) && state.startsWith(Strings.sliceFrom(infix, 1))){
				let length = Strings.length(infix) - 1;
				let counter = 0;
				let current = state;
				while (counter < length){
					counter++;
					current = current.pop().map((tuple: Tuple2<DivideState, string>) => tuple.left()).orElse(current);
				}
				return current.advance();
			}
			return state.append(c);
		};
	}
	static parseNumber(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		let stripped = Strings.strip(input);
		if (Main.isNumber(stripped)){
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state, new SymbolNode(stripped)));
		}
		else {
			return new None<Tuple2<CompileState, Value>>();
		}
	}
	static isNumber(input: string): boolean {
		let query = new HeadedQuery<number>(new RangeHead(Strings.length(input)));
		return query.map(input.charAt).allMatch((c: string) => Characters.isDigit(c));
	}
	static parseSymbol(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		let stripped = Strings.strip(input);
		if (Main.isSymbol(stripped)){
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state, new SymbolNode(stripped)));
		}
		else {
			return new None<Tuple2<CompileState, Value>>();
		}
	}
	static isSymbol(input: string): boolean {
		let query = new HeadedQuery<number>(new RangeHead(Strings.length(input)));
		return query.allMatch((index: number) => Main.isSymbolChar(index, input.charAt(index)));
	}
	static isSymbolChar(index: number, c: string): boolean {
		return "_" === c || Characters.isLetter(c) || (0 !== index && Characters.isDigit(c));
	}
	static compilePrefix<T>(input: string, infix: string, mapper: (arg0 : string) => Option<Tuple2<CompileState, T>>): Option<Tuple2<CompileState, T>> {
		if (!input.startsWith(infix)){
			return new None<Tuple2<CompileState, T>>();
		}
		let slice = Strings.sliceFrom(input, Strings.length(infix));
		return mapper(slice);
	}
	static compileWhitespace(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main.parseWhitespace(state, input).map((tuple: Tuple2<CompileState, Whitespace>) => new Tuple2Impl<CompileState, string>(tuple.left(), tuple.right().generate()));
	}
	static parseWhitespace(state: CompileState, input: string): Option<Tuple2<CompileState, Whitespace>> {
		if (Strings.isBlank(input)){
			return new Some<Tuple2<CompileState, Whitespace>>(new Tuple2Impl<CompileState, Whitespace>(state, new Whitespace()));
		}
		return new None<Tuple2<CompileState, Whitespace>>();
	}
	static compileFieldDefinition(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main.compileSuffix(Strings.strip(input), ";", (withoutEnd: string) => {
			return Main.getTupleOption(state, withoutEnd).or(() => Main.compileEnumValues(state, withoutEnd));
		});
	}
	static getTupleOption(state: CompileState, withoutEnd: string): Option<Tuple2<CompileState, string>> {
		return Main.parseParameter(state, withoutEnd).flatMap((definitionTuple: Tuple2<CompileState, Parameter>) => {
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(definitionTuple.left(), "\n\t" + definitionTuple.right().generate() + ";"));
		});
	}
	static compileEnumValues(state: CompileState, withoutEnd: string): Option<Tuple2<CompileState, string>> {
		return Main.parseValues(state, withoutEnd, (state1: CompileState, s: string) => {
			return Main.parseInvokable(state1, s).flatMap((tuple: Tuple2<CompileState, Value>) => {
				let structureName = state.maybeStructureName.orElse("");
				return tuple.right().generateAsEnumValue(structureName).map((stringOption: string) => {
					return new Tuple2Impl<CompileState, string>(tuple.left(), stringOption);
				});
			});
		}).map((tuple: Tuple2<CompileState, List<string>>) => {
			return new Tuple2Impl<CompileState, string>(tuple.left(), tuple.right().query().collect(new Joiner("")).orElse(""));
		});
	}
	static parseParameterOrPlaceholder(state: CompileState, input: string): Tuple2<CompileState, Parameter> {
		return Main.parseParameter(state, input).orElseGet(() => new Tuple2Impl<CompileState, Parameter>(state, new Placeholder(input)));
	}
	static parseParameter(state: CompileState, input: string): Option<Tuple2<CompileState, Parameter>> {
		return Main.parseWhitespace(state, input).map((tuple: Tuple2<CompileState, Whitespace>) => Main.getCompileStateParameterTuple2(tuple)).or(() => Main.parseDefinition(state, input).map((tuple: Tuple2<CompileState, Definition>) => new Tuple2Impl<CompileState, Parameter>(tuple.left(), tuple.right())));
	}
	static getCompileStateParameterTuple2(tuple: Tuple2<CompileState, Whitespace>): Tuple2<CompileState, Parameter> {
		return new Tuple2Impl<CompileState, Parameter>(tuple.left(), tuple.right());
	}
	static parseDefinition(state: CompileState, input: string): Option<Tuple2<CompileState, Definition>> {
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
		return Main.divide(s, (state1: DivideState, c: string) => Main.foldDelimited(state1, c, "\n")).query().map((s2: string) => Strings.strip(s2)).filter((value: string) => !Strings.isEmpty(value)).filter((value: string) => 1 <= Strings.length(value)).map((value: string) => Strings.sliceFrom(value, 1)).map((s1: string) => Strings.strip(s1)).filter((value: string) => !Strings.isEmpty(value)).collect(new ListCollector<string>());
	}
	static parseDefinitionWithAnnotations(state: CompileState, annotations: List<string>, beforeType: string, type: string, name: string): Option<Tuple2<CompileState, Definition>> {
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
		return Main.divide(Strings.strip(beforeType), (state1: DivideState, c: string) => Main.foldDelimited(state1, c, " ")).query().map((s: string) => Strings.strip(s)).filter((value: string) => !Strings.isEmpty(value)).collect(new ListCollector<string>());
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
	static parseDefinitionWithTypeParameters(state: CompileState, annotations: List<string>, typeParams: List<string>, oldModifiers: List<string>, type: string, name: string): Option<Tuple2<CompileState, Definition>> {
		return Main.parseType(state, type).flatMap((typeTuple: Tuple2<CompileState, Type>) => {
			let newModifiers = Main.modifyModifiers(oldModifiers);
			let generated = new Definition(annotations, newModifiers, typeParams, typeTuple.right(), name);
			return new Some<Tuple2<CompileState, Definition>>(new Tuple2Impl<CompileState, Definition>(typeTuple.left(), generated));
		});
	}
	static modifyModifiers(oldModifiers: List<string>): List<string> {
		if (oldModifiers.contains("static")){
			return Lists.of("static");
		}
		return Lists.empty();
	}
	static parseTypeOrPlaceholder(state: CompileState, type: string): Tuple2<CompileState, Type> {
		return Main.parseType(state, type).map((tuple: Tuple2<CompileState, Type>) => new Tuple2Impl<CompileState, Type>(tuple.left(), tuple.right())).orElseGet(() => new Tuple2Impl<CompileState, Type>(state, new Placeholder(type)));
	}
	static compileTypeOrPlaceholder(state: CompileState, type: string): Tuple2<CompileState, string> {
		return Main.compileType(state, type).orElseGet(() => new Tuple2Impl<CompileState, string>(state, Main.generatePlaceholder(type)));
	}
	static compileType(state: CompileState, type: string): Option<Tuple2<CompileState, string>> {
		return Main.parseType(state, type).map((tuple: Tuple2<CompileState, Type>) => new Tuple2Impl<CompileState, string>(tuple.left(), tuple.right().generate()));
	}
	static parseType(state: CompileState, type: string): Option<Tuple2<CompileState, Type>> {
		return Main.or(state, type, Lists.of(Main.parseVarArgs, Main.parseGeneric, Main.parsePrimitive, Main.parseSymbolType));
	}
	static parseVarArgs(state: CompileState, input: string): Option<Tuple2<CompileState, Type>> {
		let stripped = Strings.strip(input);
		return Main.compileSuffix(stripped, "...", (s: string) => {
			let child = Main.parseTypeOrPlaceholder(state, s);
			return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(child.left(), new VarArgs(child.right())));
		});
	}
	static parseSymbolType(state: CompileState, input: string): Option<Tuple2<CompileState, Type>> {
		let stripped = Strings.strip(input);
		if (Main.isSymbol(stripped)){
			return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(state, new SymbolNode(stripped)));
		}
		return new None<Tuple2<CompileState, Type>>();
	}
	static parsePrimitive(state: CompileState, input: string): Option<Tuple2<CompileState, Type>> {
		return Main.findPrimitiveValue(Strings.strip(input)).map((result: Type) => new Tuple2Impl<CompileState, Type>(state, result));
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
	static parseGeneric(state: CompileState, input: string): Option<Tuple2<CompileState, Type>> {
		return Main.compileSuffix(Strings.strip(input), ">", (withoutEnd: string) => {
			return Main.compileFirst(withoutEnd, "<", (baseString: string, argsString: string) => {
				let argsTuple = Main.parseValuesOrEmpty(state, argsString, (state1: CompileState, s: string) => Main.compileTypeArgument(state1, s));
				let argsState = argsTuple.left();
				let args = argsTuple.right();
				let base = Strings.strip(baseString);
				return Main.assembleFunctionType(argsState, base, args).or(() => {
					let importString = new Import(Lists.of(".", base), base);
					return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(argsState.addImport(importString), new Generic(base, args)));
				});
			});
		});
	}
	static assembleFunctionType(state: CompileState, base: string, args: List<string>): Option<Tuple2<CompileState, Type>> {
		return Main.mapFunctionType(base, args).map((generated: Type) => new Tuple2Impl<CompileState, Type>(state, generated));
	}
	static mapFunctionType(base: string, args: List<string>): Option<Type> {
		if (Strings.equalsTo("Function", base)){
			return args.findFirst().and(() => args.find(1)).map((tuple: Tuple2<string, string>) => new FunctionType(Lists.of(tuple.left()), tuple.right()));
		}
		if (Strings.equalsTo("BiFunction", base)){
			return args.find(0).and(() => args.find(1)).and(() => args.find(2)).map((tuple: Tuple2<Tuple2<string, string>, string>) => new FunctionType(Lists.of(tuple.left().left(), tuple.left().right()), tuple.right()));
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
	static compileTypeArgument(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main.or(state, input, Lists.of((state2: CompileState, input1: string) => Main.compileWhitespace(state2, input1), (state1: CompileState, type: string) => Main.compileType(state1, type)));
	}
	static generateValueStrings(values: List<string>): string {
		return Main.generateAll(values, Main.mergeValues);
	}
	static parseValuesOrEmpty<T>(state: CompileState, input: string, mapper: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>): Tuple2<CompileState, List<T>> {
		return Main.parseValues(state, input, mapper).orElse(new Tuple2Impl<CompileState, List<T>>(state, Lists.empty()));
	}
	static parseValues<T>(state: CompileState, input: string, mapper: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>): Option<Tuple2<CompileState, List<T>>> {
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
	static compileSplit<T>(splitter: Option<Tuple2<string, string>>, mapper: (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return splitter.flatMap((tuple: Tuple2<string, string>) => mapper(tuple.left(), tuple.right()));
	}
	static split(input: string, infix: string, locator: (arg0 : string, arg1 : string) => number): Option<Tuple2<string, string>> {
		let index = locator(input, infix);
		if (0 > index){
			return new None<Tuple2<string, string>>();
		}
		let left = Strings.sliceBetween(input, 0, index);
		let length = Strings.length(infix);
		let right = Strings.sliceFrom(input, index + length);
		return new Some<Tuple2<string, string>>(new Tuple2Impl<string, string>(left, right));
	}
	static findFirst(input: string, infix: string): number {
		return input.indexOf(infix);
	}
	static generatePlaceholder(input: string): string {
		let replaced = input.replace("/*", "start").replace("*/", "end");
		return "/*" + replaced + "*/";
	}
}
