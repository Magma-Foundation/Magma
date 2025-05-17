/*[
	Actual, 
	Characters, 
	Collector, 
	CompileState, 
	Console, 
	Definition, 
	EmptyHead, 
	Err, 
	Files, 
	FlatMapHead, 
	FunctionHeader, 
	Head, 
	HeadedQuery, 
	IOError, 
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
import { Platform } from "../../../../magma/app/io/Platform";
import { Definition } from "../../../../magma/app/compile/define/Definition";
import { Option } from "../../../../magma/api/option/Option";
export interface Parameter {
	generate(platform: Platform): string;
	asDefinition(): Option<Definition>;
}
