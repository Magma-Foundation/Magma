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
	Composable: magma.app.compile.compose, 
	PrefixComposable: magma.app.compile.compose, 
	SplitComposable: magma.app.compile.compose, 
	SuffixComposable: magma.app.compile.compose, 
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
	ValueMerger: magma.app.compile.merge, 
	Registry: magma.app.compile, 
	OrRule: magma.app.compile.rule, 
	Rule: magma.app.compile.rule, 
	FirstSelector: magma.app.compile.select, 
	LastSelector: magma.app.compile.select, 
	Selector: magma.app.compile.select, 
	FoldingSplitter: magma.app.compile.split, 
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
	ValueUtils: magma.app.compile, 
	CompilerUtils: magma.app, 
	DefiningCompiler: magma.app, 
	DefinitionCompiler: magma.app, 
	DivideRule: magma.app, 
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
	ValueCompiler: magma.app, 
	WhitespaceCompiler: magma.app
]*/
import { Sources } from "../../magma/app/Sources";
import { Targets } from "../../magma/app/Targets";
import { Source } from "../../magma/app/io/Source";
import { Joiner } from "../../magma/api/collect/Joiner";
import { IOError } from "../../magma/api/io/IOError";
import { Option } from "../../magma/api/option/Option";
import { Platform } from "../../magma/app/Platform";
import { Iterable } from "../../magma/api/collect/list/Iterable";
import { CompileState } from "../../magma/app/compile/CompileState";
import { Result } from "../../magma/api/result/Result";
import { ImmutableCompileState } from "../../magma/app/compile/ImmutableCompileState";
import { Context } from "../../magma/app/compile/Context";
import { Files } from "../../jvm/api/io/Files";
import { Dependency } from "../../magma/app/compile/Dependency";
import { Err } from "../../magma/api/result/Err";
import { RootCompiler } from "../../magma/app/RootCompiler";
import { Ok } from "../../magma/api/result/Ok";
import { Import } from "../../magma/app/compile/Import";
import { Registry } from "../../magma/app/compile/Registry";
class Application {
	sources: Sources;
	targets: Targets;
	constructor (sources: Sources, targets: Targets) {
		this.sources = sources;
		this.targets = targets;
	}
	static formatSource(source: Source): string {
		return "\n\t" + source.createLocation().name() + ": " + Application.joinNamespace(source)/*unknown*/;
	}
	static joinNamespace(source: Source): string {
		return source.createLocation().namespace().iter().collect(new Joiner(".")).orElse("")/*unknown*/;
	}
	runWith(platform: Platform): Option<IOError> {
		return this.sources.listSources().flatMapValue((children: Iterable<Source>) => {
			return this.runWithChildren(platform, children)/*unknown*/;
		}).findError()/*unknown*/;
	}
	runWithChildren(platform: Platform, children: Iterable<Source>): Result<CompileState, IOError> {
		let state: CompileState = ImmutableCompileState.createEmpty().mapContext((context: Context) => {
			return context.withPlatform(platform)/*unknown*/;
		})/*unknown*/;
		let initial = children.iter().foldWithInitial(state, (current: CompileState, source: Source) => {
			return current.mapContext((context1: Context) => {
				return context1.addSource(source)/*unknown*/;
			})/*unknown*/;
		})/*unknown*/;
		let folded = children.iter().foldWithInitialToResult(initial, (state1: CompileState, source: Source) => {
			return this.runWithSource(state1, source)/*unknown*/;
		})/*unknown*/;
		if (!state/*unknown*/.context().hasPlatform(Platform.PlantUML) || !/*(folded instanceof Ok(var result))*//*unknown*/){
			return folded/*unknown*/;
		}
		let diagramPath = Files.get(".", "diagram.puml")/*unknown*/;
		let joinedDependencies = result.registry().iterDependencies().map((dependency: Dependency) => {
			return dependency.name() + " --> " + dependency.child() + "\n"/*unknown*/;
		}).collect(new Joiner("")).orElse("")/*unknown*/;
		let maybeError = diagramPath.writeString("@startuml\nskinparam linetype ortho\n" + result.registry().output() + joinedDependencies + "@enduml")/*unknown*/;
		if (!/*(maybeError instanceof Some(var error))*//*unknown*/){
			return folded/*unknown*/;
		}
		return new Err<CompileState, IOError>(error)/*unknown*/;
	}
	runWithSource(state: CompileState, source: Source): Result<CompileState, IOError> {
		return source.read().flatMapValue((input: string) => {
			return this.runWithInput(state, source, input)/*unknown*/;
		})/*unknown*/;
	}
	runWithInput(state1: CompileState, source: Source, input: string): Result<CompileState, IOError> {
		let location = source.createLocation()/*unknown*/;
		let compiled = RootCompiler.compileRoot(state1, input, location)/*unknown*/;
		let compiledState = compiled.left()/*unknown*/;
		if (compiledState.context().hasPlatform(Platform.PlantUML)/*unknown*/){
			return new Ok<CompileState, IOError>(compiledState)/*unknown*/;
		}
		let segment = state1.context().iterSources().map((source1: Source) => {
			return Application.formatSource(source1)/*unknown*/;
		}).collect(new Joiner(", ")).orElse("")/*unknown*/;
		let otherOutput = compiled.right()/*unknown*/;
		let joinedImports = compiledState.registry().queryImports().map((anImport: Import) => {
			return anImport.generate()/*unknown*/;
		}).collect(new Joiner("")).orElse("")/*unknown*/;
		let joined = joinedImports + compiledState.registry().output() + otherOutput/*unknown*/;
		let cleared = state1.mapRegistry((registry: Registry) => {
			return registry.reset()/*unknown*/;
		})/*unknown*/;
		return this.writeTarget(source, cleared, "/*[" + segment + "\n]*/\n" + joined)/*unknown*/;
	}
	writeTarget(source: Source, cleared: CompileState, output: string): Result<CompileState, IOError> {
		/*return this.targets().writeSource(source.createLocation(), output)
                .<Result<CompileState, IOError>>map((IOError error) -> {
                    return new Err<CompileState, IOError>(error);
                })
                .orElseGet(() -> {
                    return new Ok<CompileState, IOError>(cleared);
                })*/;
	}
}
