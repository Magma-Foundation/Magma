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
	Head, 
	HeadedQuery, 
	IOError, 
	Joiner, 
	List, 
	ListCollector, 
	Lists, 
	MapHead, 
	Namespace, 
	None, 
	Ok, 
	Option, 
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
import { Platform } from "../../../../magma/app/io/Platform";
import { Definition } from "../../../../magma/app/compile/define/Definition";
import { List } from "../../../../magma/api/collect/list/List";
export interface FunctionHeader<S extends FunctionHeader<S>> {
	generateWithDefinitions(platform: Platform, definitions: List<Definition>): string;
	hasAnnotation(annotation: string): boolean;
	removeModifier(modifier: string): S;
	addModifierLast(modifier: string): S;
}
