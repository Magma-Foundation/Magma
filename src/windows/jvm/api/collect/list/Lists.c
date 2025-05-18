#include "./Lists.h"
export interface ListsInstance {
	static List<T> fromArray(T[] elements);
	static List<T> empty();
	static List<T> of(...T[] elements);
}
