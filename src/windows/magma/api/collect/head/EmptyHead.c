#include "./EmptyHead.h"
export class EmptyHead<T> implements Head<T> {
}

next(): Option<T> {
	return new None<T>();
}