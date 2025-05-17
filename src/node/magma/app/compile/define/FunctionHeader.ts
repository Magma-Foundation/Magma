// [Lists, Console, Files, Characters, Strings, Actual, Namespace, Collector, EmptyHead, FlatMapHead, Head, HeadedQuery, MapHead, RangeHead, SingleHead, ZipHead, Joiner, List, ListCollector, Queries, Query, IOError, Path, None, Option, Some, Err, Ok, Result, Tuple2, Tuple2Impl, Type, CompileState, Definition, FunctionHeader]
import { Platform } from "../../../../magma/app/io/Platform";
import { Definition } from "../../../../magma/app/compile/define/Definition";
import { List } from "../../../../magma/api/collect/list/List";
export interface FunctionHeader<S extends FunctionHeader<S>> {
	generateWithDefinitions(platform: Platform, definitions: List<Definition>): string;
	hasAnnotation(annotation: string): boolean;
	removeModifier(modifier: string): S;
	addModifierLast(modifier: string): S;
}
