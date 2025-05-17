import { Result } from "../../../magma/api/result/Result";
import { Option } from "../../../magma/api/option/Option";
import { None } from "../../../magma/api/option/None";
import { Some } from "../../../magma/api/option/Some";
export class Ok<T, X> implements Result<T, X> {
	value: T;
	constructor (value: T) {
		this.value = value;
	}
	findError(): Option<X> {
		return new None<X>(/*auto*/);
	}
	findValue(): Option<T> {
		return new Some<T>(this/*auto*/.value);
	}
	match<R>(whenOk: (arg0 : T) => R, whenErr: (arg0 : X) => R): R {
		return whenOk/*(arg0 : T) => R*/(this/*auto*/.value);
	}
}
