#include "./Caller.h"
export interface Caller {
	mut generate(): &[I8];
	mut findChild(): Option<Value>;
}
