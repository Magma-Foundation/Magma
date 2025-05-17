export interface StringsInstance {
	mut length(stripped: &[I8]): number;

	mut sliceBetween(input: &[I8], startInclusive: number, endExclusive: number): &[I8];

	mut sliceFrom(input: &[I8], startInclusive: number): &[I8];

	mut isEmpty(cache: &[I8]): Bool;

	mut equalsTo(left: &[I8], right: &[I8]): Bool;

	mut strip(input: &[I8]): &[I8];

	mut isBlank(value: &[I8]): Bool;

	mut charAt(input: &[I8], index: number): I8;

}
