#include "./MapHead.h"
export class MapHead<T, R> implements Head<R> {
	head: Head<T>;
	mapper: (arg0 : T) => R;
	constructor (head: Head<T>, mapper: (arg0 : T) => R) {
		this.head = head;
		this.mapper = mapper;
	}
}

next(): Option<R> {
	return this.head.next().map(this.mapper);
}