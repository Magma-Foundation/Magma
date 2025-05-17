#include "./ListCollector.h"
export class ListCollector<T> implements Collector<T, List<T>> {
}

mut createInitial(): List<T> {
	return Lists.empty();
}
mut fold(current: List<T>, element: T): List<T> {
	return current.addLast(element);
}