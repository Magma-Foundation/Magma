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
	ImmutableDivideState: magma.app.compile, 
	Import: magma.app.compile, 
	Registry: magma.app.compile, 
	Stack: magma.app.compile, 
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
	PathSource: magma.app.io, 
	Source: magma.app.io, 
	Location: magma.app, 
	Main: magma.app, 
	Platform: magma.app, 
	Sources: magma.app, 
	Targets: magma.app
]*/
import { CompileState } from "../../../magma/app/compile/CompileState";
import { Context } from "../../../magma/app/compile/Context";
import { Registry } from "../../../magma/app/compile/Registry";
import { Stack } from "../../../magma/app/compile/Stack";
import { Location } from "../../../magma/app/Location";
import { Platform } from "../../../magma/app/Platform";
import { Dependency } from "../../../magma/app/compile/Dependency";
import { Lists } from "../../../jvm/api/collect/list/Lists";
import { Import } from "../../../magma/app/compile/Import";
import { Some } from "../../../magma/api/option/Some";
import { Definition } from "../../../magma/app/compile/define/Definition";
import { Iterable } from "../../../magma/api/collect/list/Iterable";
import { Source } from "../../../magma/app/io/Source";
export class ImmutableCompileState implements CompileState {
	context: Context;
	registry: Registry;
	depth: number;
	stack: Stack;
	constructor (context: Context, registry: Registry, depth: number, stack: Stack) {
		this.context/*unknown*/ = context/*Context*/;
		this.registry/*unknown*/ = registry/*Registry*/;
		this.depth/*unknown*/ = depth/*number*/;
		this.stack/*unknown*/ = stack/*Stack*/;
	}
	createIndent(): string {
		return "\n" + "\t".repeat(this.depth)/*unknown*/;
	}
	addResolvedImport(location: Location): CompileState {
		if (Platform.PlantUML === this.context().platform()/*unknown*/){
			let name = this.context().maybeLocation().map(Location.name).orElse("")/*unknown*/;
			let dependency = new Dependency(name, location.name())/*unknown*/;
			if (!this/*unknown*/.registry().dependencies().contains(dependency)/*unknown*/){
				return new ImmutableCompileState(this.context, new Registry(this.registry().imports(), this.registry().dependencies().addLast(dependency), this.registry().output()), this.depth, this.stack)/*unknown*/;
			}
		}
		let requestedNamespace = location.namespace()/*unknown*/;
		let requestedChild = location.name()/*unknown*/;
		let thisNamespace = this.context().maybeLocation().map(Location.namespace).orElse(Lists.empty())/*unknown*/;
		if (thisNamespace.isEmpty()/*unknown*/){
			requestedNamespace/*unknown*/ = requestedNamespace.addFirst(".")/*unknown*/;
		}
		let i = 0/*unknown*/;
		let size = thisNamespace.size()/*unknown*/;
		while (i < size/*unknown*/){
			requestedNamespace/*unknown*/ = requestedNamespace.addFirst("..")/*unknown*/;
			i/*unknown*/++;
		}
		if (this.registry().doesImportExistAlready(requestedChild)/*unknown*/){
			return this/*unknown*/;
		}
		let newNamespace = requestedNamespace.addLast(requestedChild)/*unknown*/;
		let importString = new Import(newNamespace, requestedChild)/*unknown*/;
		return new ImmutableCompileState(this.context, new Registry(this.registry().imports().addLast(importString), this.registry().dependencies(), this.registry().output()), this.depth, this.stack)/*unknown*/;
	}
	withLocation(location: Location): CompileState {
		return new ImmutableCompileState(new Context(this.context().platform(), new Some<Location>(location), this.context().sources()), this.registry, this.depth, this.stack)/*unknown*/;
	}
	append(element: string): CompileState {
		return new ImmutableCompileState(this.context, new Registry(this.registry().imports(), this.registry().dependencies(), this.registry().output() + element), this.depth, this.stack)/*unknown*/;
	}
	pushStructureName(name: string): CompileState {
		return new ImmutableCompileState(this.context, this.registry, this.depth, new Stack(this.stack().structureNames().addLast(name), this.stack().definitions()))/*unknown*/;
	}
	enterDepth(): CompileState {
		return new ImmutableCompileState(this.context, this.registry, this.depth + 1, this.stack)/*unknown*/;
	}
	exitDepth(): CompileState {
		return new ImmutableCompileState(this.context, this.registry, this.depth - 1, this.stack)/*unknown*/;
	}
	defineAll(definitions: Iterable<Definition>): CompileState {
		return new ImmutableCompileState(this.context, this.registry, this.depth, new Stack(this.stack().structureNames(), this.stack().definitions().addAll(definitions)))/*unknown*/;
	}
	clearImports(): CompileState {
		return new ImmutableCompileState(this.context, new Registry(Lists.empty(), this.registry().dependencies(), this.registry().output()), this.depth, this.stack)/*unknown*/;
	}
	clearOutput(): CompileState {
		return new ImmutableCompileState(this.context, new Registry(this.registry().imports(), this.registry().dependencies(), ""), this.depth, this.stack)/*unknown*/;
	}
	addSource(source: Source): CompileState {
		return new ImmutableCompileState(new Context(this.context().platform(), this.context().maybeLocation(), this.context().sources().addLast(source)), this.registry, this.depth, this.stack)/*unknown*/;
	}
	addResolvedImportFromCache(base: string): CompileState {
		if (this.stack().hasAnyStructureName(base)/*unknown*/){
			return this/*unknown*/;
		}
		return this.context().findSource(base).map((source: Source) => this.addResolvedImport(source.createLocation())/*unknown*/).orElse(this)/*unknown*/;
	}
	popStructureName(): CompileState {
		return new ImmutableCompileState(this.context, this.registry, this.depth, new Stack(this.stack().structureNames().removeLast().orElse(this.stack().structureNames()), this.stack().definitions()))/*unknown*/;
	}
	withPlatform(platform: Platform): CompileState {
		return new ImmutableCompileState(new Context(platform, this.context().maybeLocation(), this.context().sources()), this.registry, this.depth, new Stack(this.stack().structureNames().removeLast().orElse(this.stack().structureNames()), this.stack().definitions()))/*unknown*/;
	}
	context(): Context {
		return this.context/*unknown*/;
	}
	registry(): Registry {
		return this.registry/*unknown*/;
	}
	stack(): Stack {
		return this.stack/*unknown*/;
	}
}
