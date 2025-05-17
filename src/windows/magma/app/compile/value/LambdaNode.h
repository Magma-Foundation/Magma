/*[
	AccessNode, 
	Actual, 
	Argument, 
	ArrayType, 
	BooleanType, 
	Caller, 
	Characters, 
	Collector, 
	CompileState, 
	Console, 
	ConstructionCaller, 
	ConstructorHeader, 
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
	InvokableNode, 
	Joiner, 
	LambdaNode, 
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
	SliceType, 
	Some, 
	Strings, 
	TemplateType, 
	Tuple2, 
	Tuple2Impl, 
	Type, 
	VariadicType, 
	Whitespace, 
	ZipHead
]*/
#ifndef magma_app_compile_value_LambdaNode
#define magma_app_compile_value_LambdaNode
import { Value } from "magma/app/compile/value/Value";
import { Definition } from "magma/app/compile/define/Definition";
import { List } from "magma/api/collect/list/List";
import { Platform } from "magma/app/io/Platform";
import { Joiner } from "magma/api/collect/Joiner";
import { Option } from "magma/api/option/Option";
import { Some } from "magma/api/option/Some";
import { None } from "magma/api/option/None";
import { Type } from "magma/api/Type";
import { PrimitiveType } from "magma/app/compile/type/PrimitiveType";
#endif
