// [Lists, Console, Files, Characters, Strings, Actual, Namespace, Collector, EmptyHead, FlatMapHead, Head, HeadedQuery, MapHead, RangeHead, SingleHead, ZipHead, Joiner, List, ListCollector, Queries, Query, IOError, Path, None, Option, Some, Err, Ok, Result, Tuple2, Tuple2Impl, Type, CompileState, Definition, FunctionHeader, Parameter, FunctionSegment, ImmutableCompileState, Import, DivideState, Placeholder, Whitespace, ArrayType, BooleanType, FunctionType, PrimitiveType, SliceType, TemplateType, VariadicType, AccessNode, Argument, Caller, ConstructionCaller, ConstructorHeader, InvokableNode, LambdaNode, NotNode]
import { Value } from "../../../../magma/app/compile/value/Value";
import { Platform } from "../../../../magma/app/io/Platform";
import { Option } from "../../../../magma/api/option/Option";
import { Some } from "../../../../magma/api/option/Some";
import { None } from "../../../../magma/api/option/None";
import { Type } from "../../../../magma/api/Type";
import { PrimitiveType } from "../../../../magma/app/compile/type/PrimitiveType";
export class NotNode implements Value {
	child: string;
	constructor (child: string) {
		this.child = child;
	}
	generate(platform: Platform): string {
		return this/*auto*/.child;
	}
	toValue(): Option<Value> {
		return new Some<Value>(this/*auto*/);
	}
	findChild(): Option<Value> {
		return new None<Value>(/*auto*/);
	}
	generateAsEnumValue(structureName: string, platform: Platform): Option<string> {
		return new None<string>(/*auto*/);
	}
	type(): Type {
		return PrimitiveType/*auto*/.Auto;
	}
}
