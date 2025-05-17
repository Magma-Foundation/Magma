#include "./ZipHead.h"
export class ZipHead<T, R> implements Head<Tuple2<T, R>> {
	head: Head<T>;
	other: Query<R>;
	constructor (head: Head<T>, other: Query<R>) {
		this.head = head;
		this.other = other;
	}
}

next(): Option<Tuple2<T, R>> {
	return this.head.next().and(() => this.other.next());
}