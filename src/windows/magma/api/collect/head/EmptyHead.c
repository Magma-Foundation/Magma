#include "./EmptyHead.h"
export class EmptyHead<T> implements Head<T> {
}

mut next(): Option<T> {
	return new None<T>();
}