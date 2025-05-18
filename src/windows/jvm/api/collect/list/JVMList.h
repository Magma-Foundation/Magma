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
	IncompleteRoot, 
	IncompleteRootSegment, 
	InvokableNode, 
	Joiner, 
	LambdaNode, 
	List, 
	ListCollector, 
	Lists, 
	Location, 
	Main, 
	MapHead, 
	Namespace, 
	None, 
	NotNode, 
	Ok, 
	OperationNode, 
	Option, 
	Parameter, 
	Path, 
	Placeholder, 
	Platform, 
	PrimitiveType, 
	Queries, 
	Query, 
	RangeHead, 
	Result, 
	SingleHead, 
	SliceType, 
	Some, 
	Source, 
	StringNode, 
	Strings, 
	SymbolNode, 
	TemplateType, 
	Tuple2, 
	Tuple2Impl, 
	Type, 
	Value, 
	VariadicType, 
	Whitespace, 
	ZipHead
]*/
#ifndef magma_app_Main
#define magma_app_Main
import { List } from "magma/api/collect/list/List";
import { Path } from "magma/api/io/Path";
import { Head } from "magma/api/collect/head/Head";
import { Option } from "magma/api/option/Option";
import { None } from "magma/api/option/None";
import { Query } from "magma/api/collect/Query";
import { Collector } from "magma/api/collect/Collector";
import { MapHead } from "magma/api/collect/head/MapHead";
import { FlatMapHead } from "magma/api/collect/head/FlatMapHead";
import { EmptyHead } from "magma/api/collect/head/EmptyHead";
import { Tuple2 } from "magma/api/Tuple2";
import { ZipHead } from "magma/api/collect/head/ZipHead";
import { Result } from "magma/api/result/Result";
import { Ok } from "magma/api/result/Ok";
import { SingleHead } from "magma/api/collect/head/SingleHead";
import { Some } from "magma/api/option/Some";
import { Lists } from "jvm/api/collect/list/Lists";
import { HeadedQuery } from "magma/api/collect/head/HeadedQuery";
import { IOError } from "magma/api/io/IOError";
import { Tuple2Impl } from "magma/api/Tuple2Impl";
import { Platform } from "magma/app/io/Platform";
import { Location } from "magma/app/io/Location";
import { Definition } from "magma/app/compile/define/Definition";
import { Type } from "magma/api/Type";
import { Source } from "magma/app/io/Source";
import { Import } from "magma/app/compile/Import";
import { Joiner } from "magma/api/collect/Joiner";
import { Strings } from "jvm/api/text/Strings";
import { FunctionHeader } from "magma/app/compile/define/FunctionHeader";
import { CompileState } from "magma/app/compile/CompileState";
import { Main } from "magma/app/Main";
import { Value } from "magma/app/compile/value/Value";
import { PrimitiveType } from "magma/app/compile/type/PrimitiveType";
import { Parameter } from "magma/app/compile/define/Parameter";
import { Caller } from "magma/app/compile/value/Caller";
import { ConstructorHeader } from "magma/app/compile/value/ConstructorHeader";
import { ListCollector } from "magma/api/collect/list/ListCollector";
import { Files } from "jvm/api/io/Files";
import { Console } from "jvm/api/io/Console";
import { Queries } from "magma/api/collect/Queries";
import { ImmutableCompileState } from "magma/app/compile/ImmutableCompileState";
import { DivideState } from "magma/app/compile/text/DivideState";
import { FunctionSegment } from "magma/app/compile/FunctionSegment";
import { ConstructionCaller } from "magma/app/compile/value/ConstructionCaller";
import { Argument } from "magma/app/compile/value/Argument";
import { InvokableNode } from "magma/app/compile/value/InvokableNode";
import { StringNode } from "magma/app/compile/value/StringNode";
import { NotNode } from "magma/app/compile/value/NotNode";
import { LambdaNode } from "magma/app/compile/value/LambdaNode";
import { SymbolNode } from "magma/app/compile/value/SymbolNode";
import { AccessNode } from "magma/app/compile/value/AccessNode";
import { OperationNode } from "magma/app/compile/value/OperationNode";
import { RangeHead } from "magma/api/collect/head/RangeHead";
import { Characters } from "jvm/api/text/Characters";
import { Whitespace } from "magma/app/compile/text/Whitespace";
import { Placeholder } from "magma/app/compile/text/Placeholder";
import { ArrayType } from "magma/app/compile/type/ArrayType";
import { VariadicType } from "magma/app/compile/type/VariadicType";
import { SliceType } from "magma/app/compile/type/SliceType";
import { BooleanType } from "magma/app/compile/type/BooleanType";
import { TemplateType } from "magma/app/compile/type/TemplateType";
import { FunctionType } from "magma/app/compile/type/FunctionType";
#endif
