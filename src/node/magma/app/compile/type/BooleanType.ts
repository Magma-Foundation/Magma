// [Lists, Console, Files, Characters, Strings, Actual, Namespace, Collector, EmptyHead, FlatMapHead, Head, HeadedQuery, MapHead, RangeHead, SingleHead, ZipHead, Joiner, List, ListCollector, Queries, Query, IOError, Path, None, Option, Some, Err, Ok, Result, Tuple2, Tuple2Impl, Type, CompileState, Definition, FunctionHeader, Parameter, FunctionSegment, ImmutableCompileState, Import, DivideState, Placeholder, Whitespace, ArrayType, BooleanType]
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
