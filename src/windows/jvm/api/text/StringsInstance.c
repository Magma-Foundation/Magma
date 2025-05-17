#include "./Strings.h"
export interface StringsInstance {static number length(&[I8] stripped);static &[I8] sliceBetween(&[I8] input, number startInclusive, number endExclusive);static &[I8] sliceFrom(&[I8] input, number startInclusive);static Bool isEmpty(&[I8] cache);static Bool equalsTo(&[I8] left, &[I8] right);static &[I8] strip(&[I8] input);static Bool isBlank(&[I8] value);static I8 charAt(&[I8] input, number index);
}
