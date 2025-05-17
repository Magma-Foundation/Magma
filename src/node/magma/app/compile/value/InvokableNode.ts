// [Lists, Console, Files, Characters, Strings, Actual, Namespace, Collector, EmptyHead, FlatMapHead, Head, HeadedQuery, MapHead, RangeHead, SingleHead, ZipHead, Joiner, List, ListCollector, Queries, Query, IOError, Path, None, Option, Some, Err, Ok, Result, Tuple2, Tuple2Impl, Type, CompileState, Definition, FunctionHeader, Parameter, FunctionSegment, ImmutableCompileState, Import, DivideState, Placeholder, Whitespace, ArrayType, BooleanType, FunctionType, PrimitiveType, SliceType, TemplateType, VariadicType, AccessNode, Argument, Caller, ConstructionCaller, ConstructorHeader, InvokableNode]
import { Value } from "../../../../magma/app/compile/value/Value";
import { Caller } from "../../../../magma/app/compile/value/Caller";
import { List } from "../../../../magma/api/collect/list/List";
import { Platform } from "../../../../magma/app/io/Platform";
import { Joiner } from "../../../../magma/api/collect/Joiner";
import { Option } from "../../../../magma/api/option/Option";
import { Some } from "../../../../magma/api/option/Some";
import { None } from "../../../../magma/api/option/None";
import { Type } from "../../../../magma/api/Type";
import { PrimitiveType } from "../../../../magma/app/compile/type/PrimitiveType";
export class InvokableNode implements Value {
	caller: Caller;
	args: List<Value>;
	constructor (caller: Caller, args: List<Value>) {
		this.caller = caller;
		this.args = args;
	}
	generate(platform: Platform): string {
		let joinedArguments = this/*auto*/.joinArgs(platform/*Platform*/);
		return this/*auto*/.caller.generate(platform/*Platform*/) + "(" + joinedArguments/*auto*/ + ")";
	}
	joinArgs(platform: Platform): string {
		return this/*auto*/.args.query(/*auto*/).map((value: Value) => value/*string*/.generate(platform/*Platform*/)).collect(new Joiner(", ")).orElse("");
	}
	toValue(): Option<Value> {
		return new Some<Value>(this/*auto*/);
	}
	findChild(): Option<Value> {
		return new None<Value>(/*auto*/);
	}
	generateAsEnumValue(structureName: string, platform: Platform): Option<string> {
		return new Some<string>("\n\tstatic " + this/*auto*/.caller.generate(platform/*Platform*/) + ": " + structureName/*string*/ + " = new " + structureName/*string*/ + "(" + this/*auto*/.joinArgs(platform/*Platform*/) + ");");
	}
	type(): Type {
		return PrimitiveType/*auto*/.Auto;
	}
}
