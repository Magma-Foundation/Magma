#include "./Queries.h"
export class Queries {
}

mut static fromOption<T>(option: Option<T>): Query<T> {
	return new HeadedQuery<T>(option.map((mut element: T) => Queries.getTSingleHead(element)).orElseGet(() => new EmptyHead<T>()));
}
mut static getTSingleHead<T>(element: T): Head<T> {
	return new SingleHead<T>(element);
}
mut static fromArray<T>(elements: T[]): Query<T> {
	/*return new HeadedQuery<Integer>(new RangeHead(elements.length)).map((Integer index) -> elements[index])*/;
}