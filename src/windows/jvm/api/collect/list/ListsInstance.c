#include "./Lists.h"
export interface ListsInstance {
	List<T> fromArray(T[] elements);

	List<T> empty();

	List<T> of(...T[] elements);

}
