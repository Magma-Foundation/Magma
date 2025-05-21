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
	Platform: magma.app, 
	Sources: magma.app, 
	Targets: magma.app
]*/
import { Path } from "../../magma/api/io/Path";
import { IOError } from "../../magma/api/io/IOError";
import { Option } from "../../magma/api/option/Option";
import { None } from "../../magma/api/option/None";
import { Location } from "../../magma/app/Location";
export class Targets {
	root: Path;
	constructor (root: Path) {
		this.root = root;
	}
	static writeTarget(target: Path, output: string): Option<IOError> {
		return Targets.ensureTargetParent(target).or(() => target.writeString(output)/*unknown*/)/*unknown*/;
	}
	static ensureTargetParent(target: Path): Option<IOError> {
		let parent = target.getParent()/*unknown*/;
		if (parent.exists()/*unknown*/){
			return new None<IOError>()/*unknown*/;
		}
		return parent.createDirectories()/*unknown*/;
	}
	writeSource(location: Location, output: string): Option<IOError> {
		let target = this.root.resolveChildSegments(location.namespace()).resolveChild(location.name() + ".ts")/*unknown*/;
		return Targets.writeTarget(target, output)/*unknown*/;
	}
}
