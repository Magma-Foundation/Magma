#include "./Caller.h"
export interface Caller {
	generate(): &[I8];
	findChild(): Option<Value>;
}
