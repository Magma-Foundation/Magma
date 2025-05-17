#include "./Head.h"
export interface Head<T> {
	next(): Option<T>;
}
