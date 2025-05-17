#include "./Argument.h"
export interface Argument {
	toValue(): Option<Value>;
}
