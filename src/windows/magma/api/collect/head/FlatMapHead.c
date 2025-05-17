#include "./FlatMapHead.h"
export class FlatMapHead<T, R> implements Head<R> {
	(arg0 : T) => Query<R> mapper;
	Head<T> head;
	Query<R> current;
}

constructor (Head<T> head, Query<R> initial, (arg0 : T) => Query<R> mapper) {
	this/*auto*/.head = head/*Head<T>*/;
	this/*auto*/.current = initial/*Query<R>*/;
	this/*auto*/.mapper = mapper/*(arg0 : T) => Query<R>*/;
}
Option<R> next() {
	while (true/*auto*/) {
		Option<R> next = this/*auto*/.current.next(/*auto*/);
		if (next/*auto*/.isPresent(/*auto*/)) {
			return next/*auto*/;
		}
		Tuple2<Bool, Query<R>> tuple = this/*auto*/.head.next(/*auto*/).map(this/*auto*/.mapper).toTuple(this/*auto*/.current);
		if (tuple/*auto*/.left(/*auto*/)) {
			this/*auto*/.current = tuple/*auto*/.right(/*auto*/);
		}
		else {
			return new None<R>(/*auto*/);
		}
	}
}