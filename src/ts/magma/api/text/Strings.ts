import { Actual } from "../../../magma/Actual";
export class Strings  {
	static declare length(stripped: string): number;
	static declare sliceBetween(input: string, startInclusive: number, endExclusive: number): string;
	static declare sliceFrom(input: string, startInclusive: number): string;
	static declare isEmpty(cache: string): boolean;
	static declare equalsTo(left: string, right: string): boolean;
	static declare strip(input: string): string;
	static declare isBlank(value: string): boolean;
}
