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
	ConstructionCaller: magma.app.compile.define, 
	ConstructorHeader: magma.app.compile.define, 
	Definition: magma.app.compile.define, 
	MethodHeader: magma.app.compile.define, 
	Parameter: magma.app.compile.define, 
	Dependency: magma.app.compile, 
	DivideState: magma.app.compile, 
	ImmutableCompileState: magma.app.compile, 
	ImmutableDivideState: magma.app.compile, 
	Import: magma.app.compile, 
	Placeholder: magma.app.compile.text, 
	Symbol: magma.app.compile.text, 
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
	StringValue: magma.app.compile.value, 
	Value: magma.app.compile.value, 
	Compiler: magma.app, 
	Source: magma.app.io, 
	Location: magma.app, 
	Main: magma.app, 
	Platform: magma.app
]*/
import { Files } from "../../jvm/api/io/Files";
import { IOError } from "../../magma/api/io/IOError";
import { Console } from "../../magma/api/io/Console";
import { CompileState } from "../../magma/app/compile/CompileState";
import { Result } from "../../magma/api/result/Result";
import { Path } from "../../magma/api/io/Path";
import { Iters } from "../../magma/api/collect/Iters";
import { Platform } from "../../magma/app/Platform";
import { List } from "../../magma/api/collect/list/List";
import { Source } from "../../magma/app/io/Source";
import { Dependency } from "../../magma/app/compile/Dependency";
import { Joiner } from "../../magma/api/collect/Joiner";
import { Err } from "../../magma/api/result/Err";
import { ListCollector } from "../../magma/api/collect/list/ListCollector";
import { Location } from "../../magma/app/Location";
import { Compiler } from "../../magma/app/Compiler";
import { Ok } from "../../magma/api/result/Ok";
import { Tuple2Impl } from "../../magma/api/Tuple2Impl";
import { Option } from "../../magma/api/option/Option";
import { None } from "../../magma/api/option/None";
import { ImmutableCompileState } from "../../magma/app/compile/ImmutableCompileState";
import { Lists } from "../../jvm/api/collect/list/Lists";
export class Main {
	static main(): void {
		let sourceDirectory = Files.get(".", "src", "java")/*unknown*/;
		Main.runWithSourceDirectory(sourceDirectory).findError().map((error: IOError) => error.display()/*unknown*/).ifPresent((displayed: string) => Console.printErrLn(displayed)/*unknown*/)/*unknown*/;
	}
	static runWithSourceDirectory(sourceDirectory: Path): Result<CompileState, IOError> {
		return Iters.fromArray(Platform.values()).foldWithInitialToResult(Main.createInitialState(), (state: CompileState, platform: Platform) => sourceDirectory.walk().flatMapValue((children: List<Path>) => Main.runWithChildren(state.withPlatform(platform), children, sourceDirectory)/*unknown*/)/*unknown*/)/*unknown*/;
	}
	static runWithChildren(state: CompileState, children: List<Path>, sourceDirectory: Path): Result<CompileState, IOError> {
		let initial = Main.retainSources(children, sourceDirectory).query().foldWithInitial(state, (current: CompileState, source: Source) => current.addSource(source)/*unknown*/)/*unknown*/;
		let folded = Main.retainSources(children, sourceDirectory).query().foldWithInitialToResult(initial, Main.runWithSource)/*unknown*/;
		if (/*state.hasPlatform(Platform.PlantUML) && folded instanceof Ok(var result)*/){
			let diagramPath = Files.get(".", "diagram.puml")/*unknown*/;
			let joinedDependencies = result.queryDependencies().map((dependency: Dependency) => dependency.name() + " --> " + dependency.child() + "\n"/*unknown*/).collect(new Joiner("")).orElse("")/*unknown*/;
			let maybeError = diagramPath.writeString("@startuml\nskinparam linetype ortho\n" + result.findOutput() + joinedDependencies + "@enduml")/*unknown*/;
			if (/*maybeError instanceof Some(var error)*/){
				return new Err<CompileState, IOError>(error)/*unknown*/;
			}
		}
		return folded/*unknown*/;
	}
	static retainSources(children: List<Path>, sourceDirectory: Path): List<Source> {
		return children.query().filter((source: Path) => source.endsWith(".java")/*unknown*/).map((child: Path) => new Source(sourceDirectory, child)/*unknown*/).collect(new ListCollector<Source>())/*unknown*/;
	}
	static runWithSource(state: CompileState, source: Source): Result<CompileState, IOError> {
		return source.read().flatMapValue((input: string) => Main.compileAndWrite(state, source, input)/*unknown*/)/*unknown*/;
	}
	static compileAndWrite(state: CompileState, source: Source, input: string): Result<CompileState, IOError> {
		let location = new Location(source.computeNamespace(), source.computeName())/*unknown*/;
		let compiled = Compiler.compileRoot(state, input, location)/*unknown*/;
		let compiledState = compiled.left()/*unknown*/;
		if (compiledState.hasPlatform(Platform.PlantUML)/*unknown*/){
			return new Ok<CompileState, IOError>(compiledState)/*unknown*/;
		}
		let segment = state.querySources().map((source1: Source) => Main.formatSource(source1)/*unknown*/).collect(new Joiner(", ")).orElse("")/*unknown*/;
		let joined = compiledState.join(compiled.right())/*unknown*/;
		let output = new Tuple2Impl<CompileState, string>(state, "/*[" + segment + "\n]*/\n" + joined)/*unknown*/;
		return Main.writeTarget(source, output.left().clearImports().clearOutput(), output.right())/*unknown*/;
	}
	static writeTarget(source: Source, state: CompileState, output: string): Result<CompileState, IOError> {
		let target = Files.get(".", "src", "ts").resolveChildSegments(source.computeNamespace()).resolveChild(source.computeName() + ".ts")/*unknown*/;
		return Main.writeTarget(target, output).orElseGet(() => new Ok<CompileState, IOError>(state)/*unknown*/)/*unknown*/;
	}
	static writeTarget(target: Path, output: string): Option<Result<CompileState, IOError>> {
		return Main.ensureTargetParent(target).or(() => target.writeString(output)/*unknown*/).map((error: IOError) => new Err<CompileState, IOError>(error)/*unknown*/)/*unknown*/;
	}
	static ensureTargetParent(target: Path): Option<IOError> {
		let parent = target.getParent()/*unknown*/;
		if (parent.exists()/*unknown*/){
			return new None<IOError>()/*unknown*/;
		}
		return parent.createDirectories()/*unknown*/;
	}
	static formatSource(source: Source): string {
		return "\n\t" + source.computeName() + ": " + Main.joinNamespace(source)/*unknown*/;
	}
	static joinNamespace(source: Source): string {
		return source.computeNamespace().query().collect(new Joiner(".")).orElse("")/*unknown*/;
	}
	static createInitialState(): CompileState {
		return new ImmutableCompileState(Lists.empty(), "", Lists.empty(), 0, Lists.empty(), new None<Location>(), Lists.empty(), Platform.TypeScript, Lists.empty())/*unknown*/;
	}
}
