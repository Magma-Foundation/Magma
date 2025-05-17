export interface StringsInstance {
	length(stripped: string): number;

	sliceBetween(input: string, startInclusive: number, endExclusive: number): string;

	sliceFrom(input: string, startInclusive: number): string;

	isEmpty(cache: string): boolean;

	equalsTo(left: string, right: string): boolean;

	strip(input: string): string;

	isBlank(value: string): boolean;

	charAt(input: string, index: number): string;

}
