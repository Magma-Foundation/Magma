#include "./Parameter.h"
export interface Parameter {
	&[I8] generate(Platform platform);
	Option<Definition> asDefinition();
}
