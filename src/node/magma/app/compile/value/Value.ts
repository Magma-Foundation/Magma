// [Lists, Console, Files, Characters, Strings, Actual, Namespace, Collector, EmptyHead, FlatMapHead, Head, HeadedQuery, MapHead, RangeHead, SingleHead, ZipHead, Joiner, List, ListCollector, Queries, Query, IOError, Path, None, Option, Some, Err, Ok, Result, Tuple2, Tuple2Impl, Type, CompileState, Definition, FunctionHeader, Parameter, FunctionSegment, ImmutableCompileState, Import, DivideState, Placeholder, Whitespace, ArrayType, BooleanType, FunctionType, PrimitiveType, SliceType, TemplateType, VariadicType, AccessNode, Argument, Caller, ConstructionCaller, ConstructorHeader, InvokableNode, LambdaNode, NotNode, OperationNode, StringNode, SymbolNode, Value]
import { Option } from "../../../../magma/api/option/Option";
import { Platform } from "../../../../magma/app/io/Platform";
import { Type } from "../../../../magma/api/Type";
export interface Value extends Argument, Caller  {
	generateAsEnumValue(structureName: string, platform: Platform): Option<string>;
	type(): Type;
}
