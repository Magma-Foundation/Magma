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
	Sequence: magma.api.collect.list, 
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
import { Registry } from "../../../magma/app/compile/Registry";
import { Import } from "../../../magma/app/compile/Import";
import { List } from "../../../magma/api/collect/list/List";
import { Dependency } from "../../../magma/app/compile/Dependency";
import { Lists } from "../../../jvm/api/collect/list/Lists";
import { Iter } from "../../../magma/api/collect/Iter";
export class ImmutableRegistry implements Registry {
	imports: List<Import>;
	output: string;
	dependencies: List<Dependency>;
	constructor (imports: List<Import>, output: string, dependencies: List<Dependency>) {
		this.imports = imports;
		this.output = output;
		this.dependencies = dependencies;
	}
	static createEmpty(): Registry {
		return new ImmutableRegistry(Lists.empty(), "", Lists.empty())/*unknown*/;
	}
	iterDependencies(): Iter<Dependency> {
		return this.dependencies().iter()/*unknown*/;
	}
	doesImportExistAlready(requestedChild: string): boolean {
		return this.imports().iter().filter((node: Import) => {
			return node.hasSameChild(requestedChild)/*unknown*/;
		}).next().isPresent()/*unknown*/;
	}
	queryImports(): Iter<Import> {
		return this.imports().iter()/*unknown*/;
	}
	addDependency(dependency: Dependency): Registry {
		return new ImmutableRegistry(this.imports(), this.output(), this.dependencies().addLast(dependency))/*unknown*/;
	}
	addImport(import_: Import): Registry {
		return new ImmutableRegistry(this.imports().addLast(import_), this.output(), this.dependencies())/*unknown*/;
	}
	append(element: string): Registry {
		return new ImmutableRegistry(this.imports(), this.output() + element, this.dependencies())/*unknown*/;
	}
	containsDependency(dependency: Dependency): boolean {
		return this.dependencies().contains(dependency)/*unknown*/;
	}
	reset(): Registry {
		return new ImmutableRegistry(Lists.empty(), "", this.dependencies())/*unknown*/;
	}
}
