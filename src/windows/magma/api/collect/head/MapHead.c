#include "./MapHead.h"
export class MapHead<T, R> implements Head<R> {
	Head<T> head;
	(arg0 : T) => R mapper;
	constructor (Head<T> head, (arg0 : T) => R mapper) {
		this.head = head;
		this.mapper = mapper;
	}
}

Option<R> next() {
	return this/*auto*/.head.next(/*auto*/).map(this/*auto*/.mapper);
}