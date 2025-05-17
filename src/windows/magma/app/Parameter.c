#include "./Parameter.h"
interface Parameter {
	mut generate(): &[I8];
	mut asDefinition(): Option<Definition>;
}
