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
	Some, 
	Strings, 
	Tuple2, 
	Tuple2Impl, 
	Type, 
	Whitespace, 
	ZipHead
]*/
import { Type } from "../../../../magma/api/Type";
export class PrimitiveType implements Type {
	static String/*auto*/: PrimitiveType = new PrimitiveType("string");
	static Number/*auto*/: PrimitiveType = new PrimitiveType("number");
	static Var/*auto*/: PrimitiveType = new PrimitiveType("var");
	static Void/*auto*/: PrimitiveType = new PrimitiveType("void");
	static Auto/*auto*/: PrimitiveType = new PrimitiveType("auto");
	static I8/*auto*/: PrimitiveType = new PrimitiveType("I8");
	static I32/*auto*/: PrimitiveType = new PrimitiveType("I32");
	value: string;
	constructor (value: string) {
		this/*auto*/.value = value/*string*/;
	}
	generate(): string {
		return this/*auto*/.value;
	}
	isFunctional(): boolean {
		return false/*auto*/;
	}
	isVar(): boolean {
		return PrimitiveType/*auto*/.Var === this/*auto*/;
	}
	generateBeforeName(): string {
		return "";
	}
}
