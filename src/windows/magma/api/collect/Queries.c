#include "./Queries.h"
export class Queries {
}

static fromOption<T>(option: Option<T>): Query<T> {
	return new HeadedQuery<T>(option.map((element: T) => Queries.getTSingleHead(element)).orElseGet(() => new EmptyHead<T>()));
}
static getTSingleHead<T>(element: T): Head<T> {
	return new SingleHead<T>(element);
}
static fromArray<T>(elements: T[]): Query<T> {
	/*return new HeadedQuery<Integer>(new RangeHead(elements.length)).map((Integer index) -> elements[index])*/;
}