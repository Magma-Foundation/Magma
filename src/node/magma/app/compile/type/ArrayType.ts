/*[
	Actual, 
	ArrayType, 
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
export class ArrayType implements Type {
	child: Type;
	constructor (child: Type) {
		this.child = child;
	}
	generate(): string {
		return this/*auto*/.child.generate(/*auto*/) + "[]";
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
