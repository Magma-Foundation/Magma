#include "./Value.h"
export interface Value extends Argument, Caller  {
	Type resolve(CompileState state);
	Option<&[I8]> generateAsEnumValue(&[I8] structureName, Platform platform);
}
