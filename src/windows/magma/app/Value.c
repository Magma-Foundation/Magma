#include "./Value.h"
export interface Value extends Argument, Caller  {
	mut resolve(mut state: CompileState): Type;
	mut generateAsEnumValue(mut structureName: &[I8]): Option<&[I8]>;
}
