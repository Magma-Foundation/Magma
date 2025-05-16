// [Actual, Namespace, Collector, EmptyHead, Head, List, ListCollector, Lists, HeadedQuery, Query, RangeHead, SingleHead, Console, IOError, Path, None, Option, Some, Result, Characters]
import { Actual } from "../../../magma/annotate/Actual";
import { Namespace } from "../../../magma/annotate/Namespace";
export interface StringsInstance {
	length(stripped: string): number;

	sliceBetween(input: string, startInclusive: number, endExclusive: number): string;

	sliceFrom(input: string, startInclusive: number): string;

	isEmpty(cache: string): boolean;

	equalsTo(left: string, right: string): boolean;

	strip(input: string): string;

	isBlank(value: string): boolean;

}
export declare const Strings: StringsInstance;
