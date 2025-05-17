/*[
	Actual, 
	ArrayType, 
	BooleanType, 
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
import { Type } from "../../../../magma/api/Type";
import { Platform } from "../../../../magma/app/io/Platform";
export class BooleanType implements Type {
	platform: Platform;
	constructor (platform: Platform) {
		this.platform = platform;
	}
	generate(): string {
		if (Platform/*auto*/.TypeScript === this/*auto*/.platform) {
			return "boolean";
		}
		return "Bool";
	}
	isFunctional(): boolean {
		return false/*auto*/;
	}
	isVar(): boolean {
		return false/*auto*/;
	}
	generateBeforeName(): string {
		return "";
	}
}
