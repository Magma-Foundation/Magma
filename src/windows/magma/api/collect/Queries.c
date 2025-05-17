#include "./Queries.h"
export class Queries {
}

static Query<T> fromOption(Option<T> option) {
	return new HeadedQuery<T>(option.map((T element) => Queries.getTSingleHead(element)).orElseGet(() => new EmptyHead<T>()));
}
static Head<T> getTSingleHead(T element) {
	return new SingleHead<T>(element);
}
static Query<T> fromArray(T[] elements) {
	/*return new HeadedQuery<Integer>(new RangeHead(elements.length)).map((Integer index) -> elements[index])*/;
}