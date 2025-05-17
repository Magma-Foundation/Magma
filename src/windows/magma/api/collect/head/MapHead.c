#include "./MapHead.h"
export class MapHead<T, R> implements Head<R> {
	mut head: Head<T>;
	mut mapper: (arg0 : T) => R;
	constructor (mut head: Head<T>, mut mapper: (arg0 : T) => R) {
		this.head = head;
		this.mapper = mapper;
	}
	mut next(): Option<R> {
		return this.head.next().map(this.mapper);
	}
}
