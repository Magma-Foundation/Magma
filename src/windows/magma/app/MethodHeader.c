#include "./MethodHeader.h"
export interface MethodHeader<S extends MethodHeader<S>> {
	mut generateWithAfterName(mut afterName: &[I8]): &[I8];
	mut hasAnnotation(mut annotation: &[I8]): Bool;
	mut removeModifier(mut modifier: &[I8]): S;
	mut addModifier(mut modifier: &[I8]): S;
}
