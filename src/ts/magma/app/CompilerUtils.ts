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
	Divider: magma.app.compile.divide, 
	FoldedDivider: magma.app.compile.divide, 
	DivideState: magma.app.compile, 
	DecoratedFolder: magma.app.compile.fold, 
	DelimitedFolder: magma.app.compile.fold, 
	Folder: magma.app.compile.fold, 
	OperatorFolder: magma.app.compile.fold, 
	StatementsFolder: magma.app.compile.fold, 
	TypeSeparatorFolder: magma.app.compile.fold, 
	ValueFolder: magma.app.compile.fold, 
	ImmutableCompileState: magma.app.compile, 
	ImmutableContext: magma.app.compile, 
	ImmutableDivideState: magma.app.compile, 
	ImmutableRegistry: magma.app.compile, 
	ImmutableStack: magma.app.compile, 
	Import: magma.app.compile, 
	FirstLocator: magma.app.compile.locate, 
	LastLocator: magma.app.compile.locate, 
	Locator: magma.app.compile.locate, 
	Merger: magma.app.compile.merge, 
	StatementsMerger: magma.app.compile.merge, 
	Registry: magma.app.compile, 
	LastSelector: magma.app.compile.select, 
	Selector: magma.app.compile.select, 
	LocatingSplitter: magma.app.compile.split, 
	Splitter: magma.app.compile.split, 
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
import { StatementsFolder } from "../../magma/app/compile/fold/StatementsFolder";
import { StatementsMerger } from "../../magma/app/compile/merge/StatementsMerger";
import { Folder } from "../../magma/app/compile/fold/Folder";
import { Merger } from "../../magma/app/compile/merge/Merger";
import { Some } from "../../magma/api/option/Some";
import { List } from "../../magma/api/collect/list/List";
import { Tuple2Impl } from "../../magma/api/Tuple2Impl";
import { Lists } from "../../jvm/api/collect/list/Lists";
import { Iterable } from "../../magma/api/collect/list/Iterable";
import { Option } from "../../magma/api/option/Option";
import { FoldedDivider } from "../../magma/app/compile/divide/FoldedDivider";
import { DecoratedFolder } from "../../magma/app/compile/fold/DecoratedFolder";
import { Iters } from "../../magma/api/collect/Iters";
import { None } from "../../magma/api/option/None";
import { Strings } from "../../magma/api/text/Strings";
import { Whitespace } from "../../magma/app/compile/text/Whitespace";
import { ValueFolder } from "../../magma/app/compile/fold/ValueFolder";
import { LocatingSplitter } from "../../magma/app/compile/split/LocatingSplitter";
import { LastLocator } from "../../magma/app/compile/locate/LastLocator";
import { Splitter } from "../../magma/app/compile/split/Splitter";
import { Joiner } from "../../magma/api/collect/Joiner";
import { ListCollector } from "../../magma/api/collect/list/ListCollector";
import { Selector } from "../../magma/app/compile/select/Selector";
export class CompilerUtils {
	static compileStatements(state: CompileState, input: string, mapper: (arg0 : CompileState, arg1 : string) => Tuple2<CompileState, string>): Tuple2<CompileState, string> {
		return CompilerUtils.compileAll(state, input, new StatementsFolder(), mapper, new StatementsMerger())/*unknown*/;
	}
	static compileAll(state: CompileState, input: string, folder: Folder, mapper: (arg0 : CompileState, arg1 : string) => Tuple2<CompileState, string>, merger: Merger): Tuple2<CompileState, string> {
		let folded = CompilerUtils.parseAll(state, input, folder, (state1: CompileState, s: string) => new Some<Tuple2<CompileState, string>>(mapper(state1, s))/*unknown*/).orElse(new Tuple2Impl<CompileState, List<string>>(state, Lists.empty()))/*unknown*/;
		return new Tuple2Impl<CompileState, string>(folded.left(), CompilerUtils.generateAll(folded.right(), merger))/*unknown*/;
	}
	static generateAll(elements: Iterable<string>, merger: Merger): string {
		return elements.iter().foldWithInitial("", merger.apply)/*unknown*/;
	}
	static parseAll<T>(state: CompileState, input: string, folder: Folder, rule: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>): Option<Tuple2<CompileState, List<T>>> {
		return new FoldedDivider(new DecoratedFolder(folder)).divide(input).foldWithInitial(new Some<Tuple2<CompileState, List<T>>>(new Tuple2Impl<CompileState, List<T>>(state, Lists.empty())), (maybeCurrent: Option<Tuple2<CompileState, List<T>>>, segment: string) => maybeCurrent.flatMap((current: Tuple2<CompileState, List<T>>) => {
			let currentState = current.left()/*unknown*/;
			let currentElement = current.right()/*unknown*/;
			return rule(currentState, segment).map((mappedTuple: Tuple2<CompileState, T>) => {
				let mappedState = mappedTuple.left()/*unknown*/;
				let mappedElement = mappedTuple.right()/*unknown*/;
				return new Tuple2Impl<CompileState, List<T>>(mappedState, currentElement.addLast(mappedElement))/*unknown*/;
			})/*unknown*/;
		})/*unknown*/)/*unknown*/;
	}
	static compileOrPlaceholder(state: CompileState, input: string, rules: Iterable<(arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, string>>>): Tuple2<CompileState, string> {
		return CompilerUtils.or(state, input, rules).orElseGet(() => new Tuple2Impl<CompileState, string>(state, CompilerUtils.generatePlaceholder(input))/*unknown*/)/*unknown*/;
	}
	static or<T>(state: CompileState, input: string, rules: Iterable<(arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>>): Option<Tuple2<CompileState, T>> {
		return rules.iter().map((rule: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>) => rule(state, input)/*unknown*/).flatMap(Iters.fromOption).next()/*unknown*/;
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
	static generateValueStrings(values: Iterable<string>): string {
		return CompilerUtils.generateAll(values, CompilerUtils.mergeValues)/*unknown*/;
	}
	static parseValuesOrEmpty<T>(state: CompileState, input: string, mapper: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>): Tuple2<CompileState, List<T>> {
		return CompilerUtils.parseValues(state, input, mapper).orElse(new Tuple2Impl<CompileState, List<T>>(state, Lists.empty()))/*unknown*/;
	}
	static parseValues<T>(state: CompileState, input: string, mapper: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>): Option<Tuple2<CompileState, List<T>>> {
		return CompilerUtils.parseAll(state, input, new ValueFolder(), mapper)/*unknown*/;
	}
	static mergeValues(cache: string, element: string): string {
		if (Strings.isEmpty(cache)/*unknown*/){
			return cache + element/*unknown*/;
		}
		return cache + ", " + element/*unknown*/;
	}
	static compileLast<T>(input: string, infix: string, mapper: (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return CompilerUtils.compileSplit(input, new LocatingSplitter(infix, new LastLocator()), mapper)/*unknown*/;
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
	static compileSplit<T>(input: string, splitter: Splitter, mapper: (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return splitter.apply(input).flatMap((tuple: Tuple2<string, string>) => mapper(tuple.left(), tuple.right())/*unknown*/)/*unknown*/;
	}
	static generatePlaceholder(input: string): string {
		let replaced = input.replace("/*", "start").replace("*/", "end")/*unknown*/;
		return "/*" + replaced + "*/"/*unknown*/;
	}
	static selectFirst(divisions: List<string>, delimiter: string): Option<Tuple2<string, string>> {
		let first = divisions.findFirst().orElse("")/*unknown*/;
		let afterFirst = divisions.subList(1, divisions.size()).orElse(divisions).iter().collect(new Joiner(delimiter)).orElse("")/*unknown*/;
		return new Some<Tuple2<string, string>>(new Tuple2Impl<string, string>(first, afterFirst))/*unknown*/;
	}
	static retain<T, R>(args: Iterable<T>, mapper: (arg0 : T) => Option<R>): Iterable<R> {
		return args.iter().map(mapper).flatMap(Iters.fromOption).collect(new ListCollector<R>())/*unknown*/;
	}
	static splitFolded(input: string, folder: Folder, selector: Selector): Option<Tuple2<string, string>> {
		let divisions = new FoldedDivider(new DecoratedFolder(folder)).divide(input).collect(new ListCollector<string>())/*unknown*/;
		if (2 > divisions.size()/*unknown*/){
			return new None<Tuple2<string, string>>()/*unknown*/;
		}
		return selector.apply(divisions)/*unknown*/;
	}
}
