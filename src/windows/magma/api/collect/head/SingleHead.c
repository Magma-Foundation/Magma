import { Head } from "magma/api/collect/head/Head";
import { Option } from "magma/api/option/Option";
import { None } from "magma/api/option/None";
import { Some } from "magma/api/option/Some";
export class SingleHead<T> implements Head<T> {
	element: T;
	mut retrieved: Bool;
	constructor (element: T) {
		this.element = element;
		this.retrieved = false;
	}
	mut next(): Option<T> {
		if (this.retrieved) {
			return new None<T>();
		}
		this.retrieved = true;
		return new Some<T>(this.element);
	}
}
