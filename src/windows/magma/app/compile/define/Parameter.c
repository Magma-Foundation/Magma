#include "./Parameter.h"
export interface Parameter {
	mut generate(): &[I8];
	mut asDefinition(): Option<Definition>;
}
