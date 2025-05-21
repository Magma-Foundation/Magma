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
	Compiler: magma.app, 
	PathSource: magma.app.io, 
	Source: magma.app.io, 
	Location: magma.app, 
	Main: magma.app, 
	PathSources: magma.app, 
	PathTargets: magma.app, 
	Platform: magma.app, 
	Sources: magma.app, 
	Targets: magma.app
]*/
import { Sources } from "../../magma/app/Sources";
import { Targets } from "../../magma/app/Targets";
import { CompileState } from "../../magma/app/compile/CompileState";
import { IOError } from "../../magma/api/io/IOError";
import { Result } from "../../magma/api/result/Result";
import { Iters } from "../../magma/api/collect/Iters";
import { Platform } from "../../magma/app/Platform";
import { ImmutableCompileState } from "../../magma/app/compile/ImmutableCompileState";
import { Source } from "../../magma/app/io/Source";
import { Iterable } from "../../magma/api/collect/list/Iterable";
import { Context } from "../../magma/app/compile/Context";
import { Files } from "../../jvm/api/io/Files";
import { Dependency } from "../../magma/app/compile/Dependency";
import { Joiner } from "../../magma/api/collect/Joiner";
import { Err } from "../../magma/api/result/Err";
import { Compiler } from "../../magma/app/Compiler";
import { Ok } from "../../magma/api/result/Ok";
import { Import } from "../../magma/app/compile/Import";
import { Tuple2Impl } from "../../magma/api/Tuple2Impl";
export class Application {
	sources: Sources;
	targets: Targets;
	constructor (sources: Sources, targets: Targets) {
		this.sources = sources;
		this.targets = targets;
	}
	static run(application: Application): Result<CompileState, IOError> {
		return Iters.fromArray(Platform.values()).foldWithInitialToResult(ImmutableCompileState.createEmpty(), (state: CompileState, platform: Platform) => {
			return application.sources().listSources().flatMapValue((children: Iterable<Source>) => {
				return Application.runWithChildren(state.mapContext((context: Context) => context.withPlatform(platform)/*unknown*/), children, application.targets())/*unknown*/;
			})/*unknown*/;
		})/*unknown*/;
	}
	static runWithChildren(state: CompileState, sources: Iterable<Source>, targets: Targets): Result<CompileState, IOError> {
		let initial = sources.iter().foldWithInitial(state, (current: CompileState, source: Source) => current.mapContext(context -  > context.addSource(source))/*unknown*/)/*unknown*/;
		let folded = sources.iter().foldWithInitialToResult(initial, (state1, source) -  > Application.runWithSource(state1, source, targets))/*unknown*/;
		if (/*state.context().hasPlatform(Platform.PlantUML) && folded instanceof Ok(var result)*/){
			let diagramPath = Files.get(".", "diagram.puml")/*unknown*/;
			let joinedDependencies = result.registry().iterDependencies().map((dependency: Dependency) => dependency.name() + " --> " + dependency.child() + "\n"/*unknown*/).collect(new Joiner("")).orElse("")/*unknown*/;
			let maybeError = diagramPath.writeString("@startuml\nskinparam linetype ortho\n" + result.registry().output() + joinedDependencies + "@enduml")/*unknown*/;
			if (/*maybeError instanceof Some(var error)*/){
				return new Err<CompileState, IOError>(error)/*unknown*/;
			}
		}
		return folded/*unknown*/;
	}
	static runWithSource(state: CompileState, source: Source, targets: Targets): Result<CompileState, IOError> {
		return source.read().flatMapValue((input: string) => Application.compileAndWrite(state, source, input, targets)/*unknown*/)/*unknown*/;
	}
	static compileAndWrite(state: CompileState, source: Source, input: string, targets: Targets): Result<CompileState, IOError> {
		let location = source.createLocation()/*unknown*/;
		let compiled = Compiler.compileRoot(state, input, location)/*unknown*/;
		let compiledState = compiled.left()/*unknown*/;
		if (compiledState.context().hasPlatform(Platform.PlantUML)/*unknown*/){
			return new Ok<CompileState, IOError>(compiledState)/*unknown*/;
		}
		let segment = state.context().iterSources().map((source1: Source) => Application.formatSource(source1)/*unknown*/).collect(new Joiner(", ")).orElse("")/*unknown*/;
		let otherOutput = compiled.right()/*unknown*/;
		let joinedImports = compiledState.registry().queryImports().map((anImport: Import) => anImport.generate()/*unknown*/).collect(new Joiner("")).orElse("")/*unknown*/;
		let joined = joinedImports + compiledState.registry().output() + otherOutput/*unknown*/;
		let output = new Tuple2Impl<CompileState, string>(state, "/*[" + segment + "\n]*/\n" + joined)/*unknown*/;
		let cleared = output.left().mapRegistry(registry -  > registry.clearImports()).mapRegistry(registry1 -  > registry1.clearOutput())/*unknown*/;
		return Application.writeTarget(source, cleared, output.right(), targets)/*unknown*/;
	}
	static writeTarget(source: Source, state: CompileState, output: string, targets: Targets): Result<CompileState, IOError> {
		/*return targets.writeSource(source.createLocation(), output)
                .<Result<CompileState, IOError>>map((IOError error) -> new Err<CompileState, IOError>(error))
                .orElseGet(() -> new Ok<CompileState, IOError>(state))*/;
	}
	static formatSource(source: Source): string {
		return "\n\t" + source.createLocation().name() + ": " + Application.joinNamespace(source)/*unknown*/;
	}
	static joinNamespace(source: Source): string {
		return source.createLocation().namespace().iter().collect(new Joiner(".")).orElse("")/*unknown*/;
	}
}
