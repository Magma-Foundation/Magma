// [Lists, Console, Files, Characters, Strings, Actual, Namespace, Collector, EmptyHead, FlatMapHead, Head, HeadedQuery, MapHead, RangeHead, SingleHead, ZipHead, Joiner, List, ListCollector, Queries, Query, IOError, Path, None, Option, Some, Err, Ok, Result, Tuple2, Tuple2Impl, Type, CompileState, Definition, FunctionHeader, Parameter, FunctionSegment, ImmutableCompileState, Import, DivideState, Placeholder, Whitespace, ArrayType, BooleanType, FunctionType, PrimitiveType, SliceType, TemplateType, VariadicType, AccessNode, Argument, Caller, ConstructionCaller, ConstructorHeader, InvokableNode, LambdaNode, NotNode, OperationNode]
import { Value } from "../../../../magma/app/compile/value/Value";
import { Platform } from "../../../../magma/app/io/Platform";
import { Option } from "../../../../magma/api/option/Option";
import { Some } from "../../../../magma/api/option/Some";
import { None } from "../../../../magma/api/option/None";
import { Type } from "../../../../magma/api/Type";
import { PrimitiveType } from "../../../../magma/app/compile/type/PrimitiveType";
export class OperationNode implements Value {
	left: Value;
	targetInfix: string;
	right: Value;
	constructor (left: Value, targetInfix: string, right: Value) {
		this.left = left;
		this.targetInfix = targetInfix;
		this.right = right;
	}
	generate(platform: Platform): string {
		return this/*auto*/.left.generate(platform/*Platform*/) + " " + this/*auto*/.targetInfix + " " + this/*auto*/.right.generate(platform/*Platform*/);
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
