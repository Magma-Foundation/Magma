#include "./Queries.h"
export class Queries {
}

auto temp(T element) {
	return Queries.getTSingleHead(element);
}
auto temp() {
	return new EmptyHead<T>();
}
static Query<T> fromOption(Option<T> option) {
	return new HeadedQuery<T>(option.map(temp).orElseGet(temp));
}
static Head<T> getTSingleHead(T element) {
	return new SingleHead<T>(element);
}
static Query<T> fromArray(T[] elements) {
	/*return new HeadedQuery<Integer>(new RangeHead(elements.length)).map((Integer index) -> elements[index])*/;
}