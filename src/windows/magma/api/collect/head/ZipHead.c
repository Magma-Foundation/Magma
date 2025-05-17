#include "./ZipHead.h"
export class ZipHead<T, R> implements Head<Tuple2<T, R>> {
	Head<T> head;
	Query<R> other;
	constructor (Head<T> head, Query<R> other) {
		this.head = head;
		this.other = other;
	}
}

auto temp() {
	return this/*auto*/.other.next(/*auto*/);
}
Option<Tuple2<T, R>> next() {
	return this/*auto*/.head.next(/*auto*/).and(lambdaDefinition/*auto*/);
}