#include "./ListCollector.h"
export class ListCollector<T> implements Collector<T, List<T>> {
}

createInitial(): List<T> {
	return Lists.empty();
}
fold(current: List<T>, element: T): List<T> {
	return current.addLast(element);
}