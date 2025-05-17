#include "./ZipHead.h"
export class ZipHead<T, R> implements Head<Tuple2<T, R>> {
	mut head: Head<T>;
	mut other: Query<R>;
	constructor (mut head: Head<T>, mut other: Query<R>) {
		this.head = head;
		this.other = other;
	}
}

mut next(): Option<Tuple2<T, R>> {
	return this.head.next().and(() => this.other.next());
}