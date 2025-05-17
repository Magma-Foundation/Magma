// [Lists, Console, Files, Characters, Strings, Actual, Namespace, Collector, EmptyHead, FlatMapHead, Head, HeadedQuery, MapHead, RangeHead, SingleHead, ZipHead, Joiner, List, ListCollector, Queries, Query, IOError, Path, None, Option, Some, Err, Ok, Result, Tuple2, Tuple2Impl, Type, CompileState, Definition, FunctionHeader, Parameter, FunctionSegment, ImmutableCompileState, Import, DivideState, Placeholder, Whitespace, ArrayType, BooleanType, FunctionType, PrimitiveType, SliceType, TemplateType, VariadicType, AccessNode, Argument, Caller, ConstructionCaller]
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
