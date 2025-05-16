/*[
	Actual: magma.annotate, 
	Namespace: magma.annotate, 
	Collector: magma.api.collect, 
	EmptyHead: magma.api.collect, 
	Head: magma.api.collect, 
	List: magma.api.collect, 
	ListCollector: magma.api.collect, 
	Lists: magma.api.collect, 
	HeadedQuery: magma.api.collect.query, 
	Query: magma.api.collect.query, 
	RangeHead: magma.api.collect, 
	SingleHead: magma.api.collect, 
	Console: magma.api, 
	IOError: magma.api.io, 
	Path: magma.api.io, 
	None: magma.api.option, 
	Option: magma.api.option, 
	Some: magma.api.option, 
	Result: magma.api.result, 
	Characters: magma.api.text
]*/
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
