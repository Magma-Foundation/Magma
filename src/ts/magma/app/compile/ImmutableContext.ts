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
import { Context } from "../../../magma/app/compile/Context";
import { Platform } from "../../../magma/app/Platform";
import { Option } from "../../../magma/api/option/Option";
import { Location } from "../../../magma/app/Location";
import { Source } from "../../../magma/app/io/Source";
import { List } from "../../../magma/api/collect/list/List";
import { None } from "../../../magma/api/option/None";
import { Lists } from "../../../jvm/api/collect/list/Lists";
import { Iter } from "../../../magma/api/collect/Iter";
import { Strings } from "../../../magma/api/text/Strings";
import { Some } from "../../../magma/api/option/Some";
export class ImmutableContext implements Context {
	maybePlatform: Option<Platform>;
	maybeLocation: Option<Location>;
	sources: List<Source>;
	constructor (maybePlatform: Option<Platform>, maybeLocation: Option<Location>, sources: List<Source>) {
		this.maybePlatform = maybePlatform;
		this.maybeLocation = maybeLocation;
		this.sources = sources;
	}
	static createEmpty(): Context {
		return new ImmutableContext(new None<Platform>(), new None<Location>(), Lists.empty())/*unknown*/;
	}
	iterSources(): Iter<Source> {
		return this.sources.iter()/*unknown*/;
	}
	hasPlatform(platform: Platform): boolean {
		return this.maybePlatform.filter((thisPlatform: Platform) => thisPlatform === platform/*unknown*/).isPresent()/*unknown*/;
	}
	findSource(name: string): Option<Source> {
		return this.iterSources().filter((source: Source) => Strings.equalsTo(source.createLocation().name(), name)/*unknown*/).next()/*unknown*/;
	}
	withLocation(location: Location): Context {
		return new ImmutableContext(this.maybePlatform, new Some<Location>(location), this.sources())/*unknown*/;
	}
	addSource(source: Source): Context {
		return new ImmutableContext(this.maybePlatform, this.maybeLocation(), this.sources().addLast(source))/*unknown*/;
	}
	findNamespaceOrEmpty(): List<string> {
		return this.maybeLocation().map(Location.namespace).orElse(Lists.empty())/*unknown*/;
	}
	findNameOrEmpty(): string {
		return this.maybeLocation().map(Location.name).orElse("")/*unknown*/;
	}
	withPlatform(platform: Platform): Context {
		return new ImmutableContext(new Some<Platform>(platform), this.maybeLocation(), this.sources())/*unknown*/;
	}
}
