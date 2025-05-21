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
import { Definition } from "../../../magma/app/compile/define/Definition";
import { Iterable } from "../../../magma/api/collect/list/Iterable";
import { Source } from "../../../magma/app/io/Source";
import { Platform } from "../../../magma/app/Platform";
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
	mapRegistry(mapper: (arg0 : Registry) => Registry): CompileState {
		return this.createWithRegistry(mapper(this.registry()))/*unknown*/;
	}
	createWithRegistry(registry: Registry): CompileState {
		return new ImmutableCompileState(this.context, registry, this.depth, this.stack)/*unknown*/;
	}
	withLocation(location: Location): CompileState {
		return this.createWithContext(this.context.withLocation(location))/*unknown*/;
	}
	createWithContext(context: Context): CompileState {
		return new ImmutableCompileState(context, this.registry, this.depth, this.stack)/*unknown*/;
	}
	append(element: string): CompileState {
		return this.createWithRegistry(this.registry().append(element))/*unknown*/;
	}
	pushStructureName(name: string): CompileState {
		return this.createWithStack(this.stack().pushStructureName(name))/*unknown*/;
	}
	enterDepth(): CompileState {
		return new ImmutableCompileState(this.context, this.registry, this.depth + 1, this.stack)/*unknown*/;
	}
	exitDepth(): CompileState {
		return new ImmutableCompileState(this.context, this.registry, this.depth - 1, this.stack)/*unknown*/;
	}
	defineAll(definitions: Iterable<Definition>): CompileState {
		return this.createWithStack(this.stack().defineAll(definitions))/*unknown*/;
	}
	clearImports(): CompileState {
		return this.createWithRegistry(this.registry().clearImports())/*unknown*/;
	}
	clearOutput(): CompileState {
		return this.createWithRegistry(new Registry(this.registry().imports(), this.registry().dependencies(), ""))/*unknown*/;
	}
	addSource(source: Source): CompileState {
		return this.createWithContext(this.context().addSource(source))/*unknown*/;
	}
	popStructureName(): CompileState {
		return this.createWithStack(this.stack().popStructureName())/*unknown*/;
	}
	createWithStack(stack: Stack): CompileState {
		return new ImmutableCompileState(this.context, this.registry, this.depth, stack)/*unknown*/;
	}
	withPlatform(platform: Platform): CompileState {
		return new ImmutableCompileState(new Context(platform, this.context().maybeLocation(), this.context().sources()), this.registry, this.depth, this.stack)/*unknown*/;
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
