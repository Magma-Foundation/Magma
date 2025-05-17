#include "./FunctionHeader.h"
export interface FunctionHeader<S extends FunctionHeader<S>> {
	&[I8] generateWithAfterName(Platform platform, &[I8] afterName);
	Bool hasAnnotation(&[I8] annotation);
	S removeModifier(&[I8] modifier);
	S addModifier(&[I8] modifier);
}
