// []
import { Tuple2 } from "../../../../magma/api/Tuple2";
import { Head } from "../../../../magma/api/collect/head/Head";
import { Query } from "../../../../magma/api/collect/Query";
import { Option } from "../../../../magma/api/option/Option";
export class ZipHead<T, R> implements Head<Tuple2<T, R>> {
	head: Head<T>;
	other: Query<R>;
	constructor (head: Head<T>, other: Query<R>) {
		this.head = head;
		this.other = other;
	}
	next(): Option<Tuple2<T, R>> {
		return this/*auto*/.head.next(/*auto*/).and(() => this/*auto*/.other.next(/*auto*/));
	}
}
