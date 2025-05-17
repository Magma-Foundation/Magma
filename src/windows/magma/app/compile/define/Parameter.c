#include "./Parameter.h"
export interface Parameter {
	generate(): &[I8];
	asDefinition(): Option<Definition>;
}
