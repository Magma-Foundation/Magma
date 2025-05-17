#include "./FlatMapHead.h"
export class FlatMapHead<T, R> implements Head<R> {
	(arg0 : T) => Query<R> mapper;
	Head<T> head;
	Query<R> current;
}

constructor (Head<T> head, Query<R> initial, (arg0 : T) => Query<R> mapper) {
	this.head = head;
	this.current = initial;
	this.mapper = mapper;
}
Option<R> next() {
	while (true) {
		Option<R> next = this.current.next();
		if (next.isPresent()) {
			return next;
		}
		Tuple2<Bool, Query<R>> tuple = this.head.next().map(this.mapper).toTuple(this.current);
		if (tuple.left()) {
			this.current = tuple.right();
		}
		else {
			return new None<R>();
		}
	}
}