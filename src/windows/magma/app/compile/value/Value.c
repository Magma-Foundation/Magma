#include "./Value.h"
export interface Value extends Argument, Caller  {
	resolve(state: CompileState): Type;
	generateAsEnumValue(structureName: &[I8]): Option<&[I8]>;
}
