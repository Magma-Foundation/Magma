#include "./Strings.h"
export interface StringsInstance {
	number length(&[I8] stripped);

	&[I8] sliceBetween(&[I8] input, number startInclusive, number endExclusive);

	&[I8] sliceFrom(&[I8] input, number startInclusive);

	Bool isEmpty(&[I8] cache);

	Bool equalsTo(&[I8] left, &[I8] right);

	&[I8] strip(&[I8] input);

	Bool isBlank(&[I8] value);

	I8 charAt(&[I8] input, number index);

}
