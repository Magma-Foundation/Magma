// [Lists, Console, Files, Characters, Strings, Actual, Namespace, Collector, EmptyHead, FlatMapHead, Head, HeadedQuery, MapHead, RangeHead, SingleHead, ZipHead, Joiner, List, ListCollector, Queries, Query, IOError, Path, None, Option, Some, Err, Ok, Result, Tuple2, Tuple2Impl, Type, CompileState, Definition, FunctionHeader, Parameter, FunctionSegment, ImmutableCompileState, Import, DivideState, Placeholder, Whitespace, ArrayType, BooleanType, FunctionType, PrimitiveType]
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
