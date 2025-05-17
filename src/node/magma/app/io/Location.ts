// [Lists, Console, Files, Characters, Strings, Actual, Namespace, Collector, EmptyHead, FlatMapHead, Head, HeadedQuery, MapHead, RangeHead, SingleHead, ZipHead, Joiner, List, ListCollector, Queries, Query, IOError, Path, None, Option, Some, Err, Ok, Result, Tuple2, Tuple2Impl, Type, CompileState, Definition, FunctionHeader, Parameter, FunctionSegment, ImmutableCompileState, Import, DivideState, Placeholder, Whitespace, ArrayType, BooleanType, FunctionType, PrimitiveType, SliceType, TemplateType, VariadicType, AccessNode, Argument, Caller, ConstructionCaller, ConstructorHeader, InvokableNode, LambdaNode, NotNode, OperationNode, StringNode, SymbolNode, Value, Location]
import { List } from "../../../magma/api/collect/list/List";
export class Location {
	namespace: List<string>;
	name: string;
	constructor (namespace: List<string>, name: string) {
		this.namespace = namespace;
		this.name = name;
	}
}
