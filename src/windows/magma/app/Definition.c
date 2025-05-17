/*[
	JVMList: jvm.api.collect.list, 
	Lists: jvm.api.collect.list, 
	Console: jvm.api.io, 
	Files: jvm.api.io, 
	JVMPath: jvm.api.io, 
	Characters: jvm.api.text, 
	Strings: jvm.api.text, 
	Actual: magma.annotate, 
	Namespace: magma.annotate, 
	Collector: magma.api.collect, 
	EmptyHead: magma.api.collect.head, 
	FlatMapHead: magma.api.collect.head, 
	Head: magma.api.collect.head, 
	HeadedQuery: magma.api.collect.head, 
	MapHead: magma.api.collect.head, 
	RangeHead: magma.api.collect.head, 
	SingleHead: magma.api.collect.head, 
	ZipHead: magma.api.collect.head, 
	Joiner: magma.api.collect, 
	List: magma.api.collect.list, 
	ListCollector: magma.api.collect.list, 
	Queries: magma.api.collect, 
	Query: magma.api.collect, 
	IOError: magma.api.io, 
	Path: magma.api.io, 
	None: magma.api.option, 
	Option: magma.api.option, 
	Some: magma.api.option, 
	Err: magma.api.result, 
	Ok: magma.api.result, 
	Result: magma.api.result, 
	Tuple2: magma.api, 
	Tuple2Impl: magma.api, 
	Definition: magma.app, 
	Main: magma.app, 
	MethodHeader: magma.app, 
	Parameter: magma.app
]*/
import { List } from "magma/api/collect/list/List";
import { Option } from "magma/api/option/Option";
import { Some } from "magma/api/option/Some";
import { Joiner } from "magma/api/collect/Joiner";
import { Strings } from "jvm/api/text/Strings";
export class Definition {
	mut annotations: List<&[I8]>;
	mut modifiers: List<&[I8]>;
	mut typeParams: List<&[I8]>;
	mut name: &[I8];
	constructor (mut annotations: List<&[I8]>, mut modifiers: List<&[I8]>, mut typeParams: List<&[I8]>, mut name: &[I8]) {
		this.annotations = annotations;
		this.modifiers = modifiers;
		this.typeParams = typeParams;
		this.name = name;
	}
	mut generate(): &[I8] {
		return this.generateWithAfterName("");
	}
	mut asDefinition(): Option<Definition> {
		return new Some<Definition>(this);
	}
	mut generateWithAfterName(afterName: &[I8]): &[I8] {
		let joinedTypeParams = this.joinTypeParams();
		let joinedModifiers = this.modifiers.query().map((mut value: &[I8]) => value + " ").collect(new Joiner("")).orElse("");
		return joinedModifiers + this.type.generateBeforeName() + this.name + joinedTypeParams + afterName + this.generateType();
	}
	mut generateType(): &[I8] {
		if (this.type.isVar()) {
			return "";
		}
		return ": " + this.type.generate();
	}
	mut joinTypeParams(): &[I8] {
		return Joiner.joinOrEmpty(this.typeParams, ", ", "<", ">");
	}
	mut hasAnnotation(annotation: &[I8]): Bool {
		return this.annotations.contains(annotation, Strings.equalsTo);
	}
	mut removeModifier(modifier: &[I8]): Definition {
		return new Definition(this.annotations, this.modifiers.removeValue(modifier, Strings.equalsTo), this.typeParams, this.type, this.name);
	}
	mut addModifier(modifier: &[I8]): Definition {
		return new Definition(this.annotations, this.modifiers.addFirst(modifier), this.typeParams, this.type, this.name);
	}
}
