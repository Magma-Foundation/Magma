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
import { Stack } from "../../../magma/app/compile/Stack";
import { List } from "../../../magma/api/collect/list/List";
import { Definition } from "../../../magma/app/compile/define/Definition";
import { Lists } from "../../../jvm/api/collect/list/Lists";
import { Option } from "../../../magma/api/option/Option";
import { Strings } from "../../../magma/api/text/Strings";
import { Iterable } from "../../../magma/api/collect/list/Iterable";
export class ImmutableStack implements Stack {
	structureNames: List<string>;
	definitions: List<Definition>;
	constructor (structureNames: List<string>, definitions: List<Definition>) {
		this.structureNames = structureNames;
		this.definitions = definitions;
	}
	static createEmpty(): Stack {
		return new ImmutableStack(Lists.empty(), Lists.empty())/*unknown*/;
	}
	findLastStructureName(): Option<string> {
		return this.structureNames().findLast()/*unknown*/;
	}
	isWithinLast(name: string): boolean {
		return this.findLastStructureName().filter((anObject: string) => Strings.equalsTo(name, anObject)/*unknown*/).isPresent()/*unknown*/;
	}
	hasAnyStructureName(base: string): boolean {
		return this.structureNames().iter().anyMatch((inner: string) => Strings.equalsTo(inner, base)/*unknown*/)/*unknown*/;
	}
	resolveValue(name: string): Option<Definition> {
		return this.definitions().iterReversed().filter((definition: Definition) => definition.isNamed(name)/*unknown*/).next()/*unknown*/;
	}
	pushStructureName(name: string): Stack {
		return new ImmutableStack(this.structureNames().addLast(name), this.definitions())/*unknown*/;
	}
	defineAll(definitions: Iterable<Definition>): Stack {
		return new ImmutableStack(this.structureNames(), this.definitions().addAll(definitions))/*unknown*/;
	}
	popStructureName(): Stack {
		return new ImmutableStack(this.structureNames().removeLast().orElse(this.structureNames()), this.definitions())/*unknown*/;
	}
}
