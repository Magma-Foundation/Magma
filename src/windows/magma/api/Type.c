#include "./Type.h"
export interface Type {
	mut generate(): &[I8];
	mut isFunctional(): Bool;
	mut isVar(): Bool;
	mut generateBeforeName(): &[I8];
}
