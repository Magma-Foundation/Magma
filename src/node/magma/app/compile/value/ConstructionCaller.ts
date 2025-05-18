/*[
	AccessNode, 
	Actual, 
	Argument, 
	ArrayType, 
	BooleanType, 
	Caller, 
	Characters, 
	Collector, 
	CompileState, 
	Console, 
	ConstructionCaller, 
	Definition, 
	DivideState, 
	EmptyHead, 
	Err, 
	Files, 
	FlatMapHead, 
	FunctionHeader, 
	FunctionSegment, 
	FunctionType, 
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
	PrimitiveType, 
	Queries, 
	Query, 
	RangeHead, 
	Result, 
	SingleHead, 
	SliceType, 
	Some, 
	Strings, 
	TemplateType, 
	Tuple2, 
	Tuple2Impl, 
	Type, 
	VariadicType, 
	Whitespace, 
	ZipHead
]*/
import { Caller } from "../../../../magma/app/compile/value/Caller";
import { Platform } from "../../../../magma/app/io/Platform";
import { Value } from "../../../../magma/app/compile/value/Value";
import { Option } from "../../../../magma/api/option/Option";
import { None } from "../../../../magma/api/option/None";
export class ConstructionCaller implements Caller {
	right: string;
	platform: Platform;
	constructor (right: string, platform: Platform) {
		this.right = right;
		this.platform = platform;
	}
	generate(platform: Platform): string {
		if (Platform/*auto*/.Magma === this/*auto*/.platform) {
			return this/*auto*/.right;
		}
		return "new " + this/*auto*/.right;
	}
	findChild(): Option<Value> {
		return new None<Value>(/*auto*/);
	}
}
