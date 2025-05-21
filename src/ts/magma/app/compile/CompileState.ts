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
	PathSource: magma.app.io, 
	Source: magma.app.io, 
	Location: magma.app, 
	Main: magma.app, 
	Platform: magma.app, 
	Sources: magma.app, 
	Targets: magma.app
]*/
import { Import } from "../../../magma/app/compile/Import";
import { Iter } from "../../../magma/api/collect/Iter";
import { Dependency } from "../../../magma/app/compile/Dependency";
import { Source } from "../../../magma/app/io/Source";
import { Option } from "../../../magma/api/option/Option";
import { Location } from "../../../magma/app/Location";
import { Definition } from "../../../magma/app/compile/define/Definition";
import { List } from "../../../magma/api/collect/list/List";
import { Platform } from "../../../magma/app/Platform";
export interface CompileState {
	join(otherOutput: string): string;
	queryImports(): Iter<Import>;
	queryDependencies(): Iter<Dependency>;
	querySources(): Iter<Source>;
	createIndent(): string;
	findLastStructureName(): Option<string>;
	isLastWithin(name: string): boolean;
	addResolvedImport(location: Location): CompileState;
	withLocation(location: Location): CompileState;
	append(element: string): CompileState;
	pushStructureName(name: string): CompileState;
	enterDepth(): CompileState;
	exitDepth(): CompileState;
	defineAll(definitions: List<Definition>): CompileState;
	resolve(name: string): Option<Definition>;
	clearImports(): CompileState;
	clearOutput(): CompileState;
	addSource(source: Source): CompileState;
	findSource(name: string): Option<Source>;
	addResolvedImportFromCache(base: string): CompileState;
	popStructureName(): CompileState;
	withPlatform(platform: Platform): CompileState;
	hasPlatform(platform: Platform): boolean;
	findOutput(): string;
}
