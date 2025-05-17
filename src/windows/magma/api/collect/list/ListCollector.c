#include "./ListCollector.h"
export class ListCollector<T> implements Collector<T, List<T>> {
}

List<T> createInitial() {
	return Lists.empty();
}
List<T> fold(List<T> current, T element) {
	return current.addLast(element);
}