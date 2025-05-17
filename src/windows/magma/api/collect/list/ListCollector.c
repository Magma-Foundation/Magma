#include "./ListCollector.h"
export class ListCollector<T> implements Collector<T, List<T>> {
}

List<T> createInitial() {
	return Lists/*auto*/.empty(/*auto*/);
}
List<T> fold(List<T> current, T element) {
	return current/*List<T>*/.addLast(element/*T*/);
}