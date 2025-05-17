#include "./Queries.h"
export class Queries {
}

auto temp(T element) {
	return Queries/*auto*/.getTSingleHead(element/*auto*/);
}
auto temp() {
	return new EmptyHead<T>(/*auto*/);
}
static Query<T> fromOption(Option<T> option) {
	return new HeadedQuery<T>(option/*auto*/.map(lambdaDefinition/*auto*/).orElseGet(lambdaDefinition/*auto*/));
}
static Head<T> getTSingleHead(T element) {
	return new SingleHead<T>(element/*auto*/);
}
static Query<T> fromArray(T[] elements) {
	/*return new HeadedQuery<Integer>(new RangeHead(elements.length)).map((Integer index) -> elements[index])*/;
}