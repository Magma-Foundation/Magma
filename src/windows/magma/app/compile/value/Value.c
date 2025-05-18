#include "./Value.h"
export interface Value extends Argument, Caller  {
	Option<&[I8]> generateAsEnumValue(&[I8] structureName, Platform platform);
	Type type();
}
