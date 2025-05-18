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
		var next = this/*auto*/.current.next(/*auto*/);
		if (next/*(arg0 : T) => R*/(/*auto*/)) {
			return next/*(arg0 : T) => R*/;
		}
		var tuple = this/*auto*/.head.next(/*auto*/).map(this/*auto*/.mapper).toTuple(this/*auto*/.current);
		if (tuple/*Tuple2<CompileState, Whitespace>*/.left(/*auto*/)) {
			this/*auto*/.current = tuple/*Tuple2<CompileState, Whitespace>*/.right(/*auto*/);
		}
		else {
			return new None<R>(/*auto*/);
		}
	}
}