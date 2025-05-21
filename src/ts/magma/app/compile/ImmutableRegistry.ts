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
import { Registry } from "../../../magma/app/compile/Registry";
import { Import } from "../../../magma/app/compile/Import";
import { List } from "../../../magma/api/collect/list/List";
import { Dependency } from "../../../magma/app/compile/Dependency";
import { Iter } from "../../../magma/api/collect/Iter";
import { Lists } from "../../../jvm/api/collect/list/Lists";
export class ImmutableRegistry implements Registry {
	imports: List<Import>;
	dependencies: List<Dependency>;
	output: string;
	constructor (imports: List<Import>, dependencies: List<Dependency>, output: string) {
		this.imports = imports;
		this.dependencies = dependencies;
		this.output = output;
	}
	iterDependencies(): Iter<Dependency> {
		return this.dependencies().iter()/*unknown*/;
	}
	doesImportExistAlready(requestedChild: string): boolean {
		return this.imports().iter().filter((node: Import) => node.hasSameChild(requestedChild)/*unknown*/).next().isPresent()/*unknown*/;
	}
	queryImports(): Iter<Import> {
		return this.imports().iter()/*unknown*/;
	}
	addDependency(dependency: Dependency): Registry {
		return new ImmutableRegistry(this.imports(), this.dependencies().addLast(dependency), this.output())/*unknown*/;
	}
	addImport(import_: Import): Registry {
		return new ImmutableRegistry(this.imports().addLast(import_), this.dependencies(), this.output())/*unknown*/;
	}
	append(element: string): Registry {
		return new ImmutableRegistry(this.imports(), this.dependencies(), this.output() + element)/*unknown*/;
	}
	clearImports(): Registry {
		return new ImmutableRegistry(Lists.empty(), this.dependencies(), this.output())/*unknown*/;
	}
	containsDependency(dependency: Dependency): boolean {
		return this.dependencies().contains(dependency)/*unknown*/;
	}
	clearOutput(): Registry {
		return new ImmutableRegistry(this.imports, this.dependencies, "")/*unknown*/;
	}
}
