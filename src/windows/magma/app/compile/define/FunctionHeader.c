#include "./FunctionHeader.h"
export interface FunctionHeader<S extends FunctionHeader<S>> {
	generateWithAfterName(afterName: &[I8]): &[I8];
	hasAnnotation(annotation: &[I8]): Bool;
	removeModifier(modifier: &[I8]): S;
	addModifier(modifier: &[I8]): S;
}
