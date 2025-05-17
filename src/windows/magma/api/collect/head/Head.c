#include "./Head.h"
export interface Head<T> {
	mut next(): Option<T>;
}
