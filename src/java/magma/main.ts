interface MethodHeader {
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
}
interface Iterator<T> {
	collect<C>(collector : Collector<T, C>): C;
	map<R>(mapper : (arg0 : T) => R): Iterator<R>;
	fold<R>(initial : R, folder : (arg0 : R, arg1 : T) => R): R;
	flatMap<R>(mapper : (arg0 : T) => Iterator<R>): Iterator<R>;
	next(): Option<T>;
	allMatch(predicate : (arg0 : T) => boolean): boolean;
	filter(predicate : (arg0 : T) => boolean): Iterator<T>;
}
interface List<T> {
	add(element : T): List<T>;
	iterate(): Iterator<T>;
	size(): number;
	subList(startInclusive : number, endExclusive : number): Option<List<T>>;
	findLast(): Option<T>;
	findFirst(): Option<T>;
	find(index : number): Option<T>;
	iterateWithIndices(): Iterator<Tuple<number, T>>;
}
interface Head<T> {
	next(): Option<T>;
}
class HeadedIterator<T> {
	next(): Option<T> {
		return this.head.next();
	}
	collect<C>(collector : Collector<T, C>): C {
		return this.fold(collector.createInitial(), collector.fold);
	}
	map<R>(mapper : (arg0 : T) => R): Iterator<R> {
		return new HeadedIterator<>(() => this.next().map(mapper));
	}
	fold<R>(initial : R, folder : (arg0 : R, arg1 : T) => R): R {
		let result : R = initial;
		while (true){
			Option < /*T> maybeNext */ = this.head.next();
			if (maybeNext.isEmpty()){
				return result;
			}
			result = folder.apply(result, maybeNext.orElse(null));
		}
	}
	flatMap<R>(mapper : (arg0 : T) => Iterator<R>): Iterator<R> {
		return new HeadedIterator<>(new FlatMapHead<T, R>(this.head, mapper));
	}
	allMatch(predicate : (arg0 : T) => boolean): boolean {
		return this.fold(true, (maybeAllTrue, element) => maybeAllTrue && predicate.test(element));
	}
	filter(predicate : (arg0 : T) => boolean): Iterator<T> {
		return this.flatMap((element) => {
			if (predicate.test(element)){
				return new HeadedIterator<>(new SingleHead<>(element));
			}
			else {
				return new HeadedIterator<>(new EmptyHead<>());
			}
		});
	}
}
class RangeHead implements Head<Integer> {
	length : number;
	0 : /*=*/;
	RangeHead(length : number): public {
		this.length = length;
	}
	next(): Option<number> {
		if (this.counter < this.length){
			let value : unknown = this.counter;
			this.counter++;
			return new Some<number>(value);
		}
		return new None<number>();
	}
}
class JVMList<T> {
	JVMList(): public {
		this(new ArrayList<>());
	}
	add(element : T): List<T> {
		this.list.add(element);
		return this;
	}
	iterate(): Iterator<T> {
		return this.iterateWithIndices().map(Tuple.right);
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
	iterateWithIndices(): Iterator<Tuple<number, T>> {
		return new /*HeadedIterator<>(new RangeHead(this.list.size()))
                        .map*/((index) => new Tuple<>(index, this.list.get(index)));
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
class Lists {
	empty<T>(): List<T> {
		return new JVMList<>();
	}
	of<T>(elements : /*T...*/): List<T> {
		return new JVMList<>(new ArrayList<>(Arrays.asList(elements)));
	}
}
class DivideState {
	input : string;
	segments : List<string>;
	index : number;
	buffer : StringBuilder;
	depth : number;
	constructor (segments : List<string>, buffer : StringBuilder, depth : number, input : string, index : number) {
		this.segments = segments;
		this.buffer = buffer;
		this.depth = depth;
		this.input = input;
		this.index = index;
	}
	constructor (input : string) {
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
		let c : unknown = this.input.charAt(this.index);
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
}
class CompileState {
	constructor () {
		this("", new None<string>(), 0);
	}
	append(element : string): CompileState {
		return new CompileState(this.output + element, this.structureName, this.depth);
	}
	withStructureName(name : string): CompileState {
		return new CompileState(this.output, new Some<string>(name), this.depth);
	}
	depth(): number {
		return this.depth;
	}
	enterDepth(): CompileState {
		return new CompileState(this.output, this.structureName, this.depth + 1);
	}
	exitDepth(): CompileState {
		return new CompileState(this.output, this.structureName, this.depth - 1);
	}
}
class Joiner {
	createInitial(): Option<string> {
		return new None<string>();
	}
	fold(maybe : Option<string>, element : string): Option<string> {
		return new Some<string>(maybe.map((inner) => inner + this.delimiter + element).orElse(element));
	}
}
class Definition {
	generate(): string {
		return this.generateWithAfterName(" ");
	}
	generateWithAfterName(afterName : string): string {
		let joinedTypeParams : unknown = this.joinTypeParams();
		return this.name + joinedTypeParams + afterName + ": " + this.type();
	}
	joinTypeParams(): string {
		return this.typeParams.iterate().collect(new Joiner(", ")).map((joined) => "<" + joined + ">").orElse("");
	}
}
class ConstructorHeader implements MethodHeader {
	generateWithAfterName(afterName : string): string {
		return "constructor " + afterName;
	}
}
class Ok<T, X> {
	match<R>(whenOk : (arg0 : T) => R, whenErr : (arg0 : X) => R): R {
		return whenOk.apply(this.value);
	}
}
class Err<T, X> {
	match<R>(whenOk : (arg0 : T) => R, whenErr : (arg0 : X) => R): R {
		return whenErr.apply(this.error);
	}
}
class Iterators {
	fromOption<T>(option : Option<T>): Iterator<T> {
		return new HeadedIterator<>(option. < Head < /*T>>map*/(SingleHead.new).orElseGet(EmptyHead.new));
	}
}
class SingleHead<T> implements Head<T> {
	element : T;
	false : /*=*/;
	SingleHead(element : T): public {
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
	mapper : (arg0 : T) => Iterator<R>;
	head : Head<T>;
	maybeCurrent : Option<Iterator<R>>;
	FlatMapHead(head : Head<T>, mapper : (arg0 : T) => Iterator<R>): public {
		this.mapper = mapper;
		this.maybeCurrent = new None<Iterator<R>>();
		this.head = head;
	}
	next(): Option<R> {
		while (true){
			if (this.maybeCurrent.isPresent()){
				Iterator < /*R> it */ = this.maybeCurrent.orElse(null);
				let next : unknown = it.next();
				if (next.isPresent()){
					return next;
				}
				this.maybeCurrent = new None<Iterator<R>>();
			}
			Option < /*T> outer */ = this.head.next();
			if (outer.isPresent()){
				this.maybeCurrent = new Some<Iterator<R>>(this.mapper.apply(outer.orElse(null)));
			}
			else {
				return new None<R>();
			}
		}
	}
}
class Some<T> {
	map<R>(mapper : (arg0 : T) => R): Option<R> {
		return new Some<>(mapper.apply(this.value));
	}
	isEmpty(): boolean {
		return false;
	}
	get(): T {
		return this.value;
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
		consumer.accept(this.value);
	}
	or(other : () => Option<T>): Option<T> {
		return this;
	}
	flatMap<R>(mapper : (arg0 : T) => Option<R>): Option<R> {
		return mapper.apply(this.value);
	}
	filter(predicate : (arg0 : T) => boolean): Option<T> {
		return /*predicate.test(this.value) ? this : new None*/ < /*>*/();
	}
}
class None<T> {
	map<R>(mapper : (arg0 : T) => R): Option<R> {
		return new None<>();
	}
	isEmpty(): boolean {
		return true;
	}
	get(): T {
		return null;
	}
	orElse(other : T): T {
		return other;
	}
	orElseGet(supplier : () => T): T {
		return supplier.get();
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
}
class Main {
	main(): void {
		let source : unknown = Paths.get(".", "src", "java", "magma", "Main.java");
		let target : unknown = source.resolveSibling("main.ts");
		readString(source).match((input) => compileAndWrite(input, target), (value) => new Some<IOException>(value)).ifPresent(Throwable.printStackTrace);
	}
	compileAndWrite(input : string, target : Path): Option<IOException> {
		let output : unknown = compileRoot(input);
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
		let compiled : unknown = compileStatements(new CompileState(), input, Main.compileRootSegment);
		return compiled.left.output + compiled.right;
	}
	compileStatements(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => Tuple<CompileState, string>): Tuple<CompileState, string> {
		return compileAll(state, input, Main.foldStatements, mapper, Main.mergeStatements);
	}
	compileAll(state : CompileState, input : string, folder : (arg0 : DivideState, arg1 : string) => DivideState, mapper : (arg0 : CompileState, arg1 : string) => Tuple<CompileState, string>, merger : (arg0 : StringBuilder, arg1 : string) => StringBuilder): Tuple<CompileState, string> {
		let folded : unknown = parseAll(state, input, folder, mapper);
		return new Tuple<>(folded.left, generateAll(folded.right, merger));
	}
	generateAll(elements : List<string>, merger : (arg0 : StringBuilder, arg1 : string) => StringBuilder): string {
		return elements.iterate().fold(new StringBuilder(), merger).toString();
	}
	parseAll(state : CompileState, input : string, folder : (arg0 : DivideState, arg1 : string) => DivideState, mapper : (arg0 : CompileState, arg1 : string) => Tuple<CompileState, string>): Tuple<CompileState, List<string>> {
		return divide(input, folder).iterate().fold(new Tuple<CompileState, List<string>>(state, Lists.empty()), (current, segment) => {
			let currentState : unknown = current.left;
			let currentElement : unknown = current.right;
			let mappedTuple : unknown = mapper.apply(currentState, segment);
			let mappedState : unknown = mappedTuple.left;
			let mappedElement : unknown = mappedTuple.right;
			currentElement.add(mappedElement);
			return new Tuple<>(mappedState, currentElement);
		});
	}
	mergeStatements(cache : StringBuilder, element : string): StringBuilder {
		return cache.append(element);
	}
	divide(input : string, folder : (arg0 : DivideState, arg1 : string) => DivideState): List<string> {
		let current : unknown = new DivideState(input);
		while (true){
			let maybePopped : unknown = current.pop();
			if (maybePopped.isEmpty()){
				break;
			}
			let poppedTuple : unknown = maybePopped.orElse(null);
			let poppedState : unknown = poppedTuple.left;
			let popped : unknown = poppedTuple.right;
			current = foldSingleQuotes(poppedState, popped).or(() => foldDoubleQuotes(poppedState, popped)).orElseGet(() => folder.apply(poppedState, popped));
		}
		return current.advance().segments;
	}
	foldDoubleQuotes(state : DivideState, c : string): Option<DivideState> {
		if (c !== "\""){
			return new None<DivideState>();
		}
		let appended : unknown = state.append(c);
		while (true){
			let maybeTuple : unknown = appended.popAndAppendToTuple();
			if (maybeTuple.isEmpty()){
				break;
			}
			let tuple : unknown = maybeTuple.orElse(null);
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
		let state : unknown = tuple.left;
		let c : unknown = tuple.right;
		if (c === "\\"){
			return state.popAndAppendToOption();
		}
		return new Some<DivideState>(state);
	}
	foldStatements(state : DivideState, c : string): DivideState {
		let appended : unknown = state.append(c);
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
		return (state1, input1) => compileFirst(input1, sourceInfix, (_, right1) => {
			return compileFirst(right1, "{", (beforeContent, withEnd) => {
				return compileSuffix(withEnd.strip(), "}", (inputContent) => {
					let strippedBeforeContent : unknown = beforeContent.strip();
					return compileFirst(strippedBeforeContent, "(", (rawName, s2) => {
						let name : unknown = rawName.strip();
						return assembleStructure(targetInfix, state1, inputContent, name);
					}).or(() => {
						return assembleStructure(targetInfix, state1, inputContent, strippedBeforeContent);
					});
				});
			});
		});
	}
	assembleStructure(targetInfix : string, state1 : CompileState, inputContent : string, name : string): Option<Tuple<CompileState, string>> {
		let outputContentTuple : unknown = compileStatements(state1.withStructureName(name), inputContent, Main.compileClassSegment);
		let outputContentState : unknown = outputContentTuple.left;
		let outputContent : unknown = outputContentTuple.right;
		let generated : unknown = targetInfix + name + " {" + outputContent + "\n}\n";
		return new Some<Tuple<CompileState, string>>(new Tuple<CompileState, string>(outputContentState.append(generated), ""));
	}
	compileNamespaced(state : CompileState, input : string): Option<Tuple<CompileState, string>> {
		let stripped : unknown = input.strip();
		if (stripped.startsWith("package ") || stripped.startsWith("import ")){
			return new Some<Tuple<CompileState, string>>(new Tuple<CompileState, string>(state, ""));
		}
		else {
			return new None<Tuple<CompileState, string>>();
		}
	}
	compileOrPlaceholder(state : CompileState, input : string, rules : List<(arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, string>>>): Tuple<CompileState, string> {
		return compileOr(state, input, rules).orElseGet(() => new Tuple<>(state, generatePlaceholder(input)));
	}
	compileOr(state : CompileState, input : string, rules : List<(arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, string>>>): Option<Tuple<CompileState, string>> {
		return rules.iterate().map((rule) => rule.apply(state, input)).flatMap(Iterators.fromOption).next();
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
				return compileDefinition(state, beforeParams).flatMap((tuple) => compileMethodWithBeforeParams(tuple.left, tuple.right, withParams));
			});
		});
	}
	compileMethodWithBeforeParams(state : CompileState, header : MethodHeader, withParams : string): Option<Tuple<CompileState, string>> {
		return compileFirst(withParams, ")", (params, afterParams) => {
			let parametersTuple : unknown = compileValues(state, params, Main.compileParameter);
			let parametersState : unknown = parametersTuple.left;
			let parameters : unknown = parametersTuple.right;
			let headerGenerated : unknown = header.generateWithAfterName("(" + parameters + ")");
			return compilePrefix(afterParams.strip(), "{", (withoutContentStart) => {
				return compileSuffix(withoutContentStart.strip(), "}", (withoutContentEnd) => {
					let statementsTuple : unknown = compileFunctionStatements(parametersState.enterDepth().enterDepth(), withoutContentEnd);
					return new Some<Tuple<CompileState, string>>(new Tuple<CompileState, string>(statementsTuple.left.exitDepth().exitDepth(), "\n\t" + headerGenerated + " {" + statementsTuple.right + "\n\t}"));
				});
			}).or(() => {
				if (afterParams.strip().equals(";")){
					return new Some<Tuple<CompileState, string>>(new Tuple<CompileState, string>(parametersState, "\n\t" + headerGenerated + ";"));
				}
				return new None<Tuple<CompileState, string>>();
			});
		});
	}
	compileFunctionStatements(state : CompileState, input : string): Tuple<CompileState, string> {
		return compileStatements(state, input, Main.compileFunctionSegment);
	}
	compileFunctionSegment(state : CompileState, input : string): Tuple<CompileState, string> {
		return compileOrPlaceholder(state, input, Lists.of(Main.compileWhitespace, Main.compileEmptySegment, Main.compileBlock, Main.compileFunctionStatement, Main.compileReturnWithoutSuffix));
	}
	compileEmptySegment(state : CompileState, input : string): Option<Tuple<CompileState, string>> {
		if (input.strip().equals(";")){
			return new Some<Tuple<CompileState, string>>(new Tuple<CompileState, string>(state, ";"));
		}
		else {
			return new None<Tuple<CompileState, string>>();
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
						let contentTuple : unknown = compileFunctionStatements(headerTuple.left.enterDepth(), content);
						let indent : unknown = generateIndent(state.depth());
						return new Some<Tuple<CompileState, string>>(new Tuple<CompileState, string>(contentTuple.left.exitDepth(), indent + headerTuple.right + "{" + contentTuple.right + indent + "}"));
					});
				});
			});
		});
	}
	foldBlockStarts(state : DivideState, c : string): DivideState {
		let appended : unknown = state.append(c);
		if (c === "{"){
			let entered : unknown = appended.enter();
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
		return compileOr(state, input, Lists.of(createConditionalRule("if"), createConditionalRule("while"), Main.compileElse));
	}
	createConditionalRule(prefix : string): (arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, string>> {
		return (state1, input1) => compilePrefix(input1.strip(), prefix, (withoutPrefix) => {
			let strippedCondition : unknown = withoutPrefix.strip();
			return compilePrefix(strippedCondition, "(", (withoutConditionStart) => {
				return compileSuffix(withoutConditionStart, ")", (withoutConditionEnd) => {
					let tuple : unknown = compileValueOrPlaceholder(state1, withoutConditionEnd);
					return new Some<Tuple<CompileState, string>>(new Tuple<CompileState, string>(tuple.left, prefix + " (" + tuple.right + ")"));
				});
			});
		});
	}
	compileElse(state : CompileState, input : string): Option<Tuple<CompileState, string>> {
		if (input.strip().equals("else")){
			return new Some<Tuple<CompileState, string>>(new Tuple<CompileState, string>(state, "else "));
		}
		else {
			return new None<Tuple<CompileState, string>>();
		}
	}
	compileFunctionStatement(state : CompileState, input : string): Option<Tuple<CompileState, string>> {
		return compileSuffix(input.strip(), ";", (withoutEnd) => {
			let valueTuple : unknown = compileFunctionStatementValue(state, withoutEnd);
			return new Some<Tuple<CompileState, string>>(new Tuple<CompileState, string>(valueTuple.left, generateIndent(state.depth()) + valueTuple.right + ";"));
		});
	}
	generateIndent(indent : number): string {
		return "\n" + "\t".repeat(indent);
	}
	compileFunctionStatementValue(state : CompileState, withoutEnd : string): Tuple<CompileState, string> {
		return compileOrPlaceholder(state, withoutEnd, Lists.of(Main.compileReturnWithValue, Main.compileAssignment, Main.compileInvokable, createPostRule("++"), createPostRule("--"), Main.compileBreak));
	}
	compileBreak(state : CompileState, input : string): Option<Tuple<CompileState, string>> {
		if (input.strip().equals("break")){
			return new Some<Tuple<CompileState, string>>(new Tuple<CompileState, string>(state, "break"));
		}
		else {
			return new None<Tuple<CompileState, string>>();
		}
	}
	createPostRule(suffix : string): (arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, string>> {
		return (state1, input) => compileSuffix(input.strip(), suffix, (child) => {
			let tuple : unknown = compileValueOrPlaceholder(state1, child);
			return new Some<Tuple<CompileState, string>>(new Tuple<CompileState, string>(tuple.left, tuple.right + suffix));
		});
	}
	compileReturnWithValue(state : CompileState, input : string): Option<Tuple<CompileState, string>> {
		return compileReturn(input, (value1) => compileValue(state, value1));
	}
	compileReturn(input : string, mapper : (arg0 : string) => Option<Tuple<CompileState, string>>): Option<Tuple<CompileState, string>> {
		return compilePrefix(input.strip(), "return ", (value) => {
			return mapper.apply(value).flatMap((tuple) => {
				return new Some<Tuple<CompileState, string>>(new Tuple<CompileState, string>(tuple.left, "return " + tuple.right));
			});
		});
	}
	compileInvokable(state : CompileState, input : string): Option<Tuple<CompileState, string>> {
		return compileSuffix(input.strip(), ")", (withoutEnd) => {
			return compileSplit(splitFoldedLast(withoutEnd, "", Main.foldInvocationStarts), (callerWithArgStart, arguments) => {
				return compileSuffix(callerWithArgStart, "(", (caller) => compilePrefix(caller.strip(), "new ", (type) => {
					let callerTuple : unknown = compileTypeOrPlaceholder(state, type);
					return assembleInvokable(callerTuple.left, "new " + callerTuple.right, arguments);
				}).or(() => {
					let callerTuple : unknown = compileValueOrPlaceholder(state, caller);
					return assembleInvokable(callerTuple.left, callerTuple.right, arguments);
				}));
			});
		});
	}
	splitFoldedLast(input : string, delimiter : string, folder : (arg0 : DivideState, arg1 : string) => DivideState): Option<Tuple<string, string>> {
		return splitFolded(input, folder, (divisions1) => selectLast(divisions1, delimiter));
	}
	splitFolded(input : string, folder : (arg0 : DivideState, arg1 : string) => DivideState, selector : (arg0 : List<string>) => Option<Tuple<string, string>>): Option<Tuple<string, string>> {
		let divisions : unknown = divide(input, folder);
		if (divisions.size() < 2){
			return new None<Tuple<string, string>>();
		}
		return selector.apply(divisions);
	}
	selectLast(divisions : List<string>, delimiter : string): Option<Tuple<string, string>> {
		let beforeLast : unknown = divisions.subList(0, divisions.size() - 1).orElse(divisions);
		let last : unknown = divisions.findLast().orElse(null);
		let joined : unknown = beforeLast.iterate().collect(new Joiner(delimiter)).orElse("");
		return new Some<Tuple<string, string>>(new Tuple<string, string>(joined, last));
	}
	foldInvocationStarts(state : DivideState, c : string): DivideState {
		let appended : unknown = state.append(c);
		if (c === "("){
			let entered : unknown = appended.enter();
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
	assembleInvokable(state : CompileState, caller : string, arguments : string): Option<Tuple<CompileState, string>> {
		let argumentsTuple : unknown = compileValues(state, arguments, Main.compileValueOrPlaceholder);
		return new Some<Tuple<CompileState, string>>(new Tuple<CompileState, string>(argumentsTuple.left, caller + "(" + argumentsTuple.right + ")"));
	}
	compileAssignment(state : CompileState, input : string): Option<Tuple<CompileState, string>> {
		return compileFirst(input, "=", (destination, source) => {
			let sourceTuple : unknown = compileValueOrPlaceholder(state, source);
			let destinationTuple : unknown = compileValue(sourceTuple.left, destination).or(() => compileDefinition(sourceTuple.left, destination).map((tuple) => new Tuple<>(tuple.left, "let " + tuple.right.generate()))).orElseGet(() => new Tuple<>(sourceTuple.left, generatePlaceholder(destination)));
			return new Some<Tuple<CompileState, string>>(new Tuple<CompileState, string>(destinationTuple.left, destinationTuple.right + " = " + sourceTuple.right));
		});
	}
	compileValueOrPlaceholder(state : CompileState, input : string): Tuple<CompileState, string> {
		return compileValue(state, input).orElseGet(() => new Tuple<>(state, generatePlaceholder(input)));
	}
	compileValue(state : CompileState, input : string): Option<Tuple<CompileState, string>> {
		return compileOr(state, input, Lists.of(createAccessRule("."), createAccessRule("::"), Main.compileSymbol, Main.compileLambda, Main.compileNot, Main.compileInvokable, Main.compileNumber, createOperatorRuleWithDifferentInfix("==", "==="), createOperatorRuleWithDifferentInfix("!=", "!=="), createOperatorRule("+"), createOperatorRule("-"), createOperatorRule("<="), createOperatorRule("<"), createOperatorRule("&&"), createOperatorRule("||"), createOperatorRule(">="), createTextRule("\""), createTextRule("'")));
	}
	createTextRule(slice : string): (arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, string>> {
		return (state1, input1) => {
			let stripped : unknown = input1.strip();
			if (!stripped.startsWith(slice) || !stripped.endsWith(slice) || stripped.length() <= slice.length()){
				return new None<Tuple<CompileState, string>>();
			}
			let value : unknown = stripped.substring(slice.length(), stripped.length() - slice.length());
			return new Some<Tuple<CompileState, string>>(new Tuple<CompileState, string>(state1, "\"" + value + "\""));
		};
	}
	compileNot(state : CompileState, input : string): Option<Tuple<CompileState, string>> {
		return compilePrefix(input.strip(), "!", (withoutPrefix) => {
			let tuple : unknown = compileValueOrPlaceholder(state, withoutPrefix);
			return new Some<Tuple<CompileState, string>>(new Tuple<CompileState, string>(tuple.left, "!" + tuple.right));
		});
	}
	compileLambda(state : CompileState, input : string): Option<Tuple<CompileState, string>> {
		return compileFirst(input, "->", (beforeArrow, afterArrow) => {
			let strippedBeforeArrow : unknown = beforeArrow.strip();
			if (isSymbol(strippedBeforeArrow)){
				return getCompileStateStringTuple(state, Lists.of(strippedBeforeArrow), afterArrow);
			}
			return compilePrefix(strippedBeforeArrow, "(", (withoutStart) => {
				return compileSuffix(withoutStart, ")", (withoutEnd) => {
					let paramNames : unknown = divideValues(withoutEnd);
					if (paramNames.iterate().allMatch(Main.isSymbol)){
						return getCompileStateStringTuple(state, paramNames, afterArrow);
					}
					else {
						return new None<Tuple<CompileState, string>>();
					}
				});
			});
		});
	}
	getCompileStateStringTuple(state : CompileState, paramNames : List<string>, afterArrow : string): Option<Tuple<CompileState, string>> {
		let strippedAfterArrow : unknown = afterArrow.strip();
		return compilePrefix(strippedAfterArrow, "{", (withoutContentStart) => {
			return compileSuffix(withoutContentStart, "}", (withoutContentEnd) => {
				let statementsTuple : unknown = compileFunctionStatements(state.enterDepth(), withoutContentEnd);
				let statementsState : unknown = statementsTuple.left;
				let statements : unknown = statementsTuple.right;
				let exited : unknown = statementsState.exitDepth();
				return assembleLambda(exited, paramNames, "{" + statements + generateIndent(exited.depth) + "}");
			});
		}).or(() => {
			let tuple : unknown = compileValueOrPlaceholder(state, strippedAfterArrow);
			return assembleLambda(tuple.left, paramNames, tuple.right);
		});
	}
	assembleLambda(exited : CompileState, paramNames : List<string>, content : string): Option<Tuple<CompileState, string>> {
		let joinedParamNames : unknown = paramNames.iterate().collect(new Joiner(", ")).orElse("");
		return new Some<Tuple<CompileState, string>>(new Tuple<CompileState, string>(exited, "(" + joinedParamNames + ")" + " => " + content));
	}
	createOperatorRule(infix : string): (arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, string>> {
		return createOperatorRuleWithDifferentInfix(infix, infix);
	}
	createAccessRule(infix : string): (arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, string>> {
		return (state, input) => compileLast(input, infix, (child, rawProperty) => {
			let property : unknown = rawProperty.strip();
			if (isSymbol(property)){
				let tuple : unknown = compileValueOrPlaceholder(state, child);
				return new Some<Tuple<CompileState, string>>(new Tuple<CompileState, string>(tuple.left, tuple.right + "." + property));
			}
			else {
				return new None<Tuple<CompileState, string>>();
			}
		});
	}
	createOperatorRuleWithDifferentInfix(sourceInfix : string, targetInfix : string): (arg0 : CompileState, arg1 : string) => Option<Tuple<CompileState, string>> {
		return (state1, input1) => {
			return compileSplit(splitFolded(input1, foldOperator(sourceInfix), (divisions) => selectFirst(divisions, sourceInfix)), (left, right) => {
				let leftTuple : unknown = compileValueOrPlaceholder(state1, left);
				let rightTuple : unknown = compileValueOrPlaceholder(leftTuple.left, right);
				return new Some<Tuple<CompileState, string>>(new Tuple<CompileState, string>(rightTuple.left, leftTuple.right + " " + targetInfix + " " + rightTuple.right));
			});
		};
	}
	selectFirst(divisions : List<string>, delimiter : string): Option<Tuple<string, string>> {
		let first : unknown = divisions.findFirst().orElse(null);
		let afterFirst : unknown = divisions.subList(1, divisions.size()).orElse(divisions).iterate().collect(new Joiner(delimiter)).orElse("");
		return new Some<Tuple<string, string>>(new Tuple<string, string>(first, afterFirst));
	}
	foldOperator(infix : string): (arg0 : DivideState, arg1 : string) => DivideState {
		return (state, c) => {
			if (c === infix.charAt(0) && state.startsWith(infix.substring(1))){
				let length : unknown = infix.length() - 1;
				let counter : unknown = 0;
				let current : unknown = state;
				while (counter < length){
					counter++;
					current = current.pop().map(Tuple.left).orElse(current);
				}
				return current.advance();
			}
			return state.append(c);
		};
	}
	compileNumber(state : CompileState, input : string): Option<Tuple<CompileState, string>> {
		let stripped : unknown = input.strip();
		if (isNumber(stripped)){
			return new Some<Tuple<CompileState, string>>(new Tuple<CompileState, string>(state, stripped));
		}
		else {
			return new None<Tuple<CompileState, string>>();
		}
	}
	isNumber(input : string): boolean {
		return IntStream.range(0, input.length()).mapToObj(input.charAt).allMatch(Character.isDigit);
	}
	compileSymbol(state : CompileState, input : string): Option<Tuple<CompileState, string>> {
		let stripped : unknown = input.strip();
		if (isSymbol(stripped)){
			return new Some<Tuple<CompileState, string>>(new Tuple<CompileState, string>(state, stripped));
		}
		else {
			return new None<Tuple<CompileState, string>>();
		}
	}
	isSymbol(input : string): boolean {
		return IntStream.range(0, input.length()).allMatch((index) => isSymbolChar(index, input.charAt(index)));
	}
	isSymbolChar(index : number, c : string): boolean {
		return c === "_" || Character.isLetter(c) || (index !== 0 && Character.isDigit(c));
	}
	compilePrefix(input : string, infix : string, mapper : (arg0 : string) => Option<Tuple<CompileState, string>>): Option<Tuple<CompileState, string>> {
		if (!input.startsWith(infix)){
			return new None<Tuple<CompileState, string>>();
		}
		let slice : unknown = input.substring(infix.length());
		return mapper.apply(slice);
	}
	compileParameter(state : CompileState, input : string): Tuple<CompileState, string> {
		return compileOrPlaceholder(state, input, Lists.of(Main.compileWhitespace, (state1, input1) => {
			return compileDefinition(state1, input1).map((tuple) => new Tuple<>(tuple.left, tuple.right.generate()));
		}));
	}
	compileWhitespace(state : CompileState, input : string): Option<Tuple<CompileState, string>> {
		if (input.isBlank()){
			return new Some<Tuple<CompileState, string>>(new Tuple<CompileState, string>(state, ""));
		}
		return new None<Tuple<CompileState, string>>();
	}
	compileFieldDefinition(state : CompileState, input : string): Option<Tuple<CompileState, string>> {
		return compileSuffix(input.strip(), ";", (withoutEnd) => {
			let definitionTuple : unknown = compileDefinitionOrPlaceholder(state, withoutEnd);
			return new Some<Tuple<CompileState, string>>(new Tuple<CompileState, string>(definitionTuple.left, "\n\t" + definitionTuple.right + ";"));
		});
	}
	compileDefinitionOrPlaceholder(state : CompileState, input : string): Tuple<CompileState, string> {
		return compileDefinition(state, input).map((tuple) => new Tuple<>(tuple.left, tuple.right.generate())).orElseGet(() => new Tuple<>(state, generatePlaceholder(input)));
	}
	compileDefinition(state : CompileState, input : string): Option<Tuple<CompileState, Definition>> {
		return compileLast(input.strip(), " ", (beforeName, name) => {
			return compileSplit(splitFoldedLast(beforeName.strip(), " ", Main.foldTypeSeparators), (beforeType, type) => {
				return compileSuffix(beforeType.strip(), ">", (withoutTypeParamEnd) => {
					return compileFirst(withoutTypeParamEnd, "<", (beforeTypeParams, typeParamsString) => {
						let typeParams : unknown = divideValues(typeParamsString);
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
		return divide(input, Main.foldValues).iterate().map(String.strip).filter((value) => !value.isEmpty()).collect(new ListCollector<>());
	}
	foldTypeSeparators(state : DivideState, c : string): DivideState {
		if (c === " " && state.isLevel()){
			return state.advance();
		}
		let appended : unknown = state.append(c);
		if (c === "<"){
			return appended.enter();
		}
		if (c === ">"){
			return appended.exit();
		}
		return appended;
	}
	assembleDefinition(state : CompileState, maybeBeforeType : Option<string>, name : string, typeParams : List<string>, type : string): Option<Tuple<CompileState, Definition>> {
		let typeTuple : unknown = compileTypeOrPlaceholder(state, type);
		let generated : unknown = new Definition(maybeBeforeType, name, typeParams, typeTuple.right);
		return new Some<Tuple<CompileState, Definition>>(new Tuple<CompileState, Definition>(typeTuple.left, generated));
	}
	compileTypeOrPlaceholder(state : CompileState, type : string): Tuple<CompileState, string> {
		return compileType(state, type).orElseGet(() => new Tuple<>(state, generatePlaceholder(type)));
	}
	compileType(state : CompileState, type : string): Option<Tuple<CompileState, string>> {
		return compileOr(state, type, Lists.of(Main.compileGeneric, Main.compilePrimitive, Main.compileSymbolType));
	}
	compileSymbolType(state : CompileState, input : string): Option<Tuple<CompileState, string>> {
		let stripped : unknown = input.strip();
		if (isSymbol(stripped)){
			return new Some<Tuple<CompileState, string>>(new Tuple<CompileState, string>(state, stripped));
		}
		return new None<Tuple<CompileState, string>>();
	}
	compilePrimitive(state : CompileState, input : string): Option<Tuple<CompileState, string>> {
		return findPrimitiveValue(input.strip()).map((result) => new Tuple<>(state, result));
	}
	findPrimitiveValue(input : string): Option<string> {
		let stripped : unknown = input.strip();
		if (stripped.equals("char") || stripped.equals("Character") || stripped.equals("String")){
			return new Some<string>("string");
		}
		if (stripped.equals("int") || stripped.equals("Integer")){
			return new Some<string>("number");
		}
		if (stripped.equals("boolean")){
			return new Some<string>("boolean");
		}
		if (stripped.equals("var")){
			return new Some<string>("unknown");
		}
		if (stripped.equals("void")){
			return new Some<string>("void");
		}
		return new None<string>();
	}
	compileGeneric(state : CompileState, input : string): Option<Tuple<CompileState, string>> {
		return compileSuffix(input.strip(), ">", (withoutEnd) => {
			return compileFirst(withoutEnd, "<", (baseString, argumentsString) => {
				let argumentsTuple : unknown = parseValues(state, argumentsString, Main.compileTypeArgument);
				let argumentsState : unknown = argumentsTuple.left;
				let arguments : unknown = argumentsTuple.right;
				let base : unknown = baseString.strip();
				return assembleFunctionType(argumentsState, base, arguments).or(() => {
					return new Some<Tuple<CompileState, string>>(new Tuple<CompileState, string>(argumentsState, base + "<" + generateValues(arguments) + ">"));
				});
			});
		});
	}
	assembleFunctionType(state : CompileState, base : string, arguments : List<string>): Option<Tuple<CompileState, string>> {
		return mapFunctionType(base, arguments).map((generated) => new Tuple<>(state, generated));
	}
	mapFunctionType(base : string, arguments : List<string>): Option<string> {
		if (base.equals("Function")){
			return new Some<string>(generateFunctionType(Lists.of(arguments.find(0).orElse(null)), arguments.find(1).orElse(null)));
		}
		if (base.equals("BiFunction")){
			return new Some<string>(generateFunctionType(Lists.of(arguments.find(0).orElse(null), arguments.find(1).orElse(null)), arguments.find(2).orElse(null)));
		}
		if (base.equals("Supplier")){
			return arguments.findFirst().map((first) => generateFunctionType(Lists.empty(), first));
		}
		if (base.equals("Consumer")){
			return arguments.findFirst().map((first) => generateFunctionType(Lists.of(first), "void"));
		}
		if (base.equals("Predicate")){
			return arguments.findFirst().map((first) => generateFunctionType(Lists.of(first), "boolean"));
		}
		return new None<string>();
	}
	generateFunctionType(arguments : List<string>, returns : string): string {
		let joinedArguments : unknown = arguments.iterateWithIndices().map((tuple) => "arg" + tuple.left + " : " + tuple.right).collect(new Joiner(", ")).orElse("");
		return "(" + joinedArguments + ") => " + returns;
	}
	compileTypeArgument(state : CompileState, input : string): Tuple<CompileState, string> {
		return compileOrPlaceholder(state, input, Lists.of(Main.compileWhitespace, Main.compileType));
	}
	compileValues(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => Tuple<CompileState, string>): Tuple<CompileState, string> {
		let folded : unknown = parseValues(state, input, mapper);
		return new Tuple<>(folded.left, generateValues(folded.right));
	}
	generateValues(values : List<string>): string {
		return generateAll(values, Main.mergeValues);
	}
	parseValues(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => Tuple<CompileState, string>): Tuple<CompileState, List<string>> {
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
		let appended : unknown = state.append(c);
		if (c === "-"){
			let peeked : unknown = appended.peek();
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
		let content : unknown = input.substring(0, input.length() - suffix.length());
		return mapper.apply(content);
	}
	compileFirst<T>(input : string, infix : string, mapper : (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return compileInfix(input, infix, Main.findFirst, mapper);
	}
	compileInfix<T>(input : string, infix : string, locator : (arg0 : string, arg1 : string) => number, mapper : (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return compileSplit(split(input, infix, locator), mapper);
	}
	compileSplit<T>(splitter : Option<Tuple<string, string>>, mapper : (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return splitter.flatMap((tuple) => mapper.apply(tuple.left, tuple.right));
	}
	split(input : string, infix : string, locator : (arg0 : string, arg1 : string) => number): Option<Tuple<string, string>> {
		let index : unknown = locator.apply(input, infix);
		if (index < 0){
			return new None<Tuple<string, string>>();
		}
		let left : unknown = input.substring(0, index);
		let right : unknown = input.substring(index + infix.length());
		return new Some<Tuple<string, string>>(new Tuple<string, string>(left, right));
	}
	findFirst(input : string, infix : string): number {
		return input.indexOf(infix);
	}
	generatePlaceholder(input : string): string {
		let replaced : unknown = input.replace("/*", "start").replace("*/", "end");
		return "/*" + replaced + "*/";
	}
}
