#include "./Strings.h"
export interface StringsInstance {
	length(stripped: &[I8]): number;

	sliceBetween(input: &[I8], startInclusive: number, endExclusive: number): &[I8];

	sliceFrom(input: &[I8], startInclusive: number): &[I8];

	isEmpty(cache: &[I8]): Bool;

	equalsTo(left: &[I8], right: &[I8]): Bool;

	strip(input: &[I8]): &[I8];

	isBlank(value: &[I8]): Bool;

	charAt(input: &[I8], index: number): I8;

}
