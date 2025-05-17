#include "./Type.h"
export interface Type {
	generate(): &[I8];
	isFunctional(): Bool;
	isVar(): Bool;
	generateBeforeName(): &[I8];
}
