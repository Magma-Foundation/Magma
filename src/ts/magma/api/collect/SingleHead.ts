import { None } from "../../../magma/api/option/None";
import { Option } from "../../../magma/api/option/Option";
import { Some } from "../../../magma/api/option/Some";
import { Head } from "./Head";
export class SingleHead<T> implements Head<T> {
	element: T;
	retrieved: boolean;
	constructor (element: T) {
		this.element = element;
		this.retrieved = false;
	}
	next(): Option<T> {
		if (this.retrieved){
			return new None<T>();
		}
		this.retrieved = true;
		return new Some<T>(this.element);
	}
}
