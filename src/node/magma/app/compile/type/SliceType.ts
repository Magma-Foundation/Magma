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
	Tuple2, 
	Tuple2Impl, 
	Type, 
	Whitespace, 
	ZipHead
]*/
import { Type } from "../../../../magma/api/Type";
export class SliceType implements Type {
	type: Type;
	constructor (type: Type) {
		this.type = type;
	}
	generate(): string {
		return "&[" + this/*auto*/.type.generate(/*auto*/) + "]";
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
