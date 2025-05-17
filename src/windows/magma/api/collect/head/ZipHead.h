import { Tuple2 } from "magma/api/Tuple2";
import { Head } from "magma/api/collect/head/Head";
import { Query } from "magma/api/collect/Query";
import { Option } from "magma/api/option/Option";
export class ZipHead<T, R> implements Head<Tuple2<T, R>> {
	mut head: Head<T>;
	mut other: Query<R>;
	constructor (mut head: Head<T>, mut other: Query<R>) {
		this.head = head;
		this.other = other;
	}
	mut next(): Option<Tuple2<T, R>> {
		return this.head.next().and(() => this.other.next());
	}
}
