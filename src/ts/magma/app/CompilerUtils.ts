/*[
	JVMList: jvm.api.collect.list, 
	Lists: jvm.api.collect.list, 
	Files: jvm.api.io, 
	Actual: magma.annotate, 
	Namespace: magma.annotate, 
	Collector: magma.api.collect, 
	EmptyHead: magma.api.collect.head, 
	FlatMapHead: magma.api.collect.head, 
	Head: magma.api.collect.head, 
	HeadedIter: magma.api.collect.head, 
	MapHead: magma.api.collect.head, 
	RangeHead: magma.api.collect.head, 
	SingleHead: magma.api.collect.head, 
	Iter: magma.api.collect, 
	Iters: magma.api.collect, 
	Joiner: magma.api.collect, 
	Iterable: magma.api.collect.list, 
	List: magma.api.collect.list, 
	ListCollector: magma.api.collect.list, 
	Console: magma.api.io, 
	IOError: magma.api.io, 
	Path: magma.api.io, 
	None: magma.api.option, 
	Option: magma.api.option, 
	Some: magma.api.option, 
	Err: magma.api.result, 
	Ok: magma.api.result, 
	Result: magma.api.result, 
	Characters: magma.api.text, 
	Strings: magma.api.text, 
	Tuple2: magma.api, 
	Tuple2Impl: magma.api, 
	Application: magma.app, 
	CompileState: magma.app.compile, 
	Context: magma.app.compile, 
	ConstructionCaller: magma.app.compile.define, 
	ConstructorHeader: magma.app.compile.define, 
	Definition: magma.app.compile.define, 
	MethodHeader: magma.app.compile.define, 
	Parameter: magma.app.compile.define, 
	Dependency: magma.app.compile, 
	DivideState: magma.app.compile, 
	ImmutableCompileState: magma.app.compile, 
	ImmutableContext: magma.app.compile, 
	ImmutableDivideState: magma.app.compile, 
	ImmutableRegistry: magma.app.compile, 
	ImmutableStack: magma.app.compile, 
	Import: magma.app.compile, 
	Registry: magma.app.compile, 
	Stack: magma.app.compile, 
	Whitespace: magma.app.compile.text, 
	FunctionType: magma.app.compile.type, 
	PrimitiveType: magma.app.compile.type, 
	TemplateType: magma.app.compile.type, 
	Type: magma.app.compile.type, 
	VariadicType: magma.app.compile.type, 
	AccessValue: magma.app.compile.value, 
	Argument: magma.app.compile.value, 
	Caller: magma.app.compile.value, 
	Invokable: magma.app.compile.value, 
	Lambda: magma.app.compile.value, 
	Not: magma.app.compile.value, 
	Operation: magma.app.compile.value, 
	Placeholder: magma.app.compile.value, 
	StringValue: magma.app.compile.value, 
	Symbol: magma.app.compile.value, 
	Value: magma.app.compile.value, 
	CompilerUtils: magma.app, 
	DefiningCompiler: magma.app, 
	DefinitionCompiler: magma.app, 
	FieldCompiler: magma.app, 
	FunctionSegmentCompiler: magma.app, 
	PathSource: magma.app.io, 
	Source: magma.app.io, 
	Location: magma.app, 
	Main: magma.app, 
	PathSources: magma.app, 
	PathTargets: magma.app, 
	Platform: magma.app, 
	RootCompiler: magma.app, 
	Sources: magma.app, 
	Targets: magma.app, 
	TypeCompiler: magma.app, 
	ValueCompiler: magma.app
]*/
import { CompileState } from "../../magma/app/compile/CompileState";
import { Tuple2 } from "../../magma/api/Tuple2";
import { DivideState } from "../../magma/app/compile/DivideState";
import { Some } from "../../magma/api/option/Some";
import { List } from "../../magma/api/collect/list/List";
import { Tuple2Impl } from "../../magma/api/Tuple2Impl";
import { Lists } from "../../jvm/api/collect/list/Lists";
import { Iterable } from "../../magma/api/collect/list/Iterable";
import { Option } from "../../magma/api/option/Option";
import { Iter } from "../../magma/api/collect/Iter";
import { RootCompiler } from "../../magma/app/RootCompiler";
import { None } from "../../magma/api/option/None";
import { Iters } from "../../magma/api/collect/Iters";
import { FunctionSegmentCompiler } from "../../magma/app/FunctionSegmentCompiler";
import { Strings } from "../../magma/api/text/Strings";
import { Whitespace } from "../../magma/app/compile/text/Whitespace";
import { ListCollector } from "../../magma/api/collect/list/ListCollector";
import { Joiner } from "../../magma/api/collect/Joiner";
export class CompilerUtils {
	static compileStatements(state: CompileState, input: string, mapper: (arg0 : CompileState, arg1 : string) => Tuple2<CompileState, string>): Tuple2<CompileState, string> {
		return CompilerUtils.compileAll(state, input, CompilerUtils.foldStatements, mapper, CompilerUtils.mergeStatements)/*unknown*/;
	}
	static compileAll(state: CompileState, input: string, folder: (arg0 : DivideState, arg1 : string) => DivideState, mapper: (arg0 : CompileState, arg1 : string) => Tuple2<CompileState, string>, merger: (arg0 : string, arg1 : string) => string): Tuple2<CompileState, string> {
		let folded = CompilerUtils.parseAll(state, input, folder, (state1: CompileState, s: string) => new Some<Tuple2<CompileState, string>>(mapper(state1, s))/*unknown*/).orElse(new Tuple2Impl<CompileState, List<string>>(state, Lists.empty()))/*unknown*/;
		return new Tuple2Impl<CompileState, string>(folded.left(), CompilerUtils.generateAll(folded.right(), merger))/*unknown*/;
	}
	static generateAll(elements: Iterable<string>, merger: (arg0 : string, arg1 : string) => string): string {
		return elements.iter().foldWithInitial("", merger)/*unknown*/;
	}
	static parseAll<T>(state: CompileState, input: string, folder: (arg0 : DivideState, arg1 : string) => DivideState, biFunction: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>): Option<Tuple2<CompileState, List<T>>> {
		return CompilerUtils.divide(input, folder).foldWithInitial(new Some<Tuple2<CompileState, List<T>>>(new Tuple2Impl<CompileState, List<T>>(state, Lists.empty())), (maybeCurrent: Option<Tuple2<CompileState, List<T>>>, segment: string) => maybeCurrent.flatMap((current: Tuple2<CompileState, List<T>>) => {
			let currentState = current.left()/*unknown*/;
			let currentElement = current.right()/*unknown*/;
			return biFunction(currentState, segment).map((mappedTuple: Tuple2<CompileState, T>) => {
				let mappedState = mappedTuple.left()/*unknown*/;
				let mappedElement = mappedTuple.right()/*unknown*/;
				return new Tuple2Impl<CompileState, List<T>>(mappedState, currentElement.addLast(mappedElement))/*unknown*/;
			})/*unknown*/;
		})/*unknown*/)/*unknown*/;
	}
	static mergeStatements(cache: string, element: string): string {
		return cache + element/*unknown*/;
	}
	static divide(input: string, folder: (arg0 : DivideState, arg1 : string) => DivideState): Iter<string> {
		let current = RootCompiler.createInitialDivideState(input)/*unknown*/;
		while (true/*unknown*/){
			let poppedTuple0 = current.pop().toTuple(new Tuple2Impl<DivideState, string>(current, "\0"))/*unknown*/;
			if (!poppedTuple0/*unknown*/.left()/*unknown*/){
				break;
			}
			let poppedTuple = poppedTuple0.right()/*unknown*/;
			let poppedState = poppedTuple.left()/*unknown*/;
			let popped = poppedTuple.right()/*unknown*/;
			current/*Tuple2<CompileState, List<T>>*/ = CompilerUtils.foldSingleQuotes(poppedState, popped).or(() => CompilerUtils.foldDoubleQuotes(poppedState, popped)/*unknown*/).orElseGet(() => folder(poppedState, popped)/*unknown*/)/*unknown*/;
		}
		return current.advance().query()/*unknown*/;
	}
	static foldDoubleQuotes(state: DivideState, c: string): Option<DivideState> {
		if ("\"" !== c/*unknown*/){
			return new None<DivideState>()/*unknown*/;
		}
		let appended = state.append(c)/*unknown*/;
		while (true/*unknown*/){
			let maybeTuple = appended.popAndAppendToTuple().toTuple(new Tuple2Impl<DivideState, string>(appended, "\0"))/*unknown*/;
			if (!maybeTuple/*unknown*/.left()/*unknown*/){
				break;
			}
			let tuple = maybeTuple.right()/*unknown*/;
			appended/*unknown*/ = tuple.left()/*unknown*/;
			if ("\\" === tuple.right()/*unknown*/){
				appended/*unknown*/ = appended.popAndAppendToOption().orElse(appended)/*unknown*/;
			}
			if ("\"" === tuple.right()/*unknown*/){
				break;
			}
		}
		return new Some<DivideState>(appended)/*unknown*/;
	}
	static foldSingleQuotes(state: DivideState, c: string): Option<DivideState> {
		if ("\'" !== c/*unknown*/){
			return new None<DivideState>()/*unknown*/;
		}
		return state.append(c).popAndAppendToTuple().flatMap(CompilerUtils.foldEscaped).flatMap((state1: DivideState) => state1.popAndAppendToOption()/*unknown*/)/*unknown*/;
	}
	static foldEscaped(tuple: Tuple2<DivideState, string>): Option<DivideState> {
		let state = tuple.left()/*unknown*/;
		let c = tuple.right()/*unknown*/;
		if ("\\" === c/*unknown*/){
			return state.popAndAppendToOption()/*unknown*/;
		}
		return new Some<DivideState>(state)/*unknown*/;
	}
	static foldStatements(state: DivideState, c: string): DivideState {
		let appended = state.append(c)/*unknown*/;
		if (";" === c && appended.isLevel()/*unknown*/){
			return appended.advance()/*unknown*/;
		}
		if ("}" === c && appended.isShallow()/*unknown*/){
			return appended.advance().exit()/*unknown*/;
		}
		if ("{" === c || "(" === c/*unknown*/){
			return appended.enter()/*unknown*/;
		}
		if ("}" === c || ")" === c/*unknown*/){
			return appended.exit()/*unknown*/;
		}
		return appended/*unknown*/;
	}
	static compileOrPlaceholder(state: CompileState, input: string, rules: Iterable<(arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, string>>>): Tuple2<CompileState, string> {
		return CompilerUtils.or(state, input, rules).orElseGet(() => new Tuple2Impl<CompileState, string>(state, CompilerUtils.generatePlaceholder(input))/*unknown*/)/*unknown*/;
	}
	static or<T>(state: CompileState, input: string, rules: Iterable<(arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>>): Option<Tuple2<CompileState, T>> {
		return rules.iter().map((rule: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>) => CompilerUtils.getApply(state, input, rule)/*unknown*/).flatMap(Iters.fromOption).next()/*unknown*/;
	}
	static getApply<T>(state: CompileState, input: string, rule: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>): Option<Tuple2<CompileState, T>> {
		return rule(state, input)/*unknown*/;
	}
	static compileFunctionSegment(state: CompileState, input: string): Tuple2<CompileState, string> {
		return CompilerUtils.compileOrPlaceholder(state, input, Lists.of(CompilerUtils.compileWhitespace, FunctionSegmentCompiler.compileEmptySegment, FunctionSegmentCompiler.compileBlock, FunctionSegmentCompiler.compileFunctionStatement, FunctionSegmentCompiler.compileReturnWithoutSuffix))/*unknown*/;
	}
	static compilePrefix<T>(input: string, infix: string, mapper: (arg0 : string) => Option<Tuple2<CompileState, T>>): Option<Tuple2<CompileState, T>> {
		if (!input/*string*/.startsWith(infix)/*unknown*/){
			return new None<Tuple2<CompileState, T>>()/*unknown*/;
		}
		let slice = Strings.sliceFrom(input, Strings.length(infix))/*unknown*/;
		return mapper(slice)/*unknown*/;
	}
	static compileWhitespace(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return CompilerUtils.parseWhitespace(state, input).map((tuple: Tuple2<CompileState, Whitespace>) => new Tuple2Impl<CompileState, string>(tuple.left(), tuple.right().generate())/*unknown*/)/*unknown*/;
	}
	static parseWhitespace(state: CompileState, input: string): Option<Tuple2<CompileState, Whitespace>> {
		if (Strings.isBlank(input)/*unknown*/){
			return new Some<Tuple2<CompileState, Whitespace>>(new Tuple2Impl<CompileState, Whitespace>(state, new Whitespace()))/*unknown*/;
		}
		return new None<Tuple2<CompileState, Whitespace>>()/*unknown*/;
	}
	static foldDelimited(state1: DivideState, c: string, delimiter: string): DivideState {
		if (delimiter === c/*unknown*/){
			return state1.advance()/*unknown*/;
		}
		return state1.append(c)/*unknown*/;
	}
	static divideValues(input: string): List<string> {
		return CompilerUtils.divide(input, CompilerUtils.foldValues).map((input1: string) => Strings.strip(input1)/*unknown*/).filter((value: string) => !Strings/*unknown*/.isEmpty(value)/*unknown*/).collect(new ListCollector<string>())/*unknown*/;
	}
	static generateValueStrings(values: Iterable<string>): string {
		return CompilerUtils.generateAll(values, CompilerUtils.mergeValues)/*unknown*/;
	}
	static parseValuesOrEmpty<T>(state: CompileState, input: string, mapper: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>): Tuple2<CompileState, List<T>> {
		return CompilerUtils.parseValues(state, input, mapper).orElse(new Tuple2Impl<CompileState, List<T>>(state, Lists.empty()))/*unknown*/;
	}
	static parseValues<T>(state: CompileState, input: string, mapper: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>): Option<Tuple2<CompileState, List<T>>> {
		return CompilerUtils.parseAll(state, input, CompilerUtils.foldValues, mapper)/*unknown*/;
	}
	static mergeValues(cache: string, element: string): string {
		if (Strings.isEmpty(cache)/*unknown*/){
			return cache + element/*unknown*/;
		}
		return cache + ", " + element/*unknown*/;
	}
	static foldValues(state: DivideState, c: string): DivideState {
		if ("," === c && state.isLevel()/*unknown*/){
			return state.advance()/*unknown*/;
		}
		let appended = state.append(c)/*unknown*/;
		if ("-" === c/*unknown*/){
			let peeked = appended.peek()/*unknown*/;
			if (">" === peeked/*unknown*/){
				return appended.popAndAppendToOption().orElse(appended)/*unknown*/;
			}
			else {
				return appended/*unknown*/;
			}
		}
		if ("<" === c || "(" === c/*unknown*/){
			return appended.enter()/*unknown*/;
		}
		if (">" === c || ")" === c/*unknown*/){
			return appended.exit()/*unknown*/;
		}
		return appended/*unknown*/;
	}
	static compileLast<T>(input: string, infix: string, mapper: (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return CompilerUtils.compileInfix(input, infix, CompilerUtils.findLast, mapper)/*unknown*/;
	}
	static findLast(input: string, infix: string): number {
		return input.lastIndexOf(infix)/*unknown*/;
	}
	static compileSuffix<T>(input: string, suffix: string, mapper: (arg0 : string) => Option<T>): Option<T> {
		if (!input/*string*/.endsWith(suffix)/*unknown*/){
			return new None<T>()/*unknown*/;
		}
		let length = Strings.length(input)/*unknown*/;
		let length1 = Strings.length(suffix)/*unknown*/;
		let content = Strings.sliceBetween(input, 0, length - length1)/*unknown*/;
		return mapper(content)/*unknown*/;
	}
	static compileFirst<T>(input: string, infix: string, mapper: (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return CompilerUtils.compileInfix(input, infix, CompilerUtils.findFirst, mapper)/*unknown*/;
	}
	static compileInfix<T>(input: string, infix: string, locator: (arg0 : string, arg1 : string) => number, mapper: (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return CompilerUtils.compileSplit(CompilerUtils.split(input, infix, locator), mapper)/*unknown*/;
	}
	static compileSplit<T>(splitter: Option<Tuple2<string, string>>, mapper: (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return splitter.flatMap((tuple: Tuple2<string, string>) => mapper(tuple.left(), tuple.right())/*unknown*/)/*unknown*/;
	}
	static split(input: string, infix: string, locator: (arg0 : string, arg1 : string) => number): Option<Tuple2<string, string>> {
		let index = locator(input, infix)/*unknown*/;
		if (0 > index/*unknown*/){
			return new None<Tuple2<string, string>>()/*unknown*/;
		}
		let left = Strings.sliceBetween(input, 0, index)/*unknown*/;
		let length = Strings.length(infix)/*unknown*/;
		let right = Strings.sliceFrom(input, index + length)/*unknown*/;
		return new Some<Tuple2<string, string>>(new Tuple2Impl<string, string>(left, right))/*unknown*/;
	}
	static findFirst(input: string, infix: string): number {
		return input.indexOf(infix)/*unknown*/;
	}
	static generatePlaceholder(input: string): string {
		let replaced = input.replace("/*", "start").replace("*/", "end")/*unknown*/;
		return "/*" + replaced + "*/"/*unknown*/;
	}
	static foldOperator(infix: string): (arg0 : DivideState, arg1 : string) => DivideState {
		return (state: DivideState, c: string) => {
			if (c === infix.charAt(0) && state.startsWith(Strings.sliceFrom(infix, 1))/*unknown*/){
				let length = Strings.length(infix) - 1/*unknown*/;
				let counter = 0/*unknown*/;
				let current = state/*DivideState*/;
				while (counter < length/*unknown*/){
					counter/*unknown*/++;
					current/*Tuple2<CompileState, List<T>>*/ = current.pop().map((tuple: Tuple2<DivideState, string>) => tuple.left()/*unknown*/).orElse(current)/*unknown*/;
				}
				return current.advance()/*unknown*/;
			}
			return state.append(c)/*unknown*/;
		}/*unknown*/;
	}
	static selectFirst(divisions: List<string>, delimiter: string): Option<Tuple2<string, string>> {
		let first = divisions.findFirst().orElse("")/*unknown*/;
		let afterFirst = divisions.subList(1, divisions.size()).orElse(divisions).iter().collect(new Joiner(delimiter)).orElse("")/*unknown*/;
		return new Some<Tuple2<string, string>>(new Tuple2Impl<string, string>(first, afterFirst))/*unknown*/;
	}
	static retain<T, R>(args: Iterable<T>, mapper: (arg0 : T) => Option<R>): Iterable<R> {
		return args.iter().map(mapper).flatMap(Iters.fromOption).collect(new ListCollector<R>())/*unknown*/;
	}
	static selectLast(divisions: List<string>, delimiter: string): Option<Tuple2<string, string>> {
		let beforeLast = divisions.subList(0, divisions.size() - 1).orElse(divisions)/*unknown*/;
		let last = divisions.findLast().orElse("")/*unknown*/;
		let joined = beforeLast.iter().collect(new Joiner(delimiter)).orElse("")/*unknown*/;
		return new Some<Tuple2<string, string>>(new Tuple2Impl<string, string>(joined, last))/*unknown*/;
	}
	static splitFoldedLast(input: string, delimiter: string, folder: (arg0 : DivideState, arg1 : string) => DivideState): Option<Tuple2<string, string>> {
		return CompilerUtils.splitFolded(input, folder, (divisions1: List<string>) => CompilerUtils.selectLast(divisions1, delimiter)/*unknown*/)/*unknown*/;
	}
	static splitFolded(input: string, folder: (arg0 : DivideState, arg1 : string) => DivideState, selector: (arg0 : List<string>) => Option<Tuple2<string, string>>): Option<Tuple2<string, string>> {
		let divisions = CompilerUtils.divide(input, folder).collect(new ListCollector<string>())/*unknown*/;
		if (2 > divisions.size()/*unknown*/){
			return new None<Tuple2<string, string>>()/*unknown*/;
		}
		return selector(divisions)/*unknown*/;
	}
}
