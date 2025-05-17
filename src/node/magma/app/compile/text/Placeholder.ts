/*[
	Actual, 
	Characters, 
	Collector, 
	CompileState, 
	Console, 
	Definition, 
	DivideState, 
	EmptyHead, 
	Err, 
	Files, 
	FlatMapHead, 
	FunctionHeader, 
	FunctionSegment, 
	Head, 
	HeadedQuery, 
	IOError, 
	ImmutableCompileState, 
	Import, 
	Joiner, 
	List, 
	ListCollector, 
	Lists, 
	MapHead, 
	Namespace, 
	None, 
	Ok, 
	Option, 
	Parameter, 
	Path, 
	Placeholder, 
	Queries, 
	Query, 
	RangeHead, 
	Result, 
	SingleHead, 
	Some, 
	Strings, 
	Tuple2, 
	Tuple2Impl, 
	Type, 
	ZipHead
]*/
import { Main } from "../../../../magma/app/Main";
import { Platform } from "../../../../magma/app/io/Platform";
import { Value } from "../../../../magma/app/compile/value/Value";
import { Option } from "../../../../magma/api/option/Option";
import { None } from "../../../../magma/api/option/None";
import { Definition } from "../../../../magma/app/compile/define/Definition";
import { Type } from "../../../../magma/api/Type";
import { PrimitiveType } from "../../../../magma/app/compile/type/PrimitiveType";
export class Placeholder {
	input: string;
	constructor (input: string) {
		this.input = input;
	}
	generate(): string {
		return Main/*auto*/.generatePlaceholder(this/*auto*/.input);
	}
	isFunctional(): boolean {
		return false/*auto*/;
	}
	generate(platform: Platform): string {
		return this/*auto*/.generate(/*auto*/);
	}
	findChild(): Option<Value> {
		return new None<Value>(/*auto*/);
	}
	asDefinition(): Option<Definition> {
		return new None<Definition>(/*auto*/);
	}
	toValue(): Option<Value> {
		return new None<Value>(/*auto*/);
	}
	isVar(): boolean {
		return false/*auto*/;
	}
	generateBeforeName(): string {
		return "";
	}
	generateAsEnumValue(structureName: string, platform: Platform): Option<string> {
		return new None<string>(/*auto*/);
	}
	type(): Type {
		return PrimitiveType/*auto*/.Auto;
	}
}
