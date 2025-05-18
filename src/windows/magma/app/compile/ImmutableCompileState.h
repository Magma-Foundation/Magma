/*[
	Actual, 
	Characters, 
	Collector, 
	CompileState, 
	Console, 
	Definition, 
	EmptyHead, 
	Err, 
	Files, 
	FlatMapHead, 
	FunctionHeader, 
	FunctionSegment, 
	Head, 
	HeadedQuery, 
	IOError, 
	ImmutableCompileState, 
	Joiner, 
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
	Queries, 
	Query, 
	RangeHead, 
	Result, 
	SingleHead, 
	Some, 
	Strings, 
	Tuple2, 
	Tuple2Impl, 
	Type, 
	ZipHead
]*/
#ifndef magma_app_compile_ImmutableCompileState
#define magma_app_compile_ImmutableCompileState
import { CompileState } from "magma/app/compile/CompileState";
import { Platform } from "magma/app/io/Platform";
import { Location } from "magma/app/io/Location";
import { Option } from "magma/api/option/Option";
import { Source } from "magma/app/io/Source";
import { List } from "magma/api/collect/list/List";
import { Import } from "magma/app/compile/Import";
import { Definition } from "magma/app/compile/define/Definition";
import { None } from "magma/api/option/None";
import { Lists } from "jvm/api/collect/list/Lists";
import { Strings } from "jvm/api/text/Strings";
import { Some } from "magma/api/option/Some";
import { Type } from "magma/api/Type";
#endif
