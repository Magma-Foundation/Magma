// [Actual, Namespace, Collector, EmptyHead, Head, List, ListCollector, Lists, HeadedQuery, Query, RangeHead, SingleHead, Console, IOError, Path, None, Option, Some, Result]
import { Actual } from "../../../magma/annotate/Actual";
import { Namespace } from "../../../magma/annotate/Namespace";
export interface CharactersInstance {
	isDigit(c: string): boolean;

	isLetter(c: string): boolean;

}
export declare const Characters: CharactersInstance;
