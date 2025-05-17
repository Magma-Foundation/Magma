#include "./FlatMapHead.h"
export class FlatMapHead<T, R> implements Head<R> {
	mapper: (arg0 : T) => Query<R>;
	head: Head<T>;
	mut current: Query<R>;
}

constructor (head: Head<T>, initial: Query<R>, mapper: (arg0 : T) => Query<R>) {
	this.head = head;
	this.current = initial;
	this.mapper = mapper;
}
mut next(): Option<R> {
	while (true) {
		let next = this.current.next();
		if (next.isPresent()) {
			return next;
		}
		let tuple = this.head.next().map(this.mapper).toTuple(this.current);
		if (tuple.left()) {
			this.current = tuple.right();
		}
		else {
			return new None<R>();
		}
	}
}