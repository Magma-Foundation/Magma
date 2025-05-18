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
	Whitespace, 
	ZipHead
]*/
import { Parameter } from "../../../../magma/app/compile/define/Parameter";
import { Platform } from "../../../../magma/app/io/Platform";
import { Definition } from "../../../../magma/app/compile/define/Definition";
import { Option } from "../../../../magma/api/option/Option";
import { None } from "../../../../magma/api/option/None";
export class Whitespace implements Parameter {
	generate(platform: Platform): string {
		return "";
	}
	asDefinition(): Option<Definition> {
		return new None<Definition>(/*auto*/);
	}
}
