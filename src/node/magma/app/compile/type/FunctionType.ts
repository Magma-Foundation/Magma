// [Lists, Lists, Lists, Console, Console, Console, Files, Files, Files, Characters, Characters, Characters, Strings, Strings, Strings, Actual, Actual, Actual, Namespace, Namespace, Namespace, Collector, Collector, Collector, EmptyHead, EmptyHead, EmptyHead, FlatMapHead, FlatMapHead, FlatMapHead, Head, Head, Head, HeadedQuery, HeadedQuery, HeadedQuery, MapHead, MapHead, MapHead, RangeHead, RangeHead, RangeHead, SingleHead, SingleHead, SingleHead, ZipHead, ZipHead, ZipHead, Joiner, Joiner, Joiner, List, List, List, ListCollector, ListCollector, ListCollector, Queries, Queries, Queries, Query, Query, Query, IOError, IOError, IOError, Path, Path, Path, None, None, None, Option, Option, Option, Some, Some, Some, Err, Err, Err, Ok, Ok, Ok, Result, Result, Result, Tuple2, Tuple2, Tuple2, Tuple2Impl, Tuple2Impl, Tuple2Impl, Type, Type, Type, CompileState, CompileState, CompileState, Definition, Definition, Definition, FunctionHeader, FunctionHeader, FunctionHeader, Parameter, Parameter, Parameter, FunctionSegment, FunctionSegment, FunctionSegment, ImmutableCompileState, ImmutableCompileState, ImmutableCompileState, Import, Import, Import, DivideState, DivideState, DivideState, Placeholder, Placeholder, Placeholder, Whitespace, Whitespace, Whitespace, ArrayType, ArrayType, ArrayType, BooleanType, BooleanType, BooleanType, FunctionType]
import { Type } from "../../../../magma/api/Type";
import { List } from "../../../../magma/api/collect/list/List";
import { Tuple2 } from "../../../../magma/api/Tuple2";
import { Joiner } from "../../../../magma/api/collect/Joiner";
export class FunctionType implements Type {
	args: List<string>;
	returns: string;
	constructor (args: List<string>, returns: string) {
		this.args = args;
		this.returns = returns;
	}
	generate(): string {
		let joinedArguments = this/*auto*/.args.queryWithIndices(/*auto*/).map((tuple: Tuple2<number, string>) => "arg" + tuple/*auto*/.left(/*auto*/) + " : " + tuple/*auto*/.right(/*auto*/)).collect(new Joiner(", ")).orElse("");
		return "(" + joinedArguments/*auto*/ + ") => " + this/*auto*/.returns;
	}
	isFunctional(): boolean {
		return true/*auto*/;
	}
	isVar(): boolean {
		return false/*auto*/;
	}
	generateBeforeName(): string {
		return "";
	}
}
