#include "./EmptyHead.h"
export class EmptyHead<T> implements Head<T> {
}

Option<T> next() {
	return new None<T>(/*auto*/);
}