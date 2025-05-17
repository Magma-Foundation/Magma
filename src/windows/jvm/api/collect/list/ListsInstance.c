#include "./Lists.h"
export interface ListsInstance {
	fromArray<T>(elements: T[]): List<T>;

	empty<T>(): List<T>;

	of<T>(...elements: T[]): List<T>;

}
