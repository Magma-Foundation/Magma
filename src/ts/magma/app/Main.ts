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
	Platform: magma.app, 
	Sources: magma.app, 
	Targets: magma.app
]*/
import { Files } from "../../jvm/api/io/Files";
import { Sources } from "../../magma/app/Sources";
import { IOError } from "../../magma/api/io/IOError";
import { Console } from "../../magma/api/io/Console";
import { CompileState } from "../../magma/app/compile/CompileState";
import { Result } from "../../magma/api/result/Result";
import { Iters } from "../../magma/api/collect/Iters";
import { Platform } from "../../magma/app/Platform";
import { Source } from "../../magma/app/io/Source";
import { Iterable } from "../../magma/api/collect/list/Iterable";
import { Dependency } from "../../magma/app/compile/Dependency";
import { Joiner } from "../../magma/api/collect/Joiner";
import { Err } from "../../magma/api/result/Err";
import { Compiler } from "../../magma/app/Compiler";
import { Ok } from "../../magma/api/result/Ok";
import { Import } from "../../magma/app/compile/Import";
import { Tuple2Impl } from "../../magma/api/Tuple2Impl";
import { ImmutableCompileState } from "../../magma/app/compile/ImmutableCompileState";
import { ImmutableContext } from "../../magma/app/compile/ImmutableContext";
import { Location } from "../../magma/app/Location";
import { None } from "../../magma/api/option/None";
import { Lists } from "../../jvm/api/collect/list/Lists";
import { ImmutableRegistry } from "../../magma/app/compile/ImmutableRegistry";
import { ImmutableStack } from "../../magma/app/compile/ImmutableStack";
export class Main {
	static main(): void {
		let sourceDirectory = Files.get(".", "src", "java")/*unknown*/;
		let sources = new Sources(sourceDirectory)/*unknown*/;
		Main.runWithSourceDirectory(sources).findError().map((error: IOError) => error.display()/*unknown*/).ifPresent((displayed: string) => Console.printErrLn(displayed)/*unknown*/)/*unknown*/;
	}
	static runWithSourceDirectory(sources: Sources): Result<CompileState, IOError> {
		return Iters.fromArray(Platform.values()).foldWithInitialToResult(Main.createInitialState(), (state: CompileState, platform: Platform) => {
			return sources.listSources().flatMapValue((children: Iterable<Source>) => {
				return Main.runWithChildren(state.mapContext(context -  > context.withPlatform(platform)), children)/*unknown*/;
			})/*unknown*/;
		})/*unknown*/;
	}
	static runWithChildren(state: CompileState, sources: Iterable<Source>): Result<CompileState, IOError> {
		let initial = sources.iter().foldWithInitial(state, (current: CompileState, source: Source) => current.mapContext(context -  > context.addSource(source))/*unknown*/)/*unknown*/;
		let folded = sources.iter().foldWithInitialToResult(initial, Main.runWithSource)/*unknown*/;
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
	static runWithSource(state: CompileState, source: Source): Result<CompileState, IOError> {
		return source.read().flatMapValue((input: string) => Main.compileAndWrite(state, source, input)/*unknown*/)/*unknown*/;
	}
	static compileAndWrite(state: CompileState, source: Source, input: string): Result<CompileState, IOError> {
		let location = source.createLocation()/*unknown*/;
		let compiled = Compiler.compileRoot(state, input, location)/*unknown*/;
		let compiledState = compiled.left()/*unknown*/;
		if (compiledState.context().hasPlatform(Platform.PlantUML)/*unknown*/){
			return new Ok<CompileState, IOError>(compiledState)/*unknown*/;
		}
		let segment = state.context().iterSources().map((source1: Source) => Main.formatSource(source1)/*unknown*/).collect(new Joiner(", ")).orElse("")/*unknown*/;
		let otherOutput = compiled.right()/*unknown*/;
		let joinedImports = compiledState.registry().queryImports().map((anImport: Import) => anImport.generate()/*unknown*/).collect(new Joiner("")).orElse("")/*unknown*/;
		let joined = joinedImports + compiledState.registry().output() + otherOutput/*unknown*/;
		let output = new Tuple2Impl<CompileState, string>(state, "/*[" + segment + "\n]*/\n" + joined)/*unknown*/;
		let cleared = output.left().mapRegistry(registry -  > registry.clearImports()).mapRegistry(registry1 -  > registry1.clearOutput())/*unknown*/;
		return Main.writeTarget(source, cleared, output.right())/*unknown*/;
	}
	static writeTarget(source: Source, state: CompileState, output: string): Result<CompileState, IOError> {
		/*return new Targets(Files.get(".", "src", "ts"))
                .writeSource(source.createLocation(), output)
                .<Result<CompileState, IOError>>map((IOError error) -> new Err<CompileState, IOError>(error))
                .orElseGet(() -> new Ok<CompileState, IOError>(state))*/;
	}
	static formatSource(source: Source): string {
		return "\n\t" + source.createLocation().name() + ": " + Main.joinNamespace(source)/*unknown*/;
	}
	static joinNamespace(source: Source): string {
		return source.createLocation().namespace().iter().collect(new Joiner(".")).orElse("")/*unknown*/;
	}
	static createInitialState(): CompileState {
		return new ImmutableCompileState(new ImmutableContext(Platform.TypeScript, new None<Location>(), Lists.empty()), new ImmutableRegistry(Lists.empty(), Lists.empty(), ""), 0, new ImmutableStack(Lists.empty(), Lists.empty()))/*unknown*/;
	}
}
