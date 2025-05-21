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
	Source: magma.app.io, 
	Main: magma.app, 
	Platform: magma.app
]*/
import { List } from "../../../../magma/api/collect/list/List";
import { Type } from "../../../../magma/app/compile/type/Type";
import { Option } from "../../../../magma/api/option/Option";
import { Some } from "../../../../magma/api/option/Some";
import { Joiner } from "../../../../magma/api/collect/Joiner";
import { Main } from "../../../../magma/app/Main";
import { MethodHeader } from "../../../../magma/app/compile/define/MethodHeader";
import { Strings } from "../../../../magma/api/text/Strings";
export class Definition {
	annotations: List<string>;
	modifiers: List<string>;
	typeParams: List<string>;
	type: Type;
	name: string;
	constructor (annotations: List<string>, modifiers: List<string>, typeParams: List<string>, type: Type, name: string) {
		this.annotations = annotations;
		this.modifiers = modifiers;
		this.typeParams = typeParams;
		this.type = type;
		this.name = name;
	}
	findType(): Type {
		return this.type/*unknown*/;
	}
	toAssignment(): string {
		return "\n\t\tthis." + this.name + " = " + this.name + ";"/*unknown*/;
	}
	generate(): string {
		return this.generateWithAfterName("")/*unknown*/;
	}
	asDefinition(): Option<Definition> {
		return new Some<Definition>(this)/*unknown*/;
	}
	generateWithAfterName(afterName: string): string {
		let joinedTypeParams = this.joinTypeParams()/*unknown*/;
		let joinedModifiers = this.modifiers.query().map((value: string) => value + " "/*unknown*/).collect(new Joiner("")).orElse("")/*unknown*/;
		return joinedModifiers + this.type.generateBeforeName() + this.name + joinedTypeParams + afterName + this.generateType()/*unknown*/;
	}
	generateType(): string {
		if (this.type.isVar()/*unknown*/){
			return ""/*unknown*/;
		}
		return ": " + this.type.generate()/*unknown*/;
	}
	joinTypeParams(): string {
		return Main.joinTypeParams(this.typeParams)/*unknown*/;
	}
	hasAnnotation(annotation: string): boolean {
		return this.annotations.contains(annotation)/*unknown*/;
	}
	removeModifier(modifier: string): MethodHeader {
		return new Definition(this.annotations, this.modifiers.removeValue(modifier), this.typeParams, this.type, this.name)/*unknown*/;
	}
	isNamed(name: string): boolean {
		return Strings.equalsTo(this.name, name)/*unknown*/;
	}
}
